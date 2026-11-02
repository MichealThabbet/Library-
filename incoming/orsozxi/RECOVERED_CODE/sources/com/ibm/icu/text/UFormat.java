package com.ibm.icu.text;

import com.ibm.icu.util.ULocale;
import java.text.Format;

/* loaded from: classes2.dex */
public abstract class UFormat extends Format {
    private static final long serialVersionUID = -4964390515840164416L;
    private ULocale actualLocale;
    private ULocale validLocale;

    public static abstract class SpanField extends Format.Field {
        private static final long serialVersionUID = -4732719509273350606L;

        protected SpanField(String str) {
            super(str);
        }
    }

    public final ULocale getLocale(ULocale.Type type) {
        return type == ULocale.ACTUAL_LOCALE ? this.actualLocale : this.validLocale;
    }

    final void setLocale(ULocale uLocale, ULocale uLocale2) {
        if ((uLocale == null) != (uLocale2 == null)) {
            throw new IllegalArgumentException();
        }
        this.validLocale = uLocale;
        this.actualLocale = uLocale2;
    }
}
