package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzov;
import com.google.android.gms.internal.measurement.zzqi;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
/* loaded from: classes.dex */
public final class zzee extends zzf {
    private String zza;
    private String zzb;
    private int zzc;
    private String zzd;
    private String zze;
    private long zzf;
    private final long zzg;
    private List<String> zzh;
    private int zzi;
    private String zzj;
    private String zzk;
    private String zzl;

    zzee(zzfu zzfuVar, long j) {
        super(zzfuVar);
        this.zzg = j;
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    protected final boolean zze() {
        return true;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(21:0|2|(1:4)(23:106|6|(1:10)(2:11|(1:13))|102|14|(4:16|(1:18)(1:20)|104|21)|26|(1:31)(1:30)|32|33|43|(1:45)|46|100|47|(1:49)(1:50)|51|52|(6:54|(1:56)(1:57)|58|(1:60)|61|(1:65))(2:66|(3:68|(1:70)(1:71)|72))|(3:74|(1:76)(1:77)|78)|82|(2:85|(1:87)(4:88|(3:91|(1:109)(1:110)|89)|108|94))(1:94)|(2:96|97)(2:98|99))|5|26|(2:28|31)(0)|32|33|43|(0)|46|100|47|(0)(0)|51|52|(0)(0)|(0)|82|(0)(0)|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0231, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0232, code lost:
    
        r13.zzs.zzau().zzb().zzc("Fetching Google App Id failed with exception. appId", com.google.android.gms.measurement.internal.zzem.zzl(r0), r3);
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01a6 A[Catch: IllegalStateException -> 0x0231, TRY_ENTER, TryCatch #0 {IllegalStateException -> 0x0231, blocks: (B:47:0x0176, B:51:0x0191, B:54:0x01a6, B:58:0x01c4, B:61:0x01d1, B:63:0x01d9, B:74:0x0212, B:76:0x0228, B:78:0x022d, B:77:0x022b, B:65:0x01df, B:57:0x01c0, B:66:0x01e6, B:68:0x01ec, B:72:0x020a, B:71:0x0206), top: B:100:0x0176 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01e6 A[Catch: IllegalStateException -> 0x0231, TryCatch #0 {IllegalStateException -> 0x0231, blocks: (B:47:0x0176, B:51:0x0191, B:54:0x01a6, B:58:0x01c4, B:61:0x01d1, B:63:0x01d9, B:74:0x0212, B:76:0x0228, B:78:0x022d, B:77:0x022b, B:65:0x01df, B:57:0x01c0, B:66:0x01e6, B:68:0x01ec, B:72:0x020a, B:71:0x0206), top: B:100:0x0176 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0212 A[Catch: IllegalStateException -> 0x0231, TryCatch #0 {IllegalStateException -> 0x0231, blocks: (B:47:0x0176, B:51:0x0191, B:54:0x01a6, B:58:0x01c4, B:61:0x01d1, B:63:0x01d9, B:74:0x0212, B:76:0x0228, B:78:0x022d, B:77:0x022b, B:65:0x01df, B:57:0x01c0, B:66:0x01e6, B:68:0x01ec, B:72:0x020a, B:71:0x0206), top: B:100:0x0176 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x025b  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02a1  */
    @Override // com.google.android.gms.measurement.internal.zzf
    @org.checkerframework.checker.nullness.qual.EnsuresNonNull({"appId", "appStore", "appName", "gmpAppId", "gaAppId"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected final void zzf() throws android.content.res.Resources.NotFoundException, android.content.pm.PackageManager.NameNotFoundException {
        /*
            Method dump skipped, instructions count: 696
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzee.zzf():void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v34, types: [com.google.android.gms.measurement.internal.zzgn, com.google.android.gms.measurement.internal.zzku] */
    /* JADX WARN: Type inference failed for: r9v35, types: [com.google.android.gms.measurement.internal.zzgn] */
    /* JADX WARN: Type inference failed for: r9v39 */
    /* JADX WARN: Type inference failed for: r9v44 */
    /* JADX WARN: Type inference failed for: r9v45 */
    /* JADX WARN: Type inference failed for: r9v46 */
    final zzp zzh(String str) throws IllegalAccessException, NoSuchAlgorithmException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        String str2;
        Class<?> clsLoadClass;
        Object objInvoke;
        String str3;
        long jMin;
        zzg();
        String strZzi = zzi();
        String strZzj = zzj();
        zzb();
        String str4 = this.zzb;
        zzb();
        long j = this.zzc;
        zzb();
        Preconditions.checkNotNull(this.zzd);
        String str5 = this.zzd;
        this.zzs.zzc().zzf();
        zzb();
        zzg();
        long j2 = this.zzf;
        if (j2 == 0) {
            ?? Zzl = this.zzs.zzl();
            Context contextZzax = this.zzs.zzax();
            String packageName = this.zzs.zzax().getPackageName();
            Zzl.zzg();
            Preconditions.checkNotNull(contextZzax);
            Preconditions.checkNotEmpty(packageName);
            PackageManager packageManager = contextZzax.getPackageManager();
            MessageDigest messageDigestZzN = zzku.zzN();
            long j3 = -1;
            if (messageDigestZzN == null) {
                Zzl.zzs.zzau().zzb().zza("Could not get MD5 instance");
            } else {
                if (packageManager != null) {
                    try {
                        if (Zzl.zzW(contextZzax, packageName)) {
                            j3 = 0;
                            Zzl = Zzl;
                        } else {
                            PackageInfo packageInfo = Wrappers.packageManager(contextZzax).getPackageInfo(Zzl.zzs.zzax().getPackageName(), 64);
                            if (packageInfo.signatures == null || packageInfo.signatures.length <= 0) {
                                Zzl.zzs.zzau().zze().zza("Could not get signatures");
                                Zzl = Zzl;
                            } else {
                                long jZzO = zzku.zzO(messageDigestZzN.digest(packageInfo.signatures[0].toByteArray()));
                                j3 = jZzO;
                                Zzl = jZzO;
                            }
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        Zzl.zzs.zzau().zzb().zzb("Package name not found", e);
                    }
                }
                j2 = 0;
                this.zzf = j2;
            }
            j2 = j3;
            this.zzf = j2;
        }
        long j4 = j2;
        boolean zZzF = this.zzs.zzF();
        boolean z = !this.zzs.zzd().zzk;
        zzg();
        if (this.zzs.zzF()) {
            zzqi.zzb();
            if (this.zzs.zzc().zzn(null, zzea.zzai)) {
                this.zzs.zzau().zzk().zza("Disabled IID for tests.");
            } else {
                try {
                    clsLoadClass = this.zzs.zzax().getClassLoader().loadClass("com.google.firebase.analytics.FirebaseAnalytics");
                } catch (ClassNotFoundException unused) {
                }
                if (clsLoadClass != null) {
                    try {
                        objInvoke = clsLoadClass.getDeclaredMethod("getInstance", Context.class).invoke(null, this.zzs.zzax());
                    } catch (Exception unused2) {
                        this.zzs.zzau().zzf().zza("Failed to obtain Firebase Analytics instance");
                    }
                    if (objInvoke == null) {
                        str2 = null;
                    } else {
                        try {
                            str2 = (String) clsLoadClass.getDeclaredMethod("getFirebaseInstanceId", new Class[0]).invoke(objInvoke, new Object[0]);
                        } catch (Exception unused3) {
                            this.zzs.zzau().zzh().zza("Failed to retrieve Firebase Instance Id");
                        }
                    }
                }
            }
            str2 = null;
        } else {
            str2 = null;
        }
        zzfu zzfuVar = this.zzs;
        long jZza = zzfuVar.zzd().zzc.zza();
        if (jZza == 0) {
            str3 = strZzi;
            jMin = zzfuVar.zzc;
        } else {
            str3 = strZzi;
            jMin = Math.min(zzfuVar.zzc, jZza);
        }
        zzb();
        int i = this.zzi;
        boolean zZzs = this.zzs.zzc().zzs();
        zzfb zzfbVarZzd = this.zzs.zzd();
        zzfbVarZzd.zzg();
        boolean z2 = zzfbVarZzd.zzd().getBoolean("deferred_analytics_collection", false);
        zzb();
        String str6 = this.zzk;
        Boolean boolValueOf = this.zzs.zzc().zzp("google_analytics_default_allow_ad_personalization_signals") == null ? null : Boolean.valueOf(!r2.booleanValue());
        long j5 = this.zzg;
        List<String> list = this.zzh;
        zzov.zzb();
        return new zzp(str3, strZzj, str4, j, str5, 42004L, j4, str, zZzF, z, str2, 0L, jMin, i, zZzs, z2, str6, boolValueOf, j5, list, this.zzs.zzc().zzn(null, zzea.zzag) ? zzl() : null, this.zzs.zzd().zzi().zzd());
    }

    final String zzi() {
        zzb();
        Preconditions.checkNotNull(this.zza);
        return this.zza;
    }

    final String zzj() {
        zzb();
        Preconditions.checkNotNull(this.zzj);
        return this.zzj;
    }

    final String zzk() {
        zzb();
        return this.zzk;
    }

    final String zzl() {
        zzb();
        Preconditions.checkNotNull(this.zzl);
        return this.zzl;
    }

    final int zzm() {
        zzb();
        return this.zzc;
    }

    final int zzn() {
        zzb();
        return this.zzi;
    }

    final List<String> zzo() {
        return this.zzh;
    }
}
