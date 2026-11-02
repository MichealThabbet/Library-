package rx.internal.util;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import kotlin.jvm.internal.LongCompanionObject;
import rx.Producer;

/* loaded from: classes3.dex */
public final class BackpressureDrainManager implements Producer {
    protected static final AtomicLongFieldUpdater<BackpressureDrainManager> REQUESTED_COUNT = AtomicLongFieldUpdater.newUpdater(BackpressureDrainManager.class, "requestedCount");
    protected final BackpressureQueueCallback actual;
    protected boolean emitting;
    protected Throwable exception;
    protected volatile long requestedCount;
    protected volatile boolean terminated;

    public interface BackpressureQueueCallback {
        boolean accept(Object obj);

        void complete(Throwable th);

        Object peek();

        Object poll();
    }

    public BackpressureDrainManager(BackpressureQueueCallback backpressureQueueCallback) {
        this.actual = backpressureQueueCallback;
    }

    public final boolean isTerminated() {
        return this.terminated;
    }

    public final void terminate() {
        this.terminated = true;
    }

    public final void terminate(Throwable th) {
        if (this.terminated) {
            return;
        }
        this.exception = th;
        this.terminated = true;
    }

    public final void terminateAndDrain() throws Throwable {
        this.terminated = true;
        drain();
    }

    public final void terminateAndDrain(Throwable th) throws Throwable {
        if (this.terminated) {
            return;
        }
        this.exception = th;
        this.terminated = true;
        drain();
    }

    @Override // rx.Producer
    public final void request(long j) throws Throwable {
        boolean z;
        if (j == 0) {
            return;
        }
        while (true) {
            long j2 = this.requestedCount;
            boolean z2 = true;
            z = j2 == 0;
            long j3 = LongCompanionObject.MAX_VALUE;
            if (j2 == LongCompanionObject.MAX_VALUE) {
                break;
            }
            if (j == LongCompanionObject.MAX_VALUE) {
                j3 = j;
            } else {
                if (j2 <= LongCompanionObject.MAX_VALUE - j) {
                    j3 = j2 + j;
                }
                z2 = z;
            }
            if (REQUESTED_COUNT.compareAndSet(this, j2, j3)) {
                z = z2;
                break;
            }
        }
        if (z) {
            drain();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0034, code lost:
    
        monitor-enter(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0035, code lost:
    
        r1 = r13.terminated;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x003b, code lost:
    
        if (r5.peek() == null) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x003d, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x003f, code lost:
    
        r2 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0049, code lost:
    
        if (r13.requestedCount != kotlin.jvm.internal.LongCompanionObject.MAX_VALUE) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x004b, code lost:
    
        if (r2 != false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x004d, code lost:
    
        if (r1 != false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x004f, code lost:
    
        r13.emitting = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0051, code lost:
    
        monitor-exit(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0052, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0053, code lost:
    
        r2 = Long.MAX_VALUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0055, code lost:
    
        r9 = rx.internal.util.BackpressureDrainManager.REQUESTED_COUNT.addAndGet(r13, -r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x005f, code lost:
    
        if (r9 == 0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0061, code lost:
    
        if (r2 != false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0063, code lost:
    
        if (r1 == false) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0065, code lost:
    
        if (r2 == false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0068, code lost:
    
        r2 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x006b, code lost:
    
        r13.emitting = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x006d, code lost:
    
        monitor-exit(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x006e, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x006f, code lost:
    
        r1 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0071, code lost:
    
        monitor-exit(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0072, code lost:
    
        throw r1;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x002e A[Catch: all -> 0x0082, TRY_ENTER, TryCatch #0 {all -> 0x0082, blocks: (B:10:0x0010, B:26:0x0034, B:16:0x001d, B:24:0x002e, B:58:0x0075), top: B:76:0x0010 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void drain() throws java.lang.Throwable {
        /*
            r13 = this;
            monitor-enter(r13)
            boolean r0 = r13.emitting     // Catch: java.lang.Throwable -> L8f
            if (r0 == 0) goto L7
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L8f
            return
        L7:
            r0 = 1
            r13.emitting = r0     // Catch: java.lang.Throwable -> L8f
            boolean r1 = r13.terminated     // Catch: java.lang.Throwable -> L8f
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L8f
            long r2 = r13.requestedCount
            r4 = 0
            rx.internal.util.BackpressureDrainManager$BackpressureQueueCallback r5 = r13.actual     // Catch: java.lang.Throwable -> L82
        L12:
            r6 = 0
        L13:
            r7 = 0
            int r9 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r9 > 0) goto L1b
            if (r1 == 0) goto L34
        L1b:
            if (r1 == 0) goto L2e
            java.lang.Object r10 = r5.peek()     // Catch: java.lang.Throwable -> L82
            if (r10 != 0) goto L2b
            java.lang.Throwable r1 = r13.exception     // Catch: java.lang.Throwable -> L29
            r5.complete(r1)     // Catch: java.lang.Throwable -> L29
            return
        L29:
            r1 = move-exception
            goto L84
        L2b:
            if (r9 != 0) goto L2e
            goto L34
        L2e:
            java.lang.Object r9 = r5.poll()     // Catch: java.lang.Throwable -> L82
            if (r9 != 0) goto L75
        L34:
            monitor-enter(r13)     // Catch: java.lang.Throwable -> L82
            boolean r1 = r13.terminated     // Catch: java.lang.Throwable -> L6f
            java.lang.Object r2 = r5.peek()     // Catch: java.lang.Throwable -> L6f
            if (r2 == 0) goto L3f
            r2 = 1
            goto L40
        L3f:
            r2 = 0
        L40:
            long r9 = r13.requestedCount     // Catch: java.lang.Throwable -> L6f
            r11 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            int r3 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r3 != 0) goto L55
            if (r2 != 0) goto L53
            if (r1 != 0) goto L53
            r13.emitting = r4     // Catch: java.lang.Throwable -> L73
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L73
            return
        L53:
            r2 = r11
            goto L69
        L55:
            java.util.concurrent.atomic.AtomicLongFieldUpdater<rx.internal.util.BackpressureDrainManager> r3 = rx.internal.util.BackpressureDrainManager.REQUESTED_COUNT     // Catch: java.lang.Throwable -> L6f
            int r6 = -r6
            long r9 = (long) r6     // Catch: java.lang.Throwable -> L6f
            long r9 = r3.addAndGet(r13, r9)     // Catch: java.lang.Throwable -> L6f
            int r3 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r3 == 0) goto L63
            if (r2 != 0) goto L68
        L63:
            if (r1 == 0) goto L6b
            if (r2 == 0) goto L68
            goto L6b
        L68:
            r2 = r9
        L69:
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L6f
            goto L12
        L6b:
            r13.emitting = r4     // Catch: java.lang.Throwable -> L73
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L73
            return
        L6f:
            r1 = move-exception
            r0 = 0
        L71:
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L73
            throw r1     // Catch: java.lang.Throwable -> L29
        L73:
            r1 = move-exception
            goto L71
        L75:
            boolean r7 = r5.accept(r9)     // Catch: java.lang.Throwable -> L82
            if (r7 == 0) goto L7c
            return
        L7c:
            r7 = 1
            long r2 = r2 - r7
            int r6 = r6 + 1
            goto L13
        L82:
            r1 = move-exception
            r0 = 0
        L84:
            if (r0 != 0) goto L8e
            monitor-enter(r13)
            r13.emitting = r4     // Catch: java.lang.Throwable -> L8b
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L8b
            goto L8e
        L8b:
            r0 = move-exception
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L8b
            throw r0
        L8e:
            throw r1
        L8f:
            r0 = move-exception
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L8f
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.util.BackpressureDrainManager.drain():void");
    }
}
