package it.feio.android.checklistview.dragging;

import android.animation.LayoutTransition;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import it.feio.android.checklistview.App;
import it.feio.android.checklistview.interfaces.Constants;
import it.feio.android.checklistview.models.CheckListViewItem;
import it.feio.android.checklistview.utils.DensityUtil;

/* loaded from: classes2.dex */
public class ChecklistViewItemOnDragListener implements View.OnDragListener {
    private static final int DIRECTION_DOWN = 1;
    private static final int DIRECTION_UP = 0;
    private static final String TAG = ChecklistViewItemOnDragListener.class.getSimpleName();
    private LayoutTransition containerLayoutTransition;
    private int dragDirection;
    private boolean scroll = false;
    private ScrollView scrollView;
    private Thread scrollerThread;
    private float y;

    @Override // android.view.View.OnDragListener
    public boolean onDrag(View view, DragEvent dragEvent) {
        int action = dragEvent.getAction();
        View view2 = (View) ((View) dragEvent.getLocalState()).getParent();
        this.scrollView = (ScrollView) getScrollableAncestor(view2);
        Log.v(TAG, "Dragging item. Action: " + action);
        if (action == 1) {
            return actionDragStarted(dragEvent, view2);
        }
        if (action == 2) {
            return actionDragLocation(view, dragEvent);
        }
        if (action == 3) {
            return actionDrop(view2);
        }
        if (action == 5) {
            return actionDragEntered(view, view2);
        }
        if (action != 6) {
            return true;
        }
        return actionDragExited(view, view2);
    }

    private boolean actionDrop(View view) {
        ((ViewGroup) view.getParent()).setLayoutTransition(this.containerLayoutTransition);
        stopScrolling();
        view.setVisibility(0);
        return true;
    }

    private boolean actionDragLocation(View view, DragEvent dragEvent) {
        if (!checkTag(view, Constants.TAG_LIST)) {
            return false;
        }
        this.y = dragEvent.getY();
        float scroll = getScroll(this.scrollView, view);
        if (this.y - scroll < 100.0f) {
            this.dragDirection = 0;
            startScrolling(view);
        } else if (this.scrollView.getHeight() - (this.y - scroll) < 100.0f) {
            this.dragDirection = 1;
            startScrolling(view);
        } else {
            stopScrolling();
        }
        return true;
    }

    private boolean actionDragExited(View view, View view2) {
        if (checkTag(view, Constants.TAG_LIST)) {
            stopScrolling();
        }
        if (!view.equals(view2.getParent())) {
            return true;
        }
        view2.setVisibility(0);
        return true;
    }

    private boolean actionDragEntered(View view, View view2) {
        if (!targetCanAcceptDrop(view2, view)) {
            return true;
        }
        stopScrolling();
        view2.setVisibility(4);
        moveView(view, view2);
        return true;
    }

    public void moveView(View view, View view2) {
        ViewGroup viewGroup = (ViewGroup) view2.getParent();
        int iIndexOfChild = viewGroup.indexOfChild(view);
        viewGroup.removeView(view2);
        viewGroup.addView(view2, iIndexOfChild);
    }

    private boolean actionDragStarted(DragEvent dragEvent, View view) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup.getLayoutTransition() == null) {
            return true;
        }
        this.containerLayoutTransition = viewGroup.getLayoutTransition();
        viewGroup.setLayoutTransition(null);
        return true;
    }

    private int getScroll(ScrollView scrollView, View view) {
        int scrollY = scrollView.getScrollY() + 0;
        int height = 0;
        while (true) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            int iIndexOfChild = viewGroup.indexOfChild(view);
            for (int i = 0; i < iIndexOfChild; i++) {
                height += viewGroup.getChildAt(i).getHeight();
            }
            if (viewGroup.equals(scrollView)) {
                return scrollY - height;
            }
            view = viewGroup;
        }
    }

    private void startScrolling(View view) {
        if (this.scroll) {
            return;
        }
        Thread thread = new Thread(new Scroller(view));
        this.scrollerThread = thread;
        this.scroll = true;
        thread.start();
    }

    private void stopScrolling() {
        if (this.scroll) {
            this.scroll = false;
            Thread thread = this.scrollerThread;
            if (thread == null || !thread.isAlive()) {
                return;
            }
            this.scrollerThread.interrupt();
        }
    }

    class Scroller implements Runnable {
        View target;

        public Scroller(View view) {
            this.target = view;
        }

        @Override // java.lang.Runnable
        public void run() throws InterruptedException {
            int i = ChecklistViewItemOnDragListener.this.dragDirection == 0 ? -5 : 5;
            while (ChecklistViewItemOnDragListener.this.scroll) {
                ChecklistViewItemOnDragListener.this.scrollView.smoothScrollBy(0, DensityUtil.dpToPx(i, ChecklistViewItemOnDragListener.this.scrollView.getContext()));
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException unused) {
                    Log.d(ChecklistViewItemOnDragListener.TAG, "InterruptedException");
                }
            }
        }
    }

    private View getScrollableAncestor(View view) {
        View view2 = (View) view.getParent();
        while (view2 != null && !view2.getClass().isAssignableFrom(ScrollView.class)) {
            view2 = (View) view2.getParent();
        }
        return view2;
    }

    private boolean targetCanAcceptDrop(View view, View view2) {
        if (checkTag(view2, Constants.TAG_ITEM) && checkTag(view, Constants.TAG_ITEM)) {
            return App.getSettings().getMoveCheckedOnBottom() == 0 || ((CheckListViewItem) view).isChecked() == ((CheckListViewItem) view2).isChecked();
        }
        return false;
    }

    private boolean checkTag(View view, Object obj) {
        return view.getTag() != null && view.getTag().equals(obj);
    }
}
