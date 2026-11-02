package com.huawei.agconnect.credential.obs;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.huawei.hms.analytics.connector.ConnectorManager;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class aj implements ai {
    al a;
    private ConnectorManager b;

    public aj(Context context, al alVar) {
        a(context, alVar);
    }

    @Override // com.huawei.agconnect.credential.obs.ai
    public final Map<String, String> a(boolean z) {
        ConnectorManager connectorManager = this.b;
        return connectorManager != null ? connectorManager.getUserProfiles(z) : new HashMap();
    }

    @Override // com.huawei.agconnect.credential.obs.ai
    public final void a() {
        ConnectorManager connectorManager = this.b;
        if (connectorManager != null) {
            connectorManager.onReport();
        }
    }

    final void a(Context context, al alVar) {
        this.a = alVar;
        try {
            if (TextUtils.isEmpty(alVar.c)) {
                this.b = new ConnectorManager(context, alVar.a, alVar.b);
            } else {
                this.b = new ConnectorManager(context, alVar.a, alVar.b, alVar.c);
            }
        } catch (Exception | NoClassDefFoundError unused) {
            Log.e("BridgeInstance", "createInstance error");
            this.b = null;
        }
    }

    @Override // com.huawei.agconnect.credential.obs.ai
    public final void a(String str, Bundle bundle) {
        ConnectorManager connectorManager = this.b;
        if (connectorManager != null) {
            connectorManager.onEvent(str, bundle);
        }
    }
}
