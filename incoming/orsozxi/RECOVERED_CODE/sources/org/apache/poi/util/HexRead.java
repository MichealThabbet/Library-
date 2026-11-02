package org.apache.poi.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class HexRead {
    public static byte[] readData(String str) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        try {
            return readData(fileInputStream, -1);
        } finally {
            fileInputStream.close();
        }
    }

    public static byte[] readData(InputStream inputStream, String str) throws IOException {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            int i = inputStream.read();
            boolean z = false;
            while (i != -1) {
                if (i == 10 || i == 13) {
                    stringBuffer = new StringBuffer();
                } else {
                    if (i == 91) {
                        z = true;
                    } else if (i != 93) {
                        if (z) {
                            stringBuffer.append((char) i);
                        }
                    } else {
                        if (stringBuffer.toString().equals(str)) {
                            return readData(inputStream, 91);
                        }
                        stringBuffer = new StringBuffer();
                    }
                    i = inputStream.read();
                }
                z = false;
                i = inputStream.read();
            }
            inputStream.close();
            throw new IOException("Section '" + str + "' not found");
        } finally {
            inputStream.close();
        }
    }

    public static byte[] readData(String str, String str2) throws IOException {
        return readData(new FileInputStream(new File(str)), str2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x0009, code lost:
    
        continue;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:13:0x001c. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:14:0x001f. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:15:0x0022. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0035 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0009 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x000b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static byte[] readData(java.io.InputStream r8, int r9) throws java.io.IOException {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            r2 = 0
        L7:
            r3 = 0
            r4 = 0
        L9:
            if (r2 != 0) goto L57
            int r5 = r8.read()
            r6 = 97
            if (r5 != r9) goto L14
            goto L57
        L14:
            r7 = -1
            if (r5 == r7) goto L55
            r7 = 35
            if (r5 == r7) goto L51
            r7 = 2
            switch(r5) {
                case 48: goto L3d;
                case 49: goto L3d;
                case 50: goto L3d;
                case 51: goto L3d;
                case 52: goto L3d;
                case 53: goto L3d;
                case 54: goto L3d;
                case 55: goto L3d;
                case 56: goto L3d;
                case 57: goto L3d;
                default: goto L1f;
            }
        L1f:
            switch(r5) {
                case 65: goto L26;
                case 66: goto L26;
                case 67: goto L26;
                case 68: goto L26;
                case 69: goto L26;
                case 70: goto L26;
                default: goto L22;
            }
        L22:
            switch(r5) {
                case 97: goto L28;
                case 98: goto L28;
                case 99: goto L28;
                case 100: goto L28;
                case 101: goto L28;
                case 102: goto L28;
                default: goto L25;
            }
        L25:
            goto L9
        L26:
            r6 = 65
        L28:
            int r3 = r3 << 4
            byte r3 = (byte) r3
            int r5 = r5 + 10
            int r5 = r5 - r6
            byte r5 = (byte) r5
            int r3 = r3 + r5
            byte r3 = (byte) r3
            int r4 = r4 + 1
            if (r4 != r7) goto L9
            java.lang.Byte r3 = java.lang.Byte.valueOf(r3)
            r0.add(r3)
            goto L7
        L3d:
            int r3 = r3 << 4
            byte r3 = (byte) r3
            int r5 = r5 + (-48)
            byte r5 = (byte) r5
            int r3 = r3 + r5
            byte r3 = (byte) r3
            int r4 = r4 + 1
            if (r4 != r7) goto L9
            java.lang.Byte r3 = java.lang.Byte.valueOf(r3)
            r0.add(r3)
            goto L7
        L51:
            readToEOL(r8)
            goto L9
        L55:
            r2 = 1
            goto L9
        L57:
            java.lang.Byte[] r8 = new java.lang.Byte[r1]
            java.lang.Object[] r8 = r0.toArray(r8)
            java.lang.Byte[] r8 = (java.lang.Byte[]) r8
            java.lang.Byte[] r8 = (java.lang.Byte[]) r8
            int r9 = r8.length
            byte[] r9 = new byte[r9]
        L64:
            int r0 = r8.length
            if (r1 >= r0) goto L72
            r0 = r8[r1]
            byte r0 = r0.byteValue()
            r9[r1] = r0
            int r1 = r1 + 1
            goto L64
        L72:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.util.HexRead.readData(java.io.InputStream, int):byte[]");
    }

    public static byte[] readFromString(String str) {
        try {
            return readData(new ByteArrayInputStream(str.getBytes()), -1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readToEOL(InputStream inputStream) throws IOException {
        int i = inputStream.read();
        while (i != -1 && i != 10 && i != 13) {
            i = inputStream.read();
        }
    }
}
