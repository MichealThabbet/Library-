package com.ibm.icu.text;

import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import java.nio.CharBuffer;
import java.text.CharacterIterator;

/* loaded from: classes2.dex */
public final class Normalizer implements Cloneable {
    public static final int COMPARE_CODE_POINT_ORDER = 32768;
    private static final int COMPARE_EQUIV = 524288;
    public static final int COMPARE_IGNORE_CASE = 65536;

    @Deprecated
    public static final int COMPARE_NORM_OPTIONS_SHIFT = 20;

    @Deprecated
    public static final Mode COMPOSE;

    @Deprecated
    public static final Mode COMPOSE_COMPAT;

    @Deprecated
    public static final Mode DECOMP;

    @Deprecated
    public static final Mode DECOMP_COMPAT;

    @Deprecated
    public static final Mode DEFAULT;

    @Deprecated
    public static final int DONE = -1;

    @Deprecated
    public static final Mode FCD;
    public static final int FOLD_CASE_DEFAULT = 0;
    public static final int FOLD_CASE_EXCLUDE_SPECIAL_I = 1;

    @Deprecated
    public static final int IGNORE_HANGUL = 1;
    public static final int INPUT_IS_FCD = 131072;
    public static final QuickCheckResult MAYBE;

    @Deprecated
    public static final Mode NFC;

    @Deprecated
    public static final Mode NFD;

    @Deprecated
    public static final Mode NFKC;

    @Deprecated
    public static final Mode NFKD;
    public static final QuickCheckResult NO;

    @Deprecated
    public static final Mode NONE;

    @Deprecated
    public static final Mode NO_OP;

    @Deprecated
    public static final int UNICODE_3_2 = 32;
    public static final QuickCheckResult YES;
    private StringBuilder buffer;
    private int bufferPos;
    private int currentIndex;
    private Mode mode;
    private int nextIndex;
    private Normalizer2 norm2;
    private int options;
    private UCharacterIterator text;

    @Deprecated
    public int getBeginIndex() {
        return 0;
    }

    @Deprecated
    public int startIndex() {
        return 0;
    }

    private static final class ModeImpl {
        private final Normalizer2 normalizer2;

        private ModeImpl(Normalizer2 normalizer2) {
            this.normalizer2 = normalizer2;
        }
    }

    private static final class NFDModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFDInstance());

        private NFDModeImpl() {
        }
    }

    private static final class NFKDModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFKDInstance());

        private NFKDModeImpl() {
        }
    }

    private static final class NFCModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFCInstance());

        private NFCModeImpl() {
        }
    }

    private static final class NFKCModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFKCInstance());

        private NFKCModeImpl() {
        }
    }

    private static final class FCDModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Norm2AllModes.getFCDNormalizer2());

        private FCDModeImpl() {
        }
    }

    private static final class Unicode32 {
        private static final UnicodeSet INSTANCE = new UnicodeSet("[:age=3.2:]").freeze();

        private Unicode32() {
        }
    }

    private static final class NFD32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFDInstance(), Unicode32.INSTANCE));

        private NFD32ModeImpl() {
        }
    }

    private static final class NFKD32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFKDInstance(), Unicode32.INSTANCE));

        private NFKD32ModeImpl() {
        }
    }

    private static final class NFC32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFCInstance(), Unicode32.INSTANCE));

        private NFC32ModeImpl() {
        }
    }

    private static final class NFKC32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFKCInstance(), Unicode32.INSTANCE));

        private NFKC32ModeImpl() {
        }
    }

    private static final class FCD32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Norm2AllModes.getFCDNormalizer2(), Unicode32.INSTANCE));

        private FCD32ModeImpl() {
        }
    }

    @Deprecated
    public static abstract class Mode {
        @Deprecated
        protected abstract Normalizer2 getNormalizer2(int i);

        @Deprecated
        protected Mode() {
        }
    }

    private static final class NONEMode extends Mode {
        private NONEMode() {
        }

        @Override // com.ibm.icu.text.Normalizer.Mode
        protected Normalizer2 getNormalizer2(int i) {
            return Norm2AllModes.NOOP_NORMALIZER2;
        }
    }

    private static final class NFDMode extends Mode {
        private NFDMode() {
        }

        @Override // com.ibm.icu.text.Normalizer.Mode
        protected Normalizer2 getNormalizer2(int i) {
            ModeImpl modeImpl;
            if ((i & 32) == 0) {
                modeImpl = NFDModeImpl.INSTANCE;
            } else {
                modeImpl = NFD32ModeImpl.INSTANCE;
            }
            return modeImpl.normalizer2;
        }
    }

    private static final class NFKDMode extends Mode {
        private NFKDMode() {
        }

        @Override // com.ibm.icu.text.Normalizer.Mode
        protected Normalizer2 getNormalizer2(int i) {
            ModeImpl modeImpl;
            if ((i & 32) == 0) {
                modeImpl = NFKDModeImpl.INSTANCE;
            } else {
                modeImpl = NFKD32ModeImpl.INSTANCE;
            }
            return modeImpl.normalizer2;
        }
    }

    private static final class NFCMode extends Mode {
        private NFCMode() {
        }

        @Override // com.ibm.icu.text.Normalizer.Mode
        protected Normalizer2 getNormalizer2(int i) {
            ModeImpl modeImpl;
            if ((i & 32) == 0) {
                modeImpl = NFCModeImpl.INSTANCE;
            } else {
                modeImpl = NFC32ModeImpl.INSTANCE;
            }
            return modeImpl.normalizer2;
        }
    }

    private static final class NFKCMode extends Mode {
        private NFKCMode() {
        }

        @Override // com.ibm.icu.text.Normalizer.Mode
        protected Normalizer2 getNormalizer2(int i) {
            ModeImpl modeImpl;
            if ((i & 32) == 0) {
                modeImpl = NFKCModeImpl.INSTANCE;
            } else {
                modeImpl = NFKC32ModeImpl.INSTANCE;
            }
            return modeImpl.normalizer2;
        }
    }

    private static final class FCDMode extends Mode {
        private FCDMode() {
        }

        @Override // com.ibm.icu.text.Normalizer.Mode
        protected Normalizer2 getNormalizer2(int i) {
            ModeImpl modeImpl;
            if ((i & 32) == 0) {
                modeImpl = FCDModeImpl.INSTANCE;
            } else {
                modeImpl = FCD32ModeImpl.INSTANCE;
            }
            return modeImpl.normalizer2;
        }
    }

    static {
        NONE = new NONEMode();
        NFD = new NFDMode();
        NFKD = new NFKDMode();
        NFCMode nFCMode = new NFCMode();
        NFC = nFCMode;
        DEFAULT = nFCMode;
        NFKC = new NFKCMode();
        FCD = new FCDMode();
        NO_OP = NONE;
        COMPOSE = NFC;
        COMPOSE_COMPAT = NFKC;
        DECOMP = NFD;
        DECOMP_COMPAT = NFKD;
        NO = new QuickCheckResult(0);
        YES = new QuickCheckResult(1);
        MAYBE = new QuickCheckResult(2);
    }

    public static final class QuickCheckResult {
        private QuickCheckResult(int i) {
        }
    }

    @Deprecated
    public Normalizer(String str, Mode mode, int i) {
        this.text = UCharacterIterator.getInstance(str);
        this.mode = mode;
        this.options = i;
        this.norm2 = mode.getNormalizer2(i);
        this.buffer = new StringBuilder();
    }

    @Deprecated
    public Normalizer(CharacterIterator characterIterator, Mode mode, int i) {
        this.text = UCharacterIterator.getInstance((CharacterIterator) characterIterator.clone());
        this.mode = mode;
        this.options = i;
        this.norm2 = mode.getNormalizer2(i);
        this.buffer = new StringBuilder();
    }

    @Deprecated
    public Normalizer(UCharacterIterator uCharacterIterator, Mode mode, int i) {
        try {
            this.text = (UCharacterIterator) uCharacterIterator.clone();
            this.mode = mode;
            this.options = i;
            this.norm2 = mode.getNormalizer2(i);
            this.buffer = new StringBuilder();
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }

    @Deprecated
    public Object clone() {
        try {
            Normalizer normalizer = (Normalizer) super.clone();
            normalizer.text = (UCharacterIterator) this.text.clone();
            normalizer.mode = this.mode;
            normalizer.options = this.options;
            normalizer.norm2 = this.norm2;
            normalizer.buffer = new StringBuilder(this.buffer);
            normalizer.bufferPos = this.bufferPos;
            normalizer.currentIndex = this.currentIndex;
            normalizer.nextIndex = this.nextIndex;
            return normalizer;
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }

    private static final Normalizer2 getComposeNormalizer2(boolean z, int i) {
        return (z ? NFKC : NFC).getNormalizer2(i);
    }

    private static final Normalizer2 getDecomposeNormalizer2(boolean z, int i) {
        return (z ? NFKD : NFD).getNormalizer2(i);
    }

    @Deprecated
    public static String compose(String str, boolean z) {
        return compose(str, z, 0);
    }

    @Deprecated
    public static String compose(String str, boolean z, int i) {
        return getComposeNormalizer2(z, i).normalize(str);
    }

    @Deprecated
    public static int compose(char[] cArr, char[] cArr2, boolean z, int i) {
        return compose(cArr, 0, cArr.length, cArr2, 0, cArr2.length, z, i);
    }

    @Deprecated
    public static int compose(char[] cArr, int i, int i2, char[] cArr2, int i3, int i4, boolean z, int i5) {
        CharBuffer charBufferWrap = CharBuffer.wrap(cArr, i, i2 - i);
        CharsAppendable charsAppendable = new CharsAppendable(cArr2, i3, i4);
        getComposeNormalizer2(z, i5).normalize(charBufferWrap, charsAppendable);
        return charsAppendable.length();
    }

    @Deprecated
    public static String decompose(String str, boolean z) {
        return decompose(str, z, 0);
    }

    @Deprecated
    public static String decompose(String str, boolean z, int i) {
        return getDecomposeNormalizer2(z, i).normalize(str);
    }

    @Deprecated
    public static int decompose(char[] cArr, char[] cArr2, boolean z, int i) {
        return decompose(cArr, 0, cArr.length, cArr2, 0, cArr2.length, z, i);
    }

    @Deprecated
    public static int decompose(char[] cArr, int i, int i2, char[] cArr2, int i3, int i4, boolean z, int i5) {
        CharBuffer charBufferWrap = CharBuffer.wrap(cArr, i, i2 - i);
        CharsAppendable charsAppendable = new CharsAppendable(cArr2, i3, i4);
        getDecomposeNormalizer2(z, i5).normalize(charBufferWrap, charsAppendable);
        return charsAppendable.length();
    }

    @Deprecated
    public static String normalize(String str, Mode mode, int i) {
        return mode.getNormalizer2(i).normalize(str);
    }

    @Deprecated
    public static String normalize(String str, Mode mode) {
        return normalize(str, mode, 0);
    }

    @Deprecated
    public static int normalize(char[] cArr, char[] cArr2, Mode mode, int i) {
        return normalize(cArr, 0, cArr.length, cArr2, 0, cArr2.length, mode, i);
    }

    @Deprecated
    public static int normalize(char[] cArr, int i, int i2, char[] cArr2, int i3, int i4, Mode mode, int i5) {
        CharBuffer charBufferWrap = CharBuffer.wrap(cArr, i, i2 - i);
        CharsAppendable charsAppendable = new CharsAppendable(cArr2, i3, i4);
        mode.getNormalizer2(i5).normalize(charBufferWrap, charsAppendable);
        return charsAppendable.length();
    }

    @Deprecated
    public static String normalize(int i, Mode mode, int i2) {
        if (mode == NFD && i2 == 0) {
            String decomposition = Normalizer2.getNFCInstance().getDecomposition(i);
            return decomposition == null ? UTF16.valueOf(i) : decomposition;
        }
        return normalize(UTF16.valueOf(i), mode, i2);
    }

    @Deprecated
    public static String normalize(int i, Mode mode) {
        return normalize(i, mode, 0);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(String str, Mode mode) {
        return quickCheck(str, mode, 0);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(String str, Mode mode, int i) {
        return mode.getNormalizer2(i).quickCheck(str);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(char[] cArr, Mode mode, int i) {
        return quickCheck(cArr, 0, cArr.length, mode, i);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(char[] cArr, int i, int i2, Mode mode, int i3) {
        return mode.getNormalizer2(i3).quickCheck(CharBuffer.wrap(cArr, i, i2 - i));
    }

    @Deprecated
    public static boolean isNormalized(char[] cArr, int i, int i2, Mode mode, int i3) {
        return mode.getNormalizer2(i3).isNormalized(CharBuffer.wrap(cArr, i, i2 - i));
    }

    @Deprecated
    public static boolean isNormalized(String str, Mode mode, int i) {
        return mode.getNormalizer2(i).isNormalized(str);
    }

    @Deprecated
    public static boolean isNormalized(int i, Mode mode, int i2) {
        return isNormalized(UTF16.valueOf(i), mode, i2);
    }

    public static int compare(char[] cArr, int i, int i2, char[] cArr2, int i3, int i4, int i5) {
        if (cArr == null || i < 0 || i2 < 0 || cArr2 == null || i3 < 0 || i4 < 0 || i2 < i || i4 < i3) {
            throw new IllegalArgumentException();
        }
        return internalCompare(CharBuffer.wrap(cArr, i, i2 - i), CharBuffer.wrap(cArr2, i3, i4 - i3), i5);
    }

    public static int compare(String str, String str2, int i) {
        return internalCompare(str, str2, i);
    }

    public static int compare(char[] cArr, char[] cArr2, int i) {
        return internalCompare(CharBuffer.wrap(cArr), CharBuffer.wrap(cArr2), i);
    }

    public static int compare(int i, int i2, int i3) {
        return internalCompare(UTF16.valueOf(i), UTF16.valueOf(i2), i3 | 131072);
    }

    public static int compare(int i, String str, int i2) {
        return internalCompare(UTF16.valueOf(i), str, i2);
    }

    @Deprecated
    public static int concatenate(char[] cArr, int i, int i2, char[] cArr2, int i3, int i4, char[] cArr3, int i5, int i6, Mode mode, int i7) {
        if (cArr3 == null) {
            throw new IllegalArgumentException();
        }
        if (cArr2 == cArr3 && i3 < i6 && i5 < i4) {
            throw new IllegalArgumentException("overlapping right and dst ranges");
        }
        int i8 = i2 - i;
        StringBuilder sb = new StringBuilder(((i8 + i4) - i3) + 16);
        sb.append(cArr, i, i8);
        mode.getNormalizer2(i7).append(sb, CharBuffer.wrap(cArr2, i3, i4 - i3));
        int length = sb.length();
        if (length <= i6 - i5) {
            sb.getChars(0, length, cArr3, i5);
            return length;
        }
        throw new IndexOutOfBoundsException(Integer.toString(length));
    }

    @Deprecated
    public static String concatenate(char[] cArr, char[] cArr2, Mode mode, int i) {
        StringBuilder sb = new StringBuilder(cArr.length + cArr2.length + 16);
        sb.append(cArr);
        return mode.getNormalizer2(i).append(sb, CharBuffer.wrap(cArr2)).toString();
    }

    @Deprecated
    public static String concatenate(String str, String str2, Mode mode, int i) {
        StringBuilder sb = new StringBuilder(str.length() + str2.length() + 16);
        sb.append(str);
        return mode.getNormalizer2(i).append(sb, str2).toString();
    }

    @Deprecated
    public static int getFC_NFKC_Closure(int i, char[] cArr) {
        String fC_NFKC_Closure = getFC_NFKC_Closure(i);
        int length = fC_NFKC_Closure.length();
        if (length != 0 && cArr != null && length <= cArr.length) {
            fC_NFKC_Closure.getChars(0, length, cArr, 0);
        }
        return length;
    }

    @Deprecated
    public static String getFC_NFKC_Closure(int i) {
        Normalizer2 normalizer2 = NFKCModeImpl.INSTANCE.normalizer2;
        UCaseProps uCaseProps = UCaseProps.INSTANCE;
        StringBuilder sb = new StringBuilder();
        int fullFolding = uCaseProps.toFullFolding(i, sb, 0);
        if (fullFolding < 0) {
            Normalizer2Impl normalizer2Impl = ((Norm2AllModes.Normalizer2WithImpl) normalizer2).impl;
            if (normalizer2Impl.getCompQuickCheck(normalizer2Impl.getNorm16(i)) != 0) {
                return "";
            }
            sb.appendCodePoint(i);
        } else if (fullFolding > 31) {
            sb.appendCodePoint(fullFolding);
        }
        String strNormalize = normalizer2.normalize(sb);
        String strNormalize2 = normalizer2.normalize(UCharacter.foldCase(strNormalize, 0));
        return strNormalize.equals(strNormalize2) ? "" : strNormalize2;
    }

    @Deprecated
    public int current() {
        if (this.bufferPos < this.buffer.length() || nextNormalize()) {
            return this.buffer.codePointAt(this.bufferPos);
        }
        return -1;
    }

    @Deprecated
    public int next() {
        if (this.bufferPos >= this.buffer.length() && !nextNormalize()) {
            return -1;
        }
        int iCodePointAt = this.buffer.codePointAt(this.bufferPos);
        this.bufferPos += Character.charCount(iCodePointAt);
        return iCodePointAt;
    }

    @Deprecated
    public int previous() {
        if (this.bufferPos <= 0 && !previousNormalize()) {
            return -1;
        }
        int iCodePointBefore = this.buffer.codePointBefore(this.bufferPos);
        this.bufferPos -= Character.charCount(iCodePointBefore);
        return iCodePointBefore;
    }

    @Deprecated
    public void reset() {
        this.text.setToStart();
        this.nextIndex = 0;
        this.currentIndex = 0;
        clearBuffer();
    }

    @Deprecated
    public void setIndexOnly(int i) {
        this.text.setIndex(i);
        this.nextIndex = i;
        this.currentIndex = i;
        clearBuffer();
    }

    @Deprecated
    public int setIndex(int i) {
        setIndexOnly(i);
        return current();
    }

    @Deprecated
    public int getEndIndex() {
        return endIndex();
    }

    @Deprecated
    public int first() {
        reset();
        return next();
    }

    @Deprecated
    public int last() {
        this.text.setToLimit();
        int index = this.text.getIndex();
        this.nextIndex = index;
        this.currentIndex = index;
        clearBuffer();
        return previous();
    }

    @Deprecated
    public int getIndex() {
        if (this.bufferPos < this.buffer.length()) {
            return this.currentIndex;
        }
        return this.nextIndex;
    }

    @Deprecated
    public int endIndex() {
        return this.text.getLength();
    }

    @Deprecated
    public void setMode(Mode mode) {
        this.mode = mode;
        this.norm2 = mode.getNormalizer2(this.options);
    }

    @Deprecated
    public Mode getMode() {
        return this.mode;
    }

    @Deprecated
    public void setOption(int i, boolean z) {
        if (z) {
            this.options = i | this.options;
        } else {
            this.options = (~i) & this.options;
        }
        this.norm2 = this.mode.getNormalizer2(this.options);
    }

    @Deprecated
    public int getOption(int i) {
        return (i & this.options) != 0 ? 1 : 0;
    }

    @Deprecated
    public int getText(char[] cArr) {
        return this.text.getText(cArr);
    }

    @Deprecated
    public int getLength() {
        return this.text.getLength();
    }

    @Deprecated
    public String getText() {
        return this.text.getText();
    }

    @Deprecated
    public void setText(StringBuffer stringBuffer) {
        UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(stringBuffer);
        if (uCharacterIterator == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = uCharacterIterator;
        reset();
    }

    @Deprecated
    public void setText(char[] cArr) {
        UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(cArr);
        if (uCharacterIterator == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = uCharacterIterator;
        reset();
    }

    @Deprecated
    public void setText(String str) {
        UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(str);
        if (uCharacterIterator == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = uCharacterIterator;
        reset();
    }

    @Deprecated
    public void setText(CharacterIterator characterIterator) {
        UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(characterIterator);
        if (uCharacterIterator == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
        }
        this.text = uCharacterIterator;
        reset();
    }

    @Deprecated
    public void setText(UCharacterIterator uCharacterIterator) {
        try {
            UCharacterIterator uCharacterIterator2 = (UCharacterIterator) uCharacterIterator.clone();
            if (uCharacterIterator2 == null) {
                throw new IllegalStateException("Could not create a new UCharacterIterator");
            }
            this.text = uCharacterIterator2;
            reset();
        } catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException("Could not clone the UCharacterIterator", e);
        }
    }

    private void clearBuffer() {
        this.buffer.setLength(0);
        this.bufferPos = 0;
    }

    private boolean nextNormalize() {
        clearBuffer();
        int i = this.nextIndex;
        this.currentIndex = i;
        this.text.setIndex(i);
        int iNextCodePoint = this.text.nextCodePoint();
        if (iNextCodePoint < 0) {
            return false;
        }
        StringBuilder sbAppendCodePoint = new StringBuilder().appendCodePoint(iNextCodePoint);
        while (true) {
            int iNextCodePoint2 = this.text.nextCodePoint();
            if (iNextCodePoint2 < 0) {
                break;
            }
            if (this.norm2.hasBoundaryBefore(iNextCodePoint2)) {
                this.text.moveCodePointIndex(-1);
                break;
            }
            sbAppendCodePoint.appendCodePoint(iNextCodePoint2);
        }
        this.nextIndex = this.text.getIndex();
        this.norm2.normalize((CharSequence) sbAppendCodePoint, this.buffer);
        return this.buffer.length() != 0;
    }

    private boolean previousNormalize() {
        int iPreviousCodePoint;
        clearBuffer();
        int i = this.currentIndex;
        this.nextIndex = i;
        this.text.setIndex(i);
        StringBuilder sb = new StringBuilder();
        do {
            iPreviousCodePoint = this.text.previousCodePoint();
            if (iPreviousCodePoint < 0) {
                break;
            }
            if (iPreviousCodePoint <= 65535) {
                sb.insert(0, (char) iPreviousCodePoint);
            } else {
                sb.insert(0, Character.toChars(iPreviousCodePoint));
            }
        } while (!this.norm2.hasBoundaryBefore(iPreviousCodePoint));
        this.currentIndex = this.text.getIndex();
        this.norm2.normalize((CharSequence) sb, this.buffer);
        this.bufferPos = this.buffer.length();
        return this.buffer.length() != 0;
    }

    private static int internalCompare(CharSequence charSequence, CharSequence charSequence2, int i) {
        Normalizer2 normalizer2;
        int i2 = i >>> 20;
        int i3 = i | 524288;
        if ((131072 & i3) == 0 || (i3 & 1) != 0) {
            if ((i3 & 1) != 0) {
                normalizer2 = NFD.getNormalizer2(i2);
            } else {
                normalizer2 = FCD.getNormalizer2(i2);
            }
            int iSpanQuickCheckYes = normalizer2.spanQuickCheckYes(charSequence);
            int iSpanQuickCheckYes2 = normalizer2.spanQuickCheckYes(charSequence2);
            if (iSpanQuickCheckYes < charSequence.length()) {
                StringBuilder sb = new StringBuilder(charSequence.length() + 16);
                sb.append(charSequence, 0, iSpanQuickCheckYes);
                charSequence = normalizer2.normalizeSecondAndAppend(sb, charSequence.subSequence(iSpanQuickCheckYes, charSequence.length()));
            }
            if (iSpanQuickCheckYes2 < charSequence2.length()) {
                StringBuilder sb2 = new StringBuilder(charSequence2.length() + 16);
                sb2.append(charSequence2, 0, iSpanQuickCheckYes2);
                charSequence2 = normalizer2.normalizeSecondAndAppend(sb2, charSequence2.subSequence(iSpanQuickCheckYes2, charSequence2.length()));
            }
        }
        return cmpEquivFold(charSequence, charSequence2, i3);
    }

    private static final class CmpEquivLevel {
        CharSequence cs;
        int s;

        private CmpEquivLevel() {
        }
    }

    private static final CmpEquivLevel[] createCmpEquivLevelStack() {
        return new CmpEquivLevel[]{new CmpEquivLevel(), new CmpEquivLevel()};
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x026d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:189:0x019c A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:194:0x02bf A[ADDED_TO_REGION, EDGE_INSN: B:194:0x02bf->B:150:0x02bf BREAK  A[LOOP:0: B:11:0x0042->B:201:0x0042], REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0137 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01a0 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static int cmpEquivFold(java.lang.CharSequence r27, java.lang.CharSequence r28, int r29) {
        /*
            Method dump skipped, instructions count: 807
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.Normalizer.cmpEquivFold(java.lang.CharSequence, java.lang.CharSequence, int):int");
    }

    private static final class CharsAppendable implements Appendable {
        private final char[] chars;
        private final int limit;
        private int offset;
        private final int start;

        public CharsAppendable(char[] cArr, int i, int i2) {
            this.chars = cArr;
            this.offset = i;
            this.start = i;
            this.limit = i2;
        }

        public int length() {
            int i = this.offset;
            int i2 = i - this.start;
            if (i <= this.limit) {
                return i2;
            }
            throw new IndexOutOfBoundsException(Integer.toString(i2));
        }

        @Override // java.lang.Appendable
        public Appendable append(char c) {
            int i = this.offset;
            if (i < this.limit) {
                this.chars[i] = c;
            }
            this.offset++;
            return this;
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence charSequence) {
            return append(charSequence, 0, charSequence.length());
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence charSequence, int i, int i2) {
            int i3 = i2 - i;
            int i4 = this.limit;
            int i5 = this.offset;
            if (i3 <= i4 - i5) {
                while (i < i2) {
                    char[] cArr = this.chars;
                    int i6 = this.offset;
                    this.offset = i6 + 1;
                    cArr[i6] = charSequence.charAt(i);
                    i++;
                }
            } else {
                this.offset = i5 + i3;
            }
            return this;
        }
    }
}
