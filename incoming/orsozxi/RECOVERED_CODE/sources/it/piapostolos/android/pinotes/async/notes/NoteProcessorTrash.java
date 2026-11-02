package it.piapostolos.android.pinotes.async.notes;

import it.piapostolos.android.pinotes.PiNotes;
import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.models.Note;
import it.piapostolos.android.pinotes.utils.ReminderHelper;
import it.piapostolos.android.pinotes.utils.ShortcutHelper;
import java.util.List;

/* loaded from: classes3.dex */
public class NoteProcessorTrash extends NoteProcessor {
    boolean trash;

    public NoteProcessorTrash(List<Note> list, boolean z) {
        super(list);
        this.trash = z;
    }

    @Override // it.piapostolos.android.pinotes.async.notes.NoteProcessor
    protected void processNote(Note note) {
        if (this.trash) {
            ShortcutHelper.removeshortCut(PiNotes.getAppContext(), note);
            ReminderHelper.removeReminder(PiNotes.getAppContext(), note);
        } else {
            ReminderHelper.addReminder(PiNotes.getAppContext(), note);
        }
        DbHelper.getInstance().trashNote(note, this.trash);
    }
}
