package it.piapostolos.android.pinotes.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import it.feio.android.omninotes.commons.models.BaseAttachment;
import java.util.Calendar;

/* loaded from: classes3.dex */
public class Attachment extends BaseAttachment implements Parcelable {
    public static final Parcelable.Creator<Attachment> CREATOR = new Parcelable.Creator<Attachment>() { // from class: it.piapostolos.android.pinotes.models.Attachment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Attachment createFromParcel(Parcel parcel) {
            return new Attachment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Attachment[] newArray(int i) {
            return new Attachment[i];
        }
    };
    private Uri uri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Attachment(Uri uri, String str) {
        this(Calendar.getInstance().getTimeInMillis(), uri, null, 0L, 0L, str);
    }

    public Attachment(long j, Uri uri, String str, long j2, long j3, String str2) {
        super(Long.valueOf(j), uri != null ? uri.getPath() : null, str, j2, j3, str2);
        setUri(uri);
    }

    public Attachment(BaseAttachment baseAttachment) {
        super(baseAttachment.getId(), baseAttachment.getUriPath(), baseAttachment.getName(), baseAttachment.getSize(), baseAttachment.getLength(), baseAttachment.getMime_type());
        this.uri = Uri.parse(baseAttachment.getUriPath());
    }

    private Attachment(Parcel parcel) {
        setId(Long.valueOf(parcel.readLong()));
        setUri(Uri.parse(parcel.readString()));
        setMime_type(parcel.readString());
    }

    public Uri getUri() {
        return this.uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
        setUriPath(uri != null ? uri.toString() : "");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(getId().longValue());
        parcel.writeString(getUri().toString());
        parcel.writeString(getMime_type());
    }
}
