package com.marshalchen.ultimaterecyclerview.uiUtils;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public class SavedStateScrolling implements Parcelable {
    public SparseIntArray childrenHeights;
    public int prevFirstVisibleChildHeight;
    public int prevFirstVisiblePosition;
    public int prevScrollY;
    public int prevScrolledChildrenHeight;
    public int scrollY;
    public Parcelable superState;
    public static final SavedStateScrolling EMPTY_STATE = new SavedStateScrolling() { // from class: com.marshalchen.ultimaterecyclerview.uiUtils.SavedStateScrolling.1
    };
    public static final Parcelable.Creator<SavedStateScrolling> CREATOR = new Parcelable.Creator<SavedStateScrolling>() { // from class: com.marshalchen.ultimaterecyclerview.uiUtils.SavedStateScrolling.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SavedStateScrolling createFromParcel(Parcel parcel) {
            return new SavedStateScrolling(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SavedStateScrolling[] newArray(int i) {
            return new SavedStateScrolling[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SavedStateScrolling() {
        this.prevFirstVisibleChildHeight = -1;
        this.superState = null;
    }

    public SavedStateScrolling(Parcelable parcelable) {
        this.prevFirstVisibleChildHeight = -1;
        this.superState = parcelable == EMPTY_STATE ? null : parcelable;
    }

    public SavedStateScrolling(Parcel parcel) {
        this.prevFirstVisibleChildHeight = -1;
        Parcelable parcelable = parcel.readParcelable(RecyclerView.class.getClassLoader());
        this.superState = parcelable == null ? EMPTY_STATE : parcelable;
        this.prevFirstVisiblePosition = parcel.readInt();
        this.prevFirstVisibleChildHeight = parcel.readInt();
        this.prevScrolledChildrenHeight = parcel.readInt();
        this.prevScrollY = parcel.readInt();
        this.scrollY = parcel.readInt();
        this.childrenHeights = new SparseIntArray();
        int i = parcel.readInt();
        if (i > 0) {
            for (int i2 = 0; i2 < i; i2++) {
                this.childrenHeights.put(parcel.readInt(), parcel.readInt());
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.superState, i);
        parcel.writeInt(this.prevFirstVisiblePosition);
        parcel.writeInt(this.prevFirstVisibleChildHeight);
        parcel.writeInt(this.prevScrolledChildrenHeight);
        parcel.writeInt(this.prevScrollY);
        parcel.writeInt(this.scrollY);
        SparseIntArray sparseIntArray = this.childrenHeights;
        int size = sparseIntArray == null ? 0 : sparseIntArray.size();
        parcel.writeInt(size);
        if (size > 0) {
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeInt(this.childrenHeights.keyAt(i2));
                parcel.writeInt(this.childrenHeights.valueAt(i2));
            }
        }
    }

    public Parcelable getSuperState() {
        return this.superState;
    }
}
