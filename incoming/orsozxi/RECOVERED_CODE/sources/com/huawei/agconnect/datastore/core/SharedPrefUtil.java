package com.huawei.agconnect.datastore.core;

import android.content.Context;
import java.util.Map;

/* loaded from: classes2.dex */
public class SharedPrefUtil {
    private static final String TAG = "SharedPrefUtil";
    private static SharedPrefUtil instance = new SharedPrefUtil();

    private SharedPrefUtil() {
    }

    public static SharedPrefUtil getInstance() {
        return instance;
    }

    public static synchronized void init(Context context) {
        Platform.get().init(context);
    }

    public static synchronized void init(Object obj) {
        Platform.get().init(obj);
    }

    public synchronized Object get(String str, String str2, Class<?> cls, Object obj, Class<?> cls2) {
        return Platform.get().get(str, str2, cls, obj, cls2);
    }

    public Map<String, ?> getAll(String str) {
        return Platform.get().getAll(str);
    }

    public synchronized void put(String str, String str2, Class<?> cls, Object obj, Class<?> cls2) {
        Platform.get().put(str, str2, cls, obj, cls2);
    }

    public void remove(String str, String str2) {
        Platform.get().remove(str, str2);
    }
}
