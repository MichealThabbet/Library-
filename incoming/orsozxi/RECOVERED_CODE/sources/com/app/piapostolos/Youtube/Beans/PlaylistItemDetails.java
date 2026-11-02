package com.app.piapostolos.Youtube.Beans;

/* loaded from: classes.dex */
public class PlaylistItemDetails {
    private String etag;
    private String id;
    private String kind;
    private PlaylistSnippetItemDetails snippet;

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

    public PlaylistSnippetItemDetails getSnippet() {
        return this.snippet;
    }

    public void setSnippet(PlaylistSnippetItemDetails playlistSnippetItemDetails) {
        this.snippet = playlistSnippetItemDetails;
    }
}
