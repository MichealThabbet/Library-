package com.huawei.agconnect.https.adapter;

import com.huawei.agconnect.https.Adapter;
import java.io.IOException;
import okhttp3.ResponseBody;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class c<T> implements Adapter<ResponseBody, T> {
    private final Class<T> a;

    public c(Class<T> cls) {
        this.a = cls;
    }

    @Override // com.huawei.agconnect.https.Adapter
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public T adapter(ResponseBody responseBody) throws IOException {
        if (responseBody == null) {
            throw new IOException("responseBody is null");
        }
        try {
            return (T) JSONDecodeUtil.getObject(responseBody.string(), this.a);
        } catch (IllegalAccessException e) {
            throw new IOException("IllegalAccessException:", e);
        } catch (InstantiationException e2) {
            throw new IOException("InstantiationException", e2);
        } catch (JSONException unused) {
            throw new IOException("the response is not json");
        }
    }
}
