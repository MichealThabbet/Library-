#!/usr/bin/env python3
import argparse
import json
import os
import re
import shutil
import sys
from dataclasses import dataclass
from pathlib import Path, PurePosixPath


MANIFEST_NAME = "skill-packages.json"
MANIFEST_VERSION = 1
SHARED_SOURCE_ROOT = "shared-references"
SAFE_PACKAGE_NAME = re.compile(r"^[A-Za-z0-9][A-Za-z0-9._-]*$")


class SyncError(Exception):
    pass


@dataclass(frozen=True)
class PackageSpec:
    name: str
    source: str
    mirror_files: tuple[str, ...]
    shared_imports: tuple[str, ...]


@dataclass(frozen=True)
class Manifest:
    mirror_root: str
    alias_roots: tuple[str, ...]
    packages: tuple[PackageSpec, ...]


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Sync generated skill packages and aliases.")
    mode = parser.add_mutually_exclusive_group(required=True)
    mode.add_argument("--check", action="store_true", help="Verify generated files and aliases.")
    mode.add_argument("--write", action="store_true", help="Rewrite generated files and aliases.")
    parser.add_argument("--repo", default=".", help=argparse.SUPPRESS)
    return parser.parse_args()


def load_manifest(repo_root: Path) -> Manifest:
    manifest_path = repo_root / MANIFEST_NAME
    try:
        raw = json.loads(manifest_path.read_text(encoding="utf-8"))
    except FileNotFoundError as exc:
        raise SyncError(f"missing manifest: {manifest_path}") from exc

    if "version" not in raw:
        raise SyncError("missing manifest version")
    if raw["version"] != MANIFEST_VERSION:
        raise SyncError(f"unsupported manifest version: {raw['version']}")

    mirror_root = validate_root_path(raw["mirror_root"])
    alias_roots = tuple(validate_root_path(value) for value in raw["alias_roots"])
    packages = []
    seen_names = set()
    seen_sources = set()
    for package_raw in raw["packages"]:
        name = validate_package_name(package_raw["name"])
        source = validate_rel_path(package_raw["source"])
        if name in seen_names:
            raise SyncError(f"duplicate package name: {name}")
        if source in seen_sources:
            raise SyncError(f"duplicate package source: {source}")
        seen_names.add(name)
        seen_sources.add(source)
        mirror_files = tuple(validate_rel_path(value) for value in package_raw["mirror_files"])
        shared_imports = tuple(validate_rel_path(value) for value in package_raw["shared_imports"])
        packages.append(
            PackageSpec(
                name=name,
                source=source,
                mirror_files=mirror_files,
                shared_imports=shared_imports,
            )
        )
    return Manifest(mirror_root=mirror_root, alias_roots=alias_roots, packages=tuple(packages))


def validate_rel_path(raw_path: str) -> str:
    if "\\" in raw_path:
        raise SyncError(f"invalid path separator: {raw_path}")
    path = PurePosixPath(raw_path)
    if path.is_absolute():
        raise SyncError(f"unsafe absolute path: {raw_path}")
    parts = path.parts
    if not parts:
        raise SyncError(f"empty path: {raw_path}")
    if any(part == ".." for part in parts):
        raise SyncError(f"unsafe traversal path: {raw_path}")
    if any(part in ("", ".") for part in parts):
        raise SyncError(f"invalid path segment: {raw_path}")
    return path.as_posix()


def validate_root_path(raw_path: str) -> str:
    if raw_path == ".":
        return "."
    return validate_rel_path(raw_path)


def validate_package_name(raw_name: str) -> str:
    if "\\" in raw_name or "/" in raw_name:
        raise SyncError(f"unsafe package name: {raw_name}")
    if raw_name in ("", ".", ".."):
        raise SyncError(f"unsafe package name: {raw_name}")
    if PurePosixPath(raw_name).is_absolute():
        raise SyncError(f"unsafe package name: {raw_name}")
    if not SAFE_PACKAGE_NAME.fullmatch(raw_name):
        raise SyncError(f"unsafe package name: {raw_name}")
    return raw_name


def build_specs(repo_root: Path, manifest: Manifest) -> tuple[list[tuple[Path, Path]], dict[Path, dict[Path, Path]], dict[Path, str]]:
    generated_dirs: dict[Path, dict[Path, Path]] = {}
    aliases: dict[Path, str] = {}
    package_targets = set()
    canonical_inputs: list[tuple[Path, Path]] = []

    for package in manifest.packages:
        source_root = repo_root / package.source
        mirror_root = repo_root / manifest.mirror_root / package.name
        if mirror_root in package_targets:
            raise SyncError(f"destination collision: {mirror_root.relative_to(repo_root).as_posix()}")
        package_targets.add(mirror_root)

        source_mapping: dict[Path, Path] = {}
        expected_rel_paths = set()

        for rel_path in package.mirror_files:
            src = source_root / rel_path
            dst = mirror_root / rel_path
            register_path(expected_rel_paths, dst.relative_to(mirror_root).as_posix())
            source_mapping[dst] = src
            canonical_inputs.append((src, dst))

        shared_root = source_root / "references/shared"
        shared_mapping: dict[Path, Path] = {}
        shared_rel_paths = set()
        for shared_name in package.shared_imports:
            shared_src = repo_root / SHARED_SOURCE_ROOT / shared_name
            bundled_dst = shared_root / shared_name
            mirror_dst = mirror_root / "references/shared" / shared_name
            register_path(shared_rel_paths, bundled_dst.relative_to(shared_root).as_posix())
            register_path(expected_rel_paths, mirror_dst.relative_to(mirror_root).as_posix())
            shared_mapping[bundled_dst] = shared_src
            source_mapping[mirror_dst] = shared_src
            canonical_inputs.append((shared_src, bundled_dst))
            canonical_inputs.append((shared_src, mirror_dst))

        generated_dirs[shared_root] = shared_mapping
        generated_dirs[mirror_root] = source_mapping

        for alias_root in manifest.alias_roots:
            alias_path = repo_root / alias_root / package.name
            aliases[alias_path] = os.path.relpath(mirror_root, start=alias_path.parent)

    return canonical_inputs, generated_dirs, aliases


def validate_generated_roots(repo_root: Path, manifest: Manifest) -> None:
    canonical_roots = [repo_root / SHARED_SOURCE_ROOT]
    canonical_roots.extend(repo_root / package.source for package in manifest.packages)
    generated_roots = [repo_root / manifest.mirror_root]
    generated_roots.extend(repo_root / alias_root for alias_root in manifest.alias_roots)

    for generated_root in generated_roots:
        for canonical_root in canonical_roots:
            if paths_overlap(generated_root, canonical_root):
                raise SyncError(
                    "unsafe generated root overlap: "
                    f"{display(repo_root, generated_root)} <-> {display(repo_root, canonical_root)}"
                )

    for index, generated_root in enumerate(generated_roots):
        for other_root in generated_roots[index + 1 :]:
            if paths_overlap(generated_root, other_root):
                raise SyncError(
                    "unsafe generated root overlap: "
                    f"{display(repo_root, generated_root)} <-> {display(repo_root, other_root)}"
                )


def register_path(owned_paths: set[str], rel_path: str) -> None:
    if rel_path in owned_paths:
        raise SyncError(f"destination collision: {rel_path}")
    owned_paths.add(rel_path)


def paths_overlap(left: Path, right: Path) -> bool:
    return is_relative_to(left, right) or is_relative_to(right, left)


def is_relative_to(path: Path, other: Path) -> bool:
    try:
        path.relative_to(other)
        return True
    except ValueError:
        return False


def validate_canonical_inputs(canonical_inputs: list[tuple[Path, Path]], repo_root: Path) -> list[str]:
    errors = []
    seen_destinations = set()
    for src, dst in canonical_inputs:
        if dst in seen_destinations:
            errors.append(f"destination collision: {display(repo_root, dst)}")
        seen_destinations.add(dst)
        if not src.is_file():
            errors.append(f"missing canonical input: {display(repo_root, src)}")
    return errors


def check_generated_dir(root: Path, expected_files: dict[Path, Path], repo_root: Path) -> list[str]:
    errors = []
    expected_paths = set(expected_files)
    symlink_paths = set()

    if root.is_symlink():
        return [f"destination collision: {display(repo_root, root)}"]
    if root.exists() and not root.is_dir():
        return [f"destination collision: {display(repo_root, root)}"]

    actual_files = set()
    if root.is_dir():
        for path in root.rglob("*"):
            if path.is_symlink():
                symlink_paths.add(path)
                continue
            if path.is_dir():
                continue
            actual_files.add(path)

    for expected_path, source_path in sorted(expected_files.items()):
        if expected_path.is_symlink():
            errors.append(f"nested generated symlink: {display(repo_root, expected_path)}")
            continue
        if not expected_path.exists():
            errors.append(f"missing generated file: {display(repo_root, expected_path)}")
            continue
        if expected_path.is_dir():
            errors.append(f"destination collision: {display(repo_root, expected_path)}")
            continue
        if not source_path.is_file():
            continue
        if expected_path.read_bytes() != source_path.read_bytes():
            errors.append(f"modified generated file: {display(repo_root, expected_path)}")

    for actual_path in sorted(actual_files - expected_paths):
        errors.append(f"extra generated file: {display(repo_root, actual_path)}")
    for symlink_path in sorted(symlink_paths - expected_paths):
        errors.append(f"nested generated symlink: {display(repo_root, symlink_path)}")
    return errors


def check_aliases(aliases: dict[Path, str], repo_root: Path) -> list[str]:
    errors = []
    by_root: dict[Path, set[Path]] = {}
    for alias_path in aliases:
        by_root.setdefault(alias_path.parent, set()).add(alias_path)

    for alias_root, expected_aliases in by_root.items():
        if alias_root.is_symlink():
            errors.append(f"destination collision: {display(repo_root, alias_root)}")
            continue
        if alias_root.exists() and not alias_root.is_dir():
            errors.append(f"destination collision: {display(repo_root, alias_root)}")
            continue
        if not alias_root.is_dir():
            for alias_path in sorted(expected_aliases):
                errors.append(f"missing alias: {display(repo_root, alias_path)}")
            continue
        for alias_path in sorted(expected_aliases):
            expected_target = aliases[alias_path]
            if not alias_path.exists() and not alias_path.is_symlink():
                errors.append(f"missing alias: {display(repo_root, alias_path)}")
                continue
            if not alias_path.is_symlink():
                errors.append(f"alias is not a symlink: {display(repo_root, alias_path)}")
                continue
            actual_target = os.readlink(alias_path)
            if actual_target != expected_target:
                errors.append(f"wrong alias target: {display(repo_root, alias_path)} -> {actual_target}")
        for actual_path in sorted(alias_root.iterdir()):
            if actual_path not in expected_aliases:
                errors.append(f"extra generated file: {display(repo_root, actual_path)}")
    return errors


def write_generated_dir(root: Path, expected_files: dict[Path, Path]) -> None:
    if root.exists():
        if root.is_symlink() or not root.is_dir():
            raise SyncError(f"destination collision: {root}")
        clear_directory(root)
    if not expected_files:
        if root.exists():
            root.rmdir()
        return
    root.mkdir(parents=True, exist_ok=True)
    for target_path, source_path in sorted(expected_files.items()):
        target_path.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(source_path, target_path)


def write_aliases(aliases: dict[Path, str]) -> None:
    by_root: dict[Path, set[Path]] = {}
    for alias_path in aliases:
        by_root.setdefault(alias_path.parent, set()).add(alias_path)

    for alias_root, expected_aliases in by_root.items():
        if alias_root.exists():
            if alias_root.is_symlink() or not alias_root.is_dir():
                raise SyncError(f"destination collision: {alias_root}")
            for actual_path in alias_root.iterdir():
                if actual_path not in expected_aliases:
                    remove_path(actual_path)
        else:
            alias_root.mkdir(parents=True, exist_ok=True)

        for alias_path in expected_aliases:
            if alias_path.exists() or alias_path.is_symlink():
                remove_path(alias_path)
            alias_path.symlink_to(aliases[alias_path])


def remove_path(path: Path) -> None:
    if path.is_symlink() or path.is_file():
        path.unlink()
        return
    if path.is_dir():
        clear_directory(path)
        path.rmdir()
        return
    if path.exists():
        raise SyncError(f"cannot remove path: {path}")


def clear_directory(root: Path) -> None:
    for path in sorted(root.rglob("*"), reverse=True):
        if path.is_symlink() or path.is_file():
            path.unlink()
        elif path.is_dir():
            path.rmdir()


def display(repo_root: Path, path: Path) -> str:
    try:
        return path.relative_to(repo_root).as_posix()
    except ValueError:
        return path.as_posix()


def main() -> int:
    args = parse_args()
    repo_root = Path(args.repo).resolve()
    try:
        manifest = load_manifest(repo_root)
        validate_generated_roots(repo_root, manifest)
        canonical_inputs, generated_dirs, aliases = build_specs(repo_root, manifest)
    except SyncError as exc:
        print(str(exc), file=sys.stderr)
        return 1

    errors = validate_canonical_inputs(canonical_inputs, repo_root)
    for root, expected_files in sorted(generated_dirs.items()):
        errors.extend(check_generated_dir(root, expected_files, repo_root))
    errors.extend(check_aliases(aliases, repo_root))

    if errors:
        if args.check:
            print("\n".join(errors), file=sys.stderr)
            return 1
        if any(error.startswith(("missing canonical input", "destination collision")) for error in errors):
            print("\n".join(errors), file=sys.stderr)
            return 1

    if args.write:
        try:
            for root, expected_files in sorted(generated_dirs.items()):
                write_generated_dir(root, expected_files)
            write_aliases(aliases)
        except SyncError as exc:
            print(str(exc), file=sys.stderr)
            return 1

        post_errors = []
        for root, expected_files in sorted(generated_dirs.items()):
            post_errors.extend(check_generated_dir(root, expected_files, repo_root))
        post_errors.extend(check_aliases(aliases, repo_root))
        if post_errors:
            print("\n".join(post_errors), file=sys.stderr)
            return 1

    print("OK")
    return 0


if __name__ == "__main__":
    sys.exit(main())
