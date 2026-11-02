package com.huawei.agconnect.applinking.a.c;

import com.huawei.agconnect.https.annotation.Field;
import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class h {

    @Field("androidPreviewUrl")
    @Result("androidPreviewUrl")
    private String androidPreviewUrl;

    @Field("iosPreviewUrl")
    @Result("iosPreviewUrl")
    private String iosPreviewUrl;

    public String getAndroidPreviewUrl() {
        return this.androidPreviewUrl;
    }

    public String getIosPreviewUrl() {
        return this.iosPreviewUrl;
    }

    public void setAndroidPreviewUrl(String str) {
        this.androidPreviewUrl = str;
    }

    public void setIosPreviewUrl(String str) {
        this.iosPreviewUrl = str;
    }
}
