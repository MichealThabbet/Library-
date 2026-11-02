package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.OperandResolver;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.ss.formula.eval.NotImplementedException;

/* loaded from: classes3.dex */
public class Subtotal implements Function {
    private static Function findFunction(int i) throws EvaluationException {
        switch (i) {
            case 1:
                return AggregateFunction.AVERAGE;
            case 2:
                return new Count();
            case 3:
                return new Counta();
            case 4:
                return AggregateFunction.MAX;
            case 5:
                return AggregateFunction.MIN;
            case 6:
                return AggregateFunction.PRODUCT;
            case 7:
                return AggregateFunction.STDEV;
            case 8:
                throw new NotImplementedException("STDEVP");
            case 9:
                return AggregateFunction.SUM;
            case 10:
                throw new NotImplementedException("VAR");
            case 11:
                throw new NotImplementedException("VARP");
            default:
                if (i > 100 && i < 112) {
                    throw new NotImplementedException("SUBTOTAL - with 'exclude hidden values' option");
                }
                throw EvaluationException.invalidValue();
        }
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function
    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        int length = valueEvalArr.length - 1;
        if (length < 1) {
            return ErrorEval.VALUE_INVALID;
        }
        try {
            Function functionFindFunction = findFunction(OperandResolver.coerceValueToInt(OperandResolver.getSingleValue(valueEvalArr[0], i, i2)));
            ValueEval[] valueEvalArr2 = new ValueEval[length];
            System.arraycopy(valueEvalArr, 1, valueEvalArr2, 0, length);
            return functionFindFunction.evaluate(valueEvalArr2, i, i2);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }
}
