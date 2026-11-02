package org.apache.poi.hssf.record.formula.eval;

import org.apache.poi.hssf.record.formula.functions.Fixed1ArgFunction;
import org.apache.poi.hssf.record.formula.functions.Function;

/* loaded from: classes3.dex */
public final class UnaryPlusEval extends Fixed1ArgFunction {
    public static final Function instance = new UnaryPlusEval();

    private UnaryPlusEval() {
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function1Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        try {
            ValueEval singleValue = OperandResolver.getSingleValue(valueEval, i, i2);
            return singleValue instanceof StringEval ? singleValue : new NumberEval(OperandResolver.coerceValueToDouble(singleValue));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }
}
