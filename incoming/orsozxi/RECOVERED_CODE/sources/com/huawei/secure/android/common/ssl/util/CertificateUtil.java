package com.huawei.secure.android.common.ssl.util;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/* loaded from: classes2.dex */
public final class CertificateUtil {
    private static final String a = "CertificateUtil";

    private CertificateUtil() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.io.InputStream] */
    public static X509Certificate getHwCbgRootCA(Context context) throws Throwable {
        InputStream inputStreamOpen;
        InputStream inputStream = null;
        X509Certificate x509Certificate = null;
        try {
            try {
                KeyStore keyStore = KeyStore.getInstance(j.e);
                inputStreamOpen = context.getAssets().open("hmsrootcas.bks");
                try {
                    inputStreamOpen.reset();
                    keyStore.load(inputStreamOpen, "".toCharArray());
                    x509Certificate = (X509Certificate) keyStore.getCertificate(j.f);
                    context = inputStreamOpen;
                } catch (IOException e) {
                    e = e;
                    g.b(a, "loadBksCA: exception : " + e.getMessage());
                    context = inputStreamOpen;
                    f.a((InputStream) context);
                    return x509Certificate;
                } catch (KeyStoreException e2) {
                    e = e2;
                    g.b(a, "loadBksCA: exception : " + e.getMessage());
                    context = inputStreamOpen;
                    f.a((InputStream) context);
                    return x509Certificate;
                } catch (NoSuchAlgorithmException e3) {
                    e = e3;
                    g.b(a, "loadBksCA: exception : " + e.getMessage());
                    context = inputStreamOpen;
                    f.a((InputStream) context);
                    return x509Certificate;
                } catch (CertificateException e4) {
                    e = e4;
                    g.b(a, "loadBksCA: exception : " + e.getMessage());
                    context = inputStreamOpen;
                    f.a((InputStream) context);
                    return x509Certificate;
                }
            } catch (Throwable th) {
                inputStream = context;
                th = th;
                f.a(inputStream);
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            inputStreamOpen = null;
            g.b(a, "loadBksCA: exception : " + e.getMessage());
            context = inputStreamOpen;
            f.a((InputStream) context);
            return x509Certificate;
        } catch (KeyStoreException e6) {
            e = e6;
            inputStreamOpen = null;
            g.b(a, "loadBksCA: exception : " + e.getMessage());
            context = inputStreamOpen;
            f.a((InputStream) context);
            return x509Certificate;
        } catch (NoSuchAlgorithmException e7) {
            e = e7;
            inputStreamOpen = null;
            g.b(a, "loadBksCA: exception : " + e.getMessage());
            context = inputStreamOpen;
            f.a((InputStream) context);
            return x509Certificate;
        } catch (CertificateException e8) {
            e = e8;
            inputStreamOpen = null;
            g.b(a, "loadBksCA: exception : " + e.getMessage());
            context = inputStreamOpen;
            f.a((InputStream) context);
            return x509Certificate;
        } catch (Throwable th2) {
            th = th2;
            f.a(inputStream);
            throw th;
        }
        f.a((InputStream) context);
        return x509Certificate;
    }
}
