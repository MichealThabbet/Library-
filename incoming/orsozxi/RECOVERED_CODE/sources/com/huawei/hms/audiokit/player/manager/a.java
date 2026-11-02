package com.huawei.hms.audiokit.player.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.huawei.hms.adapter.AvailableAdapter;

/* loaded from: classes2.dex */
class a {
    public static int a(Context context, int i) {
        String str;
        Log.i("HmsUpdateUtils", "checkHmsAvailable");
        if (context == null) {
            Log.e("HmsUpdateUtils", "context is null");
            return 6;
        }
        AvailableAdapter availableAdapter = new AvailableAdapter(i);
        int iIsHuaweiMobileServicesAvailable = availableAdapter.isHuaweiMobileServicesAvailable(context);
        if (iIsHuaweiMobileServicesAvailable == 0) {
            return 0;
        }
        if (availableAdapter.isUserResolvableError(iIsHuaweiMobileServicesAvailable)) {
            Log.i("HmsUpdateUtils", "HMS try to update");
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (activity.isDestroyed()) {
                    return 8;
                }
                availableAdapter.startResolution(activity, null);
                return 8;
            }
            str = "isHuaweiMobileServicesAvailable result " + iIsHuaweiMobileServicesAvailable;
        } else {
            str = "HMS not available";
        }
        Log.e("HmsUpdateUtils", str);
        return 7;
    }
}
