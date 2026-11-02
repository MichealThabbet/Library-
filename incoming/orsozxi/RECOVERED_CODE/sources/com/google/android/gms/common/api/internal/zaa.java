package com.google.android.gms.common.api.internal;

import android.app.Activity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class zaa extends ActivityLifecycleObserver {
    private final WeakReference<C0027zaa> zacl;

    public zaa(Activity activity) {
        this(C0027zaa.zaa(activity));
    }

    private zaa(C0027zaa c0027zaa) {
        this.zacl = new WeakReference<>(c0027zaa);
    }

    @Override // com.google.android.gms.common.api.internal.ActivityLifecycleObserver
    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable) {
        C0027zaa c0027zaa = this.zacl.get();
        if (c0027zaa == null) {
            throw new IllegalStateException("The target activity has already been GC'd");
        }
        c0027zaa.zaa(runnable);
        return this;
    }

    /* renamed from: com.google.android.gms.common.api.internal.zaa$zaa, reason: collision with other inner class name */
    static class C0027zaa extends LifecycleCallback {
        private List<Runnable> zacm;

        /* JADX INFO: Access modifiers changed from: private */
        public static C0027zaa zaa(Activity activity) {
            C0027zaa c0027zaa;
            synchronized (activity) {
                LifecycleFragment fragment = getFragment(activity);
                c0027zaa = (C0027zaa) fragment.getCallbackOrNull("LifecycleObserverOnStop", C0027zaa.class);
                if (c0027zaa == null) {
                    c0027zaa = new C0027zaa(fragment);
                }
            }
            return c0027zaa;
        }

        private C0027zaa(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.zacm = new ArrayList();
            this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final synchronized void zaa(Runnable runnable) {
            this.zacm.add(runnable);
        }

        @Override // com.google.android.gms.common.api.internal.LifecycleCallback
        public void onStop() {
            List<Runnable> list;
            synchronized (this) {
                list = this.zacm;
                this.zacm = new ArrayList();
            }
            Iterator<Runnable> it2 = list.iterator();
            while (it2.hasNext()) {
                it2.next().run();
            }
        }
    }
}
