package com.huawei.agconnect.credential.obs;

import android.text.TextUtils;
import com.huawei.agconnect.datastore.annotation.SharedPreference;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class r {
    private Boolean a;
    private String b;

    @SharedPreference(fileName = "agc_site", isDynamic = true, key = "backup")
    String backUrl;

    @SharedPreference(fileName = "agc_site", isDynamic = true, key = "main")
    String mainUrl;

    @SharedPreference(fileName = "agc_site", isDynamic = true, key = "validTime")
    long validTime;

    private r() {
        this.validTime = 0L;
        this.a = false;
        this.b = null;
    }

    public r(String str, String str2) {
        this.validTime = 0L;
        this.a = false;
        this.b = null;
        this.mainUrl = str;
        this.backUrl = str2;
        if (TextUtils.isEmpty(str)) {
            this.a = true;
        }
    }

    private int a(Object... objArr) {
        return Arrays.hashCode(objArr);
    }

    public static void a(String str, long j) {
        r rVar = new r();
        rVar.validTime = j;
        s.a().c(rVar, str);
    }

    private boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static r b(String str) {
        r rVar = new r();
        s.a().d(rVar, str);
        s.a().e(rVar, str);
        s.a().f(rVar, str);
        return rVar;
    }

    public static void c(String str) {
        s.a().a(str);
        s.a().b(str);
    }

    public String a() {
        return this.mainUrl;
    }

    public void a(String str) {
        if (!TextUtils.isEmpty(this.mainUrl)) {
            s.a().a(this, str);
        }
        if (TextUtils.isEmpty(this.backUrl)) {
            return;
        }
        s.a().b(this, str);
    }

    public void a(String str, boolean z) {
        this.b = str;
        this.a = Boolean.valueOf(z);
    }

    public String b() {
        return this.backUrl;
    }

    public String c() {
        return this.a.booleanValue() ? this.backUrl : this.mainUrl;
    }

    public String d() {
        return this.b;
    }

    public boolean e() {
        return (TextUtils.isEmpty(this.mainUrl) && TextUtils.isEmpty(this.backUrl)) ? false : true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        r rVar = (r) obj;
        return a(this.mainUrl, rVar.mainUrl) && a(this.backUrl, rVar.backUrl);
    }

    public int hashCode() {
        return a(this.mainUrl, this.backUrl);
    }
}
