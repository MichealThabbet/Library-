package org.acra.security;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import org.acra.ACRA;

/* loaded from: classes3.dex */
final class AssetKeyStoreFactory extends BaseKeyStoreFactory {
    private final String assetName;

    AssetKeyStoreFactory(String str, String str2) {
        super(str);
        this.assetName = str2;
    }

    @Override // org.acra.security.BaseKeyStoreFactory
    public InputStream getInputStream(Context context) {
        try {
            return context.getAssets().open(this.assetName);
        } catch (IOException e) {
            ACRA.log.e(ACRA.LOG_TAG, "Could not open certificate in asset://" + this.assetName, e);
            return null;
        }
    }
}
