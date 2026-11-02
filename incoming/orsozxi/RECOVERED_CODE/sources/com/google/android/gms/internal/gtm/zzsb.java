package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
final class zzsb implements zzsj {
    private zzsj[] zzbco;

    zzsb(zzsj... zzsjVarArr) {
        this.zzbco = zzsjVarArr;
    }

    @Override // com.google.android.gms.internal.gtm.zzsj
    public final boolean zze(Class<?> cls) {
        for (zzsj zzsjVar : this.zzbco) {
            if (zzsjVar.zze(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.gtm.zzsj
    public final zzsi zzf(Class<?> cls) {
        for (zzsj zzsjVar : this.zzbco) {
            if (zzsjVar.zze(cls)) {
                return zzsjVar.zzf(cls);
            }
        }
        String strValueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(strValueOf.length() != 0 ? "No factory is available for message type: ".concat(strValueOf) : new String("No factory is available for message type: "));
    }
}
