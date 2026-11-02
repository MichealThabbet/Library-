package com.huawei.hms.common;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hms.adapter.BinderAdapter;
import com.huawei.hms.api.Api;
import com.huawei.hms.api.Api.ApiOptions;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.common.internal.AbstractClientBuilder;
import com.huawei.hms.common.internal.AnyClient;
import com.huawei.hms.common.internal.BaseHmsClient;
import com.huawei.hms.common.internal.BindResolveClients;
import com.huawei.hms.common.internal.ClientSettings;
import com.huawei.hms.common.internal.HmsClient;
import com.huawei.hms.common.internal.RequestHeader;
import com.huawei.hms.common.internal.RequestManager;
import com.huawei.hms.common.internal.ResolveClientBean;
import com.huawei.hms.common.internal.ResponseHeader;
import com.huawei.hms.common.internal.TaskApiCall;
import com.huawei.hms.common.internal.TaskApiCallWrapper;
import com.huawei.hms.common.internal.TransactionIdCreater;
import com.huawei.hms.core.aidl.IAIDLInvoke;
import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.client.SubAppInfo;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.huawei.hms.support.hianalytics.b;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.Checker;
import com.huawei.hms.utils.HMSBIInitializer;
import com.huawei.hms.utils.HMSPackageManager;
import com.huawei.hms.utils.Util;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class HuaweiApi<TOption extends Api.ApiOptions> {
    private static final String TAG = "HuaweiApi";
    private String innerHmsPkg;
    private boolean isUseInnerHms;
    private WeakReference<Activity> mActivity;
    private String mAppID;
    private AbstractClientBuilder<?, TOption> mClientBuilder;
    private Context mContext;
    private String mHostAppid;
    private Context mHostContext;
    private int mKitSdkVersion;
    private TOption mOption;
    private SubAppInfo mSubAppInfo;
    private RequestManager requestManager;
    private int apiLevel = 1;
    private boolean isFirstReqSent = false;

    public static class RequestHandler<OptionsT extends Api.ApiOptions> implements BaseHmsClient.ConnectionCallbacks, BaseHmsClient.OnConnectionFailedListener {
        private final HuaweiApi<OptionsT> mApi;
        private final AnyClient mClient;
        private ResolveClientBean mResolveClientBean;
        public final Queue<TaskApiCallbackWrapper> callbackWaitQueue = new LinkedList();
        private final Queue<TaskApiCallbackWrapper> callbackRunQueue = new LinkedList();
        private ConnectionResult mConnectionResult = null;

        RequestHandler(HuaweiApi<OptionsT> huaweiApi) {
            this.mApi = huaweiApi;
            this.mClient = huaweiApi.getClient(RequestManager.getHandler().getLooper(), this);
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x0049 A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private java.lang.String errorReason(com.huawei.hms.api.ConnectionResult r5) {
            /*
                r4 = this;
                com.huawei.hms.common.HuaweiApi<OptionsT extends com.huawei.hms.api.Api$ApiOptions> r0 = r4.mApi
                android.content.Context r0 = r0.getContext()
                boolean r0 = com.huawei.hms.utils.Util.isAvailableLibExist(r0)
                r1 = 10
                r2 = 8
                r3 = -1
                if (r0 == 0) goto L3f
                int r5 = r5.getErrorCode()
                if (r5 == r3) goto L53
                r0 = 3
                if (r5 == r0) goto L3c
                if (r5 == r2) goto L50
                if (r5 == r1) goto L4d
                r0 = 13
                if (r5 == r0) goto L38
                r0 = 21
                if (r5 == r0) goto L35
                switch(r5) {
                    case 25: goto L32;
                    case 26: goto L2e;
                    case 27: goto L2a;
                    default: goto L29;
                }
            L29:
                goto L49
            L2a:
                java.lang.String r5 = "there is already an update popup at the front desk, but it hasn't been clicked or it is not effective for a while"
                goto L55
            L2e:
                java.lang.String r5 = "update failed, because no activity incoming, can't pop update page"
                goto L55
            L32:
                java.lang.String r5 = "failed to get update result"
                goto L55
            L35:
                java.lang.String r5 = "device is too old to be support"
                goto L55
            L38:
                java.lang.String r5 = "update cancelled"
                goto L55
            L3c:
                java.lang.String r5 = "HuaWei Mobile Service is disabled"
                goto L55
            L3f:
                int r5 = r5.getErrorCode()
                if (r5 == r3) goto L53
                if (r5 == r2) goto L50
                if (r5 == r1) goto L4d
            L49:
                java.lang.String r5 = "unknown errorReason"
                goto L55
            L4d:
                java.lang.String r5 = "application configuration error, please developer check configuration"
                goto L55
            L50:
                java.lang.String r5 = "internal error"
                goto L55
            L53:
                java.lang.String r5 = "get update result, but has other error codes"
            L55:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.common.HuaweiApi.RequestHandler.errorReason(com.huawei.hms.api.ConnectionResult):java.lang.String");
        }

        private String getTransactionId(String str, String str2) {
            return TextUtils.isEmpty(str) ? TransactionIdCreater.getId(this.mApi.getAppID(), str2) : str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void innerConnected() {
            this.mConnectionResult = null;
            this.callbackRunQueue.clear();
            Iterator<TaskApiCallbackWrapper> it2 = this.callbackWaitQueue.iterator();
            while (it2.hasNext()) {
                postMessage(it2.next());
            }
            this.callbackWaitQueue.clear();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void innerConnectionFailed(ConnectionResult connectionResult) {
            this.mConnectionResult = connectionResult;
            Iterator<TaskApiCallbackWrapper> it2 = this.callbackWaitQueue.iterator();
            boolean z = true;
            while (it2.hasNext()) {
                TaskApiCallWrapper apiCallWrapper = it2.next().getApiCallWrapper();
                ResponseHeader responseHeader = new ResponseHeader(1, CommonCode.ErrorCode.CLIENT_API_INVALID, "Connection Failed:" + errorReason(connectionResult) + "(" + connectionResult.getErrorCode() + ")");
                responseHeader.setTransactionId(apiCallWrapper.getTaskApiCall().getTransactionId());
                b.a(this.mApi.getContext(), responseHeader, String.valueOf(this.mApi.getKitSdkVersion()));
                if (this.mConnectionResult.getResolution() != null && z) {
                    responseHeader.setParcelable(this.mConnectionResult.getResolution());
                    z = false;
                    if (Util.isAvailableLibExist(this.mApi.getContext()) && this.mConnectionResult.getErrorCode() == 26) {
                        responseHeader.setResolution(CommonCode.Resolution.HAS_RESOLUTION);
                    }
                }
                int errorCode = this.mConnectionResult.getErrorCode();
                if (errorCode == 30 || errorCode == 31) {
                    responseHeader.setErrorCode(errorCode);
                }
                apiCallWrapper.getTaskApiCall().onResponse(this.mClient, responseHeader, null, apiCallWrapper.getTaskCompletionSource());
            }
            this.callbackWaitQueue.clear();
            this.callbackRunQueue.clear();
            this.mConnectionResult = null;
            this.mClient.disconnect();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void innerConnectionSuspended() {
            HMSLog.i(HuaweiApi.TAG, "wait queue size = " + this.callbackWaitQueue.size());
            HMSLog.i(HuaweiApi.TAG, "run queue size = " + this.callbackRunQueue.size());
            Iterator<TaskApiCallbackWrapper> it2 = this.callbackWaitQueue.iterator();
            while (it2.hasNext()) {
                sendConnectionSuspended(it2.next());
            }
            Iterator<TaskApiCallbackWrapper> it3 = this.callbackRunQueue.iterator();
            while (it3.hasNext()) {
                sendConnectionSuspended(it3.next());
            }
            this.callbackWaitQueue.clear();
            this.callbackRunQueue.clear();
            this.mConnectionResult = null;
            this.mClient.disconnect();
        }

        private void sendConnectionSuspended(TaskApiCallbackWrapper taskApiCallbackWrapper) {
            TaskApiCallWrapper apiCallWrapper = taskApiCallbackWrapper.getApiCallWrapper();
            ResponseHeader responseHeader = new ResponseHeader(1, CommonCode.ErrorCode.CLIENT_API_INVALID, "Connection Suspended");
            responseHeader.setTransactionId(apiCallWrapper.getTaskApiCall().getTransactionId());
            apiCallWrapper.getTaskApiCall().onResponse(this.mClient, responseHeader, null, apiCallWrapper.getTaskCompletionSource());
        }

        private TaskApiCallbackWrapper wrapperRequest(final TaskApiCallWrapper taskApiCallWrapper) {
            return new TaskApiCallbackWrapper(taskApiCallWrapper, new AnyClient.CallBack() { // from class: com.huawei.hms.common.HuaweiApi.RequestHandler.1
                private AtomicBoolean isFirstRsp = new AtomicBoolean(true);

                @Override // com.huawei.hms.common.internal.AnyClient.CallBack
                public void onCallback(IMessageEntity iMessageEntity, String str) {
                    if (!(iMessageEntity instanceof ResponseHeader)) {
                        HMSLog.e(HuaweiApi.TAG, "header is not instance of ResponseHeader");
                        return;
                    }
                    ResponseHeader responseHeader = (ResponseHeader) iMessageEntity;
                    if (responseHeader.getErrorCode() == 11) {
                        RequestHandler.this.disconnect();
                        HMSLog.i(HuaweiApi.TAG, "unbind service");
                    }
                    if (!TextUtils.isEmpty(responseHeader.getResolution())) {
                        HMSLog.e(HuaweiApi.TAG, "Response has resolution: " + responseHeader.getResolution());
                    }
                    if (this.isFirstRsp.compareAndSet(true, false)) {
                        b.a(RequestHandler.this.mApi.getContext(), responseHeader, String.valueOf(RequestHandler.this.mApi.getKitSdkVersion()));
                    }
                    taskApiCallWrapper.getTaskApiCall().onResponse(RequestHandler.this.mClient, responseHeader, str, taskApiCallWrapper.getTaskCompletionSource());
                }
            });
        }

        synchronized void connect(int i, TaskApiCallbackWrapper taskApiCallbackWrapper) {
            if (this.mClient.isConnected()) {
                HMSLog.d(HuaweiApi.TAG, "client is connected");
                return;
            }
            if (this.mClient.isConnecting()) {
                HMSLog.d(HuaweiApi.TAG, "client is isConnecting");
                return;
            }
            if (this.mApi.getActivity() != null) {
                if (this.mResolveClientBean == null) {
                    this.mResolveClientBean = new ResolveClientBean(this.mClient, i);
                }
                if (BindResolveClients.getInstance().isClientRegistered(this.mResolveClientBean)) {
                    HMSLog.i(HuaweiApi.TAG, "mResolveClientBean has already register, return!");
                    return;
                }
                BindResolveClients.getInstance().register(this.mResolveClientBean);
            }
            this.mClient.connect(i);
        }

        void disconnect() {
            this.mClient.disconnect();
        }

        public AnyClient getClient() {
            return this.mClient;
        }

        @Override // com.huawei.hms.common.internal.BaseHmsClient.ConnectionCallbacks
        public void onConnected() {
            HMSLog.i(HuaweiApi.TAG, "onConnected");
            BindResolveClients.getInstance().unRegister(this.mResolveClientBean);
            this.mResolveClientBean = null;
            RequestManager.getHandler().post(new Runnable() { // from class: com.huawei.hms.common.HuaweiApi.RequestHandler.4
                @Override // java.lang.Runnable
                public void run() {
                    RequestHandler.this.innerConnected();
                }
            });
        }

        @Override // com.huawei.hms.common.internal.BaseHmsClient.OnConnectionFailedListener
        public void onConnectionFailed(final ConnectionResult connectionResult) {
            HMSLog.i(HuaweiApi.TAG, "onConnectionFailed");
            BindResolveClients.getInstance().unRegister(this.mResolveClientBean);
            this.mResolveClientBean = null;
            RequestManager.getHandler().post(new Runnable() { // from class: com.huawei.hms.common.HuaweiApi.RequestHandler.3
                @Override // java.lang.Runnable
                public void run() {
                    RequestHandler.this.innerConnectionFailed(connectionResult);
                }
            });
        }

        @Override // com.huawei.hms.common.internal.BaseHmsClient.ConnectionCallbacks
        public void onConnectionSuspended(int i) {
            HMSLog.i(HuaweiApi.TAG, "onConnectionSuspended");
            BindResolveClients.getInstance().unRegister(this.mResolveClientBean);
            this.mResolveClientBean = null;
            RequestManager.getHandler().post(new Runnable() { // from class: com.huawei.hms.common.HuaweiApi.RequestHandler.5
                @Override // java.lang.Runnable
                public void run() {
                    RequestHandler.this.innerConnectionSuspended();
                }
            });
        }

        public void postMessage(final TaskApiCallbackWrapper taskApiCallbackWrapper) {
            RequestManager.addToConnectedReqMap(taskApiCallbackWrapper.getApiCallWrapper().getTaskApiCall().getTransactionId(), this);
            this.callbackRunQueue.add(taskApiCallbackWrapper);
            String uri = taskApiCallbackWrapper.getApiCallWrapper().getTaskApiCall().getUri();
            String packageName = (((HuaweiApi) this.mApi).mHostContext == null ? this.mApi.getContext() : ((HuaweiApi) this.mApi).mHostContext).getPackageName();
            if (((HuaweiApi) this.mApi).mHostContext != null) {
                HuaweiApi<OptionsT> huaweiApi = this.mApi;
                huaweiApi.setHostAppId(((HuaweiApi) huaweiApi).mHostContext);
            }
            final RequestHeader requestHeader = new RequestHeader();
            requestHeader.setSrvName(uri.split("\\.")[0]);
            requestHeader.setApiName(uri);
            requestHeader.setAppID(this.mApi.getAppID() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + this.mApi.getSubAppID());
            requestHeader.setPkgName(packageName);
            requestHeader.setSessionId(this.mClient.getSessionId());
            TaskApiCall taskApiCall = taskApiCallbackWrapper.getApiCallWrapper().getTaskApiCall();
            requestHeader.setTransactionId(getTransactionId(taskApiCall.getTransactionId(), uri));
            requestHeader.setParcelable(taskApiCall.getParcelable());
            requestHeader.setKitSdkVersion(this.mApi.getKitSdkVersion());
            requestHeader.setApiLevel(Math.max(this.mApi.getApiLevel(), taskApiCall.getApiLevel()));
            this.mClient.post(requestHeader, taskApiCall.getRequestJson(), new AnyClient.CallBack() { // from class: com.huawei.hms.common.HuaweiApi.RequestHandler.2
                @Override // com.huawei.hms.common.internal.AnyClient.CallBack
                public void onCallback(IMessageEntity iMessageEntity, String str) {
                    AnyClient.CallBack callBack = taskApiCallbackWrapper.getCallBack();
                    if (callBack != null) {
                        callBack.onCallback(iMessageEntity, str);
                    }
                    RequestManager.removeReqByTransId(requestHeader.getTransactionId());
                    RequestManager.getHandler().post(new Runnable() { // from class: com.huawei.hms.common.HuaweiApi.RequestHandler.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            RequestHandler.this.callbackRunQueue.remove(taskApiCallbackWrapper);
                        }
                    });
                }
            });
        }

        void sendRequest(TaskApiCallWrapper taskApiCallWrapper) {
            HMSLog.i(HuaweiApi.TAG, "sendRequest");
            TaskApiCallbackWrapper taskApiCallbackWrapperWrapperRequest = wrapperRequest(taskApiCallWrapper);
            if (this.mClient.isConnected()) {
                HMSLog.i(HuaweiApi.TAG, "isConnected:true.");
                BinderAdapter adapter = ((BaseHmsClient) this.mClient).getAdapter();
                adapter.updateDelayTask();
                ((HmsClient) this.mClient).setService(IAIDLInvoke.Stub.asInterface(adapter.getServiceBinder()));
                postMessage(taskApiCallbackWrapperWrapperRequest);
                return;
            }
            HMSLog.i(HuaweiApi.TAG, "isConnected:false.");
            this.callbackWaitQueue.add(taskApiCallbackWrapperWrapperRequest);
            ConnectionResult connectionResult = this.mConnectionResult;
            if (connectionResult == null || connectionResult.getErrorCode() == 0) {
                RequestManager.addRequestToQueue(this);
                Object obj = this.mClient;
                if (obj instanceof BaseHmsClient) {
                    ((BaseHmsClient) obj).setInternalRequest(this);
                }
                connect(taskApiCallWrapper.getTaskApiCall().getMinApkVersion(), taskApiCallbackWrapperWrapperRequest);
                return;
            }
            HMSLog.i(HuaweiApi.TAG, "onConnectionFailed, ErrorCode:" + this.mConnectionResult.getErrorCode());
            onConnectionFailed(this.mConnectionResult);
        }
    }

    private static class RequestRunnable<OptionsT extends Api.ApiOptions> implements Runnable {
        private final HuaweiApi<OptionsT> mApi;
        private final TaskApiCallWrapper mTaskApiCallWrapper;

        public RequestRunnable(HuaweiApi<OptionsT> huaweiApi, TaskApiCallWrapper taskApiCallWrapper) {
            this.mApi = huaweiApi;
            this.mTaskApiCallWrapper = taskApiCallWrapper;
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x0056 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:27:0x005f  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x0062  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0066  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private void notifyCpException(com.huawei.hms.common.HuaweiApi.RequestHandler r9, java.lang.Exception r10) {
            /*
                r8 = this;
                java.lang.String r0 = "HuaweiApi"
                r1 = 1
                r2 = 0
                com.huawei.hms.common.internal.AnyClient r9 = r9.getClient()     // Catch: java.lang.RuntimeException -> L36
                com.huawei.hms.common.internal.ResponseHeader r3 = new com.huawei.hms.common.internal.ResponseHeader     // Catch: java.lang.RuntimeException -> L33
                r4 = 907135001(0x3611c819, float:2.1723156E-6)
                java.lang.String r10 = r10.getMessage()     // Catch: java.lang.RuntimeException -> L33
                r3.<init>(r1, r4, r10)     // Catch: java.lang.RuntimeException -> L33
                org.json.JSONObject r10 = new org.json.JSONObject     // Catch: java.lang.RuntimeException -> L2f
                r10.<init>()     // Catch: java.lang.RuntimeException -> L2f
                java.lang.String r10 = r10.toString()     // Catch: java.lang.RuntimeException -> L2f
                com.huawei.hms.common.internal.TaskApiCallWrapper r4 = r8.mTaskApiCallWrapper     // Catch: java.lang.RuntimeException -> L2c
                com.huawei.hmf.tasks.TaskCompletionSource r4 = r4.getTaskCompletionSource()     // Catch: java.lang.RuntimeException -> L2c
                com.huawei.hms.common.internal.TaskApiCallWrapper r5 = r8.mTaskApiCallWrapper     // Catch: java.lang.RuntimeException -> L2a
                com.huawei.hms.common.internal.TaskApiCall r2 = r5.getTaskApiCall()     // Catch: java.lang.RuntimeException -> L2a
                goto L54
            L2a:
                r5 = move-exception
                goto L3c
            L2c:
                r5 = move-exception
                r4 = r2
                goto L3c
            L2f:
                r5 = move-exception
                r10 = r2
                r4 = r10
                goto L3c
            L33:
                r10 = move-exception
                r5 = r10
                goto L39
            L36:
                r9 = move-exception
                r5 = r9
                r9 = r2
            L39:
                r10 = r2
                r3 = r10
                r4 = r3
            L3c:
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "<notifyCpException> "
                r6.append(r7)
                java.lang.String r5 = r5.getMessage()
                r6.append(r5)
                java.lang.String r5 = r6.toString()
                com.huawei.hms.support.log.HMSLog.e(r0, r5)
            L54:
                if (r9 == 0) goto L5f
                if (r3 == 0) goto L5f
                if (r10 == 0) goto L5f
                if (r4 == 0) goto L5f
                if (r2 == 0) goto L5f
                goto L60
            L5f:
                r1 = 0
            L60:
                if (r1 == 0) goto L66
                r2.onResponse(r9, r3, r10, r4)
                goto L6b
            L66:
                java.lang.String r9 = "<notifyCpException> isNotify is false, Can not notify CP."
                com.huawei.hms.support.log.HMSLog.e(r0, r9)
            L6b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.common.HuaweiApi.RequestRunnable.notifyCpException(com.huawei.hms.common.HuaweiApi$RequestHandler, java.lang.Exception):void");
        }

        @Override // java.lang.Runnable
        public void run() {
            RequestHandler requestHandler = new RequestHandler(this.mApi);
            try {
                requestHandler.sendRequest(this.mTaskApiCallWrapper);
            } catch (RuntimeException e) {
                notifyCpException(requestHandler, e);
            }
        }
    }

    public static class TaskApiCallbackWrapper {
        private final TaskApiCallWrapper mApiCallWrapper;
        private final AnyClient.CallBack mCallBack;

        TaskApiCallbackWrapper(TaskApiCallWrapper taskApiCallWrapper, AnyClient.CallBack callBack) {
            this.mApiCallWrapper = taskApiCallWrapper;
            this.mCallBack = callBack;
        }

        TaskApiCallWrapper getApiCallWrapper() {
            return this.mApiCallWrapper;
        }

        AnyClient.CallBack getCallBack() {
            return this.mCallBack;
        }
    }

    public HuaweiApi(Activity activity, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder, int i) {
        Checker.checkNonNull(activity, "Null activity is not permitted.");
        this.mActivity = new WeakReference<>(activity);
        init(activity, api, toption, abstractClientBuilder, i, null);
    }

    private void init(Context context, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder, int i, String str) {
        this.mContext = context.getApplicationContext();
        this.mOption = toption;
        this.mClientBuilder = abstractClientBuilder;
        setHostAppId(context);
        this.mSubAppInfo = new SubAppInfo("");
        this.mKitSdkVersion = i;
        if (!TextUtils.isEmpty(str)) {
            if (str.equals(this.mHostAppid)) {
                HMSLog.e(TAG, "subAppId is host appid");
            } else {
                HMSLog.i(TAG, "subAppId is " + str);
                this.mSubAppInfo = new SubAppInfo(str);
            }
        }
        initBI(context);
    }

    private void initBI(Context context) {
        HMSBIInitializer.getInstance(context).initBI();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void innerDisconnect(HuaweiApi<?> huaweiApi, TaskCompletionSource<Boolean> taskCompletionSource) {
        HMSLog.i(TAG, "innerDisconnect.");
        try {
            huaweiApi.getClient(RequestManager.getHandler().getLooper(), null).disconnect();
            taskCompletionSource.setResult(true);
        } catch (Exception e) {
            HMSLog.w(TAG, "disconnect the binder failed for:" + e.getMessage());
        }
    }

    private <TResult, TClient extends AnyClient> Task<TResult> sendRequest(TaskApiCall<TClient, TResult> taskApiCall) {
        TaskCompletionSource taskCompletionSource = taskApiCall.getToken() == null ? new TaskCompletionSource() : new TaskCompletionSource(taskApiCall.getToken());
        RequestManager.getHandler().post(new RequestRunnable(this, new TaskApiCallWrapper(taskApiCall, taskCompletionSource)));
        return taskCompletionSource.getTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHostAppId(Context context) {
        String appId = Util.getAppId(context);
        this.mHostAppid = appId;
        this.mAppID = appId;
    }

    @Deprecated
    public Task<Boolean> disconnectService() {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        RequestManager.getInstance();
        RequestManager.getHandler().post(new Runnable() { // from class: com.huawei.hms.common.HuaweiApi.1
            @Override // java.lang.Runnable
            public void run() {
                HuaweiApi.this.innerDisconnect(this, taskCompletionSource);
            }
        });
        return taskCompletionSource.getTask();
    }

    public <TResult, TClient extends AnyClient> Task<TResult> doWrite(TaskApiCall<TClient, TResult> taskApiCall) {
        this.isFirstReqSent = true;
        if (taskApiCall == null) {
            HMSLog.e(TAG, "in doWrite:taskApiCall is null");
            TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
            taskCompletionSource.setException(new ApiException(Status.FAILURE));
            return taskCompletionSource.getTask();
        }
        b.a(this.mContext, taskApiCall.getUri(), TextUtils.isEmpty(this.mSubAppInfo.getSubAppID()) ? this.mAppID : this.mSubAppInfo.getSubAppID(), taskApiCall.getTransactionId(), String.valueOf(getKitSdkVersion()));
        if (this.requestManager == null) {
            this.requestManager = RequestManager.getInstance();
        }
        return sendRequest(taskApiCall);
    }

    public Activity getActivity() {
        WeakReference<Activity> weakReference = this.mActivity;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public int getApiLevel() {
        return this.apiLevel;
    }

    public String getAppID() {
        return this.mAppID;
    }

    public AnyClient getClient(Looper looper, RequestHandler requestHandler) {
        return this.mClientBuilder.buildClient(this.mContext, getClientSetting(), requestHandler, requestHandler);
    }

    protected ClientSettings getClientSetting() {
        ClientSettings clientSettings = new ClientSettings(this.mContext.getPackageName(), this.mContext.getClass().getName(), getScopes(), this.mHostAppid, null, this.mSubAppInfo);
        if (!this.isUseInnerHms) {
            this.innerHmsPkg = HMSPackageManager.getInstance(this.mContext).getHMSPackageNameForMultiService();
            HMSLog.i(TAG, "No setInnerHms, hms pkg name is " + this.innerHmsPkg);
        }
        clientSettings.setInnerHmsPkg(this.innerHmsPkg);
        clientSettings.setUseInnerHms(this.isUseInnerHms);
        WeakReference<Activity> weakReference = this.mActivity;
        if (weakReference != null) {
            clientSettings.setCpActivity(weakReference.get());
        }
        return clientSettings;
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getKitSdkVersion() {
        return this.mKitSdkVersion;
    }

    public TOption getOption() {
        return this.mOption;
    }

    protected List<Scope> getScopes() {
        return Collections.emptyList();
    }

    public String getSubAppID() {
        return this.mSubAppInfo.getSubAppID();
    }

    public void setApiLevel(int i) {
        this.apiLevel = i;
    }

    public void setHostContext(Context context) {
        this.mHostContext = context;
    }

    public void setInnerHms() {
        this.innerHmsPkg = this.mContext.getPackageName();
        this.isUseInnerHms = true;
        HMSLog.i(TAG, "<setInnerHms> init inner hms pkg info:" + this.innerHmsPkg);
    }

    public void setKitSdkVersion(int i) {
        this.mKitSdkVersion = i;
    }

    public void setSubAppId(String str) throws ApiException {
        if (!setSubAppInfo(new SubAppInfo(str))) {
            throw new ApiException(Status.FAILURE);
        }
    }

    @Deprecated
    public boolean setSubAppInfo(SubAppInfo subAppInfo) {
        HMSLog.i(TAG, "Enter setSubAppInfo");
        SubAppInfo subAppInfo2 = this.mSubAppInfo;
        if (subAppInfo2 != null && !TextUtils.isEmpty(subAppInfo2.getSubAppID())) {
            HMSLog.e(TAG, "subAppInfo is already set");
            return false;
        }
        if (subAppInfo == null) {
            HMSLog.e(TAG, "subAppInfo is null");
            return false;
        }
        String subAppID = subAppInfo.getSubAppID();
        if (TextUtils.isEmpty(subAppID)) {
            HMSLog.e(TAG, "subAppId is empty");
            return false;
        }
        if (subAppID.equals(this.mHostAppid)) {
            HMSLog.e(TAG, "subAppId is host appid");
            return false;
        }
        if (this.isFirstReqSent) {
            HMSLog.e(TAG, "Client has sent request to Huawei Mobile Services, setting subAppId is not allowed");
            return false;
        }
        this.mSubAppInfo = new SubAppInfo(subAppInfo);
        return true;
    }

    public HuaweiApi(Activity activity, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder, int i, String str) {
        Checker.checkNonNull(activity, "Null activity is not permitted.");
        this.mActivity = new WeakReference<>(activity);
        init(activity, api, toption, abstractClientBuilder, i, str);
    }

    public HuaweiApi(Activity activity, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder) {
        Checker.checkNonNull(activity, "Null activity is not permitted.");
        this.mActivity = new WeakReference<>(activity);
        init(activity, api, toption, abstractClientBuilder, 0, null);
    }

    public HuaweiApi(Context context, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder, int i) {
        Checker.checkNonNull(context, "Null context is not permitted.");
        init(context, api, toption, abstractClientBuilder, i, null);
    }

    public HuaweiApi(Context context, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder, int i, String str) {
        Checker.checkNonNull(context, "Null context is not permitted.");
        init(context, api, toption, abstractClientBuilder, i, str);
    }

    public HuaweiApi(Context context, Api<TOption> api, TOption toption, AbstractClientBuilder abstractClientBuilder) {
        Checker.checkNonNull(context, "Null context is not permitted.");
        init(context, api, toption, abstractClientBuilder, 0, null);
    }
}
