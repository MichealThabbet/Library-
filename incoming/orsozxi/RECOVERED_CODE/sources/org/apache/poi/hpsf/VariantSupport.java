package org.apache.poi.hpsf;

import com.google.zxing.common.StringUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.poi.util.LittleEndian;

/* loaded from: classes3.dex */
public class VariantSupport extends Variant {
    public static final int[] SUPPORTED_TYPES = {0, 2, 3, 20, 5, 64, 30, 31, 71, 11};
    private static boolean logUnsupportedTypes = false;
    protected static List unsupportedMessage;

    public static void setLogUnsupportedTypes(boolean z) {
        logUnsupportedTypes = z;
    }

    public static boolean isLogUnsupportedTypes() {
        return logUnsupportedTypes;
    }

    protected static void writeUnsupportedTypeMessage(UnsupportedVariantTypeException unsupportedVariantTypeException) {
        if (isLogUnsupportedTypes()) {
            if (unsupportedMessage == null) {
                unsupportedMessage = new LinkedList();
            }
            Long lValueOf = Long.valueOf(unsupportedVariantTypeException.getVariantType());
            if (unsupportedMessage.contains(lValueOf)) {
                return;
            }
            System.err.println(unsupportedVariantTypeException.getMessage());
            unsupportedMessage.add(lValueOf);
        }
    }

    public boolean isSupportedType(int i) {
        int i2 = 0;
        while (true) {
            int[] iArr = SUPPORTED_TYPES;
            if (i2 >= iArr.length) {
                return false;
            }
            if (i == iArr[i2]) {
                return true;
            }
            i2++;
        }
    }

    public static Object read(byte[] bArr, int i, int i2, long j, int i3) throws ReadingNotSupportedException, UnsupportedEncodingException {
        Object str;
        int i4 = i2 - 4;
        int i5 = (int) ((i3 == 1200 && j == 30) ? 31L : j);
        if (i5 == 0) {
            return null;
        }
        if (i5 == 5) {
            return new Double(LittleEndian.getDouble(bArr, i));
        }
        if (i5 == 11) {
            if (LittleEndian.getUInt(bArr, i) != 0) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
        if (i5 == 20) {
            return Long.valueOf(LittleEndian.getLong(bArr, i));
        }
        if (i5 == 64) {
            return Util.filetimeToDate((int) LittleEndian.getUInt(bArr, i + 4), (int) LittleEndian.getUInt(bArr, i));
        }
        int i6 = 0;
        if (i5 == 71) {
            if (i4 < 0) {
                i4 = LittleEndian.getInt(bArr, i);
                i += 4;
            }
            str = new byte[i4];
            System.arraycopy(bArr, i, str, 0, i4);
        } else {
            if (i5 == 2) {
                return Integer.valueOf(LittleEndian.getShort(bArr, i));
            }
            if (i5 == 3) {
                return Integer.valueOf(LittleEndian.getInt(bArr, i));
            }
            if (i5 != 30) {
                if (i5 == 31) {
                    int i7 = i + 4;
                    long j2 = i7;
                    long uInt = ((LittleEndian.getUInt(bArr, i) + j2) - 1) - j2;
                    StringBuffer stringBuffer = new StringBuffer((int) uInt);
                    while (i6 <= uInt) {
                        int i8 = (i6 * 2) + i7;
                        stringBuffer.append((char) ((bArr[i8] & 255) | (bArr[i8 + 1] << 8)));
                        i6++;
                    }
                    while (stringBuffer.length() > 0 && stringBuffer.charAt(stringBuffer.length() - 1) == 0) {
                        stringBuffer.setLength(stringBuffer.length() - 1);
                    }
                    return stringBuffer.toString();
                }
                byte[] bArr2 = new byte[i4];
                while (i6 < i4) {
                    bArr2[i6] = bArr[i + i6];
                    i6++;
                }
                throw new ReadingNotSupportedException(j, bArr2);
            }
            int i9 = i + 4;
            long j3 = i9;
            long uInt2 = LittleEndian.getUInt(bArr, i) + j3;
            do {
                uInt2--;
                if (bArr[(int) uInt2] != 0) {
                    break;
                }
            } while (j3 <= uInt2);
            int i10 = (int) ((uInt2 - j3) + 1);
            str = i3 != -1 ? new String(bArr, i9, i10, codepageToEncoding(i3)) : new String(bArr, i9, i10);
        }
        return str;
    }

    public static String codepageToEncoding(int i) throws UnsupportedEncodingException {
        if (i <= 0) {
            throw new UnsupportedEncodingException("Codepage number may not be " + i);
        }
        if (i == 1200) {
            return "UTF-16";
        }
        if (i == 1201) {
            return "UTF-16BE";
        }
        if (i == 10081) {
            return "MacTurkish";
        }
        if (i == 10082) {
            return "MacCroatian";
        }
        switch (i) {
            case 37:
                return "cp037";
            case 932:
                return StringUtils.SHIFT_JIS;
            case 936:
                return "GBK";
            case 949:
                return "ms949";
            case 1361:
                return "johab";
            case Constants.CP_MAC_ROMANIA /* 10010 */:
                return "MacRomania";
            case Constants.CP_MAC_UKRAINE /* 10017 */:
                return "MacUkraine";
            case Constants.CP_MAC_THAI /* 10021 */:
                return "MacThai";
            case Constants.CP_MAC_CENTRAL_EUROPE /* 10029 */:
                return "MacCentralEurope";
            case Constants.CP_MAC_ICELAND /* 10079 */:
                return "MacIceland";
            case Constants.CP_US_ACSII /* 20127 */:
                return "US-ASCII";
            case Constants.CP_KOI8_R /* 20866 */:
                return "KOI8-R";
            case Constants.CP_ISO_2022_KR /* 50225 */:
                return "ISO-2022-KR";
            case Constants.CP_EUC_JP /* 51932 */:
                return "EUC-JP";
            case Constants.CP_EUC_KR /* 51949 */:
                return "EUC-KR";
            case Constants.CP_GB2312 /* 52936 */:
                return StringUtils.GB2312;
            case Constants.CP_GB18030 /* 54936 */:
                return "GB18030";
            default:
                switch (i) {
                    case 1250:
                        return "windows-1250";
                    case 1251:
                        return "windows-1251";
                    case 1252:
                        return "windows-1252";
                    case 1253:
                        return "windows-1253";
                    case 1254:
                        return InternalZipConstants.CHARSET_COMMENTS_DEFAULT;
                    case 1255:
                        return "windows-1255";
                    case 1256:
                        return "windows-1256";
                    case 1257:
                        return "windows-1257";
                    case 1258:
                        return "windows-1258";
                    default:
                        switch (i) {
                            case 10000:
                                return "MacRoman";
                            case Constants.CP_MAC_JAPAN /* 10001 */:
                                return StringUtils.SHIFT_JIS;
                            case Constants.CP_MAC_CHINESE_TRADITIONAL /* 10002 */:
                                return "Big5";
                            case Constants.CP_MAC_KOREAN /* 10003 */:
                                return "EUC-KR";
                            case Constants.CP_MAC_ARABIC /* 10004 */:
                                return "MacArabic";
                            case Constants.CP_MAC_HEBREW /* 10005 */:
                                return "MacHebrew";
                            case Constants.CP_MAC_GREEK /* 10006 */:
                                return "MacGreek";
                            case Constants.CP_MAC_CYRILLIC /* 10007 */:
                                return "MacCyrillic";
                            case Constants.CP_MAC_CHINESE_SIMPLE /* 10008 */:
                                return "EUC_CN";
                            default:
                                switch (i) {
                                    case Constants.CP_ISO_8859_1 /* 28591 */:
                                        return "ISO-8859-1";
                                    case Constants.CP_ISO_8859_2 /* 28592 */:
                                        return "ISO-8859-2";
                                    case Constants.CP_ISO_8859_3 /* 28593 */:
                                        return "ISO-8859-3";
                                    case Constants.CP_ISO_8859_4 /* 28594 */:
                                        return "ISO-8859-4";
                                    case Constants.CP_ISO_8859_5 /* 28595 */:
                                        return "ISO-8859-5";
                                    case Constants.CP_ISO_8859_6 /* 28596 */:
                                        return "ISO-8859-6";
                                    case Constants.CP_ISO_8859_7 /* 28597 */:
                                        return "ISO-8859-7";
                                    case Constants.CP_ISO_8859_8 /* 28598 */:
                                        return "ISO-8859-8";
                                    case Constants.CP_ISO_8859_9 /* 28599 */:
                                        return "ISO-8859-9";
                                    default:
                                        switch (i) {
                                            case Constants.CP_ISO_2022_JP1 /* 50220 */:
                                            case Constants.CP_ISO_2022_JP2 /* 50221 */:
                                            case Constants.CP_ISO_2022_JP3 /* 50222 */:
                                                return "ISO-2022-JP";
                                            default:
                                                switch (i) {
                                                    case Constants.CP_US_ASCII2 /* 65000 */:
                                                        return "US-ASCII";
                                                    case Constants.CP_UTF8 /* 65001 */:
                                                        return "UTF-8";
                                                    default:
                                                        return "cp" + i;
                                                }
                                        }
                                }
                        }
                }
        }
    }

    public static int write(OutputStream outputStream, long j, Object obj, int i) throws IOException, WritingNotSupportedException {
        int iWriteToStream;
        int i2 = (int) j;
        if (i2 == 0) {
            TypeWriter.writeUIntToStream(outputStream, 0L);
            return 4;
        }
        if (i2 != 5) {
            if (i2 == 11) {
                return TypeWriter.writeUIntToStream(outputStream, ((Boolean) obj).booleanValue() ? 1L : 0L);
            }
            if (i2 == 20) {
                TypeWriter.writeToStream(outputStream, ((Long) obj).longValue());
                return 8;
            }
            if (i2 == 64) {
                long jDateToFileTime = Util.dateToFileTime((Date) obj);
                return TypeWriter.writeUIntToStream(outputStream, ((int) (jDateToFileTime & 4294967295L)) & 4294967295L) + 0 + TypeWriter.writeUIntToStream(outputStream, ((int) ((jDateToFileTime >> 32) & 4294967295L)) & 4294967295L);
            }
            if (i2 == 71) {
                byte[] bArr = (byte[]) obj;
                outputStream.write(bArr);
                return bArr.length;
            }
            if (i2 == 2) {
                TypeWriter.writeToStream(outputStream, ((Integer) obj).shortValue());
                return 2;
            }
            if (i2 != 3) {
                if (i2 == 30) {
                    String str = (String) obj;
                    byte[] bytes = i == -1 ? str.getBytes() : str.getBytes(codepageToEncoding(i));
                    int iWriteUIntToStream = TypeWriter.writeUIntToStream(outputStream, bytes.length + 1);
                    int length = bytes.length + 1;
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(bytes, 0, bArr2, 0, bytes.length);
                    bArr2[length - 1] = 0;
                    outputStream.write(bArr2);
                    return iWriteUIntToStream + length;
                }
                if (i2 == 31) {
                    int iWriteUIntToStream2 = TypeWriter.writeUIntToStream(outputStream, r8.length() + 1) + 0;
                    char[] cArrPad4 = Util.pad4((String) obj);
                    for (int i3 = 0; i3 < cArrPad4.length; i3++) {
                        int i4 = (cArrPad4[i3] & 65280) >> 8;
                        outputStream.write((byte) (cArrPad4[i3] & 255));
                        outputStream.write((byte) i4);
                        iWriteUIntToStream2 += 2;
                    }
                    outputStream.write(0);
                    outputStream.write(0);
                    return iWriteUIntToStream2 + 2;
                }
                if (obj instanceof byte[]) {
                    byte[] bArr3 = (byte[]) obj;
                    outputStream.write(bArr3);
                    int length2 = bArr3.length;
                    writeUnsupportedTypeMessage(new WritingNotSupportedException(j, obj));
                    return length2;
                }
                throw new WritingNotSupportedException(j, obj);
            }
            if (!(obj instanceof Integer)) {
                throw new ClassCastException("Could not cast an object to " + Integer.class.toString() + ": " + obj.getClass().toString() + ", " + obj.toString());
            }
            iWriteToStream = TypeWriter.writeToStream(outputStream, ((Integer) obj).intValue());
        } else {
            iWriteToStream = TypeWriter.writeToStream(outputStream, ((Double) obj).doubleValue());
        }
        return iWriteToStream + 0;
    }
}
