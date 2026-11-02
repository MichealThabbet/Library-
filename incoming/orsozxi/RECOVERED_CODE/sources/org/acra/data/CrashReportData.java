package org.acra.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.collections.ImmutableSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class CrashReportData {
    private final JSONObject content;

    public CrashReportData() {
        this.content = new JSONObject();
    }

    public CrashReportData(String str) throws JSONException {
        this.content = new JSONObject(str);
    }

    public synchronized void put(String str, boolean z) {
        try {
            this.content.put(str, z);
        } catch (JSONException unused) {
            ACRA.log.w(ACRA.LOG_TAG, "Failed to put value into CrashReportData: " + String.valueOf(z));
        }
    }

    public synchronized void put(String str, double d) {
        try {
            this.content.put(str, d);
        } catch (JSONException unused) {
            ACRA.log.w(ACRA.LOG_TAG, "Failed to put value into CrashReportData: " + String.valueOf(d));
        }
    }

    public synchronized void put(String str, int i) {
        try {
            this.content.put(str, i);
        } catch (JSONException unused) {
            ACRA.log.w(ACRA.LOG_TAG, "Failed to put value into CrashReportData: " + String.valueOf(i));
        }
    }

    public synchronized void put(String str, long j) {
        try {
            this.content.put(str, j);
        } catch (JSONException unused) {
            ACRA.log.w(ACRA.LOG_TAG, "Failed to put value into CrashReportData: " + String.valueOf(j));
        }
    }

    public synchronized void put(String str, String str2) {
        if (str2 == null) {
            putNA(str);
            return;
        }
        try {
            this.content.put(str, str2);
        } catch (JSONException unused) {
            ACRA.log.w(ACRA.LOG_TAG, "Failed to put value into CrashReportData: " + str2);
        }
    }

    public synchronized void put(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            putNA(str);
            return;
        }
        try {
            this.content.put(str, jSONObject);
        } catch (JSONException unused) {
            ACRA.log.w(ACRA.LOG_TAG, "Failed to put value into CrashReportData: " + String.valueOf(jSONObject));
        }
    }

    public synchronized void put(String str, JSONArray jSONArray) {
        if (jSONArray == null) {
            putNA(str);
            return;
        }
        try {
            this.content.put(str, jSONArray);
        } catch (JSONException unused) {
            ACRA.log.w(ACRA.LOG_TAG, "Failed to put value into CrashReportData: " + String.valueOf(jSONArray));
        }
    }

    public synchronized void put(ReportField reportField, boolean z) {
        put(reportField.toString(), z);
    }

    public synchronized void put(ReportField reportField, double d) {
        put(reportField.toString(), d);
    }

    public synchronized void put(ReportField reportField, int i) {
        put(reportField.toString(), i);
    }

    public synchronized void put(ReportField reportField, long j) {
        put(reportField.toString(), j);
    }

    public synchronized void put(ReportField reportField, String str) {
        put(reportField.toString(), str);
    }

    public synchronized void put(ReportField reportField, JSONObject jSONObject) {
        put(reportField.toString(), jSONObject);
    }

    public synchronized void put(ReportField reportField, JSONArray jSONArray) {
        put(reportField.toString(), jSONArray);
    }

    private void putNA(String str) throws JSONException {
        try {
            this.content.put(str, ACRAConstants.NOT_AVAILABLE);
        } catch (JSONException unused) {
        }
    }

    public String getString(ReportField reportField) {
        return this.content.optString(reportField.toString());
    }

    public Object get(String str) {
        return this.content.opt(str);
    }

    public boolean containsKey(String str) {
        return this.content.has(str);
    }

    public boolean containsKey(ReportField reportField) {
        return containsKey(reportField.toString());
    }

    public String toJSON() throws JSONException {
        try {
            return StringFormat.JSON.toFormattedString(this, ImmutableSet.empty(), "", "", false);
        } catch (JSONException e) {
            throw e;
        } catch (Exception e2) {
            throw new JSONException(e2.getMessage());
        }
    }

    public Map<String, Object> toMap() {
        HashMap map = new HashMap(this.content.length());
        Iterator<String> itKeys = this.content.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            map.put(next, get(next));
        }
        return map;
    }
}
