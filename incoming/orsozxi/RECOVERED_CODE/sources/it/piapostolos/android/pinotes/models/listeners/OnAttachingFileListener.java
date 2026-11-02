package it.piapostolos.android.pinotes.models.listeners;

import it.piapostolos.android.pinotes.models.Attachment;

/* loaded from: classes3.dex */
public interface OnAttachingFileListener {
    void onAttachingFileErrorOccurred(Attachment attachment);

    void onAttachingFileFinished(Attachment attachment);
}
