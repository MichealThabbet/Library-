package com.huawei.hms.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.analytics.bc;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.framework.config.ICallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class cl implements ICallback {
    public boolean lmn = false;
    private boolean klm = false;

    private static void klm(List<String> list) throws JSONException {
        Context context = ar.lmn().lmn.ghi;
        try {
            JSONArray jSONArray = new JSONArray(dl.klm(context, "global_v2", "camp_evt", ""));
            JSONArray jSONArray2 = new JSONArray();
            int length = jSONArray.length();
            if (length == 0) {
                return;
            }
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (!list.contains(jSONObject.getString("f")) && !dc.klm(jSONObject.getString("t"))) {
                    jSONArray2.put(jSONArray.getJSONObject(i));
                }
            }
            if (jSONArray2.length() == 0) {
                dl.lmn(ar.lmn().lmn.ghi, "global_v2", "camp_evt");
            } else if (jSONArray2.length() != length) {
                dl.lmn(context, "global_v2", "camp_evt", jSONArray2.toString());
            }
        } catch (JSONException unused) {
            dl.lmn(ar.lmn().lmn.ghi, "global_v2", "camp_evt");
        }
    }

    private static List<String> lmn(List<Event> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<Event> it2 = list.iterator();
        while (it2.hasNext()) {
            String pushEvtFlag = it2.next().getPushEvtFlag();
            if (!TextUtils.isEmpty(pushEvtFlag)) {
                arrayList.add(pushEvtFlag);
            }
        }
        return arrayList;
    }

    private void lmn(int i, int i2, List<Event> list) throws JSONException {
        if (this.lmn) {
            klm(lmn(list));
        }
        if (i == 200 || i2 == -2) {
            return;
        }
        if (!isStopEvent() || list.size() != 1) {
            if (this.lmn) {
                fgh.lmn().lmn(list.get(0).getServiceTag()).klm(list);
            }
        } else {
            JSONObject json = list.get(0).toJson();
            if (json == null) {
                return;
            }
            dl.lmn(ar.lmn().lmn.ghi, "stop_v2_1", "stop_event", json.toString());
        }
    }

    @Override // com.huawei.hms.analytics.framework.config.ICallback
    public final boolean isAAIDChanged(String str, boolean z) {
        if (!"_openness_config_tag".equals(str) || !cy.lmn().klm(ar.lmn().lmn.ghi)) {
            return false;
        }
        ar.lmn().lmn.p = false;
        HiLog.i("ReportRingback", "aaid is changed,then reset");
        try {
            if (TextUtils.isEmpty(str)) {
                return true;
            }
            bh bhVarLmn = fgh.lmn().lmn(str);
            if (!(bhVarLmn instanceof ijk)) {
                return true;
            }
            ((ijk) bhVarLmn).ikl(z);
            return true;
        } catch (Exception e) {
            HiLog.w("ReportRingback", "reset analytics exception: " + e.getMessage());
            return true;
        }
    }

    @Override // com.huawei.hms.analytics.framework.config.ICallback
    public final boolean isNeedStorage() {
        return false;
    }

    @Override // com.huawei.hms.analytics.framework.config.ICallback
    public final boolean isStopEvent() {
        return this.klm;
    }

    @Override // com.huawei.hms.analytics.framework.config.ICallback
    public final void onResult(int i, long j, int i2, List<Event> list) throws JSONException {
        HiLog.i("ReportRingback", "report callback");
        if (dg.ghi(ar.lmn().lmn.ghi)) {
            HiLog.i("ReportRingback", "do not enable APIEvt in debug model");
            return;
        }
        if (list == null || list.size() == 0) {
            return;
        }
        if (i == -1) {
            lmn(i, i2, list);
            return;
        }
        if (i == 200) {
            try {
                bo.lmn();
                if (bo.ikl() && bc.lmn(ar.lmn().lmn.ghi).ikl().size() == 0) {
                    bo.lmn().ijk();
                }
            } catch (bc.lmn unused) {
                HiLog.w("ReportRingback", "checkLocalKeyRefresh, DBException, init DB error");
            }
        }
        lmn(i, i2, list);
        long jCurrentTimeMillis = System.currentTimeMillis() - j;
        HashMap map = new HashMap();
        Iterator<Event> it2 = list.iterator();
        while (it2.hasNext()) {
            String evtid = it2.next().getEvtid();
            Integer num = (Integer) map.get(evtid);
            int iIntValue = 1;
            if (num != null) {
                iIntValue = 1 + num.intValue();
            }
            map.put(evtid, Integer.valueOf(iIntValue));
        }
        cd.lmn(map, "ITransportHandler#execute()", jCurrentTimeMillis, i);
    }

    @Override // com.huawei.hms.analytics.framework.config.ICallback
    public final void setStopEventReport(boolean z) {
        this.klm = z;
    }
}
