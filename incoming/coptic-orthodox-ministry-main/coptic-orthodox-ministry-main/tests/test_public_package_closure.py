import json
import re
import unittest
from pathlib import Path


REPO_ROOT = Path(__file__).resolve().parents[1]
MANIFEST = json.loads((REPO_ROOT / "skill-packages.json").read_text(encoding="utf-8"))

# These packages were added or refreshed as portable public ministry packages.
PUBLIC_PACKAGE_NAMES = {
    "coptic-orthodox-spiritual-lessons",
    "orthodox-biblical-explanation",
    "orthodox-iconography",
    "orthodox-apocalyptic-exegesis",
    "orthodox-shared-references",
    "exact-scripture-speaker-ledger",
}
PRIVATE_MARKERS = {
    "absolute user path": re.compile(r"/Users/", re.IGNORECASE),
    "Hermes home path": re.compile(r"~/.hermes", re.IGNORECASE),
    "personal workflow name": re.compile(r"\bGeorge(?:'s|’s)?\b", re.IGNORECASE),
    "Google document identifier": re.compile(
        r"https?://docs\.google\.com/document/d/[A-Za-z0-9_-]{20,}", re.IGNORECASE
    ),
}


class PublicPackageClosureTest(unittest.TestCase):
    def test_public_packages_have_all_declared_inputs(self):
        packages = {
            package["name"]: package
            for package in MANIFEST["packages"]
            if package["name"] in PUBLIC_PACKAGE_NAMES
        }
        self.assertEqual(PUBLIC_PACKAGE_NAMES, set(packages))

        for package in packages.values():
            source = REPO_ROOT / package["source"]
            for rel_path in package["mirror_files"]:
                self.assertTrue(
                    (source / rel_path).is_file(),
                    f"missing declared input: {package['name']}/{rel_path}",
                )
            for shared_name in package["shared_imports"]:
                self.assertTrue(
                    (REPO_ROOT / "shared-references" / shared_name).is_file(),
                    f"missing shared input: {package['name']}/{shared_name}",
                )

    def test_portable_public_package_sources_have_no_private_markers(self):
        packages = [
            package
            for package in MANIFEST["packages"]
            if package["name"] in PUBLIC_PACKAGE_NAMES
        ]
        checked_files = []
        for package in packages:
            source = REPO_ROOT / package["source"]
            for rel_path in package["mirror_files"]:
                checked_files.append(source / rel_path)
            for shared_name in package["shared_imports"]:
                checked_files.append(REPO_ROOT / "shared-references" / shared_name)

        for path in checked_files:
            text = path.read_text(encoding="utf-8")
            for label, marker in PRIVATE_MARKERS.items():
                self.assertIsNone(marker.search(text), f"{label}: {path.relative_to(REPO_ROOT)}")


if __name__ == "__main__":
    unittest.main()
