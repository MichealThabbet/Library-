# Patristic Sourcing Policy

- Prefer direct patristic texts when practical.
- Keep summary and paraphrase as the interpretive frame, but when exact wording is verified and source status allows it, include verified direct wording that gives the Father's own voice.
- Treat Catena-style tools as discovery aids, not final citation authority, when direct sources are available.
- Paraphrase when exact wording is not verified, source status does not allow reader-facing quotation, the quote would be too long, or citation/provenance is weak.
- Cite works specifically: treatise, homily, book, chapter, section, or paragraph when available.
- Never invent a Father, quote, title, homily number, citation location, feast usage, or lectionary placement.
- Track two separate gates: citation validity and teaching depth.

## Direct quote gems

the lesson author's prefers patristic material not to be all summary. When a guide, lesson, or audit uses a substantive Father, ask whether a verified sentence or tightly connected excerpt from that source would add authenticity, beauty, and weight. If yes, include it inline after the summary or paraphrase.

A direct quote is useful only when it does interpretive work. Do not quote as ornament. First explain what the Father is teaching in plain language, then let the Father's own words sharpen, confirm, or deepen that point.

Rules:
- Preserve the summary/paraphrase first; the direct quote sharpens and authenticates it, not replaces it.
- Prefer fuller, readable excerpts when the Father's own wording carries the theological point better than a clipped phrase. A good Bible-study quote is often one strong sentence or a tightly connected pair of clauses, not just a tiny gem. Still avoid long quote blocks that do not do real interpretive work.
- Follow the live source metadata. `quote_eligible` material may be quoted with citation. `paraphrase_only` material remains paraphrase-first and may use only protocol-allowed brief gems within the configured verbatim cap and with attribution. `discovery_only`, `private_only`, and `blocked` material cannot become reader-facing quotes.
- Use the returned `source_deep_url` when available, falling back to `source_url` only when a deep link is unavailable or fails audit.
- Skip the quote rather than weaken provenance. A paraphrase with honest citation is better than an attractive but unsupported quote.

## Bible-study direct-quote citation style

For Bible-study guides, direct patristic quotes should carry a footnote marker, and the footnote itself should contain the citation with the direct public URL or `source_deep_url`. Do not make the reader click from the quote to a footnote and then again to a numbered source-list item. The footnote is the citation.

Preferred pattern:
- Use semantic footnote IDs in the Markdown, such as `[^athanasius-incarnation]` rather than bare `[^1]`. Rendered output will still number the footnotes, but semantic IDs are easier to maintain.
- Define the footnote near the end of the guide with the full short citation and direct/deep URL, e.g. `[^basil-hexaemeron]: St. Basil the Great, *Hexaemeron*, Homily I on Genesis 1:1, <https://www.newadvent.org/fathers/32011.htm>.`
- `## Sources and Further Reading` may still use numbered lists for readability, but do not add HTML anchors solely for quote footnotes and do not make quote footnotes hop internally to the source list.
- Do not use a Markdown knowledge base wikilinks for citation footnotes in reader-facing guide bodies. Use portable Markdown footnotes with direct citation URLs.

## Body use: three tiers for `Gems from the Fathers`

1. **Tier 1 direct.** A Father comments on this passage or book. Summarize the interpretation in clean prose, then include verified direct wording when possible under a `Gems from the Fathers 💎` heading, e.g. `Saint John Chrysostom, commenting on this passage, teaches that ... He calls this "...".`
2. **Tier 2 thematic.** No direct comment surfaced for the passage, but a Father speaks to the passage's theme, such as repentance, exile, temple, perseverance, pride, mercy, judgment, prayer, or worship. Bring it only when honest, naming the Father and the theme, e.g. `The Fathers do not treat Ezra 4 directly in the sources used for this study, but Saint John Chrysostom on repentance speaks to what this chapter shows: ...` Add verified direct wording only if it genuinely illuminates the theme.
3. **Tier 3 none.** Omit the patristic subsection entirely. No empty heading, no placeholder, and no absence statement in the body.

## Source notes absence

When a passage had no direct or thematic patristic material in the sources used for the study, record that only in the final `## Source notes`, scoped to the study:

`No direct patristic commentary on Ezra 4 surfaced in this study's sources.`

Do not claim the Fathers never addressed the passage. Do not use tooling, search-process, index, script, command, helper, file-path, or match-status language in reader-facing notes.

## Depth rule

A hollow or forced citation fails even if the citation is real. A generic point dressed as patristic is worse than omitting `### Gems from the Fathers 💎` and recording scoped absence in Source notes.

A fully summarized patristic section can pass citation validity but still feel thin if a quote-eligible source offers a brief phrase that would let the Father speak directly. For publication-quality Bible-study work, prefer fewer, stronger Fathers with a sentence of interpretation and a small verified quote gem over many summarized names. When quote-eligible sources exist for an NT session guide, include two or three exact lines with footnotes. Do not invent quotes to hit a count.
