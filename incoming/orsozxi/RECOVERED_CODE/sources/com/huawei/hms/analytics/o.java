package com.huawei.hms.analytics;

import android.content.Context;
import com.huawei.hms.api.Api;
import com.huawei.hms.common.HuaweiApi;

/* loaded from: classes2.dex */
public final class o extends HuaweiApi<Api.ApiOptions.NoOptions> {
    private static final n lmn = new n();

    public o(Context context) {
        super(context, (Api<Api.ApiOptions.NoOptions>) new Api(""), new Api.ApiOptions.NoOptions(), lmn);
    }
}
