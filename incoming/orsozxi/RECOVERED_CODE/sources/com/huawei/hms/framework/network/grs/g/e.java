package com.huawei.hms.framework.network.grs.g;

import android.content.Context;
import com.facebook.internal.NativeProtocol;
import com.huawei.hms.framework.common.ExceptionCode;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.framework.common.hianalytics.HianalyticsHelper;
import com.huawei.hms.framework.common.hianalytics.LinkedHashMapPack;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class e {

    static class a implements Runnable {
        final /* synthetic */ long a;
        final /* synthetic */ ArrayList b;
        final /* synthetic */ JSONArray c;

        a(long j, ArrayList arrayList, JSONArray jSONArray) {
            this.a = j;
            this.b = arrayList;
            this.c = jSONArray;
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            com.huawei.hms.framework.network.grs.g.k.a aVar = new com.huawei.hms.framework.network.grs.g.k.a();
            aVar.put("total_time", this.a);
            Iterator it2 = this.b.iterator();
            while (it2.hasNext()) {
                d dVar = (d) it2.next();
                if (dVar.o() || dVar.m()) {
                    aVar.put(e.b(dVar));
                    it2.remove();
                    z = true;
                    break;
                }
            }
            z = false;
            if (!z && this.b.size() > 0) {
                ArrayList arrayList = this.b;
                d dVar2 = (d) arrayList.get(arrayList.size() - 1);
                aVar.put(e.b(dVar2));
                this.b.remove(dVar2);
            }
            if (this.b.size() > 0) {
                Iterator it3 = this.b.iterator();
                while (it3.hasNext()) {
                    this.c.put(new JSONObject(e.b((d) it3.next())));
                }
            }
            if (this.c.length() > 0) {
                aVar.put("failed_info", this.c.toString());
            }
            Logger.d("HaReportHelper", "grssdk report data to aiops is: %s", new JSONObject(aVar.get()));
            HianalyticsHelper.getInstance().onEvent(aVar.get(), "grs_request");
        }
    }

    public static void a(ArrayList<d> arrayList, long j, JSONArray jSONArray, Context context) {
        if (context == null || arrayList == null || arrayList.size() <= 0 || !HianalyticsHelper.getInstance().isEnableReportNoSeed(context)) {
            return;
        }
        HianalyticsHelper.getInstance().getReportExecutor().submit(new a(j, arrayList, jSONArray));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LinkedHashMap<String, String> b(d dVar) {
        LinkedHashMapPack linkedHashMapPack = new LinkedHashMapPack();
        Exception excD = dVar.d();
        if (excD != null) {
            linkedHashMapPack.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, ExceptionCode.getErrorCodeFromException(excD));
            linkedHashMapPack.put(CrashHianalyticsData.EXCEPTION_NAME, excD.getClass().getSimpleName());
            linkedHashMapPack.put("message", StringUtils.anonymizeMessage(excD.getMessage()));
        } else {
            linkedHashMapPack.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, dVar.b());
            linkedHashMapPack.put(CrashHianalyticsData.EXCEPTION_NAME, dVar.c());
        }
        try {
            linkedHashMapPack.put(ClientCookie.DOMAIN_ATTR, new URL(dVar.l()).getHost());
        } catch (MalformedURLException e) {
            Logger.w("HaReportHelper", "report host MalformedURLException", e);
        }
        linkedHashMapPack.put("req_start_time", dVar.h());
        linkedHashMapPack.put("req_end_time", dVar.g());
        linkedHashMapPack.put("req_total_time", dVar.i());
        return linkedHashMapPack.getAll();
    }
}
