package com.huawei.hms.analytics;

import android.content.Context;
import android.os.Build;
import androidx.mediarouter.media.SystemMediaRouteProvider;
import com.sun.mail.imap.IMAPStore;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class di {
    private static Context klm;
    private static final Set<String> lmn = new HashSet();

    private static String lmn() {
        at atVar = ar.lmn().lmn;
        JSONObject jSONObject = new JSONObject();
        ay ayVar = atVar.n;
        try {
            jSONObject.put("app_id", (ayVar == null || ayVar.klm == null) ? "" : ayVar.klm);
            jSONObject.put("lib_ver", "6.6.0.300");
            jSONObject.put("app_ver", atVar.klm == null ? dg.hij(klm) : atVar.klm);
            jSONObject.put("package_name", klm == null ? "" : klm.getPackageName());
            jSONObject.put("model", Build.MODEL);
            jSONObject.put("manufacturer", Build.MANUFACTURER);
            jSONObject.put("lib_type", SystemMediaRouteProvider.PACKAGE_NAME);
            jSONObject.put(IMAPStore.ID_OS, SystemMediaRouteProvider.PACKAGE_NAME);
            jSONObject.put("os_ver", dg.ijk());
            return jSONObject.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static void lmn(Context context, Throwable th) {
        klm = context;
        lmn(th);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0044 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0046 A[Catch: all -> 0x0102, TRY_ENTER, TRY_LEAVE, TryCatch #0 {, blocks: (B:7:0x0007, B:9:0x000b, B:10:0x0015, B:12:0x001b, B:14:0x001f, B:16:0x0023, B:29:0x0046, B:33:0x0054, B:35:0x006b, B:37:0x0074, B:39:0x0077, B:41:0x0082, B:42:0x0099, B:44:0x00a3, B:46:0x00a9, B:49:0x00b0, B:55:0x00da, B:57:0x00e6, B:58:0x00ef, B:36:0x0070, B:18:0x0027, B:20:0x0031, B:23:0x003a), top: B:64:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static synchronized void lmn(java.lang.Throwable r9) {
        /*
            Method dump skipped, instructions count: 261
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.di.lmn(java.lang.Throwable):void");
    }
}
