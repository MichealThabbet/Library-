package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.StringEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* loaded from: classes3.dex */
public final class Replace extends Fixed4ArgFunction {
    @Override // org.apache.poi.hssf.record.formula.functions.Function4Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3, ValueEval valueEval4) {
        try {
            String strEvaluateStringArg = TextFunction.evaluateStringArg(valueEval, i, i2);
            int iEvaluateIntArg = TextFunction.evaluateIntArg(valueEval2, i, i2);
            int iEvaluateIntArg2 = TextFunction.evaluateIntArg(valueEval3, i, i2);
            String strEvaluateStringArg2 = TextFunction.evaluateStringArg(valueEval4, i, i2);
            if (iEvaluateIntArg < 1 || iEvaluateIntArg2 < 0) {
                return ErrorEval.VALUE_INVALID;
            }
            StringBuffer stringBuffer = new StringBuffer(strEvaluateStringArg);
            if (iEvaluateIntArg <= strEvaluateStringArg.length() && iEvaluateIntArg2 != 0) {
                int i3 = iEvaluateIntArg - 1;
                stringBuffer.delete(i3, iEvaluateIntArg2 + i3);
            }
            if (iEvaluateIntArg > stringBuffer.length()) {
                stringBuffer.append(strEvaluateStringArg2);
            } else {
                stringBuffer.insert(iEvaluateIntArg - 1, strEvaluateStringArg2);
            }
            return new StringEval(stringBuffer.toString());
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }
}
