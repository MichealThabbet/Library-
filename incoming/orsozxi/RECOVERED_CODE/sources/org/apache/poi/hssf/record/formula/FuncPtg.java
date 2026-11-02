package org.apache.poi.hssf.record.formula;

import org.apache.poi.hssf.record.formula.function.FunctionMetadata;
import org.apache.poi.hssf.record.formula.function.FunctionMetadataRegistry;
import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class FuncPtg extends AbstractFunctionPtg {
    public static final int SIZE = 3;
    public static final byte sid = 33;

    @Override // org.apache.poi.hssf.record.formula.AbstractFunctionPtg, org.apache.poi.hssf.record.formula.Ptg
    public int getSize() {
        return 3;
    }

    public static FuncPtg create(LittleEndianInput littleEndianInput) {
        return create(littleEndianInput.readUShort());
    }

    private FuncPtg(int i, FunctionMetadata functionMetadata) {
        super(i, functionMetadata.getReturnClassCode(), functionMetadata.getParameterClassCodes(), functionMetadata.getMinParams());
    }

    public static FuncPtg create(int i) {
        FunctionMetadata functionByIndex = FunctionMetadataRegistry.getFunctionByIndex(i);
        if (functionByIndex == null) {
            throw new RuntimeException("Invalid built-in function index (" + i + ")");
        }
        return new FuncPtg(i, functionByIndex);
    }

    @Override // org.apache.poi.hssf.record.formula.Ptg
    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 33);
        littleEndianOutput.writeShort(getFunctionIndex());
    }
}
