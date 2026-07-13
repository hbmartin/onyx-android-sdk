import hashlib
import io
import sys
import tempfile
import unittest
import zipfile
from dataclasses import replace
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT / "scripts"))

from module_registry import load_registry
from publish_central import (
    CentralPublishError,
    PortalClient,
    artifact_name,
    classify_coordinates,
    coordinate_directory,
    create_filtered_bundle,
    primary_artifact_names,
    remote_artifact_sha256,
    sha256,
    verify_bundle_repository,
    wait_for_publication,
)


class Response(io.BytesIO):
    def __enter__(self):
        return self

    def __exit__(self, *_):
        self.close()


class PublishCentralTest(unittest.TestCase):
    def setUp(self):
        registry = load_registry(ROOT)
        self.registry = replace(registry, modules=(registry.module("device"),))

    def local_repository(self, root):
        module = self.registry.published_modules[0]
        directory = coordinate_directory(self.registry, module, Path(root))
        directory.mkdir(parents=True)
        artifacts = {}
        for name in primary_artifact_names(module):
            artifact = directory / name
            artifact.write_bytes(f"local-{name}".encode())
            artifacts[name] = artifact
        return module, artifacts

    def test_classifies_new_identical_and_conflicting_coordinates(self):
        with tempfile.TemporaryDirectory() as temporary:
            module, artifacts = self.local_repository(temporary)
            new, identical = classify_coordinates(
                self.registry, Path(temporary), lambda *_: None)
            self.assertEqual([module], new)
            self.assertEqual([], identical)

            remote_digests = {
                name: sha256(artifact) for name, artifact in artifacts.items()
            }
            new, identical = classify_coordinates(
                self.registry,
                Path(temporary),
                lambda _registry, _module, name: remote_digests[name],
            )
            self.assertEqual([], new)
            self.assertEqual([module], identical)

            with self.assertRaisesRegex(CentralPublishError, "bump the affected module versions"):
                classify_coordinates(
                    self.registry,
                    Path(temporary),
                    lambda _registry, _module, name: (
                        "0" * 64 if name.endswith(".pom") else remote_digests[name]
                    ),
                )

            with self.assertRaisesRegex(CentralPublishError, "missing remote artifact"):
                classify_coordinates(
                    self.registry,
                    Path(temporary),
                    lambda _registry, _module, name: (
                        remote_digests[name] if name == artifact_name(module) else None
                    ),
                )

    def test_empty_remote_checksum_is_reported_cleanly(self):
        module = self.registry.published_modules[0]

        def opener(_url, timeout):
            self.assertEqual(30, timeout)
            return Response(b"  \n")

        with self.assertRaisesRegex(CentralPublishError, "Empty Central SHA-256"):
            remote_artifact_sha256(self.registry, module, opener=opener)

    def test_portal_upload_uses_automatic_publication(self):
        captured = []

        def opener(request, timeout):
            captured.append((request, timeout))
            return Response(b"deployment-id")

        with tempfile.TemporaryDirectory() as temporary:
            bundle = Path(temporary) / "bundle.zip"
            bundle.write_bytes(b"zip")
            deployment_id = PortalClient("user", "token", opener=opener).upload(bundle, "release")

        self.assertEqual("deployment-id", deployment_id)
        request, timeout = captured[0]
        self.assertIn("publishingType=AUTOMATIC", request.full_url)
        self.assertTrue(request.headers["Authorization"].startswith("Bearer "))
        self.assertEqual(300, timeout)

    def test_signed_bundle_structure_rejects_repository_metadata(self):
        with tempfile.TemporaryDirectory() as temporary:
            repository = Path(temporary)
            module = self.registry.published_modules[0]
            directory = coordinate_directory(self.registry, module, repository)
            directory.mkdir(parents=True)
            stem = f"{module.artifact_id}-{module.version}"
            for name in (
                f"{stem}.aar",
                f"{stem}-sources.jar",
                f"{stem}-javadoc.jar",
                f"{stem}.pom",
                f"{stem}.module",
            ):
                primary = directory / name
                primary.write_bytes(b"artifact")
                Path(str(primary) + ".asc").write_text(
                    "-----BEGIN PGP SIGNATURE-----\ntest\n",
                    encoding="ascii",
                )
                for suffix, algorithm in ((".md5", "md5"), (".sha1", "sha1")):
                    Path(str(primary) + suffix).write_text(
                        hashlib.new(algorithm, primary.read_bytes()).hexdigest(),
                        encoding="ascii",
                    )

            verify_bundle_repository(self.registry, repository)
            stale_classifier = directory / f"{stem}-all.jar"
            stale_classifier.write_bytes(b"stale")
            with self.assertRaisesRegex(CentralPublishError, "undeclared for their coordinate"):
                verify_bundle_repository(self.registry, repository)
            filtered_bundle = Path(temporary) / "filtered.zip"
            create_filtered_bundle(self.registry, repository, [module], filtered_bundle)
            with zipfile.ZipFile(filtered_bundle) as archive:
                self.assertNotIn(
                    stale_classifier.relative_to(repository).as_posix(),
                    archive.namelist(),
                )
            stale_classifier.unlink()
            metadata = directory.parent / "maven-metadata.xml"
            metadata.write_text("metadata")
            with self.assertRaisesRegex(CentralPublishError, "outside registry coordinate"):
                verify_bundle_repository(self.registry, repository)

    def test_wait_reports_success_and_failure(self):
        class Client:
            def __init__(self, payload):
                self.payload = payload

            def status(self, _):
                return self.payload

        result = wait_for_publication(
            Client({"deploymentState": "PUBLISHED"}),
            "id",
            poll_interval=0,
        )
        self.assertEqual("PUBLISHED", result["deploymentState"])
        with self.assertRaisesRegex(CentralPublishError, "validation failed"):
            wait_for_publication(
                Client({"deploymentState": "FAILED", "errors": ["bad metadata"]}),
                "id",
                poll_interval=0,
            )


if __name__ == "__main__":
    unittest.main()
