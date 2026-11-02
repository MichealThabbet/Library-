package com.huawei.hms.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.analytics.av;
import com.huawei.hms.analytics.core.log.HiLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class ca {
    private static void ikl(av avVar, JSONObject jSONObject) throws JSONException {
        try {
            if (!jSONObject.has("disable_evts")) {
                HiLog.d("onlineApi", "Not has disabled events");
                return;
            }
            JSONArray jSONArray = jSONObject.getJSONArray("disable_evts");
            av.lmn lmnVar = new av.lmn();
            int length = jSONArray.length();
            if (length == 0) {
                return;
            }
            if (length > 1000) {
                HiLog.w("onlineApi", "Get disabled events size is " + length + ". Exceeds limit.");
                jSONArray = dd.lmn(jSONArray);
                jSONObject.put("disable_evts", jSONArray);
                length = 1000;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < length; i++) {
                arrayList.add(jSONArray.getString(i));
            }
            if (arrayList.size() > 0) {
                lmnVar.lmn = arrayList;
                avVar.hij = lmnVar;
            }
        } catch (JSONException unused) {
            HiLog.w("onlineApi", "disabled evts is not json");
        }
    }

    public static void klm(Context context, String str, long j) {
        if (lmn(context, j)) {
            lmn(context, str, j);
        }
    }

    private static void klm(av avVar, JSONObject jSONObject) throws JSONException {
        try {
            if (!jSONObject.has("online_events")) {
                HiLog.d("onlineApi", "Not has online events");
                return;
            }
            JSONArray jSONArray = jSONObject.getJSONArray("online_events");
            int length = jSONArray.length();
            if (length == 0) {
                return;
            }
            if (length > 1000) {
                HiLog.w("onlineApi", "Get online events size is " + length + ". Exceeds limit.");
                jSONArray = dd.lmn(jSONArray);
                jSONObject.put("online_events", jSONArray);
                length = 1000;
            }
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                av.klm klmVar = new av.klm();
                klmVar.lmn = jSONObject2.getString("event_id");
                JSONArray jSONArray2 = jSONObject2.getJSONArray("time_ranges");
                if (jSONArray2.length() != 0) {
                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                        JSONObject jSONObject3 = jSONArray2.getJSONObject(i2);
                        av.ikl iklVar = new av.ikl();
                        iklVar.lmn = Long.valueOf(jSONObject3.optLong("start"));
                        iklVar.klm = Long.valueOf(jSONObject3.optLong("end"));
                        if (klmVar.klm == null) {
                            klmVar.klm = new ArrayList();
                        }
                        klmVar.klm.add(iklVar);
                    }
                    if (avVar.klm == null) {
                        avVar.klm = new ArrayList();
                    }
                    avVar.klm.add(klmVar);
                }
            }
            if (avVar.klm != null) {
                avVar.ghi = true;
            }
        } catch (JSONException unused) {
            HiLog.w("onlineApi", "instance events json exception");
        }
    }

    public static synchronized av lmn(Context context) {
        String strKlm = dl.klm(context, "global_v2", "instance_event_info", "");
        if (TextUtils.isEmpty(strKlm)) {
            return null;
        }
        return lmn(context, strKlm);
    }

    public static av lmn(Context context, String str) {
        av avVar = new av();
        try {
            JSONObject jSONObject = new JSONObject(str);
            avVar.lmn = Math.max(1800L, jSONObject.getLong("query_interval")) * 1000;
            klm(avVar, jSONObject);
            ikl(avVar, jSONObject);
            if (!lmn(avVar, jSONObject)) {
                dl.lmn(context, "global_v2", "instance_event_info", jSONObject.toString());
            }
            return avVar;
        } catch (JSONException unused) {
            return null;
        }
    }

    public static synchronized void lmn(Context context, String str, long j) {
        cx.ikl().lmn(new ci(str));
        dl.lmn(context, "latest_upload_time", "instance_event_trigger_time", j);
    }

    private static boolean lmn(Context context, long j) {
        return j - dl.klm(context, "latest_upload_time", "instance_event_trigger_time") > 1800000;
    }

    private static boolean lmn(av avVar, JSONObject jSONObject) throws JSONException {
        if (jSONObject.has("timestamp") && jSONObject.has("region")) {
            avVar.ikl = Long.valueOf(jSONObject.optLong("timestamp"));
            avVar.ijk = jSONObject.optString("region");
            return true;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        String strLmn = ar.lmn().lmn.n.lmn("_openness_config_tag");
        try {
            jSONObject.put("timestamp", jCurrentTimeMillis);
            jSONObject.put("region", strLmn);
        } catch (JSONException unused) {
            HiLog.w("onlineApi", "json put exception");
        }
        avVar.ijk = strLmn;
        avVar.ikl = Long.valueOf(jCurrentTimeMillis);
        return false;
    }
}
