package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzov;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.fortuna.ical4j.model.property.RequestStatus;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
final class zzai extends zzke {
    private static final String[] zza = {"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_bundled_day", "ALTER TABLE events ADD COLUMN last_bundled_day INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;", "current_session_count", "ALTER TABLE events ADD COLUMN current_session_count INTEGER;"};
    private static final String[] zzb = {"origin", "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    private static final String[] zzc = {"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;", "ssaid_reporting_enabled", "ALTER TABLE apps ADD COLUMN ssaid_reporting_enabled INTEGER;", "admob_app_id", "ALTER TABLE apps ADD COLUMN admob_app_id TEXT;", "linked_admob_app_id", "ALTER TABLE apps ADD COLUMN linked_admob_app_id TEXT;", "dynamite_version", "ALTER TABLE apps ADD COLUMN dynamite_version INTEGER;", "safelisted_events", "ALTER TABLE apps ADD COLUMN safelisted_events TEXT;", "ga_app_id", "ALTER TABLE apps ADD COLUMN ga_app_id TEXT;", "config_last_modified_time", "ALTER TABLE apps ADD COLUMN config_last_modified_time TEXT;"};
    private static final String[] zzd = {"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    private static final String[] zze = {"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;", "retry_count", "ALTER TABLE queue ADD COLUMN retry_count INTEGER;"};
    private static final String[] zzg = {"session_scoped", "ALTER TABLE event_filters ADD COLUMN session_scoped BOOLEAN;"};
    private static final String[] zzh = {"session_scoped", "ALTER TABLE property_filters ADD COLUMN session_scoped BOOLEAN;"};
    private static final String[] zzi = {"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final zzah zzj;
    private final zzka zzk;

    zzai(zzkn zzknVar) {
        super(zzknVar);
        this.zzk = new zzka(this.zzs.zzay());
        this.zzs.zzc();
        this.zzj = new zzah(this, this.zzs.zzax(), "google_app_measurement.db");
    }

    static final void zzX(ContentValues contentValues, String str, Object obj) {
        Preconditions.checkNotEmpty("value");
        Preconditions.checkNotNull(obj);
        if (obj instanceof String) {
            contentValues.put("value", (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put("value", (Long) obj);
        } else {
            if (!(obj instanceof Double)) {
                throw new IllegalArgumentException("Invalid value type");
            }
            contentValues.put("value", (Double) obj);
        }
    }

    private final long zzab(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            try {
                Cursor cursorRawQuery = zze().rawQuery(str, strArr);
                if (!cursorRawQuery.moveToFirst()) {
                    throw new SQLiteException("Database returned empty set");
                }
                long j = cursorRawQuery.getLong(0);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return j;
            } catch (SQLiteException e) {
                this.zzs.zzau().zzb().zzc("Database error", str, e);
                throw e;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    private final long zzac(String str, String[] strArr, long j) {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = zze().rawQuery(str, strArr);
                if (cursorRawQuery.moveToFirst()) {
                    return cursorRawQuery.getLong(0);
                }
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return j;
            } catch (SQLiteException e) {
                this.zzs.zzau().zzb().zzc("Database error", str, e);
                throw e;
            }
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    final void zzA() {
        zzg();
        zzZ();
        if (zzM()) {
            long jZza = this.zzf.zzn().zza.zza();
            long jElapsedRealtime = this.zzs.zzay().elapsedRealtime();
            long jAbs = Math.abs(jElapsedRealtime - jZza);
            this.zzs.zzc();
            if (jAbs > zzea.zzx.zzb(null).longValue()) {
                this.zzf.zzn().zza.zzb(jElapsedRealtime);
                zzg();
                zzZ();
                if (zzM()) {
                    SQLiteDatabase sQLiteDatabaseZze = zze();
                    this.zzs.zzc();
                    int iDelete = sQLiteDatabaseZze.delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(this.zzs.zzay().currentTimeMillis()), String.valueOf(zzae.zzA())});
                    if (iDelete > 0) {
                        this.zzs.zzau().zzk().zzb("Deleted stale rows. rowsDeleted", Integer.valueOf(iDelete));
                    }
                }
            }
        }
    }

    final void zzB(List<Long> list) throws SQLException {
        zzg();
        zzZ();
        Preconditions.checkNotNull(list);
        Preconditions.checkNotZero(list.size());
        if (zzM()) {
            String strJoin = TextUtils.join(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR, list);
            StringBuilder sb = new StringBuilder(String.valueOf(strJoin).length() + 2);
            sb.append("(");
            sb.append(strJoin);
            sb.append(")");
            String string = sb.toString();
            StringBuilder sb2 = new StringBuilder(String.valueOf(string).length() + 80);
            sb2.append("SELECT COUNT(1) FROM queue WHERE rowid IN ");
            sb2.append(string);
            sb2.append(" AND retry_count =  2147483647 LIMIT 1");
            if (zzab(sb2.toString(), null) > 0) {
                this.zzs.zzau().zze().zza("The number of upload retries exceeds the limit. Will remain unchanged.");
            }
            try {
                SQLiteDatabase sQLiteDatabaseZze = zze();
                StringBuilder sb3 = new StringBuilder(String.valueOf(string).length() + 127);
                sb3.append("UPDATE queue SET retry_count = IFNULL(retry_count, 0) + 1 WHERE rowid IN ");
                sb3.append(string);
                sb3.append(" AND (retry_count IS NULL OR retry_count < ");
                sb3.append(Integer.MAX_VALUE);
                sb3.append(")");
                sQLiteDatabaseZze.execSQL(sb3.toString());
            } catch (SQLiteException e) {
                this.zzs.zzau().zzb().zzb("Error incrementing retry count. error", e);
            }
        }
    }

    final Object zzC(Cursor cursor, int i) {
        int type = cursor.getType(i);
        if (type == 0) {
            this.zzs.zzau().zzb().zza("Loaded invalid null value from database");
            return null;
        }
        if (type == 1) {
            return Long.valueOf(cursor.getLong(i));
        }
        if (type == 2) {
            return Double.valueOf(cursor.getDouble(i));
        }
        if (type == 3) {
            return cursor.getString(i);
        }
        if (type != 4) {
            this.zzs.zzau().zzb().zzb("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
            return null;
        }
        this.zzs.zzau().zzb().zza("Loaded invalid blob type value, ignoring it");
        return null;
    }

    public final long zzD() {
        return zzac("select max(bundle_end_timestamp) from queue", null, 0L);
    }

    protected final long zzE(String str, String str2) {
        long jZzac;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty("first_open_count");
        zzg();
        zzZ();
        SQLiteDatabase sQLiteDatabaseZze = zze();
        sQLiteDatabaseZze.beginTransaction();
        long j = 0;
        try {
            try {
                StringBuilder sb = new StringBuilder(48);
                sb.append("select ");
                sb.append("first_open_count");
                sb.append(" from app2 where app_id=?");
                jZzac = zzac(sb.toString(), new String[]{str}, -1L);
                if (jZzac == -1) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("app_id", str);
                    contentValues.put("first_open_count", (Integer) 0);
                    contentValues.put("previous_install_count", (Integer) 0);
                    if (sQLiteDatabaseZze.insertWithOnConflict("app2", null, contentValues, 5) == -1) {
                        this.zzs.zzau().zzb().zzc("Failed to insert column (got -1). appId", zzem.zzl(str), "first_open_count");
                        return -1L;
                    }
                    jZzac = 0;
                }
            } catch (SQLiteException e) {
                e = e;
            }
            try {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("app_id", str);
                contentValues2.put("first_open_count", Long.valueOf(1 + jZzac));
                if (sQLiteDatabaseZze.update("app2", contentValues2, "app_id = ?", new String[]{str}) == 0) {
                    this.zzs.zzau().zzb().zzc("Failed to update column (got 0). appId", zzem.zzl(str), "first_open_count");
                    return -1L;
                }
                sQLiteDatabaseZze.setTransactionSuccessful();
                return jZzac;
            } catch (SQLiteException e2) {
                e = e2;
                j = jZzac;
                this.zzs.zzau().zzb().zzd("Error inserting column. appId", zzem.zzl(str), "first_open_count", e);
                sQLiteDatabaseZze.endTransaction();
                return j;
            }
        } finally {
            sQLiteDatabaseZze.endTransaction();
        }
    }

    public final long zzF() {
        return zzac("select max(timestamp) from raw_events", null, 0L);
    }

    public final boolean zzG() {
        return zzab("select count(1) > 0 from raw_events", null) != 0;
    }

    public final boolean zzH() {
        return zzab("select count(1) > 0 from raw_events where realtime = 1", null) != 0;
    }

    public final long zzI(String str) {
        Preconditions.checkNotEmpty(str);
        return zzac("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0L);
    }

    public final boolean zzJ(String str, Long l, long j, com.google.android.gms.internal.measurement.zzfo zzfoVar) {
        zzg();
        zzZ();
        Preconditions.checkNotNull(zzfoVar);
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(l);
        byte[] bArrZzbp = zzfoVar.zzbp();
        this.zzs.zzau().zzk().zzc("Saving complex main event, appId, data size", this.zzs.zzm().zzc(str), Integer.valueOf(bArrZzbp.length));
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("event_id", l);
        contentValues.put("children_to_process", Long.valueOf(j));
        contentValues.put("main_event", bArrZzbp);
        try {
            if (zze().insertWithOnConflict("main_event_params", null, contentValues, 5) != -1) {
                return true;
            }
            this.zzs.zzau().zzb().zzb("Failed to insert complex main event (got -1). appId", zzem.zzl(str));
            return false;
        } catch (SQLiteException e) {
            this.zzs.zzau().zzb().zzc("Error storing complex main event. appId", zzem.zzl(str), e);
            return false;
        }
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x00dc: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:47:0x00dc */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.os.Bundle zzK(java.lang.String r8) throws java.lang.Throwable {
        /*
            r7 = this;
            r7.zzg()
            r7.zzZ()
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r7.zze()     // Catch: java.lang.Throwable -> Lc2 android.database.sqlite.SQLiteException -> Lc4
            r2 = 1
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch: java.lang.Throwable -> Lc2 android.database.sqlite.SQLiteException -> Lc4
            r3 = 0
            r2[r3] = r8     // Catch: java.lang.Throwable -> Lc2 android.database.sqlite.SQLiteException -> Lc4
            java.lang.String r4 = "select parameters from default_event_params where app_id=?"
            android.database.Cursor r1 = r1.rawQuery(r4, r2)     // Catch: java.lang.Throwable -> Lc2 android.database.sqlite.SQLiteException -> Lc4
            boolean r2 = r1.moveToFirst()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            if (r2 != 0) goto L32
            com.google.android.gms.measurement.internal.zzfu r8 = r7.zzs     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            com.google.android.gms.measurement.internal.zzem r8 = r8.zzau()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            com.google.android.gms.measurement.internal.zzek r8 = r8.zzk()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            java.lang.String r2 = "Default event parameters not found"
            r8.zza(r2)     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            if (r1 == 0) goto L31
            r1.close()
        L31:
            return r0
        L32:
            byte[] r2 = r1.getBlob(r3)     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            com.google.android.gms.internal.measurement.zzfn r3 = com.google.android.gms.internal.measurement.zzfo.zzk()     // Catch: java.io.IOException -> La6 android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            com.google.android.gms.internal.measurement.zzlh r2 = com.google.android.gms.measurement.internal.zzkp.zzt(r3, r2)     // Catch: java.io.IOException -> La6 android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            com.google.android.gms.internal.measurement.zzfn r2 = (com.google.android.gms.internal.measurement.zzfn) r2     // Catch: java.io.IOException -> La6 android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            com.google.android.gms.internal.measurement.zzkd r2 = r2.zzaA()     // Catch: java.io.IOException -> La6 android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            com.google.android.gms.internal.measurement.zzfo r2 = (com.google.android.gms.internal.measurement.zzfo) r2     // Catch: java.io.IOException -> La6 android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            com.google.android.gms.measurement.internal.zzkn r8 = r7.zzf     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            r8.zzm()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            java.util.List r8 = r2.zza()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            android.os.Bundle r2 = new android.os.Bundle     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            r2.<init>()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            java.util.Iterator r8 = r8.iterator()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
        L58:
            boolean r3 = r8.hasNext()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            if (r3 == 0) goto La0
            java.lang.Object r3 = r8.next()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            com.google.android.gms.internal.measurement.zzfs r3 = (com.google.android.gms.internal.measurement.zzfs) r3     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            java.lang.String r4 = r3.zzb()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            boolean r5 = r3.zzi()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            if (r5 == 0) goto L76
            double r5 = r3.zzj()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            r2.putDouble(r4, r5)     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            goto L58
        L76:
            boolean r5 = r3.zzg()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            if (r5 == 0) goto L84
            float r3 = r3.zzh()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            r2.putFloat(r4, r3)     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            goto L58
        L84:
            boolean r5 = r3.zzc()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            if (r5 == 0) goto L92
            java.lang.String r3 = r3.zzd()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            r2.putString(r4, r3)     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            goto L58
        L92:
            boolean r5 = r3.zze()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            if (r5 == 0) goto L58
            long r5 = r3.zzf()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            r2.putLong(r4, r5)     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            goto L58
        La0:
            if (r1 == 0) goto La5
            r1.close()
        La5:
            return r2
        La6:
            r2 = move-exception
            com.google.android.gms.measurement.internal.zzfu r3 = r7.zzs     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            com.google.android.gms.measurement.internal.zzem r3 = r3.zzau()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            com.google.android.gms.measurement.internal.zzek r3 = r3.zzb()     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            java.lang.String r4 = "Failed to retrieve default event parameters. appId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzem.zzl(r8)     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            r3.zzc(r4, r8, r2)     // Catch: android.database.sqlite.SQLiteException -> Lc0 java.lang.Throwable -> Ldb
            if (r1 == 0) goto Lbf
            r1.close()
        Lbf:
            return r0
        Lc0:
            r8 = move-exception
            goto Lc6
        Lc2:
            r8 = move-exception
            goto Ldd
        Lc4:
            r8 = move-exception
            r1 = r0
        Lc6:
            com.google.android.gms.measurement.internal.zzfu r2 = r7.zzs     // Catch: java.lang.Throwable -> Ldb
            com.google.android.gms.measurement.internal.zzem r2 = r2.zzau()     // Catch: java.lang.Throwable -> Ldb
            com.google.android.gms.measurement.internal.zzek r2 = r2.zzb()     // Catch: java.lang.Throwable -> Ldb
            java.lang.String r3 = "Error selecting default event parameters"
            r2.zzb(r3, r8)     // Catch: java.lang.Throwable -> Ldb
            if (r1 == 0) goto Lda
            r1.close()
        Lda:
            return r0
        Ldb:
            r8 = move-exception
            r0 = r1
        Ldd:
            if (r0 == 0) goto Le2
            r0.close()
        Le2:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzai.zzK(java.lang.String):android.os.Bundle");
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0343, code lost:
    
        r0 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0344, code lost:
    
        r11.put("session_scoped", r0);
        r11.put("data", r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0358, code lost:
    
        if (zze().insertWithOnConflict("property_filters", null, r11, 5) != (-1)) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x035a, code lost:
    
        r23.zzs.zzau().zzb().zzb("Failed to insert property filter (got -1). appId", com.google.android.gms.measurement.internal.zzem.zzl(r24));
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x036e, code lost:
    
        r0 = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0372, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0373, code lost:
    
        r23.zzs.zzau().zzb().zzc("Error storing property filter. appId", com.google.android.gms.measurement.internal.zzem.zzl(r24), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x0386, code lost:
    
        zzZ();
        zzg();
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24);
        r0 = zze();
        r3 = r17;
        r0.delete("property_filters", r3, new java.lang.String[]{r24, java.lang.String.valueOf(r10)});
        r0.delete("event_filters", r3, new java.lang.String[]{r24, java.lang.String.valueOf(r10)});
        r17 = r3;
        r4 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x03bd, code lost:
    
        r4 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x018b, code lost:
    
        r11 = r0.zzc().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0197, code lost:
    
        if (r11.hasNext() == false) goto L169;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01a3, code lost:
    
        if (r11.next().zza() != false) goto L182;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x01a5, code lost:
    
        r23.zzs.zzau().zze().zzc("Property filter with no ID. Audience definition ignored. appId, audienceId", com.google.android.gms.measurement.internal.zzem.zzl(r24), java.lang.Integer.valueOf(r10));
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x01be, code lost:
    
        r11 = r0.zzf().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x01d4, code lost:
    
        if (r11.hasNext() == false) goto L183;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x01d6, code lost:
    
        r12 = r11.next();
        zzZ();
        zzg();
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24);
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x01f0, code lost:
    
        if (android.text.TextUtils.isEmpty(r12.zzc()) == false) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x01f2, code lost:
    
        r0 = r23.zzs.zzau().zze();
        r8 = com.google.android.gms.measurement.internal.zzem.zzl(r24);
        r11 = java.lang.Integer.valueOf(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x020a, code lost:
    
        if (r12.zza() == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x020c, code lost:
    
        r20 = java.lang.Integer.valueOf(r12.zzb());
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0217, code lost:
    
        r20 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0219, code lost:
    
        r0.zzd("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", r8, r11, java.lang.String.valueOf(r20));
        r21 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0224, code lost:
    
        r3 = r12.zzbp();
        r21 = r4;
        r4 = new android.content.ContentValues();
        r4.put("app_id", r24);
        r4.put("audience_id", java.lang.Integer.valueOf(r10));
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x023d, code lost:
    
        if (r12.zza() == false) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x023f, code lost:
    
        r8 = java.lang.Integer.valueOf(r12.zzb());
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0248, code lost:
    
        r8 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0249, code lost:
    
        r4.put("filter_id", r8);
        r4.put(bolts.MeasurementEvent.MEASUREMENT_EVENT_NAME_KEY, r12.zzc());
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0259, code lost:
    
        if (r12.zzk() == false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x025b, code lost:
    
        r8 = java.lang.Boolean.valueOf(r12.zzm());
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0264, code lost:
    
        r8 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0265, code lost:
    
        r4.put("session_scoped", r8);
        r4.put("data", r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0279, code lost:
    
        if (zze().insertWithOnConflict("event_filters", null, r4, 5) != (-1)) goto L185;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x027b, code lost:
    
        r23.zzs.zzau().zzb().zzb("Failed to insert event filter (got -1). appId", com.google.android.gms.measurement.internal.zzem.zzl(r24));
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x028e, code lost:
    
        r4 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0294, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0295, code lost:
    
        r23.zzs.zzau().zzb().zzc("Error storing event filter. appId", com.google.android.gms.measurement.internal.zzem.zzl(r24), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x02aa, code lost:
    
        r21 = r4;
        r0 = r0.zzc().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x02b8, code lost:
    
        if (r0.hasNext() == false) goto L171;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x02ba, code lost:
    
        r3 = r0.next();
        zzZ();
        zzg();
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24);
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x02d4, code lost:
    
        if (android.text.TextUtils.isEmpty(r3.zzc()) == false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x02d6, code lost:
    
        r0 = r23.zzs.zzau().zze();
        r7 = com.google.android.gms.measurement.internal.zzem.zzl(r24);
        r8 = java.lang.Integer.valueOf(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x02ee, code lost:
    
        if (r3.zza() == false) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x02f0, code lost:
    
        r3 = java.lang.Integer.valueOf(r3.zzb());
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x02f9, code lost:
    
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x02fa, code lost:
    
        r0.zzd("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", r7, r8, java.lang.String.valueOf(r3));
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0303, code lost:
    
        r4 = r3.zzbp();
        r11 = new android.content.ContentValues();
        r11.put("app_id", r24);
        r11.put("audience_id", java.lang.Integer.valueOf(r10));
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x031a, code lost:
    
        if (r3.zza() == false) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x031c, code lost:
    
        r12 = java.lang.Integer.valueOf(r3.zzb());
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0325, code lost:
    
        r12 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0326, code lost:
    
        r11.put("filter_id", r12);
        r22 = r0;
        r11.put("property_name", r3.zzc());
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0338, code lost:
    
        if (r3.zzg() == false) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x033a, code lost:
    
        r0 = java.lang.Boolean.valueOf(r3.zzh());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final void zzL(java.lang.String r24, java.util.List<com.google.android.gms.internal.measurement.zzeh> r25) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1221
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzai.zzL(java.lang.String, java.util.List):void");
    }

    protected final boolean zzM() {
        Context contextZzax = this.zzs.zzax();
        this.zzs.zzc();
        return contextZzax.getDatabasePath("google_app_measurement.db").exists();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x023f: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:106:0x023f */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r4v3, types: [boolean] */
    public final void zzW(String str, long j, long j2, zzkm zzkmVar) throws Throwable {
        ?? IsEmpty;
        Cursor cursor;
        String str2;
        Cursor cursorRawQuery;
        String string;
        String str3;
        String[] strArr;
        Preconditions.checkNotNull(zzkmVar);
        zzg();
        zzZ();
        Cursor cursor2 = null;
        string = null;
        string = null;
        String string2 = null;
        try {
            try {
                SQLiteDatabase sQLiteDatabaseZze = zze();
                IsEmpty = TextUtils.isEmpty(null);
                try {
                    if (IsEmpty != 0) {
                        String[] strArr2 = j2 != -1 ? new String[]{String.valueOf(j2), String.valueOf(j)} : new String[]{String.valueOf(j)};
                        str2 = j2 != -1 ? "rowid <= ? and " : "";
                        StringBuilder sb = new StringBuilder(str2.length() + 148);
                        sb.append("select app_id, metadata_fingerprint from raw_events where ");
                        sb.append(str2);
                        sb.append("app_id in (select app_id from apps where config_fetched_time >= ?) order by rowid limit 1;");
                        cursorRawQuery = sQLiteDatabaseZze.rawQuery(sb.toString(), strArr2);
                        if (!cursorRawQuery.moveToFirst()) {
                            if (cursorRawQuery != null) {
                                cursorRawQuery.close();
                                return;
                            }
                            return;
                        } else {
                            string2 = cursorRawQuery.getString(0);
                            string = cursorRawQuery.getString(1);
                            cursorRawQuery.close();
                        }
                    } else {
                        String[] strArr3 = j2 != -1 ? new String[]{null, String.valueOf(j2)} : new String[]{null};
                        str2 = j2 != -1 ? " and rowid <= ?" : "";
                        StringBuilder sb2 = new StringBuilder(str2.length() + 84);
                        sb2.append("select metadata_fingerprint from raw_events where app_id = ?");
                        sb2.append(str2);
                        sb2.append(" order by rowid limit 1;");
                        cursorRawQuery = sQLiteDatabaseZze.rawQuery(sb2.toString(), strArr3);
                        if (!cursorRawQuery.moveToFirst()) {
                            if (cursorRawQuery != null) {
                                cursorRawQuery.close();
                                return;
                            }
                            return;
                        }
                        string = cursorRawQuery.getString(0);
                        cursorRawQuery.close();
                    }
                    Cursor cursor3 = cursorRawQuery;
                    String str4 = string;
                    try {
                        Cursor cursorQuery = sQLiteDatabaseZze.query("raw_events_metadata", new String[]{"metadata"}, "app_id = ? and metadata_fingerprint = ?", new String[]{string2, str4}, null, null, "rowid", RequestStatus.SUCCESS);
                        try {
                            if (!cursorQuery.moveToFirst()) {
                                this.zzs.zzau().zzb().zzb("Raw event metadata record is missing. appId", zzem.zzl(string2));
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                    return;
                                }
                                return;
                            }
                            try {
                                try {
                                    com.google.android.gms.internal.measurement.zzfw zzfwVarZzaA = ((com.google.android.gms.internal.measurement.zzfv) zzkp.zzt(com.google.android.gms.internal.measurement.zzfw.zzaj(), cursorQuery.getBlob(0))).zzaA();
                                    if (cursorQuery.moveToNext()) {
                                        this.zzs.zzau().zze().zzb("Get multiple raw event metadata records, expected one. appId", zzem.zzl(string2));
                                    }
                                    cursorQuery.close();
                                    Preconditions.checkNotNull(zzfwVarZzaA);
                                    zzkmVar.zza = zzfwVarZzaA;
                                    if (j2 != -1) {
                                        str3 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?";
                                        strArr = new String[]{string2, str4, String.valueOf(j2)};
                                    } else {
                                        str3 = "app_id = ? and metadata_fingerprint = ?";
                                        strArr = new String[]{string2, str4};
                                    }
                                    Cursor cursorQuery2 = sQLiteDatabaseZze.query("raw_events", new String[]{"rowid", "name", "timestamp", "data"}, str3, strArr, null, null, "rowid", null);
                                    if (!cursorQuery2.moveToFirst()) {
                                        this.zzs.zzau().zze().zzb("Raw event data disappeared while in transaction. appId", zzem.zzl(string2));
                                        if (cursorQuery2 != null) {
                                            cursorQuery2.close();
                                            return;
                                        }
                                        return;
                                    }
                                    do {
                                        long j3 = cursorQuery2.getLong(0);
                                        try {
                                            com.google.android.gms.internal.measurement.zzfn zzfnVar = (com.google.android.gms.internal.measurement.zzfn) zzkp.zzt(com.google.android.gms.internal.measurement.zzfo.zzk(), cursorQuery2.getBlob(3));
                                            zzfnVar.zzl(cursorQuery2.getString(1));
                                            zzfnVar.zzo(cursorQuery2.getLong(2));
                                            if (!zzkmVar.zza(j3, zzfnVar.zzaA())) {
                                                if (cursorQuery2 != null) {
                                                    cursorQuery2.close();
                                                    return;
                                                }
                                                return;
                                            }
                                        } catch (IOException e) {
                                            this.zzs.zzau().zzb().zzc("Data loss. Failed to merge raw event. appId", zzem.zzl(string2), e);
                                        }
                                    } while (cursorQuery2.moveToNext());
                                    if (cursorQuery2 != null) {
                                        cursorQuery2.close();
                                    }
                                } catch (IOException e2) {
                                    this.zzs.zzau().zzb().zzc("Data loss. Failed to merge raw event metadata. appId", zzem.zzl(string2), e2);
                                    if (cursorQuery != null) {
                                        cursorQuery.close();
                                    }
                                }
                            } catch (SQLiteException e3) {
                                e = e3;
                                IsEmpty = cursorQuery;
                                this.zzs.zzau().zzb().zzc("Data loss. Error selecting raw event. appId", zzem.zzl(string2), e);
                                if (IsEmpty != 0) {
                                    IsEmpty.close();
                                }
                            } catch (Throwable th) {
                                th = th;
                                cursor2 = cursorQuery;
                                if (cursor2 != null) {
                                    cursor2.close();
                                }
                                throw th;
                            }
                        } catch (SQLiteException e4) {
                            e = e4;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (SQLiteException e5) {
                        e = e5;
                        IsEmpty = cursor3;
                    } catch (Throwable th3) {
                        th = th3;
                        cursor2 = cursor3;
                    }
                } catch (SQLiteException e6) {
                    e = e6;
                }
            } catch (Throwable th4) {
                th = th4;
                cursor2 = cursor;
            }
        } catch (SQLiteException e7) {
            e = e7;
            IsEmpty = 0;
        } catch (Throwable th5) {
            th = th5;
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzke
    protected final boolean zzaA() {
        return false;
    }

    public final void zzb() {
        zzZ();
        zze().beginTransaction();
    }

    public final void zzc() {
        zzZ();
        zze().setTransactionSuccessful();
    }

    public final void zzd() {
        zzZ();
        zze().endTransaction();
    }

    final SQLiteDatabase zze() {
        zzg();
        try {
            return this.zzj.getWritableDatabase();
        } catch (SQLiteException e) {
            this.zzs.zzau().zze().zzb("Error opening database", e);
            throw e;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x0154  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.google.android.gms.measurement.internal.zzao zzf(java.lang.String r28, java.lang.String r29) {
        /*
            Method dump skipped, instructions count: 344
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzai.zzf(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzao");
    }

    public final void zzh(zzao zzaoVar) {
        Preconditions.checkNotNull(zzaoVar);
        zzg();
        zzZ();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzaoVar.zza);
        contentValues.put("name", zzaoVar.zzb);
        contentValues.put("lifetime_count", Long.valueOf(zzaoVar.zzc));
        contentValues.put("current_bundle_count", Long.valueOf(zzaoVar.zzd));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzaoVar.zzf));
        contentValues.put("last_bundled_timestamp", Long.valueOf(zzaoVar.zzg));
        contentValues.put("last_bundled_day", zzaoVar.zzh);
        contentValues.put("last_sampled_complex_event_id", zzaoVar.zzi);
        contentValues.put("last_sampling_rate", zzaoVar.zzj);
        contentValues.put("current_session_count", Long.valueOf(zzaoVar.zze));
        Boolean bool = zzaoVar.zzk;
        contentValues.put("last_exempt_from_sampling", (bool == null || !bool.booleanValue()) ? null : 1L);
        try {
            if (zze().insertWithOnConflict("events", null, contentValues, 5) == -1) {
                this.zzs.zzau().zzb().zzb("Failed to insert/update event aggregates (got -1). appId", zzem.zzl(zzaoVar.zza));
            }
        } catch (SQLiteException e) {
            this.zzs.zzau().zzb().zzc("Error storing event aggregates. appId", zzem.zzl(zzaoVar.zza), e);
        }
    }

    public final void zzi(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzg();
        zzZ();
        try {
            zze().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            this.zzs.zzau().zzb().zzd("Error deleting user property. appId", zzem.zzl(str), this.zzs.zzm().zze(str2), e);
        }
    }

    public final boolean zzj(zzks zzksVar) {
        Preconditions.checkNotNull(zzksVar);
        zzg();
        zzZ();
        if (zzk(zzksVar.zza, zzksVar.zzc) == null) {
            if (zzku.zzh(zzksVar.zzc)) {
                if (zzab("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zzksVar.zza}) >= this.zzs.zzc().zzl(zzksVar.zza, zzea.zzF, 25, 100)) {
                    return false;
                }
            } else if (!"_npa".equals(zzksVar.zzc)) {
                long jZzab = zzab("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzksVar.zza, zzksVar.zzb});
                this.zzs.zzc();
                if (jZzab >= 25) {
                    return false;
                }
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzksVar.zza);
        contentValues.put("origin", zzksVar.zzb);
        contentValues.put("name", zzksVar.zzc);
        contentValues.put("set_timestamp", Long.valueOf(zzksVar.zzd));
        zzX(contentValues, "value", zzksVar.zze);
        try {
            if (zze().insertWithOnConflict("user_attributes", null, contentValues, 5) == -1) {
                this.zzs.zzau().zzb().zzb("Failed to insert/update user property (got -1). appId", zzem.zzl(zzksVar.zza));
            }
        } catch (SQLiteException e) {
            this.zzs.zzau().zzb().zzc("Error storing user property. appId", zzem.zzl(zzksVar.zza), e);
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.google.android.gms.measurement.internal.zzks zzk(java.lang.String r20, java.lang.String r21) {
        /*
            r19 = this;
            r1 = r19
            r9 = r21
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r20)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r21)
            r19.zzg()
            r19.zzZ()
            r10 = 0
            android.database.sqlite.SQLiteDatabase r11 = r19.zze()     // Catch: java.lang.Throwable -> L82 android.database.sqlite.SQLiteException -> L84
            java.lang.String r0 = "set_timestamp"
            java.lang.String r2 = "value"
            java.lang.String r3 = "origin"
            java.lang.String[] r13 = new java.lang.String[]{r0, r2, r3}     // Catch: java.lang.Throwable -> L82 android.database.sqlite.SQLiteException -> L84
            r0 = 2
            java.lang.String[] r15 = new java.lang.String[r0]     // Catch: java.lang.Throwable -> L82 android.database.sqlite.SQLiteException -> L84
            r2 = 0
            r15[r2] = r20     // Catch: java.lang.Throwable -> L82 android.database.sqlite.SQLiteException -> L84
            r3 = 1
            r15[r3] = r9     // Catch: java.lang.Throwable -> L82 android.database.sqlite.SQLiteException -> L84
            java.lang.String r12 = "user_attributes"
            java.lang.String r14 = "app_id=? and name=?"
            r16 = 0
            r17 = 0
            r18 = 0
            android.database.Cursor r11 = r11.query(r12, r13, r14, r15, r16, r17, r18)     // Catch: java.lang.Throwable -> L82 android.database.sqlite.SQLiteException -> L84
            boolean r4 = r11.moveToFirst()     // Catch: android.database.sqlite.SQLiteException -> L80 java.lang.Throwable -> La9
            if (r4 != 0) goto L43
            if (r11 == 0) goto L42
            r11.close()
        L42:
            return r10
        L43:
            long r6 = r11.getLong(r2)     // Catch: android.database.sqlite.SQLiteException -> L80 java.lang.Throwable -> La9
            java.lang.Object r8 = r1.zzC(r11, r3)     // Catch: android.database.sqlite.SQLiteException -> L80 java.lang.Throwable -> La9
            if (r8 != 0) goto L53
            if (r11 == 0) goto L52
            r11.close()
        L52:
            return r10
        L53:
            java.lang.String r4 = r11.getString(r0)     // Catch: android.database.sqlite.SQLiteException -> L80 java.lang.Throwable -> La9
            com.google.android.gms.measurement.internal.zzks r0 = new com.google.android.gms.measurement.internal.zzks     // Catch: android.database.sqlite.SQLiteException -> L80 java.lang.Throwable -> La9
            r2 = r0
            r3 = r20
            r5 = r21
            r2.<init>(r3, r4, r5, r6, r8)     // Catch: android.database.sqlite.SQLiteException -> L80 java.lang.Throwable -> La9
            boolean r2 = r11.moveToNext()     // Catch: android.database.sqlite.SQLiteException -> L80 java.lang.Throwable -> La9
            if (r2 == 0) goto L7a
            com.google.android.gms.measurement.internal.zzfu r2 = r1.zzs     // Catch: android.database.sqlite.SQLiteException -> L80 java.lang.Throwable -> La9
            com.google.android.gms.measurement.internal.zzem r2 = r2.zzau()     // Catch: android.database.sqlite.SQLiteException -> L80 java.lang.Throwable -> La9
            com.google.android.gms.measurement.internal.zzek r2 = r2.zzb()     // Catch: android.database.sqlite.SQLiteException -> L80 java.lang.Throwable -> La9
            java.lang.String r3 = "Got multiple records for user property, expected one. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzem.zzl(r20)     // Catch: android.database.sqlite.SQLiteException -> L80 java.lang.Throwable -> La9
            r2.zzb(r3, r4)     // Catch: android.database.sqlite.SQLiteException -> L80 java.lang.Throwable -> La9
        L7a:
            if (r11 == 0) goto L7f
            r11.close()
        L7f:
            return r0
        L80:
            r0 = move-exception
            goto L86
        L82:
            r0 = move-exception
            goto Lab
        L84:
            r0 = move-exception
            r11 = r10
        L86:
            com.google.android.gms.measurement.internal.zzfu r2 = r1.zzs     // Catch: java.lang.Throwable -> La9
            com.google.android.gms.measurement.internal.zzem r2 = r2.zzau()     // Catch: java.lang.Throwable -> La9
            com.google.android.gms.measurement.internal.zzek r2 = r2.zzb()     // Catch: java.lang.Throwable -> La9
            java.lang.String r3 = "Error querying user property. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzem.zzl(r20)     // Catch: java.lang.Throwable -> La9
            com.google.android.gms.measurement.internal.zzfu r5 = r1.zzs     // Catch: java.lang.Throwable -> La9
            com.google.android.gms.measurement.internal.zzeh r5 = r5.zzm()     // Catch: java.lang.Throwable -> La9
            java.lang.String r5 = r5.zze(r9)     // Catch: java.lang.Throwable -> La9
            r2.zzd(r3, r4, r5, r0)     // Catch: java.lang.Throwable -> La9
            if (r11 == 0) goto La8
            r11.close()
        La8:
            return r10
        La9:
            r0 = move-exception
            r10 = r11
        Lab:
            if (r10 == 0) goto Lb0
            r10.close()
        Lb0:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzai.zzk(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzks");
    }

    public final List<zzks> zzl(String str) {
        Preconditions.checkNotEmpty(str);
        zzg();
        zzZ();
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                this.zzs.zzc();
                cursorQuery = zze().query("user_attributes", new String[]{"name", "origin", "set_timestamp", "value"}, "app_id=?", new String[]{str}, null, null, "rowid", "1000");
                if (!cursorQuery.moveToFirst()) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayList;
                }
                do {
                    String string = cursorQuery.getString(0);
                    String string2 = cursorQuery.getString(1);
                    if (string2 == null) {
                        string2 = "";
                    }
                    String str2 = string2;
                    long j = cursorQuery.getLong(2);
                    Object objZzC = zzC(cursorQuery, 3);
                    if (objZzC == null) {
                        this.zzs.zzau().zzb().zzb("Read invalid user property value, ignoring it. appId", zzem.zzl(str));
                    } else {
                        arrayList.add(new zzks(str, str2, string, j, objZzC));
                    }
                } while (cursorQuery.moveToNext());
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return arrayList;
            } catch (SQLiteException e) {
                this.zzs.zzau().zzb().zzc("Error querying user properties. appId", zzem.zzl(str), e);
                List<zzks> listEmptyList = Collections.emptyList();
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return listEmptyList;
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x009f, code lost:
    
        r2 = r16.zzs.zzau().zzb();
        r16.zzs.zzc();
        r2.zzb("Read more than the max allowed user properties, ignoring excess", 1000);
     */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0122  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.List<com.google.android.gms.measurement.internal.zzks> zzm(java.lang.String r17, java.lang.String r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 300
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzai.zzm(java.lang.String, java.lang.String, java.lang.String):java.util.List");
    }

    public final boolean zzn(zzaa zzaaVar) {
        Preconditions.checkNotNull(zzaaVar);
        zzg();
        zzZ();
        String str = zzaaVar.zza;
        Preconditions.checkNotNull(str);
        if (zzk(str, zzaaVar.zzc.zzb) == null) {
            long jZzab = zzab("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{str});
            this.zzs.zzc();
            if (jZzab >= 1000) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("origin", zzaaVar.zzb);
        contentValues.put("name", zzaaVar.zzc.zzb);
        zzX(contentValues, "value", Preconditions.checkNotNull(zzaaVar.zzc.zza()));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, Boolean.valueOf(zzaaVar.zze));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, zzaaVar.zzf);
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, Long.valueOf(zzaaVar.zzh));
        contentValues.put("timed_out_event", this.zzs.zzl().zzX(zzaaVar.zzg));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, Long.valueOf(zzaaVar.zzd));
        contentValues.put("triggered_event", this.zzs.zzl().zzX(zzaaVar.zzi));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, Long.valueOf(zzaaVar.zzc.zzc));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, Long.valueOf(zzaaVar.zzj));
        contentValues.put("expired_event", this.zzs.zzl().zzX(zzaaVar.zzk));
        try {
            if (zze().insertWithOnConflict("conditional_properties", null, contentValues, 5) == -1) {
                this.zzs.zzau().zzb().zzb("Failed to insert/update conditional user property (got -1)", zzem.zzl(str));
            }
        } catch (SQLiteException e) {
            this.zzs.zzau().zzb().zzc("Error storing conditional user property", zzem.zzl(str), e);
        }
        return true;
    }

    /* JADX WARN: Not initialized variable reg: 10, insn: 0x0126: MOVE (r9 I:??[OBJECT, ARRAY]) = (r10 I:??[OBJECT, ARRAY]), block:B:30:0x0126 */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0129  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.google.android.gms.measurement.internal.zzaa zzo(java.lang.String r31, java.lang.String r32) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 301
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzai.zzo(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzaa");
    }

    public final int zzp(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzg();
        zzZ();
        try {
            return zze().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            this.zzs.zzau().zzb().zzd("Error deleting conditional property", zzem.zzl(str), this.zzs.zzm().zze(str2), e);
            return 0;
        }
    }

    public final List<zzaa> zzq(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzg();
        zzZ();
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(str);
        StringBuilder sb = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            sb.append(" and origin=?");
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(String.valueOf(str3).concat("*"));
            sb.append(" and name glob ?");
        }
        return zzr(sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0059, code lost:
    
        r2 = r27.zzs.zzau().zzb();
        r27.zzs.zzc();
        r2.zzb("Read more than the max allowed conditional properties, ignoring extra", 1000);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.List<com.google.android.gms.measurement.internal.zzaa> zzr(java.lang.String r28, java.lang.String[] r29) {
        /*
            Method dump skipped, instructions count: 308
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzai.zzr(java.lang.String, java.lang.String[]):java.util.List");
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x020c: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:60:0x020c */
    /* JADX WARN: Removed duplicated region for block: B:62:0x020f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.google.android.gms.measurement.internal.zzg zzs(java.lang.String r34) {
        /*
            Method dump skipped, instructions count: 531
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzai.zzs(java.lang.String):com.google.android.gms.measurement.internal.zzg");
    }

    public final void zzt(zzg zzgVar) {
        Preconditions.checkNotNull(zzgVar);
        zzg();
        zzZ();
        String strZzc = zzgVar.zzc();
        Preconditions.checkNotNull(strZzc);
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", strZzc);
        contentValues.put("app_instance_id", zzgVar.zzd());
        contentValues.put("gmp_app_id", zzgVar.zzf());
        contentValues.put("resettable_device_id_hash", zzgVar.zzl());
        contentValues.put("last_bundle_index", Long.valueOf(zzgVar.zzI()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzgVar.zzp()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzgVar.zzr()));
        contentValues.put("app_version", zzgVar.zzt());
        contentValues.put("app_store", zzgVar.zzx());
        contentValues.put("gmp_version", Long.valueOf(zzgVar.zzz()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzgVar.zzB()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzgVar.zzF()));
        contentValues.put("day", Long.valueOf(zzgVar.zzO()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzgVar.zzQ()));
        contentValues.put("daily_events_count", Long.valueOf(zzgVar.zzS()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzgVar.zzU()));
        contentValues.put("config_fetched_time", Long.valueOf(zzgVar.zzJ()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzgVar.zzL()));
        contentValues.put("app_version_int", Long.valueOf(zzgVar.zzv()));
        contentValues.put("firebase_instance_id", zzgVar.zzn());
        contentValues.put("daily_error_events_count", Long.valueOf(zzgVar.zzY()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzgVar.zzW()));
        contentValues.put("health_monitor_sample", zzgVar.zzaa());
        contentValues.put("android_id", Long.valueOf(zzgVar.zzad()));
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(zzgVar.zzaf()));
        contentValues.put("admob_app_id", zzgVar.zzh());
        contentValues.put("dynamite_version", Long.valueOf(zzgVar.zzD()));
        List<String> listZzaj = zzgVar.zzaj();
        if (listZzaj != null) {
            if (listZzaj.size() == 0) {
                this.zzs.zzau().zze().zzb("Safelisted events should not be an empty list. appId", strZzc);
            } else {
                contentValues.put("safelisted_events", TextUtils.join(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR, listZzaj));
            }
        }
        zzov.zzb();
        if (this.zzs.zzc().zzn(strZzc, zzea.zzag)) {
            contentValues.put("ga_app_id", zzgVar.zzj());
        }
        try {
            SQLiteDatabase sQLiteDatabaseZze = zze();
            if (sQLiteDatabaseZze.update("apps", contentValues, "app_id = ?", new String[]{strZzc}) == 0 && sQLiteDatabaseZze.insertWithOnConflict("apps", null, contentValues, 5) == -1) {
                this.zzs.zzau().zzb().zzb("Failed to insert/update app (got -1). appId", zzem.zzl(strZzc));
            }
        } catch (SQLiteException e) {
            this.zzs.zzau().zzb().zzc("Error storing app. appId", zzem.zzl(strZzc), e);
        }
    }

    public final zzag zzu(long j, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        return zzv(j, str, 1L, false, false, z3, false, z5);
    }

    public final zzag zzv(long j, String str, long j2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        Preconditions.checkNotEmpty(str);
        zzg();
        zzZ();
        String[] strArr = {str};
        zzag zzagVar = new zzag();
        Cursor cursor = null;
        try {
            try {
                SQLiteDatabase sQLiteDatabaseZze = zze();
                Cursor cursorQuery = sQLiteDatabaseZze.query("apps", new String[]{"day", "daily_events_count", "daily_public_events_count", "daily_conversions_count", "daily_error_events_count", "daily_realtime_events_count"}, "app_id=?", new String[]{str}, null, null, null);
                if (!cursorQuery.moveToFirst()) {
                    this.zzs.zzau().zze().zzb("Not updating daily counts, app is not known. appId", zzem.zzl(str));
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return zzagVar;
                }
                if (cursorQuery.getLong(0) == j) {
                    zzagVar.zzb = cursorQuery.getLong(1);
                    zzagVar.zza = cursorQuery.getLong(2);
                    zzagVar.zzc = cursorQuery.getLong(3);
                    zzagVar.zzd = cursorQuery.getLong(4);
                    zzagVar.zze = cursorQuery.getLong(5);
                }
                if (z) {
                    zzagVar.zzb += j2;
                }
                if (z2) {
                    zzagVar.zza += j2;
                }
                if (z3) {
                    zzagVar.zzc += j2;
                }
                if (z4) {
                    zzagVar.zzd += j2;
                }
                if (z5) {
                    zzagVar.zze += j2;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("day", Long.valueOf(j));
                contentValues.put("daily_public_events_count", Long.valueOf(zzagVar.zza));
                contentValues.put("daily_events_count", Long.valueOf(zzagVar.zzb));
                contentValues.put("daily_conversions_count", Long.valueOf(zzagVar.zzc));
                contentValues.put("daily_error_events_count", Long.valueOf(zzagVar.zzd));
                contentValues.put("daily_realtime_events_count", Long.valueOf(zzagVar.zze));
                sQLiteDatabaseZze.update("apps", contentValues, "app_id=?", strArr);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return zzagVar;
            } catch (SQLiteException e) {
                this.zzs.zzau().zzb().zzc("Error updating daily counts. appId", zzem.zzl(str), e);
                if (0 != 0) {
                    cursor.close();
                }
                return zzagVar;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    public final void zzw(String str, byte[] bArr, String str2) {
        Preconditions.checkNotEmpty(str);
        zzg();
        zzZ();
        ContentValues contentValues = new ContentValues();
        contentValues.put("remote_config", bArr);
        contentValues.put("config_last_modified_time", str2);
        try {
            if (zze().update("apps", contentValues, "app_id = ?", new String[]{str}) == 0) {
                this.zzs.zzau().zzb().zzb("Failed to update remote config (got 0). appId", zzem.zzl(str));
            }
        } catch (SQLiteException e) {
            this.zzs.zzau().zzb().zzc("Error storing remote config. appId", zzem.zzl(str), e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzx(com.google.android.gms.internal.measurement.zzfw r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 296
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzai.zzx(com.google.android.gms.internal.measurement.zzfw, boolean):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.String zzy() throws java.lang.Throwable {
        /*
            r6 = this;
            android.database.sqlite.SQLiteDatabase r0 = r6.zze()
            r1 = 0
            java.lang.String r2 = "select app_id from queue order by has_realtime desc, rowid asc limit 1;"
            android.database.Cursor r0 = r0.rawQuery(r2, r1)     // Catch: java.lang.Throwable -> L24 android.database.sqlite.SQLiteException -> L26
            boolean r2 = r0.moveToFirst()     // Catch: android.database.sqlite.SQLiteException -> L22 java.lang.Throwable -> L3e
            if (r2 == 0) goto L1c
            r2 = 0
            java.lang.String r1 = r0.getString(r2)     // Catch: android.database.sqlite.SQLiteException -> L22 java.lang.Throwable -> L3e
            if (r0 == 0) goto L1b
            r0.close()
        L1b:
            return r1
        L1c:
            if (r0 == 0) goto L21
            r0.close()
        L21:
            return r1
        L22:
            r2 = move-exception
            goto L29
        L24:
            r0 = move-exception
            goto L42
        L26:
            r0 = move-exception
            r2 = r0
            r0 = r1
        L29:
            com.google.android.gms.measurement.internal.zzfu r3 = r6.zzs     // Catch: java.lang.Throwable -> L3e
            com.google.android.gms.measurement.internal.zzem r3 = r3.zzau()     // Catch: java.lang.Throwable -> L3e
            com.google.android.gms.measurement.internal.zzek r3 = r3.zzb()     // Catch: java.lang.Throwable -> L3e
            java.lang.String r4 = "Database error getting next bundle app id"
            r3.zzb(r4, r2)     // Catch: java.lang.Throwable -> L3e
            if (r0 == 0) goto L3d
            r0.close()
        L3d:
            return r1
        L3e:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
        L42:
            if (r1 == 0) goto L47
            r1.close()
        L47:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzai.zzy():java.lang.String");
    }

    public final boolean zzz() {
        return zzab("select count(1) > 0 from queue where has_realtime = 1", null) != 0;
    }
}
