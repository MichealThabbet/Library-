package it.piapostolos.android.pinotes.async.bus;

import it.piapostolos.android.pinotes.helpers.LogDelegate;

/* loaded from: classes3.dex */
public class NotesMergeEvent {
    public final boolean keepMergedNotes;

    public NotesMergeEvent(boolean z) {
        LogDelegate.d(getClass().getName());
        this.keepMergedNotes = z;
    }
}
