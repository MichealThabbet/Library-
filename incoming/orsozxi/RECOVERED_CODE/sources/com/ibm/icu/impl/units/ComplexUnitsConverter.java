package com.ibm.icu.impl.units;

import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.ibm.icu.impl.units.MeasureUnitImpl;
import com.ibm.icu.number.Precision;
import com.ibm.icu.util.Measure;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class ComplexUnitsConverter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final BigDecimal EPSILON = BigDecimal.valueOf(Math.ulp(1.0d));
    public static final BigDecimal EPSILON_MULTIPLIER = BigDecimal.valueOf(1L).add(EPSILON);
    private MeasureUnitImpl inputUnit_;
    private ArrayList<UnitsConverter> unitsConverters_;
    private List<MeasureUnitImpl.MeasureUnitImplWithIndex> units_;

    public ComplexUnitsConverter(MeasureUnitImpl measureUnitImpl, ConversionRates conversionRates) {
        ArrayList<MeasureUnitImpl.MeasureUnitImplWithIndex> arrayListExtractIndividualUnitsWithIndices = measureUnitImpl.extractIndividualUnitsWithIndices();
        this.units_ = arrayListExtractIndividualUnitsWithIndices;
        this.inputUnit_ = arrayListExtractIndividualUnitsWithIndices.get(0).unitImpl;
        MeasureUnitImpl.MeasureUnitImplComparator measureUnitImplComparator = new MeasureUnitImpl.MeasureUnitImplComparator(conversionRates);
        for (MeasureUnitImpl.MeasureUnitImplWithIndex measureUnitImplWithIndex : this.units_) {
            if (measureUnitImplComparator.compare(measureUnitImplWithIndex.unitImpl, this.inputUnit_) > 0) {
                this.inputUnit_ = measureUnitImplWithIndex.unitImpl;
            }
        }
        init(conversionRates);
    }

    public ComplexUnitsConverter(String str, String str2) {
        this(MeasureUnitImpl.forIdentifier(str), MeasureUnitImpl.forIdentifier(str2), new ConversionRates());
    }

    public ComplexUnitsConverter(MeasureUnitImpl measureUnitImpl, MeasureUnitImpl measureUnitImpl2, ConversionRates conversionRates) {
        this.inputUnit_ = measureUnitImpl;
        this.units_ = measureUnitImpl2.extractIndividualUnitsWithIndices();
        init(conversionRates);
    }

    private void init(ConversionRates conversionRates) {
        Collections.sort(this.units_, Collections.reverseOrder(new MeasureUnitImpl.MeasureUnitImplWithIndexComparator(conversionRates)));
        this.unitsConverters_ = new ArrayList<>();
        int size = this.units_.size();
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                this.unitsConverters_.add(new UnitsConverter(this.inputUnit_, this.units_.get(i).unitImpl, conversionRates));
            } else {
                this.unitsConverters_.add(new UnitsConverter(this.units_.get(i - 1).unitImpl, this.units_.get(i).unitImpl, conversionRates));
            }
        }
    }

    public boolean greaterThanOrEqual(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
        return this.unitsConverters_.get(0).convert(bigDecimal).multiply(EPSILON_MULTIPLIER).compareTo(bigDecimal2) >= 0;
    }

    public static class ComplexConverterResult {
        public final int indexOfQuantity;
        public final List<Measure> measures;

        ComplexConverterResult(int i, List<Measure> list) {
            this.indexOfQuantity = i;
            this.measures = list;
        }
    }

    public ComplexConverterResult convert(BigDecimal bigDecimal, Precision precision) {
        int i;
        BigInteger bigIntegerNegate = BigInteger.ONE;
        if (bigDecimal.compareTo(BigDecimal.ZERO) < 0) {
            bigDecimal = bigDecimal.abs();
            bigIntegerNegate = bigIntegerNegate.negate();
        }
        ArrayList arrayList = new ArrayList(this.unitsConverters_.size() - 1);
        int size = this.unitsConverters_.size();
        int i2 = 0;
        while (true) {
            i = -1;
            if (i2 >= size) {
                break;
            }
            bigDecimal = this.unitsConverters_.get(i2).convert(bigDecimal);
            if (i2 < size - 1) {
                BigInteger bigInteger = bigDecimal.multiply(EPSILON_MULTIPLIER).setScale(0, RoundingMode.FLOOR).toBigInteger();
                arrayList.add(bigInteger);
                bigDecimal = bigDecimal.subtract(BigDecimal.valueOf(bigInteger.longValue()));
                if (bigDecimal.compareTo(BigDecimal.ZERO) == -1) {
                    bigDecimal = BigDecimal.ZERO;
                }
            }
            i2++;
        }
        BigDecimal bigDecimalApplyRounder = applyRounder(arrayList, bigDecimal, precision);
        ArrayList arrayList2 = new ArrayList(this.unitsConverters_.size());
        for (int i3 = 0; i3 < this.unitsConverters_.size(); i3++) {
            arrayList2.add(null);
        }
        int size2 = this.unitsConverters_.size();
        for (int i4 = 0; i4 < size2; i4++) {
            if (i4 < size2 - 1) {
                arrayList2.set(this.units_.get(i4).index, new Measure(arrayList.get(i4).multiply(bigIntegerNegate), this.units_.get(i4).unitImpl.build()));
            } else {
                int i5 = this.units_.get(i4).index;
                arrayList2.set(i5, new Measure(bigDecimalApplyRounder.multiply(BigDecimal.valueOf(bigIntegerNegate.longValue())), this.units_.get(i4).unitImpl.build()));
                i = i5;
            }
        }
        return new ComplexConverterResult(i, arrayList2);
    }

    private BigDecimal applyRounder(List<BigInteger> list, BigDecimal bigDecimal, Precision precision) {
        if (precision == null) {
            return bigDecimal;
        }
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(bigDecimal);
        precision.apply(decimalQuantity_DualStorageBCD);
        BigDecimal bigDecimal2 = decimalQuantity_DualStorageBCD.toBigDecimal();
        if (list.size() == 0) {
            return bigDecimal2;
        }
        int size = this.unitsConverters_.size() - 1;
        BigDecimal scale = this.unitsConverters_.get(size).convertInverse(bigDecimal2).multiply(EPSILON_MULTIPLIER).setScale(0, RoundingMode.FLOOR);
        if (scale.compareTo(BigDecimal.ZERO) <= 0) {
            return bigDecimal2;
        }
        BigDecimal bigDecimalSubtract = bigDecimal2.subtract(this.unitsConverters_.get(size).convert(scale));
        int i = size - 1;
        list.set(i, list.get(i).add(scale.toBigInteger()));
        while (i > 0) {
            BigDecimal scale2 = this.unitsConverters_.get(i).convertInverse(BigDecimal.valueOf(list.get(i).longValue())).multiply(EPSILON_MULTIPLIER).setScale(0, RoundingMode.FLOOR);
            if (scale2.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
            list.set(i, list.get(i).subtract(this.unitsConverters_.get(i).convert(scale2).toBigInteger()));
            int i2 = i - 1;
            list.set(i2, list.get(i2).add(scale2.toBigInteger()));
            i--;
        }
        return bigDecimalSubtract;
    }

    public String toString() {
        return "ComplexUnitsConverter [unitsConverters_=" + this.unitsConverters_ + ", units_=" + this.units_ + "]";
    }
}
