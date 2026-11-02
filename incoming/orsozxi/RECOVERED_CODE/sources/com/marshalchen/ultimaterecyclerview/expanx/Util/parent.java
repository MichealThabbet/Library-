package com.marshalchen.ultimaterecyclerview.expanx.Util;

import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class parent<T extends ExpandableItemData> extends BaseViewHolder<T> implements ParentVH<T>, ValueAnimator.AnimatorUpdateListener {
    protected abstract void setCountVisible(int i);

    protected abstract void updateCountNumber(String str);

    public parent(View view) {
        super(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleExpenable(ExpandableItemData expandableItemData, ItemDataClickListener itemDataClickListener, int i) {
        if (itemDataClickListener != null) {
            if (expandableItemData.isExpand()) {
                itemDataClickListener.onHideChildren(expandableItemData);
                expandableItemData.setExpand(false);
                rotationExpandIcon(openDegree(), closeDegree());
                setCountVisible(8);
                return;
            }
            itemDataClickListener.onExpandChildren(expandableItemData);
            expandableItemData.setExpand(true);
            rotationExpandIcon(closeDegree(), openDegree());
            List<T> children = expandableItemData.getChildren();
            if (children != null) {
                updateCountNumber(String.format("(%s)", Integer.valueOf(children.size())));
            }
            onParentItemClick(expandableItemData.toString());
            setCountVisible(0);
        }
    }

    @Override // com.marshalchen.ultimaterecyclerview.expanx.Util.ParentVH
    public void rotationExpandIcon(float f, float f2) {
        if (Build.VERSION.SDK_INT >= 11) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
            valueAnimatorOfFloat.setDuration(150L);
            valueAnimatorOfFloat.setInterpolator(new LinearOutSlowInInterpolator());
            valueAnimatorOfFloat.addUpdateListener(this);
            valueAnimatorOfFloat.start();
        }
    }

    protected void setHandleInitiatedViewStatus(ExpandableItemData expandableItemData, View view, TextView textView) {
        if (expandableItemData.isExpand()) {
            view.setRotation(openDegree());
            if (expandableItemData.getChildren() != null) {
                textView.setText(String.format("(%s)", Integer.valueOf(expandableItemData.getChildren().size())));
            }
            textView.setVisibility(0);
            return;
        }
        view.setRotation(closeDegree());
        textView.setVisibility(8);
        textView.setText("");
    }

    protected <V extends View> void setRelativeLayoutClickable(V v, final ExpandableItemData expandableItemData, final ItemDataClickListener itemDataClickListener, final int i) {
        v.setOnClickListener(new View.OnClickListener() { // from class: com.marshalchen.ultimaterecyclerview.expanx.Util.parent.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                parent.this.toggleExpenable(expandableItemData, itemDataClickListener, i);
            }
        });
    }
}
