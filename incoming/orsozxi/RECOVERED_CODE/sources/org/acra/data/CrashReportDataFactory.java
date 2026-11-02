package org.acra.data;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.acra.ACRA;
import org.acra.builder.ReportBuilder;
import org.acra.collector.ApplicationStartupCollector;
import org.acra.collector.Collector;
import org.acra.collector.CollectorException;
import org.acra.config.CoreConfiguration;

/* loaded from: classes3.dex */
public final class CrashReportDataFactory {
    private final List<Collector> collectors;
    private final CoreConfiguration config;
    private final Context context;

    public CrashReportDataFactory(Context context, CoreConfiguration coreConfiguration) {
        this.context = context;
        this.config = coreConfiguration;
        List<Collector> listLoadEnabled = coreConfiguration.pluginLoader().loadEnabled(coreConfiguration, Collector.class);
        this.collectors = listLoadEnabled;
        Collections.sort(listLoadEnabled, new Comparator() { // from class: org.acra.data.-$$Lambda$CrashReportDataFactory$w4-ZRNFc7G4q3tBC1_4dG1ejRHE
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return CrashReportDataFactory.lambda$new$0((Collector) obj, (Collector) obj2);
            }
        });
    }

    static /* synthetic */ int lambda$new$0(Collector collector, Collector collector2) {
        Collector.Order order;
        Collector.Order order2;
        try {
            order = collector.getOrder();
        } catch (Exception unused) {
            order = Collector.Order.NORMAL;
        }
        try {
            order2 = collector2.getOrder();
        } catch (Exception unused2) {
            order2 = Collector.Order.NORMAL;
        }
        return order.ordinal() - order2.ordinal();
    }

    public CrashReportData createCrashData(final ReportBuilder reportBuilder) throws ExecutionException, InterruptedException {
        ExecutorService executorServiceNewCachedThreadPool = this.config.parallel() ? Executors.newCachedThreadPool() : Executors.newSingleThreadExecutor();
        final CrashReportData crashReportData = new CrashReportData();
        ArrayList<Future> arrayList = new ArrayList();
        for (final Collector collector : this.collectors) {
            arrayList.add(executorServiceNewCachedThreadPool.submit(new Runnable() { // from class: org.acra.data.-$$Lambda$CrashReportDataFactory$JhNXduZFyp6NfMtDKKl8MZ9kTPI
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createCrashData$1$CrashReportDataFactory(collector, reportBuilder, crashReportData);
                }
            }));
        }
        for (Future future : arrayList) {
            while (!future.isDone()) {
                try {
                    future.get();
                } catch (InterruptedException unused) {
                } catch (ExecutionException unused2) {
                }
            }
        }
        return crashReportData;
    }

    public /* synthetic */ void lambda$createCrashData$1$CrashReportDataFactory(Collector collector, ReportBuilder reportBuilder, CrashReportData crashReportData) {
        try {
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Calling collector " + collector.getClass().getName());
            }
            collector.collect(this.context, this.config, reportBuilder, crashReportData);
            if (ACRA.DEV_LOGGING) {
                ACRA.log.d(ACRA.LOG_TAG, "Collector " + collector.getClass().getName() + " completed");
            }
        } catch (CollectorException e) {
            ACRA.log.w(ACRA.LOG_TAG, e);
        } catch (Exception e2) {
            ACRA.log.e(ACRA.LOG_TAG, "Error in collector " + collector.getClass().getSimpleName(), e2);
        }
    }

    public void collectStartUp() {
        for (Collector collector : this.collectors) {
            if (collector instanceof ApplicationStartupCollector) {
                try {
                    ((ApplicationStartupCollector) collector).collectApplicationStartUp(this.context, this.config);
                } catch (Exception e) {
                    ACRA.log.w(ACRA.LOG_TAG, collector.getClass().getSimpleName() + " failed to collect its startup data", e);
                }
            }
        }
    }
}
