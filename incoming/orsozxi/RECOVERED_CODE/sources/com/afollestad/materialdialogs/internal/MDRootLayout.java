package com.afollestad.materialdialogs.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ScrollView;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.R;
import com.afollestad.materialdialogs.StackingBehavior;
import com.afollestad.materialdialogs.util.DialogUtils;

/* loaded from: classes.dex */
public class MDRootLayout extends ViewGroup {
    private static final int INDEX_NEGATIVE = 1;
    private static final int INDEX_NEUTRAL = 0;
    private static final int INDEX_POSITIVE = 2;
    private ViewTreeObserver.OnScrollChangedListener bottomOnScrollChangedListener;
    private int buttonBarHeight;
    private GravityEnum buttonGravity;
    private int buttonHorizontalEdgeMargin;
    private int buttonPaddingFull;
    private final MDButton[] buttons;
    private View content;
    private Paint dividerPaint;
    private int dividerWidth;
    private boolean drawBottomDivider;
    private boolean drawTopDivider;
    private boolean isStacked;
    private int maxHeight;
    private boolean noTitleNoPadding;
    private int noTitlePaddingFull;
    private boolean reducePaddingNoTitleNoButtons;
    private StackingBehavior stackBehavior;
    private View titleBar;
    private ViewTreeObserver.OnScrollChangedListener topOnScrollChangedListener;
    private boolean useFullPadding;

    public MDRootLayout(Context context) {
        super(context);
        this.buttons = new MDButton[3];
        this.drawTopDivider = false;
        this.drawBottomDivider = false;
        this.stackBehavior = StackingBehavior.ADAPTIVE;
        this.isStacked = false;
        this.useFullPadding = true;
        this.buttonGravity = GravityEnum.START;
        init(context, null, 0);
    }

    public MDRootLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.buttons = new MDButton[3];
        this.drawTopDivider = false;
        this.drawBottomDivider = false;
        this.stackBehavior = StackingBehavior.ADAPTIVE;
        this.isStacked = false;
        this.useFullPadding = true;
        this.buttonGravity = GravityEnum.START;
        init(context, attributeSet, 0);
    }

    public MDRootLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.buttons = new MDButton[3];
        this.drawTopDivider = false;
        this.drawBottomDivider = false;
        this.stackBehavior = StackingBehavior.ADAPTIVE;
        this.isStacked = false;
        this.useFullPadding = true;
        this.buttonGravity = GravityEnum.START;
        init(context, attributeSet, i);
    }

    public MDRootLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.buttons = new MDButton[3];
        this.drawTopDivider = false;
        this.drawBottomDivider = false;
        this.stackBehavior = StackingBehavior.ADAPTIVE;
        this.isStacked = false;
        this.useFullPadding = true;
        this.buttonGravity = GravityEnum.START;
        init(context, attributeSet, i);
    }

    private static boolean isVisible(View view) {
        boolean z = (view == null || view.getVisibility() == 8) ? false : true;
        if (z && (view instanceof MDButton)) {
            return ((MDButton) view).getText().toString().trim().length() > 0;
        }
        return z;
    }

    public static boolean canRecyclerViewScroll(RecyclerView recyclerView) {
        return (recyclerView == null || recyclerView.getLayoutManager() == null || !recyclerView.getLayoutManager().canScrollVertically()) ? false : true;
    }

    private static boolean canScrollViewScroll(ScrollView scrollView) {
        if (scrollView.getChildCount() == 0) {
            return false;
        }
        return (scrollView.getMeasuredHeight() - scrollView.getPaddingTop()) - scrollView.getPaddingBottom() < scrollView.getChildAt(0).getMeasuredHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean canWebViewScroll(WebView webView) {
        return ((float) webView.getMeasuredHeight()) < ((float) webView.getContentHeight()) * webView.getScale();
    }

    private static boolean canAdapterViewScroll(AdapterView adapterView) {
        if (adapterView.getLastVisiblePosition() == -1) {
            return false;
        }
        return !(adapterView.getFirstVisiblePosition() == 0) || !(adapterView.getLastVisiblePosition() == adapterView.getCount() - 1) || adapterView.getChildCount() <= 0 || adapterView.getChildAt(0).getTop() < adapterView.getPaddingTop() || adapterView.getChildAt(adapterView.getChildCount() - 1).getBottom() > adapterView.getHeight() - adapterView.getPaddingBottom();
    }

    private static View getBottomView(ViewGroup viewGroup) {
        if (viewGroup == null || viewGroup.getChildCount() == 0) {
            return null;
        }
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount);
            if (childAt.getVisibility() == 0 && childAt.getBottom() == viewGroup.getMeasuredHeight()) {
                return childAt;
            }
        }
        return null;
    }

    private static View getTopView(ViewGroup viewGroup) {
        if (viewGroup == null || viewGroup.getChildCount() == 0) {
            return null;
        }
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount);
            if (childAt.getVisibility() == 0 && childAt.getTop() == 0) {
                return childAt;
            }
        }
        return null;
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        Resources resources = context.getResources();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MDRootLayout, i, 0);
        this.reducePaddingNoTitleNoButtons = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MDRootLayout_md_reduce_padding_no_title_no_buttons, true);
        typedArrayObtainStyledAttributes.recycle();
        this.noTitlePaddingFull = resources.getDimensionPixelSize(R.dimen.md_notitle_vertical_padding);
        this.buttonPaddingFull = resources.getDimensionPixelSize(R.dimen.md_button_frame_vertical_padding);
        this.buttonHorizontalEdgeMargin = resources.getDimensionPixelSize(R.dimen.md_button_padding_frame_side);
        this.buttonBarHeight = resources.getDimensionPixelSize(R.dimen.md_button_height);
        this.dividerPaint = new Paint();
        this.dividerWidth = resources.getDimensionPixelSize(R.dimen.md_divider_height);
        this.dividerPaint.setColor(DialogUtils.resolveColor(context, R.attr.md_divider_color));
        setWillNotDraw(false);
    }

    public void setMaxHeight(int i) {
        this.maxHeight = i;
    }

    public void noTitleNoPadding() {
        this.noTitleNoPadding = true;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getId() == R.id.md_titleFrame) {
                this.titleBar = childAt;
            } else if (childAt.getId() == R.id.md_buttonDefaultNeutral) {
                this.buttons[0] = (MDButton) childAt;
            } else if (childAt.getId() == R.id.md_buttonDefaultNegative) {
                this.buttons[1] = (MDButton) childAt;
            } else if (childAt.getId() == R.id.md_buttonDefaultPositive) {
                this.buttons[2] = (MDButton) childAt;
            } else {
                this.content = childAt;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0112  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onMeasure(int r12, int r13) {
        /*
            Method dump skipped, instructions count: 280
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.afollestad.materialdialogs.internal.MDRootLayout.onMeasure(int, int):void");
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        View view = this.content;
        if (view != null) {
            if (this.drawTopDivider) {
                canvas.drawRect(0.0f, r0 - this.dividerWidth, getMeasuredWidth(), view.getTop(), this.dividerPaint);
            }
            if (this.drawBottomDivider) {
                canvas.drawRect(0.0f, this.content.getBottom(), getMeasuredWidth(), r0 + this.dividerWidth, this.dividerPaint);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int measuredWidth;
        int measuredWidth2;
        int measuredWidth3;
        int measuredWidth4;
        int measuredWidth5;
        int measuredWidth6;
        int measuredWidth7;
        int measuredWidth8;
        if (isVisible(this.titleBar)) {
            int measuredHeight = this.titleBar.getMeasuredHeight() + i2;
            this.titleBar.layout(i, i2, i3, measuredHeight);
            i2 = measuredHeight;
        } else if (!this.noTitleNoPadding && this.useFullPadding) {
            i2 += this.noTitlePaddingFull;
        }
        if (isVisible(this.content)) {
            View view = this.content;
            view.layout(i, i2, i3, view.getMeasuredHeight() + i2);
        }
        if (this.isStacked) {
            int measuredHeight2 = i4 - this.buttonPaddingFull;
            for (MDButton mDButton : this.buttons) {
                if (isVisible(mDButton)) {
                    mDButton.layout(i, measuredHeight2 - mDButton.getMeasuredHeight(), i3, measuredHeight2);
                    measuredHeight2 -= mDButton.getMeasuredHeight();
                }
            }
        } else {
            if (this.useFullPadding) {
                i4 -= this.buttonPaddingFull;
            }
            int i6 = i4 - this.buttonBarHeight;
            int measuredWidth9 = this.buttonHorizontalEdgeMargin;
            if (isVisible(this.buttons[2])) {
                if (this.buttonGravity == GravityEnum.END) {
                    measuredWidth7 = i + measuredWidth9;
                    measuredWidth8 = this.buttons[2].getMeasuredWidth() + measuredWidth7;
                    i5 = -1;
                } else {
                    int i7 = i3 - measuredWidth9;
                    measuredWidth7 = i7 - this.buttons[2].getMeasuredWidth();
                    measuredWidth8 = i7;
                    i5 = measuredWidth7;
                }
                this.buttons[2].layout(measuredWidth7, i6, measuredWidth8, i4);
                measuredWidth9 += this.buttons[2].getMeasuredWidth();
            } else {
                i5 = -1;
            }
            if (isVisible(this.buttons[1])) {
                if (this.buttonGravity == GravityEnum.END) {
                    measuredWidth5 = measuredWidth9 + i;
                    measuredWidth6 = this.buttons[1].getMeasuredWidth() + measuredWidth5;
                } else if (this.buttonGravity == GravityEnum.START) {
                    measuredWidth6 = i3 - measuredWidth9;
                    measuredWidth5 = measuredWidth6 - this.buttons[1].getMeasuredWidth();
                } else {
                    measuredWidth5 = this.buttonHorizontalEdgeMargin + i;
                    measuredWidth6 = this.buttons[1].getMeasuredWidth() + measuredWidth5;
                    measuredWidth = measuredWidth6;
                    this.buttons[1].layout(measuredWidth5, i6, measuredWidth6, i4);
                }
                measuredWidth = -1;
                this.buttons[1].layout(measuredWidth5, i6, measuredWidth6, i4);
            } else {
                measuredWidth = -1;
            }
            if (isVisible(this.buttons[0])) {
                if (this.buttonGravity == GravityEnum.END) {
                    measuredWidth3 = i3 - this.buttonHorizontalEdgeMargin;
                    measuredWidth4 = measuredWidth3 - this.buttons[0].getMeasuredWidth();
                } else if (this.buttonGravity == GravityEnum.START) {
                    measuredWidth4 = i + this.buttonHorizontalEdgeMargin;
                    measuredWidth3 = this.buttons[0].getMeasuredWidth() + measuredWidth4;
                } else {
                    if (measuredWidth != -1 || i5 == -1) {
                        if (i5 == -1 && measuredWidth != -1) {
                            measuredWidth2 = this.buttons[0].getMeasuredWidth();
                        } else if (i5 == -1) {
                            measuredWidth = ((i3 - i) / 2) - (this.buttons[0].getMeasuredWidth() / 2);
                            measuredWidth2 = this.buttons[0].getMeasuredWidth();
                        }
                        i5 = measuredWidth + measuredWidth2;
                    } else {
                        measuredWidth = i5 - this.buttons[0].getMeasuredWidth();
                    }
                    measuredWidth3 = i5;
                    measuredWidth4 = measuredWidth;
                }
                this.buttons[0].layout(measuredWidth4, i6, measuredWidth3, i4);
            }
        }
        setUpDividersVisibility(this.content, true, true);
    }

    public void setStackingBehavior(StackingBehavior stackingBehavior) {
        this.stackBehavior = stackingBehavior;
        invalidate();
    }

    public void setDividerColor(int i) {
        this.dividerPaint.setColor(i);
        invalidate();
    }

    public void setButtonGravity(GravityEnum gravityEnum) {
        this.buttonGravity = gravityEnum;
        invertGravityIfNecessary();
    }

    private void invertGravityIfNecessary() {
        if (Build.VERSION.SDK_INT >= 17 && getResources().getConfiguration().getLayoutDirection() == 1) {
            int i = AnonymousClass4.$SwitchMap$com$afollestad$materialdialogs$GravityEnum[this.buttonGravity.ordinal()];
            if (i == 1) {
                this.buttonGravity = GravityEnum.END;
            } else {
                if (i != 2) {
                    return;
                }
                this.buttonGravity = GravityEnum.START;
            }
        }
    }

    /* renamed from: com.afollestad.materialdialogs.internal.MDRootLayout$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$afollestad$materialdialogs$GravityEnum;

        static {
            int[] iArr = new int[GravityEnum.values().length];
            $SwitchMap$com$afollestad$materialdialogs$GravityEnum = iArr;
            try {
                iArr[GravityEnum.START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$afollestad$materialdialogs$GravityEnum[GravityEnum.END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public void setButtonStackedGravity(GravityEnum gravityEnum) {
        for (MDButton mDButton : this.buttons) {
            if (mDButton != null) {
                mDButton.setStackedGravity(gravityEnum);
            }
        }
    }

    private void setUpDividersVisibility(final View view, final boolean z, final boolean z2) {
        if (view == null) {
            return;
        }
        if (view instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) view;
            if (canScrollViewScroll(scrollView)) {
                addScrollListener(scrollView, z, z2);
                return;
            }
            if (z) {
                this.drawTopDivider = false;
            }
            if (z2) {
                this.drawBottomDivider = false;
                return;
            }
            return;
        }
        if (view instanceof AdapterView) {
            AdapterView adapterView = (AdapterView) view;
            if (canAdapterViewScroll(adapterView)) {
                addScrollListener(adapterView, z, z2);
                return;
            }
            if (z) {
                this.drawTopDivider = false;
            }
            if (z2) {
                this.drawBottomDivider = false;
                return;
            }
            return;
        }
        if (view instanceof WebView) {
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.afollestad.materialdialogs.internal.MDRootLayout.1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    if (view.getMeasuredHeight() == 0) {
                        return true;
                    }
                    if (MDRootLayout.canWebViewScroll((WebView) view)) {
                        MDRootLayout.this.addScrollListener((ViewGroup) view, z, z2);
                    } else {
                        if (z) {
                            MDRootLayout.this.drawTopDivider = false;
                        }
                        if (z2) {
                            MDRootLayout.this.drawBottomDivider = false;
                        }
                    }
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
            return;
        }
        if (view instanceof RecyclerView) {
            boolean zCanRecyclerViewScroll = canRecyclerViewScroll((RecyclerView) view);
            if (z) {
                this.drawTopDivider = zCanRecyclerViewScroll;
            }
            if (z2) {
                this.drawBottomDivider = zCanRecyclerViewScroll;
            }
            if (zCanRecyclerViewScroll) {
                addScrollListener((ViewGroup) view, z, z2);
                return;
            }
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            View topView = getTopView(viewGroup);
            setUpDividersVisibility(topView, z, z2);
            View bottomView = getBottomView(viewGroup);
            if (bottomView != topView) {
                setUpDividersVisibility(bottomView, false, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addScrollListener(final ViewGroup viewGroup, final boolean z, final boolean z2) {
        if ((z2 || this.topOnScrollChangedListener != null) && !(z2 && this.bottomOnScrollChangedListener == null)) {
            return;
        }
        if (viewGroup instanceof RecyclerView) {
            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() { // from class: com.afollestad.materialdialogs.internal.MDRootLayout.2
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                    super.onScrolled(recyclerView, i, i2);
                    MDButton[] mDButtonArr = MDRootLayout.this.buttons;
                    int length = mDButtonArr.length;
                    boolean z3 = false;
                    int i3 = 0;
                    while (true) {
                        if (i3 < length) {
                            MDButton mDButton = mDButtonArr[i3];
                            if (mDButton != null && mDButton.getVisibility() != 8) {
                                z3 = true;
                                break;
                            }
                            i3++;
                        } else {
                            break;
                        }
                    }
                    MDRootLayout.this.invalidateDividersForScrollingView(viewGroup, z, z2, z3);
                    MDRootLayout.this.invalidate();
                }
            };
            RecyclerView recyclerView = (RecyclerView) viewGroup;
            recyclerView.addOnScrollListener(onScrollListener);
            onScrollListener.onScrolled(recyclerView, 0, 0);
            return;
        }
        ViewTreeObserver.OnScrollChangedListener onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.afollestad.materialdialogs.internal.MDRootLayout.3
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public void onScrollChanged() {
                MDButton[] mDButtonArr = MDRootLayout.this.buttons;
                int length = mDButtonArr.length;
                boolean z3 = false;
                int i = 0;
                while (true) {
                    if (i < length) {
                        MDButton mDButton = mDButtonArr[i];
                        if (mDButton != null && mDButton.getVisibility() != 8) {
                            z3 = true;
                            break;
                        }
                        i++;
                    } else {
                        break;
                    }
                }
                ViewGroup viewGroup2 = viewGroup;
                if (viewGroup2 instanceof WebView) {
                    MDRootLayout.this.invalidateDividersForWebView((WebView) viewGroup2, z, z2, z3);
                } else {
                    MDRootLayout.this.invalidateDividersForScrollingView(viewGroup2, z, z2, z3);
                }
                MDRootLayout.this.invalidate();
            }
        };
        if (!z2) {
            this.topOnScrollChangedListener = onScrollChangedListener;
            viewGroup.getViewTreeObserver().addOnScrollChangedListener(this.topOnScrollChangedListener);
        } else {
            this.bottomOnScrollChangedListener = onScrollChangedListener;
            viewGroup.getViewTreeObserver().addOnScrollChangedListener(this.bottomOnScrollChangedListener);
        }
        onScrollChangedListener.onScrollChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateDividersForScrollingView(ViewGroup viewGroup, boolean z, boolean z2, boolean z3) {
        if (z && viewGroup.getChildCount() > 0) {
            View view = this.titleBar;
            this.drawTopDivider = (view == null || view.getVisibility() == 8 || viewGroup.getScrollY() + viewGroup.getPaddingTop() <= viewGroup.getChildAt(0).getTop()) ? false : true;
        }
        if (!z2 || viewGroup.getChildCount() <= 0) {
            return;
        }
        this.drawBottomDivider = z3 && (viewGroup.getScrollY() + viewGroup.getHeight()) - viewGroup.getPaddingBottom() < viewGroup.getChildAt(viewGroup.getChildCount() - 1).getBottom();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateDividersForWebView(WebView webView, boolean z, boolean z2, boolean z3) {
        if (z) {
            View view = this.titleBar;
            this.drawTopDivider = (view == null || view.getVisibility() == 8 || webView.getScrollY() + webView.getPaddingTop() <= 0) ? false : true;
        }
        if (z2) {
            this.drawBottomDivider = z3 && ((float) ((webView.getScrollY() + webView.getMeasuredHeight()) - webView.getPaddingBottom())) < ((float) webView.getContentHeight()) * webView.getScale();
        }
    }
}
