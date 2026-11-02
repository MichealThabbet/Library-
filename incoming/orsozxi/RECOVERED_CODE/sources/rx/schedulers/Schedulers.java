package rx.schedulers;

import java.util.concurrent.Executor;
import rx.Scheduler;
import rx.internal.schedulers.EventLoopsScheduler;
import rx.plugins.RxJavaPlugins;

/* loaded from: classes3.dex */
public final class Schedulers {
    private static final Schedulers INSTANCE = new Schedulers();
    private final Scheduler computationScheduler;
    private final Scheduler ioScheduler;
    private final Scheduler newThreadScheduler;

    private Schedulers() {
        Scheduler computationScheduler = RxJavaPlugins.getInstance().getSchedulersHook().getComputationScheduler();
        if (computationScheduler != null) {
            this.computationScheduler = computationScheduler;
        } else {
            this.computationScheduler = new EventLoopsScheduler();
        }
        Scheduler iOScheduler = RxJavaPlugins.getInstance().getSchedulersHook().getIOScheduler();
        if (iOScheduler != null) {
            this.ioScheduler = iOScheduler;
        } else {
            this.ioScheduler = new CachedThreadScheduler();
        }
        Scheduler newThreadScheduler = RxJavaPlugins.getInstance().getSchedulersHook().getNewThreadScheduler();
        if (newThreadScheduler != null) {
            this.newThreadScheduler = newThreadScheduler;
        } else {
            this.newThreadScheduler = NewThreadScheduler.instance();
        }
    }

    public static Scheduler immediate() {
        return ImmediateScheduler.instance();
    }

    public static Scheduler trampoline() {
        return TrampolineScheduler.instance();
    }

    public static Scheduler newThread() {
        return INSTANCE.newThreadScheduler;
    }

    public static Scheduler computation() {
        return INSTANCE.computationScheduler;
    }

    public static Scheduler io() {
        return INSTANCE.ioScheduler;
    }

    public static TestScheduler test() {
        return new TestScheduler();
    }

    public static Scheduler from(Executor executor) {
        return new ExecutorScheduler(executor);
    }
}
