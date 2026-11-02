package it.orsozoxi.android.orsonotes.async.notes;

import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.models.Category;
import it.orsozoxi.android.orsonotes.models.Note;
import java.util.List;

/* loaded from: classes3.dex */
public class NoteProcessorCategorize extends NoteProcessor {
    Category category;

    public NoteProcessorCategorize(List<Note> list, Category category) {
        super(list);
        this.category = category;
    }

    @Override // it.orsozoxi.android.orsonotes.async.notes.NoteProcessor
    protected void processNote(Note note) {
        note.setCategory(this.category);
        DbHelper.getInstance().updateNote(note, false);
    }
}
