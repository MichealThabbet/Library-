package com.ibm.icu.impl;

import com.facebook.internal.ServerProtocol;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;

/* loaded from: classes2.dex */
public final class ICUDebug {
    private static boolean debug;
    private static boolean help;
    private static String params;

    static {
        try {
            params = System.getProperty("ICUDebug");
        } catch (SecurityException unused) {
        }
        boolean z = true;
        boolean z2 = params != null;
        debug = z2;
        if (!z2 || (!params.equals("") && params.indexOf("help") == -1)) {
            z = false;
        }
        help = z;
        if (debug) {
            System.out.println("\nICUDebug=" + params);
        }
    }

    public static boolean enabled() {
        return debug;
    }

    public static boolean enabled(String str) {
        if (debug) {
            z = params.indexOf(str) != -1;
            if (help) {
                System.out.println("\nICUDebug.enabled(" + str + ") = " + z);
            }
        }
        return z;
    }

    public static String value(String str) {
        String strSubstring;
        String str2 = "false";
        if (debug) {
            int iIndexOf = params.indexOf(str);
            if (iIndexOf != -1) {
                int length = iIndexOf + str.length();
                if (params.length() <= length || params.charAt(length) != '=') {
                    strSubstring = ServerProtocol.DIALOG_RETURN_SCOPES_TRUE;
                } else {
                    int i = length + 1;
                    int iIndexOf2 = params.indexOf(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR, i);
                    String str3 = params;
                    if (iIndexOf2 == -1) {
                        iIndexOf2 = str3.length();
                    }
                    strSubstring = str3.substring(i, iIndexOf2);
                }
                str2 = strSubstring;
            }
            if (help) {
                System.out.println("\nICUDebug.value(" + str + ") = " + str2);
            }
        }
        return str2;
    }
}
