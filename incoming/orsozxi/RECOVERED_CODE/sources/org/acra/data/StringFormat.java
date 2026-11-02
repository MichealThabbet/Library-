package org.acra.data;

import android.text.TextUtils;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.acra.ReportField;
import org.acra.collections.ImmutableSet;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/* loaded from: classes3.dex */
public enum StringFormat {
    JSON("application/json") { // from class: org.acra.data.StringFormat.1
        @Override // org.acra.data.StringFormat
        public String toFormattedString(CrashReportData crashReportData, ImmutableSet<ReportField> immutableSet, String str, String str2, boolean z) throws JSONException {
            Map<String, Object> map = crashReportData.toMap();
            JSONStringer jSONStringerObject = new JSONStringer().object();
            Iterator<ReportField> it2 = immutableSet.iterator();
            while (it2.hasNext()) {
                ReportField next = it2.next();
                jSONStringerObject.key(next.toString()).value(map.remove(next.toString()));
            }
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                jSONStringerObject.key(entry.getKey()).value(entry.getValue());
            }
            return jSONStringerObject.endObject().toString();
        }
    },
    KEY_VALUE_LIST("application/x-www-form-urlencoded") { // from class: org.acra.data.StringFormat.2
        @Override // org.acra.data.StringFormat
        public String toFormattedString(CrashReportData crashReportData, ImmutableSet<ReportField> immutableSet, String str, String str2, boolean z) throws UnsupportedEncodingException {
            Map<String, String> stringMap = toStringMap(crashReportData.toMap(), str2);
            StringBuilder sb = new StringBuilder();
            Iterator<ReportField> it2 = immutableSet.iterator();
            while (it2.hasNext()) {
                ReportField next = it2.next();
                append(sb, next.toString(), stringMap.remove(next.toString()), str, z);
            }
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                append(sb, entry.getKey(), entry.getValue(), str, z);
            }
            return sb.toString();
        }

        private void append(StringBuilder sb, String str, String str2, String str3, boolean z) throws UnsupportedEncodingException {
            if (sb.length() > 0) {
                sb.append(str3);
            }
            if (z) {
                str = str != null ? URLEncoder.encode(str, "UTF-8") : null;
                str2 = str2 != null ? URLEncoder.encode(str2, "UTF-8") : null;
            }
            sb.append(str);
            sb.append('=');
            sb.append(str2);
        }

        private Map<String, String> toStringMap(Map<String, Object> map, String str) {
            HashMap map2 = new HashMap(map.size());
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                map2.put(entry.getKey(), valueToString(str, entry.getValue()));
            }
            return map2;
        }

        private String valueToString(String str, Object obj) {
            if (obj instanceof JSONObject) {
                return TextUtils.join(str, flatten((JSONObject) obj));
            }
            return String.valueOf(obj);
        }

        private List<String> flatten(JSONObject jSONObject) throws JSONException {
            Object obj;
            ArrayList arrayList = new ArrayList();
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                try {
                    obj = jSONObject.get(next);
                } catch (JSONException unused) {
                    obj = null;
                }
                if (obj instanceof JSONObject) {
                    Iterator<String> it2 = flatten((JSONObject) obj).iterator();
                    while (it2.hasNext()) {
                        arrayList.add(next + "." + it2.next());
                    }
                } else {
                    arrayList.add(next + ContainerUtils.KEY_VALUE_DELIMITER + obj);
                }
            }
            return arrayList;
        }
    };

    private final String contentType;

    public abstract String toFormattedString(CrashReportData crashReportData, ImmutableSet<ReportField> immutableSet, String str, String str2, boolean z) throws Exception;

    StringFormat(String str) {
        this.contentType = str;
    }

    public String getMatchingHttpContentType() {
        return this.contentType;
    }
}
