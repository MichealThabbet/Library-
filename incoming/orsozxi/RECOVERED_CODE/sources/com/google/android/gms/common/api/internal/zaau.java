package com.google.android.gms.common.api.internal;

/* loaded from: classes.dex */
abstract class zaau implements Runnable {
    private final /* synthetic */ zaak zagj;

    private zaau(zaak zaakVar) {
        this.zagj = zaakVar;
    }

    protected abstract void zaan();

    @Override // java.lang.Runnable
    public void run() {
        this.zagj.zaeo.lock();
        try {
            if (Thread.interrupted()) {
                return;
            }
            zaan();
        } catch (RuntimeException e) {
            this.zagj.zaft.zab(e);
        } finally {
            this.zagj.zaeo.unlock();
        }
    }

    /* synthetic */ zaau(zaak zaakVar, zaal zaalVar) {
        this(zaakVar);
    }
}
