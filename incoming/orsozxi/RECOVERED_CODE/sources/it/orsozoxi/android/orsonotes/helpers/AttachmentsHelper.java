package it.orsozoxi.android.orsonotes.helpers;

import it.orsozoxi.android.orsonotes.models.Attachment;
import java.io.File;
import org.apache.commons.io.FileUtils;

/* loaded from: classes3.dex */
public class AttachmentsHelper {
    private AttachmentsHelper() {
    }

    public static String getSize(Attachment attachment) {
        long size = attachment.getSize();
        if (attachment.getSize() == 0) {
            size = new File(attachment.getUri().getPath()).length();
        }
        return FileUtils.byteCountToDisplaySize(size);
    }

    public static boolean typeOf(Attachment attachment, String... strArr) {
        for (String str : strArr) {
            if (str.equals(attachment.getMime_type())) {
                return true;
            }
        }
        return false;
    }
}
