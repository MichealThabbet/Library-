package com.google.android.gms.internal.gtm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;

/* loaded from: classes.dex */
public final class zzav implements ServiceConnection {
    final /* synthetic */ zzat zzxe;
    private volatile zzce zzxf;
    private volatile boolean zzxg;

    protected zzav(zzat zzatVar) {
        this.zzxe = zzatVar;
    }

    public final zzce zzdq() {
        com.google.android.gms.analytics.zzk.zzav();
        Intent intent = new Intent("com.google.android.gms.analytics.service.START");
        intent.setComponent(new ComponentName("com.google.android.gms", "com.google.android.gms.analytics.service.AnalyticsService"));
        Context context = this.zzxe.getContext();
        intent.putExtra("app_package_name", context.getPackageName());
        ConnectionTracker connectionTracker = ConnectionTracker.getInstance();
        synchronized (this) {
            this.zzxf = null;
            this.zzxg = true;
            boolean zBindService = connectionTracker.bindService(context, intent, this.zzxe.zzxa, 129);
            this.zzxe.zza("Bind to service requested", Boolean.valueOf(zBindService));
            if (!zBindService) {
                this.zzxg = false;
                return null;
            }
            try {
                wait(zzby.zzaak.get().longValue());
            } catch (InterruptedException unused) {
                this.zzxe.zzt("Wait for service connect was interrupted");
            }
            this.zzxg = false;
            zzce zzceVar = this.zzxf;
            this.zzxf = null;
            if (zzceVar == null) {
                this.zzxe.zzu("Successfully bound to service but never got onServiceConnected callback");
            }
            return zzceVar;
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzce zzcfVar;
        Preconditions.checkMainThread("AnalyticsServiceConnection.onServiceConnected");
        synchronized (this) {
            try {
                if (iBinder == null) {
                    this.zzxe.zzu("Service connected with null binder");
                    return;
                }
                zzce zzceVar = null;
                try {
                    String interfaceDescriptor = iBinder.getInterfaceDescriptor();
                    if ("com.google.android.gms.analytics.internal.IAnalyticsService".equals(interfaceDescriptor)) {
                        if (iBinder != null) {
                            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.analytics.internal.IAnalyticsService");
                            if (iInterfaceQueryLocalInterface instanceof zzce) {
                                zzcfVar = (zzce) iInterfaceQueryLocalInterface;
                            } else {
                                zzcfVar = new zzcf(iBinder);
                            }
                            zzceVar = zzcfVar;
                        }
                        this.zzxe.zzq("Bound to IAnalyticsService interface");
                    } else {
                        this.zzxe.zze("Got binder with a wrong descriptor", interfaceDescriptor);
                    }
                } catch (RemoteException unused) {
                    this.zzxe.zzu("Service connect failed to get IAnalyticsService");
                }
                if (zzceVar != null) {
                    if (!this.zzxg) {
                        this.zzxe.zzt("onServiceConnected received after the timeout limit");
                        this.zzxe.zzcq().zza(new zzaw(this, zzceVar));
                    } else {
                        this.zzxf = zzceVar;
                    }
                } else {
                    try {
                        ConnectionTracker.getInstance().unbindService(this.zzxe.getContext(), this.zzxe.zzxa);
                    } catch (IllegalArgumentException unused2) {
                    }
                }
            } finally {
                notifyAll();
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        Preconditions.checkMainThread("AnalyticsServiceConnection.onServiceDisconnected");
        this.zzxe.zzcq().zza(new zzax(this, componentName));
    }
}
