package com.getbase.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import org.apache.poi.ss.util.CellUtil;

/* loaded from: classes.dex */
public class FloatingActionsMenu extends ViewGroup {
    private static final int ANIMATION_DURATION = 300;
    private static final float COLLAPSED_PLUS_ROTATION = 0.0f;
    private static final float EXPANDED_PLUS_ROTATION = 135.0f;
    public static final int EXPAND_DOWN = 1;
    public static final int EXPAND_LEFT = 2;
    public static final int EXPAND_RIGHT = 3;
    public static final int EXPAND_UP = 0;
    public static final int LABELS_ON_LEFT_SIDE = 0;
    public static final int LABELS_ON_RIGHT_SIDE = 1;
    private AddFloatingActionButton mAddButton;
    private int mAddButtonColorNormal;
    private int mAddButtonColorPressed;
    private int mAddButtonPlusColor;
    private int mAddButtonSize;
    private boolean mAddButtonStrokeVisible;
    private int mButtonSpacing;
    private int mButtonsCount;
    private AnimatorSet mCollapseAnimation;
    private AnimatorSet mExpandAnimation;
    private int mExpandDirection;
    private boolean mExpanded;
    private int mLabelsMargin;
    private int mLabelsPosition;
    private int mLabelsStyle;
    private int mLabelsVerticalOffset;
    private OnFloatingActionsMenuUpdateListener mListener;
    private int mMaxButtonHeight;
    private int mMaxButtonWidth;
    private RotatingDrawable mRotatingDrawable;
    private TouchDelegateGroup mTouchDelegateGroup;
    private static Interpolator sExpandInterpolator = new OvershootInterpolator();
    private static Interpolator sCollapseInterpolator = new DecelerateInterpolator(3.0f);
    private static Interpolator sAlphaExpandInterpolator = new DecelerateInterpolator();

    public interface OnFloatingActionsMenuUpdateListener {
        void onMenuCollapsed();

        void onMenuExpanded();
    }

    public FloatingActionsMenu(Context context) {
        this(context, null);
    }

    public FloatingActionsMenu(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mExpandAnimation = new AnimatorSet().setDuration(300L);
        this.mCollapseAnimation = new AnimatorSet().setDuration(300L);
        init(context, attributeSet);
    }

    public FloatingActionsMenu(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mExpandAnimation = new AnimatorSet().setDuration(300L);
        this.mCollapseAnimation = new AnimatorSet().setDuration(300L);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mButtonSpacing = (int) ((getResources().getDimension(R.dimen.fab_actions_spacing) - getResources().getDimension(R.dimen.fab_shadow_radius)) - getResources().getDimension(R.dimen.fab_shadow_offset));
        this.mLabelsMargin = getResources().getDimensionPixelSize(R.dimen.fab_labels_margin);
        this.mLabelsVerticalOffset = getResources().getDimensionPixelSize(R.dimen.fab_shadow_offset);
        TouchDelegateGroup touchDelegateGroup = new TouchDelegateGroup(this);
        this.mTouchDelegateGroup = touchDelegateGroup;
        setTouchDelegate(touchDelegateGroup);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionsMenu, 0, 0);
        this.mAddButtonPlusColor = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionsMenu_fab_addButtonPlusIconColor, getColor(android.R.color.white));
        this.mAddButtonColorNormal = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionsMenu_fab_addButtonColorNormal, getColor(android.R.color.holo_blue_dark));
        this.mAddButtonColorPressed = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionsMenu_fab_addButtonColorPressed, getColor(android.R.color.holo_blue_light));
        this.mAddButtonSize = typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionsMenu_fab_addButtonSize, 0);
        this.mAddButtonStrokeVisible = typedArrayObtainStyledAttributes.getBoolean(R.styleable.FloatingActionsMenu_fab_addButtonStrokeVisible, true);
        this.mExpandDirection = typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionsMenu_fab_expandDirection, 0);
        this.mLabelsStyle = typedArrayObtainStyledAttributes.getResourceId(R.styleable.FloatingActionsMenu_fab_labelStyle, 0);
        this.mLabelsPosition = typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionsMenu_fab_labelsPosition, 0);
        typedArrayObtainStyledAttributes.recycle();
        if (this.mLabelsStyle != 0 && expandsHorizontally()) {
            throw new IllegalStateException("Action labels in horizontal expand orientation is not supported.");
        }
        createAddButton(context);
    }

    public void setOnFloatingActionsMenuUpdateListener(OnFloatingActionsMenuUpdateListener onFloatingActionsMenuUpdateListener) {
        this.mListener = onFloatingActionsMenuUpdateListener;
    }

    private boolean expandsHorizontally() {
        int i = this.mExpandDirection;
        return i == 2 || i == 3;
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
        AddFloatingActionButton addFloatingActionButton = new AddFloatingActionButton(context) { // from class: com.getbase.floatingactionbutton.FloatingActionsMenu.1
            @Override // com.getbase.floatingactionbutton.FloatingActionButton
            void updateBackground() {
                this.mPlusColor = FloatingActionsMenu.this.mAddButtonPlusColor;
                this.mColorNormal = FloatingActionsMenu.this.mAddButtonColorNormal;
                this.mColorPressed = FloatingActionsMenu.this.mAddButtonColorPressed;
                this.mStrokeVisible = FloatingActionsMenu.this.mAddButtonStrokeVisible;
                super.updateBackground();
            }

            @Override // com.getbase.floatingactionbutton.AddFloatingActionButton, com.getbase.floatingactionbutton.FloatingActionButton
            Drawable getIconDrawable() {
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
        addFloatingActionButton.setId(R.id.fab_expand_menu_button);
        this.mAddButton.setSize(this.mAddButtonSize);
        this.mAddButton.setOnClickListener(new View.OnClickListener() { // from class: com.getbase.floatingactionbutton.FloatingActionsMenu.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FloatingActionsMenu.this.toggle();
            }
        });
        addView(this.mAddButton, super.generateDefaultLayoutParams());
        this.mButtonsCount++;
    }

    public void addButton(FloatingActionButton floatingActionButton) {
        addView(floatingActionButton, this.mButtonsCount - 1);
        this.mButtonsCount++;
        if (this.mLabelsStyle != 0) {
            createLabels();
        }
    }

    public void removeButton(FloatingActionButton floatingActionButton) {
        removeView(floatingActionButton.getLabelView());
        removeView(floatingActionButton);
        floatingActionButton.setTag(R.id.fab_label, null);
        this.mButtonsCount--;
    }

    private int getColor(int i) {
        return getResources().getColor(i);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        TextView textView;
        measureChildren(i, i2);
        this.mMaxButtonWidth = 0;
        this.mMaxButtonHeight = 0;
        int iMax = 0;
        int iAdjustForOvershoot = 0;
        int iAdjustForOvershoot2 = 0;
        for (int i3 = 0; i3 < this.mButtonsCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                int i4 = this.mExpandDirection;
                if (i4 == 0 || i4 == 1) {
                    this.mMaxButtonWidth = Math.max(this.mMaxButtonWidth, childAt.getMeasuredWidth());
                    iAdjustForOvershoot += childAt.getMeasuredHeight();
                } else if (i4 == 2 || i4 == 3) {
                    iAdjustForOvershoot2 += childAt.getMeasuredWidth();
                    this.mMaxButtonHeight = Math.max(this.mMaxButtonHeight, childAt.getMeasuredHeight());
                }
                if (!expandsHorizontally() && (textView = (TextView) childAt.getTag(R.id.fab_label)) != null) {
                    iMax = Math.max(iMax, textView.getMeasuredWidth());
                }
            }
        }
        if (!expandsHorizontally()) {
            iAdjustForOvershoot2 = this.mMaxButtonWidth + (iMax > 0 ? this.mLabelsMargin + iMax : 0);
        } else {
            iAdjustForOvershoot = this.mMaxButtonHeight;
        }
        int i5 = this.mExpandDirection;
        if (i5 == 0 || i5 == 1) {
            iAdjustForOvershoot = adjustForOvershoot(iAdjustForOvershoot + (this.mButtonSpacing * (this.mButtonsCount - 1)));
        } else if (i5 == 2 || i5 == 3) {
            iAdjustForOvershoot2 = adjustForOvershoot(iAdjustForOvershoot2 + (this.mButtonSpacing * (this.mButtonsCount - 1)));
        }
        setMeasuredDimension(iAdjustForOvershoot2, iAdjustForOvershoot);
    }

    private int adjustForOvershoot(int i) {
        return (i * 12) / 10;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredHeight;
        int i5;
        int measuredWidth;
        int measuredWidth2;
        int i6 = this.mExpandDirection;
        int i7 = 8;
        float f = 0.0f;
        char c = 0;
        char c2 = 1;
        if (i6 != 0 && i6 != 1) {
            if (i6 == 2 || i6 == 3) {
                boolean z2 = this.mExpandDirection == 2;
                int measuredWidth3 = z2 ? (i3 - i) - this.mAddButton.getMeasuredWidth() : 0;
                int i8 = this.mMaxButtonHeight;
                int measuredHeight2 = ((i4 - i2) - i8) + ((i8 - this.mAddButton.getMeasuredHeight()) / 2);
                AddFloatingActionButton addFloatingActionButton = this.mAddButton;
                addFloatingActionButton.layout(measuredWidth3, measuredHeight2, addFloatingActionButton.getMeasuredWidth() + measuredWidth3, this.mAddButton.getMeasuredHeight() + measuredHeight2);
                if (z2) {
                    measuredWidth2 = measuredWidth3 - this.mButtonSpacing;
                } else {
                    measuredWidth2 = this.mAddButton.getMeasuredWidth() + measuredWidth3 + this.mButtonSpacing;
                }
                for (int i9 = this.mButtonsCount - 1; i9 >= 0; i9--) {
                    View childAt = getChildAt(i9);
                    if (childAt != this.mAddButton && childAt.getVisibility() != 8) {
                        if (z2) {
                            measuredWidth2 -= childAt.getMeasuredWidth();
                        }
                        int measuredHeight3 = ((this.mAddButton.getMeasuredHeight() - childAt.getMeasuredHeight()) / 2) + measuredHeight2;
                        childAt.layout(measuredWidth2, measuredHeight3, childAt.getMeasuredWidth() + measuredWidth2, childAt.getMeasuredHeight() + measuredHeight3);
                        float f2 = measuredWidth3 - measuredWidth2;
                        childAt.setTranslationX(this.mExpanded ? 0.0f : f2);
                        childAt.setAlpha(this.mExpanded ? 1.0f : 0.0f);
                        LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                        layoutParams.mCollapseDir.setFloatValues(0.0f, f2);
                        layoutParams.mExpandDir.setFloatValues(f2, 0.0f);
                        layoutParams.setAnimationsTarget(childAt);
                        if (z2) {
                            measuredWidth2 -= this.mButtonSpacing;
                        } else {
                            measuredWidth2 = measuredWidth2 + childAt.getMeasuredWidth() + this.mButtonSpacing;
                        }
                    }
                }
                return;
            }
            return;
        }
        boolean z3 = this.mExpandDirection == 0;
        if (z) {
            this.mTouchDelegateGroup.clearTouchDelegates();
        }
        int measuredHeight4 = z3 ? (i4 - i2) - this.mAddButton.getMeasuredHeight() : 0;
        int i10 = this.mLabelsPosition == 0 ? (i3 - i) - (this.mMaxButtonWidth / 2) : this.mMaxButtonWidth / 2;
        int measuredWidth4 = i10 - (this.mAddButton.getMeasuredWidth() / 2);
        AddFloatingActionButton addFloatingActionButton2 = this.mAddButton;
        addFloatingActionButton2.layout(measuredWidth4, measuredHeight4, addFloatingActionButton2.getMeasuredWidth() + measuredWidth4, this.mAddButton.getMeasuredHeight() + measuredHeight4);
        int i11 = (this.mMaxButtonWidth / 2) + this.mLabelsMargin;
        int i12 = this.mLabelsPosition == 0 ? i10 - i11 : i11 + i10;
        if (z3) {
            measuredHeight = measuredHeight4 - this.mButtonSpacing;
        } else {
            measuredHeight = this.mAddButton.getMeasuredHeight() + measuredHeight4 + this.mButtonSpacing;
        }
        int i13 = this.mButtonsCount - 1;
        while (i13 >= 0) {
            View childAt2 = getChildAt(i13);
            if (childAt2 == this.mAddButton || childAt2.getVisibility() == i7) {
                i5 = measuredHeight4;
            } else {
                int measuredWidth5 = i10 - (childAt2.getMeasuredWidth() / 2);
                if (z3) {
                    measuredHeight -= childAt2.getMeasuredHeight();
                }
                childAt2.layout(measuredWidth5, measuredHeight, childAt2.getMeasuredWidth() + measuredWidth5, childAt2.getMeasuredHeight() + measuredHeight);
                float f3 = measuredHeight4 - measuredHeight;
                childAt2.setTranslationY(this.mExpanded ? 0.0f : f3);
                childAt2.setAlpha(this.mExpanded ? 1.0f : 0.0f);
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                ObjectAnimator objectAnimator = layoutParams2.mCollapseDir;
                i5 = measuredHeight4;
                float[] fArr = new float[2];
                fArr[c] = f;
                fArr[c2] = f3;
                objectAnimator.setFloatValues(fArr);
                ObjectAnimator objectAnimator2 = layoutParams2.mExpandDir;
                float[] fArr2 = new float[2];
                fArr2[c] = f3;
                fArr2[c2] = f;
                objectAnimator2.setFloatValues(fArr2);
                layoutParams2.setAnimationsTarget(childAt2);
                View view = (View) childAt2.getTag(R.id.fab_label);
                if (view != null) {
                    if (this.mLabelsPosition == 0) {
                        measuredWidth = i12 - view.getMeasuredWidth();
                    } else {
                        measuredWidth = view.getMeasuredWidth() + i12;
                    }
                    int i14 = this.mLabelsPosition == 0 ? measuredWidth : i12;
                    if (this.mLabelsPosition == 0) {
                        measuredWidth = i12;
                    }
                    int measuredHeight5 = (measuredHeight - this.mLabelsVerticalOffset) + ((childAt2.getMeasuredHeight() - view.getMeasuredHeight()) / 2);
                    view.layout(i14, measuredHeight5, measuredWidth, measuredHeight5 + view.getMeasuredHeight());
                    this.mTouchDelegateGroup.addTouchDelegate(new TouchDelegate(new Rect(Math.min(measuredWidth5, i14), measuredHeight - (this.mButtonSpacing / 2), Math.max(measuredWidth5 + childAt2.getMeasuredWidth(), measuredWidth), childAt2.getMeasuredHeight() + measuredHeight + (this.mButtonSpacing / 2)), childAt2));
                    view.setTranslationY(this.mExpanded ? 0.0f : f3);
                    view.setAlpha(this.mExpanded ? 1.0f : 0.0f);
                    LayoutParams layoutParams3 = (LayoutParams) view.getLayoutParams();
                    layoutParams3.mCollapseDir.setFloatValues(0.0f, f3);
                    layoutParams3.mExpandDir.setFloatValues(f3, 0.0f);
                    layoutParams3.setAnimationsTarget(view);
                }
                if (z3) {
                    measuredHeight -= this.mButtonSpacing;
                } else {
                    measuredHeight = measuredHeight + childAt2.getMeasuredHeight() + this.mButtonSpacing;
                }
            }
            i13--;
            measuredHeight4 = i5;
            i7 = 8;
            f = 0.0f;
            c = 0;
            c2 = 1;
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
        private boolean animationsSetToPlay;
        private ObjectAnimator mCollapseAlpha;
        private ObjectAnimator mCollapseDir;
        private ObjectAnimator mExpandAlpha;
        private ObjectAnimator mExpandDir;

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mExpandDir = new ObjectAnimator();
            this.mExpandAlpha = new ObjectAnimator();
            this.mCollapseDir = new ObjectAnimator();
            this.mCollapseAlpha = new ObjectAnimator();
            this.mExpandDir.setInterpolator(FloatingActionsMenu.sExpandInterpolator);
            this.mExpandAlpha.setInterpolator(FloatingActionsMenu.sAlphaExpandInterpolator);
            this.mCollapseDir.setInterpolator(FloatingActionsMenu.sCollapseInterpolator);
            this.mCollapseAlpha.setInterpolator(FloatingActionsMenu.sCollapseInterpolator);
            this.mCollapseAlpha.setProperty(View.ALPHA);
            this.mCollapseAlpha.setFloatValues(1.0f, 0.0f);
            this.mExpandAlpha.setProperty(View.ALPHA);
            this.mExpandAlpha.setFloatValues(0.0f, 1.0f);
            int i = FloatingActionsMenu.this.mExpandDirection;
            if (i == 0 || i == 1) {
                this.mCollapseDir.setProperty(View.TRANSLATION_Y);
                this.mExpandDir.setProperty(View.TRANSLATION_Y);
            } else if (i == 2 || i == 3) {
                this.mCollapseDir.setProperty(View.TRANSLATION_X);
                this.mExpandDir.setProperty(View.TRANSLATION_X);
            }
        }

        public void setAnimationsTarget(View view) {
            this.mCollapseAlpha.setTarget(view);
            this.mCollapseDir.setTarget(view);
            this.mExpandAlpha.setTarget(view);
            this.mExpandDir.setTarget(view);
            if (this.animationsSetToPlay) {
                return;
            }
            addLayerTypeListener(this.mExpandDir, view);
            addLayerTypeListener(this.mCollapseDir, view);
            FloatingActionsMenu.this.mCollapseAnimation.play(this.mCollapseAlpha);
            FloatingActionsMenu.this.mCollapseAnimation.play(this.mCollapseDir);
            FloatingActionsMenu.this.mExpandAnimation.play(this.mExpandAlpha);
            FloatingActionsMenu.this.mExpandAnimation.play(this.mExpandDir);
            this.animationsSetToPlay = true;
        }

        private void addLayerTypeListener(Animator animator, final View view) {
            animator.addListener(new AnimatorListenerAdapter() { // from class: com.getbase.floatingactionbutton.FloatingActionsMenu.LayoutParams.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    view.setLayerType(0, null);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator2) {
                    view.setLayerType(2, null);
                }
            });
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        bringChildToFront(this.mAddButton);
        this.mButtonsCount = getChildCount();
        if (this.mLabelsStyle != 0) {
            createLabels();
        }
    }

    private void createLabels() {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getContext(), this.mLabelsStyle);
        for (int i = 0; i < this.mButtonsCount; i++) {
            FloatingActionButton floatingActionButton = (FloatingActionButton) getChildAt(i);
            String title = floatingActionButton.getTitle();
            if (floatingActionButton != this.mAddButton && title != null && floatingActionButton.getTag(R.id.fab_label) == null) {
                TextView textView = new TextView(contextThemeWrapper);
                textView.setTextAppearance(getContext(), this.mLabelsStyle);
                textView.setText(floatingActionButton.getTitle());
                addView(textView);
                floatingActionButton.setTag(R.id.fab_label, textView);
            }
        }
    }

    public void collapse() {
        collapse(false);
    }

    public void collapseImmediately() {
        collapse(true);
    }

    private void collapse(boolean z) {
        if (this.mExpanded) {
            this.mExpanded = false;
            this.mTouchDelegateGroup.setEnabled(false);
            this.mCollapseAnimation.setDuration(z ? 0L : 300L);
            this.mCollapseAnimation.start();
            this.mExpandAnimation.cancel();
            OnFloatingActionsMenuUpdateListener onFloatingActionsMenuUpdateListener = this.mListener;
            if (onFloatingActionsMenuUpdateListener != null) {
                onFloatingActionsMenuUpdateListener.onMenuCollapsed();
            }
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
        this.mTouchDelegateGroup.setEnabled(true);
        this.mCollapseAnimation.cancel();
        this.mExpandAnimation.start();
        OnFloatingActionsMenuUpdateListener onFloatingActionsMenuUpdateListener = this.mListener;
        if (onFloatingActionsMenuUpdateListener != null) {
            onFloatingActionsMenuUpdateListener.onMenuExpanded();
        }
    }

    public boolean isExpanded() {
        return this.mExpanded;
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mAddButton.setEnabled(z);
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
            this.mTouchDelegateGroup.setEnabled(z);
            RotatingDrawable rotatingDrawable = this.mRotatingDrawable;
            if (rotatingDrawable != null) {
                rotatingDrawable.setRotation(this.mExpanded ? EXPANDED_PLUS_ROTATION : 0.0f);
            }
            super.onRestoreInstanceState(savedState.getSuperState());
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.getbase.floatingactionbutton.FloatingActionsMenu.SavedState.1
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
}
