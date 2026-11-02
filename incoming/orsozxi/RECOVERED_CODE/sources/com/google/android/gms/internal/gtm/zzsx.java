package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzrc;

/* loaded from: classes.dex */
final class zzsx implements zzsi {
    private final int flags;
    private final String info;
    private final Object[] zzbcz;
    private final zzsk zzbdc;

    zzsx(zzsk zzskVar, String str, Object[] objArr) {
        this.zzbdc = zzskVar;
        this.info = str;
        this.zzbcz = objArr;
        char cCharAt = str.charAt(0);
        if (cCharAt < 55296) {
            this.flags = cCharAt;
            return;
        }
        int i = cCharAt & 8191;
        int i2 = 13;
        int i3 = 1;
        while (true) {
            int i4 = i3 + 1;
            char cCharAt2 = str.charAt(i3);
            if (cCharAt2 < 55296) {
                this.flags = i | (cCharAt2 << i2);
                return;
            } else {
                i |= (cCharAt2 & 8191) << i2;
                i2 += 13;
                i3 = i4;
            }
        }
    }

    final String zzqt() {
        return this.info;
    }

    final Object[] zzqu() {
        return this.zzbcz;
    }

    @Override // com.google.android.gms.internal.gtm.zzsi
    public final zzsk zzqn() {
        return this.zzbdc;
    }

    @Override // com.google.android.gms.internal.gtm.zzsi
    public final int zzql() {
        return (this.flags & 1) == 1 ? zzrc.zze.zzbaz : zzrc.zze.zzbba;
    }

    @Override // com.google.android.gms.internal.gtm.zzsi
    public final boolean zzqm() {
        return (this.flags & 2) == 2;
    }
}
