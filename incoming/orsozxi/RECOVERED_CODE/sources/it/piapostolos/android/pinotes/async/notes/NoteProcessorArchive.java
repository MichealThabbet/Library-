package it.piapostolos.android.pinotes.async.notes;

import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.models.Note;
import java.util.List;

/* loaded from: classes3.dex */
public class NoteProcessorArchive extends NoteProcessor {
    boolean archive;

    public NoteProcessorArchive(List<Note> list, boolean z) {
        super(list);
        this.archive = z;
    }

    @Override // it.piapostolos.android.pinotes.async.notes.NoteProcessor
    protected void processNote(Note note) {
        DbHelper.getInstance().archiveNote(note, this.archive);
    }
}
