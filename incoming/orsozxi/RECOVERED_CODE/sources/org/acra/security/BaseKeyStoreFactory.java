package org.acra.security;

import android.content.Context;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.util.IOUtils;

/* loaded from: classes3.dex */
public abstract class BaseKeyStoreFactory implements KeyStoreFactory {
    private final String certificateType;

    public enum Type {
        CERTIFICATE,
        KEYSTORE
    }

    protected abstract InputStream getInputStream(Context context);

    protected char[] getPassword() {
        return null;
    }

    public BaseKeyStoreFactory() {
        this(ACRAConstants.DEFAULT_CERTIFICATE_TYPE);
    }

    public BaseKeyStoreFactory(String str) {
        this.certificateType = str;
    }

    protected String getKeyStoreType() {
        return KeyStore.getDefaultType();
    }

    protected Type getStreamType() {
        return Type.CERTIFICATE;
    }

    @Override // org.acra.security.KeyStoreFactory
    public final KeyStore create(Context context) throws IOException {
        InputStream inputStream = getInputStream(context);
        if (inputStream != null) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            try {
                try {
                    try {
                        try {
                            KeyStore keyStore = KeyStore.getInstance(getKeyStoreType());
                            int i = AnonymousClass1.$SwitchMap$org$acra$security$BaseKeyStoreFactory$Type[getStreamType().ordinal()];
                            if (i == 1) {
                                Certificate certificateGenerateCertificate = CertificateFactory.getInstance(this.certificateType).generateCertificate(bufferedInputStream);
                                keyStore.load(null, null);
                                keyStore.setCertificateEntry("ca", certificateGenerateCertificate);
                            } else if (i == 2) {
                                keyStore.load(bufferedInputStream, getPassword());
                            }
                            return keyStore;
                        } catch (KeyStoreException e) {
                            ACRA.log.e(ACRA.LOG_TAG, "Could not load keystore", e);
                            return null;
                        }
                    } catch (CertificateException e2) {
                        ACRA.log.e(ACRA.LOG_TAG, "Could not load certificate", e2);
                        return null;
                    }
                } catch (IOException e3) {
                    ACRA.log.e(ACRA.LOG_TAG, "Could not load keystore", e3);
                    return null;
                } catch (NoSuchAlgorithmException e4) {
                    ACRA.log.e(ACRA.LOG_TAG, "Could not load keystore", e4);
                    return null;
                }
            } finally {
                IOUtils.safeClose(bufferedInputStream);
            }
        }
        return null;
    }

    /* renamed from: org.acra.security.BaseKeyStoreFactory$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$acra$security$BaseKeyStoreFactory$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$org$acra$security$BaseKeyStoreFactory$Type = iArr;
            try {
                iArr[Type.CERTIFICATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$acra$security$BaseKeyStoreFactory$Type[Type.KEYSTORE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
