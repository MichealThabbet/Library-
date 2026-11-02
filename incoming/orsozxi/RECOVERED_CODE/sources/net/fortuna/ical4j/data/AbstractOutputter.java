package net.fortuna.ical4j.data;

import java.nio.charset.Charset;
import net.fortuna.ical4j.util.CompatibilityHints;

/* loaded from: classes3.dex */
public abstract class AbstractOutputter {
    protected static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    protected int foldLength;
    private boolean validating;

    public AbstractOutputter() {
        this(true);
    }

    public AbstractOutputter(boolean z) {
        this(z, CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_OUTLOOK_COMPATIBILITY) ? 75 : 73);
    }

    public AbstractOutputter(boolean z, int i) {
        this.validating = z;
        this.foldLength = i;
    }

    public final boolean isValidating() {
        return this.validating;
    }

    public final void setValidating(boolean z) {
        this.validating = z;
    }
}
