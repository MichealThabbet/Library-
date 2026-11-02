package com.marshalchen.ultimaterecyclerview.swipe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import com.marshalchen.ultimaterecyclerview.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class SwipeLayout extends FrameLayout {
    private static final int DRAG_BOTTOM = 8;
    private static final int DRAG_LEFT = 1;
    private static final int DRAG_RIGHT = 2;
    private static final int DRAG_TOP = 4;
    private static final DragEdge DefaultDragEdge = DragEdge.Right;

    @Deprecated
    public static final int EMPTY_LAYOUT = -1;
    View.OnClickListener clickListener;
    private GestureDetector gestureDetector;
    private Rect hitSurfaceRect;
    View.OnLongClickListener longClickListener;
    private boolean mClickToClose;
    private DragEdge mCurrentDragEdge;
    private DoubleClickListener mDoubleClickListener;
    private int mDragDistance;
    private LinkedHashMap<DragEdge, View> mDragEdges;
    private ViewDragHelper mDragHelper;
    private ViewDragHelper.Callback mDragHelperCallback;
    private float[] mEdgeSwipesOffset;
    private int mEventCounter;
    private boolean mIsBeingDragged;
    private List<OnLayout> mOnLayoutListeners;
    private Map<View, ArrayList<OnRevealListener>> mRevealListeners;
    private Map<View, Boolean> mShowEntirely;
    private ShowMode mShowMode;
    private List<SwipeDenier> mSwipeDeniers;
    private boolean mSwipeEnabled;
    private List<SwipeListener> mSwipeListeners;
    private boolean[] mSwipesEnabled;
    private int mTouchSlop;
    private float sX;
    private float sY;

    public interface DoubleClickListener {
        void onDoubleClick(SwipeLayout swipeLayout, boolean z);
    }

    public enum DragEdge {
        Left,
        Top,
        Right,
        Bottom
    }

    public interface OnLayout {
        void onLayout(SwipeLayout swipeLayout);
    }

    public interface OnRevealListener {
        void onReveal(View view, DragEdge dragEdge, float f, int i);
    }

    public enum ShowMode {
        LayDown,
        PullOut
    }

    public enum Status {
        Middle,
        Open,
        Close
    }

    public interface SwipeDenier {
        boolean shouldDenySwipe(MotionEvent motionEvent);
    }

    public interface SwipeListener {
        void onClose(SwipeLayout swipeLayout);

        void onHandRelease(SwipeLayout swipeLayout, float f, float f2);

        void onOpen(SwipeLayout swipeLayout);

        void onStartClose(SwipeLayout swipeLayout);

        void onStartOpen(SwipeLayout swipeLayout);

        void onUpdate(SwipeLayout swipeLayout, int i, int i2);
    }

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SwipeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentDragEdge = DefaultDragEdge;
        this.mDragDistance = 0;
        this.mDragEdges = new LinkedHashMap<>();
        this.mEdgeSwipesOffset = new float[4];
        this.mSwipeListeners = new ArrayList();
        this.mSwipeDeniers = new ArrayList();
        this.mRevealListeners = new HashMap();
        this.mShowEntirely = new HashMap();
        this.mSwipeEnabled = true;
        this.mSwipesEnabled = new boolean[]{true, true, true, true};
        this.mClickToClose = false;
        this.mDragHelperCallback = new ViewDragHelper.Callback() { // from class: com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.1
            boolean isCloseBeforeDrag = true;

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(View view, int i2, int i3) {
                if (view == SwipeLayout.this.getSurfaceView()) {
                    int i4 = AnonymousClass4.$SwitchMap$com$marshalchen$ultimaterecyclerview$swipe$SwipeLayout$DragEdge[SwipeLayout.this.mCurrentDragEdge.ordinal()];
                    if (i4 == 1 || i4 == 2) {
                        return SwipeLayout.this.getPaddingLeft();
                    }
                    if (i4 != 3) {
                        if (i4 == 4) {
                            if (i2 > SwipeLayout.this.getPaddingLeft()) {
                                return SwipeLayout.this.getPaddingLeft();
                            }
                            if (i2 < SwipeLayout.this.getPaddingLeft() - SwipeLayout.this.mDragDistance) {
                                return SwipeLayout.this.getPaddingLeft() - SwipeLayout.this.mDragDistance;
                            }
                        }
                    } else {
                        if (i2 < SwipeLayout.this.getPaddingLeft()) {
                            return SwipeLayout.this.getPaddingLeft();
                        }
                        if (i2 > SwipeLayout.this.getPaddingLeft() + SwipeLayout.this.mDragDistance) {
                            return SwipeLayout.this.getPaddingLeft() + SwipeLayout.this.mDragDistance;
                        }
                    }
                } else if (SwipeLayout.this.getCurrentBottomView() == view) {
                    int i5 = AnonymousClass4.$SwitchMap$com$marshalchen$ultimaterecyclerview$swipe$SwipeLayout$DragEdge[SwipeLayout.this.mCurrentDragEdge.ordinal()];
                    if (i5 == 1 || i5 == 2) {
                        return SwipeLayout.this.getPaddingLeft();
                    }
                    if (i5 != 3) {
                        if (i5 == 4 && SwipeLayout.this.mShowMode == ShowMode.PullOut && i2 < SwipeLayout.this.getMeasuredWidth() - SwipeLayout.this.mDragDistance) {
                            return SwipeLayout.this.getMeasuredWidth() - SwipeLayout.this.mDragDistance;
                        }
                    } else if (SwipeLayout.this.mShowMode == ShowMode.PullOut && i2 > SwipeLayout.this.getPaddingLeft()) {
                        return SwipeLayout.this.getPaddingLeft();
                    }
                }
                return i2;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(View view, int i2, int i3) {
                if (view == SwipeLayout.this.getSurfaceView()) {
                    int i4 = AnonymousClass4.$SwitchMap$com$marshalchen$ultimaterecyclerview$swipe$SwipeLayout$DragEdge[SwipeLayout.this.mCurrentDragEdge.ordinal()];
                    if (i4 != 1) {
                        if (i4 != 2) {
                            if (i4 == 3 || i4 == 4) {
                                return SwipeLayout.this.getPaddingTop();
                            }
                        } else {
                            if (i2 < SwipeLayout.this.getPaddingTop() - SwipeLayout.this.mDragDistance) {
                                return SwipeLayout.this.getPaddingTop() - SwipeLayout.this.mDragDistance;
                            }
                            if (i2 > SwipeLayout.this.getPaddingTop()) {
                                return SwipeLayout.this.getPaddingTop();
                            }
                        }
                    } else {
                        if (i2 < SwipeLayout.this.getPaddingTop()) {
                            return SwipeLayout.this.getPaddingTop();
                        }
                        if (i2 > SwipeLayout.this.getPaddingTop() + SwipeLayout.this.mDragDistance) {
                            return SwipeLayout.this.getPaddingTop() + SwipeLayout.this.mDragDistance;
                        }
                    }
                } else {
                    View surfaceView = SwipeLayout.this.getSurfaceView();
                    int top = surfaceView == null ? 0 : surfaceView.getTop();
                    int i5 = AnonymousClass4.$SwitchMap$com$marshalchen$ultimaterecyclerview$swipe$SwipeLayout$DragEdge[SwipeLayout.this.mCurrentDragEdge.ordinal()];
                    if (i5 != 1) {
                        if (i5 != 2) {
                            if (i5 == 3 || i5 == 4) {
                                return SwipeLayout.this.getPaddingTop();
                            }
                        } else if (SwipeLayout.this.mShowMode == ShowMode.PullOut) {
                            if (i2 < SwipeLayout.this.getMeasuredHeight() - SwipeLayout.this.mDragDistance) {
                                return SwipeLayout.this.getMeasuredHeight() - SwipeLayout.this.mDragDistance;
                            }
                        } else {
                            int i6 = top + i3;
                            if (i6 < SwipeLayout.this.getPaddingTop()) {
                                if (i6 <= SwipeLayout.this.getPaddingTop() - SwipeLayout.this.mDragDistance) {
                                    return SwipeLayout.this.getPaddingTop() - SwipeLayout.this.mDragDistance;
                                }
                            } else {
                                return SwipeLayout.this.getPaddingTop();
                            }
                        }
                    } else if (SwipeLayout.this.mShowMode == ShowMode.PullOut) {
                        if (i2 > SwipeLayout.this.getPaddingTop()) {
                            return SwipeLayout.this.getPaddingTop();
                        }
                    } else {
                        int i7 = top + i3;
                        if (i7 >= SwipeLayout.this.getPaddingTop()) {
                            if (i7 > SwipeLayout.this.getPaddingTop() + SwipeLayout.this.mDragDistance) {
                                return SwipeLayout.this.getPaddingTop() + SwipeLayout.this.mDragDistance;
                            }
                        } else {
                            return SwipeLayout.this.getPaddingTop();
                        }
                    }
                }
                return i2;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view, int i2) {
                boolean z = view == SwipeLayout.this.getSurfaceView() || SwipeLayout.this.getBottomViews().contains(view);
                if (z) {
                    this.isCloseBeforeDrag = SwipeLayout.this.getOpenStatus() == Status.Close;
                }
                return z;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewHorizontalDragRange(View view) {
                return SwipeLayout.this.mDragDistance;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(View view) {
                return SwipeLayout.this.mDragDistance;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(View view, float f, float f2) {
                super.onViewReleased(view, f, f2);
                SwipeLayout.this.processHandRelease(f, f2, this.isCloseBeforeDrag);
                Iterator it2 = SwipeLayout.this.mSwipeListeners.iterator();
                while (it2.hasNext()) {
                    ((SwipeListener) it2.next()).onHandRelease(SwipeLayout.this, f, f2);
                }
                SwipeLayout.this.invalidate();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View view, int i2, int i3, int i4, int i5) {
                View surfaceView = SwipeLayout.this.getSurfaceView();
                if (surfaceView == null) {
                    return;
                }
                View currentBottomView = SwipeLayout.this.getCurrentBottomView();
                int left = surfaceView.getLeft();
                int right = surfaceView.getRight();
                int top = surfaceView.getTop();
                int bottom = surfaceView.getBottom();
                if (view == surfaceView) {
                    if (SwipeLayout.this.mShowMode == ShowMode.PullOut && currentBottomView != null) {
                        if (SwipeLayout.this.mCurrentDragEdge == DragEdge.Left || SwipeLayout.this.mCurrentDragEdge == DragEdge.Right) {
                            currentBottomView.offsetLeftAndRight(i4);
                        } else {
                            currentBottomView.offsetTopAndBottom(i5);
                        }
                    }
                } else if (SwipeLayout.this.getBottomViews().contains(view)) {
                    if (SwipeLayout.this.mShowMode == ShowMode.PullOut) {
                        surfaceView.offsetLeftAndRight(i4);
                        surfaceView.offsetTopAndBottom(i5);
                    } else {
                        SwipeLayout swipeLayout = SwipeLayout.this;
                        Rect rectComputeBottomLayDown = swipeLayout.computeBottomLayDown(swipeLayout.mCurrentDragEdge);
                        if (currentBottomView != null) {
                            currentBottomView.layout(rectComputeBottomLayDown.left, rectComputeBottomLayDown.top, rectComputeBottomLayDown.right, rectComputeBottomLayDown.bottom);
                        }
                        int left2 = surfaceView.getLeft() + i4;
                        int top2 = surfaceView.getTop() + i5;
                        if (SwipeLayout.this.mCurrentDragEdge != DragEdge.Left || left2 >= SwipeLayout.this.getPaddingLeft()) {
                            if (SwipeLayout.this.mCurrentDragEdge != DragEdge.Right || left2 <= SwipeLayout.this.getPaddingLeft()) {
                                if (SwipeLayout.this.mCurrentDragEdge != DragEdge.Top || top2 >= SwipeLayout.this.getPaddingTop()) {
                                    if (SwipeLayout.this.mCurrentDragEdge == DragEdge.Bottom && top2 > SwipeLayout.this.getPaddingTop()) {
                                        top2 = SwipeLayout.this.getPaddingTop();
                                    }
                                } else {
                                    top2 = SwipeLayout.this.getPaddingTop();
                                }
                            } else {
                                left2 = SwipeLayout.this.getPaddingLeft();
                            }
                        } else {
                            left2 = SwipeLayout.this.getPaddingLeft();
                        }
                        surfaceView.layout(left2, top2, SwipeLayout.this.getMeasuredWidth() + left2, SwipeLayout.this.getMeasuredHeight() + top2);
                    }
                }
                SwipeLayout.this.dispatchRevealEvent(left, top, right, bottom);
                SwipeLayout.this.dispatchSwipeEvent(left, top, i4, i5);
                SwipeLayout.this.invalidate();
            }
        };
        this.mEventCounter = 0;
        this.sX = -1.0f;
        this.sY = -1.0f;
        this.gestureDetector = new GestureDetector(getContext(), new SwipeDetector());
        this.mDragHelper = ViewDragHelper.create(this, this.mDragHelperCallback);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SwipeLayout);
        int i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.SwipeLayout_drag_edge, 2);
        this.mEdgeSwipesOffset[DragEdge.Left.ordinal()] = typedArrayObtainStyledAttributes.getDimension(R.styleable.SwipeLayout_leftEdgeSwipeOffset, 0.0f);
        this.mEdgeSwipesOffset[DragEdge.Right.ordinal()] = typedArrayObtainStyledAttributes.getDimension(R.styleable.SwipeLayout_rightEdgeSwipeOffset, 0.0f);
        this.mEdgeSwipesOffset[DragEdge.Top.ordinal()] = typedArrayObtainStyledAttributes.getDimension(R.styleable.SwipeLayout_topEdgeSwipeOffset, 0.0f);
        this.mEdgeSwipesOffset[DragEdge.Bottom.ordinal()] = typedArrayObtainStyledAttributes.getDimension(R.styleable.SwipeLayout_bottomEdgeSwipeOffset, 0.0f);
        setClickToClose(typedArrayObtainStyledAttributes.getBoolean(R.styleable.SwipeLayout_clickToClose, this.mClickToClose));
        if ((i2 & 1) == 1) {
            this.mDragEdges.put(DragEdge.Left, null);
        }
        if ((i2 & 4) == 4) {
            this.mDragEdges.put(DragEdge.Top, null);
        }
        if ((i2 & 2) == 2) {
            this.mDragEdges.put(DragEdge.Right, null);
        }
        if ((i2 & 8) == 8) {
            this.mDragEdges.put(DragEdge.Bottom, null);
        }
        this.mShowMode = ShowMode.values()[typedArrayObtainStyledAttributes.getInt(R.styleable.SwipeLayout_show_mode, ShowMode.PullOut.ordinal())];
        typedArrayObtainStyledAttributes.recycle();
    }

    public void addSwipeListener(SwipeListener swipeListener) {
        this.mSwipeListeners.add(swipeListener);
    }

    public void removeSwipeListener(SwipeListener swipeListener) {
        this.mSwipeListeners.remove(swipeListener);
    }

    public void removeAllSwipeListener() {
        this.mSwipeListeners.clear();
    }

    public void addSwipeDenier(SwipeDenier swipeDenier) {
        this.mSwipeDeniers.add(swipeDenier);
    }

    public void removeSwipeDenier(SwipeDenier swipeDenier) {
        this.mSwipeDeniers.remove(swipeDenier);
    }

    public void removeAllSwipeDeniers() {
        this.mSwipeDeniers.clear();
    }

    public void addRevealListener(int i, OnRevealListener onRevealListener) {
        View viewFindViewById = findViewById(i);
        if (viewFindViewById == null) {
            throw new IllegalArgumentException("Child does not belong to SwipeListener.");
        }
        if (!this.mShowEntirely.containsKey(viewFindViewById)) {
            this.mShowEntirely.put(viewFindViewById, false);
        }
        if (this.mRevealListeners.get(viewFindViewById) == null) {
            this.mRevealListeners.put(viewFindViewById, new ArrayList<>());
        }
        this.mRevealListeners.get(viewFindViewById).add(onRevealListener);
    }

    public void addRevealListener(int[] iArr, OnRevealListener onRevealListener) {
        for (int i : iArr) {
            addRevealListener(i, onRevealListener);
        }
    }

    public void removeRevealListener(int i, OnRevealListener onRevealListener) {
        View viewFindViewById = findViewById(i);
        if (viewFindViewById == null) {
            return;
        }
        this.mShowEntirely.remove(viewFindViewById);
        if (this.mRevealListeners.containsKey(viewFindViewById)) {
            this.mRevealListeners.get(viewFindViewById).remove(onRevealListener);
        }
    }

    public void removeAllRevealListeners(int i) {
        View viewFindViewById = findViewById(i);
        if (viewFindViewById != null) {
            this.mRevealListeners.remove(viewFindViewById);
            this.mShowEntirely.remove(viewFindViewById);
        }
    }

    /* renamed from: com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$marshalchen$ultimaterecyclerview$swipe$SwipeLayout$DragEdge;

        static {
            int[] iArr = new int[DragEdge.values().length];
            $SwitchMap$com$marshalchen$ultimaterecyclerview$swipe$SwipeLayout$DragEdge = iArr;
            try {
                iArr[DragEdge.Top.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$marshalchen$ultimaterecyclerview$swipe$SwipeLayout$DragEdge[DragEdge.Bottom.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$marshalchen$ultimaterecyclerview$swipe$SwipeLayout$DragEdge[DragEdge.Left.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$marshalchen$ultimaterecyclerview$swipe$SwipeLayout$DragEdge[DragEdge.Right.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    protected boolean isViewTotallyFirstShowed(View view, Rect rect, DragEdge dragEdge, int i, int i2, int i3, int i4) {
        if (this.mShowEntirely.get(view).booleanValue()) {
            return false;
        }
        int i5 = rect.left;
        int i6 = rect.right;
        int i7 = rect.top;
        int i8 = rect.bottom;
        if (getShowMode() == ShowMode.LayDown) {
            if ((dragEdge != DragEdge.Right || i3 > i5) && ((dragEdge != DragEdge.Left || i < i6) && ((dragEdge != DragEdge.Top || i2 < i8) && (dragEdge != DragEdge.Bottom || i4 > i7)))) {
                return false;
            }
        } else {
            if (getShowMode() != ShowMode.PullOut) {
                return false;
            }
            if ((dragEdge != DragEdge.Right || i6 > getWidth()) && ((dragEdge != DragEdge.Left || i5 < getPaddingLeft()) && ((dragEdge != DragEdge.Top || i7 < getPaddingTop()) && (dragEdge != DragEdge.Bottom || i8 > getHeight())))) {
                return false;
            }
        }
        return true;
    }

    protected boolean isViewShowing(View view, Rect rect, DragEdge dragEdge, int i, int i2, int i3, int i4) {
        int i5 = rect.left;
        int i6 = rect.right;
        int i7 = rect.top;
        int i8 = rect.bottom;
        if (getShowMode() == ShowMode.LayDown) {
            int i9 = AnonymousClass4.$SwitchMap$com$marshalchen$ultimaterecyclerview$swipe$SwipeLayout$DragEdge[dragEdge.ordinal()];
            return i9 != 1 ? i9 != 2 ? i9 != 3 ? i9 == 4 && i3 > i5 && i3 <= i6 : i < i6 && i >= i5 : i4 > i7 && i4 <= i8 : i2 >= i7 && i2 < i8;
        }
        if (getShowMode() != ShowMode.PullOut) {
            return false;
        }
        int i10 = AnonymousClass4.$SwitchMap$com$marshalchen$ultimaterecyclerview$swipe$SwipeLayout$DragEdge[dragEdge.ordinal()];
        return i10 != 1 ? i10 != 2 ? i10 != 3 ? i10 == 4 && i5 <= getWidth() && i6 > getWidth() : i6 >= getPaddingLeft() && i5 < getPaddingLeft() : i7 < getHeight() && i7 >= getPaddingTop() : i7 < getPaddingTop() && i8 >= getPaddingTop();
    }

    protected Rect getRelativePosition(View view) {
        Rect rect = new Rect(view.getLeft(), view.getTop(), 0, 0);
        View view2 = view;
        while (view2.getParent() != null && view2 != getRootView() && (view2 = (View) view2.getParent()) != this) {
            rect.left += view2.getLeft();
            rect.top += view2.getTop();
        }
        rect.right = rect.left + view.getMeasuredWidth();
        rect.bottom = rect.top + view.getMeasuredHeight();
        return rect;
    }

    protected void dispatchSwipeEvent(int i, int i2, int i3, int i4) {
        DragEdge dragEdge = getDragEdge();
        boolean z = false;
        if (dragEdge != DragEdge.Left ? dragEdge != DragEdge.Right ? dragEdge != DragEdge.Top ? dragEdge != DragEdge.Bottom || i4 <= 0 : i4 >= 0 : i3 <= 0 : i3 >= 0) {
            z = true;
        }
        dispatchSwipeEvent(i, i2, z);
    }

    protected void dispatchSwipeEvent(int i, int i2, boolean z) {
        safeBottomView();
        Status openStatus = getOpenStatus();
        if (this.mSwipeListeners.isEmpty()) {
            return;
        }
        this.mEventCounter++;
        for (SwipeListener swipeListener : this.mSwipeListeners) {
            if (this.mEventCounter == 1) {
                if (z) {
                    swipeListener.onStartOpen(this);
                } else {
                    swipeListener.onStartClose(this);
                }
            }
            swipeListener.onUpdate(this, i - getPaddingLeft(), i2 - getPaddingTop());
        }
        if (openStatus == Status.Close) {
            Iterator<SwipeListener> it2 = this.mSwipeListeners.iterator();
            while (it2.hasNext()) {
                it2.next().onClose(this);
            }
            this.mEventCounter = 0;
        }
        if (openStatus == Status.Open) {
            View currentBottomView = getCurrentBottomView();
            if (currentBottomView != null) {
                currentBottomView.setEnabled(true);
            }
            Iterator<SwipeListener> it3 = this.mSwipeListeners.iterator();
            while (it3.hasNext()) {
                it3.next().onOpen(this);
            }
            this.mEventCounter = 0;
        }
    }

    private void safeBottomView() {
        Status openStatus = getOpenStatus();
        List<View> bottomViews = getBottomViews();
        if (openStatus == Status.Close) {
            for (View view : bottomViews) {
                if (view != null && view.getVisibility() != 4) {
                    view.setVisibility(4);
                }
            }
            return;
        }
        View currentBottomView = getCurrentBottomView();
        if (currentBottomView == null || currentBottomView.getVisibility() == 0) {
            return;
        }
        currentBottomView.setVisibility(0);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void dispatchRevealEvent(int r16, int r17, int r18, int r19) {
        /*
            Method dump skipped, instructions count: 366
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.dispatchRevealEvent(int, int, int, int):void");
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void addOnLayoutListener(OnLayout onLayout) {
        if (this.mOnLayoutListeners == null) {
            this.mOnLayoutListeners = new ArrayList();
        }
        this.mOnLayoutListeners.add(onLayout);
    }

    public void removeOnLayoutListener(OnLayout onLayout) {
        List<OnLayout> list = this.mOnLayoutListeners;
        if (list != null) {
            list.remove(onLayout);
        }
    }

    public void clearDragEdge() {
        this.mDragEdges.clear();
    }

    public void setDrag(DragEdge dragEdge, int i) {
        clearDragEdge();
        addDrag(dragEdge, i);
    }

    public void setDrag(DragEdge dragEdge, View view) {
        clearDragEdge();
        addDrag(dragEdge, view);
    }

    public void addDrag(DragEdge dragEdge, int i) {
        addDrag(dragEdge, findViewById(i), null);
    }

    public void addDrag(DragEdge dragEdge, View view) {
        addDrag(dragEdge, view, null);
    }

    public void addDrag(DragEdge dragEdge, View view, ViewGroup.LayoutParams layoutParams) {
        if (view == null) {
            return;
        }
        if (layoutParams == null) {
            layoutParams = generateDefaultLayoutParams();
        }
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateLayoutParams(layoutParams);
        }
        int i = -1;
        int i2 = AnonymousClass4.$SwitchMap$com$marshalchen$ultimaterecyclerview$swipe$SwipeLayout$DragEdge[dragEdge.ordinal()];
        if (i2 == 1) {
            i = 48;
        } else if (i2 == 2) {
            i = 80;
        } else if (i2 == 3) {
            i = 3;
        } else if (i2 == 4) {
            i = 5;
        }
        if (layoutParams instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) layoutParams).gravity = i;
        }
        addView(view, 0, layoutParams);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view == null) {
            return;
        }
        int iIntValue = 0;
        try {
            iIntValue = ((Integer) layoutParams.getClass().getField("gravity").get(layoutParams)).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (iIntValue > 0) {
            int absoluteGravity = GravityCompat.getAbsoluteGravity(iIntValue, ViewCompat.getLayoutDirection(this));
            if ((absoluteGravity & 3) == 3) {
                this.mDragEdges.put(DragEdge.Left, view);
            }
            if ((absoluteGravity & 5) == 5) {
                this.mDragEdges.put(DragEdge.Right, view);
            }
            if ((absoluteGravity & 48) == 48) {
                this.mDragEdges.put(DragEdge.Top, view);
            }
            if ((absoluteGravity & 80) == 80) {
                this.mDragEdges.put(DragEdge.Bottom, view);
            }
        } else {
            Iterator<Map.Entry<DragEdge, View>> it2 = this.mDragEdges.entrySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Map.Entry<DragEdge, View> next = it2.next();
                if (next.getValue() == null) {
                    this.mDragEdges.put(next.getKey(), view);
                    break;
                }
            }
        }
        if (view.getParent() == this) {
            return;
        }
        super.addView(view, i, layoutParams);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        updateBottomViews();
        if (this.mOnLayoutListeners != null) {
            for (int i5 = 0; i5 < this.mOnLayoutListeners.size(); i5++) {
                this.mOnLayoutListeners.get(i5).onLayout(this);
            }
        }
    }

    void layoutPullOut() {
        Rect rectComputeSurfaceLayoutArea = computeSurfaceLayoutArea(false);
        View surfaceView = getSurfaceView();
        if (surfaceView != null) {
            surfaceView.layout(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
            bringChildToFront(surfaceView);
        }
        Rect rectComputeBottomLayoutAreaViaSurface = computeBottomLayoutAreaViaSurface(ShowMode.PullOut, rectComputeSurfaceLayoutArea);
        View currentBottomView = getCurrentBottomView();
        if (currentBottomView != null) {
            currentBottomView.layout(rectComputeBottomLayoutAreaViaSurface.left, rectComputeBottomLayoutAreaViaSurface.top, rectComputeBottomLayoutAreaViaSurface.right, rectComputeBottomLayoutAreaViaSurface.bottom);
        }
    }

    void layoutLayDown() {
        Rect rectComputeSurfaceLayoutArea = computeSurfaceLayoutArea(false);
        View surfaceView = getSurfaceView();
        if (surfaceView != null) {
            surfaceView.layout(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
            bringChildToFront(surfaceView);
        }
        Rect rectComputeBottomLayoutAreaViaSurface = computeBottomLayoutAreaViaSurface(ShowMode.LayDown, rectComputeSurfaceLayoutArea);
        View currentBottomView = getCurrentBottomView();
        if (currentBottomView != null) {
            currentBottomView.layout(rectComputeBottomLayoutAreaViaSurface.left, rectComputeBottomLayoutAreaViaSurface.top, rectComputeBottomLayoutAreaViaSurface.right, rectComputeBottomLayoutAreaViaSurface.bottom);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void checkCanDrag(android.view.MotionEvent r10) {
        /*
            Method dump skipped, instructions count: 328
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.checkCanDrag(android.view.MotionEvent):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0064  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            boolean r0 = r4.isSwipeEnabled()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            boolean r0 = r4.mClickToClose
            r2 = 1
            if (r0 == 0) goto L1c
            com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout$Status r0 = r4.getOpenStatus()
            com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout$Status r3 = com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.Status.Open
            if (r0 != r3) goto L1c
            boolean r0 = r4.isTouchOnSurface(r5)
            if (r0 == 0) goto L1c
            return r2
        L1c:
            java.util.List<com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout$SwipeDenier> r0 = r4.mSwipeDeniers
            java.util.Iterator r0 = r0.iterator()
        L22:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L37
            java.lang.Object r3 = r0.next()
            com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout$SwipeDenier r3 = (com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.SwipeDenier) r3
            if (r3 == 0) goto L22
            boolean r3 = r3.shouldDenySwipe(r5)
            if (r3 == 0) goto L22
            return r1
        L37:
            int r0 = r5.getAction()
            if (r0 == 0) goto L6c
            if (r0 == r2) goto L64
            r3 = 2
            if (r0 == r3) goto L4b
            r2 = 3
            if (r0 == r2) goto L64
            androidx.customview.widget.ViewDragHelper r0 = r4.mDragHelper
            r0.processTouchEvent(r5)
            goto L89
        L4b:
            boolean r0 = r4.mIsBeingDragged
            r4.checkCanDrag(r5)
            boolean r5 = r4.mIsBeingDragged
            if (r5 == 0) goto L5d
            android.view.ViewParent r5 = r4.getParent()
            if (r5 == 0) goto L5d
            r5.requestDisallowInterceptTouchEvent(r2)
        L5d:
            if (r0 != 0) goto L89
            boolean r5 = r4.mIsBeingDragged
            if (r5 == 0) goto L89
            return r1
        L64:
            r4.mIsBeingDragged = r1
            androidx.customview.widget.ViewDragHelper r0 = r4.mDragHelper
            r0.processTouchEvent(r5)
            goto L89
        L6c:
            androidx.customview.widget.ViewDragHelper r0 = r4.mDragHelper
            r0.processTouchEvent(r5)
            r4.mIsBeingDragged = r1
            float r0 = r5.getRawX()
            r4.sX = r0
            float r5 = r5.getRawY()
            r4.sY = r5
            com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout$Status r5 = r4.getOpenStatus()
            com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout$Status r0 = com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.Status.Middle
            if (r5 != r0) goto L89
            r4.mIsBeingDragged = r2
        L89:
            boolean r5 = r4.mIsBeingDragged
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0026  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            boolean r0 = r4.isSwipeEnabled()
            if (r0 != 0) goto Lb
            boolean r5 = super.onTouchEvent(r5)
            return r5
        Lb:
            int r0 = r5.getActionMasked()
            android.view.GestureDetector r1 = r4.gestureDetector
            r1.onTouchEvent(r5)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L2e
            if (r0 == r2) goto L26
            r3 = 2
            if (r0 == r3) goto L3f
            r3 = 3
            if (r0 == r3) goto L26
            androidx.customview.widget.ViewDragHelper r3 = r4.mDragHelper
            r3.processTouchEvent(r5)
            goto L52
        L26:
            r4.mIsBeingDragged = r1
            androidx.customview.widget.ViewDragHelper r3 = r4.mDragHelper
            r3.processTouchEvent(r5)
            goto L52
        L2e:
            androidx.customview.widget.ViewDragHelper r3 = r4.mDragHelper
            r3.processTouchEvent(r5)
            float r3 = r5.getRawX()
            r4.sX = r3
            float r3 = r5.getRawY()
            r4.sY = r3
        L3f:
            r4.checkCanDrag(r5)
            boolean r3 = r4.mIsBeingDragged
            if (r3 == 0) goto L52
            android.view.ViewParent r3 = r4.getParent()
            r3.requestDisallowInterceptTouchEvent(r2)
            androidx.customview.widget.ViewDragHelper r3 = r4.mDragHelper
            r3.processTouchEvent(r5)
        L52:
            boolean r5 = super.onTouchEvent(r5)
            if (r5 != 0) goto L5e
            boolean r5 = r4.mIsBeingDragged
            if (r5 != 0) goto L5e
            if (r0 != 0) goto L5f
        L5e:
            r1 = 1
        L5f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean isClickToClose() {
        return this.mClickToClose;
    }

    public void setClickToClose(boolean z) {
        this.mClickToClose = z;
    }

    public void setSwipeEnabled(boolean z) {
        this.mSwipeEnabled = z;
    }

    public boolean isSwipeEnabled() {
        return this.mSwipeEnabled;
    }

    public boolean isLeftSwipeEnabled() {
        View view = this.mDragEdges.get(DragEdge.Left);
        return view != null && view.getParent() == this && view != getSurfaceView() && this.mSwipesEnabled[DragEdge.Left.ordinal()];
    }

    public void setLeftSwipeEnabled(boolean z) {
        this.mSwipesEnabled[DragEdge.Left.ordinal()] = z;
    }

    public boolean isRightSwipeEnabled() {
        View view = this.mDragEdges.get(DragEdge.Right);
        return view != null && view.getParent() == this && view != getSurfaceView() && this.mSwipesEnabled[DragEdge.Right.ordinal()];
    }

    public void setRightSwipeEnabled(boolean z) {
        this.mSwipesEnabled[DragEdge.Right.ordinal()] = z;
    }

    public boolean isTopSwipeEnabled() {
        View view = this.mDragEdges.get(DragEdge.Top);
        return view != null && view.getParent() == this && view != getSurfaceView() && this.mSwipesEnabled[DragEdge.Top.ordinal()];
    }

    public void setTopSwipeEnabled(boolean z) {
        this.mSwipesEnabled[DragEdge.Top.ordinal()] = z;
    }

    public boolean isBottomSwipeEnabled() {
        View view = this.mDragEdges.get(DragEdge.Bottom);
        return view != null && view.getParent() == this && view != getSurfaceView() && this.mSwipesEnabled[DragEdge.Bottom.ordinal()];
    }

    public void setBottomSwipeEnabled(boolean z) {
        this.mSwipesEnabled[DragEdge.Bottom.ordinal()] = z;
    }

    private boolean insideAdapterView() {
        return getAdapterView() != null;
    }

    private AdapterView getAdapterView() {
        ViewParent parent = getParent();
        if (parent instanceof AdapterView) {
            return (AdapterView) parent;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performAdapterViewItemClick() {
        AdapterView adapterView;
        int positionForView;
        if (getOpenStatus() != Status.Close) {
            return;
        }
        ViewParent parent = getParent();
        if (!(parent instanceof AdapterView) || (positionForView = (adapterView = (AdapterView) parent).getPositionForView(this)) == -1) {
            return;
        }
        adapterView.performItemClick(adapterView.getChildAt(positionForView - adapterView.getFirstVisiblePosition()), positionForView, adapterView.getAdapter().getItemId(positionForView));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0012, code lost:
    
        r0 = (android.widget.AdapterView) r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean performAdapterViewItemLongClick() throws java.lang.NoSuchMethodException, java.lang.SecurityException {
        /*
            r12 = this;
            com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout$Status r0 = r12.getOpenStatus()
            com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout$Status r1 = com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.Status.Close
            r2 = 0
            if (r0 == r1) goto La
            return r2
        La:
            android.view.ViewParent r0 = r12.getParent()
            boolean r1 = r0 instanceof android.widget.AdapterView
            if (r1 == 0) goto L74
            android.widget.AdapterView r0 = (android.widget.AdapterView) r0
            int r6 = r0.getPositionForView(r12)
            r1 = -1
            if (r6 != r1) goto L1c
            return r2
        L1c:
            long r7 = r0.getItemIdAtPosition(r6)
            java.lang.Class<android.widget.AbsListView> r1 = android.widget.AbsListView.class
            java.lang.String r3 = "performLongPress"
            r4 = 3
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch: java.lang.Exception -> L57
            java.lang.Class<android.view.View> r9 = android.view.View.class
            r5[r2] = r9     // Catch: java.lang.Exception -> L57
            java.lang.Class r9 = java.lang.Integer.TYPE     // Catch: java.lang.Exception -> L57
            r10 = 1
            r5[r10] = r9     // Catch: java.lang.Exception -> L57
            java.lang.Class r9 = java.lang.Long.TYPE     // Catch: java.lang.Exception -> L57
            r11 = 2
            r5[r11] = r9     // Catch: java.lang.Exception -> L57
            java.lang.reflect.Method r1 = r1.getDeclaredMethod(r3, r5)     // Catch: java.lang.Exception -> L57
            r1.setAccessible(r10)     // Catch: java.lang.Exception -> L57
            java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch: java.lang.Exception -> L57
            r3[r2] = r12     // Catch: java.lang.Exception -> L57
            java.lang.Integer r4 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Exception -> L57
            r3[r10] = r4     // Catch: java.lang.Exception -> L57
            java.lang.Long r4 = java.lang.Long.valueOf(r7)     // Catch: java.lang.Exception -> L57
            r3[r11] = r4     // Catch: java.lang.Exception -> L57
            java.lang.Object r1 = r1.invoke(r0, r3)     // Catch: java.lang.Exception -> L57
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch: java.lang.Exception -> L57
            boolean r0 = r1.booleanValue()     // Catch: java.lang.Exception -> L57
            goto L73
        L57:
            r1 = move-exception
            r1.printStackTrace()
            android.widget.AdapterView$OnItemLongClickListener r1 = r0.getOnItemLongClickListener()
            if (r1 == 0) goto L6c
            android.widget.AdapterView$OnItemLongClickListener r3 = r0.getOnItemLongClickListener()
            r4 = r0
            r5 = r12
            boolean r1 = r3.onItemLongClick(r4, r5, r6, r7)
            goto L6d
        L6c:
            r1 = 0
        L6d:
            if (r1 == 0) goto L72
            r0.performHapticFeedback(r2)
        L72:
            r0 = r1
        L73:
            return r0
        L74:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.performAdapterViewItemLongClick():boolean");
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (insideAdapterView()) {
            if (this.clickListener == null) {
                setOnClickListener(new View.OnClickListener() { // from class: com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        SwipeLayout.this.performAdapterViewItemClick();
                    }
                });
            }
            if (this.longClickListener == null) {
                setOnLongClickListener(new View.OnLongClickListener() { // from class: com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout.3
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(View view) throws NoSuchMethodException, SecurityException {
                        SwipeLayout.this.performAdapterViewItemLongClick();
                        return true;
                    }
                });
            }
        }
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.clickListener = onClickListener;
    }

    @Override // android.view.View
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        super.setOnLongClickListener(onLongClickListener);
        this.longClickListener = onLongClickListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTouchOnSurface(MotionEvent motionEvent) {
        View surfaceView = getSurfaceView();
        if (surfaceView == null) {
            return false;
        }
        if (this.hitSurfaceRect == null) {
            this.hitSurfaceRect = new Rect();
        }
        surfaceView.getHitRect(this.hitSurfaceRect);
        return this.hitSurfaceRect.contains((int) motionEvent.getX(), (int) motionEvent.getY());
    }

    class SwipeDetector extends GestureDetector.SimpleOnGestureListener {
        SwipeDetector() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (SwipeLayout.this.mClickToClose && SwipeLayout.this.isTouchOnSurface(motionEvent)) {
                SwipeLayout.this.close();
            }
            return super.onSingleTapUp(motionEvent);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (SwipeLayout.this.mDoubleClickListener != null) {
                View currentBottomView = SwipeLayout.this.getCurrentBottomView();
                View surfaceView = SwipeLayout.this.getSurfaceView();
                if (currentBottomView == null || motionEvent.getX() <= currentBottomView.getLeft() || motionEvent.getX() >= currentBottomView.getRight() || motionEvent.getY() <= currentBottomView.getTop() || motionEvent.getY() >= currentBottomView.getBottom()) {
                    currentBottomView = surfaceView;
                }
                SwipeLayout.this.mDoubleClickListener.onDoubleClick(SwipeLayout.this, currentBottomView == surfaceView);
            }
            return true;
        }
    }

    public void setDragDistance(int i) {
        if (i < 0) {
            i = 0;
        }
        this.mDragDistance = dp2px(i);
        requestLayout();
    }

    public void setShowMode(ShowMode showMode) {
        this.mShowMode = showMode;
        requestLayout();
    }

    public DragEdge getDragEdge() {
        return this.mCurrentDragEdge;
    }

    public int getDragDistance() {
        return this.mDragDistance;
    }

    public ShowMode getShowMode() {
        return this.mShowMode;
    }

    public View getSurfaceView() {
        if (getChildCount() == 0) {
            return null;
        }
        return getChildAt(getChildCount() - 1);
    }

    public View getCurrentBottomView() {
        List<View> bottomViews = getBottomViews();
        if (this.mCurrentDragEdge.ordinal() < bottomViews.size()) {
            return bottomViews.get(this.mCurrentDragEdge.ordinal());
        }
        return null;
    }

    public List<View> getBottomViews() {
        ArrayList arrayList = new ArrayList();
        for (DragEdge dragEdge : DragEdge.values()) {
            arrayList.add(this.mDragEdges.get(dragEdge));
        }
        return arrayList;
    }

    public Status getOpenStatus() {
        View surfaceView = getSurfaceView();
        if (surfaceView == null) {
            return Status.Close;
        }
        int left = surfaceView.getLeft();
        int top = surfaceView.getTop();
        if (left == getPaddingLeft() && top == getPaddingTop()) {
            return Status.Close;
        }
        if (left == getPaddingLeft() - this.mDragDistance || left == getPaddingLeft() + this.mDragDistance || top == getPaddingTop() - this.mDragDistance || top == getPaddingTop() + this.mDragDistance) {
            return Status.Open;
        }
        return Status.Middle;
    }

    protected void processHandRelease(float f, float f2, boolean z) {
        float minVelocity = this.mDragHelper.getMinVelocity();
        View surfaceView = getSurfaceView();
        DragEdge dragEdge = this.mCurrentDragEdge;
        if (dragEdge == null || surfaceView == null) {
            return;
        }
        float f3 = z ? 0.25f : 0.75f;
        if (dragEdge == DragEdge.Left) {
            if (f > minVelocity) {
                open();
                return;
            }
            if (f < (-minVelocity)) {
                close();
                return;
            } else if ((getSurfaceView().getLeft() * 1.0f) / this.mDragDistance > f3) {
                open();
                return;
            } else {
                close();
                return;
            }
        }
        if (dragEdge == DragEdge.Right) {
            if (f > minVelocity) {
                close();
                return;
            }
            if (f < (-minVelocity)) {
                open();
                return;
            } else if (((-getSurfaceView().getLeft()) * 1.0f) / this.mDragDistance > f3) {
                open();
                return;
            } else {
                close();
                return;
            }
        }
        if (dragEdge == DragEdge.Top) {
            if (f2 > minVelocity) {
                open();
                return;
            }
            if (f2 < (-minVelocity)) {
                close();
                return;
            } else if ((getSurfaceView().getTop() * 1.0f) / this.mDragDistance > f3) {
                open();
                return;
            } else {
                close();
                return;
            }
        }
        if (dragEdge == DragEdge.Bottom) {
            if (f2 > minVelocity) {
                close();
                return;
            }
            if (f2 < (-minVelocity)) {
                open();
            } else if (((-getSurfaceView().getTop()) * 1.0f) / this.mDragDistance > f3) {
                open();
            } else {
                close();
            }
        }
    }

    public void open() {
        open(true, true);
    }

    public void open(boolean z) {
        open(z, true);
    }

    public void open(boolean z, boolean z2) {
        View surfaceView = getSurfaceView();
        View currentBottomView = getCurrentBottomView();
        if (surfaceView == null) {
            return;
        }
        Rect rectComputeSurfaceLayoutArea = computeSurfaceLayoutArea(true);
        if (z) {
            this.mDragHelper.smoothSlideViewTo(surfaceView, rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top);
        } else {
            int left = rectComputeSurfaceLayoutArea.left - surfaceView.getLeft();
            int top = rectComputeSurfaceLayoutArea.top - surfaceView.getTop();
            surfaceView.layout(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
            if (getShowMode() == ShowMode.PullOut) {
                Rect rectComputeBottomLayoutAreaViaSurface = computeBottomLayoutAreaViaSurface(ShowMode.PullOut, rectComputeSurfaceLayoutArea);
                if (currentBottomView != null) {
                    currentBottomView.layout(rectComputeBottomLayoutAreaViaSurface.left, rectComputeBottomLayoutAreaViaSurface.top, rectComputeBottomLayoutAreaViaSurface.right, rectComputeBottomLayoutAreaViaSurface.bottom);
                }
            }
            if (z2) {
                dispatchRevealEvent(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
                dispatchSwipeEvent(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, left, top);
            } else {
                safeBottomView();
            }
        }
        invalidate();
    }

    public void open(DragEdge dragEdge) {
        setCurrentDragEdge(dragEdge);
        open(true, true);
    }

    public void open(boolean z, DragEdge dragEdge) {
        setCurrentDragEdge(dragEdge);
        open(z, true);
    }

    public void open(boolean z, boolean z2, DragEdge dragEdge) {
        setCurrentDragEdge(dragEdge);
        open(z, z2);
    }

    public void close() {
        close(true, true);
    }

    public void close(boolean z) {
        close(z, true);
    }

    public void close(boolean z, boolean z2) {
        View surfaceView = getSurfaceView();
        if (surfaceView == null) {
            return;
        }
        if (z) {
            this.mDragHelper.smoothSlideViewTo(getSurfaceView(), getPaddingLeft(), getPaddingTop());
        } else {
            Rect rectComputeSurfaceLayoutArea = computeSurfaceLayoutArea(false);
            int left = rectComputeSurfaceLayoutArea.left - surfaceView.getLeft();
            int top = rectComputeSurfaceLayoutArea.top - surfaceView.getTop();
            surfaceView.layout(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
            if (z2) {
                dispatchRevealEvent(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, rectComputeSurfaceLayoutArea.right, rectComputeSurfaceLayoutArea.bottom);
                dispatchSwipeEvent(rectComputeSurfaceLayoutArea.left, rectComputeSurfaceLayoutArea.top, left, top);
            } else {
                safeBottomView();
            }
        }
        invalidate();
    }

    public void toggle() {
        toggle(true);
    }

    public void toggle(boolean z) {
        if (getOpenStatus() == Status.Open) {
            close(z);
        } else if (getOpenStatus() == Status.Close) {
            open(z);
        }
    }

    private Rect computeSurfaceLayoutArea(boolean z) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        if (z) {
            if (this.mCurrentDragEdge == DragEdge.Left) {
                paddingLeft = this.mDragDistance + getPaddingLeft();
            } else if (this.mCurrentDragEdge == DragEdge.Right) {
                paddingLeft = getPaddingLeft() - this.mDragDistance;
            } else if (this.mCurrentDragEdge == DragEdge.Top) {
                paddingTop = this.mDragDistance + getPaddingTop();
            } else {
                paddingTop = getPaddingTop() - this.mDragDistance;
            }
        }
        return new Rect(paddingLeft, paddingTop, getMeasuredWidth() + paddingLeft, getMeasuredHeight() + paddingTop);
    }

    private Rect computeBottomLayoutAreaViaSurface(ShowMode showMode, Rect rect) {
        View currentBottomView = getCurrentBottomView();
        int i = rect.left;
        int i2 = rect.top;
        int measuredWidth = rect.right;
        int measuredHeight = rect.bottom;
        if (showMode == ShowMode.PullOut) {
            if (this.mCurrentDragEdge == DragEdge.Left) {
                i = rect.left - this.mDragDistance;
            } else if (this.mCurrentDragEdge == DragEdge.Right) {
                i = rect.right;
            } else if (this.mCurrentDragEdge == DragEdge.Top) {
                i2 = rect.top - this.mDragDistance;
            } else {
                i2 = rect.bottom;
            }
            if (this.mCurrentDragEdge == DragEdge.Left || this.mCurrentDragEdge == DragEdge.Right) {
                int i3 = rect.bottom;
                measuredWidth = i + (currentBottomView != null ? currentBottomView.getMeasuredWidth() : 0);
                measuredHeight = i3;
            } else {
                measuredHeight = (currentBottomView != null ? currentBottomView.getMeasuredHeight() : 0) + i2;
                measuredWidth = rect.right;
            }
        } else if (showMode == ShowMode.LayDown) {
            if (this.mCurrentDragEdge == DragEdge.Left) {
                measuredWidth = i + this.mDragDistance;
            } else if (this.mCurrentDragEdge == DragEdge.Right) {
                i = measuredWidth - this.mDragDistance;
            } else if (this.mCurrentDragEdge == DragEdge.Top) {
                measuredHeight = i2 + this.mDragDistance;
            } else {
                i2 = measuredHeight - this.mDragDistance;
            }
        }
        return new Rect(i, i2, measuredWidth, measuredHeight);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Rect computeBottomLayDown(DragEdge dragEdge) {
        int measuredWidth;
        int measuredHeight;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        if (dragEdge == DragEdge.Right) {
            paddingLeft = getMeasuredWidth() - this.mDragDistance;
        } else if (dragEdge == DragEdge.Bottom) {
            paddingTop = getMeasuredHeight() - this.mDragDistance;
        }
        if (dragEdge == DragEdge.Left || dragEdge == DragEdge.Right) {
            measuredWidth = this.mDragDistance + paddingLeft;
            measuredHeight = getMeasuredHeight();
        } else {
            measuredWidth = getMeasuredWidth() + paddingLeft;
            measuredHeight = this.mDragDistance;
        }
        return new Rect(paddingLeft, paddingTop, measuredWidth, measuredHeight + paddingTop);
    }

    public void setOnDoubleClickListener(DoubleClickListener doubleClickListener) {
        this.mDoubleClickListener = doubleClickListener;
    }

    private int dp2px(float f) {
        return (int) ((f * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Deprecated
    public void setDragEdge(DragEdge dragEdge) {
        clearDragEdge();
        if (getChildCount() >= 2) {
            this.mDragEdges.put(dragEdge, getChildAt(getChildCount() - 2));
        }
        setCurrentDragEdge(dragEdge);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public void onViewRemoved(View view) {
        for (Map.Entry entry : new HashMap(this.mDragEdges).entrySet()) {
            if (entry.getValue() == view) {
                this.mDragEdges.remove(entry.getKey());
            }
        }
    }

    public Map<DragEdge, View> getDragEdgeMap() {
        return this.mDragEdges;
    }

    @Deprecated
    public List<DragEdge> getDragEdges() {
        return new ArrayList(this.mDragEdges.keySet());
    }

    @Deprecated
    public void setDragEdges(List<DragEdge> list) {
        clearDragEdge();
        int iMin = Math.min(list.size(), getChildCount() - 1);
        for (int i = 0; i < iMin; i++) {
            this.mDragEdges.put(list.get(i), getChildAt(i));
        }
        if (list.size() == 0 || list.contains(DefaultDragEdge)) {
            setCurrentDragEdge(DefaultDragEdge);
        } else {
            setCurrentDragEdge(list.get(0));
        }
    }

    @Deprecated
    public void setDragEdges(DragEdge... dragEdgeArr) {
        clearDragEdge();
        setDragEdges(Arrays.asList(dragEdgeArr));
    }

    @Deprecated
    public void setBottomViewIds(int i, int i2, int i3, int i4) {
        addDrag(DragEdge.Left, findViewById(i));
        addDrag(DragEdge.Right, findViewById(i2));
        addDrag(DragEdge.Top, findViewById(i3));
        addDrag(DragEdge.Bottom, findViewById(i4));
    }

    private float getCurrentOffset() {
        DragEdge dragEdge = this.mCurrentDragEdge;
        if (dragEdge == null) {
            return 0.0f;
        }
        return this.mEdgeSwipesOffset[dragEdge.ordinal()];
    }

    private void setCurrentDragEdge(DragEdge dragEdge) {
        this.mCurrentDragEdge = dragEdge;
        updateBottomViews();
    }

    private void updateBottomViews() {
        View currentBottomView = getCurrentBottomView();
        if (currentBottomView != null) {
            if (this.mCurrentDragEdge == DragEdge.Left || this.mCurrentDragEdge == DragEdge.Right) {
                this.mDragDistance = currentBottomView.getMeasuredWidth() - dp2px(getCurrentOffset());
            } else {
                this.mDragDistance = currentBottomView.getMeasuredHeight() - dp2px(getCurrentOffset());
            }
        }
        if (this.mShowMode == ShowMode.PullOut) {
            layoutPullOut();
        } else if (this.mShowMode == ShowMode.LayDown) {
            layoutLayDown();
        }
        safeBottomView();
    }
}
