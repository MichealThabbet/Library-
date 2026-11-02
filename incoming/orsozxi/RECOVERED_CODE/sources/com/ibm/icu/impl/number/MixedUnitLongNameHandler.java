package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.LongNameMultiplexer;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.ListFormatter;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.text.SimpleFormatter;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.ULocale;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class MixedUnitLongNameHandler implements MicroPropsGenerator, ModifierStore, LongNameMultiplexer.ParentlessMicroPropsGenerator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private LocalizedNumberFormatter fIntegerFormatter;
    private ListFormatter fListFormatter;
    private List<String[]> fMixedUnitData;
    private final MicroPropsGenerator parent;
    private final PluralRules rules;

    @Override // com.ibm.icu.impl.number.ModifierStore
    public Modifier getModifier(Modifier.Signum signum, StandardPlural standardPlural) {
        return null;
    }

    private MixedUnitLongNameHandler(PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        this.rules = pluralRules;
        this.parent = microPropsGenerator;
    }

    public static MixedUnitLongNameHandler forMeasureUnit(ULocale uLocale, MeasureUnit measureUnit, NumberFormatter.UnitWidth unitWidth, String str, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        MixedUnitLongNameHandler mixedUnitLongNameHandler = new MixedUnitLongNameHandler(pluralRules, microPropsGenerator);
        List<MeasureUnit> listSplitToSingleUnits = measureUnit.splitToSingleUnits();
        mixedUnitLongNameHandler.fMixedUnitData = new ArrayList();
        for (int i = 0; i < listSplitToSingleUnits.size(); i++) {
            String[] strArr = new String[LongNameHandler.ARRAY_LENGTH];
            LongNameHandler.getMeasureData(uLocale, listSplitToSingleUnits.get(i), unitWidth, str, strArr);
            mixedUnitLongNameHandler.fMixedUnitData.add(strArr);
        }
        ListFormatter.Width width = ListFormatter.Width.SHORT;
        if (unitWidth == NumberFormatter.UnitWidth.NARROW) {
            width = ListFormatter.Width.NARROW;
        } else if (unitWidth == NumberFormatter.UnitWidth.FULL_NAME) {
            width = ListFormatter.Width.WIDE;
        }
        mixedUnitLongNameHandler.fListFormatter = ListFormatter.getInstance(uLocale, ListFormatter.Type.UNITS, width);
        mixedUnitLongNameHandler.fIntegerFormatter = NumberFormatter.withLocale(uLocale);
        return mixedUnitLongNameHandler;
    }

    @Override // com.ibm.icu.impl.number.MicroPropsGenerator
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
        MicroProps microPropsProcessQuantity = this.parent.processQuantity(decimalQuantity);
        microPropsProcessQuantity.modOuter = getMixedUnitModifier(decimalQuantity, microPropsProcessQuantity);
        return microPropsProcessQuantity;
    }

    @Override // com.ibm.icu.impl.number.LongNameMultiplexer.ParentlessMicroPropsGenerator
    public MicroProps processQuantityWithMicros(DecimalQuantity decimalQuantity, MicroProps microProps) {
        microProps.modOuter = getMixedUnitModifier(decimalQuantity, microProps);
        return microProps;
    }

    private Modifier getMixedUnitModifier(DecimalQuantity decimalQuantity, MicroProps microProps) {
        if (microProps.mixedMeasures.size() == 0) {
            throw new UnsupportedOperationException();
        }
        ArrayList arrayList = new ArrayList();
        StandardPlural pluralSafe = StandardPlural.OTHER;
        for (int i = 0; i < microProps.mixedMeasures.size(); i++) {
            if (i == microProps.indexOfQuantity) {
                if (i > 0 && decimalQuantity.isNegative()) {
                    decimalQuantity.negate();
                }
                pluralSafe = RoundingUtils.getPluralSafe(microProps.rounder, this.rules, decimalQuantity);
                arrayList.add(SimpleFormatter.compileMinMaxArguments(LongNameHandler.getWithPlural(this.fMixedUnitData.get(i), pluralSafe), 0, 1).format("{0}"));
            } else {
                DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(microProps.mixedMeasures.get(i).getNumber());
                if (i > 0 && decimalQuantity_DualStorageBCD.isNegative()) {
                    decimalQuantity_DualStorageBCD.negate();
                }
                SimpleFormatter simpleFormatterCompileMinMaxArguments = SimpleFormatter.compileMinMaxArguments(LongNameHandler.getWithPlural(this.fMixedUnitData.get(i), RoundingUtils.getPluralSafe(microProps.rounder, this.rules, decimalQuantity_DualStorageBCD)), 0, 1);
                FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder();
                this.fIntegerFormatter.formatImpl(decimalQuantity_DualStorageBCD, formattedStringBuilder);
                arrayList.add(simpleFormatterCompileMinMaxArguments.format(formattedStringBuilder.toString()));
            }
        }
        String strCompileToStringMinMaxArguments = SimpleFormatterImpl.compileToStringMinMaxArguments(this.fListFormatter.format(arrayList), new StringBuilder(), 0, 1);
        Modifier.Parameters parameters = new Modifier.Parameters();
        parameters.obj = this;
        parameters.signum = Modifier.Signum.POS_ZERO;
        parameters.plural = pluralSafe;
        return new SimpleModifier(strCompileToStringMinMaxArguments, null, false, parameters);
    }
}
