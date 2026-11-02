package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzpl;
import com.google.android.gms.internal.gtm.zzpm;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class zzpl<MessageType extends zzpl<MessageType, BuilderType>, BuilderType extends zzpm<MessageType, BuilderType>> implements zzsk {
    private static boolean zzavq = false;
    protected int zzavp = 0;

    @Override // com.google.android.gms.internal.gtm.zzsk
    public final zzps zzmv() {
        try {
            zzqa zzqaVarZzam = zzps.zzam(zzpe());
            zzb(zzqaVarZzam.zznh());
            return zzqaVarZzam.zzng();
        } catch (IOException e) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 62 + "ByteString".length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append("ByteString");
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    int zzmw() {
        throw new UnsupportedOperationException();
    }

    void zzag(int i) {
        throw new UnsupportedOperationException();
    }
}
