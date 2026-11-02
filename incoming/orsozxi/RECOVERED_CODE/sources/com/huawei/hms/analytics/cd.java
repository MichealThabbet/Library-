package com.huawei.hms.analytics;

import com.huawei.hms.analytics.core.crypto.RandomUtil;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.database.APIEvent;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes2.dex */
public final class cd {
    public static APIEvent klm(cc ccVar) {
        return new APIEvent(RandomUtil.getInstance().generateSecureRandomStr(16), String.valueOf(System.currentTimeMillis()), "0", ccVar.ikl, ccVar.lmn, ccVar.klm, ccVar.ijk, "1", String.valueOf(ccVar.lmn()));
    }

    public static synchronized cc lmn(String str) {
        cc ccVar;
        ccVar = new cc();
        ccVar.ikl = str;
        return ccVar;
    }

    public static synchronized void lmn(cc ccVar) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("APIEvtRecordHolder", "The Analytics Kit is disabled, don't record");
            return;
        }
        if (ccVar != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(klm(ccVar));
            try {
                cx.klm().lmn(new cg(arrayList));
            } catch (Exception unused) {
                HiLog.e("APIEvtRecordHolder", "HMSBIInitializer init failed");
            } catch (NoClassDefFoundError unused2) {
                HiLog.e("APIEvtRecordHolder", HiLog.ErrorCode.IE004, "HMSBIInitializer init failed,Missing hms sdk");
            }
        }
    }

    public static void lmn(Map<String, Integer> map, String str, long j, int i) {
        if (!ar.lmn().lmn.fgh) {
            HiLog.w("APIEvtRecordHolder", "The Analytics Kit is disabled, don't record");
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            arrayList.add(new APIEvent(RandomUtil.getInstance().generateSecureRandomStr(16), String.valueOf(System.currentTimeMillis()), "1", str, i == 200 ? "0" : "1", String.valueOf(i), entry.getKey(), String.valueOf(entry.getValue()), String.valueOf(j)));
        }
        try {
            cx.klm().lmn(new cg(arrayList));
        } catch (Exception unused) {
            HiLog.e("APIEvtRecordHolder", "HMSBIInitializer init failed!");
        } catch (NoClassDefFoundError unused2) {
            HiLog.e("APIEvtRecordHolder", HiLog.ErrorCode.IE004, "HMSBIInitializer init failed,Missing hms sdk!");
        }
    }
}
