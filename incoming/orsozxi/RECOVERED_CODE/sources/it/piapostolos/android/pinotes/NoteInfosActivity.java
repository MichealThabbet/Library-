package it.piapostolos.android.pinotes;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.piapostolos.android.pinotes.R2;
import it.piapostolos.android.pinotes.helpers.NotesHelper;
import it.piapostolos.android.pinotes.models.Note;
import it.piapostolos.android.pinotes.models.StatsSingleNote;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.util.Objects;

/* loaded from: classes3.dex */
public class NoteInfosActivity extends Activity {

    @BindView(R2.id.note_infos_audiorecordings)
    TextView audioRecordings;

    @BindView(R2.id.note_infos_category)
    TextView category;

    @BindView(R2.id.note_infos_chars)
    TextView chars;

    @BindView(R2.id.note_infos_completed_checklist_items)
    TextView checklistCompletedItems;

    @BindView(R2.id.note_infos_checklist_items)
    TextView checklistItems;

    @BindView(R2.id.note_infos_files)
    TextView files;

    @BindView(R2.id.note_infos_images)
    TextView images;

    @BindView(R2.id.note_infos_sketches)
    TextView sketches;

    @BindView(R2.id.note_infos_tags)
    TextView tags;

    @BindView(R2.id.note_infos_videos)
    TextView videos;

    @BindView(R2.id.note_infos_words)
    TextView words;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_note_infos);
        ButterKnife.bind(this);
        populateViews((Note) ((Bundle) Objects.requireNonNull(getIntent().getExtras())).getParcelable(ConstantsBase.INTENT_NOTE));
    }

    private void populateViews(Note note) {
        StatsSingleNote noteInfos = NotesHelper.getNoteInfos(note);
        populateView(this.category, noteInfos.getCategoryName());
        populateView(this.tags, noteInfos.getTags());
        populateView(this.chars, noteInfos.getChars());
        populateView(this.words, noteInfos.getWords());
        populateView(this.checklistItems, noteInfos.getChecklistItemsNumber());
        populateView(this.checklistCompletedItems, noteInfos.getChecklistCompletedItemsNumber());
        populateView(this.images, noteInfos.getImages());
        populateView(this.videos, noteInfos.getVideos());
        populateView(this.audioRecordings, noteInfos.getAudioRecordings());
        populateView(this.sketches, noteInfos.getSketches());
        populateView(this.files, noteInfos.getFiles());
    }

    private void populateView(TextView textView, int i) {
        populateView(textView, i > 0 ? String.valueOf(i) : "");
    }

    private void populateView(TextView textView, String str) {
        if (!TextUtils.isEmpty(str)) {
            textView.setText(str);
        } else {
            ((View) textView.getParent()).setVisibility(8);
        }
    }
}
