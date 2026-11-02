# Contributing to Coptic Orthodox Ministry Toolkit

Thank you for wanting to contribute. This repo exists to serve the Church, and quality matters more than quantity. Please read this before submitting anything.

Canonical authored files live in the top-level skill directories and `shared-references/`. Generated files under `.agents/skills/`, `skills/`, the tool-specific alias roots, and each package's `references/shared/` directory must never be edited directly.

Do not add `skills-lock.json`. This repository generates its local package mirrors from `skill-packages.json`; treating those same mirrors as independently installed dependencies creates conflicting ownership and stale hashes.

---

## Theological Standards (Required)

All content must meet these standards. This is not negotiable, and submissions that do not meet them will not be merged.

### What this repo is
- **Fully Orthodox.** Aligned with the Coptic Orthodox Church, the Church Fathers, and ancient Orthodox Christianity.
- **Christ-centered.** Every lesson, skill, or prompt should ultimately point to Christ: His person, His work, His Cross, His Resurrection, His Church.
- **Biblically grounded.** Scripture is primary. Use it richly, accurately, and in context.
- **Patristically informed.** The Fathers are a guide and a standard, not decoration. Only use real, verifiable patristic quotes. If you are not sure, paraphrase the theme and note it is consistent with the Fathers - do not invent or approximate attributions.
- **Sacramental and ecclesial.** Content should connect to the life of the Church: liturgy, feasts, fasts, prayer, and the sacraments where relevant.

### What this repo is not
- Generic "Christian" content that could belong to any denomination
- Motivational or therapeutic content dressed up with scripture
- Content that flattens or softens Orthodox doctrine for broader appeal
- Academic content that informs without moving the heart

### Core themes that should run through lesson content
Repentance, humility, salvation in Christ, healing of the soul, holiness, watchfulness, prayer, ascetic struggle, divine mercy, the Cross, the Resurrection, and life in the Church.

---

## Content Quality Bar

Ask yourself these questions before submitting:

1. Is this spiritually deep and theologically faithful?
2. Does it move the heart toward repentance, faith, prayer, or transformation - not just inform?
3. Is it grounded in Scripture and the Orthodox tradition, not just general spirituality?
4. Would a Coptic Orthodox priest recognize this as consistent with the Church's teaching?
5. Is it practically useful for an adult meeting or Bible study?

If you answered no to any of these, revise before submitting.

---

## File Format

All content must be in **Markdown (`.md`)** format. This ensures it renders cleanly on GitHub, is searchable, and is usable by AI tools.

## Generated Skill Packaging

After changing any canonical skill file or shared reference, run:

```bash
python3 scripts/sync_skill_packages.py --write
python3 scripts/sync_skill_packages.py --check
```

When adding or removing a file that must ship in a minimal agent package, update `skill-packages.json` in the same change. Shared material must be authored once in `shared-references/` and declared under the consuming package's `shared_imports`; never create or edit a package copy by hand.

`--write` regenerates standalone shared bundles in `references/shared/`, rebuilds `.agents/skills/*`, and repairs the alias symlinks under `skills/`, `.claude/skills/`, `.codebuddy/skills/`, `.cortex/skills/`, `.factory/skills/`, `.kilocode/skills/`, `.mcpjam/skills/`, `.mux/skills/`, `.openhands/skills/`, `.qwen/skills/`, `.vibe/skills/`, and `.zencoder/skills/`.

`--check` verifies that generated files and symlinks are clean and deterministic without making changes.

For standalone installs, target the top-level subdirectory you want:

```bash
npx skills add github.com/george-andraws/coptic-orthodox-ministry/spiritual-lessons
npx skills add github.com/george-andraws/coptic-orthodox-ministry/orthodox-biblical-explanation
npx skills add github.com/george-andraws/coptic-orthodox-ministry/orthodox-iconography
npx skills add github.com/george-andraws/coptic-orthodox-ministry/orthodox-apocalyptic-exegesis
npx skills add github.com/george-andraws/coptic-orthodox-ministry/orthodox-shared-references
npx skills add github.com/george-andraws/coptic-orthodox-ministry/exact-scripture-speaker-ledger
npx skills add github.com/george-andraws/coptic-orthodox-ministry/outreach
```

### Lesson file structure

Every lesson file should follow this order:

```markdown
# Lesson Title

**Series:** [Series name or "Standalone"]
**Occasion:** [Lent / Apostles Fast / General / etc.]
**Prepared by:** [Name or "Anonymous"]
**Scripture focus:** [Primary passage(s)]

---

## Main Burden
[One short paragraph: what is the core spiritual claim of this lesson?]

## Opening Hook
[How does the lesson begin? A question, an image, a story?]

## Scriptural and Historical Context
...

## Main Teaching Sections
...

## Orthodox Theological Significance
...

## Patristic Insight
...

## Old Testament Links / Typology
...

## Liturgical / Sacramental Connection
...

## Personal Spiritual Contemplation
...

## Practical Application
...

## Conclusion
...

## Discussion Questions
1.
2.
3.

## Closing Prayer (optional)
...
```

### Series overview file structure

Every series needs an `overview.md` with:

```markdown
# Series Title

## Central Spiritual Question
[What governing question or burden holds the series together?]

## Spiritual Need It Addresses
[Why does this series matter? What problem or hunger does it meet?]

## Audience, Timing, and Constraints
[Audience, teaching length, available dates, and liturgical/calendar constraints. Mark unverified details as proposed.]

## Sessions
| # | Title | Scripture / source | Distinct burden | Date | Status |
|---|-------|--------------------|-----------------|------|--------|
| 1 | ...   | ...                | ...             | ...  | Proposed / Confirmed / Done |

## Why This Series Matters
[A short paragraph on the spiritual urgency or opportunity of this series]
```

---

## File Naming

Use lowercase with hyphens. No spaces. No special characters.

```
encounters-with-christ/
├── overview.md
├── 01-treasure-reorients-heart.md
├── 02-healing-38-years.md
```

Number lesson files with two digits so they sort correctly.

---

## How to Submit Content

### If you know GitHub (technical)

1. Fork this repo
2. Add your content following the format above
3. Submit a pull request with a short description of what you added
4. A maintainer will review for theological and format standards before merging

### If you do not know GitHub (non-technical)

1. Prepare your content as a plain text or Word document following the lesson structure above
2. Email it to [<georgeandraws@gmail.com>] with the subject: `Contribution: [Lesson or Series Title]`
3. A maintainer will format and upload it on your behalf, with your name credited

---

## What Gets Rejected

- Content that does not follow the theological standards above
- Lessons that are clearly AI-generated without human theological review
- Invented or unverifiable patristic quotes
- Files not in Markdown format
- Lessons without a clear Orthodox theological anchor
- Generic motivational content

---

## Questions

Open a GitHub issue labeled `question` or email the maintainer directly.
