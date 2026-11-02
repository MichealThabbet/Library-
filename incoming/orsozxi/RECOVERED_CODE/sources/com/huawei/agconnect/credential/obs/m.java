package com.huawei.agconnect.credential.obs;

import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.core.service.auth.AuthProvider;
import com.huawei.agconnect.core.service.auth.Token;
import com.huawei.hmf.tasks.Tasks;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import okhttp3.Interceptor;
import okhttp3.Response;

/* loaded from: classes2.dex */
final class m implements Interceptor {
    private AGConnectInstance a;
    private boolean b;

    public m(AGConnectInstance aGConnectInstance, boolean z) {
        this.a = aGConnectInstance;
        this.b = z;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        if (((AuthProvider) this.a.getService(AuthProvider.class)) == null) {
            if (this.b) {
                throw new IOException("Please intergrate agconnect-auth in project");
            }
            return chain.proceed(chain.request());
        }
        try {
            Token token = (Token) Tasks.await(((AuthProvider) this.a.getService(AuthProvider.class)).getTokens(), 5L, TimeUnit.SECONDS);
            if (token != null) {
                return chain.proceed(chain.request().newBuilder().addHeader("access_token", token.getTokenString()).build());
            }
            if (this.b) {
                throw new IOException("no user is signed");
            }
            return chain.proceed(chain.request());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new IOException(e.getMessage());
        }
    }
}
