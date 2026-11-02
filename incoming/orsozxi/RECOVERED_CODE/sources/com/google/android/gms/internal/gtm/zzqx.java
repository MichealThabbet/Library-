package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
final /* synthetic */ class zzqx {
    static final /* synthetic */ int[] zzazy;
    static final /* synthetic */ int[] zzazz;

    static {
        int[] iArr = new int[zzrm.values().length];
        zzazz = iArr;
        try {
            iArr[zzrm.BYTE_STRING.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            zzazz[zzrm.MESSAGE.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            zzazz[zzrm.STRING.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        int[] iArr2 = new int[zzqy.values().length];
        zzazy = iArr2;
        try {
            iArr2[zzqy.MAP.ordinal()] = 1;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            zzazy[zzqy.VECTOR.ordinal()] = 2;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            zzazy[zzqy.SCALAR.ordinal()] = 3;
        } catch (NoSuchFieldError unused6) {
        }
    }
}
