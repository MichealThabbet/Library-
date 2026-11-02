package com.huawei.hms.push;

import android.R;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import androidx.mediarouter.media.SystemMediaRouteProvider;
import com.huawei.hms.android.HwBuildEx;
import com.huawei.hms.support.log.HMSLog;

/* compiled from: NotificationIconUtil.java */
/* loaded from: classes2.dex */
public class l {
    public static int a(Context context) {
        int i = context.getApplicationInfo().icon;
        if (i != 0) {
            return i;
        }
        int identifier = context.getResources().getIdentifier("btn_star_big_on", "drawable", SystemMediaRouteProvider.PACKAGE_NAME);
        HMSLog.d("PushSelfShowLog", "icon is btn_star_big_on ");
        if (identifier != 0) {
            return identifier;
        }
        HMSLog.d("PushSelfShowLog", "icon is sym_def_app_icon ");
        return R.drawable.sym_def_app_icon;
    }

    public static int b(Context context, k kVar) throws NoSuchFieldException, PackageManager.NameNotFoundException, NumberFormatException {
        int iA = 0;
        if (context == null || kVar == null) {
            HMSLog.i("PushSelfShowLog", "enter getSmallIconId, context or msg is null");
            return 0;
        }
        if (!TextUtils.isEmpty(kVar.m())) {
            String[] strArrSplit = kVar.m().split("/");
            if (strArrSplit.length == 3) {
                iA = r.a(context, strArrSplit[1], strArrSplit[2]);
            }
        }
        if (iA == 0) {
            iA = r.a(context, "com.huawei.messaging.default_notification_icon");
        }
        return iA != 0 ? iA : a(context);
    }

    public static Bitmap a(Context context, k kVar) {
        if (context == null || kVar == null) {
            return null;
        }
        try {
            if (HwBuildEx.VERSION.EMUI_SDK_INT >= 11) {
                HMSLog.i("PushSelfShowLog", "huawei phone, and emui5.0, need not show large icon.");
                return null;
            }
            if ("com.huawei.android.pushagent".equals(kVar.k())) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("get left bitmap from ");
            sb.append(kVar.k());
            HMSLog.i("PushSelfShowLog", sb.toString());
            return ((BitmapDrawable) context.getPackageManager().getApplicationIcon(kVar.k())).getBitmap();
        } catch (PackageManager.NameNotFoundException unused) {
            HMSLog.e("PushSelfShowLog", "build left icon occur NameNotFoundException.");
            return null;
        } catch (Exception unused2) {
            HMSLog.e("PushSelfShowLog", "build left icon occur Exception.");
            return null;
        }
    }

    public static void a(Context context, Notification.Builder builder, k kVar) {
        if (context != null && builder != null && kVar != null) {
            builder.setSmallIcon(b(context, kVar));
        } else {
            HMSLog.e("PushSelfShowLog", "msg is null");
        }
    }
}
