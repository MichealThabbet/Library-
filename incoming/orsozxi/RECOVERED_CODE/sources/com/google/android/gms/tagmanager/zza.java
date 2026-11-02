package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;

/* loaded from: classes.dex */
public final class zza {
    private static Object zzadq = new Object();
    private static zza zzadr;
    private volatile boolean closed;
    private volatile long zzadj;
    private volatile long zzadk;
    private volatile long zzadl;
    private volatile long zzadm;
    private final Thread zzadn;
    private final Object zzado;
    private zzd zzadp;
    private final Context zzrm;
    private final Clock zzsd;
    private volatile AdvertisingIdClient.Info zzvp;

    public static zza zzf(Context context) {
        if (zzadr == null) {
            synchronized (zzadq) {
                if (zzadr == null) {
                    zza zzaVar = new zza(context);
                    zzadr = zzaVar;
                    zzaVar.zzadn.start();
                }
            }
        }
        return zzadr;
    }

    private zza(Context context) {
        this(context, null, DefaultClock.getInstance());
    }

    private zza(Context context, zzd zzdVar, Clock clock) {
        this.zzadj = 900000L;
        this.zzadk = 30000L;
        this.closed = false;
        this.zzado = new Object();
        this.zzadp = new zzb(this);
        this.zzsd = clock;
        if (context != null) {
            this.zzrm = context.getApplicationContext();
        } else {
            this.zzrm = context;
        }
        this.zzadl = this.zzsd.currentTimeMillis();
        this.zzadn = new Thread(new zzc(this));
    }

    public final String zzgq() {
        if (this.zzvp == null) {
            zzgr();
        } else {
            zzgs();
        }
        zzgt();
        if (this.zzvp == null) {
            return null;
        }
        return this.zzvp.getId();
    }

    public final boolean isLimitAdTrackingEnabled() {
        if (this.zzvp == null) {
            zzgr();
        } else {
            zzgs();
        }
        zzgt();
        return this.zzvp == null || this.zzvp.isLimitAdTrackingEnabled();
    }

    private final void zzgr() {
        synchronized (this) {
            try {
                if (!this.closed) {
                    zzgs();
                    wait(500L);
                }
            } catch (InterruptedException unused) {
            }
        }
    }

    private final void zzgs() {
        if (this.zzsd.currentTimeMillis() - this.zzadl > this.zzadk) {
            synchronized (this.zzado) {
                this.zzado.notify();
            }
            this.zzadl = this.zzsd.currentTimeMillis();
        }
    }

    private final void zzgt() {
        if (this.zzsd.currentTimeMillis() - this.zzadm > 3600000) {
            this.zzvp = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzgu() throws SecurityException, IllegalArgumentException {
        Process.setThreadPriority(10);
        while (!this.closed) {
            AdvertisingIdClient.Info infoZzgv = this.zzadp.zzgv();
            if (infoZzgv != null) {
                this.zzvp = infoZzgv;
                this.zzadm = this.zzsd.currentTimeMillis();
                zzdi.zzaw("Obtained fresh AdvertisingId info from GmsCore.");
            }
            synchronized (this) {
                notifyAll();
            }
            try {
                synchronized (this.zzado) {
                    this.zzado.wait(this.zzadj);
                }
            } catch (InterruptedException unused) {
                zzdi.zzaw("sleep interrupted in AdvertiserDataPoller thread; continuing");
            }
        }
    }

    public final void close() {
        this.closed = true;
        this.zzadn.interrupt();
    }
}
