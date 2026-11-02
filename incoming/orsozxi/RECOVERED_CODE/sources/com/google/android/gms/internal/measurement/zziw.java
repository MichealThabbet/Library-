package com.google.android.gms.internal.measurement;

import java.util.Comparator;

/* compiled from: com.google.android.gms:play-services-measurement-base@@19.0.0 */
/* loaded from: classes.dex */
final class zziw implements Comparator<zzjd> {
    zziw() {
    }

    @Override // java.util.Comparator
    public final /* bridge */ /* synthetic */ int compare(zzjd zzjdVar, zzjd zzjdVar2) {
        zzjd zzjdVar3 = zzjdVar;
        zzjd zzjdVar4 = zzjdVar2;
        zziv zzivVar = new zziv(zzjdVar3);
        zziv zzivVar2 = new zziv(zzjdVar4);
        while (zzivVar.hasNext() && zzivVar2.hasNext()) {
            int iCompare = Integer.compare(zzivVar.zza() & 255, zzivVar2.zza() & 255);
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return Integer.compare(zzjdVar3.zzc(), zzjdVar4.zzc());
    }
}
