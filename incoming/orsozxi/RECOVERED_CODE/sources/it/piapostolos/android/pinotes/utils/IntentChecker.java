package it.piapostolos.android.pinotes.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import java.util.List;

/* loaded from: classes3.dex */
public class IntentChecker {
    private IntentChecker() {
    }

    public static String resolveActivityPackage(Context context, Intent intent) {
        ComponentName componentNameResolveActivity = intent.resolveActivity(context.getPackageManager());
        return componentNameResolveActivity != null ? componentNameResolveActivity.getPackageName() : "";
    }

    public static boolean isAvailable(Context context, Intent intent, String[] strArr) {
        boolean z = getCompatiblePackages(context, intent).size() > 0;
        if (strArr != null) {
            for (String str : strArr) {
                z = z && context.getPackageManager().hasSystemFeature(str);
            }
        }
        return z;
    }

    public static boolean checkAction(Intent intent, String str) {
        return str.equals(intent.getAction());
    }

    public static boolean checkAction(Intent intent, String... strArr) {
        for (String str : strArr) {
            if (checkAction(intent, str)) {
                return true;
            }
        }
        return false;
    }

    private static List<ResolveInfo> getCompatiblePackages(Context context, Intent intent) {
        return context.getPackageManager().queryIntentActivities(intent, 65536);
    }
}
