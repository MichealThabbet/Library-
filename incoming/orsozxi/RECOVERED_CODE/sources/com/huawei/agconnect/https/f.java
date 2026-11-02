package com.huawei.agconnect.https;

import okhttp3.Request;

/* loaded from: classes2.dex */
class f<HttpsRequest> {
    private HttpsRequest a;

    private f(HttpsRequest httpsrequest) {
        this.a = httpsrequest;
    }

    static <HttpsRequest> f a(HttpsRequest httpsrequest) {
        return new f(httpsrequest);
    }

    Request.Builder a() {
        return a.a(this.a);
    }
}
