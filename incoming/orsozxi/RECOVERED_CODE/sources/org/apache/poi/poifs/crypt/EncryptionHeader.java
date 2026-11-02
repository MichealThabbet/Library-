package org.apache.poi.poifs.crypt;

import java.io.IOException;
import org.apache.poi.poifs.filesystem.DocumentInputStream;

/* loaded from: classes3.dex */
public class EncryptionHeader {
    public static final int ALGORITHM_AES_128 = 26126;
    public static final int ALGORITHM_AES_192 = 26127;
    public static final int ALGORITHM_AES_256 = 26128;
    public static final int ALGORITHM_RC4 = 26625;
    public static final int HASH_SHA1 = 32772;
    public static final int PROVIDER_AES = 24;
    public static final int PROVIDER_RC4 = 1;
    private final int algorithm;
    private final String cspName;
    private final int flags;
    private final int hashAlgorithm;
    private final int keySize;
    private final int providerType;
    private final int sizeExtra;

    public EncryptionHeader(DocumentInputStream documentInputStream) throws IOException {
        this.flags = documentInputStream.readInt();
        this.sizeExtra = documentInputStream.readInt();
        this.algorithm = documentInputStream.readInt();
        this.hashAlgorithm = documentInputStream.readInt();
        this.keySize = documentInputStream.readInt();
        this.providerType = documentInputStream.readInt();
        documentInputStream.readLong();
        StringBuilder sb = new StringBuilder();
        while (true) {
            char c = (char) documentInputStream.readShort();
            if (c != 0) {
                sb.append(c);
            } else {
                this.cspName = sb.toString();
                return;
            }
        }
    }

    public int getFlags() {
        return this.flags;
    }

    public int getSizeExtra() {
        return this.sizeExtra;
    }

    public int getAlgorithm() {
        return this.algorithm;
    }

    public int getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public int getKeySize() {
        return this.keySize;
    }

    public int getProviderType() {
        return this.providerType;
    }

    public String getCspName() {
        return this.cspName;
    }
}
