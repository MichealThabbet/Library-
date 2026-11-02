package org.apache.poi.poifs.crypt;

import org.apache.poi.poifs.filesystem.DocumentInputStream;

/* loaded from: classes3.dex */
public class EncryptionVerifier {
    private final byte[] salt = new byte[16];
    private final byte[] verifier = new byte[16];
    private final byte[] verifierHash;
    private final int verifierHashSize;

    public EncryptionVerifier(DocumentInputStream documentInputStream, int i) {
        if (documentInputStream.readInt() != 16) {
            throw new RuntimeException("Salt size != 16 !?");
        }
        documentInputStream.readFully(this.salt);
        documentInputStream.readFully(this.verifier);
        this.verifierHashSize = documentInputStream.readInt();
        byte[] bArr = new byte[i];
        this.verifierHash = bArr;
        documentInputStream.readFully(bArr);
    }

    public byte[] getSalt() {
        return this.salt;
    }

    public byte[] getVerifier() {
        return this.verifier;
    }

    public byte[] getVerifierHash() {
        return this.verifierHash;
    }
}
