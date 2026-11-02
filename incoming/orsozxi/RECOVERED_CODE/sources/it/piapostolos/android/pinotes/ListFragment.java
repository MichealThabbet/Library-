package it.piapostolos.android.pinotes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.SearchView;
import androidx.core.util.Pair;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.neopixl.pixlui.components.textview.TextView;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.pnikosis.materialishprogress.ProgressWheel;
import de.greenrobot.event.EventBus;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import it.feio.android.pixlui.links.UrlCompleter;
import it.feio.android.simplegallery.util.BitmapUtils;
import it.piapostolos.android.pinotes.R2;
import it.piapostolos.android.pinotes.async.bus.CategoriesUpdatedEvent;
import it.piapostolos.android.pinotes.async.bus.NavigationUpdatedNavDrawerClosedEvent;
import it.piapostolos.android.pinotes.async.bus.NotesLoadedEvent;
import it.piapostolos.android.pinotes.async.bus.NotesMergeEvent;
import it.piapostolos.android.pinotes.async.bus.PasswordRemovedEvent;
import it.piapostolos.android.pinotes.async.notes.NoteLoaderTask;
import it.piapostolos.android.pinotes.async.notes.NoteProcessorArchive;
import it.piapostolos.android.pinotes.async.notes.NoteProcessorCategorize;
import it.piapostolos.android.pinotes.async.notes.NoteProcessorDelete;
import it.piapostolos.android.pinotes.async.notes.NoteProcessorTrash;
import it.piapostolos.android.pinotes.db.DbHelper;
import it.piapostolos.android.pinotes.helpers.LogDelegate;
import it.piapostolos.android.pinotes.helpers.NotesHelper;
import it.piapostolos.android.pinotes.models.Category;
import it.piapostolos.android.pinotes.models.Note;
import it.piapostolos.android.pinotes.models.ONStyle;
import it.piapostolos.android.pinotes.models.PasswordValidator;
import it.piapostolos.android.pinotes.models.Tag;
import it.piapostolos.android.pinotes.models.UndoBarController;
import it.piapostolos.android.pinotes.models.adapters.CategoryRecyclerViewAdapter;
import it.piapostolos.android.pinotes.models.adapters.NoteAdapter;
import it.piapostolos.android.pinotes.models.holders.NoteViewHolder;
import it.piapostolos.android.pinotes.models.listeners.OnFabItemClickedListener;
import it.piapostolos.android.pinotes.models.listeners.OnViewTouchedListener;
import it.piapostolos.android.pinotes.models.listeners.RecyclerViewItemClickSupport;
import it.piapostolos.android.pinotes.models.views.Fab;
import it.piapostolos.android.pinotes.models.views.InterceptorLinearLayout;
import it.piapostolos.android.pinotes.utils.AnimationsHelper;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import it.piapostolos.android.pinotes.utils.IntentChecker;
import it.piapostolos.android.pinotes.utils.KeyboardUtils;
import it.piapostolos.android.pinotes.utils.Navigation;
import it.piapostolos.android.pinotes.utils.PasswordHelper;
import it.piapostolos.android.pinotes.utils.ReminderHelper;
import it.piapostolos.android.pinotes.utils.TagsHelper;
import it.piapostolos.android.pinotes.utils.TextHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import net.sqlcipher.database.SQLiteDatabase;
import org.apache.commons.lang3.ObjectUtils;

/* loaded from: classes3.dex */
public class ListFragment extends BaseFragment implements OnViewTouchedListener, UndoBarController.UndoListener {
    public static final String LIST_VIEW_POSITION = "listViewPosition";
    public static final String LIST_VIEW_POSITION_OFFSET = "listViewPositionOffset";
    private static final int REQUEST_CODE_ADD_ALARMS = 3;
    private static final int REQUEST_CODE_CATEGORY = 1;
    private static final int REQUEST_CODE_CATEGORY_NOTES = 2;
    private ActionMode actionMode;

    @BindView(R2.id.empty_list)
    TextView empyListItem;

    @BindView(R2.id.expanded_image)
    ImageView expandedImageView;
    private Fab fab;

    @BindView(R2.id.fab)
    View fabView;
    private AnimationDrawable jinglesAnimation;

    @BindView(R2.id.list)
    DynamicListView list;
    private NoteAdapter listAdapter;

    @BindView(R2.id.list_root)
    InterceptorLinearLayout listRoot;
    private int listViewPosition;
    private ListFragment mFragment;
    private Menu menu;
    NoteViewHolder noteViewHolder;
    private NotesMainActivity notesMainActivity;
    private SharedPreferences prefs;

    @BindView(R2.id.progress_wheel)
    ProgressWheel progress_wheel;

    @BindView(R2.id.search_cancel)
    ImageView searchCancel;

    @BindView(R2.id.search_layout)
    View searchLayout;
    private MenuItem searchMenuItem;
    private String searchQuery;
    private String searchQueryInstant;

    @BindView(R2.id.search_query)
    android.widget.TextView searchQueryView;
    private String searchTags;
    private boolean searchUncompleteChecklists;
    private SearchView searchView;
    private boolean sendToArchive;

    @BindView(R2.id.snackbar_placeholder)
    View snackBarPlaceholder;
    private UndoBarController ubc;

    @BindView(R2.id.undobar)
    View undoBarView;
    private List<Note> selectedNotes = new ArrayList();
    private int listViewPositionOffset = 16;
    private boolean keepActionMode = false;
    private boolean undoTrash = false;
    private boolean undoArchive = false;
    private boolean undoCategorize = false;
    private Category undoCategorizeCategory = null;
    private SortedMap<Integer, Note> undoNotesMap = new TreeMap();
    private Map<Note, Category> undoCategoryMap = new HashMap();
    private Map<Note, Boolean> undoArchivedMap = new HashMap();
    private boolean goBackOnToggleSearchLabel = false;
    private boolean searchLabelActive = false;

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mFragment = this;
        setHasOptionsMenu(true);
        setRetainInstance(false);
        EventBus.getDefault().register(this, 1);
    }

    @Override // it.piapostolos.android.pinotes.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) throws NoSuchMethodException, SecurityException {
        if (bundle != null) {
            if (bundle.containsKey(LIST_VIEW_POSITION)) {
                this.listViewPosition = bundle.getInt(LIST_VIEW_POSITION);
                this.listViewPositionOffset = bundle.getInt(LIST_VIEW_POSITION_OFFSET);
                this.searchQuery = bundle.getString("searchQuery");
                this.searchTags = bundle.getString("searchTags");
            }
            this.keepActionMode = false;
        }
        View viewInflate = layoutInflater.inflate(R.layout.fragment_list, viewGroup, false);
        ButterKnife.bind(this, viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) throws Resources.NotFoundException {
        super.onActivityCreated(bundle);
        NotesMainActivity notesMainActivity = (NotesMainActivity) getActivity();
        this.notesMainActivity = notesMainActivity;
        this.prefs = notesMainActivity.prefs;
        if (bundle != null) {
            this.notesMainActivity.navigationTmp = bundle.getString("navigationTmp");
        }
        init();
    }

    private void init() throws Resources.NotFoundException {
        initEasterEgg();
        initListView();
        this.ubc = new UndoBarController(this.undoBarView, this);
        initNotesList(this.notesMainActivity.getIntent());
        initFab();
        initTitle();
        this.prefs = this.notesMainActivity.getSharedPreferences("PiNotes", 4);
    }

    private void initFab() {
        Fab fab = new Fab(this.fabView, this.list, this.prefs.getBoolean(ConstantsBase.PREF_FAB_EXPANSION_BEHAVIOR, false));
        this.fab = fab;
        fab.setOnFabItemClickedListener(new OnFabItemClickedListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$8IsWERsajyn9Rv278qtBfdNvzWU
            @Override // it.piapostolos.android.pinotes.models.listeners.OnFabItemClickedListener
            public final void onFabItemClick(int i) {
                this.f$0.lambda$initFab$0$ListFragment(i);
            }
        });
    }

    public /* synthetic */ void lambda$initFab$0$ListFragment(int i) {
        View viewFindViewById = this.notesMainActivity.findViewById(i);
        if (i == R.id.fab_expand_menu_button) {
            editNote(new Note(), viewFindViewById);
            return;
        }
        if (i == R.id.fab_note) {
            editNote(new Note(), viewFindViewById);
        } else if (i == R.id.fab_checklist) {
            Note note = new Note();
            note.setChecklist((Boolean) true);
            editNote(note, viewFindViewById);
        }
    }

    boolean closeFab() {
        Fab fab = this.fab;
        if (fab == null || !fab.isExpanded()) {
            return false;
        }
        this.fab.performToggle();
        return true;
    }

    private void initTitle() throws Resources.NotFoundException {
        String name;
        String[] stringArray = getResources().getStringArray(R.array.navigation_list);
        String[] stringArray2 = getResources().getStringArray(R.array.navigation_list_codes);
        String string = this.notesMainActivity.navigationTmp != null ? this.notesMainActivity.navigationTmp : this.prefs.getString("navigation", stringArray2[0]);
        int iIndexOf = Arrays.asList(stringArray2).indexOf(string);
        if (iIndexOf >= 0 && iIndexOf < stringArray2.length) {
            name = stringArray[iIndexOf];
        } else {
            Category category = DbHelper.getInstance().getCategory(Long.valueOf(Long.parseLong(string)));
            name = category != null ? category.getName() : "";
        }
        if (name == null) {
            name = getString(R.string.title_activity_list);
        }
        this.notesMainActivity.setActionBarTitle(name);
    }

    private void initEasterEgg() {
        this.empyListItem.setOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$c3V8J36zDVOWSIkO-sVoDx3maV8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initEasterEgg$2$ListFragment(view);
            }
        });
    }

    public /* synthetic */ void lambda$initEasterEgg$2$ListFragment(View view) {
        if (this.jinglesAnimation == null) {
            this.jinglesAnimation = (AnimationDrawable) this.empyListItem.getCompoundDrawables()[1];
            this.empyListItem.post(new Runnable() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$qVLZ9OvYbvdUi7AypMJelATsBbU
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$initEasterEgg$1$ListFragment();
                }
            });
        } else {
            stopJingles();
        }
    }

    public /* synthetic */ void lambda$initEasterEgg$1$ListFragment() {
        AnimationDrawable animationDrawable = this.jinglesAnimation;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }

    private void stopJingles() {
        AnimationDrawable animationDrawable = this.jinglesAnimation;
        if (animationDrawable != null) {
            animationDrawable.stop();
            this.jinglesAnimation = null;
            this.empyListItem.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.jingles_animation, 0, 0);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.searchQueryInstant = this.searchQuery;
        stopJingles();
        Crouton.cancelAllCroutons();
        closeFab();
        if (this.keepActionMode) {
            return;
        }
        commitPending();
        this.list.clearChoices();
        if (getActionMode() != null) {
            getActionMode().finish();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        refreshListScrollPosition();
        bundle.putInt(LIST_VIEW_POSITION, this.listViewPosition);
        bundle.putInt(LIST_VIEW_POSITION_OFFSET, this.listViewPositionOffset);
        bundle.putString("searchQuery", this.searchQuery);
        bundle.putString("searchTags", this.searchTags);
    }

    private void refreshListScrollPosition() {
        DynamicListView dynamicListView = this.list;
        if (dynamicListView != null) {
            this.listViewPosition = dynamicListView.getFirstVisiblePosition();
            View childAt = this.list.getChildAt(0);
            this.listViewPositionOffset = childAt == null ? (int) getResources().getDimension(R.dimen.vertical_margin) : childAt.getTop();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() throws Resources.NotFoundException {
        super.onResume();
        if (this.notesMainActivity.prefsChanged) {
            this.notesMainActivity.prefsChanged = false;
            init();
        } else if ("android.intent.action.SEARCH".equals(this.notesMainActivity.getIntent().getAction())) {
            initNotesList(this.notesMainActivity.getIntent());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class ModeCallback implements ActionMode.Callback {
        private ModeCallback() {
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.getMenuInflater().inflate(R.menu.menu_list, menu);
            ListFragment.this.actionMode = actionMode;
            ListFragment.this.fab.setAllowed(ListFragment.this.isFabAllowed());
            ListFragment.this.fab.hideFab();
            return true;
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            for (int i = 0; i < ListFragment.this.listAdapter.getSelectedItems().size(); i++) {
                int iKeyAt = ListFragment.this.listAdapter.getSelectedItems().keyAt(i);
                View childAt = ListFragment.this.list.getChildAt(iKeyAt - ListFragment.this.list.getFirstVisiblePosition());
                if (ListFragment.this.listAdapter.getCount() > iKeyAt && ListFragment.this.listAdapter.getItem(iKeyAt) != null && childAt != null) {
                    ListFragment.this.listAdapter.restoreDrawable(ListFragment.this.listAdapter.getItem(iKeyAt), childAt.findViewById(R.id.card_layout));
                }
            }
            ListFragment.this.selectedNotes.clear();
            ListFragment.this.listAdapter.clearSelectedItems();
            ListFragment.this.list.clearChoices();
            ListFragment.this.fab.setAllowed(ListFragment.this.isFabAllowed(true));
            if (ListFragment.this.undoNotesMap.size() == 0) {
                ListFragment.this.fab.showFab();
            }
            ListFragment.this.actionMode = null;
            LogDelegate.d("Closed multiselection contextual menu");
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) throws Resources.NotFoundException {
            ListFragment.this.prepareActionModeMenu();
            return true;
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onActionItemClicked(final ActionMode actionMode, final MenuItem menuItem) throws Resources.NotFoundException {
            if (!Arrays.asList(Integer.valueOf(R.id.menu_select_all), Integer.valueOf(R.id.menu_merge)).contains(Integer.valueOf(menuItem.getItemId()))) {
                ListFragment.this.notesMainActivity.requestPassword(ListFragment.this.notesMainActivity, ListFragment.this.getSelectedNotes(), new PasswordValidator() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$ModeCallback$blI8GYURDhpukCGo1T-HEpK2Q-A
                    @Override // it.piapostolos.android.pinotes.models.PasswordValidator
                    public final void onPasswordValidated(PasswordValidator.Result result) throws Resources.NotFoundException {
                        this.f$0.lambda$onActionItemClicked$0$ListFragment$ModeCallback(menuItem, actionMode, result);
                    }
                });
            } else {
                ListFragment.this.performAction(menuItem, actionMode);
            }
            return true;
        }

        public /* synthetic */ void lambda$onActionItemClicked$0$ListFragment$ModeCallback(MenuItem menuItem, ActionMode actionMode, PasswordValidator.Result result) throws Resources.NotFoundException {
            if (result.equals(PasswordValidator.Result.SUCCEED)) {
                ListFragment.this.performAction(menuItem, actionMode);
            }
        }
    }

    public void finishActionMode() {
        if (getActionMode() != null) {
            getActionMode().finish();
        }
    }

    private void toggleListViewItem(View view, int i) throws Resources.NotFoundException {
        Note item = this.listAdapter.getItem(i);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.card_layout);
        if (!getSelectedNotes().contains(item)) {
            getSelectedNotes().add(item);
            this.listAdapter.addSelectedItem(Integer.valueOf(i));
            linearLayout.setBackgroundColor(getResources().getColor(R.color.list_bg_selected));
        } else {
            getSelectedNotes().remove(item);
            this.listAdapter.removeSelectedItem(Integer.valueOf(i));
            this.listAdapter.restoreDrawable(item, linearLayout);
        }
        prepareActionModeMenu();
        if (getSelectedNotes().isEmpty()) {
            finishActionMode();
        }
    }

    private void initListView() {
        this.list.setChoiceMode(2);
        this.list.setItemsCanFocus(false);
        this.list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$HmQXvlg42Azh7vBVeX2oQQ5gFpM
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public final boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
                return this.f$0.lambda$initListView$3$ListFragment(adapterView, view, i, j);
            }
        });
        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$05mC3eId3WX70EX3FoTsTY62YKE
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) throws Resources.NotFoundException {
                this.f$0.lambda$initListView$4$ListFragment(adapterView, view, i, j);
            }
        });
        this.listRoot.setOnViewTouchedListener(this);
    }

    public /* synthetic */ boolean lambda$initListView$3$ListFragment(AdapterView adapterView, View view, int i, long j) throws Resources.NotFoundException {
        if (getActionMode() != null) {
            return false;
        }
        this.notesMainActivity.startSupportActionMode(new ModeCallback());
        toggleListViewItem(view, i);
        setCabTitle();
        return true;
    }

    public /* synthetic */ void lambda$initListView$4$ListFragment(AdapterView adapterView, View view, int i, long j) throws Resources.NotFoundException {
        if (getActionMode() == null) {
            editNote(this.listAdapter.getItem(i), view);
        } else {
            toggleListViewItem(view, i);
            setCabTitle();
        }
    }

    private ImageView getZoomListItemView(View view, Note note) {
        if (this.expandedImageView != null) {
            View viewFindViewById = note.getAttachmentsList().isEmpty() ? null : view.findViewById(R.id.attachmentThumbnail);
            if (viewFindViewById == null && note.getCategory() != null) {
                viewFindViewById = view.findViewById(R.id.category_marker);
            }
            if (viewFindViewById == null) {
                viewFindViewById = new ImageView(this.notesMainActivity);
                viewFindViewById.setBackgroundColor(-1);
            }
            viewFindViewById.setDrawingCacheEnabled(true);
            viewFindViewById.buildDrawingCache();
            this.expandedImageView.setBackgroundColor(BitmapUtils.getDominantColor(viewFindViewById.getDrawingCache()));
        }
        return this.expandedImageView;
    }

    private AnimatorListenerAdapter buildAnimatorListenerAdapter(final Note note) {
        return new AnimatorListenerAdapter() { // from class: it.piapostolos.android.pinotes.ListFragment.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ListFragment.this.editNote2(note);
            }
        };
    }

    @Override // it.piapostolos.android.pinotes.models.listeners.OnViewTouchedListener
    public void onViewTouchOccurred(MotionEvent motionEvent) {
        LogDelegate.v("Notes list: onViewTouchOccurred " + motionEvent.getAction());
        commitPending();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_list, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
        this.menu = menu;
        initSearchView(menu);
    }

    private void initSortingSubmenu() throws Resources.NotFoundException {
        String[] stringArray = getResources().getStringArray(R.array.sortable_columns);
        String[] stringArray2 = getResources().getStringArray(R.array.sortable_columns_human_readable);
        int iIndexOf = Arrays.asList(stringArray).indexOf(this.prefs.getString(ConstantsBase.PREF_SORTING_COLUMN, stringArray[0]));
        SubMenu subMenu = this.menu.findItem(R.id.menu_sort).getSubMenu();
        for (int i = 0; i < stringArray2.length; i++) {
            if (subMenu.findItem(i) == null) {
                subMenu.add(ConstantsBase.MENU_SORT_GROUP_ID, i, i, stringArray2[i]);
            }
            if (i == iIndexOf) {
                subMenu.getItem(i).setChecked(true);
            }
        }
        subMenu.setGroupCheckable(ConstantsBase.MENU_SORT_GROUP_ID, true, true);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) throws Resources.NotFoundException {
        setActionItemsVisibility(menu, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareActionModeMenu() throws Resources.NotFoundException {
        Menu menu = getActionMode().getMenu();
        int navigation = Navigation.getNavigation();
        boolean z = navigation == 0 || navigation == 2 || navigation == 4 || navigation == 5;
        boolean z2 = navigation == 1 || navigation == 4 || navigation == 5;
        if (navigation == 3) {
            menu.findItem(R.id.menu_untrash).setVisible(true);
            menu.findItem(R.id.menu_delete).setVisible(true);
        } else {
            if (getSelectedCount() == 1) {
                menu.findItem(R.id.menu_share).setVisible(true);
                menu.findItem(R.id.menu_merge).setVisible(false);
                menu.findItem(R.id.menu_archive).setVisible(z && !getSelectedNotes().get(0).isArchived().booleanValue());
                menu.findItem(R.id.menu_unarchive).setVisible(z2 && getSelectedNotes().get(0).isArchived().booleanValue());
            } else {
                menu.findItem(R.id.menu_share).setVisible(false);
                menu.findItem(R.id.menu_merge).setVisible(true);
                menu.findItem(R.id.menu_archive).setVisible(z);
                menu.findItem(R.id.menu_unarchive).setVisible(z2);
            }
            menu.findItem(R.id.menu_add_reminder).setVisible(true);
            menu.findItem(R.id.menu_category).setVisible(true);
            menu.findItem(R.id.menu_uncomplete_checklists).setVisible(false);
            menu.findItem(R.id.menu_tags).setVisible(true);
            menu.findItem(R.id.menu_trash).setVisible(true);
        }
        menu.findItem(R.id.menu_select_all).setVisible(true);
        setCabTitle();
    }

    private int getSelectedCount() {
        return getSelectedNotes().size();
    }

    private void setCabTitle() {
        if (getActionMode() != null) {
            getActionMode().setTitle(String.valueOf(getSelectedCount()));
        }
    }

    private void initSearchView(final Menu menu) {
        if (this.notesMainActivity == null) {
            return;
        }
        this.searchMenuItem = menu.findItem(R.id.menu_search);
        SearchManager searchManager = (SearchManager) this.notesMainActivity.getSystemService(FirebaseAnalytics.Event.SEARCH);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
        this.searchView = searchView;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.notesMainActivity.getComponentName()));
        this.searchView.setImeOptions(3);
        this.searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$T5HlzrEKINMv0-6RMuNPQDU0olc
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) throws Resources.NotFoundException {
                this.f$0.lambda$initSearchView$5$ListFragment(menu, view, z);
            }
        });
        MenuItemCompat.setOnActionExpandListener(this.searchMenuItem, new AnonymousClass2());
    }

    public /* synthetic */ void lambda$initSearchView$5$ListFragment(Menu menu, View view, boolean z) throws Resources.NotFoundException {
        setActionItemsVisibility(menu, z);
    }

    /* renamed from: it.piapostolos.android.pinotes.ListFragment$2, reason: invalid class name */
    class AnonymousClass2 implements MenuItemCompat.OnActionExpandListener {
        boolean searchPerformed = false;

        AnonymousClass2() {
        }

        @Override // androidx.core.view.MenuItemCompat.OnActionExpandListener
        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            ListFragment.this.searchQuery = null;
            if (ListFragment.this.searchLayout.getVisibility() == 0) {
                ListFragment.this.toggleSearchLabel(false);
            }
            ListFragment.this.notesMainActivity.getIntent().setAction("android.intent.action.MAIN");
            ListFragment listFragment = ListFragment.this;
            listFragment.initNotesList(listFragment.notesMainActivity.getIntent());
            ListFragment.this.notesMainActivity.supportInvalidateOptionsMenu();
            return true;
        }

        @Override // androidx.core.view.MenuItemCompat.OnActionExpandListener
        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            ListFragment.this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // from class: it.piapostolos.android.pinotes.ListFragment.2.1
                @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    return ListFragment.this.prefs.getBoolean("settings_instant_search", false);
                }

                @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    if (ListFragment.this.prefs.getBoolean("settings_instant_search", false) && ListFragment.this.searchLayout != null && AnonymousClass2.this.searchPerformed && ListFragment.this.mFragment.isAdded()) {
                        ListFragment.this.searchTags = null;
                        ListFragment.this.searchQuery = str;
                        NoteLoaderTask.getInstance().execute("getNotesByPattern", str);
                        return true;
                    }
                    AnonymousClass2.this.searchPerformed = true;
                    return false;
                }
            });
            return true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setActionItemsVisibility(android.view.Menu r12, boolean r13) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 305
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: it.piapostolos.android.pinotes.ListFragment.setActionItemsVisibility(android.view.Menu, boolean):void");
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(final MenuItem menuItem) throws Resources.NotFoundException {
        if (Arrays.asList(Integer.valueOf(R.id.menu_empty_trash)).contains(Integer.valueOf(menuItem.getItemId()))) {
            NotesMainActivity notesMainActivity = this.notesMainActivity;
            notesMainActivity.requestPassword(notesMainActivity, getSelectedNotes(), new PasswordValidator() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$V_eNvdQs2erh4r6NwvcP-PHLiUc
                @Override // it.piapostolos.android.pinotes.models.PasswordValidator
                public final void onPasswordValidated(PasswordValidator.Result result) throws Resources.NotFoundException {
                    this.f$0.lambda$onOptionsItemSelected$6$ListFragment(menuItem, result);
                }
            });
        } else {
            performAction(menuItem, null);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public /* synthetic */ void lambda$onOptionsItemSelected$6$ListFragment(MenuItem menuItem, PasswordValidator.Result result) throws Resources.NotFoundException {
        if (result.equals(PasswordValidator.Result.SUCCEED)) {
            performAction(menuItem, null);
        }
    }

    public void performAction(MenuItem menuItem, ActionMode actionMode) throws Resources.NotFoundException {
        if (isOptionsItemFastClick()) {
            return;
        }
        if (actionMode == null) {
            int itemId = menuItem.getItemId();
            if (itemId == 16908332) {
                if (this.notesMainActivity.getDrawerLayout().isDrawerOpen(8388611)) {
                    this.notesMainActivity.getDrawerLayout().closeDrawer(8388611);
                } else {
                    this.notesMainActivity.getDrawerLayout().openDrawer(8388611);
                }
            } else if (itemId == R.id.menu_filter) {
                filterReminders(true);
            } else if (itemId == R.id.menu_filter_remove) {
                filterReminders(false);
            } else if (itemId == R.id.menu_filter_category) {
                filterCategoryArchived(true);
            } else if (itemId == R.id.menu_filter_category_remove) {
                filterCategoryArchived(false);
            } else if (itemId == R.id.menu_uncomplete_checklists) {
                filterByUncompleteChecklists();
            } else if (itemId == R.id.menu_tags) {
                filterByTags();
            } else if (itemId == R.id.menu_sort) {
                initSortingSubmenu();
            } else if (itemId == R.id.menu_expanded_view || itemId == R.id.menu_contracted_view) {
                switchNotesView();
            } else if (itemId == R.id.menu_empty_trash) {
                emptyTrash();
            } else {
                LogDelegate.e("Wrong element choosen: " + menuItem.getItemId());
            }
        } else {
            int itemId2 = menuItem.getItemId();
            if (itemId2 == R.id.menu_category) {
                categorizeNotes();
            } else if (itemId2 == R.id.menu_tags) {
                tagNotes();
            } else if (itemId2 == R.id.menu_share) {
                share();
            } else if (itemId2 == R.id.menu_merge) {
                merge();
            } else if (itemId2 == R.id.menu_archive) {
                archiveNotes(true);
            } else if (itemId2 == R.id.menu_unarchive) {
                archiveNotes(false);
            } else if (itemId2 == R.id.menu_trash) {
                trashNotes(true);
            } else if (itemId2 == R.id.menu_untrash) {
                trashNotes(false);
            } else if (itemId2 == R.id.menu_delete) {
                deleteNotes();
            } else if (itemId2 == R.id.menu_select_all) {
                selectAllNotes();
            } else if (itemId2 == R.id.menu_add_reminder) {
                addReminders();
            } else {
                LogDelegate.e("Wrong element choosen: " + menuItem.getItemId());
            }
        }
        checkSortActionPerformed(menuItem);
    }

    private void addReminders() {
        Intent intent = new Intent(PiNotes.getAppContext(), (Class<?>) SnoozeActivity.class);
        intent.setAction(ConstantsBase.ACTION_POSTPONE);
        List<Note> list = this.selectedNotes;
        intent.putExtra(ConstantsBase.INTENT_NOTE, (Parcelable[]) list.toArray(new Note[list.size()]));
        intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        startActivityForResult(intent, 3);
    }

    private void switchNotesView() {
        this.prefs.edit().putBoolean(ConstantsBase.PREF_EXPANDED_VIEW, !this.prefs.getBoolean(ConstantsBase.PREF_EXPANDED_VIEW, true)).commit();
        initNotesList(this.notesMainActivity.getIntent());
        this.notesMainActivity.supportInvalidateOptionsMenu();
    }

    void editNote(final Note note, final View view) {
        if (note.isLocked().booleanValue() && !this.prefs.getBoolean("settings_password_access", false)) {
            PasswordHelper.requestPassword(this.notesMainActivity, new PasswordValidator() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$sCiz8zZgdmwEXTRyEriVSbx0u0A
                @Override // it.piapostolos.android.pinotes.models.PasswordValidator
                public final void onPasswordValidated(PasswordValidator.Result result) {
                    this.f$0.lambda$editNote$7$ListFragment(note, view, result);
                }
            });
        } else {
            AnimationsHelper.zoomListItem(this.notesMainActivity, view, getZoomListItemView(view, note), this.listRoot, buildAnimatorListenerAdapter(note));
        }
    }

    public /* synthetic */ void lambda$editNote$7$ListFragment(Note note, View view, PasswordValidator.Result result) {
        if (result.equals(PasswordValidator.Result.SUCCEED)) {
            note.setPasswordChecked(true);
            AnimationsHelper.zoomListItem(this.notesMainActivity, view, getZoomListItemView(view, note), this.listRoot, buildAnimatorListenerAdapter(note));
        }
    }

    void editNote2(Note note) {
        if (note.get_id() == null) {
            LogDelegate.d("Adding new note");
            try {
                if (Navigation.checkNavigation(5) || !TextUtils.isEmpty(this.notesMainActivity.navigationTmp)) {
                    note.setCategory(DbHelper.getInstance().getCategory(Long.valueOf(Long.parseLong((String) ObjectUtils.defaultIfNull(this.notesMainActivity.navigationTmp, Navigation.getCategory().toString())))));
                }
            } catch (NumberFormatException unused) {
                LogDelegate.v("Maybe was not a category!");
            }
        } else {
            LogDelegate.d("Editing note with ID: " + note.get_id());
        }
        refreshListScrollPosition();
        this.notesMainActivity.switchToDetail(note);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1) {
            if (i2 == -1) {
                this.notesMainActivity.showMessage(R.string.category_saved, ONStyle.CONFIRM);
                EventBus.getDefault().post(new CategoriesUpdatedEvent());
                return;
            } else {
                if (i2 != 1) {
                    return;
                }
                this.notesMainActivity.showMessage(R.string.category_deleted, ONStyle.ALERT);
                return;
            }
        }
        if (i == 2) {
            if (intent != null) {
                categorizeNotesExecute((Category) intent.getParcelableExtra("category"));
            }
        } else {
            if (i != 3) {
                return;
            }
            this.list.clearChoices();
            this.selectedNotes.clear();
            finishActionMode();
            this.list.invalidateViews();
        }
    }

    private void checkSortActionPerformed(MenuItem menuItem) throws Resources.NotFoundException {
        if (menuItem.getGroupId() == 11998811) {
            this.prefs.edit().putString(ConstantsBase.PREF_SORTING_COLUMN, getResources().getStringArray(R.array.sortable_columns)[menuItem.getOrder()]).apply();
            initNotesList(this.notesMainActivity.getIntent());
            this.listViewPositionOffset = 16;
            this.listViewPosition = 0;
            restoreListScrollPosition();
            toggleSearchLabel(false);
            this.notesMainActivity.updateWidgets();
        }
    }

    private void emptyTrash() {
        new MaterialDialog.Builder(this.notesMainActivity).content(R.string.empty_trash_confirmation).positiveText(R.string.ok).onPositive(new AnonymousClass3()).build().show();
    }

    /* renamed from: it.piapostolos.android.pinotes.ListFragment$3, reason: invalid class name */
    class AnonymousClass3 implements MaterialDialog.SingleButtonCallback {
        AnonymousClass3() {
        }

        @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
            boolean z = false;
            for (int i = 0; i < ListFragment.this.listAdapter.getCount(); i++) {
                ListFragment.this.selectedNotes.add(ListFragment.this.listAdapter.getItem(i));
                z = z || ListFragment.this.listAdapter.getItem(i).isLocked().booleanValue();
            }
            if (z) {
                ListFragment.this.notesMainActivity.requestPassword(ListFragment.this.notesMainActivity, ListFragment.this.getSelectedNotes(), new PasswordValidator() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$3$bwLhK8E97BQjsukK54fesrFPF5k
                    @Override // it.piapostolos.android.pinotes.models.PasswordValidator
                    public final void onPasswordValidated(PasswordValidator.Result result) {
                        this.f$0.lambda$onClick$0$ListFragment$3(result);
                    }
                });
            } else {
                ListFragment.this.deleteNotesExecute();
            }
        }

        public /* synthetic */ void lambda$onClick$0$ListFragment$3(PasswordValidator.Result result) {
            if (result.equals(PasswordValidator.Result.SUCCEED)) {
                ListFragment.this.deleteNotesExecute();
            }
        }
    }

    void initNotesList(Intent intent) {
        LogDelegate.d("initNotesList intent: " + intent.getAction());
        this.progress_wheel.setAlpha(1.0f);
        this.list.setAlpha(0.0f);
        if ("android.intent.action.VIEW".equals(intent.getAction()) && intent.getCategories() != null && intent.getCategories().contains("android.intent.category.BROWSABLE")) {
            this.searchTags = intent.getDataString().replace(UrlCompleter.HASHTAG_SCHEME, "");
            this.goBackOnToggleSearchLabel = true;
        }
        if (ConstantsBase.ACTION_SHORTCUT_WIDGET.equals(intent.getAction())) {
            return;
        }
        String str = this.searchQueryInstant;
        this.searchQuery = str;
        this.searchQueryInstant = null;
        if (this.searchTags != null || str != null || this.searchUncompleteChecklists || IntentChecker.checkAction(intent, "android.intent.action.SEARCH", ConstantsBase.ACTION_SEARCH_UNCOMPLETE_CHECKLISTS)) {
            if (this.searchTags != null && intent.getStringExtra("query") == null) {
                this.searchQuery = this.searchTags;
                NoteLoaderTask.getInstance().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "getNotesByTag", this.searchQuery);
            } else if (this.searchUncompleteChecklists || ConstantsBase.ACTION_SEARCH_UNCOMPLETE_CHECKLISTS.equals(intent.getAction())) {
                this.searchQuery = getContext().getResources().getString(R.string.uncompleted_checklists);
                this.searchUncompleteChecklists = true;
                NoteLoaderTask.getInstance().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "getNotesByUncompleteChecklist");
            } else {
                if (intent.getStringExtra("query") != null) {
                    this.searchQuery = intent.getStringExtra("query");
                    this.searchTags = null;
                }
                NoteLoaderTask.getInstance().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "getNotesByPattern", this.searchQuery);
            }
            toggleSearchLabel(true);
            return;
        }
        if ((ConstantsBase.ACTION_WIDGET_SHOW_LIST.equals(intent.getAction()) && intent.hasExtra(ConstantsBase.INTENT_WIDGET)) || !TextUtils.isEmpty(this.notesMainActivity.navigationTmp)) {
            String string = intent.hasExtra(ConstantsBase.INTENT_WIDGET) ? intent.getExtras().get(ConstantsBase.INTENT_WIDGET).toString() : null;
            if (string != null) {
                String strCheckIntentCategory = TextHelper.checkIntentCategory(this.prefs.getString(ConstantsBase.PREF_WIDGET_PREFIX + string, ""));
                this.notesMainActivity.navigationTmp = TextUtils.isEmpty(strCheckIntentCategory) ? null : strCheckIntentCategory;
            }
            intent.removeExtra(ConstantsBase.INTENT_WIDGET);
            if (this.notesMainActivity.navigationTmp != null) {
                NoteLoaderTask.getInstance().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "getNotesByCategory", Long.valueOf(Long.parseLong(this.notesMainActivity.navigationTmp)));
                return;
            } else {
                NoteLoaderTask.getInstance().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "getAllNotes", true);
                return;
            }
        }
        NoteLoaderTask.getInstance().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "getAllNotes", true);
    }

    public void toggleSearchLabel(boolean z) {
        if (z) {
            this.searchQueryView.setText(Html.fromHtml(getString(R.string.search) + ":<b> " + this.searchQuery + "</b>"));
            this.searchLayout.setVisibility(0);
            this.searchCancel.setOnClickListener(new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$dMJBWFWIDbeBPa6R3-M576sT4t8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$toggleSearchLabel$8$ListFragment(view);
                }
            });
            this.searchLabelActive = true;
            return;
        }
        if (this.searchLabelActive) {
            this.searchLabelActive = false;
            AnimationsHelper.expandOrCollapse(this.searchLayout, false);
            this.searchTags = null;
            this.searchQuery = null;
            this.searchUncompleteChecklists = false;
            if (!this.goBackOnToggleSearchLabel) {
                this.notesMainActivity.getIntent().setAction("android.intent.action.MAIN");
                if (this.searchView != null) {
                    this.searchMenuItem.collapseActionView();
                }
                initNotesList(this.notesMainActivity.getIntent());
            } else {
                this.notesMainActivity.onBackPressed();
            }
            this.goBackOnToggleSearchLabel = false;
            if ("android.intent.action.VIEW".equals(this.notesMainActivity.getIntent().getAction())) {
                this.notesMainActivity.getIntent().setAction(null);
            }
        }
    }

    public /* synthetic */ void lambda$toggleSearchLabel$8$ListFragment(View view) {
        toggleSearchLabel(false);
    }

    public void onEvent(NavigationUpdatedNavDrawerClosedEvent navigationUpdatedNavDrawerClosedEvent) throws Resources.NotFoundException {
        this.listViewPosition = 0;
        this.listViewPositionOffset = 16;
        initNotesList(this.notesMainActivity.getIntent());
        setActionItemsVisibility(this.menu, false);
    }

    public void onEvent(CategoriesUpdatedEvent categoriesUpdatedEvent) {
        initNotesList(this.notesMainActivity.getIntent());
    }

    public void onEvent(NotesLoadedEvent notesLoadedEvent) {
        int i = this.prefs.getBoolean(ConstantsBase.PREF_EXPANDED_VIEW, true) ? R.layout.note_layout_expanded : R.layout.note_layout;
        this.listAdapter = new NoteAdapter(this.notesMainActivity, i, notesLoadedEvent.notes);
        this.noteViewHolder = new NoteViewHolder(LayoutInflater.from(this.notesMainActivity).inflate(i, (ViewGroup) null, false));
        if (Navigation.getNavigation() != 4 && this.prefs.getBoolean(ConstantsBase.PREF_ENABLE_SWIPE, true)) {
            this.list.enableSwipeToDismiss(new OnDismissCallback() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$jPb1lsXFeCskk5_LaKrW9r32F3c
                @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback
                public final void onDismiss(ViewGroup viewGroup, int[] iArr) {
                    this.f$0.lambda$onEvent$10$ListFragment(viewGroup, iArr);
                }
            });
        } else {
            this.list.disableSwipeToDismiss();
        }
        this.list.setAdapter((ListAdapter) this.listAdapter);
        if (notesLoadedEvent.notes.size() == 0) {
            this.list.setEmptyView(this.empyListItem);
        }
        if (this.list != null && notesLoadedEvent.notes.size() > 0) {
            if (Navigation.checkNavigation(2)) {
                this.listViewPosition = this.listAdapter.getClosestNotePosition();
            }
            restoreListScrollPosition();
        }
        animateListView();
        closeFab();
    }

    public /* synthetic */ void lambda$onEvent$10$ListFragment(ViewGroup viewGroup, int[] iArr) {
        finishActionMode();
        for (int i : iArr) {
            try {
                Note item = this.listAdapter.getItem(i);
                if (item != null && item.isLocked().booleanValue()) {
                    PasswordHelper.requestPassword(this.notesMainActivity, new PasswordValidator() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$RbbgTbKkIEqb0xz_mltmuog6pko
                        @Override // it.piapostolos.android.pinotes.models.PasswordValidator
                        public final void onPasswordValidated(PasswordValidator.Result result) {
                            this.f$0.lambda$onEvent$9$ListFragment(result);
                        }
                    });
                }
                getSelectedNotes().add(item);
                if (Navigation.checkNavigation(3)) {
                    trashNotes(false);
                } else if (Navigation.checkNavigation(5)) {
                    categorizeNotesExecute(null);
                } else if (this.prefs.getBoolean("settings_swipe_to_trash", false) || Navigation.checkNavigation(1)) {
                    trashNotes(true);
                } else {
                    archiveNotes(true);
                }
            } catch (IndexOutOfBoundsException unused) {
                LogDelegate.d("Please stop swiping in the zone beneath the last card");
            }
        }
    }

    public /* synthetic */ void lambda$onEvent$9$ListFragment(PasswordValidator.Result result) {
        if (result.equals(PasswordValidator.Result.SUCCEED)) {
            return;
        }
        onUndo(null);
    }

    public void onEvent(PasswordRemovedEvent passwordRemovedEvent) {
        initNotesList(this.notesMainActivity.getIntent());
    }

    private void animateListView() {
        if (!PiNotes.isDebugBuild()) {
            ViewCompat.animate(this.progress_wheel).setDuration(getResources().getInteger(R.integer.list_view_fade_anim)).alpha(0.0f);
            ViewCompat.animate(this.list).setDuration(getResources().getInteger(R.integer.list_view_fade_anim)).alpha(1.0f);
        } else {
            this.progress_wheel.setVisibility(4);
            this.list.setAlpha(1.0f);
        }
    }

    private void restoreListScrollPosition() {
        int count = this.list.getCount();
        int i = this.listViewPosition;
        if (count > i) {
            this.list.setSelectionFromTop(i, this.listViewPositionOffset);
            Handler handler = new Handler();
            final Fab fab = this.fab;
            Objects.requireNonNull(fab);
            handler.postDelayed(new Runnable() { // from class: it.piapostolos.android.pinotes.-$$Lambda$Ik22a23GLV-FVFdDIPIpJw8DRNQ
                @Override // java.lang.Runnable
                public final void run() {
                    fab.showFab();
                }
            }, 150L);
            return;
        }
        this.list.setSelectionFromTop(0, 0);
    }

    public void trashNotes(boolean z) {
        int size = getSelectedNotes().size();
        if (z) {
            trackModifiedNotes(getSelectedNotes());
            for (Note note : getSelectedNotes()) {
                this.listAdapter.remove((NoteAdapter) note);
                ReminderHelper.removeReminder(PiNotes.getAppContext(), note);
            }
        } else {
            trashNote(getSelectedNotes(), false);
        }
        if (this.listAdapter.getCount() == 0) {
            this.list.setEmptyView(this.empyListItem);
        }
        finishActionMode();
        if (z) {
            this.notesMainActivity.showMessage(R.string.note_trashed, ONStyle.WARN);
        } else {
            this.notesMainActivity.showMessage(R.string.note_untrashed, ONStyle.INFO);
        }
        if (z) {
            this.ubc.showUndoBar(false, size + " " + getString(R.string.trashed), null);
            this.fab.hideFab();
            this.undoTrash = true;
            return;
        }
        getSelectedNotes().clear();
    }

    private ActionMode getActionMode() {
        return this.actionMode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<Note> getSelectedNotes() {
        return this.selectedNotes;
    }

    protected void trashNote(List<Note> list, boolean z) {
        this.listAdapter.remove(list);
        new NoteProcessorTrash(list, z).process();
    }

    private void selectAllNotes() throws Resources.NotFoundException {
        for (int i = 0; i < this.list.getChildCount(); i++) {
            ((LinearLayout) this.list.getChildAt(i).findViewById(R.id.card_layout)).setBackgroundColor(getResources().getColor(R.color.list_bg_selected));
        }
        this.selectedNotes.clear();
        for (int i2 = 0; i2 < this.listAdapter.getCount(); i2++) {
            this.selectedNotes.add(this.listAdapter.getItem(i2));
            this.listAdapter.addSelectedItem(Integer.valueOf(i2));
        }
        prepareActionModeMenu();
        setCabTitle();
    }

    private void deleteNotes() {
        new MaterialDialog.Builder(this.notesMainActivity).content(R.string.delete_note_confirmation).positiveText(R.string.ok).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$ObujWNrhauZRwzcVWjKylBmbbOI
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                this.f$0.lambda$deleteNotes$12$ListFragment(materialDialog, dialogAction);
            }
        }).build().show();
    }

    public /* synthetic */ void lambda$deleteNotes$12$ListFragment(MaterialDialog materialDialog, DialogAction dialogAction) {
        NotesMainActivity notesMainActivity = this.notesMainActivity;
        notesMainActivity.requestPassword(notesMainActivity, getSelectedNotes(), new PasswordValidator() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$PqcIgP9LSvnQgXpLX1RxEgIlfB4
            @Override // it.piapostolos.android.pinotes.models.PasswordValidator
            public final void onPasswordValidated(PasswordValidator.Result result) {
                this.f$0.lambda$deleteNotes$11$ListFragment(result);
            }
        });
    }

    public /* synthetic */ void lambda$deleteNotes$11$ListFragment(PasswordValidator.Result result) {
        if (result.equals(PasswordValidator.Result.SUCCEED)) {
            deleteNotesExecute();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteNotesExecute() {
        this.listAdapter.remove(getSelectedNotes());
        new NoteProcessorDelete(getSelectedNotes()).process();
        this.list.clearChoices();
        this.selectedNotes.clear();
        finishActionMode();
        if (this.listAdapter.getCount() == 0) {
            this.list.setEmptyView(this.empyListItem);
        }
        this.notesMainActivity.showMessage(R.string.note_deleted, ONStyle.ALERT);
    }

    public void archiveNotes(boolean z) {
        int size = getSelectedNotes().size();
        this.sendToArchive = z;
        if (!z) {
            archiveNote(getSelectedNotes(), false);
        } else {
            trackModifiedNotes(getSelectedNotes());
        }
        for (Note note : getSelectedNotes()) {
            if (z) {
                this.undoArchivedMap.put(note, note.isArchived());
            }
            if (!Navigation.checkNavigation(0) && (!Navigation.checkNavigation(1) || z)) {
                if (Navigation.checkNavigation(5)) {
                    if (this.prefs.getBoolean(ConstantsBase.PREF_FILTER_ARCHIVED_IN_CATEGORIES + Navigation.getCategory(), false)) {
                    }
                }
                note.setArchived(Boolean.valueOf(z));
                NoteAdapter noteAdapter = this.listAdapter;
                noteAdapter.replace(note, noteAdapter.getPosition(note));
            }
            this.listAdapter.remove((NoteAdapter) note);
        }
        this.listAdapter.notifyDataSetChanged();
        finishActionMode();
        if (this.listAdapter.getCount() == 0) {
            this.list.setEmptyView(this.empyListItem);
        }
        this.notesMainActivity.showMessage(z ? R.string.note_archived : R.string.note_unarchived, z ? ONStyle.WARN : ONStyle.INFO);
        if (z) {
            this.ubc.showUndoBar(false, size + " " + getString(R.string.archived), null);
            this.fab.hideFab();
            this.undoArchive = true;
            return;
        }
        getSelectedNotes().clear();
    }

    private void trackModifiedNotes(List<Note> list) {
        for (Note note : list) {
            this.undoNotesMap.put(Integer.valueOf(this.listAdapter.getPosition(note)), note);
        }
    }

    private void archiveNote(List<Note> list, boolean z) {
        new NoteProcessorArchive(list, z).process();
        if (!Navigation.checkNavigation(5)) {
            this.listAdapter.remove(list);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Notes");
        sb.append(z ? DbHelper.KEY_ARCHIVED : "restored from archive");
        LogDelegate.d(sb.toString());
    }

    void editCategory(Category category) {
        Intent intent = new Intent(this.notesMainActivity, (Class<?>) CategoryActivity.class);
        intent.putExtra("category", category);
        startActivityForResult(intent, 1);
    }

    private void categorizeNotes() {
        final ArrayList<Category> categories = DbHelper.getInstance().getCategories();
        final MaterialDialog materialDialogBuild = new MaterialDialog.Builder(this.notesMainActivity).title(R.string.categorize_as).adapter(new CategoryRecyclerViewAdapter(this.notesMainActivity, categories), null).positiveText(R.string.add_category).positiveColorRes(R.color.colorPrimary).negativeText(R.string.remove_category).negativeColorRes(R.color.colorAccent).onPositive(new MaterialDialog.SingleButtonCallback() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$7wq0iDD754JT2xNQC5qWemcJxJQ
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                this.f$0.lambda$categorizeNotes$13$ListFragment(materialDialog, dialogAction);
            }
        }).onNegative(new MaterialDialog.SingleButtonCallback() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$ciXgxI4-6DQl0ePBLlcF1JECqa8
            @Override // com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
            public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                this.f$0.lambda$categorizeNotes$14$ListFragment(materialDialog, dialogAction);
            }
        }).build();
        RecyclerViewItemClickSupport.addTo(materialDialogBuild.getRecyclerView()).setOnItemClickListener(new RecyclerViewItemClickSupport.OnItemClickListener() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$WxX_gQKw40Ek8EsWOjyOS4wlI9Q
            @Override // it.piapostolos.android.pinotes.models.listeners.RecyclerViewItemClickSupport.OnItemClickListener
            public final void onItemClicked(RecyclerView recyclerView, int i, View view) {
                this.f$0.lambda$categorizeNotes$15$ListFragment(materialDialogBuild, categories, recyclerView, i, view);
            }
        });
        materialDialogBuild.show();
    }

    public /* synthetic */ void lambda$categorizeNotes$13$ListFragment(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.keepActionMode = true;
        Intent intent = new Intent(this.notesMainActivity, (Class<?>) CategoryActivity.class);
        intent.putExtra("noHome", true);
        startActivityForResult(intent, 2);
    }

    public /* synthetic */ void lambda$categorizeNotes$14$ListFragment(MaterialDialog materialDialog, DialogAction dialogAction) {
        categorizeNotesExecute(null);
    }

    public /* synthetic */ void lambda$categorizeNotes$15$ListFragment(MaterialDialog materialDialog, ArrayList arrayList, RecyclerView recyclerView, int i, View view) {
        materialDialog.dismiss();
        categorizeNotesExecute((Category) arrayList.get(i));
    }

    private void categorizeNotesExecute(Category category) {
        if (category != null) {
            categorizeNote(getSelectedNotes(), category);
        } else {
            trackModifiedNotes(getSelectedNotes());
        }
        for (Note note : getSelectedNotes()) {
            if (category == null) {
                this.undoCategoryMap.put(note, note.getCategory());
            }
            if ((Navigation.checkNavigation(5) && !Navigation.checkNavigationCategory(category)) || Navigation.checkNavigation(4)) {
                this.listAdapter.remove((NoteAdapter) note);
            } else {
                note.setCategory(category);
                NoteAdapter noteAdapter = this.listAdapter;
                noteAdapter.replace(note, noteAdapter.getPosition(note));
            }
        }
        finishActionMode();
        if (this.listAdapter.getCount() == 0) {
            this.list.setEmptyView(this.empyListItem);
        }
        this.notesMainActivity.showMessage(category != null ? ((Object) getResources().getText(R.string.notes_categorized_as)) + " '" + category.getName() + "'" : getResources().getText(R.string.notes_category_removed).toString(), ONStyle.INFO);
        if (category == null) {
            this.ubc.showUndoBar(false, getString(R.string.notes_category_removed), null);
            this.fab.hideFab();
            this.undoCategorize = true;
            this.undoCategorizeCategory = null;
            return;
        }
        getSelectedNotes().clear();
    }

    private void categorizeNote(List<Note> list, Category category) {
        new NoteProcessorCategorize(list, category).process();
    }

    private void tagNotes() {
        final List<Tag> tags = DbHelper.getInstance().getTags();
        if (tags.size() == 0) {
            finishActionMode();
            this.notesMainActivity.showMessage(R.string.no_tags_created, ONStyle.WARN);
        } else {
            final Integer[] preselectedTagsArray = TagsHelper.getPreselectedTagsArray(this.selectedNotes, tags);
            new MaterialDialog.Builder(this.notesMainActivity).title(R.string.select_tags).items(TagsHelper.getTagsArray(tags)).positiveText(R.string.ok).itemsCallbackMultiChoice(preselectedTagsArray, new MaterialDialog.ListCallbackMultiChoice() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$9STXJELQLvNC7SHwNi9lte09yYM
                @Override // com.afollestad.materialdialogs.MaterialDialog.ListCallbackMultiChoice
                public final boolean onSelection(MaterialDialog materialDialog, Integer[] numArr, CharSequence[] charSequenceArr) {
                    return this.f$0.lambda$tagNotes$16$ListFragment(tags, preselectedTagsArray, materialDialog, numArr, charSequenceArr);
                }
            }).build().show();
        }
    }

    public /* synthetic */ boolean lambda$tagNotes$16$ListFragment(List list, Integer[] numArr, MaterialDialog materialDialog, Integer[] numArr2, CharSequence[] charSequenceArr) {
        materialDialog.dismiss();
        tagNotesExecute(list, numArr2, numArr);
        return false;
    }

    private void tagNotesExecute(List<Tag> list, Integer[] numArr, Integer[] numArr2) {
        Iterator<Note> it2 = getSelectedNotes().iterator();
        while (it2.hasNext()) {
            tagNote(list, numArr, it2.next());
        }
        this.list.clearChoices();
        this.list.invalidateViews();
        if (this.listAdapter.getCount() == 0) {
            this.list.setEmptyView(this.empyListItem);
        }
        if (getActionMode() != null) {
            getActionMode().finish();
        }
        this.notesMainActivity.showMessage(R.string.tags_added, ONStyle.INFO);
    }

    private void tagNote(List<Tag> list, Integer[] numArr, Note note) {
        Pair<String, List<Tag>> pairAddTagToNote = TagsHelper.addTagToNote(list, numArr, note);
        if (note.isChecklist().booleanValue()) {
            note.setTitle(note.getTitle() + System.getProperty("line.separator") + pairAddTagToNote.first);
        } else {
            StringBuilder sb = new StringBuilder(note.getContent());
            if (sb.length() > 0) {
                sb.append(System.getProperty("line.separator"));
                sb.append(System.getProperty("line.separator"));
            }
            sb.append(pairAddTagToNote.first);
            note.setContent(sb.toString());
        }
        Pair<String, String> pairRemoveTag = TagsHelper.removeTag(note.getTitle(), note.getContent(), pairAddTagToNote.second);
        note.setTitle(pairRemoveTag.first);
        note.setContent(pairRemoveTag.second);
        DbHelper.getInstance().updateNote(note, false);
    }

    @Override // it.piapostolos.android.pinotes.models.UndoBarController.UndoListener
    public void onUndo(Parcelable parcelable) {
        for (Integer num : this.undoNotesMap.keySet()) {
            Note note = this.undoNotesMap.get(num);
            if ((this.undoCategorize && !Navigation.checkNavigationCategory(this.undoCategoryMap.get(note))) || (this.undoArchive && !Navigation.checkNavigation(0))) {
                if (this.undoCategorize) {
                    note.setCategory(this.undoCategoryMap.get(note));
                } else if (this.undoArchive) {
                    note.setArchived(this.undoArchivedMap.get(note));
                }
                NoteAdapter noteAdapter = this.listAdapter;
                noteAdapter.replace(note, noteAdapter.getPosition(note));
            } else {
                this.list.insert(num.intValue(), note);
            }
        }
        this.listAdapter.notifyDataSetChanged();
        this.selectedNotes.clear();
        this.undoNotesMap.clear();
        this.undoTrash = false;
        this.undoArchive = false;
        this.undoCategorize = false;
        this.undoNotesMap.clear();
        this.undoCategoryMap.clear();
        this.undoArchivedMap.clear();
        this.undoCategorizeCategory = null;
        Crouton.cancelAllCroutons();
        if (getActionMode() != null) {
            getActionMode().finish();
        }
        this.ubc.hideUndoBar(false);
        this.fab.showFab();
    }

    void commitPending() {
        if (this.undoTrash || this.undoArchive || this.undoCategorize) {
            ArrayList arrayList = new ArrayList(this.undoNotesMap.values());
            if (this.undoTrash) {
                trashNote(arrayList, true);
            } else if (this.undoArchive) {
                archiveNote(arrayList, this.sendToArchive);
            } else if (this.undoCategorize) {
                categorizeNote(arrayList, this.undoCategorizeCategory);
            }
            this.undoTrash = false;
            this.undoArchive = false;
            this.undoCategorize = false;
            this.undoCategorizeCategory = null;
            this.selectedNotes.clear();
            this.undoNotesMap.clear();
            this.undoCategoryMap.clear();
            this.undoArchivedMap.clear();
            this.list.clearChoices();
            this.ubc.hideUndoBar(false);
            this.fab.showFab();
            LogDelegate.d("Changes committed");
        }
        this.notesMainActivity.updateWidgets();
    }

    private void share() {
        Iterator<Note> it2 = getSelectedNotes().iterator();
        while (it2.hasNext()) {
            this.notesMainActivity.shareNote(it2.next());
        }
        getSelectedNotes().clear();
        if (getActionMode() != null) {
            getActionMode().finish();
        }
    }

    public void merge() {
        EventBus.getDefault().post(new NotesMergeEvent(false));
    }

    public void onEventAsync(final NotesMergeEvent notesMergeEvent) {
        final Note noteMergeNotes = NotesHelper.mergeNotes(getSelectedNotes(), notesMergeEvent.keepMergedNotes);
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$ZG9EMbKLBEzd-T93hwnAaRwjEIw
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onEventAsync$17$ListFragment(notesMergeEvent, noteMergeNotes);
            }
        });
    }

    public /* synthetic */ void lambda$onEventAsync$17$ListFragment(NotesMergeEvent notesMergeEvent, Note note) {
        if (!notesMergeEvent.keepMergedNotes) {
            ArrayList arrayList = new ArrayList();
            Iterator<Note> it2 = getSelectedNotes().iterator();
            while (it2.hasNext()) {
                arrayList.add(String.valueOf(it2.next().get_id()));
            }
            this.notesMainActivity.getIntent().putExtra("merged_notes", arrayList);
        }
        getSelectedNotes().clear();
        if (getActionMode() != null) {
            getActionMode().finish();
        }
        this.notesMainActivity.getIntent().setAction(ConstantsBase.ACTION_MERGE);
        this.notesMainActivity.switchToDetail(note);
    }

    private void filterReminders(boolean z) {
        this.prefs.edit().putBoolean(ConstantsBase.PREF_FILTER_PAST_REMINDERS, z).apply();
        initNotesList(this.notesMainActivity.getIntent());
        this.notesMainActivity.supportInvalidateOptionsMenu();
    }

    private void filterCategoryArchived(boolean z) {
        if (z) {
            this.prefs.edit().putBoolean(ConstantsBase.PREF_FILTER_ARCHIVED_IN_CATEGORIES + Navigation.getCategory(), true).apply();
        } else {
            this.prefs.edit().remove(ConstantsBase.PREF_FILTER_ARCHIVED_IN_CATEGORIES + Navigation.getCategory()).apply();
        }
        initNotesList(this.notesMainActivity.getIntent());
        this.notesMainActivity.supportInvalidateOptionsMenu();
    }

    private void filterByUncompleteChecklists() {
        initNotesList(new Intent(ConstantsBase.ACTION_SEARCH_UNCOMPLETE_CHECKLISTS));
    }

    private void filterByTags() {
        final List<Tag> allTags = TagsHelper.getAllTags();
        if (allTags.isEmpty()) {
            this.notesMainActivity.showMessage(R.string.no_tags_created, ONStyle.WARN);
        } else {
            new MaterialDialog.Builder(this.notesMainActivity).title(R.string.select_tags).items(TagsHelper.getTagsArray(allTags)).positiveText(R.string.ok).itemsCallbackMultiChoice(new Integer[0], new MaterialDialog.ListCallbackMultiChoice() { // from class: it.piapostolos.android.pinotes.-$$Lambda$ListFragment$2lPzJUeDbDG2GnbvdfR9P1O1rGU
                @Override // com.afollestad.materialdialogs.MaterialDialog.ListCallbackMultiChoice
                public final boolean onSelection(MaterialDialog materialDialog, Integer[] numArr, CharSequence[] charSequenceArr) {
                    return this.f$0.lambda$filterByTags$18$ListFragment(allTags, materialDialog, numArr, charSequenceArr);
                }
            }).build().show();
        }
    }

    public /* synthetic */ boolean lambda$filterByTags$18$ListFragment(List list, MaterialDialog materialDialog, Integer[] numArr, CharSequence[] charSequenceArr) {
        ArrayList arrayList = new ArrayList();
        for (Integer num : numArr) {
            arrayList.add(((Tag) list.get(num.intValue())).getText());
        }
        this.searchTags = arrayList.toString().substring(1, arrayList.toString().length() - 1).replace(" ", "");
        Intent intent = this.notesMainActivity.getIntent();
        this.searchView.clearFocus();
        KeyboardUtils.hideKeyboard(this.searchView);
        intent.removeExtra("query");
        initNotesList(intent);
        return false;
    }

    public MenuItem getSearchMenuItem() {
        return this.searchMenuItem;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFabAllowed() {
        return isFabAllowed(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFabAllowed(boolean z) throws Resources.NotFoundException {
        boolean z2 = getActionMode() == null || z;
        int navigation = Navigation.getNavigation();
        return (!(z2 && navigation != 1 && navigation != 2 && navigation != 3) || this.notesMainActivity.getDrawerLayout() == null || this.notesMainActivity.getDrawerLayout().isDrawerOpen(8388611)) ? false : true;
    }
}
