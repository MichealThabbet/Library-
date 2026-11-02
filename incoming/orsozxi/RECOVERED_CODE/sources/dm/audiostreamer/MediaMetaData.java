package dm.audiostreamer;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class MediaMetaData implements Parcelable {
    public static final Parcelable.Creator<MediaMetaData> CREATOR = new Parcelable.Creator<MediaMetaData>() { // from class: dm.audiostreamer.MediaMetaData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaMetaData createFromParcel(Parcel parcel) {
            return new MediaMetaData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaMetaData[] newArray(int i) {
            return new MediaMetaData[i];
        }
    };
    private String mediaAlbum;
    private String mediaArt;
    private String mediaArtist;
    private String mediaComposer;
    private String mediaDuration;
    private String mediaId;
    private String mediaTitle;
    private String mediaUrl;
    private int playState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MediaMetaData() {
    }

    protected MediaMetaData(Parcel parcel) {
        this.mediaId = parcel.readString();
        this.mediaUrl = parcel.readString();
        this.mediaTitle = parcel.readString();
        this.mediaArtist = parcel.readString();
        this.mediaAlbum = parcel.readString();
        this.mediaComposer = parcel.readString();
        this.mediaDuration = parcel.readString();
        this.mediaArt = parcel.readString();
        this.playState = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mediaId);
        parcel.writeString(this.mediaUrl);
        parcel.writeString(this.mediaTitle);
        parcel.writeString(this.mediaArtist);
        parcel.writeString(this.mediaAlbum);
        parcel.writeString(this.mediaComposer);
        parcel.writeString(this.mediaDuration);
        parcel.writeString(this.mediaArt);
        parcel.writeInt(this.playState);
    }

    public String getMediaId() {
        return this.mediaId;
    }

    public void setMediaId(String str) {
        this.mediaId = str;
    }

    public String getMediaUrl() {
        return this.mediaUrl;
    }

    public void setMediaUrl(String str) {
        this.mediaUrl = str;
    }

    public String getMediaTitle() {
        return this.mediaTitle;
    }

    public void setMediaTitle(String str) {
        this.mediaTitle = str;
    }

    public String getMediaArtist() {
        return this.mediaArtist;
    }

    public void setMediaArtist(String str) {
        this.mediaArtist = str;
    }

    public String getMediaAlbum() {
        return this.mediaAlbum;
    }

    public void setMediaAlbum(String str) {
        this.mediaAlbum = str;
    }

    public String getMediaComposer() {
        return this.mediaComposer;
    }

    public void setMediaComposer(String str) {
        this.mediaComposer = str;
    }

    public String getMediaDuration() {
        return this.mediaDuration;
    }

    public void setMediaDuration(String str) {
        this.mediaDuration = str;
    }

    public String getMediaArt() {
        return this.mediaArt;
    }

    public void setMediaArt(String str) {
        this.mediaArt = str;
    }

    public int getPlayState() {
        return this.playState;
    }

    public void setPlayState(int i) {
        this.playState = i;
    }
}
