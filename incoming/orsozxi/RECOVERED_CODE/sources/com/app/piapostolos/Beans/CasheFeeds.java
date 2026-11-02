package com.app.piapostolos.Beans;

import java.util.List;

/* loaded from: classes.dex */
public class CasheFeeds {
    private List<RSSFeed> akwalFeeds;
    private List<RSSFeed> cardsFeeds;
    private List<RSSFeed> tipInWordFeeds;
    private List<RSSFeed> trueAyahFeeds;

    public List<RSSFeed> getCardsFeeds() {
        return this.cardsFeeds;
    }

    public void setCardsFeeds(List<RSSFeed> list) {
        this.cardsFeeds = list;
    }

    public List<RSSFeed> getAkwalFeeds() {
        return this.akwalFeeds;
    }

    public void setAkwalFeeds(List<RSSFeed> list) {
        this.akwalFeeds = list;
    }

    public List<RSSFeed> getTrueAyahFeeds() {
        return this.trueAyahFeeds;
    }

    public void setTrueAyahFeeds(List<RSSFeed> list) {
        this.trueAyahFeeds = list;
    }

    public List<RSSFeed> getTipInWordFeeds() {
        return this.tipInWordFeeds;
    }

    public void setTipInWordFeeds(List<RSSFeed> list) {
        this.tipInWordFeeds = list;
    }
}
