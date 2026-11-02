package com.ibm.icu.impl;

import com.app.orsozoxi.Views.CustomCopticTextView;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.lang.UCharacterEnums;
import com.ibm.icu.text.RuleBasedBreakIterator;
import com.ibm.icu.util.CodePointTrie;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public final class RBBIDataWrapper {
    public static final int ACCEPTING = 0;
    public static final int ACCEPTING_UNCONDITIONAL = 1;
    public static final int DATA_FORMAT = 1114794784;
    public static final int DH_CATCOUNT = 3;
    public static final int DH_FORMATVERSION = 1;
    public static final int DH_FTABLE = 4;
    public static final int DH_FTABLELEN = 5;
    public static final int DH_LENGTH = 2;
    public static final int DH_MAGIC = 0;
    public static final int DH_RTABLE = 6;
    public static final int DH_RTABLELEN = 7;
    public static final int DH_RULESOURCE = 10;
    public static final int DH_RULESOURCELEN = 11;
    public static final int DH_SIZE = 20;
    public static final int DH_STATUSTABLE = 12;
    public static final int DH_STATUSTABLELEN = 13;
    public static final int DH_TRIE = 8;
    public static final int DH_TRIELEN = 9;
    public static final int FORMAT_VERSION = 100663296;
    private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();
    public static final int LOOKAHEAD = 1;
    public static final int NEXTSTATES = 3;
    public static final int RBBI_8BITS_ROWS = 4;
    public static final int RBBI_BOF_REQUIRED = 2;
    public static final int RBBI_LOOKAHEAD_HARD_BREAK = 1;
    public static final int TAGSIDX = 2;
    public RBBIStateTable fFTable;
    public RBBIDataHeader fHeader;
    public RBBIStateTable fRTable;
    public String fRuleSource;
    public int[] fStatusTable;
    public CodePointTrie fTrie;

    public static final class RBBIDataHeader {
        public int fCatCount;
        int fFTable;
        int fFTableLen;
        int fLength;
        int fRTable;
        int fRTableLen;
        int fRuleSource;
        int fRuleSourceLen;
        int fStatusTable;
        int fStatusTableLen;
        int fTrie;
        int fTrieLen;
        int fMagic = 0;
        byte[] fFormatVersion = new byte[4];
    }

    public static class RBBIStateTable {
        public static int fHeaderSize = 20;
        public int fDictCategoriesStart;
        public int fFlags;
        public int fLookAheadResultsSize;
        public int fNumStates;
        public int fRowLen;
        public char[] fTable;

        static RBBIStateTable get(ByteBuffer byteBuffer, int i) throws IOException {
            if (i == 0) {
                return null;
            }
            if (i < fHeaderSize) {
                throw new IOException("Invalid RBBI state table length.");
            }
            RBBIStateTable rBBIStateTable = new RBBIStateTable();
            rBBIStateTable.fNumStates = byteBuffer.getInt();
            rBBIStateTable.fRowLen = byteBuffer.getInt();
            rBBIStateTable.fDictCategoriesStart = byteBuffer.getInt();
            rBBIStateTable.fLookAheadResultsSize = byteBuffer.getInt();
            int i2 = byteBuffer.getInt();
            rBBIStateTable.fFlags = i2;
            int i3 = i - fHeaderSize;
            if ((i2 & 4) == 4) {
                rBBIStateTable.fTable = new char[i3];
                for (int i4 = 0; i4 < i3; i4++) {
                    rBBIStateTable.fTable[i4] = (char) (byteBuffer.get() & 255);
                }
                ICUBinary.skipBytes(byteBuffer, i3 & 1);
            } else {
                rBBIStateTable.fTable = ICUBinary.getChars(byteBuffer, i3 / 2, i3 & 1);
            }
            return rBBIStateTable;
        }

        public int put(DataOutputStream dataOutputStream) throws IOException {
            dataOutputStream.writeInt(this.fNumStates);
            dataOutputStream.writeInt(this.fRowLen);
            dataOutputStream.writeInt(this.fDictCategoriesStart);
            dataOutputStream.writeInt(this.fLookAheadResultsSize);
            dataOutputStream.writeInt(this.fFlags);
            if ((this.fFlags & 4) == 4) {
                int i = this.fRowLen * this.fNumStates;
                for (int i2 = 0; i2 < i; i2++) {
                    dataOutputStream.writeByte((byte) (this.fTable[i2] & 255));
                }
            } else {
                int i3 = (this.fRowLen * this.fNumStates) / 2;
                for (int i4 = 0; i4 < i3; i4++) {
                    dataOutputStream.writeChar(this.fTable[i4]);
                }
            }
            int i5 = fHeaderSize + (this.fRowLen * this.fNumStates);
            while (i5 % 8 != 0) {
                dataOutputStream.writeByte(0);
                i5++;
            }
            return i5;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof RBBIStateTable)) {
                return false;
            }
            RBBIStateTable rBBIStateTable = (RBBIStateTable) obj;
            if (this.fNumStates == rBBIStateTable.fNumStates && this.fRowLen == rBBIStateTable.fRowLen && this.fDictCategoriesStart == rBBIStateTable.fDictCategoriesStart && this.fLookAheadResultsSize == rBBIStateTable.fLookAheadResultsSize && this.fFlags == rBBIStateTable.fFlags) {
                return Arrays.equals(this.fTable, rBBIStateTable.fTable);
            }
            return false;
        }
    }

    public static boolean equals(RBBIStateTable rBBIStateTable, RBBIStateTable rBBIStateTable2) {
        if (rBBIStateTable == rBBIStateTable2) {
            return true;
        }
        if (rBBIStateTable == null || rBBIStateTable2 == null) {
            return false;
        }
        return rBBIStateTable.equals(rBBIStateTable2);
    }

    private static final class IsAcceptable implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override // com.ibm.icu.impl.ICUBinary.Authenticate
        public boolean isDataVersionAcceptable(byte[] bArr) {
            return (((bArr[0] << UCharacterEnums.ECharacterCategory.MATH_SYMBOL) + (bArr[1] << 16)) + (bArr[2] << 8)) + bArr[3] == 100663296;
        }
    }

    public int getRowIndex(int i) {
        return i * (this.fHeader.fCatCount + 3);
    }

    RBBIDataWrapper() {
    }

    public static RBBIDataWrapper get(ByteBuffer byteBuffer) throws IOException {
        RBBIDataWrapper rBBIDataWrapper = new RBBIDataWrapper();
        ICUBinary.readHeader(byteBuffer, DATA_FORMAT, IS_ACCEPTABLE);
        RBBIDataHeader rBBIDataHeader = new RBBIDataHeader();
        rBBIDataWrapper.fHeader = rBBIDataHeader;
        rBBIDataHeader.fMagic = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fFormatVersion[0] = byteBuffer.get();
        rBBIDataWrapper.fHeader.fFormatVersion[1] = byteBuffer.get();
        rBBIDataWrapper.fHeader.fFormatVersion[2] = byteBuffer.get();
        rBBIDataWrapper.fHeader.fFormatVersion[3] = byteBuffer.get();
        rBBIDataWrapper.fHeader.fLength = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fCatCount = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fFTable = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fFTableLen = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fRTable = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fRTableLen = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fTrie = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fTrieLen = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fRuleSource = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fRuleSourceLen = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fStatusTable = byteBuffer.getInt();
        rBBIDataWrapper.fHeader.fStatusTableLen = byteBuffer.getInt();
        ICUBinary.skipBytes(byteBuffer, 24);
        if (rBBIDataWrapper.fHeader.fMagic != 45472 || !IS_ACCEPTABLE.isDataVersionAcceptable(rBBIDataWrapper.fHeader.fFormatVersion)) {
            throw new IOException("Break Iterator Rule Data Magic Number Incorrect, or unsupported data version.");
        }
        if (rBBIDataWrapper.fHeader.fFTable < 80 || rBBIDataWrapper.fHeader.fFTable > rBBIDataWrapper.fHeader.fLength) {
            throw new IOException("Break iterator Rule data corrupt");
        }
        ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fFTable - 80);
        int i = rBBIDataWrapper.fHeader.fFTable;
        rBBIDataWrapper.fFTable = RBBIStateTable.get(byteBuffer, rBBIDataWrapper.fHeader.fFTableLen);
        ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fRTable - (i + rBBIDataWrapper.fHeader.fFTableLen));
        int i2 = rBBIDataWrapper.fHeader.fRTable;
        rBBIDataWrapper.fRTable = RBBIStateTable.get(byteBuffer, rBBIDataWrapper.fHeader.fRTableLen);
        ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fTrie - (i2 + rBBIDataWrapper.fHeader.fRTableLen));
        int i3 = rBBIDataWrapper.fHeader.fTrie;
        byteBuffer.mark();
        rBBIDataWrapper.fTrie = CodePointTrie.fromBinary(CodePointTrie.Type.FAST, null, byteBuffer);
        byteBuffer.reset();
        if (i3 > rBBIDataWrapper.fHeader.fStatusTable) {
            throw new IOException("Break iterator Rule data corrupt");
        }
        ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fStatusTable - i3);
        int i4 = rBBIDataWrapper.fHeader.fStatusTable;
        rBBIDataWrapper.fStatusTable = ICUBinary.getInts(byteBuffer, rBBIDataWrapper.fHeader.fStatusTableLen / 4, 3 & rBBIDataWrapper.fHeader.fStatusTableLen);
        int i5 = i4 + rBBIDataWrapper.fHeader.fStatusTableLen;
        if (i5 > rBBIDataWrapper.fHeader.fRuleSource) {
            throw new IOException("Break iterator Rule data corrupt");
        }
        ICUBinary.skipBytes(byteBuffer, rBBIDataWrapper.fHeader.fRuleSource - i5);
        int i6 = rBBIDataWrapper.fHeader.fRuleSource;
        rBBIDataWrapper.fRuleSource = new String(ICUBinary.getBytes(byteBuffer, rBBIDataWrapper.fHeader.fRuleSourceLen, 0), StandardCharsets.UTF_8);
        if (RuleBasedBreakIterator.fDebugEnv != null && RuleBasedBreakIterator.fDebugEnv.indexOf("data") >= 0) {
            rBBIDataWrapper.dump(System.out);
        }
        return rBBIDataWrapper;
    }

    public void dump(PrintStream printStream) {
        if (this.fFTable == null) {
            throw null;
        }
        printStream.println("RBBI Data Wrapper dump ...");
        printStream.println();
        printStream.println("Forward State Table");
        dumpTable(printStream, this.fFTable);
        printStream.println("Reverse State Table");
        dumpTable(printStream, this.fRTable);
        dumpCharCategories(printStream);
        printStream.println("Source Rules: " + this.fRuleSource);
    }

    public static String intToString(int i, int i2) {
        StringBuilder sb = new StringBuilder(i2);
        sb.append(i);
        while (sb.length() < i2) {
            sb.insert(0, TokenParser.SP);
        }
        return sb.toString();
    }

    public static String charToString(char c, int i) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(c);
        while (sb.length() < i) {
            sb.insert(0, TokenParser.SP);
        }
        return sb.toString();
    }

    public static String intToHexString(int i, int i2) {
        StringBuilder sb = new StringBuilder(i2);
        sb.append(Integer.toHexString(i));
        while (sb.length() < i2) {
            sb.insert(0, TokenParser.SP);
        }
        return sb.toString();
    }

    private void dumpTable(PrintStream printStream, RBBIStateTable rBBIStateTable) {
        if (rBBIStateTable == null || rBBIStateTable.fTable.length == 0) {
            printStream.println("  -- null -- ");
            return;
        }
        StringBuilder sb = new StringBuilder(" Row  Acc Look  Tag");
        for (char c = 0; c < this.fHeader.fCatCount; c = (char) (c + 1)) {
            sb.append(intToString(c, 5));
        }
        printStream.println(sb.toString());
        for (char c2 = 0; c2 < sb.length(); c2 = (char) (c2 + 1)) {
            printStream.print(LanguageTag.SEP);
        }
        printStream.println();
        for (char c3 = 0; c3 < rBBIStateTable.fNumStates; c3 = (char) (c3 + 1)) {
            dumpRow(printStream, rBBIStateTable, c3);
        }
        printStream.println();
    }

    private void dumpRow(PrintStream printStream, RBBIStateTable rBBIStateTable, char c) {
        StringBuilder sb = new StringBuilder((this.fHeader.fCatCount * 5) + 20);
        sb.append(intToString(c, 4));
        int rowIndex = getRowIndex(c);
        int i = rowIndex + 0;
        if (rBBIStateTable.fTable[i] != 0) {
            sb.append(intToString(rBBIStateTable.fTable[i], 5));
        } else {
            sb.append("     ");
        }
        int i2 = rowIndex + 1;
        if (rBBIStateTable.fTable[i2] != 0) {
            sb.append(intToString(rBBIStateTable.fTable[i2], 5));
        } else {
            sb.append("     ");
        }
        sb.append(intToString(rBBIStateTable.fTable[rowIndex + 2], 5));
        for (int i3 = 0; i3 < this.fHeader.fCatCount; i3++) {
            sb.append(intToString(rBBIStateTable.fTable[rowIndex + 3 + i3], 5));
        }
        printStream.println(sb);
    }

    private void dumpCharCategories(PrintStream printStream) {
        int i = this.fHeader.fCatCount + 1;
        String[] strArr = new String[i];
        int[] iArr = new int[i];
        for (int i2 = 0; i2 <= this.fHeader.fCatCount; i2++) {
            strArr[i2] = "";
        }
        printStream.println("\nCharacter Categories");
        printStream.println("--------------------");
        int i3 = -1;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 <= 1114111; i6++) {
            int i7 = this.fTrie.get(i6);
            if (i7 < 0 || i7 > this.fHeader.fCatCount) {
                printStream.println("Error, bad category " + Integer.toHexString(i7) + " for char " + Integer.toHexString(i6));
                break;
            }
            if (i7 != i3) {
                if (i3 >= 0) {
                    if (strArr[i3].length() > iArr[i3] + 70) {
                        iArr[i3] = strArr[i3].length() + 10;
                        strArr[i3] = strArr[i3] + "\n       ";
                    }
                    strArr[i3] = strArr[i3] + " " + Integer.toHexString(i4);
                    if (i5 != i4) {
                        strArr[i3] = strArr[i3] + LanguageTag.SEP + Integer.toHexString(i5);
                    }
                }
                i4 = i6;
                i3 = i7;
            }
            i5 = i6;
        }
        strArr[i3] = strArr[i3] + " " + Integer.toHexString(i4);
        if (i5 != i4) {
            strArr[i3] = strArr[i3] + LanguageTag.SEP + Integer.toHexString(i5);
        }
        for (int i8 = 0; i8 <= this.fHeader.fCatCount; i8++) {
            printStream.println(intToString(i8, 5) + CustomCopticTextView.TWO_CHINESE_BLANK + strArr[i8]);
        }
        printStream.println();
    }
}
