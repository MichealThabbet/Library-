package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.AreaEval;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.RefEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* loaded from: classes3.dex */
public final class RowFunc implements Function0Arg, Function1Arg {
    @Override // org.apache.poi.hssf.record.formula.functions.Function0Arg
    public ValueEval evaluate(int i, int i2) {
        return new NumberEval(i + 1);
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function1Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        int row;
        if (valueEval instanceof AreaEval) {
            row = ((AreaEval) valueEval).getFirstRow();
        } else if (valueEval instanceof RefEval) {
            row = ((RefEval) valueEval).getRow();
        } else {
            return ErrorEval.VALUE_INVALID;
        }
        return new NumberEval(row + 1);
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function
    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        int length = valueEvalArr.length;
        if (length == 0) {
            return new NumberEval(i + 1);
        }
        if (length == 1) {
            return evaluate(i, i2, valueEvalArr[0]);
        }
        return ErrorEval.VALUE_INVALID;
    }
}
