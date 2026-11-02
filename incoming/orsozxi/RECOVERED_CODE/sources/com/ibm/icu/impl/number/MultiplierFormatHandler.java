package com.ibm.icu.impl.number;

import com.ibm.icu.number.Scale;

/* loaded from: classes2.dex */
public class MultiplierFormatHandler implements MicroPropsGenerator {
    final Scale multiplier;
    final MicroPropsGenerator parent;

    public MultiplierFormatHandler(Scale scale, MicroPropsGenerator microPropsGenerator) {
        this.multiplier = scale;
        this.parent = microPropsGenerator;
    }

    @Override // com.ibm.icu.impl.number.MicroPropsGenerator
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
        MicroProps microPropsProcessQuantity = this.parent.processQuantity(decimalQuantity);
        this.multiplier.applyTo(decimalQuantity);
        return microPropsProcessQuantity;
    }
}
