package com.huawei.hms.analytics;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class ay {
    public String fgh;
    public String ghi;
    public String ijk;
    public String ikl;
    public String klm;
    public Map<String, String[]> lmn = new HashMap();
    public final Map<String, String> hij = new HashMap();

    public final String lmn(String str) {
        Map<String, String> map = this.hij;
        return (map == null || !map.containsKey(str)) ? this.ghi : this.hij.get(str);
    }
}
