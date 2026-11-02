package org.acra.util;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.acra.ACRA;

/* loaded from: classes3.dex */
public final class Installation {
    private static final String INSTALLATION = "ACRA-INSTALLATION";

    private Installation() {
    }

    public static synchronized String id(Context context) {
        File file = new File(context.getFilesDir(), INSTALLATION);
        try {
            if (!file.exists()) {
                IOUtils.writeStringToFile(file, UUID.randomUUID().toString());
            }
            return new StreamReader(file).read();
        } catch (IOException | RuntimeException e) {
            ACRA.log.w(ACRA.LOG_TAG, "Couldn't retrieve InstallationId for " + context.getPackageName(), e);
            return "Couldn't retrieve InstallationId";
        }
    }
}
