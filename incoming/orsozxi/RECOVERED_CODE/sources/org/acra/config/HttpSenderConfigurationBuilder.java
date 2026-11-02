package org.acra.config;

import java.util.Map;
import org.acra.security.KeyStoreFactory;
import org.acra.sender.HttpSender;

/* loaded from: classes3.dex */
public interface HttpSenderConfigurationBuilder extends ConfigurationBuilder {
    HttpSenderConfigurationBuilder setBasicAuthLogin(String str);

    HttpSenderConfigurationBuilder setBasicAuthPassword(String str);

    HttpSenderConfigurationBuilder setCertificatePath(String str);

    HttpSenderConfigurationBuilder setCertificateType(String str);

    HttpSenderConfigurationBuilder setCompress(boolean z);

    HttpSenderConfigurationBuilder setConnectionTimeout(int i);

    HttpSenderConfigurationBuilder setDropReportsOnTimeout(boolean z);

    HttpSenderConfigurationBuilder setEnabled(boolean z);

    HttpSenderConfigurationBuilder setHttpHeaders(Map<String, String> map);

    HttpSenderConfigurationBuilder setHttpMethod(HttpSender.Method method);

    HttpSenderConfigurationBuilder setKeyStoreFactoryClass(Class<? extends KeyStoreFactory> cls);

    HttpSenderConfigurationBuilder setResCertificate(int i);

    HttpSenderConfigurationBuilder setSocketTimeout(int i);

    HttpSenderConfigurationBuilder setUri(String str);
}
