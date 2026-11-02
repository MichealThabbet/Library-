package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import it.piapostolos.android.pinotes.R2;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
final class zzeb implements zzcb {
    private static final String zzxj = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", "gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time");
    private final zzed zzaie;
    private volatile zzbe zzaif;
    private final zzcc zzaig;
    private final String zzaih;
    private long zzaii;
    private final int zzaij;
    private final Context zzrm;
    private Clock zzsd;

    zzeb(zzcc zzccVar, Context context) {
        this(zzccVar, context, "gtm_urls.db", R2.dimen.notification_large_icon_width);
    }

    private zzeb(zzcc zzccVar, Context context, String str, int i) {
        this.zzrm = context.getApplicationContext();
        this.zzaih = str;
        this.zzaig = zzccVar;
        this.zzsd = DefaultClock.getInstance();
        this.zzaie = new zzed(this, this.zzrm, this.zzaih);
        this.zzaif = new zzfu(this.zzrm, new zzec(this));
        this.zzaii = 0L;
        this.zzaij = R2.dimen.notification_large_icon_width;
    }

    @Override // com.google.android.gms.tagmanager.zzcb
    public final void zzb(long j, String str) {
        long jCurrentTimeMillis = this.zzsd.currentTimeMillis();
        if (jCurrentTimeMillis > this.zzaii + 86400000) {
            this.zzaii = jCurrentTimeMillis;
            SQLiteDatabase sQLiteDatabaseZzau = zzau("Error opening database for deleteStaleHits.");
            if (sQLiteDatabaseZzau != null) {
                sQLiteDatabaseZzau.delete("gtm_hits", "HIT_TIME < ?", new String[]{Long.toString(this.zzsd.currentTimeMillis() - 2592000000L)});
                this.zzaig.zze(zziv() == 0);
            }
        }
        int iZziv = (zziv() - this.zzaij) + 1;
        if (iZziv > 0) {
            List<String> listZzz = zzz(iZziv);
            int size = listZzz.size();
            StringBuilder sb = new StringBuilder(51);
            sb.append("Store full, deleting ");
            sb.append(size);
            sb.append(" hits to make room.");
            zzdi.zzab(sb.toString());
            zza((String[]) listZzz.toArray(new String[0]));
        }
        SQLiteDatabase sQLiteDatabaseZzau2 = zzau("Error opening database for putHit");
        if (sQLiteDatabaseZzau2 != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_time", Long.valueOf(j));
            contentValues.put("hit_url", str);
            contentValues.put("hit_first_send_time", (Integer) 0);
            try {
                sQLiteDatabaseZzau2.insert("gtm_hits", null, contentValues);
                this.zzaig.zze(false);
            } catch (SQLiteException unused) {
                zzdi.zzac("Error storing hit");
            }
        }
    }

    private final List<String> zzz(int i) {
        ArrayList arrayList = new ArrayList();
        if (i <= 0) {
            zzdi.zzac("Invalid maxHits specified. Skipping");
            return arrayList;
        }
        SQLiteDatabase sQLiteDatabaseZzau = zzau("Error opening database for peekHitIds.");
        if (sQLiteDatabaseZzau == null) {
            return arrayList;
        }
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = sQLiteDatabaseZzau.query("gtm_hits", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", "hit_id"), Integer.toString(i));
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
                zzdi.zzac(strValueOf.length() != 0 ? "Error in peekHits fetching hitIds: ".concat(strValueOf) : new String("Error in peekHits fetching hitIds: "));
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

    /* JADX WARN: Removed duplicated region for block: B:60:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x015c A[Catch: all -> 0x016f, TryCatch #6 {all -> 0x016f, blocks: (B:72:0x014c, B:74:0x015c, B:76:0x0166, B:75:0x0161), top: B:88:0x014c }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0161 A[Catch: all -> 0x016f, TryCatch #6 {all -> 0x016f, blocks: (B:72:0x014c, B:74:0x015c, B:76:0x0166, B:75:0x0161), top: B:88:0x014c }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0172  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final java.util.List<com.google.android.gms.tagmanager.zzbw> zzaa(int r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 374
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzeb.zzaa(int):java.util.List");
    }

    private final void zza(String[] strArr) {
        SQLiteDatabase sQLiteDatabaseZzau;
        if (strArr == null || strArr.length == 0 || (sQLiteDatabaseZzau = zzau("Error opening database for deleteHits.")) == null) {
            return;
        }
        boolean z = true;
        try {
            sQLiteDatabaseZzau.delete("gtm_hits", String.format("HIT_ID in (%s)", TextUtils.join(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR, Collections.nCopies(strArr.length, "?"))), strArr);
            zzcc zzccVar = this.zzaig;
            if (zziv() != 0) {
                z = false;
            }
            zzccVar.zze(z);
        } catch (SQLiteException unused) {
            zzdi.zzac("Error deleting hits");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zze(long j) {
        zza(new String[]{String.valueOf(j)});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzb(long j, long j2) {
        SQLiteDatabase sQLiteDatabaseZzau = zzau("Error opening database for getNumStoredHits.");
        if (sQLiteDatabaseZzau == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_first_send_time", Long.valueOf(j2));
        try {
            sQLiteDatabaseZzau.update("gtm_hits", contentValues, "hit_id=?", new String[]{String.valueOf(j)});
        } catch (SQLiteException unused) {
            StringBuilder sb = new StringBuilder(69);
            sb.append("Error setting HIT_FIRST_DISPATCH_TIME for hitId: ");
            sb.append(j);
            zzdi.zzac(sb.toString());
            zze(j);
        }
    }

    private final int zziv() {
        SQLiteDatabase sQLiteDatabaseZzau = zzau("Error opening database for getNumStoredHits.");
        if (sQLiteDatabaseZzau == null) {
            return 0;
        }
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = sQLiteDatabaseZzau.rawQuery("SELECT COUNT(*) from gtm_hits", null);
                i = cursorRawQuery.moveToFirst() ? (int) cursorRawQuery.getLong(0) : 0;
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
            } catch (SQLiteException unused) {
                zzdi.zzac("Error getting numStoredHits");
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

    private final int zziw() {
        SQLiteDatabase sQLiteDatabaseZzau = zzau("Error opening database for getNumStoredHits.");
        int count = 0;
        if (sQLiteDatabaseZzau == null) {
            return 0;
        }
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = sQLiteDatabaseZzau.query("gtm_hits", new String[]{"hit_id", "hit_first_send_time"}, "hit_first_send_time=0", null, null, null, null);
                count = cursorQuery.getCount();
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (SQLiteException unused) {
                zzdi.zzac("Error getting num untried hits");
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
            return count;
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    @Override // com.google.android.gms.tagmanager.zzcb
    public final void dispatch() throws Throwable {
        zzdi.zzab("GTM Dispatch running...");
        if (this.zzaif.zzhy()) {
            List<zzbw> listZzaa = zzaa(40);
            if (listZzaa.isEmpty()) {
                zzdi.zzab("...nothing to dispatch");
                this.zzaig.zze(true);
            } else {
                this.zzaif.zzd(listZzaa);
                if (zziw() > 0) {
                    zzfn.zzjq().dispatch();
                }
            }
        }
    }

    private final SQLiteDatabase zzau(String str) {
        try {
            return this.zzaie.getWritableDatabase();
        } catch (SQLiteException unused) {
            zzdi.zzac(str);
            return null;
        }
    }
}
