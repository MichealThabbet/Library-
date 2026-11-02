package com.huawei.hms.analytics.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.app.piapostolos.Alarm.Database.Database;
import com.huawei.hms.analytics.core.storage.Event;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* loaded from: classes2.dex */
public class EventDao extends AbstractDao<Event, Long> {
    public static final String TABLENAME = "EVENT";

    public static class Properties {
        public static final Property lmn = new Property(0, Long.class, "id", true, Database.COLUMN_ALARM_ID);
        public static final Property klm = new Property(1, String.class, "evtid", false, "EVTID");
        public static final Property ikl = new Property(2, String.class, "evttype", false, "EVTTYPE");
        public static final Property ijk = new Property(3, String.class, "content", false, "CONTENT");
        public static final Property hij = new Property(4, String.class, "evttime", false, "EVTTIME");
        public static final Property ghi = new Property(5, String.class, "servicetag", false, "SERVICETAG");
        public static final Property fgh = new Property(6, String.class, "sessionid", false, "SESSIONID");
        public static final Property efg = new Property(7, String.class, "sessionname", false, "SESSIONNAME");
        public static final Property def = new Property(8, String.class, "associationId", false, "ID");
        public static final Property cde = new Property(9, String.class, "pid", false, "PID");
    }

    public EventDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public EventDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(org.greenrobot.greendao.database.Database database, boolean z) {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"EVENT\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"EVTID\" TEXT,\"EVTTYPE\" TEXT,\"CONTENT\" TEXT,\"EVTTIME\" TEXT,\"SERVICETAG\" TEXT,\"SESSIONID\" TEXT,\"SESSIONNAME\" TEXT,\"ID\" TEXT,\"PID\" TEXT)");
    }

    public static void dropTable(org.greenrobot.greendao.database.Database database, boolean z) {
        StringBuilder sb = new StringBuilder("DROP TABLE ");
        sb.append(z ? "IF EXISTS " : "");
        sb.append("\"EVENT\"");
        database.execSQL(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, Event event) {
        sQLiteStatement.clearBindings();
        Long id = event.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        String evtid = event.getEvtid();
        if (evtid != null) {
            sQLiteStatement.bindString(2, evtid);
        }
        String evttype = event.getEvttype();
        if (evttype != null) {
            sQLiteStatement.bindString(3, evttype);
        }
        String content = event.getContent();
        if (content != null) {
            sQLiteStatement.bindString(4, content);
        }
        String evttime = event.getEvttime();
        if (evttime != null) {
            sQLiteStatement.bindString(5, evttime);
        }
        String serviceTag = event.getServiceTag();
        if (serviceTag != null) {
            sQLiteStatement.bindString(6, serviceTag);
        }
        String sessionid = event.getSessionid();
        if (sessionid != null) {
            sQLiteStatement.bindString(7, sessionid);
        }
        String sessionName = event.getSessionName();
        if (sessionName != null) {
            sQLiteStatement.bindString(8, sessionName);
        }
        String associationid = event.getAssociationid();
        if (associationid != null) {
            sQLiteStatement.bindString(9, associationid);
        }
        String pid = event.getPid();
        if (pid != null) {
            sQLiteStatement.bindString(10, pid);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, Event event) {
        databaseStatement.clearBindings();
        Long id = event.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        String evtid = event.getEvtid();
        if (evtid != null) {
            databaseStatement.bindString(2, evtid);
        }
        String evttype = event.getEvttype();
        if (evttype != null) {
            databaseStatement.bindString(3, evttype);
        }
        String content = event.getContent();
        if (content != null) {
            databaseStatement.bindString(4, content);
        }
        String evttime = event.getEvttime();
        if (evttime != null) {
            databaseStatement.bindString(5, evttime);
        }
        String serviceTag = event.getServiceTag();
        if (serviceTag != null) {
            databaseStatement.bindString(6, serviceTag);
        }
        String sessionid = event.getSessionid();
        if (sessionid != null) {
            databaseStatement.bindString(7, sessionid);
        }
        String sessionName = event.getSessionName();
        if (sessionName != null) {
            databaseStatement.bindString(8, sessionName);
        }
        String associationid = event.getAssociationid();
        if (associationid != null) {
            databaseStatement.bindString(9, associationid);
        }
        String pid = event.getPid();
        if (pid != null) {
            databaseStatement.bindString(10, pid);
        }
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(Event event) {
        if (event != null) {
            return event.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(Event event) {
        return event.getId() != null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    protected final boolean isEntityUpdateable() {
        return true;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Event readEntity(Cursor cursor, int i) {
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
        return new Event(lValueOf, string, string2, string3, string4, string5, string6, string7, cursor.isNull(i10) ? null : cursor.getString(i10), cursor.isNull(i11) ? null : cursor.getString(i11));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, Event event, int i) {
        int i2 = i + 0;
        event.setId(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = i + 1;
        event.setEvtid(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 2;
        event.setEvttype(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = i + 3;
        event.setContent(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = i + 4;
        event.setEvttime(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = i + 5;
        event.setServiceTag(cursor.isNull(i7) ? null : cursor.getString(i7));
        int i8 = i + 6;
        event.setSessionid(cursor.isNull(i8) ? null : cursor.getString(i8));
        int i9 = i + 7;
        event.setSessionName(cursor.isNull(i9) ? null : cursor.getString(i9));
        int i10 = i + 8;
        event.setAssociationid(cursor.isNull(i10) ? null : cursor.getString(i10));
        int i11 = i + 9;
        event.setPid(cursor.isNull(i11) ? null : cursor.getString(i11));
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
    public final Long updateKeyAfterInsert(Event event, long j) {
        event.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }
}
