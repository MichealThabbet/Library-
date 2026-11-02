package com.huawei.agconnect.applinking.a.c;

import com.huawei.agconnect.https.annotation.Field;
import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class b {

    @Field("affiliateToken")
    @Result("affiliateToken")
    private String affiliateToken;

    @Field("campaignToken")
    @Result("campaignToken")
    private String campaignToken;

    @Field("mediaType")
    @Result("mediaType")
    private String mediaType;

    @Field("providerToken")
    @Result("providerToken")
    private String providerToken;

    public String getAffiliateToken() {
        return this.affiliateToken;
    }

    public String getCampaignToken() {
        return this.campaignToken;
    }

    public String getMediaType() {
        return this.mediaType;
    }

    public String getProviderToken() {
        return this.providerToken;
    }

    public void setAffiliateToken(String str) {
        this.affiliateToken = str;
    }

    public void setCampaignToken(String str) {
        this.campaignToken = str;
    }

    public void setMediaType(String str) {
        this.mediaType = str;
    }

    public void setProviderToken(String str) {
        this.providerToken = str;
    }
}
