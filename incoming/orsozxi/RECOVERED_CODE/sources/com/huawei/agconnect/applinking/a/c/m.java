package com.huawei.agconnect.applinking.a.c;

import com.huawei.agconnect.common.api.BaseRequest;
import com.huawei.agconnect.https.annotation.Field;
import com.huawei.agconnect.https.annotation.Header;
import com.huawei.agconnect.https.annotation.Url;

/* loaded from: classes2.dex */
public class m extends BaseRequest {
    private static final String FIRST_OPEN_URL = "http://localhost/agc/apigw/dlm/dlm-service/v2/usage/first-open-link";
    private static final String REPEAT_OPEN_URL = "http://localhost/agc/apigw/dlm/dlm-service/v2/usage/repeat-open-link";

    @Url
    public String URL;

    @Field("clickId")
    private String clickId;

    @Header("firstOpenSource")
    private String firstOpenSource;

    @Field("shortOrLongUrl")
    private String shortOrLongUrl;

    public m(boolean z) {
        this.URL = z ? FIRST_OPEN_URL : REPEAT_OPEN_URL;
        setSdkServiceName("agconnect-applinking");
        setSdkVersion("1.7.0.300");
    }

    public String getClickId() {
        return this.clickId;
    }

    public String getShortOrLongUrl() {
        return this.shortOrLongUrl;
    }

    public boolean isFirst() {
        return FIRST_OPEN_URL.equals(this.URL);
    }

    public void setClickId(String str) {
        this.clickId = str;
    }

    public void setFirstOpenSource(String str) {
        this.firstOpenSource = str;
    }

    public void setShortOrLongUrl(String str) {
        this.shortOrLongUrl = str;
    }
}
