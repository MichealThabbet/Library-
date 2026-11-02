package com.huawei.hms.analytics.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import com.app.orsozoxi.Alarm.Database.Database;
import com.huawei.hms.analytics.bf;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* loaded from: classes2.dex */
public class LogEventDao extends AbstractDao<LogEvent, Long> {
    public static final String TABLENAME = "ha_lg";

    public static class Properties {
        public static final Property lmn = new Property(0, Long.class, "id", true, Database.COLUMN_ALARM_ID);
        public static final Property klm = new Property(1, String.class, "evtTime", false, "ev_t");
        public static final Property ikl = new Property(2, Long.class, "evtType", false, "cf_id");
        public static final Property ijk = new Property(3, String.class, "content", false, "ev_ct");
        public static final Property hij = new Property(4, String.class, "eventId", false, "ev_id");
    }

    public LogEventDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public LogEventDao(DaoConfig daoConfig, bf bfVar) {
        super(daoConfig, bfVar);
    }

    public static void createTable(org.greenrobot.greendao.database.Database database, boolean z) throws SQLException {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"ha_lg\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"ev_t\" TEXT,\"cf_id\" INTEGER,\"ev_ct\" TEXT,\"ev_id\" TEXT);");
    }

    public static void dropTable(org.greenrobot.greendao.database.Database database, boolean z) throws SQLException {
        StringBuilder sb = new StringBuilder("DROP TABLE ");
        sb.append(z ? "IF EXISTS " : "");
        sb.append("\"ha_lg\"");
        database.execSQL(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, LogEvent logEvent) {
        sQLiteStatement.clearBindings();
        Long id = logEvent.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        String evtTime = logEvent.getEvtTime();
        if (evtTime != null) {
            sQLiteStatement.bindString(2, evtTime);
        }
        Long configId = logEvent.getConfigId();
        if (configId != null) {
            sQLiteStatement.bindLong(3, configId.longValue());
        }
        String content = logEvent.getContent();
        if (content != null) {
            sQLiteStatement.bindString(4, content);
        }
        String eventId = logEvent.getEventId();
        if (eventId != null) {
            sQLiteStatement.bindString(5, eventId);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, LogEvent logEvent) {
        databaseStatement.clearBindings();
        Long id = logEvent.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        String evtTime = logEvent.getEvtTime();
        if (evtTime != null) {
            databaseStatement.bindString(2, evtTime);
        }
        Long configId = logEvent.getConfigId();
        if (configId != null) {
            databaseStatement.bindLong(3, configId.longValue());
        }
        String content = logEvent.getContent();
        if (content != null) {
            databaseStatement.bindString(4, content);
        }
        String eventId = logEvent.getEventId();
        if (eventId != null) {
            databaseStatement.bindString(5, eventId);
        }
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(LogEvent logEvent) {
        if (logEvent != null) {
            return logEvent.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(LogEvent logEvent) {
        return logEvent.getId() != null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    protected final boolean isEntityUpdateable() {
        return true;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public LogEvent readEntity(Cursor cursor, int i) {
        int i2 = i + 0;
        Long lValueOf = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        String string = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        Long lValueOf2 = cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4));
        int i5 = i + 3;
        return new LogEvent(lValueOf, string, lValueOf2, cursor.isNull(i5) ? null : cursor.getString(i5), cursor.isNull(i + 5) ? null : cursor.getString(i + 4));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, LogEvent logEvent, int i) {
        int i2 = i + 0;
        logEvent.setId(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = i + 1;
        logEvent.setEvtTime(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 2;
        logEvent.setConfigId(cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4)));
        int i5 = i + 3;
        logEvent.setContent(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = i + 4;
        logEvent.setEventId(cursor.isNull(i6) ? null : cursor.getString(i6));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Long readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final Long updateKeyAfterInsert(LogEvent logEvent, long j) {
        logEvent.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }
}
