package com.huawei.hms.analytics;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.lmn;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class m {
    public l ikl;
    public ServiceConnection klm;
    public Context lmn;

    public final class lmn implements ServiceConnection {
        private lmn() {
        }

        public /* synthetic */ lmn(m mVar, byte b) {
            this();
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            l lVar;
            String message;
            String str;
            com.huawei.hms.analytics.lmn lmnVarLmn;
            l lVar2;
            String str2;
            String str3;
            HiLog.i("PpsServiceCommander", "onServiceConnected");
            try {
                try {
                    try {
                        lmnVarLmn = lmn.AbstractBinderC0053lmn.lmn(iBinder);
                    } catch (JSONException unused) {
                        lVar = m.this.ikl;
                        message = "parseChannelJson: json exception";
                        str = "102";
                        lVar.lmn(message, str);
                    }
                } catch (RemoteException e) {
                    HiLog.e("PpsServiceCommander", "getChannelInfo Exception");
                    lVar = m.this.ikl;
                    message = e.getMessage();
                    str = "998";
                    lVar.lmn(message, str);
                }
                if (lmnVarLmn == null) {
                    lVar2 = m.this.ikl;
                    str2 = "pps service is null";
                    str3 = "006";
                } else {
                    String strLmn = lmnVarLmn.lmn();
                    if (!TextUtils.isEmpty(strLmn)) {
                        m.this.ikl.lmn(new JSONObject(strLmn).optString("channelInfo", ""));
                    } else {
                        lVar2 = m.this.ikl;
                        str2 = "channelInfo is empty";
                        str3 = "007";
                    }
                }
                lVar2.lmn(str2, str3);
            } finally {
                m.this.lmn();
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            HiLog.i("PpsServiceCommander", "onServiceDisconnected");
            m.this.ikl.lmn("onServiceDisconnected", "008");
        }
    }

    public m(Context context, l lVar) {
        this.lmn = context;
        this.ikl = lVar;
    }

    public final void lmn() {
        ServiceConnection serviceConnection;
        HiLog.i("PpsServiceCommander", "ads unBindService..");
        Context context = this.lmn;
        if (context == null || (serviceConnection = this.klm) == null) {
            return;
        }
        context.unbindService(serviceConnection);
    }
}
