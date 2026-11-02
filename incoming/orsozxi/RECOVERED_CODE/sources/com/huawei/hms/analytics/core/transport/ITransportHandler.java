package com.huawei.hms.analytics.core.transport;

import android.content.Context;
import com.huawei.hms.analytics.core.transport.net.Response;
import java.util.Map;

/* loaded from: classes2.dex */
public interface ITransportHandler {
    Response execute();

    void execute(CallbackListener callbackListener);

    void setContext(Context context);

    void setHttpHeaders(Map<String, String> map);

    void setReportData(String str);

    void setReportData(byte[] bArr);

    void setRequestMethod(String str);

    void setUrls(String[] strArr);
}
