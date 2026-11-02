package org.apache.poi.hssf.record.formula.udf;

import org.apache.poi.hssf.record.formula.functions.FreeRefFunction;

/* loaded from: classes3.dex */
public final class AggregatingUDFFinder implements UDFFinder {
    private final UDFFinder[] _usedToolPacks;

    public AggregatingUDFFinder(UDFFinder... uDFFinderArr) {
        this._usedToolPacks = (UDFFinder[]) uDFFinderArr.clone();
    }

    @Override // org.apache.poi.hssf.record.formula.udf.UDFFinder
    public FreeRefFunction findFunction(String str) {
        for (UDFFinder uDFFinder : this._usedToolPacks) {
            FreeRefFunction freeRefFunctionFindFunction = uDFFinder.findFunction(str);
            if (freeRefFunctionFindFunction != null) {
                return freeRefFunctionFindFunction;
            }
        }
        return null;
    }
}
