package it.piapostolos.android.pinotes.async;

import android.content.Context;
import android.os.AsyncTask;
import de.greenrobot.event.EventBus;
import it.piapostolos.android.pinotes.BaseActivity;
import it.piapostolos.android.pinotes.async.bus.NotesUpdatedEvent;

/* loaded from: classes3.dex */
public class UpdateWidgetsTask extends AsyncTask<Void, Void, Void> {
    private Context context;

    public UpdateWidgetsTask(Context context) {
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Void doInBackground(Void... voidArr) {
        new WidgetUpdateSubscriber();
        return null;
    }

    class WidgetUpdateSubscriber {
        WidgetUpdateSubscriber() {
            EventBus.getDefault().register(this);
        }

        public void onEvent(NotesUpdatedEvent notesUpdatedEvent) {
            BaseActivity.notifyAppWidgets(UpdateWidgetsTask.this.context);
            EventBus.getDefault().unregister(this);
        }
    }
}
