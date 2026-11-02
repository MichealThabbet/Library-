package com.huawei.hms.analytics.database;

/* loaded from: classes2.dex */
public class LogConfig {
    private String ghi;
    private String hij;
    private String ijk;
    private String ikl;
    private String klm;
    private Long lmn;

    public LogConfig() {
    }

    public LogConfig(Long l, String str, String str2, String str3, String str4, String str5) {
        this.lmn = l;
        this.klm = str;
        this.ikl = str2;
        this.ijk = str3;
        this.hij = str4;
        this.ghi = str5;
    }

    public Long getId() {
        return this.lmn;
    }

    public String getLogGroupId() {
        return this.ijk;
    }

    public String getLogStreamId() {
        return this.hij;
    }

    public String getLogTags() {
        return this.ghi;
    }

    public String getProjectId() {
        return this.ikl;
    }

    public String getRegion() {
        return this.klm;
    }

    public void setId(Long l) {
        this.lmn = l;
    }

    public void setLogGroupId(String str) {
        this.ijk = str;
    }

    public void setLogStreamId(String str) {
        this.hij = str;
    }

    public void setLogTags(String str) {
        this.ghi = str;
    }

    public void setProjectId(String str) {
        this.ikl = str;
    }

    public void setRegion(String str) {
        this.klm = str;
    }
}
