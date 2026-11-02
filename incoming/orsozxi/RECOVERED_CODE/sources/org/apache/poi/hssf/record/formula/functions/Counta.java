package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.BlankEval;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.record.formula.functions.CountUtils;

/* loaded from: classes3.dex */
public final class Counta implements Function {
    private static final CountUtils.I_MatchPredicate predicate = new CountUtils.I_MatchPredicate() { // from class: org.apache.poi.hssf.record.formula.functions.Counta.1
        @Override // org.apache.poi.hssf.record.formula.functions.CountUtils.I_MatchPredicate
        public boolean matches(ValueEval valueEval) {
            return valueEval != BlankEval.instance;
        }
    };

    @Override // org.apache.poi.hssf.record.formula.functions.Function
    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        int length = valueEvalArr.length;
        if (length < 1) {
            return ErrorEval.VALUE_INVALID;
        }
        if (length > 30) {
            return ErrorEval.VALUE_INVALID;
        }
        int iCountArg = 0;
        for (ValueEval valueEval : valueEvalArr) {
            iCountArg += CountUtils.countArg(valueEval, predicate);
        }
        return new NumberEval(iCountArg);
    }
}
