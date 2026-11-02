package com.huawei.hms.analytics;

import com.ibm.icu.impl.UCharacterProperty;
import com.ibm.icu.lang.UCharacterEnums;
import com.ibm.icu.util.ULocale;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.record.PaletteRecord;
import org.apache.poi.hssf.record.formula.Area3DPtg;
import org.apache.poi.hssf.record.formula.AreaErrPtg;
import org.apache.poi.hssf.record.formula.DeletedArea3DPtg;
import org.apache.poi.hssf.record.formula.DeletedRef3DPtg;
import org.apache.poi.hssf.record.formula.MemFuncPtg;
import org.apache.poi.hssf.record.formula.NumberPtg;
import org.apache.poi.hssf.record.formula.Ref3DPtg;
import org.apache.poi.hssf.record.formula.RefErrorPtg;
import org.apache.poi.hssf.record.formula.RefNPtg;
import org.apache.poi.hssf.record.formula.RefPtg;

/* loaded from: classes2.dex */
public final class da {
    private static final char[] lmn = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', UCharacterProperty.LATIN_SMALL_LETTER_I_, 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', ULocale.PRIVATE_USE_EXTENSION, 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', IOUtils.DIR_SEPARATOR_UNIX, '='};
    private static final byte[] klm = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, PaletteRecord.STANDARD_PALETTE_SIZE, 57, Ref3DPtg.sid, Area3DPtg.sid, DeletedRef3DPtg.sid, DeletedArea3DPtg.sid, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, UCharacterEnums.ECharacterCategory.MATH_SYMBOL, 25, -1, -1, -1, -1, -1, -1, UCharacterEnums.ECharacterCategory.MODIFIER_SYMBOL, UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, 28, 29, 30, NumberPtg.sid, 32, 33, 34, 35, RefPtg.sid, 37, 38, 39, 40, MemFuncPtg.sid, RefErrorPtg.sid, AreaErrPtg.sid, RefNPtg.sid, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    public static String klm(String str) {
        try {
            byte[] bArrLmn = lmn(str);
            return bArrLmn.length == 0 ? "" : new String(bArrLmn, Charset.forName("UTF-8"));
        } catch (Throwable unused) {
            return "";
        }
    }

    public static byte[] lmn(String str) {
        int length = str.length();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt > 255 || klm[cCharAt] < 0) {
                length--;
            }
        }
        int i2 = (length / 4) * 3;
        int i3 = length % 4;
        if (i3 == 3) {
            i2 += 2;
        }
        if (i3 == 2) {
            i2++;
        }
        byte[] bArr = new byte[i2];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < str.length(); i7++) {
            char cCharAt2 = str.charAt(i7);
            byte b = cCharAt2 > 255 ? (byte) -1 : klm[cCharAt2];
            if (b >= 0) {
                i6 += 6;
                i5 = (i5 << 6) | b;
                if (i6 >= 8) {
                    i6 -= 8;
                    bArr[i4] = (byte) ((i5 >> i6) & 255);
                    i4++;
                }
            }
        }
        return i4 != i2 ? new byte[0] : bArr;
    }
}
