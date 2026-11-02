package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;

/* loaded from: classes.dex */
final class zzfl implements zzej {
    private final long zzabf;
    private final int zzabg;
    private double zzabh;
    private final Object zzabj;
    private long zzakm;
    private final Clock zzsd;

    private zzfl(int i, long j) {
        this.zzabj = new Object();
        this.zzabg = 60;
        this.zzabh = 60;
        this.zzabf = 2000L;
        this.zzsd = DefaultClock.getInstance();
    }

    public zzfl() {
        this(60, 2000L);
    }

    @Override // com.google.android.gms.tagmanager.zzej
    public final boolean zzfm() {
        synchronized (this.zzabj) {
            long jCurrentTimeMillis = this.zzsd.currentTimeMillis();
            if (this.zzabh < this.zzabg) {
                double d = (jCurrentTimeMillis - this.zzakm) / this.zzabf;
                if (d > 0.0d) {
                    this.zzabh = Math.min(this.zzabg, this.zzabh + d);
                }
            }
            this.zzakm = jCurrentTimeMillis;
            if (this.zzabh >= 1.0d) {
                this.zzabh -= 1.0d;
                return true;
            }
            zzdi.zzac("No more tokens available.");
            return false;
        }
    }
}
