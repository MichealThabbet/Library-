package com.appeaser.sublimepickerlibrary.recurrencepicker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ToggleButton;
import com.appeaser.sublimepickerlibrary.drawables.CheckableDrawable;

/* loaded from: classes.dex */
public class WeekButton extends ToggleButton {
    private static int mCheckedTextColor;
    private static int mDefaultTextColor;
    private CheckableDrawable.OnAnimationDone mCallback;
    private CheckableDrawable mDrawable;
    private boolean noAnimate;

    public WeekButton(Context context) {
        super(context);
        this.noAnimate = false;
        this.mCallback = new CheckableDrawable.OnAnimationDone() { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.WeekButton.1
            @Override // com.appeaser.sublimepickerlibrary.drawables.CheckableDrawable.OnAnimationDone
            public void animationIsDone() {
                WeekButton weekButton = WeekButton.this;
                weekButton.setTextColor(weekButton.isChecked() ? WeekButton.mCheckedTextColor : WeekButton.mDefaultTextColor);
                WeekButton.this.mDrawable.setChecked(WeekButton.this.isChecked());
            }

            @Override // com.appeaser.sublimepickerlibrary.drawables.CheckableDrawable.OnAnimationDone
            public void animationHasBeenCancelled() {
                WeekButton weekButton = WeekButton.this;
                weekButton.setTextColor(weekButton.isChecked() ? WeekButton.mCheckedTextColor : WeekButton.mDefaultTextColor);
                WeekButton.this.mDrawable.setChecked(WeekButton.this.isChecked());
            }
        };
    }

    public WeekButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.noAnimate = false;
        this.mCallback = new CheckableDrawable.OnAnimationDone() { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.WeekButton.1
            @Override // com.appeaser.sublimepickerlibrary.drawables.CheckableDrawable.OnAnimationDone
            public void animationIsDone() {
                WeekButton weekButton = WeekButton.this;
                weekButton.setTextColor(weekButton.isChecked() ? WeekButton.mCheckedTextColor : WeekButton.mDefaultTextColor);
                WeekButton.this.mDrawable.setChecked(WeekButton.this.isChecked());
            }

            @Override // com.appeaser.sublimepickerlibrary.drawables.CheckableDrawable.OnAnimationDone
            public void animationHasBeenCancelled() {
                WeekButton weekButton = WeekButton.this;
                weekButton.setTextColor(weekButton.isChecked() ? WeekButton.mCheckedTextColor : WeekButton.mDefaultTextColor);
                WeekButton.this.mDrawable.setChecked(WeekButton.this.isChecked());
            }
        };
    }

    public WeekButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.noAnimate = false;
        this.mCallback = new CheckableDrawable.OnAnimationDone() { // from class: com.appeaser.sublimepickerlibrary.recurrencepicker.WeekButton.1
            @Override // com.appeaser.sublimepickerlibrary.drawables.CheckableDrawable.OnAnimationDone
            public void animationIsDone() {
                WeekButton weekButton = WeekButton.this;
                weekButton.setTextColor(weekButton.isChecked() ? WeekButton.mCheckedTextColor : WeekButton.mDefaultTextColor);
                WeekButton.this.mDrawable.setChecked(WeekButton.this.isChecked());
            }

            @Override // com.appeaser.sublimepickerlibrary.drawables.CheckableDrawable.OnAnimationDone
            public void animationHasBeenCancelled() {
                WeekButton weekButton = WeekButton.this;
                weekButton.setTextColor(weekButton.isChecked() ? WeekButton.mCheckedTextColor : WeekButton.mDefaultTextColor);
                WeekButton.this.mDrawable.setChecked(WeekButton.this.isChecked());
            }
        };
    }

    public void setCheckedNoAnimate(boolean z) {
        this.noAnimate = true;
        setChecked(z);
        this.noAnimate = false;
    }

    @Override // android.widget.ToggleButton, android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z) {
        super.setChecked(z);
        CheckableDrawable checkableDrawable = this.mDrawable;
        if (checkableDrawable != null) {
            if (this.noAnimate) {
                checkableDrawable.setChecked(z);
                setTextColor(isChecked() ? mCheckedTextColor : mDefaultTextColor);
            } else {
                setTextColor(mCheckedTextColor);
                this.mDrawable.setCheckedOnClick(isChecked(), this.mCallback);
            }
        }
    }

    @Override // android.widget.ToggleButton, android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (drawable instanceof CheckableDrawable) {
            this.mDrawable = (CheckableDrawable) drawable;
        } else {
            this.mDrawable = null;
        }
    }

    public static void setStateColors(int i, int i2) {
        mDefaultTextColor = i;
        mCheckedTextColor = i2;
    }
}
