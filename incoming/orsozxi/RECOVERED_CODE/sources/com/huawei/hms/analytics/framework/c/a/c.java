package com.huawei.hms.analytics.framework.c.a;

import com.huawei.hms.analytics.core.crypto.AesCipher;
import com.huawei.hms.analytics.core.log.HiLog;
import com.huawei.hms.analytics.core.storage.Event;
import com.huawei.hms.analytics.core.storage.IStorageHandler;
import com.huawei.hms.analytics.framework.config.DeviceAttributeCollector;
import com.huawei.hms.analytics.framework.config.EvtHeaderAttributeCollector;
import com.huawei.hms.analytics.framework.config.ICollectorConfig;
import com.huawei.hms.analytics.framework.config.RomAttributeCollector;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class c {
    public static final Charset a = Charset.forName("UTF-8");
    private DeviceAttributeCollector b;
    private EvtHeaderAttributeCollector c;
    private RomAttributeCollector d;
    private String e;
    private String f;
    private List<Event> g;
    private ICollectorConfig h;

    public c(DeviceAttributeCollector deviceAttributeCollector, EvtHeaderAttributeCollector evtHeaderAttributeCollector, RomAttributeCollector romAttributeCollector, String str, String str2) {
        this.b = deviceAttributeCollector;
        this.c = evtHeaderAttributeCollector;
        this.d = romAttributeCollector;
        this.f = str;
        this.e = str2;
        this.h = com.huawei.hms.analytics.framework.b.b.a().a(str2);
    }

    private void a(JSONArray jSONArray, Event event, boolean z) throws JSONException {
        String content = event.getContent();
        if (!z) {
            content = com.huawei.hms.analytics.framework.c.b.a(content, com.huawei.hms.analytics.framework.b.b.a().a);
        }
        event.setContent(content);
        JSONObject json = event.toJson();
        if (json != null) {
            jSONArray.put(json);
            return;
        }
        if (this.g == null) {
            this.g = new ArrayList();
        }
        this.g.add(event);
        HiLog.w("UploadEvtBean", "custom event is empty,delete this event");
    }

    public final JSONObject a(List<Event> list, boolean z) throws JSONException {
        IStorageHandler iStorageHandlerA;
        if (list == null || list.size() == 0) {
            HiLog.w("UploadEvtBean", "Not have actionEvent to send");
            return null;
        }
        if (this.c == null || this.b == null || this.d == null) {
            HiLog.w("UploadEvtBean", "one attributeModel is null");
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObjectDoCollector = this.c.doCollector();
        if (jSONObjectDoCollector == null) {
            HiLog.w("UploadEvtBean", "headerJson is null");
            return null;
        }
        jSONObject.put("header", jSONObjectDoCollector);
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObjectDoCollector2 = this.b.doCollector();
        jSONObjectDoCollector2.put("properties", this.d.doCollector());
        jSONObject2.put("events_common", jSONObjectDoCollector2);
        JSONArray jSONArray = new JSONArray();
        for (Event event : list) {
            if (!"$AppPushToken".equals(event.getEvtid()) || !this.h.isDebugModel()) {
                try {
                    a(jSONArray, event.m18clone(), z);
                } catch (CloneNotSupportedException unused) {
                    HiLog.w("UploadEvtBean", "CloneNotSupportedException");
                    a(jSONArray, event, z);
                }
            }
        }
        List<Event> list2 = this.g;
        if (list2 != null && list2.size() > 0 && (iStorageHandlerA = com.huawei.hms.analytics.framework.a.a.a(this.e)) != null) {
            iStorageHandlerA.deleteEvents(this.g);
        }
        if (jSONArray.length() == 0) {
            HiLog.i("UploadEvtBean", "send data is empty");
            return null;
        }
        jSONObject2.put("events", jSONArray);
        jSONObject.put("event", AesCipher.encryptCbc(com.huawei.hms.analytics.framework.g.a.a(jSONObject2.toString().getBytes(a)), this.f));
        return jSONObject;
    }
}
