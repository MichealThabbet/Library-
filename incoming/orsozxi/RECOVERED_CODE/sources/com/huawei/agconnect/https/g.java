package com.huawei.agconnect.https;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes2.dex */
class g implements Interceptor {
    private int a;
    private int b;

    g(int i) {
        this.a = i;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response responseProceed;
        int i;
        Request request = chain.request();
        while (true) {
            responseProceed = chain.proceed(request);
            if (responseProceed.isSuccessful() || (i = this.b) >= this.a) {
                break;
            }
            this.b = i + 1;
        }
        return responseProceed;
    }
}
