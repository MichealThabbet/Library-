package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.BlankEval;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.RefEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.record.formula.functions.CountUtils;
import org.apache.poi.ss.formula.TwoDEval;

/* loaded from: classes3.dex */
public final class Countblank extends Fixed1ArgFunction {
    private static final CountUtils.I_MatchPredicate predicate = new CountUtils.I_MatchPredicate() { // from class: org.apache.poi.hssf.record.formula.functions.Countblank.1
        @Override // org.apache.poi.hssf.record.formula.functions.CountUtils.I_MatchPredicate
        public boolean matches(ValueEval valueEval) {
            return valueEval == BlankEval.instance;
        }
    };

    @Override // org.apache.poi.hssf.record.formula.functions.Function1Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        int iCountMatchingCellsInArea;
        if (valueEval instanceof RefEval) {
            iCountMatchingCellsInArea = CountUtils.countMatchingCell((RefEval) valueEval, predicate);
        } else if (valueEval instanceof TwoDEval) {
            iCountMatchingCellsInArea = CountUtils.countMatchingCellsInArea((TwoDEval) valueEval, predicate);
        } else {
            throw new IllegalArgumentException("Bad range arg type (" + valueEval.getClass().getName() + ")");
        }
        return new NumberEval(iCountMatchingCellsInArea);
    }
}
