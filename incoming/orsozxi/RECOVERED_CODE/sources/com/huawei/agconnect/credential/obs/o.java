package com.huawei.agconnect.credential.obs;

import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.common.api.Backend;
import com.huawei.agconnect.common.api.BackendService;
import com.huawei.agconnect.common.api.BaseRequest;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.agconnect.common.api.RequestThrottle;
import com.huawei.agconnect.exception.AGCServerException;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hmf.tasks.TaskExecutors;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class o {
    private static AGConnectInstance a(BackendService.Options options) {
        return (options == null || options.getApp() == null) ? AGConnectInstance.getInstance() : options.getApp();
    }

    public static <Rsp> Task<Rsp> a(BaseRequest baseRequest, int i, Class<Rsp> cls, BackendService.Options options) {
        Logger.i("BackendServiceImpl", "sendRequest2");
        BackendService.Options optionsBuild = options == null ? new BackendService.Options.Builder().app(AGConnectInstance.getInstance()).build() : options;
        if (optionsBuild.getApp() == null) {
            optionsBuild = optionsBuild.newBuilder().app(AGConnectInstance.getInstance()).build();
        }
        baseRequest.initBase(optionsBuild.getApp());
        ArrayList arrayList = new ArrayList();
        arrayList.add(new u(optionsBuild));
        if (!optionsBuild.isClientToken()) {
            return Backend.call(baseRequest, i, cls, optionsBuild.getFactory(), optionsBuild.getTimeout(), optionsBuild.getTimeUnit(), arrayList, null, a(optionsBuild).getOptions());
        }
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        final RequestThrottle.Throttle throttle = optionsBuild.getThrottle();
        Logger.i("BackendServiceImpl", "clientToken:" + optionsBuild.isClientToken());
        if (optionsBuild.isClientToken()) {
            arrayList.add(new q(optionsBuild.getApp()));
        }
        Logger.i("BackendServiceImpl", "accessToken:" + optionsBuild.isAccessToken());
        if (optionsBuild.isAccessToken()) {
            arrayList.add(new m(optionsBuild.getApp(), optionsBuild.getAccessTokenType() == BackendService.AccessTokenType.MUST));
        }
        if (optionsBuild.getThrottle() != null) {
            if (optionsBuild.getThrottle().getEndTime() > 0) {
                taskCompletionSource.setException(new AGCServerException("fetch throttled, try again later", 1));
            }
            optionsBuild.getThrottle().addForStart();
        }
        Backend.call(baseRequest, i, cls, optionsBuild.getFactory(), optionsBuild.getTimeout(), optionsBuild.getTimeUnit(), arrayList, new k(optionsBuild), a(optionsBuild).getOptions()).addOnSuccessListener(TaskExecutors.immediate(), new OnSuccessListener<Rsp>() { // from class: com.huawei.agconnect.credential.obs.o.2
            @Override // com.huawei.hmf.tasks.OnSuccessListener
            public void onSuccess(Rsp rsp) {
                RequestThrottle.Throttle throttle2 = throttle;
                if (throttle2 != null) {
                    throttle2.addForSuccess();
                }
                taskCompletionSource.setResult(rsp);
            }
        }).addOnFailureListener(TaskExecutors.immediate(), new OnFailureListener() { // from class: com.huawei.agconnect.credential.obs.o.1
            @Override // com.huawei.hmf.tasks.OnFailureListener
            public void onFailure(Exception exc) {
                RequestThrottle.Throttle throttle2 = throttle;
                if (throttle2 != null && throttle2.checkFail(exc)) {
                    throttle.addForFail();
                }
                taskCompletionSource.setException(exc);
            }
        });
        return taskCompletionSource.getTask();
    }
}
