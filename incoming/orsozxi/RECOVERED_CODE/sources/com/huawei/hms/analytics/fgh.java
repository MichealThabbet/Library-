package com.huawei.hms.analytics;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.analytics.receiver.SafeIntent;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class fgh {
    private static fgh lmn = new fgh();
    private final Map<String, bh> klm = new LinkedHashMap();

    public static void klm(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            SafeIntent safeIntent = new SafeIntent(activity.getIntent());
            bw bwVarLmn = cz.lmn(activity);
            if (TextUtils.isEmpty(ar.lmn().lmn.d)) {
                cz.lmn(activity, bwVarLmn);
            }
            if (TextUtils.isEmpty(ar.lmn().lmn.e)) {
                cz.klm(bwVarLmn);
            }
            ar.lmn().lmn.cde = safeIntent.getAction();
        }
    }

    public static fgh lmn() {
        return lmn;
    }

    public static void lmn(Context context) {
        dl.lmn(context, "global_v2", "is_analytics_initialized", 1);
    }

    public static void lmn(Context context, bh bhVar) {
        Application applicationLmn = context instanceof Application ? (Application) context : dg.lmn();
        a aVarLmn = a.lmn(applicationLmn);
        aVarLmn.ikl = bhVar;
        if (aVarLmn.lmn) {
            aVarLmn.lmn();
        }
        bcd.lmn().def = bhVar;
        abc.lmn().lmn(applicationLmn);
    }

    public static void lmn(hij hijVar) {
        cx.lmn().lmn(hijVar);
    }

    public static boolean lmn(String str, String str2) {
        if (str.equals(GrsBaseInfo.CountryCodeSource.UNKNOWN)) {
            str = ar.lmn().lmn.n.ghi;
        }
        return cde.lmn(ar.lmn().lmn.ghi, str2).lmn(str);
    }

    public final void klm() {
        for (Map.Entry<String, bh> entry : this.klm.entrySet()) {
            if (!entry.getKey().equals("_openness_config_tag")) {
                entry.getValue().lmn();
            }
        }
    }

    public final bh lmn(String str) {
        return this.klm.get(str);
    }

    public final void lmn(String str, bh bhVar) {
        this.klm.put(str, bhVar);
    }
}
