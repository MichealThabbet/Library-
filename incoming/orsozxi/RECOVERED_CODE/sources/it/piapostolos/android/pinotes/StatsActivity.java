package it.piapostolos.android.pinotes;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.models.Stats;

/* loaded from: classes3.dex */
public class StatsActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_stats);
        initData();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [it.piapostolos.android.pinotes.StatsActivity$1StatsTask] */
    private void initData() {
        new AsyncTask<Void, Void, Stats>() { // from class: it.piapostolos.android.pinotes.StatsActivity.1StatsTask
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public Stats doInBackground(Void... voidArr) {
                return DbHelper.getInstance().getStats();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(Stats stats) {
                StatsActivity.this.populateViews(stats);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void populateViews(Stats stats) {
        ((TextView) findViewById(R.id.stat_notes_total)).setText(String.valueOf(stats.getNotesTotalNumber()));
        ((TextView) findViewById(R.id.stat_notes_active)).setText(String.valueOf(stats.getNotesActive()));
        ((TextView) findViewById(R.id.stat_notes_archived)).setText(String.valueOf(stats.getNotesArchived()));
        ((TextView) findViewById(R.id.stat_notes_trashed)).setText(String.valueOf(stats.getNotesTrashed()));
        ((TextView) findViewById(R.id.stat_reminders)).setText(String.valueOf(stats.getReminders()));
        ((TextView) findViewById(R.id.stat_reminders_futures)).setText(String.valueOf(stats.getRemindersFutures()));
        ((TextView) findViewById(R.id.stat_checklists)).setText(String.valueOf(stats.getNotesChecklist()));
        ((TextView) findViewById(R.id.stat_masked)).setText(String.valueOf(stats.getNotesMasked()));
        ((TextView) findViewById(R.id.stat_categories)).setText(String.valueOf(stats.getCategories()));
        ((TextView) findViewById(R.id.stat_tags)).setText(String.valueOf(stats.getTags()));
        ((TextView) findViewById(R.id.stat_attachments)).setText(String.valueOf(stats.getAttachments()));
        ((TextView) findViewById(R.id.stat_attachments_images)).setText(String.valueOf(stats.getImages()));
        ((TextView) findViewById(R.id.stat_attachments_videos)).setText(String.valueOf(stats.getVideos()));
        ((TextView) findViewById(R.id.stat_attachments_audiorecordings)).setText(String.valueOf(stats.getAudioRecordings()));
        ((TextView) findViewById(R.id.stat_attachments_sketches)).setText(String.valueOf(stats.getSketches()));
        ((TextView) findViewById(R.id.stat_attachments_files)).setText(String.valueOf(stats.getFiles()));
        ((TextView) findViewById(R.id.stat_locations)).setText(String.valueOf(stats.getLocation()));
        ((TextView) findViewById(R.id.stat_words)).setText(String.valueOf(stats.getWords()));
        ((TextView) findViewById(R.id.stat_words_max)).setText(String.valueOf(stats.getWordsMax()));
        ((TextView) findViewById(R.id.stat_words_avg)).setText(String.valueOf(stats.getWordsAvg()));
        ((TextView) findViewById(R.id.stat_chars)).setText(String.valueOf(stats.getChars()));
        ((TextView) findViewById(R.id.stat_chars_max)).setText(String.valueOf(stats.getCharsMax()));
        ((TextView) findViewById(R.id.stat_chars_avg)).setText(String.valueOf(stats.getCharsAvg()));
    }
}
