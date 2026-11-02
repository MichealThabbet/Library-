package org.apache.poi.hssf.record.cf;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.util.CellRangeAddress;

/* loaded from: classes3.dex */
public final class CellRangeUtil {
    public static final int ENCLOSES = 4;
    public static final int INSIDE = 3;
    public static final int NO_INTERSECTION = 1;
    public static final int OVERLAP = 2;

    private static boolean lt(int i, int i2) {
        return i != -1 && (i2 == -1 || i < i2);
    }

    private CellRangeUtil() {
    }

    public static int intersect(CellRangeAddress cellRangeAddress, CellRangeAddress cellRangeAddress2) {
        int firstRow = cellRangeAddress2.getFirstRow();
        int lastRow = cellRangeAddress2.getLastRow();
        int firstColumn = cellRangeAddress2.getFirstColumn();
        int lastColumn = cellRangeAddress2.getLastColumn();
        if (gt(cellRangeAddress.getFirstRow(), lastRow) || lt(cellRangeAddress.getLastRow(), firstRow) || gt(cellRangeAddress.getFirstColumn(), lastColumn) || lt(cellRangeAddress.getLastColumn(), firstColumn)) {
            return 1;
        }
        if (contains(cellRangeAddress, cellRangeAddress2)) {
            return 3;
        }
        return contains(cellRangeAddress2, cellRangeAddress) ? 4 : 2;
    }

    public static CellRangeAddress[] mergeCellRanges(CellRangeAddress[] cellRangeAddressArr) {
        if (cellRangeAddressArr.length < 1) {
            return cellRangeAddressArr;
        }
        ArrayList arrayList = new ArrayList();
        for (CellRangeAddress cellRangeAddress : cellRangeAddressArr) {
            arrayList.add(cellRangeAddress);
        }
        return toArray(mergeCellRanges(arrayList));
    }

    private static List mergeCellRanges(List list) {
        while (list.size() > 1) {
            int i = 0;
            boolean z = false;
            while (i < list.size()) {
                CellRangeAddress cellRangeAddress = (CellRangeAddress) list.get(i);
                int i2 = i + 1;
                int i3 = i2;
                while (i3 < list.size()) {
                    CellRangeAddress[] cellRangeAddressArrMergeRanges = mergeRanges(cellRangeAddress, (CellRangeAddress) list.get(i3));
                    if (cellRangeAddressArrMergeRanges != null) {
                        list.set(i, cellRangeAddressArrMergeRanges[0]);
                        list.remove(i3);
                        i3--;
                        for (int i4 = 1; i4 < cellRangeAddressArrMergeRanges.length; i4++) {
                            i3++;
                            list.add(i3, cellRangeAddressArrMergeRanges[i4]);
                        }
                        z = true;
                    }
                    i3++;
                }
                i = i2;
            }
            if (!z) {
                break;
            }
        }
        return list;
    }

    private static CellRangeAddress[] mergeRanges(CellRangeAddress cellRangeAddress, CellRangeAddress cellRangeAddress2) {
        int iIntersect = intersect(cellRangeAddress, cellRangeAddress2);
        if (iIntersect == 1) {
            if (hasExactSharedBorder(cellRangeAddress, cellRangeAddress2)) {
                return new CellRangeAddress[]{createEnclosingCellRange(cellRangeAddress, cellRangeAddress2)};
            }
            return null;
        }
        if (iIntersect == 2) {
            return resolveRangeOverlap(cellRangeAddress, cellRangeAddress2);
        }
        if (iIntersect == 3) {
            return new CellRangeAddress[]{cellRangeAddress};
        }
        if (iIntersect == 4) {
            return new CellRangeAddress[]{cellRangeAddress2};
        }
        throw new RuntimeException("unexpected intersection result (" + iIntersect + ")");
    }

    static CellRangeAddress[] resolveRangeOverlap(CellRangeAddress cellRangeAddress, CellRangeAddress cellRangeAddress2) {
        if (cellRangeAddress.isFullColumnRange()) {
            if (cellRangeAddress.isFullRowRange()) {
                return null;
            }
            return sliceUp(cellRangeAddress, cellRangeAddress2);
        }
        if (cellRangeAddress.isFullRowRange()) {
            if (cellRangeAddress2.isFullColumnRange()) {
                return null;
            }
            return sliceUp(cellRangeAddress, cellRangeAddress2);
        }
        if (cellRangeAddress2.isFullColumnRange()) {
            return sliceUp(cellRangeAddress2, cellRangeAddress);
        }
        if (cellRangeAddress2.isFullRowRange()) {
            return sliceUp(cellRangeAddress2, cellRangeAddress);
        }
        return sliceUp(cellRangeAddress, cellRangeAddress2);
    }

    private static CellRangeAddress[] sliceUp(CellRangeAddress cellRangeAddress, CellRangeAddress cellRangeAddress2) {
        List arrayList = new ArrayList();
        arrayList.add(cellRangeAddress2);
        if (!cellRangeAddress.isFullColumnRange()) {
            arrayList = cutHorizontally(cellRangeAddress.getLastRow() + 1, cutHorizontally(cellRangeAddress.getFirstRow(), arrayList));
        }
        if (!cellRangeAddress.isFullRowRange()) {
            arrayList = cutVertically(cellRangeAddress.getLastColumn() + 1, cutVertically(cellRangeAddress.getFirstColumn(), arrayList));
        }
        CellRangeAddress[] array = toArray(arrayList);
        arrayList.clear();
        arrayList.add(cellRangeAddress);
        for (CellRangeAddress cellRangeAddress3 : array) {
            if (intersect(cellRangeAddress, cellRangeAddress3) != 4) {
                arrayList.add(cellRangeAddress3);
            }
        }
        return toArray(arrayList);
    }

    private static List cutHorizontally(int i, List list) {
        ArrayList arrayList = new ArrayList();
        for (CellRangeAddress cellRangeAddress : toArray(list)) {
            if (cellRangeAddress.getFirstRow() < i && i < cellRangeAddress.getLastRow()) {
                arrayList.add(new CellRangeAddress(cellRangeAddress.getFirstRow(), i, cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn()));
                arrayList.add(new CellRangeAddress(i + 1, cellRangeAddress.getLastRow(), cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn()));
            } else {
                arrayList.add(cellRangeAddress);
            }
        }
        return arrayList;
    }

    private static List cutVertically(int i, List list) {
        ArrayList arrayList = new ArrayList();
        for (CellRangeAddress cellRangeAddress : toArray(list)) {
            if (cellRangeAddress.getFirstColumn() < i && i < cellRangeAddress.getLastColumn()) {
                arrayList.add(new CellRangeAddress(cellRangeAddress.getFirstRow(), cellRangeAddress.getLastRow(), cellRangeAddress.getFirstColumn(), i));
                arrayList.add(new CellRangeAddress(cellRangeAddress.getFirstRow(), cellRangeAddress.getLastRow(), i + 1, cellRangeAddress.getLastColumn()));
            } else {
                arrayList.add(cellRangeAddress);
            }
        }
        return arrayList;
    }

    private static CellRangeAddress[] toArray(List list) {
        CellRangeAddress[] cellRangeAddressArr = new CellRangeAddress[list.size()];
        list.toArray(cellRangeAddressArr);
        return cellRangeAddressArr;
    }

    public static boolean contains(CellRangeAddress cellRangeAddress, CellRangeAddress cellRangeAddress2) {
        return le(cellRangeAddress.getFirstRow(), cellRangeAddress2.getFirstRow()) && ge(cellRangeAddress.getLastRow(), cellRangeAddress2.getLastRow()) && le(cellRangeAddress.getFirstColumn(), cellRangeAddress2.getFirstColumn()) && ge(cellRangeAddress.getLastColumn(), cellRangeAddress2.getLastColumn());
    }

    public static boolean hasExactSharedBorder(CellRangeAddress cellRangeAddress, CellRangeAddress cellRangeAddress2) {
        int firstRow = cellRangeAddress2.getFirstRow();
        int lastRow = cellRangeAddress2.getLastRow();
        int firstColumn = cellRangeAddress2.getFirstColumn();
        int lastColumn = cellRangeAddress2.getLastColumn();
        return ((cellRangeAddress.getFirstRow() <= 0 || cellRangeAddress.getFirstRow() - 1 != lastRow) && (firstRow <= 0 || firstRow + (-1) != cellRangeAddress.getLastRow())) ? ((cellRangeAddress.getFirstColumn() > 0 && cellRangeAddress.getFirstColumn() - 1 == lastColumn) || (firstColumn > 0 && cellRangeAddress.getLastColumn() == firstColumn - 1)) && cellRangeAddress.getFirstRow() == firstRow && cellRangeAddress.getLastRow() == lastRow : cellRangeAddress.getFirstColumn() == firstColumn && cellRangeAddress.getLastColumn() == lastColumn;
    }

    public static CellRangeAddress createEnclosingCellRange(CellRangeAddress cellRangeAddress, CellRangeAddress cellRangeAddress2) {
        if (cellRangeAddress2 == null) {
            return cellRangeAddress.copy();
        }
        return new CellRangeAddress(lt(cellRangeAddress2.getFirstRow(), cellRangeAddress.getFirstRow()) ? cellRangeAddress2.getFirstRow() : cellRangeAddress.getFirstRow(), gt(cellRangeAddress2.getLastRow(), cellRangeAddress.getLastRow()) ? cellRangeAddress2.getLastRow() : cellRangeAddress.getLastRow(), lt(cellRangeAddress2.getFirstColumn(), cellRangeAddress.getFirstColumn()) ? cellRangeAddress2.getFirstColumn() : cellRangeAddress.getFirstColumn(), gt(cellRangeAddress2.getLastColumn(), cellRangeAddress.getLastColumn()) ? cellRangeAddress2.getLastColumn() : cellRangeAddress.getLastColumn());
    }

    private static boolean le(int i, int i2) {
        return i == i2 || lt(i, i2);
    }

    private static boolean gt(int i, int i2) {
        return lt(i2, i);
    }

    private static boolean ge(int i, int i2) {
        return !lt(i, i2);
    }
}
