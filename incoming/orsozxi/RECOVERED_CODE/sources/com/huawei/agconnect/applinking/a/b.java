package com.huawei.agconnect.applinking.a;

import com.huawei.agconnect.datastore.annotation.DefaultCrypto;
import com.huawei.agconnect.datastore.core.SharedPrefUtil;

/* loaded from: classes2.dex */
public class b {
    public boolean a() {
        return ((String) SharedPrefUtil.getInstance().get("com.huawei.agconnect.applinking", "isLaunched", String.class, "default", DefaultCrypto.class)).equals("default");
    }

    public void b() {
        SharedPrefUtil.getInstance().put("com.huawei.agconnect.applinking", "isLaunched", String.class, "yes", DefaultCrypto.class);
    }
}
