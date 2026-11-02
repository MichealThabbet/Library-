package com.app.orsozoxi.Alarm.Bubbles;

import android.app.Service;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import com.app.orsozoxi.Alarm.Services.FlyBitch;
import com.app.orsozoxi.Alarm.Services.FlyBitchJobService;
import com.app.orsozoxi.R;

/* loaded from: classes.dex */
public final class BubblesLayoutCoordinator {
    private static BubblesLayoutCoordinator INSTANCE;
    private Service bubblesService;
    private BubbleTrashLayout trashView;
    private WindowManager windowManager;

    /* JADX INFO: Access modifiers changed from: private */
    public static BubblesLayoutCoordinator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BubblesLayoutCoordinator();
        }
        return INSTANCE;
    }

    private BubblesLayoutCoordinator() {
    }

    public void notifyBubblePositionChanged(BubbleLayout bubbleLayout, int i, int i2) {
        BubbleTrashLayout bubbleTrashLayout = this.trashView;
        if (bubbleTrashLayout != null) {
            bubbleTrashLayout.setVisibility(0);
            if (checkIfBubbleIsOverTrash(bubbleLayout)) {
                this.trashView.applyMagnetism();
                applyTrashMagnetismToBubble(bubbleLayout);
            } else {
                this.trashView.releaseMagnetism();
            }
        }
    }

    private void applyTrashMagnetismToBubble(BubbleLayout bubbleLayout) {
        View trashContent = getTrashContent();
        int left = trashContent.getLeft() + (trashContent.getMeasuredWidth() / 2);
        int top = trashContent.getTop() + (trashContent.getMeasuredHeight() / 2);
        int measuredWidth = left - (bubbleLayout.getMeasuredWidth() / 2);
        int measuredHeight = top - (bubbleLayout.getMeasuredHeight() / 2);
        bubbleLayout.getViewParams().x = measuredWidth;
        bubbleLayout.getViewParams().y = measuredHeight;
        this.windowManager.updateViewLayout(bubbleLayout, bubbleLayout.getViewParams());
    }

    private boolean checkIfBubbleIsOverTrash(BubbleLayout bubbleLayout) {
        if (this.trashView.getVisibility() == 0) {
            View trashContent = getTrashContent();
            int measuredWidth = trashContent.getMeasuredWidth();
            int measuredHeight = trashContent.getMeasuredHeight();
            int i = measuredWidth / 2;
            int left = trashContent.getLeft() - i;
            int left2 = trashContent.getLeft() + measuredWidth + i;
            int i2 = measuredHeight / 2;
            int top = trashContent.getTop() - i2;
            int top2 = trashContent.getTop() + measuredHeight + i2;
            int measuredWidth2 = bubbleLayout.getMeasuredWidth();
            int measuredHeight2 = bubbleLayout.getMeasuredHeight();
            int i3 = bubbleLayout.getViewParams().x;
            int i4 = measuredWidth2 + i3;
            int i5 = bubbleLayout.getViewParams().y;
            int i6 = measuredHeight2 + i5;
            if (i3 >= left && i4 <= left2 && i5 >= top && i6 <= top2) {
                return true;
            }
        }
        return false;
    }

    public void notifyBubbleRelease(BubbleLayout bubbleLayout) {
        if (this.trashView != null) {
            if (checkIfBubbleIsOverTrash(bubbleLayout)) {
                Service service = this.bubblesService;
                if (service instanceof FlyBitch) {
                    ((FlyBitch) service).removeBubble(bubbleLayout);
                } else {
                    ((FlyBitchJobService) service).removeBubble(bubbleLayout);
                }
            }
            this.trashView.setVisibility(8);
        }
    }

    public void HideScroll(BubbleLayout bubbleLayout) {
        ((ScrollView) bubbleLayout.findViewById(R.id.scrollView1)).setVisibility(8);
    }

    public static class Builder {
        private BubblesLayoutCoordinator layoutCoordinator;

        public Builder(FlyBitch flyBitch) {
            BubblesLayoutCoordinator bubblesLayoutCoordinator = BubblesLayoutCoordinator.getInstance();
            this.layoutCoordinator = bubblesLayoutCoordinator;
            bubblesLayoutCoordinator.bubblesService = flyBitch;
        }

        public Builder(FlyBitchJobService flyBitchJobService) {
            BubblesLayoutCoordinator bubblesLayoutCoordinator = BubblesLayoutCoordinator.getInstance();
            this.layoutCoordinator = bubblesLayoutCoordinator;
            bubblesLayoutCoordinator.bubblesService = flyBitchJobService;
        }

        public Builder setTrashView(BubbleTrashLayout bubbleTrashLayout) {
            this.layoutCoordinator.trashView = bubbleTrashLayout;
            return this;
        }

        public Builder setWindowManager(WindowManager windowManager) {
            this.layoutCoordinator.windowManager = windowManager;
            return this;
        }

        public BubblesLayoutCoordinator build() {
            return this.layoutCoordinator;
        }
    }

    private View getTrashContent() {
        return this.trashView.getChildAt(0);
    }
}
