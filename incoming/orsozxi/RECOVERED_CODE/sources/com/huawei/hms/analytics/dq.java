package com.huawei.hms.analytics;

import android.content.Context;
import android.os.Build;
import android.os.UserManager;

/* loaded from: classes2.dex */
public final class dq {
    private static final dq lmn = new dq();
    private boolean klm = false;

    public static dq lmn() {
        return lmn;
    }

    public final boolean lmn(Context context) {
        boolean zIsUserUnlocked;
        if (!this.klm) {
            if (context == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= 24) {
                UserManager userManager = (UserManager) context.getSystemService("user");
                if (userManager != null) {
                    zIsUserUnlocked = userManager.isUserUnlocked();
                } else {
                    this.klm = false;
                }
            } else {
                zIsUserUnlocked = true;
            }
            this.klm = zIsUserUnlocked;
        }
        return this.klm;
    }
}
