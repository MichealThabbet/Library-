package com.huawei.agconnect.applinking;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.huawei.agconnect.applinking.AppLinkingConfig;
import com.huawei.agconnect.applinking.a.a;
import com.huawei.agconnect.common.appinfo.SafeAppInfo;
import com.huawei.agconnect.core.Service;
import com.huawei.agconnect.core.ServiceRegistrar;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class AppLinkingRegistrar implements ServiceRegistrar {
    private void initReadClipboardPermission(Context context) {
        ApplicationInfo applicationInfoSafeGetApplicationInfo = SafeAppInfo.safeGetApplicationInfo(context.getPackageManager(), context.getPackageName(), 128);
        String string = applicationInfoSafeGetApplicationInfo.metaData != null ? applicationInfoSafeGetApplicationInfo.metaData.getString("com.huawei.agconnect.applinking.READ_CLIPBOARD_PERMISSION") : null;
        AppLinkingConfig.Builder builder = new AppLinkingConfig.Builder();
        if ("Unavailable".equals(string)) {
            builder.setDisableClipboardCheck();
        }
        if ("Available".equals(string)) {
            builder.setEnableClipboardAlways();
        }
        builder.asDefault();
    }

    @Override // com.huawei.agconnect.core.ServiceRegistrar
    public List<Service> getServices(Context context) {
        return Collections.singletonList(Service.builder(a.class).isSingleton(true).build());
    }

    @Override // com.huawei.agconnect.core.ServiceRegistrar
    public void initialize(Context context) {
        initReadClipboardPermission(context);
    }
}
