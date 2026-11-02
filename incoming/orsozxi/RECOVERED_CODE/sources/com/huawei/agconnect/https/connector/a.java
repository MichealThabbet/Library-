package com.huawei.agconnect.https.connector;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/* loaded from: classes2.dex */
public class a implements Connector {
    private static final Object a = new Object();
    private static a b;
    private final Context c;

    private a(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.c = applicationContext != null ? applicationContext : context;
    }

    public static a a(Context context) {
        a aVar;
        synchronized (a) {
            if (b == null) {
                b = new a(context);
            }
            aVar = b;
        }
        return aVar;
    }

    @Override // com.huawei.agconnect.https.connector.Connector
    public boolean hasActiveNetwork() {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        Context context = this.c;
        return (context == null || (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }

    @Override // com.huawei.agconnect.https.connector.Connector
    public void logger(String str, String str2) {
        Log.d(str, str2);
    }
}
