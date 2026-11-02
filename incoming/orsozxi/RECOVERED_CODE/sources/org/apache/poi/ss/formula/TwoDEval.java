package org.apache.poi.ss.formula;

import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* loaded from: classes3.dex */
public interface TwoDEval extends ValueEval {
    TwoDEval getColumn(int i);

    int getHeight();

    TwoDEval getRow(int i);

    ValueEval getValue(int i, int i2);

    int getWidth();

    boolean isColumn();

    boolean isRow();
}
