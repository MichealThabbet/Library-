package com.huawei.hms.analytics;

import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.database.APIEvent;
import java.util.List;

/* loaded from: classes2.dex */
public final class cg implements Runnable {
    private bc ikl;
    private List<APIEvent> klm;
    private Context lmn;

    public cg(List<APIEvent> list) {
        Context context = ar.lmn().lmn.ghi;
        this.lmn = context;
        this.klm = list;
        this.ikl = bc.lmn(context);
        ce.lmn(this.lmn);
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        List<APIEvent> list = this.klm;
        if (list == null || list.size() == 0) {
            HiLog.w("EventApiUsageRecordMission", "apiEvents is empty, no data save");
            return;
        }
        long jKlm = dl.klm(this.lmn, "latest_upload_time", "apiUsageUploadKey");
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jKlm != -1) {
            if (jCurrentTimeMillis - jKlm > 86400000) {
                HiLog.i("EventApiUsageRecordMission", "api usage cycle is exceeded the threshold.");
                dl.lmn(this.lmn, "latest_upload_time", "apiUsageUploadKey", jCurrentTimeMillis);
                z = true;
            }
            if (!z || this.klm.size() > 20) {
                HiLog.i("EventApiUsageRecordMission", "auto report api data for bi");
                List<APIEvent> listIjk = this.ikl.ijk();
                this.ikl.klm();
                this.klm.addAll(listIjk);
                ce.lmn(this.lmn).lmn(this.klm);
            }
            List<APIEvent> listIjk2 = this.ikl.ijk();
            if (listIjk2.size() + this.klm.size() > 20) {
                HiLog.i("EventApiUsageRecordMission", "auto report api data for bi");
                this.klm.addAll(listIjk2);
                this.ikl.klm();
                ce.lmn(this.lmn).lmn(listIjk2);
                return;
            }
            bc bcVar = this.ikl;
            try {
                bcVar.lmn.getAPIEventDao().insertInTx(this.klm);
                return;
            } catch (SQLiteFullException e) {
                HiLog.w("DBCommander", "SQLite full exception");
                bcVar.lmn.getAPIEventDao().deleteAll();
                di.lmn(e);
                return;
            }
        }
        dl.lmn(this.lmn, "latest_upload_time", "apiUsageUploadKey", jCurrentTimeMillis);
        z = false;
        if (z) {
        }
        HiLog.i("EventApiUsageRecordMission", "auto report api data for bi");
        List<APIEvent> listIjk3 = this.ikl.ijk();
        this.ikl.klm();
        this.klm.addAll(listIjk3);
        ce.lmn(this.lmn).lmn(this.klm);
    }
}
