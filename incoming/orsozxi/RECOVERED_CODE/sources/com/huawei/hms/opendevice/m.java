package com.huawei.hms.opendevice;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.support.log.HMSLog;
import java.util.Map;
import org.slf4j.Logger;

/* compiled from: ReportAaidToken.java */
/* loaded from: classes2.dex */
class m extends Thread {
    public final /* synthetic */ Context a;
    public final /* synthetic */ String b;

    public m(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws Throwable {
        if (!p.b()) {
            HMSLog.d("ReportAaidToken", "Not HW Phone.");
            return;
        }
        if (n.b(this.a)) {
            return;
        }
        String strA = o.a(this.a);
        if (TextUtils.isEmpty(strA)) {
            HMSLog.w("ReportAaidToken", "AAID is empty.");
            return;
        }
        if (!n.d(this.a, strA, this.b)) {
            HMSLog.d("ReportAaidToken", "This time need not report.");
            return;
        }
        String string = AGConnectServicesConfig.fromContext(this.a).getString("region");
        if (TextUtils.isEmpty(string)) {
            HMSLog.i("ReportAaidToken", "The data storage region is empty.");
            return;
        }
        String strA2 = e.a(this.a, "com.huawei.hms.opendevicesdk", Logger.ROOT_LOGGER_NAME, null, string);
        if (TextUtils.isEmpty(strA2)) {
            return;
        }
        String strC = n.c(this.a, strA, this.b);
        n.b(this.a, d.a(this.a, strA2 + "/rest/appdata/v1/aaid/report", strC, (Map<String, String>) null), strA, this.b);
    }
}
