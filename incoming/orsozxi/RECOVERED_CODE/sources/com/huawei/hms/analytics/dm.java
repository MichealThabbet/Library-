package com.huawei.hms.analytics;

import android.os.Bundle;
import com.huawei.hms.analytics.core.log.HiLog;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.Deflater;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class dm {
    private static final Charset lmn = Charset.forName("UTF-8");

    public static String lmn(File file) throws Throwable {
        FileInputStream fileInputStream;
        byte[] bArr;
        FileInputStream fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Throwable th) {
                th = th;
            }
        } catch (FileNotFoundException unused) {
        } catch (IOException unused2) {
        }
        try {
            db dbVar = new db((byte) 0);
            byte[] bArr2 = new byte[1024];
            while (true) {
                int i = fileInputStream.read(bArr2);
                if (i == -1) {
                    break;
                }
                if (i > 0) {
                    if (dbVar.lmn.length - dbVar.klm >= i) {
                        System.arraycopy(bArr2, 0, dbVar.lmn, dbVar.klm, i);
                    } else {
                        byte[] bArr3 = new byte[(dbVar.lmn.length + i) << 1];
                        System.arraycopy(dbVar.lmn, 0, bArr3, 0, dbVar.klm);
                        System.arraycopy(bArr2, 0, bArr3, dbVar.klm, i);
                        dbVar.lmn = bArr3;
                    }
                    dbVar.klm += i;
                }
            }
            if (dbVar.klm == 0) {
                lmn(fileInputStream);
                return "";
            }
            if (dbVar.klm <= 0) {
                bArr = new byte[0];
            } else {
                byte[] bArr4 = new byte[dbVar.klm];
                System.arraycopy(dbVar.lmn, 0, bArr4, 0, dbVar.klm);
                bArr = bArr4;
            }
            String str = new String(bArr, lmn);
            lmn(fileInputStream);
            return str;
        } catch (FileNotFoundException unused3) {
            fileInputStream2 = fileInputStream;
            HiLog.w("StreamToolsKit", "getInfoFromFile(): No files need to be read");
            lmn(fileInputStream2);
            return "";
        } catch (IOException unused4) {
            fileInputStream2 = fileInputStream;
            HiLog.w("StreamToolsKit", "getInfoFromFile(): stream.read or new string exception");
            lmn(fileInputStream2);
            return "";
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            lmn(fileInputStream2);
            throw th;
        }
    }

    public static JSONObject lmn(Bundle bundle) {
        JSONObject jSONObject = new JSONObject();
        if (bundle != null && bundle.size() != 0) {
            for (String str : bundle.keySet()) {
                try {
                    jSONObject.put(str, bundle.get(str));
                } catch (JSONException unused) {
                    HiLog.w("StreamToolsKit", "bundleToJSON exception");
                }
            }
        }
        return jSONObject;
    }

    private static void lmn(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
                HiLog.w("StreamToolsKit", "closeQuietly(): Exception when closing the closeable!");
            }
        }
    }

    public static void lmn(File file, String str) throws Throwable {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (Throwable th) {
                th = th;
            }
        } catch (FileNotFoundException unused) {
        } catch (IOException unused2) {
        }
        try {
            fileOutputStream.write(str.getBytes("UTF-8"));
            fileOutputStream.flush();
            lmn(fileOutputStream);
        } catch (FileNotFoundException unused3) {
            fileOutputStream2 = fileOutputStream;
            HiLog.w("StreamToolsKit", "saveInfoToFile(): No files need to be read");
            lmn(fileOutputStream2);
        } catch (IOException unused4) {
            fileOutputStream2 = fileOutputStream;
            HiLog.w("StreamToolsKit", "saveInfoToFile(): io exc from write info to file!");
            lmn(fileOutputStream2);
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            lmn(fileOutputStream2);
            throw th;
        }
    }

    public static byte[] lmn(byte[] bArr) throws IOException {
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
            lmn(byteArrayOutputStream);
        }
    }
}
