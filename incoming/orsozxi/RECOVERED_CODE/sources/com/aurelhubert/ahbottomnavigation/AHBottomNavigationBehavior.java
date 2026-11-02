package com.aurelhubert.ahbottomnavigation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

/* loaded from: classes.dex */
public class AHBottomNavigationBehavior<V extends View> extends VerticalScrollingBehavior<V> {
    private static final int ANIM_DURATION = 300;
    private static final Interpolator INTERPOLATOR = new LinearOutSlowInInterpolator();
    private boolean behaviorTranslationEnabled;
    private boolean fabBottomMarginInitialized;
    private float fabDefaultBottomMargin;
    private float fabTargetOffset;
    private FloatingActionButton floatingActionButton;
    private boolean hidden;
    private int mSnackbarHeight;
    private TabLayout mTabLayout;
    private int mTabLayoutId;
    private AHBottomNavigation.OnNavigationPositionListener navigationPositionListener;
    private float snackBarY;
    private Snackbar.SnackbarLayout snackbarLayout;
    private float targetOffset;
    private ViewPropertyAnimatorCompat translationAnimator;
    private ObjectAnimator translationObjectAnimator;

    @Override // com.aurelhubert.ahbottomnavigation.VerticalScrollingBehavior
    public void onDirectionNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr, int i3) {
    }

    @Override // com.aurelhubert.ahbottomnavigation.VerticalScrollingBehavior
    protected boolean onNestedDirectionFling(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2, int i) {
        return false;
    }

    @Override // com.aurelhubert.ahbottomnavigation.VerticalScrollingBehavior
    public void onNestedVerticalOverScroll(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3) {
    }

    public AHBottomNavigationBehavior() {
        this.hidden = false;
        this.mSnackbarHeight = -1;
        this.fabBottomMarginInitialized = false;
        this.targetOffset = 0.0f;
        this.fabTargetOffset = 0.0f;
        this.fabDefaultBottomMargin = 0.0f;
        this.snackBarY = 0.0f;
        this.behaviorTranslationEnabled = true;
    }

    public AHBottomNavigationBehavior(boolean z) {
        this.hidden = false;
        this.mSnackbarHeight = -1;
        this.fabBottomMarginInitialized = false;
        this.targetOffset = 0.0f;
        this.fabTargetOffset = 0.0f;
        this.fabDefaultBottomMargin = 0.0f;
        this.snackBarY = 0.0f;
        this.behaviorTranslationEnabled = true;
        this.behaviorTranslationEnabled = z;
    }

    public AHBottomNavigationBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.hidden = false;
        this.mSnackbarHeight = -1;
        this.fabBottomMarginInitialized = false;
        this.targetOffset = 0.0f;
        this.fabTargetOffset = 0.0f;
        this.fabDefaultBottomMargin = 0.0f;
        this.snackBarY = 0.0f;
        this.behaviorTranslationEnabled = true;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AHBottomNavigationBehavior_Params);
        this.mTabLayoutId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AHBottomNavigationBehavior_Params_tabLayoutId, -1);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
        boolean zOnLayoutChild = super.onLayoutChild(coordinatorLayout, v, i);
        if (this.mTabLayout == null && this.mTabLayoutId != -1) {
            this.mTabLayout = findTabLayout(v);
        }
        return zOnLayoutChild;
    }

    private TabLayout findTabLayout(View view) {
        int i = this.mTabLayoutId;
        if (i == 0) {
            return null;
        }
        return (TabLayout) view.findViewById(i);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, V v, View view) {
        return super.onDependentViewChanged(coordinatorLayout, v, view);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, V v, View view) {
        super.onDependentViewRemoved(coordinatorLayout, v, view);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, V v, View view) {
        updateSnackbar(v, view);
        updateFloatingActionButton(view);
        return super.layoutDependsOn(coordinatorLayout, v, view);
    }

    @Override // com.aurelhubert.ahbottomnavigation.VerticalScrollingBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int i3, int i4) {
        super.onNestedScroll(coordinatorLayout, v, view, i, i2, i3, i4);
        if (i2 < 0) {
            handleDirection(v, -1);
        } else if (i2 > 0) {
            handleDirection(v, 1);
        }
    }

    @Override // com.aurelhubert.ahbottomnavigation.VerticalScrollingBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i) {
        return i == 2 || super.onStartNestedScroll(coordinatorLayout, v, view, view2, i);
    }

    private void handleDirection(V v, int i) {
        if (this.behaviorTranslationEnabled) {
            if (i == -1 && this.hidden) {
                this.hidden = false;
                animateOffset(v, 0, false, true);
            } else {
                if (i != 1 || this.hidden) {
                    return;
                }
                this.hidden = true;
                animateOffset(v, v.getHeight(), false, true);
            }
        }
    }

    private void animateOffset(V v, int i, boolean z, boolean z2) {
        if (this.behaviorTranslationEnabled || z) {
            if (Build.VERSION.SDK_INT < 19) {
                ensureOrCancelObjectAnimation(v, i, z2);
                this.translationObjectAnimator.start();
            } else {
                ensureOrCancelAnimator(v, z2);
                this.translationAnimator.translationY(i).start();
            }
        }
    }

    private void ensureOrCancelAnimator(V v, boolean z) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.translationAnimator;
        if (viewPropertyAnimatorCompat == null) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompatAnimate = ViewCompat.animate(v);
            this.translationAnimator = viewPropertyAnimatorCompatAnimate;
            viewPropertyAnimatorCompatAnimate.setDuration(z ? 300L : 0L);
            this.translationAnimator.setUpdateListener(new ViewPropertyAnimatorUpdateListener() { // from class: com.aurelhubert.ahbottomnavigation.AHBottomNavigationBehavior.1
                @Override // androidx.core.view.ViewPropertyAnimatorUpdateListener
                public void onAnimationUpdate(View view) {
                    if (AHBottomNavigationBehavior.this.snackbarLayout != null && (AHBottomNavigationBehavior.this.snackbarLayout.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
                        AHBottomNavigationBehavior.this.targetOffset = view.getMeasuredHeight() - view.getTranslationY();
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) AHBottomNavigationBehavior.this.snackbarLayout.getLayoutParams();
                        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, (int) AHBottomNavigationBehavior.this.targetOffset);
                        AHBottomNavigationBehavior.this.snackbarLayout.requestLayout();
                    }
                    if (AHBottomNavigationBehavior.this.floatingActionButton != null && (AHBottomNavigationBehavior.this.floatingActionButton.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
                        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) AHBottomNavigationBehavior.this.floatingActionButton.getLayoutParams();
                        AHBottomNavigationBehavior aHBottomNavigationBehavior = AHBottomNavigationBehavior.this;
                        aHBottomNavigationBehavior.fabTargetOffset = (aHBottomNavigationBehavior.fabDefaultBottomMargin - view.getTranslationY()) + AHBottomNavigationBehavior.this.snackBarY;
                        marginLayoutParams2.setMargins(marginLayoutParams2.leftMargin, marginLayoutParams2.topMargin, marginLayoutParams2.rightMargin, (int) AHBottomNavigationBehavior.this.fabTargetOffset);
                        AHBottomNavigationBehavior.this.floatingActionButton.requestLayout();
                    }
                    if (AHBottomNavigationBehavior.this.navigationPositionListener != null) {
                        AHBottomNavigationBehavior.this.navigationPositionListener.onPositionChange((int) ((view.getMeasuredHeight() - view.getTranslationY()) + AHBottomNavigationBehavior.this.snackBarY));
                    }
                }
            });
            this.translationAnimator.setInterpolator(INTERPOLATOR);
            return;
        }
        viewPropertyAnimatorCompat.setDuration(z ? 300L : 0L);
        this.translationAnimator.cancel();
    }

    private void ensureOrCancelObjectAnimation(final V v, int i, boolean z) {
        ObjectAnimator objectAnimator = this.translationObjectAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(v, (Property<V, Float>) View.TRANSLATION_Y, i);
        this.translationObjectAnimator = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(z ? 300L : 0L);
        this.translationObjectAnimator.setInterpolator(INTERPOLATOR);
        this.translationObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.aurelhubert.ahbottomnavigation.AHBottomNavigationBehavior.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (AHBottomNavigationBehavior.this.snackbarLayout != null && (AHBottomNavigationBehavior.this.snackbarLayout.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
                    AHBottomNavigationBehavior.this.targetOffset = v.getMeasuredHeight() - v.getTranslationY();
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) AHBottomNavigationBehavior.this.snackbarLayout.getLayoutParams();
                    marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, (int) AHBottomNavigationBehavior.this.targetOffset);
                    AHBottomNavigationBehavior.this.snackbarLayout.requestLayout();
                }
                if (AHBottomNavigationBehavior.this.floatingActionButton != null && (AHBottomNavigationBehavior.this.floatingActionButton.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) AHBottomNavigationBehavior.this.floatingActionButton.getLayoutParams();
                    AHBottomNavigationBehavior aHBottomNavigationBehavior = AHBottomNavigationBehavior.this;
                    aHBottomNavigationBehavior.fabTargetOffset = (aHBottomNavigationBehavior.fabDefaultBottomMargin - v.getTranslationY()) + AHBottomNavigationBehavior.this.snackBarY;
                    marginLayoutParams2.setMargins(marginLayoutParams2.leftMargin, marginLayoutParams2.topMargin, marginLayoutParams2.rightMargin, (int) AHBottomNavigationBehavior.this.fabTargetOffset);
                    AHBottomNavigationBehavior.this.floatingActionButton.requestLayout();
                }
                if (AHBottomNavigationBehavior.this.navigationPositionListener != null) {
                    AHBottomNavigationBehavior.this.navigationPositionListener.onPositionChange((int) ((v.getMeasuredHeight() - v.getTranslationY()) + AHBottomNavigationBehavior.this.snackBarY));
                }
            }
        });
    }

    public static <V extends View> AHBottomNavigationBehavior<V> from(V v) {
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (!(behavior instanceof AHBottomNavigationBehavior)) {
            throw new IllegalArgumentException("The view is not associated with AHBottomNavigationBehavior");
        }
        return (AHBottomNavigationBehavior) behavior;
    }

    public void setTabLayoutId(int i) {
        this.mTabLayoutId = i;
    }

    public void setBehaviorTranslationEnabled(boolean z) {
        this.behaviorTranslationEnabled = z;
    }

    public void setOnNavigationPositionListener(AHBottomNavigation.OnNavigationPositionListener onNavigationPositionListener) {
        this.navigationPositionListener = onNavigationPositionListener;
    }

    public void removeOnNavigationPositionListener() {
        this.navigationPositionListener = null;
    }

    public void hideView(V v, int i, boolean z) {
        if (this.hidden) {
            return;
        }
        this.hidden = true;
        animateOffset(v, i, true, z);
    }

    public void resetOffset(V v, boolean z) {
        if (this.hidden) {
            this.hidden = false;
            animateOffset(v, 0, true, z);
        }
    }

    public void updateSnackbar(final View view, View view2) {
        if (view2 == null || !(view2 instanceof Snackbar.SnackbarLayout)) {
            return;
        }
        this.snackbarLayout = (Snackbar.SnackbarLayout) view2;
        if (Build.VERSION.SDK_INT >= 19) {
            this.snackbarLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.aurelhubert.ahbottomnavigation.AHBottomNavigationBehavior.3
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view3, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    if (AHBottomNavigationBehavior.this.floatingActionButton != null && (AHBottomNavigationBehavior.this.floatingActionButton.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) AHBottomNavigationBehavior.this.floatingActionButton.getLayoutParams();
                        AHBottomNavigationBehavior.this.snackBarY = i4 - view3.getY();
                        AHBottomNavigationBehavior aHBottomNavigationBehavior = AHBottomNavigationBehavior.this;
                        aHBottomNavigationBehavior.fabTargetOffset = (aHBottomNavigationBehavior.fabDefaultBottomMargin - view.getTranslationY()) + AHBottomNavigationBehavior.this.snackBarY;
                        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, (int) AHBottomNavigationBehavior.this.fabTargetOffset);
                        AHBottomNavigationBehavior.this.floatingActionButton.requestLayout();
                    }
                    if (AHBottomNavigationBehavior.this.navigationPositionListener != null) {
                        AHBottomNavigationBehavior.this.navigationPositionListener.onPositionChange((int) ((view.getMeasuredHeight() - view.getTranslationY()) + AHBottomNavigationBehavior.this.snackBarY));
                    }
                }
            });
        }
        if (this.mSnackbarHeight == -1) {
            this.mSnackbarHeight = view2.getHeight();
        }
        int measuredHeight = (int) (view.getMeasuredHeight() - view.getTranslationY());
        if (Build.VERSION.SDK_INT < 21) {
            view.bringToFront();
        }
        if (view2.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, measuredHeight);
            view2.requestLayout();
        }
    }

    public void updateFloatingActionButton(View view) {
        if (view == null || !(view instanceof FloatingActionButton)) {
            return;
        }
        this.floatingActionButton = (FloatingActionButton) view;
        if (this.fabBottomMarginInitialized || !(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            return;
        }
        this.fabBottomMarginInitialized = true;
        this.fabDefaultBottomMargin = ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin;
    }
}
