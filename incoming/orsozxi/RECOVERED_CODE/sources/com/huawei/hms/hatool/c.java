package com.huawei.hms.hatool;

import java.util.Map;

/* loaded from: classes2.dex */
public abstract class c {
    public static void a(String str, String str2, long j) {
        k kVarH = h(str, str2);
        if (kVarH != null) {
            kVarH.a(j);
        }
    }

    public static boolean a(String str, String str2) {
        k kVarH = h(str, str2);
        if (kVarH != null) {
            return kVarH.a();
        }
        return true;
    }

    public static int b(String str, String str2) {
        k kVarH = h(str, str2);
        if (kVarH != null) {
            return kVarH.d();
        }
        return 7;
    }

    public static boolean c(String str, String str2) {
        k kVarH = h(str, str2);
        if (kVarH != null) {
            return kVarH.g();
        }
        return true;
    }

    public static String d(String str, String str2) {
        k kVarH = h(str, str2);
        return kVarH != null ? kVarH.f() : "";
    }

    public static boolean e(String str, String str2) {
        k kVarH = h(str, str2);
        if (kVarH != null) {
            return kVarH.i();
        }
        return false;
    }

    public static String f(String str, String str2) {
        k kVarH = h(str, str2);
        return kVarH != null ? kVarH.h() : "";
    }

    public static String g(String str, String str2) {
        k kVarH = h(str, str2);
        return kVarH != null ? kVarH.n() : "";
    }

    public static k h(String str, String str2) {
        m mVarA = i.c().a(str);
        if (mVarA == null) {
            return null;
        }
        if (!"alltype".equals(str2)) {
            return mVarA.a(str2);
        }
        k kVarA = mVarA.a("oper");
        return kVarA == null ? mVarA.a("maint") : kVarA;
    }

    public static Map<String, String> i(String str, String str2) {
        k kVarH = h(str, str2);
        if (kVarH != null) {
            return kVarH.k();
        }
        return null;
    }

    public static long j(String str, String str2) {
        k kVarH = h(str, str2);
        if (kVarH != null) {
            return kVarH.l();
        }
        return 0L;
    }

    public static int k(String str, String str2) {
        k kVarH = h(str, str2);
        if (kVarH != null) {
            return kVarH.b();
        }
        return 10;
    }

    public static String l(String str, String str2) {
        k kVarH = h(str, str2);
        return kVarH != null ? kVarH.o() : "";
    }

    public static String m(String str, String str2) {
        k kVarH = h(str, str2);
        return kVarH != null ? kVarH.q() : "";
    }

    public static String n(String str, String str2) {
        k kVarH = h(str, str2);
        return kVarH != null ? kVarH.m() : "";
    }

    public static String o(String str, String str2) {
        k kVarH = h(str, str2);
        return kVarH != null ? kVarH.p() : "";
    }
}
