"""Regression coverage for annotation constant-pool decoding."""

import sys
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent.parent))

import dump_metadata


class ElementValueTest(unittest.TestCase):
    def test_class_value_resolves_class_reference_to_utf8(self):
        pool = {
            1: ("ref", 2),
            2: ("utf8", b"Lexample/Type;"),
        }

        value, offset = dump_metadata.read_element_value(b"c\x00\x01", 0, pool)

        self.assertEqual(value, ("c", "Lexample/Type;"))
        self.assertEqual(offset, 3)


if __name__ == "__main__":
    unittest.main()
