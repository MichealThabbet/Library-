package com.huawei.hms.analytics.framework.c;

import com.huawei.hms.analytics.core.storage.Event;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class c {
    public static List<Event> a(String str, String str2, boolean z) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        List<JSONObject> customEventJson = com.huawei.hms.analytics.framework.b.b.a().a(str).getCustomEventJson(jCurrentTimeMillis);
        if (customEventJson == null || customEventJson.size() <= 0) {
            return null;
        }
        d dVar = new d(str, str2, customEventJson, jCurrentTimeMillis);
        dVar.a = z;
        ArrayList arrayList = new ArrayList();
        Iterator<JSONObject> it2 = customEventJson.iterator();
        while (it2.hasNext()) {
            dVar.a(arrayList, it2.next());
        }
        if (arrayList.size() > 0) {
            return arrayList;
        }
        return null;
    }
}
