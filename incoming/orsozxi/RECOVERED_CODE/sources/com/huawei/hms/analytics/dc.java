package com.huawei.hms.analytics;

import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.type.HAEventType;
import com.huawei.hms.analytics.type.HAParamType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class dc {
    private static final String[] lmn = {"$HA_LOGIN", HAEventType.SIGNIN, "$HA_LOGOUT", HAEventType.SIGNOUT};

    public static boolean klm(Object obj) {
        if (obj == null) {
            return true;
        }
        try {
            return System.currentTimeMillis() > Long.parseLong((String) obj);
        } catch (NumberFormatException unused) {
            return true;
        }
    }

    public static long lmn(String str, long j) {
        StringBuilder sb;
        String str2;
        long j2 = 5000;
        if (j < 5000) {
            sb = new StringBuilder();
            sb.append(str);
            str2 = " below minimum size.";
        } else {
            j2 = 18000000;
            if (j <= 18000000) {
                return j;
            }
            sb = new StringBuilder();
            sb.append(str);
            str2 = " exceeds the maximum size.";
        }
        sb.append(str2);
        HiLog.w("CheckToolsKit", sb.toString());
        return j2;
    }

    private static List<ax> lmn(Map<String, String> map, Map<String, String> map2) {
        String str;
        boolean z = map instanceof LinkedHashMap;
        long j = 0;
        ArrayList arrayList = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            boolean zLmn = false;
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                HiLog.w("CheckToolsKit", HiLog.ErrorCode.PE001, "tags param is null");
            } else {
                if (key.length() > 64) {
                    str = "Length of log tag key exceeds the limit. Max Length is 64.";
                } else if (value.length() > 256) {
                    str = "Length of log tag value exceeds the limit. Max Length is 256.";
                } else {
                    zLmn = lmn("logTag", key, am.ikl);
                }
                HiLog.w("CheckToolsKit", HiLog.ErrorCode.PE002, str);
            }
            if (zLmn) {
                if (z) {
                    map2.put(key, value);
                    j++;
                    if (j >= 50) {
                        break;
                    }
                } else {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(new ax(key, value));
                }
            }
        }
        return arrayList;
    }

    public static Map<String, String> lmn(Map<String, String> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (map == null || map.size() == 0) {
            HiLog.w("CheckToolsKit", "custom log tags is empty");
            return linkedHashMap;
        }
        List<ax> listLmn = lmn(map, linkedHashMap);
        if (listLmn != null && listLmn.size() > 0) {
            lmn(listLmn, linkedHashMap);
        }
        return linkedHashMap;
    }

    private static void lmn(List<ax> list, Map<String, String> map) {
        Collections.sort(list, new df());
        int i = 0;
        for (ax axVar : list) {
            map.put(axVar.lmn, (String) axVar.klm);
            i++;
            if (i >= 50) {
                return;
            }
        }
    }

    public static boolean lmn(long j, long j2) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        calendar2.setTimeInMillis(j2);
        return (calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6)) ? false : true;
    }

    public static boolean lmn(Object obj) {
        return (obj instanceof Number) || (obj instanceof String) || (obj instanceof Character) || (obj instanceof Boolean);
    }

    public static boolean lmn(String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            str2 = "checkProperty: key is empty";
        } else {
            if (str.length() <= 256 && !str.startsWith("ha_")) {
                return true;
            }
            str2 = "checkProperty: key check failed";
        }
        HiLog.w("CheckToolsKit", str2);
        return false;
    }

    public static boolean lmn(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            HiLog.w("CheckToolsKit", HiLog.ErrorCode.PE001, " param is null");
            return false;
        }
        if (str.length() <= i) {
            return true;
        }
        HiLog.w("CheckToolsKit", HiLog.ErrorCode.PE002, " Length of param exceeds the limit. Max Length is ".concat(String.valueOf(i)));
        return false;
    }

    public static boolean lmn(String str, Bundle bundle) {
        if (bundle == null || bundle.isEmpty() || TextUtils.isEmpty(str) || !Arrays.asList(lmn).contains(str)) {
            return false;
        }
        String string = bundle.getString("$HA_METHOD");
        String string2 = bundle.getString(HAParamType.CHANNEL);
        if (!"Account Kit".equals(string) && !"Account Kit".equals(string2)) {
            return false;
        }
        HiLog.w("CheckToolsKit", "params value Account Kit is illegal");
        return true;
    }

    public static boolean lmn(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            HiLog.w("CheckToolsKit", "checkProperty: key is empty");
            return false;
        }
        if (str2 != null && TextUtils.isEmpty(str2)) {
            HiLog.w("CheckToolsKit", "checkProperty: value is empty");
            return false;
        }
        if (str.length() > 256 || str.startsWith("ha_")) {
            HiLog.w("CheckToolsKit", HiLog.ErrorCode.PE002, "Length of userProperty key exceeds the limit. Max Length is 256.");
            return false;
        }
        if (str2 == null || str2.length() <= 256) {
            return lmn("userProfileParams", str, am.ikl);
        }
        HiLog.w("CheckToolsKit", HiLog.ErrorCode.PE002, "Length of userProperty value exceeds the limit. Max Length is 256.");
        return false;
    }

    public static boolean lmn(String str, String str2, Pattern pattern) {
        String str3;
        String str4;
        if (TextUtils.isEmpty(str2)) {
            str3 = str + " is null";
            str4 = HiLog.ErrorCode.PE001;
        } else {
            if (pattern.matcher(str2).matches()) {
                return true;
            }
            str3 = str + " is invalid.";
            str4 = HiLog.ErrorCode.PE006;
        }
        HiLog.w("CheckToolsKit", str4, str3);
        return false;
    }

    public static boolean lmn(String str, List<String> list) {
        Iterator<String> it2 = list.iterator();
        while (it2.hasNext()) {
            if (it2.next().equals(str)) {
                return true;
            }
        }
        return false;
    }
}
