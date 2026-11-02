package org.apache.poi.ss.formula;

import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* loaded from: classes3.dex */
final class PlainValueCellCacheEntry extends CellCacheEntry {
    public PlainValueCellCacheEntry(ValueEval valueEval) {
        updateValue(valueEval);
    }
}
