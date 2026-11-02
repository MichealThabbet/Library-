package rx.internal.util.unsafe;

/* loaded from: classes3.dex */
interface MessagePassingQueue<M> {
    boolean isEmpty();

    boolean offer(M m);

    M peek();

    M poll();

    int size();
}
