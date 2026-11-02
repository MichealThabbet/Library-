package org.acra.interaction;

import android.content.Context;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.acra.ACRA;
import org.acra.config.CoreConfiguration;

/* loaded from: classes3.dex */
public class ReportInteractionExecutor {
    private final CoreConfiguration config;
    private final Context context;
    private final List<ReportInteraction> reportInteractions;

    public ReportInteractionExecutor(Context context, CoreConfiguration coreConfiguration) {
        this.context = context;
        this.config = coreConfiguration;
        this.reportInteractions = coreConfiguration.pluginLoader().loadEnabled(coreConfiguration, ReportInteraction.class);
    }

    public boolean hasInteractions() {
        return this.reportInteractions.size() > 0;
    }

    public boolean performInteractions(final File file) {
        ExecutorService executorServiceNewCachedThreadPool = Executors.newCachedThreadPool();
        ArrayList<Future> arrayList = new ArrayList();
        for (final ReportInteraction reportInteraction : this.reportInteractions) {
            arrayList.add(executorServiceNewCachedThreadPool.submit(new Callable() { // from class: org.acra.interaction.-$$Lambda$ReportInteractionExecutor$5aQVTLpN6nZG0Wzj2N7M6eINB64
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return this.f$0.lambda$performInteractions$0$ReportInteractionExecutor(reportInteraction, file);
                }
            }));
        }
        boolean zBooleanValue = true;
        for (Future future : arrayList) {
            do {
                try {
                    zBooleanValue &= ((Boolean) future.get()).booleanValue();
                } catch (InterruptedException unused) {
                } catch (ExecutionException unused2) {
                }
            } while (!future.isDone());
        }
        return zBooleanValue;
    }

    public /* synthetic */ Boolean lambda$performInteractions$0$ReportInteractionExecutor(ReportInteraction reportInteraction, File file) throws Exception {
        if (ACRA.DEV_LOGGING) {
            ACRA.log.d(ACRA.LOG_TAG, "Calling ReportInteraction of class " + reportInteraction.getClass().getName());
        }
        return Boolean.valueOf(reportInteraction.performInteraction(this.context, this.config, file));
    }
}
