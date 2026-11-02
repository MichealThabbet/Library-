# Coptic Orthodox Ministry - AI Content Toolkit

A shared, open-source repository of AI skills, system prompts, and prepared content for Coptic Orthodox servants and ministers. Built for community reuse, not just one church.

---

## What This Is

This repo contains:
- **AI skills** that teach compatible AI assistants how to prepare deeply Orthodox spiritual content
- **System prompts** you can paste into Claude, ChatGPT, Gemini, or any LLM
- **Prepared lessons, series, and study materials** ready to use or adapt
- **Style guides** for lesson structure, tone, source confidence, iconography, and ministry communication
- **Shared references** for patristic sourcing, Coptic Orthodox source discovery, licensed Bible-library research, and visual asset usage

Canonical authored sources live only in the four top-level skill directories and [`shared-references/`](shared-references/). Generated mirrors under [`.agents/skills/`](.agents/skills/) and the alias roots under `skills/` plus tool-specific directories are rebuilt from the manifest; do not edit those generated copies directly.

The repository intentionally does not use `skills-lock.json`: these local package mirrors are generated build artifacts, not independently installed dependencies. External users can still install any top-level skill through `npx skills add` as shown below.

Everything is grounded in ancient Orthodox Christianity, especially the Coptic Orthodox tradition: Christ-centered, biblical, patristic, sacramental, pastorally serious, visually source-aware, and written with spiritual depth rather than generic filler.

---

## Who This Is For

- Servants preparing weekly adult meetings or Bible studies
- Deacons, teachers, or youth ministers creating lesson content
- Anyone in Coptic ministry who wants AI assistance that actually understands Orthodox theology - not generic Christian content

---

## How to Use This

### Option A - Just copy a system prompt (no technical setup)

1. Open [`spiritual-lessons/system-prompts/universal.md`](spiritual-lessons/system-prompts/universal.md)
2. Copy the full contents
3. Paste into the system/custom instructions field of Claude, ChatGPT, Gemini, or any other AI
4. Start asking it to prepare lessons, series, or WhatsApp promos

Platform-specific versions are also available:
- [`system-prompts/claude-projects.md`](spiritual-lessons/system-prompts/claude-projects.md) - for Claude.ai Projects
- [`system-prompts/chatgpt-custom-gpt.md`](spiritual-lessons/system-prompts/chatgpt-custom-gpt.md) - for ChatGPT GPT Builder

### Option B - Install as a skill (Claude Code, Cursor, Codex CLI, etc.)

Install the main lesson skill:

```bash
npx skills add github.com/george-andraws/coptic-orthodox-ministry/spiritual-lessons
```

Supporting skills and portable reference packages:

```bash
npx skills add github.com/george-andraws/coptic-orthodox-ministry/orthodox-biblical-explanation
npx skills add github.com/george-andraws/coptic-orthodox-ministry/orthodox-apocalyptic-exegesis
npx skills add github.com/george-andraws/coptic-orthodox-ministry/orthodox-iconography
npx skills add github.com/george-andraws/coptic-orthodox-ministry/orthodox-shared-references
npx skills add github.com/george-andraws/coptic-orthodox-ministry/exact-scripture-speaker-ledger
npx skills add github.com/george-andraws/coptic-orthodox-ministry/outreach
```

Each install target is self-contained: its referenced reusable instructions are shipped in the package or in `references/shared/`. The public packages deliberately omit personal vault paths, calendars, document IDs, delivery channels, provider settings, and other machine-specific production workflow details.

The skills will be available to compatible AI coding agents automatically.

Shared reference bundles for standalone installs are generated into each package's `references/shared/` directory. If you change canonical files, regenerate before publishing or validating:

```bash
python3 scripts/sync_skill_packages.py --write
python3 scripts/sync_skill_packages.py --check
```

### Option C - Browse and reuse prepared content

All lessons and series are in [`spiritual-lessons/series/`](spiritual-lessons/series/) and [`spiritual-lessons/standalone/`](spiritual-lessons/standalone/). Read them directly on GitHub, copy what you need, or adapt for your community.

---

## Repo Structure

```
coptic-orthodox-ministry/
│
├── README.md                        ← you are here
├── CONTRIBUTING.md                  ← how to contribute content or skills
│
├── spiritual-lessons/               ← Canonical lesson skill source
│   ├── SKILL.md                     ← authored lesson-writing skill
│   ├── references/                  ← authored lesson-specific references
│   │   └── shared/                  ← generated shared bundles for standalone install
│   ├── system-prompts/              ← paste into any LLM
│   ├── style-guides/                ← lesson structure and promo format
│   ├── theology-references/         ← Orthodox doctrine and patristic approach
│   ├── series/                      ← multi-lesson series with overviews
│   └── standalone/                  ← single topical lessons
│
├── orthodox-biblical-explanation/   ← canonical passage-exegesis support skill
├── orthodox-iconography/            ← canonical iconography and visual theology skill
├── orthodox-apocalyptic-exegesis/    ← canonical apocalyptic Scripture support skill
├── orthodox-shared-references/       ← portable shared standards reference package
├── exact-scripture-speaker-ledger/   ← byte-exact Scripture quotation ledger skill
├── outreach/                        ← canonical Orthodox outreach communications skill
├── shared-references/               ← canonical sourcing, licensed-library, visual, and quality references
├── skill-packages.json              ← manifest for generated bundles, mirrors, and aliases
├── scripts/sync_skill_packages.py   ← stdlib sync/check tool
├── .agents/skills/                  ← generated package mirrors
└── skills/                          ← generated aliases to .agents/skills/
    ... plus other generated tool-specific alias roots
```

---

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for the theological standards, file format, and submission process.

Short version: content must be Orthodox, Christ-centered, patristically grounded, and practically useful. No generic Christian content. No invented patristic quotes. Edit canonical source files only, then run the sync script before relying on generated mirrors.

---

## License

All content is released under [Creative Commons Attribution 4.0 (CC BY 4.0)](https://creativecommons.org/licenses/by/4.0/). You are free to use, adapt, and share with attribution.

---

## Questions or Contributions

Open an issue or submit a pull request. If you are not familiar with GitHub, see the "Non-technical contribution" section in [CONTRIBUTING.md](CONTRIBUTING.md).
