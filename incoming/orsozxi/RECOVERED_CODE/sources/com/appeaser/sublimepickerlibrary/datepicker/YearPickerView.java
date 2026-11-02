package com.appeaser.sublimepickerlibrary.datepicker;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.appeaser.sublimepickerlibrary.utilities.SUtils;
import java.util.Calendar;

/* loaded from: classes.dex */
public class YearPickerView extends ListView {
    private YearAdapter mAdapter;
    private int mChildSize;
    private OnYearSelectedListener mOnYearSelectedListener;
    private int mViewSize;

    public interface OnYearSelectedListener {
        void onYearChanged(YearPickerView yearPickerView, int i);
    }

    public YearPickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listViewStyle);
    }

    public YearPickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public YearPickerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    private void init() {
        setLayoutParams(new AbsListView.LayoutParams(-1, -2));
        Resources resources = getContext().getResources();
        this.mViewSize = resources.getDimensionPixelOffset(com.appeaser.sublimepickerlibrary.R.dimen.datepicker_view_animator_height);
        this.mChildSize = resources.getDimensionPixelOffset(com.appeaser.sublimepickerlibrary.R.dimen.datepicker_year_label_height);
        setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.appeaser.sublimepickerlibrary.datepicker.YearPickerView.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                int yearForPosition = YearPickerView.this.mAdapter.getYearForPosition(i);
                YearPickerView.this.mAdapter.setSelection(yearForPosition);
                if (YearPickerView.this.mOnYearSelectedListener != null) {
                    YearPickerView.this.mOnYearSelectedListener.onYearChanged(YearPickerView.this, yearForPosition);
                }
            }
        });
        YearAdapter yearAdapter = new YearAdapter(getContext());
        this.mAdapter = yearAdapter;
        setAdapter((ListAdapter) yearAdapter);
    }

    public void setOnYearSelectedListener(OnYearSelectedListener onYearSelectedListener) {
        this.mOnYearSelectedListener = onYearSelectedListener;
    }

    public void setYear(final int i) {
        this.mAdapter.setSelection(i);
        post(new Runnable() { // from class: com.appeaser.sublimepickerlibrary.datepicker.YearPickerView.2
            @Override // java.lang.Runnable
            public void run() {
                int positionForYear = YearPickerView.this.mAdapter.getPositionForYear(i);
                if (positionForYear < 0 || positionForYear >= YearPickerView.this.getCount()) {
                    return;
                }
                YearPickerView.this.setSelectionCentered(positionForYear);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSelectionCentered(int i) {
        setSelectionFromTop(i, (this.mViewSize / 2) - (this.mChildSize / 2));
    }

    public void setRange(Calendar calendar, Calendar calendar2) {
        this.mAdapter.setRange(calendar, calendar2);
    }

    private static class YearAdapter extends BaseAdapter {
        private int mActivatedYear;
        private final Context mContext;
        private int mCount;
        private final LayoutInflater mInflater;
        private int mMinYear;
        private static final int ITEM_LAYOUT = com.appeaser.sublimepickerlibrary.R.layout.year_label_text_view;
        private static final int ITEM_TEXT_APPEARANCE = com.appeaser.sublimepickerlibrary.R.style.SPYearLabelTextAppearance;
        private static final int ITEM_TEXT_ACTIVATED_APPEARANCE = com.appeaser.sublimepickerlibrary.R.style.SPYearLabelActivatedTextAppearance;

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getItemViewType(int i) {
            return 0;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getViewTypeCount() {
            return 1;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public boolean hasStableIds() {
            return true;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public boolean isEmpty() {
            return false;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean isEnabled(int i) {
            return true;
        }

        public YearAdapter(Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
        }

        public void setRange(Calendar calendar, Calendar calendar2) {
            int i = calendar.get(1);
            int i2 = (calendar2.get(1) - i) + 1;
            if (this.mMinYear == i && this.mCount == i2) {
                return;
            }
            this.mMinYear = i;
            this.mCount = i2;
            notifyDataSetInvalidated();
        }

        public boolean setSelection(int i) {
            if (this.mActivatedYear == i) {
                return false;
            }
            this.mActivatedYear = i;
            notifyDataSetChanged();
            return true;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mCount;
        }

        @Override // android.widget.Adapter
        public Integer getItem(int i) {
            return Integer.valueOf(getYearForPosition(i));
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return getYearForPosition(i);
        }

        public int getPositionForYear(int i) {
            return i - this.mMinYear;
        }

        public int getYearForPosition(int i) {
            return this.mMinYear + i;
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView;
            int i2;
            boolean z = view == null;
            if (z) {
                textView = (TextView) this.mInflater.inflate(ITEM_LAYOUT, viewGroup, false);
            } else {
                textView = (TextView) view;
            }
            int yearForPosition = getYearForPosition(i);
            boolean z2 = this.mActivatedYear == yearForPosition;
            if (z || textView.isActivated() != z2) {
                if (!z2 || (i2 = ITEM_TEXT_ACTIVATED_APPEARANCE) == 0) {
                    i2 = ITEM_TEXT_APPEARANCE;
                }
                if (SUtils.isApi_23_OrHigher()) {
                    textView.setTextAppearance(i2);
                } else {
                    textView.setTextAppearance(this.mContext, i2);
                }
                textView.setActivated(z2);
            }
            textView.setText(Integer.toString(yearForPosition));
            return textView;
        }
    }

    public int getFirstPositionOffset() {
        View childAt = getChildAt(0);
        if (childAt == null) {
            return 0;
        }
        return childAt.getTop();
    }
}
