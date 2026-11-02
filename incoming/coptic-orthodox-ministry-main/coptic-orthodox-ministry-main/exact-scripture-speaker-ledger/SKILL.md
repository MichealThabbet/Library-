---
name: exact-scripture-speaker-ledger
description: Create and verify a byte-exact Scripture speaker ledger from Markdown block quotations. Use when Scripture quotation lines need speaker roles, offsets, coverage checks, and a machine-verifiable JSON artifact.
version: 1.0.0
license: CC-BY-4.0
---

# Exact Scripture Speaker Ledger

- Read source Markdown as UTF-8 bytes and select only lines beginning with `>` whose content after removal of `>` plus at most one following ASCII space is non-empty.
- Store the resulting text verbatim; use UTF-8 byte offsets for spans and require that concatenated span text equals the source text byte-for-byte.
- Treat prophetic/literary delivery as the prophet by default, and reserve `narrative` for genuine third-person framing. Split a line at every explicit speaker transition, including carrier formulas and embedded quotations.
- Record source filename, 1-based Markdown line number, per-source SHA-256, line counts, role counts, uncertainties, and machine-verification results in one JSON artifact.
- Re-read all source files after generation and compare the ordered `(line_number, text)` list against the ledger. Verify permitted role IDs, exact concatenation, offset adjacency, and total coverage before reporting the artifact SHA-256.

Keep the ledger source-neutral: do not include local absolute paths, account identifiers, or private repository metadata.
