package net.fortuna.ical4j.util;

/* loaded from: classes3.dex */
public class SimpleHostInfo implements HostInfo {
    private final String hostName;

    public SimpleHostInfo(String str) {
        this.hostName = str;
    }

    @Override // net.fortuna.ical4j.util.HostInfo
    public String getHostName() {
        return this.hostName;
    }
}
