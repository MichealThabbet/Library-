package org.apache.poi.hssf.record.formula.functions;

import com.ibm.icu.impl.locale.LanguageTag;
import it.piapostolos.android.pinotes.R2;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import org.apache.poi.hssf.record.formula.eval.BoolEval;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.OperandResolver;
import org.apache.poi.hssf.record.formula.eval.StringEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* loaded from: classes3.dex */
public abstract class TextFunction implements Function {
    protected static final String EMPTY_STRING = "";
    public static final Function LEN = new SingleArgTextFunc() { // from class: org.apache.poi.hssf.record.formula.functions.TextFunction.1
        @Override // org.apache.poi.hssf.record.formula.functions.TextFunction.SingleArgTextFunc
        protected ValueEval evaluate(String str) {
            return new NumberEval(str.length());
        }
    };
    public static final Function LOWER = new SingleArgTextFunc() { // from class: org.apache.poi.hssf.record.formula.functions.TextFunction.2
        @Override // org.apache.poi.hssf.record.formula.functions.TextFunction.SingleArgTextFunc
        protected ValueEval evaluate(String str) {
            return new StringEval(str.toLowerCase());
        }
    };
    public static final Function UPPER = new SingleArgTextFunc() { // from class: org.apache.poi.hssf.record.formula.functions.TextFunction.3
        @Override // org.apache.poi.hssf.record.formula.functions.TextFunction.SingleArgTextFunc
        protected ValueEval evaluate(String str) {
            return new StringEval(str.toUpperCase());
        }
    };
    public static final Function TRIM = new SingleArgTextFunc() { // from class: org.apache.poi.hssf.record.formula.functions.TextFunction.4
        @Override // org.apache.poi.hssf.record.formula.functions.TextFunction.SingleArgTextFunc
        protected ValueEval evaluate(String str) {
            return new StringEval(str.trim());
        }
    };
    public static final Function MID = new Fixed3ArgFunction() { // from class: org.apache.poi.hssf.record.formula.functions.TextFunction.5
        @Override // org.apache.poi.hssf.record.formula.functions.Function3Arg
        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
            try {
                String strEvaluateStringArg = TextFunction.evaluateStringArg(valueEval, i, i2);
                int iEvaluateIntArg = TextFunction.evaluateIntArg(valueEval2, i, i2);
                int iEvaluateIntArg2 = TextFunction.evaluateIntArg(valueEval3, i, i2);
                int i3 = iEvaluateIntArg - 1;
                if (i3 < 0) {
                    return ErrorEval.VALUE_INVALID;
                }
                if (iEvaluateIntArg2 < 0) {
                    return ErrorEval.VALUE_INVALID;
                }
                int length = strEvaluateStringArg.length();
                if (iEvaluateIntArg2 < 0 || i3 > length) {
                    return new StringEval("");
                }
                return new StringEval(strEvaluateStringArg.substring(i3, Math.min(iEvaluateIntArg2 + i3, length)));
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    };
    public static final Function LEFT = new LeftRight(true);
    public static final Function RIGHT = new LeftRight(false);
    public static final Function CONCATENATE = new Function() { // from class: org.apache.poi.hssf.record.formula.functions.TextFunction.6
        @Override // org.apache.poi.hssf.record.formula.functions.Function
        public ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
            StringBuilder sb = new StringBuilder();
            for (ValueEval valueEval : valueEvalArr) {
                try {
                    sb.append(TextFunction.evaluateStringArg(valueEval, i, i2));
                } catch (EvaluationException e) {
                    return e.getErrorEval();
                }
            }
            return new StringEval(sb.toString());
        }
    };
    public static final Function EXACT = new Fixed2ArgFunction() { // from class: org.apache.poi.hssf.record.formula.functions.TextFunction.7
        @Override // org.apache.poi.hssf.record.formula.functions.Function2Arg
        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
            try {
                return BoolEval.valueOf(TextFunction.evaluateStringArg(valueEval, i, i2).equals(TextFunction.evaluateStringArg(valueEval2, i, i2)));
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    };
    public static final Function TEXT = new Fixed2ArgFunction() { // from class: org.apache.poi.hssf.record.formula.functions.TextFunction.8
        @Override // org.apache.poi.hssf.record.formula.functions.Function2Arg
        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
            String[] strArrSplit;
            try {
                double dEvaluateDoubleArg = TextFunction.evaluateDoubleArg(valueEval, i, i2);
                String strEvaluateStringArg = TextFunction.evaluateStringArg(valueEval2, i, i2);
                if (strEvaluateStringArg.matches("[\\d,\\#,\\.,\\$,\\,]+")) {
                    return new StringEval(new DecimalFormat(strEvaluateStringArg).format(dEvaluateDoubleArg));
                }
                if (strEvaluateStringArg.indexOf("/") == strEvaluateStringArg.lastIndexOf("/") && strEvaluateStringArg.indexOf("/") >= 0 && !strEvaluateStringArg.contains(LanguageTag.SEP)) {
                    double dFloor = Math.floor(dEvaluateDoubleArg);
                    double d = dEvaluateDoubleArg - dFloor;
                    if (dFloor * d == 0.0d) {
                        return new StringEval("0");
                    }
                    String[] strArrSplit2 = strEvaluateStringArg.split(" ");
                    if (strArrSplit2.length == 2) {
                        strArrSplit = strArrSplit2[1].split("/");
                    } else {
                        strArrSplit = strEvaluateStringArg.split("/");
                    }
                    if (strArrSplit.length == 2) {
                        double d2 = 10.0d;
                        double dPow = Math.pow(10.0d, strArrSplit[1].length()) - 1.0d;
                        int iPow = (int) (Math.pow(10.0d, strArrSplit[1].length()) - 1.0d);
                        double dAbs = 1.0d;
                        double d3 = 0.0d;
                        while (iPow > 0) {
                            double d4 = dPow;
                            for (int iPow2 = (int) (Math.pow(d2, strArrSplit[1].length()) - 1.0d); iPow2 > 0; iPow2--) {
                                double d5 = iPow2;
                                double d6 = d3;
                                double d7 = iPow;
                                double d8 = (d5 / d7) - d;
                                if (dAbs >= Math.abs(d8)) {
                                    dAbs = Math.abs(d8);
                                    d4 = d7;
                                    d3 = d5;
                                } else {
                                    d3 = d6;
                                }
                            }
                            iPow--;
                            dPow = d4;
                            d2 = 10.0d;
                        }
                        double d9 = dPow;
                        DecimalFormat decimalFormat = new DecimalFormat(strArrSplit[0]);
                        DecimalFormat decimalFormat2 = new DecimalFormat(strArrSplit[1]);
                        if (strArrSplit2.length == 2) {
                            return new StringEval(new DecimalFormat(strArrSplit2[0]).format(dFloor) + " " + decimalFormat.format(d3) + "/" + decimalFormat2.format(d9));
                        }
                        return new StringEval(decimalFormat.format(d3 + (d9 * dFloor)) + "/" + decimalFormat2.format(d9));
                    }
                    return ErrorEval.VALUE_INVALID;
                }
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strEvaluateStringArg);
                    GregorianCalendar gregorianCalendar = new GregorianCalendar(R2.dimen.mtrl_extended_fab_corner_radius, 11, 30, 0, 0, 0);
                    gregorianCalendar.add(5, (int) Math.floor(dEvaluateDoubleArg));
                    gregorianCalendar.add(14, (int) Math.round((dEvaluateDoubleArg - Math.floor(dEvaluateDoubleArg)) * 24.0d * 60.0d * 60.0d * 1000.0d));
                    return new StringEval(simpleDateFormat.format(gregorianCalendar.getTime()));
                } catch (Exception unused) {
                    return ErrorEval.VALUE_INVALID;
                }
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    };
    public static final Function FIND = new SearchFind(true);
    public static final Function SEARCH = new SearchFind(false);

    protected abstract ValueEval evaluateFunc(ValueEval[] valueEvalArr, int i, int i2) throws EvaluationException;

    protected static final String evaluateStringArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        return OperandResolver.coerceValueToString(OperandResolver.getSingleValue(valueEval, i, i2));
    }

    protected static final int evaluateIntArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        return OperandResolver.coerceValueToInt(OperandResolver.getSingleValue(valueEval, i, i2));
    }

    protected static final double evaluateDoubleArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        return OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(valueEval, i, i2));
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function
    public final ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        try {
            return evaluateFunc(valueEvalArr, i, i2);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static abstract class SingleArgTextFunc extends Fixed1ArgFunction {
        protected abstract ValueEval evaluate(String str);

        protected SingleArgTextFunc() {
        }

        @Override // org.apache.poi.hssf.record.formula.functions.Function1Arg
        public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
            try {
                return evaluate(TextFunction.evaluateStringArg(valueEval, i, i2));
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    }

    private static final class LeftRight extends Var1or2ArgFunction {
        private static final ValueEval DEFAULT_ARG1 = new NumberEval(1.0d);
        private final boolean _isLeft;

        protected LeftRight(boolean z) {
            this._isLeft = z;
        }

        @Override // org.apache.poi.hssf.record.formula.functions.Function1Arg
        public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
            return evaluate(i, i2, valueEval, DEFAULT_ARG1);
        }

        @Override // org.apache.poi.hssf.record.formula.functions.Function2Arg
        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
            String strSubstring;
            try {
                String strEvaluateStringArg = TextFunction.evaluateStringArg(valueEval, i, i2);
                int iEvaluateIntArg = TextFunction.evaluateIntArg(valueEval2, i, i2);
                if (iEvaluateIntArg < 0) {
                    return ErrorEval.VALUE_INVALID;
                }
                if (this._isLeft) {
                    strSubstring = strEvaluateStringArg.substring(0, Math.min(strEvaluateStringArg.length(), iEvaluateIntArg));
                } else {
                    strSubstring = strEvaluateStringArg.substring(Math.max(0, strEvaluateStringArg.length() - iEvaluateIntArg));
                }
                return new StringEval(strSubstring);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    }

    private static final class SearchFind extends Var2or3ArgFunction {
        private final boolean _isCaseSensitive;

        public SearchFind(boolean z) {
            this._isCaseSensitive = z;
        }

        @Override // org.apache.poi.hssf.record.formula.functions.Function2Arg
        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
            try {
                return eval(TextFunction.evaluateStringArg(valueEval2, i, i2), TextFunction.evaluateStringArg(valueEval, i, i2), 0);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }

        @Override // org.apache.poi.hssf.record.formula.functions.Function3Arg
        public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
            try {
                String strEvaluateStringArg = TextFunction.evaluateStringArg(valueEval, i, i2);
                String strEvaluateStringArg2 = TextFunction.evaluateStringArg(valueEval2, i, i2);
                int iEvaluateIntArg = TextFunction.evaluateIntArg(valueEval3, i, i2) - 1;
                if (iEvaluateIntArg < 0) {
                    return ErrorEval.VALUE_INVALID;
                }
                return eval(strEvaluateStringArg2, strEvaluateStringArg, iEvaluateIntArg);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }

        private ValueEval eval(String str, String str2, int i) {
            int iIndexOf;
            if (this._isCaseSensitive) {
                iIndexOf = str.indexOf(str2, i);
            } else {
                iIndexOf = str.toUpperCase().indexOf(str2.toUpperCase(), i);
            }
            if (iIndexOf == -1) {
                return ErrorEval.VALUE_INVALID;
            }
            return new NumberEval(iIndexOf + 1);
        }
    }
}
