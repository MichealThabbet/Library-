package com.huawei.agconnect.applinking.a.c;

import com.google.firebase.messaging.Constants;
import com.huawei.agconnect.https.annotation.Field;
import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class a {

    @Field("deepLink")
    @Result("deepLink")
    private String deepLink;

    @Field("fallbackUrl")
    @Result("fallbackUrl")
    private String fallbackUrl;

    @Field("openType")
    @Result("openType")
    private Integer openType;

    @Field(Constants.FirelogAnalytics.PARAM_PACKAGE_NAME)
    @Result(Constants.FirelogAnalytics.PARAM_PACKAGE_NAME)
    private String packageName;

    public String getDeepLink() {
        return this.deepLink;
    }

    public String getFallbackUrl() {
        return this.fallbackUrl;
    }

    public Integer getOpenType() {
        return this.openType;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setDeepLink(String str) {
        this.deepLink = str;
    }

    public void setFallbackUrl(String str) {
        this.fallbackUrl = str;
    }

    public void setOpenType(Integer num) {
        this.openType = num;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }
}
