package it.orsozoxi.android.orsonotes.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import java.io.Closeable;
import java.io.IOException;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes3.dex */
public class SystemHelper {
    public static void restartApp(Context context, Class cls) {
        try {
            ((AlarmManager) context.getSystemService("alarm")).set(1, System.currentTimeMillis() + 100, PendingIntent.getActivity(context, 123456, new Intent(context, Class.forName("com.app.piapostolos.SplashScreen")), SQLiteDatabase.CREATE_IF_NECESSARY));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void closeCloseable(Closeable... closeableArr) throws IOException {
        for (Closeable closeable : closeableArr) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    LogDelegate.w("Can't close " + closeable, e);
                }
            }
        }
    }

    public static void copyToClipboard(Context context, String str) {
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("text label", str));
    }
}
