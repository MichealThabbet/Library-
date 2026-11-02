package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.BlankEval;
import org.apache.poi.hssf.record.formula.eval.BoolEval;
import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.NumberEval;
import org.apache.poi.hssf.record.formula.eval.OperandResolver;
import org.apache.poi.hssf.record.formula.eval.RefEval;
import org.apache.poi.hssf.record.formula.eval.StringEval;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.ss.formula.TwoDEval;

/* loaded from: classes3.dex */
public abstract class MultiOperandNumericFunction implements Function {
    private static final int DEFAULT_MAX_NUM_OPERANDS = 30;
    static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    private final boolean _isBlankCounted;
    private final boolean _isReferenceBoolCounted;

    protected abstract double evaluate(double[] dArr) throws EvaluationException;

    protected int getMaxNumOperands() {
        return 30;
    }

    protected MultiOperandNumericFunction(boolean z, boolean z2) {
        this._isReferenceBoolCounted = z;
        this._isBlankCounted = z2;
    }

    private static class DoubleList {
        private double[] _array = new double[8];
        private int _count = 0;

        public double[] toArray() {
            int i = this._count;
            if (i < 1) {
                return MultiOperandNumericFunction.EMPTY_DOUBLE_ARRAY;
            }
            double[] dArr = new double[i];
            System.arraycopy(this._array, 0, dArr, 0, i);
            return dArr;
        }

        private void ensureCapacity(int i) {
            double[] dArr = this._array;
            if (i > dArr.length) {
                double[] dArr2 = new double[(i * 3) / 2];
                System.arraycopy(dArr, 0, dArr2, 0, this._count);
                this._array = dArr2;
            }
        }

        public void add(double d) {
            ensureCapacity(this._count + 1);
            double[] dArr = this._array;
            int i = this._count;
            dArr[i] = d;
            this._count = i + 1;
        }
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function
    public final ValueEval evaluate(ValueEval[] valueEvalArr, int i, int i2) {
        try {
            double dEvaluate = evaluate(getNumberArray(valueEvalArr));
            if (Double.isNaN(dEvaluate) || Double.isInfinite(dEvaluate)) {
                return ErrorEval.NUM_ERROR;
            }
            return new NumberEval(dEvaluate);
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    protected final double[] getNumberArray(ValueEval[] valueEvalArr) throws EvaluationException {
        if (valueEvalArr.length > getMaxNumOperands()) {
            throw EvaluationException.invalidValue();
        }
        DoubleList doubleList = new DoubleList();
        for (ValueEval valueEval : valueEvalArr) {
            collectValues(valueEval, doubleList);
        }
        return doubleList.toArray();
    }

    private void collectValues(ValueEval valueEval, DoubleList doubleList) throws EvaluationException {
        if (valueEval instanceof TwoDEval) {
            TwoDEval twoDEval = (TwoDEval) valueEval;
            int width = twoDEval.getWidth();
            int height = twoDEval.getHeight();
            for (int i = 0; i < height; i++) {
                for (int i2 = 0; i2 < width; i2++) {
                    collectValue(twoDEval.getValue(i, i2), true, doubleList);
                }
            }
            return;
        }
        if (valueEval instanceof RefEval) {
            collectValue(((RefEval) valueEval).getInnerValueEval(), true, doubleList);
        } else {
            collectValue(valueEval, false, doubleList);
        }
    }

    private void collectValue(ValueEval valueEval, boolean z, DoubleList doubleList) throws EvaluationException {
        if (valueEval == null) {
            throw new IllegalArgumentException("ve must not be null");
        }
        if (valueEval instanceof NumberEval) {
            doubleList.add(((NumberEval) valueEval).getNumberValue());
            return;
        }
        if (valueEval instanceof ErrorEval) {
            throw new EvaluationException((ErrorEval) valueEval);
        }
        if (valueEval instanceof StringEval) {
            if (z) {
                return;
            }
            Double d = OperandResolver.parseDouble(((StringEval) valueEval).getStringValue());
            if (d == null) {
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            }
            doubleList.add(d.doubleValue());
            return;
        }
        if (valueEval instanceof BoolEval) {
            if (!z || this._isReferenceBoolCounted) {
                doubleList.add(((BoolEval) valueEval).getNumberValue());
                return;
            }
            return;
        }
        if (valueEval == BlankEval.instance) {
            if (this._isBlankCounted) {
                doubleList.add(0.0d);
            }
        } else {
            throw new RuntimeException("Invalid ValueEval type passed for conversion: (" + valueEval.getClass() + ")");
        }
    }
}
