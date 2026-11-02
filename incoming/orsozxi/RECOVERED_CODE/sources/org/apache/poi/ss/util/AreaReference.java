package org.apache.poi.ss.util;

import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.apache.poi.ss.SpreadsheetVersion;

/* loaded from: classes3.dex */
public class AreaReference {
    private static final char CELL_DELIMITER = ':';
    private static final char SHEET_NAME_DELIMITER = '!';
    private static final char SPECIAL_NAME_DELIMITER = '\'';
    private final CellReference _firstCell;
    private final boolean _isSingleCell;
    private final CellReference _lastCell;

    public AreaReference(String str) {
        if (!isContiguous(str)) {
            throw new IllegalArgumentException("References passed to the AreaReference must be contiguous, use generateContiguous(ref) if you have non-contiguous references");
        }
        String[] strArrSeparateAreaRefs = separateAreaRefs(str);
        String str2 = strArrSeparateAreaRefs[0];
        if (strArrSeparateAreaRefs.length == 1) {
            CellReference cellReference = new CellReference(str2);
            this._firstCell = cellReference;
            this._lastCell = cellReference;
            this._isSingleCell = true;
            return;
        }
        if (strArrSeparateAreaRefs.length != 2) {
            throw new IllegalArgumentException("Bad area ref '" + str + "'");
        }
        String str3 = strArrSeparateAreaRefs[1];
        if (isPlainColumn(str2)) {
            if (!isPlainColumn(str3)) {
                throw new RuntimeException("Bad area ref '" + str + "'");
            }
            boolean zIsPartAbsolute = CellReference.isPartAbsolute(str2);
            boolean zIsPartAbsolute2 = CellReference.isPartAbsolute(str3);
            int iConvertColStringToIndex = CellReference.convertColStringToIndex(str2);
            int iConvertColStringToIndex2 = CellReference.convertColStringToIndex(str3);
            this._firstCell = new CellReference(0, iConvertColStringToIndex, true, zIsPartAbsolute);
            this._lastCell = new CellReference(65535, iConvertColStringToIndex2, true, zIsPartAbsolute2);
            this._isSingleCell = false;
            return;
        }
        this._firstCell = new CellReference(str2);
        this._lastCell = new CellReference(str3);
        this._isSingleCell = str2.equals(str3);
    }

    private boolean isPlainColumn(String str) {
        for (int length = str.length() - 1; length >= 0; length--) {
            char cCharAt = str.charAt(length);
            if (!(cCharAt == '$' && length == 0) && (cCharAt < 'A' || cCharAt > 'Z')) {
                return false;
            }
        }
        return true;
    }

    public AreaReference(CellReference cellReference, CellReference cellReference2) {
        int row;
        boolean zIsRowAbsolute;
        int row2;
        boolean zIsRowAbsolute2;
        short col;
        short col2;
        boolean zIsColAbsolute;
        boolean zIsColAbsolute2;
        boolean z = cellReference.getRow() > cellReference2.getRow();
        boolean z2 = cellReference.getCol() > cellReference2.getCol();
        if (z || z2) {
            if (z) {
                row = cellReference2.getRow();
                zIsRowAbsolute = cellReference2.isRowAbsolute();
                row2 = cellReference.getRow();
                zIsRowAbsolute2 = cellReference.isRowAbsolute();
            } else {
                row = cellReference.getRow();
                zIsRowAbsolute = cellReference.isRowAbsolute();
                row2 = cellReference2.getRow();
                zIsRowAbsolute2 = cellReference2.isRowAbsolute();
            }
            if (z2) {
                col = cellReference2.getCol();
                zIsColAbsolute = cellReference2.isColAbsolute();
                col2 = cellReference.getCol();
                zIsColAbsolute2 = cellReference.isColAbsolute();
            } else {
                col = cellReference.getCol();
                boolean zIsColAbsolute3 = cellReference.isColAbsolute();
                col2 = cellReference2.getCol();
                boolean zIsColAbsolute4 = cellReference2.isColAbsolute();
                zIsColAbsolute = zIsColAbsolute3;
                zIsColAbsolute2 = zIsColAbsolute4;
            }
            this._firstCell = new CellReference(row, col, zIsRowAbsolute, zIsColAbsolute);
            this._lastCell = new CellReference(row2, col2, zIsRowAbsolute2, zIsColAbsolute2);
        } else {
            this._firstCell = cellReference;
            this._lastCell = cellReference2;
        }
        this._isSingleCell = false;
    }

    public static boolean isContiguous(String str) {
        return str.indexOf(44) == -1;
    }

    public static AreaReference getWholeRow(String str, String str2) {
        return new AreaReference("$A" + str + ":$IV" + str2);
    }

    public static AreaReference getWholeColumn(String str, String str2) {
        return new AreaReference(str + "$1:" + str2 + "$65536");
    }

    public static boolean isWholeColumnReference(CellReference cellReference, CellReference cellReference2) {
        return cellReference.getRow() == 0 && cellReference.isRowAbsolute() && cellReference2.getRow() == SpreadsheetVersion.EXCEL97.getLastRowIndex() && cellReference2.isRowAbsolute();
    }

    public boolean isWholeColumnReference() {
        return isWholeColumnReference(this._firstCell, this._lastCell);
    }

    public static AreaReference[] generateContiguous(String str) {
        ArrayList arrayList = new ArrayList();
        StringTokenizer stringTokenizer = new StringTokenizer(str, ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(new AreaReference(stringTokenizer.nextToken()));
        }
        return (AreaReference[]) arrayList.toArray(new AreaReference[arrayList.size()]);
    }

    public boolean isSingleCell() {
        return this._isSingleCell;
    }

    public CellReference getFirstCell() {
        return this._firstCell;
    }

    public CellReference getLastCell() {
        return this._lastCell;
    }

    public CellReference[] getAllReferencedCells() {
        if (this._isSingleCell) {
            return new CellReference[]{this._firstCell};
        }
        int iMax = Math.max(this._firstCell.getRow(), this._lastCell.getRow());
        int iMin = Math.min((int) this._firstCell.getCol(), (int) this._lastCell.getCol());
        int iMax2 = Math.max((int) this._firstCell.getCol(), (int) this._lastCell.getCol());
        String sheetName = this._firstCell.getSheetName();
        ArrayList arrayList = new ArrayList();
        for (int iMin2 = Math.min(this._firstCell.getRow(), this._lastCell.getRow()); iMin2 <= iMax; iMin2++) {
            for (int i = iMin; i <= iMax2; i++) {
                arrayList.add(new CellReference(sheetName, iMin2, i, this._firstCell.isRowAbsolute(), this._firstCell.isColAbsolute()));
            }
        }
        return (CellReference[]) arrayList.toArray(new CellReference[arrayList.size()]);
    }

    public String formatAsString() {
        if (isWholeColumnReference()) {
            return CellReference.convertNumToColString(this._firstCell.getCol()) + ":" + CellReference.convertNumToColString(this._lastCell.getCol());
        }
        StringBuffer stringBuffer = new StringBuffer(32);
        stringBuffer.append(this._firstCell.formatAsString());
        if (!this._isSingleCell) {
            stringBuffer.append(CELL_DELIMITER);
            if (this._lastCell.getSheetName() == null) {
                stringBuffer.append(this._lastCell.formatAsString());
            } else {
                this._lastCell.appendCellReference(stringBuffer);
            }
        }
        return stringBuffer.toString();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [");
        stringBuffer.append(formatAsString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    private static String[] separateAreaRefs(String str) {
        int length = str.length();
        int i = -1;
        int i2 = 0;
        boolean z = false;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt != '\'') {
                if (cCharAt == ':' && !z) {
                    if (i >= 0) {
                        throw new IllegalArgumentException("More than one cell delimiter ':' appears in area reference '" + str + "'");
                    }
                    i = i2;
                }
            } else if (!z) {
                z = true;
            } else {
                if (i2 >= length - 1) {
                    throw new IllegalArgumentException("Area reference '" + str + "' ends with special name delimiter '''");
                }
                int i3 = i2 + 1;
                if (str.charAt(i3) == '\'') {
                    i2 = i3;
                } else {
                    z = false;
                }
            }
            i2++;
        }
        if (i < 0) {
            return new String[]{str};
        }
        String strSubstring = str.substring(0, i);
        String strSubstring2 = str.substring(i + 1);
        if (strSubstring2.indexOf(33) >= 0) {
            throw new RuntimeException("Unexpected ! in second cell reference of '" + str + "'");
        }
        int iLastIndexOf = strSubstring.lastIndexOf(33);
        if (iLastIndexOf < 0) {
            return new String[]{strSubstring, strSubstring2};
        }
        return new String[]{strSubstring, strSubstring.substring(0, iLastIndexOf + 1) + strSubstring2};
    }
}
