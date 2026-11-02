package com.huawei.hms.analytics;

import com.huawei.hms.analytics.bc;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.database.LogConfig;
import com.huawei.hms.analytics.database.LogConfigDao;
import com.huawei.hms.analytics.database.LogEvent;
import com.huawei.hms.analytics.framework.policy.IStoragePolicy;
import com.huawei.hms.analytics.type.HALogConfig;
import com.huawei.hms.analytics.type.HAParamType;
import org.greenrobot.greendao.query.WhereCondition;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class co implements Runnable {
    private be hij;
    private final String ijk;
    private final String klm;
    private final HALogConfig lmn;
    private final String ikl = String.valueOf(System.currentTimeMillis());
    private final bu ghi = new bu();

    public co(HALogConfig hALogConfig, String str, String str2) {
        this.lmn = hALogConfig;
        this.klm = str2;
        this.ijk = str;
    }

    private long lmn(LogConfig logConfig) throws JSONException {
        long jLmn;
        String strValueOf;
        String str;
        JSONObject jSONObjectLmn = dd.lmn(this.lmn.getTags());
        if (logConfig != null) {
            logConfig.setLogTags(jSONObjectLmn != null ? jSONObjectLmn.toString() : "");
            this.hij.klm(logConfig);
            jLmn = logConfig.getId().longValue();
            strValueOf = String.valueOf(jLmn);
            str = "update indexId: ";
        } else {
            LogConfig logConfig2 = new LogConfig();
            logConfig2.setLogTags(jSONObjectLmn != null ? jSONObjectLmn.toString() : "");
            logConfig2.setLogGroupId(this.lmn.getLogGroupId());
            logConfig2.setLogStreamId(this.lmn.getLogStreamId());
            logConfig2.setRegion(this.lmn.getRegion());
            logConfig2.setProjectId(this.lmn.getProjectId());
            jLmn = this.hij.lmn(logConfig2);
            strValueOf = String.valueOf(jLmn);
            str = "insert indexId: ";
        }
        HiLog.d("LogEvtRecord", str.concat(strValueOf));
        return jLmn;
    }

    private void lmn(Long l) {
        LogEvent logEvent = new LogEvent();
        logEvent.setConfigId(l);
        logEvent.setEvtTime(this.ikl);
        try {
            logEvent.setContent(new JSONObject().put(HAParamType.CONTENT, this.klm).toString());
        } catch (JSONException unused) {
            HiLog.w("LogEvtRecord", "setContent json exception");
        }
        logEvent.setEventId(this.ijk);
        this.hij.lmn(logEvent);
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            be beVarLmn = be.lmn();
            this.hij = beVarLmn;
            LogConfig logConfigUnique = beVarLmn.lmn.lmn.queryBuilder().where(LogConfigDao.Properties.klm.eq(this.lmn.getRegion()), new WhereCondition[0]).where(LogConfigDao.Properties.ikl.eq(this.lmn.getProjectId()), new WhereCondition[0]).where(LogConfigDao.Properties.ijk.eq(this.lmn.getLogGroupId()), new WhereCondition[0]).where(LogConfigDao.Properties.hij.eq(this.lmn.getLogStreamId()), new WhereCondition[0]).build().forCurrentThread().unique();
            if (this.ghi.decide(IStoragePolicy.PolicyType.STORAGELENGTH, "ha_levt.db") || this.hij.klm() > 5000) {
                HiLog.w("LogEvtRecord", "log db file reach max limited size,clear it");
                this.hij.ikl();
                lmn(Long.valueOf(lmn(logConfigUnique)));
                return;
            }
            lmn(Long.valueOf(lmn(logConfigUnique)));
            long jKlm = this.hij.klm();
            HiLog.d("LogEvtRecord", "count: ".concat(String.valueOf(jKlm)));
            if (this.ghi.decide(IStoragePolicy.PolicyType.STORAGESIZE, jKlm) && this.ghi.decide(IStoragePolicy.PolicyType.NETWORK, "")) {
                at atVar = ar.lmn().lmn;
                long j = atVar.l;
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - j <= 30000) {
                    HiLog.w("LogEvtRecord", "log autoReport timeout. interval < 30s ");
                    return;
                }
                bh bhVarLmn = fgh.lmn().lmn("_openness_config_tag");
                if (bhVarLmn != null) {
                    bhVarLmn.klm();
                    atVar.l = jCurrentTimeMillis;
                }
            }
        } catch (bc.lmn unused) {
            HiLog.e("LogEvtRecord", "log db init failed");
        }
    }
}
