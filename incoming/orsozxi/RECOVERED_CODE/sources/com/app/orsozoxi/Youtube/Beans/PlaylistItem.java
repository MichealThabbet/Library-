package com.app.orsozoxi.Youtube.Beans;

/* loaded from: classes.dex */
public class PlaylistItem {
    private String etag;
    private String id;
    private String kind;
    private PlaylistSnippetItem snippet;

    public String getKind() {
        return this.kind;
    }

    public void setKind(String str) {
        this.kind = str;
    }

    public String getEtag() {
        return this.etag;
    }

    public void setEtag(String str) {
        this.etag = str;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public PlaylistSnippetItem getSnippet() {
        return this.snippet;
    }

    public void setSnippet(PlaylistSnippetItem playlistSnippetItem) {
        this.snippet = playlistSnippetItem;
    }
}
