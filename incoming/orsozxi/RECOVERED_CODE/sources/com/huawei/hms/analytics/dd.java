package com.huawei.hms.analytics;

import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class dd {
    private static final List<String> klm;
    private static final String[] lmn;

    static {
        String[] strArr = {"_campaign_info"};
        lmn = strArr;
        klm = Collections.unmodifiableList(Arrays.asList(strArr));
    }

    public static List<JSONObject> klm(JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                arrayList.add(jSONArray.getJSONObject(i));
            } catch (JSONException unused) {
                HiLog.w("ConvertMethod", "json array item is not jsonObject");
            }
        }
        return arrayList;
    }

    public static Map<String, String> klm(Map<String, Object> map) {
        HashMap map2 = new HashMap();
        if (map != null && map.size() != 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                map2.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        return map2;
    }

    public static Bundle lmn(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return (Bundle) bundle.clone();
    }

    public static String lmn(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "";
        }
        try {
            return new JSONObject(str).getString(str2);
        } catch (JSONException unused) {
            HiLog.w("ConvertMethod", "JSONException: Failed to obtain the key from jsonStr.");
            return "";
        }
    }

    public static Map<String, String> lmn(JSONObject jSONObject) {
        String string;
        HashMap map = new HashMap();
        if (jSONObject != null && jSONObject.length() != 0) {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                if ("_restriction_shared".equals(next)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ar.lmn().lmn.def);
                    string = sb.toString();
                } else if (!dc.lmn(next, klm)) {
                    string = jSONObject.optString(next);
                }
                map.put(next, string);
            }
        }
        return map;
    }

    public static JSONArray lmn(List<JSONObject> list) {
        JSONArray jSONArray = new JSONArray();
        Iterator<JSONObject> it2 = list.iterator();
        while (it2.hasNext()) {
            jSONArray.put(it2.next());
        }
        return jSONArray;
    }

    public static JSONArray lmn(JSONArray jSONArray) {
        if (jSONArray == null) {
            return new JSONArray();
        }
        if (1000 >= jSONArray.length()) {
            return jSONArray;
        }
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < 1000; i++) {
            try {
                jSONArray2.put(jSONArray.get(i));
            } catch (JSONException unused) {
                HiLog.w("ConvertMethod", "truncate JSONArray error");
            }
        }
        return jSONArray2;
    }

    public static JSONObject lmn(Map<String, String> map) throws JSONException {
        if (map == null || map.size() == 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            try {
                jSONObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException unused) {
                HiLog.w("ConvertMethod", "map to json exception");
            }
        }
        return jSONObject;
    }
}
