package com.google.android.gms.internal.gtm;

import com.google.android.gms.common.util.Clock;

/* loaded from: classes.dex */
public final class zzcg {
    private final long zzabf;
    private final int zzabg;
    private double zzabh;
    private long zzabi;
    private final Object zzabj;
    private final Clock zzsd;
    private final String zzup;

    private zzcg(int i, long j, String str, Clock clock) {
        this.zzabj = new Object();
        this.zzabg = 60;
        this.zzabh = 60;
        this.zzabf = 2000L;
        this.zzup = str;
        this.zzsd = clock;
    }

    public zzcg(String str, Clock clock) {
        this(60, 2000L, str, clock);
    }

    public final boolean zzfm() {
        synchronized (this.zzabj) {
            long jCurrentTimeMillis = this.zzsd.currentTimeMillis();
            if (this.zzabh < this.zzabg) {
                double d = (jCurrentTimeMillis - this.zzabi) / this.zzabf;
                if (d > 0.0d) {
                    this.zzabh = Math.min(this.zzabg, this.zzabh + d);
                }
            }
            this.zzabi = jCurrentTimeMillis;
            if (this.zzabh >= 1.0d) {
                this.zzabh -= 1.0d;
                return true;
            }
            String str = this.zzup;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34);
            sb.append("Excessive ");
            sb.append(str);
            sb.append(" detected; call ignored.");
            zzch.zzac(sb.toString());
            return false;
        }
    }
}
