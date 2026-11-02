package com.huawei.hms.analytics;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class an {
    private static final String[] ijk;
    public static final List<String> lmn;
    private static final String[] hij = {"$OpenAppFromAppLinking", "$UpdateFromAppLinking", "$ReOpenAppFromAppLinking", "$DisplayAppMessaging", "$DismissInAppMessage", "$ClickInAppMessage"};
    private static final String[] ghi = {"USERAGENT"};
    public static final List<String> klm = Collections.unmodifiableList(Arrays.asList(hij));
    public static final List<String> ikl = Collections.unmodifiableList(Arrays.asList(ghi));

    static {
        String[] strArr = {"$HA_NOTIFICATION_DISPLAY", "$DisplayNotification", "$HA_NOTIFICATION_CLICK", "$ClickNotification", "$HA_NOTIFICATION_CLEAR", "$ClearNotification", "$HA_APP_INSTALL", "$InstallApp", "$HA_APP_START", "$LaunchApp", "$HA_APP_UPDATE", "$UpdateApp", "$HA_FIRST_OPEN", "$AppFirstOpen", "$CustomReferrer", "$HA_SCREEN_ENTER", "$EnterScreen", "$HA_SCREEN_EXIT", "$ExitScreen", "$HA_CLEAR_DATA", "$ClearData", "$HA_CLEAR_CACHE", "$ClearCache", "$HA_APP_UNINSTALL", "$UninstallApp", "$InAppPurchase", "$RequestAd", "$DisplayAd", "$ClickAd", "$ObtainAdAward", "$StopAnalyticsCollection", "$CampaignPushClick", "$AppPushToken"};
        ijk = strArr;
        lmn = Collections.unmodifiableList(Arrays.asList(strArr));
    }
}
