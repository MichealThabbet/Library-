package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.backends.BackendRegistry;
import com.google.android.datatransport.runtime.backends.BackendRequest;
import com.google.android.datatransport.runtime.backends.BackendResponse;
import com.google.android.datatransport.runtime.backends.TransportBackend;
import com.google.android.datatransport.runtime.logging.Logging;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStore;
import com.google.android.datatransport.runtime.scheduling.persistence.PersistedEvent;
import com.google.android.datatransport.runtime.synchronization.SynchronizationException;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;
import com.google.android.datatransport.runtime.time.Clock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;
import javax.inject.Inject;

/* loaded from: classes.dex */
public class Uploader {
    private static final String LOG_TAG = "Uploader";
    private final BackendRegistry backendRegistry;
    private final Clock clock;
    private final Context context;
    private final EventStore eventStore;
    private final Executor executor;
    private final SynchronizationGuard guard;
    private final WorkScheduler workScheduler;

    @Inject
    public Uploader(Context context, BackendRegistry backendRegistry, EventStore eventStore, WorkScheduler workScheduler, Executor executor, SynchronizationGuard synchronizationGuard, Clock clock) {
        this.context = context;
        this.backendRegistry = backendRegistry;
        this.eventStore = eventStore;
        this.workScheduler = workScheduler;
        this.executor = executor;
        this.guard = synchronizationGuard;
        this.clock = clock;
    }

    boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void upload(final TransportContext transportContext, final int i, final Runnable runnable) {
        this.executor.execute(new Runnable() { // from class: com.google.android.datatransport.runtime.scheduling.jobscheduling.-$$Lambda$Uploader$gD2ng4DJI1wrsPbN_hxDpvworD8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$upload$1$Uploader(transportContext, i, runnable);
            }
        });
    }

    public /* synthetic */ void lambda$upload$1$Uploader(final TransportContext transportContext, final int i, Runnable runnable) {
        try {
            try {
                SynchronizationGuard synchronizationGuard = this.guard;
                final EventStore eventStore = this.eventStore;
                Objects.requireNonNull(eventStore);
                synchronizationGuard.runCriticalSection(new SynchronizationGuard.CriticalSection() { // from class: com.google.android.datatransport.runtime.scheduling.jobscheduling.-$$Lambda$7iIGXG4rziTDaCv7wibWFWjAdgo
                    @Override // com.google.android.datatransport.runtime.synchronization.SynchronizationGuard.CriticalSection
                    public final Object execute() {
                        return Integer.valueOf(eventStore.cleanUp());
                    }
                });
                if (!isNetworkAvailable()) {
                    this.guard.runCriticalSection(new SynchronizationGuard.CriticalSection() { // from class: com.google.android.datatransport.runtime.scheduling.jobscheduling.-$$Lambda$Uploader$-PizdFkrUS80CHQoeatutNhEQNk
                        @Override // com.google.android.datatransport.runtime.synchronization.SynchronizationGuard.CriticalSection
                        public final Object execute() {
                            return this.f$0.lambda$upload$0$Uploader(transportContext, i);
                        }
                    });
                } else {
                    logAndUpdateState(transportContext, i);
                }
            } catch (SynchronizationException unused) {
                this.workScheduler.schedule(transportContext, i + 1);
            }
        } finally {
            runnable.run();
        }
    }

    public /* synthetic */ Object lambda$upload$0$Uploader(TransportContext transportContext, int i) {
        this.workScheduler.schedule(transportContext, i + 1);
        return null;
    }

    void logAndUpdateState(final TransportContext transportContext, final int i) {
        BackendResponse backendResponseSend;
        TransportBackend transportBackend = this.backendRegistry.get(transportContext.getBackendName());
        final Iterable iterable = (Iterable) this.guard.runCriticalSection(new SynchronizationGuard.CriticalSection() { // from class: com.google.android.datatransport.runtime.scheduling.jobscheduling.-$$Lambda$Uploader$YOQT1rGzgM7xDFyc87MybXuGXpM
            @Override // com.google.android.datatransport.runtime.synchronization.SynchronizationGuard.CriticalSection
            public final Object execute() {
                return this.f$0.lambda$logAndUpdateState$2$Uploader(transportContext);
            }
        });
        if (iterable.iterator().hasNext()) {
            if (transportBackend == null) {
                Logging.d(LOG_TAG, "Unknown backend for %s, deleting event batch for it...", transportContext);
                backendResponseSend = BackendResponse.fatalError();
            } else {
                ArrayList arrayList = new ArrayList();
                Iterator it2 = iterable.iterator();
                while (it2.hasNext()) {
                    arrayList.add(((PersistedEvent) it2.next()).getEvent());
                }
                backendResponseSend = transportBackend.send(BackendRequest.builder().setEvents(arrayList).setExtras(transportContext.getExtras()).build());
            }
            final BackendResponse backendResponse = backendResponseSend;
            this.guard.runCriticalSection(new SynchronizationGuard.CriticalSection() { // from class: com.google.android.datatransport.runtime.scheduling.jobscheduling.-$$Lambda$Uploader$uhccXyrB8dzthtbSkZDlHOlHlU8
                @Override // com.google.android.datatransport.runtime.synchronization.SynchronizationGuard.CriticalSection
                public final Object execute() {
                    return this.f$0.lambda$logAndUpdateState$3$Uploader(backendResponse, iterable, transportContext, i);
                }
            });
        }
    }

    public /* synthetic */ Iterable lambda$logAndUpdateState$2$Uploader(TransportContext transportContext) {
        return this.eventStore.loadBatch(transportContext);
    }

    public /* synthetic */ Object lambda$logAndUpdateState$3$Uploader(BackendResponse backendResponse, Iterable iterable, TransportContext transportContext, int i) {
        if (backendResponse.getStatus() == BackendResponse.Status.TRANSIENT_ERROR) {
            this.eventStore.recordFailure(iterable);
            this.workScheduler.schedule(transportContext, i + 1);
            return null;
        }
        this.eventStore.recordSuccess(iterable);
        if (backendResponse.getStatus() == BackendResponse.Status.OK) {
            this.eventStore.recordNextCallTime(transportContext, this.clock.getTime() + backendResponse.getNextRequestWaitMillis());
        }
        if (!this.eventStore.hasPendingEventsFor(transportContext)) {
            return null;
        }
        this.workScheduler.schedule(transportContext, 1, true);
        return null;
    }
}
