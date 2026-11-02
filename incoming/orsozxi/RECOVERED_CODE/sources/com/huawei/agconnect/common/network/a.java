package com.huawei.agconnect.common.network;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.huawei.agconnect.common.appinfo.SafeAppInfo;
import com.huawei.agconnect.common.network.AccessNetworkManager;
import com.huawei.agconnect.credential.obs.w;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class a extends AccessNetworkManager {
    private final List<AccessNetworkManager.AccessNetworkCallback> a = new CopyOnWriteArrayList();
    private Context b = w.a().b();

    private void a() {
        Iterator<AccessNetworkManager.AccessNetworkCallback> it2 = this.a.iterator();
        while (it2.hasNext()) {
            it2.next().onNetWorkReady();
        }
    }

    private void a(AccessNetworkManager.AccessNetworkCallback accessNetworkCallback) {
        if (canAccessNetwork()) {
            accessNetworkCallback.onNetWorkReady();
        }
    }

    private void a(boolean z) {
        b.a().a(z);
    }

    @Override // com.huawei.agconnect.common.network.AccessNetworkManager
    public void addCallback(AccessNetworkManager.AccessNetworkCallback accessNetworkCallback) {
        if (accessNetworkCallback != null) {
            this.a.add(accessNetworkCallback);
            a(accessNetworkCallback);
        }
    }

    @Override // com.huawei.agconnect.common.network.AccessNetworkManager
    public boolean canAccessNetwork() {
        ApplicationInfo applicationInfoSafeGetApplicationInfo = SafeAppInfo.safeGetApplicationInfo(this.b.getPackageManager(), this.b.getPackageName(), 128);
        return applicationInfoSafeGetApplicationInfo.metaData == null || !"false".equalsIgnoreCase(String.valueOf(applicationInfoSafeGetApplicationInfo.metaData.get("com.huawei.agconnect.AccessNetwork"))) || b.a().b();
    }

    @Override // com.huawei.agconnect.common.network.AccessNetworkManager
    public void setAccessNetwork(boolean z) {
        a(z);
        if (z) {
            a();
        }
    }
}
