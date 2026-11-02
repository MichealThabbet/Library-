package com.huawei.agconnect.common.api;

import android.content.Context;
import com.huawei.agconnect.credential.obs.p;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/* loaded from: classes2.dex */
public class Client {
    public static OkHttpClient build(Context context, List<Interceptor> list) {
        return new p(context, list, false).a();
    }

    public static OkHttpClient build(Context context, List<Interceptor> list, boolean z) {
        return new p(context, list, z).a();
    }
}
