package org.apache.poi.hssf.record.formula;

/* loaded from: classes3.dex */
public final class UnaryPlusPtg extends ValueOperatorPtg {
    private static final String ADD = "+";
    public static final ValueOperatorPtg instance = new UnaryPlusPtg();
    public static final byte sid = 18;

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public int getNumberOfOperands() {
        return 1;
    }

    @Override // org.apache.poi.hssf.record.formula.ValueOperatorPtg
    protected byte getSid() {
        return (byte) 18;
    }

    private UnaryPlusPtg() {
    }

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("+");
        stringBuffer.append(strArr[0]);
        return stringBuffer.toString();
    }
}
