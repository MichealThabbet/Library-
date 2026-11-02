package com.huawei.hms.analytics;

import android.os.Build;
import com.huawei.hms.analytics.bc;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.transport.net.HttpTransportHandler;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.methods.HttpPost;

/* loaded from: classes2.dex */
public final class cq {
    public static final Charset lmn = Charset.forName("UTF-8");
    private boolean ijk;
    private String ikl;
    private cn klm;

    public cq(cn cnVar, String str, boolean z) {
        this.klm = cnVar;
        this.ikl = str;
        this.ijk = z;
    }

    private Map<String, String> ijk() {
        at atVar = ar.lmn().lmn;
        HashMap map = new HashMap();
        map.put("App-Id", atVar.n.klm);
        map.put("App-Ver", atVar.klm);
        map.put("x-hasdk-productid", atVar.n.ikl);
        map.put("x-hasdk-resourceid", atVar.n.ijk);
        map.put("x-hasdk-token", atVar.hij);
        map.put("x-hasdk-clientid", atVar.n.fgh);
        map.put("Sdk-Ver", "6.6.0.300");
        map.put("Device-Type", Build.MODEL);
        map.put("Sdk-Name", "hianalytics");
        map.put("servicetag", "_openness_log_tag");
        map.put("x-hasdk-log-region", this.klm.fgh);
        return map;
    }

    private String[] ikl() {
        String[] strArr = (String[]) ar.lmn().lmn.n.lmn.get(this.ikl).clone();
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = "{url}/common/hmshimaintqrt".replace("{url}", strArr[i]);
        }
        return strArr;
    }

    private void klm() {
        be.lmn().lmn(this.klm.klm);
        if (this.klm.ikl()) {
            be.lmn().ijk();
            this.klm.hij();
        }
    }

    public final void lmn() {
        HiLog.d("LgEvtSend", "start report");
        String string = this.klm.klm().toString();
        String str = this.klm.hij;
        HttpTransportHandler httpTransportHandler = new HttpTransportHandler();
        httpTransportHandler.setUrls(ikl());
        httpTransportHandler.setRequestMethod(HttpPost.METHOD_NAME);
        httpTransportHandler.setContext(ar.lmn().lmn.ghi);
        httpTransportHandler.setHttpHeaders(ijk());
        httpTransportHandler.setReportData(dm.lmn(string.getBytes(lmn)));
        int httpCode = httpTransportHandler.execute().getHttpCode();
        HiLog.i("LgEvtSend", "log evts PostRequest, resultCode: %d ,reqID:".concat(String.valueOf(str)), Integer.valueOf(httpCode));
        if (httpCode == 200 || this.ijk) {
            try {
                klm();
            } catch (bc.lmn unused) {
                HiLog.e("LgEvtSend", "log db init failed");
            }
        }
        this.klm.ijk();
    }
}
