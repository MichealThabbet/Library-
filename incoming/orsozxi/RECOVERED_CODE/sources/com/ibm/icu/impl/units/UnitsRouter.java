package com.ibm.icu.impl.units;

import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.ibm.icu.impl.number.MicroProps;
import com.ibm.icu.impl.units.ComplexUnitsConverter;
import com.ibm.icu.impl.units.MeasureUnitImpl;
import com.ibm.icu.impl.units.UnitPreferences;
import com.ibm.icu.number.Precision;
import com.ibm.icu.util.MeasureUnit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class UnitsRouter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private ArrayList<ConverterPreference> converterPreferences_;
    private ArrayList<MeasureUnit> outputUnits_;

    public UnitsRouter(String str, String str2, String str3) {
        this(MeasureUnitImpl.forIdentifier(str), str2, str3);
    }

    public UnitsRouter(MeasureUnitImpl measureUnitImpl, String str, String str2) {
        this.outputUnits_ = new ArrayList<>();
        this.converterPreferences_ = new ArrayList<>();
        UnitsData unitsData = new UnitsData();
        UnitPreferences.UnitPreference[] preferencesFor = unitsData.getPreferencesFor(unitsData.getCategory(measureUnitImpl), str2, str);
        for (UnitPreferences.UnitPreference unitPreference : preferencesFor) {
            MeasureUnitImpl forIdentifier = MeasureUnitImpl.UnitsParser.parseForIdentifier(unitPreference.getUnit());
            String skeleton = unitPreference.getSkeleton();
            if (!skeleton.isEmpty() && !skeleton.startsWith("precision-increment")) {
                throw new AssertionError("Only `precision-increment` is allowed");
            }
            this.outputUnits_.add(forIdentifier.build());
            this.converterPreferences_.add(new ConverterPreference(measureUnitImpl, forIdentifier, unitPreference.getGeq(), skeleton, unitsData.getConversionRates()));
        }
    }

    public RouteResult route(BigDecimal bigDecimal, MicroProps microProps) {
        ConverterPreference next = null;
        Precision precisionInto = microProps == null ? null : microProps.rounder;
        Iterator<ConverterPreference> it2 = this.converterPreferences_.iterator();
        while (it2.hasNext()) {
            next = it2.next();
            if (next.converter.greaterThanOrEqual(bigDecimal.abs(), next.limit)) {
                break;
            }
        }
        if (precisionInto != null && (precisionInto instanceof Precision.BogusRounder)) {
            Precision.BogusRounder bogusRounder = (Precision.BogusRounder) precisionInto;
            precisionInto = next.precision.length() > 0 ? bogusRounder.into(parseSkeletonToPrecision(next.precision)) : bogusRounder.into(Precision.integer().withMinDigits(2));
        }
        if (microProps != null) {
            microProps.rounder = precisionInto;
        }
        return new RouteResult(next.converter.convert(bigDecimal, precisionInto), next.targetUnit);
    }

    private static Precision parseSkeletonToPrecision(String str) {
        if (!str.startsWith("precision-increment/")) {
            throw new IllegalIcuArgumentException("precisionSkeleton is only precision-increment");
        }
        return Precision.increment(new BigDecimal(str.substring(20)));
    }

    public List<MeasureUnit> getOutputUnits() {
        return this.outputUnits_;
    }

    public static class ConverterPreference {
        final ComplexUnitsConverter converter;
        final BigDecimal limit;
        final String precision;
        final MeasureUnitImpl targetUnit;

        public ConverterPreference(MeasureUnitImpl measureUnitImpl, MeasureUnitImpl measureUnitImpl2, String str, ConversionRates conversionRates) {
            this(measureUnitImpl, measureUnitImpl2, BigDecimal.valueOf(Double.MIN_VALUE), str, conversionRates);
        }

        public ConverterPreference(MeasureUnitImpl measureUnitImpl, MeasureUnitImpl measureUnitImpl2, BigDecimal bigDecimal, String str, ConversionRates conversionRates) {
            this.converter = new ComplexUnitsConverter(measureUnitImpl, measureUnitImpl2, conversionRates);
            this.limit = bigDecimal;
            this.precision = str;
            this.targetUnit = measureUnitImpl2;
        }
    }

    public class RouteResult {
        public final ComplexUnitsConverter.ComplexConverterResult complexConverterResult;
        public final MeasureUnitImpl outputUnit;

        RouteResult(ComplexUnitsConverter.ComplexConverterResult complexConverterResult, MeasureUnitImpl measureUnitImpl) {
            this.complexConverterResult = complexConverterResult;
            this.outputUnit = measureUnitImpl;
        }
    }
}
