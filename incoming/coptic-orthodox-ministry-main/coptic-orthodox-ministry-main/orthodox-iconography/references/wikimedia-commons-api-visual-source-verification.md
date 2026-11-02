# Wikimedia Commons API visual source verification

Use this when an Orthodox Bible-study or iconography task needs a sourced visual asset with license metadata, especially for icons, maps, manuscript images, museum objects, or public-domain teaching art.

This is a positive workflow pattern, not a claim about any specific web tool. It avoids relying on generic search snippets and records the actual Commons file metadata before embedding an image in the lesson author's vault.

## When to use

- A guide plan explicitly calls for iconography or visual teaching.
- You need a public-domain or clearly licensed Orthodox icon / biblical artwork.
- You need to verify the exact Commons file page, upload URL, MIME type, size, artist, and license before downloading.
- You want to avoid citing a Commons category page or search result as if it were the selected source.

## API search pattern

Query Commons file namespace (`gsrnamespace=6`) and request `imageinfo` metadata:

```python
import urllib.parse, urllib.request, json

query = "Transfiguration icon Theophanes the Greek"
params = {
    "action": "query",
    "format": "json",
    "generator": "search",
    "gsrnamespace": "6",
    "gsrsearch": query,
    "gsrlimit": "8",
    "prop": "imageinfo",
    "iiprop": "url|extmetadata|mime|size",
}
url = "https://commons.wikimedia.org/w/api.php?" + urllib.parse.urlencode(params)
data = json.load(urllib.request.urlopen(urllib.request.Request(url, headers={"User-Agent": "Hermes/1.0"}), timeout=30))
for page in data.get("query", {}).get("pages", {}).values():
    ii = (page.get("imageinfo") or [{}])[0]
    meta = ii.get("extmetadata") or {}
    print(page.get("title"), meta.get("LicenseShortName", {}).get("value"), ii.get("mime"), ii.get("url"))
```

Select a file only after checking:

- file title is the specific visual, not a category or unrelated object
- `LicenseShortName` is acceptable for the project, ideally `Public domain` for simple guide use
- MIME type is an image type
- upload URL is present
- artist/source/description metadata is sufficient for a clean caption or source note

## Metadata detail lookup

For a known file title:

```python
params = {
    "action": "query",
    "format": "json",
    "titles": "File:1400-10 Theophanes the Greek Transfiguration anagoria.JPG",
    "prop": "imageinfo",
    "iiprop": "url|extmetadata|mime|size",
}
```

Useful metadata keys:

- `LicenseShortName`
- `Artist`
- `ImageDescription`
- `Credit`
- `Date`
- imageinfo `url`, `mime`, `size`

Strip HTML tags only for internal review if needed. Reader-facing captions should be short and clean.

### Known-file / user-supplied URL pattern

When the lesson author's supplies a specific Wikipedia/Commons image URL, do not do a broad image search first. Resolve the actual Commons file title, then query the Commons API for file-level metadata. If page extraction is unavailable or rate-limited, use the MediaWiki API directly for:

- Commons `imageinfo` with `url|extmetadata|mime|size`
- Wikipedia `extracts|info` for article summary, official location, date, medium, dimensions, and interpretive context when the file page is sparse
- `parse` wikitext when the article infobox carries fields such as `year`, `medium`, `museum`, `city`, `owner`, or `website`

If remote image inspection hits rate limiting, inspect the downloaded local file instead of treating the image as unverifiable. The durable lesson is the fallback sequence: API metadata, local download, local visual verification, then guide embedding.

Record museum/location/date/medium/dimensions in a project manifest or resources note when the image is used in a Bible-study guide. Keep the reader-facing caption short; put fuller provenance in the guide prose only when it directly helps the passage.

## Download and verify

Download the selected upload URL into the book-local `Assets/` folder with a stable, project-specific filename.

Then verify:

```bash
file "Assets/<filename>.jpg"
python3 - <<'PY'
from pathlib import Path
p = Path('Assets/<filename>.jpg')
print(p.exists(), p.stat().st_size)
PY
```

Do not embed from temporary directories, a download folder, or a remote upload URL when the guide should remain portable inside its Markdown project.

## Caption pattern for guide bodies

Use a concise reader-facing caption:

```markdown
![Transfiguration icon](Assets/matthew-17-transfiguration-theophanes.jpg)
<small><em>Source: [Theophanes the Greek, Transfiguration icon](https://commons.wikimedia.org/wiki/File:1400-10_Theophanes_the_Greek_Transfiguration_anagoria.JPG), public domain, via Wikimedia Commons.</em></small>
```

Do not cite a generic Commons category page. Do not mention API calls, local paths, or tooling in the guide body.

## Resources/audit note pattern

Record the operational source details in `Resources Used - <Book>.md` or an audit note:

- local asset filename
- Commons file page
- license status
- artist/source metadata
- teaching purpose
- any caution, e.g. Orthodox icon vs Western art vs map vs generated teaching aid

## Verifier interaction

If the guide uses iconography or visual language without an actual inline image/embed, the guide verifier may warn. Either:

1. add the intended verified image with a source caption, or
2. replace metaphorical visual wording with non-visual wording when no image is intended.

Example replacements:

- `image for decisive repentance` -> `language for decisive repentance`
- `shepherd image` -> `shepherd teaching`
