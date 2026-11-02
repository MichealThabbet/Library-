package com.huawei.agconnect.https;

import android.content.Context;
import com.huawei.agconnect.https.Service;
import com.huawei.agconnect.https.connector.HttpsPlatform;
import java.util.concurrent.Executor;
import okhttp3.OkHttpClient;

/* loaded from: classes2.dex */
public class HttpsKit {
    private OkHttpClient client;
    private Executor executor;

    public static final class Builder {
        OkHttpClient client;
        Executor executor;

        public HttpsKit build() {
            if (this.client == null) {
                this.client = new OkHttpClient();
            }
            if (this.executor == null) {
                this.executor = e.a.a();
            }
            return new HttpsKit(this.client, this.executor);
        }

        public Builder client(OkHttpClient okHttpClient) {
            this.client = okHttpClient;
            return this;
        }

        public Builder executor(Executor executor) {
            this.executor = executor;
            return this;
        }
    }

    private HttpsKit(OkHttpClient okHttpClient, Executor executor) {
        this.client = okHttpClient;
        this.executor = executor;
    }

    public OkHttpClient client() {
        return this.client;
    }

    public Service create(Context context) {
        HttpsPlatform.getInstance().init(com.huawei.agconnect.https.connector.a.a(context));
        return Service.Factory.get(this);
    }

    public Executor executor() {
        return this.executor;
    }
}
