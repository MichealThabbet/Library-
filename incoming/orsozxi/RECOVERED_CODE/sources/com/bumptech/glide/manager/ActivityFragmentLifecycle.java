package com.bumptech.glide.manager;

import com.bumptech.glide.util.Util;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
class ActivityFragmentLifecycle implements Lifecycle {
    private boolean isDestroyed;
    private boolean isStarted;
    private final Set<LifecycleListener> lifecycleListeners = Collections.newSetFromMap(new WeakHashMap());

    ActivityFragmentLifecycle() {
    }

    @Override // com.bumptech.glide.manager.Lifecycle
    public void addListener(LifecycleListener lifecycleListener) {
        this.lifecycleListeners.add(lifecycleListener);
        if (this.isDestroyed) {
            lifecycleListener.onDestroy();
        } else if (this.isStarted) {
            lifecycleListener.onStart();
        } else {
            lifecycleListener.onStop();
        }
    }

    @Override // com.bumptech.glide.manager.Lifecycle
    public void removeListener(LifecycleListener lifecycleListener) {
        this.lifecycleListeners.remove(lifecycleListener);
    }

    void onStart() {
        this.isStarted = true;
        Iterator it2 = Util.getSnapshot(this.lifecycleListeners).iterator();
        while (it2.hasNext()) {
            ((LifecycleListener) it2.next()).onStart();
        }
    }

    void onStop() {
        this.isStarted = false;
        Iterator it2 = Util.getSnapshot(this.lifecycleListeners).iterator();
        while (it2.hasNext()) {
            ((LifecycleListener) it2.next()).onStop();
        }
    }

    void onDestroy() {
        this.isDestroyed = true;
        Iterator it2 = Util.getSnapshot(this.lifecycleListeners).iterator();
        while (it2.hasNext()) {
            ((LifecycleListener) it2.next()).onDestroy();
        }
    }
}
