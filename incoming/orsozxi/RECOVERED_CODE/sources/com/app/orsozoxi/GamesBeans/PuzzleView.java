package com.app.orsozoxi.GamesBeans;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import com.app.orsozoxi.PuzzleActivity;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class PuzzleView extends View {
    private static final int COLOR_ACTIVE = -13619152;
    private static final int COLOR_SOLVED = -16777216;
    private static final int FRAME_SHRINK = 1;
    private static final long VIBRATE_DRAG = 5;
    private static final long VIBRATE_MATCH = 50;
    private static final long VIBRATE_SOLVED = 250;
    private Bitmap bitmap;
    private int canvasHeight;
    private int canvasWidth;
    private int dragDirection;
    private boolean dragInTarget;
    private int dragOffsetX;
    private int dragOffsetY;
    private int dragStartX;
    private int dragStartY;
    private Set<Integer> dragging;
    private Paint framePaint;
    private Puzzle puzzle;
    private int puzzleHeight;
    private int puzzleWidth;
    private ShowNumbers showNumbers;
    private int sourceColumnWidth;
    private int sourceHeight;
    private Rect sourceRect;
    private int sourceRowHeight;
    private int sourceWidth;
    private int targetColumnWidth;
    private int targetHeight;
    private int targetOffsetX;
    private int targetOffsetY;
    private RectF targetRect;
    private int targetRowHeight;
    private int targetWidth;
    private Paint textPaint;
    private Paint tilePaint;
    private int[] tiles;

    public enum ShowNumbers {
        NONE,
        SOME,
        ALL
    }

    public PuzzleView(Context context, Puzzle puzzle) {
        super(context);
        this.dragging = null;
        this.showNumbers = ShowNumbers.SOME;
        this.dragInTarget = false;
        this.sourceRect = new Rect();
        this.targetRect = new RectF();
        this.puzzle = puzzle;
        Paint paint = new Paint();
        this.tilePaint = paint;
        paint.setAntiAlias(true);
        this.tilePaint.setDither(true);
        this.tilePaint.setFilterBitmap(true);
        Paint paint2 = new Paint();
        this.textPaint = paint2;
        paint2.setARGB(255, 255, 255, 255);
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTextAlign(Paint.Align.CENTER);
        this.textPaint.setTextSize(20.0f);
        this.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        this.textPaint.setShadowLayer(1.0f, 2.0f, 2.0f, -16777216);
        Paint paint3 = new Paint();
        this.framePaint = paint3;
        paint3.setARGB(255, 128, 128, 128);
        this.framePaint.setStyle(Paint.Style.STROKE);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        this.puzzleHeight = 0;
        this.puzzleWidth = 0;
    }

    private void refreshDimensions(Canvas canvas) {
        this.targetWidth = this.canvasWidth;
        this.targetHeight = this.canvasHeight;
        this.sourceWidth = this.bitmap.getWidth();
        int height = this.bitmap.getHeight();
        this.sourceHeight = height;
        int i = this.targetWidth;
        int i2 = this.targetHeight;
        double d = i / i2;
        double d2 = this.sourceWidth / height;
        this.targetOffsetX = 0;
        this.targetOffsetY = 0;
        if (d2 > d) {
            int i3 = (int) (i / d2);
            this.targetOffsetY = (i2 - i3) / 2;
            this.targetHeight = i3;
        } else if (d2 < d) {
            int i4 = (int) (i2 * d2);
            this.targetOffsetX = (i - i4) / 2;
            this.targetWidth = i4;
        }
        this.puzzleWidth = this.puzzle.getWidth();
        int height2 = this.puzzle.getHeight();
        this.puzzleHeight = height2;
        int i5 = this.targetWidth;
        int i6 = this.puzzleWidth;
        this.targetColumnWidth = i5 / i6;
        this.targetRowHeight = this.targetHeight / height2;
        this.sourceColumnWidth = this.sourceWidth / i6;
        this.sourceRowHeight = this.sourceHeight / height2;
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01db  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void onDraw(android.graphics.Canvas r19) {
        /*
            Method dump skipped, instructions count: 833
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.orsozoxi.GamesBeans.PuzzleView.onDraw(android.graphics.Canvas):void");
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Puzzle puzzle = this.puzzle;
        if (puzzle == null || this.bitmap == null || puzzle.isSolved()) {
            return false;
        }
        if (motionEvent.getAction() == 0) {
            return startDrag(motionEvent);
        }
        if (motionEvent.getAction() == 2) {
            return updateDrag(motionEvent);
        }
        if (motionEvent.getAction() == 1) {
            return finishDrag(motionEvent);
        }
        return false;
    }

    private boolean finishDrag(MotionEvent motionEvent) {
        if (this.dragging == null) {
            return false;
        }
        updateDrag(motionEvent);
        if (this.dragInTarget) {
            doMove(this.dragDirection, this.dragging.size());
        } else {
            vibrate(5L);
        }
        this.dragInTarget = false;
        this.dragging = null;
        invalidate();
        return true;
    }

    private void doMove(int i, int i2) {
        if (this.puzzle.move(i, i2)) {
            vibrate(this.puzzle.isSolved() ? VIBRATE_SOLVED : VIBRATE_MATCH);
        } else {
            vibrate(5L);
        }
        invalidate();
        if (this.puzzle.isSolved()) {
            onFinish();
        }
    }

    private void vibrate(long j) {
        Vibrator vibrator = (Vibrator) getContext().getSystemService("vibrator");
        if (vibrator != null) {
            vibrator.vibrate(j);
        }
    }

    private void onFinish() {
        ((PuzzleActivity) getContext()).onFinish();
    }

    private boolean startDrag(MotionEvent motionEvent) {
        int i;
        if (this.dragging != null) {
            return false;
        }
        int x = (((int) motionEvent.getX()) - this.targetOffsetX) / this.targetColumnWidth;
        int y = (((int) motionEvent.getY()) - this.targetOffsetY) / this.targetRowHeight;
        if (x < 0 || x >= (i = this.puzzleWidth) || y < 0 || y >= this.puzzleHeight) {
            return false;
        }
        int direction = this.puzzle.getDirection((i * y) + x);
        if (direction >= 0) {
            this.dragging = new HashSet();
            while ((this.puzzleWidth * y) + x != this.puzzle.getHandleLocation()) {
                this.dragging.add(Integer.valueOf((this.puzzleWidth * y) + x));
                this.dragStartX = (int) motionEvent.getX();
                this.dragStartY = (int) motionEvent.getY();
                this.dragOffsetX = 0;
                this.dragOffsetY = 0;
                this.dragDirection = direction;
                x -= Puzzle.DIRECTION_X[direction];
                y -= Puzzle.DIRECTION_Y[direction];
            }
        }
        this.dragInTarget = false;
        vibrate(5L);
        return true;
    }

    private boolean updateDrag(MotionEvent motionEvent) {
        if (this.dragging == null) {
            return false;
        }
        int i = Puzzle.DIRECTION_X[this.dragDirection] * (-1);
        int i2 = Puzzle.DIRECTION_Y[this.dragDirection] * (-1);
        if (i != 0) {
            int x = ((int) motionEvent.getX()) - this.dragStartX;
            this.dragOffsetX = x;
            if (Math.signum(x) != i) {
                this.dragOffsetX = 0;
            } else {
                int iAbs = Math.abs(this.dragOffsetX);
                int i3 = this.targetColumnWidth;
                if (iAbs > i3) {
                    this.dragOffsetX = i * i3;
                }
            }
        }
        if (i2 != 0) {
            int y = ((int) motionEvent.getY()) - this.dragStartY;
            this.dragOffsetY = y;
            if (Math.signum(y) != i2) {
                this.dragOffsetY = 0;
            } else {
                int iAbs2 = Math.abs(this.dragOffsetY);
                int i4 = this.targetRowHeight;
                if (iAbs2 > i4) {
                    this.dragOffsetY = i2 * i4;
                }
            }
        }
        this.dragInTarget = Math.abs(this.dragOffsetX) > this.targetColumnWidth / 2 || Math.abs(this.dragOffsetY) > this.targetRowHeight / 2;
        invalidate();
        return true;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.puzzleWidth = 0;
        this.puzzleHeight = 0;
    }

    public ShowNumbers getShowNumbers() {
        return this.showNumbers;
    }

    public void setShowNumbers(ShowNumbers showNumbers) {
        this.showNumbers = showNumbers;
    }

    public int getTargetWidth() {
        return this.targetWidth;
    }

    public int getTargetHeight() {
        return this.targetHeight;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (this.canvasWidth == size && this.canvasHeight == size2) {
            return;
        }
        this.canvasWidth = size;
        this.canvasHeight = size2;
        this.puzzleWidth = 0;
        this.puzzleHeight = 0;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }
}
