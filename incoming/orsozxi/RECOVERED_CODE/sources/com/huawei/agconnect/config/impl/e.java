package com.huawei.agconnect.config.impl;

import android.text.TextUtils;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.SecretKey;

/* loaded from: classes2.dex */
public class e implements g {
    private final d a;
    private SecretKey b;

    e(d dVar) {
        this.a = dVar;
        a();
    }

    private SecretKey a() {
        try {
            String strA = this.a.a("/code/code1", null);
            String strA2 = this.a.a("/code/code2", null);
            String strA3 = this.a.a("/code/code3", null);
            String strA4 = this.a.a("/code/code4", null);
            if (strA != null && strA2 != null && strA3 != null && strA4 != null) {
                this.b = i.a(Hex.decodeHexString(strA), Hex.decodeHexString(strA2), Hex.decodeHexString(strA3), Hex.decodeHexString(strA4), 10000);
            }
        } catch (IllegalArgumentException | NoSuchAlgorithmException | InvalidKeySpecException unused) {
            Log.e("ExclamationMark", "Exception when reading the 'K&I' for 'Config'.");
            this.b = null;
        }
        return this.b;
    }

    public static boolean a(String str) {
        return !TextUtils.isEmpty(str) && Pattern.matches("^\\[!([A-Fa-f0-9]*)]", str);
    }

    static String b(String str) {
        try {
            Matcher matcher = Pattern.compile("^\\[!([A-Fa-f0-9]*)]").matcher(str);
            return matcher.find() ? matcher.group(1) : "";
        } catch (IllegalStateException | IndexOutOfBoundsException unused) {
            Log.e("ExclamationMark", "getRawString exception");
            return "";
        }
    }

    @Override // com.huawei.agconnect.config.impl.g
    public String a(String str, String str2) {
        String str3;
        if (this.b == null) {
            str3 = "mKey is null, return default value";
        } else {
            if (!a(str)) {
                return str2;
            }
            try {
                return new String(i.a(this.b, Hex.decodeHexString(b(str))), "UTF-8");
            } catch (UnsupportedEncodingException | IllegalArgumentException | GeneralSecurityException unused) {
                str3 = "UnsupportedEncodingException||GeneralSecurityException||IllegalArgumentException";
            }
        }
        Log.e("ExclamationMark", str3);
        return str2;
    }
}
