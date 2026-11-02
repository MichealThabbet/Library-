package org.ocpsoft.prettytime.impl;

import java.util.Locale;
import java.util.ResourceBundle;
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.LocaleAware;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.format.SimpleTimeFormat;

/* loaded from: classes3.dex */
public class ResourcesTimeFormat extends SimpleTimeFormat implements TimeFormat, LocaleAware<ResourcesTimeFormat> {
    private ResourceBundle bundle;
    private TimeFormat override;
    private final ResourcesTimeUnit unit;

    public ResourcesTimeFormat(ResourcesTimeUnit resourcesTimeUnit) {
        this.unit = resourcesTimeUnit;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.ocpsoft.prettytime.LocaleAware
    public ResourcesTimeFormat setLocale(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(this.unit.getResourceBundleName(), locale);
        this.bundle = bundle;
        if (bundle instanceof TimeFormatProvider) {
            TimeFormat formatFor = ((TimeFormatProvider) bundle).getFormatFor(this.unit);
            if (formatFor != null) {
                this.override = formatFor;
            }
        } else {
            this.override = null;
        }
        if (this.override == null) {
            setPattern(this.bundle.getString(this.unit.getResourceKeyPrefix() + "Pattern"));
            setFuturePrefix(this.bundle.getString(this.unit.getResourceKeyPrefix() + "FuturePrefix"));
            setFutureSuffix(this.bundle.getString(this.unit.getResourceKeyPrefix() + "FutureSuffix"));
            setPastPrefix(this.bundle.getString(this.unit.getResourceKeyPrefix() + "PastPrefix"));
            setPastSuffix(this.bundle.getString(this.unit.getResourceKeyPrefix() + "PastSuffix"));
            setSingularName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "SingularName"));
            setPluralName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "PluralName"));
            try {
                setFuturePluralName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "FuturePluralName"));
            } catch (Exception unused) {
            }
            try {
                setFutureSingularName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "FutureSingularName"));
            } catch (Exception unused2) {
            }
            try {
                setPastPluralName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "PastPluralName"));
            } catch (Exception unused3) {
            }
            try {
                setPastSingularName(this.bundle.getString(this.unit.getResourceKeyPrefix() + "PastSingularName"));
            } catch (Exception unused4) {
            }
        }
        return this;
    }

    @Override // org.ocpsoft.prettytime.format.SimpleTimeFormat, org.ocpsoft.prettytime.TimeFormat
    public String decorate(Duration duration, String str) {
        TimeFormat timeFormat = this.override;
        return timeFormat == null ? super.decorate(duration, str) : timeFormat.decorate(duration, str);
    }

    @Override // org.ocpsoft.prettytime.format.SimpleTimeFormat, org.ocpsoft.prettytime.TimeFormat
    public String decorateUnrounded(Duration duration, String str) {
        TimeFormat timeFormat = this.override;
        return timeFormat == null ? super.decorateUnrounded(duration, str) : timeFormat.decorateUnrounded(duration, str);
    }

    @Override // org.ocpsoft.prettytime.format.SimpleTimeFormat, org.ocpsoft.prettytime.TimeFormat
    public String format(Duration duration) {
        TimeFormat timeFormat = this.override;
        return timeFormat == null ? super.format(duration) : timeFormat.format(duration);
    }

    @Override // org.ocpsoft.prettytime.format.SimpleTimeFormat, org.ocpsoft.prettytime.TimeFormat
    public String formatUnrounded(Duration duration) {
        TimeFormat timeFormat = this.override;
        return timeFormat == null ? super.formatUnrounded(duration) : timeFormat.formatUnrounded(duration);
    }
}
