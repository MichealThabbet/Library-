package com.huawei.hms.analytics;

import android.os.Bundle;
import com.huawei.hms.analytics.core.log.HiLog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/* loaded from: classes2.dex */
public final class cf implements Runnable {
    private Map<String, Method> hij;
    private Map<String, Object> ijk;
    private Bundle ikl;
    private Bundle klm;
    private String lmn;

    public cf(String str, Bundle bundle, Bundle bundle2, Map<String, Object> map, Map<String, Method> map2) {
        this.lmn = str;
        this.klm = bundle;
        this.ikl = bundle2;
        this.ijk = map;
        this.hij = map2;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str;
        HiLog.d("EventAdapterMission", "EventAdapterTask is running");
        Map<String, Object> map = this.ijk;
        if (map == null || this.hij == null) {
            return;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Method method = this.hij.get(entry.getKey());
            if (method == null) {
                HiLog.d("EventAdapterMission", "mOnEvent is null");
            } else {
                try {
                    method.invoke(entry.getValue(), this.lmn, this.klm, this.ikl);
                } catch (IllegalAccessException unused) {
                    str = "IllegalAccessException";
                    HiLog.w("EventAdapterMission", str);
                } catch (InvocationTargetException unused2) {
                    str = "InvocationTargetException";
                    HiLog.w("EventAdapterMission", str);
                }
            }
        }
    }
}
