package com.navdrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

/* loaded from: classes2.dex */
public class SimpleSideDrawer extends FrameLayout {
    private final ViewGroup mAboveView;
    private final BehindLinearLayout mBehindView;
    private int mDurationLeft;
    private int mDurationRight;
    private final LinearLayout mLeftBehindBase;
    private View mLeftBehindView;
    private int mLeftBehindViewWidth;
    private DragAction mLeftDragAction;
    private Rect mLeftPaddingRect;
    private final View mOverlay;
    private final LinearLayout mRightBehindBase;
    private View mRightBehindView;
    private int mRightBehindViewWidth;
    private DragAction mRightDragAction;
    private Rect mRightPaddingRect;
    private Scroller mScroller;
    private final Window mWindow;

    private abstract class DragAction {
        private boolean mDraggable;
        private float mLastMotionX;
        private boolean mOpening;

        public abstract boolean onTouchEvent(MotionEvent motionEvent);

        private DragAction() {
            this.mLastMotionX = 0.0f;
            this.mOpening = false;
            this.mDraggable = false;
        }

        /* synthetic */ DragAction(SimpleSideDrawer simpleSideDrawer, DragAction dragAction) {
            this();
        }
    }

    public SimpleSideDrawer(Activity activity) {
        this(activity, new AccelerateDecelerateInterpolator(), 180);
    }

    public SimpleSideDrawer(Activity activity, Interpolator interpolator, int i) {
        super(activity.getApplicationContext());
        this.mLeftDragAction = new DragAction() { // from class: com.navdrawer.SimpleSideDrawer.1
            @Override // com.navdrawer.SimpleSideDrawer.DragAction
            public boolean onTouchEvent(MotionEvent motionEvent) {
                int action = motionEvent.getAction() & 255;
                if (action == 0) {
                    SimpleSideDrawer.this.mLeftDragAction.mLastMotionX = motionEvent.getX();
                    SimpleSideDrawer.this.mLeftDragAction.mDraggable = SimpleSideDrawer.this.mAboveView.getScrollX() != 0;
                } else if (action != 1) {
                    if (action != 2 || !SimpleSideDrawer.this.mLeftDragAction.mDraggable) {
                        return false;
                    }
                    float x = motionEvent.getX();
                    float f = -(x - SimpleSideDrawer.this.mLeftDragAction.mLastMotionX);
                    int scrollX = SimpleSideDrawer.this.mAboveView.getScrollX();
                    SimpleSideDrawer.this.mLeftDragAction.mOpening = SimpleSideDrawer.this.mLeftDragAction.mLastMotionX < x;
                    SimpleSideDrawer.this.mLeftDragAction.mLastMotionX = x;
                    float f2 = scrollX + f;
                    if (0.0f < f2) {
                        SimpleSideDrawer.this.mAboveView.scrollTo(0, 0);
                    } else if (f2 >= (-SimpleSideDrawer.this.mLeftBehindViewWidth)) {
                        SimpleSideDrawer.this.mAboveView.scrollBy((int) f, 0);
                    } else {
                        SimpleSideDrawer.this.mAboveView.scrollTo(-SimpleSideDrawer.this.mLeftBehindViewWidth, 0);
                    }
                } else if (SimpleSideDrawer.this.mLeftDragAction.mDraggable) {
                    int scrollX2 = SimpleSideDrawer.this.mAboveView.getScrollX();
                    SimpleSideDrawer.this.mScroller.startScroll(scrollX2, 0, SimpleSideDrawer.this.mLeftDragAction.mOpening ? -(SimpleSideDrawer.this.mLeftBehindViewWidth + scrollX2) : -scrollX2, 0, SimpleSideDrawer.this.mDurationLeft);
                    SimpleSideDrawer.this.invalidate();
                }
                return false;
            }
        };
        this.mRightDragAction = new DragAction() { // from class: com.navdrawer.SimpleSideDrawer.2
            @Override // com.navdrawer.SimpleSideDrawer.DragAction
            public boolean onTouchEvent(MotionEvent motionEvent) {
                int action = motionEvent.getAction() & 255;
                if (action == 0) {
                    SimpleSideDrawer.this.mRightDragAction.mLastMotionX = motionEvent.getX();
                    SimpleSideDrawer.this.mRightDragAction.mDraggable = SimpleSideDrawer.this.mAboveView.getScrollX() != 0;
                } else if (action != 1) {
                    if (action != 2 || !SimpleSideDrawer.this.mRightDragAction.mDraggable) {
                        return false;
                    }
                    float x = motionEvent.getX();
                    float f = -(x - SimpleSideDrawer.this.mRightDragAction.mLastMotionX);
                    int scrollX = SimpleSideDrawer.this.mAboveView.getScrollX();
                    System.out.format("[SLIDE] ACTION_MOVE(r): %f, %d\n", Float.valueOf(x), Integer.valueOf(scrollX));
                    SimpleSideDrawer.this.mRightDragAction.mOpening = x < SimpleSideDrawer.this.mRightDragAction.mLastMotionX;
                    SimpleSideDrawer.this.mRightDragAction.mLastMotionX = x;
                    float f2 = scrollX + f;
                    if (f2 < 0.0f) {
                        SimpleSideDrawer.this.mAboveView.scrollTo(0, 0);
                    } else if (f2 < SimpleSideDrawer.this.mRightBehindViewWidth) {
                        SimpleSideDrawer.this.mAboveView.scrollBy((int) f, 0);
                    } else {
                        SimpleSideDrawer.this.mAboveView.scrollTo(SimpleSideDrawer.this.mRightBehindViewWidth, 0);
                    }
                } else if (SimpleSideDrawer.this.mRightDragAction.mDraggable) {
                    int scrollX2 = SimpleSideDrawer.this.mAboveView.getScrollX();
                    SimpleSideDrawer.this.mScroller.startScroll(scrollX2, 0, SimpleSideDrawer.this.mRightDragAction.mOpening ? SimpleSideDrawer.this.mRightBehindViewWidth - scrollX2 : -scrollX2, 0, SimpleSideDrawer.this.mDurationRight);
                    SimpleSideDrawer.this.invalidate();
                }
                return false;
            }
        };
        Context applicationContext = activity.getApplicationContext();
        this.mDurationLeft = i;
        this.mDurationRight = i;
        this.mWindow = activity.getWindow();
        this.mScroller = new Scroller(applicationContext, interpolator);
        BehindLinearLayout behindLinearLayout = new BehindLinearLayout(applicationContext);
        this.mBehindView = behindLinearLayout;
        behindLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.mBehindView.setOrientation(0);
        BehindLinearLayout behindLinearLayout2 = new BehindLinearLayout(applicationContext);
        this.mLeftBehindBase = behindLinearLayout2;
        this.mBehindView.addView(behindLinearLayout2, new LinearLayout.LayoutParams(-2, -1));
        this.mBehindView.addView(new View(applicationContext), new LinearLayout.LayoutParams(0, -1, 1.0f));
        BehindLinearLayout behindLinearLayout3 = new BehindLinearLayout(applicationContext);
        this.mRightBehindBase = behindLinearLayout3;
        this.mBehindView.addView(behindLinearLayout3, new LinearLayout.LayoutParams(-2, -1));
        addView(this.mBehindView);
        FrameLayout frameLayout = new FrameLayout(applicationContext);
        this.mAboveView = frameLayout;
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        OverlayView overlayView = new OverlayView(getContext());
        this.mOverlay = overlayView;
        overlayView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1, 80));
        this.mOverlay.setEnabled(true);
        this.mOverlay.setVisibility(8);
        this.mOverlay.setOnClickListener(new View.OnClickListener() { // from class: com.navdrawer.SimpleSideDrawer.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SimpleSideDrawer.this.mLeftBehindBase.getVisibility() == 8) {
                    if (SimpleSideDrawer.this.mRightBehindBase.getVisibility() != 8) {
                        SimpleSideDrawer.this.closeRightSide();
                        return;
                    }
                    return;
                }
                SimpleSideDrawer.this.closeLeftSide();
            }
        });
        ViewGroup viewGroup = (ViewGroup) this.mWindow.getDecorView();
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.getChildAt(0);
        viewGroup.removeView(viewGroup2);
        viewGroup2.setBackgroundDrawable(viewGroup.getBackground());
        this.mAboveView.addView(viewGroup2);
        this.mAboveView.addView(this.mOverlay);
        viewGroup.addView(this);
        addView(this.mAboveView);
    }

    public View getLeftBehindView() {
        return this.mLeftBehindBase.getChildAt(0);
    }

    public View getRightBehindView() {
        return this.mRightBehindBase.getChildAt(0);
    }

    public View setBehindContentView(int i) {
        return setLeftBehindContentView(i);
    }

    public View setLeftBehindContentView(int i) {
        View viewInflate = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(i, this.mLeftBehindBase);
        this.mLeftPaddingRect = new Rect(viewInflate.getPaddingLeft(), viewInflate.getPaddingTop(), viewInflate.getPaddingRight(), viewInflate.getPaddingBottom());
        this.mLeftBehindView = viewInflate;
        return viewInflate;
    }

    public View setRightBehindContentView(int i) {
        View viewInflate = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(i, this.mRightBehindBase);
        this.mRightPaddingRect = new Rect(viewInflate.getPaddingLeft(), viewInflate.getPaddingTop(), viewInflate.getPaddingRight(), viewInflate.getPaddingBottom());
        this.mRightBehindView = viewInflate;
        return viewInflate;
    }

    public void setScrollInterpolator(Interpolator interpolator) {
        this.mScroller = new Scroller(getContext(), interpolator);
    }

    public void setAnimationDuration(int i) {
        setAnimationDurationLeft(i);
    }

    public void setAnimationDurationLeft(int i) {
        this.mDurationLeft = i;
    }

    public void setAnimationDurationRight(int i) {
        this.mDurationRight = i;
    }

    public void close() {
        closeLeftSide();
    }

    public void closeLeftSide() {
        int i = -this.mLeftBehindViewWidth;
        this.mScroller.startScroll(i, 0, -i, 0, this.mDurationLeft);
        invalidate();
    }

    public void closeRightSide() {
        int i = this.mRightBehindViewWidth;
        this.mScroller.startScroll(i, 0, -i, 0, this.mDurationRight);
        invalidate();
    }

    public void open() {
        openLeftSide();
    }

    public void openLeftSide() {
        System.out.format("[SLIDE] openLeftSide: %d\n", Integer.valueOf(this.mLeftBehindViewWidth));
        this.mLeftBehindBase.setVisibility(0);
        this.mRightBehindBase.setVisibility(8);
        this.mScroller.startScroll(this.mAboveView.getScrollX(), 0, -this.mLeftBehindViewWidth, 0, this.mDurationLeft);
        invalidate();
    }

    public void openRightSide() {
        System.out.format("[SLIDE] openRightSide: %d\n", Integer.valueOf(this.mRightBehindViewWidth));
        this.mRightBehindBase.setVisibility(0);
        this.mLeftBehindBase.setVisibility(8);
        this.mScroller.startScroll(this.mAboveView.getScrollX(), 0, this.mRightBehindViewWidth, 0, this.mDurationRight);
        invalidate();
    }

    public void toggleDrawer() {
        toggleLeftDrawer();
    }

    public void toggleLeftDrawer() {
        if (isClosed()) {
            System.out.println("[SLIDE] toggleLeft: open");
            openLeftSide();
        } else {
            System.out.println("[SLIDE] toggleLeft: close");
            closeLeftSide();
        }
    }

    public void toggleRightDrawer() {
        if (isClosed()) {
            System.out.println("[SLIDE] toggleRight: open");
            openRightSide();
        } else {
            System.out.println("[SLIDE] toggleRight: close");
            closeRightSide();
        }
    }

    public boolean isClosed() {
        ViewGroup viewGroup = this.mAboveView;
        return viewGroup != null && viewGroup.getScrollX() == 0;
    }

    private boolean isLeftSideOpened() {
        return this.mLeftBehindBase.getVisibility() == 0 && this.mRightBehindBase.getVisibility() == 8;
    }

    private boolean isRightSideOpened() {
        return this.mRightBehindBase.getVisibility() == 0 && this.mLeftBehindBase.getVisibility() == 8;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mLeftBehindViewWidth = this.mLeftBehindBase.getMeasuredWidth();
        this.mRightBehindViewWidth = this.mRightBehindBase.getMeasuredWidth();
        System.out.format("[SLIDE] onMeasure: %d, %d\n", Integer.valueOf(this.mLeftBehindViewWidth), Integer.valueOf(this.mRightBehindViewWidth));
        ViewGroup viewGroup = (ViewGroup) this.mWindow.getDecorView();
        Rect rect = new Rect();
        viewGroup.getWindowVisibleDisplayFrame(rect);
        this.mBehindView.fitDisplay(rect);
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            this.mAboveView.scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            invalidate();
        } else if (this.mAboveView.getScrollX() == 0) {
            this.mOverlay.setVisibility(8);
        } else {
            this.mOverlay.setVisibility(0);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isLeftSideOpened()) {
            return this.mLeftDragAction.onTouchEvent(motionEvent);
        }
        if (isRightSideOpened()) {
            return this.mRightDragAction.onTouchEvent(motionEvent);
        }
        return true;
    }

    private class BehindLinearLayout extends LinearLayout {
        public BehindLinearLayout(Context context) {
            super(context);
        }

        public void fitDisplay(Rect rect) {
            SimpleSideDrawer.this.mBehindView.setPadding(rect.left, rect.top, 0, 0);
            requestLayout();
        }
    }

    private class OverlayView extends View {
        private static final float CLICK_RANGE = 3.0f;
        private View.OnClickListener mClickListener;
        private float mDownX;
        private float mDownY;

        public OverlayView(Context context) {
            super(context);
        }

        @Override // android.view.View
        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.mClickListener = onClickListener;
            super.setOnClickListener(onClickListener);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            motionEvent.setLocation(motionEvent.getX() - SimpleSideDrawer.this.mAboveView.getScrollX(), 0.0f);
            SimpleSideDrawer.this.onTouchEvent(motionEvent);
            int action = motionEvent.getAction() & 255;
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (action == 0) {
                this.mDownX = x;
                this.mDownY = y;
            } else if (action == 1 && this.mClickListener != null && Math.abs(this.mDownX - x) < 3.0f && Math.abs(this.mDownY - y) < 3.0f) {
                this.mClickListener.onClick(this);
            }
            return true;
        }
    }
}
