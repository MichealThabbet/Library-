package org.apache.poi.ss.formula;

import org.apache.poi.hssf.record.formula.eval.NameEval;
import org.apache.poi.hssf.record.formula.eval.NameXEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.record.formula.functions.FreeRefFunction;
import org.apache.poi.ss.formula.eval.NotImplementedException;

/* loaded from: classes3.dex */
final class UserDefinedFunction implements FreeRefFunction {
    public static final FreeRefFunction instance = new UserDefinedFunction();

    private UserDefinedFunction() {
    }

    @Override // org.apache.poi.hssf.record.formula.functions.FreeRefFunction
    public ValueEval evaluate(ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext) {
        String strResolveNameXText;
        int length = valueEvalArr.length;
        if (length < 1) {
            throw new RuntimeException("function name argument missing");
        }
        ValueEval valueEval = valueEvalArr[0];
        if (valueEval instanceof NameEval) {
            strResolveNameXText = ((NameEval) valueEval).getFunctionName();
        } else if (valueEval instanceof NameXEval) {
            strResolveNameXText = operationEvaluationContext.getWorkbook().resolveNameXText(((NameXEval) valueEval).getPtg());
        } else {
            throw new RuntimeException("First argument should be a NameEval, but got (" + valueEval.getClass().getName() + ")");
        }
        FreeRefFunction freeRefFunctionFindUserDefinedFunction = operationEvaluationContext.findUserDefinedFunction(strResolveNameXText);
        if (freeRefFunctionFindUserDefinedFunction == null) {
            throw new NotImplementedException(strResolveNameXText);
        }
        int i = length - 1;
        ValueEval[] valueEvalArr2 = new ValueEval[i];
        System.arraycopy(valueEvalArr, 1, valueEvalArr2, 0, i);
        return freeRefFunctionFindUserDefinedFunction.evaluate(valueEvalArr2, operationEvaluationContext);
    }
}
