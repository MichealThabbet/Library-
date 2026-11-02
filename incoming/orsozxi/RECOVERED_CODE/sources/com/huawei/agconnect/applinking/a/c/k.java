package com.huawei.agconnect.applinking.a.c;

import com.huawei.agconnect.https.annotation.Field;
import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class k {

    @Field("bundleId")
    @Result("bundleId")
    private String bundleId;

    @Field("fallbackUrl")
    @Result("fallbackUrl")
    private String fallbackUrl;

    public String getBundleId() {
        return this.bundleId;
    }

    public String getFallbackUrl() {
        return this.fallbackUrl;
    }

    public void setBundleId(String str) {
        this.bundleId = str;
    }

    public void setFallbackUrl(String str) {
        this.fallbackUrl = str;
    }
}
