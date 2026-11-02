package com.google.android.gms.internal.gtm;

import android.content.SharedPreferences;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import java.util.UUID;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: classes.dex */
public final class zzco {
    private final String name;
    private final long zzabz;
    private final /* synthetic */ zzcm zzaca;

    private zzco(zzcm zzcmVar, String str, long j) {
        this.zzaca = zzcmVar;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkArgument(j > 0);
        this.name = str;
        this.zzabz = j;
    }

    private final void zzgb() {
        long jCurrentTimeMillis = this.zzaca.zzcn().currentTimeMillis();
        SharedPreferences.Editor editorEdit = this.zzaca.zzabv.edit();
        editorEdit.remove(zzgf());
        editorEdit.remove(zzgg());
        editorEdit.putLong(zzge(), jCurrentTimeMillis);
        editorEdit.commit();
    }

    public final void zzae(String str) {
        if (zzgd() == 0) {
            zzgb();
        }
        if (str == null) {
            str = "";
        }
        synchronized (this) {
            long j = this.zzaca.zzabv.getLong(zzgf(), 0L);
            if (j <= 0) {
                SharedPreferences.Editor editorEdit = this.zzaca.zzabv.edit();
                editorEdit.putString(zzgg(), str);
                editorEdit.putLong(zzgf(), 1L);
                editorEdit.apply();
                return;
            }
            long j2 = j + 1;
            boolean z = (UUID.randomUUID().getLeastSignificantBits() & LongCompanionObject.MAX_VALUE) < LongCompanionObject.MAX_VALUE / j2;
            SharedPreferences.Editor editorEdit2 = this.zzaca.zzabv.edit();
            if (z) {
                editorEdit2.putString(zzgg(), str);
            }
            editorEdit2.putLong(zzgf(), j2);
            editorEdit2.apply();
        }
    }

    public final Pair<String, Long> zzgc() {
        long jZzgd = zzgd();
        long jAbs = jZzgd == 0 ? 0L : Math.abs(jZzgd - this.zzaca.zzcn().currentTimeMillis());
        long j = this.zzabz;
        if (jAbs < j) {
            return null;
        }
        if (jAbs > (j << 1)) {
            zzgb();
            return null;
        }
        String string = this.zzaca.zzabv.getString(zzgg(), null);
        long j2 = this.zzaca.zzabv.getLong(zzgf(), 0L);
        zzgb();
        if (string == null || j2 <= 0) {
            return null;
        }
        return new Pair<>(string, Long.valueOf(j2));
    }

    private final long zzgd() {
        return this.zzaca.zzabv.getLong(zzge(), 0L);
    }

    private final String zzge() {
        return String.valueOf(this.name).concat(":start");
    }

    private final String zzgf() {
        return String.valueOf(this.name).concat(":count");
    }

    private final String zzgg() {
        return String.valueOf(this.name).concat(":value");
    }
}
