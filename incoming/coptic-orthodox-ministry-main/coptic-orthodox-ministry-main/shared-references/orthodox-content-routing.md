# Orthodox Content Routing

## Routing table

- **Full biblical book Bible study**: primary `bible-study-orchestration` (or the `biblestudy` bundle shortcut when the lesson author's is explicitly using skill bundles). Use for prompts such as "create a Bible study on Amos", "build a full study guide for Ecclesiastes", or "redo the Leviticus study".
- **Single Bible-study session or chapter guide**: primary `orthodox-a Markdown knowledge base-study-guide-drafting`, with `orthodox-biblical-explanation`, `coptic-lectionary-reference`, `a Markdown knowledge base`, and visual/iconography support as needed.
- **Orthodox lesson, adult meeting lesson, feast/fast lesson, saint lesson, article, or reflection**: primary `coptic-orthodox-spiritual-lessons` (or the `orthodox-lesson` bundle shortcut when the lesson author's is explicitly using skill bundles).
- **Passage explanation only**: primary `orthodox-biblical-explanation`. It supports lessons and Bible studies but does not take over full artifacts unless the lesson author's asks only for explanation.
- **Lectionary question only**: primary `coptic-lectionary-reference`.
- **Icon/image interpretation only**: primary `orthodox-iconography`.
- **Vault mechanics only**: primary `a Markdown knowledge base`.

## Ambiguity rules

- If the lesson author's says "create a Bible study" and names a whole biblical book, route to `bible-study-orchestration`.
- If the lesson author's says "create a lesson", route to `coptic-orthodox-spiritual-lessons`.
- If a prompt includes both "lesson" and a biblical passage, the lesson skill owns the deliverable and passage explanation supports it.
- If a prompt includes a whole biblical book, Bible-study orchestration owns the deliverable even if the wording includes "lesson".
- Supporting skills supply facts and method. They do not decide the final artifact shape.

## End-user examples

- "Create an Orthodox lesson on the Fast of the Apostles" -> `coptic-orthodox-spiritual-lessons`.
- "Draft the next Amos Bible-study session" -> `orthodox-a Markdown knowledge base-study-guide-drafting` inside the existing book project, after orchestration resolves the target session.
- "Create a full Orthodox Bible study on Amos" -> `bible-study-orchestration` Phase 1.
- "When is Amos 5 read in the Coptic lectionary?" -> lectionary skill.
