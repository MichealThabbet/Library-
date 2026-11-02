package it.orsozoxi.android.orsonotes.models.listeners;

import it.orsozoxi.android.orsonotes.models.Attachment;

/* loaded from: classes3.dex */
public interface OnAttachingFileListener {
    void onAttachingFileErrorOccurred(Attachment attachment);

    void onAttachingFileFinished(Attachment attachment);
}
