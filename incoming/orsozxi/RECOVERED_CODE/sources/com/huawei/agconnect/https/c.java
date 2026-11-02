package com.huawei.agconnect.https;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/* loaded from: classes2.dex */
class c implements Interceptor {

    private static class a extends RequestBody {
        private final RequestBody a;

        public a(RequestBody requestBody) {
            this.a = requestBody;
        }

        @Override // okhttp3.RequestBody
        public long contentLength() {
            return -1L;
        }

        @Override // okhttp3.RequestBody
        public MediaType contentType() {
            return MediaType.parse("application/x-gzip");
        }

        @Override // okhttp3.RequestBody
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            BufferedSink bufferedSinkBuffer = Okio.buffer(new GzipSink(bufferedSink));
            this.a.writeTo(bufferedSinkBuffer);
            bufferedSinkBuffer.close();
        }
    }

    static class b extends RequestBody {
        RequestBody a;
        Buffer b;

        b(RequestBody requestBody) throws IOException {
            this.a = null;
            this.b = null;
            this.a = requestBody;
            Buffer buffer = new Buffer();
            this.b = buffer;
            requestBody.writeTo(buffer);
        }

        @Override // okhttp3.RequestBody
        public long contentLength() {
            return this.b.size();
        }

        @Override // okhttp3.RequestBody
        public MediaType contentType() {
            return this.a.contentType();
        }

        @Override // okhttp3.RequestBody
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            bufferedSink.write(this.b.snapshot());
        }
    }

    c() {
    }

    private RequestBody a(RequestBody requestBody) throws IOException {
        return new b(requestBody);
    }

    private RequestBody b(RequestBody requestBody) {
        return new a(requestBody);
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        return (request.body() == null || request.header("Content-Encoding") != null) ? chain.proceed(request) : chain.proceed(request.newBuilder().header("Content-Encoding", "gzip").method(request.method(), a(b(request.body()))).build());
    }
}
