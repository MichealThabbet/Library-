package com.huawei.agconnect.applinking.a.a;

import android.content.Context;
import com.huawei.agconnect.applinking.AppLinkingConfig;
import com.huawei.agconnect.applinking.AppLinkingException;
import com.huawei.agconnect.applinking.ReferrerProvider;
import com.huawei.agconnect.applinking.a.f;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hmf.tasks.Tasks;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class e {
    private final a a = new a();
    private final c b = new c();
    private String c;

    public Task<String> a(Context context) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        HashMap map = new HashMap();
        if (AppLinkingConfig.getInstance().isClipboardCheck()) {
            map.put("clipboard", this.b);
        }
        if (AppLinkingConfig.getInstance().isMarketCheck()) {
            map.put("AG", this.a);
            map.putAll(AppLinkingConfig.getInstance().getMarketsProvider());
        }
        final HashMap map2 = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            map2.put(entry.getKey(), a(context, (String) entry.getKey(), (ReferrerProvider) entry.getValue()));
        }
        Tasks.allOf((Collection<? extends Task<?>>) map2.values()).addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() { // from class: com.huawei.agconnect.applinking.a.a.e.1
            @Override // com.huawei.hmf.tasks.OnCompleteListener
            public void onComplete(Task<List<Task<?>>> task) {
                String str = null;
                String str2 = null;
                String str3 = null;
                for (Map.Entry entry2 : map2.entrySet()) {
                    String str4 = (String) entry2.getKey();
                    if (((Task) entry2.getValue()).isSuccessful()) {
                        String str5 = (String) ((Task) entry2.getValue()).getResult();
                        if ("clipboard".equals(str4)) {
                            str2 = str5;
                        } else {
                            str = str5;
                            str3 = str4;
                        }
                        Logger.d("AppLinkingSDK", "read referrer from " + str4 + " : " + str5);
                    } else {
                        Logger.w("AppLinkingSDK", "read referrer from " + str4 + " fail :" + ((Task) entry2.getValue()).getException());
                    }
                }
                if (str != null) {
                    e.this.c = str3;
                    taskCompletionSource.setResult(str);
                    return;
                }
                TaskCompletionSource taskCompletionSource2 = taskCompletionSource;
                if (str2 != null) {
                    taskCompletionSource2.setResult(str2);
                } else {
                    taskCompletionSource2.setException(new AppLinkingException("not find click id", 103));
                }
            }
        });
        return taskCompletionSource.getTask();
    }

    public Task<String> a(Context context, final String str, ReferrerProvider referrerProvider) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        if (referrerProvider != null) {
            referrerProvider.getCustomReferrer(context).addOnSuccessListener(new OnSuccessListener<String>() { // from class: com.huawei.agconnect.applinking.a.a.e.3
                @Override // com.huawei.hmf.tasks.OnSuccessListener
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onSuccess(String str2) {
                    String str3 = f.a(str2).get("agc_click_id");
                    if (str3 != null) {
                        taskCompletionSource.setResult(str3);
                        return;
                    }
                    taskCompletionSource.setException(new AppLinkingException("not find refer from " + str, 109));
                }
            }).addOnFailureListener(new OnFailureListener() { // from class: com.huawei.agconnect.applinking.a.a.e.2
                @Override // com.huawei.hmf.tasks.OnFailureListener
                public void onFailure(Exception exc) {
                    taskCompletionSource.setException(exc);
                }
            });
        }
        return taskCompletionSource.getTask();
    }

    public String a() {
        return this.c;
    }
}
