package it.piapostolos.android.pinotes.async.notes;

import de.greenrobot.event.EventBus;
import it.piapostolos.android.pinotes.PiNotes;
import it.piapostolos.android.pinotes.async.bus.NotesDeletedEvent;
import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.models.Attachment;
import it.piapostolos.android.pinotes.models.Note;
import it.piapostolos.android.pinotes.utils.StorageHelper;
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

    @Override // it.piapostolos.android.pinotes.async.notes.NoteProcessor
    protected void processNote(Note note) {
        if (!DbHelper.getInstance().deleteNote(note) || this.keepAttachments) {
            return;
        }
        Iterator<Attachment> it2 = note.getAttachmentsList().iterator();
        while (it2.hasNext()) {
            StorageHelper.deleteExternalStoragePrivateFile(PiNotes.getAppContext(), it2.next().getUri().getLastPathSegment());
        }
    }

    @Override // it.piapostolos.android.pinotes.async.notes.NoteProcessor
    protected void afterProcess(List<Note> list) {
        EventBus.getDefault().post(new NotesDeletedEvent(list));
    }
}
