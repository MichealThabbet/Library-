package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class zzcw {
    private static String zzahj;
    static Map<String, String> zzahk = new HashMap();

    public static void zzbe(String str) {
        synchronized (zzcw.class) {
            zzahj = str;
        }
    }

    static void zzd(Context context, String str) {
        zzft.zza(context, "gtm_install_referrer", "referrer", str);
        zzf(context, str);
    }

    public static String zze(Context context, String str) {
        if (zzahj == null) {
            synchronized (zzcw.class) {
                if (zzahj == null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_install_referrer", 0);
                    if (sharedPreferences != null) {
                        zzahj = sharedPreferences.getString("referrer", "");
                    } else {
                        zzahj = "";
                    }
                }
            }
        }
        return zze(zzahj, str);
    }

    public static void zzf(Context context, String str) {
        String strZze = zze(str, "conv");
        if (strZze == null || strZze.length() <= 0) {
            return;
        }
        zzahk.put(strZze, str);
        zzft.zza(context, "gtm_click_referrers", strZze, str);
    }

    public static String zze(String str, String str2) {
        if (str2 == null) {
            if (str.length() > 0) {
                return str;
            }
            return null;
        }
        String strValueOf = String.valueOf(str);
        return Uri.parse(strValueOf.length() != 0 ? "http://hostname/?".concat(strValueOf) : new String("http://hostname/?")).getQueryParameter(str2);
    }
}
