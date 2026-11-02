package com.google.android.gms.internal.gtm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.stats.WakeLock;

/* loaded from: classes.dex */
public final class zzcp {
    static Object lock = new Object();
    static WakeLock zzacb;
    private static Boolean zzri;

    public static boolean zza(Context context) throws PackageManager.NameNotFoundException {
        Preconditions.checkNotNull(context);
        Boolean bool = zzri;
        if (bool != null) {
            return bool.booleanValue();
        }
        boolean zZza = zzcz.zza(context, "com.google.android.gms.analytics.AnalyticsReceiver", false);
        zzri = Boolean.valueOf(zZza);
        return zZza;
    }

    public static void onReceive(Context context, Intent intent) {
        zzci zzciVarZzco = zzap.zzc(context).zzco();
        if (intent == null) {
            zzciVarZzco.zzt("AnalyticsReceiver called with null intent");
            return;
        }
        String action = intent.getAction();
        zzciVarZzco.zza("Local AnalyticsReceiver got", action);
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
            boolean zZze = zzcq.zze(context);
            Intent intent2 = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            intent2.setComponent(new ComponentName(context, "com.google.android.gms.analytics.AnalyticsService"));
            intent2.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            synchronized (lock) {
                context.startService(intent2);
                if (zZze) {
                    try {
                        if (zzacb == null) {
                            WakeLock wakeLock = new WakeLock(context, 1, "Analytics WakeLock");
                            zzacb = wakeLock;
                            wakeLock.setReferenceCounted(false);
                        }
                        zzacb.acquire(1000L);
                    } catch (SecurityException unused) {
                        zzciVarZzco.zzt("Analytics service at risk of not starting. For more reliable analytics, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                    }
                }
            }
        }
    }
}
