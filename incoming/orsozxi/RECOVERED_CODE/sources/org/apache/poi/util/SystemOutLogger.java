package org.apache.poi.util;

/* loaded from: classes3.dex */
public class SystemOutLogger extends POILogger {
    private String _cat;

    @Override // org.apache.poi.util.POILogger
    public void initialize(String str) {
        this._cat = str;
    }

    @Override // org.apache.poi.util.POILogger
    public void log(int i, Object obj) {
        log(i, obj, (Throwable) null);
    }

    @Override // org.apache.poi.util.POILogger
    public void log(int i, Object obj, Throwable th) {
        if (check(i)) {
            System.out.println("[" + this._cat + "] " + obj);
            if (th != null) {
                th.printStackTrace(System.out);
            }
        }
    }

    @Override // org.apache.poi.util.POILogger
    public boolean check(int i) throws NumberFormatException {
        int i2;
        try {
            i2 = Integer.parseInt(System.getProperty("poi.log.level", WARN + ""));
        } catch (SecurityException unused) {
            i2 = POILogger.DEBUG;
        }
        return i >= i2;
    }
}
