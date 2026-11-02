package com.google.android.gms.analytics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzcz;
import com.google.android.gms.internal.gtm.zzq;
import com.google.android.gms.internal.gtm.zzv;
import java.lang.Thread;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class zzk {
    private static volatile zzk zzsq;
    private final Context zzrm;
    private final List<zzn> zzsr;
    private final zze zzss;
    private final zza zzst;
    private volatile zzq zzsu;
    private Thread.UncaughtExceptionHandler zzsv;

    static class zzb implements ThreadFactory {
        private static final AtomicInteger zzsz = new AtomicInteger();

        private zzb() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            int iIncrementAndGet = zzsz.incrementAndGet();
            StringBuilder sb = new StringBuilder(23);
            sb.append("measurement-");
            sb.append(iIncrementAndGet);
            return new zzc(runnable, sb.toString());
        }

        /* synthetic */ zzb(zzl zzlVar) {
            this();
        }
    }

    private zzk(Context context) {
        Context applicationContext = context.getApplicationContext();
        Preconditions.checkNotNull(applicationContext);
        this.zzrm = applicationContext;
        this.zzst = new zza();
        this.zzsr = new CopyOnWriteArrayList();
        this.zzss = new zze();
    }

    static class zzc extends Thread {
        zzc(Runnable runnable, String str) {
            super(runnable, str);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() throws SecurityException, IllegalArgumentException {
            Process.setThreadPriority(10);
            super.run();
        }
    }

    class zza extends ThreadPoolExecutor {
        public zza() {
            super(1, 1, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue());
            setThreadFactory(new zzb(null));
            allowCoreThreadTimeOut(true);
        }

        @Override // java.util.concurrent.AbstractExecutorService
        protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
            return new zzm(this, runnable, t);
        }
    }

    public static zzk zzb(Context context) {
        Preconditions.checkNotNull(context);
        if (zzsq == null) {
            synchronized (zzk.class) {
                if (zzsq == null) {
                    zzsq = new zzk(context);
                }
            }
        }
        return zzsq;
    }

    public final zzq zzat() {
        if (this.zzsu == null) {
            synchronized (this) {
                if (this.zzsu == null) {
                    zzq zzqVar = new zzq();
                    PackageManager packageManager = this.zzrm.getPackageManager();
                    String packageName = this.zzrm.getPackageName();
                    zzqVar.setAppId(packageName);
                    zzqVar.setAppInstallerId(packageManager.getInstallerPackageName(packageName));
                    String str = null;
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(this.zzrm.getPackageName(), 0);
                        if (packageInfo != null) {
                            CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                            if (!TextUtils.isEmpty(applicationLabel)) {
                                packageName = applicationLabel.toString();
                            }
                            str = packageInfo.versionName;
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                        String strValueOf = String.valueOf(packageName);
                        Log.e("GAv4", strValueOf.length() != 0 ? "Error retrieving package info: appName set to ".concat(strValueOf) : new String("Error retrieving package info: appName set to "));
                    }
                    zzqVar.setAppName(packageName);
                    zzqVar.setAppVersion(str);
                    this.zzsu = zzqVar;
                }
            }
        }
        return this.zzsu;
    }

    public final zzv zzau() {
        DisplayMetrics displayMetrics = this.zzrm.getResources().getDisplayMetrics();
        zzv zzvVar = new zzv();
        zzvVar.setLanguage(zzcz.zza(Locale.getDefault()));
        zzvVar.zzul = displayMetrics.widthPixels;
        zzvVar.zzum = displayMetrics.heightPixels;
        return zzvVar;
    }

    final void zze(zzg zzgVar) {
        if (zzgVar.zzaq()) {
            throw new IllegalStateException("Measurement prototype can't be submitted");
        }
        if (zzgVar.zzan()) {
            throw new IllegalStateException("Measurement can only be submitted once");
        }
        zzg zzgVarZzai = zzgVar.zzai();
        zzgVarZzai.zzao();
        this.zzst.execute(new zzl(this, zzgVarZzai));
    }

    public final Context getContext() {
        return this.zzrm;
    }

    public static void zzav() {
        if (!(Thread.currentThread() instanceof zzc)) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final void zza(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.zzsv = uncaughtExceptionHandler;
    }

    public final <V> Future<V> zza(Callable<V> callable) {
        Preconditions.checkNotNull(callable);
        if (Thread.currentThread() instanceof zzc) {
            FutureTask futureTask = new FutureTask(callable);
            futureTask.run();
            return futureTask;
        }
        return this.zzst.submit(callable);
    }

    public final void zza(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        this.zzst.submit(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzb(zzg zzgVar) {
        Preconditions.checkNotMainThread("deliver should be called from worker thread");
        Preconditions.checkArgument(zzgVar.zzan(), "Measurement must be submitted");
        List<zzo> listZzak = zzgVar.zzak();
        if (listZzak.isEmpty()) {
            return;
        }
        HashSet hashSet = new HashSet();
        for (zzo zzoVar : listZzak) {
            Uri uriZzae = zzoVar.zzae();
            if (!hashSet.contains(uriZzae)) {
                hashSet.add(uriZzae);
                zzoVar.zzb(zzgVar);
            }
        }
    }
}
