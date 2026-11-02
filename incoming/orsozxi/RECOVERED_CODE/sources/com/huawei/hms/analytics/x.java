package com.huawei.hms.analytics;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.huawei.hms.analytics.core.log.HiLog;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
final class x {
    private String ghi;
    private String hij;
    private Intent ijk;
    private final w ikl;
    lmn klm;
    final Context lmn;

    public static final class lmn implements ServiceConnection {
        private final w ikl;
        private boolean klm;
        private final LinkedBlockingQueue<IBinder> lmn = new LinkedBlockingQueue<>(1);

        public lmn(w wVar) {
            this.ikl = wVar;
        }

        public final IBinder lmn() {
            if (this.klm) {
                throw new IllegalStateException();
            }
            this.klm = true;
            return this.lmn.poll(1000L, TimeUnit.MILLISECONDS);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.lmn.offer(iBinder, 300L, TimeUnit.MILLISECONDS);
            } catch (Throwable unused) {
                HiLog.w("idServiceConn", "onServiceConnected handler Ibinder error");
                this.ikl.lmn("getOaid error,begin get gaid");
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            HiLog.w("idServiceConn", "onServiceDisconnected");
        }
    }

    public x(Context context, Intent intent, w wVar) {
        this.lmn = context;
        this.ijk = intent;
        this.ikl = wVar;
    }

    public x(Context context, String str, String str2, w wVar) {
        this.lmn = context;
        this.hij = str;
        this.ghi = str2;
        this.ikl = wVar;
    }

    public final void lmn() {
        if (this.ijk == null) {
            Intent intent = new Intent(this.hij);
            this.ijk = intent;
            intent.setPackage(this.ghi);
        }
        lmn lmnVar = new lmn(this.ikl);
        this.klm = lmnVar;
        boolean zBindService = this.lmn.bindService(this.ijk, lmnVar, 1);
        HiLog.i("oaidService", "isBind: ".concat(String.valueOf(zBindService)));
        if (zBindService) {
            this.ikl.lmn(this.klm);
        } else {
            this.ikl.lmn("bindService fail,begin get gaid");
        }
    }
}
