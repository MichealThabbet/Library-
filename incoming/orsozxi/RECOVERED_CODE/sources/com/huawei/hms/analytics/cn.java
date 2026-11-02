package com.huawei.hms.analytics;

import com.huawei.hms.analytics.core.crypto.RandomUtil;
import com.huawei.hms.analytics.database.LogEvent;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class cn {
    String fgh;
    int ghi;
    boolean ikl;
    JSONArray lmn = new JSONArray();
    final List<Long> klm = new ArrayList();
    final List<Long> ijk = new ArrayList();
    String hij = RandomUtil.getInstance().generateSecureRandomStr(16);

    cn(int i) {
        this.ghi = i;
    }

    public final void hij() {
        this.ijk.clear();
        this.fgh = "";
    }

    public final void ijk() {
        this.klm.clear();
        this.hij = RandomUtil.getInstance().generateSecureRandomStr(16);
        this.lmn = new JSONArray();
    }

    public final boolean ikl() {
        return this.ikl && this.ijk.size() == this.ghi;
    }

    public final JSONObject klm() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        at atVar = ar.lmn().lmn;
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("protocol_version", "1");
        jSONObject2.put("productid", atVar.n.ikl);
        jSONObject2.put("chifer", "");
        jSONObject2.put(HiAnalyticsConstant.HaKey.BI_KEY_APPID, atVar.n.klm);
        jSONObject2.put("requestid", this.hij);
        jSONObject2.put("servicetag", "_openness_log_tag");
        jSONObject2.put("compress_mode", "0");
        jSONObject2.put("serviceid", "hmshimaintqrt");
        jSONObject2.put("timestamp", String.valueOf(System.currentTimeMillis()));
        jSONObject.put("header", jSONObject2);
        jSONObject.put("event", this.lmn);
        return jSONObject;
    }

    final int lmn(JSONArray jSONArray, List<LogEvent> list) throws JSONException {
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("log events is empty");
        }
        int i = 0;
        for (LogEvent logEvent : list) {
            i++;
            this.klm.add(logEvent.getId());
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("event", logEvent.getEventId());
            jSONObject.put("eventtime", logEvent.getEvtTime());
            try {
                jSONObject.put("properties", new JSONObject(logEvent.getContent()));
                jSONArray.put(jSONObject);
            } catch (JSONException unused) {
            }
            if (this.klm.size() >= 300) {
                break;
            }
        }
        return i;
    }

    public final boolean lmn() {
        return this.lmn.length() != 0;
    }
}
