package com.google.android.gms.measurement.internal;

import android.database.sqlite.SQLiteDatabase;
import java.io.File;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
/* loaded from: classes.dex */
public final class zzaj {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0081 A[Catch: SQLiteException -> 0x00ec, TryCatch #3 {SQLiteException -> 0x00ec, blocks: (B:21:0x0046, B:23:0x0072, B:25:0x0081, B:27:0x0089, B:28:0x008c, B:29:0x00b9, B:31:0x00bc, B:33:0x00bf, B:35:0x00c7, B:36:0x00ce, B:37:0x00d1, B:39:0x00d7, B:42:0x00e8, B:43:0x00eb, B:22:0x006b), top: B:58:0x0046, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00bc A[Catch: SQLiteException -> 0x00ec, LOOP:1: B:31:0x00bc->B:36:0x00ce, LOOP_START, PHI: r12
      0x00bc: PHI (r12v1 int) = (r12v0 int), (r12v2 int) binds: [B:30:0x00ba, B:36:0x00ce] A[DONT_GENERATE, DONT_INLINE], TryCatch #3 {SQLiteException -> 0x00ec, blocks: (B:21:0x0046, B:23:0x0072, B:25:0x0081, B:27:0x0089, B:28:0x008c, B:29:0x00b9, B:31:0x00bc, B:33:0x00bf, B:35:0x00c7, B:36:0x00ce, B:37:0x00d1, B:39:0x00d7, B:42:0x00e8, B:43:0x00eb, B:22:0x006b), top: B:58:0x0046, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00d7 A[Catch: SQLiteException -> 0x00ec, TryCatch #3 {SQLiteException -> 0x00ec, blocks: (B:21:0x0046, B:23:0x0072, B:25:0x0081, B:27:0x0089, B:28:0x008c, B:29:0x00b9, B:31:0x00bc, B:33:0x00bf, B:35:0x00c7, B:36:0x00ce, B:37:0x00d1, B:39:0x00d7, B:42:0x00e8, B:43:0x00eb, B:22:0x006b), top: B:58:0x0046, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:67:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r13v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static void zza(com.google.android.gms.measurement.internal.zzem r14, android.database.sqlite.SQLiteDatabase r15, java.lang.String r16, java.lang.String r17, java.lang.String r18, java.lang.String[] r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 263
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzaj.zza(com.google.android.gms.measurement.internal.zzem, android.database.sqlite.SQLiteDatabase, java.lang.String, java.lang.String, java.lang.String, java.lang.String[]):void");
    }

    static void zzb(zzem zzemVar, SQLiteDatabase sQLiteDatabase) {
        if (zzemVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        File file = new File(sQLiteDatabase.getPath());
        if (!file.setReadable(false, false)) {
            zzemVar.zze().zza("Failed to turn off database read permission");
        }
        if (!file.setWritable(false, false)) {
            zzemVar.zze().zza("Failed to turn off database write permission");
        }
        if (!file.setReadable(true, true)) {
            zzemVar.zze().zza("Failed to turn on database read permission for owner");
        }
        if (file.setWritable(true, true)) {
            return;
        }
        zzemVar.zze().zza("Failed to turn on database write permission for owner");
    }
}
