package com.huawei.hms.feature.dynamic.a;

import android.content.Context;
import android.os.Bundle;
import com.huawei.hms.common.util.Logger;
import com.huawei.hms.feature.dynamic.DynamicModule;

/* loaded from: classes2.dex */
public class d implements DynamicModule.VersionPolicy {
    private static final String a = d.class.getSimpleName();

    @Override // com.huawei.hms.feature.dynamic.DynamicModule.VersionPolicy
    public Bundle getModuleInfo(Context context, String str) throws DynamicModule.LoadingException {
        Bundle remoteModuleInfo = DynamicModule.getRemoteModuleInfo(context, str);
        Bundle localModuleInfo = DynamicModule.getLocalModuleInfo(context, str);
        Logger.i(a, "The version of remote module " + str + ":" + remoteModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.j));
        Logger.i(a, "The version of local module " + str + ":" + localModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.k));
        if (remoteModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.j) >= localModuleInfo.getInt(com.huawei.hms.feature.dynamic.b.k)) {
            Logger.i(a, "Choose remote module info.");
            return remoteModuleInfo;
        }
        Logger.i(a, "Choose local module info.");
        return localModuleInfo;
    }
}
