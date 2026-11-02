package it.orsozoxi.android.orsonotes.models.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/* loaded from: classes3.dex */
public class VerticalSeekBar extends SeekBar {
    protected SeekBar.OnSeekBarChangeListener changeListener;
    protected int w;
    protected int x;
    protected int y;
    protected int z;

    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i2, i, i4, i3);
        this.x = i;
        this.y = i2;
        this.z = i3;
        this.w = i4;
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i2, i);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.rotate(-90.0f);
        canvas.translate(-getHeight(), 0.0f);
        super.onDraw(canvas);
    }

    @Override // android.widget.AbsSeekBar, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            setSelected(true);
            setPressed(true);
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.changeListener;
            if (onSeekBarChangeListener != null) {
                onSeekBarChangeListener.onStartTrackingTouch(this);
            }
        } else if (action == 1) {
            setSelected(false);
            setPressed(false);
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 = this.changeListener;
            if (onSeekBarChangeListener2 != null) {
                onSeekBarChangeListener2.onStopTrackingTouch(this);
            }
        } else if (action == 2) {
            int max = getMax() - ((int) ((getMax() * motionEvent.getY()) / getHeight()));
            setProgress(max);
            onSizeChanged(getWidth(), getHeight(), 0, 0);
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener3 = this.changeListener;
            if (onSeekBarChangeListener3 != null) {
                onSeekBarChangeListener3.onProgressChanged(this, max, true);
            }
        }
        return true;
    }

    @Override // android.widget.SeekBar
    public synchronized void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.changeListener = onSeekBarChangeListener;
    }

    @Override // android.widget.ProgressBar
    public synchronized void setProgress(int i) {
        if (i >= 0) {
            super.setProgress(i);
        } else {
            super.setProgress(0);
        }
        onSizeChanged(this.x, this.y, this.z, this.w);
        if (this.changeListener != null) {
            this.changeListener.onProgressChanged(this, i, false);
        }
    }
}
