package com.huawei.agconnect.credential.obs;

import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.core.service.auth.CredentialsProvider;
import com.huawei.agconnect.core.service.auth.Token;
import com.huawei.hmf.tasks.Tasks;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import okhttp3.Interceptor;
import okhttp3.Response;

/* loaded from: classes2.dex */
final class q implements Interceptor {
    private AGConnectInstance a;

    public q(AGConnectInstance aGConnectInstance) {
        this.a = aGConnectInstance;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        try {
            Token token = (Token) Tasks.await(((CredentialsProvider) this.a.getService(CredentialsProvider.class)).getTokens(), 5L, TimeUnit.SECONDS);
            return chain.proceed(chain.request().newBuilder().addHeader("Authorization", "Bearer " + token.getTokenString()).build());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new IOException(e.getMessage());
        }
    }
}
