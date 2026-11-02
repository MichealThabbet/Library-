package it.orsozoxi.android.orsonotes.utils;

import android.net.Uri;
import androidx.core.content.FileProvider;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.models.Attachment;
import java.io.File;

/* loaded from: classes3.dex */
public class FileProviderHelper {
    private FileProviderHelper() {
    }

    public static Uri getFileProvider(File file) {
        return FileProvider.getUriForFile(OrsoNotes.getAppContext(), OrsoNotes.getAppContext().getPackageName() + ".authority", file);
    }

    public static Uri getShareableUri(Attachment attachment) {
        File file = new File(attachment.getUri().getPath());
        if (file.exists()) {
            return getFileProvider(file);
        }
        return attachment.getUri();
    }
}
