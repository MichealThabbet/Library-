package com.huawei.agconnect.credential.obs;

import com.huawei.agconnect.datastore.annotation.DefaultCrypto;
import com.huawei.agconnect.datastore.core.SharedPrefUtil;

/* loaded from: classes2.dex */
public class s {
    private static s a;
    private SharedPrefUtil b = SharedPrefUtil.getInstance();

    private s() {
    }

    public static synchronized s a() {
        if (a == null) {
            a = new s();
        }
        return a;
    }

    public void a(r rVar, String str) {
        if (rVar == null || str == null) {
            return;
        }
        this.b.put("agc_site", "main_" + str, String.class, rVar.mainUrl, DefaultCrypto.class);
    }

    public void a(String str) {
        this.b.remove("agc_site", "main_" + str);
    }

    public void b(r rVar, String str) {
        if (rVar == null || str == null) {
            return;
        }
        this.b.put("agc_site", "backup_" + str, String.class, rVar.backUrl, DefaultCrypto.class);
    }

    public void b(String str) {
        this.b.remove("agc_site", "backup_" + str);
    }

    public void c(r rVar, String str) {
        if (rVar == null || str == null) {
            return;
        }
        this.b.put("agc_site", "validTime_" + str, Long.class, Long.valueOf(rVar.validTime), DefaultCrypto.class);
    }

    public void d(r rVar, String str) {
        if (rVar == null || str == null) {
            return;
        }
        rVar.mainUrl = (String) this.b.get("agc_site", "main_" + str, String.class, null, DefaultCrypto.class);
    }

    public void e(r rVar, String str) {
        if (rVar == null || str == null) {
            return;
        }
        rVar.backUrl = (String) this.b.get("agc_site", "backup_" + str, String.class, null, DefaultCrypto.class);
    }

    public void f(r rVar, String str) {
        if (rVar == null || str == null) {
            return;
        }
        rVar.validTime = ((Long) this.b.get("agc_site", "validTime_" + str, Long.class, null, DefaultCrypto.class)).longValue();
    }
}
