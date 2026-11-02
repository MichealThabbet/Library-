package com.huawei.hms.api.config;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class NotificationConfig implements Parcelable {
    public static final Parcelable.Creator<NotificationConfig> CREATOR = new Parcelable.Creator<NotificationConfig>() { // from class: com.huawei.hms.api.config.NotificationConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationConfig createFromParcel(Parcel parcel) {
            return new NotificationConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationConfig[] newArray(int i) {
            return new NotificationConfig[i];
        }
    };
    private final Bitmap bitmap;
    private final boolean isQueueEmpty;
    private final PendingIntent nextPendingIntent;
    private final PendingIntent playPendingIntent;
    private final PendingIntent prePendingIntent;
    private final String singerName;
    private final String songName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private NotificationConfig(Builder builder) {
        this.prePendingIntent = builder.prePendingIntent;
        this.playPendingIntent = builder.playPendingIntent;
        this.nextPendingIntent = builder.nextPendingIntent;
        this.isQueueEmpty = builder.isQueueEmpty;
        this.bitmap = builder.bitmap;
        this.singerName = builder.singerName;
        this.songName = builder.songName;
    }

    protected NotificationConfig(Parcel parcel) {
        this.prePendingIntent = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
        this.playPendingIntent = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
        this.nextPendingIntent = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
        this.isQueueEmpty = parcel.readByte() != 0;
        this.bitmap = (Bitmap) parcel.readParcelable(Bitmap.class.getClassLoader());
        this.singerName = parcel.readString();
        this.songName = parcel.readString();
    }

    public PendingIntent getPrePendingIntent() {
        return this.prePendingIntent;
    }

    public PendingIntent getPlayPendingIntent() {
        return this.playPendingIntent;
    }

    public PendingIntent getNextPendingIntent() {
        return this.nextPendingIntent;
    }

    public boolean isQueueEmpty() {
        return this.isQueueEmpty;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public String getSingerName() {
        return this.singerName;
    }

    public String getSongName() {
        return this.songName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.prePendingIntent, i);
        parcel.writeParcelable(this.playPendingIntent, i);
        parcel.writeParcelable(this.nextPendingIntent, i);
        parcel.writeByte(this.isQueueEmpty ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.bitmap, i);
        parcel.writeString(this.singerName);
        parcel.writeString(this.songName);
    }

    public static final class Builder implements Parcelable {
        public static final Parcelable.Creator<Builder> CREATOR = new Parcelable.Creator<Builder>() { // from class: com.huawei.hms.api.config.NotificationConfig.Builder.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Builder createFromParcel(Parcel parcel) {
                return new Builder(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Builder[] newArray(int i) {
                return new Builder[i];
            }
        };
        Bitmap bitmap;
        boolean isQueueEmpty;
        PendingIntent nextPendingIntent;
        PendingIntent playPendingIntent;
        PendingIntent prePendingIntent;
        String singerName;
        String songName;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Builder() {
            this.prePendingIntent = null;
            this.playPendingIntent = null;
            this.nextPendingIntent = null;
            this.isQueueEmpty = false;
            this.bitmap = null;
            this.singerName = null;
            this.songName = null;
        }

        protected Builder(Parcel parcel) {
            this.prePendingIntent = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
            this.playPendingIntent = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
            this.nextPendingIntent = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
            this.isQueueEmpty = parcel.readByte() != 0;
            this.bitmap = (Bitmap) parcel.readParcelable(Bitmap.class.getClassLoader());
            this.singerName = parcel.readString();
            this.songName = parcel.readString();
        }

        public Builder setPrePendingIntent(PendingIntent pendingIntent) {
            this.prePendingIntent = pendingIntent;
            return this;
        }

        public Builder setPlayPendingIntent(PendingIntent pendingIntent) {
            this.playPendingIntent = pendingIntent;
            return this;
        }

        public Builder setNextPendingIntent(PendingIntent pendingIntent) {
            this.nextPendingIntent = pendingIntent;
            return this;
        }

        public Builder setQueueEmpty(boolean z) {
            this.isQueueEmpty = z;
            return this;
        }

        public Builder setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        public Builder setSingerName(String str) {
            this.singerName = str;
            return this;
        }

        public Builder setSongName(String str) {
            this.songName = str;
            return this;
        }

        public NotificationConfig build() {
            return new NotificationConfig(this);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.prePendingIntent, i);
            parcel.writeParcelable(this.playPendingIntent, i);
            parcel.writeParcelable(this.nextPendingIntent, i);
            parcel.writeByte(this.isQueueEmpty ? (byte) 1 : (byte) 0);
            parcel.writeParcelable(this.bitmap, i);
            parcel.writeString(this.singerName);
            parcel.writeString(this.songName);
        }
    }
}
