# Bible-study guide structure standard

Canonical standard for the lesson author's Orthodox Bible-study guide files. This reference governs new Bible studies, direct guide drafting, revision passes, final audits, and publication-readiness work unless the lesson author's explicitly asks for a narrow no-structure-change review.

## Exact section-title contract

When organizing or updating Bible-study content into these sections, use these exact titles word-for-word, including the emoji at the end of the line:

- `Geographical Significance ⛰️`
- `The Characters 👥`
- `Light from the Word 🕯️` for Old Testament or general scriptural insights
- `Light from the Gospel 🕯️` for New Testament / Gospel-specific insights
- `Iconography Connection 🖼️` or `Iconography and Visual Teaching 🖼️`
- `The Church's Voice in Worship ⛪`
- `Spiritual Fruit 🍇`
- `Gems from the Fathers 💎` for a single Gems subsection, or `Gems from the Fathers 💎: <specific subtitle>` for each of multiple Gems subsections

During audits and structural migrations, convert old or variant headings to this contract. In particular, always convert `In the Fathers`, `Patristic Depth`, and un-emojied `Gems from the Fathers` to `Gems from the Fathers 💎`. Convert `The Text Broken Open` to `Light from the Word 🕯️` for Old Testament or general Scripture work, and to `Light from the Gospel 🕯️` for Gospel-specific New Testament work.

### NT session-guide note

Most New Testament **session guides** do **not** wrap the whole body under one `## Light from the Gospel 🕯️` heading. The controlling form is interleaved H2 passage sections with the Scripture reference in each H2. Use the emoji-named sections above when they truly help as orientation or support sections, or when an older project form already depends on them. Do not force OT publication-batch sections such as separate “Orthodox Theological Reading,” “Christological Types and Prophecies,” or “Closing Meditation” onto NT session guides unless the lesson author's explicitly asks for that shape. Christology, worship, and spiritual application normally live inside the passage explanations and `## Spiritual Fruit 🍇`.

For Gospel narrative and other typological NT studies, follow `orthodox-a Markdown knowledge base-study-guide-drafting/references/nt-session-guide-closeout-and-crosslink-standard.md`:

- Give a real context paragraph before commentary: setting, people, place in the book, and Evangelist order when it matters.
- After the last passage explanation and before `## Spiritual Fruit 🍇`, add a compact table of Promise in Israel / the sign or event here / fulfillment in Christ when the passage can carry one. The table is not a heading.
- End Spiritual Fruit with a short scene-placed meditation, three to six sentences. Do not add a `## Closing Meditation` heading.
- When another published study is mentioned, make the reference text itself a site-absolute link. Do not print the path as visible prose.

## Reader-facing guide body

Guide/article files themselves should not contain `## Navigation` sections or a Markdown knowledge base navigation links. Keep navigation links in indexes, status notes, resources notes, and audit notes.

The body is for clean reader-facing study prose:
- begin after frontmatter with a lead/opening paragraph, not a duplicate H1 or navigation block;
- orient the reader briefly;
- move passage by passage through natural sense-units;
- for session guides verified by the standard `verify_session_guide.py` script, make each natural Scripture unit an H2 passage heading that includes the reference and contains the blockquoted Scripture; do not put all passage units as H3s under a wrapper H2 such as `## Light from the Gospel 🕯️` unless the verifier has been updated for that structure;
- interleave quoted Scripture with explanation and application;
- include relevant Greek, Hebrew, Aramaic, Septuagint, or translation-shape insights inside the specific passage section they illuminate, immediately under that Scripture unit's explanation; do not collect them into a standalone end-of-guide language-notes section;
- keep teaching directives out of the exposition;
- keep `## Sources and Further Reading` as the final H2 section at the very bottom of the guide when present;
- do not mention internal project resources, local tooling, private files, query helpers, audit roadmaps, or process notes in reader-facing source sections. If a cited source has a public URL, include the direct URL; if it has no public URL, cite it plainly without inventing one;
- when a guide has a distinct patristic subsection, title a single one `### Gems from the Fathers 💎` or `## Gems from the Fathers 💎` depending on the existing heading level. When more than one appears, use `### Gems from the Fathers 💎: <specific subtitle>` or the matching H2 form and give every subtitle a unique, reader-meaningful description. Do not let the section become a passing reference list. Preserve or add an explanatory summary/paraphrase of what the Father is teaching about the passage, then add verified direct quotations when the wording is eloquent, significant, or spiritually useful.
- when a substantive verified Coptic or Orthodox worship connection is present, place `### The Church's Voice in Worship ⛪` under the passage it illuminates. Put it after the lead only when the worship connection governs the whole guide; do not bury a full lectionary rationale in an unheaded introduction paragraph.
- when direct patristic quotations appear, attach a portable Markdown footnote to each quote. The footnote itself should contain the short citation and direct/deep URL. Do not make the footnote link to a separate source-list item as an intermediate hop. `## Sources and Further Reading` may still use numbered lists for readability, but quote footnotes should cite directly.

## Spiritual Fruit and Teaching Notes

Each new or substantially revised Bible-study guide should contain exactly one `## Spiritual Fruit 🍇` section followed by one `## Teaching Notes` section near the bottom, with `## Sources and Further Reading` remaining the final section when present.

`## Spiritual Fruit 🍇` is reader-facing spiritual application. It must grow from the verses already explained and give concrete benefit for prayer, repentance, worship, mercy, watchfulness, family life, service, or endurance in Christ. Generic uplift such as “be more faithful” fails unless sharpened by the passage’s actual claim. Prefer three to five substantial fruits over a long soft list. See `references/nt-publication-ready-spiritual-depth-and-engagement.md` for NT final-depth fruit standards and `references/spiritually-edifying-exegesis-voice-standard.md` for the controlling application voice. For NT Gospel and typological studies, end this section with a short scene-placed meditation. Do not add a separate meditation heading.

Use this shape:

```markdown
## Spiritual Fruit 🍇

<Reader-facing spiritual fruit and practical application.>

## Teaching Notes

### <Plain passage label>
- Ask: <discussion question>
- Emphasize: <teaching emphasis>
- Watch for: <common misunderstanding or pastoral caution>
- Connect: <link to Christ, the Church, repentance, worship, or the book's arc>
- Show: <how and when to use a specific icon, map, slide, or handout, when applicable>
```

Teaching-note passage labels must be plain text. Do not use a Markdown knowledge base backlinks or same-page heading links such as `[[#...]]` in guide-body teaching headings.

## Term, concept, language, and translation insights

Technical terms are part of teaching, not decoration. Define Greek, Hebrew, Aramaic, Syriac, Coptic, patristic, liturgical, iconographic, historical, and doctrinal terms when a faithful parish reader or servant may not know them. Define the term briefly, then explain why it matters for this passage or practice.

Language insights are part of passage exegesis, not an appendix. For every natural sense-unit, ask whether a Greek, Hebrew, Aramaic, Septuagint, or translation-shape detail materially deepens the reader's understanding. Use `references/lexical-and-translation-depth-standard.md` as the quality bar.

A good language note should reveal something the English reader would likely miss: movement or escalation in the passage, distinct original words hidden behind one English word, source-shape or numbering differences that affect interpretation, wording that supports patristic or liturgical reading, or a guardrail against a shallow or false reading. For New Testament guides, actively look for meaningful Greek or Aramaic depth when the text warrants it: Logos behind “Word,” Paraklētos behind “Helper” or “Comforter,” distinct Greek love words hidden behind English “love,” or other repeated/theological terms that shape interpretation. The John 6 eating-verbs note is the model: it distinguishes φάγω / ἐσθίω from τρώγω, explains the movement of the Bread of Life discourse, strengthens Eucharistic realism, and avoids overclaiming.

When a language note meets that bar, add it directly under the passage section it explains, usually as a short paragraph or a `### Language insight` subsection. The note must define the term plainly and then explain why the wording matters for meaning, worship, patristic use, doctrine, or spiritual application. Do not use `### Language insight` for generic English-phrase explanation. For example, “He who sent me places John's ministry under divine command” may be a valid exegetical sentence, but it is not a linguistic insight unless the original wording, term choice, or translation shape itself deepens the reading. Omit the subsection when there is no meaningful insight; do not pad with lexicon trivia or isolated word definitions.

Do not create a single late `## Greek and Linguistic Notes`, `## Hebrew Notes`, or equivalent catch-all section in new or substantially revised guides. If an older guide has such a section during a revision or audit, migrate each useful note into the passage section it explains, then remove the grouped end section.

## Consolidation rule

When a legacy guide has multiple `Lesson guide`, `Teacher's Notes`, or `## Teaching guide` fragments, do not merely move the fragments to the bottom unchanged.

Rework them into a coherent teaching guide for the whole study:
- name the study's main teaching burden when helpful;
- organize helps passage by passage with plain labels;
- combine duplicate or overlapping prompts;
- keep teacher actions practical and specific;
- make the teaching notes serve the whole guide rather than preserve the old fragment layout.

## Legacy structures

Legacy `## Teaching guide` sections and Obadiah-style linked teaching headings are historical patterns only. They solved local transition problems but are too noisy and fragile for future audits.

During future revisions or audits, migrate them to the canonical `## Spiritual Fruit 🍇` followed by `## Teaching Notes` structure unless the lesson author's explicitly scopes the work as no-structure-change.

## Mechanical verification

When filesystem access is available, run the guide verifier after creating or substantially revising a session guide:

```bash
python3 <local path> <path-to-note.md>
```

The verifier is the backstop for this standard: it rejects legacy `## Teaching guide` structure, same-page teaching links, navigation sections, duplicate-title starts, and missing bottom `## Spiritual Fruit 🍇` followed by `## Teaching Notes` structure.
