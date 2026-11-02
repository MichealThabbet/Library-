package it.piapostolos.android.pinotes.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Spanned;
import android.widget.TextView;
import it.piapostolos.android.pinotes.models.Note;
import it.piapostolos.android.pinotes.utils.TextHelper;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class TextWorkerTask extends AsyncTask<Note, Void, Spanned[]> {
    private TextView contentTextView;
    private boolean expandedView;
    private Activity mActivity;
    private final WeakReference<Activity> mActivityWeakReference;
    private TextView titleTextView;

    public TextWorkerTask(Activity activity, TextView textView, TextView textView2, boolean z) {
        this.mActivityWeakReference = new WeakReference<>(activity);
        this.mActivity = activity;
        this.titleTextView = textView;
        this.contentTextView = textView2;
        this.expandedView = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Spanned[] doInBackground(Note... noteArr) {
        return TextHelper.parseTitleAndContent(this.mActivity, noteArr[0]);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Spanned[] spannedArr) {
        if (isAlive()) {
            this.titleTextView.setText(spannedArr[0]);
            if (spannedArr[1].length() > 0) {
                this.contentTextView.setText(spannedArr[1]);
                this.contentTextView.setVisibility(0);
            } else if (this.expandedView) {
                this.contentTextView.setVisibility(4);
            } else {
                this.contentTextView.setVisibility(8);
            }
        }
    }

    private boolean isAlive() {
        WeakReference<Activity> weakReference = this.mActivityWeakReference;
        return (weakReference == null || weakReference.get() == null) ? false : true;
    }
}
