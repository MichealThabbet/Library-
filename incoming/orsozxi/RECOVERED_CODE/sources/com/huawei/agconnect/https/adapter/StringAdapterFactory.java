package com.huawei.agconnect.https.adapter;

import com.huawei.agconnect.https.Adapter;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/* loaded from: classes2.dex */
public class StringAdapterFactory extends Adapter.Factory {
    @Override // com.huawei.agconnect.https.Adapter.Factory
    public <F> Adapter<F, RequestBody> requestBodyAdapter() {
        return new d();
    }

    @Override // com.huawei.agconnect.https.Adapter.Factory
    public <T> Adapter<ResponseBody, T> responseBodyAdapter(Class<T> cls) {
        return new c(cls);
    }
}
