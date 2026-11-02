package com.huawei.hms.analytics;

import android.content.Context;
import com.huawei.hms.analytics.database.APIEvent;
import com.huawei.hms.support.hianalytics.HiAnalyticsUtil;
import com.huawei.hms.utils.HMSBIInitializer;
import java.util.List;

/* loaded from: classes2.dex */
public final class ce {
    private static ce lmn;
    private Context klm;

    private ce(Context context) {
        this.klm = context;
        HMSBIInitializer.getInstance(context).initBI();
    }

    private static synchronized void klm(Context context) {
        if (lmn == null) {
            lmn = new ce(context);
        }
    }

    public static ce lmn(Context context) {
        if (lmn == null) {
            klm(context);
        }
        return lmn;
    }

    public final void lmn(List<APIEvent> list) {
        for (APIEvent aPIEvent : list) {
            HiAnalyticsUtil.getInstance().onNewEvent(this.klm, aPIEvent.getCode(), aPIEvent.toMap());
        }
    }
}
