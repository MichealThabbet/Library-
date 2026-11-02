package com.app.piapostolos.Beans;

import java.io.Serializable;

/* loaded from: classes.dex */
public class RSSFeed implements Serializable {
    private String category;
    private String description;
    private String feedburnerOrigLink;
    private String guid;
    private String link;
    private String pubDate;
    private String title;

    public RSSFeed() {
    }

    public RSSFeed(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.title = str;
        this.link = str2;
        this.description = str3;
        this.category = str4;
        this.pubDate = str5;
        this.guid = str6;
        this.feedburnerOrigLink = str7;
    }

    public String getTitle() {
        return this.title;
    }

    public String getLink() {
        return this.link;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCategory() {
        return this.category;
    }

    public String getPubDate() {
        return this.pubDate;
    }

    public String getGuid() {
        return this.guid;
    }

    public String getFeedburnerOrigLink() {
        return this.feedburnerOrigLink;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setLink(String str) {
        this.link = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setCategory(String str) {
        this.category = str;
    }

    public void setPubDate(String str) {
        this.pubDate = str;
    }

    public void setGuid(String str) {
        this.guid = str;
    }

    public void setFeedburnerOrigLink(String str) {
        this.feedburnerOrigLink = str;
    }
}
