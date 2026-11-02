package it.piapostolos.android.pinotes.async.notes;

import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.models.Category;
import it.piapostolos.android.pinotes.models.Note;
import java.util.List;

/* loaded from: classes3.dex */
public class NoteProcessorCategorize extends NoteProcessor {
    Category category;

    public NoteProcessorCategorize(List<Note> list, Category category) {
        super(list);
        this.category = category;
    }

    @Override // it.piapostolos.android.pinotes.async.notes.NoteProcessor
    protected void processNote(Note note) {
        note.setCategory(this.category);
        DbHelper.getInstance().updateNote(note, false);
    }
}
