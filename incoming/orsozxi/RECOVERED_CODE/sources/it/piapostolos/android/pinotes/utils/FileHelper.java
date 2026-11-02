package it.piapostolos.android.pinotes.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import it.piapostolos.android.pinotes.factory.MediaStoreFactory;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import java.io.File;

/* loaded from: classes3.dex */
public class FileHelper {
    private FileHelper() {
    }

    public static String getStoragePath(Context context) {
        if (Build.VERSION.SDK_INT >= 30) {
            return context.getExternalFilesDir(null).getPath();
        }
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static String getPath(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(strArrSplit[0])) {
                    return getStoragePath(context) + "/" + strArrSplit[1];
                }
            } else {
                if (isDownloadsDocument(uri)) {
                    return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(DocumentsContract.getDocumentId(uri))), null, null);
                }
                if (isMediaDocument(uri)) {
                    String[] strArrSplit2 = DocumentsContract.getDocumentId(uri).split(":");
                    return getDataColumn(context, new MediaStoreFactory().createURI(strArrSplit2[0]), "_id=?", new String[]{strArrSplit2[1]});
                }
            }
        } else {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        try {
            Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return string;
                    }
                } finally {
                }
            }
            if (cursorQuery == null) {
                return null;
            }
            cursorQuery.close();
            return null;
        } catch (Exception e) {
            LogDelegate.e("Error retrieving uri path", e);
            return null;
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getNameFromUri(Context context, Uri uri) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            cursorQuery = context.getContentResolver().query(uri, new String[]{"_display_name"}, null, null, null);
            String string = "";
            try {
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            string = cursorQuery.getString(0);
                        }
                    } catch (Exception e) {
                        LogDelegate.e("Error managing diskk cache", e);
                    }
                } else {
                    string = uri.getLastPathSegment();
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return string;
            } catch (SecurityException unused) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            } catch (Throwable th) {
                th = th;
                cursor = cursorQuery;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SecurityException unused2) {
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String getFilePrefix(File file) {
        return getFilePrefix(file.getName());
    }

    public static String getFilePrefix(String str) {
        int iIndexOf = str.indexOf(46);
        return iIndexOf != -1 ? str.substring(0, iIndexOf) : str;
    }

    public static String getFileExtension(File file) {
        return getFileExtension(file.getName());
    }

    public static String getFileExtension(String str) {
        int iLastIndexOf;
        return (TextUtils.isEmpty(str) || (iLastIndexOf = str.lastIndexOf(46)) == -1) ? "" : str.substring(iLastIndexOf);
    }
}
