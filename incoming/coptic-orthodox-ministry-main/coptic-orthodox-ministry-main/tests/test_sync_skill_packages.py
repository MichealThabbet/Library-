import json
import os
import shutil
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path


REPO_ROOT = Path(__file__).resolve().parents[1]
SCRIPT_PATH = REPO_ROOT / "scripts" / "sync_skill_packages.py"

MANIFEST = {
    "version": 1,
    "packages": [
        {
            "name": "coptic-orthodox-spiritual-lessons",
            "source": "spiritual-lessons",
            "mirror_files": [
                "SKILL.md",
                "references/adult-lesson-research-author-ownership-and-storyboards.md",
                "references/church-fathers-adult-meeting-lessons.md",
                "style-guides/lesson-structure.md",
                "style-guides/whatsapp-promo.md",
                "theology-references/liturgical-framework.md",
                "theology-references/theological-posture.md",
            ],
            "shared_imports": [
                "patristic-sources.md",
                "source-confidence.md",
                "accordance-and-licensed-bible-library-research.md",
                "orthodox-visual-assets.md",
                "orthodox-literary-voice-synthesis.md",
            ],
        },
        {
            "name": "orthodox-biblical-explanation",
            "source": "orthodox-biblical-explanation",
            "mirror_files": [
                "SKILL.md",
                "examples/1-kings-13-example.md",
            ],
            "shared_imports": [
                "patristic-sources.md",
                "source-confidence.md",
                "accordance-and-licensed-bible-library-research.md",
                "orthodox-visual-assets.md",
                "orthodox-literary-voice-synthesis.md",
            ],
        },
        {
            "name": "orthodox-iconography",
            "source": "orthodox-iconography",
            "mirror_files": [
                "SKILL.md",
            ],
            "shared_imports": [
                "patristic-sources.md",
                "source-confidence.md",
                "orthodox-visual-assets.md",
            ],
        },
        {
            "name": "orthodox-outreach-communications",
            "source": "outreach",
            "mirror_files": [
                "SKILL.md",
                "templates/congregation-update.md",
            ],
            "shared_imports": [],
        },
    ],
    "mirror_root": ".agents/skills",
    "alias_roots": [
        "skills",
        ".claude/skills",
        ".codebuddy/skills",
        ".cortex/skills",
        ".factory/skills",
        ".kilocode/skills",
        ".mcpjam/skills",
        ".mux/skills",
        ".openhands/skills",
        ".qwen/skills",
        ".vibe/skills",
        ".zencoder/skills",
    ],
}

AUTHORED_SOURCE_FILES = {
    "spiritual-lessons/SKILL.md": "Read references/shared/patristic-sources.md\n",
    "spiritual-lessons/references/adult-lesson-research-author-ownership-and-storyboards.md": "adult lesson research\n",
    "spiritual-lessons/references/church-fathers-adult-meeting-lessons.md": "church fathers\n",
    "spiritual-lessons/style-guides/lesson-structure.md": "lesson structure\n",
    "spiritual-lessons/style-guides/whatsapp-promo.md": "whatsapp promo\n",
    "spiritual-lessons/theology-references/liturgical-framework.md": "liturgical framework\n",
    "spiritual-lessons/theology-references/theological-posture.md": "theological posture\n",
    "orthodox-biblical-explanation/SKILL.md": "Read references/shared/patristic-sources.md\n",
    "orthodox-biblical-explanation/examples/1-kings-13-example.md": "example\n",
    "orthodox-iconography/SKILL.md": "Read references/shared/patristic-sources.md\n",
    "outreach/SKILL.md": "outreach skill\n",
    "outreach/templates/congregation-update.md": "template\n",
    "shared-references/patristic-sources.md": "shared patristic sources\n",
    "shared-references/source-confidence.md": "shared source confidence\n",
    "shared-references/accordance-and-licensed-bible-library-research.md": "shared licensed library research\n",
    "shared-references/orthodox-visual-assets.md": "shared visual assets\n",
    "shared-references/orthodox-literary-voice-synthesis.md": "shared literary voice\n",
}


class SyncSkillPackagesTest(unittest.TestCase):
    def setUp(self):
        self.temp_dir = tempfile.TemporaryDirectory()
        self.repo = Path(self.temp_dir.name)
        self._write_repo_fixture()

    def tearDown(self):
        self.temp_dir.cleanup()

    def test_check_passes_for_clean_repo(self):
        self.run_script("--check")

    def test_write_is_idempotent(self):
        self.run_script("--write")
        first_snapshot = self.snapshot()
        self.run_script("--write")
        second_snapshot = self.snapshot()
        self.assertEqual(first_snapshot, second_snapshot)

    def test_check_fails_for_modified_mirror_file_without_mutating(self):
        mirror_file = self.repo / ".agents/skills/orthodox-biblical-explanation/SKILL.md"
        mirror_file.write_text("mutated\n", encoding="utf-8")
        before = mirror_file.read_text(encoding="utf-8")
        result = self.run_script("--check", expect_success=False)
        self.assertIn("modified generated file", result.stderr)
        self.assertEqual(before, mirror_file.read_text(encoding="utf-8"))

    def test_check_fails_for_missing_mirror_file_without_mutating(self):
        missing_file = self.repo / ".agents/skills/orthodox-iconography/SKILL.md"
        missing_file.unlink()
        result = self.run_script("--check", expect_success=False)
        self.assertIn("missing generated file", result.stderr)
        self.assertFalse(missing_file.exists())

    def test_check_fails_for_extra_mirror_file_without_mutating(self):
        extra_file = self.repo / ".agents/skills/orthodox-outreach-communications/extra.md"
        extra_file.write_text("extra\n", encoding="utf-8")
        result = self.run_script("--check", expect_success=False)
        self.assertIn("extra generated file", result.stderr)
        self.assertTrue(extra_file.exists())

    def test_check_fails_for_stale_shared_bundle_file_without_mutating(self):
        stale_file = (
            self.repo
            / "orthodox-iconography/references/shared/stale.md"
        )
        stale_file.parent.mkdir(parents=True, exist_ok=True)
        stale_file.write_text("stale\n", encoding="utf-8")
        result = self.run_script("--check", expect_success=False)
        self.assertIn("extra generated file", result.stderr)
        self.assertTrue(stale_file.exists())

    def test_check_fails_for_wrong_alias_target_without_mutating(self):
        alias = self.repo / "skills/orthodox-iconography"
        alias.unlink()
        alias.symlink_to("../.agents/skills/orthodox-biblical-explanation")
        result = self.run_script("--check", expect_success=False)
        self.assertIn("wrong alias target", result.stderr)
        self.assertTrue(alias.is_symlink())

    def test_check_fails_when_alias_is_directory_without_mutating(self):
        alias = self.repo / ".claude/skills/orthodox-iconography"
        alias.unlink()
        alias.mkdir()
        result = self.run_script("--check", expect_success=False)
        self.assertIn("alias is not a symlink", result.stderr)
        self.assertTrue(alias.is_dir())

    def test_check_fails_for_missing_canonical_source_without_mutating(self):
        authored_file = self.repo / "shared-references/patristic-sources.md"
        authored_file.unlink()
        result = self.run_script("--check", expect_success=False)
        self.assertIn("missing canonical input", result.stderr)
        self.assertFalse(authored_file.exists())

    def test_check_fails_for_destination_collision_without_mutating(self):
        shutil.rmtree(self.repo / ".agents/skills/orthodox-iconography")
        collision = self.repo / ".agents/skills/orthodox-iconography"
        collision.write_text("not a directory\n", encoding="utf-8")
        result = self.run_script("--check", expect_success=False)
        self.assertIn("destination collision", result.stderr)
        self.assertTrue(collision.is_file())

    def test_check_fails_for_absolute_manifest_path(self):
        manifest_path = self.repo / "skill-packages.json"
        manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
        manifest["packages"][0]["mirror_files"].append("/absolute.md")
        manifest_path.write_text(json.dumps(manifest, indent=2) + "\n", encoding="utf-8")
        result = self.run_script("--check", expect_success=False)
        self.assertIn("unsafe absolute path", result.stderr)

    def test_check_fails_for_parent_traversal_manifest_path(self):
        manifest_path = self.repo / "skill-packages.json"
        manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
        manifest["packages"][0]["mirror_files"].append("../escape.md")
        manifest_path.write_text(json.dumps(manifest, indent=2) + "\n", encoding="utf-8")
        result = self.run_script("--check", expect_success=False)
        self.assertIn("unsafe traversal path", result.stderr)

    def test_check_fails_for_missing_manifest_version(self):
        manifest = self.read_manifest()
        del manifest["version"]
        self.write_manifest(manifest)
        result = self.run_script("--check", expect_success=False)
        self.assertIn("missing manifest version", result.stderr)

    def test_check_fails_for_unsupported_manifest_version(self):
        manifest = self.read_manifest()
        manifest["version"] = 2
        self.write_manifest(manifest)
        result = self.run_script("--check", expect_success=False)
        self.assertIn("unsupported manifest version: 2", result.stderr)

    def test_check_fails_for_unsafe_package_name_segments(self):
        for name in ("nested/name", r"nested\\name", "/absolute", ".", ".."):
            with self.subTest(name=name):
                self._reset_fixture()
                manifest = self.read_manifest()
                manifest["packages"][0]["name"] = name
                self.write_manifest(manifest)
                result = self.run_script("--check", expect_success=False)
                self.assertIn("unsafe package name", result.stderr)

    def test_check_fails_for_backslash_manifest_path(self):
        manifest = self.read_manifest()
        manifest["packages"][0]["mirror_files"].append(r"nested\\file.md")
        self.write_manifest(manifest)
        result = self.run_script("--check", expect_success=False)
        self.assertIn("invalid path separator", result.stderr)

    def test_check_rejects_generated_root_overlapping_canonical_source(self):
        manifest = self.read_manifest()
        manifest["mirror_root"] = "spiritual-lessons"
        self.write_manifest(manifest)
        result = self.run_script("--check", expect_success=False)
        self.assertIn("unsafe generated root overlap", result.stderr)

    def test_check_rejects_generated_root_overlapping_shared_references(self):
        manifest = self.read_manifest()
        manifest["alias_roots"][0] = "shared-references"
        self.write_manifest(manifest)
        result = self.run_script("--check", expect_success=False)
        self.assertIn("unsafe generated root overlap", result.stderr)

    def test_check_rejects_generated_roots_with_parent_child_overlap(self):
        manifest = self.read_manifest()
        manifest["alias_roots"] = ["skills", "skills/nested"]
        self.write_manifest(manifest)
        result = self.run_script("--check", expect_success=False)
        self.assertIn("unsafe generated root overlap", result.stderr)

    def test_check_rejects_canonical_source_nested_inside_generated_root(self):
        manifest = self.read_manifest()
        manifest["mirror_root"] = "."
        self.write_manifest(manifest)
        result = self.run_script("--check", expect_success=False)
        self.assertIn("unsafe generated root overlap", result.stderr)

    def test_check_rejects_symlink_generated_root(self):
        shutil.rmtree(self.repo / ".agents/skills/orthodox-iconography")
        (self.repo / ".agents/skills/orthodox-iconography").symlink_to(
            "../../orthodox-biblical-explanation"
        )
        result = self.run_script("--check", expect_success=False)
        self.assertIn("destination collision", result.stderr)

    def test_check_rejects_symlink_alias_root(self):
        shutil.rmtree(self.repo / "skills")
        (self.repo / "skills").symlink_to(".agents/skills")
        result = self.run_script("--check", expect_success=False)
        self.assertIn("destination collision", result.stderr)

    def test_check_reports_every_missing_alias_when_alias_root_directory_is_absent(self):
        shutil.rmtree(self.repo / "skills")
        result = self.run_script("--check", expect_success=False)

        missing_aliases = [
            f"missing alias: skills/{package['name']}"
            for package in MANIFEST["packages"]
        ]
        for expected_error in missing_aliases:
            self.assertIn(expected_error, result.stderr)

    def test_check_rejects_nested_symlink_at_expected_generated_file_without_mutating(self):
        generated_file = self.repo / ".agents/skills/orthodox-iconography/SKILL.md"
        generated_file.unlink()
        generated_file.symlink_to(
            os.path.relpath(self.repo / "orthodox-iconography/SKILL.md", start=generated_file.parent)
        )

        before_target = os.readlink(generated_file)
        result = self.run_script("--check", expect_success=False)

        self.assertIn("nested generated symlink", result.stderr)
        self.assertTrue(generated_file.is_symlink())
        self.assertEqual(before_target, os.readlink(generated_file))

    def test_check_rejects_nested_symlink_extra_generated_entry_without_mutating(self):
        extra_entry = self.repo / ".agents/skills/orthodox-outreach-communications/extra.md"
        extra_entry.symlink_to(
            os.path.relpath(self.repo / "outreach/SKILL.md", start=extra_entry.parent)
        )

        before_target = os.readlink(extra_entry)
        result = self.run_script("--check", expect_success=False)

        self.assertIn("nested generated symlink", result.stderr)
        self.assertTrue(extra_entry.is_symlink())
        self.assertEqual(before_target, os.readlink(extra_entry))

    def test_write_repairs_nested_symlink_drift_inside_owned_generated_roots(self):
        expected_generated_file = self.repo / ".agents/skills/orthodox-iconography/SKILL.md"
        expected_generated_file.unlink()
        expected_generated_file.symlink_to(
            os.path.relpath(self.repo / "orthodox-iconography/SKILL.md", start=expected_generated_file.parent)
        )

        extra_entry = self.repo / ".agents/skills/orthodox-outreach-communications/extra.md"
        extra_entry.symlink_to(
            os.path.relpath(self.repo / "outreach/SKILL.md", start=extra_entry.parent)
        )

        self.run_script("--write")
        self.run_script("--check")

        self.assertTrue(expected_generated_file.is_file())
        self.assertFalse(expected_generated_file.is_symlink())
        self.assertFalse(extra_entry.exists())

    def test_check_fails_for_manifest_declared_destination_collision(self):
        manifest = self.read_manifest()
        manifest["packages"][0]["mirror_files"].append("SKILL.md")
        self.write_manifest(manifest)
        result = self.run_script("--check", expect_success=False)
        self.assertIn("destination collision", result.stderr)

    def test_failing_check_does_not_mutate_repository_snapshot(self):
        target = self.repo / ".agents/skills/orthodox-biblical-explanation/SKILL.md"
        target.write_text("drifted\n", encoding="utf-8")
        before = self.snapshot()
        result = self.run_script("--check", expect_success=False)
        after = self.snapshot()
        self.assertIn("modified generated file", result.stderr)
        self.assertEqual(before, after)

    def test_write_repairs_multiple_generated_drift_then_check_passes(self):
        changed_canonical = self.repo / "shared-references/patristic-sources.md"
        changed_canonical.write_text("updated shared patristic sources\n", encoding="utf-8")

        modified_mirror = (
            self.repo
            / ".agents/skills/orthodox-biblical-explanation/references/shared/patristic-sources.md"
        )
        modified_mirror.write_text("drifted\n", encoding="utf-8")

        missing_shared = self.repo / "orthodox-iconography/references/shared/patristic-sources.md"
        missing_shared.unlink()

        extra_generated = self.repo / ".agents/skills/orthodox-outreach-communications/extra.md"
        extra_generated.write_text("extra\n", encoding="utf-8")

        wrong_alias = self.repo / "skills/orthodox-iconography"
        wrong_alias.unlink()
        wrong_alias.symlink_to("../.agents/skills/orthodox-biblical-explanation")

        self.run_script("--write")
        self.run_script("--check")

        self.assertEqual(
            changed_canonical.read_text(encoding="utf-8"),
            modified_mirror.read_text(encoding="utf-8"),
        )
        self.assertTrue(missing_shared.exists())
        self.assertFalse(extra_generated.exists())
        self.assertEqual(
            os.readlink(wrong_alias),
            "../.agents/skills/orthodox-iconography",
        )

    def _reset_fixture(self):
        self.temp_dir.cleanup()
        self.temp_dir = tempfile.TemporaryDirectory()
        self.repo = Path(self.temp_dir.name)
        self._write_repo_fixture()

    def read_manifest(self):
        return json.loads((self.repo / "skill-packages.json").read_text(encoding="utf-8"))

    def write_manifest(self, manifest):
        (self.repo / "skill-packages.json").write_text(
            json.dumps(manifest, indent=2) + "\n",
            encoding="utf-8",
        )

    def _write_repo_fixture(self):
        (self.repo / "skill-packages.json").write_text(
            json.dumps(MANIFEST, indent=2) + "\n",
            encoding="utf-8",
        )
        for rel_path, contents in AUTHORED_SOURCE_FILES.items():
            path = self.repo / rel_path
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text(contents, encoding="utf-8")
        self.populate_generated_state()

    def populate_generated_state(self):
        for package in MANIFEST["packages"]:
            source_root = self.repo / package["source"]
            shared_root = source_root / "references/shared"
            shared_root.mkdir(parents=True, exist_ok=True)
            for shared_name in package["shared_imports"]:
                shutil.copyfile(
                    self.repo / "shared-references" / shared_name,
                    shared_root / shared_name,
                )

            mirror_root = self.repo / MANIFEST["mirror_root"] / package["name"]
            mirror_root.mkdir(parents=True, exist_ok=True)
            for rel_path in package["mirror_files"]:
                source_file = source_root / rel_path
                target_file = mirror_root / rel_path
                target_file.parent.mkdir(parents=True, exist_ok=True)
                shutil.copyfile(source_file, target_file)
            for shared_name in package["shared_imports"]:
                target_file = mirror_root / "references/shared" / shared_name
                target_file.parent.mkdir(parents=True, exist_ok=True)
                shutil.copyfile(
                    self.repo / "shared-references" / shared_name,
                    target_file,
                )

        for alias_root in MANIFEST["alias_roots"]:
            root_path = self.repo / alias_root
            root_path.mkdir(parents=True, exist_ok=True)
            for package in MANIFEST["packages"]:
                alias_path = root_path / package["name"]
                target = os.path.relpath(
                    self.repo / MANIFEST["mirror_root"] / package["name"],
                    start=alias_path.parent,
                )
                alias_path.symlink_to(target)

    def run_script(self, *args, expect_success=True):
        result = subprocess.run(
            [sys.executable, str(SCRIPT_PATH), *args, "--repo", str(self.repo)],
            capture_output=True,
            text=True,
            check=False,
        )
        if expect_success:
            self.assertEqual(
                result.returncode,
                0,
                msg=f"stdout:\n{result.stdout}\n\nstderr:\n{result.stderr}",
            )
        else:
            self.assertNotEqual(result.returncode, 0, msg=result.stdout)
        return result

    def snapshot(self):
        entries = []
        for path in sorted(self.repo.rglob("*")):
            rel = path.relative_to(self.repo).as_posix()
            if path.is_symlink():
                entries.append(("symlink", rel, os.readlink(path)))
            elif path.is_file():
                entries.append(("file", rel, path.read_text(encoding="utf-8")))
            elif path.is_dir():
                entries.append(("dir", rel))
        return entries


if __name__ == "__main__":
    unittest.main()
