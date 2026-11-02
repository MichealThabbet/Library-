package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class TimedUndoAdapter extends SimpleSwipeUndoAdapter {
    public static final long DEFAULT_TIMEOUT_MS = 3000;
    private final Handler mHandler;
    private final Map<Integer, TimeoutRunnable> mRunnables;
    private long mTimeoutMs;

    public <V extends BaseAdapter & UndoAdapter> TimedUndoAdapter(V v, Context context, OnDismissCallback onDismissCallback) {
        super(v, context, onDismissCallback);
        this.mTimeoutMs = DEFAULT_TIMEOUT_MS;
        this.mHandler = new Handler();
        this.mRunnables = new HashMap();
    }

    public void setTimeoutMs(long j) {
        this.mTimeoutMs = j;
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter, com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoCallback
    public void onUndoShown(View view, int i) {
        super.onUndoShown(view, i);
        TimeoutRunnable timeoutRunnable = new TimeoutRunnable(i);
        this.mRunnables.put(Integer.valueOf(i), timeoutRunnable);
        this.mHandler.postDelayed(timeoutRunnable, this.mTimeoutMs);
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter, com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoCallback
    public void onUndo(View view, int i) {
        super.onUndo(view, i);
        cancelCallback(i);
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter, com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoCallback
    public void onDismiss(View view, int i) {
        super.onDismiss(view, i);
        cancelCallback(i);
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SwipeUndoAdapter
    public void dismiss(int i) {
        super.dismiss(i);
        cancelCallback(i);
    }

    private void cancelCallback(int i) {
        TimeoutRunnable timeoutRunnable = this.mRunnables.get(Integer.valueOf(i));
        if (timeoutRunnable != null) {
            this.mHandler.removeCallbacks(timeoutRunnable);
            this.mRunnables.remove(Integer.valueOf(i));
        }
    }

    @Override // com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter, com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback
    public void onDismiss(ViewGroup viewGroup, int[] iArr) {
        super.onDismiss(viewGroup, iArr);
        HashMap map = new HashMap();
        for (int i : iArr) {
            Iterator<Integer> it2 = this.mRunnables.keySet().iterator();
            while (it2.hasNext()) {
                int iIntValue = it2.next().intValue();
                TimeoutRunnable timeoutRunnable = this.mRunnables.get(Integer.valueOf(iIntValue));
                if (iIntValue > i) {
                    int i2 = iIntValue - 1;
                    timeoutRunnable.setPosition(i2);
                    map.put(Integer.valueOf(i2), timeoutRunnable);
                } else if (iIntValue != i) {
                    map.put(Integer.valueOf(iIntValue), timeoutRunnable);
                }
            }
            this.mRunnables.clear();
            this.mRunnables.putAll(map);
            map.clear();
        }
    }

    private class TimeoutRunnable implements Runnable {
        private int mPosition;

        TimeoutRunnable(int i) {
            this.mPosition = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            TimedUndoAdapter.this.dismiss(this.mPosition);
        }

        public int getPosition() {
            return this.mPosition;
        }

        public void setPosition(int i) {
            this.mPosition = i;
        }
    }
}
