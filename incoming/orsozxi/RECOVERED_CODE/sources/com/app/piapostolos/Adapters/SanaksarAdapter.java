package com.app.piapostolos.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import com.app.piapostolos.Beans.Bible;
import com.app.piapostolos.Beans.WhiteBackgroundActivition;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.checksim;
import com.app.piapostolos.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class SanaksarAdapter extends ArrayAdapter<Bible> {
    private Context context;
    private LayoutInflater inflater;
    private int resource;

    public SanaksarAdapter(Context context, int i, List<Bible> list) {
        super(context, i, list);
        this.resource = i;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) throws SQLException {
        RelativeLayout relativeLayout = (RelativeLayout) this.inflater.inflate(this.resource, (ViewGroup) null);
        Bible item = getItem(i);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.ayahnum);
        textView.setText("");
        TextView textView2 = (TextView) relativeLayout.findViewById(R.id.gcm_msg);
        textView2.setTextAppearance(this.context, android.R.style.TextAppearance.Small);
        textView2.setText(item.getNick());
        checksim checksimVar = new checksim();
        DBAdapter_highlightAgpya dBAdapter_highlightAgpya = new DBAdapter_highlightAgpya(this.context);
        dBAdapter_highlightAgpya.open();
        Cursor cursorFetchAll = dBAdapter_highlightAgpya.fetchAll();
        new ArrayList();
        boolean z = false;
        if (cursorFetchAll.moveToFirst()) {
            do {
                String string = cursorFetchAll.getString(1);
                System.out.println("aya" + string + "ayahh" + item.getNick());
                if (string.equals(getItem(i).getNick())) {
                    System.out.println("equals");
                    System.out.println("sim");
                    textView2.setTextColor(SupportMenu.CATEGORY_MASK);
                    textView.setTextColor(SupportMenu.CATEGORY_MASK);
                    z = true;
                }
            } while (cursorFetchAll.moveToNext());
        }
        dBAdapter_highlightAgpya.close();
        textView2.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.context)).floatValue());
        textView.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.context)).floatValue());
        if (!z) {
            WhiteBackgroundActivition whiteBackgroundActivition = (WhiteBackgroundActivition) new SavePrefs(this.context, (Class<?>) WhiteBackgroundActivition.class).load();
            if (whiteBackgroundActivition == null || !whiteBackgroundActivition.isWhiteBackground()) {
                textView2.setTextColor(-1);
                textView.setTextColor(-1);
            } else {
                textView2.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                textView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
        }
        return relativeLayout;
    }
}
