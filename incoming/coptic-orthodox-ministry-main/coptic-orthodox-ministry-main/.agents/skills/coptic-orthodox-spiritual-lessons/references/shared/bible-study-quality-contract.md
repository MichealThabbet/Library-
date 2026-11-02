# Bible Study Quality Contract

Use this as the compact, current quality contract for the lesson author's Orthodox Bible-study guides. It does not replace book-specific references, but it overrides older historical examples when structure or wording conflicts.

## When to load

Load this reference before drafting, revising, auditing, or publishing any Orthodox Bible-study guide when one of these is true:

- a guide body will be written or changed;
- an audit will judge publication readiness;
- a source pack will drive guide drafting;
- a historical precedent might conflict with current structure;
- the lesson author's asks for a high-quality, publication-ready, final, depth, or full-series pass.

For quick routing or lookup-only questions, do not load this unless the output will become guide prose.

## Governing principle

The guide should help a Coptic Orthodox servant teach the actual passage with Scripture, the Fathers, worship, and pastoral wisdom. It must not merely sound religious. Every section should answer: why does this exact text matter for life in Christ?

Load `references/spiritually-edifying-exegesis-voice-standard.md` whenever guide prose is drafted, revised, or judged for publication readiness. Its controlling sequence is: relevant context, close explanation of the actual verses, Christ-centered Orthodox meaning, personal conviction, and concrete spiritual benefit. Preserve eloquence and spiritual depth, but reject generic AI-sounding abstractions, decorative context, uncommon framing jargon such as `movement`, and repeated rhetorical-question chains. Patristic material belongs later in the guide rather than in the introduction unless the lesson author's explicitly asks otherwise.

For New Testament final-depth or publication-ready revision of session guides, also load `references/nt-publication-ready-spiritual-depth-and-engagement.md` and `references/orthodox-literary-voice-synthesis.md`. Use them as the qualitative promotion standard for spiritual depth, modern engagement, source-informed literary beauty, Spiritual Fruit strength, and read-aloud readiness. Do not force OT batch section laundry lists onto NT session guides.

## Publication-depth contract

For final revision, NT publication-readiness, full-series audits, and any guide the lesson author's expects to be "done," mechanical structure is not enough. Read the actual guide body and judge whether it is spiritually substantial and engaging enough for a modern adult reader without diluting Orthodox substance.

A guide passes only when it has all of these:

- **Scriptural insight:** the explanation follows the actual argument and progression of the passage, not a generic theme attached to the passage.
- **Orthodox imagination:** Christology, the Church, repentance, worship, ascetical life, and sacramental meaning arise naturally from the text where warranted.
- **Spiritual depth:** each major section presses toward prayer, repentance, watchfulness, mercy, humility, worship, or union with Christ.
- **Concrete fruit:** Spiritual Fruit names recognizable heart conditions and usable responses, not vague uplift.
- **Research sufficiency:** difficult claims, Fathers, lectionary placements, historical context, iconography, language notes, and source-shape issues are checked against appropriate sources rather than filled from memory.
- **Explained terms:** Greek, Hebrew, Aramaic, Coptic, Syriac, patristic, liturgical, iconographic, historical, and doctrinal terms are defined where a parish reader or servant may not know them.
- **Original-language depth where it matters:** especially in NT guides, check whether Greek or Aramaic terms carry theological or spiritual meaning that English flattens. Examples include Logos behind “Word,” Paraklētos behind “Helper” or “Comforter,” and distinct Greek love words behind English “love.” Include this only when it deepens the passage, not as decorative vocabulary. Treat the sense of significant names and places the same way when they have theological payoff.
- **Cross-study links:** when another published Bible study is mentioned, make the reference text itself a site-absolute Markdown link with no `.md` suffix. Do not print the path as visible prose. If the revision develops that other text, add a return link in the matching study.
- **Beautiful prose and flow:** write in clear, reverent, flowing paragraphs that a modern adult will keep reading. Avoid list-stacking when a paragraph should carry the thought. The prose should feel like a serious Orthodox teacher speaking with warmth and depth, not like a checklist filled in or an AI devotion assembled from stock phrases.
- **No shallow filler:** remove any paragraph that could fit almost any Bible passage after swapping the reference.
- **Read-aloud readiness:** the lead, one major explanation, and Spiritual Fruit pass the voice standard’s read-aloud test.

If a guide passes verifiers but fails this depth contract, report it as structurally clean but not publication-ready, then revise the weak sections before promotion.

## Current structure contract

Use `references/bible-study-guide-structure-standard.md` as the exact section-title source of truth. In short:

- frontmatter first when present;
- no duplicate body `# Title`;
- no `## Navigation` in guide bodies;
- no same-page a Markdown knowledge base wikilinks in guide bodies;
- lead paragraph immediately after frontmatter;
- interleaved H2 passage sections with the Scripture reference in each H2;
- Scripture blockquote directly under each passage heading;
- explanation tied directly to the quoted text before the next H2;
- language, LXX, Greek, Hebrew, or translation-shape notes under the specific passage they explain, following `references/lexical-and-translation-depth-standard.md` when the note materially affects interpretation;
- `### Gems from the Fathers 💎` only when direct or honest thematic patristic material genuinely illuminates that passage; if a guide has more than one Gems subsection, use the exact form `### Gems from the Fathers 💎: <specific subtitle>` and give every subtitle a unique, reader-meaningful description;
- `### The Church's Voice in Worship ⛪` for a substantive verified Coptic or Orthodox worship connection, placed under the passage it illuminates unless that connection governs the whole guide;
- exactly one `## Spiritual Fruit 🍇`;
- exactly one `## Teaching Notes`, immediately after `## Spiritual Fruit 🍇`;
- `## Sources and Further Reading` or source notes last when present.

Do not use legacy `## Teaching guide`, `Teaching Notes / Discussion Questions`, grouped end-of-guide language appendices, or front-loaded Scripture dumps in new or structurally migrated guides.

## Source honesty contract

### Scripture

Never type Scripture from memory.

- Approved NT guides that use NKJV: reader-facing source wording is exactly `Scripture is from NKJV.`
- Approved NT guide Scripture blocks should include visible verse numbers, preferably inline `<sup>` markers inside blockquoted paragraphs.
- In NT Scripture blocks, clear words spoken by Jesus should be wrapped with semantic markup only: `<span class="words-of-christ">...</span>`. Do not use raw inline color/style markup; the site CSS controls the red presentation.
- OT / Septuagint projects using Brenton: reader-facing source wording is `Embedded Scripture is from Brenton's English Septuagint (LXX).`
- Do not expose permissions, local paths, scripts, databases, extractors, checksums, or tooling in reader-facing guide prose.
- If source numbering is irregular, explain the source shape clearly without inventing missing verses.
- For reader-facing Psalm citations, retain the project’s MT-primary reference and add the Septuagint number whenever it differs. Use `Psalm 68:5 (LXX Ps 67:6)` as the exact form. Research must verify the mapping; do not guess from a blanket offset rule.

### Patristic material

Use `references/patristic-sourcing-policy.md` for source hierarchy.

- Prefer direct primary-source text when quoting.
- Use Catena-style tools and Coptic Treasures as discovery aids unless the exact primary source is verified.
- Distinguish direct commentary, close parallel, thematic witness, and absence.
- Do not use a Father as a passing reference or decorative name-drop. Each patristic item must give the reader an actual insight into the passage.
- For each substantive patristic witness, first summarize or paraphrase the Father's point in plain reader-facing language, then include a direct quote when the verified wording is eloquent, significant, or spiritually useful.
- Never write `the Fathers say` unless a real consensus is supported.
- Omit the Fathers subsection entirely when no genuine direct or thematic content helps the passage.
- When direct quotes are used, add semantic Markdown footnotes with short citation and direct/deep URL.

### Coptic lectionary and worship

Use `coptic-lectionary-reference` for verified readings.

- Do not infer a reading from thematic similarity.
- If exact local evidence is absent, say so plainly.
- Distinguish verified lectionary use from broader liturgical resonance.
- Explain the spiritual logic of a verified placement; do not merely list it.
- Use `### The Church's Voice in Worship ⛪` for a substantive verified worship connection so the table of contents and reader can find it. Place it under the passage it illuminates, unless the connection governs the whole guide and therefore belongs after the lead.
- In reader prose, state the verified worship fact and its spiritual significance first. Keep authority limits, provenance detail, and what the evidence does not establish in Teaching Notes, Sources, or a compact source note unless a short in-line qualification is essential to prevent a false claim.
- Where the passage has a commemorated saint, feast, or liturgical day in the Orthodox calendar, name it in the guide and let it shape the study. This is what joins Bible study to the Church's worship rather than leaving the passage merely patristic. Prefer the verified Coptic name and placement first. When the wider Orthodox Church keeps a received title or saint for the same Gospel, such as St. Photini and the Sunday of the Samaritan Woman, name that tradition too and label it as later remembrance when the Gospel itself does not record the name. Do not invent a feast, saint, Synaxarium date, or martyrdom vita. Do not let later hagiography override the biblical text.

### Visuals and iconography

Use `references/orthodox-visual-assets.md` and `orthodox-iconography` when visual theology matters.

- Visuals teach; they do not decorate.
- Label authority precisely: Orthodox icon, manuscript, map, museum object, Western biblical art, or generated schematic.
- Put source captions in compact attribution form.
- Keep teaching-use directions in `## Teaching Notes`, not captions or body prose.

## Depth bar

A publication-quality guide should include:

- passage-specific exegesis, not generic Christian encouragement;
- high-value language or translation-shape insight when the wording materially affects the passage, using `references/lexical-and-translation-depth-standard.md`; for NT guides this means actively checking original Greek or Aramaic terms where they carry theological force, while rejecting generic English-phrase explanations and vocabulary trivia;
- the historical and literary movement of the text;
- Christological, ecclesial, sacramental, ascetical, and pastoral significance where the text warrants it;
- hard-text guardrails placed in the body near the risky verse, not hidden only in teacher notes;
- concrete spiritual fruit for prayer, repentance, humility, worship, family life, service, watchfulness, or life in Christ;
- teacher notes that help a servant lead discussion without turning the guide body into a lesson-plan worksheet.

## Historical references rule

Historical precedent files record what happened in a prior pass. They do not override this contract. When a historical file says to use an older heading or structure, preserve only the durable insight from that file and apply the current structure contract.

## Verification before reporting done

Before saying a guide or batch is complete:

1. Run `verify_session_guide.py` on true session guides when filesystem access allows.
2. Run `verify_bible_study_quality_contract.py` for contract-level hygiene checks.
3. Re-read the saved file or exact changed section from disk.
4. Run scoped scans for marker classes only on active guide files, not broad folders that include audit notes quoting the markers.
5. Verify index/resources/audit/status bookkeeping only after guide verification passes and only when bookkeeping is in scope.
6. Mark downstream Docs, PDFs, slides, or MP3s stale whenever Markdown source materially changes.
7. Orthodox feast, saint, or liturgical day named where one exists. If none is verified, do not invent one.

Do not claim publication-ready status if Markdown changed but downstream artifacts were not refreshed, unless the report explicitly says Markdown is ready and downstream artifacts are stale or pending.
