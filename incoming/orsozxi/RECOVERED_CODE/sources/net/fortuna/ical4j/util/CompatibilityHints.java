package net.fortuna.ical4j.util;

import com.facebook.internal.ServerProtocol;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public final class CompatibilityHints {
    private static final Map<String, Boolean> HINTS = new ConcurrentHashMap();
    public static final String KEY_NOTES_COMPATIBILITY = "ical4j.compatibility.notes";
    public static final String KEY_OUTLOOK_COMPATIBILITY = "ical4j.compatibility.outlook";
    public static final String KEY_RELAXED_PARSING = "ical4j.parsing.relaxed";
    public static final String KEY_RELAXED_UNFOLDING = "ical4j.unfolding.relaxed";
    public static final String KEY_RELAXED_VALIDATION = "ical4j.validation.relaxed";
    public static final String KEY_VCARD_COMPATIBILITY = "ical4j.compatibility.vcard";

    static {
        setHintEnabled(KEY_RELAXED_UNFOLDING, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(Configurator.getProperty(KEY_RELAXED_UNFOLDING).orElse("false")));
        setHintEnabled(KEY_RELAXED_PARSING, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(Configurator.getProperty(KEY_RELAXED_PARSING).orElse("false")));
        setHintEnabled(KEY_RELAXED_VALIDATION, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(Configurator.getProperty(KEY_RELAXED_VALIDATION).orElse("false")));
        setHintEnabled(KEY_OUTLOOK_COMPATIBILITY, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(Configurator.getProperty(KEY_OUTLOOK_COMPATIBILITY).orElse("false")));
        setHintEnabled(KEY_NOTES_COMPATIBILITY, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(Configurator.getProperty(KEY_NOTES_COMPATIBILITY).orElse("false")));
    }

    private CompatibilityHints() {
    }

    public static void setHintEnabled(String str, boolean z) {
        HINTS.put(str, Boolean.valueOf(z));
    }

    public static void clearHintEnabled(String str) {
        HINTS.remove(str);
    }

    public static boolean isHintEnabled(String str) {
        if (HINTS.get(str) != null) {
            return HINTS.get(str).booleanValue();
        }
        return ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(Configurator.getProperty(str));
    }
}
