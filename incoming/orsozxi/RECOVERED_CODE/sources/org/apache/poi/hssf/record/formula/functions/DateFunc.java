package org.apache.poi.hssf.record.formula.functions;

import it.orsozoxi.android.orsonotes.R2;
import java.util.GregorianCalendar;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

/* loaded from: classes3.dex */
public final class DateFunc extends Fixed3ArgFunction {
    public static final Function instance = new DateFunc();

    private static int getYear(double d) {
        int i = (int) d;
        if (i < 0) {
            return -1;
        }
        return i < 1900 ? i + R2.dimen.mtrl_extended_fab_disabled_elevation : i;
    }

    private DateFunc() {
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function3Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
        try {
            double dEvaluate = evaluate(getYear(NumericFunction.singleOperandEvaluate(valueEval, i, i2)), (int) (NumericFunction.singleOperandEvaluate(valueEval2, i, i2) - 1.0d), (int) NumericFunction.singleOperandEvaluate(valueEval3, i, i2));
            NumericFunction.checkValue(dEvaluate);
            return new NumberEval(dEvaluate);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static double evaluate(int i, int i2, int i3) throws EvaluationException {
        if (i < 0 || i2 < 0 || i3 < 0) {
            throw new EvaluationException(ErrorEval.VALUE_INVALID);
        }
        if (i == 1900 && i2 == 1 && i3 == 29) {
            return 60.0d;
        }
        if (i == 1900 && ((i2 == 0 && i3 >= 60) || (i2 == 1 && i3 >= 30))) {
            i3--;
        }
        int i4 = i3;
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(i, i2, i4, 0, 0, 0);
        gregorianCalendar.set(14, 0);
        return HSSFDateUtil.getExcelDate(gregorianCalendar.getTime(), false);
    }
}
