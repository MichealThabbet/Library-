package it.feio.android.checklistview.dragging;

import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import it.feio.android.checklistview.App;

/* loaded from: classes2.dex */
public class ChecklistViewOnTouchListener implements View.OnTouchListener {
    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0) {
            return false;
        }
        actionDown(view);
        return true;
    }

    private void actionDown(View view) {
        View view2 = (View) view.getParent();
        view2.startDrag(null, new ChecklistViewDragShadowBuilder(view2), view2, 0);
        if (App.getSettings().getDragVibrationEnabled()) {
            ((Vibrator) view.getContext().getSystemService("vibrator")).vibrate(App.getSettings().getDragVibrationDuration());
        }
    }
}
