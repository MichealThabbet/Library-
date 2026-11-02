package com.huawei.agconnect.credential.obs;

import android.content.Context;
import android.util.Log;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import net.fortuna.ical4j.model.Parameter;

/* loaded from: classes2.dex */
public class ah {
    private static final String[] a;
    private static final List<String> b;

    static {
        String[] strArr = {Parameter.CN, "DE", "SG", "RU", GrsBaseInfo.CountryCodeSource.UNKNOWN};
        a = strArr;
        b = Collections.unmodifiableList(Arrays.asList(strArr));
    }

    public static ai a(Context context, String str, String str2) {
        return ak.a(context, new al(str, str2, ""));
    }

    public static ai a(Context context, String str, String str2, String str3) {
        if (str3 != null && b.contains(str3.toUpperCase(Locale.ENGLISH))) {
            return ak.a(context, new al(str, str2, str3.toUpperCase(Locale.ENGLISH)));
        }
        Log.e("HiAnalyticsBridge", "RoutePolicy check failed: ".concat(String.valueOf(str3)));
        return null;
    }
}
