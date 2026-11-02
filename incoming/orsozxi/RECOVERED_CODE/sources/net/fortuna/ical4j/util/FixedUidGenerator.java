package net.fortuna.ical4j.util;

import java.net.SocketException;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.property.Uid;
import org.apache.commons.codec.language.Soundex;

/* loaded from: classes3.dex */
public class FixedUidGenerator implements UidGenerator {
    private static long lastMillis;
    private final String hostName;
    private final String pid;

    public FixedUidGenerator(String str) throws SocketException {
        this(new InetAddressHostInfo(), str);
    }

    public FixedUidGenerator(HostInfo hostInfo, String str) {
        this.hostName = hostInfo == null ? null : hostInfo.getHostName();
        this.pid = str;
    }

    @Override // net.fortuna.ical4j.util.UidGenerator
    public Uid generateUid() {
        StringBuilder sb = new StringBuilder();
        sb.append(uniqueTimestamp());
        sb.append(Soundex.SILENT_MARKER);
        sb.append(this.pid);
        if (this.hostName != null) {
            sb.append('@');
            sb.append(this.hostName);
        }
        return new Uid(sb.toString());
    }

    private static DateTime uniqueTimestamp() {
        long jCurrentTimeMillis;
        synchronized (FixedUidGenerator.class) {
            jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis < lastMillis) {
                jCurrentTimeMillis = lastMillis;
            }
            if (jCurrentTimeMillis - lastMillis < 1000) {
                jCurrentTimeMillis += 1000;
            }
            lastMillis = jCurrentTimeMillis;
        }
        DateTime dateTime = new DateTime(jCurrentTimeMillis);
        dateTime.setUtc(true);
        return dateTime;
    }
}
