#!/usr/bin/env python3
"""Merge IntelliJ XML findings, keeping only ERROR-severity problems."""

from __future__ import annotations

import argparse
from pathlib import Path
import xml.etree.ElementTree as ET


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Merge XML findings and keep only problems with severity ERROR."
    )
    parser.add_argument(
        "input_dir",
        nargs="?",
        type=Path,
        default=Path("findings"),
        help="directory containing XML finding files (default: findings)",
    )
    parser.add_argument(
        "output",
        nargs="?",
        type=Path,
        default=Path("error-findings.xml"),
        help="combined XML output path (default: error-findings.xml)",
    )
    return parser.parse_args()


def is_error(problem: ET.Element) -> bool:
    problem_class = problem.find("problem_class")
    if problem_class is None:
        return False
    return problem_class.get("severity", "").casefold() == "error"


def gather_error_findings(input_dir: Path, output: Path) -> int:
    if not input_dir.is_dir():
        raise NotADirectoryError(f"Findings directory does not exist: {input_dir}")

    output_resolved = output.resolve()
    combined_root = ET.Element("problems")

    for xml_path in sorted(input_dir.rglob("*.xml")):
        if xml_path.resolve() == output_resolved:
            continue

        root = ET.parse(xml_path).getroot()
        if root.tag != "problems":
            raise ValueError(
                f"Expected <problems> as the root element in {xml_path}, "
                f"found <{root.tag}>"
            )

        for problem in root.findall("problem"):
            if is_error(problem):
                combined_root.append(problem)

    tree = ET.ElementTree(combined_root)
    ET.indent(tree, space="  ")
    output.parent.mkdir(parents=True, exist_ok=True)
    tree.write(output, encoding="utf-8", xml_declaration=True)
    return len(combined_root)


def main() -> None:
    args = parse_args()
    count = gather_error_findings(args.input_dir, args.output)
    print(f"Wrote {count} error findings to {args.output}")


if __name__ == "__main__":
    main()
