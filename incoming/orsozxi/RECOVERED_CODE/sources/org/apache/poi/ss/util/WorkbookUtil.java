package org.apache.poi.ss.util;

/* loaded from: classes3.dex */
public class WorkbookUtil {
    /* JADX WARN: Removed duplicated region for block: B:21:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.String createSafeSheetName(java.lang.String r5) {
        /*
            if (r5 != 0) goto L5
            java.lang.String r5 = "null"
            return r5
        L5:
            int r0 = r5.length()
            r1 = 1
            if (r0 >= r1) goto Lf
            java.lang.String r5 = "empty"
            return r5
        Lf:
            r0 = 31
            int r1 = r5.length()
            int r0 = java.lang.Math.min(r0, r1)
            r1 = 0
            java.lang.String r5 = r5.substring(r1, r0)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r5)
        L23:
            if (r1 >= r0) goto L4f
            char r5 = r2.charAt(r1)
            r3 = 39
            r4 = 32
            if (r5 == r3) goto L43
            r3 = 42
            if (r5 == r3) goto L3f
            r3 = 47
            if (r5 == r3) goto L3f
            r3 = 63
            if (r5 == r3) goto L3f
            switch(r5) {
                case 91: goto L3f;
                case 92: goto L3f;
                case 93: goto L3f;
                default: goto L3e;
            }
        L3e:
            goto L4c
        L3f:
            r2.setCharAt(r1, r4)
            goto L4c
        L43:
            if (r1 == 0) goto L49
            int r5 = r0 + (-1)
            if (r1 != r5) goto L4c
        L49:
            r2.setCharAt(r1, r4)
        L4c:
            int r1 = r1 + 1
            goto L23
        L4f:
            java.lang.String r5 = r2.toString()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.ss.util.WorkbookUtil.createSafeSheetName(java.lang.String):java.lang.String");
    }

    public static void validateSheetName(String str) {
        if (str == null) {
            throw new IllegalArgumentException("sheetName must not be null");
        }
        int length = str.length();
        if (length < 1) {
            throw new IllegalArgumentException("sheetName must not be empty string");
        }
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt != '*' && cCharAt != '/' && cCharAt != ':' && cCharAt != '?') {
                switch (cCharAt) {
                    case '[':
                    case '\\':
                    case ']':
                        break;
                    default:
                }
            }
            throw new IllegalArgumentException("Invalid char (" + cCharAt + ") found at index (" + i + ") in sheet name '" + str + "'");
        }
        if (str.charAt(0) == '\'' || str.charAt(length - 1) == '\'') {
            throw new IllegalArgumentException("Invalid sheet name '" + str + "'. Sheet names must not begin or end with (').");
        }
    }

    public static void validateSheetState(int i) {
        if (i == 0 || i == 1 || i == 2) {
            return;
        }
        throw new IllegalArgumentException("Ivalid sheet state : " + i + "\nSheet state must beone of the Workbook.SHEET_STATE_* constants");
    }
}
