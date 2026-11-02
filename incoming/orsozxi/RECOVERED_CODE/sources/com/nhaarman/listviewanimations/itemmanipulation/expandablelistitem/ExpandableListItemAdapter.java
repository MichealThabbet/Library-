package com.nhaarman.listviewanimations.itemmanipulation.expandablelistitem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.util.AdapterViewUtil;
import com.nhaarman.listviewanimations.util.ListViewWrapper;
import com.nhaarman.listviewanimations.util.ListViewWrapperSetter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class ExpandableListItemAdapter<T> extends ArrayAdapter<T> implements ListViewWrapperSetter {
    private static final int DEFAULTCONTENTPARENTRESID = 10001;
    private static final int DEFAULTTITLEPARENTRESID = 10000;
    private int mActionViewResId;
    private final int mContentParentResId;
    private final Context mContext;
    private ExpandCollapseListener mExpandCollapseListener;
    private final List<Long> mExpandedIds;
    private int mLimit;
    private ListViewWrapper mListViewWrapper;
    private final int mTitleParentResId;
    private int mViewLayoutResId;

    public interface ExpandCollapseListener {
        void onItemCollapsed(int i);

        void onItemExpanded(int i);
    }

    public abstract View getContentView(int i, View view, ViewGroup viewGroup);

    public abstract View getTitleView(int i, View view, ViewGroup viewGroup);

    protected ExpandableListItemAdapter(Context context) {
        this(context, null);
    }

    protected ExpandableListItemAdapter(Context context, List<T> list) {
        super(list);
        this.mContext = context;
        this.mTitleParentResId = 10000;
        this.mContentParentResId = 10001;
        this.mExpandedIds = new ArrayList();
    }

    protected ExpandableListItemAdapter(Context context, int i, int i2, int i3) {
        this(context, i, i2, i3, null);
    }

    protected ExpandableListItemAdapter(Context context, int i, int i2, int i3, List<T> list) {
        super(list);
        this.mContext = context;
        this.mViewLayoutResId = i;
        this.mTitleParentResId = i2;
        this.mContentParentResId = i3;
        this.mExpandedIds = new ArrayList();
    }

    @Override // com.nhaarman.listviewanimations.util.ListViewWrapperSetter
    public void setListViewWrapper(ListViewWrapper listViewWrapper) {
        this.mListViewWrapper = listViewWrapper;
    }

    public void setActionViewResId(int i) {
        this.mActionViewResId = i;
    }

    public void setLimit(int i) {
        this.mLimit = i;
        this.mExpandedIds.clear();
        notifyDataSetChanged();
    }

    public void setExpandCollapseListener(ExpandCollapseListener expandCollapseListener) {
        this.mExpandCollapseListener = expandCollapseListener;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        ViewGroup viewGroupCreateView = (ViewGroup) view;
        if (viewGroupCreateView == null) {
            viewGroupCreateView = createView(viewGroup);
            viewHolder = new ViewHolder();
            viewHolder.titleParent = (ViewGroup) viewGroupCreateView.findViewById(this.mTitleParentResId);
            viewHolder.contentParent = (ViewGroup) viewGroupCreateView.findViewById(this.mContentParentResId);
            viewGroupCreateView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) viewGroupCreateView.getTag();
        }
        View titleView = getTitleView(i, viewHolder.titleView, viewHolder.titleParent);
        if (!titleView.equals(viewHolder.titleView)) {
            viewHolder.titleParent.removeAllViews();
            viewHolder.titleParent.addView(titleView);
            int i2 = this.mActionViewResId;
            if (i2 == 0) {
                viewGroupCreateView.setOnClickListener(new TitleViewOnClickListener(viewHolder.contentParent));
            } else {
                viewGroupCreateView.findViewById(i2).setOnClickListener(new TitleViewOnClickListener(viewHolder.contentParent));
            }
        }
        viewHolder.titleView = titleView;
        View contentView = getContentView(i, viewHolder.contentView, viewHolder.contentParent);
        if (!contentView.equals(viewHolder.contentView)) {
            viewHolder.contentParent.removeAllViews();
            viewHolder.contentParent.addView(contentView);
        }
        viewHolder.contentView = contentView;
        viewHolder.contentParent.setVisibility(this.mExpandedIds.contains(Long.valueOf(getItemId(i))) ? 0 : 8);
        viewHolder.contentParent.setTag(Long.valueOf(getItemId(i)));
        ViewGroup.LayoutParams layoutParams = viewHolder.contentParent.getLayoutParams();
        layoutParams.height = -2;
        viewHolder.contentParent.setLayoutParams(layoutParams);
        return viewGroupCreateView;
    }

    public boolean isExpanded(int i) {
        return this.mExpandedIds.contains(Long.valueOf(getItemId(i)));
    }

    public View getTitleView(int i) {
        View viewFindViewForPosition = findViewForPosition(i);
        if (viewFindViewForPosition != null) {
            Object tag = viewFindViewForPosition.getTag();
            if (tag instanceof ViewHolder) {
                return ((ViewHolder) tag).titleView;
            }
        }
        return null;
    }

    public View getContentView(int i) {
        View viewFindViewForPosition = findViewForPosition(i);
        if (viewFindViewForPosition != null) {
            Object tag = viewFindViewForPosition.getTag();
            if (tag instanceof ViewHolder) {
                return ((ViewHolder) tag).contentView;
            }
        }
        return null;
    }

    @Override // com.nhaarman.listviewanimations.ArrayAdapter, android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        HashSet hashSet = new HashSet(this.mExpandedIds);
        for (int i = 0; i < getCount(); i++) {
            hashSet.remove(Long.valueOf(getItemId(i)));
        }
        this.mExpandedIds.removeAll(hashSet);
    }

    public void expand(int i) {
        if (this.mExpandedIds.contains(Long.valueOf(getItemId(i)))) {
            return;
        }
        toggle(i);
    }

    public void collapse(int i) {
        if (this.mExpandedIds.contains(Long.valueOf(getItemId(i)))) {
            toggle(i);
        }
    }

    public void toggle(int i) {
        long itemId = getItemId(i);
        boolean zContains = this.mExpandedIds.contains(Long.valueOf(itemId));
        View contentParent = getContentParent(i);
        if (contentParent != null) {
            toggle(contentParent);
        }
        if (contentParent == null && zContains) {
            this.mExpandedIds.remove(Long.valueOf(itemId));
        } else if (contentParent == null) {
            this.mExpandedIds.add(Long.valueOf(itemId));
        }
    }

    private ViewGroup createView(ViewGroup viewGroup) {
        if (this.mViewLayoutResId == 0) {
            return new RootView(this.mContext);
        }
        return (ViewGroup) LayoutInflater.from(this.mContext).inflate(this.mViewLayoutResId, viewGroup, false);
    }

    private View getContentParent(int i) {
        View viewFindViewForPosition = findViewForPosition(i);
        if (viewFindViewForPosition != null) {
            Object tag = viewFindViewForPosition.getTag();
            if (tag instanceof ViewHolder) {
                return ((ViewHolder) tag).contentParent;
            }
        }
        return null;
    }

    private View findViewForPosition(int i) {
        if (this.mListViewWrapper == null) {
            throw new IllegalStateException("Call setAbsListView on this ExpanableListItemAdapter!");
        }
        View view = null;
        for (int i2 = 0; i2 < this.mListViewWrapper.getChildCount() && view == null; i2++) {
            View childAt = this.mListViewWrapper.getChildAt(i2);
            if (childAt != null && AdapterViewUtil.getPositionForView(this.mListViewWrapper, childAt) == i) {
                view = childAt;
            }
        }
        return view;
    }

    private int findPositionForId(long j) {
        for (int i = 0; i < getCount(); i++) {
            if (getItemId(i) == j) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggle(View view) {
        if (this.mListViewWrapper == null) {
            throw new IllegalStateException("No ListView set!");
        }
        boolean z = view.getVisibility() == 0;
        if (!z && this.mLimit > 0 && this.mExpandedIds.size() >= this.mLimit) {
            Long l = this.mExpandedIds.get(0);
            int iFindPositionForId = findPositionForId(l.longValue());
            View contentParent = getContentParent(iFindPositionForId);
            if (contentParent != null) {
                ExpandCollapseHelper.animateCollapsing(contentParent);
            }
            this.mExpandedIds.remove(l);
            ExpandCollapseListener expandCollapseListener = this.mExpandCollapseListener;
            if (expandCollapseListener != null) {
                expandCollapseListener.onItemCollapsed(iFindPositionForId);
            }
        }
        Long l2 = (Long) view.getTag();
        int iFindPositionForId2 = findPositionForId(l2.longValue());
        if (z) {
            ExpandCollapseHelper.animateCollapsing(view);
            this.mExpandedIds.remove(l2);
            ExpandCollapseListener expandCollapseListener2 = this.mExpandCollapseListener;
            if (expandCollapseListener2 != null) {
                expandCollapseListener2.onItemCollapsed(iFindPositionForId2);
                return;
            }
            return;
        }
        ExpandCollapseHelper.animateExpanding(view, this.mListViewWrapper);
        this.mExpandedIds.add(l2);
        ExpandCollapseListener expandCollapseListener3 = this.mExpandCollapseListener;
        if (expandCollapseListener3 != null) {
            expandCollapseListener3.onItemExpanded(iFindPositionForId2);
        }
    }

    private static class RootView extends LinearLayout {
        private ViewGroup mContentViewGroup;
        private ViewGroup mTitleViewGroup;

        private RootView(Context context) {
            super(context);
            init();
        }

        private void init() {
            setOrientation(1);
            FrameLayout frameLayout = new FrameLayout(getContext());
            this.mTitleViewGroup = frameLayout;
            frameLayout.setId(10000);
            addView(this.mTitleViewGroup);
            FrameLayout frameLayout2 = new FrameLayout(getContext());
            this.mContentViewGroup = frameLayout2;
            frameLayout2.setId(10001);
            addView(this.mContentViewGroup);
        }
    }

    private static class ViewHolder {
        ViewGroup contentParent;
        View contentView;
        ViewGroup titleParent;
        View titleView;

        private ViewHolder() {
        }
    }

    private static class ExpandCollapseHelper {
        private ExpandCollapseHelper() {
        }

        public static void animateCollapsing(final View view) {
            ValueAnimator valueAnimatorCreateHeightAnimator = createHeightAnimator(view, view.getHeight(), 0);
            valueAnimatorCreateHeightAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.nhaarman.listviewanimations.itemmanipulation.expandablelistitem.ExpandableListItemAdapter.ExpandCollapseHelper.1
                @Override // com.nineoldandroids.animation.AnimatorListenerAdapter, com.nineoldandroids.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    view.setVisibility(8);
                }
            });
            valueAnimatorCreateHeightAnimator.start();
        }

        public static void animateExpanding(final View view, final ListViewWrapper listViewWrapper) {
            view.setVisibility(0);
            View view2 = (View) view.getParent();
            view.measure(View.MeasureSpec.makeMeasureSpec((view2.getMeasuredWidth() - view2.getPaddingLeft()) - view2.getPaddingRight(), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
            ValueAnimator valueAnimatorCreateHeightAnimator = createHeightAnimator(view, 0, view.getMeasuredHeight());
            valueAnimatorCreateHeightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.nhaarman.listviewanimations.itemmanipulation.expandablelistitem.ExpandableListItemAdapter.ExpandCollapseHelper.2
                final int listViewBottomPadding;
                final int listViewHeight;
                final View v;

                {
                    this.listViewHeight = listViewWrapper.getListView().getHeight();
                    this.listViewBottomPadding = listViewWrapper.getListView().getPaddingBottom();
                    this.v = ExpandCollapseHelper.findDirectChild(view, listViewWrapper.getListView());
                }

                @Override // com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int top;
                    int bottom = this.v.getBottom();
                    if (bottom <= this.listViewHeight || (top = this.v.getTop()) <= 0) {
                        return;
                    }
                    listViewWrapper.smoothScrollBy(Math.min((bottom - this.listViewHeight) + this.listViewBottomPadding, top), 0);
                }
            });
            valueAnimatorCreateHeightAnimator.start();
        }

        public static ValueAnimator createHeightAnimator(final View view, int i, int i2) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(i, i2);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.nhaarman.listviewanimations.itemmanipulation.expandablelistitem.ExpandableListItemAdapter.ExpandCollapseHelper.3
                @Override // com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = iIntValue;
                    view.setLayoutParams(layoutParams);
                }
            });
            return valueAnimatorOfInt;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static View findDirectChild(View view, ViewGroup viewGroup) {
            Object parent = view.getParent();
            while (true) {
                View view2 = (View) parent;
                View view3 = view;
                view = view2;
                if (view.equals(viewGroup)) {
                    return view3;
                }
                parent = view.getParent();
            }
        }
    }

    private class TitleViewOnClickListener implements View.OnClickListener {
        private final View mContentParent;

        private TitleViewOnClickListener(View view) {
            this.mContentParent = view;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ExpandableListItemAdapter.this.toggle(this.mContentParent);
        }
    }
}
