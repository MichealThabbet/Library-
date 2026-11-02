package com.huawei.agconnect.applinking.a.c;

import androidx.mediarouter.media.SystemMediaRouteProvider;
import com.huawei.agconnect.https.annotation.Field;
import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class l {

    @Field(SystemMediaRouteProvider.PACKAGE_NAME)
    @Result(SystemMediaRouteProvider.PACKAGE_NAME)
    private a androidInfoData;

    @Field("campaign")
    @Result("campaign")
    private d campaignInfoData;

    @Field("customPreview")
    @Result("customPreview")
    private h customPreviewInfoData;

    @Field("deepLink")
    @Result("deepLink")
    private String deepLink;

    @Field("harmonyos")
    @Result("harmonyos")
    private i harmonyOSInfoData;

    @Field("ios")
    @Result("ios")
    private j iosInfoData;

    @Field("landingPageType")
    @Result("landingPageType")
    private Integer landingPageType;

    @Field("previewType")
    @Result("previewType")
    private Integer previewType;

    @Field("socialCard")
    @Result("socialCard")
    private o socialCardData;

    @Field("uriPrefix")
    @Result("uriPrefix")
    private String uriPrefix;

    public a getAndroidInfoData() {
        return this.androidInfoData;
    }

    public d getCampaignInfoData() {
        return this.campaignInfoData;
    }

    public h getCustomPreviewInfoData() {
        return this.customPreviewInfoData;
    }

    public String getDeepLink() {
        return this.deepLink;
    }

    public String getFinalDeepLink() {
        a aVar = this.androidInfoData;
        return (aVar == null || aVar.getDeepLink() == null) ? this.deepLink : this.androidInfoData.getDeepLink();
    }

    public i getHarmonyOSInfoData() {
        return this.harmonyOSInfoData;
    }

    public j getIosInfoData() {
        return this.iosInfoData;
    }

    public Integer getLandingPageType() {
        return this.landingPageType;
    }

    public Integer getPreviewType() {
        return this.previewType;
    }

    public o getSocialCardData() {
        return this.socialCardData;
    }

    public String getUriPrefix() {
        return this.uriPrefix;
    }

    public void setAndroidInfoData(a aVar) {
        this.androidInfoData = aVar;
    }

    public void setCampaignInfoData(d dVar) {
        this.campaignInfoData = dVar;
    }

    public void setCustomPreviewInfoData(h hVar) {
        this.customPreviewInfoData = hVar;
    }

    public void setDeepLink(String str) {
        this.deepLink = str;
    }

    public void setHarmonyOSInfoData(i iVar) {
        this.harmonyOSInfoData = iVar;
    }

    public void setIosInfoData(j jVar) {
        this.iosInfoData = jVar;
    }

    public void setLandingPageType(Integer num) {
        this.landingPageType = num;
    }

    public void setPreviewType(Integer num) {
        this.previewType = num;
    }

    public void setSocialCardData(o oVar) {
        this.socialCardData = oVar;
    }

    public void setUriPrefix(String str) {
        this.uriPrefix = str;
    }
}
