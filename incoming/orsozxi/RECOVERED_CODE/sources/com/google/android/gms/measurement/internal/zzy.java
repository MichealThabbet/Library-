package com.google.android.gms.measurement.internal;

import java.util.Map;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
final class zzy extends zzke {
    private String zza;
    private Set<Integer> zzb;
    private Map<Integer, zzt> zzc;
    private Long zzd;
    private Long zze;

    zzy(zzkn zzknVar) {
        super(zzknVar);
    }

    private final zzt zzc(Integer num) {
        if (this.zzc.containsKey(num)) {
            return this.zzc.get(num);
        }
        zzt zztVar = new zzt(this, this.zza, null);
        this.zzc.put(num, zztVar);
        return zztVar;
    }

    private final boolean zzd(int i, int i2) {
        zzt zztVar = this.zzc.get(Integer.valueOf(i));
        if (zztVar == null) {
            return false;
        }
        return zztVar.zze.get(i2);
    }

    @Override // com.google.android.gms.measurement.internal.zzke
    protected final boolean zzaA() {
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:408:0x0a26, code lost:
    
        r0 = r65.zzs.zzau().zze();
        r6 = com.google.android.gms.measurement.internal.zzem.zzl(r65.zza);
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x0a3a, code lost:
    
        if (r7.zza() == false) goto L411;
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x0a3c, code lost:
    
        r7 = java.lang.Integer.valueOf(r7.zzb());
     */
    /* JADX WARN: Code restructure failed: missing block: B:411:0x0a45, code lost:
    
        r7 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:412:0x0a46, code lost:
    
        r0.zzc("Invalid property filter ID. appId, id", r6, java.lang.String.valueOf(r7));
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0255  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x026a  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02e5 A[PHI: r0 r5
      0x02e5: PHI (r0v64 java.util.Map) = (r0v46 java.util.Map), (r0v66 java.util.Map), (r0v40 java.util.Map) binds: [B:133:0x0312, B:122:0x02ed, B:119:0x02e3] A[DONT_GENERATE, DONT_INLINE]
      0x02e5: PHI (r5v16 android.database.Cursor) = (r5v9 android.database.Cursor), (r5v17 android.database.Cursor), (r5v17 android.database.Cursor) binds: [B:133:0x0312, B:122:0x02ed, B:119:0x02e3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0323  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x03e0  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x03ef  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x05a3  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x072e A[PHI: r0 r11 r13 r25 r28 r29
      0x072e: PHI (r0v97 java.util.Map) = (r0v99 java.util.Map), (r0v108 java.util.Map) binds: [B:300:0x0766, B:282:0x072c] A[DONT_GENERATE, DONT_INLINE]
      0x072e: PHI (r11v14 android.database.Cursor) = (r11v15 android.database.Cursor), (r11v17 android.database.Cursor) binds: [B:300:0x0766, B:282:0x072c] A[DONT_GENERATE, DONT_INLINE]
      0x072e: PHI (r13v12 java.lang.String) = (r13v13 java.lang.String), (r13v16 java.lang.String) binds: [B:300:0x0766, B:282:0x072c] A[DONT_GENERATE, DONT_INLINE]
      0x072e: PHI (r25v4 java.lang.String) = (r25v5 java.lang.String), (r25v7 java.lang.String) binds: [B:300:0x0766, B:282:0x072c] A[DONT_GENERATE, DONT_INLINE]
      0x072e: PHI (r28v7 java.lang.String) = (r28v8 java.lang.String), (r28v13 java.lang.String) binds: [B:300:0x0766, B:282:0x072c] A[DONT_GENERATE, DONT_INLINE]
      0x072e: PHI (r29v7 java.lang.String) = (r29v8 java.lang.String), (r29v14 java.lang.String) binds: [B:300:0x0766, B:282:0x072c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:312:0x078b  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x0833  */
    /* JADX WARN: Removed duplicated region for block: B:361:0x08fe A[PHI: r0 r13 r67
      0x08fe: PHI (r0v150 java.util.Map) = (r0v152 java.util.Map), (r0v158 java.util.Map) binds: [B:373:0x0926, B:360:0x08fc] A[DONT_GENERATE, DONT_INLINE]
      0x08fe: PHI (r13v31 android.database.Cursor) = (r13v32 android.database.Cursor), (r13v33 android.database.Cursor) binds: [B:373:0x0926, B:360:0x08fc] A[DONT_GENERATE, DONT_INLINE]
      0x08fe: PHI (r67v7 java.util.Iterator<com.google.android.gms.internal.measurement.zzgh>) = 
      (r67v8 java.util.Iterator<com.google.android.gms.internal.measurement.zzgh>)
      (r67v11 java.util.Iterator<com.google.android.gms.internal.measurement.zzgh>)
     binds: [B:373:0x0926, B:360:0x08fc] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:385:0x0949  */
    /* JADX WARN: Removed duplicated region for block: B:421:0x0a83  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0153 A[PHI: r0 r5
      0x0153: PHI (r0v207 java.util.Map) = (r0v206 java.util.Map), (r0v211 java.util.Map) binds: [B:55:0x0177, B:42:0x0151] A[DONT_GENERATE, DONT_INLINE]
      0x0153: PHI (r5v43 android.database.Cursor) = (r5v42 android.database.Cursor), (r5v44 android.database.Cursor) binds: [B:55:0x0177, B:42:0x0151] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01b6 A[Catch: SQLiteException -> 0x022a, all -> 0x0b13, TRY_LEAVE, TryCatch #13 {SQLiteException -> 0x022a, blocks: (B:63:0x01b0, B:65:0x01b6, B:69:0x01c6, B:70:0x01cb, B:71:0x01d5, B:72:0x01e5, B:74:0x01f4), top: B:460:0x01b0 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01c6 A[Catch: SQLiteException -> 0x022a, all -> 0x0b13, TRY_ENTER, TryCatch #13 {SQLiteException -> 0x022a, blocks: (B:63:0x01b0, B:65:0x01b6, B:69:0x01c6, B:70:0x01cb, B:71:0x01d5, B:72:0x01e5, B:74:0x01f4), top: B:460:0x01b0 }] */
    /* JADX WARN: Type inference failed for: r4v45, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r5v45, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v46 */
    /* JADX WARN: Type inference failed for: r5v47, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r5v5, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v8, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final java.util.List<com.google.android.gms.internal.measurement.zzfk> zzb(java.lang.String r66, java.util.List<com.google.android.gms.internal.measurement.zzfo> r67, java.util.List<com.google.android.gms.internal.measurement.zzgh> r68, java.lang.Long r69, java.lang.Long r70) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 2843
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzy.zzb(java.lang.String, java.util.List, java.util.List, java.lang.Long, java.lang.Long):java.util.List");
    }
}
