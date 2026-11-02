package com.app.orsozoxi.Others;

import android.content.Context;
import android.util.Log;
import com.app.orsozoxi.R;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import kotlin.text.Typography;
import net.fortuna.ical4j.model.Property;
import org.apache.http.client.methods.HttpPost;

/* loaded from: classes.dex */
public final class ServerUtilities {
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final int MAX_ATTEMPTS = 5;
    private static final String TAG = "GCM Orso";
    private static final Random random = new Random();

    static void register(Context context, String str, String str2, String str3) throws Throwable {
        Log.i(TAG, "registering device (regId = " + str3 + ")");
        HashMap map = new HashMap();
        map.put("regId", str3);
        map.put("name", str);
        map.put("email", str2);
        long jNextInt = random.nextInt(1000) + 2000;
        for (int i = 1; i <= 5; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
            try {
                post(CommonUtilities.SERVER_URL, map);
                new SavePrefs(context, (Class<?>) Boolean.class).save(true);
                return;
            } catch (IOException e) {
                Log.e(TAG, "Failed to register on attempt " + i + ":" + e);
                if (i == 5) {
                    break;
                }
                try {
                    Log.d(TAG, "Sleeping for " + jNextInt + " ms before retry");
                    Thread.sleep(jNextInt);
                    jNextInt *= 2;
                } catch (InterruptedException unused) {
                    Log.d(TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
        CommonUtilities.displayMessage(context, context.getString(R.string.server_register_error, 5));
    }

    public static void post(String str, Map<String, String> map) throws Throwable {
        try {
            URL url = new URL(str);
            StringBuilder sb = new StringBuilder();
            Iterator<Map.Entry<String, String>> it2 = map.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry<String, String> next = it2.next();
                sb.append(next.getKey());
                sb.append('=');
                sb.append(next.getValue());
                if (it2.hasNext()) {
                    sb.append(Typography.amp);
                }
            }
            String string = sb.toString();
            Log.v(TAG, "Posting '" + string + "' to " + url);
            byte[] bytes = string.getBytes();
            HttpURLConnection httpURLConnection = null;
            try {
                Log.e(Property.URL, "> " + url);
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) url.openConnection();
                try {
                    httpURLConnection2.setDoOutput(true);
                    httpURLConnection2.setUseCaches(false);
                    httpURLConnection2.setFixedLengthStreamingMode(bytes.length);
                    httpURLConnection2.setRequestMethod(HttpPost.METHOD_NAME);
                    httpURLConnection2.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                    OutputStream outputStream = httpURLConnection2.getOutputStream();
                    outputStream.write(bytes);
                    outputStream.close();
                    int responseCode = httpURLConnection2.getResponseCode();
                    if (responseCode == 200) {
                        if (httpURLConnection2 != null) {
                            httpURLConnection2.disconnect();
                        }
                    } else {
                        throw new IOException("Post failed with error code " + responseCode);
                    }
                } catch (Throwable th) {
                    th = th;
                    httpURLConnection = httpURLConnection2;
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (MalformedURLException unused) {
            throw new IllegalArgumentException("invalid url: " + str);
        }
    }
}
