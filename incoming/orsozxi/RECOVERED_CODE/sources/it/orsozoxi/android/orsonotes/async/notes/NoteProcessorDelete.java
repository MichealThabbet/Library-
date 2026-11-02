package it.orsozoxi.android.orsonotes.async.notes;

import de.greenrobot.event.EventBus;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.async.bus.NotesDeletedEvent;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.models.Attachment;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.utils.StorageHelper;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class NoteProcessorDelete extends NoteProcessor {
    private final boolean keepAttachments;

    public NoteProcessorDelete(List<Note> list) {
        this(list, false);
    }

    public NoteProcessorDelete(List<Note> list, boolean z) {
        super(list);
        this.keepAttachments = z;
    }

    @Override // it.orsozoxi.android.orsonotes.async.notes.NoteProcessor
    protected void processNote(Note note) {
        if (!DbHelper.getInstance().deleteNote(note) || this.keepAttachments) {
            return;
        }
        Iterator<Attachment> it2 = note.getAttachmentsList().iterator();
        while (it2.hasNext()) {
            StorageHelper.deleteExternalStoragePrivateFile(OrsoNotes.getAppContext(), it2.next().getUri().getLastPathSegment());
        }
    }

    @Override // it.orsozoxi.android.orsonotes.async.notes.NoteProcessor
    protected void afterProcess(List<Note> list) {
        EventBus.getDefault().post(new NotesDeletedEvent(list));
    }
}
