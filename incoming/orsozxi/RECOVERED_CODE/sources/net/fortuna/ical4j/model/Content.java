package net.fortuna.ical4j.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class Content implements Serializable {
    private static final long serialVersionUID = -2892694760294583989L;

    public abstract String getName();

    public abstract String getValue();

    public static abstract class Factory implements Serializable {
        private final List<String> supportedNames;

        public Factory(String... strArr) {
            this.supportedNames = Arrays.asList(strArr);
        }

        public final boolean supports(String str) {
            return this.supportedNames.contains(str);
        }
    }
}
