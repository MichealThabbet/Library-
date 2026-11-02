package com.huawei.hms.analytics.core.transport.net;

/* loaded from: classes2.dex */
public class Response {
    private String content;
    private int httpCode;

    public Response(int i, String str) {
        this.httpCode = i;
        this.content = str;
    }

    public String getContent() {
        return this.content;
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setHttpCode(int i) {
        this.httpCode = i;
    }
}
