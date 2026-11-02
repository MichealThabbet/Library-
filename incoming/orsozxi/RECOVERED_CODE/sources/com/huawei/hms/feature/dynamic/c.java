package com.huawei.hms.feature.dynamic;

import android.os.Bundle;
import com.huawei.hms.common.util.Logger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public final class c {
    private static final String b = c.class.getSimpleName();
    private static final c c = new c();
    public Set<String> a;

    private c() {
    }

    public static c a() {
        return c;
    }

    public final void a(Bundle bundle) {
        ArrayList<String> stringArrayList = bundle.getStringArrayList("installed_module_name");
        if (stringArrayList == null || stringArrayList.isEmpty()) {
            Logger.w(b, "Get installed module name failed.");
            this.a = new HashSet();
        } else {
            Logger.i(b, "Installed module name:".concat(String.valueOf(stringArrayList)));
            this.a = new HashSet(stringArrayList);
        }
    }
}
