package com.huawei.hms.analytics.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.huawei.hms.analytics.bf;
import com.huawei.hms.analytics.core.log.HiLog;
import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

/* loaded from: classes2.dex */
public class LogEventDaoManager extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    public static abstract class klm extends DatabaseOpenHelper {
        public klm(Context context, String str) {
            super(context, str, 1);
        }

        public klm(Context context, String str, byte b) {
            super(context, str, null, 1);
        }

        @Override // org.greenrobot.greendao.database.DatabaseOpenHelper
        public void onCreate(Database database) throws SQLException {
            HiLog.i("greenDAO", "Creating tables for schema version 1");
            LogEventDaoManager.createAllTables(database, false);
        }
    }

    public static class lmn extends klm {
        public lmn(Context context, String str) {
            super(context, str);
        }

        public lmn(Context context, String str, byte b) {
            super(context, str, (byte) 0);
        }

        @Override // org.greenrobot.greendao.database.DatabaseOpenHelper
        public final void onUpgrade(Database database, int i, int i2) {
            HiLog.i("greenDAO", "Upgrading schema from version " + i + " to " + i2 + " by dropping all tables");
        }
    }

    public LogEventDaoManager(SQLiteDatabase sQLiteDatabase) {
        this(new StandardDatabase(sQLiteDatabase));
    }

    public LogEventDaoManager(Database database) {
        super(database, 1);
        registerDaoClass(LogEventDao.class);
        registerDaoClass(LogConfigDao.class);
    }

    public static void createAllTables(Database database, boolean z) throws SQLException {
        LogEventDao.createTable(database, z);
        LogConfigDao.createTable(database, z);
    }

    public static bf newDevSession(Context context, String str) {
        return new LogEventDaoManager(new lmn(context, str).getWritableDb()).newSession();
    }

    @Override // org.greenrobot.greendao.AbstractDaoMaster
    public bf newSession() {
        return new bf(this.db, IdentityScopeType.Session, this.daoConfigMap);
    }

    @Override // org.greenrobot.greendao.AbstractDaoMaster
    public bf newSession(IdentityScopeType identityScopeType) {
        return new bf(this.db, identityScopeType, this.daoConfigMap);
    }
}
