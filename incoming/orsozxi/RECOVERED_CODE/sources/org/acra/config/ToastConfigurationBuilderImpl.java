package org.acra.config;

import android.content.Context;
import org.acra.annotation.AcraToast;

/* loaded from: classes3.dex */
final class ToastConfigurationBuilderImpl implements ToastConfigurationBuilder {
    private final Context context;
    private boolean enabled;
    private int length;
    private String text;

    ToastConfigurationBuilderImpl(Context context) {
        AcraToast acraToast = (AcraToast) context.getClass().getAnnotation(AcraToast.class);
        this.context = context;
        boolean z = acraToast != null;
        this.enabled = z;
        if (z) {
            if (acraToast.resText() != 0) {
                this.text = this.context.getString(acraToast.resText());
            }
            this.length = acraToast.length();
            return;
        }
        this.length = 1;
    }

    @Override // org.acra.config.ToastConfigurationBuilder
    public ToastConfigurationBuilderImpl setEnabled(boolean z) {
        this.enabled = z;
        return this;
    }

    boolean enabled() {
        return this.enabled;
    }

    @Override // org.acra.config.ToastConfigurationBuilder
    public ToastConfigurationBuilderImpl setText(String str) {
        this.text = str;
        return this;
    }

    @Override // org.acra.config.ToastConfigurationBuilder
    public ToastConfigurationBuilderImpl setResText(int i) {
        this.text = this.context.getString(i);
        return this;
    }

    String text() {
        return this.text;
    }

    @Override // org.acra.config.ToastConfigurationBuilder
    public ToastConfigurationBuilderImpl setLength(int i) {
        this.length = i;
        return this;
    }

    int length() {
        return this.length;
    }

    @Override // org.acra.config.ConfigurationBuilder
    public ToastConfiguration build() throws ACRAConfigurationException {
        if (this.enabled && this.text == null) {
            throw new ACRAConfigurationException("text has to be set");
        }
        return new ToastConfiguration(this);
    }
}
