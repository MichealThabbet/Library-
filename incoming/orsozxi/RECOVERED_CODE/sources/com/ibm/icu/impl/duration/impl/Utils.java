package com.ibm.icu.impl.duration.impl;

import com.ibm.icu.impl.locale.BaseLocale;
import java.util.Locale;

/* loaded from: classes2.dex */
public class Utils {
    public static final Locale localeFromString(String str) {
        String strSubstring;
        int iIndexOf = str.indexOf(BaseLocale.SEP);
        String strSubstring2 = "";
        if (iIndexOf != -1) {
            strSubstring = str.substring(iIndexOf + 1);
            str = str.substring(0, iIndexOf);
        } else {
            strSubstring = "";
        }
        int iIndexOf2 = strSubstring.indexOf(BaseLocale.SEP);
        if (iIndexOf2 != -1) {
            strSubstring2 = strSubstring.substring(iIndexOf2 + 1);
            strSubstring = strSubstring.substring(0, iIndexOf2);
        }
        return new Locale(str, strSubstring, strSubstring2);
    }

    /* JADX WARN: Removed duplicated region for block: B:93:0x013c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String chineseNumber(long r16, com.ibm.icu.impl.duration.impl.Utils.ChineseDigits r18) {
        /*
            Method dump skipped, instructions count: 376
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.impl.duration.impl.Utils.chineseNumber(long, com.ibm.icu.impl.duration.impl.Utils$ChineseDigits):java.lang.String");
    }

    public static class ChineseDigits {
        final char[] digits;
        final boolean ko;
        final char[] levels;
        final char liang;
        final char[] units;
        public static final ChineseDigits DEBUG = new ChineseDigits("0123456789s", "sbq", "WYZ", 'L', false);
        public static final ChineseDigits TRADITIONAL = new ChineseDigits("零一二三四五六七八九十", "十百千", "萬億兆", 20841, false);
        public static final ChineseDigits SIMPLIFIED = new ChineseDigits("零一二三四五六七八九十", "十百千", "万亿兆", 20004, false);
        public static final ChineseDigits KOREAN = new ChineseDigits("영일이삼사오육칠팔구십", "십백천", "만억?", 51060, true);

        ChineseDigits(String str, String str2, String str3, char c, boolean z) {
            this.digits = str.toCharArray();
            this.units = str2.toCharArray();
            this.levels = str3.toCharArray();
            this.liang = c;
            this.ko = z;
        }
    }
}
