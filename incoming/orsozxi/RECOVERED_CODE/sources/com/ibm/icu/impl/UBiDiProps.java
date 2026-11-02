package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.Trie2;
import com.ibm.icu.text.DictionaryData;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;

/* loaded from: classes2.dex */
public final class UBiDiProps {
    private static final int BIDI_CONTROL_SHIFT = 11;
    private static final int BPT_MASK = 768;
    private static final int BPT_SHIFT = 8;
    private static final int CLASS_MASK = 31;
    private static final String DATA_FILE_NAME = "ubidi.icu";
    private static final String DATA_NAME = "ubidi";
    private static final String DATA_TYPE = "icu";
    private static final int ESC_MIRROR_DELTA = -4;
    private static final int FMT = 1114195049;
    public static final UBiDiProps INSTANCE;
    private static final int IS_MIRRORED_SHIFT = 12;
    private static final int IX_JG_LIMIT = 5;
    private static final int IX_JG_LIMIT2 = 7;
    private static final int IX_JG_START = 4;
    private static final int IX_JG_START2 = 6;
    private static final int IX_MAX_VALUES = 15;
    private static final int IX_MIRROR_LENGTH = 3;
    private static final int IX_TOP = 16;
    private static final int IX_TRIE_SIZE = 2;
    private static final int JOIN_CONTROL_SHIFT = 10;
    private static final int JT_MASK = 224;
    private static final int JT_SHIFT = 5;
    private static final int MAX_JG_MASK = 16711680;
    private static final int MAX_JG_SHIFT = 16;
    private static final int MIRROR_DELTA_SHIFT = 13;
    private static final int MIRROR_INDEX_SHIFT = 21;
    private int[] indexes;
    private byte[] jgArray;
    private byte[] jgArray2;
    private int[] mirrors;
    private Trie2_16 trie;

    private static final int getClassFromProps(int i) {
        return i & 31;
    }

    private static final boolean getFlagFromProps(int i, int i2) {
        return ((i >> i2) & 1) != 0;
    }

    private static final int getMirrorCodePoint(int i) {
        return i & DictionaryData.TRANSFORM_OFFSET_MASK;
    }

    private static final int getMirrorDeltaFromProps(int i) {
        return ((short) i) >> 13;
    }

    private static final int getMirrorIndex(int i) {
        return i >>> 21;
    }

    private UBiDiProps() throws IOException {
        readData(ICUBinary.getData(DATA_FILE_NAME));
    }

    private void readData(ByteBuffer byteBuffer) throws IOException {
        ICUBinary.readHeader(byteBuffer, FMT, new IsAcceptable());
        int i = byteBuffer.getInt();
        if (i < 16) {
            throw new IOException("indexes[0] too small in ubidi.icu");
        }
        int[] iArr = new int[i];
        this.indexes = iArr;
        iArr[0] = i;
        for (int i2 = 1; i2 < i; i2++) {
            this.indexes[i2] = byteBuffer.getInt();
        }
        Trie2_16 trie2_16CreateFromSerialized = Trie2_16.createFromSerialized(byteBuffer);
        this.trie = trie2_16CreateFromSerialized;
        int i3 = this.indexes[2];
        int serializedLength = trie2_16CreateFromSerialized.getSerializedLength();
        if (serializedLength > i3) {
            throw new IOException("ubidi.icu: not enough bytes for the trie");
        }
        ICUBinary.skipBytes(byteBuffer, i3 - serializedLength);
        int i4 = this.indexes[3];
        if (i4 > 0) {
            this.mirrors = ICUBinary.getInts(byteBuffer, i4, 0);
        }
        int[] iArr2 = this.indexes;
        byte[] bArr = new byte[iArr2[5] - iArr2[4]];
        this.jgArray = bArr;
        byteBuffer.get(bArr);
        int[] iArr3 = this.indexes;
        byte[] bArr2 = new byte[iArr3[7] - iArr3[6]];
        this.jgArray2 = bArr2;
        byteBuffer.get(bArr2);
    }

    private static final class IsAcceptable implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override // com.ibm.icu.impl.ICUBinary.Authenticate
        public boolean isDataVersionAcceptable(byte[] bArr) {
            return bArr[0] == 2;
        }
    }

    public final void addPropertyStarts(UnicodeSet unicodeSet) {
        Iterator<Trie2.Range> it2 = this.trie.iterator();
        while (it2.hasNext()) {
            Trie2.Range next = it2.next();
            if (next.leadSurrogate) {
                break;
            } else {
                unicodeSet.add(next.startCodePoint);
            }
        }
        int i = this.indexes[3];
        for (int i2 = 0; i2 < i; i2++) {
            int mirrorCodePoint = getMirrorCodePoint(this.mirrors[i2]);
            unicodeSet.add(mirrorCodePoint, mirrorCodePoint + 1);
        }
        int[] iArr = this.indexes;
        int i3 = iArr[4];
        int i4 = iArr[5];
        byte[] bArr = this.jgArray;
        while (true) {
            int i5 = i4 - i3;
            byte b = 0;
            for (int i6 = 0; i6 < i5; i6++) {
                byte b2 = bArr[i6];
                if (b2 != b) {
                    unicodeSet.add(i3);
                    b = b2;
                }
                i3++;
            }
            if (b != 0) {
                unicodeSet.add(i4);
            }
            int[] iArr2 = this.indexes;
            if (i4 != iArr2[5]) {
                return;
            }
            int i7 = iArr2[6];
            int i8 = iArr2[7];
            bArr = this.jgArray2;
            i3 = i7;
            i4 = i8;
        }
    }

    public final int getMaxValue(int i) {
        int i2 = this.indexes[15];
        if (i == 4096) {
            return i2 & 31;
        }
        if (i == 4117) {
            return (i2 & 768) >> 8;
        }
        if (i == 4102) {
            return (MAX_JG_MASK & i2) >> 16;
        }
        if (i != 4103) {
            return -1;
        }
        return (i2 & 224) >> 5;
    }

    public final int getClass(int i) {
        return getClassFromProps(this.trie.get(i));
    }

    public final boolean isMirrored(int i) {
        return getFlagFromProps(this.trie.get(i), 12);
    }

    private final int getMirror(int i, int i2) {
        int mirrorDeltaFromProps = getMirrorDeltaFromProps(i2);
        if (mirrorDeltaFromProps != -4) {
            return i + mirrorDeltaFromProps;
        }
        int i3 = this.indexes[3];
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = this.mirrors[i4];
            int mirrorCodePoint = getMirrorCodePoint(i5);
            if (i == mirrorCodePoint) {
                return getMirrorCodePoint(this.mirrors[getMirrorIndex(i5)]);
            }
            if (i < mirrorCodePoint) {
                break;
            }
        }
        return i;
    }

    public final int getMirror(int i) {
        return getMirror(i, this.trie.get(i));
    }

    public final boolean isBidiControl(int i) {
        return getFlagFromProps(this.trie.get(i), 11);
    }

    public final boolean isJoinControl(int i) {
        return getFlagFromProps(this.trie.get(i), 10);
    }

    public final int getJoiningType(int i) {
        return (this.trie.get(i) & 224) >> 5;
    }

    public final int getJoiningGroup(int i) {
        byte b;
        int[] iArr = this.indexes;
        int i2 = iArr[4];
        int i3 = iArr[5];
        if (i2 <= i && i < i3) {
            b = this.jgArray[i - i2];
        } else {
            int[] iArr2 = this.indexes;
            int i4 = iArr2[6];
            int i5 = iArr2[7];
            if (i4 > i || i >= i5) {
                return 0;
            }
            b = this.jgArray2[i - i4];
        }
        return b & 255;
    }

    public final int getPairedBracketType(int i) {
        return (this.trie.get(i) & 768) >> 8;
    }

    public final int getPairedBracket(int i) {
        int i2 = this.trie.get(i);
        return (i2 & 768) == 0 ? i : getMirror(i, i2);
    }

    static {
        try {
            INSTANCE = new UBiDiProps();
        } catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }
}
