package org.apache.poi.hssf.record.formula.functions;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.regex.Pattern;
import org.apache.poi.hssf.record.formula.eval.BlankEval;
import org.apache.poi.hssf.record.formula.eval.BoolEval;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.OperandResolver;
import org.apache.poi.hssf.record.formula.eval.RefEval;
import org.apache.poi.hssf.record.formula.eval.StringEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.record.formula.functions.CountUtils;
import org.apache.poi.ss.formula.TwoDEval;
import org.apache.poi.ss.usermodel.ErrorConstants;

/* loaded from: classes3.dex */
public final class Countif extends Fixed2ArgFunction {

    private static final class CmpOp {
        public static final int EQ = 1;
        public static final int GE = 6;
        public static final int GT = 5;
        public static final int LE = 3;
        public static final int LT = 4;
        public static final int NE = 2;
        public static final int NONE = 0;
        private final int _code;
        private final String _representation;
        public static final CmpOp OP_NONE = op("", 0);
        public static final CmpOp OP_EQ = op(ContainerUtils.KEY_VALUE_DELIMITER, 1);
        public static final CmpOp OP_NE = op("<>", 2);
        public static final CmpOp OP_LE = op("<=", 3);
        public static final CmpOp OP_LT = op("<", 4);
        public static final CmpOp OP_GT = op(">", 5);
        public static final CmpOp OP_GE = op(">=", 6);

        private static CmpOp op(String str, int i) {
            return new CmpOp(str, i);
        }

        private CmpOp(String str, int i) {
            this._representation = str;
            this._code = i;
        }

        public int getLength() {
            return this._representation.length();
        }

        public int getCode() {
            return this._code;
        }

        public static CmpOp getOperator(String str) {
            int length = str.length();
            if (length < 1) {
                return OP_NONE;
            }
            switch (str.charAt(0)) {
                case '<':
                    if (length > 1) {
                        char cCharAt = str.charAt(1);
                        if (cCharAt == '=') {
                            break;
                        } else if (cCharAt == '>') {
                            break;
                        }
                    }
                    break;
                case '>':
                    if (length > 1 && str.charAt(1) == '=') {
                        break;
                    } else {
                        break;
                    }
                    break;
            }
            return OP_NONE;
        }

        public boolean evaluate(boolean z) {
            int i = this._code;
            if (i == 0 || i == 1) {
                return z;
            }
            if (i == 2) {
                return !z;
            }
            throw new RuntimeException("Cannot call boolean evaluate on non-equality operator '" + this._representation + "'");
        }

        public boolean evaluate(int i) {
            switch (this._code) {
                case 0:
                case 1:
                    return i == 0;
                case 2:
                    return i != 0;
                case 3:
                    return i <= 0;
                case 4:
                    return i < 0;
                case 5:
                    return i > 0;
                case 6:
                    return i <= 0;
                default:
                    throw new RuntimeException("Cannot call boolean evaluate on non-equality operator '" + this._representation + "'");
            }
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            stringBuffer.append(this._representation);
            stringBuffer.append("]");
            return stringBuffer.toString();
        }

        public String getRepresentation() {
            return this._representation;
        }
    }

    private static abstract class MatcherBase implements CountUtils.I_MatchPredicate {
        private final CmpOp _operator;

        protected abstract String getValueText();

        MatcherBase(CmpOp cmpOp) {
            this._operator = cmpOp;
        }

        protected final int getCode() {
            return this._operator.getCode();
        }

        protected final boolean evaluate(int i) {
            return this._operator.evaluate(i);
        }

        protected final boolean evaluate(boolean z) {
            return this._operator.evaluate(z);
        }

        public final String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            stringBuffer.append(this._operator.getRepresentation());
            stringBuffer.append(getValueText());
            stringBuffer.append("]");
            return stringBuffer.toString();
        }
    }

    private static final class NumberMatcher extends MatcherBase {
        private final double _value;

        public NumberMatcher(double d, CmpOp cmpOp) {
            super(cmpOp);
            this._value = d;
        }

        @Override // org.apache.poi.hssf.record.formula.functions.Countif.MatcherBase
        protected String getValueText() {
            return String.valueOf(this._value);
        }

        @Override // org.apache.poi.hssf.record.formula.functions.CountUtils.I_MatchPredicate
        public boolean matches(ValueEval valueEval) {
            if (valueEval instanceof StringEval) {
                int code = getCode();
                if (code != 0 && code != 1) {
                    return code == 2;
                }
                Double d = OperandResolver.parseDouble(((StringEval) valueEval).getStringValue());
                return d != null && this._value == d.doubleValue();
            }
            if (valueEval instanceof NumberEval) {
                return evaluate(Double.compare(((NumberEval) valueEval).getNumberValue(), this._value));
            }
            return false;
        }
    }

    private static final class BooleanMatcher extends MatcherBase {
        private final int _value;

        private static int boolToInt(boolean z) {
            return z ? 1 : 0;
        }

        public BooleanMatcher(boolean z, CmpOp cmpOp) {
            super(cmpOp);
            this._value = boolToInt(z);
        }

        @Override // org.apache.poi.hssf.record.formula.functions.Countif.MatcherBase
        protected String getValueText() {
            return this._value == 1 ? "TRUE" : "FALSE";
        }

        @Override // org.apache.poi.hssf.record.formula.functions.CountUtils.I_MatchPredicate
        public boolean matches(ValueEval valueEval) {
            if (!(valueEval instanceof StringEval) && (valueEval instanceof BoolEval)) {
                return evaluate(boolToInt(((BoolEval) valueEval).getBooleanValue()) - this._value);
            }
            return false;
        }
    }

    private static final class ErrorMatcher extends MatcherBase {
        private final int _value;

        public ErrorMatcher(int i, CmpOp cmpOp) {
            super(cmpOp);
            this._value = i;
        }

        @Override // org.apache.poi.hssf.record.formula.functions.Countif.MatcherBase
        protected String getValueText() {
            return ErrorConstants.getText(this._value);
        }

        @Override // org.apache.poi.hssf.record.formula.functions.CountUtils.I_MatchPredicate
        public boolean matches(ValueEval valueEval) {
            if (valueEval instanceof ErrorEval) {
                return evaluate(((ErrorEval) valueEval).getErrorCode() - this._value);
            }
            return false;
        }
    }

    private static final class StringMatcher extends MatcherBase {
        private final Pattern _pattern;
        private final String _value;

        public StringMatcher(String str, CmpOp cmpOp) {
            super(cmpOp);
            this._value = str;
            int code = cmpOp.getCode();
            if (code == 0 || code == 1 || code == 2) {
                this._pattern = getWildCardPattern(str);
            } else {
                this._pattern = null;
            }
        }

        @Override // org.apache.poi.hssf.record.formula.functions.Countif.MatcherBase
        protected String getValueText() {
            Pattern pattern = this._pattern;
            if (pattern == null) {
                return this._value;
            }
            return pattern.pattern();
        }

        @Override // org.apache.poi.hssf.record.formula.functions.CountUtils.I_MatchPredicate
        public boolean matches(ValueEval valueEval) {
            if (valueEval instanceof BlankEval) {
                int code = getCode();
                return (code == 0 || code == 1) && this._value.length() == 0;
            }
            if (!(valueEval instanceof StringEval)) {
                return false;
            }
            String stringValue = ((StringEval) valueEval).getStringValue();
            if (stringValue.length() < 1 && this._value.length() < 1) {
                int code2 = getCode();
                return code2 == 0 || code2 == 2;
            }
            Pattern pattern = this._pattern;
            if (pattern != null) {
                return evaluate(pattern.matcher(stringValue).matches());
            }
            return evaluate(stringValue.compareTo(this._value));
        }

        /* JADX WARN: Removed duplicated region for block: B:30:0x005e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private static java.util.regex.Pattern getWildCardPattern(java.lang.String r12) {
            /*
                int r0 = r12.length()
                java.lang.StringBuffer r1 = new java.lang.StringBuffer
                r1.<init>(r0)
                r2 = 0
                r3 = 0
            Lb:
                if (r2 >= r0) goto L68
                char r4 = r12.charAt(r2)
                r5 = 36
                r6 = 1
                if (r4 == r5) goto L5e
                r5 = 46
                if (r4 == r5) goto L5e
                r7 = 63
                if (r4 == r7) goto L5a
                r5 = 91
                if (r4 == r5) goto L5e
                r8 = 93
                r9 = 126(0x7e, float:1.77E-43)
                if (r4 == r9) goto L3c
                if (r4 == r8) goto L5e
                r5 = 94
                if (r4 == r5) goto L5e
                switch(r4) {
                    case 40: goto L5e;
                    case 41: goto L5e;
                    case 42: goto L35;
                    default: goto L31;
                }
            L31:
                r1.append(r4)
                goto L66
            L35:
                java.lang.String r3 = ".*"
                r1.append(r3)
            L3a:
                r3 = 1
                goto L66
            L3c:
                int r4 = r2 + 1
                if (r4 >= r0) goto L56
                char r10 = r12.charAt(r4)
                r11 = 42
                if (r10 == r11) goto L4b
                if (r10 == r7) goto L4b
                goto L56
            L4b:
                r1.append(r5)
                r1.append(r10)
                r1.append(r8)
                r2 = r4
                goto L3a
            L56:
                r1.append(r9)
                goto L66
            L5a:
                r1.append(r5)
                goto L3a
            L5e:
                java.lang.String r5 = "\\"
                r1.append(r5)
                r1.append(r4)
            L66:
                int r2 = r2 + r6
                goto Lb
            L68:
                if (r3 == 0) goto L73
                java.lang.String r12 = r1.toString()
                java.util.regex.Pattern r12 = java.util.regex.Pattern.compile(r12)
                return r12
            L73:
                r12 = 0
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.hssf.record.formula.functions.Countif.StringMatcher.getWildCardPattern(java.lang.String):java.util.regex.Pattern");
        }
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function2Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        CountUtils.I_MatchPredicate i_MatchPredicateCreateCriteriaPredicate = createCriteriaPredicate(valueEval2, i, i2);
        if (i_MatchPredicateCreateCriteriaPredicate == null) {
            return NumberEval.ZERO;
        }
        return new NumberEval(countMatchingCellsInArea(valueEval, i_MatchPredicateCreateCriteriaPredicate));
    }

    private double countMatchingCellsInArea(ValueEval valueEval, CountUtils.I_MatchPredicate i_MatchPredicate) {
        int iCountMatchingCellsInArea;
        if (valueEval instanceof RefEval) {
            iCountMatchingCellsInArea = CountUtils.countMatchingCell((RefEval) valueEval, i_MatchPredicate);
        } else if (valueEval instanceof TwoDEval) {
            iCountMatchingCellsInArea = CountUtils.countMatchingCellsInArea((TwoDEval) valueEval, i_MatchPredicate);
        } else {
            throw new IllegalArgumentException("Bad range arg type (" + valueEval.getClass().getName() + ")");
        }
        return iCountMatchingCellsInArea;
    }

    static CountUtils.I_MatchPredicate createCriteriaPredicate(ValueEval valueEval, int i, int i2) {
        ValueEval valueEvalEvaluateCriteriaArg = evaluateCriteriaArg(valueEval, i, i2);
        if (valueEvalEvaluateCriteriaArg instanceof NumberEval) {
            return new NumberMatcher(((NumberEval) valueEvalEvaluateCriteriaArg).getNumberValue(), CmpOp.OP_NONE);
        }
        if (valueEvalEvaluateCriteriaArg instanceof BoolEval) {
            return new BooleanMatcher(((BoolEval) valueEvalEvaluateCriteriaArg).getBooleanValue(), CmpOp.OP_NONE);
        }
        if (valueEvalEvaluateCriteriaArg instanceof StringEval) {
            return createGeneralMatchPredicate((StringEval) valueEvalEvaluateCriteriaArg);
        }
        if (valueEvalEvaluateCriteriaArg instanceof ErrorEval) {
            return new ErrorMatcher(((ErrorEval) valueEvalEvaluateCriteriaArg).getErrorCode(), CmpOp.OP_NONE);
        }
        if (valueEvalEvaluateCriteriaArg == BlankEval.instance) {
            return null;
        }
        throw new RuntimeException("Unexpected type for criteria (" + valueEvalEvaluateCriteriaArg.getClass().getName() + ")");
    }

    private static ValueEval evaluateCriteriaArg(ValueEval valueEval, int i, int i2) {
        try {
            return OperandResolver.getSingleValue(valueEval, i, (short) i2);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static CountUtils.I_MatchPredicate createGeneralMatchPredicate(StringEval stringEval) {
        String stringValue = stringEval.getStringValue();
        CmpOp operator = CmpOp.getOperator(stringValue);
        String strSubstring = stringValue.substring(operator.getLength());
        Boolean bool = parseBoolean(strSubstring);
        if (bool != null) {
            return new BooleanMatcher(bool.booleanValue(), operator);
        }
        Double d = OperandResolver.parseDouble(strSubstring);
        if (d != null) {
            return new NumberMatcher(d.doubleValue(), operator);
        }
        ErrorEval error = parseError(strSubstring);
        if (error != null) {
            return new ErrorMatcher(error.getErrorCode(), operator);
        }
        return new StringMatcher(strSubstring, operator);
    }

    private static ErrorEval parseError(String str) {
        if (str.length() >= 4 && str.charAt(0) == '#') {
            if (str.equals("#NULL!")) {
                return ErrorEval.NULL_INTERSECTION;
            }
            if (str.equals("#DIV/0!")) {
                return ErrorEval.DIV_ZERO;
            }
            if (str.equals("#VALUE!")) {
                return ErrorEval.VALUE_INVALID;
            }
            if (str.equals("#REF!")) {
                return ErrorEval.REF_INVALID;
            }
            if (str.equals("#NAME?")) {
                return ErrorEval.NAME_INVALID;
            }
            if (str.equals("#NUM!")) {
                return ErrorEval.NUM_ERROR;
            }
            if (str.equals("#N/A")) {
                return ErrorEval.NA;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x001f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static java.lang.Boolean parseBoolean(java.lang.String r3) {
        /*
            int r0 = r3.length()
            r1 = 0
            r2 = 1
            if (r0 >= r2) goto L9
            return r1
        L9:
            r0 = 0
            char r0 = r3.charAt(r0)
            r2 = 70
            if (r0 == r2) goto L2a
            r2 = 84
            if (r0 == r2) goto L1f
            r2 = 102(0x66, float:1.43E-43)
            if (r0 == r2) goto L2a
            r2 = 116(0x74, float:1.63E-43)
            if (r0 == r2) goto L1f
            goto L35
        L1f:
            java.lang.String r0 = "TRUE"
            boolean r3 = r0.equalsIgnoreCase(r3)
            if (r3 == 0) goto L35
            java.lang.Boolean r3 = java.lang.Boolean.TRUE
            return r3
        L2a:
            java.lang.String r0 = "FALSE"
            boolean r3 = r0.equalsIgnoreCase(r3)
            if (r3 == 0) goto L35
            java.lang.Boolean r3 = java.lang.Boolean.FALSE
            return r3
        L35:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.poi.hssf.record.formula.functions.Countif.parseBoolean(java.lang.String):java.lang.Boolean");
    }
}
