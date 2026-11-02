package org.apache.poi.hssf.record.formula.atp;

import org.apache.poi.hssf.record.formula.eval.BoolEval;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.OperandResolver;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.record.formula.functions.FreeRefFunction;
import org.apache.poi.ss.formula.OperationEvaluationContext;

/* loaded from: classes3.dex */
final class ParityFunction implements FreeRefFunction {
    public static final FreeRefFunction IS_EVEN = new ParityFunction(0);
    public static final FreeRefFunction IS_ODD = new ParityFunction(1);
    private final int _desiredParity;

    private ParityFunction(int i) {
        this._desiredParity = i;
    }

    @Override // org.apache.poi.hssf.record.formula.functions.FreeRefFunction
    public ValueEval evaluate(ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext) {
        if (valueEvalArr.length != 1) {
            return ErrorEval.VALUE_INVALID;
        }
        try {
            return BoolEval.valueOf(evaluateArgParity(valueEvalArr[0], operationEvaluationContext.getRowIndex(), operationEvaluationContext.getColumnIndex()) == this._desiredParity);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static int evaluateArgParity(ValueEval valueEval, int i, int i2) throws EvaluationException {
        double dCoerceValueToDouble = OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(valueEval, i, (short) i2));
        if (dCoerceValueToDouble < 0.0d) {
            dCoerceValueToDouble = -dCoerceValueToDouble;
        }
        return (int) (((long) Math.floor(dCoerceValueToDouble)) & 1);
    }
}
