package org.acra.security;

import android.content.Context;
import java.security.KeyStore;

/* loaded from: classes3.dex */
public class NoKeyStoreFactory implements KeyStoreFactory {
    @Override // org.acra.security.KeyStoreFactory
    public KeyStore create(Context context) {
        return null;
    }
}
