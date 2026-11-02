package org.apache.poi.ss.util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellRange;
import org.apache.poi.util.Internal;

@Internal
/* loaded from: classes3.dex */
public final class SSCellRange<K extends Cell> implements CellRange<K> {
    private final int _firstColumn;
    private final int _firstRow;
    private final K[] _flattenedArray;
    private final int _height;
    private final int _width;

    private SSCellRange(int i, int i2, int i3, int i4, K[] kArr) {
        this._firstRow = i;
        this._firstColumn = i2;
        this._height = i3;
        this._width = i4;
        this._flattenedArray = kArr;
    }

    public static <B extends Cell> SSCellRange<B> create(int i, int i2, int i3, int i4, List<B> list, Class<B> cls) {
        int size = list.size();
        if (i3 * i4 != size) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        Cell[] cellArr = (Cell[]) Array.newInstance((Class<?>) cls, size);
        list.toArray(cellArr);
        return new SSCellRange<>(i, i2, i3, i4, cellArr);
    }

    @Override // org.apache.poi.ss.usermodel.CellRange
    public int getHeight() {
        return this._height;
    }

    @Override // org.apache.poi.ss.usermodel.CellRange
    public int getWidth() {
        return this._width;
    }

    @Override // org.apache.poi.ss.usermodel.CellRange
    public int size() {
        return this._height * this._width;
    }

    @Override // org.apache.poi.ss.usermodel.CellRange
    public String getReferenceText() {
        int i = this._firstRow;
        return new CellRangeAddress(i, (this._height + i) - 1, this._firstColumn, (this._width + r3) - 1).formatAsString();
    }

    @Override // org.apache.poi.ss.usermodel.CellRange
    public K getTopLeftCell() {
        return this._flattenedArray[0];
    }

    @Override // org.apache.poi.ss.usermodel.CellRange
    public K getCell(int i, int i2) {
        int i3;
        if (i < 0 || i >= this._height) {
            StringBuilder sb = new StringBuilder();
            sb.append("Specified row ");
            sb.append(i);
            sb.append(" is outside the allowable range (0..");
            sb.append(this._height - 1);
            sb.append(").");
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        if (i2 < 0 || i2 >= (i3 = this._width)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Specified colummn ");
            sb2.append(i2);
            sb2.append(" is outside the allowable range (0..");
            sb2.append(this._width - 1);
            sb2.append(").");
            throw new ArrayIndexOutOfBoundsException(sb2.toString());
        }
        return this._flattenedArray[(i3 * i) + i2];
    }

    @Override // org.apache.poi.ss.usermodel.CellRange
    public K[] getFlattenedCells() {
        return (K[]) ((Cell[]) this._flattenedArray.clone());
    }

    @Override // org.apache.poi.ss.usermodel.CellRange
    public K[][] getCells() {
        Class<?> cls = this._flattenedArray.getClass();
        K[][] kArr = (K[][]) ((Cell[][]) Array.newInstance(cls, this._height));
        Class<?> componentType = cls.getComponentType();
        for (int i = this._height - 1; i >= 0; i--) {
            Cell[] cellArr = (Cell[]) Array.newInstance(componentType, this._width);
            int i2 = this._width;
            System.arraycopy(this._flattenedArray, i2 * i, cellArr, 0, i2);
        }
        return kArr;
    }

    @Override // org.apache.poi.ss.usermodel.CellRange, java.lang.Iterable
    public Iterator<K> iterator() {
        return new ArrayIterator(this._flattenedArray);
    }

    private static final class ArrayIterator<D> implements Iterator<D> {
        private final D[] _array;
        private int _index = 0;

        public ArrayIterator(D[] dArr) {
            this._array = dArr;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this._index < this._array.length;
        }

        @Override // java.util.Iterator
        public D next() {
            int i = this._index;
            D[] dArr = this._array;
            if (i >= dArr.length) {
                throw new NoSuchElementException(String.valueOf(this._index));
            }
            this._index = i + 1;
            return dArr[i];
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove cells from this CellRange.");
        }
    }
}
