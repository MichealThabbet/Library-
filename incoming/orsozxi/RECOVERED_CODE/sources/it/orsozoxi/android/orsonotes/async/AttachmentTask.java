package it.orsozoxi.android.orsonotes.async;

import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import androidx.fragment.app.Fragment;
import it.orsozoxi.android.orsonotes.OrsoNotes;
import it.orsozoxi.android.orsonotes.models.Attachment;
import it.orsozoxi.android.orsonotes.models.listeners.OnAttachingFileListener;
import it.orsozoxi.android.orsonotes.utils.StorageHelper;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class AttachmentTask extends AsyncTask<Void, Void, Attachment> {
    private String fileName;
    private final WeakReference<Fragment> mFragmentWeakReference;
    private OnAttachingFileListener mOnAttachingFileListener;
    private Uri uri;

    public AttachmentTask(Fragment fragment, Uri uri, OnAttachingFileListener onAttachingFileListener) {
        this(fragment, uri, null, onAttachingFileListener);
    }

    public AttachmentTask(Fragment fragment, Uri uri, String str, OnAttachingFileListener onAttachingFileListener) {
        this.mFragmentWeakReference = new WeakReference<>(fragment);
        this.uri = uri;
        this.fileName = TextUtils.isEmpty(str) ? "" : str;
        this.mOnAttachingFileListener = onAttachingFileListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Attachment doInBackground(Void... voidArr) {
        Attachment attachmentCreateAttachmentFromUri = StorageHelper.createAttachmentFromUri(OrsoNotes.getAppContext(), this.uri);
        attachmentCreateAttachmentFromUri.setName(this.fileName);
        return attachmentCreateAttachmentFromUri;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Attachment attachment) {
        if (!isAlive()) {
            if (attachment != null) {
                StorageHelper.delete(OrsoNotes.getAppContext(), attachment.getUri().getPath());
            }
        } else if (attachment != null) {
            this.mOnAttachingFileListener.onAttachingFileFinished(attachment);
        } else {
            this.mOnAttachingFileListener.onAttachingFileErrorOccurred(null);
        }
    }

    private boolean isAlive() {
        WeakReference<Fragment> weakReference = this.mFragmentWeakReference;
        return (weakReference == null || weakReference.get() == null || !this.mFragmentWeakReference.get().isAdded() || this.mFragmentWeakReference.get().getActivity() == null || this.mFragmentWeakReference.get().getActivity().isFinishing()) ? false : true;
    }
}
