#!/usr/bin/env python3
"""Validate the metadata and artifacts for every registry publication."""

from __future__ import annotations

import argparse
import hashlib
import json
import sys
import xml.etree.ElementTree as ET
from pathlib import Path

from module_registry import DEFAULT_ROOT, Module, Registry, load_registry


MAVEN = {"m": "http://maven.apache.org/POM/4.0.0"}


class PublicationError(ValueError):
    """Raised when generated publication output is incomplete or inconsistent."""


def text(root: ET.Element, path: str) -> str | None:
    element = root.find(path, MAVEN)
    return element.text if element is not None else None


def require_equal(label: str, actual: object, expected: object) -> None:
    if actual != expected:
        raise PublicationError(f"{label}: expected {expected!r}, found {actual!r}")


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def artifact_paths(registry: Registry, module: Module) -> dict[str, Path]:
    build = registry.root / module.project_dir / "build"
    return {
        "aar": registry.root / module.aar_relative_path,
        "sources": build / "intermediates/source_jar/release/release-sources.jar",
        "javadoc": build / "intermediates/java_doc_jar/release/release-javadoc.jar",
        "pom": build / "publications/release/pom-default.xml",
        "module": build / "publications/release/module.json",
    }


def verify_pom(registry: Registry, module: Module, pom_path: Path) -> None:
    root = ET.parse(pom_path).getroot()
    distribution = registry.distribution
    license_metadata = distribution["licenses"][module.license]
    expected_fields = {
        "groupId": distribution["group"],
        "artifactId": module.artifact_id,
        "version": module.version,
        "packaging": "aar",
        "name": module.name,
        "description": module.description,
        "url": distribution["projectUrl"],
        "licenses/license/name": license_metadata["name"],
        "licenses/license/url": license_metadata["url"],
        "licenses/license/distribution": license_metadata["distribution"],
        "developers/developer/id": distribution["developer"]["id"],
        "developers/developer/name": distribution["developer"]["name"],
        "developers/developer/url": distribution["developer"]["url"],
        "scm/connection": distribution["scmConnection"],
        "scm/developerConnection": distribution["scmDeveloperConnection"],
        "scm/url": distribution["projectUrl"],
    }
    for path, expected in expected_fields.items():
        require_equal(f"{module.id} POM {path}", text(root, "m:" + path.replace("/", "/m:")), expected)

    actual_projects = set()
    for dependency in root.findall("m:dependencies/m:dependency", MAVEN):
        group = text(dependency, "m:groupId")
        if group != distribution["group"]:
            continue
        actual_projects.add((
            text(dependency, "m:artifactId"),
            text(dependency, "m:version"),
            text(dependency, "m:scope"),
        ))
    expected_projects = {
        (
            registry.module(target).artifact_id,
            registry.module(target).version,
            "compile" if configuration == "api" else "runtime",
        )
        for configuration, target in module.project_dependencies
    }
    require_equal(f"{module.id} POM project dependencies", actual_projects, expected_projects)


def verify_module_metadata(registry: Registry, module: Module, path: Path, aar: Path) -> None:
    payload = json.loads(path.read_text(encoding="utf-8"))
    require_equal(
        f"{module.id} module group",
        payload["component"]["group"],
        registry.distribution["group"],
    )
    require_equal(f"{module.id} module name", payload["component"]["module"], module.artifact_id)
    require_equal(f"{module.id} module version", payload["component"]["version"], module.version)
    files = [item for variant in payload["variants"] for item in variant.get("files", [])]
    primary_name = f"{module.artifact_id}-{module.version}.aar"
    primary = next((item for item in files if item.get("name") == primary_name), None)
    if primary is None:
        raise PublicationError(f"{module.id} Gradle metadata has no primary AAR")
    require_equal(f"{module.id} unchanged AAR SHA-256", primary.get("sha256"), sha256(aar))
    names = {item.get("name") for item in files}
    for classifier in ("sources", "javadoc"):
        expected = f"{module.artifact_id}-{module.version}-{classifier}.jar"
        if expected not in names:
            raise PublicationError(f"{module.id} Gradle metadata has no {classifier} artifact")


def verify_module(registry: Registry, module: Module) -> None:
    paths = artifact_paths(registry, module)
    missing = [f"{kind}: {path}" for kind, path in paths.items() if not path.is_file() or not path.stat().st_size]
    if missing:
        raise PublicationError(f"{module.id} publication output missing: {', '.join(missing)}")
    verify_pom(registry, module, paths["pom"])
    verify_module_metadata(registry, module, paths["module"], paths["aar"])


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--root", type=Path, default=DEFAULT_ROOT)
    args = parser.parse_args()
    try:
        registry = load_registry(args.root)
        for module in registry.published_modules:
            verify_module(registry, module)
            print(f"Verified Maven publication {registry.distribution['group']}:{module.artifact_id}:{module.version}")
    except (OSError, KeyError, ValueError, ET.ParseError, json.JSONDecodeError) as error:
        print(f"publication verification failed: {error}", file=sys.stderr)
        return 1
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
