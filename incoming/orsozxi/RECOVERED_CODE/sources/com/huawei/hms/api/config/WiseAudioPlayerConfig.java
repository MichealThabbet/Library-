package com.huawei.hms.api.config;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class WiseAudioPlayerConfig implements Parcelable {
    public static final Parcelable.Creator<WiseAudioPlayerConfig> CREATOR = new Parcelable.Creator<WiseAudioPlayerConfig>() { // from class: com.huawei.hms.api.config.WiseAudioPlayerConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WiseAudioPlayerConfig createFromParcel(Parcel parcel) {
            return new WiseAudioPlayerConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WiseAudioPlayerConfig[] newArray(int i) {
            return new WiseAudioPlayerConfig[i];
        }
    };
    private String debugPath;
    private boolean handleAudioFocus;
    private boolean isDebugMode;
    private int playCacheSize;
    private String releasePath;
    private int sdklevel;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WiseAudioPlayerConfig() {
    }

    public WiseAudioPlayerConfig(Parcel parcel) {
        this.playCacheSize = parcel.readInt();
        this.handleAudioFocus = parcel.readInt() != 0;
        this.debugPath = parcel.readString();
        this.releasePath = parcel.readString();
        this.isDebugMode = parcel.readInt() != 0;
        this.sdklevel = parcel.readInt();
    }

    public WiseAudioPlayerConfig setPlayCacheSize(int i) {
        this.playCacheSize = i;
        return this;
    }

    public WiseAudioPlayerConfig setHandleAudioFocus(boolean z) {
        this.handleAudioFocus = z;
        return this;
    }

    public WiseAudioPlayerConfig setDebugPath(String str) {
        this.debugPath = str;
        return this;
    }

    public WiseAudioPlayerConfig setReleasePath(String str) {
        this.releasePath = str;
        return this;
    }

    public WiseAudioPlayerConfig setDebugMode(boolean z) {
        this.isDebugMode = z;
        return this;
    }

    public int getPlayCacheSize() {
        return this.playCacheSize;
    }

    public boolean isHandleAudioFocus() {
        return this.handleAudioFocus;
    }

    public String getDebugPath() {
        return this.debugPath;
    }

    public String getReleasePath() {
        return this.releasePath;
    }

    public boolean isDebugMode() {
        return this.isDebugMode;
    }

    public int getSdklevel() {
        return this.sdklevel;
    }

    public WiseAudioPlayerConfig setSdklevel(int i) {
        this.sdklevel = i;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.playCacheSize);
        parcel.writeInt(this.handleAudioFocus ? 1 : 0);
        parcel.writeString(this.debugPath);
        parcel.writeString(this.releasePath);
        parcel.writeInt(this.isDebugMode ? 1 : 0);
        parcel.writeInt(this.sdklevel);
    }
}
