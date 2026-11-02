package org.apache.poi.hssf.record.formula.eval;

import org.apache.poi.hssf.record.formula.AreaI;

/* loaded from: classes3.dex */
public abstract class AreaEvalBase implements AreaEval {
    private final int _firstColumn;
    private final int _firstRow;
    private final int _lastColumn;
    private final int _lastRow;
    private final int _nColumns;
    private final int _nRows;

    @Override // org.apache.poi.hssf.record.formula.eval.AreaEval
    public abstract ValueEval getRelativeValue(int i, int i2);

    protected AreaEvalBase(int i, int i2, int i3, int i4) {
        this._firstColumn = i2;
        this._firstRow = i;
        this._lastColumn = i4;
        this._lastRow = i3;
        this._nColumns = (i4 - i2) + 1;
        this._nRows = (i3 - i) + 1;
    }

    protected AreaEvalBase(AreaI areaI) {
        this._firstRow = areaI.getFirstRow();
        this._firstColumn = areaI.getFirstColumn();
        this._lastRow = areaI.getLastRow();
        int lastColumn = areaI.getLastColumn();
        this._lastColumn = lastColumn;
        this._nColumns = (lastColumn - this._firstColumn) + 1;
        this._nRows = (this._lastRow - this._firstRow) + 1;
    }

    @Override // org.apache.poi.hssf.record.formula.eval.AreaEval
    public final int getFirstColumn() {
        return this._firstColumn;
    }

    @Override // org.apache.poi.hssf.record.formula.eval.AreaEval
    public final int getFirstRow() {
        return this._firstRow;
    }

    @Override // org.apache.poi.hssf.record.formula.eval.AreaEval
    public final int getLastColumn() {
        return this._lastColumn;
    }

    @Override // org.apache.poi.hssf.record.formula.eval.AreaEval
    public final int getLastRow() {
        return this._lastRow;
    }

    @Override // org.apache.poi.hssf.record.formula.eval.AreaEval
    public final ValueEval getAbsoluteValue(int i, int i2) {
        int i3 = i - this._firstRow;
        int i4 = i2 - this._firstColumn;
        if (i3 < 0 || i3 >= this._nRows) {
            throw new IllegalArgumentException("Specified row index (" + i + ") is outside the allowed range (" + this._firstRow + ".." + this._lastRow + ")");
        }
        if (i4 < 0 || i4 >= this._nColumns) {
            throw new IllegalArgumentException("Specified column index (" + i2 + ") is outside the allowed range (" + this._firstColumn + ".." + i2 + ")");
        }
        return getRelativeValue(i3, i4);
    }

    @Override // org.apache.poi.hssf.record.formula.eval.AreaEval
    public final boolean contains(int i, int i2) {
        return this._firstRow <= i && this._lastRow >= i && this._firstColumn <= i2 && this._lastColumn >= i2;
    }

    @Override // org.apache.poi.hssf.record.formula.eval.AreaEval
    public final boolean containsRow(int i) {
        return this._firstRow <= i && this._lastRow >= i;
    }

    @Override // org.apache.poi.hssf.record.formula.eval.AreaEval
    public final boolean containsColumn(int i) {
        return this._firstColumn <= i && this._lastColumn >= i;
    }

    @Override // org.apache.poi.ss.formula.TwoDEval
    public final boolean isColumn() {
        return this._firstColumn == this._lastColumn;
    }

    @Override // org.apache.poi.ss.formula.TwoDEval
    public final boolean isRow() {
        return this._firstRow == this._lastRow;
    }

    @Override // org.apache.poi.hssf.record.formula.eval.AreaEval, org.apache.poi.ss.formula.TwoDEval
    public int getHeight() {
        return (this._lastRow - this._firstRow) + 1;
    }

    @Override // org.apache.poi.ss.formula.TwoDEval
    public final ValueEval getValue(int i, int i2) {
        return getRelativeValue(i, i2);
    }

    @Override // org.apache.poi.hssf.record.formula.eval.AreaEval, org.apache.poi.ss.formula.TwoDEval
    public int getWidth() {
        return (this._lastColumn - this._firstColumn) + 1;
    }
}
