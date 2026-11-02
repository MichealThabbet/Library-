package org.apache.poi.ss.formula;

import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* loaded from: classes3.dex */
final class SheetRefEvaluator {
    private final WorkbookEvaluator _bookEvaluator;
    private EvaluationSheet _sheet;
    private final int _sheetIndex;
    private final EvaluationTracker _tracker;

    public SheetRefEvaluator(WorkbookEvaluator workbookEvaluator, EvaluationTracker evaluationTracker, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Invalid sheetIndex: " + i + ".");
        }
        this._bookEvaluator = workbookEvaluator;
        this._tracker = evaluationTracker;
        this._sheetIndex = i;
    }

    public String getSheetName() {
        return this._bookEvaluator.getSheetName(this._sheetIndex);
    }

    public ValueEval getEvalForCell(int i, int i2) {
        return this._bookEvaluator.evaluateReference(getSheet(), this._sheetIndex, i, i2, this._tracker);
    }

    private EvaluationSheet getSheet() {
        if (this._sheet == null) {
            this._sheet = this._bookEvaluator.getSheet(this._sheetIndex);
        }
        return this._sheet;
    }
}
