package com.marshalchen.ultimaterecyclerview;

import android.util.Log;

/* loaded from: classes2.dex */
public final class URLogs {
    private static final String TAG_CONTENT_PRINT = "%s:%s.%s:%d";
    private static String sApplicationTag = "Chen";
    private static boolean sIsLogEnabled = true;

    public static String getTagContentPrint() {
        return TAG_CONTENT_PRINT;
    }

    private static StackTraceElement getCurrentStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static void trace() {
        if (sIsLogEnabled) {
            Log.d(sApplicationTag, getContent(getCurrentStackTraceElement()));
        }
    }

    private static String getContent(StackTraceElement stackTraceElement) {
        return String.format(TAG_CONTENT_PRINT, sApplicationTag, stackTraceElement.getClassName(), stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber()));
    }

    private static String getContents(StackTraceElement stackTraceElement) {
        return String.format("%s:%s:%d", sApplicationTag, stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber()));
    }

    public static void traceStack() {
        if (sIsLogEnabled) {
            traceStack(sApplicationTag, 6);
        }
    }

    public static void traceStack(String str, int i) {
        if (sIsLogEnabled) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            Log.println(i, str, stackTrace[4].toString());
            StringBuilder sb = new StringBuilder();
            String str2 = null;
            int i2 = 5;
            while (i2 < stackTrace.length) {
                String fileName = stackTrace[i2].getFileName();
                int iIndexOf = fileName.indexOf(".java");
                if (iIndexOf >= 0) {
                    fileName = fileName.substring(0, iIndexOf);
                }
                if (str2 == null || !str2.equals(fileName)) {
                    sb.append(fileName.substring(0, iIndexOf));
                }
                sb.append(".");
                sb.append(stackTrace[i2].getMethodName());
                sb.append(":");
                sb.append(stackTrace[i2].getLineNumber());
                sb.append("->");
                i2++;
                str2 = fileName;
            }
            Log.println(i, str, sb.toString());
        }
    }

    public static void v(String str) {
        if (sIsLogEnabled) {
            Log.v(sApplicationTag, getContents(getCurrentStackTraceElement()) + ">" + str);
        }
    }

    public static void d(String str, String str2) {
        if (sIsLogEnabled) {
            Log.d(str, getContent(getCurrentStackTraceElement()) + ">" + str2);
        }
    }

    public static void d(String str) {
        if (sIsLogEnabled) {
            Log.d(sApplicationTag, getContents(getCurrentStackTraceElement()) + ">" + str);
        }
    }

    public static void i(String str, String str2) {
        if (sIsLogEnabled) {
            Log.i(str, getContent(getCurrentStackTraceElement()) + ">" + str2);
        }
    }

    public static void d(String str, Object... objArr) {
        if (sIsLogEnabled) {
            d(String.format(str, objArr));
        }
    }

    public static void w(String str, String str2) {
        if (sIsLogEnabled) {
            Log.w(str, getContent(getCurrentStackTraceElement()) + ">" + str2);
        }
    }

    public static void e(String str, String str2) {
        if (sIsLogEnabled) {
            Log.e(str, getContent(getCurrentStackTraceElement()) + ">" + str2);
        }
    }

    public static void i(String str) {
        if (sIsLogEnabled) {
            Log.i(sApplicationTag, getContent(getCurrentStackTraceElement()) + ">" + str);
        }
    }

    public static void w(String str) {
        if (sIsLogEnabled) {
            Log.w(sApplicationTag, getContent(getCurrentStackTraceElement()) + ">" + str);
        }
    }

    public static void e(String str) {
        if (sIsLogEnabled) {
            Log.e(sApplicationTag, getContent(getCurrentStackTraceElement()) + "\n>" + str);
        }
    }

    public static void e(Exception exc) {
        if (sIsLogEnabled) {
            Log.e(sApplicationTag, getContent(getCurrentStackTraceElement()) + "\n>" + exc.getMessage());
            exc.printStackTrace();
        }
    }

    public static void e(Exception exc, String str) {
        if (sIsLogEnabled) {
            Log.e(sApplicationTag, getContent(getCurrentStackTraceElement()) + "\n>" + exc.getMessage() + "\n>" + exc.getStackTrace() + "   " + str);
            exc.printStackTrace();
        }
    }

    public static void e(String str, Exception exc) {
        if (sIsLogEnabled) {
            Log.e(sApplicationTag, getContent(getCurrentStackTraceElement()) + "\n>" + exc.getMessage() + "\n>" + exc.getStackTrace() + "   " + str);
            exc.printStackTrace();
        }
    }

    public static void e(String str, String str2, Exception exc) {
        if (sIsLogEnabled) {
            Log.e(str, getContent(getCurrentStackTraceElement()) + "\n>" + exc.getMessage() + "\n>" + exc.getStackTrace() + "   " + str2);
            exc.printStackTrace();
        }
    }

    public static boolean issIsLogEnabled() {
        return sIsLogEnabled;
    }

    public static void setsIsLogEnabled(boolean z) {
        sIsLogEnabled = z;
    }

    public static String getsApplicationTag() {
        return sApplicationTag;
    }

    public static void setsApplicationTag(String str) {
        sApplicationTag = str;
    }
}
