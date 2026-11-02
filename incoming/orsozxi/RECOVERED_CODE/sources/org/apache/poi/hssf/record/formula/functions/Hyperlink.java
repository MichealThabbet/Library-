package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* loaded from: classes3.dex */
public final class Hyperlink extends Var1or2ArgFunction {
    @Override // org.apache.poi.hssf.record.formula.functions.Function1Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        return valueEval;
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function2Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        return valueEval2;
    }
}
