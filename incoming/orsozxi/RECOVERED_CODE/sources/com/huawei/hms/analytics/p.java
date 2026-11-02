package com.huawei.hms.analytics;

import android.content.Context;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.api.Api;
import com.huawei.hms.common.HuaweiApi;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class p extends HuaweiApi<Api.ApiOptions.NoOptions> {
    private static final n lmn = new n();

    public p(Context context) {
        super(context, (Api<Api.ApiOptions.NoOptions>) new Api(""), new Api.ApiOptions.NoOptions(), lmn);
    }

    public final Task<String> lmn() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            at atVar = ar.lmn().lmn;
            jSONObject.put(FirebaseAnalytics.Param.METHOD, "setKitEnable");
            jSONObject.put("pkg_name", atVar.ghi.getPackageName());
            jSONObject.put("3rd_aaid", cy.lmn().klm());
            jSONObject.put("app_id", atVar.n.klm);
            jSONObject.put("enable_collect", atVar.fgh);
            jSONObject.put("_restriction_enabled", atVar.efg);
            jSONObject.put("_restriction_shared", atVar.def);
            jSONObject.put("_region", atVar.n.lmn("_openness_config_tag"));
        } catch (JSONException unused) {
            HiLog.e("HiAnalyticsClientWatchable", "unableCollect build json failed. ");
        }
        return doWrite(new u("hianalytics.analyticsInvokeService", jSONObject.toString()));
    }
}
