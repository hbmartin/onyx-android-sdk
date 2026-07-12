"""Regression coverage for the descriptor-level JVM API classifier."""

import json
import sys
import tempfile
import unittest
from collections import Counter
from pathlib import Path

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


if __name__ == "__main__":
    unittest.main()
