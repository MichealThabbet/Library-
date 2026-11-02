package com.app.orsozoxi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import com.app.orsozoxi.Beans.WhiteBackgroundActivition;
import com.app.orsozoxi.BibleDataBase.BibleDataBase;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.checksim;
import com.app.orsozoxi.R;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Result_BibleAdapter extends ArrayAdapter<BibleDataBase> {
    private Context context;
    private LayoutInflater inflater;
    private int resource;
    private ArrayList<BibleDataBase> searchResultBeans;

    public Result_BibleAdapter(Context context, int i, ArrayList<BibleDataBase> arrayList) {
        super(context, i, arrayList);
        this.resource = i;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.searchResultBeans = arrayList;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        RelativeLayout relativeLayout = (RelativeLayout) this.inflater.inflate(this.resource, (ViewGroup) null);
        BibleDataBase item = getItem(i);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.title);
        textView.setText(item.getBookName() + " " + item.getChapterName() + " آية " + (item.getAyahIndex() + 1));
        TextView textView2 = (TextView) relativeLayout.findViewById(R.id.content);
        textView2.setTextAppearance(this.context, android.R.style.TextAppearance.Small);
        textView2.setText(item.getAyah());
        checksim checksimVar = new checksim();
        textView2.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.context)).floatValue());
        textView.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.context)).floatValue());
        textView.setTextColor(SupportMenu.CATEGORY_MASK);
        WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs(this.context, (Class<?>) WhiteBackgroundActivition.class).load();
        if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
            textView2.setTextColor(-1);
        } else {
            textView2.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
        return relativeLayout;
    }
}
