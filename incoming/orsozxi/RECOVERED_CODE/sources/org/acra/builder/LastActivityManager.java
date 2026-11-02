package org.acra.builder;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import org.acra.ACRA;
import org.acra.collections.WeakStack;

/* loaded from: classes3.dex */
public final class LastActivityManager {
    private final WeakStack<Activity> activityStack = new WeakStack<>();

    public LastActivityManager(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: org.acra.builder.LastActivityManager.1
            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
                if (ACRA.DEV_LOGGING) {
                    ACRA.log.d(ACRA.LOG_TAG, "onActivityCreated " + activity.getClass());
                }
                LastActivityManager.this.activityStack.add(activity);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
                if (ACRA.DEV_LOGGING) {
                    ACRA.log.d(ACRA.LOG_TAG, "onActivityStarted " + activity.getClass());
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(Activity activity) {
                if (ACRA.DEV_LOGGING) {
                    ACRA.log.d(ACRA.LOG_TAG, "onActivityResumed " + activity.getClass());
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(Activity activity) {
                if (ACRA.DEV_LOGGING) {
                    ACRA.log.d(ACRA.LOG_TAG, "onActivityPaused " + activity.getClass());
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
                if (ACRA.DEV_LOGGING) {
                    ACRA.log.d(ACRA.LOG_TAG, "onActivityStopped " + activity.getClass());
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                if (ACRA.DEV_LOGGING) {
                    ACRA.log.d(ACRA.LOG_TAG, "onActivitySaveInstanceState " + activity.getClass());
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
                if (ACRA.DEV_LOGGING) {
                    ACRA.log.d(ACRA.LOG_TAG, "onActivityDestroyed " + activity.getClass());
                }
                synchronized (LastActivityManager.this.activityStack) {
                    LastActivityManager.this.activityStack.remove(activity);
                    LastActivityManager.this.activityStack.notify();
                }
            }
        });
    }

    public Activity getLastActivity() {
        return this.activityStack.peek();
    }

    public List<Activity> getLastActivities() {
        return new ArrayList(this.activityStack);
    }

    public void clearLastActivities() {
        this.activityStack.clear();
    }

    public void waitForAllActivitiesDestroy(int i) {
        synchronized (this.activityStack) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jCurrentTimeMillis2 = jCurrentTimeMillis;
            while (!this.activityStack.isEmpty()) {
                long j = i;
                if (jCurrentTimeMillis + j <= jCurrentTimeMillis2) {
                    break;
                }
                try {
                    this.activityStack.wait((jCurrentTimeMillis - jCurrentTimeMillis2) + j);
                } catch (InterruptedException unused) {
                }
                jCurrentTimeMillis2 = System.currentTimeMillis();
            }
        }
    }
}
