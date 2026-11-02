package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* loaded from: classes3.dex */
abstract class Var3or4ArgFunction implements Function3Arg, Function4Arg {
    Var3or4ArgFunction() {
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function
    public final ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        int length = valueEvalArr.length;
        if (length == 3) {
            return evaluate(i, i2, valueEvalArr[0], valueEvalArr[1], valueEvalArr[2]);
        }
        if (length == 4) {
            return evaluate(i, i2, valueEvalArr[0], valueEvalArr[1], valueEvalArr[2], valueEvalArr[3]);
        }
        return ErrorEval.VALUE_INVALID;
    }
}
