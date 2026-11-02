package com.ibm.icu.impl.number;

import com.ibm.icu.impl.units.ComplexUnitsConverter;
import com.ibm.icu.impl.units.MeasureUnitImpl;
import com.ibm.icu.impl.units.UnitsRouter;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.ULocale;
import java.math.BigDecimal;
import java.util.List;

/* loaded from: classes2.dex */
public class UsagePrefsHandler implements MicroPropsGenerator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final MicroPropsGenerator fParent;
    private UnitsRouter fUnitsRouter;

    public UsagePrefsHandler(ULocale uLocale, MeasureUnit measureUnit, String str, MicroPropsGenerator microPropsGenerator) {
        this.fParent = microPropsGenerator;
        this.fUnitsRouter = new UnitsRouter(MeasureUnitImpl.forIdentifier(measureUnit.getIdentifier()), uLocale.getCountry(), str);
    }

    protected static void mixedMeasuresToMicros(ComplexUnitsConverter.ComplexConverterResult complexConverterResult, DecimalQuantity decimalQuantity, MicroProps microProps) {
        microProps.mixedMeasures = complexConverterResult.measures;
        microProps.indexOfQuantity = complexConverterResult.indexOfQuantity;
        decimalQuantity.setToBigDecimal((BigDecimal) microProps.mixedMeasures.get(microProps.indexOfQuantity).getNumber());
    }

    public List<MeasureUnit> getOutputUnits() {
        return this.fUnitsRouter.getOutputUnits();
    }

    @Override // com.ibm.icu.impl.number.MicroPropsGenerator
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
        MicroProps microPropsProcessQuantity = this.fParent.processQuantity(decimalQuantity);
        decimalQuantity.roundToInfinity();
        UnitsRouter.RouteResult routeResultRoute = this.fUnitsRouter.route(decimalQuantity.toBigDecimal(), microPropsProcessQuantity);
        microPropsProcessQuantity.outputUnit = routeResultRoute.outputUnit.build();
        mixedMeasuresToMicros(routeResultRoute.complexConverterResult, decimalQuantity, microPropsProcessQuantity);
        return microPropsProcessQuantity;
    }
}
