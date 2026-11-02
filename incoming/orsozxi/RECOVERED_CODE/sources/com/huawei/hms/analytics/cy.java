package com.huawei.hms.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.framework.SyncManager;
import java.util.concurrent.Callable;

/* loaded from: classes2.dex */
public final class cy {
    private static cy lmn;
    private String ikl = "";
    private Task<String> klm;

    static class klm implements OnSuccessListener<String> {
        klm() {
        }

        @Override // com.huawei.hmf.tasks.OnSuccessListener
        public final /* synthetic */ void onSuccess(String str) {
            HiLog.i("AAIDAssignment", "Task<String>.addOnSuccessListener onSuccess!");
        }
    }

    static class lmn implements Callable<String> {
        private String lmn;

        private lmn(String str) {
            this.lmn = str;
        }

        /* synthetic */ lmn(String str, byte b) {
            this(str);
        }

        @Override // java.util.concurrent.Callable
        public final /* synthetic */ String call() {
            HiLog.i("AAIDAssignment", "MyAAIDCallable.call() executed");
            return this.lmn;
        }
    }

    private static synchronized void hij() {
        if (lmn == null) {
            lmn = new cy();
        }
    }

    public static void ijk() {
        k kVar = ar.lmn().lmn.a;
        if (kVar == null) {
            k kVar2 = new k(fgh.lmn().lmn("_openness_config_tag"));
            ar.lmn().lmn.a = kVar2;
            kVar = kVar2;
        }
        if (ar.lmn().lmn.abc) {
            kVar.lmn();
        }
        kVar.lmn(new String[0]);
    }

    public static cy lmn() {
        if (lmn == null) {
            hij();
        }
        return lmn;
    }

    public final synchronized Task<String> ikl() {
        return this.klm;
    }

    public final synchronized void ikl(Context context) {
        SyncManager.getInstance().start(SyncManager.TaskNames.AAID_TASK);
        try {
            try {
                HmsInstanceId.getInstance(context).deleteAAID();
            } catch (NoClassDefFoundError unused) {
                HiLog.e("AAIDAssignment", HiLog.ErrorCode.IE005, "Missing hms opendevice sdk, delete aaid failed, Please upgrade the hms version");
            }
        } catch (Exception unused2) {
            HiLog.w("AAIDAssignment", "delete aaid ApiException");
        }
        lmn(context);
        SyncManager.getInstance().countDown(SyncManager.TaskNames.AAID_TASK);
    }

    public final synchronized String klm() {
        return this.ikl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final synchronized boolean klm(Context context) {
        String str = this.ikl;
        boolean z = false;
        Object[] objArr = 0;
        try {
            this.ikl = HmsInstanceId.getInstance(context).getId();
        } finally {
            return z;
        }
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(this.ikl)) {
            return false;
        }
        boolean z2 = !this.ikl.equals(str);
        if (z2) {
            Task<String> taskCallInBackground = Tasks.callInBackground(new lmn(this.ikl, objArr == true ? 1 : 0));
            this.klm = taskCallInBackground;
            taskCallInBackground.addOnSuccessListener(new klm());
        }
        z = z2;
        return z;
    }

    public final synchronized void lmn(Context context) {
        String str = "1";
        String str2 = "";
        cc ccVarLmn = cd.lmn("HmsInstanceId#getId()");
        try {
            try {
                HiLog.i("AAIDAssignment", "begin sync aaid from opendevice sdk");
                String id = HmsInstanceId.getInstance(context).getId();
                this.ikl = id;
                if (TextUtils.isEmpty(id)) {
                    HiLog.w("AAIDAssignment", HiLog.ErrorCode.SE002, "Failed to obtain AAID  from hms core sdk");
                    this.ikl = "";
                    str2 = HiLog.ErrorCode.SE002;
                } else {
                    HiLog.i("AAIDAssignment", "get aaid success");
                    str = "0";
                }
                Task<String> taskCallInBackground = Tasks.callInBackground(new lmn(this.ikl, (byte) 0));
                this.klm = taskCallInBackground;
                taskCallInBackground.addOnSuccessListener(new klm());
            } catch (Exception e) {
                HiLog.w("AAIDAssignment", HiLog.ErrorCode.SE002, "Failed to obtain AAID from hms core sdk,unknown exception: " + e.getMessage());
                ccVarLmn.lmn = str;
                ccVarLmn.klm = HiLog.ErrorCode.SE002;
                cd.lmn(ccVarLmn);
            } catch (NoClassDefFoundError unused) {
                HiLog.e("AAIDAssignment", HiLog.ErrorCode.IE005, "Missing hms opendevice sdk,get aaid failed.Please upgrade the hms version");
                ccVarLmn.lmn = str;
                ccVarLmn.klm = HiLog.ErrorCode.IE005;
                cd.lmn(ccVarLmn);
            }
        } finally {
            ccVarLmn.lmn = str;
            ccVarLmn.klm = str2;
            cd.lmn(ccVarLmn);
        }
    }
}
