package com.huawei.agconnect.applinking.a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.annotation.Singleton;
import com.huawei.agconnect.applinking.AGConnectAppLinking;
import com.huawei.agconnect.applinking.AppLinkingConfig;
import com.huawei.agconnect.applinking.AppLinkingException;
import com.huawei.agconnect.applinking.ReferrerProvider;
import com.huawei.agconnect.applinking.ResolvedLinkData;
import com.huawei.agconnect.applinking.a.c.m;
import com.huawei.agconnect.applinking.a.c.n;
import com.huawei.agconnect.common.api.BackendService;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.agconnect.common.api.SafeIntent;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hmf.tasks.Tasks;
import java.util.concurrent.Callable;
import org.json.JSONObject;

@Singleton
/* loaded from: classes2.dex */
public class a extends AGConnectAppLinking {
    private final com.huawei.agconnect.applinking.a.a.e a = new com.huawei.agconnect.applinking.a.a.e();
    private final b b = new b();
    private final com.huawei.agconnect.applinking.a.b.a c = new com.huawei.agconnect.applinking.a.b.a();
    private final AGConnectInstance d;

    public a(Context context, AGConnectInstance aGConnectInstance) {
        this.d = aGConnectInstance;
    }

    private Task<ResolvedLinkData> a(Activity activity) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.a.a(activity).addOnCompleteListener(new OnCompleteListener<String>() { // from class: com.huawei.agconnect.applinking.a.a.1
            @Override // com.huawei.hmf.tasks.OnCompleteListener
            public void onComplete(Task<String> task) {
                if (task.isSuccessful()) {
                    a.this.a(task.getResult(), true, (TaskCompletionSource<ResolvedLinkData>) taskCompletionSource);
                } else {
                    taskCompletionSource.setException(task.getException());
                }
            }
        });
        return taskCompletionSource.getTask();
    }

    private Task<ResolvedLinkData> a(Activity activity, Intent intent, final boolean z) {
        String string;
        final TaskCompletionSource<ResolvedLinkData> taskCompletionSource = new TaskCompletionSource<>();
        com.huawei.agconnect.applinking.a.a.c cVar = new com.huawei.agconnect.applinking.a.a.c();
        String dataString = intent.getDataString();
        if (dataString == null) {
            if (AppLinkingConfig.getInstance().isClipboardAlways()) {
                cVar.getCustomReferrer(activity).addOnSuccessListener(new OnSuccessListener<String>() { // from class: com.huawei.agconnect.applinking.a.a.3
                    @Override // com.huawei.hmf.tasks.OnSuccessListener
                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                    public void onSuccess(String str) {
                        String str2 = f.a(str).get("agc_click_id");
                        if (str2 == null) {
                            taskCompletionSource.setException(new AppLinkingException("not find refer from clipboard", 107));
                            return;
                        }
                        a.this.a(str2, z, (TaskCompletionSource<ResolvedLinkData>) taskCompletionSource);
                        Logger.d("AppLinkingSDK", "read referrer from clipboard : " + str2);
                    }
                }).addOnFailureListener(new OnFailureListener() { // from class: com.huawei.agconnect.applinking.a.a.2
                    @Override // com.huawei.hmf.tasks.OnFailureListener
                    public void onFailure(Exception exc) {
                        taskCompletionSource.setException(exc);
                    }
                });
            } else {
                taskCompletionSource.setException(new AppLinkingException("no intent data", 1));
            }
            return taskCompletionSource.getTask();
        }
        if (new Intent("android.intent.action.VIEW", Uri.parse(dataString)).resolveActivity(activity.getPackageManager()) == null) {
            taskCompletionSource.setException(new AppLinkingException("not from intent filter", 106));
        } else {
            String strA = f.a(dataString, "agc_click_id");
            if (strA != null) {
                a(strA, z, taskCompletionSource);
            } else {
                Bundle bundleExtra = SafeIntent.getBundleExtra(intent, "al_applink_data");
                if (bundleExtra != null && (string = SafeIntent.getString(bundleExtra, "target_url")) != null) {
                    Logger.d("AppLinkingSDK", "link form facebook");
                    b(string, z, taskCompletionSource);
                } else if ("https".equals(Uri.parse(dataString).getScheme())) {
                    b(dataString, z, taskCompletionSource);
                } else {
                    taskCompletionSource.setResult(d.a().a(ResolvedLinkData.LinkType.AppLinking).a(dataString).a(System.currentTimeMillis()).a());
                }
            }
        }
        return taskCompletionSource.getTask();
    }

    private Task<ResolvedLinkData> a(final Intent intent) {
        Logger.d("AppLinkingSDK", "From V1 extra data");
        return Tasks.callInBackground(new Callable<ResolvedLinkData>() { // from class: com.huawei.agconnect.applinking.a.a.4
            @Override // java.util.concurrent.Callable
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public ResolvedLinkData call() {
                String stringExtra = SafeIntent.getStringExtra(intent, "com.huawei.agc.links.AGC_LINK_DATA");
                if (stringExtra == null) {
                    return null;
                }
                JSONObject jSONObject = new JSONObject(stringExtra);
                String strOptString = jSONObject.optString("deepLink");
                return d.a().a(ResolvedLinkData.LinkType.AppLinking).a(strOptString).a(jSONObject.optLong("clickTimestamp", 0L)).a();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, boolean z, final TaskCompletionSource<ResolvedLinkData> taskCompletionSource) {
        final m mVar = new m(z);
        mVar.setClickId(str);
        if (z) {
            mVar.setFirstOpenSource(this.a.a());
        }
        BackendService.sendRequest(mVar, 1, n.class, new BackendService.Options.Builder().clientToken(true).app(this.d).build()).addOnCompleteListener(new OnCompleteListener<n>() { // from class: com.huawei.agconnect.applinking.a.a.5
            @Override // com.huawei.hmf.tasks.OnCompleteListener
            public void onComplete(Task<n> task) {
                if (!task.isSuccessful()) {
                    taskCompletionSource.setException(task.getException());
                    return;
                }
                n result = task.getResult();
                if (!result.isSuccess()) {
                    taskCompletionSource.setException(new AppLinkingException(result.getRetMsg(), result.getRetCode()));
                    return;
                }
                a.this.c.a(mVar, result);
                if (result.getLinkType() == null || !result.getLinkType().equals("UnifiedLinking") || result.getUnifiedLinkInfo() == null) {
                    taskCompletionSource.setResult(d.a().a(ResolvedLinkData.LinkType.AppLinking).a(result.getLinkInfoData().getFinalDeepLink()).a(result.getLinkInfoData().getSocialCardData()).a(result.getLinkInfoData().getCampaignInfoData()).a(result.getClickTime()).b(a.this.a.a()).a());
                } else {
                    taskCompletionSource.setResult(d.a().a(ResolvedLinkData.LinkType.UnifiedLinking).a(result.getUnifiedLinkInfo().getCampaignInfoData()).a(result.getClickTime()).b(a.this.a.a()).a());
                }
            }
        });
    }

    private void b(final String str, boolean z, final TaskCompletionSource<ResolvedLinkData> taskCompletionSource) {
        final m mVar = new m(z);
        mVar.setShortOrLongUrl(str);
        BackendService.sendRequest(mVar, 1, n.class, new BackendService.Options.Builder().clientToken(true).app(this.d).build()).addOnCompleteListener(new OnCompleteListener<n>() { // from class: com.huawei.agconnect.applinking.a.a.6
            @Override // com.huawei.hmf.tasks.OnCompleteListener
            public void onComplete(Task<n> task) {
                if (!task.isSuccessful()) {
                    taskCompletionSource.setException(task.getException());
                    return;
                }
                n result = task.getResult();
                a.this.c.a(mVar, result);
                if (result.isSuccess()) {
                    taskCompletionSource.setResult(d.a().a(ResolvedLinkData.LinkType.AppLinking).a(result.getLinkInfoData().getFinalDeepLink()).a(result.getLinkInfoData().getSocialCardData()).a(result.getLinkInfoData().getCampaignInfoData()).a(result.getClickTime()).a());
                } else {
                    if (result.getRetCode() != 204865576) {
                        taskCompletionSource.setException(new AppLinkingException(task.getResult().getRetMsg(), task.getResult().getRetCode()));
                        return;
                    }
                    Logger.i("AppLinkingSDK", "open request is prefix not found, return link url");
                    taskCompletionSource.setResult(d.a().a(ResolvedLinkData.LinkType.AppLinking).a(str).a(System.currentTimeMillis()).a());
                }
            }
        });
    }

    @Override // com.huawei.agconnect.applinking.AGConnectAppLinking
    public Task<ResolvedLinkData> getAppLinking(Activity activity) {
        return getAppLinking(activity, activity.getIntent());
    }

    @Override // com.huawei.agconnect.applinking.AGConnectAppLinking
    public Task<ResolvedLinkData> getAppLinking(Activity activity, Intent intent) {
        if (intent == null) {
            intent = activity.getIntent();
        }
        if (!TextUtils.isEmpty(SafeIntent.getStringExtra(intent, "com.huawei.agc.links.AGC_LINK_DATA"))) {
            return a(intent);
        }
        boolean zA = this.b.a();
        if (zA) {
            this.b.b();
            if (activity.getIntent().getDataString() == null) {
                return a(activity);
            }
        }
        return a(activity, intent, zA);
    }

    @Override // com.huawei.agconnect.applinking.AGConnectAppLinking
    public Task<ResolvedLinkData> getAppLinking(Activity activity, Uri uri) {
        Intent intent = new Intent();
        intent.setData(uri);
        return getAppLinking(activity, intent);
    }

    @Override // com.huawei.agconnect.applinking.AGConnectAppLinking
    public void setCustomReferrer(ReferrerProvider referrerProvider) {
        AppLinkingConfig.getInstance().getMarketsProvider().put("GP", referrerProvider);
    }
}
