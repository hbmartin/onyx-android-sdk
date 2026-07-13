#!/usr/bin/env python3
"""Load and validate the repository's single Onyx module registry."""

from __future__ import annotations

import argparse
import json
from dataclasses import dataclass
from pathlib import Path, PurePosixPath
from typing import Any


DEFAULT_ROOT = Path(__file__).resolve().parent.parent
REGISTRY_PATH = Path("gradle/onyx-modules.json")
PRODUCTION_CONFIGURATIONS = frozenset({"api", "implementation", "compileOnly", "runtimeOnly"})


class RegistryError(ValueError):
    """Raised when the checked-in registry is structurally inconsistent."""


@dataclass(frozen=True)
class Module:
    id: str
    project_path: str
    project_dir: str
    artifact_id: str
    version: str
    name: str
    description: str
    published: bool
    license: str
    device_validation: dict[str, Any]
    project_dependencies: tuple[tuple[str, str], ...]
    owned_packages: tuple[str, ...]
    legacy_owned_types: tuple[str, ...]

    @property
    def release_task(self) -> str:
        return f"{self.project_path}:assembleRelease"

    @property
    def aar_relative_path(self) -> Path:
        return Path(self.project_dir) / "build" / "outputs" / "aar" / f"{self.artifact_id}-release.aar"


@dataclass(frozen=True)
class Registry:
    root: Path
    distribution: dict[str, Any]
    modules: tuple[Module, ...]

    @property
    def published_modules(self) -> tuple[Module, ...]:
        return tuple(module for module in self.modules if module.published)

    @property
    def by_id(self) -> dict[str, Module]:
        return {module.id: module for module in self.modules}

    def module(self, module_id: str) -> Module:
        try:
            return self.by_id[module_id]
        except KeyError as error:
            raise RegistryError(f"Unknown module id: {module_id}") from error


def _require_string(mapping: dict[str, Any], key: str, context: str) -> str:
    value = mapping.get(key)
    if not isinstance(value, str) or not value.strip():
        raise RegistryError(f"{context}.{key} must be a non-empty string")
    return value


def _string_list(mapping: dict[str, Any], key: str, context: str) -> tuple[str, ...]:
    value = mapping.get(key, [])
    if not isinstance(value, list) or any(not isinstance(item, str) or not item for item in value):
        raise RegistryError(f"{context}.{key} must be a list of non-empty strings")
    if len(value) != len(set(value)):
        raise RegistryError(f"{context}.{key} contains duplicate entries")
    return tuple(value)


def _validate_reference(value: str, context: str, *, jar: bool) -> None:
    path = PurePosixPath(value)
    if (
        path.is_absolute()
        or "\\" in value
        or any(part in ("", ".", "..") for part in value.split("/"))
        or path.as_posix() != value
    ):
        raise RegistryError(f"{context} must be a normalized relative path")
    if jar and path.suffix != ".jar":
        raise RegistryError(f"{context} must reference a .jar file")


def _parse_module(raw: Any, index: int, default_license: str) -> Module:
    context = f"modules[{index}]"
    if not isinstance(raw, dict):
        raise RegistryError(f"{context} must be an object")
    validation = raw.get("deviceValidation", {})
    if not isinstance(validation, dict):
        raise RegistryError(f"{context}.deviceValidation must be an object")
    for key in ("referenceCompileJars", "apiReferenceJars"):
        for item in _string_list(validation, key, f"{context}.deviceValidation"):
            _validate_reference(item, f"{context}.deviceValidation.{key}", jar=True)
    jni_dir = validation.get("referenceJniDir")
    if jni_dir is not None and (not isinstance(jni_dir, str) or not jni_dir):
        raise RegistryError(f"{context}.deviceValidation.referenceJniDir must be a string")
    if jni_dir is not None:
        _validate_reference(
            jni_dir,
            f"{context}.deviceValidation.referenceJniDir",
            jar=False,
        )
    common = validation.get("commonRecovered")
    if common is not None and not isinstance(common, bool):
        raise RegistryError(f"{context}.deviceValidation.commonRecovered must be a boolean")

    dependencies = raw.get("projectDependencies", [])
    if not isinstance(dependencies, list):
        raise RegistryError(f"{context}.projectDependencies must be a list")
    parsed_dependencies = []
    for dep_index, dependency in enumerate(dependencies):
        dep_context = f"{context}.projectDependencies[{dep_index}]"
        if not isinstance(dependency, dict):
            raise RegistryError(f"{dep_context} must be an object")
        configuration = _require_string(dependency, "configuration", dep_context)
        if configuration not in PRODUCTION_CONFIGURATIONS:
            raise RegistryError(f"{dep_context}.configuration is not a production configuration")
        parsed_dependencies.append((configuration, _require_string(dependency, "target", dep_context)))
    if len(parsed_dependencies) != len(set(parsed_dependencies)):
        raise RegistryError(f"{context}.projectDependencies contains duplicates")

    published = raw.get("published")
    if not isinstance(published, bool):
        raise RegistryError(f"{context}.published must be a boolean")
    license_id = raw.get("license", default_license)
    if not isinstance(license_id, str) or not license_id:
        raise RegistryError(f"{context}.license must be a non-empty string")
    return Module(
        id=_require_string(raw, "id", context),
        project_path=_require_string(raw, "projectPath", context),
        project_dir=_require_string(raw, "projectDir", context),
        artifact_id=_require_string(raw, "artifactId", context),
        version=_require_string(raw, "version", context),
        name=_require_string(raw, "name", context),
        description=_require_string(raw, "description", context),
        published=published,
        license=license_id,
        device_validation=validation,
        project_dependencies=tuple(parsed_dependencies),
        owned_packages=_string_list(raw, "ownedPackages", context),
        legacy_owned_types=_string_list(raw, "legacyOwnedTypes", context),
    )


def _has_device_validation_roles(validation: dict[str, Any]) -> bool:
    return bool(
        validation.get("commonRecovered", False)
        or validation.get("referenceCompileJars", [])
        or validation.get("apiReferenceJars", [])
        or validation.get("referenceJniDir")
    )


def load_registry(root: Path = DEFAULT_ROOT, *, validate_paths: bool = True) -> Registry:
    root = root.resolve()
    path = root / REGISTRY_PATH
    try:
        payload = json.loads(path.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError) as error:
        raise RegistryError(f"Could not load module registry {path}: {error}") from error
    if not isinstance(payload, dict) or payload.get("schemaVersion") != 1:
        raise RegistryError("Module registry schemaVersion must be 1")
    distribution = payload.get("distribution")
    if not isinstance(distribution, dict):
        raise RegistryError("distribution must be an object")
    for key in ("group", "projectUrl", "scmConnection", "scmDeveloperConnection"):
        _require_string(distribution, key, "distribution")
    developer = distribution.get("developer")
    if not isinstance(developer, dict):
        raise RegistryError("distribution.developer must be an object")
    for key in ("id", "name", "url"):
        _require_string(developer, key, "distribution.developer")
    licenses = distribution.get("licenses")
    if not isinstance(licenses, dict) or not licenses:
        raise RegistryError("distribution.licenses must be a non-empty object")
    for license_id, license_data in licenses.items():
        if not isinstance(license_id, str) or not isinstance(license_data, dict):
            raise RegistryError("distribution.licenses entries must be objects")
        for key in ("name", "url", "distribution"):
            _require_string(license_data, key, f"distribution.licenses.{license_id}")
    default_license = _require_string(distribution, "defaultLicense", "distribution")
    if default_license not in licenses:
        raise RegistryError(f"distribution.defaultLicense is unknown: {default_license}")

    raw_modules = payload.get("modules")
    if not isinstance(raw_modules, list) or not raw_modules:
        raise RegistryError("modules must be a non-empty list")
    modules = tuple(
        _parse_module(raw, index, default_license)
        for index, raw in enumerate(raw_modules)
    )

    def require_unique(label: str, values: list[str]) -> None:
        duplicates = sorted(value for value in set(values) if values.count(value) > 1)
        if duplicates:
            raise RegistryError(f"Duplicate module {label}: {', '.join(duplicates)}")

    require_unique("id", [module.id for module in modules])
    require_unique("projectPath", [module.project_path for module in modules])
    require_unique("projectDir", [module.project_dir for module in modules])
    require_unique("artifactId", [module.artifact_id for module in modules if module.published])
    require_unique(
        "coordinates",
        [f"{distribution['group']}:{module.artifact_id}:{module.version}"
         for module in modules if module.published],
    )

    by_id = {module.id: module for module in modules}
    for module in modules:
        if module.license not in licenses:
            raise RegistryError(f"{module.id} refers to unknown license {module.license}")
        if validate_paths:
            raw_project_dir = Path(module.project_dir)
            project_dir = (root / raw_project_dir).resolve()
            if (
                raw_project_dir.is_absolute()
                or ".." in raw_project_dir.parts
                or (project_dir != root and root not in project_dir.parents)
            ):
                raise RegistryError(
                    f"{module.id} project directory must remain within the repository: "
                    f"{module.project_dir}"
                )
            if not project_dir.is_dir():
                raise RegistryError(
                    f"{module.id} project directory does not exist: {module.project_dir}"
                )
        for _, target in module.project_dependencies:
            if target not in by_id:
                raise RegistryError(f"{module.id} depends on unknown module {target}")
            if not by_id[target].published:
                raise RegistryError(f"{module.id} has a production dependency on non-published {target}")

    package_owners: dict[str, str] = {}
    validation_owners: dict[str, str] = {}
    legacy_owners: dict[str, str] = {}
    for module in modules:
        if not module.published:
            if _has_device_validation_roles(module.device_validation):
                raise RegistryError(
                    f"Non-published module {module.id} cannot define device validation roles")
            continue
        validation_paths = set(module.device_validation.get("referenceCompileJars", []))
        validation_paths.update(module.device_validation.get("apiReferenceJars", []))
        jni_dir = module.device_validation.get("referenceJniDir")
        if jni_dir:
            validation_paths.add(jni_dir)
        for validation_path in validation_paths:
            previous = validation_owners.setdefault(validation_path, module.id)
            if previous != module.id:
                raise RegistryError(
                    f"Validation input {validation_path} belongs to both {previous} and {module.id}")
        for package in module.owned_packages:
            previous = package_owners.setdefault(package, module.id)
            if previous != module.id:
                raise RegistryError(f"Package {package} is owned by both {previous} and {module.id}")
        for type_name in module.legacy_owned_types:
            package = type_name.rsplit(".", 1)[0]
            if package not in package_owners and not any(
                    candidate.published and package in candidate.owned_packages
                    for candidate in modules):
                raise RegistryError(
                    f"Legacy type {type_name} has no default package owner")
            previous = legacy_owners.setdefault(type_name, module.id)
            if previous != module.id:
                raise RegistryError(
                    f"Legacy type {type_name} is owned by both {previous} and {module.id}")

    return Registry(root=root, distribution=distribution, modules=modules)


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--root", type=Path, default=DEFAULT_ROOT)
    subparsers = parser.add_subparsers(dest="command", required=True)
    for name in ("aar", "release-task", "module"):
        command = subparsers.add_parser(name)
        command.add_argument("module_id")
    subparsers.add_parser("published-aars")
    subparsers.add_parser("validate")
    args = parser.parse_args()
    registry = load_registry(args.root)
    if args.command == "validate":
        print(f"Validated {len(registry.modules)} modules ({len(registry.published_modules)} published)")
        return 0
    if args.command == "published-aars":
        for published_module in registry.published_modules:
            print(registry.root / published_module.aar_relative_path)
        return 0
    module = registry.module(args.module_id)
    if args.command == "aar":
        print(registry.root / module.aar_relative_path)
    elif args.command == "release-task":
        print(module.release_task)
    else:
        print(json.dumps(module.__dict__, indent=2, sort_keys=True))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
