#!/usr/bin/env python3
"""Idempotently upload a signed registry-driven bundle to Central Portal."""

from __future__ import annotations

import argparse
import base64
import hashlib
import json
import os
import sys
import tempfile
import time
import urllib.error
import urllib.parse
import urllib.request
import uuid
import zipfile
from pathlib import Path
from typing import Callable

from module_registry import DEFAULT_ROOT, Module, Registry, load_registry


CENTRAL_REPOSITORY = "https://repo1.maven.org/maven2"
PORTAL_API = "https://central.sonatype.com/api/v1/publisher"
CHECKSUM_SUFFIXES = (".md5", ".sha1", ".sha256", ".sha512")


class CentralPublishError(RuntimeError):
    """Raised when immutable coordinates or Portal publication fail."""


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def coordinate_directory(registry: Registry, module: Module, repository: Path) -> Path:
    return (
        repository
        / registry.distribution["group"].replace(".", "/")
        / module.artifact_id
        / module.version
    )


def artifact_name(module: Module) -> str:
    return f"{module.artifact_id}-{module.version}.aar"


def primary_artifact_names(module: Module) -> tuple[str, ...]:
    stem = f"{module.artifact_id}-{module.version}"
    return (
        f"{stem}.aar",
        f"{stem}-sources.jar",
        f"{stem}-javadoc.jar",
        f"{stem}.pom",
        f"{stem}.module",
    )


def permitted_coordinate_file_names(module: Module) -> set[str]:
    names = set(primary_artifact_names(module))
    for primary in primary_artifact_names(module):
        names.add(f"{primary}.asc")
        names.update(f"{primary}{suffix}" for suffix in CHECKSUM_SUFFIXES)
    return names


def remote_artifact_sha256(
    registry: Registry,
    module: Module,
    artifact: str | None = None,
    *,
    repository_url: str = CENTRAL_REPOSITORY,
    opener: Callable = urllib.request.urlopen,
) -> str | None:
    remote_name = artifact or artifact_name(module)
    relative = "/".join((
        registry.distribution["group"].replace(".", "/"),
        urllib.parse.quote(module.artifact_id),
        urllib.parse.quote(module.version),
        urllib.parse.quote(remote_name),
    ))
    artifact_url = f"{repository_url.rstrip('/')}/{relative}"
    try:
        with opener(f"{artifact_url}.sha256", timeout=30) as response:
            parts = response.read().decode("ascii").strip().split()
        if not parts:
            raise CentralPublishError(
                f"Empty Central SHA-256 response for {module.id} artifact {remote_name}"
            )
        checksum = parts[0].lower()
        if len(checksum) != 64 or any(character not in "0123456789abcdef" for character in checksum):
            raise CentralPublishError(f"Invalid Central SHA-256 response for {module.id}: {checksum!r}")
        return checksum
    except urllib.error.HTTPError as error:
        if error.code != 404:
            raise CentralPublishError(f"Could not query Central for {module.id}: HTTP {error.code}") from error

    digest = hashlib.sha256()
    try:
        with opener(artifact_url, timeout=60) as response:
            for chunk in iter(lambda: response.read(1024 * 1024), b""):
                digest.update(chunk)
    except urllib.error.HTTPError as error:
        if error.code == 404:
            return None
        raise CentralPublishError(f"Could not download Central artifact for {module.id}: HTTP {error.code}") from error
    return digest.hexdigest()


def classify_coordinates(
    registry: Registry,
    repository: Path,
    checksum_lookup: Callable[[Registry, Module, str], str | None] = remote_artifact_sha256,
) -> tuple[list[Module], list[Module]]:
    new_modules: list[Module] = []
    identical_modules: list[Module] = []
    conflicts: list[str] = []
    for module in registry.published_modules:
        directory = coordinate_directory(registry, module, repository)
        local_digests: dict[str, str] = {}
        remote_digests: dict[str, str | None] = {}
        for name in primary_artifact_names(module):
            local = directory / name
            if not local.is_file():
                raise CentralPublishError(
                    f"Signed bundle is missing {local.relative_to(repository)}"
                )
            local_digests[name] = sha256(local)
            remote_digests[name] = checksum_lookup(registry, module, name)

        if all(digest is None for digest in remote_digests.values()):
            new_modules.append(module)
        elif all(
            remote_digests[name] == local_digests[name]
            for name in primary_artifact_names(module)
        ):
            identical_modules.append(module)
        else:
            coordinate = (
                f"{registry.distribution['group']}:{module.artifact_id}:{module.version}"
            )
            for name in primary_artifact_names(module):
                remote_digest = remote_digests[name]
                local_digest = local_digests[name]
                if remote_digest is None:
                    conflicts.append(f"{coordinate} is missing remote artifact {name}")
                elif remote_digest != local_digest:
                    conflicts.append(
                        f"{coordinate} artifact {name} already exists with SHA-256 "
                        f"{remote_digest}, local is {local_digest}"
                    )
    if conflicts:
        raise CentralPublishError(
            "Published Maven coordinates are immutable; bump the affected module versions:\n"
            + "\n".join(conflicts),
        )
    return new_modules, identical_modules


def verify_coordinate_files(registry: Registry, module: Module, repository: Path) -> None:
    directory = coordinate_directory(registry, module, repository)
    primary_files = [directory / name for name in primary_artifact_names(module)]
    failures = []
    for primary in primary_files:
        if not primary.is_file() or not primary.stat().st_size:
            failures.append(str(primary.relative_to(repository)))
            continue
        signature = Path(str(primary) + ".asc")
        if not signature.is_file() or not signature.stat().st_size:
            failures.append(str(signature.relative_to(repository)))
        elif not signature.read_text(encoding="ascii").startswith("-----BEGIN PGP SIGNATURE-----"):
            failures.append(f"{signature.relative_to(repository)} (invalid armored signature)")
        for suffix, algorithm in (
            (".md5", "md5"),
            (".sha1", "sha1"),
            (".sha256", "sha256"),
            (".sha512", "sha512"),
        ):
            sidecar = Path(str(primary) + suffix)
            if suffix in (".sha256", ".sha512") and not sidecar.exists():
                continue
            if not sidecar.is_file() or not sidecar.stat().st_size:
                failures.append(str(sidecar.relative_to(repository)))
                continue
            expected = hashlib.new(algorithm, primary.read_bytes()).hexdigest()
            actual = sidecar.read_text(encoding="ascii").strip().split()[0].lower()
            if actual != expected:
                failures.append(f"{sidecar.relative_to(repository)} (checksum mismatch)")
    if failures:
        raise CentralPublishError(
            f"{module.id} is missing Central-required files:\n" + "\n".join(failures),
        )


def verify_bundle_repository(registry: Registry, repository: Path) -> None:
    coordinate_directories = {
        coordinate_directory(registry, module, repository).resolve(): module
        for module in registry.published_modules
    }
    unexpected = []
    for path in repository.rglob("*"):
        if not path.is_file():
            continue
        resolved = path.resolve()
        module = coordinate_directories.get(resolved.parent)
        if module is None:
            unexpected.append(str(path.relative_to(repository)))
        elif path.name not in permitted_coordinate_file_names(module):
            unexpected.append(str(path.relative_to(repository)))
    if unexpected:
        raise CentralPublishError(
            "Central bundle contains files outside registry coordinate versions, "
            "undeclared for their coordinate, or unnecessary signature checksums:\n"
            + "\n".join(sorted(unexpected)),
        )
    for module in registry.published_modules:
        verify_coordinate_files(registry, module, repository)


def extract_bundle(bundle: Path, destination: Path) -> None:
    with zipfile.ZipFile(bundle) as archive:
        for info in archive.infolist():
            target = (destination / info.filename).resolve()
            if destination.resolve() not in target.parents and target != destination.resolve():
                raise CentralPublishError(f"Unsafe path in Central bundle: {info.filename}")
        archive.extractall(destination)


def create_filtered_bundle(
    registry: Registry,
    repository: Path,
    modules: list[Module],
    output: Path,
) -> None:
    output.parent.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(output, "w", compression=zipfile.ZIP_DEFLATED, compresslevel=9) as archive:
        for module in modules:
            verify_coordinate_files(registry, module, repository)
            directory = coordinate_directory(registry, module, repository)
            allowed = permitted_coordinate_file_names(module)
            for path in sorted(directory / name for name in allowed if (directory / name).is_file()):
                info = zipfile.ZipInfo(path.relative_to(repository).as_posix())
                info.date_time = (1980, 1, 1, 0, 0, 0)
                info.compress_type = zipfile.ZIP_DEFLATED
                info.external_attr = 0o100644 << 16
                archive.writestr(info, path.read_bytes(), compresslevel=9)


class PortalClient:
    def __init__(
        self,
        username: str,
        password: str,
        *,
        api_url: str = PORTAL_API,
        opener: Callable = urllib.request.urlopen,
    ) -> None:
        token = base64.b64encode(f"{username}:{password}".encode("utf-8")).decode("ascii")
        self.authorization = f"Bearer {token}"
        self.api_url = api_url.rstrip("/")
        self.opener = opener

    def upload(self, bundle: Path, deployment_name: str) -> str:
        boundary = f"----onyx-{uuid.uuid4().hex}"
        body = b"".join((
            f"--{boundary}\r\n".encode(),
            f'Content-Disposition: form-data; name="bundle"; filename="{bundle.name}"\r\n'.encode(),
            b"Content-Type: application/octet-stream\r\n\r\n",
            bundle.read_bytes(),
            f"\r\n--{boundary}--\r\n".encode(),
        ))
        query = urllib.parse.urlencode({
            "name": deployment_name,
            "publishingType": "AUTOMATIC",
        })
        request = urllib.request.Request(
            f"{self.api_url}/upload?{query}",
            data=body,
            method="POST",
            headers={
                "Authorization": self.authorization,
                "Content-Type": f"multipart/form-data; boundary={boundary}",
            },
        )
        try:
            with self.opener(request, timeout=300) as response:
                deployment_id = response.read().decode("utf-8").strip()
        except urllib.error.HTTPError as error:
            detail = error.read().decode("utf-8", errors="replace")
            raise CentralPublishError(f"Central upload failed: HTTP {error.code}: {detail}") from error
        if not deployment_id:
            raise CentralPublishError("Central upload returned no deployment id")
        return deployment_id

    def status(self, deployment_id: str) -> dict:
        query = urllib.parse.urlencode({"id": deployment_id})
        request = urllib.request.Request(
            f"{self.api_url}/status?{query}",
            data=b"",
            method="POST",
            headers={"Authorization": self.authorization},
        )
        try:
            with self.opener(request, timeout=60) as response:
                return json.loads(response.read().decode("utf-8"))
        except (urllib.error.HTTPError, json.JSONDecodeError) as error:
            raise CentralPublishError(f"Could not read Central deployment status: {error}") from error


def wait_for_publication(
    client: PortalClient,
    deployment_id: str,
    *,
    poll_interval: float = 15,
    timeout: float = 1800,
    sleep: Callable[[float], None] = time.sleep,
) -> dict:
    deadline = time.monotonic() + timeout
    last_state = None
    while time.monotonic() < deadline:
        payload = client.status(deployment_id)
        state = payload.get("deploymentState")
        if state != last_state:
            print(f"Central deployment {deployment_id}: {state}", flush=True)
            last_state = state
        if state == "PUBLISHED":
            return payload
        if state == "FAILED":
            raise CentralPublishError(
                "Central validation failed: " + json.dumps(payload.get("errors", payload), sort_keys=True),
            )
        sleep(poll_interval)
    raise CentralPublishError(f"Timed out waiting for Central deployment {deployment_id}")


def required_environment(name: str) -> str:
    value = os.environ.get(name)
    if not value:
        raise CentralPublishError(f"Missing required environment variable {name}")
    return value


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--root", type=Path, default=DEFAULT_ROOT)
    parser.add_argument("--bundle", type=Path, required=True)
    parser.add_argument("--poll-interval", type=float, default=15)
    parser.add_argument("--timeout", type=float, default=1800)
    parser.add_argument("--verify-only", action="store_true")
    args = parser.parse_args()
    try:
        registry = load_registry(args.root)
        with tempfile.TemporaryDirectory() as temporary:
            work = Path(temporary)
            repository = work / "repository"
            repository.mkdir()
            extract_bundle(args.bundle.resolve(), repository)
            verify_bundle_repository(registry, repository)
            if args.verify_only:
                print(f"Verified signed Central bundle for {len(registry.published_modules)} coordinates")
                return 0
            new_modules, identical_modules = classify_coordinates(registry, repository)
            for module in identical_modules:
                print(f"Central already contains byte-identical {module.artifact_id}:{module.version}")
            if not new_modules:
                print("All registry coordinates are already published with identical bytes")
                return 0
            filtered_bundle = work / "onyx-central-new-coordinates.zip"
            create_filtered_bundle(registry, repository, new_modules, filtered_bundle)
            client = PortalClient(
                required_environment("MAVEN_CENTRAL_USERNAME"),
                required_environment("MAVEN_CENTRAL_PASSWORD"),
            )
            deployment_name = "onyx-android-sdk-" + "-".join(
                f"{module.artifact_id}-{module.version}" for module in new_modules
            )
            deployment_id = client.upload(filtered_bundle, deployment_name)
            wait_for_publication(
                client,
                deployment_id,
                poll_interval=args.poll_interval,
                timeout=args.timeout,
            )
            print(f"Published {len(new_modules)} new Maven coordinate(s) to Central")
    except (CentralPublishError, OSError, ValueError, urllib.error.URLError, zipfile.BadZipFile) as error:
        print(f"Central publication failed: {error}", file=sys.stderr)
        return 1
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
