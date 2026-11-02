package com.ibm.icu.text;

import com.ibm.icu.text.MeasureFormat;
import com.ibm.icu.util.CurrencyAmount;
import com.ibm.icu.util.ULocale;
import java.io.ObjectStreamException;
import java.text.FieldPosition;
import java.text.ParsePosition;

/* loaded from: classes2.dex */
class CurrencyFormat extends MeasureFormat {
    static final long serialVersionUID = -931679363692504634L;

    public CurrencyFormat(ULocale uLocale) {
        super(uLocale, MeasureFormat.FormatWidth.DEFAULT_CURRENCY);
    }

    @Override // com.ibm.icu.text.MeasureFormat, java.text.Format
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (!(obj instanceof CurrencyAmount)) {
            throw new IllegalArgumentException("Invalid type: " + obj.getClass().getName());
        }
        return super.format(obj, stringBuffer, fieldPosition);
    }

    @Override // com.ibm.icu.text.MeasureFormat, java.text.Format
    public CurrencyAmount parseObject(String str, ParsePosition parsePosition) {
        return getNumberFormatInternal().parseCurrency(str, parsePosition);
    }

    private Object writeReplace() throws ObjectStreamException {
        return toCurrencyProxy();
    }

    private Object readResolve() throws ObjectStreamException {
        return new CurrencyFormat(getLocale(ULocale.ACTUAL_LOCALE));
    }
}
