# Coptic Lessons Publication Schema

Use this for durable notes under `Hermes/04-Reference/Coptic Orthodox Lessons/`, especially `References/Biblical Explanations/`.

## Core frontmatter

Preserve existing good fields. Add or normalize grounded values only.

Common fields:
- `title`
- `type`: `lesson`, `chapter-study`, `overview`, `audit`, `moc`
- `publish`
- `book`
- `passages`
- `audience`
- `summary`
- `date`
- `updated`
- `tags`
- `fathers`
- `lectionary`
- `season`
- `feast`
- `hero`
- `audio`
- `deck`

Do not invent passages, Fathers, feast placement, lectionary placement, Psalm numbering, or publication status. Omit uncertain values and report them.

## Bible-study notes

- Use `type: chapter-study` for session guides.
- Use `Overview`, never `General Introduction`.
- Body normally begins after frontmatter with the reader-facing opening paragraph or first overview/passage section. Do not add `## Navigation` to guide bodies.
- For chapter-study / Bible-study guides, consolidate teacher-facing material at the bottom under `## Spiritual Fruit` / `### Teaching Notes`, using plain passage labels with no a Markdown knowledge base heading links.
- Keep Biblical Explanations path language broad enough for Pentateuch, Historical Books, Psalms, Wisdom, Prophets, Maccabees, Gospels, and New Testament projects.
- Schema compliance is required before a note is considered complete.

## Lesson notes

- Use `type: lesson` for adult-meeting, feast, fast, saint, and spiritual teaching notes.
- Derive `passages` from the body, not the title, unless the body confirms the governing passage.
- Add a one-sentence `summary` suitable for cards and search.

## Synaxarium commemoration

When a lesson or overview covers a saint, prophet, biblical author, martyr, or feast with a likely Coptic commemoration:
- Include a `Synaxarium / commemoration` section in the reader-facing note, or an Overview-level section for book authors and prophets.
- Route lookups through `coptic-lectionary-reference` or the current local Synaxarium source path if available.
- Use confidence labels: confirmed, likely, uncertain, absent evidence.
- If no source is confirmed, say plainly: "No confirmed Synaxarium commemoration was found in the checked sources." Do not guess dates.

## Formatting rules

- Use three spaces for nested bullets under numbered lists when preserving Markdown list hierarchy.
- Translation/source notes must name the text source without machinery: `Embedded Scripture is from Brenton's English Septuagint (LXX).`
- Do not mention scripts, commands, helpers, local file paths, extraction tooling, file sizes, model names, TODOs, placeholders, citation-needed markers, or TTS tags in reader-facing notes.
- Embedded icon, map, or manuscript image source captions use exactly: `<small><em>Source: [name or work], [license or URL]</em></small>`.
