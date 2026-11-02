package org.acra.security;

import android.content.Context;
import java.security.KeyStore;
import org.acra.config.ConfigUtils;
import org.acra.config.CoreConfiguration;
import org.acra.config.HttpSenderConfiguration;
import org.acra.util.InstanceCreator;

/* loaded from: classes3.dex */
public final class KeyStoreHelper {
    private static final String ASSET_PREFIX = "asset://";

    private KeyStoreHelper() {
    }

    public static KeyStore getKeyStore(Context context, CoreConfiguration coreConfiguration) {
        HttpSenderConfiguration httpSenderConfiguration = (HttpSenderConfiguration) ConfigUtils.getPluginConfiguration(coreConfiguration, HttpSenderConfiguration.class);
        KeyStore keyStoreCreate = ((KeyStoreFactory) new InstanceCreator().create(httpSenderConfiguration.keyStoreFactoryClass(), new InstanceCreator.Fallback() { // from class: org.acra.security.-$$Lambda$AKzyPtlIEkTHjT48dgUBnmvH5nc
            @Override // org.acra.util.InstanceCreator.Fallback
            public final Object get() {
                return new NoKeyStoreFactory();
            }
        })).create(context);
        if (keyStoreCreate != null) {
            return keyStoreCreate;
        }
        int iResCertificate = httpSenderConfiguration.resCertificate();
        String strCertificatePath = httpSenderConfiguration.certificatePath();
        String strCertificateType = httpSenderConfiguration.certificateType();
        if (iResCertificate != 0) {
            return new ResourceKeyStoreFactory(strCertificateType, iResCertificate).create(context);
        }
        if (strCertificatePath.equals("")) {
            return keyStoreCreate;
        }
        if (strCertificatePath.startsWith(ASSET_PREFIX)) {
            return new AssetKeyStoreFactory(strCertificateType, strCertificatePath.substring(8)).create(context);
        }
        return new FileKeyStoreFactory(strCertificateType, strCertificatePath).create(context);
    }
}
