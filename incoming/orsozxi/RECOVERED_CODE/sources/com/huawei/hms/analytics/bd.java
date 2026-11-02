package com.huawei.hms.analytics;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteFullException;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.core.storage.IStorageHandler;
import com.huawei.hms.analytics.database.EventDao;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.query.WhereCondition;

/* loaded from: classes2.dex */
public final class bd implements IStorageHandler {
    private static IStorageHandler lmn;
    private bc klm;

    private bd(Context context) {
        this.klm = bc.lmn(context);
    }

    private static synchronized void klm(Context context) {
        if (lmn == null) {
            lmn = new bd(context);
        }
    }

    public static IStorageHandler lmn(Context context) {
        if (lmn == null) {
            klm(context);
        }
        return lmn;
    }

    @Override // com.huawei.hms.analytics.core.storage.IStorageHandler
    public final long count(String str) {
        return this.klm.lmn.getEventDao().queryBuilder().where(EventDao.Properties.ghi.eq(str), new WhereCondition[0]).count();
    }

    @Override // com.huawei.hms.analytics.core.storage.IStorageHandler
    public final void deleteAll() {
        this.klm.lmn();
    }

    @Override // com.huawei.hms.analytics.core.storage.IStorageHandler
    public final void deleteByTag(String str) throws SQLException {
        this.klm.lmn(str);
    }

    @Override // com.huawei.hms.analytics.core.storage.IStorageHandler
    public final void deleteByTagType(String str, String str2) throws SQLException {
        this.klm.lmn(str, str2);
    }

    @Override // com.huawei.hms.analytics.core.storage.IStorageHandler
    public final void deleteEvents(List<Event> list) {
        this.klm.lmn.getEventDao().deleteInTx(list);
    }

    @Override // com.huawei.hms.analytics.core.storage.IStorageHandler
    public final long insert(Event event) {
        try {
            return this.klm.lmn.getEventDao().insert(event);
        } catch (SQLiteFullException e) {
            HiLog.w("StorageHandler", "SQLite full exception");
            this.klm.lmn();
            di.lmn(e);
            return 0L;
        }
    }

    @Override // com.huawei.hms.analytics.core.storage.IStorageHandler
    public final void insertEx(List<Event> list) {
        try {
            this.klm.lmn(list);
        } catch (SQLiteFullException e) {
            HiLog.w("StorageHandler", "SQLite full exception");
            this.klm.lmn();
            di.lmn(e);
        }
    }

    @Override // com.huawei.hms.analytics.core.storage.IStorageHandler
    public final List<Event> readBySql(String str) {
        return this.klm.lmn.getEventDao().queryBuilder().where(new WhereCondition.StringCondition(str), new WhereCondition[0]).build().forCurrentThread().list();
    }

    @Override // com.huawei.hms.analytics.core.storage.IStorageHandler
    public final List<Event> readEvents(String str) throws SQLException {
        try {
            return this.klm.lmn.getEventDao().queryBuilder().where(EventDao.Properties.ghi.eq(str), new WhereCondition[0]).build().forCurrentThread().list();
        } catch (SQLiteBlobTooBigException e) {
            HiLog.w("StorageHandler", "SQLite blob too big exception");
            this.klm.lmn(str);
            di.lmn(e);
            return new ArrayList();
        }
    }

    @Override // com.huawei.hms.analytics.core.storage.IStorageHandler
    public final List<Event> readEvents(String str, String str2) throws SQLException {
        try {
            return this.klm.lmn.getEventDao().queryBuilder().where(EventDao.Properties.ghi.eq(str), new WhereCondition[0]).where(EventDao.Properties.ikl.eq(str2), new WhereCondition[0]).build().forCurrentThread().list();
        } catch (SQLiteBlobTooBigException e) {
            HiLog.w("StorageHandler", "SQLite blob too big exception");
            this.klm.lmn(str, str2);
            di.lmn(e);
            return new ArrayList();
        }
    }

    @Override // com.huawei.hms.analytics.core.storage.IStorageHandler
    public final List<Event> readEvents(String str, String str2, int i) throws SQLException {
        try {
            return this.klm.lmn.getEventDao().queryBuilder().where(EventDao.Properties.ghi.eq(str), new WhereCondition[0]).where(EventDao.Properties.ikl.eq(str2), new WhereCondition[0]).limit(i).build().forCurrentThread().list();
        } catch (SQLiteBlobTooBigException e) {
            HiLog.w("StorageHandler", "SQLite blob too big exception");
            this.klm.lmn(str, str2);
            di.lmn(e);
            return new ArrayList();
        }
    }
}
