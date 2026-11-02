package com.huawei.hms.feature.dynamic.a;

import android.content.Context;
import android.os.Bundle;
import com.huawei.hms.common.util.Logger;
import com.huawei.hms.feature.dynamic.DynamicModule;

/* loaded from: classes2.dex */
public class c implements DynamicModule.VersionPolicy {
    private static final String a = c.class.getSimpleName();

    @Override // com.huawei.hms.feature.dynamic.DynamicModule.VersionPolicy
    public Bundle getModuleInfo(Context context, String str) throws DynamicModule.LoadingException {
        Bundle bundle;
        try {
            bundle = DynamicModule.getRemoteModuleInfo(context, str);
            e = null;
        } catch (DynamicModule.LoadingException e) {
            e = e;
            Logger.w(a, "Get remote module info failed: " + e.getMessage() + ". try to query local.");
            bundle = new Bundle();
        }
        Bundle localModuleInfo = DynamicModule.getLocalModuleInfo(context, str);
        Logger.i(a, "The version of remote module " + str + ":" + bundle.getInt(com.huawei.hms.feature.dynamic.b.j));
        Logger.i(a, "The version of local module " + str + ":" + localModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.k));
        if (localModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.k) > 0 && localModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.k) >= bundle.getInt(com.huawei.hms.feature.dynamic.b.j)) {
            Logger.i(a, "Choose local module info.");
            return localModuleInfo;
        }
        if (e != null && bundle.getInt(com.huawei.hms.feature.dynamic.b.j) == 0) {
            throw e;
        }
        Logger.i(a, "Choose remote module info.");
        return bundle;
    }
}
