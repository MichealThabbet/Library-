package org.apache.poi.hssf.record.formula;

/* loaded from: classes3.dex */
public final class FormulaShifter {
    private final int _amountToMove;
    private final int _externSheetIndex;
    private final int _firstMovedIndex;
    private final int _lastMovedIndex;

    private FormulaShifter(int i, int i2, int i3, int i4) {
        if (i4 == 0) {
            throw new IllegalArgumentException("amountToMove must not be zero");
        }
        if (i2 > i3) {
            throw new IllegalArgumentException("firstMovedIndex, lastMovedIndex out of order");
        }
        this._externSheetIndex = i;
        this._firstMovedIndex = i2;
        this._lastMovedIndex = i3;
        this._amountToMove = i4;
    }

    public static FormulaShifter createForRowShift(int i, int i2, int i3, int i4) {
        return new FormulaShifter(i, i2, i3, i4);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [");
        stringBuffer.append(this._firstMovedIndex);
        stringBuffer.append(this._lastMovedIndex);
        stringBuffer.append(this._amountToMove);
        return stringBuffer.toString();
    }

    public boolean adjustFormula(Ptg[] ptgArr, int i) {
        boolean z = false;
        for (int i2 = 0; i2 < ptgArr.length; i2++) {
            Ptg ptgAdjustPtg = adjustPtg(ptgArr[i2], i);
            if (ptgAdjustPtg != null) {
                ptgArr[i2] = ptgAdjustPtg;
                z = true;
            }
        }
        return z;
    }

    private Ptg adjustPtg(Ptg ptg, int i) {
        return adjustPtgDueToRowMove(ptg, i);
    }

    private Ptg adjustPtgDueToRowMove(Ptg ptg, int i) {
        if (ptg instanceof RefPtg) {
            if (i != this._externSheetIndex) {
                return null;
            }
            return rowMoveRefPtg((RefPtg) ptg);
        }
        if (ptg instanceof Ref3DPtg) {
            Ref3DPtg ref3DPtg = (Ref3DPtg) ptg;
            if (this._externSheetIndex != ref3DPtg.getExternSheetIndex()) {
                return null;
            }
            return rowMoveRefPtg(ref3DPtg);
        }
        if (ptg instanceof Area2DPtgBase) {
            return i != this._externSheetIndex ? ptg : rowMoveAreaPtg((Area2DPtgBase) ptg);
        }
        if (!(ptg instanceof Area3DPtg)) {
            return null;
        }
        Area3DPtg area3DPtg = (Area3DPtg) ptg;
        if (this._externSheetIndex != area3DPtg.getExternSheetIndex()) {
            return null;
        }
        return rowMoveAreaPtg(area3DPtg);
    }

    private Ptg rowMoveRefPtg(RefPtgBase refPtgBase) {
        int row = refPtgBase.getRow();
        if (this._firstMovedIndex <= row && row <= this._lastMovedIndex) {
            refPtgBase.setRow(row + this._amountToMove);
            return refPtgBase;
        }
        int i = this._firstMovedIndex;
        int i2 = this._amountToMove;
        int i3 = i + i2;
        int i4 = this._lastMovedIndex + i2;
        if (i4 < row || row < i3) {
            return null;
        }
        if (i3 <= row && row <= i4) {
            return createDeletedRef(refPtgBase);
        }
        throw new IllegalStateException("Situation not covered: (" + this._firstMovedIndex + ", " + this._lastMovedIndex + ", " + this._amountToMove + ", " + row + ", " + row + ")");
    }

    private Ptg rowMoveAreaPtg(AreaPtgBase areaPtgBase) {
        int i;
        int firstRow = areaPtgBase.getFirstRow();
        int lastRow = areaPtgBase.getLastRow();
        if (this._firstMovedIndex <= firstRow && lastRow <= this._lastMovedIndex) {
            areaPtgBase.setFirstRow(firstRow + this._amountToMove);
            areaPtgBase.setLastRow(lastRow + this._amountToMove);
            return areaPtgBase;
        }
        int i2 = this._firstMovedIndex;
        int i3 = this._amountToMove;
        int i4 = i2 + i3;
        int i5 = this._lastMovedIndex;
        int i6 = i3 + i5;
        if (firstRow < i2 && i5 < lastRow) {
            if (i4 < firstRow && firstRow <= i6) {
                areaPtgBase.setFirstRow(i6 + 1);
                return areaPtgBase;
            }
            if (i4 > lastRow || lastRow >= i6) {
                return null;
            }
            areaPtgBase.setLastRow(i4 - 1);
            return areaPtgBase;
        }
        if (this._firstMovedIndex <= firstRow && firstRow <= (i = this._lastMovedIndex)) {
            int i7 = this._amountToMove;
            if (i7 < 0) {
                areaPtgBase.setFirstRow(firstRow + i7);
                return areaPtgBase;
            }
            if (i4 > lastRow) {
                return null;
            }
            int i8 = firstRow + i7;
            if (i6 < lastRow) {
                areaPtgBase.setFirstRow(i8);
                return areaPtgBase;
            }
            int i9 = i + 1;
            if (i4 > i9) {
                i8 = i9;
            }
            areaPtgBase.setFirstRow(i8);
            areaPtgBase.setLastRow(Math.max(lastRow, i6));
            return areaPtgBase;
        }
        int i10 = this._firstMovedIndex;
        if (i10 <= lastRow && lastRow <= this._lastMovedIndex) {
            int i11 = this._amountToMove;
            if (i11 > 0) {
                areaPtgBase.setLastRow(lastRow + i11);
                return areaPtgBase;
            }
            if (i6 < firstRow) {
                return null;
            }
            int i12 = lastRow + i11;
            if (i4 > firstRow) {
                areaPtgBase.setLastRow(i12);
                return areaPtgBase;
            }
            int i13 = i10 - 1;
            if (i6 < i13) {
                i12 = i13;
            }
            areaPtgBase.setFirstRow(Math.min(firstRow, i4));
            areaPtgBase.setLastRow(i12);
            return areaPtgBase;
        }
        if (i6 < firstRow || lastRow < i4) {
            return null;
        }
        if (i4 <= firstRow && lastRow <= i6) {
            return createDeletedRef(areaPtgBase);
        }
        if (firstRow <= i4 && i6 <= lastRow) {
            return null;
        }
        if (i4 < firstRow && firstRow <= i6) {
            areaPtgBase.setFirstRow(i6 + 1);
            return areaPtgBase;
        }
        if (i4 < lastRow && lastRow <= i6) {
            areaPtgBase.setLastRow(i4 - 1);
            return areaPtgBase;
        }
        throw new IllegalStateException("Situation not covered: (" + this._firstMovedIndex + ", " + this._lastMovedIndex + ", " + this._amountToMove + ", " + firstRow + ", " + lastRow + ")");
    }

    private static Ptg createDeletedRef(Ptg ptg) {
        if (ptg instanceof RefPtg) {
            return new RefErrorPtg();
        }
        if (ptg instanceof Ref3DPtg) {
            return new DeletedRef3DPtg(((Ref3DPtg) ptg).getExternSheetIndex());
        }
        if (ptg instanceof AreaPtg) {
            return new AreaErrPtg();
        }
        if (ptg instanceof Area3DPtg) {
            return new DeletedArea3DPtg(((Area3DPtg) ptg).getExternSheetIndex());
        }
        throw new IllegalArgumentException("Unexpected ref ptg class (" + ptg.getClass().getName() + ")");
    }
}
