package com.huawei.hms.analytics;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class UploadInfo implements Parcelable {
    public static final Parcelable.Creator<UploadInfo> CREATOR = new Parcelable.Creator<UploadInfo>() { // from class: com.huawei.hms.analytics.UploadInfo.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ UploadInfo createFromParcel(Parcel parcel) {
            return new UploadInfo(parcel, (byte) 0);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ UploadInfo[] newArray(int i) {
            return new UploadInfo[i];
        }
    };
    private String[] hij;
    public String ijk;
    public String ikl;
    public String klm;
    public String lmn;

    public UploadInfo() {
        this.hij = new String[0];
    }

    private UploadInfo(Parcel parcel) {
        this.hij = new String[0];
        this.lmn = parcel.readString();
        this.klm = parcel.readString();
        this.hij = parcel.createStringArray();
        this.ikl = parcel.readString();
        this.ijk = parcel.readString();
    }

    /* synthetic */ UploadInfo(Parcel parcel, byte b) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final void lmn(String[] strArr) {
        if (strArr != null) {
            this.hij = (String[]) strArr.clone();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.lmn);
        parcel.writeString(this.klm);
        parcel.writeStringArray(this.hij);
        parcel.writeString(this.ikl);
        parcel.writeString(this.ijk);
    }
}
