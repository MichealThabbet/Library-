package com.huawei.agconnect.credential.obs;

import com.huawei.agconnect.common.api.AgcCrypto;
import com.huawei.agconnect.core.service.auth.Token;
import com.huawei.agconnect.datastore.annotation.SharedPreference;
import org.apache.http.cookie.ClientCookie;

/* loaded from: classes2.dex */
public class aa implements Token {

    @SharedPreference(crypto = AgcCrypto.class, fileName = "com.huawei.agconnect", isDynamic = true, key = ClientCookie.EXPIRES_ATTR)
    long expires;

    @SharedPreference(crypto = AgcCrypto.class, fileName = "com.huawei.agconnect", isDynamic = true, key = "tokenString")
    String tokenString;

    @SharedPreference(crypto = AgcCrypto.class, fileName = "com.huawei.agconnect", isDynamic = true, key = "validTime")
    long validTime = 0;

    public aa() {
    }

    public aa(String str, long j) {
        a(str, j);
    }

    private void a(String str, long j) {
        this.tokenString = str;
        this.expires = j;
        this.validTime = (System.currentTimeMillis() + (j * 1000)) - 120000;
    }

    public boolean a() {
        return this.tokenString != null && System.currentTimeMillis() <= this.validTime;
    }

    @Override // com.huawei.agconnect.core.service.auth.Token
    public long getExpiration() {
        return this.expires;
    }

    @Override // com.huawei.agconnect.core.service.auth.Token
    public long getIssuedAt() {
        return 0L;
    }

    @Override // com.huawei.agconnect.core.service.auth.Token
    public long getNotBefore() {
        return 0L;
    }

    @Override // com.huawei.agconnect.core.service.auth.Token
    public String getTokenString() {
        return this.tokenString;
    }
}
