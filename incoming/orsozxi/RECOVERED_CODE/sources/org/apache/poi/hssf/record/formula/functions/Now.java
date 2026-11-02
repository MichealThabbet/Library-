package org.apache.poi.hssf.record.formula.functions;

import java.util.Date;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

/* loaded from: classes3.dex */
public final class Now extends Fixed0ArgFunction {
    @Override // org.apache.poi.hssf.record.formula.functions.Function0Arg
    public ValueEval evaluate(int i, int i2) {
        return new NumberEval(HSSFDateUtil.getExcelDate(new Date(System.currentTimeMillis())));
    }
}
