package com.huawei.agconnect.https;

import com.huawei.agconnect.https.Adapter;
import com.huawei.agconnect.https.adapter.StringAdapterFactory;
import java.io.IOException;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* loaded from: classes2.dex */
public class HttpsResult {
    Response rawResponse;

    HttpsResult(boolean z, int i, Response response) {
        this.rawResponse = response;
    }

    public int code() {
        Response response = this.rawResponse;
        if (response != null) {
            return response.code();
        }
        return -1;
    }

    public String getErrorMsg() {
        if (isSuccess()) {
            return null;
        }
        Response response = this.rawResponse;
        return response == null ? "rawResponse is null" : response.message();
    }

    public <T> T getResponse(Class<T> cls, Adapter.Factory factory) throws IllegalAccessException, InstantiationException {
        Adapter<ResponseBody, T> adapterResponseBodyAdapter = factory == null ? new StringAdapterFactory().responseBodyAdapter(cls) : factory.responseBodyAdapter(cls);
        try {
            if (adapterResponseBodyAdapter == null) {
                throw new IllegalArgumentException("ResponseBodyAdapter should not be null.");
            }
            try {
                T tAdapter = adapterResponseBodyAdapter.adapter(this.rawResponse.body());
                if (tAdapter != null) {
                    return tAdapter;
                }
                try {
                    return cls.newInstance();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e2) {
                    throw new RuntimeException(e2);
                }
            } catch (IOException e3) {
                throw new RuntimeException(e3);
            }
        } catch (Throwable th) {
            try {
                cls.newInstance();
                throw th;
            } catch (IllegalAccessException e4) {
                throw new RuntimeException(e4);
            } catch (InstantiationException e5) {
                throw new RuntimeException(e5);
            }
        }
    }

    public String getResponse() {
        try {
            return (this.rawResponse == null || this.rawResponse.body() == null) ? "" : this.rawResponse.body().string();
        } catch (IOException unused) {
            return "";
        }
    }

    public boolean isSuccess() {
        Response response = this.rawResponse;
        return response != null && response.isSuccessful();
    }
}
