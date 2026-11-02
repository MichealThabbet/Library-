package com.sun.mail.imap;

import javax.mail.Provider;

/* loaded from: classes2.dex */
public class IMAPSSLProvider extends Provider {
    public IMAPSSLProvider() {
        super(Provider.Type.STORE, "imaps", IMAPSSLStore.class.getName(), "Oracle", null);
    }
}
