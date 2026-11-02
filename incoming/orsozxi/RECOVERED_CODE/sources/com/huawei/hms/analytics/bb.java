package com.huawei.hms.analytics;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class bb extends aq {
    public bb(Context context) {
        super(context, "global_v2", "common_prop");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x004d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.huawei.hms.analytics.aq
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void lmn() {
        /*
            r5 = this;
            java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Object> r0 = r5.ikl
            if (r0 != 0) goto L59
            java.lang.String r0 = r5.ijk()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            java.lang.String r2 = "userPro"
            if (r1 == 0) goto L17
            java.lang.String r0 = "commonprop is empty"
            com.huawei.hms.analytics.core.log.HiLog.i(r2, r0)
            goto L59
        L17:
            com.huawei.hms.analytics.bo r1 = com.huawei.hms.analytics.bo.lmn()
            java.lang.String r1 = r1.klm()
            java.lang.String r3 = lmn(r1, r0)
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 == 0) goto L3d
            java.lang.String r3 = com.huawei.hms.analytics.core.crypto.AesCipher.decryptCbc(r0, r1)
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 == 0) goto L36
            java.lang.String r0 = ""
            goto L3e
        L36:
            java.lang.String r0 = klm(r1, r3)
            r5.lmn(r0)
        L3d:
            r0 = r3
        L3e:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L4d
            java.lang.String r0 = "customParams is error"
        L46:
            com.huawei.hms.analytics.core.log.HiLog.i(r2, r0)
            r5.hij()
            goto L59
        L4d:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch: org.json.JSONException -> L56
            r1.<init>(r0)     // Catch: org.json.JSONException -> L56
            r5.lmn(r1)     // Catch: org.json.JSONException -> L56
            goto L59
        L56:
            java.lang.String r0 = "cache user properties is no json"
            goto L46
        L59:
            super.lmn()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.bb.lmn():void");
    }

    @Override // com.huawei.hms.analytics.aq
    public final void lmn(Bundle bundle) {
    }

    @Override // com.huawei.hms.analytics.aq
    public final void lmn(String str, Object obj) {
        if (this.ikl == null) {
            this.ikl = new ConcurrentHashMap<>();
        }
        if (this.ikl.size() == 0 && obj == null) {
            return;
        }
        if (obj == null) {
            this.ikl.remove(str);
        } else if (lmn(25)) {
            this.ikl.put(str, obj);
        }
        String strFgh = fgh();
        if (TextUtils.isEmpty(strFgh)) {
            return;
        }
        lmn(klm(bo.lmn().klm(), strFgh));
    }
}
