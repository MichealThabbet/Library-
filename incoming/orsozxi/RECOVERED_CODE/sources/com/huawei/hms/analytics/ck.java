package com.huawei.hms.analytics;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.m;

/* loaded from: classes2.dex */
public final class ck implements Runnable {
    private String ikl = "0";
    private final cw klm;
    private Context lmn;

    final class lmn implements l {
        private final cc ikl;
        private boolean klm;

        private lmn() {
            this.klm = false;
            this.ikl = cd.lmn("ReferrerTask#ADSReferrer");
        }

        /* synthetic */ lmn(ck ckVar, byte b) {
            this();
        }

        @Override // com.huawei.hms.analytics.l
        public final void lmn(String str) {
            HiLog.i("ReferrerMission", "onSuccess");
            ck.this.lmn(TextUtils.isEmpty(str) ? new cu("") : new cu("PPS", str));
            this.klm = true;
            this.ikl.lmn = "0";
            cd.lmn(this.ikl);
        }

        @Override // com.huawei.hms.analytics.l
        public final void lmn(String str, String str2) {
            HiLog.w("ReferrerMission", "onFail: ".concat(String.valueOf(str)));
            if (this.klm) {
                return;
            }
            ck.this.lmn(new cu(""));
            this.ikl.klm = str2;
            cd.lmn(this.ikl);
        }
    }

    public ck(Context context, bh bhVar) {
        this.lmn = context;
        this.klm = new ct(bhVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x004c A[Catch: all -> 0x00fe, Exception -> 0x0100, CursorIndexOutOfBoundsException -> 0x011b, IllegalStateException -> 0x0136, TryCatch #3 {CursorIndexOutOfBoundsException -> 0x011b, IllegalStateException -> 0x0136, Exception -> 0x0100, blocks: (B:3:0x001a, B:5:0x0037, B:7:0x003b, B:11:0x004c, B:12:0x0057, B:15:0x0077, B:16:0x0082, B:17:0x0086, B:19:0x008c, B:20:0x0098, B:23:0x00ad, B:26:0x00bf, B:28:0x00c8, B:30:0x00d2, B:35:0x00e1, B:34:0x00db, B:36:0x00e4, B:25:0x00b3, B:8:0x0044), top: B:64:0x001a, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0057 A[Catch: all -> 0x00fe, Exception -> 0x0100, CursorIndexOutOfBoundsException -> 0x011b, IllegalStateException -> 0x0136, TRY_LEAVE, TryCatch #3 {CursorIndexOutOfBoundsException -> 0x011b, IllegalStateException -> 0x0136, Exception -> 0x0100, blocks: (B:3:0x001a, B:5:0x0037, B:7:0x003b, B:11:0x004c, B:12:0x0057, B:15:0x0077, B:16:0x0082, B:17:0x0086, B:19:0x008c, B:20:0x0098, B:23:0x00ad, B:26:0x00bf, B:28:0x00c8, B:30:0x00d2, B:35:0x00e1, B:34:0x00db, B:36:0x00e4, B:25:0x00b3, B:8:0x0044), top: B:64:0x001a, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00f7 A[PHI: r6
      0x00f7: PHI (r6v2 com.huawei.hms.analytics.cu) = 
      (r6v0 com.huawei.hms.analytics.cu)
      (r6v0 com.huawei.hms.analytics.cu)
      (r6v0 com.huawei.hms.analytics.cu)
      (r6v4 com.huawei.hms.analytics.cu)
     binds: [B:47:0x0118, B:55:0x014e, B:51:0x0133, B:39:0x00f4] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.huawei.hms.analytics.cu lmn() {
        /*
            Method dump skipped, instructions count: 358
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.ck.lmn():com.huawei.hms.analytics.cu");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lmn(cv cvVar) {
        this.klm.lmn(cvVar);
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.lmn != null) {
            if (!z.lmn() && !z.klm()) {
                lmn(new cu());
                return;
            }
            byte b = 0;
            boolean zKlm = dl.klm(this.lmn, "global_v2", "isLastReferrerQueryFailed", false);
            cu cuVarLmn = lmn();
            if (!zKlm) {
                if ((TextUtils.isEmpty(cuVarLmn.klm) && TextUtils.isEmpty(cuVarLmn.lmn)) ? false : true) {
                    lmn(cuVarLmn);
                    return;
                }
            }
            m mVar = new m(this.lmn, new lmn(this, b));
            HiLog.i("PpsServiceCommander", "ads bindService..");
            if (mVar.lmn != null) {
                mVar.klm = new m.lmn(mVar, (byte) 0);
                Intent intent = new Intent("com.huawei.android.hms.CHANNEL_SERVICE");
                try {
                    intent.setPackage("com.huawei.hwid");
                    if (mVar.lmn.bindService(intent, mVar.klm, 1)) {
                        HiLog.i("PpsServiceCommander", "bindService result: true");
                    } else {
                        mVar.ikl.lmn("bindService result: false", "009");
                    }
                } catch (IllegalArgumentException unused) {
                    mVar.ikl.lmn("setPackage IllegalArgumentException", "103");
                }
            }
        }
    }
}
