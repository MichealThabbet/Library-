package it.orsozoxi.android.orsonotes.models.adapters.category;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.orsozoxi.android.orsonotes.R2;

/* loaded from: classes3.dex */
public class CategoryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R2.id.count)
    public TextView count;

    @BindView(R2.id.icon)
    public ImageView imgIcon;

    @BindView(R2.id.title)
    public com.neopixl.pixlui.components.textview.TextView txtTitle;

    public CategoryViewHolder(View view) throws NoSuchMethodException, SecurityException {
        super(view);
        ButterKnife.bind(this, view);
    }
}
