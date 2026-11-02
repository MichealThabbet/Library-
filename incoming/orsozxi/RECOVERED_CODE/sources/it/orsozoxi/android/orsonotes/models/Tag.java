package it.orsozoxi.android.orsonotes.models;

import android.os.Parcel;
import android.os.Parcelable;
import it.feio.android.omninotes.commons.models.BaseTag;

/* loaded from: classes3.dex */
public class Tag extends BaseTag implements Parcelable {
    public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>() { // from class: it.orsozoxi.android.orsonotes.models.Tag.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Tag createFromParcel(Parcel parcel) {
            return new Tag(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Tag[] newArray(int i) {
            return new Tag[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private Tag(Parcel parcel) {
        setText(parcel.readString());
        setCount(parcel.readInt());
    }

    public Tag() {
    }

    public Tag(String str, Integer num) {
        super(str, num.intValue());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getText());
        parcel.writeInt(getCount());
    }

    public String toString() {
        return getText();
    }
}
