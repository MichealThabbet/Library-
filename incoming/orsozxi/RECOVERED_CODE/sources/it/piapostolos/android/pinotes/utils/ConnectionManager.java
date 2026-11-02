package it.piapostolos.android.pinotes.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/* loaded from: classes3.dex */
public class ConnectionManager {
    private ConnectionManager() {
    }

    public static boolean internetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager.getActiveNetworkInfo() != null) {
            return connectivityManager.getActiveNetworkInfo().isConnected();
        }
        return false;
    }
}
