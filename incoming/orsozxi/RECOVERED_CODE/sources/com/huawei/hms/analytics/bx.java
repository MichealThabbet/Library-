package com.huawei.hms.analytics;

import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.transport.net.Response;
import org.apache.http.client.methods.HttpGet;

/* loaded from: classes2.dex */
public final class bx extends cb {
    public lmn lmn;

    public interface lmn {
        void lmn();
    }

    public bx(String str) {
        super(cd.lmn("GETInstanceEventRequest#execute"), HttpGet.METHOD_NAME, "/analytics/api/events/online", str);
    }

    @Override // com.huawei.hms.analytics.cb
    public final void klm() {
        super.klm();
    }

    @Override // com.huawei.hms.analytics.core.transport.CallbackListener
    public final void onFailure(int i) {
        HiLog.w("InstanceRequest", "GET instance event exception,errorCode: ".concat(String.valueOf(i)));
        lmn(i);
    }

    @Override // com.huawei.hms.analytics.core.transport.CallbackListener
    public final void onSuccess(Response response) {
        lmn lmnVar;
        this.ikl.lmn = "0";
        lmn(response.getHttpCode());
        if (response.getHttpCode() == 200) {
            HiLog.i("InstanceRequest", "get instance events result code : " + response.getHttpCode());
            String content = response.getContent();
            at atVar = ar.lmn().lmn;
            if (TextUtils.isEmpty(content)) {
                HiLog.i("InstanceRequest", "instance events config is empty");
                atVar.q = null;
                dl.lmn(atVar.ghi, "global_v2", "instance_event_info");
                return;
            }
            av avVarLmn = ca.lmn(atVar.ghi, content);
            if (avVarLmn == null) {
                return;
            }
            ar.lmn().lmn.q = avVarLmn;
            if (avVarLmn.ghi && (lmnVar = this.lmn) != null) {
                lmnVar.lmn();
            }
        }
    }
}
