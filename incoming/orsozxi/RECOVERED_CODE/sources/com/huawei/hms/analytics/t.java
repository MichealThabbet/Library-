package com.huawei.hms.analytics;

import android.content.Intent;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.internal.AnyClient;
import com.huawei.hms.common.internal.ResponseErrorCode;
import com.huawei.hms.common.internal.TaskApiCall;
import com.huawei.hms.support.api.client.Status;

/* loaded from: classes2.dex */
public final class t extends TaskApiCall<q, s> {
    public t(String str, String str2) {
        super(str, str2, null);
    }

    @Override // com.huawei.hms.common.internal.TaskApiCall
    protected final /* synthetic */ void doExecute(AnyClient anyClient, ResponseErrorCode responseErrorCode, String str, TaskCompletionSource<s> taskCompletionSource) {
        if (responseErrorCode == null || taskCompletionSource == null) {
            HiLog.w("HiAnalyticsTaskApiRequire", "HMS API call failed. header or taskCompletionSource is null");
            return;
        }
        if (responseErrorCode.getErrorCode() != 0) {
            HiLog.w("HiAnalyticsTaskApiRequire", "HMS API call failed. header.getErrorCode() != CommonCode.OK");
            taskCompletionSource.setException(new ApiException(new Status(responseErrorCode.getErrorCode(), responseErrorCode.getErrorReason())));
            return;
        }
        s sVar = new s(str);
        if (responseErrorCode.getParcelable() == null) {
            HiLog.w("HiAnalyticsTaskApiRequire", "HMS API call failed. header.getParcelable() is null");
            taskCompletionSource.setResult(sVar);
        } else if (responseErrorCode.getParcelable() instanceof Intent) {
            HiLog.i("HiAnalyticsTaskApiRequire", "HMS API call succeed.");
            taskCompletionSource.setResult(new s((Intent) responseErrorCode.getParcelable()));
        } else {
            HiLog.w("HiAnalyticsTaskApiRequire", "HMS API call failed. header.getParcelable() is not Intent");
            taskCompletionSource.setResult(sVar);
        }
    }

    @Override // com.huawei.hms.common.internal.TaskApiCall
    public final int getMinApkVersion() {
        return 40000000;
    }
}
