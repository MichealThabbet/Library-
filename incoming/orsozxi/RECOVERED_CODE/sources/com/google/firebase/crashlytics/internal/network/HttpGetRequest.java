package com.google.firebase.crashlytics.internal.network;

import com.google.firebase.crashlytics.internal.Logger;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

/* loaded from: classes2.dex */
public class HttpGetRequest {
    private static final int DEFAULT_TIMEOUT_MS = 10000;
    private static final String METHOD_GET = "GET";
    private static final int READ_BUFFER_SIZE = 8192;
    private final Map<String, String> headers = new HashMap();
    private final Map<String, String> queryParams;
    private final String url;

    public HttpGetRequest(String str, Map<String, String> map) {
        this.url = str;
        this.queryParams = map;
    }

    public HttpGetRequest header(String str, String str2) {
        this.headers.put(str, str2);
        return this;
    }

    public HttpGetRequest header(Map.Entry<String, String> entry) {
        return header(entry.getKey(), entry.getValue());
    }

    public HttpResponse execute() throws Throwable {
        HttpsURLConnection httpsURLConnection;
        InputStream inputStream = null;
        String stream = null;
        inputStream = null;
        try {
            String strCreateUrlWithParams = createUrlWithParams(this.url, this.queryParams);
            Logger.getLogger().v("GET Request URL: " + strCreateUrlWithParams);
            httpsURLConnection = (HttpsURLConnection) new URL(strCreateUrlWithParams).openConnection();
            try {
                httpsURLConnection.setReadTimeout(10000);
                httpsURLConnection.setConnectTimeout(10000);
                httpsURLConnection.setRequestMethod("GET");
                for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                    httpsURLConnection.addRequestProperty(entry.getKey(), entry.getValue());
                }
                httpsURLConnection.connect();
                int responseCode = httpsURLConnection.getResponseCode();
                InputStream inputStream2 = httpsURLConnection.getInputStream();
                if (inputStream2 != null) {
                    try {
                        stream = readStream(inputStream2);
                    } catch (Throwable th) {
                        th = th;
                        inputStream = inputStream2;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (httpsURLConnection != null) {
                            httpsURLConnection.disconnect();
                        }
                        throw th;
                    }
                }
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (httpsURLConnection != null) {
                    httpsURLConnection.disconnect();
                }
                return new HttpResponse(responseCode, stream);
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            httpsURLConnection = null;
        }
    }

    private String createUrlWithParams(String str, Map<String, String> map) {
        String strCreateParamsString = createParamsString(map);
        if (strCreateParamsString.isEmpty()) {
            return str;
        }
        if (str.contains("?")) {
            if (!str.endsWith(ContainerUtils.FIELD_DELIMITER)) {
                strCreateParamsString = ContainerUtils.FIELD_DELIMITER + strCreateParamsString;
            }
            return str + strCreateParamsString;
        }
        return str + "?" + strCreateParamsString;
    }

    private String createParamsString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> it2 = map.entrySet().iterator();
        Map.Entry<String, String> next = it2.next();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(next.getKey());
        sb2.append(ContainerUtils.KEY_VALUE_DELIMITER);
        sb2.append(next.getValue() != null ? next.getValue() : "");
        sb.append(sb2.toString());
        while (it2.hasNext()) {
            Map.Entry<String, String> next2 = it2.next();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(ContainerUtils.FIELD_DELIMITER);
            sb3.append(next2.getKey());
            sb3.append(ContainerUtils.KEY_VALUE_DELIMITER);
            sb3.append(next2.getValue() != null ? next2.getValue() : "");
            sb.append(sb3.toString());
        }
        return sb.toString();
    }

    private String readStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        char[] cArr = new char[8192];
        StringBuilder sb = new StringBuilder();
        while (true) {
            int i = bufferedReader.read(cArr);
            if (i != -1) {
                sb.append(cArr, 0, i);
            } else {
                return sb.toString();
            }
        }
    }
}
