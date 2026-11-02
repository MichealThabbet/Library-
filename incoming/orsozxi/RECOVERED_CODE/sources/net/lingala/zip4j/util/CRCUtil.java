package net.lingala.zip4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

/* loaded from: classes3.dex */
public class CRCUtil {
    private static final int BUF_SIZE = 16384;

    public static long computeFileCRC(String str) throws ZipException {
        return computeFileCRC(str, null);
    }

    public static long computeFileCRC(String str, ProgressMonitor progressMonitor) throws Throwable {
        FileInputStream fileInputStream;
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("input file is null or empty, cannot calculate CRC for the file");
        }
        FileInputStream fileInputStream2 = null;
        try {
            try {
                Zip4jUtil.checkFileReadAccess(str);
                fileInputStream = new FileInputStream(new File(str));
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
        } catch (Exception e2) {
            e = e2;
        }
        try {
            byte[] bArr = new byte[16384];
            CRC32 crc32 = new CRC32();
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i != -1) {
                    crc32.update(bArr, 0, i);
                    if (progressMonitor != null) {
                        progressMonitor.updateWorkCompleted(i);
                        if (progressMonitor.isCancelAllTasks()) {
                            progressMonitor.setResult(3);
                            progressMonitor.setState(0);
                            try {
                                fileInputStream.close();
                                return 0L;
                            } catch (IOException unused) {
                                throw new ZipException("error while closing the file after calculating crc");
                            }
                        }
                    }
                } else {
                    long value = crc32.getValue();
                    try {
                        fileInputStream.close();
                        return value;
                    } catch (IOException unused2) {
                        throw new ZipException("error while closing the file after calculating crc");
                    }
                }
            }
        } catch (IOException e3) {
            e = e3;
            throw new ZipException(e);
        } catch (Exception e4) {
            e = e4;
            throw new ZipException(e);
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException unused3) {
                    throw new ZipException("error while closing the file after calculating crc");
                }
            }
            throw th;
        }
    }
}
