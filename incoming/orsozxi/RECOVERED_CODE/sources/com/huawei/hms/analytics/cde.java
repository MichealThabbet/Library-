package com.huawei.hms.analytics;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class cde {
    private static final Map<String, cde> ikl = new HashMap();
    String klm;
    final String lmn;

    private cde(Context context, String str) {
        this.lmn = str;
        String strKlm = dl.klm(context, "Privacy_MY", str + "_region", "");
        this.klm = strKlm;
        if (TextUtils.isEmpty(strKlm)) {
            this.klm = ar.lmn().lmn.n.ghi;
            dl.lmn(context, "Privacy_MY", str + "_region", this.klm);
        }
    }

    public static cde lmn(Context context, String str) {
        if (ikl.containsKey(str)) {
            return ikl.get(str);
        }
        cde cdeVar = new cde(context, str);
        ikl.put(str, cdeVar);
        return cdeVar;
    }

    public final void lmn() {
        bh bhVarLmn = fgh.lmn().lmn(this.lmn);
        if (bhVarLmn != null) {
            if ("_openness_config_tag".equals(this.lmn)) {
                bhVarLmn.klm(this.klm);
            }
            bhVarLmn.ikl(this.klm);
        }
    }

    public final boolean lmn(String str) {
        return (TextUtils.isEmpty(this.klm) || str.equals(this.klm)) ? false : true;
    }
}
