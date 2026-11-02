package com.huawei.hms.analytics.core.transport;

import com.huawei.hms.analytics.core.transport.net.Response;

/* loaded from: classes2.dex */
public interface CallbackListener {
    void onFailure(int i);

    void onSuccess(Response response);
}
