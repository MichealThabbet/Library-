package it.feio.android.omninotes.commons.models;

import com.google.gson.Gson;
import it.feio.android.omninotes.commons.utils.EqualityChecker;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class BaseNote implements Serializable {
    private String address;
    private String alarm;
    private Boolean archived;
    private List<? extends BaseAttachment> attachmentsList;
    private transient List<? extends BaseAttachment> attachmentsListOld;
    private BaseCategory baseCategory;
    private Boolean checklist;
    private String content;
    private Long creation;
    private Long lastModification;
    private Double latitude;
    private Boolean locked;
    private Double longitude;
    private String recurrenceRule;
    private Boolean reminderFired;
    private String title;
    private Boolean trashed;

    public BaseNote() {
        this.attachmentsList = new ArrayList();
        this.attachmentsListOld = new ArrayList();
        this.title = "";
        this.content = "";
        this.archived = false;
        this.trashed = false;
        this.locked = false;
        this.checklist = false;
    }

    public BaseNote(Long l, Long l2, String str, String str2, Integer num, Integer num2, String str3, Integer num3, String str4, String str5, String str6, BaseCategory baseCategory, Integer num4, Integer num5) {
        this.attachmentsList = new ArrayList();
        this.attachmentsListOld = new ArrayList();
        this.title = str;
        this.content = str2;
        this.creation = l;
        this.lastModification = l2;
        this.archived = Boolean.valueOf(num.intValue() == 1);
        this.trashed = Boolean.valueOf(num2.intValue() == 1);
        this.alarm = str3;
        this.reminderFired = Boolean.valueOf(num3.intValue() == 1);
        this.recurrenceRule = str4;
        setLatitude(str5);
        setLongitude(str6);
        this.baseCategory = baseCategory;
        this.locked = Boolean.valueOf(num4.intValue() == 1);
        this.checklist = Boolean.valueOf(num5.intValue() == 1);
    }

    public BaseNote(BaseNote baseNote) {
        this.attachmentsList = new ArrayList();
        this.attachmentsListOld = new ArrayList();
        buildFromNote(baseNote);
    }

    private void buildFromNote(BaseNote baseNote) {
        setTitle(baseNote.getTitle());
        setContent(baseNote.getContent());
        setCreation(baseNote.getCreation());
        setLastModification(baseNote.getLastModification());
        setArchived(baseNote.isArchived());
        setTrashed(baseNote.isTrashed());
        setAlarm(baseNote.getAlarm());
        setRecurrenceRule(baseNote.getRecurrenceRule());
        setReminderFired(baseNote.isReminderFired());
        setLatitude(baseNote.getLatitude());
        setLongitude(baseNote.getLongitude());
        setAddress(baseNote.getAddress());
        setCategory(baseNote.getCategory());
        setLocked(baseNote.isLocked());
        setChecklist(baseNote.isChecklist());
        ArrayList arrayList = new ArrayList();
        Iterator<? extends BaseAttachment> it2 = baseNote.getAttachmentsList().iterator();
        while (it2.hasNext()) {
            arrayList.add(it2.next());
        }
        setAttachmentsList(arrayList);
    }

    public void buildFromJson(String str) {
        buildFromNote((BaseNote) new Gson().fromJson(str, (Class) getClass()));
    }

    public void set_id(Long l) {
        this.creation = l;
    }

    public Long get_id() {
        return this.creation;
    }

    public String getTitle() {
        String str = this.title;
        return str == null ? "" : str;
    }

    public void setTitle(String str) {
        if (str == null) {
            str = "";
        }
        this.title = str;
    }

    public String getContent() {
        String str = this.content;
        return str == null ? "" : str;
    }

    public void setContent(String str) {
        if (str == null) {
            str = "";
        }
        this.content = str;
    }

    public Long getCreation() {
        return this.creation;
    }

    public void setCreation(Long l) {
        this.creation = l;
    }

    public void setCreation(String str) {
        Long lValueOf;
        try {
            lValueOf = Long.valueOf(Long.parseLong(str));
        } catch (NumberFormatException unused) {
            lValueOf = null;
        }
        this.creation = lValueOf;
    }

    public Long getLastModification() {
        return this.lastModification;
    }

    public void setLastModification(Long l) {
        this.lastModification = l;
    }

    public void setLastModification(String str) {
        Long lValueOf;
        try {
            lValueOf = Long.valueOf(Long.parseLong(str));
        } catch (NumberFormatException unused) {
            lValueOf = null;
        }
        this.lastModification = lValueOf;
    }

    public Boolean isArchived() {
        Boolean bool = this.archived;
        return Boolean.valueOf(bool != null && bool.booleanValue());
    }

    public void setArchived(Boolean bool) {
        this.archived = bool;
    }

    public void setArchived(int i) {
        this.archived = Boolean.valueOf(i == 1);
    }

    public Boolean isTrashed() {
        Boolean bool = this.trashed;
        return Boolean.valueOf(bool != null && bool.booleanValue());
    }

    public void setTrashed(Boolean bool) {
        this.trashed = bool;
    }

    public void setTrashed(int i) {
        this.trashed = Boolean.valueOf(i == 1);
    }

    public String getAlarm() {
        return this.alarm;
    }

    public void setAlarm(String str) {
        this.alarm = str;
    }

    public void setAlarm(long j) {
        this.alarm = String.valueOf(j);
    }

    public Boolean isReminderFired() {
        Boolean bool = this.reminderFired;
        return Boolean.valueOf(bool != null && bool.booleanValue());
    }

    public void setReminderFired(Boolean bool) {
        this.reminderFired = bool;
    }

    public void setReminderFired(int i) {
        this.reminderFired = Boolean.valueOf(i == 1);
    }

    public String getRecurrenceRule() {
        return this.recurrenceRule;
    }

    public void setRecurrenceRule(String str) {
        this.recurrenceRule = str;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double d) {
        this.latitude = d;
    }

    public void setLatitude(String str) {
        try {
            setLatitude(Double.valueOf(Double.parseDouble(str)));
        } catch (NullPointerException | NumberFormatException unused) {
            this.latitude = null;
        }
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double d) {
        this.longitude = d;
    }

    public void setLongitude(String str) {
        try {
            setLongitude(Double.valueOf(Double.parseDouble(str)));
        } catch (NullPointerException unused) {
            this.longitude = null;
        } catch (NumberFormatException unused2) {
            this.longitude = null;
        }
    }

    public BaseCategory getCategory() {
        return this.baseCategory;
    }

    public void setCategory(BaseCategory baseCategory) {
        this.baseCategory = baseCategory;
    }

    public Boolean isLocked() {
        Boolean bool = this.locked;
        return Boolean.valueOf(bool != null && bool.booleanValue());
    }

    public void setLocked(Boolean bool) {
        this.locked = bool;
    }

    public void setLocked(int i) {
        this.locked = Boolean.valueOf(i == 1);
    }

    public Boolean isChecklist() {
        Boolean bool = this.checklist;
        return Boolean.valueOf(bool != null && bool.booleanValue());
    }

    public void setChecklist(Boolean bool) {
        this.checklist = bool;
    }

    public void setChecklist(int i) {
        this.checklist = Boolean.valueOf(i == 1);
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public List<? extends BaseAttachment> getAttachmentsList() {
        return this.attachmentsList;
    }

    public void setAttachmentsList(List<? extends BaseAttachment> list) {
        this.attachmentsList = list;
    }

    public void backupAttachmentsList() {
        ArrayList arrayList = new ArrayList();
        Iterator<? extends BaseAttachment> it2 = getAttachmentsList().iterator();
        while (it2.hasNext()) {
            arrayList.add(it2.next());
        }
        this.attachmentsListOld = arrayList;
    }

    public List<? extends BaseAttachment> getAttachmentsListOld() {
        return this.attachmentsListOld;
    }

    public void setAttachmentsListOld(List<? extends BaseAttachment> list) {
        this.attachmentsListOld = list;
    }

    public boolean equals(Object obj) {
        try {
            BaseNote baseNote = (BaseNote) obj;
            return EqualityChecker.check(new Object[]{getTitle(), getContent(), getCreation(), getLastModification(), isArchived(), isTrashed(), getAlarm(), getRecurrenceRule(), getLatitude(), getLongitude(), getAddress(), isLocked(), getCategory(), isChecklist()}, new Object[]{baseNote.getTitle(), baseNote.getContent(), baseNote.getCreation(), baseNote.getLastModification(), baseNote.isArchived(), baseNote.isTrashed(), baseNote.getAlarm(), baseNote.getRecurrenceRule(), baseNote.getLatitude(), baseNote.getLongitude(), baseNote.getAddress(), baseNote.isLocked(), baseNote.getCategory(), baseNote.isChecklist()});
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean isChanged(BaseNote baseNote) {
        return (equals(baseNote) && getAttachmentsList().equals(baseNote.getAttachmentsList())) ? false : true;
    }

    public boolean isEmpty() {
        BaseNote baseNote = new BaseNote();
        baseNote.setCreation(getCreation());
        baseNote.setCategory(getCategory());
        return !isChanged(baseNote);
    }

    public String toString() {
        return getTitle();
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }
}
