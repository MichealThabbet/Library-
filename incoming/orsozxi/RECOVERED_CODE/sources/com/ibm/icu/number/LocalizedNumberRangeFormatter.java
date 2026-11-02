package com.ibm.icu.number;

import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;

/* loaded from: classes2.dex */
public class LocalizedNumberRangeFormatter extends NumberRangeFormatterSettings<LocalizedNumberRangeFormatter> {
    private volatile NumberRangeFormatterImpl fImpl;

    LocalizedNumberRangeFormatter(NumberRangeFormatterSettings<?> numberRangeFormatterSettings, int i, Object obj) {
        super(numberRangeFormatterSettings, i, obj);
    }

    public FormattedNumberRange formatRange(int i, int i2) {
        return formatImpl(new DecimalQuantity_DualStorageBCD(i), new DecimalQuantity_DualStorageBCD(i2), i == i2);
    }

    public FormattedNumberRange formatRange(double d, double d2) {
        return formatImpl(new DecimalQuantity_DualStorageBCD(d), new DecimalQuantity_DualStorageBCD(d2), d == d2);
    }

    public FormattedNumberRange formatRange(Number number, Number number2) {
        if (number == null || number2 == null) {
            throw new IllegalArgumentException("Cannot format null values in range");
        }
        return formatImpl(new DecimalQuantity_DualStorageBCD(number), new DecimalQuantity_DualStorageBCD(number2), number.equals(number2));
    }

    FormattedNumberRange formatImpl(DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, boolean z) {
        if (this.fImpl == null) {
            this.fImpl = new NumberRangeFormatterImpl(resolve());
        }
        return this.fImpl.format(decimalQuantity, decimalQuantity2, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.ibm.icu.number.NumberRangeFormatterSettings
    public LocalizedNumberRangeFormatter create(int i, Object obj) {
        return new LocalizedNumberRangeFormatter(this, i, obj);
    }
}
