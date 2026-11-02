package com.ibm.icu.impl.units;

import com.ibm.icu.util.MeasureUnit;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class UnitsConverter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private BigDecimal conversionRate;
    private BigDecimal offset;
    private boolean reciprocal;

    public static class ConversionInfo {
        public BigDecimal conversionRate;
        public BigDecimal offset;
        public boolean reciprocal;
    }

    public enum Convertibility {
        CONVERTIBLE,
        RECIPROCAL,
        UNCONVERTIBLE
    }

    public UnitsConverter(String str, String str2) {
        this(MeasureUnitImpl.forIdentifier(str), MeasureUnitImpl.forIdentifier(str2), new ConversionRates());
    }

    public UnitsConverter(MeasureUnitImpl measureUnitImpl, MeasureUnitImpl measureUnitImpl2, ConversionRates conversionRates) {
        Convertibility convertibilityExtractConvertibility = extractConvertibility(measureUnitImpl, measureUnitImpl2, conversionRates);
        Factor factorToBase = conversionRates.getFactorToBase(measureUnitImpl);
        Factor factorToBase2 = conversionRates.getFactorToBase(measureUnitImpl2);
        if (convertibilityExtractConvertibility == Convertibility.CONVERTIBLE) {
            this.conversionRate = factorToBase.divide(factorToBase2).getConversionRate();
        } else {
            this.conversionRate = factorToBase.multiply(factorToBase2).getConversionRate();
        }
        this.reciprocal = convertibilityExtractConvertibility == Convertibility.RECIPROCAL;
        this.offset = conversionRates.getOffset(measureUnitImpl, measureUnitImpl2, factorToBase, factorToBase2, convertibilityExtractConvertibility);
    }

    public static Convertibility extractConvertibility(MeasureUnitImpl measureUnitImpl, MeasureUnitImpl measureUnitImpl2, ConversionRates conversionRates) {
        ArrayList<SingleUnitImpl> arrayListExtractBaseUnits = conversionRates.extractBaseUnits(measureUnitImpl);
        ArrayList<SingleUnitImpl> arrayListExtractBaseUnits2 = conversionRates.extractBaseUnits(measureUnitImpl2);
        HashMap map = new HashMap();
        insertInMap(map, arrayListExtractBaseUnits, 1);
        insertInMap(map, arrayListExtractBaseUnits2, -1);
        if (areDimensionsZeroes(map)) {
            return Convertibility.CONVERTIBLE;
        }
        insertInMap(map, arrayListExtractBaseUnits2, 2);
        return areDimensionsZeroes(map) ? Convertibility.RECIPROCAL : Convertibility.UNCONVERTIBLE;
    }

    private static void insertInMap(HashMap<String, Integer> map, ArrayList<SingleUnitImpl> arrayList, int i) {
        Iterator<SingleUnitImpl> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            SingleUnitImpl next = it2.next();
            if (map.containsKey(next.getSimpleUnitID())) {
                map.put(next.getSimpleUnitID(), Integer.valueOf(map.get(next.getSimpleUnitID()).intValue() + (next.getDimensionality() * i)));
            } else {
                map.put(next.getSimpleUnitID(), Integer.valueOf(next.getDimensionality() * i));
            }
        }
    }

    private static boolean areDimensionsZeroes(HashMap<String, Integer> map) {
        Iterator<Integer> it2 = map.values().iterator();
        while (it2.hasNext()) {
            if (!it2.next().equals(0)) {
                return false;
            }
        }
        return true;
    }

    public BigDecimal convert(BigDecimal bigDecimal) {
        BigDecimal bigDecimalAdd = bigDecimal.multiply(this.conversionRate).add(this.offset);
        if (!this.reciprocal) {
            return bigDecimalAdd;
        }
        if (bigDecimalAdd == BigDecimal.ZERO) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.ONE.divide(bigDecimalAdd, MathContext.DECIMAL128);
    }

    public BigDecimal convertInverse(BigDecimal bigDecimal) {
        if (this.reciprocal) {
            if (bigDecimal == BigDecimal.ZERO) {
                return BigDecimal.ZERO;
            }
            bigDecimal = BigDecimal.ONE.divide(bigDecimal, MathContext.DECIMAL128);
        }
        return bigDecimal.subtract(this.offset).divide(this.conversionRate, MathContext.DECIMAL128);
    }

    public ConversionInfo getConversionInfo() {
        ConversionInfo conversionInfo = new ConversionInfo();
        conversionInfo.conversionRate = this.conversionRate;
        conversionInfo.offset = this.offset;
        conversionInfo.reciprocal = this.reciprocal;
        return conversionInfo;
    }

    static class Factor {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int exponentFtToM = 0;
        private int exponentPi = 0;
        private int exponentGravity = 0;
        private int exponentG = 0;
        private int exponentGalImpToM3 = 0;
        private int exponentLbToKg = 0;
        private int exponentGlucoseMolarMass = 0;
        private int exponentItemPerMole = 0;
        private BigDecimal factorNum = BigDecimal.valueOf(1L);
        private BigDecimal factorDen = BigDecimal.valueOf(1L);

        public static Factor processFactor(String str) {
            String[] strArrSplit = str.replaceAll("\\s+", "").split("/");
            if (strArrSplit.length == 1) {
                return processFactorWithoutDivision(strArrSplit[0]);
            }
            return processFactorWithoutDivision(strArrSplit[0]).divide(processFactorWithoutDivision(strArrSplit[1]));
        }

        private static Factor processFactorWithoutDivision(String str) {
            Factor factor = new Factor();
            for (String str2 : str.split(Pattern.quote("*"))) {
                factor.addPoweredEntity(str2);
            }
            return factor;
        }

        protected Factor copy() {
            Factor factor = new Factor();
            factor.factorNum = this.factorNum;
            factor.factorDen = this.factorDen;
            factor.exponentFtToM = this.exponentFtToM;
            factor.exponentPi = this.exponentPi;
            factor.exponentGravity = this.exponentGravity;
            factor.exponentG = this.exponentG;
            factor.exponentGalImpToM3 = this.exponentGalImpToM3;
            factor.exponentLbToKg = this.exponentLbToKg;
            factor.exponentGlucoseMolarMass = this.exponentGlucoseMolarMass;
            factor.exponentItemPerMole = this.exponentItemPerMole;
            return factor;
        }

        public BigDecimal getConversionRate() {
            Factor factorCopy = copy();
            factorCopy.multiply(new BigDecimal("0.3048"), this.exponentFtToM);
            factorCopy.multiply(new BigDecimal("411557987.0").divide(new BigDecimal("131002976.0"), MathContext.DECIMAL128), this.exponentPi);
            factorCopy.multiply(new BigDecimal("9.80665"), this.exponentGravity);
            factorCopy.multiply(new BigDecimal("6.67408E-11"), this.exponentG);
            factorCopy.multiply(new BigDecimal("0.00454609"), this.exponentGalImpToM3);
            factorCopy.multiply(new BigDecimal("0.45359237"), this.exponentLbToKg);
            factorCopy.multiply(new BigDecimal("180.1557"), this.exponentGlucoseMolarMass);
            factorCopy.multiply(new BigDecimal("6.02214076E+23"), this.exponentItemPerMole);
            return factorCopy.factorNum.divide(factorCopy.factorDen, MathContext.DECIMAL128);
        }

        private void multiply(BigDecimal bigDecimal, int i) {
            if (i == 0) {
                return;
            }
            BigDecimal bigDecimalPow = bigDecimal.pow(Math.abs(i), MathContext.DECIMAL128);
            if (i > 0) {
                this.factorNum = this.factorNum.multiply(bigDecimalPow);
            } else {
                this.factorDen = this.factorDen.multiply(bigDecimalPow);
            }
        }

        public Factor applyPrefix(MeasureUnit.MeasurePrefix measurePrefix) {
            Factor factorCopy = copy();
            if (measurePrefix == MeasureUnit.MeasurePrefix.ONE) {
                return factorCopy;
            }
            int base = measurePrefix.getBase();
            int power = measurePrefix.getPower();
            BigDecimal bigDecimalPow = BigDecimal.valueOf(base).pow(Math.abs(power), MathContext.DECIMAL128);
            if (power < 0) {
                factorCopy.factorDen = this.factorDen.multiply(bigDecimalPow);
                return factorCopy;
            }
            factorCopy.factorNum = this.factorNum.multiply(bigDecimalPow);
            return factorCopy;
        }

        public Factor power(int i) {
            Factor factor = new Factor();
            if (i == 0) {
                return factor;
            }
            if (i > 0) {
                factor.factorNum = this.factorNum.pow(i);
                factor.factorDen = this.factorDen.pow(i);
            } else {
                int i2 = i * (-1);
                factor.factorNum = this.factorDen.pow(i2);
                factor.factorDen = this.factorNum.pow(i2);
            }
            factor.exponentFtToM = this.exponentFtToM * i;
            factor.exponentPi = this.exponentPi * i;
            factor.exponentGravity = this.exponentGravity * i;
            factor.exponentG = this.exponentG * i;
            factor.exponentGalImpToM3 = this.exponentGalImpToM3 * i;
            factor.exponentLbToKg = this.exponentLbToKg * i;
            factor.exponentGlucoseMolarMass = this.exponentGlucoseMolarMass * i;
            factor.exponentItemPerMole = this.exponentItemPerMole * i;
            return factor;
        }

        public Factor divide(Factor factor) {
            Factor factor2 = new Factor();
            factor2.factorNum = this.factorNum.multiply(factor.factorDen);
            factor2.factorDen = this.factorDen.multiply(factor.factorNum);
            factor2.exponentFtToM = this.exponentFtToM - factor.exponentFtToM;
            factor2.exponentPi = this.exponentPi - factor.exponentPi;
            factor2.exponentGravity = this.exponentGravity - factor.exponentGravity;
            factor2.exponentG = this.exponentG - factor.exponentG;
            factor2.exponentGalImpToM3 = this.exponentGalImpToM3 - factor.exponentGalImpToM3;
            factor2.exponentLbToKg = this.exponentLbToKg - factor.exponentLbToKg;
            factor2.exponentGlucoseMolarMass = this.exponentGlucoseMolarMass - factor.exponentGlucoseMolarMass;
            factor2.exponentItemPerMole = this.exponentItemPerMole - factor.exponentItemPerMole;
            return factor2;
        }

        public Factor multiply(Factor factor) {
            Factor factor2 = new Factor();
            factor2.factorNum = this.factorNum.multiply(factor.factorNum);
            factor2.factorDen = this.factorDen.multiply(factor.factorDen);
            factor2.exponentFtToM = this.exponentFtToM + factor.exponentFtToM;
            factor2.exponentPi = this.exponentPi + factor.exponentPi;
            factor2.exponentGravity = this.exponentGravity + factor.exponentGravity;
            factor2.exponentG = this.exponentG + factor.exponentG;
            factor2.exponentGalImpToM3 = this.exponentGalImpToM3 + factor.exponentGalImpToM3;
            factor2.exponentLbToKg = this.exponentLbToKg + factor.exponentLbToKg;
            factor2.exponentGlucoseMolarMass = this.exponentGlucoseMolarMass + factor.exponentGlucoseMolarMass;
            factor2.exponentItemPerMole = this.exponentItemPerMole + factor.exponentItemPerMole;
            return factor2;
        }

        private void addPoweredEntity(String str) {
            String[] strArrSplit = str.split(Pattern.quote("^"));
            addEntity(strArrSplit[0], strArrSplit.length == 2 ? Integer.parseInt(strArrSplit[1]) : 1);
        }

        private void addEntity(String str, int i) {
            if ("ft_to_m".equals(str)) {
                this.exponentFtToM += i;
                return;
            }
            if ("ft2_to_m2".equals(str)) {
                this.exponentFtToM += i * 2;
                return;
            }
            if ("ft3_to_m3".equals(str)) {
                this.exponentFtToM += i * 3;
                return;
            }
            if ("in3_to_m3".equals(str)) {
                this.exponentFtToM += i * 3;
                this.factorDen = this.factorDen.multiply(BigDecimal.valueOf(Math.pow(12.0d, 3.0d)));
                return;
            }
            if ("gal_to_m3".equals(str)) {
                this.factorNum = this.factorNum.multiply(BigDecimal.valueOf(231L));
                this.exponentFtToM += i * 3;
                this.factorDen = this.factorDen.multiply(BigDecimal.valueOf(1728L));
                return;
            }
            if ("gal_imp_to_m3".equals(str)) {
                this.exponentGalImpToM3 += i;
                return;
            }
            if ("G".equals(str)) {
                this.exponentG += i;
                return;
            }
            if ("gravity".equals(str)) {
                this.exponentGravity += i;
                return;
            }
            if ("lb_to_kg".equals(str)) {
                this.exponentLbToKg += i;
                return;
            }
            if ("glucose_molar_mass".equals(str)) {
                this.exponentGlucoseMolarMass += i;
                return;
            }
            if ("item_per_mole".equals(str)) {
                this.exponentItemPerMole += i;
            } else if ("PI".equals(str)) {
                this.exponentPi += i;
            } else {
                this.factorNum = this.factorNum.multiply(new BigDecimal(str).pow(i, MathContext.DECIMAL128));
            }
        }
    }

    public String toString() {
        return "UnitsConverter [conversionRate=" + this.conversionRate + ", offset=" + this.offset + "]";
    }
}
