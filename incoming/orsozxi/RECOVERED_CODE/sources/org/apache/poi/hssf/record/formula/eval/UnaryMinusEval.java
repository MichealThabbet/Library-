package org.apache.poi.hssf.record.formula.eval;

import org.apache.poi.hssf.record.formula.functions.Fixed1ArgFunction;
import org.apache.poi.hssf.record.formula.functions.Function;

/* loaded from: classes3.dex */
public final class UnaryMinusEval extends Fixed1ArgFunction {
    public static final Function instance = new UnaryMinusEval();

    private UnaryMinusEval() {
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function1Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        try {
            double dCoerceValueToDouble = OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(valueEval, i, i2));
            if (dCoerceValueToDouble == 0.0d) {
                return NumberEval.ZERO;
            }
            return new NumberEval(-dCoerceValueToDouble);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }
}
