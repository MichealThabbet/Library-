package it.orsozoxi.android.orsonotes.models;

import android.os.Parcel;
import android.os.Parcelable;
import it.feio.android.omninotes.commons.models.BaseCategory;

/* loaded from: classes3.dex */
public class Category extends BaseCategory implements Parcelable {
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() { // from class: it.orsozoxi.android.orsonotes.models.Category.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Category createFromParcel(Parcel parcel) {
            return new Category(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Category[] newArray(int i) {
            return new Category[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private Category(Parcel parcel) {
        setId(Long.valueOf(parcel.readLong()));
        setName(parcel.readString());
        setDescription(parcel.readString());
        setColor(parcel.readString());
    }

    public Category() {
    }

    public Category(BaseCategory baseCategory) {
        super(baseCategory.getId(), baseCategory.getName(), baseCategory.getDescription(), baseCategory.getColor());
    }

    public Category(Long l, String str, String str2, String str3) {
        super(l, str, str2, str3);
    }

    public Category(Long l, String str, String str2, String str3, int i) {
        super(l, str, str2, str3, i);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(getId().longValue());
        parcel.writeString(getName());
        parcel.writeString(getDescription());
        parcel.writeString(getColor());
    }

    public String toString() {
        return getName();
    }
}
