package com.huawei.hms.adapter;

import android.content.Context;
import com.huawei.hms.common.internal.Objects;
import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes2.dex */
public class OuterBinderAdapter extends BinderAdapter {
    private static final Object LOCK_OBJECT_INIT = new Object();
    private static final int MSG_CONN_TIMEOUT = 1001;
    private static final int MSG_DELAY_DISCONNECT = 1002;
    private static final String TAG = "OuterBinderAdapter";
    private static BinderAdapter adapter;
    private static String sActionName;
    private static String sServiceName;

    private OuterBinderAdapter(Context context, String str, String str2) {
        super(context, str, str2);
    }

    public static BinderAdapter getInstance(Context context, String str, String str2) {
        BinderAdapter binderAdapter;
        HMSLog.i(TAG, "OuterBinderAdapter getInstance.");
        synchronized (LOCK_OBJECT_INIT) {
            if (adapter == null) {
                sActionName = str;
                sServiceName = str2;
                adapter = new OuterBinderAdapter(context, str, str2);
            } else {
                if ((Objects.equal(sActionName, str) && Objects.equal(sServiceName, str2)) ? false : true) {
                    HMSLog.i(TAG, "OuterBinderAdapter getInstance refresh adapter");
                    sActionName = str;
                    sServiceName = str2;
                    adapter.unBind();
                    adapter = new OuterBinderAdapter(context, str, str2);
                }
            }
            binderAdapter = adapter;
        }
        return binderAdapter;
    }

    @Override // com.huawei.hms.adapter.BinderAdapter
    protected int getConnTimeOut() {
        return 1001;
    }

    @Override // com.huawei.hms.adapter.BinderAdapter
    protected int getMsgDelayDisconnect() {
        return 1002;
    }
}
