package it.piapostolos.android.pinotes.helpers;

import it.piapostolos.android.pinotes.exceptions.GenericException;

/* loaded from: classes3.dex */
public class LogDelegate {
    private static Boolean fileLoggingEnabled;

    public static void d(String str) {
    }

    public static void e(String str, Throwable th) {
    }

    public static void i(String str) {
    }

    public static void v(String str) {
    }

    public static void w(String str) {
    }

    public static void w(String str, Throwable th) {
    }

    private LogDelegate() {
    }

    public static void e(String str) {
        e(str, new GenericException(str));
    }

    private static boolean isFileLoggingEnabled() {
        return fileLoggingEnabled.booleanValue();
    }
}
