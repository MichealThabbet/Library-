package rx.schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import rx.internal.schedulers.NewThreadWorker;
import rx.internal.util.RxThreadFactory;

/* loaded from: classes3.dex */
final class GenericScheduledExecutorService {
    private final ScheduledExecutorService executor;
    private static final String THREAD_NAME_PREFIX = "RxScheduledExecutorPool-";
    private static final RxThreadFactory THREAD_FACTORY = new RxThreadFactory(THREAD_NAME_PREFIX);
    private static final GenericScheduledExecutorService INSTANCE = new GenericScheduledExecutorService();

    private GenericScheduledExecutorService() {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        iAvailableProcessors = iAvailableProcessors > 4 ? iAvailableProcessors / 2 : iAvailableProcessors;
        ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(iAvailableProcessors > 8 ? 8 : iAvailableProcessors, THREAD_FACTORY);
        if (!NewThreadWorker.tryEnableCancelPolicy(scheduledExecutorServiceNewScheduledThreadPool) && (scheduledExecutorServiceNewScheduledThreadPool instanceof ScheduledThreadPoolExecutor)) {
            NewThreadWorker.registerExecutor((ScheduledThreadPoolExecutor) scheduledExecutorServiceNewScheduledThreadPool);
        }
        this.executor = scheduledExecutorServiceNewScheduledThreadPool;
    }

    public static ScheduledExecutorService getInstance() {
        return INSTANCE.executor;
    }
}
