package org.apache.poi.hssf.record.formula;

/* loaded from: classes3.dex */
public final class NotEqualPtg extends ValueOperatorPtg {
    public static final ValueOperatorPtg instance = new NotEqualPtg();
    public static final byte sid = 14;

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public int getNumberOfOperands() {
        return 2;
    }

    @Override // org.apache.poi.hssf.record.formula.ValueOperatorPtg
    protected byte getSid() {
        return (byte) 14;
    }

    private NotEqualPtg() {
    }

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append("<>");
        stringBuffer.append(strArr[1]);
        return stringBuffer.toString();
    }
}
