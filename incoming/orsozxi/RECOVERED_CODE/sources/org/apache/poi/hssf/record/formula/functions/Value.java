package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.OperandResolver;
import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* loaded from: classes3.dex */
public final class Value extends Fixed1ArgFunction {
    private static final int MIN_DISTANCE_BETWEEN_THOUSANDS_SEPARATOR = 4;
    private static final Double ZERO = new Double(0.0d);

    @Override // org.apache.poi.hssf.record.formula.functions.Function1Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval) throws NumberFormatException {
        try {
            Double dConvertTextToNumber = convertTextToNumber(OperandResolver.coerceValueToString(OperandResolver.getSingleValue(valueEval, i, i2)));
            if (dConvertTextToNumber == null) {
                return ErrorEval.VALUE_INVALID;
            }
            return new NumberEval(dConvertTextToNumber.doubleValue());
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static Double convertTextToNumber(String str) throws NumberFormatException {
        int length = str.length();
        boolean z = false;
        int i = 0;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (Character.isDigit(cCharAt) || cCharAt == '.') {
                break;
            }
            if (cCharAt != ' ') {
                if (cCharAt != '$') {
                    if (cCharAt != '+') {
                        if (cCharAt != '-' || z3 || z4) {
                            return null;
                        }
                        z3 = true;
                    } else {
                        if (z3 || z4) {
                            return null;
                        }
                        z4 = true;
                    }
                } else {
                    if (z2) {
                        return null;
                    }
                    z2 = true;
                }
            }
            i++;
        }
        if (i >= length) {
            if (z2 || z3 || z4) {
                return null;
            }
            return ZERO;
        }
        int i2 = -32768;
        StringBuffer stringBuffer = new StringBuffer(length);
        while (i < length) {
            char cCharAt2 = str.charAt(i);
            if (Character.isDigit(cCharAt2)) {
                stringBuffer.append(cCharAt2);
            } else if (cCharAt2 != ' ') {
                if (cCharAt2 != ',') {
                    if (cCharAt2 != '.') {
                        if ((cCharAt2 != 'E' && cCharAt2 != 'e') || i - i2 < 4) {
                            return null;
                        }
                        stringBuffer.append(str.substring(i));
                        i = length;
                    } else {
                        if (z || i - i2 < 4) {
                            return null;
                        }
                        stringBuffer.append('.');
                        z = true;
                    }
                } else {
                    if (z || i - i2 < 4) {
                        return null;
                    }
                    i2 = i;
                }
            } else if (str.substring(i).trim().length() > 0) {
                return null;
            }
            i++;
        }
        if (!z && i - i2 < 4) {
            return null;
        }
        try {
            double d = Double.parseDouble(stringBuffer.toString());
            if (z3) {
                d = -d;
            }
            return new Double(d);
        } catch (NumberFormatException unused) {
            return null;
        }
    }
}
