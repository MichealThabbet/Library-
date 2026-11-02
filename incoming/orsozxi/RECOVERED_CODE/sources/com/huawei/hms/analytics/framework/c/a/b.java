package com.huawei.hms.analytics.framework.c.a;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class b {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;

    public final JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("protocol_version", "1");
            jSONObject.put("compress_mode", "1");
            jSONObject.put("serviceid", this.f);
            jSONObject.put(HiAnalyticsConstant.HaKey.BI_KEY_APPID, this.b);
            jSONObject.put("chifer", this.a);
            jSONObject.put("timestamp", this.d);
            jSONObject.put("servicetag", this.c);
            jSONObject.put("requestid", this.e);
            return jSONObject;
        } catch (JSONException unused) {
            return null;
        }
    }
}
