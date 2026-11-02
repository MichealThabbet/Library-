package com.huawei.hms.analytics.internal.filter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.analytics.am;
import com.huawei.hms.analytics.bj;
import com.huawei.hms.analytics.bk;
import com.huawei.hms.analytics.bl;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.dc;
import com.huawei.hms.analytics.dj;
import it.feio.android.springpadimporter.utils.Constants;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/* loaded from: classes2.dex */
public class EventFilter implements bk {
    private Context ghi;
    private Method hij;
    private Object ijk;
    private Method ikl;
    private Method klm;
    private bl lmn;

    /* JADX WARN: Removed duplicated region for block: B:11:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean klm(java.lang.String r6, android.os.Bundle r7) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r5 = this;
            java.lang.String r0 = "IncidentFilter"
            r1 = 0
            java.lang.reflect.Method r2 = r5.klm     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L22
            java.lang.Object r3 = r5.ijk     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L22
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L22
            r4[r1] = r6     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L22
            r6 = 1
            r4[r6] = r7     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L22
            java.lang.Object r6 = r2.invoke(r3, r4)     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L22
            boolean r2 = r6 instanceof java.lang.Boolean     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L22
            if (r2 == 0) goto L27
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L22
            boolean r6 = r6.booleanValue()     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L22
            r1 = r6
            goto L27
        L1f:
            java.lang.String r6 = "Invocation target warning"
            goto L24
        L22:
            java.lang.String r6 = "Illegal access warning"
        L24:
            com.huawei.hms.analytics.core.log.HiLog.w(r0, r6)
        L27:
            if (r1 != 0) goto L2c
            lmn(r7)
        L2c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.internal.filter.EventFilter.klm(java.lang.String, android.os.Bundle):boolean");
    }

    private static long lmn(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return 0L;
        }
    }

    private static void lmn(Bundle bundle) {
        bundle.remove("$HA_FIXED_TAG");
        bundle.remove("$HA_FIXED_TYPE");
        bundle.remove("$HA_FIXED_SOURCE");
        bundle.remove("$HA_FIXED_TIMESTAMP");
    }

    private static boolean lmn(String str, String str2) {
        if (!TextUtils.isEmpty(str) && "oper".equals(str2)) {
            return true;
        }
        HiLog.e("IncidentFilter", "third callback params invalid");
        return false;
    }

    public String getUserProfile(String str) {
        HiLog.d("IncidentFilter", "get user property#".concat(String.valueOf(str)));
        bl blVar = this.lmn;
        if (blVar == null) {
            return null;
        }
        return blVar.lmn(str);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x008f A[Catch: InvocationTargetException -> 0x0099, IllegalAccessException -> 0x009f, TRY_LEAVE, TryCatch #9 {IllegalAccessException -> 0x009f, InvocationTargetException -> 0x0099, blocks: (B:21:0x0084, B:23:0x008f), top: B:29:0x0084 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0084 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    @Override // com.huawei.hms.analytics.bk
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void lmn(android.content.Context r12) throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException, java.lang.ClassNotFoundException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r11 = this;
            java.lang.String r0 = "Illegal access warning"
            java.lang.String r1 = "IncidentFilter"
            r11.ghi = r12
            r2 = 0
            r3 = 0
            r4 = 1
            java.lang.String r5 = "com.huawei.hms.dtm.EventFilter"
            java.lang.Class r5 = java.lang.Class.forName(r5)     // Catch: java.lang.NoSuchMethodException -> L6d java.lang.InstantiationException -> L71 java.lang.IllegalAccessException -> L75 java.lang.ClassNotFoundException -> L7a
            java.lang.Object r6 = r5.newInstance()     // Catch: java.lang.NoSuchMethodException -> L6d java.lang.InstantiationException -> L71 java.lang.IllegalAccessException -> L75 java.lang.ClassNotFoundException -> L7a
            r11.ijk = r6     // Catch: java.lang.NoSuchMethodException -> L6d java.lang.InstantiationException -> L71 java.lang.IllegalAccessException -> L75 java.lang.ClassNotFoundException -> L7a
            java.lang.String r6 = "setLogCallback"
            java.lang.Class[] r7 = new java.lang.Class[r4]     // Catch: java.lang.NoSuchMethodException -> L6d java.lang.InstantiationException -> L71 java.lang.IllegalAccessException -> L75 java.lang.ClassNotFoundException -> L7a
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r7[r3] = r8     // Catch: java.lang.NoSuchMethodException -> L6d java.lang.InstantiationException -> L71 java.lang.IllegalAccessException -> L75 java.lang.ClassNotFoundException -> L7a
            java.lang.reflect.Method r6 = r5.getDeclaredMethod(r6, r7)     // Catch: java.lang.NoSuchMethodException -> L6d java.lang.InstantiationException -> L71 java.lang.IllegalAccessException -> L75 java.lang.ClassNotFoundException -> L7a
            java.lang.String r7 = "initialize"
            java.lang.Class[] r8 = new java.lang.Class[r4]     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            java.lang.Class<android.content.Context> r9 = android.content.Context.class
            r8[r3] = r9     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            java.lang.reflect.Method r2 = r5.getDeclaredMethod(r7, r8)     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            java.lang.String r7 = "logEvent"
            r8 = 2
            java.lang.Class[] r9 = new java.lang.Class[r8]     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            java.lang.Class<java.lang.String> r10 = java.lang.String.class
            r9[r3] = r10     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            java.lang.Class<android.os.Bundle> r10 = android.os.Bundle.class
            r9[r4] = r10     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            java.lang.reflect.Method r7 = r5.getDeclaredMethod(r7, r9)     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            r11.klm = r7     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            java.lang.String r7 = "logAutoEvent"
            java.lang.Class[] r8 = new java.lang.Class[r8]     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            java.lang.Class<java.lang.String> r9 = java.lang.String.class
            r8[r3] = r9     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            java.lang.Class<android.os.Bundle> r9 = android.os.Bundle.class
            r8[r4] = r9     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            java.lang.reflect.Method r7 = r5.getDeclaredMethod(r7, r8)     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            r11.ikl = r7     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            java.lang.String r7 = "setParameter"
            java.lang.Class[] r8 = new java.lang.Class[r4]     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            java.lang.Class<java.util.Map> r9 = java.util.Map.class
            r8[r3] = r9     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            java.lang.reflect.Method r5 = r5.getDeclaredMethod(r7, r8)     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            r11.hij = r5     // Catch: java.lang.NoSuchMethodException -> L61 java.lang.InstantiationException -> L64 java.lang.IllegalAccessException -> L67 java.lang.ClassNotFoundException -> L6a
            goto L82
        L61:
            r5 = r2
            r2 = r6
            goto L6e
        L64:
            r5 = r2
            r2 = r6
            goto L72
        L67:
            r5 = r2
            r2 = r6
            goto L76
        L6a:
            r5 = r2
            r2 = r6
            goto L7b
        L6d:
            r5 = r2
        L6e:
            java.lang.String r6 = "No such method warning"
            goto L7d
        L71:
            r5 = r2
        L72:
            java.lang.String r6 = "Instantiation warning"
            goto L7d
        L75:
            r5 = r2
        L76:
            com.huawei.hms.analytics.core.log.HiLog.w(r1, r0)
            goto L80
        L7a:
            r5 = r2
        L7b:
            java.lang.String r6 = "Class not found warning"
        L7d:
            com.huawei.hms.analytics.core.log.HiLog.w(r1, r6)
        L80:
            r6 = r2
            r2 = r5
        L82:
            if (r6 == 0) goto L8d
            java.lang.Object r5 = r11.ijk     // Catch: java.lang.reflect.InvocationTargetException -> L99 java.lang.IllegalAccessException -> L9f
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch: java.lang.reflect.InvocationTargetException -> L99 java.lang.IllegalAccessException -> L9f
            r7[r3] = r11     // Catch: java.lang.reflect.InvocationTargetException -> L99 java.lang.IllegalAccessException -> L9f
            r6.invoke(r5, r7)     // Catch: java.lang.reflect.InvocationTargetException -> L99 java.lang.IllegalAccessException -> L9f
        L8d:
            if (r2 == 0) goto La2
            java.lang.Object r5 = r11.ijk     // Catch: java.lang.reflect.InvocationTargetException -> L99 java.lang.IllegalAccessException -> L9f
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.reflect.InvocationTargetException -> L99 java.lang.IllegalAccessException -> L9f
            r4[r3] = r12     // Catch: java.lang.reflect.InvocationTargetException -> L99 java.lang.IllegalAccessException -> L9f
            r2.invoke(r5, r4)     // Catch: java.lang.reflect.InvocationTargetException -> L99 java.lang.IllegalAccessException -> L9f
            goto La2
        L99:
            java.lang.String r12 = "Invocation target warning"
            com.huawei.hms.analytics.core.log.HiLog.w(r1, r12)
            return
        L9f:
            com.huawei.hms.analytics.core.log.HiLog.w(r1, r0)
        La2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.internal.filter.EventFilter.lmn(android.content.Context):void");
    }

    @Override // com.huawei.hms.analytics.bk
    public final void lmn(bl blVar) {
        this.lmn = blVar;
    }

    @Override // com.huawei.hms.analytics.bk
    public final void lmn(String str, Bundle bundle) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str2;
        Method method = this.ikl;
        if (method == null) {
            return;
        }
        try {
            method.invoke(this.ijk, str, bundle);
        } catch (IllegalAccessException unused) {
            str2 = "Illegal access warning";
            HiLog.w("IncidentFilter", str2);
        } catch (InvocationTargetException unused2) {
            str2 = "Invocation target warning";
            HiLog.w("IncidentFilter", str2);
        }
    }

    @Override // com.huawei.hms.analytics.bk
    public final void lmn(Map<Object, Object> map) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str;
        if (this.hij == null) {
            str = "analytics callback init failed, pls check log.";
        } else if (map.size() <= 0) {
            str = "params is empty,Not need notify DTM";
        } else {
            try {
                this.hij.invoke(this.ijk, map);
                return;
            } catch (IllegalAccessException unused) {
                str = "Illegal access warning";
            } catch (InvocationTargetException unused2) {
                str = "Invocation target warning";
            }
        }
        HiLog.w("IncidentFilter", str);
    }

    @Override // com.huawei.hms.analytics.bk
    public final boolean lmn(String str, String str2, String str3, Bundle bundle) {
        if (this.klm == null) {
            return false;
        }
        bundle.putString("$HA_FIXED_TAG", str);
        bundle.putString("$HA_FIXED_TYPE", str2);
        bundle.putString("$HA_FIXED_SOURCE", Constants.TYPE_EVENT);
        return klm(str3, bundle);
    }

    @Override // com.huawei.hms.analytics.bk
    public final boolean lmn(String str, String str2, String str3, Bundle bundle, long j) {
        if (this.klm == null) {
            return false;
        }
        bundle.putString("$HA_FIXED_TAG", str);
        bundle.putString("$HA_FIXED_TYPE", str2);
        bundle.putString("$HA_FIXED_SOURCE", "StreamEvent");
        bundle.putString("$HA_FIXED_TIMESTAMP", String.valueOf(j));
        return klm(str3, bundle);
    }

    public void logFilteredEvent(String str, Bundle bundle) {
        HiLog.d("IncidentFilter", "log filtered evtID#".concat(String.valueOf(str)));
        if (this.lmn == null) {
            return;
        }
        if (bundle == null) {
            HiLog.e("IncidentFilter", "the params is null");
            return;
        }
        String string = bundle.getString("$HA_FIXED_SOURCE");
        String string2 = bundle.getString("$HA_FIXED_TAG");
        if (str == null || str.length() > 256 || !dc.lmn("eventId", str, am.klm)) {
            return;
        }
        String string3 = bundle.getString("$HA_FIXED_TYPE");
        lmn(bundle.getString("$HA_FIXED_TIMESTAMP"));
        lmn(bundle);
        dj djVar = new dj(str, true);
        if (!djVar.lmn(bundle)) {
            HiLog.w("IncidentFilter", "bundle params is invalid.");
            return;
        }
        if (Constants.TYPE_EVENT.equals(string)) {
            HiLog.i("IncidentFilter", "begin onEventFiltered");
            if (lmn(string2, string3)) {
                this.lmn.lmn(string2, str, djVar.ikl);
                bj.lmn(this.ghi);
                bj.klm().lmn(string2, "oper", str, djVar.lmn);
                return;
            }
            return;
        }
        if (!"StreamEvent".equals(string)) {
            HiLog.i("IncidentFilter", "begin onEventFiltered with default");
            this.lmn.lmn("_openness_config_tag", str, djVar.ikl);
            bj.lmn(this.ghi);
            bj.klm().lmn(string2, "oper", str, djVar.lmn);
            return;
        }
        HiLog.i("IncidentFilter", "begin onStreamEventFiltered");
        if (lmn(string2, string3)) {
            this.lmn.klm(string2, str, djVar.ikl);
            bj.lmn(this.ghi);
            bj.klm().lmn(string2, "oper", str, djVar.lmn, System.currentTimeMillis());
        }
    }
}
