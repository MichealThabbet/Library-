package com.huawei.hms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import com.google.firebase.messaging.Constants;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.ibm.icu.text.DateFormat;
import java.lang.reflect.Field;

/* loaded from: classes2.dex */
public final class cz {
    private static String ijk(bw bwVar) {
        String str;
        String strLmn = lmn(bwVar, "$CampaignPushInfo", "activityId");
        if (TextUtils.isEmpty(strLmn)) {
            strLmn = dd.lmn(lmn(bwVar), "aid");
            if (TextUtils.isEmpty(strLmn)) {
                String strLmn2 = lmn(bwVar, RemoteMessageConst.ANALYTIC_INFO, "taskId");
                if (TextUtils.isEmpty(strLmn2)) {
                    HiLog.d("ActivityReferrerToolsKit", "get taskId is empty");
                    return "";
                }
                HiLog.i("ActivityReferrerToolsKit", "get taskId by push");
                return strLmn2;
            }
            str = "get taskId by HMS push";
        } else {
            str = "get taskId by camp";
        }
        HiLog.i("ActivityReferrerToolsKit", str);
        return strLmn;
    }

    private static String ikl(Activity activity) {
        try {
            return activity.getCallingPackage();
        } catch (Throwable th) {
            HiLog.w("ActivityReferrerToolsKit", "getCallingPackage exception. " + th.getMessage());
            return "";
        }
    }

    private static String ikl(bw bwVar) {
        String strLmn = dd.lmn(lmn(bwVar), DateFormat.SECOND);
        return !TextUtils.isEmpty(strLmn) ? strLmn : lmn(bwVar, RemoteMessageConst.ANALYTIC_INFO, "source");
    }

    private static String klm(Activity activity) {
        String str;
        if (Build.VERSION.SDK_INT < 22) {
            return "";
        }
        try {
            Field declaredField = Class.forName("android.app.Activity").getDeclaredField("mReferrer");
            declaredField.setAccessible(true);
            str = (String) declaredField.get(activity);
        } catch (Throwable unused) {
            HiLog.w("ActivityReferrerToolsKit", "No referrer.");
            str = "";
        }
        return str == null ? "" : str;
    }

    private static String klm(Activity activity, bw bwVar) {
        String str;
        String str2;
        String strLmn = lmn(bwVar, "AnalyticsKit-Info", "_s");
        if (TextUtils.isEmpty(strLmn)) {
            strLmn = lmn(bwVar, "$CampaignPushInfo", "source");
            if (TextUtils.isEmpty(strLmn)) {
                String strIkl = ikl(bwVar);
                if (TextUtils.isEmpty(strIkl)) {
                    strIkl = klm(activity);
                    if (TextUtils.isEmpty(strIkl)) {
                        String strIkl2 = ikl(activity);
                        if (TextUtils.isEmpty(strIkl2)) {
                            HiLog.d("ActivityReferrerToolsKit", "get start info empty");
                            return "";
                        }
                        HiLog.i("ActivityReferrerToolsKit", "get start info by pkg");
                        return strIkl2;
                    }
                    str = "get start info by reflect";
                } else {
                    str = "get start info by push";
                }
                HiLog.i("ActivityReferrerToolsKit", str);
                return strIkl;
            }
            str2 = "get start info by camp";
        } else {
            str2 = "get start info by custom";
        }
        HiLog.i("ActivityReferrerToolsKit", str2);
        return strLmn;
    }

    public static void klm(bw bwVar) {
        if (bwVar == null) {
            return;
        }
        String strIjk = ijk(bwVar);
        if (TextUtils.isEmpty(strIjk)) {
            return;
        }
        ar.lmn().lmn.e = strIjk;
    }

    public static bw lmn(Activity activity) {
        Intent intent;
        if (activity == null || (intent = activity.getIntent()) == null) {
            return null;
        }
        try {
            return new bw(intent.getExtras());
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String lmn(bw bwVar) {
        String strLmn = dd.lmn(lmn(bwVar, RemoteMessageConst.ANALYTIC_INFO), Constants.FirelogAnalytics.PARAM_ANALYTICS_LABEL);
        return TextUtils.isEmpty(strLmn) ? "" : dd.lmn(da.klm(strLmn), "hw_hms_push_campaign");
    }

    private static String lmn(bw bwVar, String str) {
        if (bwVar == null || TextUtils.isEmpty(str)) {
            HiLog.w("ActivityReferrerToolsKit", "input bundle extras or key is empty.");
            return "";
        }
        String strKlm = bwVar.klm(str);
        return strKlm == null ? "" : strKlm;
    }

    private static String lmn(bw bwVar, String str, String str2) {
        return dd.lmn(lmn(bwVar, str), str2);
    }

    public static void lmn(Activity activity, bw bwVar) {
        if (activity == null && bwVar == null) {
            return;
        }
        String strKlm = klm(activity, bwVar);
        if (TextUtils.isEmpty(strKlm)) {
            return;
        }
        ar.lmn().lmn.d = strKlm;
    }
}
