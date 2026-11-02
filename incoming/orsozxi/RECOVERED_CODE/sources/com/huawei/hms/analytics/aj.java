package com.huawei.hms.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.i;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public final class aj extends ad {
    private Object hij;
    private Class<?> ijk;

    public aj(Context context, i.lmn lmnVar) throws ClassNotFoundException {
        super(context, lmnVar, new v(context, lmnVar));
        try {
            Class<?> cls = Class.forName("com.android.id.impl.IdProviderImpl");
            this.ijk = cls;
            this.hij = cls.newInstance();
        } catch (Exception unused) {
            HiLog.w("miOaid", "invoke class Exception");
        }
    }

    private String ijk() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Object objInvoke = this.ijk.getMethod("getOAID", Context.class).invoke(this.hij, this.klm);
            return objInvoke instanceof String ? (String) objInvoke : "";
        } catch (Exception unused) {
            HiLog.w("miOaid", "invoke method Exception");
            return "";
        }
    }

    @Override // com.huawei.hms.analytics.ad
    public final boolean klm() {
        return this.hij != null;
    }

    @Override // com.huawei.hms.analytics.ad
    public final void lmn() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String strIjk = ijk();
        this.lmn.lmn(strIjk, TextUtils.isEmpty(strIjk) ? "false" : "", "oaid");
    }
}
