package com.google.android.gms.internal.gtm;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.HttpUtils;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
final class zzay extends zzan implements Closeable {
    private static final String zzxj = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", "hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id");
    private static final String zzxk = String.format("SELECT MAX(%s) FROM %s WHERE 1;", "hit_time", "hits2");
    private final zzaz zzxl;
    private final zzcv zzxm;
    private final zzcv zzxn;

    zzay(zzap zzapVar) {
        super(zzapVar);
        this.zzxm = new zzcv(zzcn());
        this.zzxn = new zzcv(zzcn());
        this.zzxl = new zzaz(this, zzapVar.getContext(), "google_analytics_v4.db");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String zzdt() {
        return "google_analytics_v4.db";
    }

    @Override // com.google.android.gms.internal.gtm.zzan
    protected final void zzaw() {
    }

    public final void beginTransaction() {
        zzdb();
        getWritableDatabase().beginTransaction();
    }

    public final void setTransactionSuccessful() {
        zzdb();
        getWritableDatabase().setTransactionSuccessful();
    }

    public final void endTransaction() {
        zzdb();
        getWritableDatabase().endTransaction();
    }

    public final void zzc(zzcd zzcdVar) {
        String strZzeu;
        Preconditions.checkNotNull(zzcdVar);
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        Preconditions.checkNotNull(zzcdVar);
        Uri.Builder builder = new Uri.Builder();
        for (Map.Entry<String, String> entry : zzcdVar.zzdm().entrySet()) {
            String key = entry.getKey();
            if (!"ht".equals(key) && !"qt".equals(key) && !"AppUID".equals(key)) {
                builder.appendQueryParameter(key, entry.getValue());
            }
        }
        String encodedQuery = builder.build().getEncodedQuery();
        if (encodedQuery == null) {
            encodedQuery = "";
        }
        if (encodedQuery.length() > 8192) {
            zzco().zza(zzcdVar, "Hit length exceeds the maximum allowed size");
            return;
        }
        int iIntValue = zzby.zzze.get().intValue();
        long jZzdl = zzdl();
        if (jZzdl > iIntValue - 1) {
            List<Long> listZzc = zzc((jZzdl - iIntValue) + 1);
            zzd("Store full, deleting hits to make room, count", Integer.valueOf(listZzc.size()));
            zza(listZzc);
        }
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_string", encodedQuery);
        contentValues.put("hit_time", Long.valueOf(zzcdVar.zzfh()));
        contentValues.put("hit_app_id", Integer.valueOf(zzcdVar.zzff()));
        if (zzcdVar.zzfj()) {
            strZzeu = zzbq.zzet();
        } else {
            strZzeu = zzbq.zzeu();
        }
        contentValues.put("hit_url", strZzeu);
        try {
            long jInsert = writableDatabase.insert("hits2", null, contentValues);
            if (jInsert == -1) {
                zzu("Failed to insert a hit (got -1)");
            } else {
                zzb("Hit saved to database. db-id, hit", Long.valueOf(jInsert), zzcdVar);
            }
        } catch (SQLiteException e) {
            zze("Error storing a hit", e);
        }
    }

    private final long zzdl() {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        return zza("SELECT COUNT(*) FROM hits2", (String[]) null);
    }

    final boolean isEmpty() {
        return zzdl() == 0;
    }

    private final List<Long> zzc(long j) {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        if (j <= 0) {
            return Collections.emptyList();
        }
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = writableDatabase.query("hits2", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", "hit_id"), Long.toString(j));
                if (cursorQuery.moveToFirst()) {
                    do {
                        arrayList.add(Long.valueOf(cursorQuery.getLong(0)));
                    } while (cursorQuery.moveToNext());
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (SQLiteException e) {
                zzd("Error selecting hit ids", e);
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

    public final List<zzcd> zzd(long j) {
        Preconditions.checkArgument(j >= 0);
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = getWritableDatabase().query("hits2", new String[]{"hit_id", "hit_time", "hit_string", "hit_url", "hit_app_id"}, null, null, null, null, String.format("%s ASC", "hit_id"), Long.toString(j));
                ArrayList arrayList = new ArrayList();
                if (cursorQuery.moveToFirst()) {
                    do {
                        arrayList.add(new zzcd(this, zzv(cursorQuery.getString(2)), cursorQuery.getLong(1), zzcz.zzaj(cursorQuery.getString(3)), cursorQuery.getLong(0), cursorQuery.getInt(4)));
                    } while (cursorQuery.moveToNext());
                }
                return arrayList;
            } catch (SQLiteException e) {
                zze("Error loading hits from the database", e);
                throw e;
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public final void zza(List<Long> list) {
        Preconditions.checkNotNull(list);
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        if (list.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder("hit_id");
        sb.append(" in (");
        for (int i = 0; i < list.size(); i++) {
            Long l = list.get(i);
            if (l == null || l.longValue() == 0) {
                throw new SQLiteException("Invalid hit id");
            }
            if (i > 0) {
                sb.append(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
            }
            sb.append(l);
        }
        sb.append(")");
        String string = sb.toString();
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            zza("Deleting dispatched hits. count", Integer.valueOf(list.size()));
            int iDelete = writableDatabase.delete("hits2", string, null);
            if (iDelete != list.size()) {
                zzb("Deleted fewer hits then expected", Integer.valueOf(list.size()), Integer.valueOf(iDelete), string);
            }
        } catch (SQLiteException e) {
            zze("Error deleting hits", e);
            throw e;
        }
    }

    public final void zze(long j) {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(Long.valueOf(j));
        zza("Deleting hit, id", Long.valueOf(j));
        zza(arrayList);
    }

    public final int zzdr() {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        if (!this.zzxm.zzj(86400000L)) {
            return 0;
        }
        this.zzxm.start();
        zzq("Deleting stale hits (if any)");
        int iDelete = getWritableDatabase().delete("hits2", "hit_time < ?", new String[]{Long.toString(zzcn().currentTimeMillis() - 2592000000L)});
        zza("Deleted stale hits, count", Integer.valueOf(iDelete));
        return iDelete;
    }

    public final long zzds() {
        com.google.android.gms.analytics.zzk.zzav();
        zzdb();
        return zza(zzxk, (String[]) null, 0L);
    }

    public final long zza(long j, String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzdb();
        com.google.android.gms.analytics.zzk.zzav();
        return zza("SELECT hits_count FROM properties WHERE app_uid=? AND cid=? AND tid=?", new String[]{String.valueOf(j), str, str2}, 0L);
    }

    public final List<zzas> zzf(long j) {
        zzdb();
        com.google.android.gms.analytics.zzk.zzav();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursorQuery = null;
        try {
            try {
                String[] strArr = {"cid", "tid", "adid", "hits_count", NativeProtocol.WEB_DIALOG_PARAMS};
                int iIntValue = zzby.zzzg.get().intValue();
                cursorQuery = writableDatabase.query("properties", strArr, "app_uid=?", new String[]{"0"}, null, null, null, String.valueOf(iIntValue));
                ArrayList arrayList = new ArrayList();
                if (cursorQuery.moveToFirst()) {
                    do {
                        String string = cursorQuery.getString(0);
                        String string2 = cursorQuery.getString(1);
                        boolean z = cursorQuery.getInt(2) != 0;
                        long j2 = cursorQuery.getInt(3);
                        Map<String, String> mapZzw = zzw(cursorQuery.getString(4));
                        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
                            zzc("Read property with empty client id or tracker id", string, string2);
                        } else {
                            arrayList.add(new zzas(0L, string, string2, z, j2, mapZzw));
                        }
                    } while (cursorQuery.moveToNext());
                }
                if (arrayList.size() >= iIntValue) {
                    zzt("Sending hits to too many properties. Campaign report might be incorrect");
                }
                return arrayList;
            } catch (SQLiteException e) {
                zze("Error loading hits from the database", e);
                throw e;
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        try {
            this.zzxl.close();
        } catch (SQLiteException e) {
            zze("Sql error closing database", e);
        } catch (IllegalStateException e2) {
            zze("Error closing database", e2);
        }
    }

    private final long zza(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            try {
                Cursor cursorRawQuery = getWritableDatabase().rawQuery(str, null);
                if (cursorRawQuery.moveToFirst()) {
                    long j = cursorRawQuery.getLong(0);
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    return j;
                }
                throw new SQLiteException("Database returned empty set");
            } catch (SQLiteException e) {
                zzd("Database error", str, e);
                throw e;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    private final long zza(String str, String[] strArr, long j) {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = getWritableDatabase().rawQuery(str, strArr);
                if (cursorRawQuery.moveToFirst()) {
                    return cursorRawQuery.getLong(0);
                }
                if (cursorRawQuery == null) {
                    return 0L;
                }
                cursorRawQuery.close();
                return 0L;
            } catch (SQLiteException e) {
                zzd("Database error", str, e);
                throw e;
            }
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    private final Map<String, String> zzv(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            if (!str.startsWith("?")) {
                String strValueOf = String.valueOf(str);
                str = strValueOf.length() != 0 ? "?".concat(strValueOf) : new String("?");
            }
            return HttpUtils.parse(new URI(str), "UTF-8");
        } catch (URISyntaxException e) {
            zze("Error parsing hit parameters", e);
            return new HashMap(0);
        }
    }

    private final Map<String, String> zzw(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            String strValueOf = String.valueOf(str);
            return HttpUtils.parse(new URI(strValueOf.length() != 0 ? "?".concat(strValueOf) : new String("?")), "UTF-8");
        } catch (URISyntaxException e) {
            zze("Error parsing property parameters", e);
            return new HashMap(0);
        }
    }

    final SQLiteDatabase getWritableDatabase() {
        try {
            return this.zzxl.getWritableDatabase();
        } catch (SQLiteException e) {
            zzd("Error opening database", e);
            throw e;
        }
    }
}
