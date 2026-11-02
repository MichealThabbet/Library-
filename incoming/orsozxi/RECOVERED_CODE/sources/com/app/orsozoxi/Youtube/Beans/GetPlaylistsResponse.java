package com.app.orsozoxi.Youtube.Beans;

/* loaded from: classes.dex */
public class GetPlaylistsResponse {
    private String etag;
    private PlaylistItem[] items;
    private String kind;
    private String nextPageToken;
    private PageInfo pageInfo;

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

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public PlaylistItem[] getItems() {
        return this.items;
    }

    public void setItems(PlaylistItem[] playlistItemArr) {
        this.items = playlistItemArr;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public void setNextPageToken(String str) {
        this.nextPageToken = str;
    }
}
