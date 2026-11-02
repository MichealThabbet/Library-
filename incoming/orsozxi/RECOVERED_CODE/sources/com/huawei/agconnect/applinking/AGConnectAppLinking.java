package com.huawei.agconnect.applinking;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.applinking.a.a;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.hmf.tasks.Task;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class AGConnectAppLinking {
    private static final Map<String, AGConnectAppLinking> SERVICES_MAP = new HashMap();

    public static AGConnectAppLinking getInstance() {
        AGConnectInstance aGConnectInstance = AGConnectInstance.getInstance();
        if (aGConnectInstance != null) {
            return getInstance(aGConnectInstance);
        }
        throw new NullPointerException("The AGConnect SDK is not initialized. Please call the 'AGConnectInstance.initialize()' method first.");
    }

    public static AGConnectAppLinking getInstance(AGConnectInstance aGConnectInstance) {
        if (aGConnectInstance == null) {
            throw new NullPointerException("AGConnectInstance can not be null.");
        }
        AGConnectAppLinking aVar = (AGConnectAppLinking) aGConnectInstance.getService(a.class);
        if (aVar == null) {
            synchronized (AGConnectAppLinking.class) {
                aVar = SERVICES_MAP.get(aGConnectInstance.getIdentifier());
                if (aVar == null) {
                    Logger.i("AGConnectAppLinking", "init appLinking with constructor.");
                    aVar = new a(aGConnectInstance.getContext(), aGConnectInstance);
                    SERVICES_MAP.put(aGConnectInstance.getIdentifier(), aVar);
                }
            }
        }
        return aVar;
    }

    public abstract Task<ResolvedLinkData> getAppLinking(Activity activity);

    @Deprecated
    public abstract Task<ResolvedLinkData> getAppLinking(Activity activity, Intent intent);

    @Deprecated
    public abstract Task<ResolvedLinkData> getAppLinking(Activity activity, Uri uri);

    @Deprecated
    public abstract void setCustomReferrer(ReferrerProvider referrerProvider);
}
