package com.google.android.gms.internal.gtm;

import java.io.PrintStream;
import java.util.List;

/* loaded from: classes.dex */
final class zzpj extends zzpg {
    private final zzph zzavo = new zzph();

    zzpj() {
    }

    @Override // com.google.android.gms.internal.gtm.zzpg
    public final void zza(Throwable th, PrintStream printStream) {
        th.printStackTrace(printStream);
        List<Throwable> listZza = this.zzavo.zza(th, false);
        if (listZza == null) {
            return;
        }
        synchronized (listZza) {
            for (Throwable th2 : listZza) {
                printStream.print("Suppressed: ");
                th2.printStackTrace(printStream);
            }
        }
    }
}
