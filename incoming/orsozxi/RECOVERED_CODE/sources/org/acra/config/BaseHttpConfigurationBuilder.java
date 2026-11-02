package org.acra.config;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class BaseHttpConfigurationBuilder {
    private final Map<String, String> httpHeaders = new HashMap();

    BaseHttpConfigurationBuilder() {
    }

    public void setHttpHeaders(Map<String, String> map) {
        this.httpHeaders.clear();
        this.httpHeaders.putAll(map);
    }

    Map<String, String> httpHeaders() {
        return this.httpHeaders;
    }
}
