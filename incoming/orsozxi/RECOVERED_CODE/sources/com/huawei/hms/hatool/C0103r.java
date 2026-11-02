package com.huawei.hms.hatool;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.huawei.hms.hatool.r, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0103r implements s {
    public List<q> a;
    public o b;
    public p c;
    public s d;
    public String e = "";
    public String f;

    public C0103r(String str) {
        this.f = str;
    }

    @Override // com.huawei.hms.hatool.s
    public JSONObject a() throws JSONException {
        String str;
        List<q> list = this.a;
        if (list == null || list.size() == 0) {
            str = "Not have actionEvent to send";
        } else if (this.b == null || this.c == null || this.d == null) {
            str = "model in wrong format";
        } else {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("header", this.b.a());
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObjectA = this.d.a();
            jSONObjectA.put("properties", this.c.a());
            try {
                jSONObjectA.put("events_global_properties", new JSONObject(this.e));
            } catch (JSONException unused) {
                jSONObjectA.put("events_global_properties", this.e);
            }
            jSONObject2.put("events_common", jSONObjectA);
            JSONArray jSONArray = new JSONArray();
            Iterator<q> it2 = this.a.iterator();
            while (it2.hasNext()) {
                JSONObject jSONObjectA2 = it2.next().a();
                if (jSONObjectA2 != null) {
                    jSONArray.put(jSONObjectA2);
                } else {
                    y.e("hmsSdk", "custom event is empty,delete this event");
                }
            }
            jSONObject2.put("events", jSONArray);
            try {
                String strA = d.a(t0.a(jSONObject2.toString().getBytes("UTF-8")), this.f);
                if (TextUtils.isEmpty(strA)) {
                    y.e("hmsSdk", "eventInfo encrypt failed,report over!");
                    return null;
                }
                jSONObject.put("event", strA);
                return jSONObject;
            } catch (UnsupportedEncodingException unused2) {
                str = "getBitZip(): Unsupported coding : utf-8";
            }
        }
        y.e("hmsSdk", str);
        return null;
    }

    public void a(e1 e1Var) {
        this.d = e1Var;
    }

    public void a(o oVar) {
        this.b = oVar;
    }

    public void a(p pVar) {
        this.c = pVar;
    }

    public void a(String str) {
        if (str != null) {
            this.e = str;
        }
    }

    public void a(List<q> list) {
        this.a = list;
    }
}
