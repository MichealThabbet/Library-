package org.apache.poi.ss.formula;

import org.apache.poi.hssf.record.formula.AbstractFunctionPtg;
import org.apache.poi.hssf.record.formula.AttrPtg;
import org.apache.poi.hssf.record.formula.ControlPtg;
import org.apache.poi.hssf.record.formula.FuncVarPtg;
import org.apache.poi.hssf.record.formula.MemAreaPtg;
import org.apache.poi.hssf.record.formula.MemFuncPtg;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.record.formula.RangePtg;
import org.apache.poi.hssf.record.formula.UnionPtg;
import org.apache.poi.hssf.record.formula.ValueOperatorPtg;

/* loaded from: classes3.dex */
final class OperandClassTransformer {
    private final int _formulaType;

    public OperandClassTransformer(int i) {
        this._formulaType = i;
    }

    public void transformFormula(ParseNode parseNode) {
        byte b;
        int i = this._formulaType;
        if (i == 0) {
            b = 32;
        } else if (i == 2) {
            b = Ptg.CLASS_ARRAY;
        } else {
            if (i != 4 && i != 5) {
                throw new RuntimeException("Incomplete code - formula type (" + this._formulaType + ") not supported yet");
            }
            b = 0;
        }
        transformNode(parseNode, b, false);
    }

    private void transformNode(ParseNode parseNode, byte b, boolean z) {
        Ptg token = parseNode.getToken();
        ParseNode[] children = parseNode.getChildren();
        int i = 0;
        if (isSimpleValueFunction(token)) {
            boolean z2 = b == 64;
            while (i < children.length) {
                transformNode(children[i], b, z2);
                i++;
            }
            setSimpleValueFuncClass((AbstractFunctionPtg) token, b, z);
            return;
        }
        if (isSingleArgSum(token)) {
            token = FuncVarPtg.SUM;
        }
        if ((token instanceof ValueOperatorPtg) || (token instanceof ControlPtg) || (token instanceof MemFuncPtg) || (token instanceof MemAreaPtg) || (token instanceof UnionPtg)) {
            if (b == 0) {
                b = 32;
            }
            while (i < children.length) {
                transformNode(children[i], b, z);
                i++;
            }
            return;
        }
        if (token instanceof AbstractFunctionPtg) {
            transformFunctionNode((AbstractFunctionPtg) token, children, b, z);
            return;
        }
        if (children.length > 0) {
            if (token != RangePtg.instance) {
                throw new IllegalStateException("Node should not have any children");
            }
        } else {
            if (token.isBaseToken()) {
                return;
            }
            token.setClass(transformClass(token.getPtgClass(), b, z));
        }
    }

    private static boolean isSingleArgSum(Ptg ptg) {
        if (ptg instanceof AttrPtg) {
            return ((AttrPtg) ptg).isSum();
        }
        return false;
    }

    private static boolean isSimpleValueFunction(Ptg ptg) {
        if (!(ptg instanceof AbstractFunctionPtg)) {
            return false;
        }
        AbstractFunctionPtg abstractFunctionPtg = (AbstractFunctionPtg) ptg;
        if (abstractFunctionPtg.getDefaultOperandClass() != 32) {
            return false;
        }
        for (int numberOfOperands = abstractFunctionPtg.getNumberOfOperands() - 1; numberOfOperands >= 0; numberOfOperands--) {
            if (abstractFunctionPtg.getParameterClass(numberOfOperands) != 32) {
                return false;
            }
        }
        return true;
    }

    private byte transformClass(byte b, byte b2, boolean z) {
        if (b2 == 0) {
            if (z) {
                return (byte) 0;
            }
            return b;
        }
        if (b2 != 32) {
            if (b2 != 64) {
                throw new IllegalStateException("Unexpected operand class (" + ((int) b2) + ")");
            }
        } else if (!z) {
            return (byte) 32;
        }
        return Ptg.CLASS_ARRAY;
    }

    private void transformFunctionNode(AbstractFunctionPtg abstractFunctionPtg, ParseNode[] parseNodeArr, byte b, boolean z) {
        byte defaultOperandClass = abstractFunctionPtg.getDefaultOperandClass();
        boolean z2 = true;
        if (z) {
            if (defaultOperandClass != 0) {
                if (defaultOperandClass == 32) {
                    abstractFunctionPtg.setClass(Ptg.CLASS_ARRAY);
                } else if (defaultOperandClass == 64) {
                    abstractFunctionPtg.setClass(Ptg.CLASS_ARRAY);
                } else {
                    throw new IllegalStateException("Unexpected operand class (" + ((int) defaultOperandClass) + ")");
                }
            } else if (b == 0) {
                abstractFunctionPtg.setClass((byte) 0);
            } else {
                abstractFunctionPtg.setClass(Ptg.CLASS_ARRAY);
            }
            z2 = false;
        } else {
            if (defaultOperandClass == b) {
                abstractFunctionPtg.setClass(defaultOperandClass);
            } else if (b != 0) {
                if (b == 32) {
                    abstractFunctionPtg.setClass((byte) 32);
                } else {
                    if (b != 64) {
                        throw new IllegalStateException("Unexpected operand class (" + ((int) b) + ")");
                    }
                    if (defaultOperandClass == 0) {
                        abstractFunctionPtg.setClass((byte) 0);
                    } else if (defaultOperandClass == 32) {
                        abstractFunctionPtg.setClass(Ptg.CLASS_ARRAY);
                    } else {
                        throw new IllegalStateException("Unexpected operand class (" + ((int) defaultOperandClass) + ")");
                    }
                    if (defaultOperandClass != 32) {
                    }
                }
            } else if (defaultOperandClass == 32) {
                abstractFunctionPtg.setClass((byte) 32);
            } else if (defaultOperandClass == 64) {
                abstractFunctionPtg.setClass(Ptg.CLASS_ARRAY);
            } else {
                throw new IllegalStateException("Unexpected operand class (" + ((int) defaultOperandClass) + ")");
            }
            z2 = false;
        }
        for (int i = 0; i < parseNodeArr.length; i++) {
            transformNode(parseNodeArr[i], abstractFunctionPtg.getParameterClass(i), z2);
        }
    }

    private void setSimpleValueFuncClass(AbstractFunctionPtg abstractFunctionPtg, byte b, boolean z) {
        if (z || b == 64) {
            abstractFunctionPtg.setClass(Ptg.CLASS_ARRAY);
        } else {
            abstractFunctionPtg.setClass((byte) 32);
        }
    }
}
