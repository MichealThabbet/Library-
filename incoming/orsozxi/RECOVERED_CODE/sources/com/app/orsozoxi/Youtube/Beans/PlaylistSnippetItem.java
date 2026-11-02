package com.app.orsozoxi.Youtube.Beans;

/* loaded from: classes.dex */
public class PlaylistSnippetItem {
    private String channelId;
    private String channelTitle;
    private String description;
    private Localized localized;
    private String publishedAt;
    private Thumbnails thumbnails;
    private String title;

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(String str) {
        this.publishedAt = str;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public Thumbnails getThumbnails() {
        return this.thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getChannelTitle() {
        return this.channelTitle;
    }

    public void setChannelTitle(String str) {
        this.channelTitle = str;
    }

    public Localized getLocalized() {
        return this.localized;
    }

    public void setLocalized(Localized localized) {
        this.localized = localized;
    }
}
