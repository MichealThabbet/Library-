package com.huawei.hms.hatool;

/* loaded from: classes2.dex */
public abstract class a {
    public static String a(String str, String str2) {
        j jVarC = c(str, str2);
        return jVarC != null ? jVarC.a() : "";
    }

    public static boolean b(String str, String str2) {
        j jVarC = c(str, str2);
        return jVarC != null && jVarC.e();
    }

    public static j c(String str, String str2) {
        k kVarA;
        m mVarA = i.c().a(str);
        if (mVarA == null || (kVarA = mVarA.a(str2)) == null) {
            return null;
        }
        return kVarA.j();
    }

    public static String d(String str, String str2) {
        j jVarC = c(str, str2);
        return jVarC != null ? jVarC.b() : "";
    }

    public static boolean e(String str, String str2) {
        j jVarC = c(str, str2);
        return jVarC != null && jVarC.f();
    }

    public static boolean f(String str, String str2) {
        k kVarA;
        m mVarA = i.c().a(str);
        if (mVarA == null || (kVarA = mVarA.a(str2)) == null) {
            return false;
        }
        return kVarA.c();
    }

    public static String g(String str, String str2) {
        j jVarC = c(str, str2);
        return jVarC != null ? jVarC.d() : "";
    }

    public static boolean h(String str, String str2) {
        j jVarC = c(str, str2);
        return jVarC != null && jVarC.g();
    }

    public static boolean i(String str, String str2) {
        k kVarA;
        m mVarA = i.c().a(str);
        if (mVarA == null || (kVarA = mVarA.a(str2)) == null) {
            return false;
        }
        return kVarA.e();
    }

    public static String j(String str, String str2) {
        j jVarC = c(str, str2);
        return jVarC != null ? jVarC.c() : "";
    }

    public static boolean k(String str, String str2) {
        j jVarC = c(str, str2);
        return jVarC != null && jVarC.h();
    }
}
