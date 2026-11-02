package com.huawei.secure.android.common.ssl;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import com.huawei.secure.android.common.ssl.hostname.StrictHostnameVerifier;
import com.huawei.secure.android.common.ssl.util.g;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.conn.ssl.X509HostnameVerifier;

/* loaded from: classes2.dex */
public class WebViewSSLCheckThread extends Thread {
    private static final String i = WebViewSSLCheckThread.class.getSimpleName();
    private SSLSocketFactory a;
    private HostnameVerifier b;
    private org.apache.http.conn.ssl.SSLSocketFactory c;
    private X509HostnameVerifier d;
    private SslErrorHandler e;
    private String f;
    private Callback g;
    private Context h;

    public interface Callback {
        void onCancel(Context context, String str);

        void onProceed(Context context, String str);
    }

    static class a implements okhttp3.Callback {
        final /* synthetic */ Callback a;
        final /* synthetic */ Context b;
        final /* synthetic */ String c;
        final /* synthetic */ SslErrorHandler d;

        a(Callback callback, Context context, String str, SslErrorHandler sslErrorHandler) {
            this.a = callback;
            this.b = context;
            this.c = str;
            this.d = sslErrorHandler;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            g.b(WebViewSSLCheckThread.i, "onFailure , IO Exception : " + iOException.getMessage());
            Callback callback = this.a;
            if (callback != null) {
                callback.onCancel(this.b, this.c);
            } else {
                this.d.cancel();
            }
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            g.b(WebViewSSLCheckThread.i, "onResponse . proceed");
            Callback callback = this.a;
            if (callback != null) {
                callback.onProceed(this.b, this.c);
            } else {
                this.d.proceed();
            }
        }
    }

    public WebViewSSLCheckThread() {
    }

    private void b() {
        g.c(i, "callbackCancel: ");
        Callback callback = this.g;
        if (callback != null) {
            callback.onCancel(this.h, this.f);
        } else if (this.e != null) {
            g.c(i, "callbackCancel 2: ");
            this.e.cancel();
        }
    }

    private void c() {
        g.c(i, "callbackProceed: ");
        Callback callback = this.g;
        if (callback != null) {
            callback.onProceed(this.h, this.f);
            return;
        }
        SslErrorHandler sslErrorHandler = this.e;
        if (sslErrorHandler != null) {
            sslErrorHandler.proceed();
        }
    }

    public static void checkServerCertificateWithOK(SslErrorHandler sslErrorHandler, String str, Context context) {
        checkServerCertificateWithOK(sslErrorHandler, str, context, null);
    }

    public X509HostnameVerifier getApacheHostnameVerifier() {
        return this.d;
    }

    public org.apache.http.conn.ssl.SSLSocketFactory getApacheSSLSocketFactory() {
        return this.c;
    }

    public Callback getCallback() {
        return this.g;
    }

    public Context getContext() {
        return this.h;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.b;
    }

    public SslErrorHandler getSslErrorHandler() {
        return this.e;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.a;
    }

    public String getUrl() {
        return this.f;
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x0148  */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 336
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.secure.android.common.ssl.WebViewSSLCheckThread.run():void");
    }

    public void setApacheHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        this.d = x509HostnameVerifier;
    }

    public void setApacheSSLSocketFactory(org.apache.http.conn.ssl.SSLSocketFactory sSLSocketFactory) {
        this.c = sSLSocketFactory;
    }

    public void setCallback(Callback callback) {
        this.g = callback;
    }

    public void setContext(Context context) {
        this.h = context;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.b = hostnameVerifier;
    }

    public void setSslErrorHandler(SslErrorHandler sslErrorHandler) {
        this.e = sslErrorHandler;
    }

    public void setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.a = sSLSocketFactory;
    }

    public void setUrl(String str) {
        this.f = str;
    }

    public WebViewSSLCheckThread(SslErrorHandler sslErrorHandler, String str, Context context) throws IllegalAccessException, NoSuchAlgorithmException, IOException, CertificateException, KeyManagementException, KeyStoreException {
        setSslErrorHandler(sslErrorHandler);
        setUrl(str);
        setContext(context);
        setSslSocketFactory(new SecureSSLSocketFactoryNew(new c(context)));
        setHostnameVerifier(new StrictHostnameVerifier());
        try {
            setApacheSSLSocketFactory(new SecureApacheSSLSocketFactory((KeyStore) null, new c(context)));
        } catch (UnrecoverableKeyException e) {
            g.b(i, "WebViewSSLCheckThread: UnrecoverableKeyException : " + e.getMessage());
        }
        setApacheHostnameVerifier(SecureApacheSSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
    }

    public static void checkServerCertificateWithOK(SslErrorHandler sslErrorHandler, String str, Context context, Callback callback) {
        if (sslErrorHandler == null || TextUtils.isEmpty(str) || context == null) {
            g.b(i, "checkServerCertificateWithOK: handler or url or context is null");
            return;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        try {
            SecureSSLSocketFactoryNew secureSSLSocketFactoryNew = new SecureSSLSocketFactoryNew(new c(context));
            secureSSLSocketFactoryNew.setContext(context);
            builder.sslSocketFactory(secureSSLSocketFactoryNew, new c(context));
            builder.hostnameVerifier(new StrictHostnameVerifier());
            builder.build().newCall(new Request.Builder().url(str).build()).enqueue(new a(callback, context, str, sslErrorHandler));
        } catch (Exception e) {
            g.b(i, "checkServerCertificateWithOK: exception : " + e.getMessage());
            sslErrorHandler.cancel();
        }
    }

    @Deprecated
    public WebViewSSLCheckThread(SslErrorHandler sslErrorHandler, String str, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier) {
        setSslErrorHandler(sslErrorHandler);
        setUrl(str);
        setSslSocketFactory(sSLSocketFactory);
        setHostnameVerifier(hostnameVerifier);
    }

    @Deprecated
    public WebViewSSLCheckThread(SslErrorHandler sslErrorHandler, String str, org.apache.http.conn.ssl.SSLSocketFactory sSLSocketFactory, X509HostnameVerifier x509HostnameVerifier) {
        setSslErrorHandler(sslErrorHandler);
        setUrl(str);
        setApacheSSLSocketFactory(sSLSocketFactory);
        setApacheHostnameVerifier(x509HostnameVerifier);
    }

    @Deprecated
    public WebViewSSLCheckThread(SslErrorHandler sslErrorHandler, String str, org.apache.http.conn.ssl.SSLSocketFactory sSLSocketFactory, X509HostnameVerifier x509HostnameVerifier, Callback callback, Context context) {
        this.e = sslErrorHandler;
        this.f = str;
        this.c = sSLSocketFactory;
        this.d = x509HostnameVerifier;
        this.g = callback;
        this.h = context;
    }
}
