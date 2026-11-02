package org.acra.security;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.acra.ACRA;

/* loaded from: classes3.dex */
final class FileKeyStoreFactory extends BaseKeyStoreFactory {
    private final String filePath;

    FileKeyStoreFactory(String str, String str2) {
        super(str);
        this.filePath = str2;
    }

    @Override // org.acra.security.BaseKeyStoreFactory
    public InputStream getInputStream(Context context) {
        try {
            return new FileInputStream(this.filePath);
        } catch (FileNotFoundException e) {
            ACRA.log.e(ACRA.LOG_TAG, "Could not find File " + this.filePath, e);
            return null;
        }
    }
}
