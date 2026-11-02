package org.apache.poi.hssf.record;

import org.apache.poi.util.IntMapper;

/* loaded from: classes3.dex */
class SSTDeserializer {
    private IntMapper<org.apache.poi.hssf.record.common.UnicodeString> strings;

    public SSTDeserializer(IntMapper<org.apache.poi.hssf.record.common.UnicodeString> intMapper) {
        this.strings = intMapper;
    }

    public void manufactureStrings(int i, RecordInputStream recordInputStream) {
        org.apache.poi.hssf.record.common.UnicodeString unicodeString;
        for (int i2 = 0; i2 < i; i2++) {
            if (recordInputStream.available() == 0 && !recordInputStream.hasNextRecord()) {
                System.err.println("Ran out of data before creating all the strings! String at index " + i2 + "");
                unicodeString = new org.apache.poi.hssf.record.common.UnicodeString("");
            } else {
                unicodeString = new org.apache.poi.hssf.record.common.UnicodeString(recordInputStream);
            }
            addToStringTable(this.strings, unicodeString);
        }
    }

    public static void addToStringTable(IntMapper<org.apache.poi.hssf.record.common.UnicodeString> intMapper, org.apache.poi.hssf.record.common.UnicodeString unicodeString) {
        intMapper.add(unicodeString);
    }
}
