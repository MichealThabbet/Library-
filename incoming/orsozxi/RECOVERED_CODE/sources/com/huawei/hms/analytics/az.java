package com.huawei.hms.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.push.HmsMessaging;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public abstract class az {
    private Object[] ijk;
    private Class<?>[] ikl;
    private String klm;
    private String lmn;

    public static class ijk extends az {
        private final Context lmn;

        public ijk(Context context) {
            super("com.vivo.push.PushClient", "getInstance", new Class[]{Context.class}, new Object[]{context});
            this.lmn = context;
        }

        @Override // com.huawei.hms.analytics.az
        public final Object lmn() throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
            try {
                Class<?> cls = Class.forName("com.vivo.push.PushClient");
                Object objInvoke = cls.getMethod("getInstance", Context.class).invoke(null, this.lmn);
                if (objInvoke != null) {
                    return cls.getDeclaredMethod("getRegId", new Class[0]).invoke(cls.cast(objInvoke), new Object[0]);
                }
            } catch (Exception unused) {
                HiLog.i("PtokenAccess", "invoke token Exception");
            }
            return null;
        }
    }

    public static class ikl extends az {
        public ikl() {
            super("com.heytap.msp.push.HeytapPushManager", "getRegisterID", null, null);
        }
    }

    public static class klm extends az {
        public klm(Context context) {
            super("com.xiaomi.mipush.sdk.MiPushClient", "getRegId", new Class[]{Context.class}, new Object[]{context});
        }
    }

    public static class lmn extends az {
        private final Context lmn;

        public lmn(Context context) {
            super(null, null, null, null);
            this.lmn = context;
        }

        private String lmn(String str) {
            try {
                return HmsInstanceId.getInstance(this.lmn).getToken(str, HmsMessaging.DEFAULT_TOKEN_SCOPE);
            } catch (ApiException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        public final String klm() {
            String strLmn;
            String str = ar.lmn().lmn.n.klm;
            try {
                strLmn = lmn(str);
            } catch (IllegalArgumentException e) {
                e = e;
                strLmn = "";
            }
            try {
                return TextUtils.isEmpty(strLmn) ? lmn(str) : strLmn;
            } catch (IllegalArgumentException e2) {
                e = e2;
                HiLog.w("PtokenAccess", e.getMessage());
                return strLmn;
            }
        }
    }

    az(String str, String str2, Class<?>[] clsArr, Object[] objArr) {
        this.lmn = str;
        this.klm = str2;
        this.ikl = clsArr;
        this.ijk = objArr;
    }

    public Object lmn() {
        try {
            return Class.forName(this.lmn).getMethod(this.klm, this.ikl).invoke(null, this.ijk);
        } catch (Exception unused) {
            HiLog.i("PtokenAccess", "invokeStaticFun() Exception");
            return null;
        }
    }
}
