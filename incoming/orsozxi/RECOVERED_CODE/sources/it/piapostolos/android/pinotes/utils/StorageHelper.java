package it.piapostolos.android.pinotes.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import it.piapostolos.android.pinotes.PiNotes;
import it.piapostolos.android.pinotes.R;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import it.piapostolos.android.pinotes.models.Attachment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/* loaded from: classes3.dex */
public class StorageHelper {
    /* JADX WARN: Removed duplicated region for block: B:13:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean checkStorage() {
        /*
            java.lang.String r0 = android.os.Environment.getExternalStorageState()
            int r1 = r0.hashCode()
            r2 = 1242932856(0x4a15a678, float:2451870.0)
            r3 = 0
            r4 = 1
            if (r1 == r2) goto L1f
            r2 = 1299749220(0x4d789964, float:2.6067514E8)
            if (r1 == r2) goto L15
            goto L29
        L15:
            java.lang.String r1 = "mounted_ro"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L29
            r0 = 1
            goto L2a
        L1f:
            java.lang.String r1 = "mounted"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L29
            r0 = 0
            goto L2a
        L29:
            r0 = -1
        L2a:
            if (r0 == 0) goto L33
            if (r0 == r4) goto L30
            r0 = 0
            goto L31
        L30:
            r0 = 1
        L31:
            r1 = 0
            goto L35
        L33:
            r0 = 1
            r1 = 1
        L35:
            if (r0 == 0) goto L3a
            if (r1 == 0) goto L3a
            r3 = 1
        L3a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: it.piapostolos.android.pinotes.utils.StorageHelper.checkStorage():boolean");
    }

    public static String getStorageDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
    }

    public static File getAttachmentDir() {
        return PiNotes.getAppContext().getExternalFilesDir(null);
    }

    public static File getDbSyncDir(Context context) {
        File file = new File(context.getExternalFilesDir(null), "PiApostolosNotes");
        file.mkdirs();
        if (file.exists() && file.isDirectory()) {
            return file;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x009a A[Catch: IOException -> 0x0096, TRY_LEAVE, TryCatch #4 {IOException -> 0x0096, blocks: (B:36:0x0092, B:40:0x009a), top: B:57:0x0092 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ad A[Catch: IOException -> 0x00a9, TRY_LEAVE, TryCatch #0 {IOException -> 0x00a9, blocks: (B:46:0x00a5, B:50:0x00ad), top: B:55:0x00a5 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00a5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0092 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v2, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r8v1, types: [java.io.File, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v2, types: [java.io.File] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.io.File createExternalStoragePrivateFile(android.content.Context r6, android.net.Uri r7, java.lang.String r8) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "Error writing "
            java.lang.String r1 = "Error closing streams"
            boolean r2 = checkStorage()
            r3 = 0
            if (r2 != 0) goto L1a
            int r7 = it.piapostolos.android.pinotes.R.string.storage_not_available
            java.lang.String r7 = r6.getString(r7)
            r8 = 0
            android.widget.Toast r6 = android.widget.Toast.makeText(r6, r7, r8)
            r6.show()
            return r3
        L1a:
            java.io.File r8 = createNewAttachmentFile(r6, r8)
            android.content.ContentResolver r2 = r6.getContentResolver()     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L4a
            java.io.InputStream r2 = r2.openInputStream(r7)     // Catch: java.lang.Throwable -> L47 java.io.IOException -> L4a
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L45
            r4.<init>(r8)     // Catch: java.lang.Throwable -> L40 java.io.IOException -> L45
            copyFile(r2, r4)     // Catch: java.io.IOException -> L4c java.lang.Throwable -> L5a
            if (r2 == 0) goto L36
            r2.close()     // Catch: java.io.IOException -> L34
            goto L36
        L34:
            r6 = move-exception
            goto L3b
        L36:
            r4.close()     // Catch: java.io.IOException -> L34
            goto La2
        L3b:
            it.piapostolos.android.pinotes.helpers.LogDelegate.e(r1, r6)
            goto La2
        L40:
            r6 = move-exception
            r4 = r3
        L42:
            r3 = r2
            goto La3
        L45:
            r4 = r3
            goto L4c
        L47:
            r6 = move-exception
            r4 = r3
            goto La3
        L4a:
            r2 = r3
            r4 = r2
        L4c:
            java.io.File r5 = new java.io.File     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5c java.lang.NullPointerException -> L70
            java.lang.String r6 = it.piapostolos.android.pinotes.utils.FileHelper.getPath(r6, r7)     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5c java.lang.NullPointerException -> L70
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5c java.lang.NullPointerException -> L70
            org.apache.commons.io.FileUtils.copyFile(r5, r8)     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5c java.lang.NullPointerException -> L70
        L58:
            r3 = r8
            goto L90
        L5a:
            r6 = move-exception
            goto L42
        L5c:
            r6 = move-exception
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5a
            r7.<init>()     // Catch: java.lang.Throwable -> L5a
            r7.append(r0)     // Catch: java.lang.Throwable -> L5a
            r7.append(r8)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L5a
            it.piapostolos.android.pinotes.helpers.LogDelegate.e(r7, r6)     // Catch: java.lang.Throwable -> L5a
            goto L90
        L70:
            java.io.File r6 = new java.io.File     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L7d
            java.lang.String r7 = r7.getPath()     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L7d
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L7d
            org.apache.commons.io.FileUtils.copyFile(r6, r8)     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L7d
            goto L58
        L7d:
            r6 = move-exception
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5a
            r7.<init>()     // Catch: java.lang.Throwable -> L5a
            r7.append(r0)     // Catch: java.lang.Throwable -> L5a
            r7.append(r8)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L5a
            it.piapostolos.android.pinotes.helpers.LogDelegate.e(r7, r6)     // Catch: java.lang.Throwable -> L5a
        L90:
            if (r2 == 0) goto L98
            r2.close()     // Catch: java.io.IOException -> L96
            goto L98
        L96:
            r6 = move-exception
            goto L9e
        L98:
            if (r4 == 0) goto La1
            r4.close()     // Catch: java.io.IOException -> L96
            goto La1
        L9e:
            it.piapostolos.android.pinotes.helpers.LogDelegate.e(r1, r6)
        La1:
            r8 = r3
        La2:
            return r8
        La3:
            if (r3 == 0) goto Lab
            r3.close()     // Catch: java.io.IOException -> La9
            goto Lab
        La9:
            r7 = move-exception
            goto Lb1
        Lab:
            if (r4 == 0) goto Lb4
            r4.close()     // Catch: java.io.IOException -> La9
            goto Lb4
        Lb1:
            it.piapostolos.android.pinotes.helpers.LogDelegate.e(r1, r7)
        Lb4:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: it.piapostolos.android.pinotes.utils.StorageHelper.createExternalStoragePrivateFile(android.content.Context, android.net.Uri, java.lang.String):java.io.File");
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0051 A[Catch: IOException -> 0x004d, TRY_LEAVE, TryCatch #7 {IOException -> 0x004d, blocks: (B:38:0x0049, B:42:0x0051), top: B:52:0x0049 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0049 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean copyFile(java.io.File r3, java.io.File r4) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "Error closing streams"
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L29 java.io.FileNotFoundException -> L2c
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L29 java.io.FileNotFoundException -> L2c
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L21 java.io.FileNotFoundException -> L25
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L21 java.io.FileNotFoundException -> L25
            boolean r4 = copyFile(r2, r3)     // Catch: java.lang.Throwable -> L1d java.io.FileNotFoundException -> L1f
            r2.close()     // Catch: java.io.IOException -> L18
            r3.close()     // Catch: java.io.IOException -> L18
            goto L1c
        L18:
            r3 = move-exception
            it.piapostolos.android.pinotes.helpers.LogDelegate.e(r0, r3)
        L1c:
            return r4
        L1d:
            r4 = move-exception
            goto L23
        L1f:
            r4 = move-exception
            goto L27
        L21:
            r4 = move-exception
            r3 = r1
        L23:
            r1 = r2
            goto L47
        L25:
            r4 = move-exception
            r3 = r1
        L27:
            r1 = r2
            goto L2e
        L29:
            r4 = move-exception
            r3 = r1
            goto L47
        L2c:
            r4 = move-exception
            r3 = r1
        L2e:
            java.lang.String r2 = "Error copying file"
            it.piapostolos.android.pinotes.helpers.LogDelegate.e(r2, r4)     // Catch: java.lang.Throwable -> L46
            r4 = 0
            if (r1 == 0) goto L3c
            r1.close()     // Catch: java.io.IOException -> L3a
            goto L3c
        L3a:
            r3 = move-exception
            goto L42
        L3c:
            if (r3 == 0) goto L45
            r3.close()     // Catch: java.io.IOException -> L3a
            goto L45
        L42:
            it.piapostolos.android.pinotes.helpers.LogDelegate.e(r0, r3)
        L45:
            return r4
        L46:
            r4 = move-exception
        L47:
            if (r1 == 0) goto L4f
            r1.close()     // Catch: java.io.IOException -> L4d
            goto L4f
        L4d:
            r3 = move-exception
            goto L55
        L4f:
            if (r3 == 0) goto L58
            r3.close()     // Catch: java.io.IOException -> L4d
            goto L58
        L55:
            it.piapostolos.android.pinotes.helpers.LogDelegate.e(r0, r3)
        L58:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: it.piapostolos.android.pinotes.utils.StorageHelper.copyFile(java.io.File, java.io.File):boolean");
    }

    public static boolean copyFile(InputStream inputStream, OutputStream outputStream) {
        try {
            IOUtils.copy(inputStream, outputStream);
            return true;
        } catch (IOException e) {
            LogDelegate.e("Error copying file", e);
            return false;
        }
    }

    public static boolean deleteExternalStoragePrivateFile(Context context, String str) {
        if (!checkStorage()) {
            Toast.makeText(context, context.getString(R.string.storage_not_available), 0).show();
            return false;
        }
        return new File(context.getExternalFilesDir(null), str).delete();
    }

    public static boolean delete(Context context, String str) {
        if (!checkStorage()) {
            Toast.makeText(context, context.getString(R.string.storage_not_available), 0).show();
            return false;
        }
        try {
            FileUtils.forceDelete(new File(str));
            return true;
        } catch (IOException e) {
            LogDelegate.e("Can't delete '" + str + "': " + e.getMessage());
            return false;
        }
    }

    public static String getRealPathFromURI(Context context, Uri uri) throws IllegalArgumentException {
        Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        if (cursorQuery == null) {
            return null;
        }
        int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("_data");
        cursorQuery.moveToFirst();
        String string = cursorQuery.getString(columnIndexOrThrow);
        cursorQuery.close();
        return string;
    }

    public static File createNewAttachmentFile(Context context, String str) {
        if (checkStorage()) {
            return new File(context.getExternalFilesDir(null), createNewAttachmentName(str));
        }
        return null;
    }

    private static synchronized String createNewAttachmentName(String str) {
        StringBuilder sb;
        String str2 = new SimpleDateFormat("ddMMyyHHmmss").format(Calendar.getInstance().getTime());
        sb = new StringBuilder();
        sb.append(str2);
        if (str == null) {
            str = "";
        }
        sb.append(str);
        return sb.toString();
    }

    public static File createNewAttachmentFile(Context context) {
        return createNewAttachmentFile(context, null);
    }

    public static File copyToBackupDir(File file, String str, InputStream inputStream) {
        if (!checkStorage()) {
            return null;
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(file, str);
        try {
            copyFile(inputStream, new FileOutputStream(file2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file2;
    }

    public static File getCacheDir(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (!externalCacheDir.exists()) {
            externalCacheDir.mkdirs();
        }
        return externalCacheDir;
    }

    public static File getExternalStoragePublicDir(Context context) {
        File file = new File(getStoragePath(context) + File.separator + "PiApostolosNotes" + File.separator);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static String getStoragePath(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            return context.getExternalFilesDir(null).getPath();
        }
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static File getBackupDir(Context context, String str) throws IOException {
        File file = new File(getExternalStoragePublicDir(context), str);
        if (!file.exists() && file.mkdirs()) {
            createNoMediaFile(file);
        }
        return file;
    }

    private static void createNoMediaFile(File file) throws IOException {
        try {
            new File(file, ".nomedia").createNewFile();
        } catch (IOException unused) {
            LogDelegate.e("Error creating .nomedia file into backup folder");
        }
    }

    public static File getSharedPreferencesFile(Context context) {
        return new File(context.getFilesDir().getParentFile() + System.getProperty("file.separator") + "shared_prefs" + System.getProperty("file.separator") + context.getApplicationContext().getPackageName() + "_preferences.xml");
    }

    public static long getSize(File file) {
        long blockSize;
        StatFs statFs = new StatFs(file.getAbsolutePath());
        try {
            if (Build.VERSION.SDK_INT >= 18) {
                blockSize = statFs.getBlockSizeLong();
            } else {
                blockSize = statFs.getBlockSize();
            }
        } catch (NoSuchMethodError e) {
            LogDelegate.e("Mysterious error", e);
            blockSize = 0;
        }
        return getSize(file, blockSize);
    }

    private static long getSize(File file, long j) {
        long length;
        long length2 = 0;
        if (j == 0) {
            throw new InvalidParameterException("Blocksize can't be 0");
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            length2 = file.length();
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    length = getSize(file2, j);
                } else {
                    length = ((file2.length() / j) + 1) * j;
                }
                length2 += length;
            }
        }
        return length2;
    }

    public static boolean copyDirectory(File file, File file2) {
        if (file.isDirectory()) {
            if (!file2.exists()) {
                file2.mkdirs();
            }
            String[] list = file.list();
            boolean z = true;
            for (int i = 0; i < file.listFiles().length; i++) {
                z = z && copyDirectory(new File(file, list[i]), new File(file2, list[i]));
            }
            return z;
        }
        return copyFile(file, file2);
    }

    public static String getMimeType(Context context, Uri uri) {
        String type = context.getContentResolver().getType(uri);
        return type == null ? getMimeType(uri.toString()) : type;
    }

    public static String getMimeType(String str) {
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(str);
        if (fileExtensionFromUrl != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl);
        }
        return null;
    }

    public static String getMimeTypeInternal(Context context, Uri uri) {
        return getMimeTypeInternal(context, getMimeType(context, uri));
    }

    public static String getMimeTypeInternal(Context context, String str) {
        return str != null ? str.contains("image/") ? ConstantsBase.MIME_TYPE_IMAGE : str.contains("audio/") ? ConstantsBase.MIME_TYPE_AUDIO : str.contains("video/") ? ConstantsBase.MIME_TYPE_VIDEO : ConstantsBase.MIME_TYPE_FILES : str;
    }

    public static Attachment createAttachmentFromUri(Context context, Uri uri) {
        return createAttachmentFromUri(context, uri, false);
    }

    public static Attachment createAttachmentFromUri(Context context, Uri uri, boolean z) throws Throwable {
        File fileCreateExternalStoragePrivateFile;
        String nameFromUri = FileHelper.getNameFromUri(context, uri);
        String lowerCase = FileHelper.getFileExtension(FileHelper.getNameFromUri(context, uri)).toLowerCase(Locale.getDefault());
        if (z) {
            fileCreateExternalStoragePrivateFile = createNewAttachmentFile(context, lowerCase);
            try {
                FileUtils.moveFile(new File(uri.getPath()), fileCreateExternalStoragePrivateFile);
            } catch (IOException unused) {
                LogDelegate.e("Can't move file " + uri.getPath());
            }
        } else {
            fileCreateExternalStoragePrivateFile = createExternalStoragePrivateFile(context, uri, lowerCase);
        }
        if (fileCreateExternalStoragePrivateFile == null) {
            return null;
        }
        Attachment attachment = new Attachment(Uri.fromFile(fileCreateExternalStoragePrivateFile), getMimeTypeInternal(context, uri));
        attachment.setName(nameFromUri);
        attachment.setSize(fileCreateExternalStoragePrivateFile.length());
        return attachment;
    }

    public static File createNewAttachmentFileFromHttp(Context context, String str) throws IOException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return getFromHttp(str, createNewAttachmentFile(context, FileHelper.getFileExtension(str)));
    }

    public static File getFromHttp(String str, File file) throws IOException {
        FileUtils.copyURLToFile(new URL(str), file);
        return file;
    }
}
