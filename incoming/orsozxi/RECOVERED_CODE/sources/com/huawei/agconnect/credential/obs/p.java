package com.huawei.agconnect.credential.obs;

import android.content.Context;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.agconnect.https.OKHttpBuilder;
import com.huawei.secure.android.common.ssl.SecureSSLSocketFactoryNew;
import com.huawei.secure.android.common.ssl.SecureX509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/* loaded from: classes2.dex */
public class p {
    private OKHttpBuilder a;

    public p(Context context, List<Interceptor> list, boolean z) {
        this.a = a(context);
        if (list != null && list.size() > 0) {
            Iterator<Interceptor> it2 = list.iterator();
            while (it2.hasNext()) {
                this.a.addInterceptor(it2.next());
            }
        }
        if (z) {
            this.a.connectTimeout(5000L).readTimeout(5000L).writeTimeout(5000L);
        }
    }

    private OKHttpBuilder a(Context context) {
        OKHttpBuilder oKHttpBuilder;
        try {
            try {
                try {
                    try {
                        try {
                            this.a = new OKHttpBuilder().sslSocketFactory(SecureSSLSocketFactoryNew.getInstance(context), new SecureX509TrustManager(context));
                        } catch (NoSuchAlgorithmException e) {
                            Logger.e("ClientImpl", "NoSuchAlgorithmException", e);
                            oKHttpBuilder = new OKHttpBuilder();
                            this.a = oKHttpBuilder;
                            return this.a;
                        }
                    } catch (KeyManagementException e2) {
                        Logger.e("ClientImpl", "KeyManagementException", e2);
                        oKHttpBuilder = new OKHttpBuilder();
                        this.a = oKHttpBuilder;
                        return this.a;
                    }
                } catch (IllegalAccessException e3) {
                    Logger.e("ClientImpl", "IllegalAccessException", e3);
                    oKHttpBuilder = new OKHttpBuilder();
                    this.a = oKHttpBuilder;
                    return this.a;
                } catch (KeyStoreException e4) {
                    Logger.e("ClientImpl", "KeyStoreException", e4);
                    oKHttpBuilder = new OKHttpBuilder();
                    this.a = oKHttpBuilder;
                    return this.a;
                }
            } catch (IOException e5) {
                Logger.e("ClientImpl", "IOException", e5);
                oKHttpBuilder = new OKHttpBuilder();
                this.a = oKHttpBuilder;
                return this.a;
            } catch (CertificateException e6) {
                Logger.e("ClientImpl", "CertificateException", e6);
                oKHttpBuilder = new OKHttpBuilder();
                this.a = oKHttpBuilder;
                return this.a;
            }
            return this.a;
        } catch (Throwable th) {
            this.a = new OKHttpBuilder();
            throw th;
        }
    }

    public p a(Authenticator authenticator) {
        if (authenticator != null) {
            this.a.authenticator(authenticator);
        }
        return this;
    }

    public OkHttpClient a() {
        return this.a.build();
    }

    public OkHttpClient a(long j, TimeUnit timeUnit) {
        return this.a.buildWithTimeOut(j, timeUnit);
    }
}
