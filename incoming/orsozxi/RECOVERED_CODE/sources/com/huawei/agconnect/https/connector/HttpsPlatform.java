package com.huawei.agconnect.https.connector;

/* loaded from: classes2.dex */
public class HttpsPlatform {
    private static final HttpsPlatform INSTANCE = new HttpsPlatform();
    private Connector connector;

    private HttpsPlatform() {
    }

    public static HttpsPlatform getInstance() {
        return INSTANCE;
    }

    public Connector getConnector() {
        return this.connector;
    }

    public void init(Connector connector) {
        if (this.connector == null) {
            this.connector = connector;
        }
    }
}
