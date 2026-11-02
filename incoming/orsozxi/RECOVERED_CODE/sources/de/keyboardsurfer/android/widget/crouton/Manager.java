package de.keyboardsurfer.android.widget.crouton;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import androidx.mediarouter.media.SystemMediaRouteProvider;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes2.dex */
final class Manager extends Handler {
    private static Manager INSTANCE;
    private final Queue<Crouton> croutonQueue = new LinkedBlockingQueue();

    private static final class Messages {
        public static final int ADD_CROUTON_TO_VIEW = -1040157475;
        public static final int DISPLAY_CROUTON = 794631;
        public static final int REMOVE_CROUTON = -1040155167;

        private Messages() {
        }
    }

    private Manager() {
    }

    static synchronized Manager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Manager();
        }
        return INSTANCE;
    }

    void add(Crouton crouton) {
        this.croutonQueue.add(crouton);
        displayCrouton();
    }

    private void displayCrouton() {
        if (this.croutonQueue.isEmpty()) {
            return;
        }
        Crouton croutonPeek = this.croutonQueue.peek();
        if (croutonPeek.getActivity() == null) {
            this.croutonQueue.poll();
        }
        if (!croutonPeek.isShowing()) {
            sendMessage(croutonPeek, Messages.ADD_CROUTON_TO_VIEW);
            if (croutonPeek.getLifecycleCallback() != null) {
                croutonPeek.getLifecycleCallback().onDisplayed();
                return;
            }
            return;
        }
        sendMessageDelayed(croutonPeek, Messages.DISPLAY_CROUTON, calculateCroutonDuration(croutonPeek));
    }

    private long calculateCroutonDuration(Crouton crouton) {
        return crouton.getConfiguration().durationInMilliseconds + crouton.getInAnimation().getDuration() + crouton.getOutAnimation().getDuration();
    }

    private void sendMessage(Crouton crouton, int i) {
        Message messageObtainMessage = obtainMessage(i);
        messageObtainMessage.obj = crouton;
        sendMessage(messageObtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessageDelayed(Crouton crouton, int i, long j) {
        Message messageObtainMessage = obtainMessage(i);
        messageObtainMessage.obj = crouton;
        sendMessageDelayed(messageObtainMessage, j);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        Crouton crouton = (Crouton) message.obj;
        if (crouton == null) {
            return;
        }
        int i = message.what;
        if (i == -1040157475) {
            addCroutonToView(crouton);
            return;
        }
        if (i != -1040155167) {
            if (i == 794631) {
                displayCrouton();
                return;
            } else {
                super.handleMessage(message);
                return;
            }
        }
        removeCrouton(crouton);
        if (crouton.getLifecycleCallback() != null) {
            crouton.getLifecycleCallback().onRemoved();
        }
    }

    private void addCroutonToView(final Crouton crouton) {
        if (crouton.isShowing()) {
            return;
        }
        final View view = crouton.getView();
        if (view.getParent() == null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.MarginLayoutParams(-1, -2);
            }
            if (crouton.getViewGroup() != null) {
                if (crouton.getViewGroup() instanceof FrameLayout) {
                    crouton.getViewGroup().addView(view, layoutParams);
                } else {
                    crouton.getViewGroup().addView(view, 0, layoutParams);
                }
            } else {
                Activity activity = crouton.getActivity();
                if (activity == null || activity.isFinishing()) {
                    return;
                }
                handleTranslucentActionBar((ViewGroup.MarginLayoutParams) layoutParams, activity);
                activity.addContentView(view, layoutParams);
            }
        }
        view.requestLayout();
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: de.keyboardsurfer.android.widget.crouton.Manager.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < 16) {
                        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    view.startAnimation(crouton.getInAnimation());
                    Manager.announceForAccessibilityCompat(crouton.getActivity(), crouton.getText());
                    if (-1 != crouton.getConfiguration().durationInMilliseconds) {
                        Manager.this.sendMessageDelayed(crouton, Messages.REMOVE_CROUTON, r1.getConfiguration().durationInMilliseconds + crouton.getInAnimation().getDuration());
                    }
                }
            });
        }
    }

    private void handleTranslucentActionBar(ViewGroup.MarginLayoutParams marginLayoutParams, Activity activity) {
        View viewFindViewById;
        if (Build.VERSION.SDK_INT < 19 || (activity.getWindow().getAttributes().flags & 67108864) != 67108864 || (viewFindViewById = activity.findViewById(Resources.getSystem().getIdentifier("action_bar_container", "id", SystemMediaRouteProvider.PACKAGE_NAME))) == null) {
            return;
        }
        marginLayoutParams.topMargin = viewFindViewById.getBottom();
    }

    protected void removeCrouton(Crouton crouton) {
        View view = crouton.getView();
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            view.startAnimation(crouton.getOutAnimation());
            Crouton croutonPoll = this.croutonQueue.poll();
            viewGroup.removeView(view);
            if (croutonPoll != null) {
                croutonPoll.detachActivity();
                croutonPoll.detachViewGroup();
                if (croutonPoll.getLifecycleCallback() != null) {
                    croutonPoll.getLifecycleCallback().onRemoved();
                }
                croutonPoll.detachLifecycleCallback();
            }
            sendMessageDelayed(crouton, Messages.DISPLAY_CROUTON, crouton.getOutAnimation().getDuration());
        }
    }

    void removeCroutonImmediately(Crouton crouton) {
        if (crouton.getActivity() != null && crouton.getView() != null && crouton.getView().getParent() != null) {
            ((ViewGroup) crouton.getView().getParent()).removeView(crouton.getView());
            removeAllMessagesForCrouton(crouton);
        }
        Iterator<Crouton> it2 = this.croutonQueue.iterator();
        while (it2.hasNext()) {
            Crouton next = it2.next();
            if (next.equals(crouton) && next.getActivity() != null) {
                removeCroutonFromViewParent(crouton);
                removeAllMessagesForCrouton(next);
                it2.remove();
                return;
            }
        }
    }

    void clearCroutonQueue() {
        removeAllMessages();
        Iterator<Crouton> it2 = this.croutonQueue.iterator();
        while (it2.hasNext()) {
            removeCroutonFromViewParent(it2.next());
        }
        this.croutonQueue.clear();
    }

    void clearCroutonsForActivity(Activity activity) {
        Iterator<Crouton> it2 = this.croutonQueue.iterator();
        while (it2.hasNext()) {
            Crouton next = it2.next();
            if (next.getActivity() != null && next.getActivity().equals(activity)) {
                removeCroutonFromViewParent(next);
                removeAllMessagesForCrouton(next);
                it2.remove();
            }
        }
    }

    private void removeCroutonFromViewParent(Crouton crouton) {
        ViewGroup viewGroup;
        if (!crouton.isShowing() || (viewGroup = (ViewGroup) crouton.getView().getParent()) == null) {
            return;
        }
        viewGroup.removeView(crouton.getView());
    }

    private void removeAllMessages() {
        removeMessages(Messages.ADD_CROUTON_TO_VIEW);
        removeMessages(Messages.DISPLAY_CROUTON);
        removeMessages(Messages.REMOVE_CROUTON);
    }

    private void removeAllMessagesForCrouton(Crouton crouton) {
        removeMessages(Messages.ADD_CROUTON_TO_VIEW, crouton);
        removeMessages(Messages.DISPLAY_CROUTON, crouton);
        removeMessages(Messages.REMOVE_CROUTON, crouton);
    }

    public static void announceForAccessibilityCompat(Context context, CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 4) {
            AccessibilityManager accessibilityManager = context != null ? (AccessibilityManager) context.getSystemService("accessibility") : null;
            if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
                return;
            }
            AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(Build.VERSION.SDK_INT < 16 ? 8 : 16384);
            accessibilityEventObtain.getText().add(charSequence);
            accessibilityEventObtain.setClassName(Manager.class.getName());
            accessibilityEventObtain.setPackageName(context.getPackageName());
            accessibilityManager.sendAccessibilityEvent(accessibilityEventObtain);
        }
    }

    @Override // android.os.Handler
    public String toString() {
        return "Manager{croutonQueue=" + this.croutonQueue + '}';
    }
}
