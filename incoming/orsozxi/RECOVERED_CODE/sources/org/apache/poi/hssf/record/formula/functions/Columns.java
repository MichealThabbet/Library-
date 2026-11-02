package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.RefEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.ss.formula.TwoDEval;

/* loaded from: classes3.dex */
public final class Columns extends Fixed1ArgFunction {
    @Override // org.apache.poi.hssf.record.formula.functions.Function1Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        int width;
        if (valueEval instanceof TwoDEval) {
            width = ((TwoDEval) valueEval).getWidth();
        } else {
            if (!(valueEval instanceof RefEval)) {
                return ErrorEval.VALUE_INVALID;
            }
            width = 1;
        }
        return new NumberEval(width);
    }
}
