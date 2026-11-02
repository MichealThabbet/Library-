package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
public abstract class zzan extends zzam {
    private boolean zzwd;

    protected zzan(zzap zzapVar) {
        super(zzapVar);
    }

    protected abstract void zzaw();

    public final boolean isInitialized() {
        return this.zzwd;
    }

    protected final void zzdb() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void zzag() {
        zzaw();
        this.zzwd = true;
    }
}
