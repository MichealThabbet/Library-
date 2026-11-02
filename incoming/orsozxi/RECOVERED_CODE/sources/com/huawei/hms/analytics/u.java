package com.huawei.hms.analytics;

import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.internal.AnyClient;
import com.huawei.hms.common.internal.ResponseErrorCode;
import com.huawei.hms.common.internal.TaskApiCall;
import com.huawei.hms.support.api.client.Status;

/* loaded from: classes2.dex */
public final class u extends TaskApiCall<q, String> {
    public u(String str, String str2) {
        super(str, str2, null);
    }

    @Override // com.huawei.hms.common.internal.TaskApiCall
    protected final /* synthetic */ void doExecute(AnyClient anyClient, ResponseErrorCode responseErrorCode, String str, TaskCompletionSource<String> taskCompletionSource) {
        if (responseErrorCode == null || taskCompletionSource == null) {
            HiLog.w("HiAnalyticsTaskApiRequire", "HMS API call failed. header or taskCompletionSource is null");
        } else if (responseErrorCode.getErrorCode() == 0) {
            HiLog.i("HiAnalyticsTaskApiRequire", "HMS API call succeed.");
            taskCompletionSource.setResult("HMS API call succeed.");
        } else {
            HiLog.w("HiAnalyticsTaskApiRequire", "HMS API call failed. header.getErrorCode() != CommonCode.OK ");
            taskCompletionSource.setException(new ApiException(new Status(responseErrorCode.getErrorCode(), responseErrorCode.getErrorReason())));
        }
    }

    @Override // com.huawei.hms.common.internal.TaskApiCall
    public final int getMinApkVersion() {
        return 40000000;
    }
}
