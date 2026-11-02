package com.huawei.agconnect.https.adapter;

import com.huawei.agconnect.https.Adapter;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class b<Request> implements Adapter<Request, RequestBody> {
    private static final MediaType a = MediaType.parse("application/json; charset=UTF-8");

    @Override // com.huawei.agconnect.https.Adapter
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public RequestBody adapter(Request request) throws IOException {
        try {
            return RequestBody.create(a, new JSONEncodeUtil().toJson(request));
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }
}
