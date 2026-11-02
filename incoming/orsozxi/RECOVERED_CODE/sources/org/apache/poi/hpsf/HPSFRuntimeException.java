package org.apache.poi.hpsf;

import java.io.PrintStream;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class HPSFRuntimeException extends RuntimeException {
    private Throwable reason;

    public HPSFRuntimeException() {
    }

    public HPSFRuntimeException(String str) {
        super(str);
    }

    public HPSFRuntimeException(Throwable th) {
        this.reason = th;
    }

    public HPSFRuntimeException(String str, Throwable th) {
        super(str);
        this.reason = th;
    }

    public Throwable getReason() {
        return this.reason;
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        Throwable reason = getReason();
        super.printStackTrace(printStream);
        if (reason != null) {
            printStream.println("Caused by:");
            reason.printStackTrace(printStream);
        }
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        Throwable reason = getReason();
        super.printStackTrace(printWriter);
        if (reason != null) {
            printWriter.println("Caused by:");
            reason.printStackTrace(printWriter);
        }
    }
}
