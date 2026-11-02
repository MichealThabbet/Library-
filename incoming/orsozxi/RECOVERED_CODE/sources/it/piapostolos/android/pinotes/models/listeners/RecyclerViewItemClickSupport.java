package it.piapostolos.android.pinotes.models.listeners;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import it.piapostolos.android.pinotes.R;

/* loaded from: classes3.dex */
public class RecyclerViewItemClickSupport {
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private final RecyclerView mRecyclerView;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() { // from class: it.piapostolos.android.pinotes.models.listeners.RecyclerViewItemClickSupport.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (RecyclerViewItemClickSupport.this.mOnItemClickListener != null) {
                RecyclerViewItemClickSupport.this.mOnItemClickListener.onItemClicked(RecyclerViewItemClickSupport.this.mRecyclerView, RecyclerViewItemClickSupport.this.mRecyclerView.getChildViewHolder(view).getAdapterPosition(), view);
            }
        }
    };
    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() { // from class: it.piapostolos.android.pinotes.models.listeners.RecyclerViewItemClickSupport.2
        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            if (RecyclerViewItemClickSupport.this.mOnItemLongClickListener == null) {
                return false;
            }
            return RecyclerViewItemClickSupport.this.mOnItemLongClickListener.onItemLongClicked(RecyclerViewItemClickSupport.this.mRecyclerView, RecyclerViewItemClickSupport.this.mRecyclerView.getChildViewHolder(view).getAdapterPosition(), view);
        }
    };
    private RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() { // from class: it.piapostolos.android.pinotes.models.listeners.RecyclerViewItemClickSupport.3
        @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
        public void onChildViewDetachedFromWindow(View view) {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
        public void onChildViewAttachedToWindow(View view) {
            if (RecyclerViewItemClickSupport.this.mOnItemClickListener != null) {
                view.setOnClickListener(RecyclerViewItemClickSupport.this.mOnClickListener);
            }
            if (RecyclerViewItemClickSupport.this.mOnItemLongClickListener != null) {
                view.setOnLongClickListener(RecyclerViewItemClickSupport.this.mOnLongClickListener);
            }
        }
    };

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int i, View view);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int i, View view);
    }

    private RecyclerViewItemClickSupport(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        recyclerView.setTag(R.id.item_click_support, this);
        this.mRecyclerView.addOnChildAttachStateChangeListener(this.mAttachListener);
    }

    public static RecyclerViewItemClickSupport addTo(RecyclerView recyclerView) {
        RecyclerViewItemClickSupport recyclerViewItemClickSupport = (RecyclerViewItemClickSupport) recyclerView.getTag(R.id.item_click_support);
        return recyclerViewItemClickSupport == null ? new RecyclerViewItemClickSupport(recyclerView) : recyclerViewItemClickSupport;
    }

    public static RecyclerViewItemClickSupport removeFrom(RecyclerView recyclerView) {
        RecyclerViewItemClickSupport recyclerViewItemClickSupport = (RecyclerViewItemClickSupport) recyclerView.getTag(R.id.item_click_support);
        if (recyclerViewItemClickSupport != null) {
            recyclerViewItemClickSupport.detach(recyclerView);
        }
        return recyclerViewItemClickSupport;
    }

    public RecyclerViewItemClickSupport setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        return this;
    }

    public RecyclerViewItemClickSupport setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
        return this;
    }

    private void detach(RecyclerView recyclerView) {
        recyclerView.removeOnChildAttachStateChangeListener(this.mAttachListener);
        recyclerView.setTag(R.id.item_click_support, null);
    }
}
