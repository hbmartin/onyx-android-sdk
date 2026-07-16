"""Regression tests for optional advanced-pen snapshot coverage."""

import importlib.util
import sys
import unittest
from pathlib import Path


SCRIPT = Path(__file__).resolve().parents[2] / "scripts" / "compare-pen-snapshots.py"
SPEC = importlib.util.spec_from_file_location("compare_pen_snapshots", SCRIPT)
compare_pen_snapshots = importlib.util.module_from_spec(SPEC)
assert SPEC.loader is not None
sys.modules[SPEC.name] = compare_pen_snapshots
SPEC.loader.exec_module(compare_pen_snapshots)


class AdvancedSnapshotValidationTest(unittest.TestCase):
    def test_snapshots_with_only_legacy_pen_types_skip_advanced_validation(self):
        errors = []

        metrics = compare_pen_snapshots.validate_notable_advanced({}, {}, errors)

        self.assertEqual([], metrics)
        self.assertEqual([], errors)

    def test_candidate_only_advanced_case_is_still_validated(self):
        safe_ink = compare_pen_snapshots.Ink((1.0, 2.0, 0.5), (3,), ())
        malformed_ink = compare_pen_snapshots.Ink(
            (float("nan"), 2.0, 0.5), (3,), ()
        )
        actual = {
            (6, False, phase, layer): (
                malformed_ink if (phase, layer) == ("move", "real") else safe_ink
            )
            for phase in ("down", "move", "up")
            for layer in ("real", "prediction")
        }
        errors = []

        compare_pen_snapshots.validate_notable_advanced({}, actual, errors)

        self.assertTrue(
            any("candidate contains a non-finite value" in error for error in errors)
        )


if __name__ == "__main__":
    unittest.main()
