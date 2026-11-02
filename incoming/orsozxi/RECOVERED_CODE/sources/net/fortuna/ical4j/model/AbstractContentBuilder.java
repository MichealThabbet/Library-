package net.fortuna.ical4j.model;

import net.fortuna.ical4j.util.CompatibilityHints;

/* loaded from: classes3.dex */
public abstract class AbstractContentBuilder {
    protected boolean isExperimentalName(String str) {
        return str.startsWith("X-") && str.length() > 2;
    }

    protected boolean allowIllegalNames() {
        return CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING);
    }
}
