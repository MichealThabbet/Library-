package com.huawei.agconnect.credential.obs;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.common.api.Logger;
import com.huawei.agconnect.core.service.EndpointService;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hmf.tasks.Tasks;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import okhttp3.Dns;

/* loaded from: classes2.dex */
public class ae implements EndpointService {
    private r a;
    private boolean b;

    static class a implements Callable<String> {
        String a;
        String b;

        a() {
        }

        static a a(String str, String str2) {
            a aVar = new a();
            aVar.a = str;
            aVar.b = str2;
            return aVar;
        }

        private boolean a(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            try {
                int iIndexOf = str.indexOf(":");
                Dns.SYSTEM.lookup(iIndexOf != -1 ? str.substring(0, iIndexOf) : str);
                return true;
            } catch (UnknownHostException unused) {
                Logger.e("EndpointServiceImpl", "checkUrl#UnknownHostException:" + str);
                return false;
            }
        }

        @Override // java.util.concurrent.Callable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public String call() throws IOException {
            if (a(this.a)) {
                return this.a;
            }
            if (a(this.b)) {
                return this.b;
            }
            throw new IOException("main/backup url both are invalid");
        }
    }

    public ae(Context context, AGConnectInstance aGConnectInstance) {
        this.b = false;
        this.a = new r(aGConnectInstance.getOptions().getString("agcgw/url"), aGConnectInstance.getOptions().getString("agcgw/backurl"));
        if (n.a().b().containsKey(this.a)) {
            this.a = n.a().b().get(this.a).a();
            this.b = n.a().b().get(this.a).b().booleanValue();
        }
    }

    @Override // com.huawei.agconnect.core.service.EndpointService
    public Task<String> getEndpointDomain(boolean z) {
        if (z || !this.b) {
            return Tasks.callInBackground(a.a(this.a.a(), this.a.b()));
        }
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        taskCompletionSource.setResult(this.a.c());
        return taskCompletionSource.getTask();
    }
}
