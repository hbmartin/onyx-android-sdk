#!/usr/bin/env python3
"""Enforce production package and AAR ownership from onyx-modules.json."""

from __future__ import annotations

import argparse
import io
import re
import sys
import zipfile
from collections import defaultdict
from pathlib import Path

from module_registry import DEFAULT_ROOT, Module, Registry, load_registry


PACKAGE = re.compile(r"^\s*package\s+([A-Za-z_][\w.]*)", re.MULTILINE)
SOURCE_SUFFIXES = frozenset({".java", ".kt", ".aidl"})


class BoundaryError(ValueError):
    """Raised when production ownership differs from the registry."""


def source_files(root: Path, module: Module) -> list[Path]:
    source_root = root / module.project_dir / "src" / "main"
    return sorted(
        path for path in source_root.rglob("*")
        if path.is_file() and path.suffix in SOURCE_SUFFIXES
    )


def declared_package(path: Path) -> str:
    match = PACKAGE.search(path.read_text(encoding="utf-8", errors="strict"))
    if not match:
        raise BoundaryError(f"Production source has no package declaration: {path}")
    return match.group(1)


def verify_sources(registry: Registry) -> dict[str, int]:
    package_owners = {
        package: module.id
        for module in registry.published_modules
        for package in module.owned_packages
    }
    legacy_owners = {
        type_name: module.id
        for module in registry.published_modules
        for type_name in module.legacy_owned_types
    }
    seen_legacy: dict[str, list[Path]] = defaultdict(list)
    counts: dict[str, int] = {}
    failures: list[str] = []

    for module in registry.published_modules:
        if module.artifact_type != "aar":
            continue
        files = source_files(registry.root, module)
        counts[module.id] = len(files)
        for path in files:
            package = declared_package(path)
            default_owner = package_owners.get(package)
            source_type = f"{package}.{path.stem}"
            legacy_owner = legacy_owners.get(source_type)
            if legacy_owner is not None:
                seen_legacy[source_type].append(path)
                if legacy_owner != module.id:
                    failures.append(
                        f"{path}: legacy type {source_type} belongs to {legacy_owner}, not {module.id}")
                continue
            if default_owner is None:
                failures.append(f"{path}: package {package} has no registry owner")
            elif default_owner != module.id:
                failures.append(
                    f"{path}: package {package} belongs to {default_owner}, not {module.id}")

    for type_name, expected_owner in sorted(legacy_owners.items()):
        paths = seen_legacy.get(type_name, [])
        if len(paths) != 1:
            failures.append(
                f"Legacy ownership exception {type_name} for {expected_owner} matched {len(paths)} files")
    if failures:
        raise BoundaryError("Source ownership violations:\n" + "\n".join(failures))
    return counts


def class_entries(aar: Path) -> set[str]:
    if not aar.is_file():
        raise BoundaryError(f"Missing production AAR: {aar}")
    try:
        with zipfile.ZipFile(aar) as archive:
            classes = archive.read("classes.jar")
        with zipfile.ZipFile(io.BytesIO(classes)) as jar:
            return {
                entry[:-6].replace("/", ".")
                for entry in jar.namelist()
                if entry.endswith(".class") and not entry.endswith("module-info.class")
            }
    except (OSError, KeyError, zipfile.BadZipFile) as error:
        raise BoundaryError(f"Could not inspect {aar}: {error}") from error


def package_of_class(class_name: str) -> str:
    return class_name.rsplit(".", 1)[0] if "." in class_name else ""


def top_level_class(class_name: str) -> str:
    return class_name.split("$", 1)[0]


def verify_aars(registry: Registry) -> dict[str, int]:
    package_owners = {
        package: module.id
        for module in registry.published_modules
        for package in module.owned_packages
    }
    legacy_owners = {
        type_name: module.id
        for module in registry.published_modules
        for type_name in module.legacy_owned_types
    }
    owners: dict[str, list[str]] = defaultdict(list)
    entries_by_module: dict[str, set[str]] = {}
    for module in registry.published_modules:
        if module.artifact_type != "aar":
            continue
        entries = class_entries(registry.root / module.aar_relative_path)
        entries_by_module[module.id] = entries
        for entry in entries:
            owners[entry].append(module.id)

    duplicates = {
        entry: modules for entry, modules in owners.items() if len(modules) > 1
    }
    failures = [
        f"Duplicate class {entry}: {', '.join(modules)}"
        for entry, modules in sorted(duplicates.items())
    ]

    for module_id, entries in sorted(entries_by_module.items()):
        for entry in sorted(entries):
            owner_key = top_level_class(entry)
            expected_owner = legacy_owners.get(owner_key)
            if expected_owner is None:
                expected_owner = package_owners.get(package_of_class(owner_key))
            if expected_owner is None:
                failures.append(f"Class {entry} has no registry owner")
            elif expected_owner != module_id:
                failures.append(
                    f"Class {entry} belongs to {expected_owner}, not {module_id}"
                )

    base_entries = entries_by_module.get("base", set())
    support_packages = {
        package
        for module_id in ("baselite", "commons-io")
        for package in registry.module(module_id).owned_packages
    }
    leaked = sorted(
        entry for entry in base_entries
        if package_of_class(entry) in support_packages
    )
    if leaked:
        failures.append(
            "Base classes.jar contains support-owned classes: " + ", ".join(leaked[:20])
        )
    if failures:
        raise BoundaryError("AAR ownership violations:\n" + "\n".join(failures))
    return {module_id: len(entries) for module_id, entries in entries_by_module.items()}


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--root", type=Path, default=DEFAULT_ROOT)
    parser.add_argument("--sources-only", action="store_true")
    args = parser.parse_args()
    try:
        registry = load_registry(args.root)
        source_counts = verify_sources(registry)
        print(
            "Verified source ownership: "
            + ", ".join(f"{module}={count}" for module, count in source_counts.items())
        )
        if not args.sources_only:
            class_counts = verify_aars(registry)
            print(
                "Verified AAR ownership: "
                + ", ".join(f"{module}={count}" for module, count in class_counts.items())
            )
    except (BoundaryError, ValueError) as error:
        print(f"module boundary verification failed: {error}", file=sys.stderr)
        return 1
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
