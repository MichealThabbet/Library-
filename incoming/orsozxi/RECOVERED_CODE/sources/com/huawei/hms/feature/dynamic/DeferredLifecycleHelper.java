package com.huawei.hms.feature.dynamic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.huawei.hms.feature.dynamic.LifecycleDelegate;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes2.dex */
public abstract class DeferredLifecycleHelper<T extends LifecycleDelegate> {
    public static final int STATUS_ONCREATED = 1;
    public static final int STATUS_ONCREATEVIEW = 2;
    public static final int STATUS_ONINFLATE = 0;
    public static final int STATUS_ONRESUME = 5;
    public static final int STATUS_ONSTART = 4;
    private static final String a = DeferredLifecycleHelper.class.getSimpleName();
    private T b;
    private Bundle c;
    private LinkedList<a> d;
    private OnDelegateCreatedListener<T> e = (OnDelegateCreatedListener<T>) new OnDelegateCreatedListener<T>() { // from class: com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.1
        @Override // com.huawei.hms.feature.dynamic.OnDelegateCreatedListener
        public final void onDelegateCreated(T t) {
            DeferredLifecycleHelper.this.b = t;
            Iterator it2 = DeferredLifecycleHelper.this.d.iterator();
            while (it2.hasNext()) {
                ((a) it2.next()).a(DeferredLifecycleHelper.this.b);
            }
            DeferredLifecycleHelper.this.d.clear();
            DeferredLifecycleHelper.c(DeferredLifecycleHelper.this);
        }
    };

    interface a {
        int a();

        void a(LifecycleDelegate lifecycleDelegate);
    }

    private void a(int i) {
        while (!this.d.isEmpty() && this.d.getLast().a() >= i) {
            this.d.removeLast();
        }
    }

    private final void a(Bundle bundle, a aVar) {
        T t = this.b;
        if (t != null) {
            aVar.a(t);
            return;
        }
        if (this.d == null) {
            this.d = new LinkedList<>();
        }
        this.d.add(aVar);
        if (bundle != null) {
            Bundle bundle2 = this.c;
            if (bundle2 == null) {
                this.c = (Bundle) bundle.clone();
            } else {
                bundle2.putAll(bundle);
            }
        }
        createDelegate(this.e);
    }

    static /* synthetic */ Bundle c(DeferredLifecycleHelper deferredLifecycleHelper) {
        deferredLifecycleHelper.c = null;
        return null;
    }

    protected abstract void createDelegate(OnDelegateCreatedListener<T> onDelegateCreatedListener);

    public T getDelegate() {
        return this.b;
    }

    public void onCreate(final Bundle bundle) {
        a(bundle, new a() { // from class: com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.3
            @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.a
            public final int a() {
                return 1;
            }

            @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.a
            public final void a(LifecycleDelegate lifecycleDelegate) {
                Log.d(DeferredLifecycleHelper.a, "IDelegateLifeCycleCall onCreate:");
                lifecycleDelegate.onCreate(bundle);
            }
        });
    }

    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        a(bundle, new a() { // from class: com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.4
            @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.a
            public final int a() {
                return 2;
            }

            @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.a
            public final void a(LifecycleDelegate lifecycleDelegate) {
                frameLayout.removeAllViews();
                frameLayout.addView(DeferredLifecycleHelper.this.b.onCreateView(layoutInflater, viewGroup, bundle));
            }
        });
        return frameLayout;
    }

    public void onDestroy() {
        T t = this.b;
        if (t != null) {
            t.onDestroy();
        } else {
            a(0);
        }
    }

    public void onDestroyView() {
        T t = this.b;
        if (t != null) {
            t.onDestroyView();
        } else {
            a(1);
        }
    }

    public void onInflate(final Activity activity, final Bundle bundle, final Bundle bundle2) {
        a(bundle2, new a() { // from class: com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.2
            @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.a
            public final int a() {
                return 0;
            }

            @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.a
            public final void a(LifecycleDelegate lifecycleDelegate) {
                DeferredLifecycleHelper.this.b.onInflate(activity, bundle, bundle2);
            }
        });
    }

    public void onLowMemory() {
        T t = this.b;
        if (t != null) {
            t.onLowMemory();
        }
    }

    public void onPause() {
        T t = this.b;
        if (t != null) {
            t.onPause();
        } else {
            a(5);
        }
    }

    public void onResume() {
        a((Bundle) null, new a() { // from class: com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.6
            @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.a
            public final int a() {
                return 5;
            }

            @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.a
            public final void a(LifecycleDelegate lifecycleDelegate) {
                Log.d(DeferredLifecycleHelper.a, "IDelegateLifeCycleCall onResume:");
                lifecycleDelegate.onResume();
            }
        });
    }

    public void onSaveInstanceState(Bundle bundle) {
        T t = this.b;
        if (t != null) {
            t.onSaveInstanceState(bundle);
            return;
        }
        Bundle bundle2 = this.c;
        if (bundle2 != null) {
            bundle.putAll(bundle2);
        }
    }

    public void onStart() {
        a((Bundle) null, new a() { // from class: com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.5
            @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.a
            public final int a() {
                return 4;
            }

            @Override // com.huawei.hms.feature.dynamic.DeferredLifecycleHelper.a
            public final void a(LifecycleDelegate lifecycleDelegate) {
                Log.d(DeferredLifecycleHelper.a, "IDelegateLifeCycleCall onStart:");
                lifecycleDelegate.onStart();
            }
        });
    }

    public void onStop() {
        T t = this.b;
        if (t != null) {
            t.onStop();
        } else {
            a(4);
        }
    }
}
