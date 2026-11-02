package com.ibm.icu.impl.number;

import com.ibm.icu.number.IntegerWidth;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Precision;
import com.ibm.icu.text.DecimalFormatSymbols;
import com.ibm.icu.util.Measure;
import com.ibm.icu.util.MeasureUnit;
import java.util.List;

/* loaded from: classes2.dex */
public class MicroProps implements Cloneable, MicroPropsGenerator {
    public NumberFormatter.DecimalSeparatorDisplay decimal;
    private volatile boolean exhausted;
    public String gender;
    public Grouper grouping;
    private final boolean immutable;
    public int indexOfQuantity = -1;
    public IntegerWidth integerWidth;
    public List<Measure> mixedMeasures;
    public Modifier modInner;
    public Modifier modMiddle;
    public Modifier modOuter;
    public String nsName;
    public MeasureUnit outputUnit;
    public Padder padding;
    public Precision rounder;
    public NumberFormatter.SignDisplay sign;
    public DecimalFormatSymbols symbols;
    public boolean useCurrency;

    public MicroProps(boolean z) {
        this.immutable = z;
    }

    @Override // com.ibm.icu.impl.number.MicroPropsGenerator
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
        if (this.immutable) {
            return (MicroProps) clone();
        }
        if (this.exhausted) {
            throw new AssertionError("Cannot re-use a mutable MicroProps in the quantity chain");
        }
        this.exhausted = true;
        return this;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
