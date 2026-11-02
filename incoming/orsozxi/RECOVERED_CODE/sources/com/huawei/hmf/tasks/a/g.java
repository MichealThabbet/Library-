package com.huawei.hmf.tasks.a;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;
import com.huawei.hmf.tasks.ExecuteResult;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* loaded from: classes2.dex */
public final class g extends Fragment {
    private static final WeakHashMap<Activity, WeakReference<g>> b = new WeakHashMap<>();
    private final List<WeakReference<ExecuteResult<?>>> a = new ArrayList();

    private static g a(Activity activity) {
        g gVarA;
        WeakReference<g> weakReference = b.get(activity);
        if (weakReference != null && weakReference.get() != null) {
            return weakReference.get();
        }
        FragmentManager fragmentManager = activity.getFragmentManager();
        try {
            g gVar = (g) fragmentManager.findFragmentByTag("com.huawei.hmf.tasks.lifecycle_fragment_tag");
            if (gVar == null) {
                try {
                    gVarA = a(fragmentManager);
                } catch (ClassCastException e) {
                    e = e;
                    gVarA = gVar;
                    Log.e("LifecycleCallbackFrg", "found LifecycleCallbackFragment but the type do not match. " + e.getMessage());
                    return gVarA;
                }
            } else {
                gVarA = gVar;
            }
            try {
                b.put(activity, new WeakReference<>(gVarA));
            } catch (ClassCastException e2) {
                e = e2;
                Log.e("LifecycleCallbackFrg", "found LifecycleCallbackFragment but the type do not match. " + e.getMessage());
                return gVarA;
            }
        } catch (ClassCastException e3) {
            e = e3;
            gVarA = null;
        }
        return gVarA;
    }

    private static g a(FragmentManager fragmentManager) {
        g gVar;
        g gVar2 = null;
        try {
            gVar = new g();
        } catch (Exception e) {
            e = e;
        }
        try {
            fragmentManager.beginTransaction().add(gVar, "com.huawei.hmf.tasks.lifecycle_fragment_tag").commitAllowingStateLoss();
            return gVar;
        } catch (Exception e2) {
            e = e2;
            gVar2 = gVar;
            Log.e("LifecycleCallbackFrg", "create fragment failed." + e.getMessage());
            return gVar2;
        }
    }

    public static void a(Activity activity, ExecuteResult executeResult) {
        g gVarA = a(activity);
        if (gVarA != null) {
            synchronized (gVarA.a) {
                gVarA.a.add(new WeakReference<>(executeResult));
            }
        }
    }

    @Override // android.app.Fragment
    public final void onStop() {
        super.onStop();
        synchronized (this.a) {
            Iterator<WeakReference<ExecuteResult<?>>> it2 = this.a.iterator();
            while (it2.hasNext()) {
                ExecuteResult<?> executeResult = it2.next().get();
                if (executeResult != null) {
                    executeResult.cancel();
                }
            }
            this.a.clear();
        }
    }
}
