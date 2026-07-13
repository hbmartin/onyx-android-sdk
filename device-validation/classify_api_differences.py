#!/usr/bin/env python3
"""Classified JVM API comparison between reference and recovered class jars.

Unlike the textual `javap -public` diff in api_surface.py, this audit reads
`javap -v` structures and compares three independent layers per class:

1. binary layer     - member (name, descriptor) sets, access flags,
                      superclass/interfaces: differences here can break
                      precompiled consumers (binary_breaking), or add
                      unintended public surface (extra_public_surface);
2. generic layer    - the optional Signature attribute: differences are
                      source-level generics only (source_generic);
3. metadata layer   - the kotlin.Metadata annotation and member annotation
                      sets: kotlin_metadata / annotation_only;
plus synthetic-only noise (compiler_artifact).

A class's primary classification is the most severe bucket present. Only
public/protected surface participates; private and package-private members
are ignored except when a reference public member became non-public.
"""

from __future__ import annotations

import argparse
import json
import re
import subprocess
import sys
import tempfile
import zipfile
from collections import Counter
from dataclasses import dataclass, field
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
sys.path.insert(0, str(ROOT / "scripts"))

from module_registry import RegistryError, load_registry  # noqa: E402

SEVERITY = [
    "binary_breaking",
    "extra_public_surface",
    "source_generic",
    "kotlin_metadata",
    "annotation_only",
    "compiler_artifact",
    "match",
]

ANONYMOUS = re.compile(r"\$\d+(\$|$)")
MEMBER_DECL = re.compile(r"^  (\S.*[;{])\s*$")
ATTR_DESCRIPTOR = re.compile(r"^    descriptor: (\S+)")
ATTR_FLAGS = re.compile(r"^    flags: \(0x[0-9a-fA-F]+\) ?(.*)")
ATTR_SIGNATURE = re.compile(r"^    Signature: #\d+\s+// (.+)")
CLASS_FLAGS = re.compile(r"^  flags: \(0x[0-9a-fA-F]+\) ?(.*)")
CLASS_SIGNATURE = re.compile(r"^Signature: #\d+\s+// (.+)")
ANNOTATION_NAME = re.compile(r"^(?:  |    |      |        )([\w.$]+)\($")


@dataclass
class Member:
    kind: str
    name: str
    descriptor: str
    flags: frozenset[str] = frozenset()
    signature: str | None = None
    annotations: tuple[str, ...] = ()

    @property
    def key(self) -> tuple[str, str, str]:
        return (self.kind, self.name, self.descriptor)

    @property
    def visible(self) -> bool:
        return "ACC_PUBLIC" in self.flags or "ACC_PROTECTED" in self.flags

    @property
    def synthetic(self) -> bool:
        return "ACC_SYNTHETIC" in self.flags

    @property
    def bridge(self) -> bool:
        return "ACC_BRIDGE" in self.flags

    def describe(self) -> str:
        vis = "public" if "ACC_PUBLIC" in self.flags else (
            "protected" if "ACC_PROTECTED" in self.flags else "internal")
        return f"{vis} {self.kind} {self.name} {self.descriptor}"


@dataclass
class ClassApi:
    name: str
    flags: frozenset[str] = frozenset()
    declaration: str = ""
    signature: str | None = None
    kotlin_metadata: str | None = None
    members: dict = field(default_factory=dict)

    @property
    def visible(self) -> bool:
        return "ACC_PUBLIC" in self.flags or "ACC_PROTECTED" in self.flags


@dataclass(frozen=True)
class AcceptedResiduals:
    class_findings: frozenset[tuple[str, str, str]] = frozenset()
    extra_classes: frozenset[str] = frozenset()


def strip_type_arguments(value: str) -> str:
    """Remove javap's generic type arguments while preserving erased names."""
    result = []
    depth = 0
    for char in value:
        if char == "<":
            depth += 1
        elif char == ">":
            depth = max(0, depth - 1)
        elif depth == 0:
            result.append(char)
    return "".join(result)


def class_hierarchy(api: ClassApi) -> tuple[str | None, tuple[str, ...]]:
    """Return the erased superclass and interfaces from a javap declaration."""
    declaration = " ".join(strip_type_arguments(api.declaration).split())
    kind_match = re.search(r"\b(class|interface)\s+[\w.$]+", declaration)
    if not kind_match:
        return None, ()
    kind = kind_match.group(1)
    tail = declaration[kind_match.end():]
    superclass = None
    interfaces: tuple[str, ...] = ()
    if kind == "interface":
        match = re.search(r"\bextends\s+(.+?)(?:\s+permits\s+|$)", tail)
        if match:
            interfaces = tuple(part.strip() for part in match.group(1).split(","))
    else:
        extends = re.search(r"\bextends\s+([^\s,]+)", tail)
        if extends:
            superclass = extends.group(1)
        elif api.name != "java.lang.Object":
            superclass = "java.lang.Object"
        implements = re.search(r"\bimplements\s+(.+?)(?:\s+permits\s+|$)", tail)
        if implements:
            interfaces = tuple(part.strip() for part in implements.group(1).split(","))
    return superclass, tuple(sorted(interfaces))


def class_names(jar: Path) -> list[str]:
    with zipfile.ZipFile(jar) as archive:
        return sorted(
            entry[:-6].replace("/", ".")
            for entry in archive.namelist()
            if entry.endswith(".class")
            and not entry.endswith("module-info.class")
            and not ANONYMOUS.search(entry[:-6].rsplit("/", 1)[-1])
        )


def run_javap(jar: Path, names: list[str]) -> str:
    output = []
    for start in range(0, len(names), 50):
        batch = names[start:start + 50]
        result = subprocess.run(
            ["javap", "-v", "-p", "-cp", str(jar), *batch],
            capture_output=True,
            text=True,
        )
        if result.returncode != 0:
            raise RuntimeError(
                f"javap failed on {jar} ({batch[0]}...): {result.stderr.strip()[:400]}")
        output.append(result.stdout)
    return "\n".join(output)


def member_name(decl: str, class_name: str) -> tuple[str, str]:
    """Returns (kind, name) from a javap member declaration line."""
    decl = decl.rstrip(";{ ").strip()
    if decl == "static":
        return ("method", "<clinit>")
    if "(" in decl:
        head = decl.split("(", 1)[0].strip()
        name = head.split()[-1].split(".")[-1] if " " in head else head.split(".")[-1]
        simple = class_name.rsplit(".", 1)[-1].rsplit("$", 1)[-1]
        if name == simple or head.replace(" ", "").endswith(class_name):
            return ("method", "<init>")
        return ("method", name)
    name = decl.split("=")[0].split()[-1]
    return ("field", name)


def parse_javap(text: str) -> dict[str, ClassApi]:
    classes: dict[str, ClassApi] = {}
    current: ClassApi | None = None
    member: Member | None = None
    in_body = False

    def commit_member() -> None:
        nonlocal member
        if current is not None and member is not None:
            member.annotations = tuple(sorted(set(member.annotations)))
            current.members[member.key] = member
        member = None

    lines = text.splitlines()
    index = 0
    while index < len(lines):
        line = lines[index]
        if line.startswith("Classfile "):
            commit_member()
            current = None
            in_body = False
            index += 1
            continue
        # Class declaration line: first column-0 line after the Classfile
        # header block that names the class.
        if (current is None and line and not line.startswith(" ")
                and ("class " in line or "interface " in line)):
            match = re.search(r"(?:class|interface)\s+([\w.$]+)", line)
            if match:
                current = ClassApi(name=match.group(1), declaration=line.strip())
            index += 1
            continue
        if current is None:
            index += 1
            continue
        if not in_body:
            flags = CLASS_FLAGS.match(line)
            if flags and not current.flags:
                current.flags = frozenset(
                    f.strip() for f in flags.group(1).split(",") if f.strip())
            if line == "{":
                in_body = True
            index += 1
            continue
        if line == "}":
            commit_member()
            in_body = False
            # Trailing class-level attributes until next Classfile header.
            while index + 1 < len(lines) and not lines[index + 1].startswith("Classfile "):
                index += 1
                tail = lines[index]
                signature = CLASS_SIGNATURE.match(tail)
                if signature:
                    current.signature = signature.group(1).strip()
                if tail.strip().startswith("kotlin.Metadata("):
                    metadata = [tail.strip()]
                    while index + 1 < len(lines) and lines[index + 1].startswith("    "):
                        index += 1
                        metadata.append(lines[index].strip())
                    current.kotlin_metadata = "\n".join(metadata)
            classes[current.name] = current
            current = None
            index += 1
            continue
        decl = MEMBER_DECL.match(line)
        if decl and not line.startswith("    "):
            commit_member()
            kind, name = member_name(decl.group(1), current.name)
            member = Member(kind=kind, name=name, descriptor="")
            index += 1
            continue
        if member is not None:
            descriptor = ATTR_DESCRIPTOR.match(line)
            if descriptor:
                member.descriptor = descriptor.group(1)
            flags = ATTR_FLAGS.match(line)
            if flags:
                member.flags = frozenset(
                    f.strip() for f in flags.group(1).split(",") if f.strip())
            signature = ATTR_SIGNATURE.match(line)
            if signature:
                member.signature = signature.group(1).strip()
            if line.strip() == "RuntimeVisibleAnnotations:":
                collected = []
                while index + 1 < len(lines):
                    upcoming = lines[index + 1]
                    if re.match(r"^\s+\d+: #\d+", upcoming):
                        index += 1
                        continue
                    name_match = re.match(r"^\s+([\w.$/;]+)\(", upcoming)
                    if name_match and not upcoming.strip().startswith("0x"):
                        collected.append(name_match.group(1))
                        index += 1
                        # Skip the annotation's argument lines.
                        while index + 1 < len(lines) and lines[index + 1].startswith(
                                " " * (len(upcoming) - len(upcoming.lstrip()) + 2)):
                            index += 1
                        continue
                    break
                member.annotations = tuple(sorted(set(member.annotations) | set(collected)))
        index += 1
    commit_member()
    return classes


DEFAULT_SUFFIX = "$default"


def classify_class(reference: ClassApi, candidate: ClassApi) -> dict:
    buckets: dict[str, list[str]] = {}

    def note(bucket: str, message: str) -> None:
        buckets.setdefault(bucket, []).append(message)

    ref_members = {k: m for k, m in reference.members.items() if m.visible}
    cand_members = {k: m for k, m in candidate.members.items() if m.visible}
    cand_all = candidate.members

    # Class-level binary attributes.
    breaking_flags = {"ACC_PUBLIC", "ACC_PROTECTED", "ACC_FINAL", "ACC_ABSTRACT",
                      "ACC_INTERFACE", "ACC_ENUM", "ACC_STATIC"}
    ref_class_flags = reference.flags & breaking_flags
    cand_class_flags = candidate.flags & breaking_flags
    if ref_class_flags != cand_class_flags:
        note("binary_breaking",
             f"class flags {sorted(ref_class_flags)} -> {sorted(cand_class_flags)}")
    ref_superclass, ref_interfaces = class_hierarchy(reference)
    cand_superclass, cand_interfaces = class_hierarchy(candidate)
    if ref_superclass != cand_superclass:
        note("binary_breaking",
             f"superclass changed: {ref_superclass!r} -> {cand_superclass!r}")
    if ref_interfaces != cand_interfaces:
        note("binary_breaking",
             f"interfaces changed: {list(ref_interfaces)} -> {list(cand_interfaces)}")

    for key, ref_member in ref_members.items():
        cand_member = cand_all.get(key)
        if cand_member is None:
            bucket = "binary_breaking"
            label = "missing member"
            if ref_member.name.endswith(DEFAULT_SUFFIX):
                label = "missing Kotlin $default bridge"
            elif ref_member.bridge:
                label = "missing bridge method"
            elif ref_member.synthetic and (
                    ref_member.name.startswith("access$")
                    or "$lambda" in ref_member.name
                    or ref_member.name.startswith("lambda$")):
                bucket = "compiler_artifact"
                label = "missing synthetic accessor"
            note(bucket, f"{label}: {ref_member.describe()}")
            continue
        if not cand_member.visible:
            note("binary_breaking",
                 f"visibility narrowed: {ref_member.describe()} -> {sorted(cand_member.flags)}")
            continue
        member_breaking = {"ACC_PUBLIC", "ACC_PROTECTED", "ACC_STATIC",
                           "ACC_FINAL", "ACC_ABSTRACT"}
        ref_flags = ref_member.flags & member_breaking
        cand_flags = cand_member.flags & member_breaking
        if ref_flags != cand_flags:
            note("binary_breaking",
                 f"member flags changed: {ref_member.describe()} "
                 f"{sorted(ref_flags)} -> {sorted(cand_flags)}")
        if (ref_member.signature or cand_member.signature) and \
                ref_member.signature != cand_member.signature:
            note("source_generic",
                 f"generic signature: {ref_member.describe()} "
                 f"{ref_member.signature!r} -> {cand_member.signature!r}")
        if set(ref_member.annotations) != set(cand_member.annotations):
            note("annotation_only",
                 f"annotations: {ref_member.describe()} "
                 f"{sorted(ref_member.annotations)} -> {sorted(cand_member.annotations)}")

    for key, cand_member in cand_members.items():
        if key in ref_members:
            continue
        if key in reference.members:
            note("extra_public_surface",
                 f"visibility widened: {cand_member.describe()}")
            continue
        if cand_member.kind == "method" and cand_member.name == "<init>" \
                and cand_member.descriptor == "()V":
            note("extra_public_surface",
                 "unintended public default constructor (reference has none)")
        elif cand_member.synthetic and not cand_member.bridge \
                and not cand_member.name.endswith(DEFAULT_SUFFIX):
            note("compiler_artifact", f"extra synthetic member: {cand_member.describe()}")
        else:
            note("extra_public_surface", f"extra member: {cand_member.describe()}")

    if (reference.signature or candidate.signature) and \
            reference.signature != candidate.signature:
        note("source_generic",
             f"class generic signature {reference.signature!r} -> {candidate.signature!r}")

    if reference.kotlin_metadata != candidate.kotlin_metadata:
        note("kotlin_metadata", "kotlin.Metadata annotation differs")

    primary = next((s for s in SEVERITY if s in buckets), "match")
    return {"classification": primary, "buckets": buckets}


def load_surface(jars: list[Path]) -> dict[str, ClassApi]:
    surface: dict[str, ClassApi] = {}
    for jar in jars:
        names = [n for n in class_names(jar) if n not in surface]
        parsed = parse_javap(run_javap(jar, names))
        for name in names:
            if name in parsed:
                surface[name] = parsed[name]
    return surface


def extract_classes_jar(aar: Path, workdir: Path) -> Path:
    destination = workdir / f"{aar.stem}-classes.jar"
    with zipfile.ZipFile(aar) as archive:
        destination.write_bytes(archive.read("classes.jar"))
    return destination


def load_accepted_residuals(path: Path | None) -> AcceptedResiduals:
    if path is None:
        return AcceptedResiduals()
    payload = json.loads(path.read_text(encoding="utf-8"))
    findings = frozenset(
        (class_name, bucket, message)
        for class_name, buckets in payload.get("classes", {}).items()
        for bucket, messages in buckets.items()
        for message in messages
    )
    return AcceptedResiduals(
        class_findings=findings,
        extra_classes=frozenset(payload.get("extraClasses", [])),
    )


def validate_accepted_residuals(
    accepted: AcceptedResiduals,
    results: dict[str, dict],
    extra_classes: list[str],
) -> None:
    actual_findings = {
        (class_name, bucket, message)
        for class_name, data in results.items()
        for bucket, messages in data.get("buckets", {}).items()
        for message in messages
    }
    stale_findings = sorted(accepted.class_findings - actual_findings)
    stale_extras = sorted(accepted.extra_classes - set(extra_classes))
    if stale_findings or stale_extras:
        details = []
        if stale_findings:
            details.append(f"stale class findings: {stale_findings}")
        if stale_extras:
            details.append(f"stale extra classes: {stale_extras}")
        raise ValueError("accepted residual file does not match the audit: " + "; ".join(details))


def unaccepted_classification(
    class_name: str,
    data: dict,
    accepted: AcceptedResiduals,
) -> str:
    for severity in SEVERITY[:-1]:
        messages = data.get("buckets", {}).get(severity, [])
        if any((class_name, severity, message) not in accepted.class_findings
               for message in messages):
            return severity
    return "match"


def gate_fails(unaccepted_counts: Counter[str], fail_on: str) -> bool:
    threshold = SEVERITY.index(fail_on)
    worst = min(
        (SEVERITY.index(classification) for classification, count
         in unaccepted_counts.items() if count),
        default=len(SEVERITY) - 1,
    )
    return worst <= threshold


def module_inputs(args: argparse.Namespace, workdir: Path) -> tuple[list[Path], list[Path]]:
    artifacts = Path(args.artifacts_root)
    recovery = Path(args.recovery_root)
    registry = load_registry(recovery)
    module = registry.module(args.module)
    reference_paths = module.device_validation.get("apiReferenceJars", [])
    if not reference_paths:
        raise RegistryError(f"Module {args.module} has no API reference inputs")
    reference = [artifacts / path for path in reference_paths]
    aar = recovery / module.aar_relative_path
    return reference, [extract_classes_jar(aar, workdir)]


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--module")
    parser.add_argument("--artifacts-root")
    parser.add_argument("--recovery-root")
    parser.add_argument("--reference", action="append", type=Path, default=[])
    parser.add_argument("--candidate", action="append", type=Path, default=[])
    parser.add_argument("--output", type=Path)
    parser.add_argument("--fail-on", choices=SEVERITY[:-1])
    parser.add_argument("--accepted-residuals", type=Path,
                        help="JSON file of exact known findings/extensions that may not fail the gate")
    args = parser.parse_args()

    with tempfile.TemporaryDirectory() as tmp:
        workdir = Path(tmp)
        if args.module:
            if not (args.artifacts_root and args.recovery_root):
                parser.error("--module requires --artifacts-root and --recovery-root")
            try:
                reference_jars, candidate_jars = module_inputs(args, workdir)
            except RegistryError as error:
                print(f"API classification failed: {error}", file=sys.stderr)
                return 1
        else:
            if not (args.reference and args.candidate):
                parser.error("pass --module or both --reference and --candidate")
            reference_jars = args.reference
            candidate_jars = [
                extract_classes_jar(j, workdir) if j.suffix == ".aar" else j
                for j in args.candidate
            ]

        reference_api = load_surface(reference_jars)
        candidate_api = load_surface(candidate_jars)

    results = {}
    counts: Counter[str] = Counter()
    for name, ref in sorted(reference_api.items()):
        if not ref.visible:
            continue
        cand = candidate_api.get(name)
        if cand is None:
            results[name] = {
                "classification": "binary_breaking",
                "buckets": {"binary_breaking": ["class missing from candidate"]},
            }
        else:
            results[name] = classify_class(ref, cand)
        counts[results[name]["classification"]] += 1

    extra_classes = sorted(
        name for name, api in candidate_api.items()
        if api.visible and name not in reference_api
    )
    accepted = load_accepted_residuals(args.accepted_residuals)
    try:
        validate_accepted_residuals(accepted, results, extra_classes)
    except ValueError as error:
        raise SystemExit(str(error)) from error
    # counts tallies reference classes by primary classification (it sums to
    # referenceClasses); extra candidate classes are reported separately but
    # participate in the gate through unaccepted_counts.
    unaccepted_counts: Counter[str] = Counter()
    for class_name, data in results.items():
        classification = unaccepted_classification(class_name, data, accepted)
        if classification != "match":
            unaccepted_counts[classification] += 1
    unaccepted_extra_count = sum(
        name not in accepted.extra_classes for name in extra_classes)
    if unaccepted_extra_count:
        unaccepted_counts["extra_public_surface"] += unaccepted_extra_count

    report = {
        "referenceClasses": sum(1 for a in reference_api.values() if a.visible),
        "candidateClasses": sum(1 for a in candidate_api.values() if a.visible),
        "counts": dict(counts),
        "unacceptedCounts": dict(unaccepted_counts),
        "extraClasses": extra_classes,
        "acceptedResiduals": {
            "classes": sorted({name for name, _, _ in accepted.class_findings}),
            "extraClasses": sorted(accepted.extra_classes),
        },
        "classes": {
            name: data for name, data in results.items()
            if data["classification"] != "match"
        },
    }
    text = json.dumps(report, indent=2, sort_keys=True)
    if args.output:
        args.output.write_text(text + "\n")
    else:
        print(text)

    print(
        "classified: "
        + ", ".join(f"{k}={v}" for k, v in sorted(counts.items()))
        + (f", extra_classes={len(extra_classes)}" if extra_classes else ""),
        file=sys.stderr,
    )
    if args.fail_on:
        if gate_fails(unaccepted_counts, args.fail_on):
            return 1
    return 0


if __name__ == "__main__":
    sys.exit(main())
