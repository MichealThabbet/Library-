package com.ibm.icu.number;

import com.ibm.icu.util.ULocale;
import java.util.Locale;

/* loaded from: classes2.dex */
public class UnlocalizedNumberRangeFormatter extends NumberRangeFormatterSettings<UnlocalizedNumberRangeFormatter> {
    UnlocalizedNumberRangeFormatter() {
        super(null, 0, null);
    }

    UnlocalizedNumberRangeFormatter(NumberRangeFormatterSettings<?> numberRangeFormatterSettings, int i, Object obj) {
        super(numberRangeFormatterSettings, i, obj);
    }

    public LocalizedNumberRangeFormatter locale(Locale locale) {
        return new LocalizedNumberRangeFormatter(this, 1, ULocale.forLocale(locale));
    }

    public LocalizedNumberRangeFormatter locale(ULocale uLocale) {
        return new LocalizedNumberRangeFormatter(this, 1, uLocale);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.ibm.icu.number.NumberRangeFormatterSettings
    public UnlocalizedNumberRangeFormatter create(int i, Object obj) {
        return new UnlocalizedNumberRangeFormatter(this, i, obj);
    }
}
