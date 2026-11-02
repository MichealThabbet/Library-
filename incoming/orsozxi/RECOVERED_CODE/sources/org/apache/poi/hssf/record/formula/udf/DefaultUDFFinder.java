package org.apache.poi.hssf.record.formula.udf;

import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.record.formula.functions.FreeRefFunction;

/* loaded from: classes3.dex */
public final class DefaultUDFFinder implements UDFFinder {
    private final Map<String, FreeRefFunction> _functionsByName;

    public DefaultUDFFinder(String[] strArr, FreeRefFunction[] freeRefFunctionArr) {
        int length = strArr.length;
        if (freeRefFunctionArr.length != length) {
            throw new IllegalArgumentException("Mismatch in number of function names and implementations");
        }
        HashMap map = new HashMap((length * 3) / 2);
        for (int i = 0; i < freeRefFunctionArr.length; i++) {
            map.put(strArr[i], freeRefFunctionArr[i]);
        }
        this._functionsByName = map;
    }

    @Override // org.apache.poi.hssf.record.formula.udf.UDFFinder
    public FreeRefFunction findFunction(String str) {
        return this._functionsByName.get(str);
    }
}
