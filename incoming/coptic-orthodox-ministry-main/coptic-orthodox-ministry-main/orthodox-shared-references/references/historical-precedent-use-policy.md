# Historical Precedent Use Policy

the lesson author's Bible-study skills contain many dated reference files from prior book projects. They are valuable, but they are not all current standards.

Use this policy whenever a task loads an older book/session precedent, audit pattern, rewrite pattern, or publication-refresh pattern.

## Rule

Historical precedent files answer: what worked or failed in a prior project?

Shared standards answer: what should be done now?

When they conflict, current shared standards win unless the lesson author's explicitly asks to preserve the older structure for that book.

## Current shared standards to prefer

Load these through `orthodox-shared-references` as needed:

- `references/bible-study-quality-contract.md`
- `references/bible-study-guide-structure-standard.md`
- `references/bible-study-source-pack-template.md`
- `references/orthodox-depth-bar.md`
- `references/patristic-sourcing-policy.md`
- `references/patristic-quote-gems-and-direct-footnotes.md`
- `references/orthodox-visual-assets.md`
- `references/article-vs-audit-boundary.md`

## How to use a historical precedent

Extract only the durable pattern:

- book-specific source pitfalls;
- known lectionary findings;
- known patristic anchors and their source-status limits;
- verified grouping/split rationale;
- hard-text guardrails;
- visual/source/license lessons;
- verifier or bookkeeping pitfalls;
- user corrections.

Do not inherit stale forms such as:

- `## Navigation` in guide bodies;
- duplicate body H1s;
- front-loaded full Scripture sections without interleaved explanation;
- `## Teaching guide` for newly migrated Bible-study guides;
- `Teaching Notes / Discussion Questions` as a section title;
- nested `### Teaching Notes` under `## Spiritual Fruit 🍇` when the active verifier expects `## Teaching Notes` as H2;
- grouped end-of-guide language-note appendices when the note belongs under a passage;
- reader-facing local/tooling/source-process wording.

## Banner for old references

When editing an old reference because it is still useful but structurally stale, add this near the top:

```markdown
> Historical precedent. Current Bible-study structure and quality rules are governed by `orthodox-shared-references/references/bible-study-quality-contract.md` and `orthodox-shared-references/references/bible-study-guide-structure-standard.md`. Preserve this file's durable source/workflow lessons, but do not copy older headings or structure when they conflict with the current standard.
```

Do not mass-edit every old reference only to add the banner. Add it opportunistically when a file is already touched or when the stale pattern caused a real mistake.
