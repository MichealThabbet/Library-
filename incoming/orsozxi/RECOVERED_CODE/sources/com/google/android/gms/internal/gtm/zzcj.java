package com.google.android.gms.internal.gtm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes.dex */
class zzcj extends BroadcastReceiver {
    private static final String zzabm = zzcj.class.getName();
    private boolean zzabn;
    private boolean zzabo;
    private final zzap zzwc;

    zzcj(zzap zzapVar) {
        Preconditions.checkNotNull(zzapVar);
        this.zzwc = zzapVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        zzfp();
        String action = intent.getAction();
        this.zzwc.zzco().zza("NetworkBroadcastReceiver received action", action);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            boolean zZzfr = zzfr();
            if (this.zzabo != zZzfr) {
                this.zzabo = zZzfr;
                zzae zzaeVarZzcs = this.zzwc.zzcs();
                zzaeVarZzcs.zza("Network connectivity status changed", Boolean.valueOf(zZzfr));
                zzaeVarZzcs.zzcq().zza(new zzag(zzaeVarZzcs, zZzfr));
                return;
            }
            return;
        }
        if ("com.google.analytics.RADIO_POWERED".equals(action)) {
            if (intent.hasExtra(zzabm)) {
                return;
            }
            zzae zzaeVarZzcs2 = this.zzwc.zzcs();
            zzaeVarZzcs2.zzq("Radio powered up");
            zzaeVarZzcs2.zzci();
            return;
        }
        this.zzwc.zzco().zzd("NetworkBroadcastReceiver received unknown action", action);
    }

    public final void zzfo() {
        zzfp();
        if (this.zzabn) {
            return;
        }
        Context context = this.zzwc.getContext();
        context.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        IntentFilter intentFilter = new IntentFilter("com.google.analytics.RADIO_POWERED");
        intentFilter.addCategory(context.getPackageName());
        context.registerReceiver(this, intentFilter);
        this.zzabo = zzfr();
        this.zzwc.zzco().zza("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzabo));
        this.zzabn = true;
    }

    private final void zzfp() {
        this.zzwc.zzco();
        this.zzwc.zzcs();
    }

    public final void unregister() {
        if (this.zzabn) {
            this.zzwc.zzco().zzq("Unregistering connectivity change receiver");
            this.zzabn = false;
            this.zzabo = false;
            try {
                this.zzwc.getContext().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                this.zzwc.zzco().zze("Failed to unregister the network broadcast receiver", e);
            }
        }
    }

    public final void zzfq() {
        Context context = this.zzwc.getContext();
        Intent intent = new Intent("com.google.analytics.RADIO_POWERED");
        intent.addCategory(context.getPackageName());
        intent.putExtra(zzabm, true);
        context.sendOrderedBroadcast(intent, null);
    }

    public final boolean isConnected() {
        if (!this.zzabn) {
            this.zzwc.zzco().zzt("Connectivity unknown. Receiver not registered");
        }
        return this.zzabo;
    }

    private final boolean zzfr() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.zzwc.getContext().getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.isConnected()) {
                    return true;
                }
            }
        } catch (SecurityException unused) {
        }
        return false;
    }
}
