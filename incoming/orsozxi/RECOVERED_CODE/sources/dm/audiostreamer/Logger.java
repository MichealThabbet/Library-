package dm.audiostreamer;

import android.util.Log;

/* loaded from: classes2.dex */
public class Logger {
    private static final String LOG_PREFIX = "dmAudioStreamingLib_";
    private static final int LOG_PREFIX_LENGTH = 20;
    private static final int MAX_LOG_TAG_LENGTH = 23;

    public static void d(String str, Object... objArr) {
    }

    public static void v(String str, Object... objArr) {
    }

    public static String makeLogTag(String str) {
        if (str.length() > 23 - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, (23 - LOG_PREFIX_LENGTH) - 1);
        }
        return LOG_PREFIX + str;
    }

    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void i(String str, Object... objArr) {
        log(str, 4, null, objArr);
    }

    public static void w(String str, Object... objArr) {
        log(str, 5, null, objArr);
    }

    public static void w(String str, Throwable th, Object... objArr) {
        log(str, 5, th, objArr);
    }

    public static void e(String str, Object... objArr) {
        log(str, 6, null, objArr);
    }

    public static void e(String str, Throwable th, Object... objArr) {
        log(str, 6, th, objArr);
    }

    public static void log(String str, int i, Throwable th, Object... objArr) {
        String string;
        if (Log.isLoggable(str, i)) {
            if (th == null && objArr != null && objArr.length == 1) {
                string = objArr[0].toString();
            } else {
                StringBuilder sb = new StringBuilder();
                if (objArr != null) {
                    for (Object obj : objArr) {
                        sb.append(obj);
                    }
                }
                if (th != null) {
                    sb.append("\n");
                    sb.append(Log.getStackTraceString(th));
                }
                string = sb.toString();
            }
            Log.println(i, str, string);
        }
    }
}
