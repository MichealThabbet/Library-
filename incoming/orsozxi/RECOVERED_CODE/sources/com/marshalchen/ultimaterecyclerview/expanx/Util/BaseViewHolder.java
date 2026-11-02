package com.marshalchen.ultimaterecyclerview.expanx.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.marshalchen.ultimaterecyclerview.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

/* loaded from: classes2.dex */
public class BaseViewHolder<T extends ExpandableItemData> extends UltimateRecyclerviewViewHolder {
    protected int itemMargin;
    protected int itemMarginRes;
    protected int offsetMargin;
    protected int offsetMarginRes;

    public BaseViewHolder(View view) {
        super(view);
        getMarginBy();
    }

    protected View getView(Context context, ViewGroup viewGroup, int i) {
        return LayoutInflater.from(context).inflate(i, viewGroup, false);
    }

    protected void getMarginBy() {
        this.itemMargin = this.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.item_margin);
        this.offsetMargin = this.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.expand_size);
    }

    protected RelativeLayout.LayoutParams getParamsLayoutOffset(ImageView imageView, T t) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.leftMargin = (this.itemMargin * t.getTreeDepth()) + this.offsetMargin;
        return layoutParams;
    }

    protected RelativeLayout.LayoutParams getParamsLayoutOffset(RelativeLayout relativeLayout, ExpandableItemData expandableItemData) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.leftMargin = (this.itemMargin * expandableItemData.getTreeDepth()) + this.offsetMargin;
        return layoutParams;
    }

    protected RelativeLayout.LayoutParams getParamsLayoutOffset(TextView textView, T t) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.leftMargin = (this.itemMargin * t.getTreeDepth()) + this.offsetMargin;
        return layoutParams;
    }

    protected RelativeLayout.LayoutParams getParamsLayout(TextView textView, T t) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.leftMargin = this.itemMargin * t.getTreeDepth();
        return layoutParams;
    }

    protected RelativeLayout.LayoutParams getParamsLayout(ImageView imageView, T t) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.leftMargin = this.itemMargin * t.getTreeDepth();
        return layoutParams;
    }

    protected RelativeLayout.LayoutParams getParamsLayout(RelativeLayout relativeLayout, T t) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.leftMargin = this.itemMargin * t.getTreeDepth();
        return layoutParams;
    }
}
