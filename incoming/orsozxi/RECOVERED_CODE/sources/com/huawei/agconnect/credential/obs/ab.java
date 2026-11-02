package com.huawei.agconnect.credential.obs;

import com.huawei.agconnect.common.api.AgcCrypto;
import com.huawei.agconnect.datastore.core.SharedPrefUtil;

/* loaded from: classes2.dex */
public class ab {
    private static ab a;
    private SharedPrefUtil b = SharedPrefUtil.getInstance();

    private ab() {
    }

    public static synchronized ab a() {
        if (a == null) {
            a = new ab();
        }
        return a;
    }

    public void a(aa aaVar, String str) {
        if (aaVar == null || str == null) {
            return;
        }
        this.b.put("com.huawei.agconnect", "tokenString_" + str, String.class, aaVar.tokenString, AgcCrypto.class);
    }

    public void b(aa aaVar, String str) {
        if (aaVar == null || str == null) {
            return;
        }
        this.b.put("com.huawei.agconnect", "expires_" + str, Long.class, Long.valueOf(aaVar.expires), AgcCrypto.class);
    }

    public void c(aa aaVar, String str) {
        if (aaVar == null || str == null) {
            return;
        }
        this.b.put("com.huawei.agconnect", "validTime_" + str, Long.class, Long.valueOf(aaVar.validTime), AgcCrypto.class);
    }

    public void d(aa aaVar, String str) {
        if (aaVar == null || str == null) {
            return;
        }
        aaVar.tokenString = (String) this.b.get("com.huawei.agconnect", "tokenString_" + str, String.class, null, AgcCrypto.class);
    }

    public void e(aa aaVar, String str) {
        if (aaVar == null || str == null) {
            return;
        }
        aaVar.expires = ((Long) this.b.get("com.huawei.agconnect", "expires_" + str, Long.class, null, AgcCrypto.class)).longValue();
    }

    public void f(aa aaVar, String str) {
        if (aaVar == null || str == null) {
            return;
        }
        aaVar.validTime = ((Long) this.b.get("com.huawei.agconnect", "validTime_" + str, Long.class, null, AgcCrypto.class)).longValue();
    }
}
