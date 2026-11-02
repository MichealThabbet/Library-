package com.huawei.agconnect.credential.obs;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.annotation.Singleton;
import com.huawei.agconnect.common.api.BackendService;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.agconnect.core.service.auth.CredentialsProvider;
import com.huawei.agconnect.core.service.auth.Token;
import com.huawei.agconnect.exception.AGCServerException;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hmf.tasks.TaskExecutors;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Singleton
/* loaded from: classes2.dex */
public class ac implements CredentialsProvider {
    private final Executor a;
    private final String b;
    private aa c = new aa();
    private long d;
    private final AGConnectInstance e;

    public ac(Context context, AGConnectInstance aGConnectInstance) {
        this.e = aGConnectInstance;
        this.b = aGConnectInstance.getOptions().getIdentifier();
        ab.a().d(this.c, this.b);
        ab.a().e(this.c, this.b);
        ab.a().f(this.c, this.b);
        this.a = Executors.newSingleThreadExecutor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final TaskCompletionSource<Token> taskCompletionSource) {
        IllegalArgumentException illegalArgumentException;
        x xVar = new x(this.e);
        if (TextUtils.isEmpty(xVar.getClientId())) {
            illegalArgumentException = new IllegalArgumentException("client token request miss client id, please check whether the 'agconnect-services.json' is configured correctly");
        } else {
            if (!TextUtils.isEmpty(xVar.getClientSecret())) {
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                BackendService.sendRequest(xVar, 1, y.class, new BackendService.Options.Builder().app(this.e).clientToken(false).build()).addOnCompleteListener(TaskExecutors.immediate(), new OnCompleteListener<y>() { // from class: com.huawei.agconnect.credential.obs.ac.2
                    @Override // com.huawei.hmf.tasks.OnCompleteListener
                    public void onComplete(Task<y> task) {
                        Logger.i("CredentialsProviderImpl", "onComplete");
                        if (!task.isSuccessful()) {
                            taskCompletionSource.setException(task.getException());
                            countDownLatch.countDown();
                            return;
                        }
                        y result = task.getResult();
                        if (result.getRet() != null && result.getRet().getCode() != 0) {
                            taskCompletionSource.setException(new AGCServerException(result.getRet().getMsg(), result.getRet().getCode()));
                            countDownLatch.countDown();
                            return;
                        }
                        ac.this.c = new aa(result.getAccessToken(), result.getExpiresIn());
                        ab.a().a(ac.this.c, ac.this.b);
                        ab.a().b(ac.this.c, ac.this.b);
                        ab.a().c(ac.this.c, ac.this.b);
                        countDownLatch.countDown();
                        ac.this.d = SystemClock.elapsedRealtime();
                        taskCompletionSource.setResult(ac.this.c);
                    }
                });
                try {
                    if (countDownLatch.await(5L, TimeUnit.SECONDS)) {
                        return;
                    }
                    Logger.e("CredentialsProviderImpl", "await failed");
                    return;
                } catch (InterruptedException unused) {
                    Logger.e("CredentialsProviderImpl", "await InterruptedException");
                    return;
                }
            }
            illegalArgumentException = new IllegalArgumentException("client token request miss client secret, please check whether the 'agconnect-services.json' is configured correctly");
        }
        taskCompletionSource.setException(illegalArgumentException);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(boolean z) {
        aa aaVar = this.c;
        if (aaVar == null || !aaVar.a()) {
            return true;
        }
        return z && (this.d == 0 || SystemClock.elapsedRealtime() - this.d > 3600000);
    }

    @Override // com.huawei.agconnect.core.service.auth.CredentialsProvider
    public Task<Token> getTokens() {
        return getTokens(false);
    }

    @Override // com.huawei.agconnect.core.service.auth.CredentialsProvider
    public Task<Token> getTokens(final boolean z) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        if (a(z)) {
            this.a.execute(new Runnable() { // from class: com.huawei.agconnect.credential.obs.ac.1
                @Override // java.lang.Runnable
                public void run() {
                    if (ac.this.a(z)) {
                        ac.this.a((TaskCompletionSource<Token>) taskCompletionSource);
                    } else {
                        taskCompletionSource.setResult(ac.this.c);
                    }
                }
            });
        } else {
            taskCompletionSource.setResult(this.c);
        }
        return taskCompletionSource.getTask();
    }
}
