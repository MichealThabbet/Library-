package com.huawei.hms.analytics;

import android.text.TextUtils;
import com.huawei.hms.analytics.i;

/* loaded from: classes2.dex */
public final class af implements i.lmn {
    private static ad klm;
    private static final af lmn = new af();

    private static void klm(String str, String str2, String str3) {
        ar.lmn().lmn.h = str;
        ar.lmn().lmn.j = str2;
        ar.lmn().lmn.i = str3;
    }

    /* JADX WARN: Removed duplicated region for block: B:82:0x0174  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void lmn(android.content.Context r10, java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 431
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.af.lmn(android.content.Context, java.lang.String):void");
    }

    @Override // com.huawei.hms.analytics.i.lmn
    public final void lmn(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str3)) {
            str = cy.lmn().klm();
            str2 = "";
            str3 = "aaid";
        }
        klm(str, str2, str3);
    }
}
