package it.orsozoxi.android.orsonotes.async.notes;

import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.utils.ReminderHelper;
import it.orsozoxi.android.orsonotes.utils.ShortcutHelper;
import java.util.List;

/* loaded from: classes3.dex */
public class NoteProcessorTrash extends NoteProcessor {
    boolean trash;

    public NoteProcessorTrash(List<Note> list, boolean z) {
        super(list);
        this.trash = z;
    }

    @Override // it.orsozoxi.android.orsonotes.async.notes.NoteProcessor
    protected void processNote(Note note) {
        if (this.trash) {
            ShortcutHelper.removeshortCut(OrsoNotes.getAppContext(), note);
            ReminderHelper.removeReminder(OrsoNotes.getAppContext(), note);
        } else {
            ReminderHelper.addReminder(OrsoNotes.getAppContext(), note);
        }
        DbHelper.getInstance().trashNote(note, this.trash);
    }
}
