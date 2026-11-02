package org.acra.config;

import java.io.Serializable;
import org.acra.collections.ImmutableMap;
import org.acra.security.KeyStoreFactory;
import org.acra.sender.HttpSender;

/* loaded from: classes3.dex */
public final class HttpSenderConfiguration implements Serializable, Configuration {
    private final String basicAuthLogin;
    private final String basicAuthPassword;
    private final String certificatePath;
    private final String certificateType;
    private final boolean compress;
    private final int connectionTimeout;
    private final boolean dropReportsOnTimeout;
    private final boolean enabled;
    private final ImmutableMap<String, String> httpHeaders;
    private final HttpSender.Method httpMethod;
    private final Class<? extends KeyStoreFactory> keyStoreFactoryClass;
    private final int resCertificate;
    private final int socketTimeout;
    private final String uri;

    public HttpSenderConfiguration(HttpSenderConfigurationBuilderImpl httpSenderConfigurationBuilderImpl) {
        this.enabled = httpSenderConfigurationBuilderImpl.enabled();
        this.uri = httpSenderConfigurationBuilderImpl.uri();
        this.basicAuthLogin = httpSenderConfigurationBuilderImpl.basicAuthLogin();
        this.basicAuthPassword = httpSenderConfigurationBuilderImpl.basicAuthPassword();
        this.httpMethod = httpSenderConfigurationBuilderImpl.httpMethod();
        this.connectionTimeout = httpSenderConfigurationBuilderImpl.connectionTimeout();
        this.socketTimeout = httpSenderConfigurationBuilderImpl.socketTimeout();
        this.dropReportsOnTimeout = httpSenderConfigurationBuilderImpl.dropReportsOnTimeout();
        this.keyStoreFactoryClass = httpSenderConfigurationBuilderImpl.keyStoreFactoryClass();
        this.certificatePath = httpSenderConfigurationBuilderImpl.certificatePath();
        this.resCertificate = httpSenderConfigurationBuilderImpl.resCertificate();
        this.certificateType = httpSenderConfigurationBuilderImpl.certificateType();
        this.compress = httpSenderConfigurationBuilderImpl.compress();
        this.httpHeaders = new ImmutableMap<>(httpSenderConfigurationBuilderImpl.httpHeaders());
    }

    @Override // org.acra.config.Configuration
    public boolean enabled() {
        return this.enabled;
    }

    public String uri() {
        return this.uri;
    }

    public String basicAuthLogin() {
        return this.basicAuthLogin;
    }

    public String basicAuthPassword() {
        return this.basicAuthPassword;
    }

    public HttpSender.Method httpMethod() {
        return this.httpMethod;
    }

    public int connectionTimeout() {
        return this.connectionTimeout;
    }

    public int socketTimeout() {
        return this.socketTimeout;
    }

    public boolean dropReportsOnTimeout() {
        return this.dropReportsOnTimeout;
    }

    public Class<? extends KeyStoreFactory> keyStoreFactoryClass() {
        return this.keyStoreFactoryClass;
    }

    public String certificatePath() {
        return this.certificatePath;
    }

    public int resCertificate() {
        return this.resCertificate;
    }

    public String certificateType() {
        return this.certificateType;
    }

    public boolean compress() {
        return this.compress;
    }

    public ImmutableMap<String, String> httpHeaders() {
        return this.httpHeaders;
    }
}
