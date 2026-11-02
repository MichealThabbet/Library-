package it.feio.android.omninotes.commons.api;

import it.feio.android.omninotes.commons.listeners.DataRetrievedListener;
import it.feio.android.omninotes.commons.models.BaseAttachment;
import it.feio.android.omninotes.commons.models.BaseCategory;
import it.feio.android.omninotes.commons.models.BaseNote;

/* loaded from: classes2.dex */
public interface ONCloudApi {
    public static final String ATTACHMENTS_FOLDER = "attachments";
    public static final String CATEGORIES_FOLDER = "categories";
    public static final String NOTES_FOLDER = "notes";

    void delAttachment(BaseAttachment baseAttachment);

    void delCategory(BaseCategory baseCategory);

    void delNote(BaseNote baseNote);

    BaseAttachment getAttachment(String str);

    void getAttachments(DataRetrievedListener dataRetrievedListener);

    void getCategories(DataRetrievedListener dataRetrievedListener);

    BaseCategory getCategory(String str);

    BaseNote getNote(String str);

    void getNotes(DataRetrievedListener dataRetrievedListener);

    void purge();

    void putAttachment(BaseAttachment baseAttachment);

    void putCategory(BaseCategory baseCategory);

    void putNote(BaseNote baseNote);
}
