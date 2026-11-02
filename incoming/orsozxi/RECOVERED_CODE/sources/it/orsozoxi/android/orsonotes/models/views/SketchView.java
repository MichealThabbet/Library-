package it.orsozoxi.android.orsonotes.models.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.ViewCompat;
import it.orsozoxi.android.orsonotes.helpers.LogDelegate;
import it.orsozoxi.android.orsonotes.models.listeners.OnDrawChangedListener;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class SketchView extends View implements View.OnTouchListener {
    public static final int DEFAULT_ERASER_SIZE = 50;
    public static final int DEFAULT_STROKE_SIZE = 7;
    public static final int ERASER = 1;
    public static final int STROKE = 0;
    private static final float TOUCH_TOLERANCE = 4.0f;
    private int backgroundColor;
    private Bitmap bitmap;
    private float eraserSize;
    private int height;
    private Context mContext;
    private float mX;
    private float mY;
    private Paint m_Paint;
    private Path m_Path;
    private int mode;
    private OnDrawChangedListener onDrawChangedListener;
    private ArrayList<Pair<Path, Paint>> paths;
    private int strokeColor;
    private float strokeSize;
    private ArrayList<Pair<Path, Paint>> undonePaths;
    private int width;

    public SketchView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.strokeSize = 7.0f;
        this.strokeColor = ViewCompat.MEASURED_STATE_MASK;
        this.eraserSize = 50.0f;
        this.backgroundColor = -1;
        this.paths = new ArrayList<>();
        this.undonePaths = new ArrayList<>();
        this.mode = 0;
        this.mContext = context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        setBackgroundColor(this.backgroundColor);
        setOnTouchListener(this);
        Paint paint = new Paint();
        this.m_Paint = paint;
        paint.setAntiAlias(true);
        this.m_Paint.setDither(true);
        this.m_Paint.setColor(this.strokeColor);
        this.m_Paint.setStyle(Paint.Style.STROKE);
        this.m_Paint.setStrokeJoin(Paint.Join.ROUND);
        this.m_Paint.setStrokeCap(Paint.Cap.ROUND);
        this.m_Paint.setStrokeWidth(this.strokeSize);
        this.m_Path = new Path();
        invalidate();
    }

    public void setMode(int i) {
        if (i == 0 || i == 1) {
            this.mode = i;
        }
    }

    public int getMode() {
        return this.mode;
    }

    public void setBackgroundBitmap(Activity activity, Bitmap bitmap) {
        if (!bitmap.isMutable()) {
            Bitmap.Config config = bitmap.getConfig();
            if (config == null) {
                config = Bitmap.Config.ARGB_8888;
            }
            bitmap = bitmap.copy(config, true);
        }
        this.bitmap = bitmap;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        this.width = View.MeasureSpec.getSize(i);
        int size = View.MeasureSpec.getSize(i2);
        this.height = size;
        setMeasuredDimension(this.width, size);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            touch_start(x, y);
            invalidate();
        } else if (action == 1) {
            invalidate();
        } else if (action == 2) {
            touch_move(x, y);
            invalidate();
        } else {
            LogDelegate.e("Wrong element choosen: " + motionEvent.getAction());
        }
        return true;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        }
        Iterator<Pair<Path, Paint>> it2 = this.paths.iterator();
        while (it2.hasNext()) {
            Pair<Path, Paint> next = it2.next();
            canvas.drawPath((Path) next.first, (Paint) next.second);
        }
        this.onDrawChangedListener.onDrawChanged();
    }

    private void touch_start(float f, float f2) {
        this.undonePaths.clear();
        if (this.mode == 1) {
            this.m_Paint.setColor(this.backgroundColor);
            this.m_Paint.setStrokeWidth(this.eraserSize);
        } else {
            this.m_Paint.setColor(this.strokeColor);
            this.m_Paint.setStrokeWidth(this.strokeSize);
        }
        if (!this.paths.isEmpty() || this.mode != 1 || this.bitmap != null) {
            this.m_Path = new Path();
            this.paths.add(new Pair<>(this.m_Path, new Paint(this.m_Paint)));
        }
        this.m_Path.moveTo(f, f2);
        float f3 = f + 1.0f;
        this.m_Path.lineTo(f3, f2);
        this.mX = f3;
        this.mY = f2;
    }

    private void touch_move(float f, float f2) {
        Path path = this.m_Path;
        float f3 = this.mX;
        float f4 = this.mY;
        path.quadTo(f3, f4, (f + f3) / 2.0f, (f2 + f4) / 2.0f);
        this.mX = f;
        this.mY = f2;
    }

    public Bitmap getBitmap() {
        if (this.paths.isEmpty()) {
            return null;
        }
        if (this.bitmap == null) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
            this.bitmap = bitmapCreateBitmap;
            bitmapCreateBitmap.eraseColor(this.backgroundColor);
        }
        Canvas canvas = new Canvas(this.bitmap);
        Iterator<Pair<Path, Paint>> it2 = this.paths.iterator();
        while (it2.hasNext()) {
            Pair<Path, Paint> next = it2.next();
            canvas.drawPath((Path) next.first, (Paint) next.second);
        }
        return this.bitmap;
    }

    public void undo() {
        if (this.paths.isEmpty()) {
            return;
        }
        this.undonePaths.add(this.paths.remove(r1.size() - 1));
        invalidate();
    }

    public void redo() {
        if (this.undonePaths.isEmpty()) {
            return;
        }
        this.paths.add(this.undonePaths.remove(r1.size() - 1));
        invalidate();
    }

    public int getUndoneCount() {
        return this.undonePaths.size();
    }

    public ArrayList<Pair<Path, Paint>> getPaths() {
        return this.paths;
    }

    public void setPaths(ArrayList<Pair<Path, Paint>> arrayList) {
        this.paths = arrayList;
    }

    public ArrayList<Pair<Path, Paint>> getUndonePaths() {
        return this.undonePaths;
    }

    public void setUndonePaths(ArrayList<Pair<Path, Paint>> arrayList) {
        this.undonePaths = arrayList;
    }

    public int getStrokeSize() {
        return Math.round(this.strokeSize);
    }

    public void setSize(int i, int i2) {
        if (i2 == 0) {
            this.strokeSize = i;
            return;
        }
        if (i2 == 1) {
            this.eraserSize = i;
            return;
        }
        LogDelegate.e("Wrong element choosen: " + i2);
    }

    public int getStrokeColor() {
        return this.strokeColor;
    }

    public void setStrokeColor(int i) {
        this.strokeColor = i;
    }

    public void erase() {
        this.paths.clear();
        this.undonePaths.clear();
        invalidate();
    }

    public void setOnDrawChangedListener(OnDrawChangedListener onDrawChangedListener) {
        this.onDrawChangedListener = onDrawChangedListener;
    }
}
