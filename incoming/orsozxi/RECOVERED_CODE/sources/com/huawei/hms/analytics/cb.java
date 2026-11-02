package com.huawei.hms.analytics;

import android.os.Looper;
import com.huawei.hms.analytics.core.transport.CallbackListener;
import com.huawei.hms.analytics.core.transport.ITransportHandler;
import com.huawei.hms.analytics.core.transport.net.HttpTransportHandler;
import com.huawei.hms.analytics.core.transport.net.Response;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class cb implements CallbackListener {
    private final CallbackListener hij = this;
    protected String ijk;
    protected cc ikl;
    public final String klm;
    private final String lmn;

    public cb(cc ccVar, String str, String str2, String str3) {
        this.ikl = ccVar;
        this.klm = str;
        this.lmn = str2;
        this.ijk = str3;
    }

    public final String[] ikl() {
        String[] strArr = ar.lmn().lmn.n.lmn.get(this.ijk);
        if (strArr == null) {
            return new String[0];
        }
        String[] strArr2 = (String[]) strArr.clone();
        int length = strArr2.length;
        for (int i = 0; i < length; i++) {
            strArr2[i] = strArr2[i] + this.lmn;
        }
        return strArr2;
    }

    public void klm() {
        HttpTransportHandler httpTransportHandler = new HttpTransportHandler();
        httpTransportHandler.setUrls(ikl());
        httpTransportHandler.setRequestMethod(this.klm);
        httpTransportHandler.setContext(ar.lmn().lmn.ghi);
        httpTransportHandler.setHttpHeaders(lmn());
        lmn(httpTransportHandler);
    }

    public Map<String, String> lmn() {
        at atVar = ar.lmn().lmn;
        HashMap map = new HashMap();
        map.put("App-Id", atVar.n.klm);
        map.put("x-hasdk-productid", atVar.n.ikl);
        map.put("x-hasdk-resourceid", atVar.n.ijk);
        map.put("x-hasdk-token", atVar.hij);
        map.put("x-hasdk-clientid", atVar.n.fgh);
        map.put("Sdk-Ver", "6.6.0.300");
        return map;
    }

    public final void lmn(int i) {
        this.ikl.klm = String.valueOf(i);
        cd.lmn(this.ikl);
    }

    public final void lmn(ITransportHandler iTransportHandler) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            iTransportHandler.execute(this.hij);
            return;
        }
        Response responseExecute = iTransportHandler.execute();
        int httpCode = responseExecute.getHttpCode();
        if (200 == httpCode) {
            this.hij.onSuccess(responseExecute);
        } else {
            this.hij.onFailure(httpCode);
        }
    }
}
