package com.app.piapostolos;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;
import com.app.piapostolos.Adapters.DBAdapter;
import com.app.piapostolos.Beans.NewsActivition;
import com.app.piapostolos.Others.CommonUtilities;
import com.app.piapostolos.Others.SavePrefs;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.text.Typography;
import net.fortuna.ical4j.model.Property;
import org.apache.http.client.methods.HttpPost;
import org.jsoup.Jsoup;

/* loaded from: classes.dex */
public class MyGcmListenerService extends FirebaseMessagingService {
    private static String MyPREFERENCES = "Notification";
    private static final String TAG = "MyGcmListenerService";

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onNewToken(String str) throws Throwable {
        super.onNewToken(str);
        try {
            Log.d("NEW_TOKEN", str);
            HashMap map = new HashMap();
            map.put("regId", str);
            map.put("name", "PiApostolos");
            map.put("email", "PiApostolos");
            post(CommonUtilities.SERVER_URL, map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        }
    }

    public void post(String str, Map<String, String> map) throws Throwable {
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
            Log.v("Orso post to server", "Posting '" + string + "' to " + url);
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
                    Log.e(Property.URL, "status= " + responseCode);
                    if (responseCode != 200) {
                        new SavePrefs(this, (Class<?>) String.class).save("UNVERFIED");
                    } else {
                        new SavePrefs(this, (Class<?>) String.class).save("VERFIED");
                    }
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
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

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) throws SQLException {
        remoteMessage.getFrom();
        Map<String, String> data = remoteMessage.getData();
        String str = data.get(FirebaseAnalytics.Param.PRICE);
        String str2 = data.get("link");
        String str3 = str + str2;
        Log.d(Constants.MessageTypes.MESSAGE, "gcm= " + str3);
        if (str == null) {
            return;
        }
        if (str.isEmpty() && str.equals("")) {
            return;
        }
        if (str3.contains("null")) {
            str2 = "orsozoxi";
        }
        if (str2.contains("news@@")) {
            SavePrefs savePrefs = new SavePrefs(this, (Class<?>) NewsActivition.class);
            NewsActivition newsActivition = (NewsActivition) savePrefs.load();
            if (newsActivition != null) {
                if (!newsActivition.isNewsActivated() || isRepeated(str)) {
                    return;
                }
                insertnewmessage(str, str2.replace("news@@", ""));
                generateNotification(this, str, str2.replace("news@@", ""));
                return;
            }
            NewsActivition newsActivition2 = new NewsActivition();
            newsActivition2.setNewsActivated(false);
            savePrefs.save(newsActivition2);
            return;
        }
        if (isRepeated(str)) {
            return;
        }
        insertnewmessage(str, str2);
        generateNotification(this, str, str2);
    }

    public void insertnewmessage(String str, String str2) throws SQLException {
        DBAdapter dBAdapter = new DBAdapter(getApplicationContext());
        dBAdapter.open();
        dBAdapter.insert(str, str2);
        dBAdapter.close();
    }

    public boolean isRepeated(String str) throws SQLException {
        DBAdapter dBAdapter = new DBAdapter(getApplicationContext());
        dBAdapter.open();
        Cursor cursorFetchAll = dBAdapter.fetchAll();
        if (cursorFetchAll.getCount() <= 0) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (cursorFetchAll.moveToFirst()) {
            do {
                arrayList.add(cursorFetchAll.getString(1));
                arrayList2.add(cursorFetchAll.getString(2));
            } while (cursorFetchAll.moveToNext());
        }
        dBAdapter.close();
        for (int size = arrayList.size() - 1; size > 0; size--) {
            if (((String) arrayList.get(size)).toString().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static String html2text(String str) {
        return Jsoup.parse(str).text();
    }

    private static void generateNotification(Context context, String str, String str2) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
        String appName = getAppName(context);
        System.currentTimeMillis();
        Intent intent = new Intent(context, (Class<?>) MainActivityGCM.class);
        intent.setFlags(268468224);
        intent.putExtra("name", str);
        intent.putExtra("email", "22");
        intent.putExtra("image", str2);
        String string = context.getString(R.string.app_name);
        if (str2.equals("https://www.orsozoxi.net/bprsms/images/bpr.jpg")) {
            string = context.getString(R.string.bishop_rafail);
        }
        if (str2.equals("https://www.orsozoxi.net/gcmsms/images/orsozoxisms.jpg")) {
            string = context.getString(R.string.piapostolos_family);
        }
        if (str2.equals("https://www.orsozoxi.net/gcmnews/images/news.jpg")) {
            string = context.getString(R.string.church_news);
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, 0);
        int i = sharedPreferences.getInt("counter", 0) + 200;
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        NotificationManager notificationManager2 = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("orso_id_01", "My Notifications", 3);
            notificationChannel.setDescription("Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(SupportMenu.CATEGORY_MASK);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager2.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "orso_id_01");
        builder.setCategory("msg").setContentTitle(string).setContentText(html2text(str)).setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setVisibility(1).setContentIntent(PendingIntent.getActivity(context, i, intent, 134217728)).setPriority(0).setDefaults(-1).build();
        Notification notificationBuild = builder.build();
        notificationBuild.defaults |= 2;
        notificationBuild.defaults |= 1;
        notificationManager.notify(appName, i, notificationBuild);
        editorEdit.putInt("counter", i + 1);
        editorEdit.apply();
    }

    private static String getAppName(Context context) {
        return (String) context.getPackageManager().getApplicationLabel(context.getApplicationInfo());
    }
}
