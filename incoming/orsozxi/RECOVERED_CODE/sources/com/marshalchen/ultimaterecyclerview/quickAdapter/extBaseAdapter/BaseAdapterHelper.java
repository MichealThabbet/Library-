package com.marshalchen.ultimaterecyclerview.quickAdapter.extBaseAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;

/* loaded from: classes2.dex */
public class BaseAdapterHelper {
    Object associatedObject;
    private final Context context;
    private View convertView;
    public int layoutId;
    private int position;
    private final SparseArray<View> views = new SparseArray<>();

    protected BaseAdapterHelper(Context context, ViewGroup viewGroup, int i, int i2) {
        this.context = context;
        this.position = i2;
        this.layoutId = i;
        View viewInflate = LayoutInflater.from(context).inflate(i, viewGroup, false);
        this.convertView = viewInflate;
        viewInflate.setTag(this);
    }

    public static BaseAdapterHelper get(Context context, View view, ViewGroup viewGroup, int i) {
        return get(context, view, viewGroup, i, -1);
    }

    static BaseAdapterHelper get(Context context, View view, ViewGroup viewGroup, int i, int i2) {
        if (view == null) {
            return new BaseAdapterHelper(context, viewGroup, i, i2);
        }
        BaseAdapterHelper baseAdapterHelper = (BaseAdapterHelper) view.getTag();
        if (baseAdapterHelper.layoutId != i) {
            return new BaseAdapterHelper(context, viewGroup, i, i2);
        }
        baseAdapterHelper.position = i2;
        return baseAdapterHelper;
    }

    public <T extends View> T getView(int i) {
        return (T) retrieveView(i);
    }

    protected <T extends View> T retrieveView(int i) {
        T t = (T) this.views.get(i);
        if (t != null) {
            return t;
        }
        T t2 = (T) this.convertView.findViewById(i);
        this.views.put(i, t2);
        return t2;
    }

    public BaseAdapterHelper setText(int i, String str) {
        ((TextView) retrieveView(i)).setText(str);
        return this;
    }

    public BaseAdapterHelper setImageResource(int i, int i2) {
        ((ImageView) retrieveView(i)).setImageResource(i2);
        return this;
    }

    public BaseAdapterHelper setBackgroundColor(int i, int i2) {
        retrieveView(i).setBackgroundColor(i2);
        return this;
    }

    public BaseAdapterHelper setBackgroundRes(int i, int i2) {
        retrieveView(i).setBackgroundResource(i2);
        return this;
    }

    public BaseAdapterHelper setTextColor(int i, int i2) {
        ((TextView) retrieveView(i)).setTextColor(i2);
        return this;
    }

    public BaseAdapterHelper setTextColorRes(int i, int i2) {
        ((TextView) retrieveView(i)).setTextColor(this.context.getResources().getColor(i2));
        return this;
    }

    public BaseAdapterHelper setImageDrawable(int i, Drawable drawable) {
        ((ImageView) retrieveView(i)).setImageDrawable(drawable);
        return this;
    }

    public BaseAdapterHelper setImageUrl(int i, String str) {
        Glide.with(this.context).load(str).into((ImageView) retrieveView(i));
        return this;
    }

    public BaseAdapterHelper setImageBitmap(int i, Bitmap bitmap) {
        ((ImageView) retrieveView(i)).setImageBitmap(bitmap);
        return this;
    }

    public BaseAdapterHelper setAlpha(int i, float f) {
        if (Build.VERSION.SDK_INT >= 11) {
            retrieveView(i).setAlpha(f);
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(f, f);
            alphaAnimation.setDuration(0L);
            alphaAnimation.setFillAfter(true);
            retrieveView(i).startAnimation(alphaAnimation);
        }
        return this;
    }

    public BaseAdapterHelper setVisible(int i, boolean z) {
        retrieveView(i).setVisibility(z ? 0 : 8);
        return this;
    }

    public BaseAdapterHelper linkify(int i) {
        Linkify.addLinks((TextView) retrieveView(i), 15);
        return this;
    }

    public BaseAdapterHelper setTypeface(int i, Typeface typeface) {
        TextView textView = (TextView) retrieveView(i);
        textView.setTypeface(typeface);
        textView.setPaintFlags(textView.getPaintFlags() | 128);
        return this;
    }

    public BaseAdapterHelper setTypeface(Typeface typeface, int... iArr) {
        for (int i : iArr) {
            TextView textView = (TextView) retrieveView(i);
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | 128);
        }
        return this;
    }

    public BaseAdapterHelper setProgress(int i, int i2) {
        ((ProgressBar) retrieveView(i)).setProgress(i2);
        return this;
    }

    public BaseAdapterHelper setProgress(int i, int i2, int i3) {
        ProgressBar progressBar = (ProgressBar) retrieveView(i);
        progressBar.setMax(i3);
        progressBar.setProgress(i2);
        return this;
    }

    public BaseAdapterHelper setMax(int i, int i2) {
        ((ProgressBar) retrieveView(i)).setMax(i2);
        return this;
    }

    public BaseAdapterHelper setRating(int i, float f) {
        ((RatingBar) retrieveView(i)).setRating(f);
        return this;
    }

    public BaseAdapterHelper setRating(int i, float f, int i2) {
        RatingBar ratingBar = (RatingBar) retrieveView(i);
        ratingBar.setMax(i2);
        ratingBar.setRating(f);
        return this;
    }

    public BaseAdapterHelper setTag(int i, Object obj) {
        retrieveView(i).setTag(obj);
        return this;
    }

    public BaseAdapterHelper setTag(int i, int i2, Object obj) {
        retrieveView(i).setTag(i2, obj);
        return this;
    }

    public BaseAdapterHelper setChecked(int i, boolean z) {
        ((Checkable) retrieveView(i)).setChecked(z);
        return this;
    }

    public BaseAdapterHelper setAdapter(int i, Adapter adapter) {
        ((AdapterView) retrieveView(i)).setAdapter(adapter);
        return this;
    }

    public BaseAdapterHelper setOnClickListener(int i, View.OnClickListener onClickListener) {
        retrieveView(i).setOnClickListener(onClickListener);
        return this;
    }

    public BaseAdapterHelper setOnTouchListener(int i, View.OnTouchListener onTouchListener) {
        retrieveView(i).setOnTouchListener(onTouchListener);
        return this;
    }

    public BaseAdapterHelper setOnLongClickListener(int i, View.OnLongClickListener onLongClickListener) {
        retrieveView(i).setOnLongClickListener(onLongClickListener);
        return this;
    }

    public View getView() {
        return this.convertView;
    }

    public int getPosition() {
        int i = this.position;
        if (i != -1) {
            return i;
        }
        throw new IllegalStateException("Use BaseAdapterHelper constructor with position if you need to retrieve the position.");
    }

    public Object getAssociatedObject() {
        return this.associatedObject;
    }

    public void setAssociatedObject(Object obj) {
        this.associatedObject = obj;
    }
}
