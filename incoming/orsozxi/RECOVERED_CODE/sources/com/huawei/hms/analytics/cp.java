package com.huawei.hms.analytics;

import android.text.TextUtils;
import com.huawei.hms.analytics.bc;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.database.LogConfig;
import com.huawei.hms.analytics.database.LogEvent;
import com.huawei.hms.analytics.database.LogEventDao;
import com.huawei.hms.analytics.df;
import java.util.Collections;
import java.util.List;
import org.greenrobot.greendao.query.WhereCondition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class cp implements Runnable {
    private final boolean hij;
    private final String ijk;
    private int ikl;
    private be klm;
    private cn lmn;

    public cp(String str, boolean z) {
        this.ijk = str;
        this.hij = z;
    }

    private void lmn() {
        String str;
        if (this.lmn.lmn()) {
            try {
                new cq(this.lmn, this.ijk, this.hij).lmn();
                return;
            } catch (Exception unused) {
                str = "event model json error";
            }
        } else {
            str = "cache log event is error";
        }
        HiLog.e("LgEvtReport", str);
        this.klm.lmn(this.lmn.klm);
        this.lmn.ijk();
    }

    private boolean lmn(LogConfig logConfig, List<LogEvent> list) throws JSONException {
        List<LogEvent> listSubList;
        int i;
        cn cnVar;
        try {
            listSubList = this.ikl > 0 ? list.subList(this.ikl, list.size()) : list;
            i = this.ikl;
            cnVar = this.lmn;
        } catch (IllegalArgumentException | JSONException e) {
            HiLog.w("LgEvtReport", "construct Event JsonArray exception." + e.getMessage());
            this.klm.lmn.klm.deleteInTx(list);
        }
        if (logConfig == null) {
            throw new IllegalArgumentException("log config is null");
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("projectId", logConfig.getProjectId());
        jSONObject.put("groupId", logConfig.getLogGroupId());
        jSONObject.put("streamId", logConfig.getLogStreamId());
        jSONObject.put("tags", TextUtils.isEmpty(logConfig.getLogTags()) ? new JSONObject() : new JSONObject(logConfig.getLogTags()));
        if (!cnVar.ijk.contains(logConfig.getId())) {
            cnVar.ijk.add(logConfig.getId());
        }
        JSONArray jSONArray = new JSONArray();
        int iLmn = cnVar.lmn(jSONArray, listSubList);
        cnVar.ikl = iLmn == listSubList.size();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("events_common", jSONObject);
        jSONObject2.put("events", jSONArray);
        cnVar.lmn.put(jSONObject2);
        int i2 = i + iLmn;
        this.ikl = i2;
        return i2 < list.size();
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (new bu().lmn(this.ijk)) {
            try {
                be beVarLmn = be.lmn();
                this.klm = beVarLmn;
                List<LogConfig> listLoadAll = beVarLmn.lmn.lmn.loadAll();
                if (listLoadAll == null || listLoadAll.size() == 0) {
                    HiLog.w("LgEvtReport", "No have log events in cache");
                    if (this.klm.klm() > 0) {
                        this.klm.ikl();
                        return;
                    }
                    return;
                }
                this.lmn = new cn(listLoadAll.size());
                Collections.sort(listLoadAll, new df.klm());
                for (LogConfig logConfig : listLoadAll) {
                    List<LogEvent> list = this.klm.lmn.klm.queryBuilder().where(LogEventDao.Properties.ikl.eq(logConfig.getId()), new WhereCondition[0]).build().forCurrentThread().list();
                    if (list == null || list.size() == 0) {
                        this.klm.lmn.lmn.delete(logConfig);
                        cn cnVar = this.lmn;
                        cnVar.ghi--;
                    } else {
                        this.ikl = 0;
                        if (TextUtils.isEmpty(this.lmn.fgh)) {
                            this.lmn.fgh = logConfig.getRegion();
                        }
                        while (true) {
                            if (!this.lmn.fgh.equals(logConfig.getRegion()) || lmn(logConfig, list)) {
                                lmn();
                                this.lmn.fgh = logConfig.getRegion();
                            }
                        }
                    }
                }
                lmn();
            } catch (bc.lmn unused) {
                HiLog.e("LgEvtReport", "log db init failed");
            }
        }
    }
}
