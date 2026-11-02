package it.orsozoxi.android.orsonotes.models.adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import it.orsozoxi.android.orsonotes.NotesMainActivity;
import it.orsozoxi.android.orsonotes.R;
import it.orsozoxi.android.orsonotes.models.Category;
import it.orsozoxi.android.orsonotes.models.adapters.category.CategoryViewHolder;
import java.util.List;

/* loaded from: classes3.dex */
public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    private List<Category> categories;
    private Activity mActivity;
    private String navigationTmp;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    public CategoryRecyclerViewAdapter(Activity activity, List<Category> list) {
        this(activity, list, null);
    }

    public CategoryRecyclerViewAdapter(Activity activity, List<Category> list, String str) {
        this.mActivity = activity;
        this.categories = list;
        this.navigationTmp = str;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.categories.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CategoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int i) throws Resources.NotFoundException {
        Category category = this.categories.get(i);
        categoryViewHolder.txtTitle.setText(category.getName());
        if (isSelected(i)) {
            categoryViewHolder.txtTitle.setTypeface(null, 1);
            categoryViewHolder.txtTitle.setTextColor(Integer.parseInt(category.getColor()));
        } else {
            categoryViewHolder.txtTitle.setTypeface(null, 0);
            categoryViewHolder.txtTitle.setTextColor(this.mActivity.getResources().getColor(R.color.drawer_text));
        }
        if (category.getColor() != null && category.getColor().length() > 0) {
            Drawable drawable = this.mActivity.getResources().getDrawable(R.drawable.ic_folder_special_black_24dp);
            drawable.setColorFilter(new LightingColorFilter(Color.parseColor("#000000"), Integer.parseInt(category.getColor())));
            categoryViewHolder.imgIcon.setImageDrawable(drawable);
            categoryViewHolder.imgIcon.setPadding(4, 4, 4, 4);
        }
        showCategoryCounter(categoryViewHolder, category);
    }

    private void showCategoryCounter(CategoryViewHolder categoryViewHolder, Category category) {
        if (this.mActivity.getSharedPreferences("OrsoNotes", 4).getBoolean("settings_show_category_count", true)) {
            categoryViewHolder.count.setText(String.valueOf(category.getCount()));
            categoryViewHolder.count.setVisibility(0);
        }
    }

    private boolean isSelected(int i) throws Resources.NotFoundException {
        String[] stringArray = this.mActivity.getResources().getStringArray(R.array.navigation_list_codes);
        String navigationTmp = NotesMainActivity.class.isAssignableFrom(this.mActivity.getClass()) ? ((NotesMainActivity) this.mActivity).getNavigationTmp() : null;
        String str = this.navigationTmp;
        if (str != null) {
            navigationTmp = str;
        }
        if (this.navigationTmp == null) {
            navigationTmp = this.mActivity.getSharedPreferences("OrsoNotes", 4).getString("navigation", stringArray[0]);
        }
        return navigationTmp.equals(String.valueOf(this.categories.get(i).getId()));
    }
}
