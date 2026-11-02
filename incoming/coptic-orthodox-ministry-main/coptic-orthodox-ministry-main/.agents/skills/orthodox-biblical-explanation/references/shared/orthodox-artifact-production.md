# Orthodox Artifact Production

Artifact production follows approved content unless the lesson author's explicitly asks earlier.

## Boundaries

- Do not create audio, slides, PDFs, a document editor, or publication packages from a weak draft unless the user explicitly wants a draft asset.
- Keep a Markdown knowledge base Markdown as source of truth unless a workflow says otherwise.
- Mark downstream artifacts stale when source Markdown changes.

## Bible-study audio

- the lesson author's prefers NKJV Scripture text for Bible-study TTS audio when that is the approved source policy for the active project.
- Before generating, search the target book/lesson set for missing audio by source-note stem and generate only missing items unless the lesson author's asks for full regeneration.
- Preserve narration-only masters.
- Unless the lesson author's specifies otherwise, use the Psalms music bed, currently `psalms-bed-01-still-waters-d-minor.mp3` / `Still Waters, D minor` when available.
- Start music 2.0 seconds before narration.
- Add real 1.0-second pauses before `#`, `##`, and `###` headings and real 0.5-second pauses before paragraphs/Scripture blocks; collapse repeated blank lines or line breaks to one paragraph pause.
- Exclude embedded-image descriptions, captions, source lines, visual-only sections, and teaching-only visual instructions from TTS staging.
- Do not append outro/background music as a separate track.
- For default Psalms pre-roll runs, final MP3 duration should be narration-only duration plus about 2.0 seconds; verify hash difference as proof the bed was mixed.
