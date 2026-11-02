package org.ocpsoft.prettytime.impl;

import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.TimeUnit;

/* loaded from: classes3.dex */
public interface TimeFormatProvider {
    TimeFormat getFormatFor(TimeUnit timeUnit);
}
