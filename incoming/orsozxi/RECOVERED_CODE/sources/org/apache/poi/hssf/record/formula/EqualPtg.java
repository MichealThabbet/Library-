package org.apache.poi.hssf.record.formula;

import com.huawei.hms.framework.common.ContainerUtils;

/* loaded from: classes3.dex */
public final class EqualPtg extends ValueOperatorPtg {
    public static final ValueOperatorPtg instance = new EqualPtg();
    public static final byte sid = 11;

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public int getNumberOfOperands() {
        return 2;
    }

    @Override // org.apache.poi.hssf.record.formula.ValueOperatorPtg
    protected byte getSid() {
        return (byte) 11;
    }

    private EqualPtg() {
    }

    @Override // org.apache.poi.hssf.record.formula.OperationPtg
    public String toFormulaString(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strArr[0]);
        stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
        stringBuffer.append(strArr[1]);
        return stringBuffer.toString();
    }
}
