package net.fortuna.ical4j.util;

import java.io.InputStream;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public class ResourceLoader {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) ResourceLoader.class);

    public static URL getResource(String str) {
        URL resource = null;
        try {
            if (Thread.currentThread().getContextClassLoader() != null) {
                resource = Thread.currentThread().getContextClassLoader().getResource(str);
            }
        } catch (SecurityException e) {
            LOG.info("Unable to access context classloader, using default. " + e.getMessage());
        }
        if (resource != null) {
            return resource;
        }
        return ResourceLoader.class.getResource("/" + str);
    }

    public static InputStream getResourceAsStream(String str) {
        InputStream resourceAsStream;
        try {
            resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(str);
        } catch (SecurityException e) {
            LOG.info("Unable to access context classloader, using default. " + e.getMessage());
            resourceAsStream = null;
        }
        if (resourceAsStream != null) {
            return resourceAsStream;
        }
        return ResourceLoader.class.getResourceAsStream("/" + str);
    }
}
