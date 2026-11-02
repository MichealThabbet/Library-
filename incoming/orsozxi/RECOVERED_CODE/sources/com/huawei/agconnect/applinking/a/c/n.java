package com.huawei.agconnect.applinking.a.c;

import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class n extends c {

    @Result("clickTime")
    private long clickTime;

    @Result("linkInfo")
    private l linkInfoData;

    @Result("linkType")
    private String linkType;

    @Result("shortOrLongUrl")
    private String shortOrLongUrl;

    @Result("unifiedLinkInfo")
    private p unifiedLinkInfo;

    public long getClickTime() {
        return this.clickTime;
    }

    public l getLinkInfoData() {
        return this.linkInfoData;
    }

    public String getLinkType() {
        return this.linkType;
    }

    public String getShortOrLongUrl() {
        return this.shortOrLongUrl;
    }

    public p getUnifiedLinkInfo() {
        return this.unifiedLinkInfo;
    }
}
