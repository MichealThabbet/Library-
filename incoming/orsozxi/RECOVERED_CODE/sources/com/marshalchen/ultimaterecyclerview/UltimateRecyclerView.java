package com.marshalchen.ultimaterecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.marshalchen.ultimaterecyclerview.ui.DividerItemDecoration;
import com.marshalchen.ultimaterecyclerview.ui.VerticalSwipeRefreshLayout;
import com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionButton;
import com.marshalchen.ultimaterecyclerview.ui.floatingactionbutton.FloatingActionsMenu;
import com.marshalchen.ultimaterecyclerview.uiUtils.SavedStateScrolling;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

/* loaded from: classes2.dex */
public class UltimateRecyclerView extends FrameLayout implements Scrollable {
    private static final int SCROLLBARS_HORIZONTAL = 2;
    private static final int SCROLLBARS_NONE = 0;
    private static final int SCROLLBARS_VERTICAL = 1;
    private static boolean isParallaxHeader = false;
    private final float SCROLL_MULTIPLIER;
    protected FloatingActionButton defaultFloatingActionButton;
    protected int[] defaultSwipeToDismissColors;
    private boolean isLoadingMore;
    private int lastVisibleItemPosition;
    protected LAYOUT_MANAGER_TYPE layoutManagerType;
    private UltimateViewAdapter mAdapter;
    private ObservableScrollViewCallbacks mCallbacks;
    private SparseIntArray mChildrenHeights;
    protected boolean mClipToPadding;
    private boolean mDragging;
    protected ViewStub mEmpty;
    protected int mEmptyId;
    protected View mEmptyView;
    private boolean mFirstScroll;
    private int mFirstVisibleItem;
    protected int mFloatingButtonId;
    protected View mFloatingButtonView;
    protected ViewStub mFloatingButtonViewStub;
    private CustomRelativeWrapper mHeader;
    private boolean mIntercepted;
    private boolean mIsLoadMoreWidgetEnabled;
    private ObservableScrollState mObservableScrollState;
    protected RecyclerView.OnScrollListener mOnScrollListener;
    protected int mPadding;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected int mPaddingTop;
    private OnParallaxScroll mParallaxScroll;
    private int mPrevFirstVisibleChildHeight;
    private int mPrevFirstVisiblePosition;
    private MotionEvent mPrevMoveEvent;
    private int mPrevScrollY;
    private int mPrevScrolledChildrenHeight;
    public RecyclerView mRecyclerView;
    private int mScrollY;
    private int mScrollbarsStyle;
    public VerticalSwipeRefreshLayout mSwipeRefreshLayout;
    private int mTotalItemCount;
    private int mTotalYScrolled;
    private ViewGroup mTouchInterceptionViewGroup;
    private int mVisibleItemCount;
    private OnLoadMoreListener onLoadMoreListener;
    private int previousTotal;
    public int showLoadMoreItemNum;

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    public interface OnLoadMoreListener {
        void loadMore(int i, int i2);
    }

    public interface OnParallaxScroll {
        void onParallaxScroll(float f, float f2, View view);
    }

    public UltimateRecyclerView(Context context) {
        super(context);
        this.isLoadingMore = false;
        this.mPrevFirstVisibleChildHeight = -1;
        this.mChildrenHeights = new SparseIntArray();
        this.defaultSwipeToDismissColors = null;
        this.showLoadMoreItemNum = 3;
        this.mVisibleItemCount = 0;
        this.mTotalItemCount = 0;
        this.previousTotal = 0;
        this.SCROLL_MULTIPLIER = 0.5f;
        initViews();
    }

    public UltimateRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isLoadingMore = false;
        this.mPrevFirstVisibleChildHeight = -1;
        this.mChildrenHeights = new SparseIntArray();
        this.defaultSwipeToDismissColors = null;
        this.showLoadMoreItemNum = 3;
        this.mVisibleItemCount = 0;
        this.mTotalItemCount = 0;
        this.previousTotal = 0;
        this.SCROLL_MULTIPLIER = 0.5f;
        initAttrs(attributeSet);
        initViews();
    }

    public UltimateRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isLoadingMore = false;
        this.mPrevFirstVisibleChildHeight = -1;
        this.mChildrenHeights = new SparseIntArray();
        this.defaultSwipeToDismissColors = null;
        this.showLoadMoreItemNum = 3;
        this.mVisibleItemCount = 0;
        this.mTotalItemCount = 0;
        this.previousTotal = 0;
        this.SCROLL_MULTIPLIER = 0.5f;
        initAttrs(attributeSet);
        initViews();
    }

    public void setRecylerViewBackgroundColor(int i) {
        this.mRecyclerView.setBackgroundColor(i);
    }

    protected void initViews() {
        View viewInflate = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.ultimate_recycler_view_layout, this);
        this.mRecyclerView = (RecyclerView) viewInflate.findViewById(R.id.ultimate_list);
        this.mSwipeRefreshLayout = (VerticalSwipeRefreshLayout) viewInflate.findViewById(R.id.swipe_refresh_layout);
        setScrollbars();
        this.mSwipeRefreshLayout.setEnabled(false);
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.setClipToPadding(this.mClipToPadding);
            int i = this.mPadding;
            if (i != -1.1f) {
                this.mRecyclerView.setPadding(i, i, i, i);
            } else {
                this.mRecyclerView.setPadding(this.mPaddingLeft, this.mPaddingTop, this.mPaddingRight, this.mPaddingBottom);
            }
        }
        this.defaultFloatingActionButton = (FloatingActionButton) viewInflate.findViewById(R.id.defaultFloatingActionButton);
        setDefaultScrollListener();
        this.mEmpty = (ViewStub) viewInflate.findViewById(R.id.emptyview);
        this.mFloatingButtonViewStub = (ViewStub) viewInflate.findViewById(R.id.floatingActionViewStub);
        this.mEmpty.setLayoutResource(this.mEmptyId);
        this.mFloatingButtonViewStub.setLayoutResource(this.mFloatingButtonId);
        if (this.mEmptyId != 0) {
            this.mEmptyView = this.mEmpty.inflate();
        }
        this.mEmpty.setVisibility(8);
    }

    public View getEmptyView() {
        return this.mEmptyView;
    }

    public void setEmptyView(int i) {
        this.mEmptyId = i;
        this.mEmpty.setLayoutResource(i);
        if (this.mEmptyId != 0) {
            this.mEmptyView = this.mEmpty.inflate();
        }
        this.mEmpty.setVisibility(8);
    }

    public void showEmptyView() {
        if (this.mEmptyId != 0) {
            this.mEmpty.setVisibility(0);
        }
    }

    public void hideEmptyView() {
        if (this.mEmptyId != 0) {
            this.mEmpty.setVisibility(8);
        }
    }

    public void showFloatingButtonView() {
        if (this.mFloatingButtonId != 0) {
            View viewInflate = this.mFloatingButtonViewStub.inflate();
            this.mFloatingButtonView = viewInflate;
            viewInflate.setVisibility(0);
        }
    }

    protected void setScrollbars() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        int i = this.mScrollbarsStyle;
        if (i == 1) {
            this.mSwipeRefreshLayout.removeView(this.mRecyclerView);
            this.mRecyclerView = (RecyclerView) layoutInflater.inflate(R.layout.vertical_recycler_view, (ViewGroup) this.mSwipeRefreshLayout, true).findViewById(R.id.ultimate_list);
        } else {
            if (i != 2) {
                return;
            }
            this.mSwipeRefreshLayout.removeView(this.mRecyclerView);
            this.mRecyclerView = (RecyclerView) layoutInflater.inflate(R.layout.horizontal_recycler_view, (ViewGroup) this.mSwipeRefreshLayout, true).findViewById(R.id.ultimate_list);
        }
    }

    protected void initAttrs(AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.UltimateRecyclerview);
        try {
            this.mPadding = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPadding, -1.1f);
            this.mPaddingTop = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingTop, 0.0f);
            this.mPaddingBottom = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingBottom, 0.0f);
            this.mPaddingLeft = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingLeft, 0.0f);
            this.mPaddingRight = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingRight, 0.0f);
            this.mClipToPadding = typedArrayObtainStyledAttributes.getBoolean(R.styleable.UltimateRecyclerview_recyclerviewClipToPadding, false);
            this.mEmptyId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.UltimateRecyclerview_recyclerviewEmptyView, 0);
            this.mFloatingButtonId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.UltimateRecyclerview_recyclerviewFloatingActionView, 0);
            this.mScrollbarsStyle = typedArrayObtainStyledAttributes.getInt(R.styleable.UltimateRecyclerview_recyclerviewScrollbars, 0);
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.UltimateRecyclerview_recyclerviewDefaultSwipeColor, 0);
            if (resourceId != 0) {
                this.defaultSwipeToDismissColors = getResources().getIntArray(resourceId);
            }
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    protected void setDefaultScrollListener() {
        this.mRecyclerView.removeOnScrollListener(this.mOnScrollListener);
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() { // from class: com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (UltimateRecyclerView.this.mHeader != null) {
                    UltimateRecyclerView.this.mTotalYScrolled += i2;
                    if (UltimateRecyclerView.isParallaxHeader) {
                        UltimateRecyclerView.this.translateHeader(r3.mTotalYScrolled);
                    }
                }
                UltimateRecyclerView.this.enableShoworHideToolbarAndFloatingButton(recyclerView);
            }
        };
        this.mOnScrollListener = onScrollListener;
        this.mRecyclerView.addOnScrollListener(onScrollListener);
    }

    private void setObserableScrollListener() {
        this.mRecyclerView.removeOnScrollListener(this.mOnScrollListener);
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() { // from class: com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                UltimateRecyclerView.this.enableShoworHideToolbarAndFloatingButton(recyclerView);
            }
        };
        this.mOnScrollListener = onScrollListener;
        this.mRecyclerView.addOnScrollListener(onScrollListener);
    }

    public void enableLoadmore() {
        this.mRecyclerView.removeOnScrollListener(this.mOnScrollListener);
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() { // from class: com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.3
            private int[] lastPositions;

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (UltimateRecyclerView.this.mHeader != null) {
                    UltimateRecyclerView.this.mTotalYScrolled += i2;
                    if (UltimateRecyclerView.isParallaxHeader) {
                        UltimateRecyclerView.this.translateHeader(r4.mTotalYScrolled);
                    }
                }
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (UltimateRecyclerView.this.layoutManagerType == null) {
                    if (layoutManager instanceof GridLayoutManager) {
                        UltimateRecyclerView.this.layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        UltimateRecyclerView.this.layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        UltimateRecyclerView.this.layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
                    } else {
                        throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
                    }
                }
                int i3 = AnonymousClass9.$SwitchMap$com$marshalchen$ultimaterecyclerview$UltimateRecyclerView$LAYOUT_MANAGER_TYPE[UltimateRecyclerView.this.layoutManagerType.ordinal()];
                if (i3 != 1) {
                    if (i3 != 2) {
                        if (i3 == 3) {
                            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                            if (this.lastPositions == null) {
                                this.lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                            }
                            staggeredGridLayoutManager.findLastVisibleItemPositions(this.lastPositions);
                            UltimateRecyclerView ultimateRecyclerView = UltimateRecyclerView.this;
                            ultimateRecyclerView.lastVisibleItemPosition = ultimateRecyclerView.findMax(this.lastPositions);
                            staggeredGridLayoutManager.findFirstVisibleItemPositions(this.lastPositions);
                            UltimateRecyclerView ultimateRecyclerView2 = UltimateRecyclerView.this;
                            ultimateRecyclerView2.mFirstVisibleItem = ultimateRecyclerView2.findMin(this.lastPositions);
                        }
                    }
                    if (UltimateRecyclerView.this.isLoadingMore && UltimateRecyclerView.this.mTotalItemCount > UltimateRecyclerView.this.previousTotal) {
                        UltimateRecyclerView.this.isLoadingMore = false;
                        UltimateRecyclerView ultimateRecyclerView3 = UltimateRecyclerView.this;
                        ultimateRecyclerView3.previousTotal = ultimateRecyclerView3.mTotalItemCount;
                    }
                    if (!UltimateRecyclerView.this.isLoadingMore && UltimateRecyclerView.this.mTotalItemCount - UltimateRecyclerView.this.mVisibleItemCount <= UltimateRecyclerView.this.mFirstVisibleItem) {
                        UltimateRecyclerView.this.onLoadMoreListener.loadMore(UltimateRecyclerView.this.mRecyclerView.getAdapter().getItemCount(), UltimateRecyclerView.this.lastVisibleItemPosition);
                        UltimateRecyclerView.this.isLoadingMore = true;
                        UltimateRecyclerView ultimateRecyclerView4 = UltimateRecyclerView.this;
                        ultimateRecyclerView4.previousTotal = ultimateRecyclerView4.mTotalItemCount;
                    }
                    UltimateRecyclerView.this.enableShoworHideToolbarAndFloatingButton(recyclerView);
                }
                UltimateRecyclerView.this.mVisibleItemCount = layoutManager.getChildCount();
                UltimateRecyclerView.this.mTotalItemCount = layoutManager.getItemCount();
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                UltimateRecyclerView.this.lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                UltimateRecyclerView.this.mFirstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                if (UltimateRecyclerView.this.isLoadingMore) {
                    UltimateRecyclerView.this.isLoadingMore = false;
                    UltimateRecyclerView ultimateRecyclerView32 = UltimateRecyclerView.this;
                    ultimateRecyclerView32.previousTotal = ultimateRecyclerView32.mTotalItemCount;
                }
                if (!UltimateRecyclerView.this.isLoadingMore) {
                    UltimateRecyclerView.this.onLoadMoreListener.loadMore(UltimateRecyclerView.this.mRecyclerView.getAdapter().getItemCount(), UltimateRecyclerView.this.lastVisibleItemPosition);
                    UltimateRecyclerView.this.isLoadingMore = true;
                    UltimateRecyclerView ultimateRecyclerView42 = UltimateRecyclerView.this;
                    ultimateRecyclerView42.previousTotal = ultimateRecyclerView42.mTotalItemCount;
                }
                UltimateRecyclerView.this.enableShoworHideToolbarAndFloatingButton(recyclerView);
            }
        };
        this.mOnScrollListener = onScrollListener;
        this.mRecyclerView.addOnScrollListener(onScrollListener);
        UltimateViewAdapter ultimateViewAdapter = this.mAdapter;
        if (ultimateViewAdapter != null && ultimateViewAdapter.getCustomLoadMoreView() == null) {
            this.mAdapter.setCustomLoadMoreView(LayoutInflater.from(getContext()).inflate(R.layout.bottom_progressbar, (ViewGroup) null));
        }
        this.mIsLoadMoreWidgetEnabled = true;
    }

    /* renamed from: com.marshalchen.ultimaterecyclerview.UltimateRecyclerView$9, reason: invalid class name */
    static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$com$marshalchen$ultimaterecyclerview$UltimateRecyclerView$LAYOUT_MANAGER_TYPE;

        static {
            int[] iArr = new int[LAYOUT_MANAGER_TYPE.values().length];
            $SwitchMap$com$marshalchen$ultimaterecyclerview$UltimateRecyclerView$LAYOUT_MANAGER_TYPE = iArr;
            try {
                iArr[LAYOUT_MANAGER_TYPE.LINEAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$marshalchen$ultimaterecyclerview$UltimateRecyclerView$LAYOUT_MANAGER_TYPE[LAYOUT_MANAGER_TYPE.GRID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$marshalchen$ultimaterecyclerview$UltimateRecyclerView$LAYOUT_MANAGER_TYPE[LAYOUT_MANAGER_TYPE.STAGGERED_GRID.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void reenableLoadmore() {
        enableLoadmore();
        UltimateViewAdapter ultimateViewAdapter = this.mAdapter;
        if (ultimateViewAdapter != null) {
            ultimateViewAdapter.setCustomLoadMoreView(LayoutInflater.from(getContext()).inflate(R.layout.bottom_progressbar, (ViewGroup) null));
            this.mAdapter.isLoadMoreChanged = false;
        }
        this.mIsLoadMoreWidgetEnabled = true;
    }

    public void reenableLoadmore(View view) {
        enableLoadmore();
        UltimateViewAdapter ultimateViewAdapter = this.mAdapter;
        if (ultimateViewAdapter != null) {
            ultimateViewAdapter.setCustomLoadMoreView(view);
            this.mAdapter.isLoadMoreChanged = false;
        }
        this.mIsLoadMoreWidgetEnabled = true;
    }

    public boolean isLoadMoreEnabled() {
        return this.mIsLoadMoreWidgetEnabled;
    }

    public void disableLoadmore() {
        setDefaultScrollListener();
        UltimateViewAdapter ultimateViewAdapter = this.mAdapter;
        if (ultimateViewAdapter != null) {
            ultimateViewAdapter.swipeCustomLoadMoreView(LayoutInflater.from(getContext()).inflate(R.layout.empty_progressbar, (ViewGroup) null));
        }
        this.mIsLoadMoreWidgetEnabled = false;
    }

    protected void enableShoworHideToolbarAndFloatingButton(RecyclerView recyclerView) {
        int i;
        int height;
        int i2;
        int height2;
        if (this.mCallbacks == null || getChildCount() <= 0) {
            return;
        }
        int childAdapterPosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
        int childAdapterPosition2 = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(recyclerView.getChildCount() - 1));
        int i3 = childAdapterPosition;
        int i4 = 0;
        while (i3 <= childAdapterPosition2) {
            try {
                View childAt = recyclerView.getChildAt(i4);
                this.mChildrenHeights.put(i3, ((this.mChildrenHeights.indexOfKey(i3) < 0 || !(childAt == null || childAt.getHeight() == this.mChildrenHeights.get(i3))) && childAt != null) ? childAt.getHeight() : 0);
                i3++;
                i4++;
            } catch (NullPointerException e) {
                e.printStackTrace();
                URLogs.e(e, "");
            }
        }
        View childAt2 = recyclerView.getChildAt(0);
        if (childAt2 != null) {
            int i5 = this.mPrevFirstVisiblePosition;
            if (i5 < childAdapterPosition) {
                if (childAdapterPosition - i5 != 1) {
                    i2 = 0;
                    for (int i6 = childAdapterPosition - 1; i6 > this.mPrevFirstVisiblePosition; i6--) {
                        if (this.mChildrenHeights.indexOfKey(i6) > 0) {
                            height2 = this.mChildrenHeights.get(i6);
                        } else {
                            height2 = childAt2.getHeight();
                        }
                        i2 += height2;
                    }
                } else {
                    i2 = 0;
                }
                this.mPrevScrolledChildrenHeight += this.mPrevFirstVisibleChildHeight + i2;
                this.mPrevFirstVisibleChildHeight = childAt2.getHeight();
            } else if (childAdapterPosition < i5) {
                if (i5 - childAdapterPosition != 1) {
                    i = 0;
                    for (int i7 = i5 - 1; i7 > childAdapterPosition; i7--) {
                        if (this.mChildrenHeights.indexOfKey(i7) > 0) {
                            height = this.mChildrenHeights.get(i7);
                        } else {
                            height = childAt2.getHeight();
                        }
                        i += height;
                    }
                } else {
                    i = 0;
                }
                this.mPrevScrolledChildrenHeight -= childAt2.getHeight() + i;
                this.mPrevFirstVisibleChildHeight = childAt2.getHeight();
            } else if (childAdapterPosition == 0) {
                this.mPrevFirstVisibleChildHeight = childAt2.getHeight();
                this.mPrevScrolledChildrenHeight = 0;
            }
            if (this.mPrevFirstVisibleChildHeight < 0) {
                this.mPrevFirstVisibleChildHeight = 0;
            }
            int top = this.mPrevScrolledChildrenHeight - childAt2.getTop();
            this.mScrollY = top;
            this.mPrevFirstVisiblePosition = childAdapterPosition;
            this.mCallbacks.onScrollChanged(top, this.mFirstScroll, this.mDragging);
            int i8 = this.mPrevScrollY;
            int i9 = this.mScrollY;
            if (i8 < i9) {
                if (this.mFirstScroll) {
                    this.mFirstScroll = false;
                    this.mObservableScrollState = ObservableScrollState.STOP;
                }
                this.mObservableScrollState = ObservableScrollState.UP;
            } else if (i9 < i8) {
                this.mObservableScrollState = ObservableScrollState.DOWN;
            } else {
                this.mObservableScrollState = ObservableScrollState.STOP;
            }
            if (this.mFirstScroll) {
                this.mFirstScroll = false;
            }
            this.mPrevScrollY = this.mScrollY;
        }
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.mRecyclerView.setOnScrollListener(onScrollListener);
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.mRecyclerView.addOnScrollListener(onScrollListener);
    }

    public void removeOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.mRecyclerView.removeOnScrollListener(onScrollListener);
    }

    public void addItemDividerDecoration(Context context) {
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(context, 1));
    }

    public void swapAdapter(UltimateViewAdapter ultimateViewAdapter, boolean z) {
        this.mRecyclerView.swapAdapter(ultimateViewAdapter, z);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.mRecyclerView.addItemDecoration(itemDecoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int i) {
        this.mRecyclerView.addItemDecoration(itemDecoration, i);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        this.mRecyclerView.setItemAnimator(itemAnimator);
    }

    public RecyclerView.ItemAnimator getItemAnimator() {
        return this.mRecyclerView.getItemAnimator();
    }

    public void setDefaultOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        this.mSwipeRefreshLayout.setEnabled(true);
        int[] iArr = this.defaultSwipeToDismissColors;
        if (iArr != null && iArr.length > 0) {
            this.mSwipeRefreshLayout.setColorSchemeColors(iArr);
        } else {
            this.mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        }
        this.mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    public void setDefaultSwipeToRefreshColorScheme(int... iArr) {
        this.mSwipeRefreshLayout.setColorSchemeColors(iArr);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mRecyclerView.setLayoutManager(layoutManager);
    }

    public RecyclerView.Adapter getAdapter() {
        return this.mRecyclerView.getAdapter();
    }

    public void setAdapter(UltimateViewAdapter ultimateViewAdapter) {
        this.mAdapter = ultimateViewAdapter;
        this.mRecyclerView.setAdapter(ultimateViewAdapter);
        VerticalSwipeRefreshLayout verticalSwipeRefreshLayout = this.mSwipeRefreshLayout;
        if (verticalSwipeRefreshLayout != null) {
            verticalSwipeRefreshLayout.setRefreshing(false);
        }
        UltimateViewAdapter ultimateViewAdapter2 = this.mAdapter;
        if (ultimateViewAdapter2 != null) {
            ultimateViewAdapter2.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // from class: com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.4
                @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public void onItemRangeChanged(int i, int i2) {
                    super.onItemRangeChanged(i, i2);
                    UltimateRecyclerView.this.updateHelperDisplays();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public void onItemRangeInserted(int i, int i2) {
                    super.onItemRangeInserted(i, i2);
                    UltimateRecyclerView.this.updateHelperDisplays();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public void onItemRangeRemoved(int i, int i2) {
                    super.onItemRangeRemoved(i, i2);
                    UltimateRecyclerView.this.updateHelperDisplays();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public void onItemRangeMoved(int i, int i2, int i3) {
                    super.onItemRangeMoved(i, i2, i3);
                    UltimateRecyclerView.this.updateHelperDisplays();
                }

                @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public void onChanged() {
                    super.onChanged();
                    UltimateRecyclerView.this.updateHelperDisplays();
                }
            });
        }
        if ((ultimateViewAdapter == null || this.mAdapter.getAdapterItemCount() == 0) && this.mEmptyId != 0) {
            this.mEmpty.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHelperDisplays() {
        this.isLoadingMore = false;
        VerticalSwipeRefreshLayout verticalSwipeRefreshLayout = this.mSwipeRefreshLayout;
        if (verticalSwipeRefreshLayout != null) {
            verticalSwipeRefreshLayout.setRefreshing(false);
        }
        UltimateViewAdapter ultimateViewAdapter = this.mAdapter;
        if (ultimateViewAdapter == null) {
            return;
        }
        if (ultimateViewAdapter.getAdapterItemCount() == 0) {
            this.mEmpty.setVisibility(this.mEmptyId != 0 ? 0 : 8);
        } else if (this.mEmptyId != 0) {
            this.mEmpty.setVisibility(8);
        }
        if (this.mAdapter.getCustomLoadMoreView() == null) {
            return;
        }
        if (this.mAdapter.getAdapterItemCount() >= this.showLoadMoreItemNum && this.mAdapter.getCustomLoadMoreView().getVisibility() == 8) {
            this.mAdapter.getCustomLoadMoreView().setVisibility(0);
        }
        if (this.mAdapter.getAdapterItemCount() < this.showLoadMoreItemNum) {
            this.mAdapter.getCustomLoadMoreView().setVisibility(8);
        }
    }

    @Deprecated
    public void setAdapter(RecyclerView.Adapter adapter) {
        this.mRecyclerView.setAdapter(adapter);
        VerticalSwipeRefreshLayout verticalSwipeRefreshLayout = this.mSwipeRefreshLayout;
        if (verticalSwipeRefreshLayout != null) {
            verticalSwipeRefreshLayout.setRefreshing(false);
        }
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // from class: com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.5
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i, int i2) {
                super.onItemRangeChanged(i, i2);
                update();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeInserted(int i, int i2) {
                super.onItemRangeInserted(i, i2);
                update();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int i, int i2) {
                super.onItemRangeRemoved(i, i2);
                update();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeMoved(int i, int i2, int i3) {
                super.onItemRangeMoved(i, i2, i3);
                update();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                super.onChanged();
                update();
            }

            private void update() {
                UltimateRecyclerView.this.isLoadingMore = false;
                if (UltimateRecyclerView.this.mSwipeRefreshLayout != null) {
                    UltimateRecyclerView.this.mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    public void setHasFixedSize(boolean z) {
        this.mRecyclerView.setHasFixedSize(z);
    }

    public void setRefreshing(boolean z) {
        VerticalSwipeRefreshLayout verticalSwipeRefreshLayout = this.mSwipeRefreshLayout;
        if (verticalSwipeRefreshLayout != null) {
            verticalSwipeRefreshLayout.setRefreshing(z);
        }
    }

    public void enableDefaultSwipeRefresh(boolean z) {
        VerticalSwipeRefreshLayout verticalSwipeRefreshLayout = this.mSwipeRefreshLayout;
        if (verticalSwipeRefreshLayout != null) {
            verticalSwipeRefreshLayout.setEnabled(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findMax(int[] iArr) {
        int i = Integer.MIN_VALUE;
        for (int i2 : iArr) {
            if (i2 > i) {
                i = i2;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findMin(int[] iArr) {
        int i = Integer.MAX_VALUE;
        for (int i2 : iArr) {
            if (i2 != -1 && i2 < i) {
                i = i2;
            }
        }
        return i;
    }

    public void setParallaxHeader(int i) {
        setParallaxHeader(LayoutInflater.from(getContext()).inflate(i, (ViewGroup) null));
    }

    public void setParallaxHeader(View view) {
        CustomRelativeWrapper customRelativeWrapper = new CustomRelativeWrapper(view.getContext());
        this.mHeader = customRelativeWrapper;
        customRelativeWrapper.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.mHeader.addView(view, new RelativeLayout.LayoutParams(-1, -1));
        UltimateViewAdapter ultimateViewAdapter = this.mAdapter;
        if (ultimateViewAdapter != null) {
            ultimateViewAdapter.setCustomHeaderView(this.mHeader);
        }
        isParallaxHeader = true;
    }

    public void setNormalHeader(View view) {
        setParallaxHeader(view);
        isParallaxHeader = false;
    }

    public void setOnParallaxScroll(OnParallaxScroll onParallaxScroll) {
        this.mParallaxScroll = onParallaxScroll;
        onParallaxScroll.onParallaxScroll(0.0f, 0.0f, this.mHeader);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void translateHeader(float f) {
        float f2 = f * 0.5f;
        if (Build.VERSION.SDK_INT >= 11) {
            this.mHeader.setTranslationY(f2);
        } else {
            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, f2, f2);
            translateAnimation.setFillAfter(true);
            translateAnimation.setDuration(0L);
            this.mHeader.startAnimation(translateAnimation);
        }
        this.mHeader.setClipY(Math.round(f2));
        if (this.mParallaxScroll != null) {
            this.mParallaxScroll.onParallaxScroll(Math.min(1.0f, f2 / (this.mHeader.getHeight() * 0.5f)), f, this.mHeader);
        }
    }

    public static class CustomRelativeWrapper extends RelativeLayout {
        private int mOffset;

        public CustomRelativeWrapper(Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            if (UltimateRecyclerView.isParallaxHeader) {
                canvas.clipRect(new Rect(getLeft(), getTop(), getRight(), getBottom() + this.mOffset));
            }
            super.dispatchDraw(canvas);
        }

        public void setClipY(int i) {
            this.mOffset = i;
            invalidate();
        }
    }

    @Override // com.marshalchen.ultimaterecyclerview.Scrollable
    public void setScrollViewCallbacks(ObservableScrollViewCallbacks observableScrollViewCallbacks) {
        this.mCallbacks = observableScrollViewCallbacks;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedStateScrolling savedStateScrolling = (SavedStateScrolling) parcelable;
        this.mPrevFirstVisiblePosition = savedStateScrolling.prevFirstVisiblePosition;
        this.mPrevFirstVisibleChildHeight = savedStateScrolling.prevFirstVisibleChildHeight;
        this.mPrevScrolledChildrenHeight = savedStateScrolling.prevScrolledChildrenHeight;
        this.mPrevScrollY = savedStateScrolling.prevScrollY;
        this.mScrollY = savedStateScrolling.scrollY;
        this.mChildrenHeights = savedStateScrolling.childrenHeights;
        super.onRestoreInstanceState(savedStateScrolling.getSuperState());
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedStateScrolling savedStateScrolling = new SavedStateScrolling(super.onSaveInstanceState());
        savedStateScrolling.prevFirstVisiblePosition = this.mPrevFirstVisiblePosition;
        savedStateScrolling.prevFirstVisibleChildHeight = this.mPrevFirstVisibleChildHeight;
        savedStateScrolling.prevScrolledChildrenHeight = this.mPrevScrolledChildrenHeight;
        savedStateScrolling.prevScrollY = this.mPrevScrollY;
        savedStateScrolling.scrollY = this.mScrollY;
        savedStateScrolling.childrenHeights = this.mChildrenHeights;
        return savedStateScrolling;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mCallbacks != null) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                this.mDragging = true;
                this.mFirstScroll = true;
                this.mCallbacks.onDownMotionEvent();
            } else if (actionMasked == 1 || actionMasked == 3) {
                this.mIntercepted = false;
                this.mDragging = false;
                this.mCallbacks.onUpOrCancelMotionEvent(this.mObservableScrollState);
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // com.marshalchen.ultimaterecyclerview.Scrollable
    public void setTouchInterceptionViewGroup(ViewGroup viewGroup) {
        this.mTouchInterceptionViewGroup = viewGroup;
        setObserableScrollListener();
    }

    @Override // com.marshalchen.ultimaterecyclerview.Scrollable
    public void scrollVerticallyTo(int i) {
        URLogs.d("vertically");
        View childAt = getChildAt(0);
        if (childAt != null) {
            scrollVerticallyToPosition(i / childAt.getHeight());
        }
    }

    public void scrollVerticallyToPosition(int i) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null && (layoutManager instanceof LinearLayoutManager)) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(i, 0);
        } else {
            layoutManager.scrollToPosition(i);
        }
    }

    @Override // com.marshalchen.ultimaterecyclerview.Scrollable
    public int getCurrentScrollY() {
        return this.mScrollY;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x009c  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r9) {
        /*
            r8 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "ev---"
            r0.append(r1)
            r0.append(r9)
            java.lang.String r0 = r0.toString()
            com.marshalchen.ultimaterecyclerview.URLogs.d(r0)
            com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks r0 = r8.mCallbacks
            if (r0 == 0) goto La7
            int r0 = r9.getActionMasked()
            r1 = 1
            r2 = 0
            if (r0 == r1) goto L9c
            r3 = 2
            if (r0 == r3) goto L28
            r1 = 3
            if (r0 == r1) goto L9c
            goto La7
        L28:
            android.view.MotionEvent r0 = r8.mPrevMoveEvent
            if (r0 != 0) goto L2e
            r8.mPrevMoveEvent = r9
        L2e:
            float r0 = r9.getY()
            android.view.MotionEvent r3 = r8.mPrevMoveEvent
            float r3 = r3.getY()
            float r0 = r0 - r3
            android.view.MotionEvent r3 = android.view.MotionEvent.obtainNoHistory(r9)
            r8.mPrevMoveEvent = r3
            int r3 = r8.getCurrentScrollY()
            float r3 = (float) r3
            float r3 = r3 - r0
            r0 = 0
            int r3 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r3 > 0) goto La7
            boolean r3 = r8.mIntercepted
            if (r3 == 0) goto L4f
            return r2
        L4f:
            android.view.ViewGroup r3 = r8.mTouchInterceptionViewGroup
            if (r3 != 0) goto L59
            android.view.ViewParent r3 = r8.getParent()
            android.view.ViewGroup r3 = (android.view.ViewGroup) r3
        L59:
            r4 = 0
            r5 = r8
        L5b:
            if (r5 == 0) goto L7c
            if (r5 == r3) goto L7c
            int r6 = r5.getLeft()
            int r7 = r5.getScrollX()
            int r6 = r6 - r7
            float r6 = (float) r6
            float r0 = r0 + r6
            int r6 = r5.getTop()
            int r7 = r5.getScrollY()
            int r6 = r6 - r7
            float r6 = (float) r6
            float r4 = r4 + r6
            android.view.ViewParent r5 = r5.getParent()
            android.view.View r5 = (android.view.View) r5
            goto L5b
        L7c:
            android.view.MotionEvent r5 = android.view.MotionEvent.obtainNoHistory(r9)
            r5.offsetLocation(r0, r4)
            boolean r0 = r3.onInterceptTouchEvent(r5)
            if (r0 == 0) goto L97
            r8.mIntercepted = r1
            r5.setAction(r2)
            com.marshalchen.ultimaterecyclerview.UltimateRecyclerView$6 r9 = new com.marshalchen.ultimaterecyclerview.UltimateRecyclerView$6
            r9.<init>()
            r8.post(r9)
            return r2
        L97:
            boolean r9 = super.onTouchEvent(r9)
            return r9
        L9c:
            r8.mIntercepted = r2
            r8.mDragging = r2
            com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks r0 = r8.mCallbacks
            com.marshalchen.ultimaterecyclerview.ObservableScrollState r1 = r8.mObservableScrollState
            r0.onUpOrCancelMotionEvent(r1)
        La7:
            boolean r9 = super.onTouchEvent(r9)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean toolbarIsShown(Toolbar toolbar) {
        return ViewHelper.getTranslationY(toolbar) == 0.0f;
    }

    public boolean toolbarIsHidden(Toolbar toolbar) {
        return ViewHelper.getTranslationY(toolbar) == ((float) (-toolbar.getHeight()));
    }

    @Deprecated
    public void showToolbarAndFAB(Toolbar toolbar, UltimateRecyclerView ultimateRecyclerView, int i) {
        showToolbar(toolbar, ultimateRecyclerView, i);
        showDefaultFloatingActionButton();
    }

    @Deprecated
    public void hideToolbarAndFAB(Toolbar toolbar, UltimateRecyclerView ultimateRecyclerView, int i) {
        hideToolbar(toolbar, ultimateRecyclerView, i);
        hideDefaultFloatingActionButton();
    }

    public void showToolbar(Toolbar toolbar, UltimateRecyclerView ultimateRecyclerView, int i) {
        moveToolbar(toolbar, ultimateRecyclerView, i, 0.0f);
    }

    public void hideToolbar(Toolbar toolbar, UltimateRecyclerView ultimateRecyclerView, int i) {
        moveToolbar(toolbar, ultimateRecyclerView, i, -toolbar.getHeight());
    }

    public void showView(View view, UltimateRecyclerView ultimateRecyclerView, int i) {
        moveView(view, ultimateRecyclerView, i, 0.0f);
    }

    public void hideView(View view, UltimateRecyclerView ultimateRecyclerView, int i) {
        moveView(view, ultimateRecyclerView, i, -view.getHeight());
    }

    protected void moveToolbar(final Toolbar toolbar, final UltimateRecyclerView ultimateRecyclerView, final int i, float f) {
        if (ViewHelper.getTranslationY(toolbar) == f) {
            return;
        }
        ValueAnimator duration = ValueAnimator.ofFloat(ViewHelper.getTranslationY(toolbar), f).setDuration(200L);
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.7
            @Override // com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ViewHelper.setTranslationY(toolbar, fFloatValue);
                ViewHelper.setTranslationY(ultimateRecyclerView, fFloatValue);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ultimateRecyclerView.getLayoutParams();
                marginLayoutParams.height = (((int) (-fFloatValue)) + i) - marginLayoutParams.topMargin;
                ultimateRecyclerView.requestLayout();
            }
        });
        duration.start();
    }

    protected void moveView(final View view, final UltimateRecyclerView ultimateRecyclerView, final int i, float f) {
        if (ViewHelper.getTranslationY(view) == f) {
            return;
        }
        ValueAnimator duration = ValueAnimator.ofFloat(ViewHelper.getTranslationY(view), f).setDuration(200L);
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.8
            @Override // com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ViewHelper.setTranslationY(view, fFloatValue);
                ViewHelper.setTranslationY(ultimateRecyclerView, fFloatValue);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ultimateRecyclerView.getLayoutParams();
                marginLayoutParams.height = (((int) (-fFloatValue)) + i) - marginLayoutParams.topMargin;
                ultimateRecyclerView.requestLayout();
            }
        });
        duration.start();
    }

    public FloatingActionButton getDefaultFloatingActionButton() {
        return this.defaultFloatingActionButton;
    }

    public void setDefaultFloatingActionButton(FloatingActionButton floatingActionButton) {
        this.defaultFloatingActionButton = floatingActionButton;
    }

    public View getCustomFloatingActionView() {
        return this.mFloatingButtonView;
    }

    public void showFloatingActionMenu() {
        View view = this.mFloatingButtonView;
        if (view != null) {
            ((FloatingActionsMenu) view).hide(false);
        }
    }

    public void hideFloatingActionMenu() {
        View view = this.mFloatingButtonView;
        if (view != null) {
            ((FloatingActionsMenu) view).hide(true);
        }
    }

    public void showFloatingActionButton() {
        View view = this.mFloatingButtonView;
        if (view != null) {
            ((FloatingActionButton) view).hide(false);
        }
    }

    public void hideFloatingActionButton() {
        View view = this.mFloatingButtonView;
        if (view != null) {
            ((FloatingActionButton) view).hide(true);
        }
    }

    public void showDefaultFloatingActionButton() {
        this.defaultFloatingActionButton.hide(false);
    }

    public void hideDefaultFloatingActionButton() {
        this.defaultFloatingActionButton.hide(true);
    }

    public void displayCustomFloatingActionView(boolean z) {
        View view = this.mFloatingButtonView;
        if (view != null) {
            view.setVisibility(z ? 0 : 4);
        }
    }

    public void displayDefaultFloatingActionButton(boolean z) {
        this.defaultFloatingActionButton.setVisibility(z ? 0 : 4);
    }

    public void removeItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.mRecyclerView.removeItemDecoration(itemDecoration);
    }

    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener onItemTouchListener) {
        this.mRecyclerView.addOnItemTouchListener(onItemTouchListener);
    }

    public void removeOnItemTouchListener(RecyclerView.OnItemTouchListener onItemTouchListener) {
        this.mRecyclerView.removeOnItemTouchListener(onItemTouchListener);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return this.mRecyclerView.getLayoutManager();
    }
}
