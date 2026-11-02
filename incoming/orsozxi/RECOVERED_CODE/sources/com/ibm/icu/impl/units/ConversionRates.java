package com.ibm.icu.impl.units;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.units.MeasureUnitImpl;
import com.ibm.icu.impl.units.UnitsConverter;
import com.ibm.icu.impl.units.UnitsData;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.UResourceBundle;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.MissingResourceException;

/* loaded from: classes2.dex */
public class ConversionRates {
    private HashMap<String, ConversionRateInfo> mapToConversionRate;

    public ConversionRates() throws MissingResourceException {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle) UResourceBundle.getBundleInstance(ICUData.ICU_BASE_NAME, "units");
        ConversionRatesSink conversionRatesSink = new ConversionRatesSink();
        iCUResourceBundle.getAllItemsWithFallback(UnitsData.Constants.CONVERSION_UNIT_TABLE_NAME, conversionRatesSink);
        this.mapToConversionRate = conversionRatesSink.getMapToConversionRate();
    }

    private UnitsConverter.Factor getFactorToBase(SingleUnitImpl singleUnitImpl) {
        return UnitsConverter.Factor.processFactor(this.mapToConversionRate.get(singleUnitImpl.getSimpleUnitID()).getConversionRate()).applyPrefix(singleUnitImpl.getPrefix()).power(singleUnitImpl.getDimensionality());
    }

    public UnitsConverter.Factor getFactorToBase(MeasureUnitImpl measureUnitImpl) {
        UnitsConverter.Factor factor = new UnitsConverter.Factor();
        Iterator<SingleUnitImpl> it2 = measureUnitImpl.getSingleUnits().iterator();
        while (it2.hasNext()) {
            factor = factor.multiply(getFactorToBase(it2.next()));
        }
        return factor;
    }

    protected BigDecimal getOffset(MeasureUnitImpl measureUnitImpl, MeasureUnitImpl measureUnitImpl2, UnitsConverter.Factor factor, UnitsConverter.Factor factor2, UnitsConverter.Convertibility convertibility) {
        if (convertibility != UnitsConverter.Convertibility.CONVERTIBLE) {
            return BigDecimal.valueOf(0L);
        }
        if (!checkSimpleUnit(measureUnitImpl) || !checkSimpleUnit(measureUnitImpl2)) {
            return BigDecimal.valueOf(0L);
        }
        return this.mapToConversionRate.get(measureUnitImpl.getSingleUnits().get(0).getSimpleUnitID()).getOffset().subtract(this.mapToConversionRate.get(measureUnitImpl2.getSingleUnits().get(0).getSimpleUnitID()).getOffset()).divide(factor2.getConversionRate(), MathContext.DECIMAL128);
    }

    public MeasureUnitImpl extractCompoundBaseUnit(MeasureUnitImpl measureUnitImpl) {
        ArrayList<SingleUnitImpl> arrayListExtractBaseUnits = extractBaseUnits(measureUnitImpl);
        MeasureUnitImpl measureUnitImpl2 = new MeasureUnitImpl();
        Iterator<SingleUnitImpl> it2 = arrayListExtractBaseUnits.iterator();
        while (it2.hasNext()) {
            measureUnitImpl2.appendSingleUnit(it2.next());
        }
        return measureUnitImpl2;
    }

    public ArrayList<SingleUnitImpl> extractBaseUnits(MeasureUnitImpl measureUnitImpl) {
        ArrayList<SingleUnitImpl> arrayList = new ArrayList<>();
        Iterator<SingleUnitImpl> it2 = measureUnitImpl.getSingleUnits().iterator();
        while (it2.hasNext()) {
            arrayList.addAll(extractBaseUnits(it2.next()));
        }
        return arrayList;
    }

    public ArrayList<SingleUnitImpl> extractBaseUnits(SingleUnitImpl singleUnitImpl) {
        MeasureUnitImpl forIdentifier = MeasureUnitImpl.UnitsParser.parseForIdentifier(this.mapToConversionRate.get(singleUnitImpl.getSimpleUnitID()).getTarget());
        forIdentifier.applyDimensionality(singleUnitImpl.getDimensionality());
        return forIdentifier.getSingleUnits();
    }

    private boolean checkSimpleUnit(MeasureUnitImpl measureUnitImpl) {
        if (measureUnitImpl.getComplexity() != MeasureUnit.Complexity.SINGLE) {
            return false;
        }
        SingleUnitImpl singleUnitImpl = measureUnitImpl.getSingleUnits().get(0);
        return singleUnitImpl.getPrefix() == MeasureUnit.MeasurePrefix.ONE && singleUnitImpl.getDimensionality() == 1;
    }

    public static class ConversionRatesSink extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private HashMap<String, ConversionRateInfo> mapToConversionRate = new HashMap<>();

        @Override // com.ibm.icu.impl.UResource.Sink
        public void put(UResource.Key key, UResource.Value value, boolean z) {
            UResource.Table table = value.getTable();
            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
                String string = key.toString();
                UResource.Table table2 = value.getTable();
                String str = null;
                String str2 = "0";
                String str3 = null;
                for (int i2 = 0; table2.getKeyAndValue(i2, key, value); i2++) {
                    String string2 = key.toString();
                    String strReplaceAll = value.toString().replaceAll(" ", "");
                    if ("target".equals(string2)) {
                        str = strReplaceAll;
                    } else if ("factor".equals(string2)) {
                        str3 = strReplaceAll;
                    } else if ("offset".equals(string2)) {
                        str2 = strReplaceAll;
                    }
                }
                this.mapToConversionRate.put(string, new ConversionRateInfo(string, str, str3, str2));
            }
        }

        public HashMap<String, ConversionRateInfo> getMapToConversionRate() {
            return this.mapToConversionRate;
        }
    }

    public static class ConversionRateInfo {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final String conversionRate;
        private final BigDecimal offset;
        private final String simpleUnit;
        private final String target;

        public ConversionRateInfo(String str, String str2, String str3, String str4) {
            this.simpleUnit = str;
            this.target = str2;
            this.conversionRate = str3;
            this.offset = forNumberWithDivision(str4);
        }

        private static BigDecimal forNumberWithDivision(String str) {
            String[] strArrSplit = str.split("/");
            if (strArrSplit.length == 1) {
                return new BigDecimal(strArrSplit[0]);
            }
            return new BigDecimal(strArrSplit[0]).divide(new BigDecimal(strArrSplit[1]), MathContext.DECIMAL128);
        }

        public String getTarget() {
            return this.target;
        }

        public BigDecimal getOffset() {
            return this.offset;
        }

        public String getConversionRate() {
            return this.conversionRate;
        }
    }
}
