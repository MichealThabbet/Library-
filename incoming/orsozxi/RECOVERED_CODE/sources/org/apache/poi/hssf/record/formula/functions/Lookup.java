package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.EvaluationException;
import org.apache.poi.hssf.record.formula.eval.OperandResolver;
import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.hssf.record.formula.functions.LookupUtils;
import org.apache.poi.ss.formula.TwoDEval;

/* loaded from: classes3.dex */
public final class Lookup extends Var2or3ArgFunction {
    @Override // org.apache.poi.hssf.record.formula.functions.Function2Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2) {
        throw new RuntimeException("Two arg version of LOOKUP not supported yet");
    }

    @Override // org.apache.poi.hssf.record.formula.functions.Function3Arg
    public ValueEval evaluate(int i, int i2, ValueEval valueEval, ValueEval valueEval2, ValueEval valueEval3) {
        try {
            ValueEval singleValue = OperandResolver.getSingleValue(valueEval, i, i2);
            TwoDEval twoDEvalResolveTableArrayArg = LookupUtils.resolveTableArrayArg(valueEval2);
            TwoDEval twoDEvalResolveTableArrayArg2 = LookupUtils.resolveTableArrayArg(valueEval3);
            LookupUtils.ValueVector valueVectorCreateVector = createVector(twoDEvalResolveTableArrayArg);
            LookupUtils.ValueVector valueVectorCreateVector2 = createVector(twoDEvalResolveTableArrayArg2);
            if (valueVectorCreateVector.getSize() > valueVectorCreateVector2.getSize()) {
                throw new RuntimeException("Lookup vector and result vector of differing sizes not supported yet");
            }
            return valueVectorCreateVector2.getItem(LookupUtils.lookupIndexOfValue(singleValue, valueVectorCreateVector, true));
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private static LookupUtils.ValueVector createVector(TwoDEval twoDEval) {
        LookupUtils.ValueVector valueVectorCreateVector = LookupUtils.createVector(twoDEval);
        if (valueVectorCreateVector != null) {
            return valueVectorCreateVector;
        }
        throw new RuntimeException("non-vector lookup or result areas not supported yet");
    }
}
