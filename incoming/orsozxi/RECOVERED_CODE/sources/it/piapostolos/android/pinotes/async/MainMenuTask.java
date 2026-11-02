package it.piapostolos.android.pinotes.async;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import it.piapostolos.android.pinotes.NotesMainActivity;
import it.piapostolos.android.pinotes.R;
import it.piapostolos.android.pinotes.R2;
import it.piapostolos.android.pinotes.async.bus.NavigationUpdatedEvent;
import it.piapostolos.android.pinotes.models.NavigationItem;
import it.piapostolos.android.pinotes.models.adapters.NavDrawerAdapter;
import it.piapostolos.android.pinotes.models.misc.DynamicNavigationLookupTable;
import it.piapostolos.android.pinotes.models.views.NonScrollableListView;
import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MainMenuTask extends AsyncTask<Void, Void, List<NavigationItem>> {

    @BindView(R2.id.drawer_tag_list)
    NonScrollableListView mDrawerCategoriesList;

    @BindView(R2.id.drawer_nav_list)
    NonScrollableListView mDrawerList;
    private final WeakReference<Fragment> mFragmentWeakReference;
    private final NotesMainActivity notesMainActivity;

    public MainMenuTask(Fragment fragment) throws NoSuchMethodException, SecurityException {
        this.mFragmentWeakReference = new WeakReference<>(fragment);
        this.notesMainActivity = (NotesMainActivity) fragment.getActivity();
        ButterKnife.bind(this, fragment.getView());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public List<NavigationItem> doInBackground(Void... voidArr) {
        return buildMainMenu();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(final List<NavigationItem> list) {
        if (isAlive()) {
            this.mDrawerList.setAdapter((ListAdapter) new NavDrawerAdapter(this.notesMainActivity, list));
            this.mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: it.piapostolos.android.pinotes.async.-$$Lambda$MainMenuTask$jmflMkM36EYqzPHC91jcX-smYZI
                @Override // android.widget.AdapterView.OnItemClickListener
                public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                    this.f$0.lambda$onPostExecute$0$MainMenuTask(list, adapterView, view, i, j);
                }
            });
            this.mDrawerList.justifyListViewHeightBasedOnChildren();
        }
    }

    public /* synthetic */ void lambda$onPostExecute$0$MainMenuTask(List list, AdapterView adapterView, View view, int i, long j) {
        if (this.notesMainActivity.updateNavigation(this.mFragmentWeakReference.get().getResources().getStringArray(R.array.navigation_list_codes)[((NavigationItem) list.get(i)).getArrayIndex()])) {
            this.mDrawerList.setItemChecked(i, true);
            NonScrollableListView nonScrollableListView = this.mDrawerCategoriesList;
            if (nonScrollableListView != null) {
                nonScrollableListView.setItemChecked(0, false);
            }
            this.notesMainActivity.getIntent().setAction("android.intent.action.MAIN");
            EventBus.getDefault().post(new NavigationUpdatedEvent(this.mDrawerList.getItemAtPosition(i)));
        }
    }

    private boolean isAlive() {
        return (this.mFragmentWeakReference.get() == null || !this.mFragmentWeakReference.get().isAdded() || this.mFragmentWeakReference.get().getActivity() == null || this.mFragmentWeakReference.get().getActivity().isFinishing()) ? false : true;
    }

    private List<NavigationItem> buildMainMenu() throws Resources.NotFoundException {
        if (!isAlive()) {
            return new ArrayList();
        }
        String[] stringArray = this.notesMainActivity.getResources().getStringArray(R.array.navigation_list);
        TypedArray typedArrayObtainTypedArray = this.notesMainActivity.getResources().obtainTypedArray(R.array.navigation_list_icons);
        TypedArray typedArrayObtainTypedArray2 = this.notesMainActivity.getResources().obtainTypedArray(R.array.navigation_list_icons_selected);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < stringArray.length; i++) {
            if (!checkSkippableItem(i)) {
                arrayList.add(new NavigationItem(i, stringArray[i], typedArrayObtainTypedArray.getResourceId(i, 0), typedArrayObtainTypedArray2.getResourceId(i, 0)));
            }
        }
        return arrayList;
    }

    private boolean checkSkippableItem(int i) {
        SharedPreferences sharedPreferences = this.notesMainActivity.getSharedPreferences("PiNotes", 4);
        boolean z = sharedPreferences.getBoolean(ConstantsBase.PREF_DYNAMIC_MENU, true);
        DynamicNavigationLookupTable dynamicNavigationLookupTable = z ? DynamicNavigationLookupTable.getInstance() : null;
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        if (!sharedPreferences.getBoolean(ConstantsBase.PREF_SHOW_UNCATEGORIZED, false)) {
                            return true;
                        }
                        if (z && dynamicNavigationLookupTable.getUncategorized() == 0) {
                            return true;
                        }
                    }
                } else if (z && dynamicNavigationLookupTable.getTrashed() == 0) {
                    return true;
                }
            } else if (z && dynamicNavigationLookupTable.getReminders() == 0) {
                return true;
            }
        } else if (z && dynamicNavigationLookupTable.getArchived() == 0) {
            return true;
        }
        return false;
    }
}
