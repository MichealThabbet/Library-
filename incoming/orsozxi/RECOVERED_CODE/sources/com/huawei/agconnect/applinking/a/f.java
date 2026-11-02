package com.huawei.agconnect.applinking.a;

import android.net.Uri;
import android.text.TextUtils;
import com.huawei.agconnect.AGCRoutePolicy;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.impl.locale.LanguageTag;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import net.fortuna.ical4j.model.property.RequestStatus;

/* loaded from: classes2.dex */
public class f {
    public static String a(AGCRoutePolicy aGCRoutePolicy) {
        return AGCRoutePolicy.CHINA.equals(aGCRoutePolicy) ? "0" : AGCRoutePolicy.GERMANY.equals(aGCRoutePolicy) ? "1" : AGCRoutePolicy.RUSSIA.equals(aGCRoutePolicy) ? RequestStatus.SUCCESS : AGCRoutePolicy.SINGAPORE.equals(aGCRoutePolicy) ? RequestStatus.CLIENT_ERROR : "0";
    }

    public static String a(String str, String str2) {
        if (str != null && str2 != null) {
            try {
                return Uri.parse(str).getQueryParameter(str2);
            } catch (UnsupportedOperationException unused) {
            }
        }
        return null;
    }

    public static HashMap<String, String> a(String str) throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<>();
        if (str != null) {
            try {
                str = URLDecoder.decode(str, "UTF-8");
                for (String str2 : str.split(ContainerUtils.FIELD_DELIMITER)) {
                    if (!TextUtils.isEmpty(str2)) {
                        boolean zContains = str2.contains(ContainerUtils.KEY_VALUE_DELIMITER);
                        String str3 = LanguageTag.SEP;
                        if (zContains || !str2.contains(LanguageTag.SEP)) {
                            str3 = ContainerUtils.KEY_VALUE_DELIMITER;
                        }
                        String[] strArrSplit = str2.split(str3);
                        if (strArrSplit.length > 1) {
                            map.put(URLDecoder.decode(strArrSplit[0], "UTF-8"), URLDecoder.decode(strArrSplit[1], "UTF-8"));
                        }
                    }
                }
            } catch (UnsupportedEncodingException unused) {
                Logger.e("AppLinkingSDK", "decode referrer fail : " + str);
            } catch (IllegalArgumentException e) {
                Logger.e("AppLinkingSDK", "decode referrer illegal fail", e);
            }
        }
        return map;
    }
}
