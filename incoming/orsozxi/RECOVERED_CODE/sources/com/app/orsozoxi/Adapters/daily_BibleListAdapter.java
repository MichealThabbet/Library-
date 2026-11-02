package com.app.orsozoxi.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import com.app.orsozoxi.Beans.Bible;
import com.app.orsozoxi.Beans.WhiteBackgroundActivition;
import com.app.orsozoxi.Beans.highlightAyat;
import com.app.orsozoxi.Others.SavePrefs;
import com.app.orsozoxi.Others.checksim;
import com.app.orsozoxi.R;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class daily_BibleListAdapter extends ArrayAdapter<Bible> {
    String[] book;
    private Context context;
    private ArrayList<Integer> current;
    private int currentAyah;
    private boolean found;
    private LayoutInflater inflater;
    private int resource;

    public daily_BibleListAdapter(Context context, int i, List<Bible> list, String[] strArr, ArrayList<Integer> arrayList, int i2) {
        super(context, i, list);
        this.resource = i;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.book = strArr;
        this.current = arrayList;
        this.currentAyah = i2;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        boolean z;
        boolean z2;
        RelativeLayout relativeLayout = (RelativeLayout) this.inflater.inflate(this.resource, (ViewGroup) null);
        RelativeLayout relativeLayout2 = (RelativeLayout) relativeLayout.findViewById(R.id.relative_id);
        if (this.currentAyah == -1 || this.current == null) {
            z = false;
        } else {
            Log.d(RemoteMessageConst.Notification.TAG, "current" + this.current.size());
            z = false;
            for (int i2 = 0; i2 < this.current.size(); i2++) {
                if (i == this.current.get(i2).intValue()) {
                    Log.d(RemoteMessageConst.Notification.TAG, "pos=" + i);
                    relativeLayout2.setBackgroundColor(Color.parseColor("#A0000000"));
                    z = true;
                }
            }
        }
        if (!z) {
            Log.d(RemoteMessageConst.Notification.TAG, "not found");
            relativeLayout2.setBackgroundColor(0);
        }
        Bible item = getItem(i);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.gcm_msg);
        textView.setTextAppearance(this.context, android.R.style.TextAppearance.Small);
        textView.setText(item.getNick());
        checksim checksimVar = new checksim();
        highlightAyat highlightayat = (highlightAyat) new SavePrefs(this.context, (Class<?>) highlightAyat.class).load();
        if (highlightayat != null) {
            ArrayList<String> ayat = highlightayat.getAyat();
            z2 = false;
            for (int i3 = 0; i3 < ayat.size(); i3++) {
                if (ayat.get(i3).equals(getItem(i).getNick())) {
                    textView.setTextColor(SupportMenu.CATEGORY_MASK);
                    z2 = true;
                }
            }
        } else {
            z2 = false;
        }
        try {
            textView.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.context)).floatValue());
        } catch (Exception unused) {
        }
        if (!z2) {
            WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs(this.context, (Class<?>) WhiteBackgroundActivition.class).load();
            if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
                textView.setTextColor(-1);
            } else {
                textView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
        }
        for (int i4 = 0; i4 < this.book.length; i4++) {
            if (item.getNick().contains(this.book[i4])) {
                textView.setTextColor(Color.parseColor("#FF002A"));
                textView.setTextSize(30.0f);
            }
        }
        if (item.getNick().contains("الفترة الصباحية") || item.getNick().contains("الفترة المسائية")) {
            textView.setTextColor(Color.parseColor("#FF002A"));
            textView.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.context)).floatValue() + 10.0f);
        }
        return relativeLayout;
    }
}
