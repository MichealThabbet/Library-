package org.ocpsoft.prettytime;

/* loaded from: classes3.dex */
public interface TimeFormat {
    String decorate(Duration duration, String str);

    String decorateUnrounded(Duration duration, String str);

    String format(Duration duration);

    String formatUnrounded(Duration duration);
}
