package com.google.android.gms.tagmanager;

/* loaded from: classes.dex */
final class zzfh {
    private zzdz<com.google.android.gms.internal.gtm.zzl> zzakf;
    private com.google.android.gms.internal.gtm.zzl zzakg;

    public zzfh(zzdz<com.google.android.gms.internal.gtm.zzl> zzdzVar, com.google.android.gms.internal.gtm.zzl zzlVar) {
        this.zzakf = zzdzVar;
        this.zzakg = zzlVar;
    }

    public final zzdz<com.google.android.gms.internal.gtm.zzl> zzjh() {
        return this.zzakf;
    }

    public final com.google.android.gms.internal.gtm.zzl zzji() {
        return this.zzakg;
    }

    public final int getSize() {
        int iZzse = this.zzakf.getObject().zzse();
        com.google.android.gms.internal.gtm.zzl zzlVar = this.zzakg;
        return iZzse + (zzlVar == null ? 0 : zzlVar.zzse());
    }
}
