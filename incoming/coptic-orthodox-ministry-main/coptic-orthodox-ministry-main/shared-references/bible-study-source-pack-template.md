# Bible Study Source Pack Template

Use this template before drafting or substantially revising an Orthodox Bible-study guide. The source pack is a compact bridge between research and guide prose. It keeps source work explicit without leaking tool/process language into the reader-facing guide.

## When required

Create or update a source pack when:

- drafting a new session guide;
- revising a weak or no-Fathers guide;
- continuing a book project after a gap;
- handling hard texts, deuterocanonical material, divergent LXX/NKJV numbering, or complex lectionary claims;
- using delegates or external reviewers;
- the lesson author's asks for publication-ready or final-quality work.

For a tiny passage explanation that will not be saved as a guide, a mental/brief source pack is enough.

## Storage

Prefer the book's existing source-pack convention. Common patterns:

- `Source Packs/<Session>.md`
- `Resources Used - <Book>.md` with a session subsection
- `Audits/<dated source pack or continuation note>.md` for audit-only work

The source pack may mention local tools, searches, and evidence limitations. The final guide should not.

## Compact source pack schema

```yaml
session:
book:
passage:
canonical_guide_path:
status_row_before:
source_text:
  translation: NKJV | Brenton LXX | other
  extraction_method: local verified source | manually provided by the lesson author's | other
  verse_coverage:
  numbering_notes:
  reader_facing_psalm_citations:
    - mt_form:
      lxx_form:
      verified_source:
      use_in_guide:
movement_divisions:
  - heading:
    reference:
    reason_for_boundary:
scriptural_cross_references:
  - reference:
    reason:
patristic:
  direct:
    - father:
      work:
      passage_or_section:
      quote_status: quote_eligible | paraphrase_only | needs_verification
      citation_url:
      guide_use:
  thematic:
    - father:
      work:
      theme:
      honesty_label:
      guide_use:
  absence_wording:
lectionary:
  exact_hits:
    - service_or_day:
      passage:
      confidence: confirmed | likely | uncertain
      reader_facing_body_claim:
      source_note_limits:
      guide_section: The Church's Voice in Worship ⛪ | inline only
      spiritual_logic:
  no_exact_hit_wording:
language_or_lxx_notes:
  - term_or_phrase:
    original_term:
    lexical_or_translation_observation:
    why_it_matters_for_this_passage:
    overclaim_guardrail:
    source_urls:
licensed_library:
  available: true | false
  platform: Peter-authorized private corpus | Accordance Cloud guest | Accordance Cloud authenticated | Accordance app | other
  resource:
  module_or_slug:
  entry_page_or_section:
  printed_page_alias:
  chunk_or_locator:
  evidence_role:
  exact_quote_status: verified | paraphrase_only | lead_only
  rights_or_distribution_note:
  guide_use:
visuals:
  - type: icon | map | manuscript | museum object | Western art | generated schematic
    source_or_path:
    teaching_purpose:
    rights_or_caution:
hard_text_guardrails:
  - verse_or_line:
    risk:
    body_guardrail:
teaching_burden:
spiritual_fruit:
open_questions:
downstream_artifacts_before:
```

## Minimum viable source pack

When context is tight, capture only:

1. target guide path and passage;
2. verified Scripture source and verse coverage;
3. passage divisions with reasons;
4. strongest patristic direct/thematic anchors or honest absence;
5. verified lectionary hits or honest absence;
6. hard-text guardrails;
7. visual/source notes if relevant;
8. downstream artifact status.

## Quality rules

- The source pack should be terse and evidence-rich.
- Do not paste massive raw source output. Summarize, then fetch exact selected source text only when needed.
- Treat language or translation-shape observations under `language_or_lxx_notes` as weight-bearing evidence, not filler. Apply `references/lexical-and-translation-depth-standard.md`: record the original term or source-shape issue, why it matters for this exact passage, the overclaim guardrail, and source URLs when online lexical/interlinear sources are used. For NT source packs, actively check for meaningful Greek or Aramaic terms when the text warrants it, such as Logos / “Word,” Paraklētos / “Helper” or “Comforter,” Greek love-word distinctions, repeated roots, or theological terms hidden by a flat English rendering. Do not record generic English-phrase explanations as language notes; put those in normal exegesis only if they are useful.
- If a source was searched and found nothing useful, record the scoped absence once.
- Treat Coptic Treasures and index tools as leads unless the direct source has been verified.
- If a delegate produces the source pack, the parent agent must verify the selected facts before guide prose is written.

## Handoff to drafting

Before drafting, convert the source pack into a short guide plan:

- H2 passage headings;
- one burden sentence per passage;
- exact patristic placement decisions;
- exact lectionary/source wording;
- hard-text guardrail locations;
- Teaching Notes labels matching body passage sections.

After drafting, verify that every planned anchor actually landed in the saved guide. Do not report intended anchors as if they were written.
