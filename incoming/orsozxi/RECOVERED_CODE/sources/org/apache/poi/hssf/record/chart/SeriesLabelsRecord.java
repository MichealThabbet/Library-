package org.apache.poi.hssf.record.chart;

import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.hssf.record.StandardRecord;
import org.apache.poi.util.BitField;
import org.apache.poi.util.BitFieldFactory;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;

/* loaded from: classes3.dex */
public final class SeriesLabelsRecord extends StandardRecord {
    public static final short sid = 4108;
    private short field_1_formatFlags;
    private static final BitField showActual = BitFieldFactory.getInstance(1);
    private static final BitField showPercent = BitFieldFactory.getInstance(2);
    private static final BitField labelAsPercentage = BitFieldFactory.getInstance(4);
    private static final BitField smoothedLine = BitFieldFactory.getInstance(8);
    private static final BitField showLabel = BitFieldFactory.getInstance(16);
    private static final BitField showBubbleSizes = BitFieldFactory.getInstance(32);

    @Override // org.apache.poi.hssf.record.StandardRecord
    protected int getDataSize() {
        return 2;
    }

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return sid;
    }

    public SeriesLabelsRecord() {
    }

    public SeriesLabelsRecord(RecordInputStream recordInputStream) {
        this.field_1_formatFlags = recordInputStream.readShort();
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[ATTACHEDLABEL]\n");
        stringBuffer.append("    .formatFlags          = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getFormatFlags()));
        stringBuffer.append(" (");
        stringBuffer.append((int) getFormatFlags());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("         .showActual               = ");
        stringBuffer.append(isShowActual());
        stringBuffer.append('\n');
        stringBuffer.append("         .showPercent              = ");
        stringBuffer.append(isShowPercent());
        stringBuffer.append('\n');
        stringBuffer.append("         .labelAsPercentage        = ");
        stringBuffer.append(isLabelAsPercentage());
        stringBuffer.append('\n');
        stringBuffer.append("         .smoothedLine             = ");
        stringBuffer.append(isSmoothedLine());
        stringBuffer.append('\n');
        stringBuffer.append("         .showLabel                = ");
        stringBuffer.append(isShowLabel());
        stringBuffer.append('\n');
        stringBuffer.append("         .showBubbleSizes          = ");
        stringBuffer.append(isShowBubbleSizes());
        stringBuffer.append('\n');
        stringBuffer.append("[/ATTACHEDLABEL]\n");
        return stringBuffer.toString();
    }

    @Override // org.apache.poi.hssf.record.StandardRecord
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_formatFlags);
    }

    @Override // org.apache.poi.hssf.record.Record
    public Object clone() {
        SeriesLabelsRecord seriesLabelsRecord = new SeriesLabelsRecord();
        seriesLabelsRecord.field_1_formatFlags = this.field_1_formatFlags;
        return seriesLabelsRecord;
    }

    public short getFormatFlags() {
        return this.field_1_formatFlags;
    }

    public void setFormatFlags(short s) {
        this.field_1_formatFlags = s;
    }

    public void setShowActual(boolean z) {
        this.field_1_formatFlags = showActual.setShortBoolean(this.field_1_formatFlags, z);
    }

    public boolean isShowActual() {
        return showActual.isSet(this.field_1_formatFlags);
    }

    public void setShowPercent(boolean z) {
        this.field_1_formatFlags = showPercent.setShortBoolean(this.field_1_formatFlags, z);
    }

    public boolean isShowPercent() {
        return showPercent.isSet(this.field_1_formatFlags);
    }

    public void setLabelAsPercentage(boolean z) {
        this.field_1_formatFlags = labelAsPercentage.setShortBoolean(this.field_1_formatFlags, z);
    }

    public boolean isLabelAsPercentage() {
        return labelAsPercentage.isSet(this.field_1_formatFlags);
    }

    public void setSmoothedLine(boolean z) {
        this.field_1_formatFlags = smoothedLine.setShortBoolean(this.field_1_formatFlags, z);
    }

    public boolean isSmoothedLine() {
        return smoothedLine.isSet(this.field_1_formatFlags);
    }

    public void setShowLabel(boolean z) {
        this.field_1_formatFlags = showLabel.setShortBoolean(this.field_1_formatFlags, z);
    }

    public boolean isShowLabel() {
        return showLabel.isSet(this.field_1_formatFlags);
    }

    public void setShowBubbleSizes(boolean z) {
        this.field_1_formatFlags = showBubbleSizes.setShortBoolean(this.field_1_formatFlags, z);
    }

    public boolean isShowBubbleSizes() {
        return showBubbleSizes.isSet(this.field_1_formatFlags);
    }
}
