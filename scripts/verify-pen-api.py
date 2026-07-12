#!/usr/bin/env python3
"""Compare the rebuilt pen AAR's public/protected JVM API with reference JARs."""

from __future__ import annotations

import argparse
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


def javap_api(jar: Path, class_name: str) -> tuple[str, tuple[tuple[str, str], ...]]:
    result = subprocess.run(
        ["javap", "-classpath", str(jar), "-protected", "-s", class_name],
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


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--old-reference", required=True, type=Path)
    parser.add_argument("--native-reference", required=True, type=Path)
    parser.add_argument("--candidate", required=True, type=Path)
    args = parser.parse_args()

    for path in (args.old_reference, args.native_reference, args.candidate):
        if not path.is_file():
            parser.error(f"missing JAR: {path}")

    checked = 0
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
                actual = javap_api(args.candidate, class_name)
            except RuntimeError as error:
                failures.append(str(error))
                continue
            if expected == actual:
                continue
            expected_members = set(expected[1])
            actual_members = set(actual[1])
            details = [f"API mismatch: {class_name}"]
            if expected[0] != actual[0]:
                details.extend(
                    [f"  expected declaration: {expected[0]}", f"  actual declaration:   {actual[0]}"]
                )
            details.extend(f"  missing: {item}" for item in sorted(expected_members - actual_members))
            details.extend(f"  extra:   {item}" for item in sorted(actual_members - expected_members))
            failures.append("\n".join(details))

    if failures:
        print("\n\n".join(failures), file=sys.stderr)
        return 1
    # The comparison is intentionally one-directional (the rebuilt module is a
    # superset of each single reference JAR); surface the extras so additions
    # are at least visible.
    candidate_only = sorted(set(class_names(args.candidate)) - reference_names)
    if candidate_only:
        print(
            f"note: {len(candidate_only)} candidate classes exist in no reference JAR "
            f"and were not compared: {', '.join(candidate_only)}"
        )
    print(f"Pen JVM API matches {checked} public reference classes.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
