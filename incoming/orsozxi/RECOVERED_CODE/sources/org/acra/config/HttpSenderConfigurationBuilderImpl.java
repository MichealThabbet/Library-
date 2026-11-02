package org.acra.config;

import android.content.Context;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import java.util.Map;
import org.acra.ACRAConstants;
import org.acra.annotation.AcraHttpSender;
import org.acra.security.KeyStoreFactory;
import org.acra.security.NoKeyStoreFactory;
import org.acra.sender.HttpSender;

/* loaded from: classes3.dex */
final class HttpSenderConfigurationBuilderImpl implements HttpSenderConfigurationBuilder {
    private String basicAuthLogin;
    private String basicAuthPassword;
    private String certificatePath;
    private String certificateType;
    private boolean compress;
    private int connectionTimeout;
    private final Context context;
    private final BaseHttpConfigurationBuilder delegate;
    private boolean dropReportsOnTimeout;
    private boolean enabled;
    private HttpSender.Method httpMethod;
    private Class<? extends KeyStoreFactory> keyStoreFactoryClass;
    private int resCertificate;
    private int socketTimeout;
    private String uri;

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public /* bridge */ /* synthetic */ HttpSenderConfigurationBuilder setHttpHeaders(Map map) {
        return setHttpHeaders((Map<String, String>) map);
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public /* bridge */ /* synthetic */ HttpSenderConfigurationBuilder setKeyStoreFactoryClass(Class cls) {
        return setKeyStoreFactoryClass((Class<? extends KeyStoreFactory>) cls);
    }

    HttpSenderConfigurationBuilderImpl(Context context) {
        AcraHttpSender acraHttpSender = (AcraHttpSender) context.getClass().getAnnotation(AcraHttpSender.class);
        this.context = context;
        this.enabled = acraHttpSender != null;
        this.delegate = new BaseHttpConfigurationBuilder();
        if (this.enabled) {
            this.uri = acraHttpSender.uri();
            this.basicAuthLogin = acraHttpSender.basicAuthLogin();
            this.basicAuthPassword = acraHttpSender.basicAuthPassword();
            this.httpMethod = acraHttpSender.httpMethod();
            this.connectionTimeout = acraHttpSender.connectionTimeout();
            this.socketTimeout = acraHttpSender.socketTimeout();
            this.dropReportsOnTimeout = acraHttpSender.dropReportsOnTimeout();
            this.keyStoreFactoryClass = acraHttpSender.keyStoreFactoryClass();
            this.certificatePath = acraHttpSender.certificatePath();
            this.resCertificate = acraHttpSender.resCertificate();
            this.certificateType = acraHttpSender.certificateType();
            this.compress = acraHttpSender.compress();
            return;
        }
        this.basicAuthLogin = ACRAConstants.NULL_VALUE;
        this.basicAuthPassword = ACRAConstants.NULL_VALUE;
        this.connectionTimeout = 5000;
        this.socketTimeout = BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT;
        this.dropReportsOnTimeout = false;
        this.keyStoreFactoryClass = NoKeyStoreFactory.class;
        this.certificatePath = "";
        this.resCertificate = 0;
        this.certificateType = ACRAConstants.DEFAULT_CERTIFICATE_TYPE;
        this.compress = false;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setEnabled(boolean z) {
        this.enabled = z;
        return this;
    }

    boolean enabled() {
        return this.enabled;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setUri(String str) {
        this.uri = str;
        return this;
    }

    String uri() {
        return this.uri;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setBasicAuthLogin(String str) {
        this.basicAuthLogin = str;
        return this;
    }

    String basicAuthLogin() {
        return this.basicAuthLogin;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setBasicAuthPassword(String str) {
        this.basicAuthPassword = str;
        return this;
    }

    String basicAuthPassword() {
        return this.basicAuthPassword;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setHttpMethod(HttpSender.Method method) {
        this.httpMethod = method;
        return this;
    }

    HttpSender.Method httpMethod() {
        return this.httpMethod;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setConnectionTimeout(int i) {
        this.connectionTimeout = i;
        return this;
    }

    int connectionTimeout() {
        return this.connectionTimeout;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setSocketTimeout(int i) {
        this.socketTimeout = i;
        return this;
    }

    int socketTimeout() {
        return this.socketTimeout;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setDropReportsOnTimeout(boolean z) {
        this.dropReportsOnTimeout = z;
        return this;
    }

    boolean dropReportsOnTimeout() {
        return this.dropReportsOnTimeout;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setKeyStoreFactoryClass(Class<? extends KeyStoreFactory> cls) {
        this.keyStoreFactoryClass = cls;
        return this;
    }

    Class<? extends KeyStoreFactory> keyStoreFactoryClass() {
        return this.keyStoreFactoryClass;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setCertificatePath(String str) {
        this.certificatePath = str;
        return this;
    }

    String certificatePath() {
        return this.certificatePath;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setResCertificate(int i) {
        this.resCertificate = i;
        return this;
    }

    int resCertificate() {
        return this.resCertificate;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setCertificateType(String str) {
        this.certificateType = str;
        return this;
    }

    String certificateType() {
        return this.certificateType;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setCompress(boolean z) {
        this.compress = z;
        return this;
    }

    boolean compress() {
        return this.compress;
    }

    @Override // org.acra.config.HttpSenderConfigurationBuilder
    public HttpSenderConfigurationBuilderImpl setHttpHeaders(Map<String, String> map) {
        this.delegate.setHttpHeaders(map);
        return this;
    }

    Map<String, String> httpHeaders() {
        return this.delegate.httpHeaders();
    }

    @Override // org.acra.config.ConfigurationBuilder
    public HttpSenderConfiguration build() throws ACRAConfigurationException {
        if (this.enabled) {
            if (this.uri == null) {
                throw new ACRAConfigurationException("uri has to be set");
            }
            if (this.httpMethod == null) {
                throw new ACRAConfigurationException("httpMethod has to be set");
            }
        }
        return new HttpSenderConfiguration(this);
    }
}
