package com.google.android.gms.internal.gtm;

import android.content.Context;
import java.util.concurrent.ExecutorService;

/* loaded from: classes.dex */
public final class zzpa {
    private static final Integer zzavg = 0;
    private static final Integer zzavh = 1;
    private final ExecutorService zzajm;
    private final Context zzrm;

    public zzpa(Context context) {
        this(context, zzdf.zzgp().zzr(zzdi.zzadg));
    }

    private zzpa(Context context, ExecutorService executorService) {
        this.zzrm = context;
        this.zzajm = executorService;
    }
}
