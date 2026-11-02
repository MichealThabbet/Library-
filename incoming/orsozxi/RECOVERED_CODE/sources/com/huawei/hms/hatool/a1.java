package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public abstract class a1 {
    public final x0 a(int i) {
        String strF;
        if (i != 0) {
            strF = f();
            if (!TextUtils.isEmpty(strF)) {
                return new x0(y0.UDID, strF);
            }
        } else {
            strF = "";
        }
        return new x0(y0.EMPTY, strF);
    }

    public x0 a(Context context) {
        String strC = c();
        if (!TextUtils.isEmpty(strC)) {
            return new x0(y0.UDID, strC);
        }
        String strA = a();
        if (!TextUtils.isEmpty(strA)) {
            return new x0(y0.IMEI, strA);
        }
        boolean zE = e();
        String strB = b();
        return !TextUtils.isEmpty(strB) ? zE ? new x0(y0.SN, strB) : new x0(y0.UDID, a(strB)) : zE ? a(d()) : b(d());
    }

    public abstract String a();

    public abstract String a(String str);

    public final x0 b(int i) {
        String strF;
        if ((i & 4) != 0) {
            strF = f();
            if (!TextUtils.isEmpty(strF)) {
                return new x0(y0.UDID, strF);
            }
        } else {
            strF = "";
        }
        return new x0(y0.EMPTY, strF);
    }

    public abstract String b();

    public abstract String c();

    public abstract int d();

    public final boolean e() {
        l lVarB = i.c().b();
        if (TextUtils.isEmpty(lVarB.l())) {
            lVarB.h(f.a());
        }
        return !TextUtils.isEmpty(lVarB.l());
    }

    public final String f() {
        l lVarB = i.c().b();
        if (TextUtils.isEmpty(lVarB.i())) {
            lVarB.e(b1.c());
        }
        return lVarB.i();
    }
}
