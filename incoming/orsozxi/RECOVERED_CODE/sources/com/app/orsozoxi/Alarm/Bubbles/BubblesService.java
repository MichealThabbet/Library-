package com.app.orsozoxi.Alarm.Bubbles;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.google.android.material.badge.BadgeDrawable;
import it.orsozoxi.android.orsonotes.R2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class BubblesService extends Service {
    private BubblesServiceBinder binder = new BubblesServiceBinder();
    private List<BubbleLayout> bubbles = new ArrayList();
    private BubbleTrashLayout bubblesTrash;
    private BubblesLayoutCoordinator layoutCoordinator;
    private WindowManager windowManager;

    private void initializeLayoutCoordinator() {
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Iterator<BubbleLayout> it2 = this.bubbles.iterator();
        while (it2.hasNext()) {
            recycleBubble(it2.next());
        }
        this.bubbles.clear();
        return super.onUnbind(intent);
    }

    private void recycleBubble(final BubbleLayout bubbleLayout) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.app.orsozoxi.Alarm.Bubbles.BubblesService.1
            @Override // java.lang.Runnable
            public void run() {
                BubblesService.this.getWindowManager().removeView(bubbleLayout);
                for (BubbleLayout bubbleLayout2 : BubblesService.this.bubbles) {
                    BubbleLayout bubbleLayout3 = bubbleLayout;
                    if (bubbleLayout2 == bubbleLayout3) {
                        bubbleLayout3.notifyBubbleRemoved();
                        BubblesService.this.bubbles.remove(bubbleLayout2);
                        return;
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public WindowManager getWindowManager() {
        if (this.windowManager == null) {
            this.windowManager = (WindowManager) getSystemService("window");
        }
        return this.windowManager;
    }

    public void addBubble(BubbleLayout bubbleLayout, int i, int i2) {
        WindowManager.LayoutParams layoutParamsBuildLayoutParamsForBubble = buildLayoutParamsForBubble(i, i2);
        bubbleLayout.setWindowManager(getWindowManager());
        bubbleLayout.setViewParams(layoutParamsBuildLayoutParamsForBubble);
        bubbleLayout.setLayoutCoordinator(this.layoutCoordinator);
        this.bubbles.add(bubbleLayout);
        addViewToWindow(bubbleLayout);
    }

    void addTrash(int i) {
        if (i != 0) {
            BubbleTrashLayout bubbleTrashLayout = new BubbleTrashLayout(this);
            this.bubblesTrash = bubbleTrashLayout;
            bubbleTrashLayout.setWindowManager(this.windowManager);
            this.bubblesTrash.setViewParams(buildLayoutParamsForTrash());
            this.bubblesTrash.setVisibility(8);
            LayoutInflater.from(this).inflate(i, (ViewGroup) this.bubblesTrash, true);
            addViewToWindow(this.bubblesTrash);
            initializeLayoutCoordinator();
        }
    }

    private void addViewToWindow(final BubbleBaseLayout bubbleBaseLayout) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.app.orsozoxi.Alarm.Bubbles.BubblesService.2
            @Override // java.lang.Runnable
            public void run() {
                WindowManager windowManager = BubblesService.this.getWindowManager();
                BubbleBaseLayout bubbleBaseLayout2 = bubbleBaseLayout;
                windowManager.addView(bubbleBaseLayout2, bubbleBaseLayout2.getViewParams());
            }
        });
    }

    private WindowManager.LayoutParams buildLayoutParamsForBubble(int i, int i2) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, R2.dimen.notification_media_narrow_margin, 8, -2);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        layoutParams.x = i;
        layoutParams.y = i2;
        return layoutParams;
    }

    private WindowManager.LayoutParams buildLayoutParamsForTrash() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, R2.dimen.notification_small_icon_size_as_large, 8, -2);
        layoutParams.x = 0;
        layoutParams.y = 0;
        return layoutParams;
    }

    public void removeBubble(BubbleLayout bubbleLayout) {
        recycleBubble(bubbleLayout);
    }

    public class BubblesServiceBinder extends Binder {
        public BubblesServiceBinder() {
        }

        public BubblesService getService() {
            return BubblesService.this;
        }
    }
}
