package it.orsozoxi.android.orsonotes.models.adapters.category;

import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.neopixl.pixlui.components.textview.TextView;
import it.orsozoxi.android.orsonotes.R;

/* loaded from: classes3.dex */
public class CategoryViewHolder_ViewBinding implements Unbinder {
    private CategoryViewHolder target;

    public CategoryViewHolder_ViewBinding(CategoryViewHolder categoryViewHolder, View view) {
        this.target = categoryViewHolder;
        categoryViewHolder.imgIcon = (ImageView) Utils.findRequiredViewAsType(view, R.id.icon, "field 'imgIcon'", ImageView.class);
        categoryViewHolder.txtTitle = (TextView) Utils.findRequiredViewAsType(view, R.id.title, "field 'txtTitle'", TextView.class);
        categoryViewHolder.count = (android.widget.TextView) Utils.findRequiredViewAsType(view, R.id.count, "field 'count'", android.widget.TextView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        CategoryViewHolder categoryViewHolder = this.target;
        if (categoryViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        categoryViewHolder.imgIcon = null;
        categoryViewHolder.txtTitle = null;
        categoryViewHolder.count = null;
    }
}
