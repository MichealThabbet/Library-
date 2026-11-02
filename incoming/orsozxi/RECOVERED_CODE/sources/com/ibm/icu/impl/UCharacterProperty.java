package com.ibm.icu.impl;

import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.core.view.MotionEventCompat;
import com.huawei.hms.support.api.entity.core.JosStatusCodes;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.Trie2;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.lang.UProperty;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.CodePointMap;
import com.ibm.icu.util.CodePointTrie;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.VersionInfo;
import it.piapostolos.android.pinotes.R2;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.MissingResourceException;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes2.dex */
public final class UCharacterProperty {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int AGE_SHIFT_ = 24;
    private static final int ALPHABETIC_PROPERTY_ = 8;
    private static final int ASCII_HEX_DIGIT_PROPERTY_ = 7;
    private static final int BLOCK_MASK_ = 130816;
    private static final int BLOCK_SHIFT_ = 8;
    private static final int CGJ = 847;
    private static final int CR = 13;
    private static final int DASH_PROPERTY_ = 1;
    private static final String DATA_FILE_NAME_ = "uprops.icu";
    private static final int DATA_FORMAT = 1431335535;
    private static final int DECOMPOSITION_TYPE_MASK_ = 31;
    private static final int DEFAULT_IGNORABLE_CODE_POINT_PROPERTY_ = 19;
    private static final int DEL = 127;
    private static final int DEPRECATED_PROPERTY_ = 20;
    private static final int DIACRITIC_PROPERTY_ = 10;
    private static final int EAST_ASIAN_MASK_ = 917504;
    private static final int EAST_ASIAN_SHIFT_ = 17;
    private static final int EXTENDER_PROPERTY_ = 11;
    private static final int FIGURESP = 8199;
    private static final int FIRST_NIBBLE_SHIFT_ = 4;
    private static final int GCB_MASK = 992;
    private static final int GCB_SHIFT = 5;
    private static final int GC_ZP_MASK;
    private static final int GC_Z_MASK;
    private static final int GRAPHEME_BASE_PROPERTY_ = 26;
    private static final int GRAPHEME_EXTEND_PROPERTY_ = 13;
    private static final int GRAPHEME_LINK_PROPERTY_ = 14;
    private static final int HAIRSP = 8202;
    private static final int HEX_DIGIT_PROPERTY_ = 6;
    private static final int HYPHEN_PROPERTY_ = 2;
    private static final int IDEOGRAPHIC_PROPERTY_ = 9;
    private static final int IDS_BINARY_OPERATOR_PROPERTY_ = 15;
    private static final int IDS_TRINARY_OPERATOR_PROPERTY_ = 16;
    private static final int ID_CONTINUE_PROPERTY_ = 25;
    private static final int ID_START_PROPERTY_ = 24;
    private static final int INHSWAP = 8298;
    public static final UCharacterProperty INSTANCE;
    private static final int LAST_NIBBLE_MASK_ = 15;
    public static final char LATIN_CAPITAL_LETTER_I_WITH_DOT_ABOVE_ = 304;
    public static final char LATIN_SMALL_LETTER_DOTLESS_I_ = 305;
    public static final char LATIN_SMALL_LETTER_I_ = 'i';
    private static final int LB_MASK = 66060288;
    private static final int LB_SHIFT = 20;
    private static final int LOGICAL_ORDER_EXCEPTION_PROPERTY_ = 21;
    private static final int MATH_PROPERTY_ = 5;
    public static final int MAX_SCRIPT = 1023;
    static final int MY_MASK = 30;
    private static final int NBSP = 160;
    private static final int NL = 133;
    private static final int NNBSP = 8239;
    private static final int NOMDIG = 8303;
    private static final int NONCHARACTER_CODE_POINT_PROPERTY_ = 12;
    private static final int NTV_BASE60_START_ = 768;
    private static final int NTV_DECIMAL_START_ = 1;
    private static final int NTV_DIGIT_START_ = 11;
    private static final int NTV_FRACTION20_START_ = 804;
    private static final int NTV_FRACTION32_START_ = 828;
    private static final int NTV_FRACTION_START_ = 176;
    private static final int NTV_LARGE_START_ = 480;
    private static final int NTV_NONE_ = 0;
    private static final int NTV_NUMERIC_START_ = 21;
    private static final int NTV_RESERVED_START_ = 844;
    private static final int NUMERIC_TYPE_VALUE_SHIFT_ = 6;
    private static final int PATTERN_SYNTAX = 29;
    private static final int PATTERN_WHITE_SPACE = 30;
    private static final int PREPENDED_CONCATENATION_MARK = 31;
    private static final int PROPS_2_EMOJI = 28;
    private static final int PROPS_2_EMOJI_COMPONENT = 27;
    private static final int PROPS_2_EMOJI_MODIFIER = 30;
    private static final int PROPS_2_EMOJI_MODIFIER_BASE = 31;
    private static final int PROPS_2_EMOJI_PRESENTATION = 29;
    private static final int PROPS_2_EXTENDED_PICTOGRAPHIC = 26;
    private static final int QUOTATION_MARK_PROPERTY_ = 3;
    private static final int RADICAL_PROPERTY_ = 17;
    private static final int RLM = 8207;
    private static final int SB_MASK = 1015808;
    private static final int SB_SHIFT = 15;
    public static final int SCRIPT_HIGH_MASK = 3145728;
    public static final int SCRIPT_HIGH_SHIFT = 12;
    public static final int SCRIPT_LOW_MASK = 255;
    public static final int SCRIPT_X_MASK = 15728895;
    public static final int SCRIPT_X_WITH_COMMON = 4194304;
    public static final int SCRIPT_X_WITH_INHERITED = 8388608;
    public static final int SCRIPT_X_WITH_OTHER = 12582912;
    public static final int SRC_BIDI = 5;
    public static final int SRC_CASE = 4;
    public static final int SRC_CASE_AND_NORM = 7;
    public static final int SRC_CHAR = 1;
    public static final int SRC_CHAR_AND_PROPSVEC = 6;
    public static final int SRC_COUNT = 15;
    public static final int SRC_INPC = 12;
    public static final int SRC_INSC = 13;
    public static final int SRC_NAMES = 3;
    public static final int SRC_NFC = 8;
    public static final int SRC_NFC_CANON_ITER = 11;
    public static final int SRC_NFKC = 9;
    public static final int SRC_NFKC_CF = 10;
    public static final int SRC_NONE = 0;
    public static final int SRC_PROPSVEC = 2;
    public static final int SRC_VO = 14;
    private static final int S_TERM_PROPERTY_ = 27;
    private static final int TAB = 9;
    private static final int TERMINAL_PUNCTUATION_PROPERTY_ = 4;
    public static final int TYPE_MASK = 31;
    private static final int UNIFIED_IDEOGRAPH_PROPERTY_ = 18;
    private static final int U_A = 65;
    private static final int U_F = 70;
    private static final int U_FW_A = 65313;
    private static final int U_FW_F = 65318;
    private static final int U_FW_Z = 65338;
    private static final int U_FW_a = 65345;
    private static final int U_FW_f = 65350;
    private static final int U_FW_z = 65370;
    private static final int U_Z = 90;
    private static final int U_a = 97;
    private static final int U_f = 102;
    private static final int U_z = 122;
    private static final int VARIATION_SELECTOR_PROPERTY_ = 28;
    private static final int WB_MASK = 31744;
    private static final int WB_SHIFT = 10;
    private static final int WHITE_SPACE_PROPERTY_ = 0;
    private static final int WJ = 8288;
    private static final int XID_CONTINUE_PROPERTY_ = 23;
    private static final int XID_START_PROPERTY_ = 22;
    private static final int ZWNBSP = 65279;
    private static final int[] gcbToHst;
    BinaryProperty[] binProps;
    IntProperty[] intProps;
    int m_additionalColumnsCount_;
    Trie2_16 m_additionalTrie_;
    int[] m_additionalVectors_;
    int m_maxBlockScriptValue_;
    int m_maxJTGValue_;
    public char[] m_scriptExtensions_;
    public Trie2_16 m_trie_;
    public VersionInfo m_unicodeVersion_;
    private static final int GC_CN_MASK = getMask(0);
    private static final int GC_CC_MASK = getMask(15);
    private static final int GC_CS_MASK = getMask(18);
    private static final int GC_ZS_MASK = getMask(12);
    private static final int GC_ZL_MASK = getMask(13);

    public static int getEuropeanDigit(int i) {
        if (i > 122 && i < U_FW_A) {
            return -1;
        }
        if (i < 65) {
            return -1;
        }
        if ((i > 90 && i < 97) || i > U_FW_z) {
            return -1;
        }
        if (i > U_FW_Z && i < U_FW_a) {
            return -1;
        }
        if (i <= 122) {
            return (i + 10) - (i > 90 ? 97 : 65);
        }
        return i <= U_FW_Z ? (i + 10) - U_FW_A : (i + 10) - U_FW_a;
    }

    public static final int getMask(int i) {
        return 1 << i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int getNumericTypeValue(int i) {
        return i >> 6;
    }

    public static final int mergeScriptCodeOrIndex(int i) {
        return (i & 255) | ((3145728 & i) >> 12);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int ntvGetType(int i) {
        if (i == 0) {
            return 0;
        }
        if (i < 11) {
            return 1;
        }
        return i < 21 ? 2 : 3;
    }

    private static final class LayoutProps {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int DATA_FORMAT = 1281456495;
        private static final int IX_INPC_TRIE_TOP = 1;
        private static final int IX_INSC_TRIE_TOP = 2;
        private static final int IX_MAX_VALUES = 9;
        private static final int IX_VO_TRIE_TOP = 3;
        private static final int MAX_INPC_SHIFT = 24;
        private static final int MAX_INSC_SHIFT = 16;
        private static final int MAX_VO_SHIFT = 8;
        CodePointTrie inpcTrie;
        CodePointTrie inscTrie;
        int maxInpcValue;
        int maxInscValue;
        int maxVoValue;
        CodePointTrie voTrie;
        private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();
        static final LayoutProps INSTANCE = new LayoutProps();

        private static final class IsAcceptable implements ICUBinary.Authenticate {
            private IsAcceptable() {
            }

            @Override // com.ibm.icu.impl.ICUBinary.Authenticate
            public boolean isDataVersionAcceptable(byte[] bArr) {
                return bArr[0] == 1;
            }
        }

        LayoutProps() {
            this.inpcTrie = null;
            this.inscTrie = null;
            this.voTrie = null;
            this.maxInpcValue = 0;
            this.maxInscValue = 0;
            this.maxVoValue = 0;
            ByteBuffer requiredData = ICUBinary.getRequiredData("ulayout.icu");
            try {
                ICUBinary.readHeaderAndDataVersion(requiredData, DATA_FORMAT, IS_ACCEPTABLE);
                int iPosition = requiredData.position();
                int i = requiredData.getInt();
                if (i < 12) {
                    throw new ICUUncheckedIOException("Text layout properties data: not enough indexes");
                }
                int[] iArr = new int[i];
                iArr[0] = i;
                for (int i2 = 1; i2 < i; i2++) {
                    iArr[i2] = requiredData.getInt();
                }
                int i3 = iArr[1];
                if (i3 - (i * 4) >= 16) {
                    this.inpcTrie = CodePointTrie.fromBinary(null, null, requiredData);
                }
                ICUBinary.skipBytes(requiredData, i3 - (requiredData.position() - iPosition));
                int i4 = iArr[2];
                if (i4 - i3 >= 16) {
                    this.inscTrie = CodePointTrie.fromBinary(null, null, requiredData);
                }
                ICUBinary.skipBytes(requiredData, i4 - (requiredData.position() - iPosition));
                int i5 = iArr[3];
                if (i5 - i4 >= 16) {
                    this.voTrie = CodePointTrie.fromBinary(null, null, requiredData);
                }
                ICUBinary.skipBytes(requiredData, i5 - (requiredData.position() - iPosition));
                int i6 = iArr[9];
                this.maxInpcValue = i6 >>> 24;
                this.maxInscValue = (i6 >> 16) & 255;
                this.maxVoValue = (i6 >> 8) & 255;
            } catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }

        public UnicodeSet addPropertyStarts(int i, UnicodeSet unicodeSet) {
            CodePointTrie codePointTrie;
            switch (i) {
                case 12:
                    codePointTrie = this.inpcTrie;
                    break;
                case 13:
                    codePointTrie = this.inscTrie;
                    break;
                case 14:
                    codePointTrie = this.voTrie;
                    break;
                default:
                    throw new IllegalStateException();
            }
            if (codePointTrie == null) {
                throw new MissingResourceException("no data for one of the text layout properties; src=" + i, "LayoutProps", "");
            }
            CodePointMap.Range range = new CodePointMap.Range();
            for (int end = 0; codePointTrie.getRange(end, null, range); end = range.getEnd() + 1) {
                unicodeSet.add(end);
            }
            return unicodeSet;
        }
    }

    public final int getProperty(int i) {
        return this.m_trie_.get(i);
    }

    public int getAdditional(int i, int i2) {
        if (i2 >= this.m_additionalColumnsCount_) {
            return 0;
        }
        return this.m_additionalVectors_[this.m_additionalTrie_.get(i) + i2];
    }

    public VersionInfo getAge(int i) {
        int additional = getAdditional(i, 0) >> 24;
        return VersionInfo.getInstance((additional >> 4) & 15, additional & 15, 0, 0);
    }

    static {
        int mask = getMask(14);
        GC_ZP_MASK = mask;
        GC_Z_MASK = mask | GC_ZS_MASK | GC_ZL_MASK;
        gcbToHst = new int[]{0, 0, 0, 0, 1, 0, 4, 5, 3, 2};
        try {
            INSTANCE = new UCharacterProperty();
        } catch (IOException e) {
            throw new MissingResourceException(e.getMessage(), "", "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isgraphPOSIX(int i) {
        return (getMask(UCharacter.getType(i)) & (((GC_CC_MASK | GC_CS_MASK) | GC_CN_MASK) | GC_Z_MASK)) == 0;
    }

    private class BinaryProperty {
        int column;
        int mask;

        BinaryProperty(int i, int i2) {
            this.column = i;
            this.mask = i2;
        }

        BinaryProperty(int i) {
            this.column = i;
            this.mask = 0;
        }

        final int getSource() {
            if (this.mask == 0) {
                return this.column;
            }
            return 2;
        }

        boolean contains(int i) {
            return (UCharacterProperty.this.getAdditional(i, this.column) & this.mask) != 0;
        }
    }

    private class CaseBinaryProperty extends BinaryProperty {
        int which;

        CaseBinaryProperty(int i) {
            super(4);
            this.which = i;
        }

        @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
        boolean contains(int i) {
            return UCaseProps.INSTANCE.hasBinaryProperty(i, this.which);
        }
    }

    private class NormInertBinaryProperty extends BinaryProperty {
        int which;

        NormInertBinaryProperty(int i, int i2) {
            super(i);
            this.which = i2;
        }

        @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
        boolean contains(int i) {
            return Norm2AllModes.getN2WithImpl(this.which - 37).isInert(i);
        }
    }

    public boolean hasBinaryProperty(int i, int i2) {
        if (i2 < 0 || 65 <= i2) {
            return false;
        }
        return this.binProps[i2].contains(i);
    }

    public int getType(int i) {
        return getProperty(i) & 31;
    }

    private class IntProperty {
        int column;
        int mask;
        int shift;

        IntProperty(int i, int i2, int i3) {
            this.column = i;
            this.mask = i2;
            this.shift = i3;
        }

        IntProperty(int i) {
            this.column = i;
            this.mask = 0;
        }

        final int getSource() {
            if (this.mask == 0) {
                return this.column;
            }
            return 2;
        }

        int getValue(int i) {
            return (UCharacterProperty.this.getAdditional(i, this.column) & this.mask) >>> this.shift;
        }

        int getMaxValue(int i) {
            return (UCharacterProperty.this.getMaxValues(this.column) & this.mask) >>> this.shift;
        }
    }

    private class BiDiIntProperty extends IntProperty {
        BiDiIntProperty() {
            super(5);
        }

        @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
        int getMaxValue(int i) {
            return UBiDiProps.INSTANCE.getMaxValue(i);
        }
    }

    private class CombiningClassIntProperty extends IntProperty {
        @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
        int getMaxValue(int i) {
            return 255;
        }

        CombiningClassIntProperty(int i) {
            super(i);
        }
    }

    private class NormQuickCheckIntProperty extends IntProperty {
        int max;
        int which;

        NormQuickCheckIntProperty(int i, int i2, int i3) {
            super(i);
            this.which = i2;
            this.max = i3;
        }

        @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
        int getValue(int i) {
            return Norm2AllModes.getN2WithImpl(this.which - 4108).getQuickCheck(i);
        }

        @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
        int getMaxValue(int i) {
            return this.max;
        }
    }

    public int getIntPropertyValue(int i, int i2) {
        if (i2 < 4096) {
            if (i2 < 0 || i2 >= 65) {
                return 0;
            }
            return this.binProps[i2].contains(i) ? 1 : 0;
        }
        if (i2 < 4121) {
            return this.intProps[i2 - 4096].getValue(i);
        }
        if (i2 == 8192) {
            return getMask(getType(i));
        }
        return 0;
    }

    public int getIntPropertyMaxValue(int i) {
        if (i < 4096) {
            return (i < 0 || i >= 65) ? -1 : 1;
        }
        if (i < 4121) {
            return this.intProps[i - 4096].getMaxValue(i);
        }
        return -1;
    }

    final int getSource(int i) {
        if (i < 0) {
            return 0;
        }
        if (i < 65) {
            return this.binProps[i].getSource();
        }
        if (i < 4096) {
            return 0;
        }
        if (i < 4121) {
            return this.intProps[i - 4096].getSource();
        }
        if (i < 16384) {
            return (i == 8192 || i == 12288) ? 1 : 0;
        }
        if (i >= 16398) {
            return i != 28672 ? 0 : 2;
        }
        switch (i) {
            case 16384:
                return 2;
            case UProperty.BIDI_MIRRORING_GLYPH /* 16385 */:
                return 5;
            case 16386:
            case UProperty.LOWERCASE_MAPPING /* 16388 */:
            case UProperty.SIMPLE_CASE_FOLDING /* 16390 */:
            case UProperty.SIMPLE_LOWERCASE_MAPPING /* 16391 */:
            case UProperty.SIMPLE_TITLECASE_MAPPING /* 16392 */:
            case UProperty.SIMPLE_UPPERCASE_MAPPING /* 16393 */:
            case UProperty.TITLECASE_MAPPING /* 16394 */:
            case UProperty.UPPERCASE_MAPPING /* 16396 */:
                return 4;
            case UProperty.ISO_COMMENT /* 16387 */:
            case UProperty.NAME /* 16389 */:
            case UProperty.UNICODE_1_NAME /* 16395 */:
                return 3;
            default:
                return 0;
        }
    }

    public int getMaxValues(int i) {
        if (i == 0) {
            return this.m_maxBlockScriptValue_;
        }
        if (i != 2) {
            return 0;
        }
        return this.m_maxJTGValue_;
    }

    public int digit(int i) {
        int numericTypeValue = getNumericTypeValue(getProperty(i)) - 1;
        if (numericTypeValue <= 9) {
            return numericTypeValue;
        }
        return -1;
    }

    public int getNumericValue(int i) {
        int i2;
        int numericTypeValue = getNumericTypeValue(getProperty(i));
        if (numericTypeValue == 0) {
            return getEuropeanDigit(i);
        }
        if (numericTypeValue < 11) {
            return numericTypeValue - 1;
        }
        if (numericTypeValue < 21) {
            return numericTypeValue - 11;
        }
        if (numericTypeValue < 176) {
            return numericTypeValue - 21;
        }
        if (numericTypeValue < 480) {
            return -2;
        }
        if (numericTypeValue < 768) {
            int i3 = (numericTypeValue >> 5) - 14;
            int i4 = (numericTypeValue & 31) + 2;
            if (i4 >= 9 && (i4 != 9 || i3 > 2)) {
                return -2;
            }
            do {
                i3 *= 10;
                i4--;
            } while (i4 > 0);
            return i3;
        }
        if (numericTypeValue >= 804) {
            if (numericTypeValue < 844) {
            }
            return -2;
        }
        int i5 = (numericTypeValue >> 2) - 191;
        int i6 = (numericTypeValue & 3) + 1;
        if (i6 == 1) {
            return i5 * 60;
        }
        if (i6 == 2) {
            return i5 * R2.string.fab_transformation_scrim_behavior;
        }
        if (i6 == 3) {
            i2 = 216000;
        } else {
            if (i6 != 4) {
                return i5;
            }
            i2 = 12960000;
        }
        return i5 * i2;
    }

    public double getUnicodeNumericValue(int i) {
        int i2;
        double d;
        int numericTypeValue = getNumericTypeValue(getProperty(i));
        if (numericTypeValue == 0) {
            return -1.23456789E8d;
        }
        if (numericTypeValue < 11) {
            return numericTypeValue - 1;
        }
        if (numericTypeValue < 21) {
            return numericTypeValue - 11;
        }
        if (numericTypeValue < 176) {
            return numericTypeValue - 21;
        }
        if (numericTypeValue < 480) {
            return ((numericTypeValue >> 4) - 12) / ((numericTypeValue & 15) + 1);
        }
        if (numericTypeValue < 768) {
            int i3 = (numericTypeValue >> 5) - 14;
            int i4 = (numericTypeValue & 31) + 2;
            double d2 = i3;
            while (i4 >= 4) {
                d2 *= 10000.0d;
                i4 -= 4;
            }
            if (i4 == 1) {
                d = 10.0d;
            } else if (i4 == 2) {
                d = 100.0d;
            } else {
                if (i4 != 3) {
                    return d2;
                }
                d = 1000.0d;
            }
            return d2 * d;
        }
        if (numericTypeValue >= 804) {
            if (numericTypeValue < 828) {
                int i5 = numericTypeValue - 804;
                return (((i5 & 3) * 2) + 1) / (20 << (i5 >> 2));
            }
            if (numericTypeValue >= 844) {
                return -1.23456789E8d;
            }
            int i6 = numericTypeValue - 828;
            return (((i6 & 3) * 2) + 1) / (32 << (i6 >> 2));
        }
        int i7 = (numericTypeValue >> 2) - 191;
        int i8 = (numericTypeValue & 3) + 1;
        if (i8 == 1) {
            i7 *= 60;
        } else if (i8 != 2) {
            if (i8 != 3) {
                i2 = i8 == 4 ? 12960000 : 216000;
            }
            i7 *= i2;
        } else {
            i7 *= R2.string.fab_transformation_scrim_behavior;
        }
        return i7;
    }

    private UCharacterProperty() throws IOException {
        int i = 1;
        int i2 = 5;
        int i3 = 2;
        int i4 = 8;
        this.binProps = new BinaryProperty[]{new BinaryProperty(1, 256), new BinaryProperty(1, 128), new BinaryProperty(i2) { // from class: com.ibm.icu.impl.UCharacterProperty.1
            @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
            boolean contains(int i5) {
                return UBiDiProps.INSTANCE.isBidiControl(i5);
            }
        }, new BinaryProperty(i2) { // from class: com.ibm.icu.impl.UCharacterProperty.2
            @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
            boolean contains(int i5) {
                return UBiDiProps.INSTANCE.isMirrored(i5);
            }
        }, new BinaryProperty(1, 2), new BinaryProperty(1, 524288), new BinaryProperty(1, 1048576), new BinaryProperty(1, 1024), new BinaryProperty(1, 2048), new BinaryProperty(i4) { // from class: com.ibm.icu.impl.UCharacterProperty.3
            @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
            boolean contains(int i5) {
                Normalizer2Impl normalizer2Impl = Norm2AllModes.getNFCInstance().impl;
                return normalizer2Impl.isCompNo(normalizer2Impl.getNorm16(i5));
            }
        }, new BinaryProperty(1, 67108864), new BinaryProperty(1, 8192), new BinaryProperty(1, 16384), new BinaryProperty(1, 64), new BinaryProperty(1, 4), new BinaryProperty(1, 33554432), new BinaryProperty(1, 16777216), new BinaryProperty(1, 512), new BinaryProperty(1, 32768), new BinaryProperty(1, 65536), new BinaryProperty(i2) { // from class: com.ibm.icu.impl.UCharacterProperty.4
            @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
            boolean contains(int i5) {
                return UBiDiProps.INSTANCE.isJoinControl(i5);
            }
        }, new BinaryProperty(1, 2097152), new CaseBinaryProperty(22), new BinaryProperty(1, 32), new BinaryProperty(1, 4096), new BinaryProperty(1, 8), new BinaryProperty(1, 131072), new CaseBinaryProperty(27), new BinaryProperty(1, 16), new BinaryProperty(1, 262144), new CaseBinaryProperty(30), new BinaryProperty(1, 1), new BinaryProperty(1, 8388608), new BinaryProperty(1, 4194304), new CaseBinaryProperty(34), new BinaryProperty(1, 134217728), new BinaryProperty(1, SQLiteDatabase.CREATE_IF_NECESSARY), new NormInertBinaryProperty(8, 37), new NormInertBinaryProperty(9, 38), new NormInertBinaryProperty(8, 39), new NormInertBinaryProperty(9, 40), new BinaryProperty(11) { // from class: com.ibm.icu.impl.UCharacterProperty.5
            @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
            boolean contains(int i5) {
                return Norm2AllModes.getNFCInstance().impl.ensureCanonIterData().isCanonSegmentStarter(i5);
            }
        }, new BinaryProperty(1, 536870912), new BinaryProperty(1, BasicMeasure.EXACTLY), new BinaryProperty(6) { // from class: com.ibm.icu.impl.UCharacterProperty.6
            @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
            boolean contains(int i5) {
                return UCharacter.isUAlphabetic(i5) || UCharacter.isDigit(i5);
            }
        }, new BinaryProperty(i) { // from class: com.ibm.icu.impl.UCharacterProperty.7
            @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
            boolean contains(int i5) {
                return i5 <= 159 ? i5 == 9 || i5 == 32 : UCharacter.getType(i5) == 12;
            }
        }, new BinaryProperty(i) { // from class: com.ibm.icu.impl.UCharacterProperty.8
            @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
            boolean contains(int i5) {
                return UCharacterProperty.isgraphPOSIX(i5);
            }
        }, new BinaryProperty(i) { // from class: com.ibm.icu.impl.UCharacterProperty.9
            @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
            boolean contains(int i5) {
                return UCharacter.getType(i5) == 12 || UCharacterProperty.isgraphPOSIX(i5);
            }
        }, new BinaryProperty(i) { // from class: com.ibm.icu.impl.UCharacterProperty.10
            @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
            boolean contains(int i5) {
                return (i5 <= 102 && i5 >= 65 && (i5 <= 70 || i5 >= 97)) || (i5 >= UCharacterProperty.U_FW_A && i5 <= UCharacterProperty.U_FW_f && (i5 <= UCharacterProperty.U_FW_F || i5 >= UCharacterProperty.U_FW_a)) || UCharacter.getType(i5) == 9;
            }
        }, new CaseBinaryProperty(49), new CaseBinaryProperty(50), new CaseBinaryProperty(51), new CaseBinaryProperty(52), new CaseBinaryProperty(53), new BinaryProperty(7) { // from class: com.ibm.icu.impl.UCharacterProperty.11
            @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
            boolean contains(int i5) {
                String decomposition = Norm2AllModes.getNFCInstance().impl.getDecomposition(i5);
                if (decomposition != null) {
                    i5 = decomposition.codePointAt(0);
                    if (Character.charCount(i5) != decomposition.length()) {
                        i5 = -1;
                    }
                } else if (i5 < 0) {
                    return false;
                }
                if (i5 >= 0) {
                    UCaseProps uCaseProps = UCaseProps.INSTANCE;
                    UCaseProps.dummyStringBuilder.setLength(0);
                    return uCaseProps.toFullFolding(i5, UCaseProps.dummyStringBuilder, 0) >= 0;
                }
                return !UCharacter.foldCase(decomposition, true).equals(decomposition);
            }
        }, new CaseBinaryProperty(55), new BinaryProperty(10) { // from class: com.ibm.icu.impl.UCharacterProperty.12
            @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
            boolean contains(int i5) throws IOException {
                Normalizer2Impl normalizer2Impl = Norm2AllModes.getNFKC_CFInstance().impl;
                String strValueOf = UTF16.valueOf(i5);
                normalizer2Impl.compose(strValueOf, 0, strValueOf.length(), false, true, new Normalizer2Impl.ReorderingBuffer(normalizer2Impl, new StringBuilder(), 5));
                return !Normalizer2Impl.UTF16Plus.equal(r0, strValueOf);
            }
        }, new BinaryProperty(2, SQLiteDatabase.CREATE_IF_NECESSARY), new BinaryProperty(2, 536870912), new BinaryProperty(2, BasicMeasure.EXACTLY), new BinaryProperty(2, Integer.MIN_VALUE), new BinaryProperty(2, 134217728), new BinaryProperty(i3) { // from class: com.ibm.icu.impl.UCharacterProperty.13
            @Override // com.ibm.icu.impl.UCharacterProperty.BinaryProperty
            boolean contains(int i5) {
                return 127462 <= i5 && i5 <= 127487;
            }
        }, new BinaryProperty(1, Integer.MIN_VALUE), new BinaryProperty(2, 67108864)};
        IntProperty[] intPropertyArr = {new BiDiIntProperty() { // from class: com.ibm.icu.impl.UCharacterProperty.14
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                return UBiDiProps.INSTANCE.getClass(i5);
            }
        }, new IntProperty(0, BLOCK_MASK_, 8), new CombiningClassIntProperty(i4) { // from class: com.ibm.icu.impl.UCharacterProperty.15
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                return Normalizer2.getNFDInstance().getCombiningClass(i5);
            }
        }, new IntProperty(2, 31, 0), new IntProperty(0, 917504, 17), new IntProperty(i) { // from class: com.ibm.icu.impl.UCharacterProperty.16
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getMaxValue(int i5) {
                return 29;
            }

            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                return UCharacterProperty.this.getType(i5);
            }
        }, new BiDiIntProperty() { // from class: com.ibm.icu.impl.UCharacterProperty.17
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                return UBiDiProps.INSTANCE.getJoiningGroup(i5);
            }
        }, new BiDiIntProperty() { // from class: com.ibm.icu.impl.UCharacterProperty.18
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                return UBiDiProps.INSTANCE.getJoiningType(i5);
            }
        }, new IntProperty(2, LB_MASK, 20), new IntProperty(i) { // from class: com.ibm.icu.impl.UCharacterProperty.19
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getMaxValue(int i5) {
                return 3;
            }

            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                return UCharacterProperty.ntvGetType(UCharacterProperty.getNumericTypeValue(UCharacterProperty.this.getProperty(i5)));
            }
        }, new IntProperty(i3) { // from class: com.ibm.icu.impl.UCharacterProperty.20
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                return UScript.getScript(i5);
            }

            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getMaxValue(int i5) {
                return UCharacterProperty.mergeScriptCodeOrIndex(UCharacterProperty.this.getMaxValues(0) & UCharacterProperty.SCRIPT_X_MASK);
            }
        }, new IntProperty(i3) { // from class: com.ibm.icu.impl.UCharacterProperty.21
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getMaxValue(int i5) {
                return 5;
            }

            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                int additional = (UCharacterProperty.this.getAdditional(i5, 2) & 992) >>> 5;
                if (additional < UCharacterProperty.gcbToHst.length) {
                    return UCharacterProperty.gcbToHst[additional];
                }
                return 0;
            }
        }, new NormQuickCheckIntProperty(8, 4108, 1), new NormQuickCheckIntProperty(9, 4109, 1), new NormQuickCheckIntProperty(8, 4110, 2), new NormQuickCheckIntProperty(9, 4111, 2), new CombiningClassIntProperty(i4) { // from class: com.ibm.icu.impl.UCharacterProperty.22
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                return Norm2AllModes.getNFCInstance().impl.getFCD16(i5) >> 8;
            }
        }, new CombiningClassIntProperty(i4) { // from class: com.ibm.icu.impl.UCharacterProperty.23
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                return Norm2AllModes.getNFCInstance().impl.getFCD16(i5) & 255;
            }
        }, new IntProperty(2, 992, 5), new IntProperty(2, SB_MASK, 15), new IntProperty(2, WB_MASK, 10), new BiDiIntProperty() { // from class: com.ibm.icu.impl.UCharacterProperty.24
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                return UBiDiProps.INSTANCE.getPairedBracketType(i5);
            }
        }, new IntProperty(12) { // from class: com.ibm.icu.impl.UCharacterProperty.25
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                CodePointTrie codePointTrie = LayoutProps.INSTANCE.inpcTrie;
                if (codePointTrie != null) {
                    return codePointTrie.get(i5);
                }
                return 0;
            }

            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getMaxValue(int i5) {
                return LayoutProps.INSTANCE.maxInpcValue;
            }
        }, new IntProperty(13) { // from class: com.ibm.icu.impl.UCharacterProperty.26
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                CodePointTrie codePointTrie = LayoutProps.INSTANCE.inscTrie;
                if (codePointTrie != null) {
                    return codePointTrie.get(i5);
                }
                return 0;
            }

            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getMaxValue(int i5) {
                return LayoutProps.INSTANCE.maxInscValue;
            }
        }, new IntProperty(14) { // from class: com.ibm.icu.impl.UCharacterProperty.27
            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getValue(int i5) {
                CodePointTrie codePointTrie = LayoutProps.INSTANCE.voTrie;
                if (codePointTrie != null) {
                    return codePointTrie.get(i5);
                }
                return 0;
            }

            @Override // com.ibm.icu.impl.UCharacterProperty.IntProperty
            int getMaxValue(int i5) {
                return LayoutProps.INSTANCE.maxVoValue;
            }
        }};
        this.intProps = intPropertyArr;
        if (this.binProps.length != 65) {
            throw new ICUException("binProps.length!=UProperty.BINARY_LIMIT");
        }
        if (intPropertyArr.length != 25) {
            throw new ICUException("intProps.length!=(UProperty.INT_LIMIT-UProperty.INT_START)");
        }
        ByteBuffer requiredData = ICUBinary.getRequiredData(DATA_FILE_NAME_);
        this.m_unicodeVersion_ = ICUBinary.readHeaderAndDataVersion(requiredData, DATA_FORMAT, new IsAcceptable());
        int i5 = requiredData.getInt();
        requiredData.getInt();
        requiredData.getInt();
        int i6 = requiredData.getInt();
        int i7 = requiredData.getInt();
        this.m_additionalColumnsCount_ = requiredData.getInt();
        int i8 = requiredData.getInt();
        int i9 = requiredData.getInt();
        requiredData.getInt();
        requiredData.getInt();
        this.m_maxBlockScriptValue_ = requiredData.getInt();
        this.m_maxJTGValue_ = requiredData.getInt();
        ICUBinary.skipBytes(requiredData, 16);
        Trie2_16 trie2_16CreateFromSerialized = Trie2_16.createFromSerialized(requiredData);
        this.m_trie_ = trie2_16CreateFromSerialized;
        int i10 = (i5 - 16) * 4;
        int serializedLength = trie2_16CreateFromSerialized.getSerializedLength();
        if (serializedLength > i10) {
            throw new IOException("uprops.icu: not enough bytes for main trie");
        }
        ICUBinary.skipBytes(requiredData, i10 - serializedLength);
        ICUBinary.skipBytes(requiredData, (i6 - i5) * 4);
        if (this.m_additionalColumnsCount_ > 0) {
            Trie2_16 trie2_16CreateFromSerialized2 = Trie2_16.createFromSerialized(requiredData);
            this.m_additionalTrie_ = trie2_16CreateFromSerialized2;
            int i11 = (i7 - i6) * 4;
            int serializedLength2 = trie2_16CreateFromSerialized2.getSerializedLength();
            if (serializedLength2 > i11) {
                throw new IOException("uprops.icu: not enough bytes for additional-properties trie");
            }
            ICUBinary.skipBytes(requiredData, i11 - serializedLength2);
            this.m_additionalVectors_ = ICUBinary.getInts(requiredData, i8 - i7, 0);
        }
        int i12 = (i9 - i8) * 2;
        if (i12 > 0) {
            this.m_scriptExtensions_ = ICUBinary.getChars(requiredData, i12, 0);
        }
    }

    private static final class IsAcceptable implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override // com.ibm.icu.impl.ICUBinary.Authenticate
        public boolean isDataVersionAcceptable(byte[] bArr) {
            return bArr[0] == 7;
        }
    }

    public UnicodeSet addPropertyStarts(UnicodeSet unicodeSet) {
        Iterator<Trie2.Range> it2 = this.m_trie_.iterator();
        while (it2.hasNext()) {
            Trie2.Range next = it2.next();
            if (next.leadSurrogate) {
                break;
            }
            unicodeSet.add(next.startCodePoint);
        }
        unicodeSet.add(9);
        unicodeSet.add(10);
        unicodeSet.add(14);
        unicodeSet.add(28);
        unicodeSet.add(32);
        unicodeSet.add(133);
        unicodeSet.add(134);
        unicodeSet.add(127);
        unicodeSet.add(HAIRSP);
        unicodeSet.add(8208);
        unicodeSet.add(INHSWAP);
        unicodeSet.add(8304);
        unicodeSet.add(ZWNBSP);
        unicodeSet.add(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        unicodeSet.add(160);
        unicodeSet.add(161);
        unicodeSet.add(FIGURESP);
        unicodeSet.add(JosStatusCodes.RNT_CODE_SERVER_ERROR);
        unicodeSet.add(NNBSP);
        unicodeSet.add(8240);
        unicodeSet.add(12295);
        unicodeSet.add(12296);
        unicodeSet.add(19968);
        unicodeSet.add(19969);
        unicodeSet.add(20108);
        unicodeSet.add(20109);
        unicodeSet.add(19977);
        unicodeSet.add(19978);
        unicodeSet.add(22235);
        unicodeSet.add(22236);
        unicodeSet.add(20116);
        unicodeSet.add(20117);
        unicodeSet.add(20845);
        unicodeSet.add(20846);
        unicodeSet.add(19971);
        unicodeSet.add(19972);
        unicodeSet.add(20843);
        unicodeSet.add(20844);
        unicodeSet.add(20061);
        unicodeSet.add(20062);
        unicodeSet.add(97);
        unicodeSet.add(123);
        unicodeSet.add(65);
        unicodeSet.add(91);
        unicodeSet.add(U_FW_a);
        unicodeSet.add(65371);
        unicodeSet.add(U_FW_A);
        unicodeSet.add(65339);
        unicodeSet.add(103);
        unicodeSet.add(71);
        unicodeSet.add(65351);
        unicodeSet.add(65319);
        unicodeSet.add(WJ);
        unicodeSet.add(65520);
        unicodeSet.add(65532);
        unicodeSet.add(917504);
        unicodeSet.add(921600);
        unicodeSet.add(847);
        unicodeSet.add(R2.attr.popupWindowStyle);
        return unicodeSet;
    }

    public void upropsvec_addPropertyStarts(UnicodeSet unicodeSet) {
        if (this.m_additionalColumnsCount_ > 0) {
            Iterator<Trie2.Range> it2 = this.m_additionalTrie_.iterator();
            while (it2.hasNext()) {
                Trie2.Range next = it2.next();
                if (next.leadSurrogate) {
                    return;
                } else {
                    unicodeSet.add(next.startCodePoint);
                }
            }
        }
    }

    static UnicodeSet ulayout_addPropertyStarts(int i, UnicodeSet unicodeSet) {
        return LayoutProps.INSTANCE.addPropertyStarts(i, unicodeSet);
    }
}
