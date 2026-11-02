package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.Clock;

/* loaded from: classes.dex */
final class zzdg implements zzej {
    private long zzabi;
    private final Clock zzsd;
    private final String zzup;
    private final Object zzabj = new Object();
    private final int zzabg = 5;
    private double zzabh = Math.min(1, 5);
    private final long zzabf = 900000;
    private final long zzaia = 5000;

    public zzdg(int i, int i2, long j, long j2, String str, Clock clock) {
        this.zzup = str;
        this.zzsd = clock;
    }

    @Override // com.google.android.gms.tagmanager.zzej
    public final boolean zzfm() {
        synchronized (this.zzabj) {
            long jCurrentTimeMillis = this.zzsd.currentTimeMillis();
            if (jCurrentTimeMillis - this.zzabi < this.zzaia) {
                String str = this.zzup;
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34);
                sb.append("Excessive ");
                sb.append(str);
                sb.append(" detected; call ignored.");
                zzdi.zzac(sb.toString());
                return false;
            }
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
            String str2 = this.zzup;
            StringBuilder sb2 = new StringBuilder(String.valueOf(str2).length() + 34);
            sb2.append("Excessive ");
            sb2.append(str2);
            sb2.append(" detected; call ignored.");
            zzdi.zzac(sb2.toString());
            return false;
        }
    }
}
