package com.ibm.icu.number;

import com.ibm.icu.text.UFormat;
import com.ibm.icu.util.ULocale;
import java.io.InvalidObjectException;
import java.util.Locale;

/* loaded from: classes2.dex */
public abstract class NumberRangeFormatter {
    private static final UnlocalizedNumberRangeFormatter BASE = new UnlocalizedNumberRangeFormatter();

    public enum RangeCollapse {
        AUTO,
        NONE,
        UNIT,
        ALL
    }

    public enum RangeIdentityFallback {
        SINGLE_VALUE,
        APPROXIMATELY_OR_SINGLE_VALUE,
        APPROXIMATELY,
        RANGE
    }

    public enum RangeIdentityResult {
        EQUAL_BEFORE_ROUNDING,
        EQUAL_AFTER_ROUNDING,
        NOT_EQUAL
    }

    public static final class SpanField extends UFormat.SpanField {
        public static final SpanField NUMBER_RANGE_SPAN = new SpanField("number-range-span");
        private static final long serialVersionUID = 8750397196515368729L;

        private SpanField(String str) {
            super(str);
        }

        @Override // java.text.AttributedCharacterIterator.Attribute
        @Deprecated
        protected Object readResolve() throws InvalidObjectException {
            if (getName().equals(NUMBER_RANGE_SPAN.getName())) {
                return NUMBER_RANGE_SPAN;
            }
            throw new InvalidObjectException("An invalid object.");
        }
    }

    public static UnlocalizedNumberRangeFormatter with() {
        return BASE;
    }

    public static LocalizedNumberRangeFormatter withLocale(Locale locale) {
        return BASE.locale(locale);
    }

    public static LocalizedNumberRangeFormatter withLocale(ULocale uLocale) {
        return BASE.locale(uLocale);
    }

    private NumberRangeFormatter() {
    }
}
