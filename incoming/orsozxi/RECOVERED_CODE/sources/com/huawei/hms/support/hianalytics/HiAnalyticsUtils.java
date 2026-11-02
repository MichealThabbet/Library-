package com.huawei.hms.support.hianalytics;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hianalytics.process.HiAnalyticsManager;
import com.huawei.hianalytics.util.HiAnalyticTools;
import com.huawei.hms.hatool.HmsHiAnalyticsUtils;
import com.huawei.hms.stats.c;
import com.huawei.hms.support.log.HMSLog;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class HiAnalyticsUtils {
    public static final Object c = new Object();
    public static final Object d = new Object();
    public static HiAnalyticsUtils e;
    public int a = 0;
    public boolean b = c.a();

    public static LinkedHashMap<String, String> a(Map<String, String> map) {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    public static HiAnalyticsUtils getInstance() {
        HiAnalyticsUtils hiAnalyticsUtils;
        synchronized (c) {
            if (e == null) {
                e = new HiAnalyticsUtils();
            }
            hiAnalyticsUtils = e;
        }
        return hiAnalyticsUtils;
    }

    public static String versionCodeToName(String str) throws NumberFormatException {
        if (!TextUtils.isEmpty(str) && (str.length() == 8 || str.length() == 9)) {
            try {
                Integer.parseInt(str);
                return Integer.parseInt(str.substring(0, str.length() - 7)) + "." + Integer.parseInt(str.substring(str.length() - 7, str.length() - 5)) + "." + Integer.parseInt(str.substring(str.length() - 5, str.length() - 3)) + "." + Integer.parseInt(str.substring(str.length() - 3));
            } catch (NumberFormatException unused) {
            }
        }
        return "";
    }

    public void enableLog(Context context) {
        HMSLog.i("HiAnalyticsUtils", "Enable Log");
        if (this.b) {
            HiAnalyticTools.enableLog(context);
        } else {
            HmsHiAnalyticsUtils.enableLog();
        }
    }

    public boolean getInitFlag() {
        return !this.b ? HmsHiAnalyticsUtils.getInitFlag() : HiAnalyticsManager.getInitFlag(HiAnalyticsConstant.HA_SERVICE_TAG);
    }

    public boolean hasError(Context context) {
        return com.huawei.hms.stats.a.c(context);
    }

    public void onBuoyEvent(Context context, String str, String str2) {
        if (hasError(context) || context == null) {
            return;
        }
        onEvent2(context, str, str2);
    }

    public void onEvent(Context context, String str, Map<String, String> map) {
        if (hasError(context) || map == null || map.isEmpty() || context == null || !getInitFlag()) {
            return;
        }
        if (this.b) {
            com.huawei.hms.stats.b.a(context, 0, str, a(map));
            com.huawei.hms.stats.b.a(context, 1, str, a(map));
        } else {
            HmsHiAnalyticsUtils.onEvent(0, str, a(map));
            HmsHiAnalyticsUtils.onEvent(1, str, a(map));
        }
        a(context);
    }

    public void onEvent2(Context context, String str, String str2) {
        if (hasError(context) || context == null || !getInitFlag()) {
            return;
        }
        if (this.b) {
            com.huawei.hms.stats.b.a(context, str, str2);
        } else {
            HmsHiAnalyticsUtils.onEvent(context, str, str2);
        }
    }

    public void onNewEvent(Context context, String str, Map map) {
        if (hasError(context) || map == null || map.isEmpty() || context == null || !getInitFlag()) {
            return;
        }
        if (this.b) {
            com.huawei.hms.stats.b.a(context, 0, str, a((Map<String, String>) map));
            com.huawei.hms.stats.b.a(context, 1, str, a((Map<String, String>) map));
        } else {
            HmsHiAnalyticsUtils.onEvent(0, str, a((Map<String, String>) map));
            HmsHiAnalyticsUtils.onEvent(1, str, a((Map<String, String>) map));
        }
        a(context);
    }

    public void onReport(Context context, String str, Map map) {
        if (hasError(context) || map == null || map.isEmpty() || context == null || !getInitFlag()) {
            return;
        }
        if (!this.b) {
            HmsHiAnalyticsUtils.onEvent(0, str, a((Map<String, String>) map));
            HmsHiAnalyticsUtils.onEvent(1, str, a((Map<String, String>) map));
            HmsHiAnalyticsUtils.onReport();
        } else {
            com.huawei.hms.stats.b.a(context, 0, str, a((Map<String, String>) map));
            com.huawei.hms.stats.b.a(context, 1, str, a((Map<String, String>) map));
            com.huawei.hms.stats.b.a(context, 0);
            com.huawei.hms.stats.b.a(context, 1);
        }
    }

    public void enableLog() {
        HMSLog.i("HiAnalyticsUtils", "Enable Log");
        if (!this.b) {
            HmsHiAnalyticsUtils.enableLog();
        } else {
            HMSLog.i("HiAnalyticsUtils", "cp needs to pass in the context, this method is not supported");
        }
    }

    public final void a(Context context) {
        synchronized (d) {
            if (this.a < 60) {
                this.a++;
            } else {
                this.a = 0;
                if (!this.b) {
                    HmsHiAnalyticsUtils.onReport();
                } else {
                    com.huawei.hms.stats.b.a(context, 0);
                    com.huawei.hms.stats.b.a(context, 1);
                }
            }
        }
    }

    public void onReport(Context context, String str, Map map, int i) {
        if (hasError(context)) {
            return;
        }
        if (i != 0 && i != 1) {
            HMSLog.e("HiAnalyticsUtils", "Data reporting type is not supported");
            return;
        }
        if (map == null || map.isEmpty() || context == null || !getInitFlag()) {
            return;
        }
        if (!this.b) {
            HmsHiAnalyticsUtils.onEvent(i, str, a((Map<String, String>) map));
            HmsHiAnalyticsUtils.onReport();
        } else {
            com.huawei.hms.stats.b.a(context, i, str, a((Map<String, String>) map));
            com.huawei.hms.stats.b.a(context, i);
        }
    }

    public void onNewEvent(Context context, String str, Map map, int i) {
        if (hasError(context)) {
            return;
        }
        if (i != 0 && i != 1) {
            HMSLog.e("HiAnalyticsUtils", "Data reporting type is not supported");
            return;
        }
        if (map == null || map.isEmpty() || context == null || !getInitFlag()) {
            return;
        }
        if (!this.b) {
            HmsHiAnalyticsUtils.onEvent(i, str, a((Map<String, String>) map));
        } else {
            com.huawei.hms.stats.b.a(context, i, str, a((Map<String, String>) map));
        }
        a(context);
    }
}
