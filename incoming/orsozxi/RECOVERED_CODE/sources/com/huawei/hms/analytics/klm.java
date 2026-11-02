package com.huawei.hms.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.AGConnectOptions;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.analytics.core.log.HiLog;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes2.dex */
public final class klm {
    final String klm;
    final boolean lmn;

    public klm(boolean z, String str) {
        this.lmn = z;
        this.klm = str;
    }

    static AGConnectOptions lmn(Context context) {
        AGConnectInstance aGConnectInstance = AGConnectInstance.getInstance();
        if (aGConnectInstance != null) {
            return aGConnectInstance.getOptions();
        }
        HiLog.w("jsonParses", "agConnectInstance is null, get services config");
        try {
            return AGConnectServicesConfig.fromContext(context);
        } catch (NoClassDefFoundError unused) {
            HiLog.w("jsonParses", "AGConnect Services Config not found");
            return null;
        }
    }

    static void lmn(Map<String, String[]> map, String str, String[] strArr) {
        if (strArr.length > 0) {
            map.put(str, strArr);
        }
    }

    static String[] lmn(String str) {
        if (TextUtils.isEmpty(str)) {
            return new String[0];
        }
        String[] strArrSplit = str.split(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        ArrayList arrayList = new ArrayList();
        int length = strArrSplit.length;
        for (int i = 0; i < length; i++) {
            String strConcat = strArrSplit[i];
            String lowerCase = strConcat.toLowerCase(Locale.US);
            if (lowerCase.startsWith("http://")) {
                HiLog.e("jsonParses", "URL cannot be http protocol");
            } else {
                if (!lowerCase.startsWith("https://")) {
                    strConcat = "https://".concat(String.valueOf(strConcat));
                }
                if (dp.lmn(strConcat)) {
                    arrayList.add(strConcat);
                }
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
}
