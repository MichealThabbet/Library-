package com.google.android.gms.tagmanager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.ibm.icu.impl.locale.LanguageTag;
import java.util.Map;

/* loaded from: classes.dex */
final class zzem extends zzbq {
    private static final String ID = com.google.android.gms.internal.gtm.zza.RESOLUTION.toString();
    private final Context zzrm;

    public zzem(Context context) {
        super(ID, new String[0]);
        this.zzrm = context;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final boolean zzgw() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzbq
    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.zzrm.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        StringBuilder sb = new StringBuilder(23);
        sb.append(i);
        sb.append(LanguageTag.PRIVATEUSE);
        sb.append(i2);
        return zzgj.zzi(sb.toString());
    }
}
