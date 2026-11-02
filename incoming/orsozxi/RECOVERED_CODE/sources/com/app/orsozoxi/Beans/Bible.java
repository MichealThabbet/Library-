package com.app.orsozoxi.Beans;

/* loaded from: classes.dex */
public class Bible {
    private String born;
    private String name;

    public Bible(String str, String str2) {
        this.name = str;
        this.born = str2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getNick() {
        return this.born;
    }

    public void setNick(String str) {
        this.born = str;
    }
}
