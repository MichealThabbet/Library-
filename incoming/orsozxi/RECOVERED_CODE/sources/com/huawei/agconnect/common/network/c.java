package com.huawei.agconnect.common.network;

import com.huawei.agconnect.datastore.annotation.DefaultCrypto;
import com.huawei.agconnect.datastore.core.SharedPrefUtil;

/* loaded from: classes2.dex */
public class c {
    private static c a;
    private SharedPrefUtil b = SharedPrefUtil.getInstance();

    private c() {
    }

    public static synchronized c a() {
        if (a == null) {
            a = new c();
        }
        return a;
    }

    public void a(b bVar) {
        if (bVar != null) {
            this.b.put("AGConnectAccessNetwork", "enableAccessNetwork", Boolean.class, Boolean.valueOf(bVar.enableAccessNetwork), DefaultCrypto.class);
        }
    }

    public void b(b bVar) {
        if (bVar != null) {
            bVar.enableAccessNetwork = ((Boolean) this.b.get("AGConnectAccessNetwork", "enableAccessNetwork", Boolean.class, Boolean.valueOf(bVar.enableAccessNetwork), DefaultCrypto.class)).booleanValue();
        }
    }
}
