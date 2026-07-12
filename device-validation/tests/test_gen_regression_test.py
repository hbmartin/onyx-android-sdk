"""The generator template and the checked-in generated test must not drift.

gen_regression_test.py duplicates the descriptor-parsing helpers into the
checked-in RecoveredApiSurfaceRegressionTest.java; an edit to one without
regenerating (or hand-syncing) the other would silently diverge.
"""

import ast
import unittest
from pathlib import Path

HERE = Path(__file__).resolve().parent
GENERATOR = HERE.parent / "gen_regression_test.py"
GENERATED = (HERE.parents[1] / "onyxsdk-base" / "src" / "test" / "java"
             / "com" / "onyx" / "android" / "sdk"
             / "RecoveredApiSurfaceRegressionTest.java")


def generator_template() -> str:
    # The module reads sys.argv at import time, so pull the TEMPLATE literal
    # out of the source instead of importing it.
    module = ast.parse(GENERATOR.read_text(encoding="utf-8"))
    for node in module.body:
        if isinstance(node, ast.Assign) and isinstance(node.value, ast.Constant) and any(
                isinstance(target, ast.Name) and target.id == "TEMPLATE"
                for target in node.targets):
            return node.value.value
    raise AssertionError("gen_regression_test.py no longer defines TEMPLATE")


class TemplateDriftTest(unittest.TestCase):
    def test_intentionally_removed_firmware_update_is_excluded(self):
        generator = GENERATOR.read_text(encoding="utf-8")
        self.assertIn("EXCLUDED_MEMBERS", generator)
        self.assertIn("com.onyx.android.sdk.firmware.api.OnyxOTAService", generator)
        self.assertIn("firmwareUpdate", generator)
        self.assertIn("(Ljava/lang/String;)Lretrofit2/Call;", generator)

    def test_checked_in_test_matches_generator_template(self):
        prefix, placeholder, suffix = generator_template().partition("//BODY//")
        self.assertTrue(placeholder,
                        "generator template lost its //BODY// placeholder")
        generated = GENERATED.read_text(encoding="utf-8")
        self.assertTrue(
            generated.startswith(prefix),
            "checked-in test no longer starts with the generator template; "
            "regenerate it or sync gen_regression_test.py")
        self.assertTrue(
            generated.endswith(suffix),
            "checked-in test helpers differ from the generator template; "
            "regenerate it or sync gen_regression_test.py")


if __name__ == "__main__":
    unittest.main()
