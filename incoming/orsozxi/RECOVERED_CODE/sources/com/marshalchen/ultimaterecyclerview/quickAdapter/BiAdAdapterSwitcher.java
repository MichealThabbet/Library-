package com.marshalchen.ultimaterecyclerview.quickAdapter;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.quickAdapter.easyRegularAdapter;
import com.marshalchen.ultimaterecyclerview.quickAdapter.simpleAdmobAdapter;
import com.marshalchen.ultimaterecyclerview.uiUtils.ScrollSmoothLineaerLayoutManager;
import java.util.List;

/* loaded from: classes2.dex */
public class BiAdAdapterSwitcher<T, B extends UltimateRecyclerviewViewHolder, EASY extends easyRegularAdapter<T, B>, V extends ViewGroup, ADMOB extends simpleAdmobAdapter<T, B, V>> {
    protected UltimateRecyclerView listview;
    protected onLoadMore loading_more;
    protected RecyclerView.LayoutManager mManager;
    protected EASY noad;
    protected boolean with_the_ad;
    protected ADMOB withad;
    protected boolean auto_disable_loadmore = false;
    protected int page_now = 1;
    protected int max_pages = 3;
    protected int layoutLoadMoreResId = 0;
    protected Runnable refresh_default = new Runnable() { // from class: com.marshalchen.ultimaterecyclerview.quickAdapter.BiAdAdapterSwitcher.1
        @Override // java.lang.Runnable
        public void run() {
            BiAdAdapterSwitcher.this.reset();
            if (BiAdAdapterSwitcher.this.loading_more != null) {
                if (BiAdAdapterSwitcher.this.loading_more.request_start(1, 0, 0, BiAdAdapterSwitcher.this, true)) {
                    BiAdAdapterSwitcher.this.page_now = 2;
                    BiAdAdapterSwitcher.this.max_pages = 3;
                } else if (BiAdAdapterSwitcher.this.auto_disable_loadmore) {
                    BiAdAdapterSwitcher.this.listview.disableLoadmore();
                }
            }
            BiAdAdapterSwitcher.this.listview.setRefreshing(false);
        }
    };

    public interface onLoadMore {
        boolean request_start(int i, int i2, int i3, BiAdAdapterSwitcher biAdAdapterSwitcher, boolean z);
    }

    public void setMaxPages(int i) {
        this.max_pages = i;
    }

    public BiAdAdapterSwitcher(UltimateRecyclerView ultimateRecyclerView, EASY easy, ADMOB admob) {
        this.listview = ultimateRecyclerView;
        this.noad = easy;
        this.withad = admob;
        this.mManager = ultimateRecyclerView.getLayoutManager();
    }

    public BiAdAdapterSwitcher EnableAutoDisableLoadMoreByMaxPages() {
        this.auto_disable_loadmore = true;
        return this;
    }

    public void init(boolean z) {
        this.with_the_ad = z;
        int i = this.layoutLoadMoreResId;
        if (i != 0) {
            if (z) {
                this.withad.setCustomLoadMoreView(getV(i));
            } else {
                this.noad.setCustomLoadMoreView(getV(i));
            }
            this.listview.enableLoadmore();
        }
        this.listview.setAdapter(z ? this.withad : this.noad);
    }

    public void scrollToTop() {
        if (this.mManager == null) {
            this.mManager = this.listview.getLayoutManager();
        }
        RecyclerView.LayoutManager layoutManager = this.mManager;
        if (layoutManager == null) {
            return;
        }
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if (linearLayoutManager.getStackFromEnd() && linearLayoutManager.canScrollVertically()) {
                linearLayoutManager.scrollToPosition(1);
                return;
            }
            return;
        }
        if (layoutManager instanceof ScrollSmoothLineaerLayoutManager) {
            ScrollSmoothLineaerLayoutManager scrollSmoothLineaerLayoutManager = (ScrollSmoothLineaerLayoutManager) layoutManager;
            if (scrollSmoothLineaerLayoutManager.getStackFromEnd() && scrollSmoothLineaerLayoutManager.canScrollVertically()) {
                scrollSmoothLineaerLayoutManager.scrollToPosition(1);
            }
        }
    }

    protected BiAdAdapterSwitcher setCustomOnFresh(Runnable runnable) {
        this.refresh_default = runnable;
        return this;
    }

    public BiAdAdapterSwitcher onEnableRefresh(final int i) {
        this.listview.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.marshalchen.ultimaterecyclerview.quickAdapter.BiAdAdapterSwitcher.2
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                new Handler().postDelayed(BiAdAdapterSwitcher.this.refresh_default, i);
            }
        });
        return this;
    }

    public void removeALL() {
        reset();
    }

    public void notifyDataSetChanged() {
        if (this.with_the_ad) {
            this.withad.notifyDataSetChanged();
        } else {
            this.noad.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reset() {
        if (this.with_the_ad) {
            this.withad.removeAll();
        } else {
            this.noad.removeAll();
        }
    }

    public void load_more_data(List<T> list) {
        if (this.with_the_ad) {
            insert_default((BiAdAdapterSwitcher<T, B, EASY, V, ADMOB>) this.withad, list);
        } else {
            insert_default((BiAdAdapterSwitcher<T, B, EASY, V, ADMOB>) this.noad, list);
        }
    }

    public void load_more_data_at_zero(List<T> list) {
        if (this.with_the_ad) {
            insert_default((BiAdAdapterSwitcher<T, B, EASY, V, ADMOB>) this.withad, list);
        } else {
            insert_default((BiAdAdapterSwitcher<T, B, EASY, V, ADMOB>) this.noad, list);
        }
    }

    public BiAdAdapterSwitcher onEnableLoadmore(int i, final int i2, onLoadMore onloadmore) {
        this.loading_more = onloadmore;
        this.listview.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() { // from class: com.marshalchen.ultimaterecyclerview.quickAdapter.BiAdAdapterSwitcher.3
            @Override // com.marshalchen.ultimaterecyclerview.UltimateRecyclerView.OnLoadMoreListener
            public void loadMore(final int i3, final int i4) {
                new Handler().postDelayed(new Runnable() { // from class: com.marshalchen.ultimaterecyclerview.quickAdapter.BiAdAdapterSwitcher.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.d("loadmore", i4 + " position");
                        if (BiAdAdapterSwitcher.this.loading_more != null) {
                            if (BiAdAdapterSwitcher.this.loading_more.request_start(BiAdAdapterSwitcher.this.page_now, i3, i4, BiAdAdapterSwitcher.this, false) && BiAdAdapterSwitcher.this.page_now < BiAdAdapterSwitcher.this.max_pages) {
                                if (BiAdAdapterSwitcher.this.auto_disable_loadmore && !BiAdAdapterSwitcher.this.listview.isLoadMoreEnabled()) {
                                    BiAdAdapterSwitcher.this.listview.reenableLoadmore();
                                }
                                BiAdAdapterSwitcher.this.page_now++;
                                return;
                            }
                            Log.d("loadmore", "request failed position");
                            if (BiAdAdapterSwitcher.this.auto_disable_loadmore) {
                                BiAdAdapterSwitcher.this.listview.disableLoadmore();
                            }
                        }
                    }
                }, i2);
            }
        });
        this.layoutLoadMoreResId = i;
        return this;
    }

    private View getV(int i) {
        return LayoutInflater.from(this.listview.getContext()).inflate(i, (ViewGroup) null);
    }

    private void insert_default(EASY easy, List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            easy.insert(list.get(i));
        }
    }

    private void insert_default(ADMOB admob, List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            admob.insert(list.get(i));
        }
    }

    public static <V extends ViewGroup> void maximum_size(LinearLayout linearLayout, V v, Activity activity) {
        v.setLayoutParams(new FrameLayout.LayoutParams(-1, (int) ((activity.getWindowManager().getDefaultDisplay().getWidth() / 300.0d) * 50.0d)));
        linearLayout.addView(v);
    }
}
