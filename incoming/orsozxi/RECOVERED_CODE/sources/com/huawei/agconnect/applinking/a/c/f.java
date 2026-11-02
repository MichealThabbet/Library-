package com.huawei.agconnect.applinking.a.c;

import com.huawei.agconnect.common.api.BaseRequest;
import com.huawei.agconnect.https.annotation.Field;
import com.huawei.agconnect.https.annotation.Url;

/* loaded from: classes2.dex */
public class f extends BaseRequest {

    @Url
    public static final String URL = "http://localhost/agc/apigw/dlm/dlm-service/v2/usage/links";

    @Field("expireTime")
    private Long expireTime;

    @Field("linkInfo")
    private l linkInfoData;

    @Field("longUrl")
    private String longUrl;

    @Field("suffixLength")
    private String suffixLength;

    public f() {
        setSdkServiceName("agconnect-applinking");
        setSdkVersion("1.7.0.300");
    }

    public Long getExpireTime() {
        return this.expireTime;
    }

    public l getLinkInfoData() {
        return this.linkInfoData;
    }

    public String getLongUrl() {
        return this.longUrl;
    }

    public String getSuffixLength() {
        return this.suffixLength;
    }

    public void setExpireTime(Long l) {
        this.expireTime = l;
    }

    public void setLinkInfoData(l lVar) {
        this.linkInfoData = lVar;
    }

    public void setLongUrl(String str) {
        this.longUrl = str;
    }

    public void setSuffixLength(String str) {
        this.suffixLength = str;
    }
}
