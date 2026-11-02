package com.app.piapostolos.Views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import com.app.piapostolos.R;
import it.orsozoxi.android.orsonotes.R2;

/* loaded from: classes.dex */
public class CircleLayout extends ViewGroup {
    private FlingRunnable actRunnable;
    private boolean allowRotating;
    private float angle;
    private int childHeight;
    private int childWidth;
    private int circleHeight;
    private int circleWidth;
    private float deceleration;
    private float firstChildPos;
    private Bitmap imageOriginal;
    private Bitmap imageScaled;
    private boolean isRotating;
    private GestureDetector mGestureDetector;
    private int mMaxChildHeight;
    private int mMaxChildWidth;
    private OnCenterClickListener mOnCenterClickListener;
    private OnItemClickListener mOnItemClickListener;
    private OnItemSelectedListener mOnItemSelectedListener;
    private OnRotationFinishedListener mOnRotationFinishedListener;
    private View mTappedView;
    private int mTappedViewsPostition;
    private Matrix matrix;
    private boolean[] quadrantTouched;
    private int radius;
    private boolean rotateToCenter;
    private int selected;
    private int speed;
    private double startAngle;

    public interface OnCenterClickListener {
        void onCenterClick();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String str);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(View view, String str);
    }

    public interface OnRotationFinishedListener {
        void onRotationFinished(View view, String str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getQuadrant(double d, double d2) {
        return d >= 0.0d ? d2 >= 0.0d ? 1 : 4 : d2 >= 0.0d ? 2 : 3;
    }

    public CircleLayout(Context context) {
        this(context, null);
    }

    public CircleLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOnItemClickListener = null;
        this.mOnItemSelectedListener = null;
        this.mOnCenterClickListener = null;
        this.mOnRotationFinishedListener = null;
        this.mTappedViewsPostition = -1;
        this.mTappedView = null;
        this.selected = 0;
        this.mMaxChildWidth = 0;
        this.mMaxChildHeight = 0;
        this.childWidth = 0;
        this.childHeight = 0;
        this.radius = 0;
        this.allowRotating = true;
        this.angle = 90.0f;
        this.firstChildPos = 270.0f;
        this.rotateToCenter = true;
        this.isRotating = true;
        this.speed = 75;
        this.deceleration = (5.0f / 75) + 1.0f;
        this.actRunnable = null;
        init(attributeSet);
    }

    protected void init(AttributeSet attributeSet) {
        int resourceId;
        this.mGestureDetector = new GestureDetector(getContext(), new MyGestureListener());
        this.quadrantTouched = new boolean[]{false, false, false, false, false};
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.Circle);
            float f = typedArrayObtainStyledAttributes.getInt(1, 90);
            this.angle = f;
            this.firstChildPos = f;
            this.rotateToCenter = typedArrayObtainStyledAttributes.getBoolean(3, true);
            this.isRotating = typedArrayObtainStyledAttributes.getBoolean(2, true);
            int i = typedArrayObtainStyledAttributes.getInt(4, 75);
            this.speed = i;
            this.deceleration = (5.0f / i) + 1.0f;
            if (!this.isRotating) {
                this.rotateToCenter = false;
            }
            if (this.imageOriginal == null && (resourceId = typedArrayObtainStyledAttributes.getResourceId(0, -1)) != -1) {
                this.imageOriginal = BitmapFactory.decodeResource(getResources(), resourceId);
            }
            typedArrayObtainStyledAttributes.recycle();
            Matrix matrix = this.matrix;
            if (matrix == null) {
                this.matrix = new Matrix();
            } else {
                matrix.reset();
            }
            setWillNotDraw(false);
        }
    }

    public View getSelectedItem() {
        int i = this.selected;
        if (i >= 0) {
            return getChildAt(i);
        }
        return null;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        this.circleHeight = getHeight();
        this.circleWidth = getWidth();
        if (this.imageOriginal != null) {
            if (this.imageScaled == null) {
                this.matrix = new Matrix();
                this.matrix.postScale(((this.radius + (this.childWidth / 4)) * 2) / this.imageOriginal.getWidth(), ((this.radius + (this.childWidth / 4)) * 2) / this.imageOriginal.getHeight());
                Bitmap bitmap = this.imageOriginal;
                this.imageScaled = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.imageOriginal.getHeight(), this.matrix, false);
            }
            Bitmap bitmap2 = this.imageScaled;
            if (bitmap2 != null) {
                int width = (this.circleWidth - bitmap2.getWidth()) / 2;
                int height = (this.circleHeight - this.imageScaled.getHeight()) / 2;
                canvas.rotate(0.0f, this.circleWidth / 2, this.circleHeight / 2);
                canvas.drawBitmap(this.imageScaled, width, height, (Paint) null);
            }
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        this.mMaxChildWidth = 0;
        this.mMaxChildHeight = 0;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), Integer.MIN_VALUE);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), Integer.MIN_VALUE);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                childAt.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                this.mMaxChildWidth = Math.max(this.mMaxChildWidth, childAt.getMeasuredWidth());
                this.mMaxChildHeight = Math.max(this.mMaxChildHeight, childAt.getMeasuredHeight());
            }
        }
        int iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(this.mMaxChildWidth, BasicMeasure.EXACTLY);
        int iMakeMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(this.mMaxChildHeight, BasicMeasure.EXACTLY);
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt2 = getChildAt(i4);
            if (childAt2.getVisibility() != 8) {
                childAt2.measure(iMakeMeasureSpec3, iMakeMeasureSpec4);
            }
        }
        setMeasuredDimension(resolveSize(this.mMaxChildWidth, i), resolveSize(this.mMaxChildHeight, i2));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6 = i3 - i;
        int i7 = i4 - i2;
        int childCount = getChildCount();
        if (i6 <= i7) {
            i5 = i6 / 3;
        } else {
            i5 = i7 / 3;
        }
        this.radius = i5;
        this.childWidth = (i5 * 5) / 5;
        this.childHeight = (i5 * 5) / 5;
        float childCount2 = R2.attr.dayStyle / getChildCount();
        for (int i8 = 0; i8 < childCount; i8++) {
            CircleImageView circleImageView = (CircleImageView) getChildAt(i8);
            if (circleImageView.getVisibility() != 8) {
                float f = this.angle;
                if (f > 360.0f) {
                    this.angle = f - 360.0f;
                } else if (f < 0.0f) {
                    this.angle = f + 360.0f;
                }
                circleImageView.setAngle(this.angle);
                circleImageView.setPosition(i8);
                int iRound = Math.round((float) (((i6 / 2) - (this.childWidth / 2)) + (this.radius * Math.cos(Math.toRadians(this.angle)))));
                int iRound2 = Math.round((float) (((i7 / 2) - (this.childHeight / 2)) + (this.radius * Math.sin(Math.toRadians(this.angle)))));
                circleImageView.layout(iRound, iRound2, this.childWidth + iRound, this.childHeight + iRound2);
                this.angle += childCount2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rotateButtons(float f) {
        ((Activity) getContext()).findViewById(R.id.Relative_home_main).setBackgroundResource(R.drawable.home_background);
        int childCount = getChildCount();
        float f2 = R2.attr.dayStyle / childCount;
        float f3 = this.angle + f;
        this.angle = f3;
        if (f3 > 360.0f) {
            this.angle = f3 - 360.0f;
        } else if (f3 < 0.0f) {
            this.angle = f3 + 360.0f;
        }
        for (int i = 0; i < childCount; i++) {
            float f4 = this.angle;
            if (f4 > 360.0f) {
                this.angle = f4 - 360.0f;
            } else if (f4 < 0.0f) {
                this.angle = f4 + 360.0f;
            }
            CircleImageView circleImageView = (CircleImageView) getChildAt(i);
            if (circleImageView.getVisibility() != 8) {
                int iRound = Math.round((float) (((this.circleWidth / 2) - (this.childWidth / 2)) + (this.radius * Math.cos(Math.toRadians(this.angle)))));
                int iRound2 = Math.round((float) (((this.circleHeight / 2) - (this.childHeight / 2)) + (this.radius * Math.sin(Math.toRadians(this.angle)))));
                circleImageView.setAngle(this.angle);
                if (i == 0) {
                    rotateBall(this.angle);
                }
                if (Math.abs(this.angle - this.firstChildPos) < f2 / 2.0f && this.selected != circleImageView.getPosition()) {
                    this.selected = circleImageView.getPosition();
                    OnItemSelectedListener onItemSelectedListener = this.mOnItemSelectedListener;
                    if (onItemSelectedListener != null && this.rotateToCenter) {
                        onItemSelectedListener.onItemSelected(circleImageView, circleImageView.getName());
                    }
                }
                circleImageView.layout(iRound, iRound2, this.childWidth + iRound, this.childHeight + iRound2);
                this.angle += f2;
            }
        }
    }

    private void rotateBall(float f) {
        if (Build.VERSION.SDK_INT >= 11) {
            ((Activity) getContext()).findViewById(R.id.ImageView_Balll).setRotation(f * (-1.0f));
        }
    }

    private double getAngle(double d, double d2) {
        double d3 = d - (this.circleWidth / 2.0d);
        int i = this.circleHeight;
        double d4 = (i - d2) - (i / 2.0d);
        int quadrant = getQuadrant(d3, d4);
        if (quadrant == 1) {
            return (Math.asin(d4 / Math.hypot(d3, d4)) * 180.0d) / 3.141592653589793d;
        }
        if (quadrant == 2 || quadrant == 3) {
            return 180.0d - ((Math.asin(d4 / Math.hypot(d3, d4)) * 180.0d) / 3.141592653589793d);
        }
        if (quadrant != 4) {
            return 0.0d;
        }
        return ((Math.asin(d4 / Math.hypot(d3, d4)) * 180.0d) / 3.141592653589793d) + 360.0d;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        if (this.isRotating) {
            int action = motionEvent.getAction();
            if (action == 0) {
                int i = 0;
                while (true) {
                    boolean[] zArr = this.quadrantTouched;
                    if (i >= zArr.length) {
                        break;
                    }
                    zArr[i] = false;
                    i++;
                }
                this.allowRotating = false;
                this.startAngle = getAngle(motionEvent.getX(), motionEvent.getY());
            } else if (action == 1) {
                this.allowRotating = true;
                rotateViewToCenter((CircleImageView) getChildAt(this.selected), false);
            } else if (action == 2) {
                double angle = getAngle(motionEvent.getX(), motionEvent.getY());
                rotateButtons((float) (this.startAngle - angle));
                this.startAngle = angle;
            }
        }
        this.quadrantTouched[getQuadrant(motionEvent.getX() - (this.circleWidth / 2), (this.circleHeight - motionEvent.getY()) - (this.circleHeight / 2))] = true;
        this.mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private MyGestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (!CircleLayout.this.isRotating) {
                return false;
            }
            int quadrant = CircleLayout.getQuadrant(motionEvent.getX() - (CircleLayout.this.circleWidth / 2), (CircleLayout.this.circleHeight - motionEvent.getY()) - (CircleLayout.this.circleHeight / 2));
            int quadrant2 = CircleLayout.getQuadrant(motionEvent2.getX() - (CircleLayout.this.circleWidth / 2), (CircleLayout.this.circleHeight - motionEvent2.getY()) - (CircleLayout.this.circleHeight / 2));
            if ((quadrant == 2 && quadrant2 == 2 && Math.abs(f) < Math.abs(f2)) || ((quadrant == 3 && quadrant2 == 3) || ((quadrant == 1 && quadrant2 == 3) || ((quadrant == 4 && quadrant2 == 4 && Math.abs(f) > Math.abs(f2)) || ((quadrant == 2 && quadrant2 == 3) || ((quadrant == 3 && quadrant2 == 2) || ((quadrant == 3 && quadrant2 == 4) || ((quadrant == 4 && quadrant2 == 3) || ((quadrant == 2 && quadrant2 == 4 && CircleLayout.this.quadrantTouched[3]) || (quadrant == 4 && quadrant2 == 2 && CircleLayout.this.quadrantTouched[3])))))))))) {
                CircleLayout circleLayout = CircleLayout.this;
                circleLayout.post(new FlingRunnable(circleLayout, (f + f2) * (-1.0f)));
            } else {
                CircleLayout circleLayout2 = CircleLayout.this;
                circleLayout2.post(new FlingRunnable(circleLayout2, f + f2));
            }
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            CircleLayout circleLayout = CircleLayout.this;
            circleLayout.mTappedViewsPostition = circleLayout.pointToPosition(motionEvent.getX(), motionEvent.getY());
            if (CircleLayout.this.mTappedViewsPostition < 0) {
                float f = CircleLayout.this.circleWidth / 2;
                float f2 = CircleLayout.this.circleHeight / 2;
                if (motionEvent.getX() < (CircleLayout.this.childWidth / 2) + f && motionEvent.getX() > f - (CircleLayout.this.childWidth / 2) && motionEvent.getY() < (CircleLayout.this.childHeight / 2) + f2 && motionEvent.getY() > f2 - (CircleLayout.this.childHeight / 2) && CircleLayout.this.mOnCenterClickListener != null) {
                    CircleLayout.this.mOnCenterClickListener.onCenterClick();
                    return true;
                }
            } else {
                CircleLayout circleLayout2 = CircleLayout.this;
                circleLayout2.mTappedView = circleLayout2.getChildAt(circleLayout2.mTappedViewsPostition);
                CircleLayout.this.mTappedView.setPressed(true);
            }
            if (CircleLayout.this.mTappedView != null) {
                CircleImageView circleImageView = (CircleImageView) CircleLayout.this.mTappedView;
                if (CircleLayout.this.selected != CircleLayout.this.mTappedViewsPostition) {
                    CircleLayout.this.rotateViewToCenter(circleImageView, false);
                    if (!CircleLayout.this.rotateToCenter) {
                        if (CircleLayout.this.mOnItemSelectedListener != null) {
                            CircleLayout.this.mOnItemSelectedListener.onItemSelected(CircleLayout.this.mTappedView, circleImageView.getName());
                        }
                        if (CircleLayout.this.mOnItemClickListener != null) {
                            CircleLayout.this.mOnItemClickListener.onItemClick(CircleLayout.this.mTappedView, circleImageView.getName());
                            ((Activity) CircleLayout.this.getContext()).findViewById(R.id.Relative_home_main).setBackgroundResource(R.drawable.back_selected);
                        }
                    }
                } else {
                    CircleLayout.this.rotateViewToCenter(circleImageView, false);
                    if (CircleLayout.this.mOnItemClickListener != null) {
                        CircleLayout.this.mOnItemClickListener.onItemClick(CircleLayout.this.mTappedView, circleImageView.getName());
                        ((Activity) CircleLayout.this.getContext()).findViewById(R.id.Relative_home_main).setBackgroundResource(R.drawable.back_selected);
                    }
                }
                return true;
            }
            return super.onSingleTapUp(motionEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rotateViewToCenter(CircleImageView circleImageView, boolean z) {
        int i;
        if (this.rotateToCenter) {
            float f = 1.0f;
            float angle = this.firstChildPos - circleImageView.getAngle();
            float f2 = 0.0f;
            if (angle < 0.0f) {
                angle += 360.0f;
            }
            if (angle > 180.0f) {
                i = -1;
                angle = 360.0f - angle;
            } else {
                i = 1;
            }
            while (f2 < angle) {
                f *= this.deceleration;
                f2 += f / this.speed;
            }
            post(new FlingRunnable(i * f, !z));
        }
    }

    private class FlingRunnable implements Runnable {
        private float angleDelay;
        private boolean isFirstForwarding;
        private float velocity;
        private boolean wasBigEnough;

        public FlingRunnable(CircleLayout circleLayout, float f) {
            this(f, true);
        }

        public FlingRunnable(float f, boolean z) {
            this.isFirstForwarding = true;
            this.wasBigEnough = false;
            this.velocity = f;
            this.angleDelay = R2.attr.dayStyle / CircleLayout.this.getChildCount();
            this.isFirstForwarding = z;
            if (Math.abs(f) > 1.0f) {
                this.wasBigEnough = true;
                CircleLayout.this.actRunnable = this;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (CircleLayout.this.allowRotating) {
                if (!CircleLayout.this.rotateToCenter) {
                    CircleLayout.this.rotateButtons(this.velocity / r0.speed);
                    this.velocity /= CircleLayout.this.deceleration;
                    CircleLayout.this.post(this);
                    return;
                }
                if (Math.abs(this.velocity) <= 1.0f) {
                    if (this.isFirstForwarding) {
                        this.isFirstForwarding = false;
                        CircleLayout circleLayout = CircleLayout.this;
                        circleLayout.rotateViewToCenter((CircleImageView) circleLayout.getChildAt(circleLayout.selected), true);
                        return;
                    }
                    return;
                }
                if (Math.abs(this.velocity) >= 200.0f || Math.abs(CircleLayout.this.angle - CircleLayout.this.firstChildPos) % this.angleDelay >= 2.0f) {
                    CircleLayout.this.rotateButtons(this.velocity / r0.speed);
                    this.velocity /= CircleLayout.this.deceleration;
                    CircleLayout.this.post(this);
                    return;
                }
                if (!this.wasBigEnough || CircleLayout.this.actRunnable != this || Math.abs(CircleLayout.this.angle - CircleLayout.this.firstChildPos) % this.angleDelay >= 2.0f || CircleLayout.this.mOnRotationFinishedListener == null) {
                    return;
                }
                CircleLayout circleLayout2 = CircleLayout.this;
                CircleImageView circleImageView = (CircleImageView) circleLayout2.getChildAt(circleLayout2.selected);
                CircleLayout.this.mOnRotationFinishedListener.onRotationFinished(circleImageView, circleImageView.getName());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int pointToPosition(float f, float f2) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getLeft() < f) {
                if (((((float) childAt.getRight()) > f) & (((float) childAt.getTop()) < f2)) && childAt.getBottom() > f2) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    public void setOnCenterClickListener(OnCenterClickListener onCenterClickListener) {
        this.mOnCenterClickListener = onCenterClickListener;
    }

    public void setOnRotationFinishedListener(OnRotationFinishedListener onRotationFinishedListener) {
        this.mOnRotationFinishedListener = onRotationFinishedListener;
    }
}
