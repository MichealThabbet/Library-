package com.huawei.hms.analytics;

import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.efg;
import com.huawei.hms.analytics.type.HAEventType;
import com.huawei.hms.analytics.type.HAParamType;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public final class j extends efg.lmn {
    private bh klm;
    String lmn;

    public j(bh bhVar) {
        this.klm = bhVar;
    }

    private static Bundle lmn(Bundle bundle) {
        String string;
        String str;
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            for (String str2 : bundle.keySet()) {
                if ("$HA_METHOD".equals(str2)) {
                    string = bundle.getString(str2);
                    str = HAParamType.CHANNEL;
                } else if ("$HA_RESULT".equals(str2)) {
                    string = bundle.getString(str2);
                    str = HAParamType.EVTRESULT;
                }
                bundle2.putString(str, string);
            }
        }
        return bundle2;
    }

    @Override // com.huawei.hms.analytics.efg
    public final int lmn(List<CustomEvent> list) {
        String str;
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("ServiceRingback", "isAnalyticsEnabled is false");
            return -1;
        }
        if (!ba.lmn() && TextUtils.isEmpty(this.lmn)) {
            HiLog.w("ServiceRingback", "opennessInitComplete is false");
            return -1;
        }
        cc ccVarLmn = cd.lmn("ServiceCallback#handleCustomEvent(List<CustomEvent>)");
        ccVarLmn.ikl = "ServiceCallback#handleCustomEvent(List<CustomEvent>)";
        if (list == null || list.isEmpty() || this.klm == null) {
            HiLog.w("ServiceRingback", "events empty");
            cd.lmn(ccVarLmn);
            return -1;
        }
        HiLog.i("ServiceRingback", "events got");
        try {
            boolean zIsEmpty = TextUtils.isEmpty(this.lmn);
            HashMap map = new HashMap();
            for (CustomEvent customEvent : list) {
                if (customEvent.lmn.equals(ar.lmn().lmn.ghi.getPackageName())) {
                    String str2 = customEvent.klm;
                    Bundle bundleLmn = customEvent.ikl;
                    if ("$HA_LOGIN".equals(str2) || "$HA_LOGOUT".equals(str2)) {
                        str = null;
                        if ("$HA_LOGIN".equals(str2)) {
                            str = HAEventType.SIGNIN;
                        } else if ("$HA_LOGOUT".equals(str2)) {
                            str = HAEventType.SIGNOUT;
                        }
                        bundleLmn = lmn(bundleLmn);
                    } else {
                        str = str2;
                    }
                    dj djVar = new dj(str, false);
                    if (!djVar.lmn(bundleLmn)) {
                        HiLog.w("ServiceRingback", "bundle params is invalid.");
                    } else if (zIsEmpty) {
                        this.klm.lmn(str, djVar, System.currentTimeMillis());
                    } else {
                        map.put(str2, djVar);
                    }
                }
            }
            if (!zIsEmpty) {
                this.klm.lmn(this.lmn, map);
                this.lmn = "";
            }
            ccVarLmn.lmn = "0";
            cd.lmn(ccVarLmn);
            return 0;
        } catch (RuntimeException unused) {
            HiLog.w("ServiceRingback", "handleCustomEvent RuntimeException");
            cd.lmn(ccVarLmn);
            return -1;
        }
    }
}
