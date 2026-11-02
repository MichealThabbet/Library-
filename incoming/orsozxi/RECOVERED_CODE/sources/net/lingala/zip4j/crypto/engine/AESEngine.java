package net.lingala.zip4j.crypto.engine;

import com.ibm.icu.lang.UCharacterEnums;
import com.ibm.icu.text.Bidi;
import java.lang.reflect.Array;
import net.lingala.zip4j.exception.ZipException;
import org.apache.poi.hssf.record.PaletteRecord;
import org.apache.poi.hssf.record.formula.Area3DPtg;
import org.apache.poi.hssf.record.formula.AreaErrPtg;
import org.apache.poi.hssf.record.formula.DeletedArea3DPtg;
import org.apache.poi.hssf.record.formula.DeletedRef3DPtg;
import org.apache.poi.hssf.record.formula.MemFuncPtg;
import org.apache.poi.hssf.record.formula.NumberPtg;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.record.formula.Ref3DPtg;
import org.apache.poi.hssf.record.formula.RefErrorPtg;
import org.apache.poi.hssf.record.formula.RefNPtg;
import org.apache.poi.hssf.record.formula.RefPtg;

/* loaded from: classes3.dex */
public class AESEngine {
    private int C0;
    private int C1;
    private int C2;
    private int C3;
    private int rounds;
    private int[][] workingKey = (int[][]) null;
    private static final byte[] S = {99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, AreaErrPtg.sid, -2, -41, -85, 118, -54, -126, -55, Bidi.MAX_EXPLICIT_LEVEL, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, -15, 113, -40, 49, 21, 4, -57, 35, -61, UCharacterEnums.ECharacterCategory.MATH_SYMBOL, -106, 5, -102, 7, 18, -128, -30, -21, 39, -78, 117, 9, -125, RefNPtg.sid, UCharacterEnums.ECharacterCategory.MODIFIER_SYMBOL, UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, 110, 90, -96, 82, Area3DPtg.sid, -42, -77, MemFuncPtg.sid, -29, 47, -124, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, 51, -123, 69, -7, 2, 127, 80, DeletedRef3DPtg.sid, -97, -88, 81, -93, Ptg.CLASS_ARRAY, -113, -110, -99, PaletteRecord.STANDARD_PALETTE_SIZE, -11, -68, -74, -38, 33, 16, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, Bidi.LEVEL_DEFAULT_LTR, DeletedArea3DPtg.sid, 100, 93, 25, 115, 96, -127, 79, -36, 34, RefErrorPtg.sid, -112, -120, 70, -18, -72, 20, -34, 94, 11, -37, -32, 50, Ref3DPtg.sid, 10, 73, 6, RefPtg.sid, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, 37, 46, 28, -90, -76, -58, -24, -35, 116, NumberPtg.sid, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, 29, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, 30, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, 22};
    private static final int[] rcon = {1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, 239, 197, 145};
    private static final int[] T0 = {-1520213050, -2072216328, -1720223762, -1921287178, 234025727, -1117033514, -1318096930, 1422247313, 1345335392, 50397442, -1452841010, 2099981142, 436141799, 1658312629, -424957107, -1703512340, 1170918031, -1652391393, 1086966153, -2021818886, 368769775, -346465870, -918075506, 200339707, -324162239, 1742001331, -39673249, -357585083, -1080255453, -140204973, -1770884380, 1539358875, -1028147339, 486407649, -1366060227, 1780885068, 1513502316, 1094664062, 49805301, 1338821763, 1546925160, -190470831, 887481809, 150073849, -1821281822, 1943591083, 1395732834, 1058346282, 201589768, 1388824469, 1696801606, 1589887901, 672667696, -1583966665, 251987210, -1248159185, 151455502, 907153956, -1686077413, 1038279391, 652995533, 1764173646, -843926913, -1619692054, 453576978, -1635548387, 1949051992, 773462580, 756751158, -1301385508, -296068428, -73359269, -162377052, 1295727478, 1641469623, -827083907, 2066295122, 1055122397, 1898917726, -1752923117, -179088474, 1758581177, 0, 753790401, 1612718144, 536673507, -927878791, -312779850, -1100322092, 1187761037, -641810841, 1262041458, -565556588, -733197160, -396863312, 1255133061, 1808847035, 720367557, -441800113, 385612781, -985447546, -682799718, 1429418854, -1803188975, -817543798, 284817897, 100794884, -2122350594, -263171936, 1144798328, -1163944155, -475486133, -212774494, -22830243, -1069531008, -1970303227, -1382903233, -1130521311, 1211644016, 83228145, -541279133, -1044990345, 1977277103, 1663115586, 806359072, 452984805, 250868733, 1842533055, 1288555905, 336333848, 890442534, 804056259, -513843266, -1567123659, -867941240, 957814574, 1472513171, -223893675, -2105639172, 1195195770, -1402706744, -413311558, 723065138, -1787595802, -1604296512, -1736343271, -783331426, 2145180835, 1713513028, 2116692564, -1416589253, -2088204277, -901364084, 703524551, -742868885, 1007948840, 2044649127, -497131844, 487262998, 1994120109, 1004593371, 1446130276, 1312438900, 503974420, -615954030, 168166924, 1814307912, -463709000, 1573044895, 1859376061, -273896381, -1503501628, -1466855111, -1533700815, 937747667, -1954973198, 854058965, 1137232011, 1496790894, -1217565222, -1936880383, 1691735473, -766620004, -525751991, -1267962664, -95005012, 133494003, 636152527, -1352309302, -1904575756, -374428089, 403179536, -709182865, -2005370640, 1864705354, 1915629148, 605822008, -240736681, -944458637, 1371981463, 602466507, 2094914977, -1670089496, 555687742, -582268010, -591544991, -2037675251, -2054518257, -1871679264, 1111375484, -994724495, -1436129588, -666351472, 84083462, 32962295, 302911004, -1553899070, 1597322602, -111716434, -793134743, -1853454825, 1489093017, 656219450, -1180787161, 954327513, 335083755, -1281845205, 856756514, -1150719534, 1893325225, -1987146233, -1483434957, -1231316179, 572399164, -1836611819, 552200649, 1238290055, -11184726, 2015897680, 2061492133, -1886614525, -123625127, -2138470135, 386731290, -624967835, 837215959, -968736124, -1201116976, -1019133566, -1332111063, 1999449434, 286199582, -877612933, -61582168, -692339859, 974525996};

    private int shift(int i, int i2) {
        return (i << (-i2)) | (i >>> i2);
    }

    public AESEngine(byte[] bArr) throws ZipException {
        init(bArr);
    }

    public void init(byte[] bArr) throws ZipException {
        this.workingKey = generateWorkingKey(bArr);
    }

    private int[][] generateWorkingKey(byte[] bArr) throws ZipException {
        int length = bArr.length / 4;
        if ((length != 4 && length != 6 && length != 8) || length * 4 != bArr.length) {
            throw new ZipException("invalid key length (not 128/192/256)");
        }
        int i = length + 6;
        this.rounds = i;
        int i2 = 0;
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) int.class, i + 1, 4);
        int i3 = 0;
        while (i2 < bArr.length) {
            iArr[i3 >> 2][i3 & 3] = (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16) | (bArr[i2 + 3] << UCharacterEnums.ECharacterCategory.MATH_SYMBOL);
            i2 += 4;
            i3++;
        }
        int i4 = (this.rounds + 1) << 2;
        for (int i5 = length; i5 < i4; i5++) {
            int i6 = i5 - 1;
            int iSubWord = iArr[i6 >> 2][i6 & 3];
            int i7 = i5 % length;
            if (i7 == 0) {
                iSubWord = subWord(shift(iSubWord, 8)) ^ rcon[(i5 / length) - 1];
            } else if (length > 6 && i7 == 4) {
                iSubWord = subWord(iSubWord);
            }
            int i8 = i5 - length;
            iArr[i5 >> 2][i5 & 3] = iSubWord ^ iArr[i8 >> 2][i8 & 3];
        }
        return iArr;
    }

    public int processBlock(byte[] bArr, byte[] bArr2) throws ZipException {
        return processBlock(bArr, 0, bArr2, 0);
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws ZipException {
        if (this.workingKey == null) {
            throw new ZipException("AES engine not initialised");
        }
        if (i + 16 > bArr.length) {
            throw new ZipException("input buffer too short");
        }
        if (i2 + 16 > bArr2.length) {
            throw new ZipException("output buffer too short");
        }
        stateIn(bArr, i);
        encryptBlock(this.workingKey);
        stateOut(bArr2, i2);
        return 16;
    }

    private final void stateIn(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = bArr[i] & 255;
        this.C0 = i3;
        int i4 = i2 + 1;
        int i5 = i3 | ((bArr[i2] & 255) << 8);
        this.C0 = i5;
        int i6 = i4 + 1;
        int i7 = i5 | ((bArr[i4] & 255) << 16);
        this.C0 = i7;
        int i8 = i6 + 1;
        this.C0 = i7 | (bArr[i6] << UCharacterEnums.ECharacterCategory.MATH_SYMBOL);
        int i9 = i8 + 1;
        int i10 = bArr[i8] & 255;
        this.C1 = i10;
        int i11 = i9 + 1;
        int i12 = ((bArr[i9] & 255) << 8) | i10;
        this.C1 = i12;
        int i13 = i11 + 1;
        int i14 = i12 | ((bArr[i11] & 255) << 16);
        this.C1 = i14;
        int i15 = i13 + 1;
        this.C1 = i14 | (bArr[i13] << UCharacterEnums.ECharacterCategory.MATH_SYMBOL);
        int i16 = i15 + 1;
        int i17 = bArr[i15] & 255;
        this.C2 = i17;
        int i18 = i16 + 1;
        int i19 = ((bArr[i16] & 255) << 8) | i17;
        this.C2 = i19;
        int i20 = i18 + 1;
        int i21 = i19 | ((bArr[i18] & 255) << 16);
        this.C2 = i21;
        int i22 = i20 + 1;
        this.C2 = i21 | (bArr[i20] << UCharacterEnums.ECharacterCategory.MATH_SYMBOL);
        int i23 = i22 + 1;
        int i24 = bArr[i22] & 255;
        this.C3 = i24;
        int i25 = i23 + 1;
        int i26 = ((bArr[i23] & 255) << 8) | i24;
        this.C3 = i26;
        int i27 = i26 | ((bArr[i25] & 255) << 16);
        this.C3 = i27;
        this.C3 = (bArr[i25 + 1] << UCharacterEnums.ECharacterCategory.MATH_SYMBOL) | i27;
    }

    private final void stateOut(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = this.C0;
        bArr[i] = (byte) i3;
        int i4 = i2 + 1;
        bArr[i2] = (byte) (i3 >> 8);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i3 >> 16);
        int i6 = i5 + 1;
        bArr[i5] = (byte) (i3 >> 24);
        int i7 = i6 + 1;
        int i8 = this.C1;
        bArr[i6] = (byte) i8;
        int i9 = i7 + 1;
        bArr[i7] = (byte) (i8 >> 8);
        int i10 = i9 + 1;
        bArr[i9] = (byte) (i8 >> 16);
        int i11 = i10 + 1;
        bArr[i10] = (byte) (i8 >> 24);
        int i12 = i11 + 1;
        int i13 = this.C2;
        bArr[i11] = (byte) i13;
        int i14 = i12 + 1;
        bArr[i12] = (byte) (i13 >> 8);
        int i15 = i14 + 1;
        bArr[i14] = (byte) (i13 >> 16);
        int i16 = i15 + 1;
        bArr[i15] = (byte) (i13 >> 24);
        int i17 = i16 + 1;
        int i18 = this.C3;
        bArr[i16] = (byte) i18;
        int i19 = i17 + 1;
        bArr[i17] = (byte) (i18 >> 8);
        bArr[i19] = (byte) (i18 >> 16);
        bArr[i19 + 1] = (byte) (i18 >> 24);
    }

    private final void encryptBlock(int[][] iArr) {
        this.C0 ^= iArr[0][0];
        this.C1 ^= iArr[0][1];
        this.C2 ^= iArr[0][2];
        this.C3 ^= iArr[0][3];
        int i = 1;
        while (i < this.rounds - 1) {
            int[] iArr2 = T0;
            int iShift = (((shift(iArr2[(this.C1 >> 8) & 255], 24) ^ iArr2[this.C0 & 255]) ^ shift(T0[(this.C2 >> 16) & 255], 16)) ^ shift(T0[(this.C3 >> 24) & 255], 8)) ^ iArr[i][0];
            int[] iArr3 = T0;
            int iShift2 = (((shift(iArr3[(this.C2 >> 8) & 255], 24) ^ iArr3[this.C1 & 255]) ^ shift(T0[(this.C3 >> 16) & 255], 16)) ^ shift(T0[(this.C0 >> 24) & 255], 8)) ^ iArr[i][1];
            int[] iArr4 = T0;
            int iShift3 = (((shift(iArr4[(this.C3 >> 8) & 255], 24) ^ iArr4[this.C2 & 255]) ^ shift(T0[(this.C0 >> 16) & 255], 16)) ^ shift(T0[(this.C1 >> 24) & 255], 8)) ^ iArr[i][2];
            int[] iArr5 = T0;
            int i2 = i + 1;
            int iShift4 = iArr[i][3] ^ (((shift(iArr5[(this.C0 >> 8) & 255], 24) ^ iArr5[this.C3 & 255]) ^ shift(T0[(this.C1 >> 16) & 255], 16)) ^ shift(T0[(this.C2 >> 24) & 255], 8));
            int[] iArr6 = T0;
            this.C0 = (((shift(iArr6[(iShift2 >> 8) & 255], 24) ^ iArr6[iShift & 255]) ^ shift(T0[(iShift3 >> 16) & 255], 16)) ^ shift(T0[(iShift4 >> 24) & 255], 8)) ^ iArr[i2][0];
            int[] iArr7 = T0;
            this.C1 = (((shift(iArr7[(iShift3 >> 8) & 255], 24) ^ iArr7[iShift2 & 255]) ^ shift(T0[(iShift4 >> 16) & 255], 16)) ^ shift(T0[(iShift >> 24) & 255], 8)) ^ iArr[i2][1];
            int[] iArr8 = T0;
            this.C2 = (((shift(iArr8[(iShift4 >> 8) & 255], 24) ^ iArr8[iShift3 & 255]) ^ shift(T0[(iShift >> 16) & 255], 16)) ^ shift(T0[(iShift2 >> 24) & 255], 8)) ^ iArr[i2][2];
            int[] iArr9 = T0;
            this.C3 = (((iArr9[iShift4 & 255] ^ shift(iArr9[(iShift >> 8) & 255], 24)) ^ shift(T0[(iShift2 >> 16) & 255], 16)) ^ shift(T0[(iShift3 >> 24) & 255], 8)) ^ iArr[i2][3];
            i = i2 + 1;
        }
        int[] iArr10 = T0;
        int iShift5 = (((shift(iArr10[(this.C1 >> 8) & 255], 24) ^ iArr10[this.C0 & 255]) ^ shift(T0[(this.C2 >> 16) & 255], 16)) ^ shift(T0[(this.C3 >> 24) & 255], 8)) ^ iArr[i][0];
        int[] iArr11 = T0;
        int iShift6 = (((shift(iArr11[(this.C2 >> 8) & 255], 24) ^ iArr11[this.C1 & 255]) ^ shift(T0[(this.C3 >> 16) & 255], 16)) ^ shift(T0[(this.C0 >> 24) & 255], 8)) ^ iArr[i][1];
        int[] iArr12 = T0;
        int iShift7 = (((shift(iArr12[(this.C3 >> 8) & 255], 24) ^ iArr12[this.C2 & 255]) ^ shift(T0[(this.C0 >> 16) & 255], 16)) ^ shift(T0[(this.C1 >> 24) & 255], 8)) ^ iArr[i][2];
        int[] iArr13 = T0;
        int i3 = i + 1;
        int iShift8 = iArr[i][3] ^ (((shift(iArr13[(this.C0 >> 8) & 255], 24) ^ iArr13[this.C3 & 255]) ^ shift(T0[(this.C1 >> 16) & 255], 16)) ^ shift(T0[(this.C2 >> 24) & 255], 8));
        byte[] bArr = S;
        this.C0 = iArr[i3][0] ^ ((((bArr[iShift5 & 255] & 255) ^ ((bArr[(iShift6 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift7 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift8 >> 24) & 255] << UCharacterEnums.ECharacterCategory.MATH_SYMBOL));
        this.C1 = ((((bArr[iShift6 & 255] & 255) ^ ((bArr[(iShift7 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift8 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift5 >> 24) & 255] << UCharacterEnums.ECharacterCategory.MATH_SYMBOL)) ^ iArr[i3][1];
        this.C2 = ((((bArr[iShift7 & 255] & 255) ^ ((bArr[(iShift8 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift5 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift6 >> 24) & 255] << UCharacterEnums.ECharacterCategory.MATH_SYMBOL)) ^ iArr[i3][2];
        this.C3 = ((((bArr[iShift8 & 255] & 255) ^ ((bArr[(iShift5 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iShift6 >> 16) & 255] & 255) << 16)) ^ (bArr[(iShift7 >> 24) & 255] << UCharacterEnums.ECharacterCategory.MATH_SYMBOL)) ^ iArr[i3][3];
    }

    private int subWord(int i) {
        byte[] bArr = S;
        return (bArr[(i >> 24) & 255] << UCharacterEnums.ECharacterCategory.MATH_SYMBOL) | (bArr[i & 255] & 255) | ((bArr[(i >> 8) & 255] & 255) << 8) | ((bArr[(i >> 16) & 255] & 255) << 16);
    }
}
