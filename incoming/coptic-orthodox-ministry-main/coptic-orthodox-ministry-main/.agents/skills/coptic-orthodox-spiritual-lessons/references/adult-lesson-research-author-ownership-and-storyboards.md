# Adult-Lesson Research, Author Ownership, and Visual Storyboards

Use this reference for Coptic Orthodox adult-meeting lesson research, deep dives, outline review, and the handoff from settled teaching notes to a visual deck plan.

Adapted on 2026-08-09 from reusable workflow ideas in `pmansour/general-meeting-prep`, commit `aaa2b1305f1d36f4b64e367db877159588baca0c`. The wording and workflow here are the lesson author's-specific and do not import Peter-specific tools, paths, accounts, feeds, or content.

## Protect the lesson author's ownership of the lesson arc

Research should expand and clarify the lesson author's choices before it narrows them. Unless the lesson author's asks the assistant to choose the final arc:

- map the strongest plausible burdens and tensions;
- show which sources support each direction;
- identify what is firm, traditional, contested, or thinly sourced;
- recommend the few strongest directions and explain why;
- do not quietly turn the research packet into a finished outline;
- after the lesson author's settles the burden, review and strengthen his notes rather than replacing his voice by default.

Asymmetry is healthy. One spiritually weighty direction may deserve far more space than several secondary ideas.

## Initial research handoff

For a substantial new lesson, capture:

1. **Preparation brief:** topic/passage/person, series placement, date, teaching time, audience, pastoral need, primary Scripture, constraints, and the lesson author's existing instincts.
2. **Local continuity:** applicable a Markdown knowledge base series overview, schedule, neighboring lessons, prior research, confirmed vocabulary, and boundaries that prevent duplication.
3. **Research map:** Scripture, primary/near-primary witnesses, Fathers, Coptic/liturgical reception, modern scholarship, historical setting, and meaningful visual evidence.
4. **Interpretive cautions:** disputed history, later tradition, translation issues, common pastoral distortions, and missing evidence.
5. **Direction menu:** normally 5-10 genuinely distinct teaching directions, with the strongest few clearly weighted.
6. **Selected source packet:** a moderate set that earns its place rather than an exhaustive bibliography.

For each candidate direction, use:

```text
Direction / possible story beat:
Why it matters:
Doctrine or spiritual question:
Adult-life pressure point:
Best source anchors:
Source strength and caution:
What this direction would leave for another lesson:
```

Do not inflate the menu with generic virtues or several rephrasings of the same burden.

## Durable research packet

When the lesson author's asks to start, begin, or kick off lesson preparation rather than merely brainstorm in chat, save the research phase as a durable a Markdown knowledge base artifact unless he explicitly asks not to save it. Use the existing series or standalone lesson folder and a descriptive filename ending in `-initial-research.md` or `-research-packet.md`.

The packet should contain:

- preparation brief and series/date context;
- local continuity findings and confirmed boundaries;
- weighted direction menu;
- selected source packet with evidence roles and cautions;
- Accordance/licensed-library coverage status when relevant;
- interpretive tensions, unresolved questions, and material gaps;
- the lesson author's chosen or still-open direction status;
- clear next decision rather than a silently completed outline.

If NotebookLM is actually used, record the notebook title/link, intended source scope, uploaded source scope, and failed or excluded uploads. If preparation audio is generated, record its role and link separately. Neither NotebookLM nor audio is required for a normal research packet.

Preserve existing research artifacts and update them carefully rather than creating parallel competing packets for the same lesson.

## Series proposal discipline

For a new or revised adult-meeting series, state explicitly:

```text
Series title:
Central spiritual question:
Pastoral need:
Audience and teaching time:
Available dates / session count:
Liturgical and calendar constraints:
Primary Scripture, patristic, historical, or doctrinal anchors:
Status: proposed | partially confirmed | confirmed

Session | Working title | Primary Scripture/source | Distinct burden | Date | Status
```

Mark each claim, date, title, and session placement as confirmed or proposed. Do not infer a complete schedule from one topic. Each session must earn a distinct place while advancing the central spiritual question; avoid repetitive burdens disguised by different titles.

## Optional source-grounded preparation audio

When the lesson author's explicitly asks for NotebookLM Audio Overview or another preparer-immersion audio, frame it as research support rather than a finished sermon or lesson outline. Use only the selected sources and ask the audio to distinguish direct source claims, firm history, later tradition, contested claims, named interpretation, and reasonable synthesis. Keep the Coptic Orthodox frame governing, avoid fake banter, fake personal experiences, melodrama, forced disagreement, and exaggerated hooks, and end with the strongest tensions, teaching possibilities, cautions, and questions the lesson author's should sit with. Do not let the audio choose the final lesson arc unless the lesson author's asks it to.

## Deep-dive handoff

When the lesson author's selects one or more threads, produce focused packets with:

- controlling Scripture and primary witnesses;
- verified quotations or tightly attributed paraphrases;
- theological and spiritual stakes;
- concrete adult-life applications;
- possible personal-story openings without inventing the lesson author's experience;
- questions the lesson author's should sit with before outlining;
- evidence cautions and genuine disagreements;
- optional NotebookLM or audio-preparation prompt only when requested.

## Review the lesson author's notes instead of rewriting by default

Evaluate:

- Does one spiritual burden carry the lesson?
- Does the sequence form a story or argument rather than a chronology dump?
- Do history, doctrine, Scripture, and adult spiritual life reinforce one another?
- Is Christ and life in the Church central rather than appended?
- Does the material fit the real teaching time?
- Are claims verified or honestly qualified?
- Are applications passage-specific and recognizable?
- Are personal-story openings natural and optional?
- Has generic AI cadence, slogan stacking, melodrama, and over-polish been removed?

Return review feedback in this order:

```text
Strongest part:
Main risk:
Source or accuracy issues:
Pacing issues:
Suggested tightening:
Possible personal-story slots:
Recommended final burden:
```

Rewrite only when the lesson author's asks, or when a bounded correction is needed to demonstrate the fix.

## Visual-first storyboard contract

Build a storyboard only after the lesson burden and notes are settled, unless the lesson author's explicitly asks for exploratory visual concepts earlier.

Choose one coherent visual language for the whole deck: Coptic/Orthodox iconography, manuscript, architectural, cartographic, archaeological, symbolic, or another justified system. Treat the lesson notes as speaker notes, not on-slide copy.

For each slide, record:

```text
Slide title:
Lesson section:
Purpose in the live lesson:
One dominant visual:
Minimal on-slide text:
Verified quote or caption, if needed:
Relationship to speaker notes:
Source, creator, tradition, and rights status:
Production note:
Image prompt seed, only if generation is appropriate:
Do-not-show guardrail:
```

The `Do-not-show guardrail` is mandatory whenever a visual metaphor, generated image, diagram, reconstruction, or icon-like composition could imply false doctrine, false history, disrespectful iconography, or archaeological certainty the evidence does not support.

## Storyboard review gate

Before generating a deck, confirm:

- the sequence follows the settled lesson arc;
- every slide has one primary visual idea;
- slides accompany rather than summarize the speaker notes;
- on-slide text is minimal and readable;
- quotations are exact and verified;
- the deck uses one coherent visual system;
- iconographic language is reverent and not generated as if canonical when it is not;
- maps, charts, and reconstructions are sourced and qualified;
- rights status is recorded;
- the production agent has enough detail without being forced into one rendering technique;
- every doctrinally risky visual has a clear do-not-show guardrail.

If the lesson author's asks for the actual PowerPoint, pass the approved storyboard to the relevant PowerPoint skill as the production specification and verify the rendered deck, not just the source file.