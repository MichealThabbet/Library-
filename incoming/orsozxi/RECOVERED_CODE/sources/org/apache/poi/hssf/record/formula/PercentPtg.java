package org.apache.poi.hssf.record.formula;

/* loaded from: classes3.dex */
public final class PercentPtg extends ValueOperatorPtg {
    private static final String PERCENT = "%";
    public static final int SIZE = 1;
    public static final ValueOperatorPtg instance = new PercentPtg();
    public static final byte sid = 20;

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public int getNumberOfOperands() {
        return 1;
    }

    @Override // org.apache.poi.hssf.record.formula.ValueOperatorPtg
    protected byte getSid() {
        return (byte) 20;
    }

    private PercentPtg() {
    }

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append(PERCENT);
        return stringBuffer.toString();
    }
}
