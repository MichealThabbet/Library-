package com.huawei.hms.analytics;

import android.os.Build;
import com.huawei.hms.analytics.core.crypto.RandomUtil;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.transport.net.HttpTransportHandler;
import com.huawei.hms.analytics.core.transport.net.Response;
import com.huawei.hms.analytics.framework.config.EvtHeaderAttributeCollector;
import com.huawei.hms.analytics.framework.config.ICollectorConfig;
import com.huawei.hms.analytics.instance.CallBack;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.Map;
import net.fortuna.ical4j.model.Parameter;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class al implements Runnable {
    private String ijk;
    private ICollectorConfig ikl;
    private CallBack klm;
    private String lmn;

    public class lmn extends cb {
        public lmn(cc ccVar, String str, String str2, String str3) {
            super(ccVar, str, str2, str3);
        }

        @Override // com.huawei.hms.analytics.cb
        public final Map<String, String> lmn() {
            Map<String, String> mapLmn = super.lmn();
            String str = ar.lmn().lmn.klm;
            String str2 = Build.MODEL;
            mapLmn.put("App-Ver", str);
            mapLmn.put("Device-Type", str2);
            mapLmn.put("Sdk-Name", "hianalytics");
            mapLmn.put("Request-Id", al.this.ijk);
            return mapLmn;
        }

        @Override // com.huawei.hms.analytics.core.transport.CallbackListener
        public final void onFailure(int i) {
            HiLog.e("RequestBas", "oaid report error:".concat(String.valueOf(i)));
            if (al.this.klm != null) {
                al.this.klm.onResult(i, "");
            }
        }

        @Override // com.huawei.hms.analytics.core.transport.CallbackListener
        public final void onSuccess(Response response) {
            if (al.this.klm != null) {
                HiLog.i("RequestBas", "ReportOaid:" + response.getHttpCode());
                al.this.klm.onResult(response.getHttpCode(), "");
            }
        }
    }

    public al(ICollectorConfig iCollectorConfig, String str, CallBack callBack) {
        this.lmn = str;
        this.klm = callBack;
        this.ikl = iCollectorConfig;
    }

    private byte[] lmn() {
        String str;
        try {
            this.ikl.syncOaid();
            String appId = this.ikl.getAppId();
            String str2 = this.ijk;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("protocol_version", "1");
            jSONObject.put("compress_mode", "0");
            jSONObject.put("serviceid", "hmshioperqrt");
            jSONObject.put(HiAnalyticsConstant.HaKey.BI_KEY_APPID, appId);
            jSONObject.put("chifer", "");
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            jSONObject.put("timestamp", sb.toString());
            jSONObject.put("servicetag", this.lmn);
            jSONObject.put("requestid", str2);
            EvtHeaderAttributeCollector evtCustomHeader = this.ikl.getEvtCustomHeader("oper", jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            JSONObject jSONObjectDoCollector = evtCustomHeader.doCollector();
            jSONObjectDoCollector.remove("wxappid");
            jSONObject3.put("header", jSONObjectDoCollector);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("aaid", cy.lmn().klm());
            jSONObject4.put("oaid", ar.lmn().lmn.h);
            jSONObject4.put("oaid_source", ar.lmn().lmn.i);
            jSONObject3.put("events_common", jSONObject4);
            jSONObject2.put("event", jSONObject3);
            return jSONObject2.toString().getBytes("UTF-8");
        } catch (JSONException unused) {
            str = "get body json exception";
            HiLog.e("ReportOaidTask", str);
            return new byte[0];
        } catch (Throwable th) {
            str = "get body error:" + th.getMessage();
            HiLog.e("ReportOaidTask", str);
            return new byte[0];
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (!ba.klm(this.lmn)) {
            this.klm.onResult(CallBack.INIT_FAILED, this.lmn + " not init");
            return;
        }
        String strLmn = ar.lmn().lmn.n.lmn(this.lmn);
        if (!Parameter.CN.equals(strLmn)) {
            HiLog.w("ReportOaidTask", "Region is not CN");
            this.klm.onResult(CallBack.REGION_ERROR, "region invalid");
            return;
        }
        this.ikl.setRegion(strLmn);
        this.ijk = RandomUtil.getInstance().generateSecureRandomStr(16);
        HiLog.d("ReportOaidTask", "reqID:" + this.ijk);
        lmn lmnVar = new lmn(cd.lmn("ReportOaidRequst#execute"), HttpPost.METHOD_NAME, "/ext-api/rest/v1/user/events", strLmn);
        byte[] bArrLmn = lmn();
        if (bArrLmn.length <= 0) {
            HiLog.e("ReportOaidTask", "body object is null or body size is 0");
            this.klm.onResult(CallBack.BODY_ERROR, "body is empty");
            return;
        }
        HttpTransportHandler httpTransportHandler = new HttpTransportHandler();
        httpTransportHandler.setUrls(lmnVar.ikl());
        httpTransportHandler.setRequestMethod(lmnVar.klm);
        httpTransportHandler.setReportData(bArrLmn);
        httpTransportHandler.setContext(ar.lmn().lmn.ghi);
        httpTransportHandler.setHttpHeaders(lmnVar.lmn());
        lmnVar.lmn(httpTransportHandler);
    }
}
