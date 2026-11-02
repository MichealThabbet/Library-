package com.github.clans.fab;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class FloatingActionMenu extends ViewGroup {
    private static final int ANIMATION_DURATION = 300;
    private static final float CLOSED_PLUS_ROTATION = 0.0f;
    private static final int LABELS_POSITION_LEFT = 0;
    private static final int LABELS_POSITION_RIGHT = 1;
    private static final float OPENED_PLUS_ROTATION_LEFT = -135.0f;
    private static final float OPENED_PLUS_ROTATION_RIGHT = 135.0f;
    private static final int OPEN_DOWN = 1;
    private static final int OPEN_UP = 0;
    private int mAnimationDelayPerItem;
    private int mBackgroundColor;
    private int mButtonSpacing;
    private int mButtonsCount;
    private AnimatorSet mCloseAnimatorSet;
    private Interpolator mCloseInterpolator;
    private Typeface mCustomTypefaceFromFont;
    private ValueAnimator mHideBackgroundAnimator;
    private Drawable mIcon;
    private boolean mIconAnimated;
    private AnimatorSet mIconToggleSet;
    private ImageView mImageToggle;
    private Animation mImageToggleHideAnimation;
    private Animation mImageToggleShowAnimation;
    private boolean mIsAnimated;
    private boolean mIsMenuButtonAnimationRunning;
    private boolean mIsMenuOpening;
    private boolean mIsSetClosedOnTouchOutside;
    private int mLabelsColorNormal;
    private int mLabelsColorPressed;
    private int mLabelsColorRipple;
    private Context mLabelsContext;
    private int mLabelsCornerRadius;
    private int mLabelsEllipsize;
    private int mLabelsHideAnimation;
    private int mLabelsMargin;
    private int mLabelsMaxLines;
    private int mLabelsPaddingBottom;
    private int mLabelsPaddingLeft;
    private int mLabelsPaddingRight;
    private int mLabelsPaddingTop;
    private int mLabelsPosition;
    private int mLabelsShowAnimation;
    private boolean mLabelsShowShadow;
    private boolean mLabelsSingleLine;
    private int mLabelsStyle;
    private ColorStateList mLabelsTextColor;
    private float mLabelsTextSize;
    private int mLabelsVerticalOffset;
    private int mMaxButtonWidth;
    private FloatingActionButton mMenuButton;
    private Animation mMenuButtonHideAnimation;
    private Animation mMenuButtonShowAnimation;
    private int mMenuColorNormal;
    private int mMenuColorPressed;
    private int mMenuColorRipple;
    private int mMenuFabSize;
    private String mMenuLabelText;
    private boolean mMenuOpened;
    private int mMenuShadowColor;
    private float mMenuShadowRadius;
    private float mMenuShadowXOffset;
    private float mMenuShadowYOffset;
    private boolean mMenuShowShadow;
    private AnimatorSet mOpenAnimatorSet;
    private int mOpenDirection;
    private Interpolator mOpenInterpolator;
    private ValueAnimator mShowBackgroundAnimator;
    private OnMenuToggleListener mToggleListener;
    private Handler mUiHandler;
    private boolean mUsingMenuLabel;

    public interface OnMenuToggleListener {
        void onMenuToggle(boolean z);
    }

    private int adjustForOvershoot(int i) {
        double d = i;
        return (int) ((0.03d * d) + d);
    }

    public FloatingActionMenu(Context context) {
        this(context, null);
    }

    public FloatingActionMenu(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FloatingActionMenu(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOpenAnimatorSet = new AnimatorSet();
        this.mCloseAnimatorSet = new AnimatorSet();
        this.mButtonSpacing = Util.dpToPx(getContext(), 0.0f);
        this.mLabelsMargin = Util.dpToPx(getContext(), 0.0f);
        this.mLabelsVerticalOffset = Util.dpToPx(getContext(), 0.0f);
        this.mUiHandler = new Handler();
        this.mLabelsPaddingTop = Util.dpToPx(getContext(), 4.0f);
        this.mLabelsPaddingRight = Util.dpToPx(getContext(), 8.0f);
        this.mLabelsPaddingBottom = Util.dpToPx(getContext(), 4.0f);
        this.mLabelsPaddingLeft = Util.dpToPx(getContext(), 8.0f);
        this.mLabelsCornerRadius = Util.dpToPx(getContext(), 3.0f);
        this.mMenuShadowRadius = 4.0f;
        this.mMenuShadowXOffset = 1.0f;
        this.mMenuShadowYOffset = 3.0f;
        this.mIsAnimated = true;
        this.mIconAnimated = true;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionMenu, 0, 0);
        this.mButtonSpacing = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionMenu_menu_buttonSpacing, this.mButtonSpacing);
        this.mLabelsMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionMenu_menu_labels_margin, this.mLabelsMargin);
        this.mLabelsPosition = typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionMenu_menu_labels_position, 0);
        this.mLabelsShowAnimation = typedArrayObtainStyledAttributes.getResourceId(R.styleable.FloatingActionMenu_menu_labels_showAnimation, this.mLabelsPosition == 0 ? R.anim.fab_slide_in_from_right : R.anim.fab_slide_in_from_left);
        this.mLabelsHideAnimation = typedArrayObtainStyledAttributes.getResourceId(R.styleable.FloatingActionMenu_menu_labels_hideAnimation, this.mLabelsPosition == 0 ? R.anim.fab_slide_out_to_right : R.anim.fab_slide_out_to_left);
        this.mLabelsPaddingTop = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionMenu_menu_labels_paddingTop, this.mLabelsPaddingTop);
        this.mLabelsPaddingRight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionMenu_menu_labels_paddingRight, this.mLabelsPaddingRight);
        this.mLabelsPaddingBottom = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionMenu_menu_labels_paddingBottom, this.mLabelsPaddingBottom);
        this.mLabelsPaddingLeft = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionMenu_menu_labels_paddingLeft, this.mLabelsPaddingLeft);
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.FloatingActionMenu_menu_labels_textColor);
        this.mLabelsTextColor = colorStateList;
        if (colorStateList == null) {
            this.mLabelsTextColor = ColorStateList.valueOf(-1);
        }
        this.mLabelsTextSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.FloatingActionMenu_menu_labels_textSize, getResources().getDimension(R.dimen.labels_text_size));
        this.mLabelsCornerRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionMenu_menu_labels_cornerRadius, this.mLabelsCornerRadius);
        this.mLabelsShowShadow = typedArrayObtainStyledAttributes.getBoolean(R.styleable.FloatingActionMenu_menu_labels_showShadow, true);
        this.mLabelsColorNormal = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionMenu_menu_labels_colorNormal, -13421773);
        this.mLabelsColorPressed = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionMenu_menu_labels_colorPressed, -12303292);
        this.mLabelsColorRipple = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionMenu_menu_labels_colorRipple, 1728053247);
        this.mMenuShowShadow = typedArrayObtainStyledAttributes.getBoolean(R.styleable.FloatingActionMenu_menu_showShadow, true);
        this.mMenuShadowColor = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionMenu_menu_shadowColor, 1711276032);
        this.mMenuShadowRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.FloatingActionMenu_menu_shadowRadius, this.mMenuShadowRadius);
        this.mMenuShadowXOffset = typedArrayObtainStyledAttributes.getDimension(R.styleable.FloatingActionMenu_menu_shadowXOffset, this.mMenuShadowXOffset);
        this.mMenuShadowYOffset = typedArrayObtainStyledAttributes.getDimension(R.styleable.FloatingActionMenu_menu_shadowYOffset, this.mMenuShadowYOffset);
        this.mMenuColorNormal = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionMenu_menu_colorNormal, -2473162);
        this.mMenuColorPressed = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionMenu_menu_colorPressed, -1617853);
        this.mMenuColorRipple = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionMenu_menu_colorRipple, -1711276033);
        this.mAnimationDelayPerItem = typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionMenu_menu_animationDelayPerItem, 50);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.FloatingActionMenu_menu_icon);
        this.mIcon = drawable;
        if (drawable == null) {
            this.mIcon = getResources().getDrawable(R.drawable.fab_add);
        }
        this.mLabelsSingleLine = typedArrayObtainStyledAttributes.getBoolean(R.styleable.FloatingActionMenu_menu_labels_singleLine, false);
        this.mLabelsEllipsize = typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionMenu_menu_labels_ellipsize, 0);
        this.mLabelsMaxLines = typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionMenu_menu_labels_maxLines, -1);
        this.mMenuFabSize = typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionMenu_menu_fab_size, 0);
        this.mLabelsStyle = typedArrayObtainStyledAttributes.getResourceId(R.styleable.FloatingActionMenu_menu_labels_style, 0);
        String string = typedArrayObtainStyledAttributes.getString(R.styleable.FloatingActionMenu_menu_labels_customFont);
        try {
            if (!TextUtils.isEmpty(string)) {
                this.mCustomTypefaceFromFont = Typeface.createFromAsset(getContext().getAssets(), string);
            }
            this.mOpenDirection = typedArrayObtainStyledAttributes.getInt(R.styleable.FloatingActionMenu_menu_openDirection, 0);
            this.mBackgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.FloatingActionMenu_menu_backgroundColor, 0);
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.FloatingActionMenu_menu_fab_label)) {
                this.mUsingMenuLabel = true;
                this.mMenuLabelText = typedArrayObtainStyledAttributes.getString(R.styleable.FloatingActionMenu_menu_fab_label);
            }
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.FloatingActionMenu_menu_labels_padding)) {
                initPadding(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionMenu_menu_labels_padding, 0));
            }
            this.mOpenInterpolator = new OvershootInterpolator();
            this.mCloseInterpolator = new AnticipateInterpolator();
            this.mLabelsContext = new ContextThemeWrapper(getContext(), this.mLabelsStyle);
            initBackgroundDimAnimation();
            createMenuButton();
            initMenuButtonAnimations(typedArrayObtainStyledAttributes);
            typedArrayObtainStyledAttributes.recycle();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Unable to load specified custom font: " + string, e);
        }
    }

    private void initMenuButtonAnimations(TypedArray typedArray) {
        int resourceId = typedArray.getResourceId(R.styleable.FloatingActionMenu_menu_fab_show_animation, R.anim.fab_scale_up);
        setMenuButtonShowAnimation(AnimationUtils.loadAnimation(getContext(), resourceId));
        this.mImageToggleShowAnimation = AnimationUtils.loadAnimation(getContext(), resourceId);
        int resourceId2 = typedArray.getResourceId(R.styleable.FloatingActionMenu_menu_fab_hide_animation, R.anim.fab_scale_down);
        setMenuButtonHideAnimation(AnimationUtils.loadAnimation(getContext(), resourceId2));
        this.mImageToggleHideAnimation = AnimationUtils.loadAnimation(getContext(), resourceId2);
    }

    private void initBackgroundDimAnimation() {
        int iAlpha = Color.alpha(this.mBackgroundColor);
        final int iRed = Color.red(this.mBackgroundColor);
        final int iGreen = Color.green(this.mBackgroundColor);
        final int iBlue = Color.blue(this.mBackgroundColor);
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, iAlpha);
        this.mShowBackgroundAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(300L);
        this.mShowBackgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.github.clans.fab.FloatingActionMenu.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                FloatingActionMenu.this.setBackgroundColor(Color.argb(((Integer) valueAnimator.getAnimatedValue()).intValue(), iRed, iGreen, iBlue));
            }
        });
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(iAlpha, 0);
        this.mHideBackgroundAnimator = valueAnimatorOfInt2;
        valueAnimatorOfInt2.setDuration(300L);
        this.mHideBackgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.github.clans.fab.FloatingActionMenu.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                FloatingActionMenu.this.setBackgroundColor(Color.argb(((Integer) valueAnimator.getAnimatedValue()).intValue(), iRed, iGreen, iBlue));
            }
        });
    }

    private boolean isBackgroundEnabled() {
        return this.mBackgroundColor != 0;
    }

    private void initPadding(int i) {
        this.mLabelsPaddingTop = i;
        this.mLabelsPaddingRight = i;
        this.mLabelsPaddingBottom = i;
        this.mLabelsPaddingLeft = i;
    }

    private void createMenuButton() {
        FloatingActionButton floatingActionButton = new FloatingActionButton(getContext());
        this.mMenuButton = floatingActionButton;
        floatingActionButton.mShowShadow = this.mMenuShowShadow;
        if (this.mMenuShowShadow) {
            this.mMenuButton.mShadowRadius = Util.dpToPx(getContext(), this.mMenuShadowRadius);
            this.mMenuButton.mShadowXOffset = Util.dpToPx(getContext(), this.mMenuShadowXOffset);
            this.mMenuButton.mShadowYOffset = Util.dpToPx(getContext(), this.mMenuShadowYOffset);
        }
        this.mMenuButton.setColors(this.mMenuColorNormal, this.mMenuColorPressed, this.mMenuColorRipple);
        this.mMenuButton.mShadowColor = this.mMenuShadowColor;
        this.mMenuButton.mFabSize = this.mMenuFabSize;
        this.mMenuButton.updateBackground();
        this.mMenuButton.setLabelText(this.mMenuLabelText);
        ImageView imageView = new ImageView(getContext());
        this.mImageToggle = imageView;
        imageView.setImageDrawable(this.mIcon);
        addView(this.mMenuButton, super.generateDefaultLayoutParams());
        addView(this.mImageToggle);
        createDefaultIconAnimation();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0015 A[PHI: r0
      0x0015: PHI (r0v13 float) = (r0v3 float), (r0v16 float) binds: [B:16:0x0023, B:9:0x0013] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void createDefaultIconAnimation() {
        /*
            r8 = this;
            int r0 = r8.mOpenDirection
            r1 = 1124532224(0x43070000, float:135.0)
            r2 = -1022951424(0xffffffffc3070000, float:-135.0)
            if (r0 != 0) goto L18
            int r0 = r8.mLabelsPosition
            if (r0 != 0) goto Lf
            r0 = -1022951424(0xffffffffc3070000, float:-135.0)
            goto L11
        Lf:
            r0 = 1124532224(0x43070000, float:135.0)
        L11:
            int r3 = r8.mLabelsPosition
            if (r3 != 0) goto L25
        L15:
            r1 = -1022951424(0xffffffffc3070000, float:-135.0)
            goto L25
        L18:
            int r0 = r8.mLabelsPosition
            if (r0 != 0) goto L1f
            r0 = 1124532224(0x43070000, float:135.0)
            goto L21
        L1f:
            r0 = -1022951424(0xffffffffc3070000, float:-135.0)
        L21:
            int r3 = r8.mLabelsPosition
            if (r3 != 0) goto L15
        L25:
            android.widget.ImageView r2 = r8.mImageToggle
            r3 = 2
            float[] r4 = new float[r3]
            r5 = 0
            r4[r5] = r0
            r0 = 1
            r6 = 0
            r4[r0] = r6
            java.lang.String r7 = "rotation"
            android.animation.ObjectAnimator r2 = android.animation.ObjectAnimator.ofFloat(r2, r7, r4)
            android.widget.ImageView r4 = r8.mImageToggle
            float[] r3 = new float[r3]
            r3[r5] = r6
            r3[r0] = r1
            android.animation.ObjectAnimator r0 = android.animation.ObjectAnimator.ofFloat(r4, r7, r3)
            android.animation.AnimatorSet r1 = r8.mOpenAnimatorSet
            r1.play(r0)
            android.animation.AnimatorSet r0 = r8.mCloseAnimatorSet
            r0.play(r2)
            android.animation.AnimatorSet r0 = r8.mOpenAnimatorSet
            android.view.animation.Interpolator r1 = r8.mOpenInterpolator
            r0.setInterpolator(r1)
            android.animation.AnimatorSet r0 = r8.mCloseAnimatorSet
            android.view.animation.Interpolator r1 = r8.mCloseInterpolator
            r0.setInterpolator(r1)
            android.animation.AnimatorSet r0 = r8.mOpenAnimatorSet
            r1 = 300(0x12c, double:1.48E-321)
            r0.setDuration(r1)
            android.animation.AnimatorSet r0 = r8.mCloseAnimatorSet
            r0.setDuration(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.clans.fab.FloatingActionMenu.createDefaultIconAnimation():void");
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        this.mMaxButtonWidth = 0;
        measureChildWithMargins(this.mImageToggle, i, 0, i2, 0);
        for (int i3 = 0; i3 < this.mButtonsCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8 && childAt != this.mImageToggle) {
                measureChildWithMargins(childAt, i, 0, i2, 0);
                this.mMaxButtonWidth = Math.max(this.mMaxButtonWidth, childAt.getMeasuredWidth());
            }
        }
        int i4 = 0;
        int i5 = 0;
        int iMax = 0;
        while (true) {
            if (i5 >= this.mButtonsCount) {
                break;
            }
            View childAt2 = getChildAt(i5);
            if (childAt2.getVisibility() != 8 && childAt2 != this.mImageToggle) {
                int measuredWidth = childAt2.getMeasuredWidth() + 0;
                int measuredHeight = i4 + childAt2.getMeasuredHeight();
                Label label = (Label) childAt2.getTag(R.id.fab_label);
                if (label != null) {
                    int measuredWidth2 = (this.mMaxButtonWidth - childAt2.getMeasuredWidth()) / (this.mUsingMenuLabel ? 1 : 2);
                    measureChildWithMargins(label, i, childAt2.getMeasuredWidth() + label.calculateShadowWidth() + this.mLabelsMargin + measuredWidth2, i2, 0);
                    iMax = Math.max(iMax, measuredWidth + label.getMeasuredWidth() + measuredWidth2);
                }
                i4 = measuredHeight;
            }
            i5++;
        }
        int iMax2 = Math.max(this.mMaxButtonWidth, iMax + this.mLabelsMargin) + getPaddingLeft() + getPaddingRight();
        int iAdjustForOvershoot = adjustForOvershoot(i4 + (this.mButtonSpacing * (this.mButtonsCount - 1)) + getPaddingTop() + getPaddingBottom());
        if (getLayoutParams().width == -1) {
            iMax2 = getDefaultSize(getSuggestedMinimumWidth(), i);
        }
        if (getLayoutParams().height == -1) {
            iAdjustForOvershoot = getDefaultSize(getSuggestedMinimumHeight(), i2);
        }
        setMeasuredDimension(iMax2, iAdjustForOvershoot);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft;
        int paddingTop;
        int measuredWidth;
        if (this.mLabelsPosition == 0) {
            paddingLeft = ((i3 - i) - (this.mMaxButtonWidth / 2)) - getPaddingRight();
        } else {
            paddingLeft = (this.mMaxButtonWidth / 2) + getPaddingLeft();
        }
        boolean z2 = this.mOpenDirection == 0;
        if (z2) {
            paddingTop = ((i4 - i2) - this.mMenuButton.getMeasuredHeight()) - getPaddingBottom();
        } else {
            paddingTop = getPaddingTop();
        }
        int measuredWidth2 = paddingLeft - (this.mMenuButton.getMeasuredWidth() / 2);
        FloatingActionButton floatingActionButton = this.mMenuButton;
        floatingActionButton.layout(measuredWidth2, paddingTop, floatingActionButton.getMeasuredWidth() + measuredWidth2, this.mMenuButton.getMeasuredHeight() + paddingTop);
        int measuredWidth3 = paddingLeft - (this.mImageToggle.getMeasuredWidth() / 2);
        int measuredHeight = ((this.mMenuButton.getMeasuredHeight() / 2) + paddingTop) - (this.mImageToggle.getMeasuredHeight() / 2);
        ImageView imageView = this.mImageToggle;
        imageView.layout(measuredWidth3, measuredHeight, imageView.getMeasuredWidth() + measuredWidth3, this.mImageToggle.getMeasuredHeight() + measuredHeight);
        if (z2) {
            paddingTop = paddingTop + this.mMenuButton.getMeasuredHeight() + this.mButtonSpacing;
        }
        for (int i5 = this.mButtonsCount - 1; i5 >= 0; i5--) {
            View childAt = getChildAt(i5);
            if (childAt != this.mImageToggle) {
                FloatingActionButton floatingActionButton2 = (FloatingActionButton) childAt;
                if (floatingActionButton2.getVisibility() != 8) {
                    int measuredWidth4 = paddingLeft - (floatingActionButton2.getMeasuredWidth() / 2);
                    if (z2) {
                        paddingTop = (paddingTop - floatingActionButton2.getMeasuredHeight()) - this.mButtonSpacing;
                    }
                    if (floatingActionButton2 != this.mMenuButton) {
                        floatingActionButton2.layout(measuredWidth4, paddingTop, floatingActionButton2.getMeasuredWidth() + measuredWidth4, floatingActionButton2.getMeasuredHeight() + paddingTop);
                        if (!this.mIsMenuOpening) {
                            floatingActionButton2.hide(false);
                        }
                    }
                    View view = (View) floatingActionButton2.getTag(R.id.fab_label);
                    if (view != null) {
                        int measuredWidth5 = ((this.mUsingMenuLabel ? this.mMaxButtonWidth : floatingActionButton2.getMeasuredWidth()) / 2) + this.mLabelsMargin;
                        int i6 = this.mLabelsPosition == 0 ? paddingLeft - measuredWidth5 : measuredWidth5 + paddingLeft;
                        if (this.mLabelsPosition == 0) {
                            measuredWidth = i6 - view.getMeasuredWidth();
                        } else {
                            measuredWidth = view.getMeasuredWidth() + i6;
                        }
                        int i7 = this.mLabelsPosition == 0 ? measuredWidth : i6;
                        if (this.mLabelsPosition != 0) {
                            i6 = measuredWidth;
                        }
                        int measuredHeight2 = (paddingTop - this.mLabelsVerticalOffset) + ((floatingActionButton2.getMeasuredHeight() - view.getMeasuredHeight()) / 2);
                        view.layout(i7, measuredHeight2, i6, view.getMeasuredHeight() + measuredHeight2);
                        if (!this.mIsMenuOpening) {
                            view.setVisibility(4);
                        }
                    }
                    if (z2) {
                        paddingTop -= this.mButtonSpacing;
                    } else {
                        paddingTop = paddingTop + childAt.getMeasuredHeight() + this.mButtonSpacing;
                    }
                }
            }
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        bringChildToFront(this.mMenuButton);
        bringChildToFront(this.mImageToggle);
        this.mButtonsCount = getChildCount();
        createLabels();
    }

    private void createLabels() {
        for (int i = 0; i < this.mButtonsCount; i++) {
            if (getChildAt(i) != this.mImageToggle) {
                FloatingActionButton floatingActionButton = (FloatingActionButton) getChildAt(i);
                if (floatingActionButton.getTag(R.id.fab_label) == null) {
                    addLabel(floatingActionButton);
                    FloatingActionButton floatingActionButton2 = this.mMenuButton;
                    if (floatingActionButton == floatingActionButton2) {
                        floatingActionButton2.setOnClickListener(new View.OnClickListener() { // from class: com.github.clans.fab.FloatingActionMenu.3
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view) {
                                FloatingActionMenu floatingActionMenu = FloatingActionMenu.this;
                                floatingActionMenu.toggle(floatingActionMenu.mIsAnimated);
                            }
                        });
                    }
                }
            }
        }
    }

    private void addLabel(FloatingActionButton floatingActionButton) {
        String labelText = floatingActionButton.getLabelText();
        if (TextUtils.isEmpty(labelText)) {
            return;
        }
        Label label = new Label(this.mLabelsContext);
        label.setClickable(true);
        label.setFab(floatingActionButton);
        label.setShowAnimation(AnimationUtils.loadAnimation(getContext(), this.mLabelsShowAnimation));
        label.setHideAnimation(AnimationUtils.loadAnimation(getContext(), this.mLabelsHideAnimation));
        if (this.mLabelsStyle > 0) {
            label.setTextAppearance(getContext(), this.mLabelsStyle);
            label.setShowShadow(false);
            label.setUsingStyle(true);
        } else {
            label.setColors(this.mLabelsColorNormal, this.mLabelsColorPressed, this.mLabelsColorRipple);
            label.setShowShadow(this.mLabelsShowShadow);
            label.setCornerRadius(this.mLabelsCornerRadius);
            if (this.mLabelsEllipsize > 0) {
                setLabelEllipsize(label);
            }
            label.setMaxLines(this.mLabelsMaxLines);
            label.updateBackground();
            label.setTextSize(0, this.mLabelsTextSize);
            label.setTextColor(this.mLabelsTextColor);
            int shadowRadius = this.mLabelsPaddingLeft;
            int shadowRadius2 = this.mLabelsPaddingTop;
            if (this.mLabelsShowShadow) {
                shadowRadius += floatingActionButton.getShadowRadius() + Math.abs(floatingActionButton.getShadowXOffset());
                shadowRadius2 += floatingActionButton.getShadowRadius() + Math.abs(floatingActionButton.getShadowYOffset());
            }
            label.setPadding(shadowRadius, shadowRadius2, this.mLabelsPaddingLeft, this.mLabelsPaddingTop);
            if (this.mLabelsMaxLines < 0 || this.mLabelsSingleLine) {
                label.setSingleLine(this.mLabelsSingleLine);
            }
        }
        Typeface typeface = this.mCustomTypefaceFromFont;
        if (typeface != null) {
            label.setTypeface(typeface);
        }
        label.setText(labelText);
        label.setOnClickListener(floatingActionButton.getOnClickListener());
        addView(label);
        floatingActionButton.setTag(R.id.fab_label, label);
    }

    private void setLabelEllipsize(Label label) {
        int i = this.mLabelsEllipsize;
        if (i == 1) {
            label.setEllipsize(TextUtils.TruncateAt.START);
            return;
        }
        if (i == 2) {
            label.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        } else if (i == 3) {
            label.setEllipsize(TextUtils.TruncateAt.END);
        } else {
            if (i != 4) {
                return;
            }
            label.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        }
    }

    @Override // android.view.ViewGroup
    public ViewGroup.MarginLayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public ViewGroup.MarginLayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new ViewGroup.MarginLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public ViewGroup.MarginLayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideMenuButtonWithImage(boolean z) {
        if (isMenuButtonHidden()) {
            return;
        }
        this.mMenuButton.hide(z);
        if (z) {
            this.mImageToggle.startAnimation(this.mImageToggleHideAnimation);
        }
        this.mImageToggle.setVisibility(4);
        this.mIsMenuButtonAnimationRunning = false;
    }

    private void showMenuButtonWithImage(boolean z) {
        if (isMenuButtonHidden()) {
            this.mMenuButton.show(z);
            if (z) {
                this.mImageToggle.startAnimation(this.mImageToggleShowAnimation);
            }
            this.mImageToggle.setVisibility(0);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mIsSetClosedOnTouchOutside) {
            int action = motionEvent.getAction();
            if (action == 0) {
                return isOpened();
            }
            if (action != 1) {
                return false;
            }
            close(this.mIsAnimated);
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean isOpened() {
        return this.mMenuOpened;
    }

    public void toggle(boolean z) {
        if (isOpened()) {
            close(z);
        } else {
            open(z);
        }
    }

    public void open(final boolean z) {
        if (isOpened()) {
            return;
        }
        if (isBackgroundEnabled()) {
            this.mShowBackgroundAnimator.start();
        }
        if (this.mIconAnimated) {
            AnimatorSet animatorSet = this.mIconToggleSet;
            if (animatorSet != null) {
                animatorSet.start();
            } else {
                this.mCloseAnimatorSet.cancel();
                this.mOpenAnimatorSet.start();
            }
        }
        this.mIsMenuOpening = true;
        int i = 0;
        int i2 = 0;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if ((childAt instanceof FloatingActionButton) && childAt.getVisibility() != 8) {
                i++;
                final FloatingActionButton floatingActionButton = (FloatingActionButton) childAt;
                this.mUiHandler.postDelayed(new Runnable() { // from class: com.github.clans.fab.FloatingActionMenu.4
                    @Override // java.lang.Runnable
                    public void run() {
                        if (FloatingActionMenu.this.isOpened()) {
                            return;
                        }
                        if (floatingActionButton != FloatingActionMenu.this.mMenuButton) {
                            floatingActionButton.show(z);
                        }
                        Label label = (Label) floatingActionButton.getTag(R.id.fab_label);
                        if (label == null || !label.isHandleVisibilityChanges()) {
                            return;
                        }
                        label.show(z);
                    }
                }, i2);
                i2 += this.mAnimationDelayPerItem;
            }
        }
        this.mUiHandler.postDelayed(new Runnable() { // from class: com.github.clans.fab.FloatingActionMenu.5
            @Override // java.lang.Runnable
            public void run() {
                FloatingActionMenu.this.mMenuOpened = true;
                if (FloatingActionMenu.this.mToggleListener != null) {
                    FloatingActionMenu.this.mToggleListener.onMenuToggle(true);
                }
            }
        }, (i + 1) * this.mAnimationDelayPerItem);
    }

    public void close(final boolean z) {
        if (isOpened()) {
            if (isBackgroundEnabled()) {
                this.mHideBackgroundAnimator.start();
            }
            if (this.mIconAnimated) {
                AnimatorSet animatorSet = this.mIconToggleSet;
                if (animatorSet != null) {
                    animatorSet.start();
                } else {
                    this.mCloseAnimatorSet.start();
                    this.mOpenAnimatorSet.cancel();
                }
            }
            this.mIsMenuOpening = false;
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                if ((childAt instanceof FloatingActionButton) && childAt.getVisibility() != 8) {
                    i++;
                    final FloatingActionButton floatingActionButton = (FloatingActionButton) childAt;
                    this.mUiHandler.postDelayed(new Runnable() { // from class: com.github.clans.fab.FloatingActionMenu.6
                        @Override // java.lang.Runnable
                        public void run() {
                            if (FloatingActionMenu.this.isOpened()) {
                                if (floatingActionButton != FloatingActionMenu.this.mMenuButton) {
                                    floatingActionButton.hide(z);
                                }
                                Label label = (Label) floatingActionButton.getTag(R.id.fab_label);
                                if (label == null || !label.isHandleVisibilityChanges()) {
                                    return;
                                }
                                label.hide(z);
                            }
                        }
                    }, i2);
                    i2 += this.mAnimationDelayPerItem;
                }
            }
            this.mUiHandler.postDelayed(new Runnable() { // from class: com.github.clans.fab.FloatingActionMenu.7
                @Override // java.lang.Runnable
                public void run() {
                    FloatingActionMenu.this.mMenuOpened = false;
                    if (FloatingActionMenu.this.mToggleListener != null) {
                        FloatingActionMenu.this.mToggleListener.onMenuToggle(false);
                    }
                }
            }, (i + 1) * this.mAnimationDelayPerItem);
        }
    }

    public void setIconAnimationInterpolator(Interpolator interpolator) {
        this.mOpenAnimatorSet.setInterpolator(interpolator);
        this.mCloseAnimatorSet.setInterpolator(interpolator);
    }

    public void setIconAnimationOpenInterpolator(Interpolator interpolator) {
        this.mOpenAnimatorSet.setInterpolator(interpolator);
    }

    public void setIconAnimationCloseInterpolator(Interpolator interpolator) {
        this.mCloseAnimatorSet.setInterpolator(interpolator);
    }

    public void setAnimated(boolean z) {
        this.mIsAnimated = z;
        this.mOpenAnimatorSet.setDuration(z ? 300L : 0L);
        this.mCloseAnimatorSet.setDuration(z ? 300L : 0L);
    }

    public boolean isAnimated() {
        return this.mIsAnimated;
    }

    public void setAnimationDelayPerItem(int i) {
        this.mAnimationDelayPerItem = i;
    }

    public int getAnimationDelayPerItem() {
        return this.mAnimationDelayPerItem;
    }

    public void setOnMenuToggleListener(OnMenuToggleListener onMenuToggleListener) {
        this.mToggleListener = onMenuToggleListener;
    }

    public void setIconAnimated(boolean z) {
        this.mIconAnimated = z;
    }

    public boolean isIconAnimated() {
        return this.mIconAnimated;
    }

    public ImageView getMenuIconView() {
        return this.mImageToggle;
    }

    public void setIconToggleAnimatorSet(AnimatorSet animatorSet) {
        this.mIconToggleSet = animatorSet;
    }

    public AnimatorSet getIconToggleAnimatorSet() {
        return this.mIconToggleSet;
    }

    public void setMenuButtonShowAnimation(Animation animation) {
        this.mMenuButtonShowAnimation = animation;
        this.mMenuButton.setShowAnimation(animation);
    }

    public void setMenuButtonHideAnimation(Animation animation) {
        this.mMenuButtonHideAnimation = animation;
        this.mMenuButton.setHideAnimation(animation);
    }

    public boolean isMenuHidden() {
        return getVisibility() == 4;
    }

    public boolean isMenuButtonHidden() {
        return this.mMenuButton.isHidden();
    }

    public void showMenu(boolean z) {
        if (isMenuHidden()) {
            if (z) {
                startAnimation(this.mMenuButtonShowAnimation);
            }
            setVisibility(0);
        }
    }

    public void hideMenu(final boolean z) {
        if (isMenuHidden() || this.mIsMenuButtonAnimationRunning) {
            return;
        }
        this.mIsMenuButtonAnimationRunning = true;
        if (isOpened()) {
            close(z);
            this.mUiHandler.postDelayed(new Runnable() { // from class: com.github.clans.fab.FloatingActionMenu.8
                @Override // java.lang.Runnable
                public void run() {
                    if (z) {
                        FloatingActionMenu floatingActionMenu = FloatingActionMenu.this;
                        floatingActionMenu.startAnimation(floatingActionMenu.mMenuButtonHideAnimation);
                    }
                    FloatingActionMenu.this.setVisibility(4);
                    FloatingActionMenu.this.mIsMenuButtonAnimationRunning = false;
                }
            }, this.mAnimationDelayPerItem * this.mButtonsCount);
        } else {
            if (z) {
                startAnimation(this.mMenuButtonHideAnimation);
            }
            setVisibility(4);
            this.mIsMenuButtonAnimationRunning = false;
        }
    }

    public void toggleMenu(boolean z) {
        if (isMenuHidden()) {
            showMenu(z);
        } else {
            hideMenu(z);
        }
    }

    public void showMenuButton(boolean z) {
        if (isMenuButtonHidden()) {
            showMenuButtonWithImage(z);
        }
    }

    public void hideMenuButton(final boolean z) {
        if (isMenuButtonHidden() || this.mIsMenuButtonAnimationRunning) {
            return;
        }
        this.mIsMenuButtonAnimationRunning = true;
        if (isOpened()) {
            close(z);
            this.mUiHandler.postDelayed(new Runnable() { // from class: com.github.clans.fab.FloatingActionMenu.9
                @Override // java.lang.Runnable
                public void run() {
                    FloatingActionMenu.this.hideMenuButtonWithImage(z);
                }
            }, this.mAnimationDelayPerItem * this.mButtonsCount);
        } else {
            hideMenuButtonWithImage(z);
        }
    }

    public void toggleMenuButton(boolean z) {
        if (isMenuButtonHidden()) {
            showMenuButton(z);
        } else {
            hideMenuButton(z);
        }
    }

    public void setClosedOnTouchOutside(boolean z) {
        this.mIsSetClosedOnTouchOutside = z;
    }

    public void setMenuButtonColorNormal(int i) {
        this.mMenuColorNormal = i;
        this.mMenuButton.setColorNormal(i);
    }

    public void setMenuButtonColorNormalResId(int i) {
        this.mMenuColorNormal = getResources().getColor(i);
        this.mMenuButton.setColorNormalResId(i);
    }

    public int getMenuButtonColorNormal() {
        return this.mMenuColorNormal;
    }

    public void setMenuButtonColorPressed(int i) {
        this.mMenuColorPressed = i;
        this.mMenuButton.setColorPressed(i);
    }

    public void setMenuButtonColorPressedResId(int i) {
        this.mMenuColorPressed = getResources().getColor(i);
        this.mMenuButton.setColorPressedResId(i);
    }

    public int getMenuButtonColorPressed() {
        return this.mMenuColorPressed;
    }

    public void setMenuButtonColorRipple(int i) {
        this.mMenuColorRipple = i;
        this.mMenuButton.setColorRipple(i);
    }

    public void setMenuButtonColorRippleResId(int i) {
        this.mMenuColorRipple = getResources().getColor(i);
        this.mMenuButton.setColorRippleResId(i);
    }

    public int getMenuButtonColorRipple() {
        return this.mMenuColorRipple;
    }

    public void addMenuButton(FloatingActionButton floatingActionButton) {
        addView(floatingActionButton, this.mButtonsCount - 2);
        this.mButtonsCount++;
        addLabel(floatingActionButton);
    }

    public void removeMenuButton(FloatingActionButton floatingActionButton) {
        removeView(floatingActionButton.getLabelView());
        removeView(floatingActionButton);
        this.mButtonsCount--;
    }

    public void addMenuButton(FloatingActionButton floatingActionButton, int i) {
        int i2 = this.mButtonsCount - 2;
        if (i < 0) {
            i = 0;
        } else if (i > i2) {
            i = i2;
        }
        addView(floatingActionButton, i);
        this.mButtonsCount++;
        addLabel(floatingActionButton);
    }

    public void removeAllMenuButtons() {
        close(true);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt != this.mMenuButton && childAt != this.mImageToggle && (childAt instanceof FloatingActionButton)) {
                arrayList.add((FloatingActionButton) childAt);
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            removeMenuButton((FloatingActionButton) it2.next());
        }
    }

    public void setMenuButtonLabelText(String str) {
        this.mMenuButton.setLabelText(str);
    }

    public String getMenuButtonLabelText() {
        return this.mMenuLabelText;
    }

    public void setOnMenuButtonClickListener(View.OnClickListener onClickListener) {
        this.mMenuButton.setOnClickListener(onClickListener);
    }

    public void setOnMenuButtonLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mMenuButton.setOnLongClickListener(onLongClickListener);
    }
}
