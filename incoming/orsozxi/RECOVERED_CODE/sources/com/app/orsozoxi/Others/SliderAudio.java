package com.app.orsozoxi.Others;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import com.app.orsozoxi.R;
import com.nineoldandroids.view.ViewHelper;

/* loaded from: classes.dex */
public class SliderAudio extends CustomView {
    private int backgroundColor;
    private int backgroundColorLine;
    private Ball ball;
    private int ballColor;
    private Bitmap bitmap;
    private int max;
    private int min;
    private OnValueChangedListener onValueChangedListener;
    private boolean placedBall;
    private boolean press;
    private int value;

    public interface OnValueChangedListener {
        void onValueChanged(int i);
    }

    public SliderAudio(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ballColor = Color.parseColor("#EE7429");
        this.backgroundColor = Color.parseColor("#000000");
        this.backgroundColorLine = Color.parseColor("#000000");
        this.max = 100;
        this.min = 0;
        this.placedBall = false;
        this.press = false;
        this.value = 0;
        setAttributes(attributeSet);
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int i) {
        this.max = i;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int i) {
        this.min = i;
    }

    public OnValueChangedListener getOnValueChangedListener() {
        return this.onValueChangedListener;
    }

    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(final int i) {
        if (!this.placedBall) {
            post(new Runnable() { // from class: com.app.orsozoxi.Others.SliderAudio.1
                @Override // java.lang.Runnable
                public void run() {
                    SliderAudio.this.setValue(i);
                }
            });
            return;
        }
        this.value = i;
        ViewHelper.setX(this.ball, ((i * ((this.ball.xFin - this.ball.xIni) / this.max)) + (getHeight() / 2)) - (this.ball.getWidth() / 2));
        this.ball.changeBackground();
    }

    @Override // android.view.View
    public void invalidate() {
        this.ball.invalidate();
        super.invalidate();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int x;
        this.isLastTouch = true;
        if (isEnabled()) {
            if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
                if (motionEvent.getX() <= getWidth() && motionEvent.getX() >= 0.0f) {
                    this.press = true;
                    float f = (this.ball.xFin - this.ball.xIni) / (this.max - this.min);
                    if (motionEvent.getX() > this.ball.xFin) {
                        x = this.max;
                    } else if (motionEvent.getX() < this.ball.xIni) {
                        x = this.min;
                    } else {
                        x = ((int) ((motionEvent.getX() - this.ball.xIni) / f)) + this.min;
                    }
                    if (this.value != x) {
                        this.value = x;
                        OnValueChangedListener onValueChangedListener = this.onValueChangedListener;
                        if (onValueChangedListener != null) {
                            onValueChangedListener.onValueChanged(x);
                        }
                    }
                    float x2 = motionEvent.getX();
                    if (x2 < this.ball.xIni) {
                        x2 = this.ball.xIni;
                    }
                    if (x2 > this.ball.xFin) {
                        x2 = this.ball.xFin;
                    }
                    ViewHelper.setX(this.ball, x2);
                    this.ball.changeBackground();
                } else {
                    this.press = false;
                    this.isLastTouch = false;
                }
            } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                this.press = false;
            }
        }
        return true;
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.backgroundColor = i;
        this.backgroundColorLine = makeLineDeselectColor();
        if (isEnabled()) {
            this.beforeBackground = this.backgroundColor;
        }
    }

    protected int makeLineDeselectColor() {
        int i = this.backgroundColor;
        int i2 = (i >> 16) & 255;
        int i3 = (i >> 8) & 255;
        int i4 = (i >> 0) & 255;
        int i5 = i2 - 30;
        if (i5 < 0) {
            i5 = 0;
        }
        int i6 = i3 - 30;
        if (i6 < 0) {
            i6 = 0;
        }
        int i7 = i4 - 30;
        return Color.argb(128, i5, i6, i7 >= 0 ? i7 : 0);
    }

    @Override // com.app.orsozoxi.Others.CustomView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.placedBall) {
            placeBall();
        }
        Paint paint = new Paint();
        paint.setColor(this.backgroundColorLine);
        paint.setStrokeWidth(dpToPx(6.0f, getResources()));
        canvas.drawLine(getHeight() / 2, getHeight() / 2, getWidth() - (getHeight() / 2), getHeight() / 2, paint);
        paint.setColor(this.backgroundColor);
        float f = this.ball.xFin - this.ball.xIni;
        int i = this.max;
        int i2 = this.min;
        canvas.drawLine(getHeight() / 2, getHeight() / 2, ((this.value - i2) * (f / (i - i2))) + (getHeight() / 2), getHeight() / 2, paint);
        if (this.press) {
            paint.setColor(this.backgroundColor);
            paint.setAntiAlias(true);
            canvas.drawCircle(ViewHelper.getX(this.ball) + (this.ball.getWidth() / 2), getHeight() / 2, getHeight() / 4, paint);
        }
        invalidate();
    }

    protected void setAttributes(AttributeSet attributeSet) {
        setBackgroundResource(R.drawable.background_transparent);
        setMinimumHeight(dpToPx(48.0f, getResources()));
        setMinimumWidth(dpToPx(80.0f, getResources()));
        int attributeResourceValue = attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", -1);
        if (attributeResourceValue != -1) {
            setBackgroundColor(getResources().getColor(attributeResourceValue));
        } else {
            int attributeIntValue = attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "background", -1);
            if (attributeIntValue != -1) {
                setBackgroundColor(attributeIntValue);
            }
        }
        this.min = attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "min", 0);
        this.max = attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "max", 0);
        this.value = attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "value", this.min);
        this.ball = new Ball(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dpToPx(15.0f, getResources()), dpToPx(15.0f, getResources()));
        layoutParams.addRule(15, -1);
        this.ball.setLayoutParams(layoutParams);
        addView(this.ball);
    }

    private void placeBall() {
        ViewHelper.setX(this.ball, (getHeight() / 2) - (this.ball.getWidth() / 2));
        Ball ball = this.ball;
        ball.xIni = ViewHelper.getX(ball);
        this.ball.xFin = (getWidth() - (getHeight() / 2)) - (this.ball.getWidth() / 2);
        this.ball.xCen = (getWidth() / 2) - (this.ball.getWidth() / 2);
        this.placedBall = true;
    }

    class Ball extends View {
        float xCen;
        float xFin;
        float xIni;

        public Ball(Context context) {
            super(context);
            changeBackground();
        }

        public void changeBackground() {
            setBackgroundResource(R.drawable.background_checkbox);
            ((GradientDrawable) ((LayerDrawable) getBackground()).findDrawableByLayerId(R.id.shape_bacground)).setColor(SliderAudio.this.ballColor);
        }
    }

    public static int dpToPx(float f, Resources resources) {
        return (int) TypedValue.applyDimension(1, f, resources.getDisplayMetrics());
    }

    public static int getRelativeTop(View view) {
        if (view.getId() == 16908290) {
            return view.getTop();
        }
        return view.getTop() + getRelativeTop((View) view.getParent());
    }

    public static int getRelativeLeft(View view) {
        if (view.getId() == 16908290) {
            return view.getLeft();
        }
        return view.getLeft() + getRelativeLeft((View) view.getParent());
    }
}
