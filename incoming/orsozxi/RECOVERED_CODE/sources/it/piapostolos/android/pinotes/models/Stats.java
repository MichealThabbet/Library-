package it.piapostolos.android.pinotes.models;

/* loaded from: classes3.dex */
public class Stats extends StatsSingleNote {
    private int categories;
    private int charsAvg;
    private int charsMax;
    private int location;
    private int notesActive;
    private int notesArchived;
    private int notesChecklist;
    private int notesMasked;
    private int notesTrashed;
    private int reminders;
    private int remindersFutures;
    private long usageTime;
    private int wordsAvg;
    private int wordsMax;

    public int getNotesTotalNumber() {
        return this.notesActive + this.notesArchived + this.notesTrashed;
    }

    public int getNotesActive() {
        return this.notesActive;
    }

    public void setNotesActive(int i) {
        this.notesActive = i;
    }

    public int getNotesArchived() {
        return this.notesArchived;
    }

    public void setNotesArchived(int i) {
        this.notesArchived = i;
    }

    public int getNotesTrashed() {
        return this.notesTrashed;
    }

    public void setNotesTrashed(int i) {
        this.notesTrashed = i;
    }

    public int getReminders() {
        return this.reminders;
    }

    public void setReminders(int i) {
        this.reminders = i;
    }

    public int getRemindersFutures() {
        return this.remindersFutures;
    }

    public void setRemindersFutures(int i) {
        this.remindersFutures = i;
    }

    public int getNotesChecklist() {
        return this.notesChecklist;
    }

    public void setNotesChecklist(int i) {
        this.notesChecklist = i;
    }

    public int getNotesMasked() {
        return this.notesMasked;
    }

    public void setNotesMasked(int i) {
        this.notesMasked = i;
    }

    public int getCategories() {
        return this.categories;
    }

    public void setCategories(int i) {
        this.categories = i;
    }

    @Override // it.piapostolos.android.pinotes.models.StatsSingleNote
    public int getTags() {
        return this.tags;
    }

    @Override // it.piapostolos.android.pinotes.models.StatsSingleNote
    public void setTags(int i) {
        this.tags = i;
    }

    @Override // it.piapostolos.android.pinotes.models.StatsSingleNote
    public int getWords() {
        return this.words;
    }

    @Override // it.piapostolos.android.pinotes.models.StatsSingleNote
    public void setWords(int i) {
        this.words = i;
    }

    @Override // it.piapostolos.android.pinotes.models.StatsSingleNote
    public int getChars() {
        return this.chars;
    }

    @Override // it.piapostolos.android.pinotes.models.StatsSingleNote
    public void setChars(int i) {
        this.chars = i;
    }

    public int getWordsMax() {
        return this.wordsMax;
    }

    public void setWordsMax(int i) {
        this.wordsMax = i;
    }

    public int getWordsAvg() {
        return this.wordsAvg;
    }

    public void setWordsAvg(int i) {
        this.wordsAvg = i;
    }

    public int getCharsMax() {
        return this.charsMax;
    }

    public void setCharsMax(int i) {
        this.charsMax = i;
    }

    public int getCharsAvg() {
        return this.charsAvg;
    }

    public void setCharsAvg(int i) {
        this.charsAvg = i;
    }

    public int getLocation() {
        return this.location;
    }

    public void setLocation(int i) {
        this.location = i;
    }

    public long getUsageTime() {
        return this.usageTime;
    }

    public void setUsageTime(long j) {
        this.usageTime = j;
    }
}
