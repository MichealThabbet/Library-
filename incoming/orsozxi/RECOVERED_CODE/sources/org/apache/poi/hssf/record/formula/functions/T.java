package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.AreaEval;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.RefEval;
import org.apache.poi.hssf.record.formula.eval.StringEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* loaded from: classes3.dex */
public final class T extends Fixed1ArgFunction {
    @Override // org.apache.poi.hssf.record.formula.functions.Function1Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        if (valueEval instanceof RefEval) {
            valueEval = ((RefEval) valueEval).getInnerValueEval();
        } else if (valueEval instanceof AreaEval) {
            valueEval = ((AreaEval) valueEval).getRelativeValue(0, 0);
        }
        return ((valueEval instanceof StringEval) || (valueEval instanceof ErrorEval)) ? valueEval : StringEval.EMPTY_INSTANCE;
    }
}
