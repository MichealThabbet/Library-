package it.piapostolos.android.pinotes.models.adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Spanned;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.nhaarman.listviewanimations.util.Insertable;
import it.piapostolos.android.pinotes.R;
import it.piapostolos.android.pinotes.async.TextWorkerTask;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import it.piapostolos.android.pinotes.models.Note;
import it.piapostolos.android.pinotes.models.holders.NoteViewHolder;
import it.piapostolos.android.pinotes.utils.BitmapHelper;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import it.piapostolos.android.pinotes.utils.Navigation;
import it.piapostolos.android.pinotes.utils.TextHelper;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes3.dex */
public class NoteAdapter extends ArrayAdapter<Note> implements Insertable {
    private int closestNotePosition;
    private long closestNoteReminder;
    private boolean expandedView;
    private LayoutInflater inflater;
    private int layout;
    private final Activity mActivity;
    private final int navigation;
    private List<Note> notes;
    private SparseBooleanArray selectedItems;

    public NoteAdapter(Activity activity, int i, List<Note> list) throws Resources.NotFoundException, NumberFormatException {
        super(activity, R.layout.note_layout_expanded, list);
        this.selectedItems = new SparseBooleanArray();
        this.closestNoteReminder = Long.parseLong(ConstantsBase.TIMESTAMP_UNIX_EPOCH_FAR);
        this.mActivity = activity;
        this.notes = list;
        this.layout = i;
        this.expandedView = i == R.layout.note_layout_expanded;
        this.inflater = (LayoutInflater) this.mActivity.getSystemService("layout_inflater");
        int navigation = Navigation.getNavigation();
        this.navigation = navigation;
        manageCloserNote(list, navigation);
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        NoteViewHolder noteViewHolder;
        Note note = this.notes.get(i);
        if (view == null) {
            view = this.inflater.inflate(this.layout, viewGroup, false);
            noteViewHolder = new NoteViewHolder(view);
            view.setTag(noteViewHolder);
        } else {
            noteViewHolder = (NoteViewHolder) view.getTag();
        }
        initText(note, noteViewHolder);
        initIcons(note, noteViewHolder);
        initDates(note, noteViewHolder);
        initThumbnail(note, noteViewHolder);
        manageSelectionColor(i, note, noteViewHolder);
        return view;
    }

    private void manageSelectionColor(int i, Note note, NoteViewHolder noteViewHolder) {
        if (this.selectedItems.get(i)) {
            noteViewHolder.cardLayout.setBackgroundColor(this.mActivity.getResources().getColor(R.color.list_bg_selected));
        } else {
            restoreDrawable(note, noteViewHolder.cardLayout, noteViewHolder);
        }
    }

    private void initThumbnail(Note note, NoteViewHolder noteViewHolder) {
        if (!this.expandedView || noteViewHolder.attachmentThumbnail == null) {
            return;
        }
        if ((note.isLocked().booleanValue() && !this.mActivity.getSharedPreferences("PiNotes", 4).getBoolean("settings_password_access", false)) || note.getAttachmentsList().isEmpty()) {
            noteViewHolder.attachmentThumbnail.setVisibility(8);
            return;
        }
        noteViewHolder.attachmentThumbnail.setVisibility(0);
        Glide.with(this.mActivity).load(BitmapHelper.getThumbnailUri(this.mActivity, note.getAttachmentsList().get(0))).apply(new RequestOptions().centerCrop()).transition(DrawableTransitionOptions.withCrossFade()).into(noteViewHolder.attachmentThumbnail);
    }

    public List<Note> getNotes() {
        return this.notes;
    }

    private void initDates(Note note, NoteViewHolder noteViewHolder) {
        noteViewHolder.date.setText(TextHelper.getDateText(this.mActivity, note, this.navigation));
    }

    private void initIcons(Note note, NoteViewHolder noteViewHolder) {
        noteViewHolder.archiveIcon.setVisibility(note.isArchived().booleanValue() ? 0 : 8);
        noteViewHolder.locationIcon.setVisibility((note.getLongitude() == null || note.getLongitude().doubleValue() == 0.0d) ? 8 : 0);
        noteViewHolder.alarmIcon.setVisibility(note.getAlarm() != null ? 0 : 8);
        noteViewHolder.lockedIcon.setVisibility(note.isLocked().booleanValue() ? 0 : 8);
        if (this.expandedView) {
            return;
        }
        noteViewHolder.attachmentIcon.setVisibility(note.getAttachmentsList().isEmpty() ? 8 : 0);
    }

    private void initText(Note note, NoteViewHolder noteViewHolder) {
        try {
            if (note.isChecklist().booleanValue()) {
                new TextWorkerTask(this.mActivity, noteViewHolder.title, noteViewHolder.content, this.expandedView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, note);
            } else {
                Spanned[] titleAndContent = TextHelper.parseTitleAndContent(this.mActivity, note);
                noteViewHolder.title.setText(titleAndContent[0]);
                noteViewHolder.content.setText(titleAndContent[1]);
                noteViewHolder.title.setText(titleAndContent[0]);
                if (titleAndContent[1].length() > 0) {
                    noteViewHolder.content.setText(titleAndContent[1]);
                    noteViewHolder.content.setVisibility(0);
                } else {
                    noteViewHolder.content.setVisibility(4);
                }
            }
        } catch (RejectedExecutionException e) {
            LogDelegate.w("Oversized tasks pool to load texts!", e);
        }
    }

    private void manageCloserNote(List<Note> list, int i) throws NumberFormatException {
        if (i == 2) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                long timeInMillis = Calendar.getInstance().getTimeInMillis();
                long j = Long.parseLong(list.get(i2).getAlarm());
                if (timeInMillis < j && j < this.closestNoteReminder) {
                    this.closestNotePosition = i2;
                    this.closestNoteReminder = j;
                }
            }
        }
    }

    public int getClosestNotePosition() {
        return this.closestNotePosition;
    }

    public SparseBooleanArray getSelectedItems() {
        return this.selectedItems;
    }

    public void addSelectedItem(Integer num) {
        this.selectedItems.put(num.intValue(), true);
    }

    public void removeSelectedItem(Integer num) {
        this.selectedItems.delete(num.intValue());
    }

    public void clearSelectedItems() {
        this.selectedItems.clear();
    }

    public void restoreDrawable(Note note, View view) {
        restoreDrawable(note, view, null);
    }

    public void restoreDrawable(Note note, View view, NoteViewHolder noteViewHolder) {
        int paddingBottom = view.getPaddingBottom();
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), paddingBottom);
        colorNote(note, view, noteViewHolder);
    }

    private void colorNote(Note note, View view) {
        colorNote(note, view, null);
    }

    private void colorNote(Note note, View view, NoteViewHolder noteViewHolder) {
        String string = this.mActivity.getSharedPreferences("PiNotes", 4).getString("settings_colors_app", ConstantsBase.PREF_COLORS_APP_DEFAULT);
        if (string.equals("disabled")) {
            return;
        }
        view.setBackgroundColor(Color.parseColor("#00000000"));
        if (note.getCategory() != null && note.getCategory().getColor() != null) {
            if (string.equals("complete") || string.equals("list")) {
                view.setBackgroundColor(Integer.parseInt(note.getCategory().getColor()));
                return;
            } else if (noteViewHolder != null) {
                noteViewHolder.categoryMarker.setBackgroundColor(Integer.parseInt(note.getCategory().getColor()));
                return;
            } else {
                view.findViewById(R.id.category_marker).setBackgroundColor(Integer.parseInt(note.getCategory().getColor()));
                return;
            }
        }
        view.findViewById(R.id.category_marker).setBackgroundColor(0);
    }

    public void replace(Note note, int i) {
        if (this.notes.indexOf(note) != -1) {
            this.notes.remove(i);
        } else {
            i = this.notes.size();
        }
        this.notes.add(i, note);
    }

    @Override // com.nhaarman.listviewanimations.util.Insertable
    public void add(int i, Object obj) {
        insert((Note) obj, i);
    }

    public void remove(List<Note> list) {
        Iterator<Note> it2 = list.iterator();
        while (it2.hasNext()) {
            remove((NoteAdapter) it2.next());
        }
    }
}
