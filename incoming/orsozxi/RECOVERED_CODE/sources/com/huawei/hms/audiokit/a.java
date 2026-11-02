package com.huawei.hms.audiokit;

import android.util.Log;
import com.huawei.hms.feature.dynamic.DynamicModule;

/* loaded from: classes2.dex */
class a implements Runnable {
    a() {
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            b.b(b.f);
        } catch (DynamicModule.LoadingException e) {
            Log.e("DynamicRemoteImpl", "Try to reload failed.", e);
        } catch (ClassNotFoundException e2) {
            e = e2;
            Log.w("DynamicRemoteImpl", e.getClass().getSimpleName() + ": " + e.getMessage());
        } catch (IllegalAccessException e3) {
            e = e3;
            Log.w("DynamicRemoteImpl", e.getClass().getSimpleName() + ": " + e.getMessage());
        } catch (InstantiationException e4) {
            e = e4;
            Log.w("DynamicRemoteImpl", e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
