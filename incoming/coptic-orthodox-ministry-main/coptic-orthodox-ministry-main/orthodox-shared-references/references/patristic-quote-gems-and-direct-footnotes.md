# Patristic quote gems and direct footnotes

Use this reference for Orthodox Bible-study drafting, audit, or revision passes where existing patristic summaries should be strengthened with the Fathers' own verified words.

## Core standard

1. Preserve the explanatory summary or paraphrase. Do not replace it with a quote dump.
2. Add direct patristic wording only when the source has been verified and `publication_status` allows quoting.
3. Prefer fuller but disciplined excerpts when the Father's wording carries the theological point better than a clipped phrase. One strong sentence, or a tightly connected pair of clauses, is often better than a tiny fragment.
4. Introduce and interpret every quote. The quote should authenticate and deepen the teaching, not decorate it.
5. Use the heading `Gems from the Fathers 💎` for body-level patristic quote sections, at the heading level required by the guide structure. Omit the subsection when there is no genuine direct or thematic patristic content.
6. Attach a semantic Markdown footnote to each direct patristic quote, such as `[^athanasius-incarnation]`.
7. The footnote definition itself contains the short citation and direct/deep URL. Do not make the footnote jump to an internal source-list anchor or say `See source [n]`.
8. Keep `Sources and Further Reading` as a clean numbered list when the guide contains direct quote footnotes.

## Batch continuation workflow

When extending an existing book-series pass:

1. Use the book index as the authoritative map for canonical guide filenames and order. Do not infer paths from memory.
2. Inventory the target batch before editing: existing `Gems from the Fathers` sections, direct quote markers, source sections, and downstream Docs/audio freshness state.
3. Query local patristic tooling first and honor `publication_status`, `known_gaps`, and paraphrase-only caps. Use public-domain web sources or normalized local source text only to verify exact wording, not to invent new attributions.
4. Patch surgically beside the existing paraphrase. Preserve already-good commentary unless the lesson author's explicitly asks for a rewrite.
5. If the structural verifier exposes legacy gaps while you are already revising the guide, repair narrow canonical structure gaps needed for the guide to pass, then disclose that extra repair.
6. Mark downstream Docs/audio stale when source Markdown changes.
7. Write a batch audit note that lists the exact quotes added, source-status basis, and verification results.

## Verification pattern

After patching a guide or batch:

- Re-read the patched quote sections and source sections.
- Check that every direct quote has a nearby footnote marker.
- Check every footnote definition has a citation and direct/deep URL.
- Scan for old redundant-hop patterns: `See source`, `#src-`, `<a id=`, `stable source-list anchor`, `footnote links back`.
- Verify numbered source lists are consecutive where present.
- Run the guide verifier and a Markdown knowledge base broken-link check for the folder when available.
- Re-run verification after bookkeeping edits, not only before them.

## Pitfalls

- Do not summarize instead of quoting when the lesson author's explicitly asked for the Fathers' own voice.
- Do not make quotes artificially short if a fuller public-domain excerpt is clearer and spiritually stronger.
- Do not let a footnote point to a source-list entry that then points to the real URL. the lesson author's corrected this as redundant.
- Do not delete body text when normalizing a heading. Change the heading only unless the body itself is wrong.
