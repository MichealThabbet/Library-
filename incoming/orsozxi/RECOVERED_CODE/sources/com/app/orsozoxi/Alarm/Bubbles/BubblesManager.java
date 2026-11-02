package com.app.orsozoxi.Alarm.Bubbles;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.app.orsozoxi.Alarm.Bubbles.BubblesService;

/* loaded from: classes.dex */
public class BubblesManager {
    private static BubblesManager INSTANCE;
    private boolean bounded;
    private ServiceConnection bubbleServiceConnection = new ServiceConnection() { // from class: com.app.orsozoxi.Alarm.Bubbles.BubblesManager.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BubblesManager.this.bubblesService = ((BubblesService.BubblesServiceBinder) iBinder).getService();
            BubblesManager.this.configureBubblesService();
            BubblesManager.this.bounded = true;
            if (BubblesManager.this.listener != null) {
                BubblesManager.this.listener.onInitialized();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            BubblesManager.this.bounded = false;
        }
    };
    private BubblesService bubblesService;
    private Context context;
    private OnInitializedCallback listener;
    private int trashLayoutResourceId;

    /* JADX INFO: Access modifiers changed from: private */
    public static BubblesManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new BubblesManager(context);
        }
        return INSTANCE;
    }

    private BubblesManager(Context context) {
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void configureBubblesService() {
        this.bubblesService.addTrash(this.trashLayoutResourceId);
    }

    public void initialize(Activity activity) {
        this.context.bindService(new Intent(activity, (Class<?>) BubblesService.class), this.bubbleServiceConnection, 1);
    }

    public void recycle() {
        this.context.unbindService(this.bubbleServiceConnection);
    }

    public void addBubble(BubbleLayout bubbleLayout, int i, int i2) {
        if (this.bounded) {
            this.bubblesService.addBubble(bubbleLayout, i, i2);
        }
    }

    public void removeBubble(BubbleLayout bubbleLayout) {
        if (this.bounded) {
            this.bubblesService.removeBubble(bubbleLayout);
        }
    }

    public static class Builder {
        private BubblesManager bubblesManager;

        public Builder(Context context) {
            this.bubblesManager = BubblesManager.getInstance(context);
        }

        public Builder setInitializationCallback(OnInitializedCallback onInitializedCallback) {
            this.bubblesManager.listener = onInitializedCallback;
            return this;
        }

        public Builder setTrashLayout(int i) {
            this.bubblesManager.trashLayoutResourceId = i;
            return this;
        }

        public BubblesManager build() {
            return this.bubblesManager;
        }
    }
}
