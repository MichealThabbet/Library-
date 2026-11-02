package com.huawei.agconnect.credential.obs;

import com.facebook.AccessToken;
import com.huawei.agconnect.https.annotation.Result;

/* loaded from: classes2.dex */
public class y {

    @Result("access_token")
    private String access_token;

    @Result(AccessToken.EXPIRES_IN_KEY)
    private long expires_in;

    @Result("ret")
    private z ret;

    public String getAccessToken() {
        return this.access_token;
    }

    public long getExpiresIn() {
        return this.expires_in;
    }

    public z getRet() {
        return this.ret;
    }
}
