package com.google.android.gms.tagmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.tagmanager.DataLayer;
import it.orsozoxi.android.orsonotes.R2;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import org.apache.http.cookie.ClientCookie;

/* loaded from: classes.dex */
final class zzat implements DataLayer.zzc {
    private static final String zzafx = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", "datalayer", "ID", "key", "value", ClientCookie.EXPIRES_ATTR);
    private final Executor zzafy;
    private zzax zzafz;
    private int zzaga;
    private final Context zzrm;
    private Clock zzsd;

    public zzat(Context context) {
        this(context, DefaultClock.getInstance(), "google_tagmanager.db", R2.dimen.notification_large_icon_width, com.google.android.gms.internal.gtm.zzdf.zzgp().zzr(com.google.android.gms.internal.gtm.zzdi.zzadg));
    }

    private zzat(Context context, Clock clock, String str, int i, Executor executor) {
        this.zzrm = context;
        this.zzsd = clock;
        this.zzaga = R2.dimen.notification_large_icon_width;
        this.zzafy = executor;
        this.zzafz = new zzax(this, this.zzrm, str);
    }

    @Override // com.google.android.gms.tagmanager.DataLayer.zzc
    public final void zza(List<DataLayer.zza> list, long j) {
        ArrayList arrayList = new ArrayList();
        for (DataLayer.zza zzaVar : list) {
            arrayList.add(new zzay(zzaVar.mKey, zzf(zzaVar.mValue)));
        }
        this.zzafy.execute(new zzau(this, arrayList, j));
    }

    @Override // com.google.android.gms.tagmanager.DataLayer.zzc
    public final void zza(zzaq zzaqVar) {
        this.zzafy.execute(new zzav(this, zzaqVar));
    }

    @Override // com.google.android.gms.tagmanager.DataLayer.zzc
    public final void zzas(String str) {
        this.zzafy.execute(new zzaw(this, str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<DataLayer.zza> zzht() {
        try {
            zzl(this.zzsd.currentTimeMillis());
            List<zzay> listZzhu = zzhu();
            ArrayList arrayList = new ArrayList();
            for (zzay zzayVar : listZzhu) {
                arrayList.add(new DataLayer.zza(zzayVar.zzagg, zza(zzayVar.zzagh)));
            }
            return arrayList;
        } finally {
            zzhw();
        }
    }

    private static Object zza(byte[] bArr) throws Throwable {
        ObjectInputStream objectInputStream;
        Throwable th;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
        } catch (IOException unused) {
            objectInputStream = null;
        } catch (ClassNotFoundException unused2) {
            objectInputStream = null;
        } catch (Throwable th2) {
            objectInputStream = null;
            th = th2;
        }
        try {
            Object object = objectInputStream.readObject();
            try {
                objectInputStream.close();
                byteArrayInputStream.close();
            } catch (IOException unused3) {
            }
            return object;
        } catch (IOException unused4) {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException unused5) {
                    return null;
                }
            }
            byteArrayInputStream.close();
            return null;
        } catch (ClassNotFoundException unused6) {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException unused7) {
                    return null;
                }
            }
            byteArrayInputStream.close();
            return null;
        } catch (Throwable th3) {
            th = th3;
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException unused8) {
                    throw th;
                }
            }
            byteArrayInputStream.close();
            throw th;
        }
    }

    private static byte[] zzf(Object obj) throws Throwable {
        ObjectOutputStream objectOutputStream;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream2 = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream.writeObject(obj);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    objectOutputStream.close();
                    byteArrayOutputStream.close();
                } catch (IOException unused) {
                }
                return byteArray;
            } catch (IOException unused2) {
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException unused3) {
                        return null;
                    }
                }
                byteArrayOutputStream.close();
                return null;
            } catch (Throwable th) {
                th = th;
                objectOutputStream2 = objectOutputStream;
                if (objectOutputStream2 != null) {
                    try {
                        objectOutputStream2.close();
                    } catch (IOException unused4) {
                        throw th;
                    }
                }
                byteArrayOutputStream.close();
                throw th;
            }
        } catch (IOException unused5) {
            objectOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0092 A[Catch: all -> 0x00d4, TryCatch #0 {all -> 0x00d4, blocks: (B:3:0x0001, B:5:0x0018, B:7:0x0046, B:10:0x004a, B:12:0x0052, B:13:0x006f, B:15:0x0075, B:17:0x0085, B:19:0x008f, B:18:0x008a, B:20:0x0092, B:22:0x009b, B:23:0x009f, B:25:0x00a5), top: B:36:0x0001, outer: #2, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009b A[Catch: all -> 0x00d4, TryCatch #0 {all -> 0x00d4, blocks: (B:3:0x0001, B:5:0x0018, B:7:0x0046, B:10:0x004a, B:12:0x0052, B:13:0x006f, B:15:0x0075, B:17:0x0085, B:19:0x008f, B:18:0x008a, B:20:0x0092, B:22:0x009b, B:23:0x009f, B:25:0x00a5), top: B:36:0x0001, outer: #2, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final synchronized void zzb(java.util.List<com.google.android.gms.tagmanager.zzay> r11, long r12) {
        /*
            r10 = this;
            monitor-enter(r10)
            com.google.android.gms.common.util.Clock r0 = r10.zzsd     // Catch: java.lang.Throwable -> Ld4
            long r0 = r0.currentTimeMillis()     // Catch: java.lang.Throwable -> Ld4
            r10.zzl(r0)     // Catch: java.lang.Throwable -> Ld4
            int r2 = r11.size()     // Catch: java.lang.Throwable -> Ld4
            int r3 = r10.zzhv()     // Catch: java.lang.Throwable -> Ld4
            int r4 = r10.zzaga     // Catch: java.lang.Throwable -> Ld4
            int r3 = r3 - r4
            int r3 = r3 + r2
            if (r3 <= 0) goto L92
            java.util.List r2 = r10.zzu(r3)     // Catch: java.lang.Throwable -> Ld4
            int r3 = r2.size()     // Catch: java.lang.Throwable -> Ld4
            r4 = 64
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld4
            r5.<init>(r4)     // Catch: java.lang.Throwable -> Ld4
            java.lang.String r4 = "DataLayer store full, deleting "
            r5.append(r4)     // Catch: java.lang.Throwable -> Ld4
            r5.append(r3)     // Catch: java.lang.Throwable -> Ld4
            java.lang.String r3 = " entries to make room."
            r5.append(r3)     // Catch: java.lang.Throwable -> Ld4
            java.lang.String r3 = r5.toString()     // Catch: java.lang.Throwable -> Ld4
            com.google.android.gms.tagmanager.zzdi.zzaw(r3)     // Catch: java.lang.Throwable -> Ld4
            r3 = 0
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch: java.lang.Throwable -> Ld4
            java.lang.Object[] r2 = r2.toArray(r4)     // Catch: java.lang.Throwable -> Ld4
            java.lang.String[] r2 = (java.lang.String[]) r2     // Catch: java.lang.Throwable -> Ld4
            if (r2 == 0) goto L92
            int r4 = r2.length     // Catch: java.lang.Throwable -> Ld4
            if (r4 != 0) goto L4a
            goto L92
        L4a:
            java.lang.String r4 = "Error opening database for deleteEntries."
            android.database.sqlite.SQLiteDatabase r4 = r10.zzau(r4)     // Catch: java.lang.Throwable -> Ld4
            if (r4 == 0) goto L92
            java.lang.String r5 = "%s in (%s)"
            r6 = 2
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch: java.lang.Throwable -> Ld4
            java.lang.String r7 = "ID"
            r6[r3] = r7     // Catch: java.lang.Throwable -> Ld4
            r3 = 1
            java.lang.String r7 = ","
            int r8 = r2.length     // Catch: java.lang.Throwable -> Ld4
            java.lang.String r9 = "?"
            java.util.List r8 = java.util.Collections.nCopies(r8, r9)     // Catch: java.lang.Throwable -> Ld4
            java.lang.String r7 = android.text.TextUtils.join(r7, r8)     // Catch: java.lang.Throwable -> Ld4
            r6[r3] = r7     // Catch: java.lang.Throwable -> Ld4
            java.lang.String r3 = java.lang.String.format(r5, r6)     // Catch: java.lang.Throwable -> Ld4
            java.lang.String r5 = "datalayer"
            r4.delete(r5, r3, r2)     // Catch: android.database.sqlite.SQLiteException -> L75 java.lang.Throwable -> Ld4
            goto L92
        L75:
            java.lang.String r3 = "Error deleting entries "
            java.lang.String r2 = java.util.Arrays.toString(r2)     // Catch: java.lang.Throwable -> Ld4
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch: java.lang.Throwable -> Ld4
            int r4 = r2.length()     // Catch: java.lang.Throwable -> Ld4
            if (r4 == 0) goto L8a
            java.lang.String r2 = r3.concat(r2)     // Catch: java.lang.Throwable -> Ld4
            goto L8f
        L8a:
            java.lang.String r2 = new java.lang.String     // Catch: java.lang.Throwable -> Ld4
            r2.<init>(r3)     // Catch: java.lang.Throwable -> Ld4
        L8f:
            com.google.android.gms.tagmanager.zzdi.zzac(r2)     // Catch: java.lang.Throwable -> Ld4
        L92:
            long r0 = r0 + r12
            java.lang.String r12 = "Error opening database for writeEntryToDatabase."
            android.database.sqlite.SQLiteDatabase r12 = r10.zzau(r12)     // Catch: java.lang.Throwable -> Ld4
            if (r12 == 0) goto Lcf
            java.util.Iterator r11 = r11.iterator()     // Catch: java.lang.Throwable -> Ld4
        L9f:
            boolean r13 = r11.hasNext()     // Catch: java.lang.Throwable -> Ld4
            if (r13 == 0) goto Lcf
            java.lang.Object r13 = r11.next()     // Catch: java.lang.Throwable -> Ld4
            com.google.android.gms.tagmanager.zzay r13 = (com.google.android.gms.tagmanager.zzay) r13     // Catch: java.lang.Throwable -> Ld4
            android.content.ContentValues r2 = new android.content.ContentValues     // Catch: java.lang.Throwable -> Ld4
            r2.<init>()     // Catch: java.lang.Throwable -> Ld4
            java.lang.String r3 = "expires"
            java.lang.Long r4 = java.lang.Long.valueOf(r0)     // Catch: java.lang.Throwable -> Ld4
            r2.put(r3, r4)     // Catch: java.lang.Throwable -> Ld4
            java.lang.String r3 = "key"
            java.lang.String r4 = r13.zzagg     // Catch: java.lang.Throwable -> Ld4
            r2.put(r3, r4)     // Catch: java.lang.Throwable -> Ld4
            java.lang.String r3 = "value"
            byte[] r13 = r13.zzagh     // Catch: java.lang.Throwable -> Ld4
            r2.put(r3, r13)     // Catch: java.lang.Throwable -> Ld4
            java.lang.String r13 = "datalayer"
            r3 = 0
            r12.insert(r13, r3, r2)     // Catch: java.lang.Throwable -> Ld4
            goto L9f
        Lcf:
            r10.zzhw()     // Catch: java.lang.Throwable -> Ld9
            monitor-exit(r10)
            return
        Ld4:
            r11 = move-exception
            r10.zzhw()     // Catch: java.lang.Throwable -> Ld9
            throw r11     // Catch: java.lang.Throwable -> Ld9
        Ld9:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzat.zzb(java.util.List, long):void");
    }

    private final List<zzay> zzhu() {
        SQLiteDatabase sQLiteDatabaseZzau = zzau("Error opening database for loadSerialized.");
        ArrayList arrayList = new ArrayList();
        if (sQLiteDatabaseZzau == null) {
            return arrayList;
        }
        Cursor cursorQuery = sQLiteDatabaseZzau.query("datalayer", new String[]{"key", "value"}, null, null, null, null, "ID", null);
        while (cursorQuery.moveToNext()) {
            try {
                arrayList.add(new zzay(cursorQuery.getString(0), cursorQuery.getBlob(1)));
            } finally {
                cursorQuery.close();
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzat(String str) {
        SQLiteDatabase sQLiteDatabaseZzau = zzau("Error opening database for clearKeysWithPrefix.");
        try {
            if (sQLiteDatabaseZzau == null) {
                return;
            }
            int iDelete = sQLiteDatabaseZzau.delete("datalayer", "key = ? OR key LIKE ?", new String[]{str, String.valueOf(str).concat(".%")});
            StringBuilder sb = new StringBuilder(25);
            sb.append("Cleared ");
            sb.append(iDelete);
            sb.append(" items");
            zzdi.zzab(sb.toString());
        } catch (SQLiteException e) {
            String strValueOf = String.valueOf(e);
            StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 44 + String.valueOf(strValueOf).length());
            sb2.append("Error deleting entries with key prefix: ");
            sb2.append(str);
            sb2.append(" (");
            sb2.append(strValueOf);
            sb2.append(").");
            zzdi.zzac(sb2.toString());
        } finally {
            zzhw();
        }
    }

    private final void zzl(long j) {
        SQLiteDatabase sQLiteDatabaseZzau = zzau("Error opening database for deleteOlderThan.");
        if (sQLiteDatabaseZzau == null) {
            return;
        }
        try {
            int iDelete = sQLiteDatabaseZzau.delete("datalayer", "expires <= ?", new String[]{Long.toString(j)});
            StringBuilder sb = new StringBuilder(33);
            sb.append("Deleted ");
            sb.append(iDelete);
            sb.append(" expired items");
            zzdi.zzab(sb.toString());
        } catch (SQLiteException unused) {
            zzdi.zzac("Error deleting old entries.");
        }
    }

    private final List<String> zzu(int i) {
        ArrayList arrayList = new ArrayList();
        if (i <= 0) {
            zzdi.zzac("Invalid maxEntries specified. Skipping.");
            return arrayList;
        }
        SQLiteDatabase sQLiteDatabaseZzau = zzau("Error opening database for peekEntryIds.");
        if (sQLiteDatabaseZzau == null) {
            return arrayList;
        }
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = sQLiteDatabaseZzau.query("datalayer", new String[]{"ID"}, null, null, null, null, String.format("%s ASC", "ID"), Integer.toString(i));
                if (cursorQuery.moveToFirst()) {
                    do {
                        arrayList.add(String.valueOf(cursorQuery.getLong(0)));
                    } while (cursorQuery.moveToNext());
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (SQLiteException e) {
                String strValueOf = String.valueOf(e.getMessage());
                zzdi.zzac(strValueOf.length() != 0 ? "Error in peekEntries fetching entryIds: ".concat(strValueOf) : new String("Error in peekEntries fetching entryIds: "));
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
            return arrayList;
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    private final int zzhv() {
        SQLiteDatabase sQLiteDatabaseZzau = zzau("Error opening database for getNumStoredEntries.");
        if (sQLiteDatabaseZzau == null) {
            return 0;
        }
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = sQLiteDatabaseZzau.rawQuery("SELECT COUNT(*) from datalayer", null);
                i = cursorRawQuery.moveToFirst() ? (int) cursorRawQuery.getLong(0) : 0;
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
            } catch (SQLiteException unused) {
                zzdi.zzac("Error getting numStoredEntries");
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
            }
            return i;
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    private final SQLiteDatabase zzau(String str) {
        try {
            return this.zzafz.getWritableDatabase();
        } catch (SQLiteException unused) {
            zzdi.zzac(str);
            return null;
        }
    }

    private final void zzhw() {
        try {
            this.zzafz.close();
        } catch (SQLiteException unused) {
        }
    }
}
