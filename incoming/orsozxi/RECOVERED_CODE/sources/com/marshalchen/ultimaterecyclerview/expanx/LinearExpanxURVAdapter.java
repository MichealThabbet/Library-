package com.marshalchen.ultimaterecyclerview.expanx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;
import com.marshalchen.ultimaterecyclerview.expanx.Util.BaseViewHolder;
import com.marshalchen.ultimaterecyclerview.expanx.Util.ChildVH;
import com.marshalchen.ultimaterecyclerview.expanx.Util.ItemDataClickListener;
import com.marshalchen.ultimaterecyclerview.expanx.Util.OnScrollToListener;
import com.marshalchen.ultimaterecyclerview.expanx.Util.ParentVH;
import com.marshalchen.ultimaterecyclerview.expanx.Util.child;
import com.marshalchen.ultimaterecyclerview.expanx.Util.parent;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class LinearExpanxURVAdapter<T extends ExpandableItemData, G extends parent<T>, H extends child<T>> extends UltimateViewAdapter {
    public static final int EXPANDABLE_ITEMS = 1;
    public static final int EXPANDABLE_SYSTEM = 0;
    public static final String TAG = "expAdapter";
    private boolean customObject;
    protected int expandableBehavior;
    private ItemDataClickListener imageClickListener;
    private ItemDataClickListener imageSetLoadItems;
    private Context mContext;
    private List<T> mDataSet;
    private List<OnScrollToListener> monScrollToListenerList;
    private OnScrollToListener onScrollToListener;

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public long generateHeaderId(int i) {
        return 0L;
    }

    protected abstract List<T> getChildrenByPath(String str, int i, int i2);

    protected abstract int getLayoutResChild();

    protected abstract int getLayoutResParent();

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return null;
    }

    protected abstract H iniCustomChildHolder(View view);

    protected abstract G iniCustomParentHolder(View view);

    @Override // com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    }

    @Override // com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    public class ExpandableViewTypes extends UltimateViewAdapter.VIEW_TYPES {
        public static final int ITEM_TYPE_CHILD = 1135;
        public static final int ITEM_TYPE_PARENT = 1026;

        protected ExpandableViewTypes() {
            super();
        }
    }

    protected Context getContext() {
        return this.mContext;
    }

    protected List<T> getSet() {
        return this.mDataSet;
    }

    public void addOnScrollToListener(OnScrollToListener onScrollToListener) {
        this.monScrollToListenerList.add(onScrollToListener);
    }

    @Deprecated
    public void setOnScrollToListener(OnScrollToListener onScrollToListener) {
        this.onScrollToListener = onScrollToListener;
    }

    public LinearExpanxURVAdapter(Context context, int i, boolean z) {
        this(context, i);
        this.customObject = z;
    }

    public LinearExpanxURVAdapter(Context context, int i) {
        this(context);
        this.expandableBehavior = i;
    }

    public LinearExpanxURVAdapter(Context context) {
        this.monScrollToListenerList = new ArrayList();
        this.expandableBehavior = 0;
        this.imageSetLoadItems = new ItemDataClickListener<T>() { // from class: com.marshalchen.ultimaterecyclerview.expanx.LinearExpanxURVAdapter.1
            @Override // com.marshalchen.ultimaterecyclerview.expanx.Util.ItemDataClickListener
            public void onExpandChildren(T t) {
                int currentPosition = LinearExpanxURVAdapter.this.getCurrentPosition(t.getUuid());
                List<T> children = t.getChildren();
                if (children == null) {
                    return;
                }
                int i = currentPosition + 1;
                LinearExpanxURVAdapter.this.addAll(children, i);
                LinearExpanxURVAdapter.this.triggerSingleEventScrollTo(i);
                LinearExpanxURVAdapter.this.triggerBoardCastEventScrollTo(i);
            }

            @Override // com.marshalchen.ultimaterecyclerview.expanx.Util.ItemDataClickListener
            public void onHideChildren(T t) {
                int currentPosition = LinearExpanxURVAdapter.this.getCurrentPosition(t.getUuid());
                if (t.getChildren() == null) {
                    return;
                }
                LinearExpanxURVAdapter.this.removeAll(currentPosition + 1, r1.getChildrenCount(t) - 1);
                LinearExpanxURVAdapter.this.triggerSingleEventScrollTo(currentPosition);
                LinearExpanxURVAdapter.this.triggerBoardCastEventScrollTo(currentPosition);
            }
        };
        this.imageClickListener = new ItemDataClickListener<T>() { // from class: com.marshalchen.ultimaterecyclerview.expanx.LinearExpanxURVAdapter.2
            @Override // com.marshalchen.ultimaterecyclerview.expanx.Util.ItemDataClickListener
            public void onExpandChildren(T t) {
                int currentPosition = LinearExpanxURVAdapter.this.getCurrentPosition(t.getUuid());
                List<T> childrenByPath = LinearExpanxURVAdapter.this.getChildrenByPath(t.getPath(), t.getTreeDepth(), currentPosition);
                if (childrenByPath == null) {
                    return;
                }
                int i = currentPosition + 1;
                LinearExpanxURVAdapter.this.addAll(childrenByPath, i);
                t.setChildren(childrenByPath);
                LinearExpanxURVAdapter.this.triggerSingleEventScrollTo(i);
                LinearExpanxURVAdapter.this.triggerBoardCastEventScrollTo(i);
            }

            @Override // com.marshalchen.ultimaterecyclerview.expanx.Util.ItemDataClickListener
            public void onHideChildren(T t) {
                int currentPosition = LinearExpanxURVAdapter.this.getCurrentPosition(t.getUuid());
                if (t.getChildren() == null) {
                    return;
                }
                LinearExpanxURVAdapter.this.removeAll(currentPosition + 1, r1.getChildrenCount(t) - 1);
                LinearExpanxURVAdapter.this.triggerSingleEventScrollTo(currentPosition);
                LinearExpanxURVAdapter.this.triggerBoardCastEventScrollTo(currentPosition);
                t.setChildren(null);
            }
        };
        this.mContext = context;
        this.mDataSet = new ArrayList();
        this.customObject = false;
    }

    private View initiateview(ViewGroup viewGroup, int i) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1026) {
            return iniCustomParentHolder(initiateview(viewGroup, getLayoutResParent()));
        }
        if (i != 1135) {
            return null;
        }
        return iniCustomChildHolder(initiateview(viewGroup, getLayoutResChild()));
    }

    private ItemDataClickListener getBehavior() {
        if (this.expandableBehavior == 1) {
            return this.imageSetLoadItems;
        }
        return this.imageClickListener;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == 1026) {
            ((ParentVH) viewHolder).bindView(this.mDataSet.get(i), i, getBehavior());
        } else {
            if (itemViewType != 1135) {
                return;
            }
            ((ChildVH) viewHolder).bindView(this.mDataSet.get(i), i);
        }
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new BaseViewHolder(viewGroup);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void triggerBoardCastEventScrollTo(int i) {
        for (int i2 = 0; i2 < this.monScrollToListenerList.size(); i2++) {
            this.monScrollToListenerList.get(i2).scrollTo(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void triggerSingleEventScrollTo(int i) {
        OnScrollToListener onScrollToListener = this.onScrollToListener;
        if (onScrollToListener != null) {
            onScrollToListener.scrollTo(i);
        }
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mDataSet.size();
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public int getAdapterItemCount() {
        return getItemCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getChildrenCount(T t) {
        ArrayList arrayList = new ArrayList();
        printChild(t, arrayList);
        return arrayList.size();
    }

    private void printChild(Object obj, List<Object> list) {
        list.add(obj);
        if (obj instanceof ExpandableItemData) {
            ExpandableItemData expandableItemData = (ExpandableItemData) obj;
            if (expandableItemData.getChildren() != null) {
                for (int i = 0; i < expandableItemData.getChildren().size(); i++) {
                    printChild(expandableItemData.getChildren().get(i), list);
                }
            }
        }
    }

    protected void removeAll(int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            this.mDataSet.remove(i);
        }
        notifyItemRangeRemoved(i, i2);
    }

    protected int getCurrentPosition(String str) {
        for (int i = 0; i < this.mDataSet.size(); i++) {
            if (str.equalsIgnoreCase(this.mDataSet.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.mDataSet.get(i).getType();
    }

    public void add(T t, int i) {
        this.mDataSet.add(i, t);
        notifyItemInserted(i);
    }

    public void addAll(List<T> list, int i) {
        this.mDataSet.addAll(i, list);
        notifyItemRangeInserted(i, list.size());
    }

    public void delete(int i) {
        if (i < 0 || i >= this.mDataSet.size()) {
            return;
        }
        if (this.mDataSet.get(i).getType() == 1026 && this.mDataSet.get(i).isExpand()) {
            for (int i2 = 0; i2 < this.mDataSet.get(i).getChildren().size() + 1; i2++) {
                this.mDataSet.remove(i);
            }
            notifyItemRangeRemoved(i, this.mDataSet.get(i).getChildren().size() + 1);
            return;
        }
        this.mDataSet.remove(i);
        notifyItemRemoved(i);
    }
}
