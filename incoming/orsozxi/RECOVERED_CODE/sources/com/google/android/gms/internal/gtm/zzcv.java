package com.google.android.gms.internal.gtm;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;

/* loaded from: classes.dex */
final class zzcv {
    private long startTime;
    private final Clock zzsd;

    public zzcv(Clock clock) {
        Preconditions.checkNotNull(clock);
        this.zzsd = clock;
    }

    public zzcv(Clock clock, long j) {
        Preconditions.checkNotNull(clock);
        this.zzsd = clock;
        this.startTime = j;
    }

    public final void start() {
        this.startTime = this.zzsd.elapsedRealtime();
    }

    public final void clear() {
        this.startTime = 0L;
    }

    public final boolean zzj(long j) {
        return this.startTime == 0 || this.zzsd.elapsedRealtime() - this.startTime > j;
    }
}
