package com.huawei.hms.analytics;

import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.framework.config.DeviceAttributeCollector;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class br implements DeviceAttributeCollector {
    String fgh;
    String ghi;
    String lmn = "";
    String klm = "";
    String ikl = "";
    String ijk = "";
    String hij = "";

    @Override // com.huawei.hms.analytics.framework.config.DeviceAttributeCollector
    public final JSONObject doCollector() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("aaid", this.lmn);
            jSONObject.put("oaid", this.klm);
            jSONObject.put("oaid_source", this.hij);
            jSONObject.put("userid", this.ikl);
            jSONObject.put("wxunionid", this.ghi);
            jSONObject.put("wxopenid", this.fgh);
            jSONObject.put("events_global_properties", this.ijk);
        } catch (JSONException unused) {
            HiLog.w("OpennessDeviceGathering", "doCollector JSONException");
        }
        return jSONObject;
    }
}
