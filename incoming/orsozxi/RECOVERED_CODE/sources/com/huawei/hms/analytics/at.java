package com.huawei.hms.analytics;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class at {
    public k a;
    public boolean abc;
    public def b;
    public String cde;
    public Context ghi;
    public String h;
    public String hij;
    public String i;
    public String ijk;
    public String ikl;
    public String j;
    public boolean k;
    public String klm;
    public long l;
    public String lmn;
    public boolean m;
    public ay n;
    public String o;
    public boolean p;
    public av q;
    public long v;
    public boolean w;
    public boolean fgh = true;
    public boolean efg = false;
    public boolean def = false;
    public String bcd = "";
    public long c = 30;
    public String d = "";
    public String e = "";
    public Map<String, String> f = new HashMap();
    private Map<String, String> x = new HashMap();
    public String g = "";
    public String r = "";
    public String s = "";
    public String t = "";
    public String u = "";

    public final void klm(String str, String str2) {
        this.x.put(str, str2);
    }

    public final String lmn() {
        return TextUtils.isEmpty(this.u) ? "" : this.u;
    }

    public final String lmn(String str) {
        return this.x.get(str);
    }

    public final void lmn(String str, String str2) {
        this.f.put(str, str2);
    }
}
