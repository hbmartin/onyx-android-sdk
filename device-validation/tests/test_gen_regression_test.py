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


def generator_exclusion_api():
    module = ast.parse(GENERATOR.read_text(encoding="utf-8"))
    selected = []
    for node in module.body:
        if isinstance(node, ast.Assign) and any(
                isinstance(target, ast.Name) and target.id == "EXCLUDED_MEMBERS"
                for target in node.targets):
            selected.append(node)
        elif isinstance(node, ast.FunctionDef) and node.name == "excluded":
            selected.append(node)
    namespace = {}
    exec(compile(ast.Module(body=selected, type_ignores=[]), GENERATOR, "exec"),
         namespace)
    return namespace["EXCLUDED_MEMBERS"], namespace["excluded"]


class TemplateDriftTest(unittest.TestCase):
    def test_intentionally_removed_firmware_update_is_excluded(self):
        members, excluded = generator_exclusion_api()
        owner, name, descriptor = next(iter(members))
        message = f"missing member: public method {name} {descriptor}"

        self.assertTrue(excluded(owner, message))
        self.assertFalse(excluded(f"{owner}.Other", message))
        self.assertFalse(excluded(
            owner,
            f"missing member: public method {name} (Ljava/lang/String;)V",
        ))

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
