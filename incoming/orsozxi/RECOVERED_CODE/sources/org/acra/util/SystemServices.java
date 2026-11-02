package org.acra.util;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.os.DropBoxManager;
import android.telephony.TelephonyManager;
import com.facebook.places.model.PlaceFields;
import com.huawei.hms.push.constant.RemoteMessageConst;

/* loaded from: classes3.dex */
public final class SystemServices {
    private SystemServices() {
    }

    public static TelephonyManager getTelephonyManager(Context context) throws ServiceNotReachedException {
        return (TelephonyManager) getService(context, PlaceFields.PHONE);
    }

    public static DropBoxManager getDropBoxManager(Context context) throws ServiceNotReachedException {
        return (DropBoxManager) getService(context, "dropbox");
    }

    public static NotificationManager getNotificationManager(Context context) throws ServiceNotReachedException {
        return (NotificationManager) getService(context, RemoteMessageConst.NOTIFICATION);
    }

    public static ActivityManager getActivityManager(Context context) throws ServiceNotReachedException {
        return (ActivityManager) getService(context, "activity");
    }

    private static Object getService(Context context, String str) throws ServiceNotReachedException {
        Object systemService = context.getSystemService(str);
        if (systemService != null) {
            return systemService;
        }
        throw new ServiceNotReachedException("Unable to load SystemService " + str);
    }

    static class ServiceNotReachedException extends Exception {
        ServiceNotReachedException(String str) {
            super(str);
        }
    }
}
