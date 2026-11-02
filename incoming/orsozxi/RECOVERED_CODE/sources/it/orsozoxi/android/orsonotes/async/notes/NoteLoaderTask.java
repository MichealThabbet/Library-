package it.orsozoxi.android.orsonotes.async.notes;

import android.os.AsyncTask;
import de.greenrobot.event.EventBus;
import it.orsozoxi.android.orsonotes.async.bus.NotesLoadedEvent;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.exceptions.NotesLoadingException;
import it.orsozoxi.android.orsonotes.models.Note;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class NoteLoaderTask extends AsyncTask<Object, Void, List<Note>> {
    private static final String ERROR_RETRIEVING_NOTES = "Error retrieving notes";
    private static NoteLoaderTask instance;

    private NoteLoaderTask() {
    }

    public static NoteLoaderTask getInstance() {
        NoteLoaderTask noteLoaderTask = instance;
        if (noteLoaderTask != null) {
            if (noteLoaderTask.getStatus() == AsyncTask.Status.RUNNING && !instance.isCancelled()) {
                instance.cancel(true);
            } else if (instance.getStatus() == AsyncTask.Status.PENDING) {
                return instance;
            }
        }
        NoteLoaderTask noteLoaderTask2 = new NoteLoaderTask();
        instance = noteLoaderTask2;
        return noteLoaderTask2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public List<Note> doInBackground(Object... objArr) {
        String string = objArr[0].toString();
        DbHelper dbHelper = DbHelper.getInstance();
        if (objArr.length < 2 || objArr[1] == null) {
            try {
                return (List) dbHelper.getClass().getDeclaredMethod(string, new Class[0]).invoke(dbHelper, new Object[0]);
            } catch (IllegalAccessException e) {
                e = e;
                throw new NotesLoadingException(ERROR_RETRIEVING_NOTES, e);
            } catch (NoSuchMethodException unused) {
                return new ArrayList();
            } catch (InvocationTargetException e2) {
                e = e2;
                throw new NotesLoadingException(ERROR_RETRIEVING_NOTES, e);
            }
        }
        Object obj = objArr[1];
        Class<?>[] clsArr = {obj.getClass()};
        try {
            return (List) dbHelper.getClass().getDeclaredMethod(string, clsArr).invoke(dbHelper, clsArr[0].cast(obj));
        } catch (Exception e3) {
            throw new NotesLoadingException(ERROR_RETRIEVING_NOTES, e3);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(List<Note> list) {
        super.onPostExecute((NoteLoaderTask) list);
        EventBus.getDefault().post(new NotesLoadedEvent(list));
    }
}
