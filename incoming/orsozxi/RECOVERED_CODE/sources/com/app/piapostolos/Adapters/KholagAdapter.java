package com.app.piapostolos.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.app.piapostolos.KholgayBeans.khloagyContentItem;
import com.app.piapostolos.Others.checksim;
import com.app.piapostolos.R;
import com.app.piapostolos.Views.CustomCopticTextView;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class KholagAdapter extends BaseAdapter {
    private Context activity;
    private Typeface faceltr;
    private Typeface faceltrAvva;
    private String fontId;
    private boolean isArabic;
    private boolean isArabicCoptic;
    private boolean isCoptic;
    private boolean isCustomCoptic;
    private boolean isEnglish;
    private boolean isSanaksar;
    private boolean isSpecialData;
    private ArrayList<khloagyContentItem> psalmodyItems;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public KholagAdapter(Context context, String str, ArrayList<khloagyContentItem> arrayList, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.isCustomCoptic = false;
        this.psalmodyItems = arrayList;
        this.activity = context;
        this.isArabicCoptic = z3;
        this.isCoptic = z2;
        this.isArabic = z;
        this.isEnglish = z4;
        this.fontId = str;
        this.isSpecialData = z5;
        this.isSanaksar = z6;
        this.faceltrAvva = Typeface.createFromAsset(context.getAssets(), "Avva_Shenouda.ttf");
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
        return this.psalmodyItems.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.psalmodyItems.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        checksim checksimVar = new checksim();
        khloagyContentItem khloagycontentitem = this.psalmodyItems.get(i);
        if (this.isSanaksar) {
            ViewGroup viewGroup2 = (ViewGroup) layoutInflaterFrom.inflate(R.layout.khloagy_special_data, viewGroup, false);
            TextView textView = (TextView) viewGroup2.findViewById(R.id.tv_one);
            textView.setText(khloagycontentitem.getTitleOrComment());
            textView.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
            return viewGroup2;
        }
        if (this.isSpecialData) {
            if (khloagycontentitem.getTitleOrComment().contains("مزمور عشية") || khloagycontentitem.getTitleOrComment().contains("إنجيل عشية") || khloagycontentitem.getTitleOrComment().contains("مزمور باكر") || khloagycontentitem.getTitleOrComment().contains("إنجيل باكر") || khloagycontentitem.getTitleOrComment().contains("البولس") || khloagycontentitem.getTitleOrComment().contains("الكاثوليكون") || khloagycontentitem.getTitleOrComment().contains("الإبركسيس") || khloagycontentitem.getTitleOrComment().contains("مزمور الأنجيل") || khloagycontentitem.getTitleOrComment().contains("الإنجيل") || khloagycontentitem.getTitleOrComment().contains("النبؤات") || khloagycontentitem.getTitleOrComment().contains("مزمور المساء") || khloagycontentitem.getTitleOrComment().contains("الفترة الصباحية") || khloagycontentitem.getTitleOrComment().contains("الفترة المسائية") || khloagycontentitem.getTitleOrComment().contains("عشية") || khloagycontentitem.getTitleOrComment().contains("باكر") || khloagycontentitem.getTitleOrComment().contains("القداس") || khloagycontentitem.getTitleOrComment().contains("المزمور") || ((khloagycontentitem.getTitleOrComment().contains(":") && khloagycontentitem.getTitleOrComment().contains("سفر")) || ((khloagycontentitem.getTitleOrComment().contains(":") && khloagycontentitem.getTitleOrComment().contains("رسالة")) || ((khloagycontentitem.getTitleOrComment().contains(":") && khloagycontentitem.getTitleOrComment().contains("مزامير")) || ((khloagycontentitem.getTitleOrComment().contains(":") && khloagycontentitem.getTitleOrComment().contains("إنجيل")) || ((khloagycontentitem.getTitleOrComment().contains(":") && khloagycontentitem.getTitleOrComment().contains("مزمور")) || (khloagycontentitem.getTitleOrComment().contains(":") && khloagycontentitem.getTitleOrComment().contains("انجيل")))))))) {
                ViewGroup viewGroup3 = (ViewGroup) layoutInflaterFrom.inflate(R.layout.khloagy_title_comment, viewGroup, false);
                TextView textView2 = (TextView) viewGroup3.findViewById(R.id.tv_one);
                textView2.setText(khloagycontentitem.getTitleOrComment());
                textView2.setTextColor(Color.parseColor("#FF002A"));
                textView2.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
                return viewGroup3;
            }
            ViewGroup viewGroup4 = (ViewGroup) layoutInflaterFrom.inflate(R.layout.khloagy_special_data, viewGroup, false);
            TextView textView3 = (TextView) viewGroup4.findViewById(R.id.tv_one);
            textView3.setText(khloagycontentitem.getTitleOrComment());
            textView3.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
            return viewGroup4;
        }
        if (khloagycontentitem.isTitleOrComment()) {
            ViewGroup viewGroup5 = (ViewGroup) layoutInflaterFrom.inflate(R.layout.khloagy_title_comment, viewGroup, false);
            TextView textView4 = (TextView) viewGroup5.findViewById(R.id.tv_one);
            textView4.setText(khloagycontentitem.getTitleOrComment());
            textView4.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
            return viewGroup5;
        }
        ViewGroup viewGroup6 = (ViewGroup) layoutInflaterFrom.inflate(R.layout.psalfour, viewGroup, false);
        TextView textView5 = (TextView) viewGroup6.findViewById(R.id.tv_one);
        TextView textView6 = (TextView) viewGroup6.findViewById(R.id.tv_two);
        CustomCopticTextView customCopticTextView = (CustomCopticTextView) viewGroup6.findViewById(R.id.tv_three_custom_coptic);
        TextView textView7 = (TextView) viewGroup6.findViewById(R.id.tv_three);
        TextView textView8 = (TextView) viewGroup6.findViewById(R.id.tv_four);
        if (i % 2 != 0) {
            viewGroup6.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            viewGroup6.setBackgroundColor(Color.parseColor("#c5c4c4"));
        }
        if (!this.isArabic || !khloagycontentitem.isArabicFound() || khloagycontentitem.getArabicText() == null || TextUtils.isEmpty(khloagycontentitem.getArabicText())) {
            textView5.setVisibility(8);
        } else {
            textView5.setVisibility(0);
            textView5.setText(khloagycontentitem.getArabicText());
        }
        if (!this.isArabicCoptic || !khloagycontentitem.isArabicCopticFound() || khloagycontentitem.getArabicCopticText() == null || TextUtils.isEmpty(khloagycontentitem.getArabicCopticText())) {
            textView6.setVisibility(8);
            viewGroup6.findViewById(R.id.v_divider_1).setVisibility(8);
        } else {
            textView6.setVisibility(0);
            textView6.setText(khloagycontentitem.getArabicCopticText());
            if (this.isArabic) {
                viewGroup6.findViewById(R.id.v_divider_1).setVisibility(0);
            }
        }
        if (!this.isEnglish || !khloagycontentitem.isEnglishFound() || khloagycontentitem.getEnglishText() == null || TextUtils.isEmpty(khloagycontentitem.getEnglishText())) {
            textView8.setVisibility(8);
            viewGroup6.findViewById(R.id.v_divider_3).setVisibility(8);
        } else {
            if (this.isArabic || this.isArabicCoptic || this.isCoptic) {
                viewGroup6.findViewById(R.id.v_divider_3).setVisibility(0);
            }
            textView8.setVisibility(0);
            textView8.setText(khloagycontentitem.getEnglishText());
        }
        if (!this.isCoptic || !khloagycontentitem.isCopticFound() || khloagycontentitem.getCopticText() == null || TextUtils.isEmpty(khloagycontentitem.getCopticText())) {
            textView7.setVisibility(8);
            customCopticTextView.setVisibility(8);
            viewGroup6.findViewById(R.id.v_divider_2).setVisibility(8);
        } else {
            if (this.isCustomCoptic) {
                customCopticTextView.setVisibility(0);
                String str = this.fontId;
                if (str != null && !str.equals(null) && !this.fontId.equals("") && this.fontId.contains("avva")) {
                    customCopticTextView.setTypeface(this.faceltrAvva);
                } else {
                    customCopticTextView.setTypeface(this.faceltr);
                }
                if (khloagycontentitem.getCopticText() != null) {
                    if (textView5.getVisibility() == 8 && textView6.getVisibility() == 8 && textView8.getVisibility() == 8) {
                        String strReplaceAll = khloagycontentitem.getCopticText().replaceAll(" >", ">").replaceAll(" ", "%");
                        customCopticTextView.setLetterSpacing(-0.03f);
                        customCopticTextView.setText(strReplaceAll);
                    } else {
                        String strReplaceAll2 = khloagycontentitem.getCopticText().replaceAll(" >", ">").replaceAll(" ", "%");
                        customCopticTextView.setLetterSpacing(0.02f);
                        customCopticTextView.setText(strReplaceAll2);
                    }
                }
            } else {
                textView7.setVisibility(0);
                String str2 = this.fontId;
                if (str2 != null && !str2.equals(null) && !this.fontId.equals("") && this.fontId.contains("avva")) {
                    textView7.setTypeface(this.faceltrAvva);
                } else {
                    textView7.setTypeface(this.faceltr);
                }
                if (khloagycontentitem.getCopticText() != null) {
                    textView7.setText(khloagycontentitem.getCopticText().replaceAll("    ", " "));
                }
            }
            if (this.isArabic || this.isArabicCoptic) {
                viewGroup6.findViewById(R.id.v_divider_2).setVisibility(0);
            }
        }
        textView5.setTextColor(Color.parseColor("#000000"));
        textView6.setTextColor(Color.parseColor("#008080"));
        textView7.setTextColor(Color.parseColor("#000080"));
        customCopticTextView.setTextColor(Color.parseColor("#000080"));
        textView8.setTextColor(Color.parseColor("#b23424"));
        textView5.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
        textView6.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
        textView7.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
        customCopticTextView.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
        textView8.setTextSize(Float.valueOf(checksimVar.checkfontsize(this.activity)).floatValue());
        return viewGroup6;
    }
}
