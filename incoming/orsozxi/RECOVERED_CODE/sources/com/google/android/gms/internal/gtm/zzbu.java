package com.google.android.gms.internal.gtm;

import com.ibm.icu.impl.locale.LanguageTag;

/* loaded from: classes.dex */
public final class zzbu extends zzan {
    zzbu(zzap zzapVar) {
        super(zzapVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzan
    protected final void zzaw() {
    }

    public final zzv zzfa() {
        zzdb();
        return zzcq().zzau();
    }

    public final String zzfb() {
        zzdb();
        zzv zzvVarZzfa = zzfa();
        int i = zzvVarZzfa.zzul;
        int i2 = zzvVarZzfa.zzum;
        StringBuilder sb = new StringBuilder(23);
        sb.append(i);
        sb.append(LanguageTag.PRIVATEUSE);
        sb.append(i2);
        return sb.toString();
    }
}
