package com.app.piapostolos.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import com.app.piapostolos.Beans.Bible;
import com.app.piapostolos.Beans.WhiteBackgroundActivition;
import com.app.piapostolos.Beans.highlightAyat;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.Others.checksim;
import com.app.piapostolos.R;
import com.app.piapostolos.ShawahedBeans.AyahShwahed;
import com.app.piapostolos.ShawahedBeans.BibleFunctions;
import com.app.piapostolos.ShawahedBeans.SafrWithShwahed;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class Newbible_BibleListAdapter extends ArrayAdapter<Bible> {
    private Typeface arialFontTypeface;
    String[] book;
    private Activity context;
    private ArrayList<Integer> current;
    private int currentAs7a7;
    private int currentAyah;
    private boolean found;
    private LayoutInflater inflater;
    private boolean isShawahedAvailable;
    private int resource;
    private SafrWithShwahed safr;

    public Newbible_BibleListAdapter(Activity activity, int i, List<Bible> list, String[] strArr, ArrayList<Integer> arrayList, int i2, SafrWithShwahed safrWithShwahed, boolean z, int i3) {
        super(activity, i, list);
        this.resource = i;
        this.inflater = LayoutInflater.from(activity);
        this.context = activity;
        this.book = strArr;
        this.current = arrayList;
        this.currentAyah = i2;
        this.safr = safrWithShwahed;
        this.isShawahedAvailable = z;
        this.currentAs7a7 = i3;
        this.arialFontTypeface = Typeface.createFromAsset(activity.getAssets(), "arial.ttf");
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        boolean z;
        boolean z2;
        if (view == null) {
            view = (ViewGroup) this.inflater.inflate(R.layout.row_agpya_item, viewGroup, false);
        }
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_id);
        if (this.currentAyah == -1 || this.current == null) {
            z = false;
        } else {
            Log.d(RemoteMessageConst.Notification.TAG, "current" + this.current.size());
            z = false;
            for (int i2 = 0; i2 < this.current.size(); i2++) {
                if (i == this.current.get(i2).intValue()) {
                    Log.d(RemoteMessageConst.Notification.TAG, "pos=" + i);
                    relativeLayout.setBackgroundColor(Color.parseColor("#A0000000"));
                    z = true;
                }
            }
        }
        if (!z) {
            Log.d(RemoteMessageConst.Notification.TAG, "not found");
            relativeLayout.setBackgroundColor(0);
        }
        Bible item = getItem(i);
        TextView textView = (TextView) view.findViewById(R.id.gcm_msg);
        textView.setTextAppearance(this.context, android.R.style.TextAppearance.Small);
        textView.setTypeface(this.arialFontTypeface);
        SafrWithShwahed safrWithShwahed = this.safr;
        if (safrWithShwahed == null || !this.isShawahedAvailable || safrWithShwahed.getAs7a7at() == null) {
            textView.setText(item.getNick());
        } else {
            int i3 = 0;
            while (true) {
                if (i3 >= this.safr.getAs7a7at().size()) {
                    i3 = -1;
                    break;
                }
                if (this.currentAs7a7 + 1 == this.safr.getAs7a7at().get(i3).getAs7a7Id()) {
                    break;
                }
                i3++;
            }
            if (i3 != -1) {
                ArrayList<AyahShwahed> ayat = this.safr.getAs7a7at().get(i3).getAyat();
                int i4 = 0;
                while (true) {
                    if (i4 >= ayat.size()) {
                        i4 = -1;
                        break;
                    }
                    if (i + 1 == ayat.get(i4).getAyahId()) {
                        break;
                    }
                    i4++;
                }
                if (i4 != -1) {
                    BibleFunctions.setAyahWithShawahedText(this.context, ayat.get(i4).getShwahed(), textView, item.getNick(), null);
                    textView.setOnTouchListener(new View.OnTouchListener() { // from class: com.app.piapostolos.Adapters.Newbible_BibleListAdapter.1
                        @Override // android.view.View.OnTouchListener
                        public boolean onTouch(View view2, MotionEvent motionEvent) {
                            TextView textView2 = (TextView) view2;
                            Spannable spannableNewSpannable = Spannable.Factory.getInstance().newSpannable(textView2.getText());
                            int action = motionEvent.getAction();
                            if (action != 1 && action != 0) {
                                return false;
                            }
                            int x = (int) motionEvent.getX();
                            int y = (int) motionEvent.getY();
                            int totalPaddingLeft = x - textView2.getTotalPaddingLeft();
                            int totalPaddingTop = y - textView2.getTotalPaddingTop();
                            int scrollX = totalPaddingLeft + textView2.getScrollX();
                            int scrollY = totalPaddingTop + textView2.getScrollY();
                            Layout layout = textView2.getLayout();
                            int offsetForHorizontal = layout.getOffsetForHorizontal(layout.getLineForVertical(scrollY), scrollX);
                            ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spannableNewSpannable.getSpans(offsetForHorizontal, offsetForHorizontal, ClickableSpan.class);
                            if (clickableSpanArr.length == 0) {
                                return false;
                            }
                            if (action == 1) {
                                clickableSpanArr[0].onClick(textView2);
                            }
                            return true;
                        }
                    });
                } else {
                    textView.setText(item.getNick());
                }
            } else {
                textView.setText(item.getNick());
            }
        }
        checksim checksimVar = new checksim();
        highlightAyat highlightayat = (highlightAyat) new SavePrefs(this.context, (Class<?>) highlightAyat.class).load();
        if (highlightayat != null) {
            ArrayList<String> ayat2 = highlightayat.getAyat();
            z2 = false;
            for (int i5 = 0; i5 < ayat2.size(); i5++) {
                if (ayat2.get(i5).equals(getItem(i).getNick())) {
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
        for (int i6 = 0; i6 < this.book.length; i6++) {
            if (item.getNick().contains(this.book[i6])) {
                textView.setTextColor(Color.parseColor("#FF002A"));
                textView.setTextSize(30.0f);
            }
        }
        return view;
    }
}
