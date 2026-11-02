package org.apache.poi.hssf.record;

/* loaded from: classes3.dex */
public final class FooterRecord extends HeaderFooterBase {
    public static final short sid = 21;

    @Override // org.apache.poi.hssf.record.Record
    public short getSid() {
        return (short) 21;
    }

    public FooterRecord(String str) {
        super(str);
    }

    public FooterRecord(RecordInputStream recordInputStream) {
        super(recordInputStream);
    }

    @Override // org.apache.poi.hssf.record.Record
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[FOOTER]\n");
        stringBuffer.append("    .footer = ");
        stringBuffer.append(getText());
        stringBuffer.append("\n");
        stringBuffer.append("[/FOOTER]\n");
        return stringBuffer.toString();
    }

    @Override // org.apache.poi.hssf.record.Record
    public Object clone() {
        return new FooterRecord(getText());
    }
}
