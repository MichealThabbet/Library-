package org.apache.poi.util;

import java.io.UnsupportedEncodingException;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class StringUtil {
    private static final String ENCODING_ISO_8859_1 = "ISO-8859-1";

    public static String getPreferredEncoding() {
        return "ISO-8859-1";
    }

    private StringUtil() {
    }

    public static String getFromUnicodeLE(byte[] bArr, int i, int i2) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (i < 0 || i >= bArr.length) {
            throw new ArrayIndexOutOfBoundsException("Illegal offset " + i + " (String data is of length " + bArr.length + ")");
        }
        if (i2 < 0 || (bArr.length - i) / 2 < i2) {
            throw new IllegalArgumentException("Illegal length " + i2);
        }
        try {
            return new String(bArr, i, i2 * 2, "UTF-16LE");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFromUnicodeLE(byte[] bArr) {
        return bArr.length == 0 ? "" : getFromUnicodeLE(bArr, 0, bArr.length / 2);
    }

    public static String getFromCompressedUnicode(byte[] bArr, int i, int i2) {
        try {
            return new String(bArr, i, Math.min(i2, bArr.length - i), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readCompressedUnicode(LittleEndianInput littleEndianInput, int i) {
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = (char) littleEndianInput.readUByte();
        }
        return new String(cArr);
    }

    public static String readUnicodeString(LittleEndianInput littleEndianInput) {
        int uShort = littleEndianInput.readUShort();
        if ((littleEndianInput.readByte() & 1) == 0) {
            return readCompressedUnicode(littleEndianInput, uShort);
        }
        return readUnicodeLE(littleEndianInput, uShort);
    }

    public static String readUnicodeString(LittleEndianInput littleEndianInput, int i) {
        if ((littleEndianInput.readByte() & 1) == 0) {
            return readCompressedUnicode(littleEndianInput, i);
        }
        return readUnicodeLE(littleEndianInput, i);
    }

    public static void writeUnicodeString(LittleEndianOutput littleEndianOutput, String str) {
        littleEndianOutput.writeShort(str.length());
        boolean zHasMultibyte = hasMultibyte(str);
        littleEndianOutput.writeByte(zHasMultibyte ? 1 : 0);
        if (zHasMultibyte) {
            putUnicodeLE(str, littleEndianOutput);
        } else {
            putCompressedUnicode(str, littleEndianOutput);
        }
    }

    public static void writeUnicodeStringFlagAndData(LittleEndianOutput littleEndianOutput, String str) {
        boolean zHasMultibyte = hasMultibyte(str);
        littleEndianOutput.writeByte(zHasMultibyte ? 1 : 0);
        if (zHasMultibyte) {
            putUnicodeLE(str, littleEndianOutput);
        } else {
            putCompressedUnicode(str, littleEndianOutput);
        }
    }

    public static int getEncodedSize(String str) {
        return (str.length() * (hasMultibyte(str) ? 2 : 1)) + 3;
    }

    public static void putCompressedUnicode(String str, byte[] bArr, int i) throws UnsupportedEncodingException {
        try {
            byte[] bytes = str.getBytes("ISO-8859-1");
            System.arraycopy(bytes, 0, bArr, i, bytes.length);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void putCompressedUnicode(String str, LittleEndianOutput littleEndianOutput) {
        try {
            littleEndianOutput.write(str.getBytes("ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void putUnicodeLE(String str, byte[] bArr, int i) throws UnsupportedEncodingException {
        try {
            byte[] bytes = str.getBytes("UTF-16LE");
            System.arraycopy(bytes, 0, bArr, i, bytes.length);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void putUnicodeLE(String str, LittleEndianOutput littleEndianOutput) {
        try {
            littleEndianOutput.write(str.getBytes("UTF-16LE"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readUnicodeLE(LittleEndianInput littleEndianInput, int i) {
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = (char) littleEndianInput.readUShort();
        }
        return new String(cArr);
    }

    public static String format(String str, Object[] objArr) {
        int i;
        int i2;
        StringBuffer stringBuffer = new StringBuffer();
        int iMatchOptionalFormatting = 0;
        int i3 = 0;
        while (iMatchOptionalFormatting < str.length()) {
            if (str.charAt(iMatchOptionalFormatting) == '%') {
                if (i3 >= objArr.length) {
                    stringBuffer.append("?missing data?");
                } else if ((objArr[i3] instanceof Number) && (i2 = iMatchOptionalFormatting + 1) < str.length()) {
                    iMatchOptionalFormatting += matchOptionalFormatting((Number) objArr[i3], str.substring(i2), stringBuffer);
                    i3++;
                } else {
                    stringBuffer.append(objArr[i3].toString());
                    i3++;
                }
            } else if (str.charAt(iMatchOptionalFormatting) == '\\' && (i = iMatchOptionalFormatting + 1) < str.length() && str.charAt(i) == '%') {
                stringBuffer.append('%');
                iMatchOptionalFormatting = i;
            } else {
                stringBuffer.append(str.charAt(iMatchOptionalFormatting));
            }
            iMatchOptionalFormatting++;
        }
        return stringBuffer.toString();
    }

    private static int matchOptionalFormatting(Number number, String str, StringBuffer stringBuffer) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        if (str.length() > 0 && Character.isDigit(str.charAt(0))) {
            numberFormat.setMinimumIntegerDigits(Integer.parseInt(str.charAt(0) + ""));
            if (2 < str.length() && str.charAt(1) == '.' && Character.isDigit(str.charAt(2))) {
                numberFormat.setMaximumFractionDigits(Integer.parseInt(str.charAt(2) + ""));
                numberFormat.format(number, stringBuffer, new FieldPosition(0));
                return 3;
            }
            numberFormat.format(number, stringBuffer, new FieldPosition(0));
            return 1;
        }
        if (str.length() > 0 && str.charAt(0) == '.' && 1 < str.length() && Character.isDigit(str.charAt(1))) {
            numberFormat.setMaximumFractionDigits(Integer.parseInt(str.charAt(1) + ""));
            numberFormat.format(number, stringBuffer, new FieldPosition(0));
            return 2;
        }
        numberFormat.format(number, stringBuffer, new FieldPosition(0));
        return 1;
    }

    public static boolean hasMultibyte(String str) {
        if (str == null) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > 255) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUnicodeString(String str) {
        try {
            return !str.equals(new String(str.getBytes("ISO-8859-1"), "ISO-8859-1"));
        } catch (UnsupportedEncodingException unused) {
            return true;
        }
    }

    public static class StringsIterator implements Iterator<String> {
        private int position = 0;
        private String[] strings;

        @Override // java.util.Iterator
        public void remove() {
        }

        public StringsIterator(String[] strArr) {
            if (strArr != null) {
                this.strings = strArr;
            } else {
                this.strings = new String[0];
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.position < this.strings.length;
        }

        @Override // java.util.Iterator
        public String next() {
            int i = this.position;
            this.position = i + 1;
            String[] strArr = this.strings;
            if (i >= strArr.length) {
                throw new ArrayIndexOutOfBoundsException(i);
            }
            return strArr[i];
        }
    }
}
