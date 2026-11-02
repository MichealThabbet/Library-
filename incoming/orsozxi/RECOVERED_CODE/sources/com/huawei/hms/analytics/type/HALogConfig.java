package com.huawei.hms.analytics.type;

import java.util.Map;

/* loaded from: classes2.dex */
public class HALogConfig {
    private Map<String, String> hij;
    private String ijk;
    private String ikl;
    private String klm;
    private String lmn;

    public HALogConfig() {
    }

    public HALogConfig(HALogConfig hALogConfig) {
        this.ijk = hALogConfig.getRegion();
        this.lmn = hALogConfig.getProjectId();
        this.klm = hALogConfig.getLogGroupId();
        this.ikl = hALogConfig.getLogStreamId();
        this.hij = hALogConfig.getTags();
    }

    public HALogConfig(String str, String str2, String str3, String str4, Map<String, String> map) {
        this.ijk = str;
        this.lmn = str2;
        this.klm = str3;
        this.ikl = str4;
        this.hij = map;
    }

    public String getLogGroupId() {
        return this.klm;
    }

    public String getLogStreamId() {
        return this.ikl;
    }

    public String getProjectId() {
        return this.lmn;
    }

    public String getRegion() {
        return this.ijk;
    }

    public Map<String, String> getTags() {
        return this.hij;
    }

    public void setLogGroupId(String str) {
        this.klm = str;
    }

    public void setLogStreamId(String str) {
        this.ikl = str;
    }

    public void setProjectId(String str) {
        this.lmn = str;
    }

    public void setRegion(String str) {
        this.ijk = str;
    }

    public void setTags(Map<String, String> map) {
        this.hij = map;
    }
}
