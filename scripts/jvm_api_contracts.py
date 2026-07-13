#!/usr/bin/env python3
"""Check deterministic JVM API contracts for every production AAR."""

from __future__ import annotations

import argparse
import difflib
import hashlib
import json
import sys
import tempfile
import zipfile
from pathlib import Path

from module_registry import load_registry


ROOT = Path(__file__).resolve().parent.parent
sys.path.insert(0, str(ROOT / "device-validation"))

from classify_api_differences import (  # noqa: E402
    ClassApi,
    class_hierarchy,
    class_names,
    parse_javap,
    run_javap,
)


# Intentional source incompatibilities that are documented in
# docs/API_INCOMPATIBILITIES.md and must not be reintroduced merely to satisfy
# a generated contract. Tuple fields are module, class, kind, name, descriptor.
EXCLUDED_MEMBERS = frozenset({
    (
        "onyxsdk-base",
        "com.onyx.android.sdk.firmware.api.OnyxOTAService",
        "method",
        "firmwareUpdate",
        "(Ljava/lang/String;)Lretrofit2/Call;",
    ),
})


def metadata_hash(metadata: str | None) -> str | None:
    if metadata is None:
        return None
    return hashlib.sha256(metadata.encode("utf-8")).hexdigest()


def canonical_class(api: ClassApi, module: str | None = None) -> dict:
    superclass, interfaces = class_hierarchy(api)
    members = []
    for member in sorted(api.members.values(), key=lambda item: item.key):
        if not member.visible:
            continue
        if (module, api.name, *member.key) in EXCLUDED_MEMBERS:
            continue
        members.append({
            "kind": member.kind,
            "name": member.name,
            "descriptor": member.descriptor,
            "flags": sorted(member.flags),
            "signature": member.signature,
            "annotations": list(member.annotations),
        })
    return {
        "flags": sorted(api.flags),
        "superclass": superclass,
        "interfaces": list(interfaces),
        "signature": api.signature,
        "kotlinMetadataSha256": metadata_hash(api.kotlin_metadata),
        "members": members,
    }


def contract_for_jar(jar: Path, module: str) -> dict:
    names = class_names(jar)
    parsed = parse_javap(run_javap(jar, names))
    visible = {
        name: canonical_class(api, module)
        for name, api in sorted(parsed.items())
        if api.visible
    }
    return {"formatVersion": 1, "module": module, "classes": visible}


def extract_classes(aar: Path, destination: Path) -> Path:
    if not aar.is_file():
        raise FileNotFoundError(f"Missing production AAR: {aar}")
    output = destination / f"{aar.stem}-classes.jar"
    with zipfile.ZipFile(aar) as archive:
        output.write_bytes(archive.read("classes.jar"))
    return output


def rendered(payload: dict) -> str:
    lines = [json.dumps({
        "formatVersion": payload["formatVersion"],
        "module": payload["module"],
    }, sort_keys=True, separators=(",", ":"))]
    for class_name, class_payload in payload["classes"].items():
        class_line = {"class": class_name}
        class_line.update({
            key: value for key, value in class_payload.items() if key != "members"
        })
        lines.append(json.dumps(class_line, sort_keys=True, separators=(",", ":")))
        for member in class_payload["members"]:
            lines.append(json.dumps(
                {"class": class_name, "member": member},
                sort_keys=True,
                separators=(",", ":"),
            ))
    return "\n".join(lines) + "\n"


def verify_or_update(path: Path, payload: dict, update: bool) -> str | None:
    actual = rendered(payload)
    if update:
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(actual, encoding="utf-8")
        return None
    if not path.is_file():
        return f"Missing JVM API contract: {path}"
    expected = path.read_text(encoding="utf-8")
    if expected == actual:
        return None
    diff = "".join(difflib.unified_diff(
        expected.splitlines(keepends=True),
        actual.splitlines(keepends=True),
        fromfile=str(path),
        tofile=f"{path} (current build)",
    ))
    return diff


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--root", type=Path, default=ROOT)
    parser.add_argument(
        "--update",
        action="store_true",
        help="Replace the checked-in contracts with the current release AAR surfaces.",
    )
    args = parser.parse_args()
    root = args.root.resolve()
    registry = load_registry(root)
    contracts = root / "scripts" / "jvm-api-contracts"
    failures = []

    with tempfile.TemporaryDirectory() as temporary:
        temp = Path(temporary)
        for module_metadata in registry.published_modules:
            module = module_metadata.artifact_id
            try:
                jar = extract_classes(root / module_metadata.aar_relative_path, temp)
                payload = contract_for_jar(jar, module)
                failure = verify_or_update(contracts / f"{module}.jsonl", payload, args.update)
                if failure:
                    failures.append(failure)
                else:
                    action = "Updated" if args.update else "Verified"
                    print(f"{action} {module}: {len(payload['classes'])} visible classes")
            except (OSError, RuntimeError, zipfile.BadZipFile, KeyError) as error:
                failures.append(f"{module}: {error}")

    if failures:
        print("\n\n".join(failures), file=sys.stderr)
        if not args.update:
            print(
                "\nIf the ABI change is intentional, review it and run "
                "./gradlew updateJvmApiContracts.",
                file=sys.stderr,
            )
        return 1
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
