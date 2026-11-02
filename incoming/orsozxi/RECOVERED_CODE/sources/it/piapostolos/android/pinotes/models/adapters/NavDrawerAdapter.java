package it.piapostolos.android.pinotes.models.adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.neopixl.pixlui.components.textview.TextView;
import it.piapostolos.android.pinotes.NotesMainActivity;
import it.piapostolos.android.pinotes.R;
import it.piapostolos.android.pinotes.models.NavigationItem;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class NavDrawerAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<NavigationItem> items;
    private Activity mActivity;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public NavDrawerAdapter(Activity activity, List<NavigationItem> list) {
        this.mActivity = activity;
        this.items = list;
        this.inflater = (LayoutInflater) activity.getSystemService("layout_inflater");
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.items.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.items.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
        NoteDrawerAdapterViewHolder noteDrawerAdapterViewHolder;
        if (view == null) {
            view = this.inflater.inflate(R.layout.drawer_list_item, viewGroup, false);
            noteDrawerAdapterViewHolder = new NoteDrawerAdapterViewHolder();
            noteDrawerAdapterViewHolder.imgIcon = (ImageView) view.findViewById(R.id.icon);
            noteDrawerAdapterViewHolder.txtTitle = (TextView) view.findViewById(R.id.title);
            view.setTag(noteDrawerAdapterViewHolder);
        } else {
            noteDrawerAdapterViewHolder = (NoteDrawerAdapterViewHolder) view.getTag();
        }
        noteDrawerAdapterViewHolder.txtTitle.setText(this.items.get(i).getText());
        if (isSelected(i)) {
            noteDrawerAdapterViewHolder.imgIcon.setImageResource(this.items.get(i).getIconSelected());
            noteDrawerAdapterViewHolder.txtTitle.setTypeface(null, 1);
            int color = this.mActivity.getResources().getColor(R.color.colorPrimaryDark);
            noteDrawerAdapterViewHolder.txtTitle.setTextColor(color);
            noteDrawerAdapterViewHolder.imgIcon.getDrawable().mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        } else {
            noteDrawerAdapterViewHolder.imgIcon.setImageResource(this.items.get(i).getIcon());
            noteDrawerAdapterViewHolder.txtTitle.setTypeface(null, 0);
            noteDrawerAdapterViewHolder.txtTitle.setTextColor(this.mActivity.getResources().getColor(R.color.drawer_text));
        }
        return view;
    }

    private boolean isSelected(int i) throws Resources.NotFoundException {
        String[] stringArray = this.mActivity.getResources().getStringArray(R.array.navigation_list_codes);
        String navigationTmp = NotesMainActivity.class.isAssignableFrom(this.mActivity.getClass()) ? ((NotesMainActivity) this.mActivity).getNavigationTmp() : null;
        if (navigationTmp == null) {
            navigationTmp = this.mActivity.getSharedPreferences("PiNotes", 4).getString("navigation", stringArray[0]);
        }
        int iIndexOf = Arrays.asList(stringArray).indexOf(navigationTmp);
        if (iIndexOf == -1) {
            return false;
        }
        return this.mActivity.getResources().getStringArray(R.array.navigation_list)[iIndexOf].equals(this.items.get(i).getText());
    }
}
