package com.huawei.agconnect.credential.obs;

import com.huawei.agconnect.datastore.annotation.DefaultCrypto;
import com.huawei.agconnect.datastore.core.SharedPrefUtil;

/* loaded from: classes2.dex */
public class i {
    private static i a;
    private SharedPrefUtil b = SharedPrefUtil.getInstance();

    private i() {
    }

    public static synchronized i a() {
        if (a == null) {
            a = new i();
        }
        return a;
    }

    public void a(h hVar) {
        if (hVar != null) {
            this.b.put("com.huawei.appgallery.datastore", "key", String.class, hVar.randmonKey, DefaultCrypto.class);
        }
    }

    public void b(h hVar) {
        if (hVar != null) {
            this.b.put("com.huawei.agconnect.internal", "jerry", String.class, hVar.jerry, DefaultCrypto.class);
        }
    }

    public void c(h hVar) {
        if (hVar != null) {
            this.b.put("com.huawei.appgallery.datastore", "fish", String.class, hVar.fish, DefaultCrypto.class);
        }
    }

    public void d(h hVar) {
        if (hVar != null) {
            hVar.randmonKey = (String) this.b.get("com.huawei.appgallery.datastore", "key", String.class, hVar.randmonKey, DefaultCrypto.class);
        }
    }

    public void e(h hVar) {
        if (hVar != null) {
            hVar.jerry = (String) this.b.get("com.huawei.agconnect.internal", "jerry", String.class, hVar.jerry, DefaultCrypto.class);
        }
    }

    public void f(h hVar) {
        if (hVar != null) {
            hVar.fish = (String) this.b.get("com.huawei.appgallery.datastore", "fish", String.class, hVar.fish, DefaultCrypto.class);
        }
    }
}
