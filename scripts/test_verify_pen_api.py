import importlib.util
from pathlib import Path
import unittest


SPEC = importlib.util.spec_from_file_location(
    "verify_pen_api",
    Path(__file__).with_name("verify-pen-api.py"),
)
assert SPEC is not None and SPEC.loader is not None
VERIFY_PEN_API = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(VERIFY_PEN_API)


class DirectionalModifierCompatibilityTest(unittest.TestCase):
    def test_candidate_cannot_add_final(self) -> None:
        self.assertFalse(
            VERIFY_PEN_API.declaration_is_compatible(
                "public void draw();",
                "public final void draw();",
            )
        )

    def test_candidate_can_remove_final(self) -> None:
        self.assertTrue(
            VERIFY_PEN_API.declaration_is_compatible(
                "public final void draw();",
                "public void draw();",
            )
        )

    def test_candidate_cannot_add_abstract(self) -> None:
        self.assertFalse(
            VERIFY_PEN_API.declaration_is_compatible(
                "public class Pen {",
                "public abstract class Pen {",
            )
        )

    def test_descriptor_change_remains_a_different_member(self) -> None:
        expected = ("public void draw();", "descriptor: ()V")
        actual = ("public void draw();", "descriptor: (I)V")
        self.assertNotEqual(
            VERIFY_PEN_API.compatibility_signature(expected),
            VERIFY_PEN_API.compatibility_signature(actual),
        )


if __name__ == "__main__":
    unittest.main()
