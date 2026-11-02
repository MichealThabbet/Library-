package com.app.orsozoxi.Adapters;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.app.orsozoxi.Beans.Bible;
import com.app.orsozoxi.Beans.WhiteBackgroundActivition;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.checksim;
import com.app.orsozoxi.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class AgpyaRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<Bible> mItems;

    public AgpyaRecyclerAdapter(List<Bible> list, Activity activity) {
        this.mItems = list;
        this.activity = activity;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SimpleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_agpya_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) throws SQLException {
        SimpleViewHolder simpleViewHolder = new SimpleViewHolder(viewHolder.itemView);
        simpleViewHolder.mItem = this.mItems.get(i);
        simpleViewHolder.legendName.setText("");
        simpleViewHolder.we.setTextAppearance(this.activity, android.R.style.TextAppearance.Small);
        simpleViewHolder.we.setText(simpleViewHolder.mItem.getNick());
        checksim checksimVar = new checksim();
        DBAdapter_highlightAgpya dBAdapter_highlightAgpya = new DBAdapter_highlightAgpya(this.activity);
        dBAdapter_highlightAgpya.open();
        Cursor cursorFetchAll = dBAdapter_highlightAgpya.fetchAll();
        new ArrayList();
        boolean z = false;
        if (cursorFetchAll.moveToFirst()) {
            do {
                String string = cursorFetchAll.getString(1);
                System.out.println("aya" + string + "ayahh" + simpleViewHolder.mItem.getNick());
                if (string.equals(simpleViewHolder.mItem.getNick())) {
                    System.out.println("equals");
                    System.out.println("sim");
                    simpleViewHolder.we.setTextColor(SupportMenu.CATEGORY_MASK);
                    simpleViewHolder.legendName.setTextColor(SupportMenu.CATEGORY_MASK);
                    z = true;
                }
            } while (cursorFetchAll.moveToNext());
        }
        dBAdapter_highlightAgpya.close();
        simpleViewHolder.we.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
        simpleViewHolder.legendName.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
        if (z) {
            return;
        }
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs(this.activity, (Class<?>) WhiteBackgroundActivition.class).load();
        if (whiteBackgroundActivition != null) {
            if (!whiteBackgroundActivition.isWhiteBackground()) {
                simpleViewHolder.we.setTextColor(-1);
                simpleViewHolder.legendName.setTextColor(-1);
                return;
            } else {
                simpleViewHolder.we.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                simpleViewHolder.legendName.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                return;
            }
        }
        simpleViewHolder.we.setTextColor(-1);
        simpleViewHolder.legendName.setTextColor(-1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mItems.size();
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView legendName;
        public Bible mItem;
        public final TextView we;

        public SimpleViewHolder(View view) {
            super(view);
            this.legendName = (TextView) view.findViewById(R.id.ayahnum);
            this.we = (TextView) view.findViewById(R.id.gcm_msg);
        }
    }
}
