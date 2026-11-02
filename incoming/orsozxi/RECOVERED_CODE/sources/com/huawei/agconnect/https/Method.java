package com.huawei.agconnect.https;

import com.huawei.agconnect.https.Adapter;
import java.io.IOException;
import okhttp3.Request;
import okhttp3.RequestBody;

/* loaded from: classes2.dex */
public abstract class Method<HttpsRequest> {
    HttpsRequest httpsRequest;

    public static class Get<HttpsRequest> extends Method {
        public Get(HttpsRequest httpsrequest) {
            this.httpsRequest = httpsrequest;
        }

        @Override // com.huawei.agconnect.https.Method
        public Request.Builder create() {
            return f.a(this.httpsRequest).a();
        }
    }

    public static class Post<HttpsRequest> extends Method {
        Adapter.Factory factory;

        public Post(HttpsRequest httpsrequest, Adapter.Factory factory) {
            init(httpsrequest, factory);
        }

        private void init(HttpsRequest httpsrequest, Adapter.Factory factory) {
            this.httpsRequest = httpsrequest;
            this.factory = factory;
        }

        @Override // com.huawei.agconnect.https.Method
        public Request.Builder create() {
            Request.Builder builderA = f.a(this.httpsRequest).a();
            try {
                if (this.factory.requestBodyAdapter() != null) {
                    return createBody(builderA, (RequestBody) this.factory.requestBodyAdapter().adapter(this.httpsRequest));
                }
                throw new IllegalArgumentException("RequestBodyAdapter should not be null.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public Request.Builder createBody(Request.Builder builder, RequestBody requestBody) {
            builder.post(requestBody);
            return builder;
        }
    }

    public static class Put<HttpsRequest> extends Post {
        public Put(HttpsRequest httpsrequest, Adapter.Factory factory) {
            super(httpsrequest, factory);
        }

        @Override // com.huawei.agconnect.https.Method.Post
        public Request.Builder createBody(Request.Builder builder, RequestBody requestBody) {
            builder.put(requestBody);
            return builder;
        }
    }

    public static final class a<HttpsRequest> extends Method {
        Adapter.Factory a;

        public a(HttpsRequest httpsrequest) {
            this.httpsRequest = httpsrequest;
        }

        public a(HttpsRequest httpsrequest, Adapter.Factory factory) {
            this.httpsRequest = httpsrequest;
            this.a = factory;
        }

        @Override // com.huawei.agconnect.https.Method
        public Request.Builder create() {
            Request.Builder builderA = f.a(this.httpsRequest).a();
            Adapter.Factory factory = this.a;
            if (factory == null || factory.requestBodyAdapter() == null) {
                builderA.delete();
            } else {
                try {
                    builderA.delete((RequestBody) this.a.requestBodyAdapter().adapter(this.httpsRequest));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return builderA;
        }
    }

    abstract Request.Builder create();
}
