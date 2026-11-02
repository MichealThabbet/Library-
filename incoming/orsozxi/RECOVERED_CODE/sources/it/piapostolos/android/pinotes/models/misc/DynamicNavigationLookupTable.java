package it.piapostolos.android.pinotes.models.misc;

import android.content.res.Resources;
import de.greenrobot.event.EventBus;
import it.piapostolos.android.pinotes.async.bus.DynamicNavigationReadyEvent;
import it.piapostolos.android.pinotes.async.bus.NotesUpdatedEvent;
import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import it.piapostolos.android.pinotes.models.Note;
import java.util.List;

/* loaded from: classes3.dex */
public class DynamicNavigationLookupTable {
    private static DynamicNavigationLookupTable instance;
    int archived;
    int reminders;
    int trashed;
    int uncategorized;

    private DynamicNavigationLookupTable() {
        EventBus.getDefault().register(this);
        update();
    }

    public static DynamicNavigationLookupTable getInstance() {
        if (instance == null) {
            instance = new DynamicNavigationLookupTable();
        }
        return instance;
    }

    public void update() {
        new Runnable() { // from class: it.piapostolos.android.pinotes.models.misc.-$$Lambda$DynamicNavigationLookupTable$wQAqEk59CbEAC2hPDBzqfHfEE7Y
            @Override // java.lang.Runnable
            public final void run() throws Resources.NotFoundException {
                this.f$0.lambda$update$0$DynamicNavigationLookupTable();
            }
        }.run();
    }

    public /* synthetic */ void lambda$update$0$DynamicNavigationLookupTable() throws Resources.NotFoundException {
        this.reminders = 0;
        this.uncategorized = 0;
        this.trashed = 0;
        this.archived = 0;
        List<Note> allNotes = DbHelper.getInstance().getAllNotes(false);
        for (int i = 0; i < allNotes.size(); i++) {
            if (allNotes.get(i).isTrashed().booleanValue()) {
                this.trashed++;
            } else if (allNotes.get(i).isArchived().booleanValue()) {
                this.archived++;
            } else if (allNotes.get(i).getAlarm() != null) {
                this.reminders++;
            }
            if (allNotes.get(i).getCategory() == null || allNotes.get(i).getCategory().getId().equals(0L)) {
                this.uncategorized++;
            }
        }
        EventBus.getDefault().post(new DynamicNavigationReadyEvent());
        LogDelegate.d("Dynamic menu finished counting items");
    }

    public void onEventAsync(NotesUpdatedEvent notesUpdatedEvent) {
        update();
    }

    public int getArchived() {
        return this.archived;
    }

    public int getTrashed() {
        return this.trashed;
    }

    public int getReminders() {
        return this.reminders;
    }

    public int getUncategorized() {
        return this.uncategorized;
    }
}
