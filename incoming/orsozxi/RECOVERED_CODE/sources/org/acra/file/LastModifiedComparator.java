package org.acra.file;

import java.io.File;
import java.util.Comparator;

/* loaded from: classes3.dex */
public final class LastModifiedComparator implements Comparator<File> {
    @Override // java.util.Comparator
    public int compare(File file, File file2) {
        long jLastModified = file.lastModified();
        long jLastModified2 = file2.lastModified();
        if (jLastModified < jLastModified2) {
            return -1;
        }
        return jLastModified == jLastModified2 ? 0 : 1;
    }
}
