package com.huawei.agconnect.applinking.a.c;

import com.huawei.agconnect.https.annotation.Field;
import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class j {

    @Field("appStoreCampaign")
    @Result("appStoreCampaign")
    private b appStoreCampaignData;

    @Field("bundleId")
    @Result("bundleId")
    private String bundleId;

    @Field("deepLink")
    @Result("deepLink")
    private String deepLink;

    @Field("fallbackUrl")
    @Result("fallbackUrl")
    private String fallbackUrl;

    @Field("ipad")
    @Result("ipad")
    private k iPadInfoData;

    public b getAppStoreCampaignData() {
        return this.appStoreCampaignData;
    }

    public String getBundleId() {
        return this.bundleId;
    }

    public String getDeepLink() {
        return this.deepLink;
    }

    public String getFallbackUrl() {
        return this.fallbackUrl;
    }

    public k getiPadInfoData() {
        return this.iPadInfoData;
    }

    public void setAppStoreCampaignData(b bVar) {
        this.appStoreCampaignData = bVar;
    }

    public void setBundleId(String str) {
        this.bundleId = str;
    }

    public void setDeepLink(String str) {
        this.deepLink = str;
    }

    public void setFallbackUrl(String str) {
        this.fallbackUrl = str;
    }

    public void setiPadInfoData(k kVar) {
        this.iPadInfoData = kVar;
    }
}
