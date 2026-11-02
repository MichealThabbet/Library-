package com.huawei.agconnect.credential.obs;

import com.huawei.agconnect.common.api.AgcCrypto;
import com.huawei.agconnect.datastore.core.SharedPrefUtil;

/* loaded from: classes2.dex */
public class b {
    private static b a;
    private SharedPrefUtil b = SharedPrefUtil.getInstance();

    private b() {
    }

    public static synchronized b a() {
        if (a == null) {
            a = new b();
        }
        return a;
    }

    public void a(a aVar) {
        b(aVar);
    }

    public void b(a aVar) {
        if (aVar != null) {
            this.b.put("com.huawei.agconnect", "aaid", String.class, aVar.aaidString, AgcCrypto.class);
        }
    }

    public void c(a aVar) {
        if (aVar != null) {
            aVar.aaidString = (String) this.b.get("com.huawei.agconnect", "aaid", String.class, aVar.aaidString, AgcCrypto.class);
        }
    }
}
