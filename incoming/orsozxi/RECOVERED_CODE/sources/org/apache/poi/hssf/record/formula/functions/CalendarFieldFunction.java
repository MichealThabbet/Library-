package org.apache.poi.hssf.record.formula.functions;

import it.orsozoxi.android.orsonotes.R2;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.OperandResolver;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

/* loaded from: classes3.dex */
public final class CalendarFieldFunction extends Fixed1ArgFunction {
    private final int _dateFieldId;
    private final boolean _needsOneBaseAdjustment;
    public static final Function YEAR = new CalendarFieldFunction(1, false);
    public static final Function MONTH = new CalendarFieldFunction(2, true);
    public static final Function DAY = new CalendarFieldFunction(5, false);

    private CalendarFieldFunction(int i, boolean z) {
        this._dateFieldId = i;
        this._needsOneBaseAdjustment = z;
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function1Arg
    public final ValueEval evaluate(int i, int i2, ValueEval valueEval) {
        try {
            if (OperandResolver.coerceValueToInt(OperandResolver.getSingleValue(valueEval, i, i2)) < 0) {
                return ErrorEval.NUM_ERROR;
            }
            return new NumberEval(getCalField(r3));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private int getCalField(int i) {
        if (i == 0) {
            int i2 = this._dateFieldId;
            if (i2 == 1) {
                return R2.dimen.mtrl_extended_fab_disabled_elevation;
            }
            if (i2 == 2) {
                return 1;
            }
            if (i2 == 5) {
                return 0;
            }
            throw new IllegalStateException("bad date field " + this._dateFieldId);
        }
        Date javaDate = HSSFDateUtil.getJavaDate(i, false);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(javaDate);
        int i3 = gregorianCalendar.get(this._dateFieldId);
        return this._needsOneBaseAdjustment ? i3 + 1 : i3;
    }
}
