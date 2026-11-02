package com.google.android.gms.tagmanager;

/* loaded from: classes.dex */
final class zzab implements zzac {
    private final /* synthetic */ zzy zzafg;
    private Long zzafh;
    private final /* synthetic */ boolean zzafi;

    zzab(zzy zzyVar, boolean z) {
        this.zzafg = zzyVar;
        this.zzafi = z;
    }

    @Override // com.google.android.gms.tagmanager.zzac
    public final boolean zzb(Container container) {
        if (!this.zzafi) {
            return !container.isDefault();
        }
        long lastRefreshTime = container.getLastRefreshTime();
        if (this.zzafh == null) {
            this.zzafh = Long.valueOf(this.zzafg.zzaex.zzhl());
        }
        return lastRefreshTime + this.zzafh.longValue() >= this.zzafg.zzsd.currentTimeMillis();
    }
}
