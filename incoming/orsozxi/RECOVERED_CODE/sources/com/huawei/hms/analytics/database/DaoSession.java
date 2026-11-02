package com.huawei.hms.analytics.database;

import com.huawei.hms.analytics.core.storage.Event;
import java.util.Map;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

/* loaded from: classes2.dex */
public class DaoSession extends AbstractDaoSession {
    private final APIEventDao aPIEventDao;
    private final DaoConfig aPIEventDaoConfig;
    private final EventDao eventDao;
    private final DaoConfig eventDaoConfig;

    public DaoSession(Database database, IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map) {
        super(database);
        DaoConfig daoConfigClone = map.get(APIEventDao.class).clone();
        this.aPIEventDaoConfig = daoConfigClone;
        daoConfigClone.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone2 = map.get(EventDao.class).clone();
        this.eventDaoConfig = daoConfigClone2;
        daoConfigClone2.initIdentityScope(identityScopeType);
        this.aPIEventDao = new APIEventDao(this.aPIEventDaoConfig, this);
        this.eventDao = new EventDao(this.eventDaoConfig, this);
        registerDao(APIEvent.class, this.aPIEventDao);
        registerDao(Event.class, this.eventDao);
    }

    public void clear() {
        this.aPIEventDaoConfig.clearIdentityScope();
        this.eventDaoConfig.clearIdentityScope();
    }

    public APIEventDao getAPIEventDao() {
        return this.aPIEventDao;
    }

    public EventDao getEventDao() {
        return this.eventDao;
    }
}
