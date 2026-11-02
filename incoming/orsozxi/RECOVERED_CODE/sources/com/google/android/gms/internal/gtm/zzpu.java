package com.google.android.gms.internal.gtm;

import java.util.Comparator;

/* loaded from: classes.dex */
final class zzpu implements Comparator<zzps> {
    zzpu() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(zzps zzpsVar, zzps zzpsVar2) {
        zzps zzpsVar3 = zzpsVar;
        zzps zzpsVar4 = zzpsVar2;
        zzpz zzpzVar = (zzpz) zzpsVar3.iterator();
        zzpz zzpzVar2 = (zzpz) zzpsVar4.iterator();
        while (zzpzVar.hasNext() && zzpzVar2.hasNext()) {
            int iCompare = Integer.compare(zzps.zza(zzpzVar.nextByte()), zzps.zza(zzpzVar2.nextByte()));
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return Integer.compare(zzpsVar3.size(), zzpsVar4.size());
    }
}
