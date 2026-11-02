package com.huawei.agconnect.common.network;

import com.huawei.agconnect.datastore.annotation.SharedPreference;

/* loaded from: classes2.dex */
public class b {
    private static b a = new b();

    @SharedPreference(fileName = "AGConnectAccessNetwork", key = "enableAccessNetwork")
    boolean enableAccessNetwork = false;

    private b() {
    }

    public static b a() {
        return a;
    }

    public void a(boolean z) {
        this.enableAccessNetwork = z;
        c.a().a(a);
    }

    public boolean b() {
        c.a().b(a);
        return this.enableAccessNetwork;
    }
}
