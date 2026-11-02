package com.app.orsozoxi.Radio;

/* loaded from: classes.dex */
public class MediaItem {
    String album;
    long albumId;
    String artist;
    String composer;
    long duration;
    String path;
    String title;

    public String toString() {
        return this.title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String str) {
        this.artist = str;
    }

    public String getAlbum() {
        return this.album;
    }

    public void setAlbum(String str) {
        this.album = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public long getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(long j) {
        this.albumId = j;
    }

    public String getComposer() {
        return this.composer;
    }

    public void setComposer(String str) {
        this.composer = str;
    }
}
