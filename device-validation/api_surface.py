#!/usr/bin/env python3
"""Compare public outer-class APIs in supplied JARs and recovered AARs."""

from __future__ import annotations

import argparse
import json
import re
import subprocess
import tempfile
import zipfile
from pathlib import Path

DECLARATION = re.compile(r"^(?:public |protected )?(?:final |abstract )?(?:class|interface|enum) ([\w.$]+)")


def extract_aar_classes(aar: Path, destination: Path) -> Path:
    jar = destination / (aar.stem + "-classes.jar")
    with zipfile.ZipFile(aar) as archive:
        jar.write_bytes(archive.read("classes.jar"))
    return jar


def classes(jar: Path) -> list[str]:
    with zipfile.ZipFile(jar) as archive:
        return sorted(name[:-6].replace("/", ".") for name in archive.namelist()
                      if name.endswith(".class") and "$" not in name and not name.endswith("module-info.class"))


def javap(jar: Path, names: list[str], classpath: list[Path]) -> dict[str, list[str]]:
    result: dict[str, list[str]] = {}
    cp = ":".join(str(path) for path in [jar, *classpath])
    for offset in range(0, len(names), 80):
        command = ["javap", "-public", "-classpath", cp, *names[offset:offset + 80]]
        completed = subprocess.run(command, text=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, check=False)
        current = None
        signature: list[str] = []
        for raw in completed.stdout.splitlines():
            line = raw.strip()
            match = DECLARATION.match(line)
            if match:
                if current is not None:
                    result[current] = [signature[0], *sorted(signature[1:])]
                current = match.group(1)
                signature = [line]
            elif current is not None and line and not line.startswith("Compiled from") and line != "}":
                signature.append(line)
        if current is not None:
            result[current] = [signature[0], *sorted(signature[1:])]
    return result


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--artifacts-root", type=Path, required=True)
    parser.add_argument("--recovery-root", type=Path, required=True)
    parser.add_argument("--output", type=Path, required=True)
    args = parser.parse_args()
    modules = {
        "base": ([args.artifacts_root / "onyxsdk-base-1.8.5/classes.jar"],
                 args.recovery_root / "onyxsdk-base/build/outputs/aar/onyxsdk-base-release.aar"),
        "device": ([args.artifacts_root / "onyxsdk-device-1.3.5/classes.jar"],
                   args.recovery_root / "onyxsdk-device/build/outputs/aar/onyxsdk-device-release.aar"),
        "pen": ([args.artifacts_root / "onyxsdk-pen-1.5.4/classes.jar",
                 args.recovery_root / "onyxsdk-pen-native-classes.jar"],
                args.recovery_root / "onyxsdk-pen/build/outputs/aar/onyxsdk-pen-release.aar"),
    }
    payload = {}
    with tempfile.TemporaryDirectory() as temporary:
        temp = Path(temporary)
        for name, (references, _) in modules.items():
            missing_references = [path for path in references if not path.is_file()]
            if missing_references:
                raise SystemExit(f"Missing {name} API reference(s): {missing_references}")
        recovered_jars = {name: extract_aar_classes(pair[1], temp) for name, pair in modules.items()}
        all_reference = [path for references, _ in modules.values() for path in references]
        all_recovered = list(recovered_jars.values())
        for name, (reference_jars, _) in modules.items():
            recovered_jar = recovered_jars[name]
            reference_names = sorted({class_name for jar in reference_jars for class_name in classes(jar)})
            recovered_names = classes(recovered_jar)
            reference_api = {}
            for reference_jar in reference_jars:
                own_names = classes(reference_jar)
                reference_api.update(javap(reference_jar, own_names, all_reference))
            recovered_api = javap(recovered_jar, recovered_names, all_recovered)
            common = sorted(set(reference_api) & set(recovered_api))
            changed = [class_name for class_name in common if reference_api[class_name] != recovered_api[class_name]]
            diffs = {}
            for class_name in changed:
                reference_signature = reference_api[class_name]
                recovered_signature = recovered_api[class_name]
                diffs[class_name] = {
                    "referenceDeclaration": reference_signature[0],
                    "recoveredDeclaration": recovered_signature[0],
                    "missingMembers": sorted(set(reference_signature[1:]) - set(recovered_signature[1:])),
                    "extraMembers": sorted(set(recovered_signature[1:]) - set(reference_signature[1:])),
                }
            payload[name] = {
                "referenceOuterClasses": len(reference_names),
                "recoveredOuterClasses": len(recovered_names),
                "missingClasses": sorted(set(reference_names) - set(recovered_names)),
                "extraClasses": sorted(set(recovered_names) - set(reference_names)),
                "changedSignatures": changed,
                "signatureDiffs": diffs,
            }
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(json.dumps(payload, indent=2, sort_keys=True) + "\n", encoding="utf-8")


if __name__ == "__main__":
    main()
