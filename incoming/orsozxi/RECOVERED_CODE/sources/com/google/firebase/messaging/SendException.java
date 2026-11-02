package com.google.firebase.messaging;

/* compiled from: com.google.firebase:firebase-messaging@@21.1.0 */
/* loaded from: classes2.dex */
public final class SendException extends Exception {
    public static final int ERROR_INVALID_PARAMETERS = 1;
    public static final int ERROR_SIZE = 2;
    public static final int ERROR_TOO_MANY_MESSAGES = 4;
    public static final int ERROR_TTL_EXCEEDED = 3;
    public static final int ERROR_UNKNOWN = 0;
    private final int errorCode;

    SendException(String str) {
        super(str);
        this.errorCode = parseErrorCode(str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int parseErrorCode(java.lang.String r7) {
        /*
            r6 = this;
            r0 = 0
            if (r7 != 0) goto L4
            return r0
        L4:
            java.util.Locale r1 = java.util.Locale.US
            java.lang.String r7 = r7.toLowerCase(r1)
            int r1 = r7.hashCode()
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            switch(r1) {
                case -1743242157: goto L3f;
                case -1290953729: goto L34;
                case -920906446: goto L2a;
                case -617027085: goto L20;
                case -95047692: goto L16;
                default: goto L15;
            }
        L15:
            goto L49
        L16:
            java.lang.String r1 = "missing_to"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L49
            r7 = 1
            goto L4a
        L20:
            java.lang.String r1 = "messagetoobig"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L49
            r7 = 2
            goto L4a
        L2a:
            java.lang.String r1 = "invalid_parameters"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L49
            r7 = 0
            goto L4a
        L34:
            java.lang.String r1 = "toomanymessages"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L49
            r7 = 4
            goto L4a
        L3f:
            java.lang.String r1 = "service_not_available"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L49
            r7 = 3
            goto L4a
        L49:
            r7 = -1
        L4a:
            if (r7 == 0) goto L58
            if (r7 == r5) goto L58
            if (r7 == r4) goto L57
            if (r7 == r3) goto L56
            if (r7 == r2) goto L55
            return r0
        L55:
            return r2
        L56:
            return r3
        L57:
            return r4
        L58:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.SendException.parseErrorCode(java.lang.String):int");
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
