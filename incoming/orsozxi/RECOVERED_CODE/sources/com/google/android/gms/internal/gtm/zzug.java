package com.google.android.gms.internal.gtm;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzbfx' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes.dex */
public class zzug {
    public static final zzug zzbfp = new zzug("DOUBLE", 0, zzul.DOUBLE, 1);
    public static final zzug zzbfq = new zzug("FLOAT", 1, zzul.FLOAT, 5);
    public static final zzug zzbfr = new zzug("INT64", 2, zzul.LONG, 0);
    public static final zzug zzbfs = new zzug("UINT64", 3, zzul.LONG, 0);
    public static final zzug zzbft = new zzug("INT32", 4, zzul.INT, 0);
    public static final zzug zzbfu = new zzug("FIXED64", 5, zzul.LONG, 1);
    public static final zzug zzbfv = new zzug("FIXED32", 6, zzul.INT, 5);
    public static final zzug zzbfw = new zzug("BOOL", 7, zzul.BOOLEAN, 0);
    public static final zzug zzbfx;
    public static final zzug zzbfy;
    public static final zzug zzbfz;
    public static final zzug zzbga;
    public static final zzug zzbgb;
    public static final zzug zzbgc;
    public static final zzug zzbgd;
    public static final zzug zzbge;
    public static final zzug zzbgf;
    public static final zzug zzbgg;
    private static final /* synthetic */ zzug[] zzbgj;
    private final zzul zzbgh;
    private final int zzbgi;

    public static zzug[] values() {
        return (zzug[]) zzbgj.clone();
    }

    private zzug(String str, int i, zzul zzulVar, int i2) {
        this.zzbgh = zzulVar;
        this.zzbgi = i2;
    }

    public final zzul zzrs() {
        return this.zzbgh;
    }

    public final int zzrt() {
        return this.zzbgi;
    }

    /* synthetic */ zzug(String str, int i, zzul zzulVar, int i2, zzuf zzufVar) {
        this(str, i, zzulVar, i2);
    }

    static {
        final int i = 2;
        final int i2 = 3;
        final zzul zzulVar = zzul.STRING;
        final String str = "STRING";
        final int i3 = 8;
        zzbfx = new zzug(str, i3, zzulVar, i) { // from class: com.google.android.gms.internal.gtm.zzuh
            {
                int i4 = 8;
                int i5 = 2;
                zzuf zzufVar = null;
            }
        };
        final zzul zzulVar2 = zzul.MESSAGE;
        final String str2 = "GROUP";
        final int i4 = 9;
        zzbfy = new zzug(str2, i4, zzulVar2, i2) { // from class: com.google.android.gms.internal.gtm.zzui
            {
                int i5 = 9;
                int i6 = 3;
                zzuf zzufVar = null;
            }
        };
        final zzul zzulVar3 = zzul.MESSAGE;
        final String str3 = "MESSAGE";
        final int i5 = 10;
        zzbfz = new zzug(str3, i5, zzulVar3, i) { // from class: com.google.android.gms.internal.gtm.zzuj
            {
                int i6 = 10;
                int i7 = 2;
                zzuf zzufVar = null;
            }
        };
        final zzul zzulVar4 = zzul.BYTE_STRING;
        final String str4 = "BYTES";
        final int i6 = 11;
        zzbga = new zzug(str4, i6, zzulVar4, i) { // from class: com.google.android.gms.internal.gtm.zzuk
            {
                int i7 = 11;
                int i8 = 2;
                zzuf zzufVar = null;
            }
        };
        zzbgb = new zzug("UINT32", 12, zzul.INT, 0);
        zzbgc = new zzug("ENUM", 13, zzul.ENUM, 0);
        zzbgd = new zzug("SFIXED32", 14, zzul.INT, 5);
        zzbge = new zzug("SFIXED64", 15, zzul.LONG, 1);
        zzbgf = new zzug("SINT32", 16, zzul.INT, 0);
        zzug zzugVar = new zzug("SINT64", 17, zzul.LONG, 0);
        zzbgg = zzugVar;
        zzbgj = new zzug[]{zzbfp, zzbfq, zzbfr, zzbfs, zzbft, zzbfu, zzbfv, zzbfw, zzbfx, zzbfy, zzbfz, zzbga, zzbgb, zzbgc, zzbgd, zzbge, zzbgf, zzugVar};
    }
}
