package com.marshalchen.ultimaterecyclerview.expanx.Util;

import android.animation.ValueAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.marshalchen.ultimaterecyclerview.R;
import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

/* loaded from: classes2.dex */
public abstract class easyTemplateParent<T extends ExpandableItemData, H extends RelativeLayout, B extends TextView> extends parent<T> {
    public RelativeLayout adjustmentlayout;
    private boolean capitalized;
    public B count;
    private boolean countenabled;
    public ImageView expand;
    public ImageView image;
    private T item;
    public H relativeLayout;
    public B text;

    @Override // com.marshalchen.ultimaterecyclerview.expanx.Util.ParentVH
    public int closeDegree() {
        return 0;
    }

    @Override // com.marshalchen.ultimaterecyclerview.expanx.Util.ParentVH
    public void onParentItemClick(String str) {
    }

    @Override // com.marshalchen.ultimaterecyclerview.expanx.Util.ParentVH
    public int openDegree() {
        return 90;
    }

    public easyTemplateParent(View view) {
        super(view);
        this.capitalized = false;
        this.countenabled = true;
        this.text = (B) view.findViewById(R.id.exp_section_title);
        this.expand = (ImageView) view.findViewById(R.id.exp_indication_arrow);
        this.count = (B) view.findViewById(R.id.exp_section_notification_number);
        this.relativeLayout = (H) view.findViewById(R.id.exp_section_ripple_wrapper_click);
        this.adjustmentlayout = (RelativeLayout) view.findViewById(R.id.exp_section_adjustment_layout);
        this.itemMargin = view.getContext().getResources().getDimensionPixelSize(R.dimen.item_margin);
    }

    protected void forceTitleCapitalized(boolean z) {
        this.capitalized = z;
    }

    protected void setNotifcationFieldEnabled(boolean z) {
        this.countenabled = z;
        if (!z) {
            this.count.setVisibility(8);
        } else {
            if (getItem() == null || !getItem().isExpand()) {
                return;
            }
            this.count.setVisibility(0);
        }
    }

    protected T getItem() {
        return this.item;
    }

    @Override // com.marshalchen.ultimaterecyclerview.expanx.Util.parent
    protected void setCountVisible(int i) {
        if (this.countenabled) {
            this.count.setVisibility(i);
        }
    }

    @Override // com.marshalchen.ultimaterecyclerview.expanx.Util.parent
    protected void updateCountNumber(String str) {
        if (this.countenabled) {
            this.count.setText(str);
        }
    }

    @Override // com.marshalchen.ultimaterecyclerview.expanx.Util.ParentVH
    public void bindView(T t, int i, ItemDataClickListener itemDataClickListener) {
        RelativeLayout relativeLayout = this.adjustmentlayout;
        relativeLayout.setLayoutParams(getParamsLayout(relativeLayout, (RelativeLayout) t));
        if (this.capitalized) {
            this.text.setText(t.getText().toUpperCase());
        } else {
            this.text.setText(t.getText());
        }
        setHandleInitiatedViewStatus(t, this.expand, this.count);
        setRelativeLayoutClickable(this.relativeLayout, t, itemDataClickListener, i);
        this.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.marshalchen.ultimaterecyclerview.expanx.Util.easyTemplateParent.1
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(), "longclick", 0).show();
                return false;
            }
        });
        this.item = t;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.expand.setRotation(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }
}
