package com.huawei.hms.common.util;

import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.ibm.icu.impl.locale.LanguageTag;
import java.io.IOException;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Locale;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class Logger {
    private static final boolean a = false;
    private static final String b = "Logger";
    private static final String c = "|";
    private static final int d = 8;
    private static final int e = 20;
    private static final String f = "dynamic-api_";

    static class a extends Throwable {
        private static final long b = 7129050843360571879L;
        String a;
        private Throwable c;
        private Throwable d;

        private a(Throwable th) {
            this.d = th;
        }

        /* synthetic */ a(Throwable th, byte b2) {
            this(th);
        }

        private void a(String str) {
            this.a = str;
        }

        private void a(Throwable th) {
            this.c = th;
        }

        @Override // java.lang.Throwable
        public final Throwable getCause() {
            Throwable th = this.c;
            if (th == this) {
                return null;
            }
            return th;
        }

        @Override // java.lang.Throwable
        public final String getMessage() {
            return this.a;
        }

        @Override // java.lang.Throwable
        public final String toString() {
            Throwable th = this.d;
            if (th == null) {
                return "";
            }
            String name = th.getClass().getName();
            if (this.a == null) {
                return name;
            }
            String str = name + ": ";
            if (this.a.startsWith(str)) {
                return this.a;
            }
            return str + this.a;
        }
    }

    private static int a(int i, String str, String str2) {
        return Log.println(i, a(str), a(str2, 7));
    }

    private static String a(int i) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length <= i) {
            return "";
        }
        StackTraceElement stackTraceElement = stackTrace[i];
        return Process.myPid() + LanguageTag.SEP + Process.myTid() + "|" + stackTraceElement.getFileName() + "|" + stackTraceElement.getClassName() + "|" + stackTraceElement.getMethodName() + "|" + stackTraceElement.getLineNumber();
    }

    private static String a(String str) {
        return f.concat(String.valueOf(str));
    }

    private static String a(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return a(i);
        }
        return a(i) + "|" + str;
    }

    private static Throwable a(Throwable th) {
        if (isLoggable(3)) {
            return th;
        }
        if (th == null) {
            return null;
        }
        int i = ((th instanceof IOException) || (th instanceof JSONException)) ? 8 : 20;
        byte b2 = 0;
        a aVar = new a(th, b2);
        StackTraceElement[] stackTrace = aVar.getStackTrace();
        if (stackTrace.length > i) {
            aVar.setStackTrace((StackTraceElement[]) Arrays.copyOf(stackTrace, i));
        } else {
            aVar.setStackTrace(stackTrace);
        }
        aVar.a = anonymizeMessage(th.getMessage());
        Throwable cause = th.getCause();
        a aVar2 = aVar;
        while (cause != null) {
            a aVar3 = new a(cause, b2);
            aVar3.a = anonymizeMessage(cause.getMessage());
            aVar2.c = aVar3;
            cause = cause.getCause();
            aVar2 = aVar3;
        }
        return aVar;
    }

    public static String anonymizeMessage(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (i % 2 == 1) {
                charArray[i] = '*';
            }
        }
        return new String(charArray);
    }

    public static void d(String str, Object obj) {
        println(3, str, obj);
    }

    public static void d(String str, String str2, Object... objArr) {
        println(3, str, str2, objArr);
    }

    public static void e(String str, Object obj) {
        println(6, str, obj);
    }

    public static void e(String str, String str2, Throwable th) {
        Log.e(a(str), a(str2, 5), a(th));
    }

    public static void e(String str, String str2, Object... objArr) {
        println(6, str, str2, objArr);
    }

    public static String format(String str, Object... objArr) {
        return str == null ? "" : String.format(Locale.ROOT, str, objArr);
    }

    public static void i(String str, Object obj) {
        println(4, str, obj);
    }

    public static void i(String str, String str2, Object... objArr) {
        println(4, str, str2, objArr);
    }

    public static boolean isLoggable(int i) {
        return Log.isLoggable(f, i);
    }

    public static void println(int i, String str, Object obj) {
        if (i >= 3 && isLoggable(i)) {
            a(i, str, obj == null ? "null" : obj.toString());
        }
    }

    public static void println(int i, String str, String str2, Object... objArr) {
        if (i < 3) {
            return;
        }
        if (str2 == null) {
            Log.w(b, "format is null, not log");
            return;
        }
        try {
            if (isLoggable(i)) {
                a(i, str, format(str2, objArr));
            }
        } catch (IllegalFormatException e2) {
            w(b, "log format error".concat(String.valueOf(str2)), e2);
        }
    }

    public static void v(String str, Object obj) {
        println(2, str, obj);
    }

    public static void v(String str, String str2, Object... objArr) {
        println(2, str, str2, objArr);
    }

    public static void w(String str, Object obj) {
        println(5, str, obj);
    }

    public static void w(String str, String str2, Throwable th) {
        Log.w(a(str), a(str2, 5), a(th));
    }

    public static void w(String str, String str2, Object... objArr) {
        println(5, str, str2, objArr);
    }
}
