package com.marshalchen.ultimaterecyclerview.uiUtils;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

/* loaded from: classes2.dex */
public class BasicGridLayoutManager extends GridLayoutManager {
    protected int headerSpan;
    private GridLayoutManager.SpanSizeLookup m;
    private final UltimateViewAdapter mAdapter;
    protected GridLayoutManager.SpanSizeLookup mSpanSizeLookUp;

    protected int getNormalSpanCount(int i) {
        return 1;
    }

    protected class VIEW_TYPES {
        public static final int CHANGED_FOOTER = 3;
        public static final int FOOTER = 2;
        public static final int HEADER = 1;
        public static final int NORMAL = 0;

        protected VIEW_TYPES() {
        }
    }

    protected int getSpanInterval(int i) {
        if (i % (getSpanCount() * 10) == 0) {
            return getSpanCount();
        }
        return 1;
    }

    protected int getHeaderSpanCount(int i) {
        return this.headerSpan;
    }

    protected GridLayoutManager.SpanSizeLookup decideSpanSizeCal() {
        return this.mSpanSizeLookUp;
    }

    public BasicGridLayoutManager(Context context, int i, UltimateViewAdapter ultimateViewAdapter) {
        super(context, i);
        this.headerSpan = 2;
        this.m = new GridLayoutManager.SpanSizeLookup() { // from class: com.marshalchen.ultimaterecyclerview.uiUtils.BasicGridLayoutManager.1
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i2) {
                if (i2 != 0) {
                    if (BasicGridLayoutManager.this.mAdapter.getItemCount() > 2) {
                        if (BasicGridLayoutManager.this.mAdapter.getItemViewType(i2) != 2) {
                            if (BasicGridLayoutManager.this.mAdapter.getItemViewType(i2) == 1) {
                                return BasicGridLayoutManager.this.getSpanCount();
                            }
                        } else {
                            return BasicGridLayoutManager.this.getSpanCount();
                        }
                    }
                    return 1;
                }
                return BasicGridLayoutManager.this.getSpanCount();
            }
        };
        this.mSpanSizeLookUp = new GridLayoutManager.SpanSizeLookup() { // from class: com.marshalchen.ultimaterecyclerview.uiUtils.BasicGridLayoutManager.2
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i2) {
                if (BasicGridLayoutManager.this.mAdapter.getItemViewType(i2) != 2) {
                    if (BasicGridLayoutManager.this.mAdapter.getItemViewType(i2) == 1) {
                        return BasicGridLayoutManager.this.getSpanCount();
                    }
                    return BasicGridLayoutManager.this.getNormalSpanCount(i2);
                }
                return BasicGridLayoutManager.this.getSpanCount();
            }
        };
        this.mAdapter = ultimateViewAdapter;
        setSpanSizeLookup(decideSpanSizeCal());
    }

    public BasicGridLayoutManager(Context context, int i, int i2, boolean z, UltimateViewAdapter ultimateViewAdapter) {
        super(context, i, i2, z);
        this.headerSpan = 2;
        this.m = new GridLayoutManager.SpanSizeLookup() { // from class: com.marshalchen.ultimaterecyclerview.uiUtils.BasicGridLayoutManager.1
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i22) {
                if (i22 != 0) {
                    if (BasicGridLayoutManager.this.mAdapter.getItemCount() > 2) {
                        if (BasicGridLayoutManager.this.mAdapter.getItemViewType(i22) != 2) {
                            if (BasicGridLayoutManager.this.mAdapter.getItemViewType(i22) == 1) {
                                return BasicGridLayoutManager.this.getSpanCount();
                            }
                        } else {
                            return BasicGridLayoutManager.this.getSpanCount();
                        }
                    }
                    return 1;
                }
                return BasicGridLayoutManager.this.getSpanCount();
            }
        };
        this.mSpanSizeLookUp = new GridLayoutManager.SpanSizeLookup() { // from class: com.marshalchen.ultimaterecyclerview.uiUtils.BasicGridLayoutManager.2
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i22) {
                if (BasicGridLayoutManager.this.mAdapter.getItemViewType(i22) != 2) {
                    if (BasicGridLayoutManager.this.mAdapter.getItemViewType(i22) == 1) {
                        return BasicGridLayoutManager.this.getSpanCount();
                    }
                    return BasicGridLayoutManager.this.getNormalSpanCount(i22);
                }
                return BasicGridLayoutManager.this.getSpanCount();
            }
        };
        this.mAdapter = ultimateViewAdapter;
        setSpanSizeLookup(decideSpanSizeCal());
    }
}
