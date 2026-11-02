package com.app.orsozoxi.ApsalmodyBeans;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.orsozoxi.Others.checksim;
import com.app.orsozoxi.R;
import com.app.orsozoxi.Views.CustomCopticTextView;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class PsalmodyAdapter extends BaseAdapter {
    private Context activity;
    private Typeface faceltr;
    private boolean isArabic;
    private boolean isArabicCoptic;
    private boolean isCoptic;
    private boolean isCustomCoptic;
    private ArrayList<psalomdyItem> psalmodyItems;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public PsalmodyAdapter(Context context, ArrayList<psalomdyItem> arrayList, boolean z, boolean z2, boolean z3) {
        this.isCustomCoptic = false;
        this.psalmodyItems = arrayList;
        this.activity = context;
        this.isArabicCoptic = z3;
        this.isCoptic = z2;
        this.isArabic = z;
        this.faceltr = Typeface.createFromAsset(context.getAssets(), "ABRAAM_0.TTF");
        if (Build.VERSION.SDK_INT >= 26) {
            this.isCustomCoptic = true;
            this.faceltr = Typeface.createFromAsset(context.getAssets(), "ABRAAM_0.TTF");
            return;
        }
        this.isCustomCoptic = false;
        if (Build.VERSION.SDK_INT < 23) {
            this.faceltr = Typeface.createFromAsset(context.getAssets(), "Athanasius.TTF");
        } else {
            this.faceltr = Typeface.createFromAsset(context.getAssets(), "ABRAAM_0.TTF");
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        ArrayList<psalomdyItem> arrayList = this.psalmodyItems;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.psalmodyItems.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        psalomdyItem psalomdyitem = this.psalmodyItems.get(i);
        checksim checksimVar = new checksim();
        ViewGroup viewGroup2 = (ViewGroup) layoutInflaterFrom.inflate(R.layout.psalfour, viewGroup, false);
        TextView textView = (TextView) viewGroup2.findViewById(R.id.tv_one);
        TextView textView2 = (TextView) viewGroup2.findViewById(R.id.tv_two);
        CustomCopticTextView customCopticTextView = (CustomCopticTextView) viewGroup2.findViewById(R.id.tv_three_custom_coptic);
        TextView textView3 = (TextView) viewGroup2.findViewById(R.id.tv_three);
        TextView textView4 = (TextView) viewGroup2.findViewById(R.id.tv_four);
        textView4.setVisibility(8);
        if (!this.isArabic || psalomdyitem.getArabicText() == null || TextUtils.isEmpty(psalomdyitem.getArabicText())) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setTypeface(this.faceltr);
            textView.setText(psalomdyitem.getArabicText());
        }
        if (!this.isArabicCoptic || psalomdyitem.getArabicCopticText() == null || TextUtils.isEmpty(psalomdyitem.getArabicCopticText())) {
            textView2.setVisibility(8);
            viewGroup2.findViewById(R.id.v_divider_1).setVisibility(8);
        } else {
            textView2.setVisibility(0);
            textView2.setText(psalomdyitem.getArabicCopticText());
            if (this.isArabic) {
                viewGroup2.findViewById(R.id.v_divider_1).setVisibility(0);
            }
        }
        if (!this.isCoptic || psalomdyitem.getCopticText() == null || TextUtils.isEmpty(psalomdyitem.getCopticText())) {
            textView3.setVisibility(8);
            customCopticTextView.setVisibility(8);
            viewGroup2.findViewById(R.id.v_divider_2).setVisibility(8);
        } else {
            if (this.isCustomCoptic) {
                customCopticTextView.setVisibility(0);
                customCopticTextView.setTypeface(this.faceltr);
                if (psalomdyitem.getCopticText() != null) {
                    if (textView.getVisibility() == 8 && textView2.getVisibility() == 8 && textView4.getVisibility() == 8) {
                        String strReplaceAll = psalomdyitem.getCopticText().replaceAll(" >", ">").replaceAll(" ", "%");
                        customCopticTextView.setLetterSpacing(-0.03f);
                        customCopticTextView.setText(strReplaceAll);
                    } else {
                        String strReplaceAll2 = psalomdyitem.getCopticText().replaceAll(" >", ">").replaceAll(" ", "%");
                        customCopticTextView.setLetterSpacing(0.02f);
                        customCopticTextView.setText(strReplaceAll2);
                    }
                }
            } else {
                textView3.setVisibility(0);
                textView3.setTypeface(this.faceltr);
                if (psalomdyitem.getCopticText() != null) {
                    textView3.setText(psalomdyitem.getCopticText().replaceAll("    ", " "));
                }
            }
            if (this.isArabic || this.isArabicCoptic) {
                viewGroup2.findViewById(R.id.v_divider_2).setVisibility(0);
            }
        }
        textView.setTextColor(Color.parseColor("#31698a"));
        textView2.setTextColor(Color.parseColor("#008080"));
        customCopticTextView.setTextColor(Color.parseColor("#000080"));
        textView3.setTextColor(Color.parseColor("#000080"));
        textView.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
        textView2.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
        textView3.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
        customCopticTextView.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
        LinearLayout linearLayout = (LinearLayout) viewGroup2.findViewById(R.id.item);
        if (psalomdyitem.isColored()) {
            linearLayout.setBackgroundColor(Color.parseColor("#d3d3d3"));
        }
        return viewGroup2;
    }
}
