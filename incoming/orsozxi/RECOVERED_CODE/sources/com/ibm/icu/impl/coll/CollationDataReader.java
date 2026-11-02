package com.ibm.icu.impl.coll;

import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.Trie2_32;
import com.ibm.icu.impl.USerializedSet;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ICUException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

/* loaded from: classes2.dex */
final class CollationDataReader {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DATA_FORMAT = 1430482796;
    private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();
    static final int IX_CE32S_OFFSET = 11;
    static final int IX_CES_OFFSET = 9;
    static final int IX_COMPRESSIBLE_BYTES_OFFSET = 17;
    static final int IX_CONTEXTS_OFFSET = 13;
    static final int IX_FAST_LATIN_TABLE_OFFSET = 15;
    static final int IX_INDEXES_LENGTH = 0;
    static final int IX_JAMO_CE32S_START = 4;
    static final int IX_OPTIONS = 1;
    static final int IX_REORDER_CODES_OFFSET = 5;
    static final int IX_REORDER_TABLE_OFFSET = 6;
    static final int IX_RESERVED10_OFFSET = 10;
    static final int IX_RESERVED18_OFFSET = 18;
    static final int IX_RESERVED2 = 2;
    static final int IX_RESERVED3 = 3;
    static final int IX_RESERVED8_OFFSET = 8;
    static final int IX_ROOT_ELEMENTS_OFFSET = 12;
    static final int IX_SCRIPTS_OFFSET = 16;
    static final int IX_TOTAL_SIZE = 19;
    static final int IX_TRIE_OFFSET = 7;
    static final int IX_UNSAFE_BWD_OFFSET = 14;

    static void read(CollationTailoring collationTailoring, ByteBuffer byteBuffer, CollationTailoring collationTailoring2) throws IOException {
        int i;
        int[] ints;
        int i2;
        byte[] bArr;
        int[] iArr;
        int i3;
        CollationData collationData;
        int[] iArr2;
        collationTailoring2.version = ICUBinary.readHeader(byteBuffer, DATA_FORMAT, IS_ACCEPTABLE);
        if (collationTailoring != null && collationTailoring.getUCAVersion() != collationTailoring2.getUCAVersion()) {
            throw new ICUException("Tailoring UCA version differs from base data UCA version");
        }
        int iRemaining = byteBuffer.remaining();
        if (iRemaining < 8) {
            throw new ICUException("not enough bytes");
        }
        int i4 = byteBuffer.getInt();
        if (i4 < 2 || iRemaining < i4 * 4) {
            throw new ICUException("not enough indexes");
        }
        int[] iArr3 = new int[20];
        iArr3[0] = i4;
        for (int i5 = 1; i5 < i4 && i5 < 20; i5++) {
            iArr3[i5] = byteBuffer.getInt();
        }
        for (int i6 = i4; i6 < 20; i6++) {
            iArr3[i6] = -1;
        }
        if (i4 > 20) {
            ICUBinary.skipBytes(byteBuffer, (i4 - 20) * 4);
        }
        if (i4 > 19) {
            i = iArr3[19];
        } else {
            i = i4 > 5 ? iArr3[i4 - 1] : 0;
        }
        if (iRemaining < i) {
            throw new ICUException("not enough bytes");
        }
        CollationData collationData2 = collationTailoring == null ? null : collationTailoring.data;
        int i7 = iArr3[6] - iArr3[5];
        if (i7 >= 4) {
            if (collationData2 == null) {
                throw new ICUException("Collation base data must not reorder scripts");
            }
            int i8 = i7 / 4;
            ints = ICUBinary.getInts(byteBuffer, i8, i7 & 3);
            int i9 = 0;
            while (i9 < i8 && (ints[(i8 - i9) - 1] & SupportMenu.CATEGORY_MASK) != 0) {
                i9++;
            }
            i2 = i8 - i9;
        } else {
            ICUBinary.skipBytes(byteBuffer, i7);
            ints = new int[0];
            i2 = 0;
        }
        int i10 = iArr3[7] - iArr3[6];
        if (i10 < 256) {
            bArr = null;
        } else {
            if (i2 == 0) {
                throw new ICUException("Reordering table without reordering codes");
            }
            bArr = new byte[256];
            byteBuffer.get(bArr);
            i10 += InputDeviceCompat.SOURCE_ANY;
        }
        ICUBinary.skipBytes(byteBuffer, i10);
        if (collationData2 != null) {
            i3 = i2;
            iArr = ints;
            if (collationData2.numericPrimary != (iArr3[1] & 4278190080L)) {
                throw new ICUException("Tailoring numeric primary weight differs from base data");
            }
        } else {
            iArr = ints;
            i3 = i2;
        }
        int i11 = iArr3[8] - iArr3[7];
        if (i11 >= 8) {
            collationTailoring2.ensureOwnedData();
            collationData = collationTailoring2.ownedData;
            collationData.base = collationData2;
            collationData.numericPrimary = iArr3[1] & 4278190080L;
            Trie2_32 trie2_32CreateFromSerialized = Trie2_32.createFromSerialized(byteBuffer);
            collationTailoring2.trie = trie2_32CreateFromSerialized;
            collationData.trie = trie2_32CreateFromSerialized;
            int serializedLength = collationData.trie.getSerializedLength();
            if (serializedLength > i11) {
                throw new ICUException("Not enough bytes for the mappings trie");
            }
            i11 -= serializedLength;
        } else if (collationData2 != null) {
            collationTailoring2.data = collationData2;
            collationData = null;
        } else {
            throw new ICUException("Missing collation data mappings");
        }
        ICUBinary.skipBytes(byteBuffer, i11);
        ICUBinary.skipBytes(byteBuffer, iArr3[9] - iArr3[8]);
        int i12 = iArr3[10] - iArr3[9];
        if (i12 >= 8) {
            if (collationData == null) {
                throw new ICUException("Tailored ces without tailored trie");
            }
            collationData.ces = ICUBinary.getLongs(byteBuffer, i12 / 8, i12 & 7);
        } else {
            ICUBinary.skipBytes(byteBuffer, i12);
        }
        ICUBinary.skipBytes(byteBuffer, iArr3[11] - iArr3[10]);
        int i13 = iArr3[12] - iArr3[11];
        if (i13 >= 4) {
            if (collationData == null) {
                throw new ICUException("Tailored ce32s without tailored trie");
            }
            collationData.ce32s = ICUBinary.getInts(byteBuffer, i13 / 4, i13 & 3);
        } else {
            ICUBinary.skipBytes(byteBuffer, i13);
        }
        int i14 = iArr3[4];
        if (i14 >= 0) {
            if (collationData == null || collationData.ce32s == null) {
                throw new ICUException("JamoCE32sStart index into non-existent ce32s[]");
            }
            collationData.jamoCE32s = new int[67];
            System.arraycopy(collationData.ce32s, i14, collationData.jamoCE32s, 0, 67);
        } else if (collationData != null) {
            if (collationData2 != null) {
                collationData.jamoCE32s = collationData2.jamoCE32s;
            } else {
                throw new ICUException("Missing Jamo CE32s for Hangul processing");
            }
        }
        int i15 = iArr3[13] - iArr3[12];
        if (i15 >= 4) {
            int i16 = i15 / 4;
            if (collationData == null) {
                throw new ICUException("Root elements but no mappings");
            }
            if (i16 <= 4) {
                throw new ICUException("Root elements array too short");
            }
            collationData.rootElements = new long[i16];
            for (int i17 = 0; i17 < i16; i17++) {
                collationData.rootElements[i17] = byteBuffer.getInt() & 4294967295L;
            }
            if (collationData.rootElements[3] != 83887360) {
                throw new ICUException("Common sec/ter weights in base data differ from the hardcoded value");
            }
            if ((collationData.rootElements[4] >>> 24) < 69) {
                throw new ICUException("[fixed last secondary common byte] is too low");
            }
            i15 &= 3;
        }
        ICUBinary.skipBytes(byteBuffer, i15);
        int i18 = iArr3[14] - iArr3[13];
        if (i18 >= 2) {
            if (collationData == null) {
                throw new ICUException("Tailored contexts without tailored trie");
            }
            collationData.contexts = ICUBinary.getString(byteBuffer, i18 / 2, i18 & 1);
        } else {
            ICUBinary.skipBytes(byteBuffer, i18);
        }
        int i19 = iArr3[15] - iArr3[14];
        if (i19 >= 2) {
            if (collationData == null) {
                throw new ICUException("Unsafe-backward-set but no mappings");
            }
            if (collationData2 == null) {
                collationTailoring2.unsafeBackwardSet = new UnicodeSet(UTF16.TRAIL_SURROGATE_MIN_VALUE, 57343);
                collationData.nfcImpl.addLcccChars(collationTailoring2.unsafeBackwardSet);
            } else {
                collationTailoring2.unsafeBackwardSet = collationData2.unsafeBackwardSet.cloneAsThawed();
            }
            USerializedSet uSerializedSet = new USerializedSet();
            char c = 0;
            uSerializedSet.getSet(ICUBinary.getChars(byteBuffer, i19 / 2, i19 & 1), 0);
            int iCountRanges = uSerializedSet.countRanges();
            int[] iArr4 = new int[2];
            int i20 = 0;
            while (i20 < iCountRanges) {
                uSerializedSet.getRange(i20, iArr4);
                collationTailoring2.unsafeBackwardSet.add(iArr4[c], iArr4[1]);
                i20++;
                c = 0;
            }
            int i21 = 65536;
            int i22 = 55296;
            while (i22 < 56320) {
                if (!collationTailoring2.unsafeBackwardSet.containsNone(i21, i21 + 1023)) {
                    collationTailoring2.unsafeBackwardSet.add(i22);
                }
                i22++;
                i21 += 1024;
            }
            collationTailoring2.unsafeBackwardSet.freeze();
            collationData.unsafeBackwardSet = collationTailoring2.unsafeBackwardSet;
            i19 = 0;
        } else if (collationData != null) {
            if (collationData2 != null) {
                collationData.unsafeBackwardSet = collationData2.unsafeBackwardSet;
            } else {
                throw new ICUException("Missing unsafe-backward-set");
            }
        }
        ICUBinary.skipBytes(byteBuffer, i19);
        int i23 = iArr3[16] - iArr3[15];
        if (collationData != null) {
            collationData.fastLatinTable = null;
            collationData.fastLatinTableHeader = null;
            if (((iArr3[1] >> 16) & 255) == 2) {
                if (i23 >= 2) {
                    char c2 = byteBuffer.getChar();
                    int i24 = c2 & 255;
                    collationData.fastLatinTableHeader = new char[i24];
                    collationData.fastLatinTableHeader[0] = c2;
                    for (int i25 = 1; i25 < i24; i25++) {
                        collationData.fastLatinTableHeader[i25] = byteBuffer.getChar();
                    }
                    collationData.fastLatinTable = ICUBinary.getChars(byteBuffer, (i23 / 2) - i24, i23 & 1);
                    if ((c2 >> '\b') != 2) {
                        throw new ICUException("Fast-Latin table version differs from version in data header");
                    }
                    i23 = 0;
                } else if (collationData2 != null) {
                    collationData.fastLatinTable = collationData2.fastLatinTable;
                    collationData.fastLatinTableHeader = collationData2.fastLatinTableHeader;
                }
            }
        }
        ICUBinary.skipBytes(byteBuffer, i23);
        int i26 = iArr3[17] - iArr3[16];
        if (i26 >= 2) {
            if (collationData == null) {
                throw new ICUException("Script order data but no mappings");
            }
            CharBuffer charBufferAsCharBuffer = byteBuffer.asCharBuffer();
            collationData.numScripts = charBufferAsCharBuffer.get();
            int i27 = (i26 / 2) - ((collationData.numScripts + 1) + 16);
            if (i27 <= 2) {
                throw new ICUException("Script order data too short");
            }
            char[] cArr = new char[collationData.numScripts + 16];
            collationData.scriptsIndex = cArr;
            charBufferAsCharBuffer.get(cArr);
            char[] cArr2 = new char[i27];
            collationData.scriptStarts = cArr2;
            charBufferAsCharBuffer.get(cArr2);
            if (collationData.scriptStarts[0] != 0 || collationData.scriptStarts[1] != 768 || collationData.scriptStarts[i27 - 1] != 65280) {
                throw new ICUException("Script order data not valid");
            }
        } else if (collationData != null && collationData2 != null) {
            collationData.numScripts = collationData2.numScripts;
            collationData.scriptsIndex = collationData2.scriptsIndex;
            collationData.scriptStarts = collationData2.scriptStarts;
        }
        ICUBinary.skipBytes(byteBuffer, i26);
        int i28 = iArr3[18] - iArr3[17];
        if (i28 >= 256) {
            if (collationData == null) {
                throw new ICUException("Data for compressible primary lead bytes but no mappings");
            }
            collationData.compressibleBytes = new boolean[256];
            for (int i29 = 0; i29 < 256; i29++) {
                collationData.compressibleBytes[i29] = byteBuffer.get() != 0;
            }
            i28 += InputDeviceCompat.SOURCE_ANY;
        } else if (collationData != null) {
            if (collationData2 != null) {
                collationData.compressibleBytes = collationData2.compressibleBytes;
            } else {
                throw new ICUException("Missing data for compressible primary lead bytes");
            }
        }
        ICUBinary.skipBytes(byteBuffer, i28);
        ICUBinary.skipBytes(byteBuffer, iArr3[19] - iArr3[18]);
        CollationSettings collationSettings = (CollationSettings) collationTailoring2.settings.readOnly();
        int i30 = iArr3[1] & 65535;
        char[] cArr3 = new char[384];
        int options = CollationFastLatin.getOptions(collationTailoring2.data, collationSettings, cArr3);
        if (i30 != collationSettings.options || collationSettings.variableTop == 0) {
            iArr2 = iArr;
        } else {
            iArr2 = iArr;
            if (Arrays.equals(iArr2, collationSettings.reorderCodes) && options == collationSettings.fastLatinOptions && (options < 0 || Arrays.equals(cArr3, collationSettings.fastLatinPrimaries))) {
                return;
            }
        }
        CollationSettings collationSettings2 = (CollationSettings) collationTailoring2.settings.copyOnWrite();
        collationSettings2.options = i30;
        collationSettings2.variableTop = collationTailoring2.data.getLastPrimaryForGroup(collationSettings2.getMaxVariable() + 4096);
        if (collationSettings2.variableTop == 0) {
            throw new ICUException("The maxVariable could not be mapped to a variableTop");
        }
        if (i3 != 0) {
            collationSettings2.aliasReordering(collationData2, iArr2, i3, bArr);
        }
        collationSettings2.fastLatinOptions = CollationFastLatin.getOptions(collationTailoring2.data, collationSettings2, collationSettings2.fastLatinPrimaries);
    }

    private static final class IsAcceptable implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override // com.ibm.icu.impl.ICUBinary.Authenticate
        public boolean isDataVersionAcceptable(byte[] bArr) {
            return bArr[0] == 5;
        }
    }

    private CollationDataReader() {
    }
}
