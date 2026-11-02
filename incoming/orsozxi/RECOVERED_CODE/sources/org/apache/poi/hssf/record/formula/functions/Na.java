package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* loaded from: classes3.dex */
public final class Na extends Fixed0ArgFunction {
    @Override // org.apache.poi.hssf.record.formula.functions.Function0Arg
    public ValueEval evaluate(int i, int i2) {
        return ErrorEval.NA;
    }
}
