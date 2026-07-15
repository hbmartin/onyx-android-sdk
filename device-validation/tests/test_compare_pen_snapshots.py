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


if __name__ == "__main__":
    unittest.main()
