package com.huawei.hms.common.parcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import androidx.core.internal.view.SupportMenu;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class ParcelReader {
    private static final int b = 0;
    private static final int c = 1;
    private static final int d = 65262;
    HashMap<Integer, Integer[]> a = new HashMap<>();
    private Parcel e;

    public class ParseException extends RuntimeException {
        public ParseException(String str, Parcel parcel) {
            super(str);
        }
    }

    public ParcelReader(Parcel parcel) {
        this.e = parcel;
        a();
    }

    private int a(int i) {
        Integer[] numArr = this.a.get(Integer.valueOf(i));
        if (numArr == null) {
            throw new ParseException("Field is null:".concat(String.valueOf(numArr)), this.e);
        }
        this.e.setDataPosition(numArr[0].intValue());
        return numArr[1].intValue();
    }

    private void a() {
        int i = this.e.readInt();
        int i2 = i & 65535;
        int i3 = (i & SupportMenu.CATEGORY_MASK) != -65536 ? (i >> 16) & 65535 : this.e.readInt();
        if (i2 != d) {
            throw new ParseException("Parse header error, not 65262. Got 0x".concat(String.valueOf(Integer.toHexString(i2))), this.e);
        }
        int iDataPosition = this.e.dataPosition();
        int i4 = i3 + iDataPosition;
        if (i4 < iDataPosition || i4 > this.e.dataSize()) {
            throw new ParseException("invalid size, start=" + iDataPosition + " end=" + i4, this.e);
        }
        while (this.e.dataPosition() < i4) {
            int i5 = this.e.readInt();
            int i6 = i5 & 65535;
            int i7 = (i5 & SupportMenu.CATEGORY_MASK) != -65536 ? (i5 >> 16) & 65535 : this.e.readInt();
            int iDataPosition2 = this.e.dataPosition();
            this.a.put(Integer.valueOf(i6), new Integer[]{Integer.valueOf(iDataPosition2), Integer.valueOf(i7)});
            this.e.setDataPosition(iDataPosition2 + i7);
        }
        if (this.e.dataPosition() != i4) {
            throw new ParseException("the dataPosition is not".concat(String.valueOf(i4)), this.e);
        }
    }

    private void a(int i, int i2) {
        Integer[] numArr = this.a.get(Integer.valueOf(i));
        if (numArr == null) {
            throw new ParseException("Field is null:".concat(String.valueOf(numArr)), this.e);
        }
        int iIntValue = numArr[1].intValue();
        if (iIntValue == i2) {
            return;
        }
        throw new ParseException("the field size is not " + i2 + " got " + iIntValue + " (0x" + Integer.toHexString(iIntValue) + ")", this.e);
    }

    private int b(int i, int i2) {
        Integer[] numArr = this.a.get(Integer.valueOf(i));
        if (numArr == null) {
            throw new ParseException("Field is null:".concat(String.valueOf(numArr)), this.e);
        }
        this.e.setDataPosition(numArr[0].intValue());
        a(i, i2);
        return i2;
    }

    public BigDecimal createBigDecimal(int i, BigDecimal bigDecimal) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return bigDecimal;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        byte[] bArrCreateByteArray = this.e.createByteArray();
        int i2 = this.e.readInt();
        this.e.setDataPosition(iDataPosition + iA);
        return new BigDecimal(new BigInteger(bArrCreateByteArray), i2);
    }

    public BigDecimal[] createBigDecimalArray(int i, BigDecimal[] bigDecimalArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return bigDecimalArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        int i2 = this.e.readInt();
        BigDecimal[] bigDecimalArr2 = new BigDecimal[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            byte[] bArrCreateByteArray = this.e.createByteArray();
            bigDecimalArr2[i3] = new BigDecimal(new BigInteger(bArrCreateByteArray), this.e.readInt());
        }
        this.e.setDataPosition(iDataPosition + iA);
        return bigDecimalArr2;
    }

    public BigInteger createBigInteger(int i, BigInteger bigInteger) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return bigInteger;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        byte[] bArrCreateByteArray = this.e.createByteArray();
        this.e.setDataPosition(iDataPosition + iA);
        return new BigInteger(bArrCreateByteArray);
    }

    public BigInteger[] createBigIntegerArray(int i, BigInteger[] bigIntegerArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return bigIntegerArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        int i2 = this.e.readInt();
        BigInteger[] bigIntegerArr2 = new BigInteger[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bigIntegerArr2[i3] = new BigInteger(this.e.createByteArray());
        }
        this.e.setDataPosition(iDataPosition + iA);
        return bigIntegerArr2;
    }

    public boolean[] createBooleanArray(int i, boolean[] zArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return zArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        boolean[] zArrCreateBooleanArray = this.e.createBooleanArray();
        this.e.setDataPosition(iDataPosition + iA);
        return zArrCreateBooleanArray;
    }

    public ArrayList<Boolean> createBooleanList(int i, ArrayList<Boolean> arrayList) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return arrayList;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        ArrayList<Boolean> arrayList2 = new ArrayList<>();
        int i2 = this.e.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList2.add(Boolean.valueOf(this.e.readInt() != 0));
        }
        this.e.setDataPosition(iDataPosition + iA);
        return arrayList2;
    }

    public byte[] createByteArray(int i, byte[] bArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return bArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        byte[] bArrCreateByteArray = this.e.createByteArray();
        this.e.setDataPosition(iDataPosition + iA);
        return bArrCreateByteArray;
    }

    public byte[][] createByteArrayArray(int i, byte[][] bArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return bArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        int i2 = this.e.readInt();
        byte[][] bArr2 = new byte[i2][];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr2[i3] = this.e.createByteArray();
        }
        this.e.setDataPosition(iDataPosition + iA);
        return bArr2;
    }

    public SparseArray<byte[]> createByteArraySparseArray(int i, SparseArray<byte[]> sparseArray) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return sparseArray;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        int i2 = this.e.readInt();
        SparseArray<byte[]> sparseArray2 = new SparseArray<>(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            sparseArray2.append(this.e.readInt(), this.e.createByteArray());
        }
        this.e.setDataPosition(iDataPosition + iA);
        return sparseArray2;
    }

    public char[] createCharArray(int i, char[] cArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return cArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        char[] cArrCreateCharArray = this.e.createCharArray();
        this.e.setDataPosition(iDataPosition + iA);
        return cArrCreateCharArray;
    }

    public double[] createDoubleArray(int i, double[] dArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return dArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        double[] dArrCreateDoubleArray = this.e.createDoubleArray();
        this.e.setDataPosition(iDataPosition + iA);
        return dArrCreateDoubleArray;
    }

    public ArrayList<Double> createDoubleList(int i, ArrayList<Double> arrayList) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return arrayList;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        ArrayList<Double> arrayList2 = new ArrayList<>();
        int i2 = this.e.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList2.add(Double.valueOf(this.e.readDouble()));
        }
        this.e.setDataPosition(iDataPosition + iA);
        return arrayList2;
    }

    public SparseArray<Double> createDoubleSparseArray(int i, SparseArray<Double> sparseArray) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return sparseArray;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        SparseArray<Double> sparseArray2 = new SparseArray<>();
        int i2 = this.e.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            sparseArray2.append(this.e.readInt(), Double.valueOf(this.e.readDouble()));
        }
        this.e.setDataPosition(iDataPosition + iA);
        return sparseArray2;
    }

    public float[] createFloatArray(int i, float[] fArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return fArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        float[] fArrCreateFloatArray = this.e.createFloatArray();
        this.e.setDataPosition(iDataPosition + iA);
        return fArrCreateFloatArray;
    }

    public ArrayList<Float> createFloatList(int i, ArrayList<Float> arrayList) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return arrayList;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        ArrayList<Float> arrayList2 = new ArrayList<>();
        int i2 = this.e.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList2.add(Float.valueOf(this.e.readFloat()));
        }
        this.e.setDataPosition(iDataPosition + iA);
        return arrayList2;
    }

    public SparseArray<Float> createFloatSparseArray(int i, SparseArray<Float> sparseArray) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return sparseArray;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        SparseArray<Float> sparseArray2 = new SparseArray<>();
        int i2 = this.e.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            sparseArray2.append(this.e.readInt(), Float.valueOf(this.e.readFloat()));
        }
        this.e.setDataPosition(iDataPosition + iA);
        return sparseArray2;
    }

    public IBinder[] createIBinderArray(int i, IBinder[] iBinderArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return iBinderArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        IBinder[] iBinderArrCreateBinderArray = this.e.createBinderArray();
        this.e.setDataPosition(iDataPosition + iA);
        return iBinderArrCreateBinderArray;
    }

    public ArrayList<IBinder> createIBinderList(int i, ArrayList<IBinder> arrayList) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return arrayList;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        ArrayList<IBinder> arrayListCreateBinderArrayList = this.e.createBinderArrayList();
        this.e.setDataPosition(iDataPosition + iA);
        return arrayListCreateBinderArrayList;
    }

    public SparseArray<IBinder> createIBinderSparseArray(int i, SparseArray<IBinder> sparseArray) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return sparseArray;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        int i2 = this.e.readInt();
        SparseArray<IBinder> sparseArray2 = new SparseArray<>(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            sparseArray2.append(this.e.readInt(), this.e.readStrongBinder());
        }
        this.e.setDataPosition(iDataPosition + iA);
        return sparseArray2;
    }

    public int[] createIntArray(int i, int[] iArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return iArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        int[] iArrCreateIntArray = this.e.createIntArray();
        this.e.setDataPosition(iDataPosition + iA);
        return iArrCreateIntArray;
    }

    public ArrayList<Integer> createIntegerList(int i, ArrayList<Integer> arrayList) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return arrayList;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        int i2 = this.e.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList2.add(Integer.valueOf(this.e.readInt()));
        }
        this.e.setDataPosition(iDataPosition + iA);
        return arrayList2;
    }

    public long[] createLongArray(int i, long[] jArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return jArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        long[] jArrCreateLongArray = this.e.createLongArray();
        this.e.setDataPosition(iDataPosition + iA);
        return jArrCreateLongArray;
    }

    public ArrayList<Long> createLongList(int i, ArrayList<Long> arrayList) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return arrayList;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        ArrayList<Long> arrayList2 = new ArrayList<>();
        int i2 = this.e.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList2.add(Long.valueOf(this.e.readLong()));
        }
        this.e.setDataPosition(iDataPosition + iA);
        return arrayList2;
    }

    public Parcel createParcel(int i, Parcel parcel) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return parcel;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.appendFrom(this.e, iDataPosition, iA);
        this.e.setDataPosition(iDataPosition + iA);
        return parcelObtain;
    }

    public Parcel[] createParcelArray(int i, Parcel[] parcelArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return parcelArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        int i2 = this.e.readInt();
        Parcel[] parcelArr2 = new Parcel[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = this.e.readInt();
            if (i4 != 0) {
                int iDataPosition2 = this.e.dataPosition();
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.appendFrom(this.e, iDataPosition2, i4);
                parcelArr2[i3] = parcelObtain;
                this.e.setDataPosition(iDataPosition2 + i4);
            } else {
                parcelArr2[i3] = null;
            }
        }
        this.e.setDataPosition(iDataPosition + iA);
        return parcelArr2;
    }

    public ArrayList<Parcel> createParcelList(int i, ArrayList<Parcel> arrayList) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return arrayList;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        int i2 = this.e.readInt();
        ArrayList<Parcel> arrayList2 = new ArrayList<>();
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = this.e.readInt();
            if (i4 != 0) {
                int iDataPosition2 = this.e.dataPosition();
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.appendFrom(this.e, iDataPosition2, i4);
                arrayList2.add(parcelObtain);
                this.e.setDataPosition(iDataPosition2 + i4);
            } else {
                arrayList2.add(null);
            }
        }
        this.e.setDataPosition(iDataPosition + iA);
        return arrayList2;
    }

    public SparseArray<Parcel> createParcelSparseArray(int i, SparseArray<Parcel> sparseArray) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return sparseArray;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        int i2 = this.e.readInt();
        SparseArray<Parcel> sparseArray2 = new SparseArray<>();
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = this.e.readInt();
            int i5 = this.e.readInt();
            if (i5 != 0) {
                int iDataPosition2 = this.e.dataPosition();
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.appendFrom(this.e, iDataPosition2, i5);
                sparseArray2.append(i4, parcelObtain);
                this.e.setDataPosition(iDataPosition2 + i5);
            } else {
                sparseArray2.append(i4, null);
            }
        }
        this.e.setDataPosition(iDataPosition + iA);
        return sparseArray2;
    }

    public SparseBooleanArray createSparseBooleanArray(int i, SparseBooleanArray sparseBooleanArray) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return sparseBooleanArray;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        SparseBooleanArray sparseBooleanArray2 = this.e.readSparseBooleanArray();
        this.e.setDataPosition(iDataPosition + iA);
        return sparseBooleanArray2;
    }

    public SparseIntArray createSparseIntArray(int i, SparseIntArray sparseIntArray) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return sparseIntArray;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        int i2 = this.e.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            sparseIntArray2.append(this.e.readInt(), this.e.readInt());
        }
        this.e.setDataPosition(iDataPosition + iA);
        return sparseIntArray2;
    }

    public SparseLongArray createSparseLongArray(int i, SparseLongArray sparseLongArray) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return sparseLongArray;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        SparseLongArray sparseLongArray2 = new SparseLongArray();
        int i2 = this.e.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            sparseLongArray2.append(this.e.readInt(), this.e.readLong());
        }
        this.e.setDataPosition(iDataPosition + iA);
        return sparseLongArray2;
    }

    public String createString(int i, String str) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return str;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        String string = this.e.readString();
        this.e.setDataPosition(iDataPosition + iA);
        return string;
    }

    public String[] createStringArray(int i, String[] strArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return strArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        String[] strArrCreateStringArray = this.e.createStringArray();
        this.e.setDataPosition(iDataPosition + iA);
        return strArrCreateStringArray;
    }

    public ArrayList<String> createStringList(int i, ArrayList<String> arrayList) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return arrayList;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        ArrayList<String> arrayListCreateStringArrayList = this.e.createStringArrayList();
        this.e.setDataPosition(iDataPosition + iA);
        return arrayListCreateStringArrayList;
    }

    public SparseArray<String> createStringSparseArray(int i, SparseArray<String> sparseArray) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return sparseArray;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        SparseArray<String> sparseArray2 = new SparseArray<>();
        int i2 = this.e.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            sparseArray2.append(this.e.readInt(), this.e.readString());
        }
        this.e.setDataPosition(iDataPosition + iA);
        return sparseArray2;
    }

    public <T> T[] createTypedArray(int i, Parcelable.Creator<T> creator, T[] tArr) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return tArr;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        Object[] objArrCreateTypedArray = this.e.createTypedArray(creator);
        this.e.setDataPosition(iDataPosition + iA);
        return (T[]) objArrCreateTypedArray;
    }

    public <T> ArrayList<T> createTypedList(int i, Parcelable.Creator<T> creator, ArrayList<T> arrayList) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return arrayList;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        ArrayList<T> arrayListCreateTypedArrayList = this.e.createTypedArrayList(creator);
        this.e.setDataPosition(iDataPosition + iA);
        return arrayListCreateTypedArrayList;
    }

    public <T> SparseArray<T> createTypedSparseArray(int i, Parcelable.Creator<T> creator, SparseArray<T> sparseArray) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return sparseArray;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        int i2 = this.e.readInt();
        SparseArray<T> sparseArray2 = new SparseArray<>();
        for (int i3 = 0; i3 < i2; i3++) {
            sparseArray2.append(this.e.readInt(), this.e.readInt() != 0 ? creator.createFromParcel(this.e) : null);
        }
        this.e.setDataPosition(iDataPosition + iA);
        return sparseArray2;
    }

    public boolean readBoolean(int i, boolean z) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return z;
        }
        b(i, 4);
        return this.e.readInt() != 0;
    }

    public Boolean readBooleanObject(int i, Boolean bool) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return bool;
        }
        if (a(i) == 0) {
            return null;
        }
        a(i, 4);
        int i2 = this.e.readInt();
        if (i2 == 0) {
            return Boolean.FALSE;
        }
        if (i2 != 1) {
            return null;
        }
        return Boolean.TRUE;
    }

    public Bundle readBundle(int i, Bundle bundle) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return bundle;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        Bundle bundle2 = this.e.readBundle();
        this.e.setDataPosition(iDataPosition + iA);
        return bundle2;
    }

    public byte readByte(int i, byte b2) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return b2;
        }
        b(i, 4);
        return (byte) this.e.readInt();
    }

    public char readChar(int i, char c2) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return c2;
        }
        b(i, 4);
        return (char) this.e.readInt();
    }

    public double readDouble(int i, double d2) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return d2;
        }
        b(i, 8);
        return this.e.readDouble();
    }

    public Double readDoubleObject(int i, Double d2) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return d2;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        a(iA, 8);
        return Double.valueOf(this.e.readDouble());
    }

    public float readFloat(int i, float f) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return f;
        }
        b(i, 4);
        return this.e.readFloat();
    }

    public Float readFloatObject(int i, Float f) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return f;
        }
        if (a(i) == 0) {
            return null;
        }
        a(i, 4);
        return Float.valueOf(this.e.readFloat());
    }

    public IBinder readIBinder(int i, IBinder iBinder) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return iBinder;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        IBinder strongBinder = this.e.readStrongBinder();
        this.e.setDataPosition(iDataPosition + iA);
        return strongBinder;
    }

    public int readInt(int i, int i2) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return i2;
        }
        b(i, 4);
        return this.e.readInt();
    }

    public Integer readIntegerObject(int i, Integer num) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return num;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        a(iA, 4);
        return Integer.valueOf(this.e.readInt());
    }

    public void readList(int i, List list, ClassLoader classLoader) {
        if (this.a.containsKey(Integer.valueOf(i))) {
            int iA = a(i);
            int iDataPosition = this.e.dataPosition();
            if (iA != 0) {
                this.e.readList(list, classLoader);
                this.e.setDataPosition(iDataPosition + iA);
            }
        }
    }

    public long readLong(int i, long j) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return j;
        }
        b(i, 8);
        return this.e.readLong();
    }

    public Long readLongObject(int i, Long l) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return l;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        a(iA, 8);
        return Long.valueOf(this.e.readLong());
    }

    public <T extends Parcelable> T readParcelable(int i, Parcelable.Creator<T> creator, T t) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return t;
        }
        int iA = a(i);
        if (iA == 0) {
            return null;
        }
        int iDataPosition = this.e.dataPosition();
        T tCreateFromParcel = creator.createFromParcel(this.e);
        this.e.setDataPosition(iDataPosition + iA);
        return tCreateFromParcel;
    }

    public short readShort(int i, short s) {
        if (!this.a.containsKey(Integer.valueOf(i))) {
            return s;
        }
        b(i, 4);
        return (short) this.e.readInt();
    }
}
