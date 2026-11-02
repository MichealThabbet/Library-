package org.apache.poi.hssf.usermodel;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/* loaded from: classes3.dex */
final class StaticFontMetrics {
    private static Map<String, FontDetails> fontDetailsMap = new HashMap();
    private static Properties fontMetricsProps;

    StaticFontMetrics() {
    }

    public static FontDetails getFontDetails(Font font) throws IOException {
        String property;
        InputStream resourceAsStream;
        if (fontMetricsProps == null) {
            InputStream inputStream = null;
            try {
                try {
                    fontMetricsProps = new Properties();
                    try {
                        property = System.getProperty("font.metrics.filename");
                    } catch (SecurityException unused) {
                        property = null;
                    }
                    if (property != null) {
                        File file = new File(property);
                        if (!file.exists()) {
                            throw new FileNotFoundException("font_metrics.properties not found at path " + file.getAbsolutePath());
                        }
                        resourceAsStream = new FileInputStream(file);
                    } else {
                        resourceAsStream = FontDetails.class.getResourceAsStream("/font_metrics.properties");
                        if (resourceAsStream == null) {
                            throw new FileNotFoundException("font_metrics.properties not found in classpath");
                        }
                    }
                    fontMetricsProps.load(resourceAsStream);
                    if (resourceAsStream != null) {
                        try {
                            resourceAsStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                } catch (Throwable th) {
                    if (0 != 0) {
                        try {
                            inputStream.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e) {
                throw new RuntimeException("Could not load font metrics: " + e.getMessage());
            }
        }
        String name = font.getName();
        String str = font.isPlain() ? "plain" : "";
        if (font.isBold()) {
            str = str + "bold";
        }
        if (font.isItalic()) {
            str = str + "italic";
        }
        if (fontMetricsProps.get(FontDetails.buildFontHeightProperty(name)) == null) {
            if (fontMetricsProps.get(FontDetails.buildFontHeightProperty(name + "." + str)) != null) {
                name = name + "." + str;
            }
        }
        if (fontDetailsMap.get(name) == null) {
            FontDetails fontDetailsCreate = FontDetails.create(name, fontMetricsProps);
            fontDetailsMap.put(name, fontDetailsCreate);
            return fontDetailsCreate;
        }
        return fontDetailsMap.get(name);
    }
}
