package com.google.android.gms.internal.gtm;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;

/* loaded from: classes.dex */
public class zzap {
    private static volatile zzap zzwf;
    private final Context zzrm;
    private final Clock zzsd;
    private final Context zzwg;
    private final zzbq zzwh;
    private final zzci zzwi;
    private final com.google.android.gms.analytics.zzk zzwj;
    private final zzae zzwk;
    private final zzbv zzwl;
    private final zzda zzwm;
    private final zzcm zzwn;
    private final GoogleAnalytics zzwo;
    private final zzbh zzwp;
    private final zzad zzwq;
    private final zzba zzwr;
    private final zzbu zzws;

    private zzap(zzar zzarVar) {
        Context applicationContext = zzarVar.getApplicationContext();
        Preconditions.checkNotNull(applicationContext, "Application context can't be null");
        Context contextZzdc = zzarVar.zzdc();
        Preconditions.checkNotNull(contextZzdc);
        this.zzrm = applicationContext;
        this.zzwg = contextZzdc;
        this.zzsd = DefaultClock.getInstance();
        this.zzwh = new zzbq(this);
        zzci zzciVar = new zzci(this);
        zzciVar.zzag();
        this.zzwi = zzciVar;
        zzci zzciVarZzco = zzco();
        String str = zzao.VERSION;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 134);
        sb.append("Google Analytics ");
        sb.append(str);
        sb.append(" is starting up. To enable debug logging on a device run:\n  adb shell setprop log.tag.GAv4 DEBUG\n  adb logcat -s GAv4");
        zzciVarZzco.zzs(sb.toString());
        zzcm zzcmVar = new zzcm(this);
        zzcmVar.zzag();
        this.zzwn = zzcmVar;
        zzda zzdaVar = new zzda(this);
        zzdaVar.zzag();
        this.zzwm = zzdaVar;
        zzae zzaeVar = new zzae(this, zzarVar);
        zzbh zzbhVar = new zzbh(this);
        zzad zzadVar = new zzad(this);
        zzba zzbaVar = new zzba(this);
        zzbu zzbuVar = new zzbu(this);
        com.google.android.gms.analytics.zzk zzkVarZzb = com.google.android.gms.analytics.zzk.zzb(applicationContext);
        zzkVarZzb.zza(new zzaq(this));
        this.zzwj = zzkVarZzb;
        GoogleAnalytics googleAnalytics = new GoogleAnalytics(this);
        zzbhVar.zzag();
        this.zzwp = zzbhVar;
        zzadVar.zzag();
        this.zzwq = zzadVar;
        zzbaVar.zzag();
        this.zzwr = zzbaVar;
        zzbuVar.zzag();
        this.zzws = zzbuVar;
        zzbv zzbvVar = new zzbv(this);
        zzbvVar.zzag();
        this.zzwl = zzbvVar;
        zzaeVar.zzag();
        this.zzwk = zzaeVar;
        googleAnalytics.zzag();
        this.zzwo = googleAnalytics;
        zzaeVar.start();
    }

    public static zzap zzc(Context context) {
        Preconditions.checkNotNull(context);
        if (zzwf == null) {
            synchronized (zzap.class) {
                if (zzwf == null) {
                    Clock defaultClock = DefaultClock.getInstance();
                    long jElapsedRealtime = defaultClock.elapsedRealtime();
                    zzap zzapVar = new zzap(new zzar(context));
                    zzwf = zzapVar;
                    GoogleAnalytics.zzah();
                    long jElapsedRealtime2 = defaultClock.elapsedRealtime() - jElapsedRealtime;
                    long jLongValue = zzby.zzaap.get().longValue();
                    if (jElapsedRealtime2 > jLongValue) {
                        zzapVar.zzco().zzc("Slow initialization (ms)", Long.valueOf(jElapsedRealtime2), Long.valueOf(jLongValue));
                    }
                }
            }
        }
        return zzwf;
    }

    public final Context getContext() {
        return this.zzrm;
    }

    public final Context zzdc() {
        return this.zzwg;
    }

    public final Clock zzcn() {
        return this.zzsd;
    }

    public final zzbq zzcp() {
        return this.zzwh;
    }

    public final zzci zzco() {
        zza(this.zzwi);
        return this.zzwi;
    }

    public final zzci zzdd() {
        return this.zzwi;
    }

    public final com.google.android.gms.analytics.zzk zzcq() {
        Preconditions.checkNotNull(this.zzwj);
        return this.zzwj;
    }

    public final zzae zzcs() {
        zza(this.zzwk);
        return this.zzwk;
    }

    public final zzbv zzct() {
        zza(this.zzwl);
        return this.zzwl;
    }

    public final GoogleAnalytics zzde() {
        Preconditions.checkNotNull(this.zzwo);
        Preconditions.checkArgument(this.zzwo.isInitialized(), "Analytics instance not initialized");
        return this.zzwo;
    }

    public final zzda zzcu() {
        zza(this.zzwm);
        return this.zzwm;
    }

    public final zzcm zzcv() {
        zza(this.zzwn);
        return this.zzwn;
    }

    public final zzcm zzdf() {
        zzcm zzcmVar = this.zzwn;
        if (zzcmVar == null || !zzcmVar.isInitialized()) {
            return null;
        }
        return this.zzwn;
    }

    public final zzad zzdg() {
        zza(this.zzwq);
        return this.zzwq;
    }

    public final zzbh zzdh() {
        zza(this.zzwp);
        return this.zzwp;
    }

    public final zzba zzcy() {
        zza(this.zzwr);
        return this.zzwr;
    }

    public final zzbu zzcz() {
        return this.zzws;
    }

    private static void zza(zzan zzanVar) {
        Preconditions.checkNotNull(zzanVar, "Analytics service not created/initialized");
        Preconditions.checkArgument(zzanVar.isInitialized(), "Analytics service not initialized");
    }
}
