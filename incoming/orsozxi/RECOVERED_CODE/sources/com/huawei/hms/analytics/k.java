package com.huawei.hms.analytics;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.analytics.cc;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.h;
import com.huawei.hms.api.HuaweiMobileServicesUtil;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import net.fortuna.ical4j.model.property.RequestStatus;

/* loaded from: classes2.dex */
public final class k {
    private boolean klm;
    private final h lmn;

    static class lmn implements cc.lmn {
        lmn() {
        }

        @Override // com.huawei.hms.analytics.cc.lmn
        public final void lmn() {
            cc ccVarLmn = cd.lmn("BindService");
            ccVarLmn.lmn = "0";
            cd.lmn(ccVarLmn);
        }

        @Override // com.huawei.hms.analytics.cc.lmn
        public final void lmn(String str) {
            cc ccVarLmn = cd.lmn("BindService");
            ccVarLmn.lmn = str;
            cd.lmn(ccVarLmn);
        }
    }

    public k(bh bhVar) {
        this.lmn = new h(ar.lmn().lmn.ghi, bhVar, new lmn());
    }

    public final void lmn() {
        try {
            if (this.klm) {
                this.lmn.lmn();
                this.klm = false;
            }
        } catch (Exception unused) {
            HiLog.w("ServiceWatcher", "service unbind exception");
        }
    }

    public final void lmn(String... strArr) throws InterruptedException {
        HiLog.d("ServiceWatcher", "service bind");
        try {
            if (this.klm) {
                return;
            }
            h hVar = this.lmn;
            HiLog.i("HAServiceCommander", "bindService is running");
            if (ar.lmn().lmn.abc) {
                HiLog.i("HAServiceCommander", "Service is already bind, when want to bind.");
            } else if (HuaweiMobileServicesUtil.isHuaweiMobileServicesAvailable(hVar.klm, 40000000) != 0) {
                HiLog.w("HAServiceCommander", HiLog.ErrorCode.IE005, "Missing hms core sdk,or HMS version is too low");
                if (hVar.ijk != null) {
                    hVar.ijk.lmn(RequestStatus.CLIENT_ERROR);
                }
            } else {
                hVar.hij.lmn = strArr.length == 1 ? strArr[0] : "";
                hVar.lmn = new CountDownLatch(1);
                Task<TResult> taskDoWrite = hVar.ikl.doWrite(new t("hianalytics.analyticsEvtService", ""));
                taskDoWrite.addOnFailureListener(new h.lmn(hVar.ijk, hVar.lmn)).addOnSuccessListener(new h.klm(taskDoWrite, hVar.klm, hVar, hVar.ijk, hVar.lmn));
            }
            h hVar2 = this.lmn;
            if (hVar2.lmn != null) {
                hVar2.lmn.await(500L, TimeUnit.MILLISECONDS);
            }
            this.klm = ar.lmn().lmn.abc;
        } catch (Exception unused) {
            HiLog.w("ServiceWatcher", "service bind exception");
        }
    }
}
