package com.huawei.hms.analytics.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import com.app.piapostolos.Alarm.Database.Database;
import com.facebook.share.internal.ShareConstants;
import com.huawei.hms.framework.common.hianalytics.WiseOpenHianalyticsData;
import net.fortuna.ical4j.model.Parameter;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* loaded from: classes2.dex */
public class APIEventDao extends AbstractDao<APIEvent, Long> {
    public static final String TABLENAME = "APIEVENT";

    public static class Properties {
        public static final Property lmn = new Property(0, Long.class, "id", true, Database.COLUMN_ALARM_ID);
        public static final Property klm = new Property(1, String.class, "code", false, "CODE");
        public static final Property ikl = new Property(2, String.class, "timestamp", false, "TIMESTAMP");
        public static final Property ijk = new Property(3, String.class, ShareConstants.MEDIA_TYPE, false, Parameter.TYPE);
        public static final Property hij = new Property(4, String.class, "name", false, net.fortuna.ical4j.model.Property.NAME);
        public static final Property ghi = new Property(5, String.class, "result", false, "RESULT");
        public static final Property fgh = new Property(6, String.class, "errorCode", false, "ERROR_CODE");
        public static final Property efg = new Property(7, String.class, "eventId", false, "EVENT_ID");
        public static final Property def = new Property(8, String.class, "eventCnt", false, "EVENT_CNT");
        public static final Property cde = new Property(9, String.class, WiseOpenHianalyticsData.UNION_COSTTIME, false, "COST_TIME");
    }

    public APIEventDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public APIEventDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(org.greenrobot.greendao.database.Database database, boolean z) throws SQLException {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"APIEVENT\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"CODE\" TEXT,\"TIMESTAMP\" TEXT,\"TYPE\" TEXT,\"NAME\" TEXT,\"RESULT\" TEXT,\"ERROR_CODE\" TEXT,\"EVENT_ID\" TEXT,\"EVENT_CNT\" TEXT,\"COST_TIME\" TEXT);");
    }

    public static void dropTable(org.greenrobot.greendao.database.Database database, boolean z) throws SQLException {
        StringBuilder sb = new StringBuilder("DROP TABLE ");
        sb.append(z ? "IF EXISTS " : "");
        sb.append("\"APIEVENT\"");
        database.execSQL(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, APIEvent aPIEvent) {
        sQLiteStatement.clearBindings();
        Long id = aPIEvent.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        String code = aPIEvent.getCode();
        if (code != null) {
            sQLiteStatement.bindString(2, code);
        }
        String timestamp = aPIEvent.getTimestamp();
        if (timestamp != null) {
            sQLiteStatement.bindString(3, timestamp);
        }
        String type = aPIEvent.getType();
        if (type != null) {
            sQLiteStatement.bindString(4, type);
        }
        String name = aPIEvent.getName();
        if (name != null) {
            sQLiteStatement.bindString(5, name);
        }
        String result = aPIEvent.getResult();
        if (result != null) {
            sQLiteStatement.bindString(6, result);
        }
        String errorCode = aPIEvent.getErrorCode();
        if (errorCode != null) {
            sQLiteStatement.bindString(7, errorCode);
        }
        String eventId = aPIEvent.getEventId();
        if (eventId != null) {
            sQLiteStatement.bindString(8, eventId);
        }
        String eventCnt = aPIEvent.getEventCnt();
        if (eventCnt != null) {
            sQLiteStatement.bindString(9, eventCnt);
        }
        String costTime = aPIEvent.getCostTime();
        if (costTime != null) {
            sQLiteStatement.bindString(10, costTime);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, APIEvent aPIEvent) {
        databaseStatement.clearBindings();
        Long id = aPIEvent.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        String code = aPIEvent.getCode();
        if (code != null) {
            databaseStatement.bindString(2, code);
        }
        String timestamp = aPIEvent.getTimestamp();
        if (timestamp != null) {
            databaseStatement.bindString(3, timestamp);
        }
        String type = aPIEvent.getType();
        if (type != null) {
            databaseStatement.bindString(4, type);
        }
        String name = aPIEvent.getName();
        if (name != null) {
            databaseStatement.bindString(5, name);
        }
        String result = aPIEvent.getResult();
        if (result != null) {
            databaseStatement.bindString(6, result);
        }
        String errorCode = aPIEvent.getErrorCode();
        if (errorCode != null) {
            databaseStatement.bindString(7, errorCode);
        }
        String eventId = aPIEvent.getEventId();
        if (eventId != null) {
            databaseStatement.bindString(8, eventId);
        }
        String eventCnt = aPIEvent.getEventCnt();
        if (eventCnt != null) {
            databaseStatement.bindString(9, eventCnt);
        }
        String costTime = aPIEvent.getCostTime();
        if (costTime != null) {
            databaseStatement.bindString(10, costTime);
        }
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(APIEvent aPIEvent) {
        if (aPIEvent != null) {
            return aPIEvent.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(APIEvent aPIEvent) {
        return aPIEvent.getId() != null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    protected final boolean isEntityUpdateable() {
        return true;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public APIEvent readEntity(Cursor cursor, int i) {
        int i2 = i + 0;
        Long lValueOf = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        String string = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        String string2 = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        String string3 = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        String string4 = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 5;
        String string5 = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 6;
        String string6 = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 7;
        String string7 = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 8;
        int i11 = i + 9;
        return new APIEvent(lValueOf, string, string2, string3, string4, string5, string6, string7, cursor.isNull(i10) ? null : cursor.getString(i10), cursor.isNull(i11) ? null : cursor.getString(i11));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, APIEvent aPIEvent, int i) {
        int i2 = i + 0;
        aPIEvent.setId(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = i + 1;
        aPIEvent.setCode(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 2;
        aPIEvent.setTimestamp(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = i + 3;
        aPIEvent.setType(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = i + 4;
        aPIEvent.setName(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = i + 5;
        aPIEvent.setResult(cursor.isNull(i7) ? null : cursor.getString(i7));
        int i8 = i + 6;
        aPIEvent.setErrorCode(cursor.isNull(i8) ? null : cursor.getString(i8));
        int i9 = i + 7;
        aPIEvent.setEventId(cursor.isNull(i9) ? null : cursor.getString(i9));
        int i10 = i + 8;
        aPIEvent.setEventCnt(cursor.isNull(i10) ? null : cursor.getString(i10));
        int i11 = i + 9;
        aPIEvent.setCostTime(cursor.isNull(i11) ? null : cursor.getString(i11));
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
    public final Long updateKeyAfterInsert(APIEvent aPIEvent, long j) {
        aPIEvent.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }
}
