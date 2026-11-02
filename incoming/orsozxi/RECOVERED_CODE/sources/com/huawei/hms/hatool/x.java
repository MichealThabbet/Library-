package com.huawei.hms.hatool;

import com.huawei.secure.android.common.encrypt.utils.EncryptUtil;

/* loaded from: classes2.dex */
public class x {
    public static x b = new x();
    public a a = new a();

    public class a {
        public String a;
        public String b;
        public long c = 0;

        public a() {
        }

        public void a(long j) {
            x.this.a.c = j;
        }

        public void a(String str) {
            x.this.a.b = str;
        }

        public void b(String str) {
            x.this.a.a = str;
        }
    }

    public static x d() {
        return b;
    }

    public String a() {
        return this.a.b;
    }

    public void a(String str, String str2) {
        long jB = b();
        String strB = r0.b(str, str2);
        if (strB == null || strB.isEmpty()) {
            y.e("WorkKeyHandler", "get rsa pubkey config error");
            return;
        }
        if (jB == 0) {
            jB = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - jB <= 43200000) {
            return;
        }
        String strGenerateSecureRandomStr = EncryptUtil.generateSecureRandomStr(16);
        String strA = e.a(strB, strGenerateSecureRandomStr);
        this.a.a(jB);
        this.a.b(strGenerateSecureRandomStr);
        this.a.a(strA);
    }

    public long b() {
        return this.a.c;
    }

    public String c() {
        return this.a.a;
    }
}
