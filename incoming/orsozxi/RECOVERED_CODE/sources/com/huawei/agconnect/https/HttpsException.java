package com.huawei.agconnect.https;

import java.io.IOException;

/* loaded from: classes2.dex */
public class HttpsException extends Exception {
    private final IOException e;
    private final boolean hasRequest;

    public HttpsException(boolean z, IOException iOException) {
        super(getMessage(z, iOException));
        this.hasRequest = z;
        this.e = iOException;
    }

    public HttpsException(boolean z, String str) {
        super(str);
        this.hasRequest = z;
        this.e = new IOException(str);
    }

    private static String getMessage(boolean z, IOException iOException) {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP HasRequest: ");
        sb.append(z);
        sb.append(" ");
        sb.append(iOException == null ? "" : iOException.getMessage());
        return sb.toString();
    }

    public IOException getException() {
        return this.e;
    }

    public boolean hasRequest() {
        return this.hasRequest;
    }

    public String message() {
        IOException iOException = this.e;
        return iOException == null ? "exception is null" : iOException.getMessage();
    }
}
