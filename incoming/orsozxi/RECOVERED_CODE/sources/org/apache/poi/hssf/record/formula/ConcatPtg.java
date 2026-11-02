package org.apache.poi.hssf.record.formula;

/* loaded from: classes3.dex */
public final class ConcatPtg extends ValueOperatorPtg {
    private static final String CONCAT = "&";
    public static final ValueOperatorPtg instance = new ConcatPtg();
    public static final byte sid = 8;

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public int getNumberOfOperands() {
        return 2;
    }

    @Override // org.apache.poi.hssf.record.formula.ValueOperatorPtg
    protected byte getSid() {
        return (byte) 8;
    }

    private ConcatPtg() {
    }

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append("&");
        stringBuffer.append(strArr[1]);
        return stringBuffer.toString();
    }
}
