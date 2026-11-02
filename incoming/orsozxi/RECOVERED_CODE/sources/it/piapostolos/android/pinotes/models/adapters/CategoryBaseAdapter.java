package it.piapostolos.android.pinotes.models.adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.neopixl.pixlui.components.textview.TextView;
import it.piapostolos.android.pinotes.NotesMainActivity;
import it.piapostolos.android.pinotes.R;
import it.piapostolos.android.pinotes.models.Category;
import it.piapostolos.android.pinotes.models.adapters.category.CategoryViewHolder;
import java.util.List;

/* loaded from: classes3.dex */
public class CategoryBaseAdapter extends BaseAdapter {
    private List<Category> categories;
    private LayoutInflater inflater;
    private int layout;
    private Activity mActivity;
    private final String navigationTmp;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public CategoryBaseAdapter(Activity activity, List<Category> list) {
        this(activity, list, null);
    }

    public CategoryBaseAdapter(Activity activity, List<Category> list, String str) {
        this.mActivity = activity;
        this.layout = R.layout.drawer_list_item;
        this.categories = list;
        this.navigationTmp = str;
        this.inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.categories.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.categories.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
        CategoryViewHolder categoryViewHolder;
        Category category = this.categories.get(i);
        if (view == null) {
            view = this.inflater.inflate(this.layout, viewGroup, false);
            categoryViewHolder = new CategoryViewHolder(view);
            categoryViewHolder.imgIcon = (ImageView) view.findViewById(R.id.icon);
            categoryViewHolder.txtTitle = (TextView) view.findViewById(R.id.title);
            categoryViewHolder.count = (android.widget.TextView) view.findViewById(R.id.count);
            view.setTag(categoryViewHolder);
        } else {
            categoryViewHolder = (CategoryViewHolder) view.getTag();
        }
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
            LightingColorFilter lightingColorFilter = new LightingColorFilter(Color.parseColor("#000000"), Integer.parseInt(category.getColor()));
            if (Build.VERSION.SDK_INT >= 16) {
                drawable.mutate().setColorFilter(lightingColorFilter);
            } else {
                drawable.setColorFilter(lightingColorFilter);
            }
            categoryViewHolder.imgIcon.setImageDrawable(drawable);
            categoryViewHolder.imgIcon.setPadding(4, 4, 4, 4);
        }
        if (this.mActivity.getSharedPreferences("PiNotes", 4).getBoolean("settings_show_category_count", true)) {
            categoryViewHolder.count.setText(String.valueOf(category.getCount()));
            categoryViewHolder.count.setVisibility(0);
        }
        return view;
    }

    private boolean isSelected(int i) throws Resources.NotFoundException {
        String[] stringArray = this.mActivity.getResources().getStringArray(R.array.navigation_list_codes);
        String navigationTmp = NotesMainActivity.class.isAssignableFrom(this.mActivity.getClass()) ? ((NotesMainActivity) this.mActivity).getNavigationTmp() : null;
        String str = this.navigationTmp;
        if (str != null) {
            navigationTmp = str;
        }
        if (this.navigationTmp == null) {
            navigationTmp = this.mActivity.getSharedPreferences("PiNotes", 4).getString("navigation", stringArray[0]);
        }
        return navigationTmp.equals(String.valueOf(this.categories.get(i).getId()));
    }
}
