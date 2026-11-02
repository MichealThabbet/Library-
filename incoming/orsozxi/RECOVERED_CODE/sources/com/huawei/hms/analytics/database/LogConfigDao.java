package com.huawei.hms.analytics.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import com.app.piapostolos.Alarm.Database.Database;
import com.huawei.hms.analytics.bf;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* loaded from: classes2.dex */
public class LogConfigDao extends AbstractDao<LogConfig, Long> {
    public static final String TABLENAME = "ha_lgcf";

    public static class Properties {
        public static final Property lmn = new Property(0, Long.class, "id", true, Database.COLUMN_ALARM_ID);
        public static final Property klm = new Property(1, String.class, "region", false, "_eg");
        public static final Property ikl = new Property(2, String.class, "projectId", false, "lg_pid");
        public static final Property ijk = new Property(3, String.class, "logGroupId", false, "lg_gid");
        public static final Property hij = new Property(4, String.class, "logStreamId", false, "lg_sid");
        public static final Property ghi = new Property(5, String.class, "logTags", false, "lg_tag");
    }

    public LogConfigDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public LogConfigDao(DaoConfig daoConfig, bf bfVar) {
        super(daoConfig, bfVar);
    }

    public static void createTable(org.greenrobot.greendao.database.Database database, boolean z) throws SQLException {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"ha_lgcf\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"_eg\" TEXT,\"lg_pid\" TEXT,\"lg_gid\" TEXT,\"lg_sid\" TEXT,\"lg_tag\" TEXT);");
    }

    public static void dropTable(org.greenrobot.greendao.database.Database database, boolean z) throws SQLException {
        StringBuilder sb = new StringBuilder("DROP TABLE ");
        sb.append(z ? "IF EXISTS " : "");
        sb.append("\"ha_lgcf\"");
        database.execSQL(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, LogConfig logConfig) {
        sQLiteStatement.clearBindings();
        Long id = logConfig.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        String region = logConfig.getRegion();
        if (region != null) {
            sQLiteStatement.bindString(2, region);
        }
        String projectId = logConfig.getProjectId();
        if (projectId != null) {
            sQLiteStatement.bindString(3, projectId);
        }
        String logGroupId = logConfig.getLogGroupId();
        if (logGroupId != null) {
            sQLiteStatement.bindString(4, logGroupId);
        }
        String logStreamId = logConfig.getLogStreamId();
        if (logStreamId != null) {
            sQLiteStatement.bindString(5, logStreamId);
        }
        String logTags = logConfig.getLogTags();
        if (logTags != null) {
            sQLiteStatement.bindString(6, logTags);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, LogConfig logConfig) {
        databaseStatement.clearBindings();
        Long id = logConfig.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        String region = logConfig.getRegion();
        if (region != null) {
            databaseStatement.bindString(2, region);
        }
        String projectId = logConfig.getProjectId();
        if (projectId != null) {
            databaseStatement.bindString(3, projectId);
        }
        String logGroupId = logConfig.getLogGroupId();
        if (logGroupId != null) {
            databaseStatement.bindString(4, logGroupId);
        }
        String logStreamId = logConfig.getLogStreamId();
        if (logStreamId != null) {
            databaseStatement.bindString(5, logStreamId);
        }
        String logTags = logConfig.getLogTags();
        if (logTags != null) {
            databaseStatement.bindString(6, logTags);
        }
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(LogConfig logConfig) {
        if (logConfig != null) {
            return logConfig.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(LogConfig logConfig) {
        return logConfig.getId() != null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    protected final boolean isEntityUpdateable() {
        return true;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public LogConfig readEntity(Cursor cursor, int i) {
        int i2 = i + 0;
        Long lValueOf = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        String string = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        String string2 = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        String string3 = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        int i7 = i + 5;
        return new LogConfig(lValueOf, string, string2, string3, cursor.isNull(i6) ? null : cursor.getString(i6), cursor.isNull(i7) ? null : cursor.getString(i7));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, LogConfig logConfig, int i) {
        int i2 = i + 0;
        logConfig.setId(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = i + 1;
        logConfig.setRegion(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 2;
        logConfig.setProjectId(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = i + 3;
        logConfig.setLogGroupId(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = i + 4;
        logConfig.setLogStreamId(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = i + 5;
        logConfig.setLogStreamId(cursor.isNull(i7) ? null : cursor.getString(i7));
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
    public final Long updateKeyAfterInsert(LogConfig logConfig, long j) {
        logConfig.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }
}
