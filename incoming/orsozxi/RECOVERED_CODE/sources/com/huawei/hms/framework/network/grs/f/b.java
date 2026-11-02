package com.huawei.hms.framework.network.grs.f;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.framework.common.AssetsUtil;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.network.grs.GrsApp;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class b {
    private static final Map<String, b> b = new ConcurrentHashMap(16);
    private static final Object c = new Object();
    private a a;

    public b(Context context, GrsBaseInfo grsBaseInfo, boolean z) {
        a(context, z);
        b.put(context.getPackageName() + grsBaseInfo.uniqueCode(), this);
    }

    public static b a(String str, GrsBaseInfo grsBaseInfo) {
        return b.get(str + grsBaseInfo.uniqueCode());
    }

    public static void a(Context context, GrsBaseInfo grsBaseInfo) {
        b bVarA = a(context.getPackageName(), grsBaseInfo);
        if (bVarA != null) {
            Logger.i("LocalManagerProxy", "appGrs is not null and clear services.");
            synchronized (c) {
                bVarA.a.a();
            }
        }
    }

    public com.huawei.hms.framework.network.grs.local.model.a a() {
        return this.a.b();
    }

    public String a(Context context, com.huawei.hms.framework.network.grs.e.a aVar, GrsBaseInfo grsBaseInfo, String str, String str2, boolean z) {
        synchronized (c) {
            String strA = this.a.a(context, aVar, grsBaseInfo, str, str2, z);
            if (!TextUtils.isEmpty(strA) || !this.a.d()) {
                return strA;
            }
            a(context, true);
            a(grsBaseInfo);
            b.put(context.getPackageName() + grsBaseInfo.uniqueCode(), this);
            return this.a.a(context, aVar, grsBaseInfo, str, str2, z);
        }
    }

    public Map<String, String> a(Context context, com.huawei.hms.framework.network.grs.e.a aVar, GrsBaseInfo grsBaseInfo, String str, boolean z) {
        synchronized (c) {
            Map<String, String> mapA = this.a.a(context, aVar, grsBaseInfo, str, z);
            if ((mapA != null && !mapA.isEmpty()) || !this.a.d()) {
                return mapA;
            }
            a(context, true);
            a(grsBaseInfo);
            b.put(context.getPackageName() + grsBaseInfo.uniqueCode(), this);
            return this.a.a(context, aVar, grsBaseInfo, str, z);
        }
    }

    public void a(Context context, boolean z) {
        String[] list = AssetsUtil.list(context, GrsApp.getInstance().getBrand(""));
        List<String> arrayList = list == null ? new ArrayList<>() : Arrays.asList(list);
        String appConfigName = GrsApp.getInstance().getAppConfigName();
        Logger.i("LocalManagerProxy", "appConfigName is" + appConfigName);
        this.a = new d(false, z);
        if (arrayList.contains("grs_app_global_route_config.json") || !TextUtils.isEmpty(appConfigName)) {
            this.a = new d(context, appConfigName, z);
        }
        if (!this.a.e() && arrayList.contains("grs_sdk_global_route_config.json")) {
            this.a = new c(context, z);
        }
        this.a.a(context, arrayList);
    }

    public void a(GrsBaseInfo grsBaseInfo) {
        this.a.a(grsBaseInfo);
    }

    public Set<String> b() {
        return this.a.c();
    }
}
