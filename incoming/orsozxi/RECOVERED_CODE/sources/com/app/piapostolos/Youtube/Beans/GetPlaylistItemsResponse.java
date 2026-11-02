package com.app.piapostolos.Youtube.Beans;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class GetPlaylistItemsResponse {
    private String etag;
    private ArrayList<PlaylistItemDetails> items;
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

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public void setNextPageToken(String str) {
        this.nextPageToken = str;
    }

    public ArrayList<PlaylistItemDetails> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<PlaylistItemDetails> arrayList) {
        this.items = arrayList;
    }
}
