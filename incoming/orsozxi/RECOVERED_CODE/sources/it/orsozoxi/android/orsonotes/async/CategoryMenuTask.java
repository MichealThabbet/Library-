package it.orsozoxi.android.orsonotes.async;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import androidx.fragment.app.Fragment;
import de.greenrobot.event.EventBus;
import it.orsozoxi.android.orsonotes.NotesMainActivity;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.SettingsActivity;
import it.orsozoxi.android.orsonotes.async.bus.NavigationUpdatedEvent;
import it.orsozoxi.android.orsonotes.db.DbHelper;
import it.orsozoxi.android.orsonotes.models.Category;
import it.orsozoxi.android.orsonotes.models.ONStyle;
import it.orsozoxi.android.orsonotes.models.adapters.CategoryBaseAdapter;
import it.orsozoxi.android.orsonotes.models.views.NonScrollableListView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class CategoryMenuTask extends AsyncTask<Void, Void, List<Category>> {
    private NonScrollableListView mDrawerCategoriesList;
    private NonScrollableListView mDrawerList;
    private final WeakReference<Fragment> mFragmentWeakReference;
    private final NotesMainActivity notesMainActivity;
    private View settingsView;
    private View settingsViewCat;

    public CategoryMenuTask(Fragment fragment) {
        this.mFragmentWeakReference = new WeakReference<>(fragment);
        this.notesMainActivity = (NotesMainActivity) fragment.getActivity();
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        super.onPreExecute();
        this.mDrawerList = (NonScrollableListView) this.notesMainActivity.findViewById(R.id.drawer_nav_list);
        LayoutInflater layoutInflater = (LayoutInflater) this.notesMainActivity.getSystemService("layout_inflater");
        this.settingsView = this.notesMainActivity.findViewById(R.id.settings_view);
        NonScrollableListView nonScrollableListView = (NonScrollableListView) this.notesMainActivity.findViewById(R.id.drawer_tag_list);
        this.mDrawerCategoriesList = nonScrollableListView;
        if (nonScrollableListView.getAdapter() == null && this.mDrawerCategoriesList.getFooterViewsCount() == 0) {
            View viewInflate = layoutInflater.inflate(R.layout.drawer_category_list_footer, (ViewGroup) null);
            this.settingsViewCat = viewInflate;
            this.mDrawerCategoriesList.addFooterView(viewInflate);
        } else {
            this.settingsViewCat = this.mDrawerCategoriesList.getChildAt(r0.getChildCount() - 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public List<Category> doInBackground(Void... voidArr) {
        if (isAlive()) {
            return buildCategoryMenu();
        }
        cancel(true);
        return Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(List<Category> list) {
        if (isAlive()) {
            NonScrollableListView nonScrollableListView = this.mDrawerCategoriesList;
            NotesMainActivity notesMainActivity = this.notesMainActivity;
            nonScrollableListView.setAdapter((ListAdapter) new CategoryBaseAdapter(notesMainActivity, list, notesMainActivity.getNavigationTmp()));
            if (list.isEmpty()) {
                setWidgetVisibility(this.settingsViewCat, false);
                setWidgetVisibility(this.settingsView, true);
            } else {
                setWidgetVisibility(this.settingsViewCat, true);
                setWidgetVisibility(this.settingsView, false);
            }
            this.mDrawerCategoriesList.justifyListViewHeightBasedOnChildren();
        }
    }

    private void setWidgetVisibility(View view, boolean z) {
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
        }
    }

    private boolean isAlive() {
        return (this.mFragmentWeakReference.get() == null || !this.mFragmentWeakReference.get().isAdded() || this.mFragmentWeakReference.get().getActivity() == null || this.mFragmentWeakReference.get().getActivity().isFinishing()) ? false : true;
    }

    private List<Category> buildCategoryMenu() {
        ArrayList<Category> categories = DbHelper.getInstance().getCategories();
        final View view = categories.isEmpty() ? this.settingsView : this.settingsViewCat;
        if (view == null) {
            return categories;
        }
        this.notesMainActivity.runOnUiThread(new Runnable() { // from class: it.orsozoxi.android.orsonotes.async.-$$Lambda$CategoryMenuTask$EpJJs_E9OZLLqpFQh2V1Oim2VOA
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$buildCategoryMenu$1$CategoryMenuTask(view);
            }
        });
        return categories;
    }

    public /* synthetic */ void lambda$buildCategoryMenu$1$CategoryMenuTask(View view) {
        view.setOnClickListener(new View.OnClickListener() { // from class: it.orsozoxi.android.orsonotes.async.-$$Lambda$CategoryMenuTask$OM_wJxMQemRysoEcSIkxBlQohrw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$buildCategoryMenu$0$CategoryMenuTask(view2);
            }
        });
        buildCategoryMenuClickEvent();
        buildCategoryMenuLongClickEvent();
    }

    public /* synthetic */ void lambda$buildCategoryMenu$0$CategoryMenuTask(View view) {
        this.notesMainActivity.startActivity(new Intent(this.notesMainActivity, (Class<?>) SettingsActivity.class));
    }

    private void buildCategoryMenuLongClickEvent() {
        this.mDrawerCategoriesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: it.orsozoxi.android.orsonotes.async.-$$Lambda$CategoryMenuTask$jsurIMlpWZZwEE1n1zr-B2AzWFQ
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public final boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
                return this.f$0.lambda$buildCategoryMenuLongClickEvent$2$CategoryMenuTask(adapterView, view, i, j);
            }
        });
    }

    public /* synthetic */ boolean lambda$buildCategoryMenuLongClickEvent$2$CategoryMenuTask(AdapterView adapterView, View view, int i, long j) {
        if (this.mDrawerCategoriesList.getAdapter() != null) {
            Object item = this.mDrawerCategoriesList.getAdapter().getItem(i);
            if (item == null) {
                return true;
            }
            this.notesMainActivity.editTag((Category) item);
            return true;
        }
        this.notesMainActivity.showMessage(R.string.category_deleted, ONStyle.ALERT);
        return true;
    }

    private void buildCategoryMenuClickEvent() {
        this.mDrawerCategoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: it.orsozoxi.android.orsonotes.async.-$$Lambda$CategoryMenuTask$NLXd5QEzDUKYfpqa9J9ayq3psHk
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                this.f$0.lambda$buildCategoryMenuClickEvent$3$CategoryMenuTask(adapterView, view, i, j);
            }
        });
    }

    public /* synthetic */ void lambda$buildCategoryMenuClickEvent$3$CategoryMenuTask(AdapterView adapterView, View view, int i, long j) {
        if (this.notesMainActivity.updateNavigation(String.valueOf(((Category) this.mDrawerCategoriesList.getAdapter().getItem(i)).getId()))) {
            this.mDrawerCategoriesList.setItemChecked(i, true);
            NonScrollableListView nonScrollableListView = this.mDrawerList;
            if (nonScrollableListView != null) {
                nonScrollableListView.setItemChecked(0, false);
                EventBus.getDefault().post(new NavigationUpdatedEvent(this.mDrawerCategoriesList.getItemAtPosition(i)));
            }
        }
    }
}
