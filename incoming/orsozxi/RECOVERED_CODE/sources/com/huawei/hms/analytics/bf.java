package com.huawei.hms.analytics;

import com.huawei.hms.analytics.database.LogConfig;
import com.huawei.hms.analytics.database.LogConfigDao;
import com.huawei.hms.analytics.database.LogEvent;
import com.huawei.hms.analytics.database.LogEventDao;
import java.util.Map;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

/* loaded from: classes2.dex */
public final class bf extends AbstractDaoSession {
    private final DaoConfig ijk;
    private final DaoConfig ikl;
    public final LogEventDao klm;
    public final LogConfigDao lmn;

    public bf(Database database, IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map) {
        super(database);
        DaoConfig daoConfigClone = map.get(LogEventDao.class).clone();
        this.ikl = daoConfigClone;
        daoConfigClone.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone2 = map.get(LogConfigDao.class).clone();
        this.ijk = daoConfigClone2;
        daoConfigClone2.initIdentityScope(identityScopeType);
        this.klm = new LogEventDao(this.ikl, this);
        this.lmn = new LogConfigDao(this.ijk, this);
        registerDao(LogEvent.class, this.klm);
        registerDao(LogConfig.class, this.lmn);
    }

    public final void lmn() {
        this.ikl.clearIdentityScope();
        this.ijk.clearIdentityScope();
    }
}
