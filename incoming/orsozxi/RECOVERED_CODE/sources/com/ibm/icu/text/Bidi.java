package com.ibm.icu.text;

import com.ibm.icu.impl.UBiDiProps;
import com.ibm.icu.lang.UCharacter;
import java.awt.font.NumericShaper;
import java.awt.font.TextAttribute;
import java.lang.reflect.Array;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;
import org.apache.poi.hssf.record.EscherAggregate;
import org.apache.poi.hssf.record.formula.NumberPtg;
import org.apache.poi.hssf.record.formula.Ptg;

/* loaded from: classes2.dex */
public class Bidi {
    static final byte AL = 13;
    static final byte AN = 5;
    static final byte B = 7;
    static final byte BN = 18;

    @Deprecated
    public static final int CLASS_DEFAULT = 23;
    private static final char CR = '\r';
    static final byte CS = 6;
    public static final int DIRECTION_DEFAULT_LEFT_TO_RIGHT = 126;
    public static final int DIRECTION_DEFAULT_RIGHT_TO_LEFT = 127;
    public static final int DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int DIRECTION_RIGHT_TO_LEFT = 1;
    public static final short DO_MIRRORING = 2;
    static final byte EN = 2;
    static final byte ENL = 23;
    static final byte ENR = 24;
    static final byte ES = 3;
    static final byte ET = 4;
    static final int FIRSTALLOC = 10;
    static final byte FSI = 19;
    private static final int IMPTABLEVELS_COLUMNS = 8;
    private static final int IMPTABLEVELS_RES = 7;
    private static final int IMPTABPROPS_COLUMNS = 16;
    private static final int IMPTABPROPS_RES = 15;
    public static final short INSERT_LRM_FOR_NUMERIC = 4;
    static final int ISOLATE = 256;
    public static final short KEEP_BASE_COMBINING = 1;
    static final byte L = 0;
    public static final byte LEVEL_DEFAULT_LTR = 126;
    public static final byte LEVEL_DEFAULT_RTL = 127;
    public static final byte LEVEL_OVERRIDE = -128;
    private static final char LF = '\n';
    static final int LOOKING_FOR_PDI = 3;
    static final byte LRE = 11;
    static final byte LRI = 20;
    static final int LRM_AFTER = 2;
    static final int LRM_BEFORE = 1;
    static final byte LRO = 12;
    public static final byte LTR = 0;
    public static final int MAP_NOWHERE = -1;
    static final int MASK_B_S;
    static final int MASK_EMBEDDING;
    static final int MASK_POSSIBLE_N;
    static final int MASK_WS;
    public static final byte MAX_EXPLICIT_LEVEL = 125;
    public static final byte MIXED = 2;
    public static final byte NEUTRAL = 3;
    static final int NOT_SEEKING_STRONG = 0;
    static final byte NSM = 17;
    static final byte ON = 10;
    public static final int OPTION_DEFAULT = 0;
    public static final int OPTION_INSERT_MARKS = 1;
    public static final int OPTION_REMOVE_CONTROLS = 2;
    public static final int OPTION_STREAMING = 4;
    public static final short OUTPUT_REVERSE = 16;
    static final byte PDF = 16;
    static final byte PDI = 22;
    static final byte R = 1;
    public static final short REMOVE_BIDI_CONTROLS = 8;
    static final short REORDER_COUNT = 7;
    public static final short REORDER_DEFAULT = 0;
    public static final short REORDER_GROUP_NUMBERS_WITH_R = 2;
    public static final short REORDER_INVERSE_FOR_NUMBERS_SPECIAL = 6;
    public static final short REORDER_INVERSE_LIKE_DIRECT = 5;
    public static final short REORDER_INVERSE_NUMBERS_AS_L = 4;
    static final short REORDER_LAST_LOGICAL_TO_VISUAL = 1;
    public static final short REORDER_NUMBERS_SPECIAL = 1;
    public static final short REORDER_RUNS_ONLY = 3;
    static final byte RLE = 14;
    static final byte RLI = 21;
    static final int RLM_AFTER = 8;
    static final int RLM_BEFORE = 4;
    static final byte RLO = 15;
    public static final byte RTL = 1;
    static final byte S = 8;
    static final int SEEKING_STRONG_FOR_FSI = 2;
    static final int SEEKING_STRONG_FOR_PARA = 1;
    static final int SIMPLE_OPENINGS_COUNT = 20;
    static final int SIMPLE_PARAS_COUNT = 10;
    static final byte WS = 9;
    private static final short _AN = 3;
    private static final short _B = 6;
    private static final short _EN = 2;
    private static final short _L = 0;
    private static final short _ON = 4;
    private static final short _R = 1;
    private static final short _S = 5;
    private static final short[] groupProp;
    private static final short[] impAct0;
    private static final short[] impAct1;
    private static final short[] impAct2;
    private static final short[] impAct3;
    private static final byte[][] impTabL_DEFAULT;
    private static final byte[][] impTabL_GROUP_NUMBERS_WITH_R;
    private static final byte[][] impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS;
    private static final byte[][] impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS;
    private static final byte[][] impTabL_INVERSE_NUMBERS_AS_L;
    private static final byte[][] impTabL_NUMBERS_SPECIAL;
    private static final short[][] impTabProps;
    private static final byte[][] impTabR_DEFAULT;
    private static final byte[][] impTabR_GROUP_NUMBERS_WITH_R;
    private static final byte[][] impTabR_INVERSE_LIKE_DIRECT;
    private static final byte[][] impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS;
    private static final byte[][] impTabR_INVERSE_NUMBERS_AS_L;
    private static final ImpTabPair impTab_DEFAULT;
    private static final ImpTabPair impTab_GROUP_NUMBERS_WITH_R;
    private static final ImpTabPair impTab_INVERSE_FOR_NUMBERS_SPECIAL;
    private static final ImpTabPair impTab_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS;
    private static final ImpTabPair impTab_INVERSE_LIKE_DIRECT;
    private static final ImpTabPair impTab_INVERSE_LIKE_DIRECT_WITH_MARKS;
    private static final ImpTabPair impTab_INVERSE_NUMBERS_AS_L;
    private static final ImpTabPair impTab_NUMBERS_SPECIAL;
    final UBiDiProps bdp;
    int controlCount;
    BidiClassifier customClassifier;
    byte defaultParaLevel;
    byte[] dirProps;
    byte[] dirPropsMemory;
    byte direction;
    String epilogue;
    int flags;
    ImpTabPair impTabPair;
    InsertPoints insertPoints;
    boolean isGoodLogicalToVisualRunsMap;
    boolean isInverse;
    int isolateCount;
    Isolate[] isolates;
    int lastArabicPos;
    int length;
    byte[] levels;
    byte[] levelsMemory;
    int[] logicalToVisualRunsMap;
    boolean mayAllocateRuns;
    boolean mayAllocateText;
    boolean orderParagraphsLTR;
    int originalLength;
    Bidi paraBidi;
    int paraCount;
    byte paraLevel;
    byte[] paras_level;
    int[] paras_limit;
    String prologue;
    int reorderingMode;
    int reorderingOptions;
    int resultLength;
    int runCount;
    BidiRun[] runs;
    BidiRun[] runsMemory;
    BidiRun[] simpleRuns;
    char[] text;
    int trailingWSStart;
    static final byte FOUND_L = (byte) DirPropFlag((byte) 0);
    static final byte FOUND_R = (byte) DirPropFlag((byte) 1);
    static final int DirPropFlagMultiRuns = DirPropFlag(NumberPtg.sid);
    static final int[] DirPropFlagLR = {DirPropFlag((byte) 0), DirPropFlag((byte) 1)};
    static final int[] DirPropFlagE = {DirPropFlag((byte) 11), DirPropFlag((byte) 14)};
    static final int[] DirPropFlagO = {DirPropFlag((byte) 12), DirPropFlag((byte) 15)};
    static final int MASK_LTR = ((((((DirPropFlag((byte) 0) | DirPropFlag((byte) 2)) | DirPropFlag((byte) 23)) | DirPropFlag((byte) 24)) | DirPropFlag((byte) 5)) | DirPropFlag((byte) 11)) | DirPropFlag((byte) 12)) | DirPropFlag((byte) 20);
    static final int MASK_RTL = (((DirPropFlag((byte) 1) | DirPropFlag((byte) 13)) | DirPropFlag((byte) 14)) | DirPropFlag((byte) 15)) | DirPropFlag((byte) 21);
    static final int MASK_R_AL = DirPropFlag((byte) 1) | DirPropFlag((byte) 13);
    static final int MASK_STRONG_EN_AN = (((DirPropFlag((byte) 0) | DirPropFlag((byte) 1)) | DirPropFlag((byte) 13)) | DirPropFlag((byte) 2)) | DirPropFlag((byte) 5);
    static final int MASK_EXPLICIT = (((DirPropFlag((byte) 11) | DirPropFlag((byte) 12)) | DirPropFlag((byte) 14)) | DirPropFlag((byte) 15)) | DirPropFlag((byte) 16);
    static final int MASK_BN_EXPLICIT = DirPropFlag((byte) 18) | MASK_EXPLICIT;
    static final int MASK_ISO = ((DirPropFlag((byte) 20) | DirPropFlag((byte) 21)) | DirPropFlag((byte) 19)) | DirPropFlag((byte) 22);

    static final byte DirFromStrong(byte b) {
        return b == 0 ? (byte) 0 : (byte) 1;
    }

    static int DirPropFlag(byte b) {
        return 1 << b;
    }

    private static short GetAction(byte b) {
        return (short) (b >> 4);
    }

    private static short GetActionProps(short s) {
        return (short) (s >> 5);
    }

    static byte GetLRFromLevel(byte b) {
        return (byte) (b & 1);
    }

    private static short GetState(byte b) {
        return (short) (b & 15);
    }

    private static short GetStateProps(short s) {
        return (short) (s & 31);
    }

    static boolean IsBidiControlChar(int i) {
        return (i & (-4)) == 8204 || (i >= 8234 && i <= 8238) || (i >= 8294 && i <= 8297);
    }

    static boolean IsDefaultLevel(byte b) {
        return (b & LEVEL_DEFAULT_LTR) == 126;
    }

    static final byte NoOverride(byte b) {
        return (byte) (b & 127);
    }

    int Bidi_Abs(int i) {
        return i >= 0 ? i : -i;
    }

    int Bidi_Min(int i, int i2) {
        return i < i2 ? i : i2;
    }

    static class Point {
        int flag;
        int pos;

        Point() {
        }
    }

    static class InsertPoints {
        int confirmed;
        Point[] points = new Point[0];
        int size;

        InsertPoints() {
        }
    }

    static class Opening {
        byte contextDir;
        int contextPos;
        short flags;
        int match;
        int position;

        Opening() {
        }
    }

    static class IsoRun {
        byte contextDir;
        int contextPos;
        byte lastBase;
        byte lastStrong;
        byte level;
        short limit;
        short start;

        IsoRun() {
        }
    }

    static class BracketData {
        boolean isNumbersSpecial;
        int isoRunLast;
        Opening[] openings = new Opening[20];
        IsoRun[] isoRuns = new IsoRun[127];

        BracketData() {
        }
    }

    static class Isolate {
        int start1;
        int startON;
        short state;
        short stateImp;

        Isolate() {
        }
    }

    static {
        int iDirPropFlag = DirPropFlag((byte) 7) | DirPropFlag((byte) 8);
        MASK_B_S = iDirPropFlag;
        MASK_WS = iDirPropFlag | DirPropFlag((byte) 9) | MASK_BN_EXPLICIT | MASK_ISO;
        MASK_POSSIBLE_N = DirPropFlag((byte) 10) | DirPropFlag((byte) 6) | DirPropFlag((byte) 3) | DirPropFlag((byte) 4) | MASK_WS;
        MASK_EMBEDDING = DirPropFlag((byte) 17) | MASK_POSSIBLE_N;
        groupProp = new short[]{0, 1, 2, 7, 8, 3, 9, 6, 5, 4, 4, 10, 10, 12, 10, 10, 10, 11, 10, 4, 4, 4, 4, 13, 14};
        impTabProps = new short[][]{new short[]{1, 2, 4, 5, 7, 15, 17, 7, 9, 7, 0, 7, 3, 18, 21, 4}, new short[]{1, 34, 36, 37, 39, 47, 49, 39, 41, 39, 1, 1, 35, 50, 53, 0}, new short[]{33, 2, 36, 37, 39, 47, 49, 39, 41, 39, 2, 2, 35, 50, 53, 1}, new short[]{33, 34, 38, 38, 40, 48, 49, 40, 40, 40, 3, 3, 3, 50, 53, 1}, new short[]{33, 34, 4, 37, 39, 47, 49, 74, 11, 74, 4, 4, 35, 18, 21, 2}, new short[]{33, 34, 36, 5, 39, 47, 49, 39, 41, 76, 5, 5, 35, 50, 53, 3}, new short[]{33, 34, 6, 6, 40, 48, 49, 40, 40, 77, 6, 6, 35, 18, 21, 3}, new short[]{33, 34, 36, 37, 7, 47, 49, 7, 78, 7, 7, 7, 35, 50, 53, 4}, new short[]{33, 34, 38, 38, 8, 48, 49, 8, 8, 8, 8, 8, 35, 50, 53, 4}, new short[]{33, 34, 4, 37, 7, 47, 49, 7, 9, 7, 9, 9, 35, 18, 21, 4}, new short[]{97, 98, 4, EscherAggregate.ST_UTURNARROW, 135, EscherAggregate.ST_FLOWCHARTINPUTOUTPUT, EscherAggregate.ST_FLOWCHARTINTERNALSTORAGE, 135, EscherAggregate.ST_TEXTRINGINSIDE, 135, 10, 135, 99, 18, 21, 2}, new short[]{33, 34, 4, 37, 39, 47, 49, 39, 11, 39, 11, 11, 35, 18, 21, 2}, new short[]{97, 98, 100, 5, 135, EscherAggregate.ST_FLOWCHARTINPUTOUTPUT, EscherAggregate.ST_FLOWCHARTINTERNALSTORAGE, 135, EscherAggregate.ST_TEXTRINGINSIDE, 135, 12, 135, 99, EscherAggregate.ST_FLOWCHARTDOCUMENT, EscherAggregate.ST_FLOWCHARTPREPARATION, 3}, new short[]{97, 98, 6, 6, 136, EscherAggregate.ST_FLOWCHARTPREDEFINEDPROCESS, EscherAggregate.ST_FLOWCHARTINTERNALSTORAGE, 136, 136, 136, 13, 136, 99, 18, 21, 3}, new short[]{33, 34, 132, 37, 7, 47, 49, 7, 14, 7, 14, 14, 35, 146, EscherAggregate.ST_TEXTARCHDOWNPOUR, 4}, new short[]{33, 34, 36, 37, 39, 15, 49, 39, 41, 39, 15, 39, 35, 50, 53, 5}, new short[]{33, 34, 38, 38, 40, 16, 49, 40, 40, 40, 16, 40, 35, 50, 53, 5}, new short[]{33, 34, 36, 37, 39, 47, 17, 39, 41, 39, 17, 39, 35, 50, 53, 6}, new short[]{33, 34, 18, 37, 39, 47, 49, 83, 20, 83, 18, 18, 35, 18, 21, 0}, new short[]{97, 98, 18, EscherAggregate.ST_UTURNARROW, 135, EscherAggregate.ST_FLOWCHARTINPUTOUTPUT, EscherAggregate.ST_FLOWCHARTINTERNALSTORAGE, 135, EscherAggregate.ST_TEXTRINGINSIDE, 135, 19, 135, 99, 18, 21, 0}, new short[]{33, 34, 18, 37, 39, 47, 49, 39, 20, 39, 20, 20, 35, 18, 21, 0}, new short[]{33, 34, 21, 37, 39, 47, 49, 86, 23, 86, 21, 21, 35, 18, 21, 3}, new short[]{97, 98, 21, EscherAggregate.ST_UTURNARROW, 135, EscherAggregate.ST_FLOWCHARTINPUTOUTPUT, EscherAggregate.ST_FLOWCHARTINTERNALSTORAGE, 135, EscherAggregate.ST_TEXTRINGINSIDE, 135, 22, 135, 99, 18, 21, 3}, new short[]{33, 34, 21, 37, 39, 47, 49, 39, 23, 39, 23, 23, 35, 18, 21, 3}};
        byte[][] bArr = {new byte[]{0, 1, 0, 2, 0, 0, 0, 0}, new byte[]{0, 1, 3, 3, 20, 20, 0, 1}, new byte[]{0, 1, 0, 2, 21, 21, 0, 2}, new byte[]{0, 1, 3, 3, 20, 20, 0, 2}, new byte[]{0, 33, 51, 51, 4, 4, 0, 0}, new byte[]{0, 33, 0, 50, 5, 5, 0, 0}};
        impTabL_DEFAULT = bArr;
        byte[][] bArr2 = {new byte[]{1, 0, 2, 2, 0, 0, 0, 0}, new byte[]{1, 0, 1, 3, 20, 20, 0, 1}, new byte[]{1, 0, 2, 2, 0, 0, 0, 1}, new byte[]{1, 0, 1, 3, 5, 5, 0, 1}, new byte[]{33, 0, 33, 3, 4, 4, 0, 0}, new byte[]{1, 0, 1, 3, 5, 5, 0, 0}};
        impTabR_DEFAULT = bArr2;
        short[] sArr = {0, 1, 2, 3, 4};
        impAct0 = sArr;
        impTab_DEFAULT = new ImpTabPair(bArr, bArr2, sArr, sArr);
        byte[][] bArr3 = {new byte[]{0, 2, 17, 17, 0, 0, 0, 0}, new byte[]{0, 66, 1, 1, 0, 0, 0, 0}, new byte[]{0, 2, 4, 4, 19, 19, 0, 1}, new byte[]{0, 34, 52, 52, 3, 3, 0, 0}, new byte[]{0, 2, 4, 4, 19, 19, 0, 2}};
        impTabL_NUMBERS_SPECIAL = bArr3;
        byte[][] bArr4 = impTabR_DEFAULT;
        short[] sArr2 = impAct0;
        impTab_NUMBERS_SPECIAL = new ImpTabPair(bArr3, bArr4, sArr2, sArr2);
        byte[][] bArr5 = {new byte[]{0, 3, 17, 17, 0, 0, 0, 0}, new byte[]{32, 3, 1, 1, 2, 32, 32, 2}, new byte[]{32, 3, 1, 1, 2, 32, 32, 1}, new byte[]{0, 3, 5, 5, 20, 0, 0, 1}, new byte[]{32, 3, 5, 5, 4, 32, 32, 1}, new byte[]{0, 3, 5, 5, 20, 0, 0, 2}};
        impTabL_GROUP_NUMBERS_WITH_R = bArr5;
        byte[][] bArr6 = {new byte[]{2, 0, 1, 1, 0, 0, 0, 0}, new byte[]{2, 0, 1, 1, 0, 0, 0, 1}, new byte[]{2, 0, 20, 20, 19, 0, 0, 1}, new byte[]{34, 0, 4, 4, 3, 0, 0, 0}, new byte[]{34, 0, 4, 4, 3, 0, 0, 1}};
        impTabR_GROUP_NUMBERS_WITH_R = bArr6;
        short[] sArr3 = impAct0;
        impTab_GROUP_NUMBERS_WITH_R = new ImpTabPair(bArr5, bArr6, sArr3, sArr3);
        byte[][] bArr7 = {new byte[]{0, 1, 0, 0, 0, 0, 0, 0}, new byte[]{0, 1, 0, 0, 20, 20, 0, 1}, new byte[]{0, 1, 0, 0, 21, 21, 0, 2}, new byte[]{0, 1, 0, 0, 20, 20, 0, 2}, new byte[]{32, 1, 32, 32, 4, 4, 32, 1}, new byte[]{32, 1, 32, 32, 5, 5, 32, 1}};
        impTabL_INVERSE_NUMBERS_AS_L = bArr7;
        byte[][] bArr8 = {new byte[]{1, 0, 1, 1, 0, 0, 0, 0}, new byte[]{1, 0, 1, 1, 20, 20, 0, 1}, new byte[]{1, 0, 1, 1, 0, 0, 0, 1}, new byte[]{1, 0, 1, 1, 5, 5, 0, 1}, new byte[]{33, 0, 33, 33, 4, 4, 0, 0}, new byte[]{1, 0, 1, 1, 5, 5, 0, 0}};
        impTabR_INVERSE_NUMBERS_AS_L = bArr8;
        short[] sArr4 = impAct0;
        impTab_INVERSE_NUMBERS_AS_L = new ImpTabPair(bArr7, bArr8, sArr4, sArr4);
        byte[][] bArr9 = {new byte[]{1, 0, 2, 2, 0, 0, 0, 0}, new byte[]{1, 0, 1, 2, 19, 19, 0, 1}, new byte[]{1, 0, 2, 2, 0, 0, 0, 1}, new byte[]{33, 48, 6, 4, 3, 3, 48, 0}, new byte[]{33, 48, 6, 4, 5, 5, 48, 3}, new byte[]{33, 48, 6, 4, 5, 5, 48, 2}, new byte[]{33, 48, 6, 4, 3, 3, 48, 1}};
        impTabR_INVERSE_LIKE_DIRECT = bArr9;
        short[] sArr5 = {0, 1, 13, 14};
        impAct1 = sArr5;
        impTab_INVERSE_LIKE_DIRECT = new ImpTabPair(impTabL_DEFAULT, bArr9, impAct0, sArr5);
        byte[][] bArr10 = {new byte[]{0, 99, 0, 1, 0, 0, 0, 0}, new byte[]{0, 99, 0, 1, 18, 48, 0, 4}, new byte[]{32, 99, 32, 1, 2, 48, 32, 3}, new byte[]{0, 99, 85, 86, 20, 48, 0, 3}, new byte[]{48, 67, 85, 86, 4, 48, 48, 3}, new byte[]{48, 67, 5, 86, 20, 48, 48, 4}, new byte[]{48, 67, 85, 6, 20, 48, 48, 4}};
        impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS = bArr10;
        byte[][] bArr11 = {new byte[]{19, 0, 1, 1, 0, 0, 0, 0}, new byte[]{35, 0, 1, 1, 2, Ptg.CLASS_ARRAY, 0, 1}, new byte[]{35, 0, 1, 1, 2, Ptg.CLASS_ARRAY, 0, 0}, new byte[]{3, 0, 3, 54, 20, Ptg.CLASS_ARRAY, 0, 1}, new byte[]{83, Ptg.CLASS_ARRAY, 5, 54, 4, Ptg.CLASS_ARRAY, Ptg.CLASS_ARRAY, 0}, new byte[]{83, Ptg.CLASS_ARRAY, 5, 54, 4, Ptg.CLASS_ARRAY, Ptg.CLASS_ARRAY, 1}, new byte[]{83, Ptg.CLASS_ARRAY, 6, 6, 4, Ptg.CLASS_ARRAY, Ptg.CLASS_ARRAY, 3}};
        impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS = bArr11;
        short[] sArr6 = {0, 1, 2, 5, 6, 7, 8};
        impAct2 = sArr6;
        short[] sArr7 = {0, 1, 9, 10, 11, 12};
        impAct3 = sArr7;
        impTab_INVERSE_LIKE_DIRECT_WITH_MARKS = new ImpTabPair(bArr10, bArr11, sArr6, sArr7);
        impTab_INVERSE_FOR_NUMBERS_SPECIAL = new ImpTabPair(impTabL_NUMBERS_SPECIAL, impTabR_INVERSE_LIKE_DIRECT, impAct0, impAct1);
        byte[][] bArr12 = {new byte[]{0, 98, 1, 1, 0, 0, 0, 0}, new byte[]{0, 98, 1, 1, 0, 48, 0, 4}, new byte[]{0, 98, 84, 84, 19, 48, 0, 3}, new byte[]{48, 66, 84, 84, 3, 48, 48, 3}, new byte[]{48, 66, 4, 4, 19, 48, 48, 4}};
        impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS = bArr12;
        impTab_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS = new ImpTabPair(bArr12, impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS, impAct2, impAct3);
    }

    boolean testDirPropFlagAt(int i, int i2) {
        return (i & DirPropFlag(this.dirProps[i2])) != 0;
    }

    static final int DirPropFlagLR(byte b) {
        return DirPropFlagLR[b & 1];
    }

    static final int DirPropFlagE(byte b) {
        return DirPropFlagE[b & 1];
    }

    static final int DirPropFlagO(byte b) {
        return DirPropFlagO[b & 1];
    }

    void verifyValidPara() {
        if (this != this.paraBidi) {
            throw new IllegalStateException();
        }
    }

    void verifyValidParaOrLine() {
        Bidi bidi = this.paraBidi;
        if (this == bidi) {
            return;
        }
        if (bidi == null || bidi != bidi.paraBidi) {
            throw new IllegalStateException();
        }
    }

    void verifyRange(int i, int i2, int i3) {
        if (i < i2 || i >= i3) {
            throw new IllegalArgumentException("Value " + i + " is out of range " + i2 + " to " + i3);
        }
    }

    public Bidi() {
        this(0, 0);
    }

    public Bidi(int i, int i2) {
        this.dirPropsMemory = new byte[1];
        this.levelsMemory = new byte[1];
        this.paras_limit = new int[10];
        this.paras_level = new byte[10];
        this.runsMemory = new BidiRun[0];
        this.simpleRuns = new BidiRun[]{new BidiRun()};
        this.customClassifier = null;
        this.insertPoints = new InsertPoints();
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException();
        }
        this.bdp = UBiDiProps.INSTANCE;
        if (i > 0) {
            getInitialDirPropsMemory(i);
            getInitialLevelsMemory(i);
        } else {
            this.mayAllocateText = true;
        }
        if (i2 <= 0) {
            this.mayAllocateRuns = true;
        } else if (i2 > 1) {
            getInitialRunsMemory(i2);
        }
    }

    private Object getMemory(String str, Object obj, Class<?> cls, boolean z, int i) {
        int length = Array.getLength(obj);
        if (i == length) {
            return obj;
        }
        if (z) {
            try {
                return Array.newInstance(cls, i);
            } catch (Exception unused) {
                throw new OutOfMemoryError("Failed to allocate memory for " + str);
            }
        }
        if (i <= length) {
            return obj;
        }
        throw new OutOfMemoryError("Failed to allocate memory for " + str);
    }

    private void getDirPropsMemory(boolean z, int i) {
        this.dirPropsMemory = (byte[]) getMemory("DirProps", this.dirPropsMemory, Byte.TYPE, z, i);
    }

    void getDirPropsMemory(int i) {
        getDirPropsMemory(this.mayAllocateText, i);
    }

    private void getLevelsMemory(boolean z, int i) {
        this.levelsMemory = (byte[]) getMemory("Levels", this.levelsMemory, Byte.TYPE, z, i);
    }

    void getLevelsMemory(int i) {
        getLevelsMemory(this.mayAllocateText, i);
    }

    private void getRunsMemory(boolean z, int i) {
        this.runsMemory = (BidiRun[]) getMemory("Runs", this.runsMemory, BidiRun.class, z, i);
    }

    void getRunsMemory(int i) {
        getRunsMemory(this.mayAllocateRuns, i);
    }

    private void getInitialDirPropsMemory(int i) {
        getDirPropsMemory(true, i);
    }

    private void getInitialLevelsMemory(int i) {
        getLevelsMemory(true, i);
    }

    private void getInitialRunsMemory(int i) {
        getRunsMemory(true, i);
    }

    public void setInverse(boolean z) {
        this.isInverse = z;
        this.reorderingMode = z ? 4 : 0;
    }

    public boolean isInverse() {
        return this.isInverse;
    }

    public void setReorderingMode(int i) {
        if (i < 0 || i >= 7) {
            return;
        }
        this.reorderingMode = i;
        this.isInverse = i == 4;
    }

    public int getReorderingMode() {
        return this.reorderingMode;
    }

    public void setReorderingOptions(int i) {
        if ((i & 2) != 0) {
            this.reorderingOptions = i & (-2);
        } else {
            this.reorderingOptions = i;
        }
    }

    public int getReorderingOptions() {
        return this.reorderingOptions;
    }

    public static byte getBaseDirection(CharSequence charSequence) {
        if (charSequence != null && charSequence.length() != 0) {
            int length = charSequence.length();
            int iOffsetByCodePoints = 0;
            while (iOffsetByCodePoints < length) {
                byte directionality = UCharacter.getDirectionality(UCharacter.codePointAt(charSequence, iOffsetByCodePoints));
                if (directionality == 0) {
                    return (byte) 0;
                }
                if (directionality == 1 || directionality == 13) {
                    return (byte) 1;
                }
                iOffsetByCodePoints = UCharacter.offsetByCodePoints(charSequence, iOffsetByCodePoints, 1);
            }
        }
        return (byte) 3;
    }

    private byte firstL_R_AL() {
        int iCharCount = 0;
        while (true) {
            byte b = 10;
            while (iCharCount < this.prologue.length()) {
                int iCodePointAt = this.prologue.codePointAt(iCharCount);
                iCharCount += Character.charCount(iCodePointAt);
                byte customizedClass = (byte) getCustomizedClass(iCodePointAt);
                if (b == 10) {
                    if (customizedClass == 0 || customizedClass == 1 || customizedClass == 13) {
                        b = customizedClass;
                    }
                } else if (customizedClass == 7) {
                    break;
                }
            }
            return b;
        }
    }

    private void checkParaCount() {
        int i = this.paraCount;
        byte[] bArr = this.paras_level;
        if (i <= bArr.length) {
            return;
        }
        int length = bArr.length;
        int[] iArr = this.paras_limit;
        int i2 = i * 2;
        try {
            int[] iArr2 = new int[i2];
            this.paras_limit = iArr2;
            this.paras_level = new byte[i2];
            System.arraycopy(iArr, 0, iArr2, 0, length);
            System.arraycopy(bArr, 0, this.paras_level, 0, length);
        } catch (Exception unused) {
            throw new OutOfMemoryError("Failed to allocate memory for paras");
        }
    }

    private void getDirProps() {
        byte b;
        byte b2;
        byte b3;
        byte b4;
        int i;
        byte bFirstL_R_AL;
        int i2;
        int i3 = 0;
        this.flags = 0;
        boolean zIsDefaultLevel = IsDefaultLevel(this.paraLevel);
        boolean z = zIsDefaultLevel && ((i2 = this.reorderingMode) == 5 || i2 == 6);
        this.lastArabicPos = -1;
        boolean z2 = (this.reorderingOptions & 2) != 0;
        int[] iArr = new int[126];
        byte[] bArr = new byte[126];
        if ((this.reorderingOptions & 4) != 0) {
            this.length = 0;
        }
        byte b5 = this.paraLevel;
        byte b6 = (byte) (b5 & 1);
        if (zIsDefaultLevel) {
            this.paras_level[0] = b6;
            if (this.prologue == null || (bFirstL_R_AL = firstL_R_AL()) == 10) {
                b2 = b6;
                b = 1;
            } else {
                if (bFirstL_R_AL == 0) {
                    this.paras_level[0] = 0;
                } else {
                    this.paras_level[0] = 1;
                }
                b2 = b6;
                b = 0;
            }
        } else {
            this.paras_level[0] = b5;
            b = 0;
            b2 = 10;
        }
        int i4 = 0;
        int i5 = -1;
        int i6 = 0;
        while (true) {
            int i7 = this.originalLength;
            if (i6 >= i7) {
                break;
            }
            int iCharAt = UTF16.charAt(this.text, i3, i7, i6);
            int charCount = UTF16.getCharCount(iCharAt) + i6;
            int i8 = charCount - 1;
            byte customizedClass = (byte) getCustomizedClass(iCharAt);
            byte b7 = b6;
            int iDirPropFlag = this.flags | DirPropFlag(customizedClass);
            this.flags = iDirPropFlag;
            boolean z3 = zIsDefaultLevel;
            this.dirProps[i8] = customizedClass;
            if (i8 > i6) {
                this.flags = iDirPropFlag | DirPropFlag((byte) 18);
                int i9 = i8;
                b4 = b2;
                do {
                    i9--;
                    this.dirProps[i9] = 18;
                } while (i9 > i6);
            } else {
                b4 = b2;
            }
            if (z2 && IsBidiControlChar(iCharAt)) {
                i4++;
            }
            if (customizedClass == 0) {
                if (b == 1) {
                    this.paras_level[this.paraCount - 1] = 0;
                    b = 0;
                } else if (b == 2) {
                    if (i5 <= 125) {
                        this.flags |= DirPropFlag((byte) 20);
                    }
                    b = 3;
                }
                i6 = charCount;
                b6 = b7;
                zIsDefaultLevel = z3;
                i3 = 0;
                b2 = 0;
            } else if (customizedClass == 1 || customizedClass == 13) {
                if (b == 1) {
                    this.paras_level[this.paraCount - 1] = 1;
                    b = 0;
                } else if (b == 2) {
                    if (i5 <= 125) {
                        this.dirProps[iArr[i5]] = 21;
                        this.flags = DirPropFlag((byte) 21) | this.flags;
                    }
                    b = 3;
                }
                if (customizedClass == 13) {
                    this.lastArabicPos = i8;
                }
                i6 = charCount;
                b6 = b7;
                zIsDefaultLevel = z3;
                i3 = 0;
                b2 = 1;
            } else if (customizedClass < 19 || customizedClass > 21) {
                if (customizedClass == 22) {
                    if (b == 2) {
                        i = 125;
                        if (i5 <= 125) {
                            this.flags |= DirPropFlag((byte) 20);
                        }
                    } else {
                        i = 125;
                    }
                    if (i5 >= 0) {
                        if (i5 <= i) {
                            b = bArr[i5];
                        }
                        i5--;
                        i6 = charCount;
                        b6 = b7;
                        zIsDefaultLevel = z3;
                        b2 = b4;
                        i3 = 0;
                    }
                } else if (customizedClass == 7) {
                    if (charCount < this.originalLength && iCharAt == 13) {
                        if (this.text[charCount] == '\n') {
                            b2 = b4;
                        }
                        i6 = charCount;
                        b6 = b7;
                        zIsDefaultLevel = z3;
                        i3 = 0;
                    }
                    int[] iArr2 = this.paras_limit;
                    int i10 = this.paraCount;
                    iArr2[i10 - 1] = charCount;
                    b2 = b4;
                    if (z && b2 == 1) {
                        this.paras_level[i10 - 1] = 1;
                    }
                    if ((this.reorderingOptions & 4) != 0) {
                        this.length = charCount;
                        this.controlCount = i4;
                    }
                    if (charCount < this.originalLength) {
                        this.paraCount++;
                        checkParaCount();
                        if (z3) {
                            this.paras_level[this.paraCount - 1] = b7;
                            b2 = b7;
                            b = 1;
                        } else {
                            this.paras_level[this.paraCount - 1] = this.paraLevel;
                            b = 0;
                        }
                        i6 = charCount;
                        b6 = b7;
                        zIsDefaultLevel = z3;
                        i3 = 0;
                        i5 = -1;
                    } else {
                        i6 = charCount;
                        b6 = b7;
                        zIsDefaultLevel = z3;
                        i3 = 0;
                    }
                }
                b2 = b4;
                i6 = charCount;
                b6 = b7;
                zIsDefaultLevel = z3;
                i3 = 0;
            } else {
                i5++;
                if (i5 <= 125) {
                    iArr[i5] = i8;
                    bArr[i5] = b;
                }
                if (customizedClass == 19) {
                    this.dirProps[i8] = 20;
                    i6 = charCount;
                    b6 = b7;
                    zIsDefaultLevel = z3;
                    b2 = b4;
                    i3 = 0;
                    b = 2;
                } else {
                    i6 = charCount;
                    b6 = b7;
                    zIsDefaultLevel = z3;
                    b2 = b4;
                    i3 = 0;
                    b = 3;
                }
            }
        }
        boolean z4 = zIsDefaultLevel;
        if (i5 > 125) {
            b = 2;
            i5 = 125;
        }
        while (true) {
            if (i5 < 0) {
                break;
            }
            if (b == 2) {
                this.flags |= DirPropFlag((byte) 20);
                break;
            } else {
                b = bArr[i5];
                i5--;
            }
        }
        if ((this.reorderingOptions & 4) != 0) {
            if (this.length < this.originalLength) {
                b3 = 1;
                this.paraCount--;
            } else {
                b3 = 1;
            }
        } else {
            b3 = 1;
            this.paras_limit[this.paraCount - 1] = this.originalLength;
            this.controlCount = i4;
        }
        if (z && b2 == b3) {
            this.paras_level[this.paraCount - b3] = b3;
        }
        if (z4) {
            this.paraLevel = this.paras_level[0];
        }
        for (int i11 = 0; i11 < this.paraCount; i11++) {
            this.flags |= DirPropFlagLR(this.paras_level[i11]);
        }
        if (!this.orderParagraphsLTR || (this.flags & DirPropFlag((byte) 7)) == 0) {
            return;
        }
        this.flags |= DirPropFlag((byte) 0);
    }

    byte GetParaLevelAt(int i) {
        if (this.defaultParaLevel == 0 || i < this.paras_limit[0]) {
            return this.paraLevel;
        }
        int i2 = 1;
        while (i2 < this.paraCount && i >= this.paras_limit[i2]) {
            i2++;
        }
        int i3 = this.paraCount;
        if (i2 >= i3) {
            i2 = i3 - 1;
        }
        return this.paras_level[i2];
    }

    private void bracketInit(BracketData bracketData) {
        bracketData.isoRunLast = 0;
        bracketData.isoRuns[0] = new IsoRun();
        bracketData.isoRuns[0].start = (short) 0;
        bracketData.isoRuns[0].limit = (short) 0;
        bracketData.isoRuns[0].level = GetParaLevelAt(0);
        IsoRun isoRun = bracketData.isoRuns[0];
        IsoRun isoRun2 = bracketData.isoRuns[0];
        IsoRun isoRun3 = bracketData.isoRuns[0];
        byte bGetParaLevelAt = (byte) (GetParaLevelAt(0) & 1);
        isoRun3.contextDir = bGetParaLevelAt;
        isoRun2.lastBase = bGetParaLevelAt;
        isoRun.lastStrong = bGetParaLevelAt;
        bracketData.isoRuns[0].contextPos = 0;
        bracketData.openings = new Opening[20];
        int i = this.reorderingMode;
        bracketData.isNumbersSpecial = i == 1 || i == 6;
    }

    private void bracketProcessB(BracketData bracketData, byte b) {
        bracketData.isoRunLast = 0;
        bracketData.isoRuns[0].limit = (short) 0;
        bracketData.isoRuns[0].level = b;
        IsoRun isoRun = bracketData.isoRuns[0];
        IsoRun isoRun2 = bracketData.isoRuns[0];
        byte b2 = (byte) (b & 1);
        bracketData.isoRuns[0].contextDir = b2;
        isoRun2.lastBase = b2;
        isoRun.lastStrong = b2;
        bracketData.isoRuns[0].contextPos = 0;
    }

    private void bracketProcessBoundary(BracketData bracketData, int i, byte b, byte b2) {
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        if ((DirPropFlag(this.dirProps[i]) & MASK_ISO) != 0) {
            return;
        }
        if (NoOverride(b2) > NoOverride(b)) {
            b = b2;
        }
        isoRun.limit = isoRun.start;
        isoRun.level = b2;
        byte b3 = (byte) (b & 1);
        isoRun.contextDir = b3;
        isoRun.lastBase = b3;
        isoRun.lastStrong = b3;
        isoRun.contextPos = i;
    }

    private void bracketProcessLRI_RLI(BracketData bracketData, byte b) {
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        isoRun.lastBase = (byte) 10;
        short s = isoRun.limit;
        bracketData.isoRunLast++;
        IsoRun isoRun2 = bracketData.isoRuns[bracketData.isoRunLast];
        if (isoRun2 == null) {
            IsoRun[] isoRunArr = bracketData.isoRuns;
            int i = bracketData.isoRunLast;
            IsoRun isoRun3 = new IsoRun();
            isoRunArr[i] = isoRun3;
            isoRun2 = isoRun3;
        }
        isoRun2.limit = s;
        isoRun2.start = s;
        isoRun2.level = b;
        byte b2 = (byte) (b & 1);
        isoRun2.contextDir = b2;
        isoRun2.lastBase = b2;
        isoRun2.lastStrong = b2;
        isoRun2.contextPos = 0;
    }

    private void bracketProcessPDI(BracketData bracketData) {
        bracketData.isoRunLast--;
        bracketData.isoRuns[bracketData.isoRunLast].lastBase = (byte) 10;
    }

    private void bracketAddOpening(BracketData bracketData, char c, int i) {
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        if (isoRun.limit >= bracketData.openings.length) {
            Opening[] openingArr = bracketData.openings;
            try {
                int length = bracketData.openings.length;
                bracketData.openings = new Opening[length * 2];
                System.arraycopy(openingArr, 0, bracketData.openings, 0, length);
            } catch (Exception unused) {
                throw new OutOfMemoryError("Failed to allocate memory for openings");
            }
        }
        Opening opening = bracketData.openings[isoRun.limit];
        if (opening == null) {
            Opening[] openingArr2 = bracketData.openings;
            short s = isoRun.limit;
            Opening opening2 = new Opening();
            openingArr2[s] = opening2;
            opening = opening2;
        }
        opening.position = i;
        opening.match = c;
        opening.contextDir = isoRun.contextDir;
        opening.contextPos = isoRun.contextPos;
        opening.flags = (short) 0;
        isoRun.limit = (short) (isoRun.limit + 1);
    }

    private void fixN0c(BracketData bracketData, int i, int i2, byte b) {
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        while (true) {
            i++;
            if (i >= isoRun.limit) {
                return;
            }
            Opening opening = bracketData.openings[i];
            if (opening.match < 0) {
                if (i2 < opening.contextPos) {
                    return;
                }
                if (i2 >= opening.position) {
                    continue;
                } else {
                    if (b == opening.contextDir) {
                        return;
                    }
                    int i3 = opening.position;
                    this.dirProps[i3] = b;
                    int i4 = -opening.match;
                    this.dirProps[i4] = b;
                    opening.match = 0;
                    fixN0c(bracketData, i, i3, b);
                    fixN0c(bracketData, i, i4, b);
                }
            }
        }
    }

    private byte bracketProcessClosing(BracketData bracketData, int i, int i2) {
        boolean z;
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        Opening opening = bracketData.openings[i];
        byte b = (byte) (isoRun.level & 1);
        if ((b == 0 && (opening.flags & FOUND_L) > 0) || (b == 1 && (opening.flags & FOUND_R) > 0)) {
            z = true;
        } else if ((opening.flags & (FOUND_L | FOUND_R)) != 0) {
            z = i == isoRun.start;
            if (b != opening.contextDir) {
                b = opening.contextDir;
            }
        } else {
            isoRun.limit = (short) i;
            return (byte) 10;
        }
        this.dirProps[opening.position] = b;
        this.dirProps[i2] = b;
        fixN0c(bracketData, i, opening.position, b);
        if (z) {
            isoRun.limit = (short) i;
            while (isoRun.limit > isoRun.start && bracketData.openings[isoRun.limit - 1].position == opening.position) {
                isoRun.limit = (short) (isoRun.limit - 1);
            }
        } else {
            opening.match = -i2;
            for (int i3 = i - 1; i3 >= isoRun.start && bracketData.openings[i3].position == opening.position; i3--) {
                bracketData.openings[i3].match = 0;
            }
            for (int i4 = i + 1; i4 < isoRun.limit; i4++) {
                Opening opening2 = bracketData.openings[i4];
                if (opening2.position >= i2) {
                    break;
                }
                if (opening2.match > 0) {
                    opening2.match = 0;
                }
            }
        }
        return b;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x006f, code lost:
    
        if (r5 == 0) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0071, code lost:
    
        r6 = (char) com.ibm.icu.lang.UCharacter.getBidiPairedBracket(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0077, code lost:
    
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0078, code lost:
    
        if (r6 == r5) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0080, code lost:
    
        if (com.ibm.icu.lang.UCharacter.getIntPropertyValue(r5, 4117) != 1) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0086, code lost:
    
        if (r6 != 9002) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0088, code lost:
    
        bracketAddOpening(r9, 12297, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x008c, code lost:
    
        if (r6 != 12297) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x008e, code lost:
    
        bracketAddOpening(r9, 9002, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0091, code lost:
    
        bracketAddOpening(r9, r6, r10);
     */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void bracketProcessChar(com.ibm.icu.text.Bidi.BracketData r9, int r10) {
        /*
            Method dump skipped, instructions count: 315
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.Bidi.bracketProcessChar(com.ibm.icu.text.Bidi$BracketData, int):void");
    }

    private byte directionFromFlags() {
        int i = this.flags;
        if ((MASK_RTL & i) == 0 && ((i & DirPropFlag((byte) 5)) == 0 || (this.flags & MASK_POSSIBLE_N) == 0)) {
            return (byte) 0;
        }
        return (this.flags & MASK_LTR) == 0 ? (byte) 1 : (byte) 2;
    }

    private byte resolveExplicitLevels() {
        byte bNoOverride;
        byte bGetParaLevelAt = GetParaLevelAt(0);
        this.isolateCount = 0;
        byte bDirectionFromFlags = directionFromFlags();
        if (bDirectionFromFlags != 2) {
            return bDirectionFromFlags;
        }
        if (this.reorderingMode > 1) {
            int i = 0;
            while (i < this.paraCount) {
                int i2 = this.paras_limit[i];
                byte b = this.paras_level[i];
                for (int i3 = i == 0 ? 0 : this.paras_limit[i - 1]; i3 < i2; i3++) {
                    this.levels[i3] = b;
                }
                i++;
            }
            return bDirectionFromFlags;
        }
        byte b2 = 10;
        if ((this.flags & (MASK_EXPLICIT | MASK_ISO)) == 0) {
            BracketData bracketData = new BracketData();
            bracketInit(bracketData);
            int i4 = 0;
            while (i4 < this.paraCount) {
                int i5 = this.paras_limit[i4];
                byte b3 = this.paras_level[i4];
                for (int i6 = i4 == 0 ? 0 : this.paras_limit[i4 - 1]; i6 < i5; i6++) {
                    this.levels[i6] = b3;
                    byte b4 = this.dirProps[i6];
                    if (b4 != 18) {
                        if (b4 == 7) {
                            int i7 = i6 + 1;
                            if (i7 < this.length) {
                                char[] cArr = this.text;
                                if (cArr[i6] != '\r' || cArr[i7] != '\n') {
                                    bracketProcessB(bracketData, b3);
                                }
                            }
                        } else {
                            bracketProcessChar(bracketData, i6);
                        }
                    }
                }
                i4++;
            }
            return bDirectionFromFlags;
        }
        short[] sArr = new short[127];
        BracketData bracketData2 = new BracketData();
        bracketInit(bracketData2);
        sArr[0] = bGetParaLevelAt;
        this.flags = 0;
        byte b5 = bGetParaLevelAt;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        while (i8 < this.length) {
            byte b6 = this.dirProps[i8];
            switch (b6) {
                case 7:
                    this.flags |= DirPropFlag((byte) 7);
                    this.levels[i8] = GetParaLevelAt(i8);
                    int i14 = i8 + 1;
                    if (i14 < this.length) {
                        char[] cArr2 = this.text;
                        if (cArr2[i8] != '\r' || cArr2[i14] != '\n') {
                            byte bGetParaLevelAt2 = GetParaLevelAt(i14);
                            sArr[0] = bGetParaLevelAt2;
                            bracketProcessB(bracketData2, bGetParaLevelAt2);
                            bGetParaLevelAt = bGetParaLevelAt2;
                            b5 = bGetParaLevelAt;
                            i9 = 0;
                            i11 = 0;
                            i12 = 0;
                            i13 = 0;
                            break;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                    break;
                case 8:
                case 9:
                case 10:
                case 13:
                case 17:
                case 19:
                default:
                    if (NoOverride(bGetParaLevelAt) != NoOverride(b5)) {
                        bracketProcessBoundary(bracketData2, i10, b5, bGetParaLevelAt);
                        int i15 = this.flags | DirPropFlagMultiRuns;
                        this.flags = i15;
                        if ((bGetParaLevelAt & (-128)) != 0) {
                            this.flags = i15 | DirPropFlagO(bGetParaLevelAt);
                        } else {
                            this.flags = i15 | DirPropFlagE(bGetParaLevelAt);
                        }
                    }
                    this.levels[i8] = bGetParaLevelAt;
                    bracketProcessChar(bracketData2, i8);
                    this.flags |= DirPropFlag(this.dirProps[i8]);
                    b5 = bGetParaLevelAt;
                    break;
                case 11:
                case 12:
                case 14:
                case 15:
                    this.flags |= DirPropFlag((byte) 18);
                    this.levels[i8] = b5;
                    bNoOverride = (byte) ((b6 == 11 || b6 == 12) ? (bGetParaLevelAt + 2) & 126 : (NoOverride(bGetParaLevelAt) + 1) | 1);
                    if (bNoOverride > 125 || i9 != 0 || i11 != 0) {
                        if (i9 == 0) {
                            i11++;
                        }
                        break;
                    } else {
                        if (b6 == 12 || b6 == 15) {
                            bNoOverride = (byte) (bNoOverride | (-128));
                        }
                        i12++;
                        sArr[i12] = bNoOverride;
                        bGetParaLevelAt = bNoOverride;
                        i10 = i8;
                    }
                    break;
                case 16:
                    this.flags |= DirPropFlag((byte) 18);
                    this.levels[i8] = b5;
                    if (i9 <= 0) {
                        if (i11 > 0) {
                            i11--;
                        } else if (i12 > 0 && sArr[i12] < 256) {
                            i12--;
                            bGetParaLevelAt = (byte) sArr[i12];
                            i10 = i8;
                        }
                    }
                    break;
                case 18:
                    this.levels[i8] = b5;
                    this.flags |= DirPropFlag((byte) 18);
                    break;
                case 20:
                case 21:
                    this.flags |= DirPropFlag(b2) | DirPropFlagLR(bGetParaLevelAt);
                    this.levels[i8] = NoOverride(bGetParaLevelAt);
                    if (NoOverride(bGetParaLevelAt) != NoOverride(b5)) {
                        bracketProcessBoundary(bracketData2, i10, b5, bGetParaLevelAt);
                        this.flags |= DirPropFlagMultiRuns;
                    }
                    bNoOverride = (byte) (b6 == 20 ? (bGetParaLevelAt + 2) & 126 : (NoOverride(bGetParaLevelAt) + 1) | 1);
                    if (bNoOverride <= 125 && i9 == 0 && i11 == 0) {
                        this.flags = DirPropFlag(b6) | this.flags;
                        int i16 = i13 + 1;
                        if (i16 > this.isolateCount) {
                            this.isolateCount = i16;
                        }
                        i12++;
                        sArr[i12] = (short) (bNoOverride + 256);
                        bracketProcessLRI_RLI(bracketData2, bNoOverride);
                        i13 = i16;
                        b5 = bGetParaLevelAt;
                        bGetParaLevelAt = bNoOverride;
                        i10 = i8;
                    } else {
                        this.dirProps[i8] = 9;
                        i9++;
                        b5 = bGetParaLevelAt;
                        break;
                    }
                case 22:
                    if (NoOverride(bGetParaLevelAt) != NoOverride(b5)) {
                        bracketProcessBoundary(bracketData2, i10, b5, bGetParaLevelAt);
                        this.flags |= DirPropFlagMultiRuns;
                    }
                    if (i9 > 0) {
                        i9--;
                        this.dirProps[i8] = 9;
                    } else if (i13 > 0) {
                        this.flags |= DirPropFlag((byte) 22);
                        while (sArr[i12] < 256) {
                            i12--;
                        }
                        i12--;
                        i13--;
                        bracketProcessPDI(bracketData2);
                        i10 = i8;
                        i11 = 0;
                    } else {
                        this.dirProps[i8] = 9;
                    }
                    byte b7 = (byte) (sArr[i12] & (-257));
                    this.flags |= DirPropFlag(b2) | DirPropFlagLR(b7);
                    this.levels[i8] = NoOverride(b7);
                    bGetParaLevelAt = b7;
                    b5 = bGetParaLevelAt;
                    break;
            }
            i8++;
            b2 = 10;
        }
        int i17 = this.flags;
        if ((MASK_EMBEDDING & i17) != 0) {
            this.flags = i17 | DirPropFlagLR(this.paraLevel);
        }
        if (this.orderParagraphsLTR && (this.flags & DirPropFlag((byte) 7)) != 0) {
            this.flags |= DirPropFlag((byte) 0);
        }
        return directionFromFlags();
    }

    private byte checkExplicitLevels() {
        int i;
        this.flags = 0;
        this.isolateCount = 0;
        int i2 = this.paras_limit[0];
        byte b = this.paraLevel;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < this.length; i5++) {
            byte b2 = this.levels[i5];
            byte b3 = this.dirProps[i5];
            if (b3 == 20 || b3 == 21) {
                i3++;
                if (i3 > this.isolateCount) {
                    this.isolateCount = i3;
                }
            } else if (b3 == 22) {
                i3--;
            } else if (b3 == 7) {
                i3 = 0;
            }
            if (this.defaultParaLevel != 0 && i5 == i2 && (i = i4 + 1) < this.paraCount) {
                i4 = i;
                b = this.paras_level[i];
                i2 = this.paras_limit[i];
            }
            int i6 = b2 & (-128);
            byte b4 = (byte) (b2 & 127);
            if (b4 < b || 125 < b4) {
                if (b4 != 0) {
                    throw new IllegalArgumentException("level " + ((int) b4) + " out of bounds at " + i5);
                }
                if (b3 != 7) {
                    this.levels[i5] = (byte) (b | i6);
                    b4 = b;
                }
            }
            if (i6 != 0) {
                this.flags = DirPropFlagO(b4) | this.flags;
            } else {
                this.flags = DirPropFlagE(b4) | DirPropFlag(b3) | this.flags;
            }
        }
        int i7 = this.flags;
        if ((MASK_EMBEDDING & i7) != 0) {
            this.flags = i7 | DirPropFlagLR(this.paraLevel);
        }
        return directionFromFlags();
    }

    private static class ImpTabPair {
        short[][] impact;
        byte[][][] imptab;

        ImpTabPair(byte[][] bArr, byte[][] bArr2, short[] sArr, short[] sArr2) {
            this.imptab = new byte[][][]{bArr, bArr2};
            this.impact = new short[][]{sArr, sArr2};
        }
    }

    private static class LevState {
        short[] impAct;
        byte[][] impTab;
        int lastStrongRTL;
        byte runLevel;
        int runStart;
        int startL2EN;
        int startON;
        short state;

        private LevState() {
        }
    }

    private void addPoint(int i, int i2) {
        Point point = new Point();
        int length = this.insertPoints.points.length;
        if (length == 0) {
            this.insertPoints.points = new Point[10];
            length = 10;
        }
        if (this.insertPoints.size >= length) {
            Point[] pointArr = this.insertPoints.points;
            this.insertPoints.points = new Point[length * 2];
            System.arraycopy(pointArr, 0, this.insertPoints.points, 0, length);
        }
        point.pos = i;
        point.flag = i2;
        this.insertPoints.points[this.insertPoints.size] = point;
        this.insertPoints.size++;
    }

    private void setLevelsOutsideIsolates(int i, int i2, byte b) {
        int i3 = 0;
        while (i < i2) {
            byte b2 = this.dirProps[i];
            if (b2 == 22) {
                i3--;
            }
            if (i3 == 0) {
                this.levels[i] = b;
            }
            if (b2 == 20 || b2 == 21) {
                i3++;
            }
            i++;
        }
    }

    private void processPropertySeq(LevState levState, short s, int i, int i2) {
        int i3;
        byte[][] bArr = levState.impTab;
        short[] sArr = levState.impAct;
        short s2 = levState.state;
        byte b = bArr[s2][s];
        levState.state = GetState(b);
        short s3 = sArr[GetAction(b)];
        byte b2 = bArr[levState.state][7];
        if (s3 != 0) {
            switch (s3) {
                case 1:
                    levState.startON = i;
                    i3 = i;
                    break;
                case 2:
                    i3 = levState.startON;
                    break;
                case 3:
                    setLevelsOutsideIsolates(levState.startON, i, (byte) (levState.runLevel + 1));
                    i3 = i;
                    break;
                case 4:
                    setLevelsOutsideIsolates(levState.startON, i, (byte) (levState.runLevel + 2));
                    i3 = i;
                    break;
                case 5:
                    if (levState.startL2EN >= 0) {
                        addPoint(levState.startL2EN, 1);
                    }
                    levState.startL2EN = -1;
                    if (this.insertPoints.points.length == 0 || this.insertPoints.size <= this.insertPoints.confirmed) {
                        levState.lastStrongRTL = -1;
                        i3 = ((bArr[s2][7] & 1) == 0 || levState.startON <= 0) ? i : levState.startON;
                        if (s == 5) {
                            addPoint(i, 1);
                            InsertPoints insertPoints = this.insertPoints;
                            insertPoints.confirmed = insertPoints.size;
                            break;
                        }
                    } else {
                        for (int i4 = levState.lastStrongRTL + 1; i4 < i; i4++) {
                            byte[] bArr2 = this.levels;
                            bArr2[i4] = (byte) ((bArr2[i4] - 2) & (-2));
                        }
                        InsertPoints insertPoints2 = this.insertPoints;
                        insertPoints2.confirmed = insertPoints2.size;
                        levState.lastStrongRTL = -1;
                        if (s == 5) {
                            addPoint(i, 1);
                            InsertPoints insertPoints3 = this.insertPoints;
                            insertPoints3.confirmed = insertPoints3.size;
                        }
                        i3 = i;
                        break;
                    }
                    break;
                case 6:
                    if (this.insertPoints.points.length > 0) {
                        InsertPoints insertPoints4 = this.insertPoints;
                        insertPoints4.size = insertPoints4.confirmed;
                    }
                    levState.startON = -1;
                    levState.startL2EN = -1;
                    levState.lastStrongRTL = i2 - 1;
                    i3 = i;
                    break;
                case 7:
                    if (s == 3 && this.dirProps[i] == 5 && this.reorderingMode != 6) {
                        if (levState.startL2EN == -1) {
                            levState.lastStrongRTL = i2 - 1;
                        } else {
                            if (levState.startL2EN >= 0) {
                                addPoint(levState.startL2EN, 1);
                                levState.startL2EN = -2;
                            }
                            addPoint(i, 1);
                        }
                    } else if (levState.startL2EN == -1) {
                        levState.startL2EN = i;
                    }
                    i3 = i;
                    break;
                case 8:
                    levState.lastStrongRTL = i2 - 1;
                    levState.startON = -1;
                    i3 = i;
                    break;
                case 9:
                    int i5 = i - 1;
                    while (i5 >= 0 && (this.levels[i5] & 1) == 0) {
                        i5--;
                    }
                    if (i5 >= 0) {
                        addPoint(i5, 4);
                        InsertPoints insertPoints5 = this.insertPoints;
                        insertPoints5.confirmed = insertPoints5.size;
                    }
                    levState.startON = i;
                    i3 = i;
                    break;
                case 10:
                    addPoint(i, 1);
                    addPoint(i, 2);
                    i3 = i;
                    break;
                case 11:
                    InsertPoints insertPoints6 = this.insertPoints;
                    insertPoints6.size = insertPoints6.confirmed;
                    if (s == 5) {
                        addPoint(i, 4);
                        InsertPoints insertPoints7 = this.insertPoints;
                        insertPoints7.confirmed = insertPoints7.size;
                    }
                    i3 = i;
                    break;
                case 12:
                    byte b3 = (byte) (levState.runLevel + b2);
                    for (int i6 = levState.startON; i6 < i; i6++) {
                        byte[] bArr3 = this.levels;
                        if (bArr3[i6] < b3) {
                            bArr3[i6] = b3;
                        }
                    }
                    InsertPoints insertPoints8 = this.insertPoints;
                    insertPoints8.confirmed = insertPoints8.size;
                    levState.startON = i;
                    i3 = i;
                    break;
                case 13:
                    byte b4 = levState.runLevel;
                    int i7 = i - 1;
                    while (i7 >= levState.startON) {
                        int i8 = b4 + 3;
                        if (this.levels[i7] == i8) {
                            while (true) {
                                byte[] bArr4 = this.levels;
                                if (bArr4[i7] == i8) {
                                    bArr4[i7] = (byte) (bArr4[i7] - 2);
                                    i7--;
                                } else {
                                    while (this.levels[i7] == b4) {
                                        i7--;
                                    }
                                }
                            }
                        }
                        byte[] bArr5 = this.levels;
                        if (bArr5[i7] == b4 + 2) {
                            bArr5[i7] = b4;
                        } else {
                            bArr5[i7] = (byte) (b4 + 1);
                        }
                        i7--;
                    }
                    i3 = i;
                    break;
                case 14:
                    byte b5 = (byte) (levState.runLevel + 1);
                    for (int i9 = i - 1; i9 >= levState.startON; i9--) {
                        byte[] bArr6 = this.levels;
                        if (bArr6[i9] > b5) {
                            bArr6[i9] = (byte) (bArr6[i9] - 2);
                        }
                    }
                    i3 = i;
                    break;
                default:
                    throw new IllegalStateException("Internal ICU error in processPropertySeq");
            }
        } else {
            i3 = i;
        }
        if (b2 != 0 || i3 < i) {
            byte b6 = (byte) (levState.runLevel + b2);
            if (i3 < levState.runStart) {
                setLevelsOutsideIsolates(i3, i2, b6);
                return;
            }
            while (i3 < i2) {
                this.levels[i3] = b6;
                i3++;
            }
        }
    }

    private byte lastL_R_AL() {
        int length = this.prologue.length();
        while (length > 0) {
            int iCodePointBefore = this.prologue.codePointBefore(length);
            length -= Character.charCount(iCodePointBefore);
            byte customizedClass = (byte) getCustomizedClass(iCodePointBefore);
            if (customizedClass == 0) {
                return (byte) 0;
            }
            if (customizedClass == 1 || customizedClass == 13) {
                return (byte) 1;
            }
            if (customizedClass == 7) {
                return (byte) 4;
            }
        }
        return (byte) 4;
    }

    private byte firstL_R_AL_EN_AN() {
        int iCharCount = 0;
        while (iCharCount < this.epilogue.length()) {
            int iCodePointAt = this.epilogue.codePointAt(iCharCount);
            iCharCount += Character.charCount(iCodePointAt);
            byte customizedClass = (byte) getCustomizedClass(iCodePointAt);
            if (customizedClass == 0) {
                return (byte) 0;
            }
            if (customizedClass == 1 || customizedClass == 13) {
                return (byte) 1;
            }
            if (customizedClass == 2) {
                return (byte) 2;
            }
            if (customizedClass == 5) {
                return (byte) 3;
            }
        }
        return (byte) 4;
    }

    private void resolveImplicitLevels(int i, int i2, short s, short s2) {
        int i3;
        short s3;
        byte bFirstL_R_AL_EN_AN;
        short s4;
        byte bLastL_R_AL;
        int i4;
        LevState levState = new LevState();
        boolean z = i < this.lastArabicPos && (GetParaLevelAt(i) & 1) > 0 && ((i4 = this.reorderingMode) == 5 || i4 == 6);
        levState.startL2EN = -1;
        levState.lastStrongRTL = -1;
        levState.runStart = i;
        levState.runLevel = this.levels[i];
        levState.impTab = this.impTabPair.imptab[levState.runLevel & 1];
        levState.impAct = this.impTabPair.impact[levState.runLevel & 1];
        short s5 = (i != 0 || this.prologue == null || (bLastL_R_AL = lastL_R_AL()) == 4) ? s : bLastL_R_AL;
        if (this.dirProps[i] == 22) {
            levState.startON = this.isolates[this.isolateCount].startON;
            i3 = this.isolates[this.isolateCount].start1;
            s3 = this.isolates[this.isolateCount].stateImp;
            levState.state = this.isolates[this.isolateCount].state;
            this.isolateCount--;
        } else {
            levState.startON = -1;
            short s6 = this.dirProps[i] == 17 ? (short) (s5 + 1) : (short) 0;
            levState.state = (short) 0;
            processPropertySeq(levState, s5, i, i);
            i3 = i;
            s3 = s6;
        }
        int i5 = i;
        int i6 = i5;
        int i7 = -1;
        short s7 = 1;
        while (i5 <= i2) {
            if (i5 >= i2) {
                int i8 = i2 - 1;
                while (i8 > i && (DirPropFlag(this.dirProps[i8]) & MASK_BN_EXPLICIT) != 0) {
                    i8--;
                }
                byte b = this.dirProps[i8];
                if (b == 20 || b == 21) {
                    break;
                } else {
                    s4 = s2;
                }
            } else {
                byte b2 = this.dirProps[i5];
                if (b2 == 7) {
                    this.isolateCount = -1;
                }
                if (z) {
                    if (b2 == 13) {
                        b2 = 1;
                    } else if (b2 == 2) {
                        if (i7 <= i5) {
                            i7 = i5 + 1;
                            while (i7 < i2) {
                                byte b3 = this.dirProps[i7];
                                if (b3 == 0 || b3 == 1 || b3 == 13) {
                                    s7 = b3;
                                    break;
                                }
                                i7++;
                            }
                            i7 = i2;
                            s7 = 1;
                        }
                        if (s7 == 13) {
                            b2 = 5;
                        }
                    }
                }
                s4 = groupProp[b2];
            }
            short s8 = impTabProps[s3][s4];
            short sGetStateProps = GetStateProps(s8);
            short sGetActionProps = GetActionProps(s8);
            if (i5 == i2 && sGetActionProps == 0) {
                sGetActionProps = 1;
            }
            if (sGetActionProps != 0) {
                short s9 = impTabProps[s3][15];
                if (sGetActionProps != 1) {
                    if (sGetActionProps != 2) {
                        if (sGetActionProps == 3) {
                            processPropertySeq(levState, s9, i3, i6);
                            processPropertySeq(levState, (short) 4, i6, i5);
                        } else if (sGetActionProps == 4) {
                            processPropertySeq(levState, s9, i3, i6);
                            i3 = i6;
                        } else {
                            throw new IllegalStateException("Internal ICU error in resolveImplicitLevels");
                        }
                    }
                    i6 = i5;
                } else {
                    processPropertySeq(levState, s9, i3, i5);
                }
                i3 = i5;
            }
            i5++;
            s3 = sGetStateProps;
        }
        short s10 = (i2 != this.length || this.epilogue == null || (bFirstL_R_AL_EN_AN = firstL_R_AL_EN_AN()) == 4) ? s2 : bFirstL_R_AL_EN_AN;
        int i9 = i2 - 1;
        while (i9 > i && (DirPropFlag(this.dirProps[i9]) & MASK_BN_EXPLICIT) != 0) {
            i9--;
        }
        byte b4 = this.dirProps[i9];
        if ((b4 == 20 || b4 == 21) && i2 < this.length) {
            int i10 = this.isolateCount + 1;
            this.isolateCount = i10;
            Isolate[] isolateArr = this.isolates;
            if (isolateArr[i10] == null) {
                isolateArr[i10] = new Isolate();
            }
            this.isolates[this.isolateCount].stateImp = s3;
            this.isolates[this.isolateCount].state = levState.state;
            this.isolates[this.isolateCount].start1 = i3;
            this.isolates[this.isolateCount].startON = levState.startON;
            return;
        }
        processPropertySeq(levState, s10, i2, i2);
    }

    private void adjustWSLevels() {
        if ((this.flags & MASK_WS) != 0) {
            int i = this.trailingWSStart;
            while (i > 0) {
                while (i > 0) {
                    i--;
                    int iDirPropFlag = DirPropFlag(this.dirProps[i]);
                    if ((MASK_WS & iDirPropFlag) == 0) {
                        break;
                    }
                    if (this.orderParagraphsLTR && (DirPropFlag((byte) 7) & iDirPropFlag) != 0) {
                        this.levels[i] = 0;
                    } else {
                        this.levels[i] = GetParaLevelAt(i);
                    }
                }
                while (true) {
                    if (i > 0) {
                        i--;
                        int iDirPropFlag2 = DirPropFlag(this.dirProps[i]);
                        if ((MASK_BN_EXPLICIT & iDirPropFlag2) != 0) {
                            byte[] bArr = this.levels;
                            bArr[i] = bArr[i + 1];
                        } else if (this.orderParagraphsLTR && (DirPropFlag((byte) 7) & iDirPropFlag2) != 0) {
                            this.levels[i] = 0;
                            break;
                        } else if ((iDirPropFlag2 & MASK_B_S) != 0) {
                            this.levels[i] = GetParaLevelAt(i);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void setContext(String str, String str2) {
        if (str == null || str.length() <= 0) {
            str = null;
        }
        this.prologue = str;
        if (str2 == null || str2.length() <= 0) {
            str2 = null;
        }
        this.epilogue = str2;
    }

    private void setParaSuccess() {
        this.prologue = null;
        this.epilogue = null;
        this.paraBidi = this;
    }

    void setParaRunsOnly(char[] cArr, byte b) {
        int i;
        int i2;
        int i3;
        this.reorderingMode = 0;
        int length = cArr.length;
        if (length == 0) {
            setPara(cArr, b, (byte[]) null);
            this.reorderingMode = 3;
            return;
        }
        int i4 = this.reorderingOptions;
        int i5 = 2;
        if ((i4 & 1) > 0) {
            int i6 = i4 & (-2);
            this.reorderingOptions = i6;
            this.reorderingOptions = i6 | 2;
        }
        byte b2 = (byte) (b & 1);
        setPara(cArr, b2, (byte[]) null);
        byte[] bArr = new byte[this.length];
        System.arraycopy(getLevels(), 0, bArr, 0, this.length);
        int i7 = this.trailingWSStart;
        String strWriteReordered = writeReordered(2);
        int[] visualMap = getVisualMap();
        this.reorderingOptions = i4;
        int i8 = this.length;
        byte b3 = this.direction;
        this.reorderingMode = 5;
        setPara(strWriteReordered, (byte) (b2 ^ 1), (byte[]) null);
        BidiLine.getRuns(this);
        int i9 = this.runCount;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (i10 < i9) {
            int i13 = this.runs[i10].limit - i12;
            if (i13 >= i5) {
                int i14 = this.runs[i10].start;
                int i15 = i14 + 1;
                while (i15 < i14 + i13) {
                    int i16 = visualMap[i15];
                    int i17 = visualMap[i15 - 1];
                    int i18 = i14;
                    int i19 = i7;
                    if (Bidi_Abs(i16 - i17) != 1 || bArr[i16] != bArr[i17]) {
                        i11++;
                    }
                    i15++;
                    i14 = i18;
                    i7 = i19;
                }
            }
            i10++;
            i12 += i13;
            i7 = i7;
            i5 = 2;
        }
        int i20 = i7;
        if (i11 > 0) {
            getRunsMemory(i9 + i11);
            int i21 = this.runCount;
            if (i21 == 1) {
                this.runsMemory[0] = this.runs[0];
            } else {
                System.arraycopy(this.runs, 0, this.runsMemory, 0, i21);
            }
            this.runs = this.runsMemory;
            this.runCount += i11;
            for (int i22 = i9; i22 < this.runCount; i22++) {
                BidiRun[] bidiRunArr = this.runs;
                if (bidiRunArr[i22] == null) {
                    bidiRunArr[i22] = new BidiRun(0, 0, (byte) 0);
                }
            }
        }
        int i23 = i9 - 1;
        while (i23 >= 0) {
            int i24 = i23 + i11;
            int i25 = i23 == 0 ? this.runs[0].limit : this.runs[i23].limit - this.runs[i23 - 1].limit;
            int i26 = this.runs[i23].start;
            int i27 = this.runs[i23].level & 1;
            if (i25 < 2) {
                if (i11 > 0) {
                    BidiRun[] bidiRunArr2 = this.runs;
                    bidiRunArr2[i24].copyFrom(bidiRunArr2[i23]);
                }
                int i28 = visualMap[i26];
                this.runs[i24].start = i28;
                this.runs[i24].level = (byte) (bArr[i28] ^ i27);
            } else {
                if (i27 > 0) {
                    i = 1;
                    i3 = (i25 + i26) - 1;
                    i2 = 1;
                } else {
                    i = 1;
                    i2 = -1;
                    i26 = (i25 + i26) - 1;
                    i3 = i26;
                }
                int i29 = i11;
                int i30 = i26;
                while (i26 != i3) {
                    int i31 = visualMap[i26];
                    int i32 = i26 + i2;
                    int i33 = visualMap[i32];
                    int i34 = i2;
                    if (Bidi_Abs(i31 - i33) != i || bArr[i31] != bArr[i33]) {
                        int iBidi_Min = Bidi_Min(visualMap[i30], i31);
                        this.runs[i24].start = iBidi_Min;
                        this.runs[i24].level = (byte) (bArr[iBidi_Min] ^ i27);
                        BidiRun[] bidiRunArr3 = this.runs;
                        bidiRunArr3[i24].limit = bidiRunArr3[i23].limit;
                        this.runs[i23].limit -= Bidi_Abs(i26 - i30) + 1;
                        int i35 = this.runs[i23].insertRemove & 10;
                        this.runs[i24].insertRemove = i35;
                        BidiRun bidiRun = this.runs[i23];
                        bidiRun.insertRemove = (~i35) & bidiRun.insertRemove;
                        i29--;
                        i24--;
                        i30 = i32;
                    }
                    i26 = i32;
                    i2 = i34;
                    i = 1;
                }
                if (i29 > 0) {
                    BidiRun[] bidiRunArr4 = this.runs;
                    bidiRunArr4[i24].copyFrom(bidiRunArr4[i23]);
                }
                int iBidi_Min2 = Bidi_Min(visualMap[i30], visualMap[i3]);
                this.runs[i24].start = iBidi_Min2;
                this.runs[i24].level = (byte) (bArr[iBidi_Min2] ^ i27);
                i11 = i29;
            }
            i23--;
        }
        this.paraLevel = (byte) (this.paraLevel ^ 1);
        this.text = cArr;
        this.length = i8;
        this.originalLength = length;
        this.direction = b3;
        this.levels = bArr;
        this.trailingWSStart = i20;
        if (this.runCount > 1) {
            this.direction = (byte) 2;
        }
        this.reorderingMode = 3;
    }

    public void setPara(String str, byte b, byte[] bArr) {
        if (str == null) {
            setPara(new char[0], b, bArr);
        } else {
            setPara(str.toCharArray(), b, bArr);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0196 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0185 A[LOOP:5: B:87:0x0185->B:93:0x019e, LOOP_START, PHI: r2
      0x0185: PHI (r2v8 int) = (r2v7 int), (r2v9 int) binds: [B:85:0x017f, B:93:0x019e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x019a A[LOOP:3: B:64:0x012d->B:92:0x019a, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setPara(char[] r8, byte r9, byte[] r10) {
        /*
            Method dump skipped, instructions count: 550
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.Bidi.setPara(char[], byte, byte[]):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4 */
    public void setPara(AttributedCharacterIterator attributedCharacterIterator) {
        byte bByteValue;
        Boolean bool = (Boolean) attributedCharacterIterator.getAttribute(TextAttribute.RUN_DIRECTION);
        byte b = bool == null ? LEVEL_DEFAULT_LTR : !bool.equals(TextAttribute.RUN_DIRECTION_LTR);
        byte[] bArr = null;
        int endIndex = attributedCharacterIterator.getEndIndex() - attributedCharacterIterator.getBeginIndex();
        byte[] bArr2 = new byte[endIndex];
        char[] cArr = new char[endIndex];
        char cFirst = attributedCharacterIterator.first();
        int i = 0;
        while (cFirst != 65535) {
            cArr[i] = cFirst;
            Integer num = (Integer) attributedCharacterIterator.getAttribute(TextAttribute.BIDI_EMBEDDING);
            if (num != null && (bByteValue = num.byteValue()) != 0) {
                if (bByteValue < 0) {
                    bArr2[i] = (byte) ((0 - bByteValue) | (-128));
                } else {
                    bArr2[i] = bByteValue;
                }
                bArr = bArr2;
            }
            cFirst = attributedCharacterIterator.next();
            i++;
        }
        NumericShaper numericShaper = (NumericShaper) attributedCharacterIterator.getAttribute(TextAttribute.NUMERIC_SHAPING);
        if (numericShaper != null) {
            numericShaper.shape(cArr, 0, endIndex);
        }
        setPara(cArr, b, bArr);
    }

    public void orderParagraphsLTR(boolean z) {
        this.orderParagraphsLTR = z;
    }

    public boolean isOrderParagraphsLTR() {
        return this.orderParagraphsLTR;
    }

    public byte getDirection() {
        verifyValidParaOrLine();
        return this.direction;
    }

    public String getTextAsString() {
        verifyValidParaOrLine();
        return new String(this.text);
    }

    public char[] getText() {
        verifyValidParaOrLine();
        return this.text;
    }

    public int getLength() {
        verifyValidParaOrLine();
        return this.originalLength;
    }

    public int getProcessedLength() {
        verifyValidParaOrLine();
        return this.length;
    }

    public int getResultLength() {
        verifyValidParaOrLine();
        return this.resultLength;
    }

    public byte getParaLevel() {
        verifyValidParaOrLine();
        return this.paraLevel;
    }

    public int countParagraphs() {
        verifyValidParaOrLine();
        return this.paraCount;
    }

    public BidiRun getParagraphByIndex(int i) {
        verifyValidParaOrLine();
        verifyRange(i, 0, this.paraCount);
        Bidi bidi = this.paraBidi;
        int i2 = i != 0 ? bidi.paras_limit[i - 1] : 0;
        BidiRun bidiRun = new BidiRun();
        bidiRun.start = i2;
        bidiRun.limit = bidi.paras_limit[i];
        bidiRun.level = GetParaLevelAt(i2);
        return bidiRun;
    }

    public BidiRun getParagraph(int i) {
        verifyValidParaOrLine();
        Bidi bidi = this.paraBidi;
        int i2 = 0;
        verifyRange(i, 0, bidi.length);
        while (i >= bidi.paras_limit[i2]) {
            i2++;
        }
        return getParagraphByIndex(i2);
    }

    public int getParagraphIndex(int i) {
        verifyValidParaOrLine();
        Bidi bidi = this.paraBidi;
        int i2 = 0;
        verifyRange(i, 0, bidi.length);
        while (i >= bidi.paras_limit[i2]) {
            i2++;
        }
        return i2;
    }

    public void setCustomClassifier(BidiClassifier bidiClassifier) {
        this.customClassifier = bidiClassifier;
    }

    public BidiClassifier getCustomClassifier() {
        return this.customClassifier;
    }

    public int getCustomizedClass(int i) {
        int iClassify;
        BidiClassifier bidiClassifier = this.customClassifier;
        if (bidiClassifier == null || (iClassify = bidiClassifier.classify(i)) == 23) {
            iClassify = this.bdp.getClass(i);
        }
        if (iClassify >= 23) {
            return 10;
        }
        return iClassify;
    }

    public Bidi setLine(int i, int i2) {
        verifyValidPara();
        verifyRange(i, 0, i2);
        verifyRange(i2, 0, this.length + 1);
        if (getParagraphIndex(i) != getParagraphIndex(i2 - 1)) {
            throw new IllegalArgumentException();
        }
        return BidiLine.setLine(this, i, i2);
    }

    public byte getLevelAt(int i) {
        verifyValidParaOrLine();
        verifyRange(i, 0, this.length);
        return BidiLine.getLevelAt(this, i);
    }

    public byte[] getLevels() {
        verifyValidParaOrLine();
        return this.length <= 0 ? new byte[0] : BidiLine.getLevels(this);
    }

    public BidiRun getLogicalRun(int i) {
        verifyValidParaOrLine();
        verifyRange(i, 0, this.length);
        return BidiLine.getLogicalRun(this, i);
    }

    public int countRuns() {
        verifyValidParaOrLine();
        BidiLine.getRuns(this);
        return this.runCount;
    }

    public BidiRun getVisualRun(int i) {
        verifyValidParaOrLine();
        BidiLine.getRuns(this);
        verifyRange(i, 0, this.runCount);
        return BidiLine.getVisualRun(this, i);
    }

    public int getVisualIndex(int i) {
        verifyValidParaOrLine();
        verifyRange(i, 0, this.length);
        return BidiLine.getVisualIndex(this, i);
    }

    public int getLogicalIndex(int i) {
        verifyValidParaOrLine();
        verifyRange(i, 0, this.resultLength);
        if (this.insertPoints.size == 0 && this.controlCount == 0) {
            byte b = this.direction;
            if (b == 0) {
                return i;
            }
            if (b == 1) {
                return (this.length - i) - 1;
            }
        }
        BidiLine.getRuns(this);
        return BidiLine.getLogicalIndex(this, i);
    }

    public int[] getLogicalMap() {
        countRuns();
        return this.length <= 0 ? new int[0] : BidiLine.getLogicalMap(this);
    }

    public int[] getVisualMap() {
        countRuns();
        return this.resultLength <= 0 ? new int[0] : BidiLine.getVisualMap(this);
    }

    public static int[] reorderLogical(byte[] bArr) {
        return BidiLine.reorderLogical(bArr);
    }

    public static int[] reorderVisual(byte[] bArr) {
        return BidiLine.reorderVisual(bArr);
    }

    public static int[] invertMap(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        return BidiLine.invertMap(iArr);
    }

    public Bidi(String str, int i) {
        this(str.toCharArray(), 0, null, 0, str.length(), i);
    }

    public Bidi(AttributedCharacterIterator attributedCharacterIterator) {
        this();
        setPara(attributedCharacterIterator);
    }

    public Bidi(char[] cArr, int i, byte[] bArr, int i2, int i3, int i4) {
        byte[] bArr2;
        this();
        byte b = 127;
        if (i4 == 1) {
            b = 1;
        } else if (i4 == 126) {
            b = LEVEL_DEFAULT_LTR;
        } else if (i4 != 127) {
            b = 0;
        }
        if (bArr == null) {
            bArr2 = null;
        } else {
            byte[] bArr3 = new byte[i3];
            for (int i5 = 0; i5 < i3; i5++) {
                byte b2 = bArr[i5 + i2];
                if (b2 < 0) {
                    b2 = (byte) ((-b2) | (-128));
                }
                bArr3[i5] = b2;
            }
            bArr2 = bArr3;
        }
        if (i == 0 && i3 == cArr.length) {
            setPara(cArr, b, bArr2);
            return;
        }
        char[] cArr2 = new char[i3];
        System.arraycopy(cArr, i, cArr2, 0, i3);
        setPara(cArr2, b, bArr2);
    }

    public Bidi createLineBidi(int i, int i2) {
        return setLine(i, i2);
    }

    public boolean isMixed() {
        return (isLeftToRight() || isRightToLeft()) ? false : true;
    }

    public boolean isLeftToRight() {
        return getDirection() == 0 && (this.paraLevel & 1) == 0;
    }

    public boolean isRightToLeft() {
        return getDirection() == 1 && (this.paraLevel & 1) == 1;
    }

    public boolean baseIsLeftToRight() {
        return getParaLevel() == 0;
    }

    public int getBaseLevel() {
        return getParaLevel();
    }

    public int getRunCount() {
        return countRuns();
    }

    void getLogicalToVisualRunsMap() {
        if (this.isGoodLogicalToVisualRunsMap) {
            return;
        }
        int iCountRuns = countRuns();
        int[] iArr = this.logicalToVisualRunsMap;
        if (iArr == null || iArr.length < iCountRuns) {
            this.logicalToVisualRunsMap = new int[iCountRuns];
        }
        long[] jArr = new long[iCountRuns];
        for (int i = 0; i < iCountRuns; i++) {
            jArr[i] = (this.runs[i].start << 32) + i;
        }
        Arrays.sort(jArr);
        for (int i2 = 0; i2 < iCountRuns; i2++) {
            this.logicalToVisualRunsMap[i2] = (int) (jArr[i2] & (-1));
        }
        this.isGoodLogicalToVisualRunsMap = true;
    }

    public int getRunLevel(int i) {
        verifyValidParaOrLine();
        BidiLine.getRuns(this);
        verifyRange(i, 0, this.runCount);
        getLogicalToVisualRunsMap();
        return this.runs[this.logicalToVisualRunsMap[i]].level;
    }

    public int getRunStart(int i) {
        verifyValidParaOrLine();
        BidiLine.getRuns(this);
        verifyRange(i, 0, this.runCount);
        getLogicalToVisualRunsMap();
        return this.runs[this.logicalToVisualRunsMap[i]].start;
    }

    public int getRunLimit(int i) {
        verifyValidParaOrLine();
        BidiLine.getRuns(this);
        verifyRange(i, 0, this.runCount);
        getLogicalToVisualRunsMap();
        int i2 = this.logicalToVisualRunsMap[i];
        BidiRun[] bidiRunArr = this.runs;
        return this.runs[i2].start + (i2 == 0 ? bidiRunArr[i2].limit : bidiRunArr[i2].limit - this.runs[i2 - 1].limit);
    }

    public static boolean requiresBidi(char[] cArr, int i, int i2) {
        while (i < i2) {
            if (((1 << UCharacter.getDirection(cArr[i])) & 57378) != 0) {
                return true;
            }
            i++;
        }
        return false;
    }

    public static void reorderVisually(byte[] bArr, int i, Object[] objArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i, bArr2, 0, i3);
        int[] iArrReorderVisual = reorderVisual(bArr2);
        Object[] objArr2 = new Object[i3];
        System.arraycopy(objArr, i2, objArr2, 0, i3);
        for (int i4 = 0; i4 < i3; i4++) {
            objArr[i2 + i4] = objArr2[iArrReorderVisual[i4]];
        }
    }

    public String writeReordered(int i) {
        verifyValidParaOrLine();
        return this.length == 0 ? "" : BidiWriter.writeReordered(this, i);
    }

    public static String writeReverse(String str, int i) {
        if (str != null) {
            return str.length() > 0 ? BidiWriter.writeReverse(str, i) : "";
        }
        throw new IllegalArgumentException();
    }
}
