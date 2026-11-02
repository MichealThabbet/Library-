package com.ibm.icu.number;

import com.ibm.icu.util.ULocale;
import java.util.Locale;

/* loaded from: classes2.dex */
public class UnlocalizedNumberFormatter extends NumberFormatterSettings<UnlocalizedNumberFormatter> {
    UnlocalizedNumberFormatter() {
        super(null, 14, new Long(3L));
    }

    UnlocalizedNumberFormatter(NumberFormatterSettings<?> numberFormatterSettings, int i, Object obj) {
        super(numberFormatterSettings, i, obj);
    }

    public LocalizedNumberFormatter locale(Locale locale) {
        return new LocalizedNumberFormatter(this, 1, ULocale.forLocale(locale));
    }

    public LocalizedNumberFormatter locale(ULocale uLocale) {
        return new LocalizedNumberFormatter(this, 1, uLocale);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.ibm.icu.number.NumberFormatterSettings
    public UnlocalizedNumberFormatter create(int i, Object obj) {
        return new UnlocalizedNumberFormatter(this, i, obj);
    }
}
