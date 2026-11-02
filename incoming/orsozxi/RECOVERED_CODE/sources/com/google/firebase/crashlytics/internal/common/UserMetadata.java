package com.google.firebase.crashlytics.internal.common;

import com.google.firebase.crashlytics.internal.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class UserMetadata {
    static final int MAX_ATTRIBUTES = 64;
    static final int MAX_ATTRIBUTE_SIZE = 1024;
    private String userId = null;
    private final Map<String, String> attributes = new HashMap();

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = sanitizeAttribute(str);
    }

    public Map<String, String> getCustomKeys() {
        return Collections.unmodifiableMap(this.attributes);
    }

    public void setCustomKey(final String str, final String str2) {
        setSyncCustomKeys(new HashMap<String, String>() { // from class: com.google.firebase.crashlytics.internal.common.UserMetadata.1
            {
                put(UserMetadata.sanitizeKey(str), UserMetadata.sanitizeAttribute(str2));
            }
        });
    }

    public void setCustomKeys(Map<String, String> map) {
        setSyncCustomKeys(map);
    }

    private synchronized void setSyncCustomKeys(Map<String, String> map) {
        HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String strSanitizeKey = sanitizeKey(entry.getKey());
            String strSanitizeAttribute = entry.getValue() == null ? "" : sanitizeAttribute(entry.getValue());
            if (this.attributes.containsKey(strSanitizeKey)) {
                map2.put(strSanitizeKey, strSanitizeAttribute);
            } else {
                map3.put(strSanitizeKey, strSanitizeAttribute);
            }
        }
        this.attributes.putAll(map2);
        if (this.attributes.size() + map3.size() > 64) {
            int size = 64 - this.attributes.size();
            Logger.getLogger().v("Exceeded maximum number of custom attributes (64).");
            map3.keySet().retainAll(new ArrayList(map3.keySet()).subList(0, size));
        }
        this.attributes.putAll(map3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String sanitizeKey(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Custom attribute key must not be null.");
        }
        return sanitizeAttribute(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String sanitizeAttribute(String str) {
        if (str == null) {
            return str;
        }
        String strTrim = str.trim();
        return strTrim.length() > 1024 ? strTrim.substring(0, 1024) : strTrim;
    }
}
