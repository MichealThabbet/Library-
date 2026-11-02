package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzap;
import com.google.android.gms.internal.gtm.zzbq;
import com.google.android.gms.internal.gtm.zzci;
import com.google.android.gms.internal.gtm.zzcz;

/* loaded from: classes.dex */
public class CampaignTrackingReceiver extends BroadcastReceiver {
    private static Boolean zzri;

    protected void zza(Context context, String str) {
    }

    public static boolean zza(Context context) throws PackageManager.NameNotFoundException {
        Preconditions.checkNotNull(context);
        Boolean bool = zzri;
        if (bool != null) {
            return bool.booleanValue();
        }
        boolean zZza = zzcz.zza(context, "com.google.android.gms.analytics.CampaignTrackingReceiver", true);
        zzri = Boolean.valueOf(zZza);
        return zZza;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        zzap zzapVarZzc = zzap.zzc(context);
        zzci zzciVarZzco = zzapVarZzc.zzco();
        if (intent == null) {
            zzciVarZzco.zzt("CampaignTrackingReceiver received null intent");
            return;
        }
        String stringExtra = intent.getStringExtra("referrer");
        String action = intent.getAction();
        zzciVarZzco.zza("CampaignTrackingReceiver received", action);
        if (!"com.android.vending.INSTALL_REFERRER".equals(action) || TextUtils.isEmpty(stringExtra)) {
            zzciVarZzco.zzt("CampaignTrackingReceiver received unexpected intent without referrer extra");
            return;
        }
        zza(context, stringExtra);
        int iZzeo = zzbq.zzeo();
        if (stringExtra.length() > iZzeo) {
            zzciVarZzco.zzc("Campaign data exceed the maximum supported size and will be clipped. size, limit", Integer.valueOf(stringExtra.length()), Integer.valueOf(iZzeo));
            stringExtra = stringExtra.substring(0, iZzeo);
        }
        zzapVarZzc.zzcs().zza(stringExtra, (Runnable) new zzc(this, goAsync()));
    }
}
