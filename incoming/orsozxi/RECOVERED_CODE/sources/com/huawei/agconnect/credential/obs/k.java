package com.huawei.agconnect.credential.obs;

import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.common.api.BackendService;
import com.huawei.agconnect.common.api.BaseResponse;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.agconnect.core.service.auth.AuthProvider;
import com.huawei.agconnect.core.service.auth.CredentialsProvider;
import com.huawei.agconnect.core.service.auth.Token;
import com.huawei.agconnect.https.adapter.JsonAdapterFactory;
import com.huawei.hmf.tasks.Tasks;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/* loaded from: classes2.dex */
final class k implements Authenticator {
    private AGConnectInstance a;
    private BackendService.Options b;

    public k(BackendService.Options options) {
        this.a = options.getApp();
        this.b = options;
    }

    @Override // okhttp3.Authenticator
    public Request authenticate(Route route, Response response) throws IOException {
        Logger.i("AGCAuthenticator", "authenticate");
        BaseResponse baseResponse = (BaseResponse) new JsonAdapterFactory().responseBodyAdapter(BaseResponse.class).adapter(response.body());
        Request.Builder builderNewBuilder = response.request().newBuilder();
        boolean z = true;
        boolean z2 = false;
        if (baseResponse != null) {
            int code = baseResponse.getRet().getCode();
            if (code == 205524993 && !this.b.isClientTokenRefreshed()) {
                this.b.setClientTokenRefreshed(true);
                try {
                    String tokenString = ((Token) Tasks.await(((CredentialsProvider) this.a.getService(CredentialsProvider.class)).getTokens(true), 3L, TimeUnit.SECONDS)).getTokenString();
                    builderNewBuilder.removeHeader("Authorization");
                    builderNewBuilder.addHeader("Authorization", "Bearer " + tokenString);
                    z2 = true;
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    throw new IOException(e.getMessage());
                }
            }
            if (code != 205524994 || this.b.isAccessTokenRefreshed()) {
                z = z2;
            } else {
                if (((AuthProvider) this.a.getService(AuthProvider.class)) == null) {
                    throw new IOException("Please intergrate agconnect-auth in project");
                }
                this.b.setAccessTokenRefreshed(true);
                try {
                    Token token = (Token) Tasks.await(((AuthProvider) this.a.getService(AuthProvider.class)).getTokens(true), 3L, TimeUnit.SECONDS);
                    builderNewBuilder.removeHeader("access_token");
                    builderNewBuilder.addHeader("access_token", token.getTokenString());
                } catch (InterruptedException | ExecutionException | TimeoutException e2) {
                    throw new IOException(e2.getMessage());
                }
            }
        } else {
            z = false;
        }
        if (z) {
            return builderNewBuilder.build();
        }
        return null;
    }
}
