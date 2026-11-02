package org.apache.poi.hssf.record.formula.functions;

import org.apache.poi.hssf.record.formula.eval.ValueEval;
import org.apache.poi.ss.formula.OperationEvaluationContext;

/* loaded from: classes3.dex */
public interface FreeRefFunction {
    ValueEval evaluate(ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext);
}
