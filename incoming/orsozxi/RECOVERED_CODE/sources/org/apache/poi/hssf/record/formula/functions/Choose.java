package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.BlankEval;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.MissingArgEval;
import org.apache.poi.hssf.record.formula.eval.OperandResolver;
import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* loaded from: classes3.dex */
public final class Choose implements Function {
    @Override // org.apache.poi.hssf.record.formula.functions.Function
    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        if (valueEvalArr.length < 2) {
            return ErrorEval.VALUE_INVALID;
        }
        try {
            int iEvaluateFirstArg = evaluateFirstArg(valueEvalArr[0], i, i2);
            if (iEvaluateFirstArg >= 1 && iEvaluateFirstArg < valueEvalArr.length) {
                ValueEval singleValue = OperandResolver.getSingleValue(valueEvalArr[iEvaluateFirstArg], i, i2);
                return singleValue == MissingArgEval.instance ? BlankEval.instance : singleValue;
            }
            return ErrorEval.VALUE_INVALID;
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    public static int evaluateFirstArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        return OperandResolver.coerceValueToInt(OperandResolver.getSingleValue(valueEval, i, i2));
    }
}
