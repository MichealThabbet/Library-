package com.ibm.icu.impl;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class ICUNotifier {

    /* renamed from: listeners, reason: collision with root package name */
    private List<EventListener> f13listeners;
    private final Object notifyLock = new Object();
    private NotifyThread notifyThread;

    protected abstract boolean acceptsListener(EventListener eventListener);

    protected abstract void notifyListener(EventListener eventListener);

    public void addListener(EventListener eventListener) {
        if (eventListener == null) {
            throw null;
        }
        if (acceptsListener(eventListener)) {
            synchronized (this.notifyLock) {
                if (this.f13listeners == null) {
                    this.f13listeners = new ArrayList();
                } else {
                    Iterator<EventListener> it2 = this.f13listeners.iterator();
                    while (it2.hasNext()) {
                        if (it2.next() == eventListener) {
                            return;
                        }
                    }
                }
                this.f13listeners.add(eventListener);
                return;
            }
        }
        throw new IllegalStateException("Listener invalid for this notifier.");
    }

    public void removeListener(EventListener eventListener) {
        if (eventListener == null) {
            throw null;
        }
        synchronized (this.notifyLock) {
            if (this.f13listeners != null) {
                Iterator<EventListener> it2 = this.f13listeners.iterator();
                while (it2.hasNext()) {
                    if (it2.next() == eventListener) {
                        it2.remove();
                        if (this.f13listeners.size() == 0) {
                            this.f13listeners = null;
                        }
                        return;
                    }
                }
            }
        }
    }

    public void notifyChanged() {
        synchronized (this.notifyLock) {
            if (this.f13listeners != null) {
                if (this.notifyThread == null) {
                    NotifyThread notifyThread = new NotifyThread(this);
                    this.notifyThread = notifyThread;
                    notifyThread.setDaemon(true);
                    this.notifyThread.start();
                }
                this.notifyThread.queue((EventListener[]) this.f13listeners.toArray(new EventListener[this.f13listeners.size()]));
            }
        }
    }

    private static class NotifyThread extends Thread {
        private final ICUNotifier notifier;
        private final List<EventListener[]> queue = new ArrayList();

        NotifyThread(ICUNotifier iCUNotifier) {
            this.notifier = iCUNotifier;
        }

        public void queue(EventListener[] eventListenerArr) {
            synchronized (this) {
                this.queue.add(eventListenerArr);
                notify();
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            int i;
            EventListener[] eventListenerArrRemove;
            while (true) {
                try {
                    synchronized (this) {
                        while (this.queue.isEmpty()) {
                            wait();
                        }
                        eventListenerArrRemove = this.queue.remove(0);
                    }
                    for (EventListener eventListener : eventListenerArrRemove) {
                        this.notifier.notifyListener(eventListener);
                    }
                } catch (InterruptedException unused) {
                    continue;
                }
            }
        }
    }
}
