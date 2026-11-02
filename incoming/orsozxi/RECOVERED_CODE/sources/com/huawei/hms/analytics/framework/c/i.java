package com.huawei.hms.analytics.framework.c;

import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.core.storage.IStorageHandler;
import com.huawei.hms.analytics.core.transport.net.HttpTransportHandler;
import com.huawei.hms.analytics.framework.config.ICallback;
import com.huawei.hms.analytics.framework.config.ICollectorConfig;
import com.huawei.hms.analytics.framework.config.IMandatoryParameters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.client.methods.HttpPost;

/* loaded from: classes2.dex */
public final class i implements Runnable {
    ICallback a;
    private byte[] b;
    private List<Event> c;
    private com.huawei.hms.analytics.framework.c.a.a d;

    public i(byte[] bArr, com.huawei.hms.analytics.framework.c.a.a aVar, List<Event> list) {
        this.b = bArr != null ? (byte[]) bArr.clone() : null;
        this.c = list;
        this.d = aVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0073  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.String[] a() {
        /*
            r5 = this;
            com.huawei.hms.analytics.framework.b.b r0 = com.huawei.hms.analytics.framework.b.b.a()
            com.huawei.hms.analytics.framework.c.a.a r1 = r5.d
            java.lang.String r1 = r1.a
            com.huawei.hms.analytics.framework.config.ICollectorConfig r0 = r0.a(r1)
            com.huawei.hms.analytics.framework.c.a.a r1 = r5.d
            java.lang.String r1 = r1.b
            com.huawei.hms.analytics.framework.c.a.a r2 = r5.d
            java.lang.String r2 = r2.c
            java.lang.String[] r0 = r0.getCollectUrls(r1, r2)
            r1 = 0
        L19:
            int r2 = r0.length
            if (r1 >= r2) goto L81
            com.huawei.hms.analytics.framework.c.a.a r2 = r5.d
            java.lang.String r2 = r2.b
            java.lang.String r3 = "oper"
            boolean r2 = r3.equals(r2)
            java.lang.String r3 = "{url}"
            if (r2 != 0) goto L73
            com.huawei.hms.analytics.framework.c.a.a r2 = r5.d
            java.lang.String r2 = r2.b
            java.lang.String r4 = "maint"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L43
            r2 = r0[r1]
            java.lang.String r4 = "{url}/common/hmshimaintqrt"
            java.lang.String r2 = r4.replace(r3, r2)
            r0[r1] = r2
            goto L7e
        L43:
            com.huawei.hms.analytics.framework.c.a.a r2 = r5.d
            java.lang.String r2 = r2.b
            java.lang.String r4 = "diffprivacy"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L5b
            r2 = r0[r1]
            java.lang.String r4 = "{url}/common/common2"
            java.lang.String r2 = r4.replace(r3, r2)
            r0[r1] = r2
            goto L7e
        L5b:
            com.huawei.hms.analytics.framework.c.a.a r2 = r5.d
            java.lang.String r2 = r2.b
            java.lang.String r4 = "preins"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L73
            r2 = r0[r1]
            java.lang.String r4 = "{url}/common/hmshioperbatch"
            java.lang.String r2 = r4.replace(r3, r2)
            r0[r1] = r2
            goto L7e
        L73:
            r2 = r0[r1]
            java.lang.String r4 = "{url}/common/hmshioperqrt"
            java.lang.String r2 = r4.replace(r3, r2)
            r0[r1] = r2
        L7e:
            int r1 = r1 + 1
            goto L19
        L81:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.framework.c.i.a():java.lang.String[]");
    }

    @Override // java.lang.Runnable
    public final void run() {
        IStorageHandler iStorageHandlerA;
        IStorageHandler iStorageHandlerA2;
        HiLog.d("SendMission", "send data running");
        long jCurrentTimeMillis = System.currentTimeMillis();
        HttpTransportHandler httpTransportHandler = new HttpTransportHandler();
        IMandatoryParameters iMandatoryParameters = com.huawei.hms.analytics.framework.b.b.a().a;
        httpTransportHandler.setUrls(a());
        httpTransportHandler.setRequestMethod(HttpPost.METHOD_NAME);
        httpTransportHandler.setReportData(this.b);
        ICollectorConfig iCollectorConfigA = com.huawei.hms.analytics.framework.b.b.a().a(this.d.a);
        String str = this.d.d;
        ICollectorConfig iCollectorConfigA2 = com.huawei.hms.analytics.framework.b.b.a().a(this.d.a);
        String appVer = com.huawei.hms.analytics.framework.b.b.a().a.getAppVer();
        String model = com.huawei.hms.analytics.framework.b.b.a().a.getModel();
        HashMap map = new HashMap();
        map.put("App-Id", iCollectorConfigA2.getAppId());
        map.put("App-Ver", appVer);
        map.put("Sdk-Name", "hianalytics");
        map.put("Sdk-Ver", "6.6.0.300");
        map.put("Device-Type", model);
        map.put("servicetag", this.d.a);
        HiLog.i("SendMission", "sendData RequestId : ".concat(String.valueOf(str)));
        map.put("Request-Id", str);
        Map<String, String> httpHeader = iCollectorConfigA.getHttpHeader(this.d.b);
        if (httpHeader != null) {
            map.putAll(httpHeader);
        }
        httpTransportHandler.setHttpHeaders(map);
        httpTransportHandler.setContext(iMandatoryParameters.getContext());
        int httpCode = httpTransportHandler.execute().getHttpCode();
        try {
            if (httpCode == 200) {
                if (!this.d.e && !this.d.f && (iStorageHandlerA2 = com.huawei.hms.analytics.framework.a.a.a(this.d.a)) != null && this.c != null && this.c.size() > 0) {
                    HiLog.i("SendMission", "storageHandler deleteEvents");
                    iStorageHandlerA2.deleteEvents(this.c);
                }
            } else if (this.a != null && this.a.isNeedStorage() && (iStorageHandlerA = com.huawei.hms.analytics.framework.a.a.a(this.d.a)) != null) {
                iStorageHandlerA.insertEx(this.c);
            }
            if (this.a != null) {
                this.a.onResult(httpCode, jCurrentTimeMillis, this.d.f ? -2 : 0, this.c);
            }
            HiLog.i("SendMission", "events PostRequest sendevent TYPE : %s, TAG : %s, resultCode: %d ,reqID:" + this.d.d, this.d.b, this.d.a, Integer.valueOf(httpCode));
        } finally {
            if (this.a != null) {
                this.a.onResult(httpCode, jCurrentTimeMillis, this.d.f ? -2 : 0, this.c);
            }
            HiLog.i("SendMission", "events PostRequest sendevent TYPE : %s, TAG : %s, resultCode: %d ,reqID:" + this.d.d, this.d.b, this.d.a, Integer.valueOf(httpCode));
        }
    }
}
