package com.marshalchen.ultimaterecyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public abstract class DragDropTouchListener implements RecyclerView.OnItemTouchListener {
    private static final String LOG_TAG = "DRAG-DROP";
    private static final int MOVE_DURATION = 150;
    private int activePointerId;
    private Activity activity;
    private DisplayMetrics displayMetrics;
    private int downX;
    private int downY;
    private Drawable dragHighlight;
    private boolean dragging;
    private boolean enabled;
    private View mobileView;
    private int mobileViewCurrentPos;
    private int mobileViewStartY;
    private RecyclerView recyclerView;
    private final int scrollAmount;

    protected abstract void onItemDrop(RecyclerView recyclerView, int i);

    protected abstract void onItemSwitch(RecyclerView recyclerView, int i, int i2);

    public DragDropTouchListener(RecyclerView recyclerView, Activity activity) {
        this.downY = -1;
        this.downX = -1;
        this.mobileViewStartY = -1;
        this.mobileViewCurrentPos = -1;
        this.enabled = true;
        this.recyclerView = recyclerView;
        this.activity = activity;
        DisplayMetrics displayMetrics = recyclerView.getResources().getDisplayMetrics();
        this.displayMetrics = displayMetrics;
        this.scrollAmount = (int) (50.0f / displayMetrics.density);
        this.dragHighlight = recyclerView.getResources().getDrawable(R.drawable.drag_frame);
    }

    public DragDropTouchListener(RecyclerView recyclerView, Activity activity, Drawable drawable) {
        this(recyclerView, activity);
        this.dragHighlight = drawable;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (!this.enabled) {
            return false;
        }
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            return down(motionEvent);
        }
        if (action == 1) {
            return up(motionEvent);
        }
        if (action == 2) {
            return this.dragging && move(motionEvent);
        }
        if (action != 3) {
            return false;
        }
        return cancel(motionEvent);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (this.dragging) {
            int action = motionEvent.getAction() & 255;
            if (action == 1) {
                up(motionEvent);
            } else if (action == 2) {
                move(motionEvent);
            } else {
                if (action != 3) {
                    return;
                }
                cancel(motionEvent);
            }
        }
    }

    public void startDrag() {
        View viewFindChildViewUnder = this.recyclerView.findChildViewUnder(this.downX, this.downY);
        if (viewFindChildViewUnder == null) {
            return;
        }
        this.dragging = true;
        this.mobileViewCurrentPos = this.recyclerView.getChildPosition(viewFindChildViewUnder);
        int[] viewRawCoords = getViewRawCoords(viewFindChildViewUnder);
        ImageView imageViewCopyViewAsImage = copyViewAsImage(viewFindChildViewUnder);
        this.mobileView = imageViewCopyViewAsImage;
        imageViewCopyViewAsImage.setX(viewRawCoords[0]);
        this.mobileView.setY(viewRawCoords[1]);
        this.mobileViewStartY = viewRawCoords[1];
        this.activity.addContentView(this.mobileView, new ViewGroup.LayoutParams(-2, -2));
        this.mobileView.bringToFront();
        viewFindChildViewUnder.setVisibility(4);
    }

    private boolean down(MotionEvent motionEvent) {
        this.activePointerId = motionEvent.getPointerId(0);
        this.downY = (int) motionEvent.getY();
        this.downX = (int) motionEvent.getX();
        return false;
    }

    private boolean move(MotionEvent motionEvent) {
        if (this.activePointerId == -1) {
            return false;
        }
        this.mobileView.setY(this.mobileViewStartY + (((int) motionEvent.getY(motionEvent.findPointerIndex(r0))) - this.downY));
        switchViewsIfNeeded();
        scrollIfNeeded();
        return true;
    }

    private void switchViewsIfNeeded() {
        int i = this.mobileViewCurrentPos;
        int i2 = i - 1;
        int i3 = i + 1;
        View viewByPosition = getViewByPosition(i2);
        View viewByPosition2 = getViewByPosition(i3);
        int y = (int) this.mobileView.getY();
        if (viewByPosition != null && viewByPosition.getTop() > -1 && y < viewByPosition.getTop()) {
            Log.d(LOG_TAG, String.format("Got aboveView with top = %s, for position = %s, %s", Integer.valueOf(viewByPosition.getTop()), Integer.valueOf(i2), viewByPosition));
            doSwitch(viewByPosition, i, i2);
        }
        if (viewByPosition2 == null || viewByPosition2.getTop() <= -1 || y <= viewByPosition2.getTop()) {
            return;
        }
        Log.d(LOG_TAG, String.format("Got belowView with top = %s, for position = %s, %s", Integer.valueOf(viewByPosition2.getTop()), Integer.valueOf(i3), viewByPosition2));
        doSwitch(viewByPosition2, i, i3);
    }

    private void doSwitch(View view, int i, int i2) {
        View viewByPosition = getViewByPosition(i);
        int top = viewByPosition.getTop() - view.getTop();
        onItemSwitch(this.recyclerView, i, i2);
        view.setVisibility(4);
        viewByPosition.setVisibility(0);
        viewByPosition.setTranslationY(-top);
        viewByPosition.animate().translationYBy(top).setDuration(150L);
        this.mobileViewCurrentPos = i2;
    }

    private boolean up(MotionEvent motionEvent) {
        if (this.dragging) {
            onItemDrop(this.recyclerView, this.mobileViewCurrentPos);
        }
        reset();
        return false;
    }

    private boolean cancel(MotionEvent motionEvent) {
        reset();
        return false;
    }

    private void reset() {
        final View viewByPosition = getViewByPosition(this.mobileViewCurrentPos);
        if (viewByPosition != null && this.mobileView != null) {
            this.mobileView.animate().y(getViewRawCoords(viewByPosition)[1]).setDuration(150L).setListener(new AnimatorListenerAdapter() { // from class: com.marshalchen.ultimaterecyclerview.DragDropTouchListener.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    viewByPosition.setVisibility(0);
                    if (DragDropTouchListener.this.mobileView != null) {
                        ((ViewGroup) DragDropTouchListener.this.mobileView.getParent()).removeView(DragDropTouchListener.this.mobileView);
                        DragDropTouchListener.this.mobileView = null;
                    }
                }
            });
        }
        this.dragging = false;
        this.mobileViewStartY = -1;
        this.mobileViewCurrentPos = -1;
    }

    private View getViewByPosition(int i) {
        RecyclerView.ViewHolder viewHolderFindViewHolderForPosition = this.recyclerView.findViewHolderForPosition(i);
        if (viewHolderFindViewHolderForPosition == null) {
            return null;
        }
        return viewHolderFindViewHolderForPosition.itemView;
    }

    private boolean scrollIfNeeded() {
        int height = this.recyclerView.getHeight();
        int y = (int) this.mobileView.getY();
        int height2 = this.mobileView.getHeight();
        if (y <= 0) {
            this.recyclerView.scrollBy(0, -this.scrollAmount);
            return true;
        }
        if (y + height2 < height) {
            return false;
        }
        this.recyclerView.scrollBy(0, this.scrollAmount);
        return true;
    }

    private ImageView copyViewAsImage(View view) {
        if (view instanceof FrameLayout) {
            Drawable foreground = ((FrameLayout) view).getForeground();
            if (foreground != null) {
                foreground.setVisible(false, false);
            }
        } else if (view.getBackground() != null) {
            view.getBackground().setVisible(false, false);
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        view.draw(canvas);
        Drawable drawable = this.dragHighlight;
        if (drawable != null) {
            drawable.setBounds(0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
            this.dragHighlight.draw(canvas);
        }
        ImageView imageView = new ImageView(this.recyclerView.getContext());
        imageView.setImageBitmap(bitmapCreateBitmap);
        return imageView;
    }

    private int[] getViewRawCoords(View view) {
        int measuredHeight = this.displayMetrics.heightPixels - this.activity.findViewById(android.R.id.content).getMeasuredHeight();
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        iArr[1] = iArr[1] - measuredHeight;
        return iArr;
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }

    public void setCustomDragHighlight(Drawable drawable) {
        this.dragHighlight = drawable;
    }
}
