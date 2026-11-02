package com.huawei.hms.analytics;

import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.framework.config.EvtHeaderAttributeCollector;
import net.fortuna.ical4j.model.Parameter;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class bs implements EvtHeaderAttributeCollector {
    private String ikl;
    private String klm;
    private JSONObject lmn;

    public bs(JSONObject jSONObject, String str, String str2) {
        this.lmn = jSONObject;
        this.klm = str;
        this.ikl = str2;
    }

    @Override // com.huawei.hms.analytics.framework.config.EvtHeaderAttributeCollector
    public final JSONObject doCollector() throws JSONException {
        if (this.lmn == null) {
            return null;
        }
        at atVar = ar.lmn().lmn;
        String str = atVar.n.ikl;
        String strLmn = atVar.lmn(this.klm);
        try {
            JSONObject jSONObject = this.lmn;
            if (str == null) {
                str = "";
            }
            jSONObject.put("productid", str);
            JSONObject jSONObject2 = this.lmn;
            if (strLmn == null) {
                strLmn = "";
            }
            jSONObject2.put("pubkey_version", strLmn);
            if (("_openness_config_tag".equals(this.ikl) || "AGC_TAG".equals(this.ikl)) && Parameter.CN.equals(this.klm)) {
                this.lmn.put("wxappid", atVar.t);
            }
        } catch (JSONException unused) {
            HiLog.e("OpennessHeaderGathering", "doCollector JSONException");
        }
        return this.lmn;
    }
}
