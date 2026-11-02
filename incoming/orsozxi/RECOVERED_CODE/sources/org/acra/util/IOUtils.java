package org.acra.util;

import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import org.acra.ACRA;

/* loaded from: classes3.dex */
public final class IOUtils {
    private IOUtils() {
    }

    public static void safeClose(Closeable closeable) throws IOException {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException unused) {
        }
    }

    public static void deleteFile(File file) {
        if (file.delete()) {
            return;
        }
        ACRA.log.w(ACRA.LOG_TAG, "Could not delete file: " + file);
    }

    public static void writeStringToFile(File file, String str) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        try {
            outputStreamWriter.write(str);
            outputStreamWriter.flush();
        } finally {
            safeClose(outputStreamWriter);
        }
    }

    public static String serialize(Serializable serializable) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream.writeObject(serializable);
                String strEncodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
                objectOutputStream.close();
                return strEncodeToString;
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T extends Serializable> T deserialize(Class<T> cls, String str) throws IOException {
        if (str == null) {
            return null;
        }
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(Base64.decode(str, 0)));
            try {
                Object object = objectInputStream.readObject();
                if (!cls.isInstance(object)) {
                    objectInputStream.close();
                    return null;
                }
                T tCast = cls.cast(object);
                objectInputStream.close();
                return tCast;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    try {
                        objectInputStream.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                    throw th2;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
