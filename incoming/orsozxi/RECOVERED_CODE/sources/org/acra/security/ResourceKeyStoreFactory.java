package org.acra.security;

import android.content.Context;
import java.io.InputStream;

/* loaded from: classes3.dex */
final class ResourceKeyStoreFactory extends BaseKeyStoreFactory {
    private final int rawRes;

    ResourceKeyStoreFactory(String str, int i) {
        super(str);
        this.rawRes = i;
    }

    @Override // org.acra.security.BaseKeyStoreFactory
    public InputStream getInputStream(Context context) {
        return context.getResources().openRawResource(this.rawRes);
    }
}
