package com.huawei.agconnect.applinking.a.c;

import com.huawei.agconnect.https.annotation.Field;
import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class p {

    @Field("agRedirectingUrl")
    @Result("agRedirectingUrl")
    private String agRedirectingUrl;

    @Field("campaign")
    @Result("campaign")
    private d campaignInfoData;

    @Field("defaultRedirectionUrl")
    @Result("defaultRedirectionUrl")
    private String defaultRedirectionUrl;

    @Field("iOSRedirectingUrl")
    @Result("iOSRedirectingUrl")
    private String iOSRedirectingUrl;

    @Field("otherRedirectionUrl")
    @Result("otherRedirectionUrl")
    private String otherRedirectionUrl;

    @Field("uriPrefix")
    @Result("uriPrefix")
    private String uriPrefix;

    public String getAgRedirectingUrl() {
        return this.agRedirectingUrl;
    }

    public d getCampaignInfoData() {
        return this.campaignInfoData;
    }

    public String getDefaultRedirectionUrl() {
        return this.defaultRedirectionUrl;
    }

    public String getOtherRedirectionUrl() {
        return this.otherRedirectionUrl;
    }

    public String getUriPrefix() {
        return this.uriPrefix;
    }

    public String getiOSRedirectingUrl() {
        return this.iOSRedirectingUrl;
    }

    public void setAgRedirectingUrl(String str) {
        this.agRedirectingUrl = str;
    }

    public void setCampaignInfoData(d dVar) {
        this.campaignInfoData = dVar;
    }

    public void setDefaultRedirectionUrl(String str) {
        this.defaultRedirectionUrl = str;
    }

    public void setOtherRedirectionUrl(String str) {
        this.otherRedirectionUrl = str;
    }

    public void setUriPrefix(String str) {
        this.uriPrefix = str;
    }

    public void setiOSRedirectingUrl(String str) {
        this.iOSRedirectingUrl = str;
    }
}
