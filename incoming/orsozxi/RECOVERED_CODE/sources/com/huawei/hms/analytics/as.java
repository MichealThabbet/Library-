package com.huawei.hms.analytics;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class as extends aq {
    private static final Object ijk = new Object();

    public as(Context context) {
        super(context, "global_v2", "def_evt_params");
    }

    private void lmn(List<ax> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        Collections.sort(list, new df());
        for (ax axVar : list) {
            try {
                synchronized (ijk) {
                    lmn(axVar.lmn, axVar.klm);
                }
            } catch (Exception unused) {
                HiLog.w("DefEvtParam", "addParam exception");
            }
        }
    }

    @Override // com.huawei.hms.analytics.aq
    public final void lmn() {
        if (this.ikl == null) {
            String strIjk = ijk();
            String strKlm = bo.lmn().klm();
            if (!TextUtils.isEmpty(strIjk)) {
                try {
                    lmn(new JSONObject(lmn(strKlm, strIjk)));
                } catch (JSONException unused) {
                    HiLog.w("DefEvtParam", "cache evt params is error");
                    hij();
                }
            }
        }
        super.lmn();
    }

    @Override // com.huawei.hms.analytics.aq
    public final void lmn(Bundle bundle) {
        String strConcat;
        if (bundle == null) {
            ghi();
            return;
        }
        if (bundle.size() == 0) {
            return;
        }
        ArrayList arrayList = null;
        for (String str : bundle.keySet()) {
            if (str == null || str.length() > 256 || !am.klm.matcher(str).matches()) {
                strConcat = "defEvtParams key check failed! key= ".concat(String.valueOf(str));
                HiLog.w("DefEvtParam", strConcat);
            } else {
                Object obj = bundle.get(str);
                if (obj == null) {
                    this.ikl.remove(str);
                } else if (!dc.lmn(obj) || obj.toString().length() > 256) {
                    strConcat = "defEvtParams value check failed! pls check value type or length";
                    HiLog.w("DefEvtParam", strConcat);
                } else {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(new ax(str, obj));
                }
            }
        }
        lmn(arrayList);
        lmn(klm(bo.lmn().klm(), fgh()));
    }

    @Override // com.huawei.hms.analytics.aq
    public final void lmn(String str, Object obj) {
        if (this.ikl == null) {
            this.ikl = new ConcurrentHashMap<>();
        }
        if (this.ikl.size() == 0 && obj == null) {
            return;
        }
        if (obj == null) {
            this.ikl.remove(str);
        } else if (lmn(100)) {
            this.ikl.put(str, obj);
        }
    }
}
