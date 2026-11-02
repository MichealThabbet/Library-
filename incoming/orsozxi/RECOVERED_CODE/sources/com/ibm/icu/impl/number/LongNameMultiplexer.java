package com.ibm.icu.impl.number;

import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.ULocale;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class LongNameMultiplexer implements MicroPropsGenerator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private List<ParentlessMicroPropsGenerator> fHandlers;
    private List<MeasureUnit> fMeasureUnits;
    private final MicroPropsGenerator fParent;

    public interface ParentlessMicroPropsGenerator {
        MicroProps processQuantityWithMicros(DecimalQuantity decimalQuantity, MicroProps microProps);
    }

    public LongNameMultiplexer(MicroPropsGenerator microPropsGenerator) {
        this.fParent = microPropsGenerator;
    }

    public static LongNameMultiplexer forMeasureUnits(ULocale uLocale, List<MeasureUnit> list, NumberFormatter.UnitWidth unitWidth, String str, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        LongNameMultiplexer longNameMultiplexer = new LongNameMultiplexer(microPropsGenerator);
        longNameMultiplexer.fMeasureUnits = new ArrayList();
        longNameMultiplexer.fHandlers = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            MeasureUnit measureUnit = list.get(i);
            longNameMultiplexer.fMeasureUnits.add(measureUnit);
            if (measureUnit.getComplexity() == MeasureUnit.Complexity.MIXED) {
                longNameMultiplexer.fHandlers.add(MixedUnitLongNameHandler.forMeasureUnit(uLocale, measureUnit, unitWidth, str, pluralRules, null));
            } else {
                longNameMultiplexer.fHandlers.add(LongNameHandler.forMeasureUnit(uLocale, measureUnit, unitWidth, str, pluralRules, null));
            }
        }
        return longNameMultiplexer;
    }

    @Override // com.ibm.icu.impl.number.MicroPropsGenerator
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
        MicroProps microPropsProcessQuantity = this.fParent.processQuantity(decimalQuantity);
        for (int i = 0; i < this.fHandlers.size(); i++) {
            if (this.fMeasureUnits.get(i).equals(microPropsProcessQuantity.outputUnit)) {
                return this.fHandlers.get(i).processQuantityWithMicros(decimalQuantity, microPropsProcessQuantity);
            }
        }
        throw new AssertionError(" We shouldn't receive any outputUnit for which we haven't already got a LongNameHandler");
    }
}
