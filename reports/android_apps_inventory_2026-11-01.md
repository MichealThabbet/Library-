# Android app archive inspection — 2026-11-01

Source ZIP packages were inspected locally; this report does not mean binary app archives have been uploaded to GitHub.

## 1. Ipsaltos / Midnight Praise, v8.1
- ZIP size: 136,540,498 bytes
- Entries: 2,317
- Uncompressed: 136,167,658 bytes
- SHA-256: 8cc8a5d124d52ceed20b1a0a21b745b718b403a324fd17b6908db123cf1e7875
- Integrity: ZIP test passed; none ZIP-encrypted
- Content: 827 HTM, 590 TXT, 150 MP3, 340 PNG, 10 CSV, 2 DEX, 286 XML, fonts and CSS.
- Readability: HTM/TXT/CSV are directly readable; MP3 playable; pictures viewable. DEX is compiled Android bytecode.
- Split into 7 local chunks, each <=20 MiB.

## 2. Ekklesia / Comprehensive Christian Library, v8.9.45
- ZIP size: 64,722,729 bytes
- Entries: 924
- Uncompressed: 64,568,801 bytes
- SHA-256: c0a92ffb5fb539cdb94932c21f560a307491b132b218db55ba644808a5465097
- Integrity: ZIP test passed; none ZIP-encrypted
- Content: 612 PNG, 7 JPG, 228 XML, 24 SO, 3 DEX, 5 HTM/HTML, 4 TXT, 6 JS, 1 JSON, 1 MP3 and fonts.
- No standalone PDF book files found in the supplied archive. The 'about' text describes a book library, but this inventory does not establish whether material is remotely retrieved at runtime.
- Split into 4 local chunks, each <=20 MiB.

## Notes
- File formats are not equivalent to licensing permissions.
- Android manifests and resource XML may be binary, not plain UTF-8.
- DEX/compiled native SO require specialized tools; not claimed to be source code.
- Binary ZIP and chunks not yet uploaded due to connector support for UTF-8 text writes only.
