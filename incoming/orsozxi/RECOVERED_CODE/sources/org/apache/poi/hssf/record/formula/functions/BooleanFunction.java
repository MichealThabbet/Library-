package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.BoolEval;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.OperandResolver;
import org.apache.poi.hssf.record.formula.eval.RefEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.ss.formula.TwoDEval;

/* loaded from: classes3.dex */
public abstract class BooleanFunction implements Function {
    public static final Function AND = new BooleanFunction() { // from class: org.apache.poi.hssf.record.formula.functions.BooleanFunction.1
        @Override // org.apache.poi.hssf.record.formula.functions.BooleanFunction
        protected boolean getInitialResultValue() {
            return true;
        }

        @Override // org.apache.poi.hssf.record.formula.functions.BooleanFunction
        protected boolean partialEvaluate(boolean z, boolean z2) {
            return z && z2;
        }
    };
    public static final Function OR = new BooleanFunction() { // from class: org.apache.poi.hssf.record.formula.functions.BooleanFunction.2
        @Override // org.apache.poi.hssf.record.formula.functions.BooleanFunction
        protected boolean getInitialResultValue() {
            return false;
        }

        @Override // org.apache.poi.hssf.record.formula.functions.BooleanFunction
        protected boolean partialEvaluate(boolean z, boolean z2) {
            return z || z2;
        }
    };
    public static final Function FALSE = new Fixed0ArgFunction() { // from class: org.apache.poi.hssf.record.formula.functions.BooleanFunction.3
        @Override // org.apache.poi.hssf.record.formula.functions.Function0Arg
        public ValueEval evaluate(int i, int i2) {
            return BoolEval.FALSE;
        }
    };
    public static final Function TRUE = new Fixed0ArgFunction() { // from class: org.apache.poi.hssf.record.formula.functions.BooleanFunction.4
        @Override // org.apache.poi.hssf.record.formula.functions.Function0Arg
        public ValueEval evaluate(int i, int i2) {
            return BoolEval.TRUE;
        }
    };
    public static final Function NOT = new Fixed1ArgFunction() { // from class: org.apache.poi.hssf.record.formula.functions.BooleanFunction.5
        @Override // org.apache.poi.hssf.record.formula.functions.Function1Arg
        public ValueEval evaluate(int i, int i2, ValueEval valueEval) {
            try {
                ValueEval singleValue = OperandResolver.getSingleValue(valueEval, i, i2);
                boolean zBooleanValue = false;
                Boolean boolCoerceValueToBoolean = OperandResolver.coerceValueToBoolean(singleValue, false);
                if (boolCoerceValueToBoolean != null) {
                    zBooleanValue = boolCoerceValueToBoolean.booleanValue();
                }
                return BoolEval.valueOf(!zBooleanValue);
            } catch (EvaluationException e) {
                return e.getErrorEval();
            }
        }
    };

    protected abstract boolean getInitialResultValue();

    protected abstract boolean partialEvaluate(boolean z, boolean z2);

    @Override // org.apache.poi.hssf.record.formula.functions.Function
    public final ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        if (valueEvalArr.length < 1) {
            return ErrorEval.VALUE_INVALID;
        }
        try {
            return BoolEval.valueOf(calculate(valueEvalArr));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private boolean calculate(ValueEval[] valueEvalArr) throws EvaluationException {
        Boolean boolCoerceValueToBoolean;
        boolean initialResultValue = getInitialResultValue();
        boolean z = false;
        for (ValueEval valueEval : valueEvalArr) {
            if (valueEval instanceof TwoDEval) {
                TwoDEval twoDEval = (TwoDEval) valueEval;
                int height = twoDEval.getHeight();
                int width = twoDEval.getWidth();
                for (int i = 0; i < height; i++) {
                    for (int i2 = 0; i2 < width; i2++) {
                        Boolean boolCoerceValueToBoolean2 = OperandResolver.coerceValueToBoolean(twoDEval.getValue(i, i2), true);
                        if (boolCoerceValueToBoolean2 != null) {
                            initialResultValue = partialEvaluate(initialResultValue, boolCoerceValueToBoolean2.booleanValue());
                            z = true;
                        }
                    }
                }
            } else {
                if (valueEval instanceof RefEval) {
                    boolCoerceValueToBoolean = OperandResolver.coerceValueToBoolean(((RefEval) valueEval).getInnerValueEval(), true);
                } else {
                    boolCoerceValueToBoolean = OperandResolver.coerceValueToBoolean(valueEval, false);
                }
                if (boolCoerceValueToBoolean != null) {
                    initialResultValue = partialEvaluate(initialResultValue, boolCoerceValueToBoolean.booleanValue());
                    z = true;
                }
            }
        }
        if (z) {
            return initialResultValue;
        }
        throw new EvaluationException(ErrorEval.VALUE_INVALID);
    }
}
