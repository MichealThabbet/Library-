package com.google.android.gms.internal.firebase_messaging;

import java.io.OutputStream;

/* compiled from: com.google.firebase:firebase-messaging@@21.1.0 */
/* loaded from: classes.dex */
final class zze extends OutputStream {
    zze() {
    }

    public final String toString() {
        return "ByteStreams.nullOutputStream()";
    }

    @Override // java.io.OutputStream
    public final void write(int i) {
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr) {
        if (bArr == null) {
            throw null;
        }
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw null;
        }
    }
}
