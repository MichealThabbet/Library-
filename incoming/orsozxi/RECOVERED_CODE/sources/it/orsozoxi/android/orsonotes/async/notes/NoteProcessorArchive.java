package it.orsozoxi.android.orsonotes.async.notes;

import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.models.Note;
import java.util.List;

/* loaded from: classes3.dex */
public class NoteProcessorArchive extends NoteProcessor {
    boolean archive;

    public NoteProcessorArchive(List<Note> list, boolean z) {
        super(list);
        this.archive = z;
    }

    @Override // it.orsozoxi.android.orsonotes.async.notes.NoteProcessor
    protected void processNote(Note note) {
        DbHelper.getInstance().archiveNote(note, this.archive);
    }
}
