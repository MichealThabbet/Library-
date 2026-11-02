package com.google.android.gms.internal.gtm;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
final class zzpi extends WeakReference<Throwable> {
    private final int zzavn;

    public zzpi(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, null);
        if (th == null) {
            throw new NullPointerException("The referent cannot be null");
        }
        this.zzavn = System.identityHashCode(th);
    }

    public final int hashCode() {
        return this.zzavn;
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (this == obj) {
                return true;
            }
            zzpi zzpiVar = (zzpi) obj;
            if (this.zzavn == zzpiVar.zzavn && get() == zzpiVar.get()) {
                return true;
            }
        }
        return false;
    }
}
