package org.apache.poi.hssf.record.formula.atp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.OperandResolver;
import org.apache.poi.hssf.record.formula.eval.StringEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.record.formula.functions.FreeRefFunction;
import org.apache.poi.ss.formula.OperationEvaluationContext;
import org.apache.poi.ss.usermodel.DateUtil;

/* loaded from: classes3.dex */
final class YearFrac implements FreeRefFunction {
    public static final FreeRefFunction instance = new YearFrac();

    private YearFrac() {
    }

    @Override // org.apache.poi.hssf.record.formula.functions.FreeRefFunction
    public ValueEval evaluate(ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext) {
        int iEvaluateIntArg;
        int rowIndex = operationEvaluationContext.getRowIndex();
        int columnIndex = operationEvaluationContext.getColumnIndex();
        try {
            int length = valueEvalArr.length;
            if (length == 2) {
                iEvaluateIntArg = 0;
            } else if (length == 3) {
                iEvaluateIntArg = evaluateIntArg(valueEvalArr[2], rowIndex, columnIndex);
            } else {
                return ErrorEval.VALUE_INVALID;
            }
            return new NumberEval(YearFracCalculator.calculate(evaluateDateArg(valueEvalArr[0], rowIndex, columnIndex), evaluateDateArg(valueEvalArr[1], rowIndex, columnIndex), iEvaluateIntArg));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static double evaluateDateArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        ValueEval singleValue = OperandResolver.getSingleValue(valueEval, i, (short) i2);
        if (singleValue instanceof StringEval) {
            String stringValue = ((StringEval) singleValue).getStringValue();
            Double d = OperandResolver.parseDouble(stringValue);
            if (d != null) {
                return d.doubleValue();
            }
            return DateUtil.getExcelDate(parseDate(stringValue), false);
        }
        return OperandResolver.coerceValueToDouble(singleValue);
    }

    private static Calendar parseDate(String str) throws NumberFormatException, EvaluationException {
        String[] strArrSplit = Pattern.compile("/").split(str);
        if (strArrSplit.length != 3) {
            throw new EvaluationException(ErrorEval.VALUE_INVALID);
        }
        String strSubstring = strArrSplit[2];
        int iIndexOf = strSubstring.indexOf(32);
        if (iIndexOf > 0) {
            strSubstring = strSubstring.substring(0, iIndexOf);
        }
        try {
            int i = Integer.parseInt(strArrSplit[0]);
            int i2 = Integer.parseInt(strArrSplit[1]);
            int i3 = Integer.parseInt(strSubstring);
            if (i < 0 || i2 < 0 || i3 < 0 || (i > 12 && i2 > 12 && i3 > 12)) {
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            }
            if (i >= 1900 && i < 9999) {
                return makeDate(i, i2, i3);
            }
            throw new RuntimeException("Unable to determine date format for text '" + str + "'");
        } catch (NumberFormatException unused) {
            throw new EvaluationException(ErrorEval.VALUE_INVALID);
        }
    }

    private static Calendar makeDate(int i, int i2, int i3) throws EvaluationException {
        if (i2 < 1 || i2 > 12) {
            throw new EvaluationException(ErrorEval.VALUE_INVALID);
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar(i, i2 - 1, 1, 0, 0, 0);
        gregorianCalendar.set(14, 0);
        if (i3 < 1 || i3 > gregorianCalendar.getActualMaximum(5)) {
            throw new EvaluationException(ErrorEval.VALUE_INVALID);
        }
        gregorianCalendar.set(5, i3);
        return gregorianCalendar;
    }

    private static int evaluateIntArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        return OperandResolver.coerceValueToInt(OperandResolver.getSingleValue(valueEval, i, (short) i2));
    }
}
