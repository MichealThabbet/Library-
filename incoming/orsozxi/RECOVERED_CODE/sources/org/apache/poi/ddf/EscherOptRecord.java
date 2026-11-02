package org.apache.poi.ddf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndian;

/* loaded from: classes3.dex */
public class EscherOptRecord extends EscherRecord {
    public static final String RECORD_DESCRIPTION = "msofbtOPT";
    public static final short RECORD_ID = -4085;
    private List properties = new ArrayList();

    @Override // org.apache.poi.ddf.EscherRecord
    public String getRecordName() {
        return "Opt";
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        int header = readHeader(bArr, i);
        this.properties = new EscherPropertyFactory().createProperties(bArr, i + 8, getInstance());
        return header + 8;
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        LittleEndian.putShort(bArr, i, getOptions());
        LittleEndian.putShort(bArr, i + 2, getRecordId());
        LittleEndian.putInt(bArr, i + 4, getPropertiesSize());
        int iSerializeComplexPart = i + 8;
        Iterator it2 = this.properties.iterator();
        while (it2.hasNext()) {
            iSerializeComplexPart += ((EscherProperty) it2.next()).serializeSimplePart(bArr, iSerializeComplexPart);
        }
        Iterator it3 = this.properties.iterator();
        while (it3.hasNext()) {
            iSerializeComplexPart += ((EscherProperty) it3.next()).serializeComplexPart(bArr, iSerializeComplexPart);
        }
        int i2 = iSerializeComplexPart - i;
        escherSerializationListener.afterRecordSerialize(iSerializeComplexPart, getRecordId(), i2, this);
        return i2;
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public int getRecordSize() {
        return getPropertiesSize() + 8;
    }

    @Override // org.apache.poi.ddf.EscherRecord
    public short getOptions() {
        setOptions((short) ((this.properties.size() << 4) | 3));
        return super.getOptions();
    }

    private int getPropertiesSize() {
        Iterator it2 = this.properties.iterator();
        int propertySize = 0;
        while (it2.hasNext()) {
            propertySize += ((EscherProperty) it2.next()).getPropertySize();
        }
        return propertySize;
    }

    public String toString() {
        String property = System.getProperty("line.separator");
        StringBuffer stringBuffer = new StringBuffer();
        Iterator it2 = this.properties.iterator();
        while (it2.hasNext()) {
            stringBuffer.append("    " + it2.next().toString() + property);
        }
        return "org.apache.poi.ddf.EscherOptRecord:" + property + "  isContainer: " + isContainerRecord() + property + "  options: 0x" + HexDump.toHex(getOptions()) + property + "  recordId: 0x" + HexDump.toHex(getRecordId()) + property + "  numchildren: " + getChildRecords().size() + property + "  properties:" + property + stringBuffer.toString();
    }

    public List getEscherProperties() {
        return this.properties;
    }

    public EscherProperty getEscherProperty(int i) {
        return (EscherProperty) this.properties.get(i);
    }

    public void addEscherProperty(EscherProperty escherProperty) {
        this.properties.add(escherProperty);
    }

    public void sortProperties() {
        Collections.sort(this.properties, new Comparator() { // from class: org.apache.poi.ddf.EscherOptRecord.1
            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                return Short.valueOf(((EscherProperty) obj).getPropertyNumber()).compareTo(Short.valueOf(((EscherProperty) obj2).getPropertyNumber()));
            }
        });
    }
}
