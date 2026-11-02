package com.huawei.agconnect.config.impl;

import android.util.Log;
import com.huawei.agconnect.AGCRoutePolicy;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import net.fortuna.ical4j.model.Parameter;

/* loaded from: classes2.dex */
public final class Utils {
    private static final int BUFF_SIZE = 4096;
    public static final String DEFAULT_NAME = "DEFAULT_INSTANCE";
    private static final String TAG = "Utils";

    public static void closeQuietly(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
                Log.e(TAG, "Exception when closing the 'Closeable'.");
            }
        }
    }

    public static void copy(Reader reader, Writer writer) throws IOException {
        copy(reader, writer, new char[4096]);
    }

    public static void copy(Reader reader, Writer writer, char[] cArr) throws IOException {
        while (true) {
            int i = reader.read(cArr);
            if (-1 == i) {
                return;
            } else {
                writer.write(cArr, 0, i);
            }
        }
    }

    public static Map<String, String> fixKeyPathMap(Map<String, String> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            map2.put(fixPath(entry.getKey()), entry.getValue());
        }
        return map2;
    }

    public static String fixPath(String str) {
        int i = 0;
        if (str.length() > 0) {
            while (str.charAt(i) == '/') {
                i++;
            }
        }
        return "/" + str.substring(i);
    }

    public static AGCRoutePolicy getRoutePolicyFromJson(String str, String str2) {
        if (str != null) {
            char c = 65535;
            int iHashCode = str.hashCode();
            if (iHashCode != 2155) {
                if (iHashCode != 2177) {
                    if (iHashCode != 2627) {
                        if (iHashCode == 2644 && str.equals("SG")) {
                            c = 3;
                        }
                    } else if (str.equals("RU")) {
                        c = 2;
                    }
                } else if (str.equals("DE")) {
                    c = 1;
                }
            } else if (str.equals(Parameter.CN)) {
                c = 0;
            }
            if (c == 0) {
                return AGCRoutePolicy.CHINA;
            }
            if (c == 1) {
                return AGCRoutePolicy.GERMANY;
            }
            if (c == 2) {
                return AGCRoutePolicy.RUSSIA;
            }
            if (c == 3) {
                return AGCRoutePolicy.SINGAPORE;
            }
        }
        if (str2 != null) {
            if (str2.contains("connect-drcn")) {
                return AGCRoutePolicy.CHINA;
            }
            if (str2.contains("connect-dre")) {
                return AGCRoutePolicy.GERMANY;
            }
            if (str2.contains("connect-drru")) {
                return AGCRoutePolicy.RUSSIA;
            }
            if (str2.contains("connect-dra")) {
                return AGCRoutePolicy.SINGAPORE;
            }
        }
        return AGCRoutePolicy.UNKNOWN;
    }

    public static String toString(InputStream inputStream, String str) throws IOException {
        StringWriter stringWriter = new StringWriter();
        copy(new InputStreamReader(inputStream, str), stringWriter);
        return stringWriter.toString();
    }
}
