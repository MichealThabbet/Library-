package org.apache.poi.hssf.record.formula.eval;

/* loaded from: classes3.dex */
public abstract class RefEvalBase implements RefEval {
    private final int _columnIndex;
    private final int _rowIndex;

    protected RefEvalBase(int i, int i2) {
        this._rowIndex = i;
        this._columnIndex = i2;
    }

    @Override // org.apache.poi.hssf.record.formula.eval.RefEval
    public final int getRow() {
        return this._rowIndex;
    }

    @Override // org.apache.poi.hssf.record.formula.eval.RefEval
    public final int getColumn() {
        return this._columnIndex;
    }
}
