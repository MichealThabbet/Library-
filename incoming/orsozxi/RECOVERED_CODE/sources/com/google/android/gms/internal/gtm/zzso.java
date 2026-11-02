package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzrc;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sqlcipher.database.SQLiteDatabase;
import sun.misc.Unsafe;

/* loaded from: classes.dex */
final class zzso<T> implements zzsz<T> {
    private static final int[] zzbcw = new int[0];
    private static final Unsafe zzbcx = zztx.zzro();
    private final int[] zzbcy;
    private final Object[] zzbcz;
    private final int zzbda;
    private final int zzbdb;
    private final zzsk zzbdc;
    private final boolean zzbdd;
    private final boolean zzbde;
    private final boolean zzbdf;
    private final boolean zzbdg;
    private final int[] zzbdh;
    private final int zzbdi;
    private final int zzbdj;
    private final zzsr zzbdk;
    private final zzru zzbdl;
    private final zztr<?, ?> zzbdm;
    private final zzqq<?> zzbdn;
    private final zzsf zzbdo;

    private zzso(int[] iArr, Object[] objArr, int i, int i2, zzsk zzskVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzsr zzsrVar, zzru zzruVar, zztr<?, ?> zztrVar, zzqq<?> zzqqVar, zzsf zzsfVar) {
        this.zzbcy = iArr;
        this.zzbcz = objArr;
        this.zzbda = i;
        this.zzbdb = i2;
        this.zzbde = zzskVar instanceof zzrc;
        this.zzbdf = z;
        this.zzbdd = zzqqVar != null && zzqqVar.zze(zzskVar);
        this.zzbdg = false;
        this.zzbdh = iArr2;
        this.zzbdi = i3;
        this.zzbdj = i4;
        this.zzbdk = zzsrVar;
        this.zzbdl = zzruVar;
        this.zzbdm = zztrVar;
        this.zzbdn = zzqqVar;
        this.zzbdc = zzskVar;
        this.zzbdo = zzsfVar;
    }

    private static boolean zzbt(int i) {
        return (i & 536870912) != 0;
    }

    static <T> zzso<T> zza(Class<T> cls, zzsi zzsiVar, zzsr zzsrVar, zzru zzruVar, zztr<?, ?> zztrVar, zzqq<?> zzqqVar, zzsf zzsfVar) {
        int i;
        int iCharAt;
        int iCharAt2;
        int i2;
        int i3;
        int i4;
        int i5;
        int[] iArr;
        int i6;
        int i7;
        char cCharAt;
        int i8;
        char cCharAt2;
        int i9;
        char cCharAt3;
        int i10;
        char cCharAt4;
        int i11;
        char cCharAt5;
        int i12;
        char cCharAt6;
        int i13;
        char cCharAt7;
        int i14;
        char cCharAt8;
        int i15;
        int i16;
        boolean z;
        int i17;
        zzsx zzsxVar;
        int i18;
        int iObjectFieldOffset;
        int i19;
        int i20;
        Class<?> cls2;
        String str;
        int iObjectFieldOffset2;
        int i21;
        Field fieldZza;
        int i22;
        char cCharAt9;
        int i23;
        Field fieldZza2;
        Field fieldZza3;
        int i24;
        char cCharAt10;
        int i25;
        char cCharAt11;
        int i26;
        char cCharAt12;
        int i27;
        char cCharAt13;
        char cCharAt14;
        if (zzsiVar instanceof zzsx) {
            zzsx zzsxVar2 = (zzsx) zzsiVar;
            int i28 = 0;
            boolean z2 = zzsxVar2.zzql() == zzrc.zze.zzbba;
            String strZzqt = zzsxVar2.zzqt();
            int length = strZzqt.length();
            int iCharAt3 = strZzqt.charAt(0);
            if (iCharAt3 >= 55296) {
                int i29 = iCharAt3 & 8191;
                int i30 = 1;
                int i31 = 13;
                while (true) {
                    i = i30 + 1;
                    cCharAt14 = strZzqt.charAt(i30);
                    if (cCharAt14 < 55296) {
                        break;
                    }
                    i29 |= (cCharAt14 & 8191) << i31;
                    i31 += 13;
                    i30 = i;
                }
                iCharAt3 = i29 | (cCharAt14 << i31);
            } else {
                i = 1;
            }
            int i32 = i + 1;
            int iCharAt4 = strZzqt.charAt(i);
            if (iCharAt4 >= 55296) {
                int i33 = iCharAt4 & 8191;
                int i34 = 13;
                while (true) {
                    i27 = i32 + 1;
                    cCharAt13 = strZzqt.charAt(i32);
                    if (cCharAt13 < 55296) {
                        break;
                    }
                    i33 |= (cCharAt13 & 8191) << i34;
                    i34 += 13;
                    i32 = i27;
                }
                iCharAt4 = i33 | (cCharAt13 << i34);
                i32 = i27;
            }
            if (iCharAt4 == 0) {
                iArr = zzbcw;
                i6 = 0;
                i3 = 0;
                iCharAt = 0;
                i4 = 0;
                iCharAt2 = 0;
                i5 = 0;
            } else {
                int i35 = i32 + 1;
                int iCharAt5 = strZzqt.charAt(i32);
                if (iCharAt5 >= 55296) {
                    int i36 = iCharAt5 & 8191;
                    int i37 = 13;
                    while (true) {
                        i14 = i35 + 1;
                        cCharAt8 = strZzqt.charAt(i35);
                        if (cCharAt8 < 55296) {
                            break;
                        }
                        i36 |= (cCharAt8 & 8191) << i37;
                        i37 += 13;
                        i35 = i14;
                    }
                    iCharAt5 = i36 | (cCharAt8 << i37);
                    i35 = i14;
                }
                int i38 = i35 + 1;
                int iCharAt6 = strZzqt.charAt(i35);
                if (iCharAt6 >= 55296) {
                    int i39 = iCharAt6 & 8191;
                    int i40 = 13;
                    while (true) {
                        i13 = i38 + 1;
                        cCharAt7 = strZzqt.charAt(i38);
                        if (cCharAt7 < 55296) {
                            break;
                        }
                        i39 |= (cCharAt7 & 8191) << i40;
                        i40 += 13;
                        i38 = i13;
                    }
                    iCharAt6 = i39 | (cCharAt7 << i40);
                    i38 = i13;
                }
                int i41 = i38 + 1;
                iCharAt = strZzqt.charAt(i38);
                if (iCharAt >= 55296) {
                    int i42 = iCharAt & 8191;
                    int i43 = 13;
                    while (true) {
                        i12 = i41 + 1;
                        cCharAt6 = strZzqt.charAt(i41);
                        if (cCharAt6 < 55296) {
                            break;
                        }
                        i42 |= (cCharAt6 & 8191) << i43;
                        i43 += 13;
                        i41 = i12;
                    }
                    iCharAt = i42 | (cCharAt6 << i43);
                    i41 = i12;
                }
                int i44 = i41 + 1;
                int iCharAt7 = strZzqt.charAt(i41);
                if (iCharAt7 >= 55296) {
                    int i45 = iCharAt7 & 8191;
                    int i46 = 13;
                    while (true) {
                        i11 = i44 + 1;
                        cCharAt5 = strZzqt.charAt(i44);
                        if (cCharAt5 < 55296) {
                            break;
                        }
                        i45 |= (cCharAt5 & 8191) << i46;
                        i46 += 13;
                        i44 = i11;
                    }
                    iCharAt7 = i45 | (cCharAt5 << i46);
                    i44 = i11;
                }
                int i47 = i44 + 1;
                iCharAt2 = strZzqt.charAt(i44);
                if (iCharAt2 >= 55296) {
                    int i48 = iCharAt2 & 8191;
                    int i49 = 13;
                    while (true) {
                        i10 = i47 + 1;
                        cCharAt4 = strZzqt.charAt(i47);
                        if (cCharAt4 < 55296) {
                            break;
                        }
                        i48 |= (cCharAt4 & 8191) << i49;
                        i49 += 13;
                        i47 = i10;
                    }
                    iCharAt2 = i48 | (cCharAt4 << i49);
                    i47 = i10;
                }
                int i50 = i47 + 1;
                int iCharAt8 = strZzqt.charAt(i47);
                if (iCharAt8 >= 55296) {
                    int i51 = iCharAt8 & 8191;
                    int i52 = 13;
                    while (true) {
                        i9 = i50 + 1;
                        cCharAt3 = strZzqt.charAt(i50);
                        if (cCharAt3 < 55296) {
                            break;
                        }
                        i51 |= (cCharAt3 & 8191) << i52;
                        i52 += 13;
                        i50 = i9;
                    }
                    iCharAt8 = i51 | (cCharAt3 << i52);
                    i50 = i9;
                }
                int i53 = i50 + 1;
                int iCharAt9 = strZzqt.charAt(i50);
                if (iCharAt9 >= 55296) {
                    int i54 = iCharAt9 & 8191;
                    int i55 = i53;
                    int i56 = 13;
                    while (true) {
                        i8 = i55 + 1;
                        cCharAt2 = strZzqt.charAt(i55);
                        if (cCharAt2 < 55296) {
                            break;
                        }
                        i54 |= (cCharAt2 & 8191) << i56;
                        i56 += 13;
                        i55 = i8;
                    }
                    iCharAt9 = i54 | (cCharAt2 << i56);
                    i2 = i8;
                } else {
                    i2 = i53;
                }
                int i57 = i2 + 1;
                int iCharAt10 = strZzqt.charAt(i2);
                if (iCharAt10 >= 55296) {
                    int i58 = iCharAt10 & 8191;
                    int i59 = i57;
                    int i60 = 13;
                    while (true) {
                        i7 = i59 + 1;
                        cCharAt = strZzqt.charAt(i59);
                        if (cCharAt < 55296) {
                            break;
                        }
                        i58 |= (cCharAt & 8191) << i60;
                        i60 += 13;
                        i59 = i7;
                    }
                    iCharAt10 = i58 | (cCharAt << i60);
                    i57 = i7;
                }
                int[] iArr2 = new int[iCharAt10 + iCharAt8 + iCharAt9];
                int i61 = (iCharAt5 << 1) + iCharAt6;
                i3 = iCharAt7;
                i4 = i61;
                i5 = iCharAt10;
                i28 = iCharAt5;
                i32 = i57;
                int i62 = iCharAt8;
                iArr = iArr2;
                i6 = i62;
            }
            Unsafe unsafe = zzbcx;
            Object[] objArrZzqu = zzsxVar2.zzqu();
            Class<?> cls3 = zzsxVar2.zzqn().getClass();
            int i63 = i32;
            int[] iArr3 = new int[iCharAt2 * 3];
            Object[] objArr = new Object[iCharAt2 << 1];
            int i64 = i5 + i6;
            int i65 = i5;
            int i66 = i63;
            int i67 = i64;
            int i68 = 0;
            int i69 = 0;
            while (i66 < length) {
                int i70 = i66 + 1;
                int iCharAt11 = strZzqt.charAt(i66);
                int i71 = length;
                if (iCharAt11 >= 55296) {
                    int i72 = iCharAt11 & 8191;
                    int i73 = i70;
                    int i74 = 13;
                    while (true) {
                        i26 = i73 + 1;
                        cCharAt12 = strZzqt.charAt(i73);
                        i15 = i5;
                        if (cCharAt12 < 55296) {
                            break;
                        }
                        i72 |= (cCharAt12 & 8191) << i74;
                        i74 += 13;
                        i73 = i26;
                        i5 = i15;
                    }
                    iCharAt11 = i72 | (cCharAt12 << i74);
                    i16 = i26;
                } else {
                    i15 = i5;
                    i16 = i70;
                }
                int i75 = i16 + 1;
                int iCharAt12 = strZzqt.charAt(i16);
                if (iCharAt12 >= 55296) {
                    int i76 = iCharAt12 & 8191;
                    int i77 = i75;
                    int i78 = 13;
                    while (true) {
                        i25 = i77 + 1;
                        cCharAt11 = strZzqt.charAt(i77);
                        z = z2;
                        if (cCharAt11 < 55296) {
                            break;
                        }
                        i76 |= (cCharAt11 & 8191) << i78;
                        i78 += 13;
                        i77 = i25;
                        z2 = z;
                    }
                    iCharAt12 = i76 | (cCharAt11 << i78);
                    i17 = i25;
                } else {
                    z = z2;
                    i17 = i75;
                }
                int i79 = iCharAt12 & 255;
                int i80 = i3;
                if ((iCharAt12 & 1024) != 0) {
                    iArr[i68] = i69;
                    i68++;
                }
                int i81 = iCharAt;
                if (i79 >= 51) {
                    int i82 = i17 + 1;
                    int iCharAt13 = strZzqt.charAt(i17);
                    char c = 55296;
                    if (iCharAt13 >= 55296) {
                        int i83 = iCharAt13 & 8191;
                        int i84 = 13;
                        while (true) {
                            i24 = i82 + 1;
                            cCharAt10 = strZzqt.charAt(i82);
                            if (cCharAt10 < c) {
                                break;
                            }
                            i83 |= (cCharAt10 & 8191) << i84;
                            i84 += 13;
                            i82 = i24;
                            c = 55296;
                        }
                        iCharAt13 = i83 | (cCharAt10 << i84);
                        i82 = i24;
                    }
                    int i85 = i79 - 51;
                    int i86 = i82;
                    if (i85 == 9 || i85 == 17) {
                        objArr[((i69 / 3) << 1) + 1] = objArrZzqu[i4];
                        i4++;
                    } else if (i85 == 12 && (iCharAt3 & 1) == 1) {
                        objArr[((i69 / 3) << 1) + 1] = objArrZzqu[i4];
                        i4++;
                    }
                    int i87 = iCharAt13 << 1;
                    Object obj = objArrZzqu[i87];
                    if (obj instanceof Field) {
                        fieldZza2 = (Field) obj;
                    } else {
                        fieldZza2 = zza(cls3, (String) obj);
                        objArrZzqu[i87] = fieldZza2;
                    }
                    zzsxVar = zzsxVar2;
                    String str2 = strZzqt;
                    iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZza2);
                    int i88 = i87 + 1;
                    Object obj2 = objArrZzqu[i88];
                    if (obj2 instanceof Field) {
                        fieldZza3 = (Field) obj2;
                    } else {
                        fieldZza3 = zza(cls3, (String) obj2);
                        objArrZzqu[i88] = fieldZza3;
                    }
                    cls2 = cls3;
                    i19 = i4;
                    i17 = i86;
                    str = str2;
                    i21 = 0;
                    iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZza3);
                    i20 = i28;
                } else {
                    zzsxVar = zzsxVar2;
                    String str3 = strZzqt;
                    int i89 = i4 + 1;
                    Field fieldZza4 = zza(cls3, (String) objArrZzqu[i4]);
                    if (i79 == 9 || i79 == 17) {
                        i18 = 1;
                        objArr[((i69 / 3) << 1) + 1] = fieldZza4.getType();
                    } else {
                        if (i79 == 27 || i79 == 49) {
                            i18 = 1;
                            i23 = i89 + 1;
                            objArr[((i69 / 3) << 1) + 1] = objArrZzqu[i89];
                        } else if (i79 == 12 || i79 == 30 || i79 == 44) {
                            i18 = 1;
                            if ((iCharAt3 & 1) == 1) {
                                i23 = i89 + 1;
                                objArr[((i69 / 3) << 1) + 1] = objArrZzqu[i89];
                            }
                        } else if (i79 == 50) {
                            int i90 = i65 + 1;
                            iArr[i65] = i69;
                            int i91 = (i69 / 3) << 1;
                            int i92 = i89 + 1;
                            objArr[i91] = objArrZzqu[i89];
                            if ((iCharAt12 & 2048) != 0) {
                                i89 = i92 + 1;
                                objArr[i91 + 1] = objArrZzqu[i92];
                                i65 = i90;
                                i18 = 1;
                            } else {
                                i89 = i92;
                                i18 = 1;
                                i65 = i90;
                            }
                        } else {
                            i18 = 1;
                        }
                        i89 = i23;
                    }
                    iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZza4);
                    if ((iCharAt3 & 1) != i18 || i79 > 17) {
                        i19 = i89;
                        i20 = i28;
                        cls2 = cls3;
                        str = str3;
                        iObjectFieldOffset2 = 0;
                        i21 = 0;
                    } else {
                        int i93 = i17 + 1;
                        str = str3;
                        int iCharAt14 = str.charAt(i17);
                        if (iCharAt14 >= 55296) {
                            int i94 = iCharAt14 & 8191;
                            int i95 = 13;
                            while (true) {
                                i22 = i93 + 1;
                                cCharAt9 = str.charAt(i93);
                                if (cCharAt9 < 55296) {
                                    break;
                                }
                                i94 |= (cCharAt9 & 8191) << i95;
                                i95 += 13;
                                i93 = i22;
                            }
                            iCharAt14 = i94 | (cCharAt9 << i95);
                            i93 = i22;
                        }
                        int i96 = (i28 << 1) + (iCharAt14 / 32);
                        Object obj3 = objArrZzqu[i96];
                        i19 = i89;
                        if (obj3 instanceof Field) {
                            fieldZza = (Field) obj3;
                        } else {
                            fieldZza = zza(cls3, (String) obj3);
                            objArrZzqu[i96] = fieldZza;
                        }
                        i20 = i28;
                        cls2 = cls3;
                        iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZza);
                        i21 = iCharAt14 % 32;
                        i17 = i93;
                    }
                    if (i79 >= 18 && i79 <= 49) {
                        iArr[i67] = iObjectFieldOffset;
                        i67++;
                    }
                }
                int i97 = i69 + 1;
                iArr3[i69] = iCharAt11;
                int i98 = i97 + 1;
                iArr3[i97] = iObjectFieldOffset | ((iCharAt12 & 256) != 0 ? SQLiteDatabase.CREATE_IF_NECESSARY : 0) | ((iCharAt12 & 512) != 0 ? 536870912 : 0) | (i79 << 20);
                i69 = i98 + 1;
                iArr3[i98] = (i21 << 20) | iObjectFieldOffset2;
                i28 = i20;
                strZzqt = str;
                i66 = i17;
                cls3 = cls2;
                i3 = i80;
                length = i71;
                i5 = i15;
                z2 = z;
                iCharAt = i81;
                i4 = i19;
                zzsxVar2 = zzsxVar;
            }
            return new zzso<>(iArr3, objArr, iCharAt, i3, zzsxVar2.zzqn(), z2, false, iArr, i5, i64, zzsrVar, zzruVar, zztrVar, zzqqVar, zzsfVar);
        }
        ((zztm) zzsiVar).zzql();
        int i99 = zzrc.zze.zzbba;
        throw new NoSuchMethodError();
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String string = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name).length() + String.valueOf(string).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(string);
            throw new RuntimeException(sb.toString());
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzsz
    public final T newInstance() {
        return (T) this.zzbdk.newInstance(this.zzbdc);
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x01c1  */
    @Override // com.google.android.gms.internal.gtm.zzsz
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean equals(T r10, T r11) {
        /*
            Method dump skipped, instructions count: 640
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzso.equals(java.lang.Object, java.lang.Object):boolean");
    }

    @Override // com.google.android.gms.internal.gtm.zzsz
    public final int hashCode(T t) {
        int i;
        int iZzz;
        int length = this.zzbcy.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iZzbr = zzbr(i3);
            int i4 = this.zzbcy[i3];
            long j = 1048575 & iZzbr;
            int iHashCode = 37;
            switch ((iZzbr & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    iZzz = zzre.zzz(Double.doubleToLongBits(zztx.zzo(t, j)));
                    i2 = i + iZzz;
                    break;
                case 1:
                    i = i2 * 53;
                    iZzz = Float.floatToIntBits(zztx.zzn(t, j));
                    i2 = i + iZzz;
                    break;
                case 2:
                    i = i2 * 53;
                    iZzz = zzre.zzz(zztx.zzl(t, j));
                    i2 = i + iZzz;
                    break;
                case 3:
                    i = i2 * 53;
                    iZzz = zzre.zzz(zztx.zzl(t, j));
                    i2 = i + iZzz;
                    break;
                case 4:
                    i = i2 * 53;
                    iZzz = zztx.zzk(t, j);
                    i2 = i + iZzz;
                    break;
                case 5:
                    i = i2 * 53;
                    iZzz = zzre.zzz(zztx.zzl(t, j));
                    i2 = i + iZzz;
                    break;
                case 6:
                    i = i2 * 53;
                    iZzz = zztx.zzk(t, j);
                    i2 = i + iZzz;
                    break;
                case 7:
                    i = i2 * 53;
                    iZzz = zzre.zzk(zztx.zzm(t, j));
                    i2 = i + iZzz;
                    break;
                case 8:
                    i = i2 * 53;
                    iZzz = ((String) zztx.zzp(t, j)).hashCode();
                    i2 = i + iZzz;
                    break;
                case 9:
                    Object objZzp = zztx.zzp(t, j);
                    if (objZzp != null) {
                        iHashCode = objZzp.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iZzz = zztx.zzp(t, j).hashCode();
                    i2 = i + iZzz;
                    break;
                case 11:
                    i = i2 * 53;
                    iZzz = zztx.zzk(t, j);
                    i2 = i + iZzz;
                    break;
                case 12:
                    i = i2 * 53;
                    iZzz = zztx.zzk(t, j);
                    i2 = i + iZzz;
                    break;
                case 13:
                    i = i2 * 53;
                    iZzz = zztx.zzk(t, j);
                    i2 = i + iZzz;
                    break;
                case 14:
                    i = i2 * 53;
                    iZzz = zzre.zzz(zztx.zzl(t, j));
                    i2 = i + iZzz;
                    break;
                case 15:
                    i = i2 * 53;
                    iZzz = zztx.zzk(t, j);
                    i2 = i + iZzz;
                    break;
                case 16:
                    i = i2 * 53;
                    iZzz = zzre.zzz(zztx.zzl(t, j));
                    i2 = i + iZzz;
                    break;
                case 17:
                    Object objZzp2 = zztx.zzp(t, j);
                    if (objZzp2 != null) {
                        iHashCode = objZzp2.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i2 * 53;
                    iZzz = zztx.zzp(t, j).hashCode();
                    i2 = i + iZzz;
                    break;
                case 50:
                    i = i2 * 53;
                    iZzz = zztx.zzp(t, j).hashCode();
                    i2 = i + iZzz;
                    break;
                case 51:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zzre.zzz(Double.doubleToLongBits(zzf(t, j)));
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = Float.floatToIntBits(zzg(t, j));
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zzre.zzz(zzi(t, j));
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zzre.zzz(zzi(t, j));
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zzh(t, j);
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zzre.zzz(zzi(t, j));
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zzh(t, j);
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zzre.zzk(zzj(t, j));
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = ((String) zztx.zzp(t, j)).hashCode();
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zztx.zzp(t, j).hashCode();
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zztx.zzp(t, j).hashCode();
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zzh(t, j);
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zzh(t, j);
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zzh(t, j);
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zzre.zzz(zzi(t, j));
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zzh(t, j);
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zzre.zzz(zzi(t, j));
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zza((zzso<T>) t, i4, i3)) {
                        i = i2 * 53;
                        iZzz = zztx.zzp(t, j).hashCode();
                        i2 = i + iZzz;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i2 * 53) + this.zzbdm.zzag(t).hashCode();
        return this.zzbdd ? (iHashCode2 * 53) + this.zzbdn.zzr(t).hashCode() : iHashCode2;
    }

    @Override // com.google.android.gms.internal.gtm.zzsz
    public final void zzd(T t, T t2) {
        if (t2 == null) {
            throw null;
        }
        for (int i = 0; i < this.zzbcy.length; i += 3) {
            int iZzbr = zzbr(i);
            long j = 1048575 & iZzbr;
            int i2 = this.zzbcy[i];
            switch ((iZzbr & 267386880) >>> 20) {
                case 0:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zza(t, j, zztx.zzo(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zza((Object) t, j, zztx.zzn(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zza((Object) t, j, zztx.zzl(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zza((Object) t, j, zztx.zzl(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zzb(t, j, zztx.zzk(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zza((Object) t, j, zztx.zzl(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zzb(t, j, zztx.zzk(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zza(t, j, zztx.zzm(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zza(t, j, zztx.zzp(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zza(t, t2, i);
                    break;
                case 10:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zza(t, j, zztx.zzp(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zzb(t, j, zztx.zzk(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zzb(t, j, zztx.zzk(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zzb(t, j, zztx.zzk(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zza((Object) t, j, zztx.zzl(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zzb(t, j, zztx.zzk(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzb((zzso<T>) t2, i)) {
                        zztx.zza((Object) t, j, zztx.zzl(t2, j));
                        zzc(t, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zza(t, t2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzbdl.zza(t, t2, j);
                    break;
                case 50:
                    zztb.zza(this.zzbdo, t, t2, j);
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zza((zzso<T>) t2, i2, i)) {
                        zztx.zza(t, j, zztx.zzp(t2, j));
                        zzb((zzso<T>) t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzb(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zza((zzso<T>) t2, i2, i)) {
                        zztx.zza(t, j, zztx.zzp(t2, j));
                        zzb((zzso<T>) t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzb(t, t2, i);
                    break;
            }
        }
        if (this.zzbdf) {
            return;
        }
        zztb.zza(this.zzbdm, t, t2);
        if (this.zzbdd) {
            zztb.zza(this.zzbdn, t, t2);
        }
    }

    private final void zza(T t, T t2, int i) {
        long jZzbr = zzbr(i) & 1048575;
        if (zzb((zzso<T>) t2, i)) {
            Object objZzp = zztx.zzp(t, jZzbr);
            Object objZzp2 = zztx.zzp(t2, jZzbr);
            if (objZzp != null && objZzp2 != null) {
                zztx.zza(t, jZzbr, zzre.zzb(objZzp, objZzp2));
                zzc(t, i);
            } else if (objZzp2 != null) {
                zztx.zza(t, jZzbr, objZzp2);
                zzc(t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int iZzbr = zzbr(i);
        int i2 = this.zzbcy[i];
        long j = iZzbr & 1048575;
        if (zza((zzso<T>) t2, i2, i)) {
            Object objZzp = zztx.zzp(t, j);
            Object objZzp2 = zztx.zzp(t2, j);
            if (objZzp != null && objZzp2 != null) {
                zztx.zza(t, j, zzre.zzb(objZzp, objZzp2));
                zzb((zzso<T>) t, i2, i);
            } else if (objZzp2 != null) {
                zztx.zza(t, j, objZzp2);
                zzb((zzso<T>) t, i2, i);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:421:0x090b A[PHI: r6
      0x090b: PHI (r6v4 int) = 
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v16 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v17 int)
      (r6v1 int)
     binds: [B:256:0x0545, B:459:0x09b0, B:453:0x0994, B:450:0x0982, B:447:0x0973, B:444:0x0966, B:441:0x0959, B:437:0x094e, B:434:0x0943, B:431:0x0936, B:428:0x0929, B:425:0x0916, B:396:0x081f, B:390:0x0802, B:384:0x07e5, B:378:0x07c8, B:372:0x07aa, B:366:0x078c, B:360:0x076e, B:354:0x0750, B:348:0x0732, B:342:0x0714, B:336:0x06f6, B:330:0x06d8, B:324:0x06ba, B:318:0x069c, B:313:0x0668, B:310:0x065b, B:307:0x064b, B:304:0x063b, B:301:0x062b, B:298:0x061d, B:295:0x0610, B:292:0x0603, B:286:0x05e5, B:283:0x05d1, B:280:0x05bf, B:277:0x05af, B:274:0x059f, B:439:0x0955, B:271:0x0592, B:268:0x0584, B:265:0x0574, B:262:0x0564, B:420:0x090a, B:259:0x054e] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.gms.internal.gtm.zzsz
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int zzad(T r20) {
        /*
            Method dump skipped, instructions count: 2986
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzso.zzad(java.lang.Object):int");
    }

    private static <UT, UB> int zza(zztr<UT, UB> zztrVar, T t) {
        return zztrVar.zzad(zztrVar.zzag(t));
    }

    private static <E> List<E> zze(Object obj, long j) {
        return (List) zztx.zzp(obj, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:178:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0032  */
    @Override // com.google.android.gms.internal.gtm.zzsz
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r14, com.google.android.gms.internal.gtm.zzum r15) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 2916
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzso.zza(java.lang.Object, com.google.android.gms.internal.gtm.zzum):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void zzb(T r19, com.google.android.gms.internal.gtm.zzum r20) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1344
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzso.zzb(java.lang.Object, com.google.android.gms.internal.gtm.zzum):void");
    }

    private final <K, V> void zza(zzum zzumVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzumVar.zza(i, this.zzbdo.zzac(zzbp(i2)), this.zzbdo.zzy(obj));
        }
    }

    private static <UT, UB> void zza(zztr<UT, UB> zztrVar, T t, zzum zzumVar) throws IOException {
        zztrVar.zza((zztr<UT, UB>) zztrVar.zzag(t), zzumVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:177:0x05dc A[LOOP:6: B:175:0x05d8->B:177:0x05dc, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x05e9  */
    @Override // com.google.android.gms.internal.gtm.zzsz
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(T r18, com.google.android.gms.internal.gtm.zzsy r19, com.google.android.gms.internal.gtm.zzqp r20) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1660
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzso.zza(java.lang.Object, com.google.android.gms.internal.gtm.zzsy, com.google.android.gms.internal.gtm.zzqp):void");
    }

    private final zzsz zzbo(int i) {
        int i2 = (i / 3) << 1;
        zzsz zzszVar = (zzsz) this.zzbcz[i2];
        if (zzszVar != null) {
            return zzszVar;
        }
        zzsz<T> zzszVarZzi = zzsw.zzqs().zzi((Class) this.zzbcz[i2 + 1]);
        this.zzbcz[i2] = zzszVarZzi;
        return zzszVarZzi;
    }

    private final Object zzbp(int i) {
        return this.zzbcz[(i / 3) << 1];
    }

    private final zzrh zzbq(int i) {
        return (zzrh) this.zzbcz[((i / 3) << 1) + 1];
    }

    @Override // com.google.android.gms.internal.gtm.zzsz
    public final void zzt(T t) {
        int i;
        int i2 = this.zzbdi;
        while (true) {
            i = this.zzbdj;
            if (i2 >= i) {
                break;
            }
            long jZzbr = zzbr(this.zzbdh[i2]) & 1048575;
            Object objZzp = zztx.zzp(t, jZzbr);
            if (objZzp != null) {
                zztx.zza(t, jZzbr, this.zzbdo.zzaa(objZzp));
            }
            i2++;
        }
        int length = this.zzbdh.length;
        while (i < length) {
            this.zzbdl.zzb(t, this.zzbdh[i]);
            i++;
        }
        this.zzbdm.zzt(t);
        if (this.zzbdd) {
            this.zzbdn.zzt(t);
        }
    }

    private final <UT, UB> UB zza(Object obj, int i, UB ub, zztr<UT, UB> zztrVar) {
        zzrh zzrhVarZzbq;
        int i2 = this.zzbcy[i];
        Object objZzp = zztx.zzp(obj, zzbr(i) & 1048575);
        return (objZzp == null || (zzrhVarZzbq = zzbq(i)) == null) ? ub : (UB) zza(i, i2, this.zzbdo.zzx(objZzp), zzrhVarZzbq, ub, zztrVar);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzrh zzrhVar, UB ub, zztr<UT, UB> zztrVar) {
        zzsd<?, ?> zzsdVarZzac = this.zzbdo.zzac(zzbp(i));
        Iterator<Map.Entry<K, V>> it2 = map.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<K, V> next = it2.next();
            if (!zzrhVar.zzb(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = zztrVar.zzri();
                }
                zzqa zzqaVarZzam = zzps.zzam(zzsc.zza(zzsdVarZzac, next.getKey(), next.getValue()));
                try {
                    zzsc.zza(zzqaVarZzam.zznh(), zzsdVarZzac, next.getKey(), next.getValue());
                    zztrVar.zza((zztr<UT, UB>) ub, i2, zzqaVarZzam.zzng());
                    it2.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00ca  */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14, types: [com.google.android.gms.internal.gtm.zzsz] */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v5, types: [com.google.android.gms.internal.gtm.zzsz] */
    @Override // com.google.android.gms.internal.gtm.zzsz
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzae(T r14) {
        /*
            Method dump skipped, instructions count: 285
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzso.zzae(java.lang.Object):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i, zzsz zzszVar) {
        return zzszVar.zzae(zztx.zzp(obj, i & 1048575));
    }

    private static void zza(int i, Object obj, zzum zzumVar) throws IOException {
        if (obj instanceof String) {
            zzumVar.zza(i, (String) obj);
        } else {
            zzumVar.zza(i, (zzps) obj);
        }
    }

    private final void zza(Object obj, int i, zzsy zzsyVar) throws IOException {
        if (zzbt(i)) {
            zztx.zza(obj, i & 1048575, zzsyVar.zznp());
        } else if (this.zzbde) {
            zztx.zza(obj, i & 1048575, zzsyVar.readString());
        } else {
            zztx.zza(obj, i & 1048575, zzsyVar.zznq());
        }
    }

    private final int zzbr(int i) {
        return this.zzbcy[i + 1];
    }

    private final int zzbs(int i) {
        return this.zzbcy[i + 2];
    }

    private static <T> double zzf(T t, long j) {
        return ((Double) zztx.zzp(t, j)).doubleValue();
    }

    private static <T> float zzg(T t, long j) {
        return ((Float) zztx.zzp(t, j)).floatValue();
    }

    private static <T> int zzh(T t, long j) {
        return ((Integer) zztx.zzp(t, j)).intValue();
    }

    private static <T> long zzi(T t, long j) {
        return ((Long) zztx.zzp(t, j)).longValue();
    }

    private static <T> boolean zzj(T t, long j) {
        return ((Boolean) zztx.zzp(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zzb((zzso<T>) t, i) == zzb((zzso<T>) t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3) {
        if (this.zzbdf) {
            return zzb((zzso<T>) t, i);
        }
        return (i2 & i3) != 0;
    }

    private final boolean zzb(T t, int i) {
        if (this.zzbdf) {
            int iZzbr = zzbr(i);
            long j = iZzbr & 1048575;
            switch ((iZzbr & 267386880) >>> 20) {
                case 0:
                    return zztx.zzo(t, j) != 0.0d;
                case 1:
                    return zztx.zzn(t, j) != 0.0f;
                case 2:
                    return zztx.zzl(t, j) != 0;
                case 3:
                    return zztx.zzl(t, j) != 0;
                case 4:
                    return zztx.zzk(t, j) != 0;
                case 5:
                    return zztx.zzl(t, j) != 0;
                case 6:
                    return zztx.zzk(t, j) != 0;
                case 7:
                    return zztx.zzm(t, j);
                case 8:
                    Object objZzp = zztx.zzp(t, j);
                    if (objZzp instanceof String) {
                        return !((String) objZzp).isEmpty();
                    }
                    if (objZzp instanceof zzps) {
                        return !zzps.zzavx.equals(objZzp);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    return zztx.zzp(t, j) != null;
                case 10:
                    return !zzps.zzavx.equals(zztx.zzp(t, j));
                case 11:
                    return zztx.zzk(t, j) != 0;
                case 12:
                    return zztx.zzk(t, j) != 0;
                case 13:
                    return zztx.zzk(t, j) != 0;
                case 14:
                    return zztx.zzl(t, j) != 0;
                case 15:
                    return zztx.zzk(t, j) != 0;
                case 16:
                    return zztx.zzl(t, j) != 0;
                case 17:
                    return zztx.zzp(t, j) != null;
                default:
                    throw new IllegalArgumentException();
            }
        }
        int iZzbs = zzbs(i);
        return (zztx.zzk(t, (long) (iZzbs & 1048575)) & (1 << (iZzbs >>> 20))) != 0;
    }

    private final void zzc(T t, int i) {
        if (this.zzbdf) {
            return;
        }
        int iZzbs = zzbs(i);
        long j = iZzbs & 1048575;
        zztx.zzb(t, j, zztx.zzk(t, j) | (1 << (iZzbs >>> 20)));
    }

    private final boolean zza(T t, int i, int i2) {
        return zztx.zzk(t, (long) (zzbs(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zztx.zzb(t, zzbs(i2) & 1048575, i);
    }
}
