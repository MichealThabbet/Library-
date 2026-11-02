package net.fortuna.ical4j.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes3.dex */
public final class Configurator {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) Configurator.class);
    private static final Properties CONFIG = new Properties();

    static {
        try {
            InputStream resourceAsStream = ResourceLoader.getResourceAsStream("ical4j.properties");
            try {
                CONFIG.load(resourceAsStream);
                if (resourceAsStream != null) {
                    resourceAsStream.close();
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    if (resourceAsStream != null) {
                        try {
                            resourceAsStream.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                    }
                    throw th2;
                }
            }
        } catch (IOException | NullPointerException unused) {
            LOG.info("ical4j.properties not found.");
        }
    }

    private Configurator() {
    }

    public static Optional<String> getProperty(String str) {
        String property = CONFIG.getProperty(str);
        if (property == null) {
            property = System.getProperty(str);
        }
        return Optional.ofNullable(property);
    }

    public static Optional<Integer> getIntProperty(String str) {
        Optional<String> property = getProperty(str);
        if (property.isPresent()) {
            try {
                return Optional.of(Integer.valueOf(Integer.parseInt(property.get())));
            } catch (NumberFormatException e) {
                LOG.info(String.format("Invalid configuration value: %s", str), (Throwable) e);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public static <T extends Enum<T>> Optional<T> getEnumProperty(Class<T> cls, String str) {
        Optional<String> property = getProperty(str);
        if (property.isPresent()) {
            try {
                return Optional.of(Enum.valueOf(cls, property.get()));
            } catch (IllegalArgumentException e) {
                LOG.info(String.format("Invalid configuration value: %s", str), (Throwable) e);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public static <T> Optional<T> getObjectProperty(String str) {
        Optional<String> property = getProperty(str);
        if (property.isPresent()) {
            try {
                return Optional.of(Class.forName(property.get()).newInstance());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                LOG.info(String.format("Invalid configuration value: %s", str), e);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
