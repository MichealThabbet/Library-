package it.piapostolos.android.pinotes.models;

import android.os.Parcel;
import android.os.Parcelable;
import it.feio.android.omninotes.commons.models.BaseAttachment;
import it.feio.android.omninotes.commons.models.BaseCategory;
import it.feio.android.omninotes.commons.models.BaseNote;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class Note extends BaseNote implements Parcelable {
    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() { // from class: it.piapostolos.android.pinotes.models.Note.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Note createFromParcel(Parcel parcel) {
            return new Note(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Note[] newArray(int i) {
            return new Note[i];
        }
    };
    private boolean passwordChecked;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Note() {
        this.passwordChecked = false;
    }

    public Note(Long l, Long l2, String str, String str2, Integer num, Integer num2, String str3, String str4, Integer num3, String str5, String str6, Category category, Integer num4, Integer num5) {
        super(l, l2, str, str2, num, num2, str3, num3, str4, str5, str6, category, num4, num5);
        this.passwordChecked = false;
    }

    public Note(Note note) {
        super(note);
        this.passwordChecked = false;
        setPasswordChecked(note.isPasswordChecked());
    }

    private Note(Parcel parcel) {
        this.passwordChecked = false;
        setCreation(parcel.readString());
        setLastModification(parcel.readString());
        setTitle(parcel.readString());
        setContent(parcel.readString());
        setArchived(parcel.readInt());
        setTrashed(parcel.readInt());
        setAlarm(parcel.readString());
        setReminderFired(parcel.readInt());
        setRecurrenceRule(parcel.readString());
        setLatitude(parcel.readString());
        setLongitude(parcel.readString());
        setAddress(parcel.readString());
        super.setCategory((BaseCategory) parcel.readParcelable(Category.class.getClassLoader()));
        setLocked(parcel.readInt());
        setChecklist(parcel.readInt());
        parcel.readList(getAttachmentsList(), Attachment.class.getClassLoader());
    }

    @Override // it.feio.android.omninotes.commons.models.BaseNote
    public List<Attachment> getAttachmentsList() {
        return super.getAttachmentsList();
    }

    public void setAttachmentsList(ArrayList<Attachment> arrayList) {
        super.setAttachmentsList((List<? extends BaseAttachment>) arrayList);
    }

    public void addAttachment(Attachment attachment) {
        List<? extends BaseAttachment> attachmentsList = super.getAttachmentsList();
        attachmentsList.add(attachment);
        setAttachmentsList(attachmentsList);
    }

    public void removeAttachment(Attachment attachment) {
        List<? extends BaseAttachment> attachmentsList = super.getAttachmentsList();
        attachmentsList.remove(attachment);
        setAttachmentsList(attachmentsList);
    }

    @Override // it.feio.android.omninotes.commons.models.BaseNote
    public List<Attachment> getAttachmentsListOld() {
        return super.getAttachmentsListOld();
    }

    public void setAttachmentsListOld(ArrayList<Attachment> arrayList) {
        super.setAttachmentsListOld((List<? extends BaseAttachment>) arrayList);
    }

    public boolean isPasswordChecked() {
        return this.passwordChecked;
    }

    public void setPasswordChecked(boolean z) {
        this.passwordChecked = z;
    }

    @Override // it.feio.android.omninotes.commons.models.BaseNote
    public Category getCategory() {
        try {
            return (Category) super.getCategory();
        } catch (ClassCastException unused) {
            return new Category(super.getCategory());
        }
    }

    public void setCategory(Category category) {
        if (category != null && category.getClass().equals(BaseCategory.class)) {
            setCategory(new Category(category));
        }
        super.setCategory((BaseCategory) category);
    }

    @Override // it.feio.android.omninotes.commons.models.BaseNote
    public void buildFromJson(String str) {
        super.buildFromJson(str);
        ArrayList arrayList = new ArrayList();
        Iterator<Attachment> it2 = getAttachmentsList().iterator();
        while (it2.hasNext()) {
            arrayList.add(new Attachment(it2.next()));
        }
        setAttachmentsList((List<? extends BaseAttachment>) arrayList);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(String.valueOf(getCreation()));
        parcel.writeString(String.valueOf(getLastModification()));
        parcel.writeString(getTitle());
        parcel.writeString(getContent());
        parcel.writeInt(isArchived().booleanValue() ? 1 : 0);
        parcel.writeInt(isTrashed().booleanValue() ? 1 : 0);
        parcel.writeString(getAlarm());
        parcel.writeInt(isReminderFired().booleanValue() ? 1 : 0);
        parcel.writeString(getRecurrenceRule());
        parcel.writeString(String.valueOf(getLatitude()));
        parcel.writeString(String.valueOf(getLongitude()));
        parcel.writeString(getAddress());
        parcel.writeParcelable(getCategory(), 0);
        parcel.writeInt(isLocked().booleanValue() ? 1 : 0);
        parcel.writeInt(isChecklist().booleanValue() ? 1 : 0);
        parcel.writeList(getAttachmentsList());
    }
}
