package it.orsozoxi.android.orsonotes.async.bus;

import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.models.Note;
import java.util.List;

/* loaded from: classes3.dex */
public class NotesUpdatedEvent {
    public List<Note> notes;

    public NotesUpdatedEvent(List<Note> list) {
        LogDelegate.d(getClass().getName());
        this.notes = list;
    }
}
