package com.huawei.hms.analytics.database;

/* loaded from: classes2.dex */
public class LogEvent {
    private String hij;
    private String ijk;
    private Long ikl;
    private String klm;
    private Long lmn;

    public LogEvent() {
    }

    public LogEvent(Long l, String str, Long l2, String str2, String str3) {
        this.lmn = l;
        this.klm = str;
        this.ikl = l2;
        this.ijk = str2;
        this.hij = str3;
    }

    public Long getConfigId() {
        return this.ikl;
    }

    public String getContent() {
        return this.ijk;
    }

    public String getEventId() {
        return this.hij;
    }

    public String getEvtTime() {
        return this.klm;
    }

    public Long getId() {
        return this.lmn;
    }

    public void setConfigId(Long l) {
        this.ikl = l;
    }

    public void setContent(String str) {
        this.ijk = str;
    }

    public void setEventId(String str) {
        this.hij = str;
    }

    public void setEvtTime(String str) {
        this.klm = str;
    }

    public void setId(Long l) {
        this.lmn = l;
    }
}
