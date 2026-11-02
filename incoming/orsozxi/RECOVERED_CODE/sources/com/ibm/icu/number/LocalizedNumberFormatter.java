package com.ibm.icu.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.impl.number.LocalizedNumberFormatterAsFormat;
import com.ibm.icu.impl.number.MacroProps;
import com.ibm.icu.impl.number.MicroProps;
import com.ibm.icu.util.Measure;
import com.ibm.icu.util.MeasureUnit;
import java.text.Format;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* loaded from: classes2.dex */
public class LocalizedNumberFormatter extends NumberFormatterSettings<LocalizedNumberFormatter> {
    static final AtomicLongFieldUpdater<LocalizedNumberFormatter> callCount = AtomicLongFieldUpdater.newUpdater(LocalizedNumberFormatter.class, "callCountInternal");
    volatile long callCountInternal;
    volatile NumberFormatterImpl compiled;
    volatile LocalizedNumberFormatter savedWithUnit;

    LocalizedNumberFormatter(NumberFormatterSettings<?> numberFormatterSettings, int i, Object obj) {
        super(numberFormatterSettings, i, obj);
    }

    public FormattedNumber format(long j) {
        return format(new DecimalQuantity_DualStorageBCD(j));
    }

    public FormattedNumber format(double d) {
        return format(new DecimalQuantity_DualStorageBCD(d));
    }

    public FormattedNumber format(Number number) {
        return format(new DecimalQuantity_DualStorageBCD(number));
    }

    public FormattedNumber format(Measure measure) {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(measure.getNumber());
        MeasureUnit unit = measure.getUnit();
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        MicroProps impl = formatImpl(decimalQuantity_DualStorageBCD, unit, formattedStringBuilder);
        return new FormattedNumber(formattedStringBuilder, decimalQuantity_DualStorageBCD, impl.outputUnit, impl.gender);
    }

    public Format toFormat() {
        return new LocalizedNumberFormatterAsFormat(this, resolve().loc);
    }

    private FormattedNumber format(DecimalQuantity decimalQuantity) {
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        MicroProps impl = formatImpl(decimalQuantity, formattedStringBuilder);
        return new FormattedNumber(formattedStringBuilder, decimalQuantity, impl.outputUnit, impl.gender);
    }

    @Deprecated
    public MicroProps formatImpl(DecimalQuantity decimalQuantity, FormattedStringBuilder formattedStringBuilder) {
        if (computeCompiled()) {
            return this.compiled.format(decimalQuantity, formattedStringBuilder);
        }
        return NumberFormatterImpl.formatStatic(resolve(), decimalQuantity, formattedStringBuilder);
    }

    @Deprecated
    public MicroProps formatImpl(DecimalQuantity decimalQuantity, MeasureUnit measureUnit, FormattedStringBuilder formattedStringBuilder) {
        if (Objects.equals(resolve().unit, measureUnit)) {
            return formatImpl(decimalQuantity, formattedStringBuilder);
        }
        LocalizedNumberFormatter localizedNumberFormatter = this.savedWithUnit;
        if (localizedNumberFormatter == null || !Objects.equals(localizedNumberFormatter.resolve().unit, measureUnit)) {
            localizedNumberFormatter = new LocalizedNumberFormatter(this, 3, measureUnit);
            this.savedWithUnit = localizedNumberFormatter;
        }
        return localizedNumberFormatter.formatImpl(decimalQuantity, formattedStringBuilder);
    }

    @Deprecated
    public String getAffixImpl(boolean z, boolean z2) {
        int prefixSuffixStatic;
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
        byte b = (byte) (z2 ? -1 : 1);
        StandardPlural standardPlural = StandardPlural.OTHER;
        if (computeCompiled()) {
            prefixSuffixStatic = this.compiled.getPrefixSuffix(b, standardPlural, formattedStringBuilder);
        } else {
            prefixSuffixStatic = NumberFormatterImpl.getPrefixSuffixStatic(resolve(), b, standardPlural, formattedStringBuilder);
        }
        if (z) {
            return formattedStringBuilder.subSequence(0, prefixSuffixStatic).toString();
        }
        return formattedStringBuilder.subSequence(prefixSuffixStatic, formattedStringBuilder.length()).toString();
    }

    private boolean computeCompiled() {
        MacroProps macroPropsResolve = resolve();
        if (callCount.incrementAndGet(this) != macroPropsResolve.threshold.longValue()) {
            return this.compiled != null;
        }
        this.compiled = new NumberFormatterImpl(macroPropsResolve);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.ibm.icu.number.NumberFormatterSettings
    public LocalizedNumberFormatter create(int i, Object obj) {
        return new LocalizedNumberFormatter(this, i, obj);
    }
}
