package org.acra.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.acra.attachment.AcraContentProvider;

/* loaded from: classes3.dex */
public final class UriUtils {
    private UriUtils() {
    }

    public static void copyFromUri(Context context, OutputStream outputStream, Uri uri) throws IOException {
        InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
        try {
            if (inputStreamOpenInputStream == null) {
                throw new FileNotFoundException("Could not open " + uri.toString());
            }
            byte[] bArr = new byte[8192];
            while (true) {
                int i = inputStreamOpenInputStream.read(bArr);
                if (i <= 0) {
                    break;
                } else {
                    outputStream.write(bArr, 0, i);
                }
            }
            if (inputStreamOpenInputStream != null) {
                inputStreamOpenInputStream.close();
            }
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                if (inputStreamOpenInputStream != null) {
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }
    }

    public static String getFileNameFromUri(Context context, Uri uri) throws FileNotFoundException {
        Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_display_name"}, null, null, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.moveToFirst()) {
                    String string = cursorQuery.getString(cursorQuery.getColumnIndex("_display_name"));
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return string;
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    if (cursorQuery != null) {
                        try {
                            cursorQuery.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                    }
                    throw th2;
                }
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        throw new FileNotFoundException("Could not resolve filename of " + uri);
    }

    public static String getMimeType(Context context, Uri uri) {
        String type;
        return (!uri.getScheme().equals("content") || (type = context.getContentResolver().getType(uri)) == null) ? AcraContentProvider.guessMimeType(uri) : type;
    }
}
