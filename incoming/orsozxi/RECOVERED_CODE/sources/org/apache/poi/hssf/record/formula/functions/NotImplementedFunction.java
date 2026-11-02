package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.ss.formula.eval.NotImplementedException;

/* loaded from: classes3.dex */
public final class NotImplementedFunction implements Function {
    private final String _functionName;

    protected NotImplementedFunction() {
        this._functionName = getClass().getName();
    }

    public NotImplementedFunction(String str) {
        this._functionName = str;
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function
    public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        throw new NotImplementedException(this._functionName);
    }

    public String getFunctionName() {
        return this._functionName;
    }
}
