package com.marshalchen.ultimaterecyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class AdmobAdapter<Adv extends ViewGroup, T, V extends UltimateRecyclerviewViewHolder> extends UltimateViewAdapter<UltimateRecyclerviewViewHolder> {
    public static final int POSITION_ON_AD = -1;
    protected int adfrequency;
    protected final Adv advertise_view;
    protected AdviewListener adviewlistener;
    protected List<T> list;
    protected boolean once;

    public interface AdviewListener<Adv extends ViewGroup> {
        Adv onGenerateAdview();
    }

    protected abstract int getNormalLayoutResId();

    protected abstract V newViewHolder(View view);

    protected class VIEW_TYPES extends UltimateViewAdapter.VIEW_TYPES {
        public static final int ADVIEW = 4;

        protected VIEW_TYPES() {
            super();
        }
    }

    public AdmobAdapter(Adv adv, boolean z, int i, List<T> list) {
        this(adv, z, i, list, null);
    }

    public AdmobAdapter(Adv adv, boolean z, int i, List<T> list, AdviewListener adviewListener) {
        this.advertise_view = adv;
        for (int i2 = 0; i2 < this.advertise_view.getChildCount(); i2++) {
            this.advertise_view.getChildAt(i2).setFocusable(false);
        }
        this.advertise_view.setFocusable(false);
        this.once = z;
        this.adfrequency = i + 1;
        this.list = list;
    }

    public static class AdHolder extends UltimateRecyclerviewViewHolder {
        public AdHolder(AdviewListener adviewListener) {
            super(adviewListener.onGenerateAdview());
        }
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return newViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(getNormalLayoutResId(), viewGroup, false));
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 4) {
            AdviewListener adviewListener = this.adviewlistener;
            if (adviewListener != null) {
                try {
                    return new AdHolder(adviewListener);
                } catch (NullPointerException unused) {
                    return new UltimateRecyclerviewViewHolder(this.advertise_view);
                } catch (Exception unused2) {
                    return new UltimateRecyclerviewViewHolder(this.advertise_view);
                }
            }
            return new UltimateRecyclerviewViewHolder(this.advertise_view);
        }
        return (UltimateRecyclerviewViewHolder) super.onCreateViewHolder(viewGroup, i);
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        int itemViewType = super.getItemViewType(i);
        if (itemViewType == 0) {
            if (!this.once) {
                if (i <= 0 || !isPosOnAdView(i)) {
                    return itemViewType;
                }
                return 4;
            }
            if (isPosOnAdView(i) && this.adfrequency == i + 1) {
                return 4;
            }
        }
        return itemViewType;
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public int getAdapterItemCount() {
        return this.list.size();
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int itemCount = super.getItemCount();
        if (this.once) {
            return this.adfrequency > 0 ? itemCount + 1 : itemCount;
        }
        int iAtAdPos = (this.adfrequency > 0 ? atAdPos(itemCount) : 0) + itemCount;
        Log.d("getItemCountE2", iAtAdPos + "");
        return iAtAdPos;
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public <T> void insert(List<T> list, T t, int i) {
        try {
            list.add(i, t);
            int reverseDataArrayPosition = getReverseDataArrayPosition(i);
            if (isPosOnAdView(reverseDataArrayPosition) && i > 0) {
                notifyItemRangeChanged(reverseDataArrayPosition, reverseDataArrayPosition + 1);
            } else {
                notifyItemInserted(reverseDataArrayPosition);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("admobErrorMr3", e.getMessage());
        } catch (IndexOutOfBoundsException e2) {
            Log.d("admobErrorMr3", e2.getMessage());
        }
    }

    public <T> void insert(List<T> list, List<T> list2, int i) {
        try {
            list.addAll(i, list2);
            notifyItemRangeChanged(getReverseDataArrayPosition(i), getReverseDataArrayPosition(i + list2.size()));
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("admobErrorMr3", e.getMessage());
        } catch (IndexOutOfBoundsException e2) {
            Log.d("admobErrorMr3", e2.getMessage());
        }
    }

    public void insert(T t) {
        List<T> list = this.list;
        insert((List<List<T>>) list, (List<T>) t, list.size());
    }

    public void insert(List<T> list) {
        List<T> list2 = this.list;
        insert((List) list2, (List) list, list2.size());
    }

    public void removeAll() {
        this.list.clear();
        notifyDataSetChanged();
    }

    @Override // com.marshalchen.ultimaterecyclerview.UltimateViewAdapter
    public void remove(List<?> list, int i) {
        try {
            if (list.size() > 0 && i < list.size()) {
                list.remove(i);
                int reverseDataArrayPosition = getReverseDataArrayPosition(i);
                notifyItemRemoved(reverseDataArrayPosition);
                if (reverseDataArrayPosition > 1 && isPosOnAdView(reverseDataArrayPosition) && i > 0) {
                    notifyItemRemoved(reverseDataArrayPosition - 1);
                }
                Log.d("normaladmob", "offset final: " + reverseDataArrayPosition);
                return;
            }
            throw new ArrayIndexOutOfBoundsException("no data or the remove position is not exist p:" + i + ", list size:" + list.size());
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("admobError r1", e.getMessage());
        } catch (IndexOutOfBoundsException e2) {
            Log.d("admobError r2", e2.getMessage());
        }
    }

    protected boolean onActionToBindData(int i, List<T> list) {
        int itemViewType = getItemViewType(i);
        if (i >= getItemCount() || itemViewType != 0) {
            return false;
        }
        return this.customHeaderView != null ? i <= list.size() && i > 0 : i < list.size();
    }

    protected T getItem(int i) {
        return this.list.get(getDataArrayPosition(i));
    }

    protected int getDataArrayPosition(int i) {
        int iAtAdPos = this.customHeaderView != null ? -1 : 0;
        int i2 = this.adfrequency;
        if (i2 > 0) {
            if (!this.once) {
                iAtAdPos -= atAdPos(i);
            } else if (i >= i2) {
                iAtAdPos--;
            }
        }
        return i + iAtAdPos;
    }

    protected int getReverseDataArrayPosition(int i) {
        int iAtAdPos = this.customHeaderView != null ? 1 : 0;
        int i2 = this.adfrequency;
        if (i2 > 0) {
            if (!this.once) {
                iAtAdPos += atAdPos(i);
            } else if (i >= i2) {
                iAtAdPos++;
            }
        }
        return i + iAtAdPos;
    }

    public boolean isPosOnAdView(int i) {
        return (i + 1) % this.adfrequency == 0;
    }

    public int atAdPos(int i) {
        int iFloor = (int) Math.floor((i + 1) / this.adfrequency);
        Log.d("atAdPosE2", iFloor + "");
        return iFloor;
    }

    public int getFinalShiftPosition(int i) {
        return getDataArrayPosition(i);
    }
}
