package org.acra.config;

import java.io.Serializable;

/* loaded from: classes3.dex */
public final class ToastConfiguration implements Serializable, Configuration {
    private final boolean enabled;
    private final int length;
    private final String text;

    public ToastConfiguration(ToastConfigurationBuilderImpl toastConfigurationBuilderImpl) {
        this.enabled = toastConfigurationBuilderImpl.enabled();
        this.text = toastConfigurationBuilderImpl.text();
        this.length = toastConfigurationBuilderImpl.length();
    }

    @Override // org.acra.config.Configuration
    public boolean enabled() {
        return this.enabled;
    }

    public String text() {
        return this.text;
    }

    public int length() {
        return this.length;
    }
}
