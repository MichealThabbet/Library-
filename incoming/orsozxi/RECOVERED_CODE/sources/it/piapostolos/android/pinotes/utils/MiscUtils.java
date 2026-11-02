package it.piapostolos.android.pinotes.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/* loaded from: classes3.dex */
public class MiscUtils {
    private MiscUtils() {
    }

    public static boolean isGooglePlayAvailable(Context context) throws PackageManager.NameNotFoundException {
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }
}
