package com.google.android.gms.internal.gtm;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;

/* loaded from: classes.dex */
public class zzam {
    private final zzap zzwc;

    protected zzam(zzap zzapVar) {
        Preconditions.checkNotNull(zzapVar);
        this.zzwc = zzapVar;
    }

    public final zzap zzcm() {
        return this.zzwc;
    }

    protected final Clock zzcn() {
        return this.zzwc.zzcn();
    }

    protected final Context getContext() {
        return this.zzwc.getContext();
    }

    protected final zzci zzco() {
        return this.zzwc.zzco();
    }

    protected final zzbq zzcp() {
        return this.zzwc.zzcp();
    }

    protected final com.google.android.gms.analytics.zzk zzcq() {
        return this.zzwc.zzcq();
    }

    public final GoogleAnalytics zzcr() {
        return this.zzwc.zzde();
    }

    protected final zzae zzcs() {
        return this.zzwc.zzcs();
    }

    protected final zzbv zzct() {
        return this.zzwc.zzct();
    }

    protected final zzda zzcu() {
        return this.zzwc.zzcu();
    }

    protected final zzcm zzcv() {
        return this.zzwc.zzcv();
    }

    protected final zzbh zzcw() {
        return this.zzwc.zzdh();
    }

    protected final zzad zzcx() {
        return this.zzwc.zzdg();
    }

    protected final zzba zzcy() {
        return this.zzwc.zzcy();
    }

    protected final zzbu zzcz() {
        return this.zzwc.zzcz();
    }

    public final void zzq(String str) {
        zza(2, str, null, null, null);
    }

    public final void zza(String str, Object obj) {
        zza(2, str, obj, null, null);
    }

    public final void zza(String str, Object obj, Object obj2) {
        zza(2, str, obj, obj2, null);
    }

    public final void zzr(String str) {
        zza(3, str, null, null, null);
    }

    public final void zzb(String str, Object obj) {
        zza(3, str, obj, null, null);
    }

    public final void zzb(String str, Object obj, Object obj2) {
        zza(3, str, obj, obj2, null);
    }

    public final void zza(String str, Object obj, Object obj2, Object obj3) {
        zza(3, str, obj, obj2, obj3);
    }

    public final void zzs(String str) {
        zza(4, str, null, null, null);
    }

    public final void zzc(String str, Object obj) {
        zza(4, str, obj, null, null);
    }

    public final void zzt(String str) {
        zza(5, str, null, null, null);
    }

    public final void zzd(String str, Object obj) {
        zza(5, str, obj, null, null);
    }

    public final void zzc(String str, Object obj, Object obj2) {
        zza(5, str, obj, obj2, null);
    }

    public final void zzb(String str, Object obj, Object obj2, Object obj3) {
        zza(5, str, obj, obj2, obj3);
    }

    public final void zzu(String str) {
        zza(6, str, null, null, null);
    }

    public final void zze(String str, Object obj) {
        zza(6, str, obj, null, null);
    }

    public final void zzd(String str, Object obj, Object obj2) {
        zza(6, str, obj, obj2, null);
    }

    public static boolean zzda() {
        return Log.isLoggable(zzby.zzzb.get(), 2);
    }

    private final void zza(int i, String str, Object obj, Object obj2, Object obj3) {
        zzap zzapVar = this.zzwc;
        zzci zzciVarZzdd = zzapVar != null ? zzapVar.zzdd() : null;
        if (zzciVarZzdd != null) {
            String str2 = zzby.zzzb.get();
            if (Log.isLoggable(str2, i)) {
                Log.println(i, str2, zzci.zzc(str, obj, obj2, obj3));
            }
            if (i >= 5) {
                zzciVarZzdd.zzb(i, str, obj, obj2, obj3);
                return;
            }
            return;
        }
        String str3 = zzby.zzzb.get();
        if (Log.isLoggable(str3, i)) {
            Log.println(i, str3, zzc(str, obj, obj2, obj3));
        }
    }

    protected static String zzc(String str, Object obj, Object obj2, Object obj3) {
        String str2 = "";
        if (str == null) {
            str = "";
        }
        String strZzb = zzb(obj);
        String strZzb2 = zzb(obj2);
        String strZzb3 = zzb(obj3);
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            str2 = ": ";
        }
        String str3 = ", ";
        if (!TextUtils.isEmpty(strZzb)) {
            sb.append(str2);
            sb.append(strZzb);
            str2 = ", ";
        }
        if (TextUtils.isEmpty(strZzb2)) {
            str3 = str2;
        } else {
            sb.append(str2);
            sb.append(strZzb2);
        }
        if (!TextUtils.isEmpty(strZzb3)) {
            sb.append(str3);
            sb.append(strZzb3);
        }
        return sb.toString();
    }

    private static String zzb(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Boolean) {
            return obj == Boolean.TRUE ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false";
        }
        if (obj instanceof Throwable) {
            return ((Throwable) obj).toString();
        }
        return obj.toString();
    }
}
