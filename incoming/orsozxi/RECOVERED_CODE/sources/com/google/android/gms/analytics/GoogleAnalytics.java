package com.google.android.gms.analytics;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.internal.gtm.zzap;
import com.google.android.gms.internal.gtm.zzby;
import com.google.android.gms.internal.gtm.zzch;
import com.google.android.gms.internal.gtm.zzcw;
import com.google.android.gms.internal.gtm.zzcy;
import com.google.android.gms.internal.gtm.zzda;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public final class GoogleAnalytics extends com.google.android.gms.analytics.zza {
    private static List<Runnable> zzrp = new ArrayList();
    private boolean zzrq;
    private Set<zza> zzrr;
    private boolean zzrs;
    private boolean zzrt;
    private volatile boolean zzru;
    private boolean zzrv;

    interface zza {
        void zzc(Activity activity);

        void zzd(Activity activity);
    }

    class zzb implements Application.ActivityLifecycleCallbacks {
        zzb() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStarted(Activity activity) {
            GoogleAnalytics.this.zza(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStopped(Activity activity) {
            GoogleAnalytics.this.zzb(activity);
        }
    }

    public final void zzag() {
        zzda zzdaVarZzcu = zzab().zzcu();
        zzdaVarZzcu.zzgh();
        if (zzdaVarZzcu.zzgi()) {
            setDryRun(zzdaVarZzcu.zzgj());
        }
        zzdaVarZzcu.zzgh();
        this.zzrq = true;
    }

    public final boolean isInitialized() {
        return this.zzrq;
    }

    public GoogleAnalytics(zzap zzapVar) {
        super(zzapVar);
        this.zzrr = new HashSet();
    }

    public static GoogleAnalytics getInstance(Context context) {
        return zzap.zzc(context).zzde();
    }

    public static void zzah() {
        synchronized (GoogleAnalytics.class) {
            if (zzrp != null) {
                Iterator<Runnable> it2 = zzrp.iterator();
                while (it2.hasNext()) {
                    it2.next().run();
                }
                zzrp = null;
            }
        }
    }

    public final void setDryRun(boolean z) {
        this.zzrt = z;
    }

    public final boolean isDryRunEnabled() {
        return this.zzrt;
    }

    public final void enableAutoActivityReports(Application application) {
        if (this.zzrs) {
            return;
        }
        application.registerActivityLifecycleCallbacks(new zzb());
        this.zzrs = true;
    }

    public final void reportActivityStart(Activity activity) {
        if (this.zzrs) {
            return;
        }
        zza(activity);
    }

    final void zza(Activity activity) {
        Iterator<zza> it2 = this.zzrr.iterator();
        while (it2.hasNext()) {
            it2.next().zzc(activity);
        }
    }

    public final void reportActivityStop(Activity activity) {
        if (this.zzrs) {
            return;
        }
        zzb(activity);
    }

    final void zzb(Activity activity) {
        Iterator<zza> it2 = this.zzrr.iterator();
        while (it2.hasNext()) {
            it2.next().zzd(activity);
        }
    }

    public final Tracker newTracker(String str) {
        Tracker tracker;
        synchronized (this) {
            tracker = new Tracker(zzab(), str, null);
            tracker.zzag();
        }
        return tracker;
    }

    public final Tracker newTracker(int i) {
        Tracker tracker;
        zzcy zzcyVarZzq;
        synchronized (this) {
            tracker = new Tracker(zzab(), null, null);
            if (i > 0 && (zzcyVarZzq = new zzcw(zzab()).zzq(i)) != null) {
                tracker.zza(zzcyVarZzq);
            }
            tracker.zzag();
        }
        return tracker;
    }

    final void zza(zza zzaVar) {
        this.zzrr.add(zzaVar);
        Context context = zzab().getContext();
        if (context instanceof Application) {
            enableAutoActivityReports((Application) context);
        }
    }

    final void zzb(zza zzaVar) {
        this.zzrr.remove(zzaVar);
    }

    public final void setAppOptOut(boolean z) {
        this.zzru = z;
        if (this.zzru) {
            zzab().zzcs().zzch();
        }
    }

    public final boolean getAppOptOut() {
        return this.zzru;
    }

    @Deprecated
    public final Logger getLogger() {
        return zzch.getLogger();
    }

    @Deprecated
    public final void setLogger(Logger logger) {
        zzch.setLogger(logger);
        if (this.zzrv) {
            return;
        }
        String str = zzby.zzzb.get();
        String str2 = zzby.zzzb.get();
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 112);
        sb.append("GoogleAnalytics.setLogger() is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag.");
        sb.append(str2);
        sb.append(" DEBUG");
        Log.i(str, sb.toString());
        this.zzrv = true;
    }

    public final void setLocalDispatchPeriod(int i) {
        zzab().zzcs().setLocalDispatchPeriod(i);
    }

    public final void dispatchLocalHits() {
        zzab().zzcs().zzci();
    }
}
