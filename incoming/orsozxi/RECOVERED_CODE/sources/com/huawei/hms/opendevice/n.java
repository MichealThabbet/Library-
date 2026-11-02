package com.huawei.hms.opendevice;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import androidx.mediarouter.media.SystemMediaRouteProvider;
import com.facebook.places.model.PlaceFields;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.android.HwBuildEx;
import com.huawei.hms.android.SystemUtils;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.PackageManagerHelper;
import com.huawei.hms.utils.Util;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.util.TimeZone;
import java.util.UUID;
import net.fortuna.ical4j.model.property.RequestStatus;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ReportAaidToken.java */
/* loaded from: classes2.dex */
public class n {

    /* compiled from: ReportAaidToken.java */
    private enum a {
        MOBILE("1"),
        PC(RequestStatus.SUCCESS),
        TABLET(RequestStatus.CLIENT_ERROR),
        TV(RequestStatus.SCHEDULING_ERROR),
        SOUNDBOX("5"),
        GLASS("6"),
        WATCH("7"),
        VEHICLE("8"),
        OFFICE_DEVICE("9"),
        IOT_DEVICES(ConstantsBase.PREF_SNOOZE_DEFAULT),
        HEALTHY("11"),
        ENTERTAINMENT("12"),
        TRANSPORT_DEVICES("13");

        public String o;

        a(String str) {
            this.o = str;
        }

        public String a() {
            return this.o;
        }
    }

    /* compiled from: ReportAaidToken.java */
    private enum b {
        IOS("ios"),
        ANDROID(SystemMediaRouteProvider.PACKAGE_NAME),
        HARMONY("harmony"),
        WINDOWS("windows"),
        EMBED("embed"),
        OTHERS("others");

        public String h;

        b(String str) {
            this.h = str;
        }

        public String a() {
            return this.h;
        }
    }

    public static String c(Context context, String str, String str2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("timezone", TimeZone.getDefault().getID());
            jSONObject2.put("country", SystemUtils.getLocalCountry());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("agent_version", new PackageManagerHelper(context).getPackageVersionName("com.huawei.android.pushagent"));
            jSONObject3.put("hms_version", String.valueOf(Util.getHmsVersion(context)));
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("dev_type", a.MOBILE.a());
            jSONObject4.put("dev_sub_type", PlaceFields.PHONE);
            jSONObject4.put("os_type", b.ANDROID.a());
            jSONObject4.put("os_version", String.valueOf(HwBuildEx.VERSION.EMUI_SDK_INT));
            jSONObject.put("id", UUID.randomUUID().toString());
            jSONObject.put("global", jSONObject2);
            jSONObject.put("push_agent", jSONObject3);
            jSONObject.put("hardware", jSONObject4);
            jSONObject.put("aaid", str);
            jSONObject.put("token", str2);
            jSONObject.put("app_id", AGConnectServicesConfig.fromContext(context).getString("client/app_id"));
            jSONObject.put("region", AGConnectServicesConfig.fromContext(context).getString("region"));
            return jSONObject.toString();
        } catch (JSONException unused) {
            HMSLog.e("ReportAaidToken", "Catch JSONException.");
            return null;
        }
    }

    public static boolean d(Context context, String str, String str2) {
        i iVarA = i.a(context);
        if (!iVarA.containsKey("reportAaidAndToken")) {
            HMSLog.d("ReportAaidToken", "It hasn't been reported, this time needs report.");
            return true;
        }
        if (TextUtils.isEmpty(iVarA.getString("reportAaidAndToken"))) {
            HMSLog.e("ReportAaidToken", "It has been reported, but sp file is empty, this time needs report.");
            return true;
        }
        return !r4.equals(r.a(str2 + str, MessageDigestAlgorithms.SHA_256));
    }

    public static boolean b(Context context) throws PackageManager.NameNotFoundException {
        int packageVersionCode = new PackageManagerHelper(context).getPackageVersionCode("com.huawei.android.pushagent");
        HMSLog.d("ReportAaidToken", "NC version code: " + packageVersionCode);
        return (90101400 <= packageVersionCode && packageVersionCode < 100000000) || packageVersionCode >= 100001301;
    }

    public static void a(Context context, String str) {
        new m(context, str).start();
    }

    public static void b(Context context, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            HMSLog.e("ReportAaidToken", "Https response is empty.");
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            int iOptInt = jSONObject.optInt("ret", 256);
            if (iOptInt == 0) {
                boolean zSaveString = i.a(context).saveString("reportAaidAndToken", r.a(str3 + str2, MessageDigestAlgorithms.SHA_256));
                StringBuilder sb = new StringBuilder();
                sb.append("Report success ");
                sb.append(zSaveString ? "and save success." : "but save failure.");
                HMSLog.d("ReportAaidToken", sb.toString());
                return;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Https response body's ret code: ");
            sb2.append(iOptInt);
            sb2.append(", error message: ");
            sb2.append(jSONObject.optString("msg"));
            HMSLog.e("ReportAaidToken", sb2.toString());
        } catch (JSONException unused) {
            HMSLog.e("ReportAaidToken", "Has JSONException.");
        } catch (Exception unused2) {
            HMSLog.e("ReportAaidToken", "Exception occur.");
        }
    }
}
