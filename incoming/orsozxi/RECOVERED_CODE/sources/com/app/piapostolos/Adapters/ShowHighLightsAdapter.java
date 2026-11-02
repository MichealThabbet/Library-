package com.app.piapostolos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.app.piapostolos.Others.checksim;
import com.app.piapostolos.R;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ShowHighLightsAdapter extends ArrayAdapter<String> {
    private Context context;
    private LayoutInflater inflater;
    private int resource;
    private ArrayList<String> searchResultBeans;

    public ShowHighLightsAdapter(Context context, int i, ArrayList<String> arrayList) {
        super(context, i, arrayList);
        this.resource = i;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.searchResultBeans = arrayList;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        RelativeLayout relativeLayout = (RelativeLayout) this.inflater.inflate(this.resource, (ViewGroup) null);
        String item = getItem(i);
        checksim checksimVar = new checksim();
        TextView textView = (TextView) relativeLayout.findViewById(R.id.title);
        textView.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.context)).floatValue());
        textView.setText(item);
        return relativeLayout;
    }
}
