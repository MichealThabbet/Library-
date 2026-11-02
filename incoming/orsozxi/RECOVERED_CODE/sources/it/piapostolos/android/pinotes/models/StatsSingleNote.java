package it.piapostolos.android.pinotes.models;

/* loaded from: classes3.dex */
public class StatsSingleNote {
    protected int attachments;
    protected int audioRecordings;
    protected String categoryName;
    protected int chars;
    protected int checklistCompletedItemsNumber;
    protected int checklistItemsNumber;
    protected int files;
    protected int images;
    protected int sketches;
    protected int tags;
    protected int videos;
    protected int words;

    public int getTags() {
        return this.tags;
    }

    public void setTags(int i) {
        this.tags = i;
    }

    public int getWords() {
        return this.words;
    }

    public void setWords(int i) {
        this.words = i;
    }

    public int getChars() {
        return this.chars;
    }

    public void setChars(int i) {
        this.chars = i;
    }

    public int getAttachments() {
        return this.attachments;
    }

    public void setAttachments(int i) {
        this.attachments = i;
    }

    public int getImages() {
        return this.images;
    }

    public void setImages(int i) {
        this.images = i;
    }

    public int getVideos() {
        return this.videos;
    }

    public void setVideos(int i) {
        this.videos = i;
    }

    public int getAudioRecordings() {
        return this.audioRecordings;
    }

    public void setAudioRecordings(int i) {
        this.audioRecordings = i;
    }

    public int getSketches() {
        return this.sketches;
    }

    public void setSketches(int i) {
        this.sketches = i;
    }

    public int getFiles() {
        return this.files;
    }

    public void setFiles(int i) {
        this.files = i;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String str) {
        this.categoryName = str;
    }

    public int getChecklistItemsNumber() {
        return this.checklistItemsNumber;
    }

    public void setChecklistItemsNumber(int i) {
        this.checklistItemsNumber = i;
    }

    public int getChecklistCompletedItemsNumber() {
        return this.checklistCompletedItemsNumber;
    }

    public void setChecklistCompletedItemsNumber(int i) {
        this.checklistCompletedItemsNumber = i;
    }
}
