package com.huawei.agconnect.credential.obs;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.agconnect.AGConnectOptions;
import com.huawei.agconnect.common.api.BaseResponse;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.agconnect.exception.AGCNetworkException;
import com.huawei.agconnect.exception.AGCServerException;
import com.huawei.agconnect.https.Adapter;
import com.huawei.agconnect.https.HttpsException;
import com.huawei.agconnect.https.HttpsKit;
import com.huawei.agconnect.https.HttpsResult;
import com.huawei.agconnect.https.Method;
import com.huawei.agconnect.https.adapter.JsonAdapterFactory;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hmf.tasks.TaskExecutors;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import okhttp3.Authenticator;
import okhttp3.Interceptor;

/* loaded from: classes2.dex */
public class n {
    private static final n a = new n();
    private static final ExecutorService b = v.a();
    private final Map<r, t> c = new HashMap();
    private final Adapter.Factory d = new JsonAdapterFactory();

    private n() {
    }

    public static n a() {
        return a;
    }

    private HttpsKit a(Context context, List<Interceptor> list, Authenticator authenticator, long j, TimeUnit timeUnit) {
        if (j == 0) {
            j = 5000;
            timeUnit = TimeUnit.MILLISECONDS;
        }
        return new HttpsKit.Builder().client(new p(context, Collections.unmodifiableList(list), true).a(authenticator).a(j, timeUnit)).executor(b).build();
    }

    private <Req> Method a(Req req, int i, Adapter.Factory factory) {
        return i == 1 ? new Method.Post(req, factory) : i == 2 ? new Method.Put(req, factory) : new Method.Get(req);
    }

    public <Req, Rsp> Task<Rsp> a(Req req, int i, Class<Rsp> cls, AGConnectOptions aGConnectOptions) {
        return a(req, i, cls, this.d, 5000L, TimeUnit.MILLISECONDS, null, null, aGConnectOptions);
    }

    public <Req, Rsp> Task<Rsp> a(Req req, int i, final Class<Rsp> cls, Adapter.Factory factory, long j, TimeUnit timeUnit, List<Interceptor> list, Authenticator authenticator, AGConnectOptions aGConnectOptions) {
        final Adapter.Factory factory2 = factory != null ? factory : this.d;
        String string = aGConnectOptions.getString("agcgw/url");
        String string2 = aGConnectOptions.getString("agcgw/backurl");
        if (TextUtils.isEmpty(string) && TextUtils.isEmpty(string2)) {
            throw new InvalidParameterException("url is null");
        }
        Context contextB = w.a().b();
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            arrayList.addAll(list);
        } else {
            arrayList.add(new u(string, string2));
        }
        a(contextB, arrayList, authenticator, j, timeUnit).create(contextB).execute(a(req, i, factory2)).addOnSuccessListener(TaskExecutors.immediate(), new OnSuccessListener<HttpsResult>() { // from class: com.huawei.agconnect.credential.obs.n.2
            @Override // com.huawei.hmf.tasks.OnSuccessListener
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onSuccess(HttpsResult httpsResult) throws IllegalAccessException, InstantiationException {
                Object response;
                if (httpsResult.isSuccess()) {
                    if (String.class.equals(cls)) {
                        response = httpsResult.getResponse();
                    } else {
                        try {
                            response = httpsResult.getResponse(cls, factory2);
                        } catch (RuntimeException e) {
                            taskCompletionSource.setException(e);
                            return;
                        }
                    }
                    taskCompletionSource.setResult(response);
                    return;
                }
                if (httpsResult.code() == 401) {
                    try {
                        BaseResponse baseResponse = (BaseResponse) httpsResult.getResponse(BaseResponse.class, factory2);
                        if (baseResponse != null && baseResponse.getRet() != null) {
                            taskCompletionSource.setException(new AGCServerException(httpsResult.getErrorMsg(), httpsResult.code(), baseResponse.getRet().getCode()));
                            return;
                        }
                    } catch (RuntimeException unused) {
                        Logger.e("BackendImpl", "get base response error");
                    }
                }
                taskCompletionSource.setException(new AGCServerException(httpsResult.getErrorMsg(), httpsResult.code()));
            }
        }).addOnFailureListener(TaskExecutors.immediate(), new OnFailureListener() { // from class: com.huawei.agconnect.credential.obs.n.1
            @Override // com.huawei.hmf.tasks.OnFailureListener
            public void onFailure(Exception exc) {
                taskCompletionSource.setException(exc instanceof HttpsException ? !((HttpsException) exc).hasRequest() ? new AGCNetworkException(exc.getMessage(), 0) : new AGCNetworkException(exc.getMessage(), 1) : new AGCServerException(exc.getMessage(), 2));
            }
        });
        return taskCompletionSource.getTask();
    }

    public Map<r, t> b() {
        return this.c;
    }
}
