package com.huawei.hms.analytics.framework.config;

import com.huawei.hms.analytics.core.storage.Event;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public interface ICollectorConfig {
    String getAppId();

    String[] getCollectUrls(String str, String str2);

    List<JSONObject> getCustomEventJson(long j);

    DeviceAttributeCollector getDeviceAttribute(String str);

    EvtHeaderAttributeCollector getEvtCustomHeader(String str, JSONObject jSONObject);

    Map<String, String> getHttpHeader(String str);

    String getRegion();

    RomAttributeCollector getRomAttribute(String str);

    Event getSpecialEvent(String str);

    void handlerThrowable(Throwable th);

    boolean isDebugModel();

    boolean isEnableSession(String str);

    void serviceListener(boolean z, String str);

    void setRegion(String str);

    void syncOaid();
}
