package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.functions.XYNumericFunction;

/* loaded from: classes3.dex */
public final class Sumxmy2 extends XYNumericFunction {
    private static final XYNumericFunction.Accumulator XMinusYSquaredAccumulator = new XYNumericFunction.Accumulator() { // from class: org.apache.poi.hssf.record.formula.functions.Sumxmy2.1
        @Override // org.apache.poi.hssf.record.formula.functions.XYNumericFunction.Accumulator
        public double accumulate(double d, double d2) {
            double d3 = d - d2;
            return d3 * d3;
        }
    };

    @Override // org.apache.poi.hssf.record.formula.functions.XYNumericFunction
    protected XYNumericFunction.Accumulator createAccumulator() {
        return XMinusYSquaredAccumulator;
    }
}
