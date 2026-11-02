package org.apache.poi.hssf.usermodel;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.apache.poi.hssf.model.InternalWorkbook;
import org.apache.poi.hssf.record.FormatRecord;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormat;

/* loaded from: classes3.dex */
public final class HSSFDataFormat implements DataFormat {
    private static final String[] _builtinFormats = BuiltinFormats.getAll();
    private final Vector<String> _formats = new Vector<>();
    private boolean _movedBuiltins = false;
    private final InternalWorkbook _workbook;

    HSSFDataFormat(InternalWorkbook internalWorkbook) {
        this._workbook = internalWorkbook;
        for (FormatRecord formatRecord : internalWorkbook.getFormats()) {
            if (this._formats.size() < formatRecord.getIndexCode() + 1) {
                this._formats.setSize(formatRecord.getIndexCode() + 1);
            }
            this._formats.set(formatRecord.getIndexCode(), formatRecord.getFormatString());
        }
    }

    public static List<String> getBuiltinFormats() {
        return Arrays.asList(_builtinFormats);
    }

    public static short getBuiltinFormat(String str) {
        return (short) BuiltinFormats.getBuiltinFormat(str);
    }

    @Override // org.apache.poi.ss.usermodel.DataFormat
    public short getFormat(String str) {
        if (str.toUpperCase().equals("TEXT")) {
            str = "@";
        }
        if (!this._movedBuiltins) {
            int i = 0;
            while (i < _builtinFormats.length) {
                int i2 = i + 1;
                if (this._formats.size() < i2) {
                    this._formats.setSize(i2);
                }
                this._formats.set(i, _builtinFormats[i]);
                i = i2;
            }
            this._movedBuiltins = true;
        }
        ListIterator<String> listIterator = this._formats.listIterator();
        while (listIterator.hasNext()) {
            int iNextIndex = listIterator.nextIndex();
            if (str.equals(listIterator.next())) {
                return (short) iNextIndex;
            }
        }
        short format = this._workbook.getFormat(str, true);
        if (this._formats.size() <= format) {
            this._formats.setSize(format + 1);
        }
        this._formats.set(format, str);
        return format;
    }

    @Override // org.apache.poi.ss.usermodel.DataFormat
    public String getFormat(short s) {
        if (this._movedBuiltins) {
            return this._formats.get(s);
        }
        String[] strArr = _builtinFormats;
        if (strArr.length > s && strArr[s] != null) {
            return strArr[s];
        }
        return this._formats.get(s);
    }

    public static String getBuiltinFormat(short s) {
        return BuiltinFormats.getBuiltinFormat(s);
    }

    public static int getNumberOfBuiltinBuiltinFormats() {
        return _builtinFormats.length;
    }
}
