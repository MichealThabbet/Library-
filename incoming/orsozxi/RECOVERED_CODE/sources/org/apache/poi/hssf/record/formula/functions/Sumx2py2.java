package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.functions.XYNumericFunction;

/* loaded from: classes3.dex */
public final class Sumx2py2 extends XYNumericFunction {
    private static final XYNumericFunction.Accumulator XSquaredPlusYSquaredAccumulator = new XYNumericFunction.Accumulator() { // from class: org.apache.poi.hssf.record.formula.functions.Sumx2py2.1
        @Override // org.apache.poi.hssf.record.formula.functions.XYNumericFunction.Accumulator
        public double accumulate(double d, double d2) {
            return (d * d) + (d2 * d2);
        }
    };

    @Override // org.apache.poi.hssf.record.formula.functions.XYNumericFunction
    protected XYNumericFunction.Accumulator createAccumulator() {
        return XSquaredPlusYSquaredAccumulator;
    }
}
