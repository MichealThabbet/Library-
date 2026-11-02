package org.apache.poi.util;

import java.io.File;
import java.util.Random;

/* loaded from: classes3.dex */
public final class TempFile {
    private static File dir;
    private static final Random rnd = new Random();

    public static File createTempFile(String str, String str2) {
        if (dir == null) {
            File file = new File(System.getProperty("java.io.tmpdir"), "poifiles");
            dir = file;
            file.mkdir();
            if (System.getProperty("poi.keep.tmp.files") == null) {
                dir.deleteOnExit();
            }
        }
        File file2 = new File(dir, str + rnd.nextInt() + str2);
        if (System.getProperty("poi.keep.tmp.files") == null) {
            file2.deleteOnExit();
        }
        return file2;
    }
}
