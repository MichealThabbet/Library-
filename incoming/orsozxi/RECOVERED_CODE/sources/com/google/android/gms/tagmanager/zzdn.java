package com.google.android.gms.tagmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/* loaded from: classes.dex */
class zzdn extends BroadcastReceiver {
    private static final String zzabm = zzdn.class.getName();
    private final zzfm zzaic;

    zzdn(zzfm zzfmVar) {
        this.zzaic = zzfmVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            Bundle extras = intent.getExtras();
            Boolean boolValueOf = Boolean.FALSE;
            if (extras != null) {
                boolValueOf = Boolean.valueOf(intent.getExtras().getBoolean("noConnectivity"));
            }
            this.zzaic.zzf(!boolValueOf.booleanValue());
            return;
        }
        if (!"com.google.analytics.RADIO_POWERED".equals(action) || intent.hasExtra(zzabm)) {
            return;
        }
        this.zzaic.zzjp();
    }

    public static void zzn(Context context) {
        Intent intent = new Intent("com.google.analytics.RADIO_POWERED");
        intent.addCategory(context.getPackageName());
        intent.putExtra(zzabm, true);
        context.sendBroadcast(intent);
    }
}
