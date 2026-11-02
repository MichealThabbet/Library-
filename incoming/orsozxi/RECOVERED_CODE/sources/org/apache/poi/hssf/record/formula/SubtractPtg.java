package org.apache.poi.hssf.record.formula;

import com.ibm.icu.impl.locale.LanguageTag;

/* loaded from: classes3.dex */
public final class SubtractPtg extends ValueOperatorPtg {
    public static final ValueOperatorPtg instance = new SubtractPtg();
    public static final byte sid = 4;

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public int getNumberOfOperands() {
        return 2;
    }

    @Override // org.apache.poi.hssf.record.formula.ValueOperatorPtg
    protected byte getSid() {
        return (byte) 4;
    }

    private SubtractPtg() {
    }

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append(LanguageTag.SEP);
        stringBuffer.append(strArr[1]);
        return stringBuffer.toString();
    }
}
