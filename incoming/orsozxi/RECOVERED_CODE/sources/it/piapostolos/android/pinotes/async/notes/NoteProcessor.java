package it.piapostolos.android.pinotes.async.notes;

import android.os.AsyncTask;
import de.greenrobot.event.EventBus;
import it.piapostolos.android.pinotes.async.bus.NotesUpdatedEvent;
import it.piapostolos.android.pinotes.models.Note;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class NoteProcessor {
    List<Note> notes;

    protected abstract void processNote(Note note);

    protected NoteProcessor(List<Note> list) {
        this.notes = new ArrayList(list);
    }

    public void process() {
        new NotesProcessorTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.notes);
    }

    class NotesProcessorTask extends AsyncTask<List<Note>, Void, List<Note>> {
        NotesProcessorTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public List<Note> doInBackground(List<Note>... listArr) {
            List<Note> list = listArr[0];
            Iterator<Note> it2 = list.iterator();
            while (it2.hasNext()) {
                NoteProcessor.this.processNote(it2.next());
            }
            return list;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(List<Note> list) {
            NoteProcessor.this.afterProcess(list);
        }
    }

    protected void afterProcess(List<Note> list) {
        EventBus.getDefault().post(new NotesUpdatedEvent(list));
    }
}
