package com.huawei.hms.analytics;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import net.fortuna.ical4j.model.Parameter;

/* loaded from: classes2.dex */
public class HiAnalytics {
    private static final List<String> klm;
    private static final String[] lmn;

    static {
        String[] strArr = {Parameter.CN, "DE", "SG", "RU"};
        lmn = strArr;
        klm = Collections.unmodifiableList(Arrays.asList(strArr));
    }

    public static HiAnalyticsInstance getInstance(Activity activity) {
        return HiAnalyticsInstance.lmn(activity);
    }

    public static HiAnalyticsInstance getInstance(Context context) {
        return HiAnalyticsInstance.lmn(context);
    }

    public static HiAnalyticsInstance getInstance(Context context, String str) {
        if (str != null && klm.contains(str.toUpperCase(Locale.ENGLISH))) {
            return HiAnalyticsInstance.lmn(context, str.toUpperCase(Locale.ENGLISH));
        }
        Log.e("HiAnalyticsSDK", "HiAnalytics=> Invalid routePolicy! Initialization failed.");
        return HiAnalyticsInstance.lmn();
    }
}
