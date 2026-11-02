package it.piapostolos.android.pinotes.async.bus;

import it.piapostolos.android.pinotes.helpers.LogDelegate;
import it.piapostolos.android.pinotes.models.Note;
import java.util.List;

/* loaded from: classes3.dex */
public class NotesUpdatedEvent {
    public List<Note> notes;

    public NotesUpdatedEvent(List<Note> list) {
        LogDelegate.d(getClass().getName());
        this.notes = list;
    }
}
