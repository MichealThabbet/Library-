package it.orsozoxi.android.orsonotes.async.notes;

import android.content.Context;
import android.os.AsyncTask;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.models.Attachment;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.models.listeners.OnNoteSaved;
import it.orsozoxi.android.orsonotes.utils.ReminderHelper;
import it.orsozoxi.android.orsonotes.utils.StorageHelper;
import it.orsozoxi.android.orsonotes.utils.date.DateUtils;
import java.util.List;

/* loaded from: classes3.dex */
public class SaveNoteTask extends AsyncTask<Note, Void, Note> {
    private Context context;
    private OnNoteSaved mOnNoteSaved;
    private boolean updateLastModification;

    public SaveNoteTask(boolean z) {
        this(null, z);
    }

    public SaveNoteTask(OnNoteSaved onNoteSaved, boolean z) {
        this.updateLastModification = true;
        this.context = OrsoNotes.getAppContext();
        this.mOnNoteSaved = onNoteSaved;
        this.updateLastModification = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Note doInBackground(Note... noteArr) {
        Note note = noteArr[0];
        purgeRemovedAttachments(note);
        boolean zIsFuture = DateUtils.isFuture(note.getAlarm());
        if (zIsFuture) {
            note.setReminderFired((Boolean) false);
        }
        Note noteUpdateNote = DbHelper.getInstance().updateNote(note, this.updateLastModification);
        if (zIsFuture) {
            ReminderHelper.addReminder(this.context, noteUpdateNote);
        }
        return noteUpdateNote;
    }

    private void purgeRemovedAttachments(Note note) {
        List<Attachment> attachmentsListOld = note.getAttachmentsListOld();
        for (Attachment fixedAttachmentInstance : note.getAttachmentsList()) {
            if (fixedAttachmentInstance.getId() != null) {
                if (attachmentsListOld.indexOf(fixedAttachmentInstance) == -1) {
                    fixedAttachmentInstance = getFixedAttachmentInstance(attachmentsListOld, fixedAttachmentInstance);
                }
                attachmentsListOld.remove(fixedAttachmentInstance);
            }
        }
        for (Attachment attachment : attachmentsListOld) {
            StorageHelper.delete(this.context, attachment.getUri().getPath());
            LogDelegate.d("Removed attachment " + attachment.getUri());
        }
    }

    private Attachment getFixedAttachmentInstance(List<Attachment> list, Attachment attachment) {
        for (Attachment attachment2 : list) {
            if (attachment2.getId().equals(attachment.getId())) {
                return attachment2;
            }
        }
        return attachment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Note note) {
        super.onPostExecute((SaveNoteTask) note);
        OnNoteSaved onNoteSaved = this.mOnNoteSaved;
        if (onNoteSaved != null) {
            onNoteSaved.onNoteSaved(note);
        }
    }
}
