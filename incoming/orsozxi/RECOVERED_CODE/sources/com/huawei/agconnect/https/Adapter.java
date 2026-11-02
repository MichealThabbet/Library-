package com.huawei.agconnect.https;

import java.io.IOException;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/* loaded from: classes2.dex */
public interface Adapter<From, To> {

    public static class Factory {
        public <F> Adapter<F, RequestBody> requestBodyAdapter() {
            return null;
        }

        public <T> Adapter<ResponseBody, T> responseBodyAdapter(Class<T> cls) {
            return null;
        }
    }

    To adapter(From from) throws IOException;
}
