package org.acra.http;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.acra.ACRA;
import org.acra.config.ConfigUtils;
import org.acra.config.CoreConfiguration;
import org.acra.config.HttpSenderConfiguration;
import org.acra.security.KeyStoreHelper;
import org.acra.sender.HttpSender;
import org.acra.util.IOUtils;
import org.apache.http.HttpHeaders;

/* loaded from: classes3.dex */
public abstract class BaseHttpRequest<T> implements HttpRequest<T> {
    private final CoreConfiguration config;
    private final int connectionTimeOut;
    private final Context context;
    private final Map<String, String> headers;
    private final String login;
    private final HttpSender.Method method;
    private final String password;
    private final HttpSenderConfiguration senderConfiguration;
    private final int socketTimeOut;

    protected abstract String getContentType(Context context, T t);

    protected abstract void write(OutputStream outputStream, T t) throws IOException;

    public BaseHttpRequest(CoreConfiguration coreConfiguration, Context context, HttpSender.Method method, String str, String str2, int i, int i2, Map<String, String> map) {
        this.config = coreConfiguration;
        this.context = context;
        this.method = method;
        this.login = str;
        this.password = str2;
        this.connectionTimeOut = i;
        this.socketTimeOut = i2;
        this.headers = map;
        this.senderConfiguration = (HttpSenderConfiguration) ConfigUtils.getPluginConfiguration(coreConfiguration, HttpSenderConfiguration.class);
    }

    @Override // org.acra.http.HttpRequest
    public void send(URL url, T t) throws IOException {
        HttpURLConnection httpURLConnectionCreateConnection = createConnection(url);
        if (httpURLConnectionCreateConnection instanceof HttpsURLConnection) {
            try {
                configureHttps((HttpsURLConnection) httpURLConnectionCreateConnection);
            } catch (GeneralSecurityException e) {
                ACRA.log.e(ACRA.LOG_TAG, "Could not configure SSL for ACRA request to " + url, e);
            }
        }
        configureTimeouts(httpURLConnectionCreateConnection, this.connectionTimeOut, this.socketTimeOut);
        configureHeaders(httpURLConnectionCreateConnection, this.login, this.password, this.headers, t);
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Sending request to " + url);
            ACRA.log.d(ACRA.LOG_TAG, "Http " + this.method.name() + " content : ");
            ACRA.log.d(ACRA.LOG_TAG, t.toString());
        }
        try {
            writeContent(httpURLConnectionCreateConnection, this.method, t);
            handleResponse(httpURLConnectionCreateConnection.getResponseCode(), httpURLConnectionCreateConnection.getResponseMessage());
            httpURLConnectionCreateConnection.disconnect();
        } catch (SocketTimeoutException e2) {
            if (this.senderConfiguration.dropReportsOnTimeout()) {
                Log.w(ACRA.LOG_TAG, "Dropped report due to timeout");
                return;
            }
            throw e2;
        }
    }

    protected HttpURLConnection createConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    protected void configureHttps(HttpsURLConnection httpsURLConnection) throws GeneralSecurityException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(KeyStoreHelper.getKeyStore(this.context, this.config));
        SSLContext sSLContext = SSLContext.getInstance("TLS");
        sSLContext.init(null, trustManagerFactory.getTrustManagers(), null);
        httpsURLConnection.setSSLSocketFactory(sSLContext.getSocketFactory());
    }

    protected void configureTimeouts(HttpURLConnection httpURLConnection, int i, int i2) {
        httpURLConnection.setConnectTimeout(i);
        httpURLConnection.setReadTimeout(i2);
    }

    protected void configureHeaders(HttpURLConnection httpURLConnection, String str, String str2, Map<String, String> map, T t) throws IOException {
        httpURLConnection.setRequestProperty("User-Agent", String.format("Android ACRA %1$s", "5.4.0"));
        httpURLConnection.setRequestProperty(HttpHeaders.ACCEPT, "text/html,application/xml,application/json,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
        httpURLConnection.setRequestProperty("Content-Type", getContentType(this.context, t));
        if (str != null && str2 != null) {
            String str3 = new String(Base64.encode((str + ':' + str2).getBytes("UTF-8"), 2), "UTF-8");
            StringBuilder sb = new StringBuilder();
            sb.append("Basic ");
            sb.append(str3);
            httpURLConnection.setRequestProperty("Authorization", sb.toString());
        }
        if (this.senderConfiguration.compress()) {
            httpURLConnection.setRequestProperty("Content-Encoding", "gzip");
        }
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    protected void writeContent(HttpURLConnection httpURLConnection, HttpSender.Method method, T t) throws IOException {
        httpURLConnection.setRequestMethod(method.name());
        httpURLConnection.setDoOutput(true);
        System.setProperty("http.keepAlive", "false");
        httpURLConnection.connect();
        OutputStream gZIPOutputStream = this.senderConfiguration.compress() ? new GZIPOutputStream(httpURLConnection.getOutputStream(), 8192) : new BufferedOutputStream(httpURLConnection.getOutputStream());
        try {
            write(gZIPOutputStream, t);
            gZIPOutputStream.flush();
        } finally {
            IOUtils.safeClose(gZIPOutputStream);
        }
    }

    protected void handleResponse(int i, String str) throws IOException {
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Request response : " + i + " : " + str);
        }
        if (i >= 200 && i < 300) {
            ACRA.log.i(ACRA.LOG_TAG, "Request received by server");
            return;
        }
        if (i == 408 || i >= 500) {
            ACRA.log.w(ACRA.LOG_TAG, "Could not send ACRA Post responseCode=" + i + " message=" + str);
            throw new IOException("Host returned error code " + i);
        }
        if (i >= 400) {
            ACRA.log.w(ACRA.LOG_TAG, i + ": Client error - request will be discarded");
            return;
        }
        ACRA.log.w(ACRA.LOG_TAG, "Could not send ACRA Post - request will be discarded. responseCode=" + i + " message=" + str);
    }
}
