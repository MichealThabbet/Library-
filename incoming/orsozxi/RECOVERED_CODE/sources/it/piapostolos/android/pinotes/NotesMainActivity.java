package it.piapostolos.android.pinotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.ViewGroup;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import it.piapostolos.android.pinotes.R2;
import it.piapostolos.android.pinotes.async.UpdateWidgetsTask;
import it.piapostolos.android.pinotes.async.bus.PasswordRemovedEvent;
import it.piapostolos.android.pinotes.async.bus.SwitchFragmentEvent;
import it.piapostolos.android.pinotes.async.notes.NoteProcessorDelete;
import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import it.piapostolos.android.pinotes.helpers.NotesHelper;
import it.piapostolos.android.pinotes.models.Attachment;
import it.piapostolos.android.pinotes.models.Category;
import it.piapostolos.android.pinotes.models.Note;
import it.piapostolos.android.pinotes.models.ONStyle;
import it.piapostolos.android.pinotes.models.PasswordValidator;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import it.piapostolos.android.pinotes.utils.FileProviderHelper;
import it.piapostolos.android.pinotes.utils.PasswordHelper;
import it.piapostolos.android.pinotes.utils.SystemHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.apache.http.protocol.HTTP;

/* loaded from: classes3.dex */
public class NotesMainActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String FRAGMENT_DETAIL_TAG = "fragment_detail";
    public static final String FRAGMENT_DRAWER_TAG = "fragment_drawer";
    public static final String FRAGMENT_LIST_TAG = "fragment_list";
    public static final String FRAGMENT_SKETCH_TAG = "fragment_sketch";
    private static boolean isPasswordAccepted = false;

    @BindView(R2.id.crouton_handle)
    ViewGroup croutonViewContainer;

    @BindView(R2.id.drawer_layout)
    DrawerLayout drawerLayout;
    private FragmentManager mFragmentManager;
    boolean prefsChanged = false;
    public Uri sketchUri;

    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    @Override // it.piapostolos.android.pinotes.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(R.style.PiNotesTheme_ApiSpec);
        setContentView(R.layout.activity_notes_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        this.prefs.registerOnSharedPreferenceChangeListener(this);
        initUI();
    }

    @Override // it.piapostolos.android.pinotes.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (isPasswordAccepted) {
            init();
        } else {
            checkPassword();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initUI() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void checkPassword() {
        if (this.prefs.getString(ConstantsBase.PREF_PASSWORD, null) != null && this.prefs.getBoolean("settings_password_access", false)) {
            PasswordHelper.requestPassword(this, new PasswordValidator() { // from class: it.piapostolos.android.pinotes.-$$Lambda$NotesMainActivity$rxwsRMO3KjlxeJWs7uEOTSEIZNQ
                @Override // it.piapostolos.android.pinotes.models.PasswordValidator
                public final void onPasswordValidated(PasswordValidator.Result result) {
                    this.f$0.lambda$checkPassword$0$NotesMainActivity(result);
                }
            });
        } else {
            init();
        }
    }

    /* renamed from: it.piapostolos.android.pinotes.NotesMainActivity$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$it$piapostolos$android$pinotes$models$PasswordValidator$Result;

        static {
            int[] iArr = new int[PasswordValidator.Result.values().length];
            $SwitchMap$it$piapostolos$android$pinotes$models$PasswordValidator$Result = iArr;
            try {
                iArr[PasswordValidator.Result.SUCCEED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$it$piapostolos$android$pinotes$models$PasswordValidator$Result[PasswordValidator.Result.FAIL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public /* synthetic */ void lambda$checkPassword$0$NotesMainActivity(PasswordValidator.Result result) {
        int i = AnonymousClass1.$SwitchMap$it$piapostolos$android$pinotes$models$PasswordValidator$Result[result.ordinal()];
        if (i == 1) {
            init();
        } else if (i == 2) {
            finish();
        } else {
            PasswordHelper.resetPassword(this);
        }
    }

    public void onEvent(PasswordRemovedEvent passwordRemovedEvent) {
        showMessage(R.string.password_successfully_removed, ONStyle.ALERT);
        init();
    }

    private void init() {
        isPasswordAccepted = true;
        getFragmentManagerInstance();
        if (((NavigationDrawerFragment) getFragmentManagerInstance().findFragmentById(R.id.navigation_drawer)) == null) {
            getFragmentManagerInstance().beginTransaction().replace(R.id.navigation_drawer, new NavigationDrawerFragment(), FRAGMENT_DRAWER_TAG).commit();
        }
        if (getFragmentManagerInstance().findFragmentByTag(FRAGMENT_LIST_TAG) == null) {
            getFragmentManagerInstance().beginTransaction().add(R.id.fragment_container, new ListFragment(), FRAGMENT_LIST_TAG).commit();
        }
        handleIntents();
    }

    private FragmentManager getFragmentManagerInstance() {
        if (this.mFragmentManager == null) {
            this.mFragmentManager = getSupportFragmentManager();
        }
        return this.mFragmentManager;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        if (intent.getAction() == null) {
            intent.setAction(ConstantsBase.ACTION_START_APP);
        }
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntents();
        LogDelegate.d("onNewIntent");
    }

    public MenuItem getSearchMenuItem() {
        Fragment fragmentCheckFragmentInstance = checkFragmentInstance(R.id.fragment_container, ListFragment.class);
        if (fragmentCheckFragmentInstance != null) {
            return ((ListFragment) fragmentCheckFragmentInstance).getSearchMenuItem();
        }
        return null;
    }

    public void editTag(Category category) {
        Fragment fragmentCheckFragmentInstance = checkFragmentInstance(R.id.fragment_container, ListFragment.class);
        if (fragmentCheckFragmentInstance != null) {
            ((ListFragment) fragmentCheckFragmentInstance).editCategory(category);
        }
    }

    public void initNotesList(final Intent intent) {
        final Fragment fragmentCheckFragmentInstance = checkFragmentInstance(R.id.fragment_container, ListFragment.class);
        if (fragmentCheckFragmentInstance != null) {
            new Handler(getMainLooper()).post(new Runnable() { // from class: it.piapostolos.android.pinotes.-$$Lambda$NotesMainActivity$ZJOR81bYpP24QufYqWluZKeW0LA
                @Override // java.lang.Runnable
                public final void run() {
                    NotesMainActivity.lambda$initNotesList$1(fragmentCheckFragmentInstance, intent);
                }
            });
        }
    }

    static /* synthetic */ void lambda$initNotesList$1(Fragment fragment, Intent intent) {
        ListFragment listFragment = (ListFragment) fragment;
        listFragment.toggleSearchLabel(false);
        listFragment.initNotesList(intent);
    }

    public void commitPending() {
        Fragment fragmentCheckFragmentInstance = checkFragmentInstance(R.id.fragment_container, ListFragment.class);
        if (fragmentCheckFragmentInstance != null) {
            ((ListFragment) fragmentCheckFragmentInstance).commitPending();
        }
    }

    private Fragment checkFragmentInstance(int i, Object obj) {
        Fragment fragmentFindFragmentById = getFragmentManagerInstance().findFragmentById(i);
        if (fragmentFindFragmentById == null || !obj.equals(fragmentFindFragmentById.getClass())) {
            return null;
        }
        return fragmentFindFragmentById;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        Fragment fragmentCheckFragmentInstance = checkFragmentInstance(R.id.fragment_container, SketchFragment.class);
        if (fragmentCheckFragmentInstance != null) {
            ((SketchFragment) fragmentCheckFragmentInstance).save();
            setRequestedOrientation(-1);
            getFragmentManagerInstance().popBackStack();
            return;
        }
        Fragment fragmentCheckFragmentInstance2 = checkFragmentInstance(R.id.fragment_container, DetailFragment.class);
        if (fragmentCheckFragmentInstance2 != null) {
            DetailFragment detailFragment = (DetailFragment) fragmentCheckFragmentInstance2;
            detailFragment.goBack = true;
            detailFragment.saveAndExit(detailFragment);
            return;
        }
        Fragment fragmentCheckFragmentInstance3 = checkFragmentInstance(R.id.fragment_container, ListFragment.class);
        if (fragmentCheckFragmentInstance3 != null) {
            if (this.prefs.getBoolean("settings_navdrawer_on_exit", false) && getDrawerLayout() != null && !getDrawerLayout().isDrawerOpen(8388611)) {
                getDrawerLayout().openDrawer(8388611);
                return;
            }
            if (!this.prefs.getBoolean("settings_navdrawer_on_exit", false) && getDrawerLayout() != null && getDrawerLayout().isDrawerOpen(8388611)) {
                getDrawerLayout().closeDrawer(8388611);
                return;
            } else {
                if (((ListFragment) fragmentCheckFragmentInstance3).closeFab()) {
                    return;
                }
                isPasswordAccepted = false;
                super.onBackPressed();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("navigationTmp", this.navigationTmp);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        Crouton.cancelAllCroutons();
    }

    public DrawerLayout getDrawerLayout() {
        return this.drawerLayout;
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        if (getFragmentManagerInstance().findFragmentById(R.id.navigation_drawer) != null) {
            return ((NavigationDrawerFragment) getFragmentManagerInstance().findFragmentById(R.id.navigation_drawer)).mDrawerToggle;
        }
        return null;
    }

    public void finishActionMode() {
        ListFragment listFragment = (ListFragment) getFragmentManagerInstance().findFragmentByTag(FRAGMENT_LIST_TAG);
        if (listFragment != null) {
            listFragment.finishActionMode();
        }
    }

    Toolbar getToolbar() {
        return this.toolbar;
    }

    private void handleIntents() {
        Intent intent = getIntent();
        if (intent.getAction() == null) {
            return;
        }
        if (ConstantsBase.ACTION_RESTART_APP.equals(intent.getAction())) {
            SystemHelper.restartApp(getApplicationContext(), NotesMainActivity.class);
        }
        if (receivedIntent(intent)) {
            Note note = (Note) intent.getParcelableExtra(ConstantsBase.INTENT_NOTE);
            if (note == null) {
                note = DbHelper.getInstance().getNote(intent.getIntExtra("note_id", 0));
            }
            if (note == null || !noteAlreadyOpened(note)) {
                if (note == null) {
                    note = new Note();
                }
                switchToDetail(note);
                return;
            }
            return;
        }
        if (ConstantsBase.ACTION_SEND_AND_EXIT.equals(intent.getAction())) {
            saveAndExit(intent);
        } else if ("android.intent.action.VIEW".equals(intent.getAction())) {
            switchToList();
        } else if (ConstantsBase.ACTION_SHORTCUT_WIDGET.equals(intent.getAction())) {
            switchToDetail(new Note());
        }
    }

    private void saveAndExit(Intent intent) {
        Note note = new Note();
        note.setTitle(intent.getStringExtra("android.intent.extra.SUBJECT"));
        note.setContent(intent.getStringExtra("android.intent.extra.TEXT"));
        DbHelper.getInstance().updateNote(note, true);
        showToast(getString(R.string.note_updated), 0);
        finish();
    }

    private boolean receivedIntent(Intent intent) {
        return ConstantsBase.ACTION_SHORTCUT.equals(intent.getAction()) || ConstantsBase.ACTION_NOTIFICATION_CLICK.equals(intent.getAction()) || ConstantsBase.ACTION_WIDGET.equals(intent.getAction()) || ConstantsBase.ACTION_WIDGET_TAKE_PHOTO.equals(intent.getAction()) || (("android.intent.action.SEND".equals(intent.getAction()) || "android.intent.action.SEND_MULTIPLE".equals(intent.getAction()) || ConstantsBase.INTENT_GOOGLE_NOW.equals(intent.getAction())) && intent.getType() != null) || intent.getAction().contains(ConstantsBase.ACTION_NOTIFICATION_CLICK);
    }

    private boolean noteAlreadyOpened(Note note) {
        DetailFragment detailFragment = (DetailFragment) getFragmentManagerInstance().findFragmentByTag(FRAGMENT_DETAIL_TAG);
        return detailFragment != null && NotesHelper.haveSameId(note, detailFragment.getCurrentNote());
    }

    public void switchToList() {
        FragmentTransaction fragmentTransactionBeginTransaction = getFragmentManagerInstance().beginTransaction();
        animateTransition(fragmentTransactionBeginTransaction, 1);
        fragmentTransactionBeginTransaction.replace(R.id.fragment_container, new ListFragment(), FRAGMENT_LIST_TAG).addToBackStack(FRAGMENT_DETAIL_TAG).commitAllowingStateLoss();
        if (getDrawerToggle() != null) {
            getDrawerToggle().setDrawerIndicatorEnabled(false);
        }
        getFragmentManagerInstance().getFragments();
        EventBus.getDefault().post(new SwitchFragmentEvent(SwitchFragmentEvent.Direction.PARENT));
    }

    public void switchToDetail(Note note) {
        FragmentTransaction fragmentTransactionBeginTransaction = getFragmentManagerInstance().beginTransaction();
        animateTransition(fragmentTransactionBeginTransaction, 1);
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantsBase.INTENT_NOTE, note);
        detailFragment.setArguments(bundle);
        if (getFragmentManagerInstance().findFragmentByTag(FRAGMENT_DETAIL_TAG) == null) {
            fragmentTransactionBeginTransaction.replace(R.id.fragment_container, detailFragment, FRAGMENT_DETAIL_TAG).addToBackStack(FRAGMENT_LIST_TAG).commitAllowingStateLoss();
        } else {
            getFragmentManagerInstance().popBackStackImmediate();
            fragmentTransactionBeginTransaction.replace(R.id.fragment_container, detailFragment, FRAGMENT_DETAIL_TAG).addToBackStack(FRAGMENT_DETAIL_TAG).commitAllowingStateLoss();
        }
    }

    public void shareNote(Note note) {
        String title = note.getTitle();
        String str = title + System.getProperty("line.separator") + note.getContent();
        Intent intent = new Intent();
        if (note.getAttachmentsList().isEmpty()) {
            intent.setAction("android.intent.action.SEND");
            intent.setType(HTTP.PLAIN_TEXT_TYPE);
        } else if (note.getAttachmentsList().size() == 1) {
            intent.setAction("android.intent.action.SEND");
            Attachment attachment = note.getAttachmentsList().get(0);
            intent.setType(attachment.getMime_type());
            intent.putExtra("android.intent.extra.STREAM", FileProviderHelper.getShareableUri(attachment));
        } else if (note.getAttachmentsList().size() > 1) {
            intent.setAction("android.intent.action.SEND_MULTIPLE");
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            HashMap map = new HashMap();
            for (Attachment attachment2 : note.getAttachmentsList()) {
                arrayList.add(FileProviderHelper.getShareableUri(attachment2));
                map.put(attachment2.getMime_type(), true);
            }
            if (map.size() > 1) {
                intent.setType("*/*");
            } else {
                intent.setType((String) map.keySet().toArray()[0]);
            }
            intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
        }
        intent.putExtra("android.intent.extra.SUBJECT", title);
        intent.putExtra("android.intent.extra.TEXT", str);
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_message_chooser)));
    }

    public void deleteNote(Note note) {
        new NoteProcessorDelete(Collections.singletonList(note)).process();
        BaseActivity.notifyAppWidgets(this);
        LogDelegate.d("Deleted permanently note with ID '" + note.get_id() + "'");
    }

    public void updateWidgets() {
        new UpdateWidgetsTask(getApplicationContext()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public void showMessage(int i, Style style) {
        showMessage(getString(i), style);
    }

    public /* synthetic */ void lambda$showMessage$2$NotesMainActivity(String str, Style style) {
        Crouton.makeText(this, str, style, this.croutonViewContainer).show();
    }

    public void showMessage(final String str, final Style style) {
        runOnUiThread(new Runnable() { // from class: it.piapostolos.android.pinotes.-$$Lambda$NotesMainActivity$PCLGVbK7FiMQXq1IXg_ClStkKV4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showMessage$2$NotesMainActivity(str, style);
            }
        });
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        this.prefsChanged = true;
    }
}
