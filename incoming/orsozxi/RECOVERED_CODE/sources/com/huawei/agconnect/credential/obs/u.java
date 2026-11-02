package com.huawei.agconnect.credential.obs;

import android.text.TextUtils;
import com.huawei.agconnect.common.api.BackendService;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.agconnect.credential.Server;
import it.piapostolos.android.pinotes.R2;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes2.dex */
final class u implements Interceptor {
    private List<r> a;

    public u(BackendService.Options options) {
        this.a = l.a().a(options.getApp());
    }

    public u(String str, String str2) {
        this.a = Collections.singletonList(new r(str, str2));
    }

    private Response a(Interceptor.Chain chain, String str) throws NumberFormatException, IOException {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Request request = chain.request();
        String[] strArrSplit = str.split(":");
        int i = R2.attr.fabAnimationMode;
        if (strArrSplit.length == 2) {
            try {
                i = Integer.parseInt(strArrSplit[1]);
                str2 = strArrSplit[0];
            } catch (NumberFormatException unused) {
                Logger.e("UrlInterceptorV2", "port is error:" + i + ", use default 443");
            }
        } else {
            str2 = str;
        }
        try {
            return chain.proceed(request.newBuilder().url(request.url().newBuilder().scheme("https").host(str2).port(i).build()).build());
        } catch (IOException e) {
            if (!(e instanceof UnknownHostException)) {
                throw e;
            }
            Logger.e("UrlInterceptorV2", "UnknownHostException" + str);
            return null;
        }
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws NumberFormatException, IOException {
        Request request = chain.request();
        l.a().a(request.header("sdkServiceName"));
        if (!Server.GW.equals(request.url().scheme() + "://" + request.url().host()) || this.a.isEmpty()) {
            return chain.proceed(request);
        }
        UnknownHostException unknownHostException = null;
        UnknownHostException unknownHostException2 = null;
        Response responseA = null;
        int i = 0;
        while (true) {
            if (i >= this.a.size()) {
                unknownHostException = unknownHostException2;
                break;
            }
            r rVar = this.a.get(i);
            if (!TextUtils.isEmpty(rVar.d())) {
                return a(chain, rVar.c());
            }
            String strA = rVar.a();
            String strB = rVar.b();
            Response responseA2 = a(chain, strA);
            if (responseA2 == null) {
                responseA = a(chain, strB);
                if (responseA != null) {
                    rVar.a(strB, true);
                    break;
                }
                unknownHostException2 = new UnknownHostException("no host can access");
                i++;
            } else {
                rVar.a(strA, false);
                responseA = responseA2;
                break;
            }
        }
        if (unknownHostException == null) {
            return responseA;
        }
        throw unknownHostException;
    }
}
