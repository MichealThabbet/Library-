package com.ibm.icu.impl;

import com.ibm.icu.text.NumberFormat;
import java.text.Format;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.language.Soundex;

/* loaded from: classes2.dex */
public class FormattedStringBuilder implements CharSequence, Appendable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final FormattedStringBuilder EMPTY = new FormattedStringBuilder();
    private static final Map<Object, Character> fieldToDebugChar;
    int appendOffset;
    Object appendableField;
    char[] chars;
    Object[] fields;
    int length;
    int zero;

    public interface FieldWrapper {
        Format.Field unwrap();
    }

    public static Format.Field unwrapField(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof FieldWrapper) {
            return ((FieldWrapper) obj).unwrap();
        }
        if (obj instanceof Format.Field) {
            return (Format.Field) obj;
        }
        throw new AssertionError("Not a field: " + obj);
    }

    static {
        HashMap map = new HashMap();
        fieldToDebugChar = map;
        map.put(NumberFormat.Field.SIGN, Character.valueOf(Soundex.SILENT_MARKER));
        fieldToDebugChar.put(NumberFormat.Field.INTEGER, Character.valueOf(UCharacterProperty.LATIN_SMALL_LETTER_I_));
        fieldToDebugChar.put(NumberFormat.Field.FRACTION, 'f');
        fieldToDebugChar.put(NumberFormat.Field.EXPONENT, 'e');
        fieldToDebugChar.put(NumberFormat.Field.EXPONENT_SIGN, '+');
        fieldToDebugChar.put(NumberFormat.Field.EXPONENT_SYMBOL, 'E');
        fieldToDebugChar.put(NumberFormat.Field.DECIMAL_SEPARATOR, '.');
        fieldToDebugChar.put(NumberFormat.Field.GROUPING_SEPARATOR, ',');
        fieldToDebugChar.put(NumberFormat.Field.PERCENT, '%');
        fieldToDebugChar.put(NumberFormat.Field.PERMILLE, (char) 8240);
        fieldToDebugChar.put(NumberFormat.Field.CURRENCY, '$');
        fieldToDebugChar.put(NumberFormat.Field.MEASURE_UNIT, 'u');
        fieldToDebugChar.put(NumberFormat.Field.COMPACT, 'C');
    }

    public FormattedStringBuilder() {
        this(40);
    }

    public FormattedStringBuilder(int i) {
        this.appendOffset = 0;
        this.appendableField = null;
        this.chars = new char[i];
        this.fields = new Object[i];
        this.zero = i / 2;
        this.length = 0;
    }

    public FormattedStringBuilder(FormattedStringBuilder formattedStringBuilder) {
        this.appendOffset = 0;
        this.appendableField = null;
        copyFrom(formattedStringBuilder);
    }

    public void copyFrom(FormattedStringBuilder formattedStringBuilder) {
        char[] cArr = formattedStringBuilder.chars;
        this.chars = Arrays.copyOf(cArr, cArr.length);
        Object[] objArr = formattedStringBuilder.fields;
        this.fields = Arrays.copyOf(objArr, objArr.length);
        this.zero = formattedStringBuilder.zero;
        this.length = formattedStringBuilder.length;
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.length;
    }

    public int codePointCount() {
        return Character.codePointCount(this, 0, length());
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return this.chars[this.zero + i];
    }

    public Object fieldAt(int i) {
        return this.fields[this.zero + i];
    }

    public int getFirstCodePoint() {
        int i = this.length;
        if (i == 0) {
            return -1;
        }
        char[] cArr = this.chars;
        int i2 = this.zero;
        return Character.codePointAt(cArr, i2, i + i2);
    }

    public int getLastCodePoint() {
        int i = this.length;
        if (i == 0) {
            return -1;
        }
        char[] cArr = this.chars;
        int i2 = this.zero;
        return Character.codePointBefore(cArr, i + i2, i2);
    }

    public int codePointAt(int i) {
        char[] cArr = this.chars;
        int i2 = this.zero;
        return Character.codePointAt(cArr, i + i2, i2 + this.length);
    }

    public int codePointBefore(int i) {
        char[] cArr = this.chars;
        int i2 = this.zero;
        return Character.codePointBefore(cArr, i + i2, i2);
    }

    public FormattedStringBuilder clear() {
        this.zero = getCapacity() / 2;
        this.length = 0;
        return this;
    }

    public void setAppendIndex(int i) {
        this.appendOffset = this.length - i;
    }

    public int appendChar16(char c, Object obj) {
        return insertChar16(this.length - this.appendOffset, c, obj);
    }

    public int insertChar16(int i, char c, Object obj) {
        int iPrepareForInsert = prepareForInsert(i, 1);
        this.chars[iPrepareForInsert] = c;
        this.fields[iPrepareForInsert] = obj;
        return 1;
    }

    public int appendCodePoint(int i, Object obj) {
        return insertCodePoint(this.length - this.appendOffset, i, obj);
    }

    public int insertCodePoint(int i, int i2, Object obj) {
        int iCharCount = Character.charCount(i2);
        int iPrepareForInsert = prepareForInsert(i, iCharCount);
        Character.toChars(i2, this.chars, iPrepareForInsert);
        Object[] objArr = this.fields;
        objArr[iPrepareForInsert] = obj;
        if (iCharCount == 2) {
            objArr[iPrepareForInsert + 1] = obj;
        }
        return iCharCount;
    }

    public int append(CharSequence charSequence, Object obj) {
        return insert(this.length - this.appendOffset, charSequence, obj);
    }

    public int insert(int i, CharSequence charSequence, Object obj) {
        if (charSequence.length() == 0) {
            return 0;
        }
        if (charSequence.length() == 1) {
            return insertCodePoint(i, charSequence.charAt(0), obj);
        }
        return insert(i, charSequence, 0, charSequence.length(), obj);
    }

    public int insert(int i, CharSequence charSequence, int i2, int i3, Object obj) {
        int i4 = i3 - i2;
        int iPrepareForInsert = prepareForInsert(i, i4);
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = iPrepareForInsert + i5;
            this.chars[i6] = charSequence.charAt(i2 + i5);
            this.fields[i6] = obj;
        }
        return i4;
    }

    public int splice(int i, int i2, CharSequence charSequence, int i3, int i4, Object obj) {
        int iRemove;
        int i5 = i4 - i3;
        int i6 = i5 - (i2 - i);
        if (i6 > 0) {
            iRemove = prepareForInsert(i, i6);
        } else {
            iRemove = remove(i, -i6);
        }
        for (int i7 = 0; i7 < i5; i7++) {
            int i8 = iRemove + i7;
            this.chars[i8] = charSequence.charAt(i3 + i7);
            this.fields[i8] = obj;
        }
        return i6;
    }

    public int append(char[] cArr, Object[] objArr) {
        return insert(this.length - this.appendOffset, cArr, objArr);
    }

    public int insert(int i, char[] cArr, Object[] objArr) {
        int length = cArr.length;
        if (length == 0) {
            return 0;
        }
        int iPrepareForInsert = prepareForInsert(i, length);
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = iPrepareForInsert + i2;
            this.chars[i3] = cArr[i2];
            this.fields[i3] = objArr == null ? null : objArr[i2];
        }
        return length;
    }

    public int append(FormattedStringBuilder formattedStringBuilder) {
        return insert(this.length - this.appendOffset, formattedStringBuilder);
    }

    public int insert(int i, FormattedStringBuilder formattedStringBuilder) {
        if (this == formattedStringBuilder) {
            throw new IllegalArgumentException("Cannot call insert/append on myself");
        }
        int i2 = formattedStringBuilder.length;
        if (i2 == 0) {
            return 0;
        }
        int iPrepareForInsert = prepareForInsert(i, i2);
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = iPrepareForInsert + i3;
            this.chars[i4] = formattedStringBuilder.charAt(i3);
            this.fields[i4] = formattedStringBuilder.fieldAt(i3);
        }
        return i2;
    }

    private int prepareForInsert(int i, int i2) {
        if (i == -1) {
            i = this.length;
        }
        if (i == 0) {
            int i3 = this.zero;
            if (i3 - i2 >= 0) {
                int i4 = i3 - i2;
                this.zero = i4;
                this.length += i2;
                return i4;
            }
        }
        int i5 = this.length;
        if (i == i5 && this.zero + i5 + i2 < getCapacity()) {
            int i6 = this.length + i2;
            this.length = i6;
            return (this.zero + i6) - i2;
        }
        return prepareForInsertHelper(i, i2);
    }

    private int prepareForInsertHelper(int i, int i2) {
        int capacity = getCapacity();
        int i3 = this.zero;
        char[] cArr = this.chars;
        Object[] objArr = this.fields;
        int i4 = this.length;
        if (i4 + i2 > capacity) {
            int i5 = (i4 + i2) * 2;
            int i6 = (i5 / 2) - ((i4 + i2) / 2);
            char[] cArr2 = new char[i5];
            Object[] objArr2 = new Object[i5];
            System.arraycopy(cArr, i3, cArr2, i6, i);
            int i7 = i3 + i;
            int i8 = i6 + i + i2;
            System.arraycopy(cArr, i7, cArr2, i8, this.length - i);
            System.arraycopy(objArr, i3, objArr2, i6, i);
            System.arraycopy(objArr, i7, objArr2, i8, this.length - i);
            this.chars = cArr2;
            this.fields = objArr2;
            this.zero = i6;
            this.length += i2;
        } else {
            int i9 = (capacity / 2) - ((i4 + i2) / 2);
            System.arraycopy(cArr, i3, cArr, i9, i4);
            int i10 = i9 + i;
            int i11 = i10 + i2;
            System.arraycopy(cArr, i10, cArr, i11, this.length - i);
            System.arraycopy(objArr, i3, objArr, i9, this.length);
            System.arraycopy(objArr, i10, objArr, i11, this.length - i);
            this.zero = i9;
            this.length += i2;
        }
        return this.zero + i;
    }

    private int remove(int i, int i2) {
        int i3 = this.zero + i;
        char[] cArr = this.chars;
        int i4 = i3 + i2;
        System.arraycopy(cArr, i4, cArr, i3, (this.length - i) - i2);
        Object[] objArr = this.fields;
        System.arraycopy(objArr, i4, objArr, i3, (this.length - i) - i2);
        this.length -= i2;
        return i3;
    }

    private int getCapacity() {
        return this.chars.length;
    }

    @Override // java.lang.CharSequence
    @Deprecated
    public CharSequence subSequence(int i, int i2) {
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder(this);
        formattedStringBuilder.zero = this.zero + i;
        formattedStringBuilder.length = i2 - i;
        return formattedStringBuilder;
    }

    public String subString(int i, int i2) {
        if (i < 0 || i2 > this.length || i2 < i) {
            throw new IndexOutOfBoundsException();
        }
        return new String(this.chars, this.zero + i, i2 - i);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return new String(this.chars, this.zero, this.length);
    }

    public String toDebugString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<FormattedStringBuilder [");
        sb.append(toString());
        sb.append("] [");
        for (int i = this.zero; i < this.zero + this.length; i++) {
            Object[] objArr = this.fields;
            if (objArr[i] == null) {
                sb.append('n');
            } else if (fieldToDebugChar.containsKey(objArr[i])) {
                sb.append(fieldToDebugChar.get(this.fields[i]));
            } else {
                sb.append('?');
            }
        }
        sb.append("]>");
        return sb.toString();
    }

    public char[] toCharArray() {
        char[] cArr = this.chars;
        int i = this.zero;
        return Arrays.copyOfRange(cArr, i, this.length + i);
    }

    public Object[] toFieldArray() {
        Object[] objArr = this.fields;
        int i = this.zero;
        return Arrays.copyOfRange(objArr, i, this.length + i);
    }

    public void setAppendableField(Object obj) {
        this.appendableField = obj;
    }

    @Override // java.lang.Appendable
    public Appendable append(CharSequence charSequence) {
        insert(this.length - this.appendOffset, charSequence, this.appendableField);
        return this;
    }

    @Override // java.lang.Appendable
    public Appendable append(CharSequence charSequence, int i, int i2) {
        insert(this.length - this.appendOffset, charSequence, i, i2, this.appendableField);
        return this;
    }

    @Override // java.lang.Appendable
    public Appendable append(char c) {
        insertChar16(this.length - this.appendOffset, c, this.appendableField);
        return this;
    }

    public boolean contentEquals(char[] cArr, Object[] objArr) {
        int length = cArr.length;
        int i = this.length;
        if (length != i || objArr.length != i) {
            return false;
        }
        for (int i2 = 0; i2 < this.length; i2++) {
            char[] cArr2 = this.chars;
            int i3 = this.zero;
            if (cArr2[i3 + i2] != cArr[i2] || unwrapField(this.fields[i3 + i2]) != unwrapField(objArr[i2])) {
                return false;
            }
        }
        return true;
    }

    public boolean contentEquals(FormattedStringBuilder formattedStringBuilder) {
        if (this.length != formattedStringBuilder.length) {
            return false;
        }
        for (int i = 0; i < this.length; i++) {
            if (charAt(i) != formattedStringBuilder.charAt(i) || unwrapField(fieldAt(i)) != unwrapField(formattedStringBuilder.fieldAt(i))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        throw new UnsupportedOperationException("Don't call #hashCode() or #equals() on a mutable.");
    }

    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Don't call #hashCode() or #equals() on a mutable.");
    }
}
