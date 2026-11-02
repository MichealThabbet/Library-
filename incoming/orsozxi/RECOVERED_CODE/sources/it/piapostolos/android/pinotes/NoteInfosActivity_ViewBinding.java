package it.piapostolos.android.pinotes;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;

/* loaded from: classes3.dex */
public class NoteInfosActivity_ViewBinding implements Unbinder {
    private NoteInfosActivity target;

    public NoteInfosActivity_ViewBinding(NoteInfosActivity noteInfosActivity) {
        this(noteInfosActivity, noteInfosActivity.getWindow().getDecorView());
    }

    public NoteInfosActivity_ViewBinding(NoteInfosActivity noteInfosActivity, View view) {
        this.target = noteInfosActivity;
        noteInfosActivity.category = (TextView) Utils.findRequiredViewAsType(view, R.id.note_infos_category, "field 'category'", TextView.class);
        noteInfosActivity.tags = (TextView) Utils.findRequiredViewAsType(view, R.id.note_infos_tags, "field 'tags'", TextView.class);
        noteInfosActivity.chars = (TextView) Utils.findRequiredViewAsType(view, R.id.note_infos_chars, "field 'chars'", TextView.class);
        noteInfosActivity.words = (TextView) Utils.findRequiredViewAsType(view, R.id.note_infos_words, "field 'words'", TextView.class);
        noteInfosActivity.checklistItems = (TextView) Utils.findRequiredViewAsType(view, R.id.note_infos_checklist_items, "field 'checklistItems'", TextView.class);
        noteInfosActivity.checklistCompletedItems = (TextView) Utils.findRequiredViewAsType(view, R.id.note_infos_completed_checklist_items, "field 'checklistCompletedItems'", TextView.class);
        noteInfosActivity.images = (TextView) Utils.findRequiredViewAsType(view, R.id.note_infos_images, "field 'images'", TextView.class);
        noteInfosActivity.videos = (TextView) Utils.findRequiredViewAsType(view, R.id.note_infos_videos, "field 'videos'", TextView.class);
        noteInfosActivity.audioRecordings = (TextView) Utils.findRequiredViewAsType(view, R.id.note_infos_audiorecordings, "field 'audioRecordings'", TextView.class);
        noteInfosActivity.sketches = (TextView) Utils.findRequiredViewAsType(view, R.id.note_infos_sketches, "field 'sketches'", TextView.class);
        noteInfosActivity.files = (TextView) Utils.findRequiredViewAsType(view, R.id.note_infos_files, "field 'files'", TextView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        NoteInfosActivity noteInfosActivity = this.target;
        if (noteInfosActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        noteInfosActivity.category = null;
        noteInfosActivity.tags = null;
        noteInfosActivity.chars = null;
        noteInfosActivity.words = null;
        noteInfosActivity.checklistItems = null;
        noteInfosActivity.checklistCompletedItems = null;
        noteInfosActivity.images = null;
        noteInfosActivity.videos = null;
        noteInfosActivity.audioRecordings = null;
        noteInfosActivity.sketches = null;
        noteInfosActivity.files = null;
    }
}
