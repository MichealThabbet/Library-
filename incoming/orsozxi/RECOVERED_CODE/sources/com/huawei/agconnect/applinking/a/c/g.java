package com.huawei.agconnect.applinking.a.c;

import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class g extends c {

    @Result("shortUrl")
    private String shortLink;

    @Result("testUrl")
    private String testLink;

    public String getShortLink() {
        return this.shortLink;
    }

    public String getTestLink() {
        return this.testLink;
    }

    public void setShortLink(String str) {
        this.shortLink = str;
    }

    public void setTestLink(String str) {
        this.testLink = str;
    }
}
