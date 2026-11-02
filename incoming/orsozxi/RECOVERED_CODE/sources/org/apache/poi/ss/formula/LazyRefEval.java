package org.apache.poi.ss.formula;

import org.apache.poi.hssf.record.formula.AreaI;
import org.apache.poi.hssf.record.formula.eval.AreaEval;
import org.apache.poi.hssf.record.formula.eval.RefEvalBase;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.util.CellReference;

/* loaded from: classes3.dex */
final class LazyRefEval extends RefEvalBase {
    private final SheetRefEvaluator _evaluator;

    public LazyRefEval(int i, int i2, SheetRefEvaluator sheetRefEvaluator) {
        super(i, i2);
        if (sheetRefEvaluator == null) {
            throw new IllegalArgumentException("sre must not be null");
        }
        this._evaluator = sheetRefEvaluator;
    }

    @Override // org.apache.poi.hssf.record.formula.eval.RefEval
    public ValueEval getInnerValueEval() {
        return this._evaluator.getEvalForCell(getRow(), getColumn());
    }

    @Override // org.apache.poi.hssf.record.formula.eval.RefEval
    public AreaEval offset(int i, int i2, int i3, int i4) {
        return new LazyAreaEval(new AreaI.OffsetArea(getRow(), getColumn(), i, i2, i3, i4), this._evaluator);
    }

    public String toString() {
        CellReference cellReference = new CellReference(getRow(), getColumn());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        stringBuffer.append("[");
        stringBuffer.append(this._evaluator.getSheetName());
        stringBuffer.append('!');
        stringBuffer.append(cellReference.formatAsString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
