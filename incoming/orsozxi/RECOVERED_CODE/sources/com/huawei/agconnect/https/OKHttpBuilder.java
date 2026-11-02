package com.huawei.agconnect.https;

import android.util.Log;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/* loaded from: classes2.dex */
public class OKHttpBuilder {
    private OkHttpClient.Builder builder = new OkHttpClient.Builder();

    public OKHttpBuilder addInterceptor(Interceptor interceptor) {
        if (interceptor == null) {
            throw new IllegalArgumentException("interceptor == null");
        }
        this.builder.addInterceptor(interceptor);
        return this;
    }

    public OKHttpBuilder authenticator(Authenticator authenticator) {
        this.builder.authenticator(authenticator);
        return this;
    }

    public OkHttpClient build() {
        return this.builder.build();
    }

    public OkHttpClient buildWithTimeOut(long j, TimeUnit timeUnit) {
        return this.builder.connectTimeout(j, timeUnit).readTimeout(j, timeUnit).writeTimeout(j, timeUnit).build();
    }

    public OKHttpBuilder connectTimeout(long j) {
        this.builder.connectTimeout(j, TimeUnit.MILLISECONDS);
        return this;
    }

    public OKHttpBuilder enableGzip() {
        this.builder.addInterceptor(new c());
        return this;
    }

    public OKHttpBuilder readTimeout(long j) {
        this.builder.readTimeout(j, TimeUnit.MILLISECONDS);
        return this;
    }

    public OKHttpBuilder setRetryTimes(int i) {
        this.builder.addInterceptor(new g(i));
        return this;
    }

    public OKHttpBuilder sslSocketFactory(SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager) {
        try {
            this.builder.sslSocketFactory(sSLSocketFactory, x509TrustManager);
        } catch (NoSuchMethodError unused) {
            Log.d("OKHttpBuilder", "use default ssl");
        }
        return this;
    }

    public OKHttpBuilder writeTimeout(long j) {
        this.builder.writeTimeout(j, TimeUnit.MILLISECONDS);
        return this;
    }
}
