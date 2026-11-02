package net.fortuna.ical4j.util;

/* loaded from: classes3.dex */
public final class Numbers {
    private Numbers() {
    }

    public static int parseInt(String str) {
        if (str != null && str.charAt(0) == '+') {
            return Integer.parseInt(str.substring(1));
        }
        return Integer.parseInt(str);
    }
}
