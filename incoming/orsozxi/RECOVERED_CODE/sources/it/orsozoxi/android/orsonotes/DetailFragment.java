package it.orsozoxi.android.orsonotes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;
import de.greenrobot.event.EventBus;
import de.keyboardsurfer.android.widget.crouton.Style;
import it.feio.android.checklistview.exceptions.ViewNotSupportedException;
import it.feio.android.checklistview.interfaces.CheckListChangedListener;
import it.feio.android.checklistview.models.CheckListView;
import it.feio.android.checklistview.models.CheckListViewItem;
import it.feio.android.checklistview.models.ChecklistManager;
import it.feio.android.pixlui.links.TextLinkClickListener;
import it.orsozoxi.android.orsonotes.R2;
import it.orsozoxi.android.orsonotes.async.AttachmentTask;
import it.orsozoxi.android.orsonotes.async.bus.NotesUpdatedEvent;
import it.orsozoxi.android.orsonotes.async.bus.PushbulletReplyEvent;
import it.orsozoxi.android.orsonotes.async.bus.SwitchFragmentEvent;
import it.orsozoxi.android.orsonotes.async.notes.NoteProcessorDelete;
import it.orsozoxi.android.orsonotes.async.notes.SaveNoteTask;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.helpers.AttachmentsHelper;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.helpers.PermissionsHelper;
import it.orsozoxi.android.orsonotes.helpers.date.DateHelper;
import it.orsozoxi.android.orsonotes.helpers.date.RecurrenceHelper;
import it.orsozoxi.android.orsonotes.models.Attachment;
import it.orsozoxi.android.orsonotes.models.Category;
import it.orsozoxi.android.orsonotes.models.Note;
import it.orsozoxi.android.orsonotes.models.ONStyle;
import it.orsozoxi.android.orsonotes.models.PasswordValidator;
import it.orsozoxi.android.orsonotes.models.Tag;
import it.orsozoxi.android.orsonotes.models.adapters.AttachmentAdapter;
import it.orsozoxi.android.orsonotes.models.adapters.CategoryRecyclerViewAdapter;
import it.orsozoxi.android.orsonotes.models.adapters.PlacesAutoCompleteAdapter;
import it.orsozoxi.android.orsonotes.models.listeners.OnAttachingFileListener;
import it.orsozoxi.android.orsonotes.models.listeners.OnGeoUtilResultListener;
import it.orsozoxi.android.orsonotes.models.listeners.OnNoteSaved;
import it.orsozoxi.android.orsonotes.models.listeners.OnPermissionRequestedListener;
import it.orsozoxi.android.orsonotes.models.listeners.OnReminderPickedListener;
import it.orsozoxi.android.orsonotes.models.listeners.RecyclerViewItemClickSupport;
import it.orsozoxi.android.orsonotes.models.views.ExpandableHeightGridView;
import it.orsozoxi.android.orsonotes.utils.AlphaManager;
import it.orsozoxi.android.orsonotes.utils.ConnectionManager;
import it.orsozoxi.android.orsonotes.utils.ConstantsBase;
import it.orsozoxi.android.orsonotes.utils.Display;
import it.orsozoxi.android.orsonotes.utils.FileHelper;
import it.orsozoxi.android.orsonotes.utils.FileProviderHelper;
import it.orsozoxi.android.orsonotes.utils.IntentChecker;
import it.orsozoxi.android.orsonotes.utils.KeyboardUtils;
import it.orsozoxi.android.orsonotes.utils.PasswordHelper;
import it.orsozoxi.android.orsonotes.utils.ReminderHelper;
import it.orsozoxi.android.orsonotes.utils.ShortcutHelper;
import it.orsozoxi.android.orsonotes.utils.StorageHelper;
import it.orsozoxi.android.orsonotes.utils.TagsHelper;
import it.orsozoxi.android.orsonotes.utils.TextHelper;
import it.orsozoxi.android.orsonotes.utils.date.DateUtils;
import it.orsozoxi.android.orsonotes.utils.date.ReminderPickers;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;
import rx.Observable;
import rx.functions.Func1;

/* loaded from: classes3.dex */
public class DetailFragment extends BaseFragment implements OnReminderPickedListener, View.OnTouchListener, OnAttachingFileListener, TextWatcher, CheckListChangedListener, OnNoteSaved, OnGeoUtilResultListener {
    private static final int CATEGORY = 5;
    private static final int DETAIL = 6;
    private static final int FILES = 7;
    private static final int SET_PASSWORD = 3;
    private static final int SKETCH = 4;
    private boolean activityPausing;
    private MaterialDialog attachmentDialog;
    private Uri attachmentUri;

    @BindView(R2.id.detail_attachments_above)
    ViewStub attachmentsAbove;

    @BindView(R2.id.detail_attachments_below)
    ViewStub attachmentsBelow;
    private long audioRecordingTime;
    private long audioRecordingTimeStart;

    @BindView(R2.id.detail_content)
    EditText content;
    private int contentCursorPosition;

    @BindView(R2.id.creation)
    TextView creationTextView;

    @BindView(R2.id.datetime)
    TextView datetime;

    @BindView(R2.id.detail_wrapper)
    ViewManager detailWrapperView;
    private String exitMessage;

    @BindView(R2.id.last_modification)
    TextView lastModificationTextView;

    @BindView(R2.id.location)
    TextView locationTextView;
    private AttachmentAdapter mAttachmentAdapter;
    private ChecklistManager mChecklistManager;
    private DetailFragment mFragment;

    @BindView(R2.id.gridview)
    ExpandableHeightGridView mGridView;
    private ArrayList<String> mergedNotesIds;
    private Note note;
    private Note noteOriginal;
    private Note noteTmp;
    private NotesMainActivity notesMainActivity;
    private boolean orientationChanged;
    private SharedPreferences prefs;
    private String recordName;
    private Bitmap recordingBitmap;

    @BindView(R2.id.reminder_icon)
    ImageView reminderIcon;

    @BindView(R2.id.reminder_layout)
    LinearLayout reminderLayout;

    @BindView(R2.id.detail_root)
    ViewGroup root;

    @BindView(R2.id.content_wrapper)
    ScrollView scrollView;
    private Attachment sketchEdited;

    @BindView(R2.id.snackbar_placeholder)
    View snackBarPlaceholder;
    private int startSwipeX;
    private boolean swiping;

    @BindView(R2.id.tag_marker)
    View tagMarkerView;

    @BindView(R2.id.detail_timestamps)
    View timestampsView;

    @BindView(R2.id.detail_title)
    EditText title;

    @BindView(R2.id.detail_tile_card)
    View titleCardView;

    @BindView(R2.id.title_wrapper)
    View titleWrapperView;
    private View toggleChecklistView;
    boolean goBack = false;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private boolean isRecording = false;
    private View isPlayingView = null;
    private Style exitCroutonStyle = ONStyle.CONFIRM;
    private boolean afterSavedReturnsToList = true;
    private boolean showKeyboard = false;
    private int contentLineCounter = 1;
    TextLinkClickListener textLinkClickListener = new AnonymousClass1();

    private void getLocation(OnGeoUtilResultListener onGeoUtilResultListener) {
    }

    static /* synthetic */ boolean lambda$initViewLocation$6(View view) {
        return true;
    }

    static /* synthetic */ boolean lambda$initViewTitle$11(View view, DragEvent dragEvent) {
        return true;
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    /* renamed from: it.orsozoxi.android.orsonotes.DetailFragment$1, reason: invalid class name */
    class AnonymousClass1 implements TextLinkClickListener {
        AnonymousClass1() {
        }

        @Override // it.feio.android.pixlui.links.TextLinkClickListener
        public void onTextLinkClick(View view, final String str, final String str2) {
            new MaterialDialog.Builder(DetailFragment.this.notesMainActivity).content(str).negativeColorRes(R.color.colorPrimary).positiveText(R.string.open).negativeText(R.string.copy).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$1$jaGVnXEfARLv04W5g_NU_jnKzNc
                @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
                public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    this.f$0.lambda$onTextLinkClick$0$DetailFragment$1(str2, materialDialog, dialogAction);
                }
            }).onNegative(new MaterialDialog.SingleButtonCallback() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$1$koeHJL4elz1W7tbmkaUAIO_KOXo
                @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
                public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    this.f$0.lambda$onTextLinkClick$1$DetailFragment$1(str, materialDialog, dialogAction);
                }
            }).build().show();
            View view2 = DetailFragment.this.noteTmp.isChecklist().booleanValue() ? DetailFragment.this.toggleChecklistView : DetailFragment.this.content;
            view2.clearFocus();
            KeyboardUtils.hideKeyboard(view2);
            new Handler().post(new Runnable() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$1$XCwSCo_4FkTOgmtCCpReXlnnzuc
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onTextLinkClick$2$DetailFragment$1();
                }
            });
        }

        public /* synthetic */ void lambda$onTextLinkClick$0$DetailFragment$1(String str, MaterialDialog materialDialog, DialogAction dialogAction) {
            boolean z;
            Intent intent;
            Intent intent2 = null;
            try {
                intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            } catch (NullPointerException unused) {
            }
            try {
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                z = false;
            } catch (NullPointerException unused2) {
                intent2 = intent;
                z = true;
                intent = intent2;
                if (intent != null) {
                }
                DetailFragment.this.notesMainActivity.showMessage(R.string.no_application_can_perform_this_action, ONStyle.ALERT);
            }
            if (intent != null || z || !IntentChecker.isAvailable(DetailFragment.this.notesMainActivity, intent, new String[]{"android.hardware.camera"})) {
                DetailFragment.this.notesMainActivity.showMessage(R.string.no_application_can_perform_this_action, ONStyle.ALERT);
            } else {
                DetailFragment.this.startActivity(intent);
            }
        }

        public /* synthetic */ void lambda$onTextLinkClick$1$DetailFragment$1(String str, MaterialDialog materialDialog, DialogAction dialogAction) {
            ((ClipboardManager) DetailFragment.this.notesMainActivity.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("text label", str));
        }

        public /* synthetic */ void lambda$onTextLinkClick$2$DetailFragment$1() {
            KeyboardUtils.hideKeyboard(DetailFragment.this.noteTmp.isChecklist().booleanValue() ? DetailFragment.this.toggleChecklistView : DetailFragment.this.content);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mFragment = this;
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().post(new SwitchFragmentEvent(SwitchFragmentEvent.Direction.CHILDREN));
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.activityPausing = false;
        EventBus.getDefault().register(this);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) throws NoSuchMethodException, SecurityException {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_detail, viewGroup, false);
        ButterKnife.bind(this, viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) throws NumberFormatException {
        super.onActivityCreated(bundle);
        NotesMainActivity notesMainActivity = (NotesMainActivity) getActivity();
        this.notesMainActivity = notesMainActivity;
        this.prefs = notesMainActivity.prefs;
        this.notesMainActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.notesMainActivity.getToolbar().setNavigationOnClickListener(new View.OnClickListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$-JM5B16S8ykE7d4BTXqIkxU87Hs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onActivityCreated$0$DetailFragment(view);
            }
        });
        if (NavigationDrawerFragment.isDoublePanelActive()) {
            this.notesMainActivity.getDrawerLayout().setDrawerLockMode(2);
        } else {
            this.notesMainActivity.getDrawerLayout().setDrawerLockMode(1);
        }
        if (bundle != null) {
            this.noteTmp = (Note) bundle.getParcelable("noteTmp");
            this.note = (Note) bundle.getParcelable(ConstantsBase.INTENT_NOTE);
            this.noteOriginal = (Note) bundle.getParcelable("noteOriginal");
            this.attachmentUri = (Uri) bundle.getParcelable("attachmentUri");
            this.orientationChanged = bundle.getBoolean("orientationChanged");
        }
        if (this.notesMainActivity.sketchUri != null) {
            addAttachment(new Attachment(this.notesMainActivity.sketchUri, ConstantsBase.MIME_TYPE_SKETCH));
            this.notesMainActivity.sketchUri = null;
            if (this.sketchEdited != null) {
                this.noteTmp.getAttachmentsList().remove(this.sketchEdited);
                this.sketchEdited = null;
            }
        }
        init();
        setHasOptionsMenu(true);
        setRetainInstance(false);
    }

    public /* synthetic */ void lambda$onActivityCreated$0$DetailFragment(View view) {
        navigateUp();
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        Note note = this.noteTmp;
        if (note != null) {
            note.setTitle(getNoteTitle());
            this.noteTmp.setContent(getNoteContent());
            bundle.putParcelable("noteTmp", this.noteTmp);
            bundle.putParcelable(ConstantsBase.INTENT_NOTE, this.note);
            bundle.putParcelable("noteOriginal", this.noteOriginal);
            bundle.putParcelable("attachmentUri", this.attachmentUri);
            bundle.putBoolean("orientationChanged", this.orientationChanged);
        }
        super.onSaveInstanceState(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        this.activityPausing = true;
        if (!this.goBack) {
            saveNote(this);
        }
        View view = this.toggleChecklistView;
        if (view != null) {
            KeyboardUtils.hideKeyboard(view);
            this.content.clearFocus();
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (getResources().getConfiguration().orientation != configuration.orientation) {
            this.orientationChanged = true;
        }
    }

    private void init() throws NumberFormatException {
        handleIntents();
        if (this.noteOriginal == null) {
            this.noteOriginal = (Note) getArguments().getParcelable(ConstantsBase.INTENT_NOTE);
        }
        if (this.note == null) {
            this.note = new Note(this.noteOriginal);
        }
        if (this.noteTmp == null) {
            this.noteTmp = new Note(this.note);
        }
        if (this.noteTmp.isLocked().booleanValue() && !this.noteTmp.isPasswordChecked()) {
            checkNoteLock(this.noteTmp);
        } else {
            initViews();
        }
    }

    private void checkNoteLock(Note note) throws NumberFormatException {
        if (note.isLocked().booleanValue() && this.prefs.getString(ConstantsBase.PREF_PASSWORD, null) != null && !this.prefs.getBoolean("settings_password_access", false)) {
            PasswordHelper.requestPassword(this.notesMainActivity, new PasswordValidator() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$udLJQG85j14bDplO8z2czP0rADo
                @Override // it.orsozoxi.android.orsonotes.models.PasswordValidator
                public final void onPasswordValidated(PasswordValidator.Result result) throws NumberFormatException {
                    this.f$0.lambda$checkNoteLock$1$DetailFragment(result);
                }
            });
        } else {
            this.noteTmp.setPasswordChecked(true);
            init();
        }
    }

    public /* synthetic */ void lambda$checkNoteLock$1$DetailFragment(PasswordValidator.Result result) throws NumberFormatException {
        int i = AnonymousClass4.$SwitchMap$it$orsozoxi$android$orsonotes$models$PasswordValidator$Result[result.ordinal()];
        if (i == 1) {
            this.noteTmp.setPasswordChecked(true);
            init();
        } else if (i == 2) {
            this.goBack = true;
            goHome();
        } else {
            if (i != 3) {
                return;
            }
            this.goBack = true;
            goHome();
            PasswordHelper.resetPassword(this.notesMainActivity);
        }
    }

    private void handleIntents() {
        Intent intent = this.notesMainActivity.getIntent();
        if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_MERGE)) {
            this.noteOriginal = new Note();
            this.note = new Note(this.noteOriginal);
            this.noteTmp = (Note) getArguments().getParcelable(ConstantsBase.INTENT_NOTE);
            if (intent.getStringArrayListExtra("merged_notes") != null) {
                this.mergedNotesIds = intent.getStringArrayListExtra("merged_notes");
            }
        }
        if (IntentChecker.checkAction(intent, ConstantsBase.ACTION_SHORTCUT, ConstantsBase.ACTION_NOTIFICATION_CLICK)) {
            this.afterSavedReturnsToList = false;
            this.noteOriginal = DbHelper.getInstance().getNote(intent.getLongExtra("note_id", 0L));
            try {
                this.note = new Note(this.noteOriginal);
                this.noteTmp = new Note(this.noteOriginal);
            } catch (NullPointerException unused) {
                this.notesMainActivity.showToast(getText(R.string.shortcut_note_deleted), 1);
                this.notesMainActivity.finish();
            }
        }
        if (IntentChecker.checkAction(intent, "android.intent.action.SEND", "android.intent.action.SEND_MULTIPLE", ConstantsBase.INTENT_GOOGLE_NOW) && intent.getType() != null) {
            this.afterSavedReturnsToList = false;
            if (this.noteTmp == null) {
                this.noteTmp = new Note();
            }
            String stringExtra = intent.getStringExtra("android.intent.extra.SUBJECT");
            if (stringExtra != null) {
                this.noteTmp.setTitle(stringExtra);
            }
            String stringExtra2 = intent.getStringExtra("android.intent.extra.TEXT");
            if (stringExtra2 != null) {
                this.noteTmp.setContent(stringExtra2);
            }
            importAttachments(intent);
        }
        if (IntentChecker.checkAction(intent, "android.intent.action.MAIN", ConstantsBase.ACTION_WIDGET_SHOW_LIST, ConstantsBase.ACTION_SHORTCUT_WIDGET, ConstantsBase.ACTION_WIDGET)) {
            this.showKeyboard = true;
        }
        intent.setAction(null);
    }

    private void importAttachments(Intent intent) {
        if (intent.hasExtra("android.intent.extra.STREAM")) {
            if (intent.getExtras().get("android.intent.extra.STREAM") instanceof Uri) {
                Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.STREAM");
                if (ConstantsBase.INTENT_GOOGLE_NOW.equals(intent.getAction())) {
                    return;
                }
                new AttachmentTask(this, uri, FileHelper.getNameFromUri(this.notesMainActivity, uri), this).execute(new Void[0]);
                return;
            }
            Iterator it2 = intent.getParcelableArrayListExtra("android.intent.extra.STREAM").iterator();
            while (it2.hasNext()) {
                Uri uri2 = (Uri) it2.next();
                new AttachmentTask(this, uri2, FileHelper.getNameFromUri(this.notesMainActivity, uri2), this).execute(new Void[0]);
            }
        }
    }

    private void initViews() throws NumberFormatException {
        this.root.setOnTouchListener(this);
        setTagMarkerColor(this.noteTmp.getCategory());
        initViewTitle();
        initViewContent();
        initViewLocation();
        initViewAttachments();
        initViewReminder();
        initViewFooter();
    }

    private void initViewFooter() {
        String str;
        String formattedDate = DateHelper.getFormattedDate(this.noteTmp.getCreation(), this.prefs.getBoolean(ConstantsBase.PREF_PRETTIFIED_DATES, true));
        TextView textView = this.creationTextView;
        String str2 = "";
        if (formattedDate.length() > 0) {
            str = getString(R.string.creation) + " " + formattedDate;
        } else {
            str = "";
        }
        textView.append(str);
        if (this.creationTextView.getText().length() == 0) {
            this.creationTextView.setVisibility(8);
        }
        String formattedDate2 = DateHelper.getFormattedDate(this.noteTmp.getLastModification(), this.prefs.getBoolean(ConstantsBase.PREF_PRETTIFIED_DATES, true));
        TextView textView2 = this.lastModificationTextView;
        if (formattedDate2.length() > 0) {
            str2 = getString(R.string.last_update) + " " + formattedDate2;
        }
        textView2.append(str2);
        if (this.lastModificationTextView.getText().length() == 0) {
            this.lastModificationTextView.setVisibility(8);
        }
    }

    private void initViewReminder() throws NumberFormatException {
        this.reminderLayout.setOnClickListener(new View.OnClickListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$Boi1UMrDMVMpoIuwFDduLtThsyM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initViewReminder$2$DetailFragment(view);
            }
        });
        this.reminderLayout.setOnLongClickListener(new View.OnLongClickListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$3J7bvxF3snYwcW9zgjpZBkemrA8
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.lambda$initViewReminder$4$DetailFragment(view);
            }
        });
        String strInitReminder = initReminder(this.noteTmp);
        if (TextUtils.isEmpty(strInitReminder)) {
            return;
        }
        this.reminderIcon.setImageResource(R.drawable.ic_alarm_add_black_18dp);
        this.datetime.setText(strInitReminder);
    }

    public /* synthetic */ void lambda$initViewReminder$2$DetailFragment(View view) {
        new ReminderPickers(this.notesMainActivity, this.mFragment).pick(DateUtils.getPresetReminder(this.noteTmp.getAlarm()), this.noteTmp.getRecurrenceRule());
    }

    public /* synthetic */ boolean lambda$initViewReminder$4$DetailFragment(View view) {
        new MaterialDialog.Builder(this.notesMainActivity).content(R.string.remove_reminder).positiveText(R.string.ok).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$c0h3MyJGJ_K0p9-dpECf1JQNO8E
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                this.f$0.lambda$initViewReminder$3$DetailFragment(materialDialog, dialogAction);
            }
        }).build().show();
        return true;
    }

    public /* synthetic */ void lambda$initViewReminder$3$DetailFragment(MaterialDialog materialDialog, DialogAction dialogAction) {
        ReminderHelper.removeReminder(OrsoNotes.getAppContext(), this.noteTmp);
        this.noteTmp.setAlarm((String) null);
        this.reminderIcon.setImageResource(R.drawable.ic_alarm_black_18dp);
        this.datetime.setText("");
    }

    private void initViewLocation() {
        if (isNoteLocationValid() && !TextUtils.isEmpty(this.noteTmp.getAddress())) {
            this.locationTextView.setText(this.noteTmp.getAddress());
            this.locationTextView.setVisibility(0);
        }
        if (this.prefs.getBoolean(ConstantsBase.PREF_AUTO_LOCATION, false) && this.noteTmp.get_id() == null) {
            getLocation(this);
        }
        this.locationTextView.setOnClickListener(new View.OnClickListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$O6aGLjDnJLfZlLM9qWjAM9BFZN4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initViewLocation$5$DetailFragment(view);
            }
        });
        this.locationTextView.setOnLongClickListener(new View.OnLongClickListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$G1S5SQTawuaZcvVPTvBEDT1mkGc
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return DetailFragment.lambda$initViewLocation$6(view);
            }
        });
    }

    public /* synthetic */ void lambda$initViewLocation$5$DetailFragment(View view) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("geo:" + this.noteTmp.getLatitude() + ',' + this.noteTmp.getLongitude() + "?q=" + this.noteTmp.getLatitude() + ',' + this.noteTmp.getLongitude()));
        if (!IntentChecker.isAvailable(this.notesMainActivity, intent, null)) {
            intent = new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?q=" + this.noteTmp.getLatitude() + ',' + this.noteTmp.getLongitude()));
        }
        startActivity(intent);
    }

    private void initViewAttachments() {
        if (this.prefs.getBoolean(ConstantsBase.PREF_ATTACHMENTS_ON_BOTTOM, false)) {
            this.attachmentsBelow.inflate();
        } else {
            this.attachmentsAbove.inflate();
        }
        this.mGridView = (ExpandableHeightGridView) this.root.findViewById(R.id.gridview);
        AttachmentAdapter attachmentAdapter = new AttachmentAdapter(this.notesMainActivity, this.noteTmp.getAttachmentsList(), this.mGridView);
        this.mAttachmentAdapter = attachmentAdapter;
        this.mGridView.setAdapter((ListAdapter) attachmentAdapter);
        this.mGridView.autoresize();
        this.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$nOpDAqrYTlszaOx9g_lJV3zoeVc
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                this.f$0.lambda$initViewAttachments$7$DetailFragment(adapterView, view, i, j);
            }
        });
        this.mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$kYc2KmZ-skygvn7yhMSxq5_QtNU
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public final boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
                return this.f$0.lambda$initViewAttachments$9$DetailFragment(adapterView, view, i, j);
            }
        });
    }

    public /* synthetic */ void lambda$initViewAttachments$7$DetailFragment(AdapterView adapterView, View view, int i, long j) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        Attachment attachment = (Attachment) adapterView.getAdapter().getItem(i);
        Uri shareableUri = FileProviderHelper.getShareableUri(attachment);
        if (ConstantsBase.MIME_TYPE_FILES.equals(attachment.getMime_type())) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(shareableUri, StorageHelper.getMimeType(this.notesMainActivity, shareableUri));
            intent.addFlags(3);
            if (IntentChecker.isAvailable(this.notesMainActivity.getApplicationContext(), intent, null)) {
                startActivity(intent);
                return;
            } else {
                this.notesMainActivity.showMessage(R.string.feature_not_available_on_this_device, ONStyle.WARN);
                return;
            }
        }
        if (ConstantsBase.MIME_TYPE_IMAGE.equals(attachment.getMime_type()) || ConstantsBase.MIME_TYPE_SKETCH.equals(attachment.getMime_type()) || ConstantsBase.MIME_TYPE_VIDEO.equals(attachment.getMime_type())) {
            this.noteTmp.setTitle(getNoteTitle());
            this.noteTmp.setContent(getNoteContent());
            int size = 0;
            String string = TextHelper.parseTitleAndContent(this.notesMainActivity, this.noteTmp)[0].toString();
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            for (Attachment attachment2 : this.noteTmp.getAttachmentsList()) {
                if (ConstantsBase.MIME_TYPE_IMAGE.equals(attachment2.getMime_type()) || ConstantsBase.MIME_TYPE_SKETCH.equals(attachment2.getMime_type()) || ConstantsBase.MIME_TYPE_VIDEO.equals(attachment2.getMime_type())) {
                    arrayList.add(attachment2);
                    if (attachment2.equals(attachment)) {
                        size = arrayList.size() - 1;
                    }
                }
            }
            Intent intent2 = new Intent(this.notesMainActivity, (Class<?>) GalleryActivity.class);
            intent2.putExtra(ConstantsBase.GALLERY_TITLE, string);
            intent2.putParcelableArrayListExtra(ConstantsBase.GALLERY_IMAGES, arrayList);
            intent2.putExtra(ConstantsBase.GALLERY_CLICKED_IMAGE, size);
            startActivity(intent2);
            return;
        }
        if (ConstantsBase.MIME_TYPE_AUDIO.equals(attachment.getMime_type())) {
            playback(view, attachment.getUri());
        }
    }

    public /* synthetic */ boolean lambda$initViewAttachments$9$DetailFragment(AdapterView adapterView, View view, final int i, long j) {
        if (this.mPlayer != null) {
            return false;
        }
        List listAsList = Arrays.asList(getResources().getStringArray(R.array.attachments_actions));
        if (!ConstantsBase.MIME_TYPE_SKETCH.equals(this.mAttachmentAdapter.getItem(i).getMime_type())) {
            listAsList = listAsList.subList(0, listAsList.size() - 1);
        }
        Attachment item = this.mAttachmentAdapter.getItem(i);
        new MaterialDialog.Builder(this.notesMainActivity).title(item.getName() + " (" + AttachmentsHelper.getSize(item) + ")").items((CharSequence[]) listAsList.toArray(new String[listAsList.size()])).itemsCallback(new MaterialDialog.ListCallback() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$ctN8-pw7s6x71IXLEa35mW-rtcw
            @Override // com.afollestad.materialdialogs.MaterialDialog.ListCallback
            public final void onSelection(MaterialDialog materialDialog, View view2, int i2, CharSequence charSequence) {
                this.f$0.lambda$initViewAttachments$8$DetailFragment(i, materialDialog, view2, i2, charSequence);
            }
        }).build().show();
        return true;
    }

    public /* synthetic */ void lambda$initViewAttachments$8$DetailFragment(int i, MaterialDialog materialDialog, View view, int i2, CharSequence charSequence) {
        performAttachmentAction(i, i2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void performAttachmentAction(int r5, int r6) {
        /*
            r4 = this;
            android.content.res.Resources r0 = r4.getResources()
            int r1 = it.orsozoxi.android.orsonotes.R.array.attachments_actions_values
            java.lang.String[] r0 = r0.getStringArray(r1)
            r6 = r0[r6]
            int r0 = r6.hashCode()
            r1 = 3
            r2 = 2
            r3 = 1
            switch(r0) {
                case -1335458389: goto L35;
                case 3108362: goto L2b;
                case 109400031: goto L21;
                case 1763032492: goto L17;
                default: goto L16;
            }
        L16:
            goto L3f
        L17:
            java.lang.String r0 = "delete all"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3f
            r6 = 2
            goto L40
        L21:
            java.lang.String r0 = "share"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3f
            r6 = 0
            goto L40
        L2b:
            java.lang.String r0 = "edit"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3f
            r6 = 3
            goto L40
        L35:
            java.lang.String r0 = "delete"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3f
            r6 = 1
            goto L40
        L3f:
            r6 = -1
        L40:
            if (r6 == 0) goto L8b
            if (r6 == r3) goto L7d
            if (r6 == r2) goto L59
            if (r6 == r1) goto L4f
            java.lang.String r5 = "No action available"
            it.orsozoxi.android.orsonotes.helpers.LogDelegate.w(r5)
            goto Lc8
        L4f:
            it.orsozoxi.android.orsonotes.models.adapters.AttachmentAdapter r6 = r4.mAttachmentAdapter
            it.orsozoxi.android.orsonotes.models.Attachment r5 = r6.getItem(r5)
            r4.takeSketch(r5)
            goto Lc8
        L59:
            com.afollestad.materialdialogs.MaterialDialog$Builder r5 = new com.afollestad.materialdialogs.MaterialDialog$Builder
            it.orsozoxi.android.orsonotes.NotesMainActivity r6 = r4.notesMainActivity
            r5.<init>(r6)
            int r6 = it.orsozoxi.android.orsonotes.R.string.delete_all_attachments
            com.afollestad.materialdialogs.MaterialDialog$Builder r5 = r5.title(r6)
            int r6 = it.orsozoxi.android.orsonotes.R.string.confirm
            com.afollestad.materialdialogs.MaterialDialog$Builder r5 = r5.positiveText(r6)
            it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$_ZCS-ezxLxRSAMFRH8RcNvqa0Ew r6 = new it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$_ZCS-ezxLxRSAMFRH8RcNvqa0Ew
            r6.<init>()
            com.afollestad.materialdialogs.MaterialDialog$Builder r5 = r5.onPositive(r6)
            com.afollestad.materialdialogs.MaterialDialog r5 = r5.build()
            r5.show()
            goto Lc8
        L7d:
            r4.removeAttachment(r5)
            it.orsozoxi.android.orsonotes.models.adapters.AttachmentAdapter r5 = r4.mAttachmentAdapter
            r5.notifyDataSetChanged()
            it.orsozoxi.android.orsonotes.models.views.ExpandableHeightGridView r5 = r4.mGridView
            r5.autoresize()
            goto Lc8
        L8b:
            android.content.Intent r6 = new android.content.Intent
            java.lang.String r0 = "android.intent.action.SEND"
            r6.<init>(r0)
            it.orsozoxi.android.orsonotes.models.adapters.AttachmentAdapter r0 = r4.mAttachmentAdapter
            it.orsozoxi.android.orsonotes.models.Attachment r5 = r0.getItem(r5)
            android.content.Context r0 = it.orsozoxi.android.orsonotes.OrsoNotes.getAppContext()
            android.net.Uri r1 = r5.getUri()
            java.lang.String r0 = it.orsozoxi.android.orsonotes.utils.StorageHelper.getMimeType(r0, r1)
            r6.setType(r0)
            android.net.Uri r5 = it.orsozoxi.android.orsonotes.utils.FileProviderHelper.getShareableUri(r5)
            java.lang.String r0 = "android.intent.extra.STREAM"
            r6.putExtra(r0, r5)
            android.content.Context r5 = it.orsozoxi.android.orsonotes.OrsoNotes.getAppContext()
            r0 = 0
            boolean r5 = it.orsozoxi.android.orsonotes.utils.IntentChecker.isAvailable(r5, r6, r0)
            if (r5 == 0) goto Lbf
            r4.startActivity(r6)
            goto Lc8
        Lbf:
            it.orsozoxi.android.orsonotes.NotesMainActivity r5 = r4.notesMainActivity
            int r6 = it.orsozoxi.android.orsonotes.R.string.feature_not_available_on_this_device
            de.keyboardsurfer.android.widget.crouton.Style r0 = it.orsozoxi.android.orsonotes.models.ONStyle.WARN
            r5.showMessage(r6, r0)
        Lc8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: it.orsozoxi.android.orsonotes.DetailFragment.performAttachmentAction(int, int):void");
    }

    public /* synthetic */ void lambda$performAttachmentAction$10$DetailFragment(MaterialDialog materialDialog, DialogAction dialogAction) {
        removeAllAttachments();
    }

    private void initViewTitle() {
        this.title.setText(this.noteTmp.getTitle());
        this.title.gatherLinksForText();
        this.title.setOnTextLinkClickListener(this.textLinkClickListener);
        this.title.setOnDragListener(new View.OnDragListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$QoUd5l32KFuKjg52PbBql3_Dxfg
            @Override // android.view.View.OnDragListener
            public final boolean onDrag(View view, DragEvent dragEvent) {
                return DetailFragment.lambda$initViewTitle$11(view, dragEvent);
            }
        });
        this.title.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$7itdCad-KXlMQS4XVnxZLUMUgAc
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(android.widget.TextView textView, int i, KeyEvent keyEvent) {
                return this.f$0.lambda$initViewTitle$12$DetailFragment(textView, i, keyEvent);
            }
        });
        requestFocus(this.title);
    }

    public /* synthetic */ boolean lambda$initViewTitle$12$DetailFragment(android.widget.TextView textView, int i, KeyEvent keyEvent) {
        this.content.requestFocus();
        EditText editText = this.content;
        editText.setSelection(editText.getText().length());
        return false;
    }

    private void initViewContent() {
        this.content.setText(this.noteTmp.getContent());
        this.content.gatherLinksForText();
        this.content.setOnTextLinkClickListener(this.textLinkClickListener);
        this.content.addTextChangedListener(this);
        this.toggleChecklistView = this.content;
        if (this.noteTmp.isChecklist().booleanValue()) {
            this.noteTmp.setChecklist((Boolean) false);
            AlphaManager.setAlpha(this.toggleChecklistView, 0.0f);
            toggleChecklist2();
        }
    }

    private void requestFocus(EditText editText) {
        if (this.note.get_id() == null && !this.noteTmp.isChanged(this.note) && this.showKeyboard) {
            KeyboardUtils.showKeyboard(editText);
        }
    }

    private void setTagMarkerColor(Category category) {
        String string = this.prefs.getString("settings_colors_app", ConstantsBase.PREF_COLORS_APP_DEFAULT);
        if ("disabled".equals(string)) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        if ("complete".equals(string)) {
            arrayList.add(this.titleWrapperView);
            arrayList.add(this.scrollView);
        } else {
            arrayList.add(this.tagMarkerView);
        }
        if (category != null && category.getColor() != null) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ((View) it2.next()).setBackgroundColor(Integer.parseInt(category.getColor()));
            }
        } else {
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                ((View) it3.next()).setBackgroundColor(Color.parseColor("#00000000"));
            }
        }
    }

    private void displayLocationDialog() {
        getLocation(new OnGeoUtilResultListenerImpl(this.notesMainActivity, this.mFragment, this.noteTmp));
    }

    @Override // it.orsozoxi.android.orsonotes.models.listeners.OnGeoUtilResultListener
    public void onLocationRetrieved(Location location) {
        if (location == null) {
            this.notesMainActivity.showMessage(R.string.location_not_found, ONStyle.ALERT);
        }
        if (location != null) {
            this.noteTmp.setLatitude(Double.valueOf(location.getLatitude()));
            this.noteTmp.setLongitude(Double.valueOf(location.getLongitude()));
            if (TextUtils.isEmpty(this.noteTmp.getAddress())) {
                return;
            }
            this.locationTextView.setVisibility(0);
            this.locationTextView.setText(this.noteTmp.getAddress());
        }
    }

    @Override // it.orsozoxi.android.orsonotes.models.listeners.OnGeoUtilResultListener
    public void onLocationUnavailable() {
        this.notesMainActivity.showMessage(R.string.location_not_found, ONStyle.ALERT);
    }

    @Override // it.orsozoxi.android.orsonotes.models.listeners.OnGeoUtilResultListener
    public void onAddressResolved(String str) throws Resources.NotFoundException {
        if (TextUtils.isEmpty(str)) {
            if (!isNoteLocationValid()) {
                this.notesMainActivity.showMessage(R.string.location_not_found, ONStyle.ALERT);
                return;
            }
            str = this.noteTmp.getLatitude() + ", " + this.noteTmp.getLongitude();
        }
        this.locationTextView.setVisibility(0);
        this.locationTextView.setText(str);
        fade(this.locationTextView, true);
    }

    @Override // it.orsozoxi.android.orsonotes.models.listeners.OnGeoUtilResultListener
    public void onCoordinatesResolved(Location location, String str) throws Resources.NotFoundException {
        if (location != null) {
            this.noteTmp.setLatitude(Double.valueOf(location.getLatitude()));
            this.noteTmp.setLongitude(Double.valueOf(location.getLongitude()));
            this.noteTmp.setAddress(str);
            this.locationTextView.setVisibility(0);
            this.locationTextView.setText(str);
            fade(this.locationTextView, true);
            return;
        }
        this.notesMainActivity.showMessage(R.string.location_not_found, ONStyle.ALERT);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_detail, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItemFindItem = menu.findItem(R.id.menu_search);
        if (menuItemFindItem != null) {
            menuItemFindItem.collapseActionView();
        }
        boolean z = false;
        boolean z2 = this.noteTmp.get_id() == null;
        menu.findItem(R.id.menu_checklist_on).setVisible(!this.noteTmp.isChecklist().booleanValue());
        menu.findItem(R.id.menu_checklist_off).setVisible(this.noteTmp.isChecklist().booleanValue());
        if (this.noteTmp.isTrashed().booleanValue()) {
            menu.findItem(R.id.menu_untrash).setVisible(true);
            menu.findItem(R.id.menu_delete).setVisible(true);
            return;
        }
        menu.findItem(R.id.menu_archive).setVisible((z2 || this.noteTmp.isArchived().booleanValue()) ? false : true);
        MenuItem menuItemFindItem2 = menu.findItem(R.id.menu_unarchive);
        if (!z2 && this.noteTmp.isArchived().booleanValue()) {
            z = true;
        }
        menuItemFindItem2.setVisible(z);
        menu.findItem(R.id.menu_trash).setVisible(!z2);
    }

    private boolean goHome() {
        Style style;
        stopPlaying();
        if (!this.afterSavedReturnsToList) {
            if (!TextUtils.isEmpty(this.exitMessage)) {
                this.notesMainActivity.showToast(this.exitMessage, 0);
            }
            this.notesMainActivity.finish();
        } else {
            if (!TextUtils.isEmpty(this.exitMessage) && (style = this.exitCroutonStyle) != null) {
                this.notesMainActivity.showMessage(this.exitMessage, style);
            }
            NotesMainActivity notesMainActivity = this.notesMainActivity;
            if (notesMainActivity != null && notesMainActivity.getSupportFragmentManager() != null) {
                this.notesMainActivity.getSupportFragmentManager().popBackStack();
                if (this.notesMainActivity.getSupportFragmentManager().getBackStackEntryCount() == 1) {
                    this.notesMainActivity.getSupportActionBar().setDisplayShowTitleEnabled(true);
                    if (this.notesMainActivity.getDrawerToggle() != null) {
                        this.notesMainActivity.getDrawerToggle().setDrawerIndicatorEnabled(true);
                    }
                    EventBus.getDefault().post(new SwitchFragmentEvent(SwitchFragmentEvent.Direction.PARENT));
                }
            }
        }
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (isOptionsItemFastClick()) {
            return true;
        }
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_attachment) {
            showAttachmentsPopup();
        } else if (itemId == R.id.menu_tag) {
            addTags();
        } else if (itemId == R.id.menu_category) {
            categorizeNote();
        } else if (itemId == R.id.menu_share) {
            shareNote();
        } else if (itemId == R.id.menu_checklist_on || itemId == R.id.menu_checklist_off) {
            toggleChecklist();
        } else if (itemId == R.id.menu_add_shortcut) {
            addShortcut();
        } else if (itemId == R.id.menu_archive) {
            archiveNote(true);
        } else if (itemId == R.id.menu_unarchive) {
            archiveNote(false);
        } else if (itemId == R.id.menu_trash) {
            trashNote(true);
        } else if (itemId == R.id.menu_untrash) {
            trashNote(false);
        } else if (itemId == R.id.menu_discard_changes) {
            discard();
        } else if (itemId == R.id.menu_delete) {
            deleteNote();
        } else if (itemId == R.id.menu_note_info) {
            showNoteInfo();
        } else {
            LogDelegate.w("Invalid menu option selected");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void showNoteInfo() {
        this.noteTmp.setTitle(getNoteTitle());
        this.noteTmp.setContent(getNoteContent());
        Intent intent = new Intent(getContext(), (Class<?>) NoteInfosActivity.class);
        intent.putExtra(ConstantsBase.INTENT_NOTE, (Parcelable) this.noteTmp);
        startActivity(intent);
    }

    private void navigateUp() {
        this.afterSavedReturnsToList = true;
        saveAndExit(this);
    }

    private void toggleChecklist() {
        if (!this.noteTmp.isChecklist().booleanValue()) {
            toggleChecklist2();
            return;
        }
        if (this.mChecklistManager.getCheckedCount() == 0) {
            toggleChecklist2(true, false);
            return;
        }
        View viewInflate = ((LayoutInflater) this.notesMainActivity.getSystemService("layout_inflater")).inflate(R.layout.dialog_remove_checklist_layout, (ViewGroup) getView().findViewById(R.id.layout_root));
        final CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.checklist_keep_checked);
        final CheckBox checkBox2 = (CheckBox) viewInflate.findViewById(R.id.checklist_keep_checkmarks);
        checkBox.setChecked(this.prefs.getBoolean(ConstantsBase.PREF_KEEP_CHECKED, true));
        checkBox2.setChecked(this.prefs.getBoolean(ConstantsBase.PREF_KEEP_CHECKMARKS, true));
        new MaterialDialog.Builder(this.notesMainActivity).customView(viewInflate, false).positiveText(R.string.ok).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.orsozoxi.android.orsonotes.DetailFragment.2
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                DetailFragment.this.prefs.edit().putBoolean(ConstantsBase.PREF_KEEP_CHECKED, checkBox.isChecked()).putBoolean(ConstantsBase.PREF_KEEP_CHECKMARKS, checkBox2.isChecked()).apply();
                DetailFragment.this.toggleChecklist2();
            }
        }).build().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleChecklist2() {
        toggleChecklist2(this.prefs.getBoolean(ConstantsBase.PREF_KEEP_CHECKED, true), this.prefs.getBoolean(ConstantsBase.PREF_KEEP_CHECKMARKS, true));
    }

    private void toggleChecklist2(boolean z, boolean z2) {
        ChecklistManager checklistManager = this.mChecklistManager;
        if (checklistManager == null) {
            checklistManager = new ChecklistManager(this.notesMainActivity);
        }
        this.mChecklistManager = checklistManager;
        this.mChecklistManager.showCheckMarks(z2).newEntryHint(getString(R.string.checklist_item_hint)).keepChecked(z).undoBarContainerView(this.scrollView).moveCheckedOnBottom(Integer.valueOf(this.prefs.getString("settings_checked_items_behavior", String.valueOf(0))).intValue());
        this.mChecklistManager.setOnTextLinkClickListener(this.textLinkClickListener);
        this.mChecklistManager.addTextChangedListener(this.mFragment);
        this.mChecklistManager.setCheckListChangedListener(this.mFragment);
        View viewConvert = null;
        try {
            viewConvert = this.mChecklistManager.convert(this.toggleChecklistView);
        } catch (ViewNotSupportedException e) {
            LogDelegate.e("Error switching checklist view", e);
        }
        if (viewConvert != null) {
            this.mChecklistManager.replaceViews(this.toggleChecklistView, viewConvert);
            this.toggleChecklistView = viewConvert;
            ViewCompat.animate(viewConvert).alpha(1.0f).scaleXBy(0.0f).scaleX(1.0f).scaleYBy(0.0f).scaleY(1.0f);
            this.noteTmp.setChecklist(Boolean.valueOf(!r4.isChecklist().booleanValue()));
        }
    }

    private void categorizeNote() {
        final String strValueOf = this.noteTmp.getCategory() != null ? String.valueOf(this.noteTmp.getCategory().getId()) : null;
        final List list = (List) Observable.from(DbHelper.getInstance().getCategories()).map(new Func1() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$IBay8Q-sbnNScbwVnDnIWiYHibY
            @Override // rx.functions.Func1
            public final Object call(Object obj) {
                return DetailFragment.lambda$categorizeNote$13(strValueOf, (Category) obj);
            }
        }).toList().toBlocking().single();
        final MaterialDialog materialDialogBuild = new MaterialDialog.Builder(this.notesMainActivity).title(R.string.categorize_as).adapter(new CategoryRecyclerViewAdapter(this.notesMainActivity, list), null).positiveText(R.string.add_category).positiveColorRes(R.color.colorPrimary).negativeText(R.string.remove_category).negativeColorRes(R.color.colorAccent).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$Ly8X_cnwfwbr3Wwy8LK3TdqvtCc
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                this.f$0.lambda$categorizeNote$14$DetailFragment(materialDialog, dialogAction);
            }
        }).onNegative(new MaterialDialog.SingleButtonCallback() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$cDPDGVfEx4H0YVl2PA1XVreR9Mk
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                this.f$0.lambda$categorizeNote$15$DetailFragment(materialDialog, dialogAction);
            }
        }).build();
        RecyclerViewItemClickSupport.addTo(materialDialogBuild.getRecyclerView()).setOnItemClickListener(new RecyclerViewItemClickSupport.OnItemClickListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$b-FpK3yI-mtsFl8rjhagVTHSgtA
            @Override // it.orsozoxi.android.orsonotes.models.listeners.RecyclerViewItemClickSupport.OnItemClickListener
            public final void onItemClicked(RecyclerView recyclerView, int i, View view) {
                this.f$0.lambda$categorizeNote$16$DetailFragment(list, materialDialogBuild, recyclerView, i, view);
            }
        });
        materialDialogBuild.show();
    }

    static /* synthetic */ Category lambda$categorizeNote$13(String str, Category category) {
        if (String.valueOf(category.getId()).equals(str)) {
            category.setCount(category.getCount() + 1);
        }
        return category;
    }

    public /* synthetic */ void lambda$categorizeNote$14$DetailFragment(MaterialDialog materialDialog, DialogAction dialogAction) {
        Intent intent = new Intent(this.notesMainActivity, (Class<?>) CategoryActivity.class);
        intent.putExtra("noHome", true);
        startActivityForResult(intent, 5);
    }

    public /* synthetic */ void lambda$categorizeNote$15$DetailFragment(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.noteTmp.setCategory((Category) null);
        setTagMarkerColor(null);
    }

    public /* synthetic */ void lambda$categorizeNote$16$DetailFragment(List list, MaterialDialog materialDialog, RecyclerView recyclerView, int i, View view) {
        this.noteTmp.setCategory((Category) list.get(i));
        setTagMarkerColor((Category) list.get(i));
        materialDialog.dismiss();
    }

    private void showAttachmentsPopup() {
        AnonymousClass1 anonymousClass1 = null;
        View viewInflate = this.notesMainActivity.getLayoutInflater().inflate(R.layout.attachment_dialog, (ViewGroup) null);
        MaterialDialog materialDialogBuild = new MaterialDialog.Builder(this.notesMainActivity).autoDismiss(false).customView(viewInflate, false).build();
        this.attachmentDialog = materialDialogBuild;
        materialDialogBuild.show();
        ((android.widget.TextView) viewInflate.findViewById(R.id.camera)).setOnClickListener(new AttachmentOnClickListener(this, anonymousClass1));
        android.widget.TextView textView = (android.widget.TextView) viewInflate.findViewById(R.id.recording);
        toggleAudioRecordingStop(textView);
        textView.setOnClickListener(new AttachmentOnClickListener(this, anonymousClass1));
        ((android.widget.TextView) viewInflate.findViewById(R.id.video)).setOnClickListener(new AttachmentOnClickListener(this, anonymousClass1));
        ((android.widget.TextView) viewInflate.findViewById(R.id.files)).setOnClickListener(new AttachmentOnClickListener(this, anonymousClass1));
        ((android.widget.TextView) viewInflate.findViewById(R.id.sketch)).setOnClickListener(new AttachmentOnClickListener(this, anonymousClass1));
        ((android.widget.TextView) viewInflate.findViewById(R.id.timestamp)).setOnClickListener(new AttachmentOnClickListener(this, anonymousClass1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void takeSketch(Attachment attachment) {
        File fileCreateNewAttachmentFile = StorageHelper.createNewAttachmentFile(this.notesMainActivity, ConstantsBase.MIME_TYPE_SKETCH_EXT);
        if (fileCreateNewAttachmentFile == null) {
            this.notesMainActivity.showMessage(R.string.error, ONStyle.ALERT);
            return;
        }
        this.attachmentUri = Uri.fromFile(fileCreateNewAttachmentFile);
        this.notesMainActivity.setRequestedOrientation(1);
        FragmentTransaction fragmentTransactionBeginTransaction = this.notesMainActivity.getSupportFragmentManager().beginTransaction();
        this.notesMainActivity.animateTransition(fragmentTransactionBeginTransaction, 1);
        SketchFragment sketchFragment = new SketchFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("output", this.attachmentUri);
        if (attachment != null) {
            bundle.putParcelable("base", attachment.getUri());
        }
        sketchFragment.setArguments(bundle);
        fragmentTransactionBeginTransaction.replace(R.id.fragment_container, sketchFragment, NotesMainActivity.FRAGMENT_SKETCH_TAG).addToBackStack(NotesMainActivity.FRAGMENT_DETAIL_TAG).commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addTimestamp() {
        Editable text = this.content.getText();
        int selectionStart = this.content.getSelectionStart();
        String str = DateFormat.getDateTimeInstance(3, 3).format(Long.valueOf(new Date().getTime())) + " ";
        if (this.noteTmp.isChecklist().booleanValue()) {
            if (this.mChecklistManager.getFocusedItemView() != null) {
                text = this.mChecklistManager.getFocusedItemView().getEditText().getEditableText();
                selectionStart = this.mChecklistManager.getFocusedItemView().getEditText().getSelectionStart();
            } else {
                ((CheckListView) this.toggleChecklistView).addItem(str, false, Integer.valueOf(this.mChecklistManager.getCount()));
            }
        }
        String str2 = (selectionStart == 0 ? "" : " ") + str;
        text.insert(selectionStart, str2);
        Selection.setSelection(text, selectionStart + str2.length());
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            if (i == 3) {
                this.noteTmp.setPasswordChecked(true);
                lockUnlock();
                return;
            }
            if (i == 4) {
                addAttachment(new Attachment(this.attachmentUri, ConstantsBase.MIME_TYPE_SKETCH));
                this.mAttachmentAdapter.notifyDataSetChanged();
                this.mGridView.autoresize();
                return;
            }
            if (i == 5) {
                this.notesMainActivity.showMessage(R.string.category_saved, ONStyle.CONFIRM);
                Category category = (Category) intent.getParcelableExtra("category");
                this.noteTmp.setCategory(category);
                setTagMarkerColor(category);
                return;
            }
            if (i == 6) {
                this.notesMainActivity.showMessage(R.string.note_updated, ONStyle.CONFIRM);
                return;
            }
            if (i == 7) {
                onActivityResultManageReceivedFiles(intent);
                return;
            }
            LogDelegate.e("Wrong element choosen: " + i);
        }
    }

    private void onActivityResultManageReceivedFiles(Intent intent) {
        ArrayList<Uri> arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT > 16 && intent.getClipData() != null) {
            for (int i = 0; i < intent.getClipData().getItemCount(); i++) {
                arrayList.add(intent.getClipData().getItemAt(i).getUri());
            }
        } else {
            arrayList.add(intent.getData());
        }
        for (Uri uri : arrayList) {
            new AttachmentTask(this, uri, FileHelper.getNameFromUri(this.notesMainActivity, uri), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    private void discard() {
        new MaterialDialog.Builder(this.notesMainActivity).content(R.string.undo_changes_note_confirmation).positiveText(R.string.ok).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$sSvOWr15cvRA6D-lpvyYBi67mrg
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                this.f$0.lambda$discard$17$DetailFragment(materialDialog, dialogAction);
            }
        }).build().show();
    }

    public /* synthetic */ void lambda$discard$17$DetailFragment(MaterialDialog materialDialog, DialogAction dialogAction) {
        if (!this.noteTmp.getAttachmentsList().equals(this.note.getAttachmentsList())) {
            for (Attachment attachment : this.noteTmp.getAttachmentsList()) {
                if (!this.note.getAttachmentsList().contains(attachment)) {
                    StorageHelper.delete(this.notesMainActivity, attachment.getUri().getPath());
                }
            }
        }
        this.goBack = true;
        if (this.noteTmp.equals(this.noteOriginal)) {
            goHome();
        }
        if (this.noteOriginal.get_id() != null) {
            new SaveNoteTask(this.mFragment, false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.noteOriginal);
            NotesMainActivity.notifyAppWidgets(this.notesMainActivity);
        } else {
            goHome();
        }
    }

    private void archiveNote(boolean z) {
        if (this.noteTmp.get_id() == null) {
            goHome();
            return;
        }
        this.noteTmp.setArchived(Boolean.valueOf(z));
        this.goBack = true;
        this.exitMessage = getString(z ? R.string.note_archived : R.string.note_unarchived);
        this.exitCroutonStyle = z ? ONStyle.WARN : ONStyle.INFO;
        saveNote(this);
    }

    private void trashNote(boolean z) {
        if (this.noteTmp.get_id() == null) {
            goHome();
            return;
        }
        this.noteTmp.setTrashed(Boolean.valueOf(z));
        this.goBack = true;
        this.exitMessage = getString(z ? R.string.note_trashed : R.string.note_untrashed);
        this.exitCroutonStyle = z ? ONStyle.WARN : ONStyle.INFO;
        if (z) {
            ShortcutHelper.removeshortCut(OrsoNotes.getAppContext(), this.noteTmp);
            ReminderHelper.removeReminder(OrsoNotes.getAppContext(), this.noteTmp);
        } else {
            ReminderHelper.addReminder(OrsoNotes.getAppContext(), this.note);
        }
        saveNote(this);
    }

    private void deleteNote() {
        new MaterialDialog.Builder(this.notesMainActivity).content(R.string.delete_note_confirmation).positiveText(R.string.ok).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$O0sO_jpixvLQEp-WRQZ7EyNBVz4
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                this.f$0.lambda$deleteNote$18$DetailFragment(materialDialog, dialogAction);
            }
        }).build().show();
    }

    public /* synthetic */ void lambda$deleteNote$18$DetailFragment(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.notesMainActivity.deleteNote(this.noteTmp);
        LogDelegate.d("Deleted note with ID '" + this.noteTmp.get_id() + "'");
        this.notesMainActivity.showMessage(R.string.note_deleted, ONStyle.ALERT);
        goHome();
    }

    public void saveAndExit(OnNoteSaved onNoteSaved) {
        if (isAdded()) {
            this.exitMessage = getString(R.string.note_updated);
            this.exitCroutonStyle = ONStyle.CONFIRM;
            this.goBack = true;
            saveNote(onNoteSaved);
        }
    }

    void saveNote(OnNoteSaved onNoteSaved) {
        this.noteTmp.setTitle(getNoteTitle());
        this.noteTmp.setContent(getNoteContent());
        if (this.goBack && TextUtils.isEmpty(this.noteTmp.getTitle()) && TextUtils.isEmpty(this.noteTmp.getContent()) && this.noteTmp.getAttachmentsList().size() == 0) {
            LogDelegate.d("Empty note not saved");
            this.exitMessage = getString(R.string.empty_note_not_saved);
            this.exitCroutonStyle = ONStyle.INFO;
            goHome();
            return;
        }
        if (saveNotNeeded()) {
            this.exitMessage = "";
            if (this.goBack) {
                goHome();
                return;
            }
            return;
        }
        this.noteTmp.setAttachmentsListOld(this.note.getAttachmentsList());
        new SaveNoteTask(onNoteSaved, lastModificationUpdatedNeeded()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.noteTmp);
    }

    private boolean saveNotNeeded() {
        if (this.noteTmp.get_id() == null && this.prefs.getBoolean(ConstantsBase.PREF_AUTO_LOCATION, false)) {
            this.note.setLatitude(this.noteTmp.getLatitude());
            this.note.setLongitude(this.noteTmp.getLongitude());
        }
        return !this.noteTmp.isChanged(this.note) || (this.noteTmp.isLocked().booleanValue() && !this.noteTmp.isPasswordChecked());
    }

    private boolean lastModificationUpdatedNeeded() {
        this.note.setCategory(this.noteTmp.getCategory());
        this.note.setArchived(this.noteTmp.isArchived());
        this.note.setTrashed(this.noteTmp.isTrashed());
        this.note.setLocked(this.noteTmp.isLocked());
        return this.noteTmp.isChanged(this.note);
    }

    @Override // it.orsozoxi.android.orsonotes.models.listeners.OnNoteSaved
    public void onNoteSaved(Note note) throws NumberFormatException {
        NotesMainActivity.notifyAppWidgets(OrsoNotes.getAppContext());
        if (!this.activityPausing) {
            EventBus.getDefault().post(new NotesUpdatedEvent(Collections.singletonList(note)));
            deleteMergedNotes(this.mergedNotesIds);
            if (this.noteTmp.getAlarm() != null && !this.noteTmp.getAlarm().equals(this.note.getAlarm())) {
                ReminderHelper.showReminderMessage(String.valueOf(this.noteTmp.getAlarm()));
            }
        }
        this.note = new Note(note);
        if (this.goBack) {
            goHome();
        }
    }

    private void deleteMergedNotes(List<String> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (String str : list) {
                Note note = new Note();
                note.set_id(Long.valueOf(str));
                arrayList.add(note);
            }
            new NoteProcessorDelete(arrayList).process();
        }
    }

    private String getNoteTitle() {
        EditText editText = this.title;
        return (editText == null || TextUtils.isEmpty(editText.getText())) ? "" : this.title.getText().toString();
    }

    private String getNoteContent() {
        String string;
        if (!this.noteTmp.isChecklist().booleanValue()) {
            View viewFindViewById = this.root.findViewById(R.id.detail_content);
            if (viewFindViewById instanceof EditText) {
                string = ((EditText) viewFindViewById).getText().toString();
            } else {
                if (!(viewFindViewById instanceof android.widget.EditText)) {
                    return "";
                }
                string = ((android.widget.EditText) viewFindViewById).getText().toString();
            }
            return string;
        }
        ChecklistManager checklistManager = this.mChecklistManager;
        if (checklistManager == null) {
            return "";
        }
        checklistManager.keepChecked(true).showCheckMarks(true);
        return this.mChecklistManager.getText();
    }

    private void shareNote() {
        Note note = new Note(this.noteTmp);
        note.setTitle(getNoteTitle());
        note.setContent(getNoteContent());
        this.notesMainActivity.shareNote(note);
    }

    private void lockNote() {
        LogDelegate.d("Locking or unlocking note " + this.note.get_id());
        if (this.prefs.getString(ConstantsBase.PREF_PASSWORD, null) == null) {
            startActivityForResult(new Intent(this.notesMainActivity, (Class<?>) PasswordActivity.class), 3);
        } else if (this.noteTmp.isPasswordChecked() || this.prefs.getBoolean("settings_password_access", false)) {
            lockUnlock();
        } else {
            PasswordHelper.requestPassword(this.notesMainActivity, new PasswordValidator() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$Zh-dDNx0M8lcHOj1oQRTqqUejko
                @Override // it.orsozoxi.android.orsonotes.models.PasswordValidator
                public final void onPasswordValidated(PasswordValidator.Result result) {
                    this.f$0.lambda$lockNote$19$DetailFragment(result);
                }
            });
        }
    }

    /* renamed from: it.orsozoxi.android.orsonotes.DetailFragment$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$it$orsozoxi$android$orsonotes$models$PasswordValidator$Result;

        static {
            int[] iArr = new int[PasswordValidator.Result.values().length];
            $SwitchMap$it$orsozoxi$android$orsonotes$models$PasswordValidator$Result = iArr;
            try {
                iArr[PasswordValidator.Result.SUCCEED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$it$orsozoxi$android$orsonotes$models$PasswordValidator$Result[PasswordValidator.Result.FAIL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$it$orsozoxi$android$orsonotes$models$PasswordValidator$Result[PasswordValidator.Result.RESTORE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public /* synthetic */ void lambda$lockNote$19$DetailFragment(PasswordValidator.Result result) {
        if (AnonymousClass4.$SwitchMap$it$orsozoxi$android$orsonotes$models$PasswordValidator$Result[result.ordinal()] != 1) {
            return;
        }
        lockUnlock();
    }

    private void lockUnlock() {
        if (this.prefs.getString(ConstantsBase.PREF_PASSWORD, null) == null) {
            this.notesMainActivity.showMessage(R.string.password_not_set, ONStyle.WARN);
            return;
        }
        this.notesMainActivity.showMessage(R.string.save_note_to_lock_it, ONStyle.INFO);
        this.notesMainActivity.supportInvalidateOptionsMenu();
        this.noteTmp.setLocked(Boolean.valueOf(!r0.isLocked().booleanValue()));
        this.noteTmp.setPasswordChecked(true);
    }

    private String initReminder(Note note) throws NumberFormatException {
        if (this.noteTmp.getAlarm() == null) {
            return "";
        }
        long j = Long.parseLong(note.getAlarm());
        String recurrenceRule = note.getRecurrenceRule();
        if (!TextUtils.isEmpty(recurrenceRule)) {
            return RecurrenceHelper.getNoteRecurrentReminderText(j, recurrenceRule);
        }
        return RecurrenceHelper.getNoteReminderText(j);
    }

    private void playback(View view, Uri uri) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            if (this.isPlayingView != view) {
                stopPlaying();
                this.isPlayingView = view;
                startPlaying(uri);
                replacePlayingAudioBitmap(view);
                return;
            }
            stopPlaying();
            return;
        }
        this.isPlayingView = view;
        startPlaying(uri);
        replacePlayingAudioBitmap(view);
    }

    private void replacePlayingAudioBitmap(View view) {
        Drawable drawable = ((ImageView) view.findViewById(R.id.gridview_item_picture)).getDrawable();
        if (BitmapDrawable.class.isAssignableFrom(drawable.getClass())) {
            this.recordingBitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            this.recordingBitmap = ((BitmapDrawable) drawable.getCurrent()).getBitmap();
        }
        ((ImageView) view.findViewById(R.id.gridview_item_picture)).setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this.notesMainActivity.getResources(), R.drawable.stop), 300, 300));
    }

    private void startPlaying(Uri uri) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (this.mPlayer == null) {
            this.mPlayer = new MediaPlayer();
        }
        try {
            this.mPlayer.setDataSource(this.notesMainActivity, uri);
            this.mPlayer.prepare();
            this.mPlayer.start();
            this.mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$728rp2GsH9CQPuiYw6tCXSiWU-I
                @Override // android.media.MediaPlayer.OnCompletionListener
                public final void onCompletion(MediaPlayer mediaPlayer) {
                    this.f$0.lambda$startPlaying$20$DetailFragment(mediaPlayer);
                }
            });
        } catch (IOException e) {
            LogDelegate.e("prepare() failed", e);
            this.notesMainActivity.showMessage(R.string.error, ONStyle.ALERT);
        }
    }

    public /* synthetic */ void lambda$startPlaying$20$DetailFragment(MediaPlayer mediaPlayer) {
        this.mPlayer = null;
        View view = this.isPlayingView;
        if (view != null) {
            ((ImageView) view.findViewById(R.id.gridview_item_picture)).setImageBitmap(this.recordingBitmap);
            this.recordingBitmap = null;
            this.isPlayingView = null;
        }
    }

    private void stopPlaying() {
        if (this.mPlayer != null) {
            View view = this.isPlayingView;
            if (view != null) {
                ((ImageView) view.findViewById(R.id.gridview_item_picture)).setImageBitmap(this.recordingBitmap);
            }
            this.isPlayingView = null;
            this.recordingBitmap = null;
            this.mPlayer.release();
            this.mPlayer = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRecording(final View view) {
        PermissionsHelper.requestPermission(getActivity(), "android.permission.RECORD_AUDIO", R.string.permission_audio_recording, this.snackBarPlaceholder, new OnPermissionRequestedListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$i8rdhVl81r4gRaUlyYd_43gbjrI
            @Override // it.orsozoxi.android.orsonotes.models.listeners.OnPermissionRequestedListener
            public final void onPermissionGranted() throws IllegalStateException, IOException {
                this.f$0.lambda$startRecording$21$DetailFragment(view);
            }
        });
    }

    public /* synthetic */ void lambda$startRecording$21$DetailFragment(View view) throws IllegalStateException, IOException {
        this.isRecording = true;
        toggleAudioRecordingStop(view);
        File fileCreateNewAttachmentFile = StorageHelper.createNewAttachmentFile(this.notesMainActivity, ConstantsBase.MIME_TYPE_AUDIO_EXT);
        if (fileCreateNewAttachmentFile == null) {
            this.notesMainActivity.showMessage(R.string.error, ONStyle.ALERT);
            return;
        }
        if (this.mRecorder == null) {
            MediaRecorder mediaRecorder = new MediaRecorder();
            this.mRecorder = mediaRecorder;
            mediaRecorder.setAudioSource(1);
            this.mRecorder.setOutputFormat(2);
            this.mRecorder.setAudioEncoder(3);
            this.mRecorder.setAudioEncodingBitRate(96000);
            this.mRecorder.setAudioSamplingRate(44100);
        }
        String absolutePath = fileCreateNewAttachmentFile.getAbsolutePath();
        this.recordName = absolutePath;
        this.mRecorder.setOutputFile(absolutePath);
        try {
            this.audioRecordingTimeStart = Calendar.getInstance().getTimeInMillis();
            this.mRecorder.prepare();
            this.mRecorder.start();
        } catch (IOException | IllegalStateException e) {
            LogDelegate.e("prepare() failed", e);
            this.notesMainActivity.showMessage(R.string.error, ONStyle.ALERT);
        }
    }

    private void toggleAudioRecordingStop(View view) {
        if (this.isRecording) {
            android.widget.TextView textView = (android.widget.TextView) view;
            textView.setText(getString(R.string.stop));
            textView.setTextColor(Color.parseColor("#ff0000"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopRecording() throws IllegalStateException {
        this.isRecording = false;
        MediaRecorder mediaRecorder = this.mRecorder;
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            this.audioRecordingTime = Calendar.getInstance().getTimeInMillis() - this.audioRecordingTimeStart;
            this.mRecorder.release();
            this.mRecorder = null;
        }
    }

    private void fade(final View view, boolean z) throws Resources.NotFoundException {
        final int i;
        int i2 = R.animator.fade_out_support;
        if (z) {
            i2 = R.animator.fade_in_support;
            i = 0;
        } else {
            i = 8;
        }
        NotesMainActivity notesMainActivity = this.notesMainActivity;
        if (notesMainActivity != null) {
            Animation animationLoadAnimation = AnimationUtils.loadAnimation(notesMainActivity, i2);
            animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: it.orsozoxi.android.orsonotes.DetailFragment.3
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(i);
                }
            });
            view.startAnimation(animationLoadAnimation);
        }
    }

    private void addShortcut() {
        ShortcutHelper.addShortcut(OrsoNotes.getAppContext(), this.noteTmp);
        this.notesMainActivity.showMessage(R.string.shortcut_added, ONStyle.INFO);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            LogDelegate.v("MotionEvent.ACTION_DOWN");
            int i = Display.getUsableSize(this.notesMainActivity).x;
            if (x < 30 || x > i - 30) {
                this.swiping = true;
                this.startSwipeX = x;
            }
        } else if (action == 1) {
            LogDelegate.v("MotionEvent.ACTION_UP");
            if (this.swiping) {
                this.swiping = false;
            }
        } else if (action == 2) {
            if (this.swiping) {
                LogDelegate.v("MotionEvent.ACTION_MOVE at position " + x + ", " + y);
                if (Math.abs(x - this.startSwipeX) > 100) {
                    this.swiping = false;
                    FragmentTransaction fragmentTransactionBeginTransaction = this.notesMainActivity.getSupportFragmentManager().beginTransaction();
                    this.notesMainActivity.animateTransition(fragmentTransactionBeginTransaction, 0);
                    DetailFragment detailFragment = new DetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ConstantsBase.INTENT_NOTE, new Note());
                    detailFragment.setArguments(bundle);
                    fragmentTransactionBeginTransaction.replace(R.id.fragment_container, detailFragment, NotesMainActivity.FRAGMENT_DETAIL_TAG).addToBackStack(NotesMainActivity.FRAGMENT_DETAIL_TAG).commit();
                }
            }
        } else {
            LogDelegate.e("Wrong element choosen: " + motionEvent.getAction());
        }
        return true;
    }

    @Override // it.orsozoxi.android.orsonotes.models.listeners.OnAttachingFileListener
    public void onAttachingFileErrorOccurred(Attachment attachment) {
        this.notesMainActivity.showMessage(R.string.error_saving_attachments, ONStyle.ALERT);
        if (this.noteTmp.getAttachmentsList().contains(attachment)) {
            removeAttachment(attachment);
            this.mAttachmentAdapter.notifyDataSetChanged();
            this.mGridView.autoresize();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAttachment(Attachment attachment) {
        this.noteTmp.addAttachment(attachment);
    }

    private void removeAttachment(Attachment attachment) {
        this.noteTmp.removeAttachment(attachment);
    }

    private void removeAttachment(int i) {
        Note note = this.noteTmp;
        note.removeAttachment(note.getAttachmentsList().get(i));
    }

    private void removeAllAttachments() {
        this.noteTmp.setAttachmentsList(new ArrayList<>());
        this.mAttachmentAdapter = new AttachmentAdapter(this.notesMainActivity, new ArrayList(), this.mGridView);
        this.mGridView.invalidateViews();
        this.mGridView.setAdapter((ListAdapter) this.mAttachmentAdapter);
    }

    @Override // it.orsozoxi.android.orsonotes.models.listeners.OnAttachingFileListener
    public void onAttachingFileFinished(Attachment attachment) {
        addAttachment(attachment);
        this.mAttachmentAdapter.notifyDataSetChanged();
        this.mGridView.autoresize();
    }

    @Override // it.orsozoxi.android.orsonotes.models.listeners.OnReminderPickedListener
    public void onReminderPicked(long j) {
        this.noteTmp.setAlarm(j);
        if (this.mFragment.isAdded()) {
            this.reminderIcon.setImageResource(R.drawable.ic_alarm_black_18dp);
            this.datetime.setText(RecurrenceHelper.getNoteReminderText(j));
        }
    }

    @Override // it.orsozoxi.android.orsonotes.models.listeners.OnReminderPickedListener
    public void onRecurrenceReminderPicked(String str) {
        this.noteTmp.setRecurrenceRule(str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        LogDelegate.d("Recurrent reminder set: " + str);
        this.datetime.setText(RecurrenceHelper.getNoteRecurrentReminderText(Long.parseLong(this.noteTmp.getAlarm()), str));
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        scrollContent();
    }

    @Override // it.feio.android.checklistview.interfaces.CheckListChangedListener
    public void onCheckListChanged() {
        scrollContent();
    }

    private void scrollContent() {
        if (this.noteTmp.isChecklist().booleanValue()) {
            if (this.mChecklistManager.getCount() > this.contentLineCounter) {
                this.scrollView.scrollBy(0, 60);
            }
            this.contentLineCounter = this.mChecklistManager.getCount();
        } else {
            if (this.content.getLineCount() > this.contentLineCounter) {
                this.scrollView.scrollBy(0, 60);
            }
            this.contentLineCounter = this.content.getLineCount();
        }
    }

    private void addTags() {
        this.contentCursorPosition = getCursorIndex();
        final List<Tag> allTags = TagsHelper.getAllTags();
        if (allTags.isEmpty()) {
            this.notesMainActivity.showMessage(R.string.no_tags_created, ONStyle.WARN);
            return;
        }
        final Note note = new Note();
        note.setTitle(getNoteTitle());
        note.setContent(getNoteContent());
        new MaterialDialog.Builder(this.notesMainActivity).title(R.string.select_tags).positiveText(R.string.ok).items(TagsHelper.getTagsArray(allTags)).itemsCallbackMultiChoice(TagsHelper.getPreselectedTagsArray(note, allTags), new MaterialDialog.ListCallbackMultiChoice() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$zkMzbe9TW8KfpRhjAlx2MJZrJus
            @Override // com.afollestad.materialdialogs.MaterialDialog.ListCallbackMultiChoice
            public final boolean onSelection(MaterialDialog materialDialog, Integer[] numArr, CharSequence[] charSequenceArr) {
                return this.f$0.lambda$addTags$22$DetailFragment(allTags, note, materialDialog, numArr, charSequenceArr);
            }
        }).build().show();
    }

    public /* synthetic */ boolean lambda$addTags$22$DetailFragment(List list, Note note, MaterialDialog materialDialog, Integer[] numArr, CharSequence[] charSequenceArr) {
        materialDialog.dismiss();
        tagNote(list, numArr, note);
        return false;
    }

    private void tagNote(List<Tag> list, Integer[] numArr, Note note) {
        Pair<String, List<Tag>> pairAddTagToNote = TagsHelper.addTagToNote(list, numArr, note);
        if (this.noteTmp.isChecklist().booleanValue()) {
            CheckListViewItem focusedItemView = this.mChecklistManager.getFocusedItemView();
            if (focusedItemView != null) {
                StringBuilder sb = new StringBuilder(focusedItemView.getText());
                sb.insert(this.contentCursorPosition, " " + pairAddTagToNote.first + " ");
                focusedItemView.setText(sb.toString());
                focusedItemView.getEditText().setSelection(this.contentCursorPosition + pairAddTagToNote.first.length() + 1);
            } else {
                this.title.append(" " + pairAddTagToNote.first);
            }
        } else {
            StringBuilder sb2 = new StringBuilder(getNoteContent());
            if (this.content.hasFocus()) {
                sb2.insert(this.contentCursorPosition, " " + pairAddTagToNote.first + " ");
                this.content.setText(sb2.toString());
                this.content.setSelection(this.contentCursorPosition + pairAddTagToNote.first.length() + 1);
            } else {
                if (getNoteContent().trim().length() > 0) {
                    sb2.append(System.getProperty("line.separator"));
                    sb2.append(System.getProperty("line.separator"));
                }
                sb2.append(pairAddTagToNote.first);
                this.content.setText(sb2.toString());
            }
        }
        if (pairAddTagToNote.second.isEmpty()) {
            return;
        }
        if (this.noteTmp.isChecklist().booleanValue()) {
            toggleChecklist2(true, true);
        }
        Pair<String, String> pairRemoveTag = TagsHelper.removeTag(getNoteTitle(), getNoteContent(), pairAddTagToNote.second);
        this.title.setText(pairRemoveTag.first);
        this.content.setText(pairRemoveTag.second);
        if (this.noteTmp.isChecklist().booleanValue()) {
            toggleChecklist2();
        }
    }

    private int getCursorIndex() {
        if (!this.noteTmp.isChecklist().booleanValue()) {
            return this.content.getSelectionStart();
        }
        CheckListViewItem focusedItemView = this.mChecklistManager.getFocusedItemView();
        if (focusedItemView != null) {
            return focusedItemView.getEditText().getSelectionStart();
        }
        return 0;
    }

    public Note getCurrentNote() {
        return this.note;
    }

    private boolean isNoteLocationValid() {
        return (this.noteTmp.getLatitude() == null || this.noteTmp.getLatitude().doubleValue() == 0.0d || this.noteTmp.getLongitude() == null || this.noteTmp.getLongitude().doubleValue() == 0.0d) ? false : true;
    }

    public void startGetContentAction() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("*/*");
        startActivityForResult(intent, 7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void askReadExternalStoragePermission() {
        PermissionsHelper.requestPermission(getActivity(), "android.permission.READ_EXTERNAL_STORAGE", R.string.permission_external_storage_detail_attachment, this.snackBarPlaceholder, new OnPermissionRequestedListener() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$iqwWmeFkC4e6UKmCKNNXFV21Z8U
            @Override // it.orsozoxi.android.orsonotes.models.listeners.OnPermissionRequestedListener
            public final void onPermissionGranted() {
                this.f$0.startGetContentAction();
            }
        });
    }

    public void onEventMainThread(PushbulletReplyEvent pushbulletReplyEvent) {
        this.content.setText(getNoteContent() + System.getProperty("line.separator") + pushbulletReplyEvent.message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnGeoUtilResultListenerImpl implements OnGeoUtilResultListener {
        private final WeakReference<DetailFragment> detailFragmentWeakReference;
        private final WeakReference<NotesMainActivity> mainActivityWeakReference;
        private final WeakReference<Note> noteTmpWeakReference;

        @Override // it.orsozoxi.android.orsonotes.models.listeners.OnGeoUtilResultListener
        public void onAddressResolved(String str) {
        }

        @Override // it.orsozoxi.android.orsonotes.models.listeners.OnGeoUtilResultListener
        public void onCoordinatesResolved(Location location, String str) {
        }

        OnGeoUtilResultListenerImpl(NotesMainActivity notesMainActivity, DetailFragment detailFragment, Note note) {
            this.mainActivityWeakReference = new WeakReference<>(notesMainActivity);
            this.detailFragmentWeakReference = new WeakReference<>(detailFragment);
            this.noteTmpWeakReference = new WeakReference<>(note);
        }

        @Override // it.orsozoxi.android.orsonotes.models.listeners.OnGeoUtilResultListener
        public void onLocationUnavailable() {
            this.mainActivityWeakReference.get().showMessage(R.string.location_not_found, ONStyle.ALERT);
        }

        @Override // it.orsozoxi.android.orsonotes.models.listeners.OnGeoUtilResultListener
        public void onLocationRetrieved(final Location location) {
            if (checkWeakReferences() && location != null) {
                if (!ConnectionManager.internetAvailable(this.mainActivityWeakReference.get())) {
                    this.noteTmpWeakReference.get().setLatitude(Double.valueOf(location.getLatitude()));
                    this.noteTmpWeakReference.get().setLongitude(Double.valueOf(location.getLongitude()));
                    onAddressResolved("");
                } else {
                    final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) this.mainActivityWeakReference.get().getLayoutInflater().inflate(R.layout.dialog_location, (ViewGroup) null).findViewById(R.id.auto_complete_location);
                    autoCompleteTextView.setHint(this.mainActivityWeakReference.get().getString(R.string.search_location));
                    autoCompleteTextView.setAdapter(new PlacesAutoCompleteAdapter(this.mainActivityWeakReference.get(), R.layout.simple_text_layout));
                    final MaterialDialog materialDialogBuild = new MaterialDialog.Builder(this.mainActivityWeakReference.get()).customView((View) autoCompleteTextView, false).positiveText(R.string.use_current_location).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.orsozoxi.android.orsonotes.-$$Lambda$DetailFragment$OnGeoUtilResultListenerImpl$19bUdTZJJkvEa3-D9TDMXoxkUsg
                        @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
                        public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                            this.f$0.lambda$onLocationRetrieved$0$DetailFragment$OnGeoUtilResultListenerImpl(autoCompleteTextView, location, materialDialog, dialogAction);
                        }
                    }).build();
                    autoCompleteTextView.addTextChangedListener(new TextWatcher() { // from class: it.orsozoxi.android.orsonotes.DetailFragment.OnGeoUtilResultListenerImpl.1
                        @Override // android.text.TextWatcher
                        public void afterTextChanged(Editable editable) {
                        }

                        @Override // android.text.TextWatcher
                        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        }

                        @Override // android.text.TextWatcher
                        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                            if (charSequence.length() != 0) {
                                materialDialogBuild.setActionButton(DialogAction.POSITIVE, ((NotesMainActivity) OnGeoUtilResultListenerImpl.this.mainActivityWeakReference.get()).getString(R.string.confirm));
                            } else {
                                materialDialogBuild.setActionButton(DialogAction.POSITIVE, ((NotesMainActivity) OnGeoUtilResultListenerImpl.this.mainActivityWeakReference.get()).getString(R.string.use_current_location));
                            }
                        }
                    });
                    materialDialogBuild.show();
                }
            }
        }

        public /* synthetic */ void lambda$onLocationRetrieved$0$DetailFragment$OnGeoUtilResultListenerImpl(AutoCompleteTextView autoCompleteTextView, Location location, MaterialDialog materialDialog, DialogAction dialogAction) {
            if (TextUtils.isEmpty(autoCompleteTextView.getText().toString())) {
                this.noteTmpWeakReference.get().setLatitude(Double.valueOf(location.getLatitude()));
                this.noteTmpWeakReference.get().setLongitude(Double.valueOf(location.getLongitude()));
            }
        }

        private boolean checkWeakReferences() {
            return (this.mainActivityWeakReference.get() == null || this.mainActivityWeakReference.get().isFinishing() || this.detailFragmentWeakReference.get() == null || this.noteTmpWeakReference.get() == null) ? false : true;
        }
    }

    private class AttachmentOnClickListener implements View.OnClickListener {
        private AttachmentOnClickListener() {
        }

        /* synthetic */ AttachmentOnClickListener(DetailFragment detailFragment, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) throws IllegalStateException {
            int id = view.getId();
            if (id == R.id.recording) {
                if (!DetailFragment.this.isRecording) {
                    DetailFragment.this.startRecording(view);
                } else {
                    DetailFragment.this.stopRecording();
                    Attachment attachment = new Attachment(Uri.fromFile(new File(DetailFragment.this.recordName)), ConstantsBase.MIME_TYPE_AUDIO);
                    attachment.setLength(DetailFragment.this.audioRecordingTime);
                    DetailFragment.this.addAttachment(attachment);
                    DetailFragment.this.mAttachmentAdapter.notifyDataSetChanged();
                    DetailFragment.this.mGridView.autoresize();
                }
            } else if (id == R.id.files) {
                if (ContextCompat.checkSelfPermission(DetailFragment.this.getActivity(), "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                    DetailFragment.this.askReadExternalStoragePermission();
                } else {
                    DetailFragment.this.startGetContentAction();
                }
            } else if (id == R.id.sketch) {
                DetailFragment.this.takeSketch(null);
            } else if (id == R.id.timestamp) {
                DetailFragment.this.addTimestamp();
            } else {
                LogDelegate.e("Wrong element choosen: " + view.getId());
            }
            if (DetailFragment.this.isRecording) {
                return;
            }
            DetailFragment.this.attachmentDialog.dismiss();
        }
    }
}
