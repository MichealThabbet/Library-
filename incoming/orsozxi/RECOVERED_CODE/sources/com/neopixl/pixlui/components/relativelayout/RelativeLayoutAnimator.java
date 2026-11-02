package com.neopixl.pixlui.components.relativelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;

/* loaded from: classes2.dex */
public class RelativeLayoutAnimator extends RelativeLayout {
    private Animation inAnimation;
    private Animation outAnimation;

    public RelativeLayoutAnimator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public RelativeLayoutAnimator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setInAnimation(Animation animation) {
        this.inAnimation = animation;
    }

    public void setOutAnimation(Animation animation) {
        this.outAnimation = animation;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        Animation animation;
        if (getVisibility() != i) {
            if (i == 0) {
                Animation animation2 = this.inAnimation;
                if (animation2 != null) {
                    startAnimation(animation2);
                }
            } else if ((i == 4 || i == 8) && (animation = this.outAnimation) != null) {
                startAnimation(animation);
            }
        }
        super.setVisibility(i);
    }
}
