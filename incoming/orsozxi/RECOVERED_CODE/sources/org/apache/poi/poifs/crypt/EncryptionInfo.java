package org.apache.poi.poifs.crypt;

import java.io.IOException;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/* loaded from: classes3.dex */
public class EncryptionInfo {
    private final int encryptionFlags;
    private final EncryptionHeader header;
    private final EncryptionVerifier verifier;
    private final int versionMajor;
    private final int versionMinor;

    public EncryptionInfo(POIFSFileSystem pOIFSFileSystem) throws IOException {
        DocumentInputStream documentInputStreamCreateDocumentInputStream = pOIFSFileSystem.createDocumentInputStream("EncryptionInfo");
        this.versionMajor = documentInputStreamCreateDocumentInputStream.readShort();
        this.versionMinor = documentInputStreamCreateDocumentInputStream.readShort();
        this.encryptionFlags = documentInputStreamCreateDocumentInputStream.readInt();
        documentInputStreamCreateDocumentInputStream.readInt();
        EncryptionHeader encryptionHeader = new EncryptionHeader(documentInputStreamCreateDocumentInputStream);
        this.header = encryptionHeader;
        if (encryptionHeader.getAlgorithm() == 26625) {
            this.verifier = new EncryptionVerifier(documentInputStreamCreateDocumentInputStream, 20);
        } else {
            this.verifier = new EncryptionVerifier(documentInputStreamCreateDocumentInputStream, 32);
        }
    }

    public int getVersionMajor() {
        return this.versionMajor;
    }

    public int getVersionMinor() {
        return this.versionMinor;
    }

    public int getEncryptionFlags() {
        return this.encryptionFlags;
    }

    public EncryptionHeader getHeader() {
        return this.header;
    }

    public EncryptionVerifier getVerifier() {
        return this.verifier;
    }
}
