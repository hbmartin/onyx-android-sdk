"""Regression coverage for the descriptor-level JVM API classifier."""

import io
import json
import sys
import tempfile
import unittest
from collections import Counter
from pathlib import Path
from unittest import mock

sys.path.insert(0, str(Path(__file__).resolve().parent.parent))

import classify_api_differences as classifier


def api(name, declaration, *flags):
    return classifier.ClassApi(
        name=name,
        declaration=declaration,
        flags=frozenset(flags or ("ACC_PUBLIC",)),
    )


class HierarchyTest(unittest.TestCase):
    def test_erases_generics_and_sorts_interfaces(self):
        value = api(
            "example.Child",
            "public class example.Child<T> extends example.Parent<T> "
            "implements example.Second<T>, example.First",
        )
        self.assertEqual(
            classifier.class_hierarchy(value),
            ("example.Parent", ("example.First", "example.Second")),
        )

    def test_interface_parents_are_binary_surface(self):
        reference = api(
            "example.Contract",
            "public interface example.Contract extends example.First, example.Second",
            "ACC_PUBLIC", "ACC_INTERFACE", "ACC_ABSTRACT",
        )
        candidate = api(
            "example.Contract",
            "public interface example.Contract extends example.First",
            "ACC_PUBLIC", "ACC_INTERFACE", "ACC_ABSTRACT",
        )
        result = classifier.classify_class(reference, candidate)
        self.assertEqual(result["classification"], "binary_breaking")
        self.assertIn("interfaces changed", result["buckets"]["binary_breaking"][0])

    def test_superclass_change_is_binary_breaking(self):
        reference = api("example.Child", "public class example.Child extends example.Parent")
        candidate = api("example.Child", "public class example.Child")
        result = classifier.classify_class(reference, candidate)
        self.assertEqual(result["classification"], "binary_breaking")
        self.assertIn("superclass changed", result["buckets"]["binary_breaking"][0])


class AnnotationParsingTest(unittest.TestCase):
    def test_descriptor_style_annotation_names_are_collected(self):
        parsed = classifier.parse_javap("""Classfile /tmp/example/Example.class
public class example.Example
  flags: (0x0001) ACC_PUBLIC
{
  public void value();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    RuntimeVisibleAnnotations:
      0: #12()
        Landroid/annotation/Nullable;(
        )
}
""")

        member = next(iter(parsed["example.Example"].members.values()))
        self.assertEqual(member.annotations, ("Landroid/annotation/Nullable;",))


class AcceptedResidualTest(unittest.TestCase):
    def test_exact_accepted_finding_is_visible_but_does_not_fail(self):
        message = "missing member: public method execute ()V"
        results = {
            "example.Type": {
                "classification": "binary_breaking",
                "buckets": {"binary_breaking": [message]},
            }
        }
        accepted = classifier.AcceptedResiduals(
            class_findings=frozenset({("example.Type", "binary_breaking", message)}))
        classifier.validate_accepted_residuals(accepted, results, [])
        self.assertEqual(
            classifier.unaccepted_classification("example.Type", results["example.Type"], accepted),
            "match",
        )
        self.assertFalse(classifier.gate_fails(Counter(), "binary_breaking"))

    def test_stale_acceptance_is_rejected(self):
        accepted = classifier.AcceptedResiduals(
            class_findings=frozenset({("example.Type", "binary_breaking", "stale")})
        )
        with self.assertRaisesRegex(ValueError, "stale class findings"):
            classifier.validate_accepted_residuals(accepted, {}, [])

    def test_extra_public_surface_fails_without_acceptance(self):
        self.assertTrue(
            classifier.gate_fails(Counter({"extra_public_surface": 1}),
                                  "extra_public_surface")
        )

    def test_loads_extra_class_acceptance(self):
        path = Path(tempfile.mkdtemp()) / "accepted.json"
        path.write_text(json.dumps({"classes": {}, "extraClasses": ["example.Extension"]}))
        accepted = classifier.load_accepted_residuals(path)
        classifier.validate_accepted_residuals(accepted, {}, ["example.Extension"])
        self.assertEqual(accepted.extra_classes, frozenset({"example.Extension"}))


class ExtraPublicSurfaceReportTest(unittest.TestCase):
    def run_main(self, *, accepted_extra=False, fail_on=None):
        workdir = Path(tempfile.mkdtemp())
        output = workdir / "classified.json"
        reference = {"example.Base": api("example.Base", "public class example.Base")}
        candidate = {
            **reference,
            "example.Extension": api("example.Extension", "public class example.Extension"),
        }
        argv = [
            "classify_api_differences.py",
            "--reference", str(workdir / "reference.jar"),
            "--candidate", str(workdir / "candidate.jar"),
            "--output", str(output),
        ]
        if accepted_extra:
            accepted = workdir / "accepted.json"
            accepted.write_text(json.dumps({
                "classes": {},
                "extraClasses": ["example.Extension"],
            }))
            argv += ["--accepted-residuals", str(accepted)]
        if fail_on:
            argv += ["--fail-on", fail_on]

        with mock.patch.object(classifier, "load_surface", side_effect=[reference, candidate]):
            with mock.patch.object(sys, "argv", argv):
                return_code = classifier.main()
        return return_code, json.loads(output.read_text(encoding="utf-8"))

    def test_extra_class_is_separate_from_reference_counts(self):
        return_code, payload = self.run_main()
        self.assertEqual(return_code, 0)
        self.assertEqual(payload["counts"], {"match": 1})
        self.assertEqual(payload["unacceptedCounts"], {"extra_public_surface": 1})
        self.assertEqual(payload["extraClasses"], ["example.Extension"])

    def test_accepted_extra_class_does_not_appear_in_unaccepted_counts(self):
        return_code, payload = self.run_main(accepted_extra=True)
        self.assertEqual(return_code, 0)
        self.assertEqual(payload["counts"], {"match": 1})
        self.assertEqual(payload["unacceptedCounts"], {})

    def test_unaccepted_extra_class_fails_extra_public_surface_gate(self):
        return_code, payload = self.run_main(fail_on="extra_public_surface")
        self.assertEqual(return_code, 1)
        self.assertEqual(payload["unacceptedCounts"], {"extra_public_surface": 1})


class RegistryErrorTest(unittest.TestCase):
    def test_module_registry_error_returns_clean_cli_failure(self):
        argv = [
            "classify_api_differences.py",
            "--module", "missing",
            "--artifacts-root", "/tmp/artifacts",
            "--recovery-root", "/tmp/recovery",
        ]
        stderr = io.StringIO()
        with mock.patch.object(sys, "argv", argv):
            with mock.patch.object(
                classifier,
                "module_inputs",
                side_effect=classifier.RegistryError("Unknown module id: missing"),
            ):
                with mock.patch("sys.stderr", stderr):
                    return_code = classifier.main()

        self.assertEqual(1, return_code)
        self.assertEqual(
            "API classification failed: Unknown module id: missing\n",
            stderr.getvalue(),
        )


if __name__ == "__main__":
    unittest.main()
