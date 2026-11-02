package com.huawei.hms.analytics.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.huawei.hms.analytics.bg;
import com.huawei.hms.analytics.core.log.HiLog;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

/* loaded from: classes2.dex */
public class DaoManager extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 2;

    public static abstract class klm extends DatabaseOpenHelper {
        public klm(Context context, String str) {
            super(context, str, 2);
        }

        public klm(Context context, String str, byte b) {
            super(context, str, null, 2);
        }

        @Override // org.greenrobot.greendao.database.DatabaseOpenHelper
        public void onCreate(Database database) throws SQLException {
            HiLog.i("greenDAO", "Creating tables for schema version 2");
            DaoManager.createAllTables(database, false);
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
        public final void onUpgrade(Database database, int i, int i2) throws Exception {
            HiLog.i("greenDAO", "Upgrading schema from version " + i + " to " + i2 + " by dropping all tables");
            Class[] clsArr = {EventDao.class};
            bg.klm(database, clsArr);
            DaoManager.createEventTable(database, false);
            bg.lmn(database, (Class<? extends AbstractDao<?, ?>>[]) clsArr);
        }
    }

    public DaoManager(SQLiteDatabase sQLiteDatabase) {
        this(new StandardDatabase(sQLiteDatabase));
    }

    public DaoManager(Database database) {
        super(database, 2);
        registerDaoClass(APIEventDao.class);
        registerDaoClass(EventDao.class);
    }

    public static void createAPIEventTable(Database database, boolean z) throws SQLException {
        APIEventDao.createTable(database, z);
    }

    public static void createAllTables(Database database, boolean z) throws SQLException {
        APIEventDao.createTable(database, z);
        EventDao.createTable(database, z);
    }

    public static void createEventTable(Database database, boolean z) {
        EventDao.createTable(database, z);
    }

    public static void dropAllTables(Database database, boolean z) throws SQLException {
        APIEventDao.dropTable(database, z);
        EventDao.dropTable(database, z);
    }

    public static DaoSession newDevSession(Context context, String str) {
        return new DaoManager(new lmn(context, str).getWritableDb()).newSession();
    }

    @Override // org.greenrobot.greendao.AbstractDaoMaster
    public DaoSession newSession() {
        return new DaoSession(this.db, IdentityScopeType.Session, this.daoConfigMap);
    }

    @Override // org.greenrobot.greendao.AbstractDaoMaster
    public DaoSession newSession(IdentityScopeType identityScopeType) {
        return new DaoSession(this.db, identityScopeType, this.daoConfigMap);
    }
}
