package com.huawei.hms.analytics.framework.g;

import com.huawei.hms.analytics.core.log.HiLog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;

/* loaded from: classes2.dex */
public final class a {
    private static void a(OutputStream outputStream) throws IOException {
        try {
            outputStream.close();
        } catch (IOException unused) {
            HiLog.w("StreamToolsKit", "closeStream(): Exception: close OutputStream error!");
        }
    }

    public static byte[] a(byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(bArr);
            deflater.finish();
            byte[] bArr2 = new byte[1024];
            while (!deflater.finished()) {
                byteArrayOutputStream.write(bArr2, 0, deflater.deflate(bArr2));
            }
            return byteArrayOutputStream.toByteArray();
        } finally {
            deflater.end();
            a(byteArrayOutputStream);
        }
    }
}
