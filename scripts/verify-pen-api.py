#!/usr/bin/env python3
"""Compare the rebuilt pen AAR's public/protected JVM API with reference JARs."""

from __future__ import annotations

import argparse
import json
import os
import subprocess
import sys
import zipfile
from pathlib import Path


def class_names(jar: Path) -> list[str]:
    with zipfile.ZipFile(jar) as archive:
        return sorted(
            name[:-6].replace("/", ".")
            for name in archive.namelist()
            if name.endswith(".class")
        )


def javap_api(classpath: str | Path, class_name: str) -> tuple[str, tuple[tuple[str, str], ...]]:
    result = subprocess.run(
        ["javap", "-classpath", str(classpath), "-protected", "-s", class_name],
        check=False,
        capture_output=True,
        text=True,
    )
    if result.returncode != 0:
        raise RuntimeError(f"javap failed for {class_name}: {result.stderr.strip()}")
    lines = [
        line.strip()
        for line in result.stdout.splitlines()
        if line.strip() and not line.startswith("Compiled from")
    ]
    if not lines:
        raise RuntimeError(f"javap returned no API for {class_name}")
    declaration = lines[0]
    members: list[tuple[str, str]] = []
    index = 1
    while index < len(lines):
        line = lines[index]
        if line == "}":
            index += 1
            continue
        if index + 1 < len(lines) and lines[index + 1].startswith("descriptor:"):
            members.append((line, lines[index + 1]))
            index += 2
        else:
            members.append((line, ""))
            index += 1
    return declaration, tuple(sorted(members))


def compatibility_signature(member: tuple[str, str]) -> tuple[str, str]:
    """Ignore modifiers whose removal only broadens binary compatibility."""
    declaration, descriptor = member
    normalized = declaration.replace(" abstract ", " ").replace(" final ", " ")
    return normalized, descriptor


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--old-reference", required=True, type=Path)
    parser.add_argument("--native-reference", required=True, type=Path)
    parser.add_argument("--candidate", required=True, type=Path)
    parser.add_argument(
        "--candidate-dependency",
        action="append",
        default=[],
        type=Path,
        help="JAR whose classes are part of the candidate's transitive public API",
    )
    parser.add_argument(
        "--accepted-additions",
        type=Path,
        default=Path(__file__).with_name("pen-api-additions.json"),
    )
    args = parser.parse_args()

    candidate_paths = [args.candidate, *args.candidate_dependency]
    for path in (args.old_reference, args.native_reference, *candidate_paths):
        if not path.is_file():
            parser.error(f"missing JAR: {path}")
    candidate_classpath = os.pathsep.join(str(path) for path in candidate_paths)
    candidate_names = {
        class_name
        for candidate_path in candidate_paths
        for class_name in class_names(candidate_path)
    }

    try:
        additions_payload = json.loads(
            args.accepted_additions.read_text(encoding="utf-8")
        )
        accepted_extra_classes = set(additions_payload.pop("__extraClasses__", []))
        accepted_additions = {
            class_name: {tuple(member) for member in members}
            for class_name, members in additions_payload.items()
        }
    except (OSError, ValueError, TypeError) as error:
        parser.error(f"invalid accepted-additions file: {error}")

    checked = 0
    observed_additions: set[tuple[str, tuple[str, str]]] = set()
    failures: list[str] = []
    reference_names: set[str] = set()
    for reference in (args.old_reference, args.native_reference):
        for class_name in class_names(reference):
            reference_names.add(class_name)
            try:
                expected = javap_api(reference, class_name)
            except RuntimeError as error:
                failures.append(f"reference JAR {reference}: {error}")
                continue
            # Obfuscator-generated members of package-private implementation
            # classes are not part of the consumable SDK API.
            if not expected[0].startswith("public "):
                continue
            checked += 1
            try:
                actual = javap_api(candidate_classpath, class_name)
            except RuntimeError as error:
                failures.append(str(error))
                continue
            expected_declaration, expected_member_tuple = expected
            actual_declaration, actual_member_tuple = actual
            expected_members = {compatibility_signature(item) for item in expected_member_tuple}
            actual_members = {compatibility_signature(item) for item in actual_member_tuple}
            extra_members = actual_members - expected_members
            allowed_members = {
                compatibility_signature(item)
                for item in accepted_additions.get(class_name, set())
            }
            unaccepted_extras = extra_members - allowed_members
            # Compatibility is one-directional: every reference member must
            # remain, while only explicitly listed additive API is allowed.
            if (expected_declaration == actual_declaration
                    and expected_members.issubset(actual_members)
                    and not unaccepted_extras):
                observed_additions.update((class_name, item) for item in extra_members)
                continue
            details = [f"API mismatch: {class_name}"]
            if expected_declaration != actual_declaration:
                details.extend(
                    [f"  expected declaration: {expected_declaration}", f"  actual declaration:   {actual_declaration}"]
                )
            details.extend(f"  missing: {item}" for item in sorted(expected_members - actual_members))
            details.extend(f"  unaccepted extra: {item}" for item in sorted(unaccepted_extras))
            failures.append("\n".join(details))

    candidate_only = sorted(
        class_name
        for class_name in candidate_names - reference_names
        if javap_api(candidate_classpath, class_name)[0].startswith("public ")
    )
    unaccepted_classes = sorted(set(candidate_only) - accepted_extra_classes)
    if unaccepted_classes:
        failures.append("Unaccepted candidate-only classes: " + ", ".join(unaccepted_classes))
    if failures:
        print("\n\n".join(failures), file=sys.stderr)
        return 1
    if candidate_only:
        print(
            f"Accepted {len(candidate_only)} intentional candidate-only classes: "
            f"{', '.join(candidate_only)}"
        )
    configured_additions = {
        (class_name, compatibility_signature(item))
        for class_name, members in accepted_additions.items()
        for item in members
    }
    unobserved = configured_additions - observed_additions
    if unobserved:
        print(
            "note: accepted additions not present in this reference comparison: "
            + ", ".join(sorted(f"{class_name}: {item[0]}" for class_name, item in unobserved))
        )
    print(f"Accepted {len(observed_additions)} intentional additive members.")
    print(f"Pen JVM API matches {checked} public reference classes.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
