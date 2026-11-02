package net.fortuna.ical4j.validate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class ValidationRule implements Serializable {
    private final List<String> instances;
    private final boolean relaxedModeSupported;
    private final ValidationType type;

    public enum ValidationType {
        None,
        One,
        OneOrLess,
        OneOrMore
    }

    public ValidationRule(ValidationType validationType, String... strArr) {
        this(validationType, false, strArr);
    }

    public ValidationRule(ValidationType validationType, boolean z, String... strArr) {
        this.type = validationType;
        this.instances = Arrays.asList(strArr);
        this.relaxedModeSupported = z;
    }

    public ValidationType getType() {
        return this.type;
    }

    public List<String> getInstances() {
        return this.instances;
    }

    public boolean isRelaxedModeSupported() {
        return this.relaxedModeSupported;
    }
}
