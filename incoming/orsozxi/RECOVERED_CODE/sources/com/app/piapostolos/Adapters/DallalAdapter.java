package com.app.piapostolos.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.app.piapostolos.Beans.AlamSettings;
import com.app.piapostolos.Beans.Items;
import com.app.piapostolos.Others.SavePrefs;
import com.app.piapostolos.R;
import com.app.piapostolos.Views.CustomScrollView;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class DallalAdapter extends PagerAdapter {
    private Activity activity;
    private Items item;
    private ArrayList<Items> items;

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object obj) {
        return -2;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public float getPageWidth(int i) {
        return 1.0f;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Parcelable saveState() {
        return null;
    }

    public DallalAdapter(Activity activity, ArrayList<Items> arrayList) {
        this.items = arrayList;
        this.activity = activity;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.items.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        TextView textView;
        View viewInflate = LayoutInflater.from(this.activity).inflate(R.layout.news_item, viewGroup, false);
        this.item = this.items.get(i);
        Typeface typefaceCreateFromAsset = Typeface.createFromAsset(this.activity.getAssets(), "Avva_Shenouda.ttf");
        RelativeLayout relativeLayout = (RelativeLayout) viewInflate.findViewById(R.id.relative);
        CustomScrollView customScrollView = new CustomScrollView(this.activity);
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        linearLayout.setOrientation(1);
        customScrollView.addView(linearLayout);
        AlamSettings alamSettings = (AlamSettings) new SavePrefs(this.activity, (Class<?>) AlamSettings.class).load();
        TextView textView2 = (TextView) this.activity.getLayoutInflater().inflate(R.layout.arabic_text_view, (ViewGroup) null);
        textView2.setText(this.item.getTitle());
        if (alamSettings != null && alamSettings.getAraSize() != 0.0f) {
            textView2.setTextSize(alamSettings.getAraSize());
        }
        textView2.setTextColor(SupportMenu.CATEGORY_MASK);
        textView2.setGravity(1);
        linearLayout.addView(textView2);
        for (int i2 = 0; i2 < this.item.getItem_details().size(); i2++) {
            if (this.item.getItem_details().get(i2).isIsarabic()) {
                textView = (TextView) this.activity.getLayoutInflater().inflate(R.layout.arabic_text_view, (ViewGroup) null);
                textView.setText(this.item.getItem_details().get(i2).getContent());
                if (alamSettings != null) {
                    if (alamSettings.getAraColor() != null) {
                        textView.setTextColor(Color.parseColor(alamSettings.getAraColor()));
                    }
                    if (alamSettings.getAraSize() != 0.0f) {
                        textView.setTextSize(alamSettings.getAraSize());
                    }
                }
            } else {
                textView = (TextView) this.activity.getLayoutInflater().inflate(R.layout.coptic_text_view, (ViewGroup) null);
                textView.setText(this.item.getItem_details().get(i2).getContent());
                textView.setTypeface(typefaceCreateFromAsset);
                if (alamSettings != null) {
                    if (alamSettings.getCopColor() != null) {
                        textView.setTextColor(Color.parseColor(alamSettings.getCopColor()));
                    }
                    if (alamSettings.getCopSize() != 0.0f) {
                        textView.setTextSize(alamSettings.getCopSize());
                    }
                }
            }
            linearLayout.addView(textView);
        }
        relativeLayout.addView(customScrollView);
        ((ViewPager) viewGroup).addView(viewInflate, 0);
        return viewInflate;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(View view, int i, Object obj) {
        ((ViewPager) view).removeView((View) obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == ((View) obj);
    }
}
