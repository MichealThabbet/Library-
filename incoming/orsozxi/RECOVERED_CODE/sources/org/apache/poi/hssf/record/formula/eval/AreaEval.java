package org.apache.poi.hssf.record.formula.eval;

import org.apache.poi.ss.formula.TwoDEval;

/* loaded from: classes3.dex */
public interface AreaEval extends TwoDEval {
    boolean contains(int i, int i2);

    boolean containsColumn(int i);

    boolean containsRow(int i);

    ValueEval getAbsoluteValue(int i, int i2);

    int getFirstColumn();

    int getFirstRow();

    @Override // org.apache.poi.ss.formula.TwoDEval
    int getHeight();

    int getLastColumn();

    int getLastRow();

    ValueEval getRelativeValue(int i, int i2);

    @Override // org.apache.poi.ss.formula.TwoDEval
    int getWidth();

    AreaEval offset(int i, int i2, int i3, int i4);
}
