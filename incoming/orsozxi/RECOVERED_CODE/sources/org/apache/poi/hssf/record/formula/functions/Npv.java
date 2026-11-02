package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* loaded from: classes3.dex */
public final class Npv implements Function2Arg, Function3Arg, Function4Arg {
    @Override // org.apache.poi.hssf.record.formula.functions.Function2Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        try {
            double dEvaluate = evaluate(NumericFunction.singleOperandEvaluate(valueEval, i, i2), NumericFunction.singleOperandEvaluate(valueEval2, i, i2));
            NumericFunction.checkValue(dEvaluate);
            return new NumberEval(dEvaluate);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function3Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
        try {
            double dEvaluate = evaluate(NumericFunction.singleOperandEvaluate(valueEval, i, i2), NumericFunction.singleOperandEvaluate(valueEval2, i, i2), NumericFunction.singleOperandEvaluate(valueEval3, i, i2));
            NumericFunction.checkValue(dEvaluate);
            return new NumberEval(dEvaluate);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function4Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3, ValueEval valueEval4) {
        try {
            double dEvaluate = evaluate(NumericFunction.singleOperandEvaluate(valueEval, i, i2), NumericFunction.singleOperandEvaluate(valueEval2, i, i2), NumericFunction.singleOperandEvaluate(valueEval3, i, i2), NumericFunction.singleOperandEvaluate(valueEval4, i, i2));
            NumericFunction.checkValue(dEvaluate);
            return new NumberEval(dEvaluate);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function
    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        int length = valueEvalArr.length;
        if (length < 2) {
            return ErrorEval.VALUE_INVALID;
        }
        int i3 = length - 1;
        double[] dArr = new double[i3];
        int i4 = 0;
        try {
            double dSingleOperandEvaluate = NumericFunction.singleOperandEvaluate(valueEvalArr[0], i, i2);
            while (i4 < i3) {
                int i5 = i4 + 1;
                dArr[i4] = NumericFunction.singleOperandEvaluate(valueEvalArr[i5], i, i2);
                i4 = i5;
            }
            double dEvaluate = evaluate(dSingleOperandEvaluate, dArr);
            NumericFunction.checkValue(dEvaluate);
            return new NumberEval(dEvaluate);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static double evaluate(double d, double... dArr) {
        double dPow = 0.0d;
        for (int i = 0; i < dArr.length; i++) {
            dPow += dArr[i] / Math.pow(1.0d + d, i);
        }
        return dPow;
    }
}
