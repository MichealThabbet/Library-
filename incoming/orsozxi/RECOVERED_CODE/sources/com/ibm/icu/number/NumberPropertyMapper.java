package com.ibm.icu.number;

import com.ibm.icu.impl.number.DecimalFormatProperties;
import com.ibm.icu.impl.number.PatternStringParser;
import com.ibm.icu.text.DecimalFormatSymbols;

/* loaded from: classes2.dex */
final class NumberPropertyMapper {
    NumberPropertyMapper() {
    }

    public static UnlocalizedNumberFormatter create(DecimalFormatProperties decimalFormatProperties, DecimalFormatSymbols decimalFormatSymbols) {
        return NumberFormatter.with().macros(oldToNew(decimalFormatProperties, decimalFormatSymbols, null));
    }

    public static UnlocalizedNumberFormatter create(DecimalFormatProperties decimalFormatProperties, DecimalFormatSymbols decimalFormatSymbols, DecimalFormatProperties decimalFormatProperties2) {
        return NumberFormatter.with().macros(oldToNew(decimalFormatProperties, decimalFormatSymbols, decimalFormatProperties2));
    }

    public static UnlocalizedNumberFormatter create(String str, DecimalFormatSymbols decimalFormatSymbols) {
        return create(PatternStringParser.parseToProperties(str), decimalFormatSymbols);
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x011e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.ibm.icu.impl.number.MacroProps oldToNew(com.ibm.icu.impl.number.DecimalFormatProperties r18, com.ibm.icu.text.DecimalFormatSymbols r19, com.ibm.icu.impl.number.DecimalFormatProperties r20) {
        /*
            Method dump skipped, instructions count: 673
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.number.NumberPropertyMapper.oldToNew(com.ibm.icu.impl.number.DecimalFormatProperties, com.ibm.icu.text.DecimalFormatSymbols, com.ibm.icu.impl.number.DecimalFormatProperties):com.ibm.icu.impl.number.MacroProps");
    }
}
