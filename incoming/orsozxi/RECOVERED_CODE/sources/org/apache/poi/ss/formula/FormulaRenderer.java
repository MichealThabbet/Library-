package org.apache.poi.ss.formula;

import java.util.Stack;
import org.apache.poi.hssf.record.formula.ArrayPtg;
import org.apache.poi.hssf.record.formula.AttrPtg;
import org.apache.poi.hssf.record.formula.MemAreaPtg;
import org.apache.poi.hssf.record.formula.MemErrPtg;
import org.apache.poi.hssf.record.formula.MemFuncPtg;
import org.apache.poi.hssf.record.formula.OperationPtg;
import org.apache.poi.hssf.record.formula.ParenthesisPtg;
import org.apache.poi.hssf.record.formula.Ptg;

/* loaded from: classes3.dex */
public class FormulaRenderer {
    /* JADX WARN: Multi-variable type inference failed */
    public static String toFormulaString(FormulaRenderingWorkbook formulaRenderingWorkbook, Ptg[] ptgArr) {
        if (ptgArr == 0 || ptgArr.length == 0) {
            throw new IllegalArgumentException("ptgs must not be null");
        }
        Stack stack = new Stack();
        for (ArrayPtg.Initial initial : ptgArr) {
            if (!(initial instanceof MemAreaPtg) && !(initial instanceof MemFuncPtg) && !(initial instanceof MemErrPtg)) {
                if (initial instanceof ParenthesisPtg) {
                    stack.push("(" + ((String) stack.pop()) + ")");
                } else if (initial instanceof AttrPtg) {
                    AttrPtg attrPtg = (AttrPtg) initial;
                    if (!attrPtg.isOptimizedIf() && !attrPtg.isOptimizedChoose() && !attrPtg.isSkip() && !attrPtg.isSpace() && !attrPtg.isSemiVolatile()) {
                        if (attrPtg.isSum()) {
                            stack.push(attrPtg.toFormulaString(getOperands(stack, attrPtg.getNumberOfOperands())));
                        } else {
                            throw new RuntimeException("Unexpected tAttr: " + attrPtg.toString());
                        }
                    }
                } else if (initial instanceof WorkbookDependentFormula) {
                    stack.push(((WorkbookDependentFormula) initial).toFormulaString(formulaRenderingWorkbook));
                } else if (!(initial instanceof OperationPtg)) {
                    stack.push(initial.toFormulaString());
                } else {
                    OperationPtg operationPtg = (OperationPtg) initial;
                    stack.push(operationPtg.toFormulaString(getOperands(stack, operationPtg.getNumberOfOperands())));
                }
            }
        }
        if (stack.isEmpty()) {
            throw new IllegalStateException("Stack underflow");
        }
        String str = (String) stack.pop();
        if (stack.isEmpty()) {
            return str;
        }
        throw new IllegalStateException("too much stuff left on the stack");
    }

    private static String[] getOperands(Stack<String> stack, int i) {
        String[] strArr = new String[i];
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (stack.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Too few arguments supplied to operation. Expected (");
                sb.append(i);
                sb.append(") operands but got (");
                sb.append((i - i2) - 1);
                sb.append(")");
                throw new IllegalStateException(sb.toString());
            }
            strArr[i2] = stack.pop();
        }
        return strArr;
    }
}
