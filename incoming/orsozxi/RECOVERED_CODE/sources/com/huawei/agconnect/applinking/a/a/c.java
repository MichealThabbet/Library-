package com.huawei.agconnect.applinking.a.a;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import com.huawei.agconnect.applinking.AppLinkingConfig;
import com.huawei.agconnect.applinking.AppLinkingException;
import com.huawei.agconnect.applinking.ReferrerProvider;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class c implements ReferrerProvider {
    private int a;
    private a b;

    private class a implements OnCompleteListener<String> {
        private final TaskCompletionSource<String> b;
        private final WeakReference<Context> c;

        public a(Context context, TaskCompletionSource<String> taskCompletionSource) {
            this.c = new WeakReference<>(context);
            this.b = taskCompletionSource;
        }

        @Override // com.huawei.hmf.tasks.OnCompleteListener
        public void onComplete(Task<String> task) {
            String result = task.getResult();
            if (!TextUtils.isEmpty(result)) {
                this.b.setResult(result);
            } else if (c.this.a >= AppLinkingConfig.getInstance().getClipboardRetryTimes()) {
                this.b.setException(new AppLinkingException("clipboard empty", 105));
            } else {
                c.this.a(this.c.get(), AppLinkingConfig.getInstance().getClipboardRetryDelayMillis()).addOnCompleteListener(c.this.b);
                c.c(c.this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Task<String> a(final Context context, long j) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        new Handler().postDelayed(new Runnable() { // from class: com.huawei.agconnect.applinking.a.a.c.1
            @Override // java.lang.Runnable
            public void run() {
                taskCompletionSource.setResult(c.this.a(context));
            }
        }, j);
        return taskCompletionSource.getTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(Context context) {
        ClipboardManager clipboardManager;
        ClipData primaryClip;
        if (context != null && (clipboardManager = (ClipboardManager) context.getApplicationContext().getSystemService("clipboard")) != null && clipboardManager.hasPrimaryClip() && (primaryClip = clipboardManager.getPrimaryClip()) != null && primaryClip.getItemCount() > 0) {
            String strValueOf = String.valueOf(primaryClip.getItemAt(0).getText());
            if (!TextUtils.isEmpty(strValueOf)) {
                return strValueOf;
            }
        }
        return null;
    }

    static /* synthetic */ int c(c cVar) {
        int i = cVar.a;
        cVar.a = i + 1;
        return i;
    }

    @Override // com.huawei.agconnect.applinking.ReferrerProvider
    public Task<String> getCustomReferrer(Context context) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.a = 0;
        this.b = new a(context, taskCompletionSource);
        a(context, 0L).addOnCompleteListener(this.b);
        return taskCompletionSource.getTask();
    }
}
