package com.google.android.gms.internal.gtm;

import it.piapostolos.android.pinotes.R2;
import java.util.Arrays;

/* loaded from: classes.dex */
final class zzuy {
    final int tag;
    final byte[] zzawe;

    zzuy(int i, byte[] bArr) {
        this.tag = i;
        this.zzawe = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzuy)) {
            return false;
        }
        zzuy zzuyVar = (zzuy) obj;
        return this.tag == zzuyVar.tag && Arrays.equals(this.zzawe, zzuyVar.zzawe);
    }

    public final int hashCode() {
        return ((this.tag + R2.attr.hoveredFocusedTranslationZ) * 31) + Arrays.hashCode(this.zzawe);
    }
}
