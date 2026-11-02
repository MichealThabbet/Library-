package com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton;

import android.R;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import com.ibm.icu.text.DateFormat;
import com.nineoldandroids.view.ViewHelper;
import org.apache.poi.ss.util.CellUtil;

/* loaded from: classes2.dex */
public class FloatingActionsMenu extends ViewGroup {
    private static final int ANIMATION_DURATION = 300;
    private static final float COLLAPSED_PLUS_ROTATION = 0.0f;
    private static final float EXPANDED_PLUS_ROTATION = 135.0f;
    private boolean isHorizontal;
    private AddFloatingActionButton mAddButton;
    private int mAddButtonColorNormal;
    private int mAddButtonColorPressed;
    private int mAddButtonPlusColor;
    private int mButtonSpacing;
    private AnimatorSet mCollapseAnimation;
    private AnimatorSet mExpandAnimation;
    private boolean mExpanded;
    private boolean mHidden;
    private final Interpolator mInterpolator;
    private RotatingDrawable mRotatingDrawable;
    private float mYDisplayed;
    private float mYHidden;
    private static Interpolator sExpandInterpolator = new OvershootInterpolator();
    private static Interpolator sCollapseInterpolator = new DecelerateInterpolator(3.0f);
    private static Interpolator sAlphaExpandInterpolator = new DecelerateInterpolator();

    public FloatingActionsMenu(Context context) {
        this(context, null);
    }

    public FloatingActionsMenu(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isHorizontal = false;
        this.mExpandAnimation = new AnimatorSet().setDuration(300L);
        this.mCollapseAnimation = new AnimatorSet().setDuration(300L);
        this.mInterpolator = new AccelerateDecelerateInterpolator();
        this.mHidden = false;
        this.mYDisplayed = -1.0f;
        this.mYHidden = -1.0f;
        init(context, attributeSet);
    }

    public FloatingActionsMenu(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isHorizontal = false;
        this.mExpandAnimation = new AnimatorSet().setDuration(300L);
        this.mCollapseAnimation = new AnimatorSet().setDuration(300L);
        this.mInterpolator = new AccelerateDecelerateInterpolator();
        this.mHidden = false;
        this.mYDisplayed = -1.0f;
        this.mYHidden = -1.0f;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes;
        this.mAddButtonPlusColor = getColor(R.color.white);
        this.mAddButtonColorNormal = getColor(R.color.holo_blue_dark);
        this.mAddButtonColorPressed = getColor(R.color.holo_blue_light);
        this.mButtonSpacing = (int) ((getResources().getDimension(com.marshalchen.ultimaterecyclerview.R.dimen.fab_actions_spacing) - getResources().getDimension(com.marshalchen.ultimaterecyclerview.R.dimen.fab_shadow_radius)) - getResources().getDimension(com.marshalchen.ultimaterecyclerview.R.dimen.fab_shadow_offset));
        if (attributeSet != null && (typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.marshalchen.ultimaterecyclerview.R.styleable.FloatingActionsMenu, 0, 0)) != null) {
            try {
                this.mAddButtonPlusColor = typedArrayObtainStyledAttributes.getColor(com.marshalchen.ultimaterecyclerview.R.styleable.FloatingActionsMenu_addButtonPlusIconColor, getColor(R.color.white));
                this.mAddButtonColorNormal = typedArrayObtainStyledAttributes.getColor(com.marshalchen.ultimaterecyclerview.R.styleable.FloatingActionsMenu_addButtonColorNormal, getColor(R.color.holo_blue_dark));
                this.mAddButtonColorPressed = typedArrayObtainStyledAttributes.getColor(com.marshalchen.ultimaterecyclerview.R.styleable.FloatingActionsMenu_addButtonColorPressed, getColor(R.color.holo_blue_light));
                this.isHorizontal = typedArrayObtainStyledAttributes.getBoolean(com.marshalchen.ultimaterecyclerview.R.styleable.FloatingActionsMenu_addButtonIsHorizontal, false);
            } finally {
                typedArrayObtainStyledAttributes.recycle();
            }
        }
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 13) {
            defaultDisplay.getSize(point);
            this.mYHidden = point.y;
        } else {
            this.mYHidden = defaultDisplay.getHeight();
        }
        createAddButton(context);
    }

    private static class RotatingDrawable extends LayerDrawable {
        private float mRotation;

        public RotatingDrawable(Drawable drawable) {
            super(new Drawable[]{drawable});
        }

        public float getRotation() {
            return this.mRotation;
        }

        public void setRotation(float f) {
            this.mRotation = f;
            invalidateSelf();
        }

        @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.rotate(this.mRotation, getBounds().centerX(), getBounds().centerY());
            super.draw(canvas);
            canvas.restore();
        }
    }

    private void createAddButton(Context context) {
        AddFloatingActionButton addFloatingActionButton = new AddFloatingActionButton(context) { // from class: com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionsMenu.1
            @Override // com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton
            protected void updateBackground() {
                this.mPlusColor = FloatingActionsMenu.this.mAddButtonPlusColor;
                this.mColorNormal = FloatingActionsMenu.this.mAddButtonColorNormal;
                this.mColorPressed = FloatingActionsMenu.this.mAddButtonColorPressed;
                super.updateBackground();
            }

            @Override // com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.AddFloatingActionButton, com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton
            protected Drawable getIconDrawable() {
                RotatingDrawable rotatingDrawable = new RotatingDrawable(super.getIconDrawable());
                FloatingActionsMenu.this.mRotatingDrawable = rotatingDrawable;
                OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(rotatingDrawable, CellUtil.ROTATION, FloatingActionsMenu.EXPANDED_PLUS_ROTATION, 0.0f);
                ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(rotatingDrawable, CellUtil.ROTATION, 0.0f, FloatingActionsMenu.EXPANDED_PLUS_ROTATION);
                objectAnimatorOfFloat.setInterpolator(overshootInterpolator);
                objectAnimatorOfFloat2.setInterpolator(overshootInterpolator);
                FloatingActionsMenu.this.mExpandAnimation.play(objectAnimatorOfFloat2);
                FloatingActionsMenu.this.mCollapseAnimation.play(objectAnimatorOfFloat);
                return rotatingDrawable;
            }
        };
        this.mAddButton = addFloatingActionButton;
        addFloatingActionButton.setId(com.marshalchen.ultimaterecyclerview.R.id.fab_expand_menu_button);
        this.mAddButton.setOnClickListener(new View.OnClickListener() { // from class: com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionsMenu.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FloatingActionsMenu.this.toggle();
            }
        });
        addView(this.mAddButton, super.generateDefaultLayoutParams());
    }

    private int getColor(int i) {
        return getResources().getColor(i);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        measureChildren(i, i2);
        int i3 = 0;
        if (!this.isHorizontal) {
            int measuredHeight = 0;
            int iMax = 0;
            while (i3 < getChildCount()) {
                View childAt = getChildAt(i3);
                iMax = Math.max(iMax, childAt.getMeasuredWidth());
                measuredHeight += childAt.getMeasuredHeight();
                i3++;
            }
            setMeasuredDimension(iMax, ((measuredHeight + (this.mButtonSpacing * (getChildCount() - 1))) * 12) / 10);
            return;
        }
        int measuredWidth = 0;
        int iMax2 = 0;
        while (i3 < getChildCount()) {
            View childAt2 = getChildAt(i3);
            iMax2 = Math.max(iMax2, childAt2.getMeasuredHeight());
            measuredWidth += childAt2.getMeasuredWidth();
            i3++;
        }
        setMeasuredDimension(((measuredWidth + (this.mButtonSpacing * (getChildCount() - 1))) * 12) / 10, iMax2);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredHeight = (i4 - i2) - this.mAddButton.getMeasuredHeight();
        int measuredWidth = (i3 - i) - this.mAddButton.getMeasuredWidth();
        if (!this.isHorizontal) {
            AddFloatingActionButton addFloatingActionButton = this.mAddButton;
            addFloatingActionButton.layout(0, measuredHeight, addFloatingActionButton.getMeasuredWidth(), this.mAddButton.getMeasuredHeight() + measuredHeight);
        } else {
            AddFloatingActionButton addFloatingActionButton2 = this.mAddButton;
            addFloatingActionButton2.layout(measuredWidth, 0, addFloatingActionButton2.getMeasuredWidth() + measuredWidth, this.mAddButton.getMeasuredHeight());
        }
        int i5 = this.mButtonSpacing;
        int i6 = measuredHeight - i5;
        int i7 = measuredWidth - i5;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt != this.mAddButton) {
                int measuredHeight2 = i6 - childAt.getMeasuredHeight();
                int measuredWidth2 = i7 - childAt.getMeasuredWidth();
                if (!this.isHorizontal) {
                    childAt.layout(0, measuredHeight2, childAt.getMeasuredWidth(), childAt.getMeasuredHeight() + measuredHeight2);
                } else {
                    childAt.layout(measuredWidth2, 0, childAt.getMeasuredWidth() + measuredWidth2, childAt.getMeasuredHeight());
                }
                if (!this.isHorizontal) {
                    float f = measuredHeight - measuredHeight2;
                    childAt.setTranslationY(this.mExpanded ? 0.0f : f);
                    childAt.setAlpha(this.mExpanded ? 1.0f : 0.0f);
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    layoutParams.mCollapseY.setFloatValues(0.0f, f);
                    layoutParams.mExpandY.setFloatValues(f, 0.0f);
                    layoutParams.setAnimationsTarget(childAt);
                } else {
                    float f2 = measuredWidth - measuredWidth2;
                    childAt.setTranslationX(this.mExpanded ? f2 : 0.0f);
                    childAt.setAlpha(this.mExpanded ? 1.0f : 0.0f);
                    LayoutParams layoutParams2 = (LayoutParams) childAt.getLayoutParams();
                    layoutParams2.mCollapseX.setFloatValues(0.0f, f2);
                    layoutParams2.mExpandX.setFloatValues(f2, 0.0f);
                    layoutParams2.setAnimationsTarget(childAt);
                }
                int i8 = this.mButtonSpacing;
                i6 = measuredHeight2 - i8;
                i7 = measuredWidth2 - i8;
            }
        }
        if (this.mYDisplayed == -1.0f) {
            this.mYDisplayed = ViewHelper.getY(this);
        }
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(super.generateDefaultLayoutParams());
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(super.generateLayoutParams(attributeSet));
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(super.generateLayoutParams(layoutParams));
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams);
    }

    private class LayoutParams extends ViewGroup.LayoutParams {
        private ObjectAnimator mCollapseAlpha;
        private ObjectAnimator mCollapseX;
        private ObjectAnimator mCollapseY;
        private ObjectAnimator mExpandAlpha;
        private ObjectAnimator mExpandX;
        private ObjectAnimator mExpandY;

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mExpandY = new ObjectAnimator();
            this.mExpandX = new ObjectAnimator();
            this.mExpandAlpha = new ObjectAnimator();
            this.mCollapseY = new ObjectAnimator();
            this.mCollapseX = new ObjectAnimator();
            this.mCollapseAlpha = new ObjectAnimator();
            this.mExpandY.setInterpolator(FloatingActionsMenu.sExpandInterpolator);
            this.mExpandX.setInterpolator(FloatingActionsMenu.sExpandInterpolator);
            this.mExpandAlpha.setInterpolator(FloatingActionsMenu.sAlphaExpandInterpolator);
            this.mCollapseY.setInterpolator(FloatingActionsMenu.sCollapseInterpolator);
            this.mCollapseX.setInterpolator(FloatingActionsMenu.sCollapseInterpolator);
            this.mCollapseAlpha.setInterpolator(FloatingActionsMenu.sCollapseInterpolator);
            this.mCollapseAlpha.setProperty(View.ALPHA);
            this.mCollapseAlpha.setFloatValues(1.0f, 0.0f);
            this.mExpandAlpha.setProperty(View.ALPHA);
            this.mExpandAlpha.setFloatValues(0.0f, 1.0f);
            this.mCollapseY.setProperty(View.TRANSLATION_Y);
            this.mCollapseX.setProperty(View.TRANSLATION_X);
            this.mExpandY.setProperty(View.TRANSLATION_Y);
            this.mExpandX.setProperty(View.TRANSLATION_X);
            FloatingActionsMenu.this.mExpandAnimation.play(this.mExpandAlpha);
            if (!FloatingActionsMenu.this.isHorizontal) {
                FloatingActionsMenu.this.mExpandAnimation.play(this.mExpandY);
            } else {
                FloatingActionsMenu.this.mExpandAnimation.play(this.mExpandX);
            }
            FloatingActionsMenu.this.mCollapseAnimation.play(this.mCollapseAlpha);
            if (!FloatingActionsMenu.this.isHorizontal) {
                FloatingActionsMenu.this.mCollapseAnimation.play(this.mCollapseY);
            } else {
                FloatingActionsMenu.this.mCollapseAnimation.play(this.mCollapseX);
            }
        }

        public void setAnimationsTarget(View view) {
            this.mCollapseAlpha.setTarget(view);
            this.mCollapseY.setTarget(view);
            this.mCollapseX.setTarget(view);
            this.mExpandAlpha.setTarget(view);
            this.mExpandY.setTarget(view);
            this.mExpandX.setTarget(view);
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        bringChildToFront(this.mAddButton);
    }

    public void collapse() {
        if (this.mExpanded) {
            this.mExpanded = false;
            this.mCollapseAnimation.start();
            this.mExpandAnimation.cancel();
        }
    }

    public void toggle() {
        if (this.mExpanded) {
            collapse();
        } else {
            expand();
        }
    }

    public void expand() {
        if (this.mExpanded) {
            return;
        }
        this.mExpanded = true;
        this.mCollapseAnimation.cancel();
        this.mExpandAnimation.start();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mExpanded = this.mExpanded;
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            boolean z = savedState.mExpanded;
            this.mExpanded = z;
            RotatingDrawable rotatingDrawable = this.mRotatingDrawable;
            if (rotatingDrawable != null) {
                rotatingDrawable.setRotation(z ? EXPANDED_PLUS_ROTATION : 0.0f);
            }
            super.onRestoreInstanceState(savedState.getSuperState());
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionsMenu.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean mExpanded;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mExpanded = parcel.readInt() == 1;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mExpanded ? 1 : 0);
        }
    }

    public void hide(boolean z) {
        if (this.mHidden != z) {
            this.mHidden = z;
            float[] fArr = new float[1];
            fArr[0] = z ? this.mYHidden : this.mYDisplayed;
            com.nineoldandroids.animation.ObjectAnimator duration = com.nineoldandroids.animation.ObjectAnimator.ofFloat(this, DateFormat.YEAR, fArr).setDuration(500L);
            duration.setInterpolator(this.mInterpolator);
            duration.start();
        }
    }
}
