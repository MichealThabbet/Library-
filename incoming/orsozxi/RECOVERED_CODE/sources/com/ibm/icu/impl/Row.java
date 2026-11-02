package com.ibm.icu.impl;

import com.ibm.icu.util.Freezable;
import java.util.Objects;

/* loaded from: classes2.dex */
public class Row<C0, C1, C2, C3, C4> implements Comparable, Cloneable, Freezable<Row<C0, C1, C2, C3, C4>> {
    protected volatile boolean frozen;
    protected Object[] items;

    public static <C0, C1> R2<C0, C1> of(C0 c0, C1 c1) {
        return new R2<>(c0, c1);
    }

    public static <C0, C1, C2> R3<C0, C1, C2> of(C0 c0, C1 c1, C2 c2) {
        return new R3<>(c0, c1, c2);
    }

    public static <C0, C1, C2, C3> R4<C0, C1, C2, C3> of(C0 c0, C1 c1, C2 c2, C3 c3) {
        return new R4<>(c0, c1, c2, c3);
    }

    public static <C0, C1, C2, C3, C4> R5<C0, C1, C2, C3, C4> of(C0 c0, C1 c1, C2 c2, C3 c3, C4 c4) {
        return new R5<>(c0, c1, c2, c3, c4);
    }

    public static class R2<C0, C1> extends Row<C0, C1, C1, C1, C1> {
        @Override // com.ibm.icu.impl.Row, com.ibm.icu.util.Freezable
        public /* bridge */ /* synthetic */ Object cloneAsThawed() {
            return super.cloneAsThawed();
        }

        @Override // com.ibm.icu.impl.Row, com.ibm.icu.util.Freezable
        public /* bridge */ /* synthetic */ Object freeze() {
            return super.freeze();
        }

        public R2(C0 c0, C1 c1) {
            this.items = new Object[]{c0, c1};
        }
    }

    public static class R3<C0, C1, C2> extends Row<C0, C1, C2, C2, C2> {
        @Override // com.ibm.icu.impl.Row, com.ibm.icu.util.Freezable
        public /* bridge */ /* synthetic */ Object cloneAsThawed() {
            return super.cloneAsThawed();
        }

        @Override // com.ibm.icu.impl.Row, com.ibm.icu.util.Freezable
        public /* bridge */ /* synthetic */ Object freeze() {
            return super.freeze();
        }

        public R3(C0 c0, C1 c1, C2 c2) {
            this.items = new Object[]{c0, c1, c2};
        }
    }

    public static class R4<C0, C1, C2, C3> extends Row<C0, C1, C2, C3, C3> {
        @Override // com.ibm.icu.impl.Row, com.ibm.icu.util.Freezable
        public /* bridge */ /* synthetic */ Object cloneAsThawed() {
            return super.cloneAsThawed();
        }

        @Override // com.ibm.icu.impl.Row, com.ibm.icu.util.Freezable
        public /* bridge */ /* synthetic */ Object freeze() {
            return super.freeze();
        }

        public R4(C0 c0, C1 c1, C2 c2, C3 c3) {
            this.items = new Object[]{c0, c1, c2, c3};
        }
    }

    public static class R5<C0, C1, C2, C3, C4> extends Row<C0, C1, C2, C3, C4> {
        @Override // com.ibm.icu.impl.Row, com.ibm.icu.util.Freezable
        public /* bridge */ /* synthetic */ Object cloneAsThawed() {
            return super.cloneAsThawed();
        }

        @Override // com.ibm.icu.impl.Row, com.ibm.icu.util.Freezable
        public /* bridge */ /* synthetic */ Object freeze() {
            return super.freeze();
        }

        public R5(C0 c0, C1 c1, C2 c2, C3 c3, C4 c4) {
            this.items = new Object[]{c0, c1, c2, c3, c4};
        }
    }

    public Row<C0, C1, C2, C3, C4> set0(C0 c0) {
        return set(0, c0);
    }

    public C0 get0() {
        return (C0) this.items[0];
    }

    public Row<C0, C1, C2, C3, C4> set1(C1 c1) {
        return set(1, c1);
    }

    public C1 get1() {
        return (C1) this.items[1];
    }

    public Row<C0, C1, C2, C3, C4> set2(C2 c2) {
        return set(2, c2);
    }

    public C2 get2() {
        return (C2) this.items[2];
    }

    public Row<C0, C1, C2, C3, C4> set3(C3 c3) {
        return set(3, c3);
    }

    public C3 get3() {
        return (C3) this.items[3];
    }

    public Row<C0, C1, C2, C3, C4> set4(C4 c4) {
        return set(4, c4);
    }

    public C4 get4() {
        return (C4) this.items[4];
    }

    protected Row<C0, C1, C2, C3, C4> set(int i, Object obj) {
        if (this.frozen) {
            throw new UnsupportedOperationException("Attempt to modify frozen object");
        }
        this.items[i] = obj;
        return this;
    }

    public int hashCode() {
        Object[] objArr = this.items;
        int length = objArr.length;
        for (Object obj : objArr) {
            length = (length * 37) + Utility.checkHash(obj);
        }
        return length;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        try {
            Row row = (Row) obj;
            if (this.items.length != row.items.length) {
                return false;
            }
            Object[] objArr = this.items;
            int length = objArr.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                int i3 = i2 + 1;
                if (!Objects.equals(objArr[i], row.items[i2])) {
                    return false;
                }
                i++;
                i2 = i3;
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        Row row = (Row) obj;
        Object[] objArr = this.items;
        int length = objArr.length - row.items.length;
        if (length != 0) {
            return length;
        }
        int length2 = objArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length2) {
            int i3 = i2 + 1;
            int iCheckCompare = Utility.checkCompare((Comparable) objArr[i], (Comparable) row.items[i2]);
            if (iCheckCompare != 0) {
                return iCheckCompare;
            }
            i++;
            i2 = i3;
        }
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean z = true;
        for (Object obj : this.items) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(obj);
        }
        sb.append("]");
        return sb.toString();
    }

    @Override // com.ibm.icu.util.Freezable
    public boolean isFrozen() {
        return this.frozen;
    }

    @Override // com.ibm.icu.util.Freezable
    public Row<C0, C1, C2, C3, C4> freeze() {
        this.frozen = true;
        return this;
    }

    public Object clone() {
        if (this.frozen) {
            return this;
        }
        try {
            Row row = (Row) super.clone();
            this.items = (Object[]) this.items.clone();
            return row;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    @Override // com.ibm.icu.util.Freezable
    public Row<C0, C1, C2, C3, C4> cloneAsThawed() {
        try {
            Row<C0, C1, C2, C3, C4> row = (Row) super.clone();
            this.items = (Object[]) this.items.clone();
            row.frozen = false;
            return row;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
