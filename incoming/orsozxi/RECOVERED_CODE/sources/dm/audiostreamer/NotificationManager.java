package dm.audiostreamer;

import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class NotificationManager {
    public static boolean DEBUG_VERSION = false;
    private static volatile NotificationManager Instance = null;
    public static final int albumsDidLoaded;
    public static final int audioDidReset;
    public static final int audioDidSent;
    public static final int audioDidStarted;
    public static final int audioPlayStateChanged;
    public static final int audioProgressDidChanged;
    public static final int audioRouteChanged;
    public static final int didReceivedNewMessages = 1;
    public static final int newaudioloaded;
    public static final int screenshotTook;
    public static final int setAnyPendingIntent;
    private static int totalEvents;
    public static final int updateInterfaces;
    private boolean animationInProgress;
    private SparseArray<ArrayList<Object>> observers = new SparseArray<>();
    private SparseArray<ArrayList<Object>> removeAfterBroadcast = new SparseArray<>();
    private SparseArray<ArrayList<Object>> addAfterBroadcast = new SparseArray<>();
    private ArrayList<DelayedPost> delayedPosts = new ArrayList<>(10);
    private int broadcasting = 0;

    public interface NotificationCenterDelegate {
        void didReceivedNotification(int i, Object... objArr);

        void newSongLoaded(Object... objArr);
    }

    static {
        int i = 1 + 1;
        totalEvents = i;
        int i2 = i + 1;
        totalEvents = i2;
        updateInterfaces = i;
        int i3 = i2 + 1;
        totalEvents = i3;
        audioProgressDidChanged = i2;
        int i4 = i3 + 1;
        totalEvents = i4;
        audioDidReset = i3;
        int i5 = i4 + 1;
        totalEvents = i5;
        audioPlayStateChanged = i4;
        int i6 = i5 + 1;
        totalEvents = i6;
        screenshotTook = i5;
        int i7 = i6 + 1;
        totalEvents = i7;
        albumsDidLoaded = i6;
        int i8 = i7 + 1;
        totalEvents = i8;
        audioDidSent = i7;
        int i9 = i8 + 1;
        totalEvents = i9;
        audioDidStarted = i8;
        int i10 = i9 + 1;
        totalEvents = i10;
        audioRouteChanged = i9;
        int i11 = i10 + 1;
        totalEvents = i11;
        newaudioloaded = i10;
        totalEvents = i11 + 1;
        setAnyPendingIntent = i11;
    }

    private class DelayedPost {
        private Object[] args;
        private int id;

        private DelayedPost(int i, Object[] objArr) {
            this.id = i;
            this.args = objArr;
        }
    }

    public static NotificationManager getInstance() {
        NotificationManager notificationManager = Instance;
        if (notificationManager == null) {
            synchronized (NotificationManager.class) {
                notificationManager = Instance;
                if (notificationManager == null) {
                    notificationManager = new NotificationManager();
                    Instance = notificationManager;
                }
            }
        }
        return notificationManager;
    }

    public void setAnimationInProgress(boolean z) {
        this.animationInProgress = z;
        if (z || this.delayedPosts.isEmpty()) {
            return;
        }
        Iterator<DelayedPost> it2 = this.delayedPosts.iterator();
        while (it2.hasNext()) {
            DelayedPost next = it2.next();
            postNotificationNameInternal(next.id, true, next.args);
        }
        this.delayedPosts.clear();
    }

    public void postNotificationName(int i, Object... objArr) {
        postNotificationNameInternal(i, false, objArr);
    }

    public void postNotificationNameInternal(int i, boolean z, Object... objArr) {
        if (DEBUG_VERSION && Thread.currentThread() != AudioStreamingManager.applicationHandler.getLooper().getThread()) {
            throw new RuntimeException("postNotificationName allowed only from MAIN thread");
        }
        if (!z && this.animationInProgress) {
            this.delayedPosts.add(new DelayedPost(i, objArr));
            if (DEBUG_VERSION) {
                Log.e("tmessages", "delay post notification " + i + " with args count = " + objArr.length);
                return;
            }
            return;
        }
        this.broadcasting++;
        ArrayList<Object> arrayList = this.observers.get(i);
        if (arrayList != null && !arrayList.isEmpty()) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                ((NotificationCenterDelegate) arrayList.get(i2)).didReceivedNotification(i, objArr);
            }
        }
        int i3 = this.broadcasting - 1;
        this.broadcasting = i3;
        if (i3 == 0) {
            if (this.removeAfterBroadcast.size() != 0) {
                for (int i4 = 0; i4 < this.removeAfterBroadcast.size(); i4++) {
                    int iKeyAt = this.removeAfterBroadcast.keyAt(i4);
                    ArrayList<Object> arrayList2 = this.removeAfterBroadcast.get(iKeyAt);
                    for (int i5 = 0; i5 < arrayList2.size(); i5++) {
                        removeObserver(arrayList2.get(i5), iKeyAt);
                    }
                }
                this.removeAfterBroadcast.clear();
            }
            if (this.addAfterBroadcast.size() != 0) {
                for (int i6 = 0; i6 < this.addAfterBroadcast.size(); i6++) {
                    int iKeyAt2 = this.addAfterBroadcast.keyAt(i6);
                    ArrayList<Object> arrayList3 = this.addAfterBroadcast.get(iKeyAt2);
                    for (int i7 = 0; i7 < arrayList3.size(); i7++) {
                        addObserver(arrayList3.get(i7), iKeyAt2);
                    }
                }
                this.addAfterBroadcast.clear();
            }
        }
    }

    public void addObserver(Object obj, int i) {
        if (DEBUG_VERSION && Thread.currentThread() != AudioStreamingManager.applicationHandler.getLooper().getThread()) {
            throw new RuntimeException("addObserver allowed only from MAIN thread");
        }
        if (this.broadcasting != 0) {
            ArrayList<Object> arrayList = this.addAfterBroadcast.get(i);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.addAfterBroadcast.put(i, arrayList);
            }
            arrayList.add(obj);
            return;
        }
        ArrayList<Object> arrayList2 = this.observers.get(i);
        if (arrayList2 == null) {
            SparseArray<ArrayList<Object>> sparseArray = this.observers;
            ArrayList<Object> arrayList3 = new ArrayList<>();
            sparseArray.put(i, arrayList3);
            arrayList2 = arrayList3;
        }
        if (arrayList2.contains(obj)) {
            return;
        }
        arrayList2.add(obj);
    }

    public void removeObserver(Object obj, int i) {
        if (DEBUG_VERSION && Thread.currentThread() != AudioStreamingManager.applicationHandler.getLooper().getThread()) {
            throw new RuntimeException("removeObserver allowed only from MAIN thread");
        }
        if (this.broadcasting != 0) {
            ArrayList<Object> arrayList = this.removeAfterBroadcast.get(i);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.removeAfterBroadcast.put(i, arrayList);
            }
            arrayList.add(obj);
            return;
        }
        ArrayList<Object> arrayList2 = this.observers.get(i);
        if (arrayList2 != null) {
            arrayList2.remove(obj);
        }
    }

    public void notifyNewSongLoaded(int i, Object... objArr) {
        ArrayList<Object> arrayList = this.observers.get(i);
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ((NotificationCenterDelegate) arrayList.get(i2)).newSongLoaded(objArr);
        }
    }
}
