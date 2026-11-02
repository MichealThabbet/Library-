package com.ibm.icu.text;

import com.ibm.icu.impl.SimpleFilteredSentenceBreakIterator;
import com.ibm.icu.util.ULocale;
import java.util.Locale;

/* loaded from: classes2.dex */
public abstract class FilteredBreakIteratorBuilder {
    public abstract boolean suppressBreakAfter(CharSequence charSequence);

    public abstract boolean unsuppressBreakAfter(CharSequence charSequence);

    public abstract BreakIterator wrapIteratorWithFilter(BreakIterator breakIterator);

    public static final FilteredBreakIteratorBuilder getInstance(Locale locale) {
        return new SimpleFilteredSentenceBreakIterator.Builder(locale);
    }

    public static final FilteredBreakIteratorBuilder getInstance(ULocale uLocale) {
        return new SimpleFilteredSentenceBreakIterator.Builder(uLocale);
    }

    public static final FilteredBreakIteratorBuilder getEmptyInstance() {
        return new SimpleFilteredSentenceBreakIterator.Builder();
    }

    @Deprecated
    protected FilteredBreakIteratorBuilder() {
    }
}
