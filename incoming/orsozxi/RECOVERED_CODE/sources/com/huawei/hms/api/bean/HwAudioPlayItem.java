package com.huawei.hms.api.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.huawei.hms.api.config.HwAudioConfigContants;

/* loaded from: classes2.dex */
public class HwAudioPlayItem implements Cloneable, Parcelable {
    public static final Parcelable.Creator<HwAudioPlayItem> CREATOR = new Parcelable.Creator<HwAudioPlayItem>() { // from class: com.huawei.hms.api.bean.HwAudioPlayItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HwAudioPlayItem createFromParcel(Parcel parcel) {
            return new HwAudioPlayItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HwAudioPlayItem[] newArray(int i) {
            return new HwAudioPlayItem[i];
        }
    };
    private String audioId;
    private String audioTitle;
    private String audioType;
    private String bigImageURL;
    private long contentLength;
    private String contentType;
    private long duration;
    private String filePath;
    private int isOnline;
    private String midImageURL;
    private String onlinePath;
    private long playPosition;
    private String singer;
    private String smallImageURL;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HwAudioPlayItem() {
    }

    public HwAudioPlayItem(Parcel parcel) {
        this.audioType = parcel.readString();
        this.audioTitle = parcel.readString();
        this.audioId = parcel.readString();
        this.filePath = parcel.readString();
        this.onlinePath = parcel.readString();
        this.isOnline = parcel.readInt();
        this.contentType = parcel.readString();
        this.duration = parcel.readLong();
        this.singer = parcel.readString();
        this.smallImageURL = parcel.readString();
        this.midImageURL = parcel.readString();
        this.bigImageURL = parcel.readString();
        this.playPosition = parcel.readLong();
        if (HwAudioConfigContants.getSdkLevel() >= 7) {
            this.contentLength = parcel.readLong();
        }
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public HwAudioPlayItem m19clone() throws CloneNotSupportedException {
        return (HwAudioPlayItem) super.clone();
    }

    public void setAudioType(String str) {
        this.audioType = str;
    }

    public void setAudioTitle(String str) {
        this.audioTitle = str;
    }

    public void setAudioId(String str) {
        this.audioId = str;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public void setOnlinePath(String str) {
        this.onlinePath = str;
    }

    public void setOnline(int i) {
        this.isOnline = i;
    }

    public void setContentType(String str) {
        this.contentType = str;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public String getAudioType() {
        return this.audioType;
    }

    public String getAudioTitle() {
        return this.audioTitle;
    }

    public String getAudioId() {
        return this.audioId;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public String getOnlinePath() {
        return this.onlinePath;
    }

    public int isOnline() {
        return this.isOnline;
    }

    public String getContentType() {
        return this.contentType;
    }

    public long getDuration() {
        return this.duration;
    }

    public int getIsOnline() {
        return this.isOnline;
    }

    public String getSinger() {
        return this.singer;
    }

    public void setIsOnline(int i) {
        this.isOnline = i;
    }

    public void setSinger(String str) {
        this.singer = str;
    }

    public String getSmallImageURL() {
        return this.smallImageURL;
    }

    public void setSmallImageURL(String str) {
        this.smallImageURL = str;
    }

    public String getMidImageURL() {
        return this.midImageURL;
    }

    public String getBigImageURL() {
        return this.bigImageURL;
    }

    public void setMidImageURL(String str) {
        this.midImageURL = str;
    }

    public void setBigImageURL(String str) {
        this.bigImageURL = str;
    }

    public long getPlayPosition() {
        return this.playPosition;
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public void setPlayPosition(long j) {
        this.playPosition = j;
    }

    public long getContentLength() {
        return this.contentLength;
    }

    public void setContentLength(long j) {
        this.contentLength = j;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.audioType);
        parcel.writeString(this.audioTitle);
        parcel.writeString(this.audioId);
        parcel.writeString(this.filePath);
        parcel.writeString(this.onlinePath);
        parcel.writeInt(this.isOnline);
        parcel.writeString(this.contentType);
        parcel.writeLong(this.duration);
        parcel.writeString(this.singer);
        parcel.writeString(this.smallImageURL);
        parcel.writeString(this.midImageURL);
        parcel.writeString(this.bigImageURL);
        parcel.writeLong(this.playPosition);
        if (HwAudioConfigContants.getSdkLevel() >= 7) {
            parcel.writeLong(this.contentLength);
        }
    }

    public void readFromParcel(Parcel parcel) {
        this.audioType = parcel.readString();
        this.audioTitle = parcel.readString();
        this.audioId = parcel.readString();
        this.filePath = parcel.readString();
        this.onlinePath = parcel.readString();
        this.isOnline = parcel.readInt();
        this.contentType = parcel.readString();
        this.duration = parcel.readLong();
        this.singer = parcel.readString();
        this.smallImageURL = parcel.readString();
        this.midImageURL = parcel.readString();
        this.bigImageURL = parcel.readString();
        this.playPosition = parcel.readLong();
        if (HwAudioConfigContants.getSdkLevel() >= 7) {
            this.contentLength = parcel.readLong();
        }
    }
}
