package it.orsozoxi.android.orsonotes.async.bus;

import it.orsozoxi.android.orsonotes.helpers.LogDelegate;

/* loaded from: classes3.dex */
public class NotesMergeEvent {
    public final boolean keepMergedNotes;

    public NotesMergeEvent(boolean z) {
        LogDelegate.d(getClass().getName());
        this.keepMergedNotes = z;
    }
}
