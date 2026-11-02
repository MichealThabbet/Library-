package rx.subjects;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.operators.NotificationLite;
import rx.internal.util.UtilityFunctions;
import rx.schedulers.Timestamped;
import rx.subjects.SubjectSubscriptionManager;

/* loaded from: classes3.dex */
public final class ReplaySubject<T> extends Subject<T, T> {
    final SubjectSubscriptionManager<T> ssm;
    final ReplayState<T, ?> state;

    interface EvictionPolicy {
        void evict(NodeList<Object> nodeList);

        void evictFinal(NodeList<Object> nodeList);

        boolean test(Object obj, long j);
    }

    interface ReplayState<T, I> {
        void complete();

        void error(Throwable th);

        boolean isEmpty();

        T latest();

        void next(T t);

        boolean replayObserver(SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver);

        I replayObserverFromIndex(I i, SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver);

        I replayObserverFromIndexTest(I i, SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver, long j);

        int size();

        boolean terminated();

        T[] toArray(T[] tArr);
    }

    public static <T> ReplaySubject<T> create() {
        return create(16);
    }

    public static <T> ReplaySubject<T> create(int i) {
        final UnboundedReplayState unboundedReplayState = new UnboundedReplayState(i);
        SubjectSubscriptionManager subjectSubscriptionManager = new SubjectSubscriptionManager();
        subjectSubscriptionManager.onStart = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() { // from class: rx.subjects.ReplaySubject.1
            @Override // rx.functions.Action1
            public void call(SubjectSubscriptionManager.SubjectObserver<T> subjectObserver) {
                subjectObserver.index(Integer.valueOf(unboundedReplayState.replayObserverFromIndex((Integer) 0, (SubjectSubscriptionManager.SubjectObserver) subjectObserver).intValue()));
            }
        };
        subjectSubscriptionManager.onAdded = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() { // from class: rx.subjects.ReplaySubject.2
            @Override // rx.functions.Action1
            public void call(SubjectSubscriptionManager.SubjectObserver<T> subjectObserver) throws Throwable {
                boolean z;
                synchronized (subjectObserver) {
                    if (subjectObserver.first && !subjectObserver.emitting) {
                        subjectObserver.first = false;
                        boolean z2 = true;
                        subjectObserver.emitting = true;
                        while (true) {
                            try {
                                int iIntValue = ((Integer) subjectObserver.index()).intValue();
                                int i2 = unboundedReplayState.index;
                                if (iIntValue != i2) {
                                    subjectObserver.index(unboundedReplayState.replayObserverFromIndex(Integer.valueOf(iIntValue), (SubjectSubscriptionManager.SubjectObserver) subjectObserver));
                                }
                                try {
                                    synchronized (subjectObserver) {
                                        try {
                                            if (i2 == unboundedReplayState.index) {
                                                subjectObserver.emitting = false;
                                                return;
                                            }
                                        } catch (Throwable th) {
                                            th = th;
                                            z2 = false;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                z = false;
                            }
                            try {
                                throw th;
                            } catch (Throwable th4) {
                                z = z2;
                                th = th4;
                                if (!z) {
                                    synchronized (subjectObserver) {
                                        subjectObserver.emitting = false;
                                    }
                                }
                                throw th;
                            }
                        }
                    }
                }
            }
        };
        subjectSubscriptionManager.onTerminated = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() { // from class: rx.subjects.ReplaySubject.3
            @Override // rx.functions.Action1
            public void call(SubjectSubscriptionManager.SubjectObserver<T> subjectObserver) {
                int i2 = (Integer) subjectObserver.index();
                if (i2 == null) {
                    i2 = 0;
                }
                unboundedReplayState.replayObserverFromIndex(i2, (SubjectSubscriptionManager.SubjectObserver) subjectObserver);
            }
        };
        return new ReplaySubject<>(subjectSubscriptionManager, subjectSubscriptionManager, unboundedReplayState);
    }

    static <T> ReplaySubject<T> createUnbounded() {
        BoundedState boundedState = new BoundedState(new EmptyEvictionPolicy(), UtilityFunctions.identity(), UtilityFunctions.identity());
        return createWithState(boundedState, new DefaultOnAdd(boundedState));
    }

    public static <T> ReplaySubject<T> createWithSize(int i) {
        BoundedState boundedState = new BoundedState(new SizeEvictionPolicy(i), UtilityFunctions.identity(), UtilityFunctions.identity());
        return createWithState(boundedState, new DefaultOnAdd(boundedState));
    }

    public static <T> ReplaySubject<T> createWithTime(long j, TimeUnit timeUnit, Scheduler scheduler) {
        BoundedState boundedState = new BoundedState(new TimeEvictionPolicy(timeUnit.toMillis(j), scheduler), new AddTimestamped(scheduler), new RemoveTimestamped());
        return createWithState(boundedState, new TimedOnAdd(boundedState, scheduler));
    }

    public static <T> ReplaySubject<T> createWithTimeAndSize(long j, TimeUnit timeUnit, int i, Scheduler scheduler) {
        BoundedState boundedState = new BoundedState(new PairEvictionPolicy(new SizeEvictionPolicy(i), new TimeEvictionPolicy(timeUnit.toMillis(j), scheduler)), new AddTimestamped(scheduler), new RemoveTimestamped());
        return createWithState(boundedState, new TimedOnAdd(boundedState, scheduler));
    }

    static final <T> ReplaySubject<T> createWithState(final BoundedState<T> boundedState, Action1<SubjectSubscriptionManager.SubjectObserver<T>> action1) {
        SubjectSubscriptionManager subjectSubscriptionManager = new SubjectSubscriptionManager();
        subjectSubscriptionManager.onStart = action1;
        subjectSubscriptionManager.onAdded = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() { // from class: rx.subjects.ReplaySubject.4
            @Override // rx.functions.Action1
            public void call(SubjectSubscriptionManager.SubjectObserver<T> subjectObserver) throws Throwable {
                boolean z;
                synchronized (subjectObserver) {
                    if (subjectObserver.first && !subjectObserver.emitting) {
                        subjectObserver.first = false;
                        boolean z2 = true;
                        subjectObserver.emitting = true;
                        while (true) {
                            try {
                                NodeList.Node<Object> node = (NodeList.Node) subjectObserver.index();
                                NodeList.Node<Object> nodeTail = boundedState.tail();
                                if (node != nodeTail) {
                                    subjectObserver.index(boundedState.replayObserverFromIndex(node, (SubjectSubscriptionManager.SubjectObserver) subjectObserver));
                                }
                                try {
                                    synchronized (subjectObserver) {
                                        try {
                                            if (nodeTail == boundedState.tail()) {
                                                subjectObserver.emitting = false;
                                                return;
                                            }
                                        } catch (Throwable th) {
                                            th = th;
                                            z2 = false;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                z = false;
                            }
                            try {
                                throw th;
                            } catch (Throwable th4) {
                                z = z2;
                                th = th4;
                                if (!z) {
                                    synchronized (subjectObserver) {
                                        subjectObserver.emitting = false;
                                    }
                                }
                                throw th;
                            }
                        }
                    }
                }
            }
        };
        subjectSubscriptionManager.onTerminated = new Action1<SubjectSubscriptionManager.SubjectObserver<T>>() { // from class: rx.subjects.ReplaySubject.5
            @Override // rx.functions.Action1
            public void call(SubjectSubscriptionManager.SubjectObserver<T> subjectObserver) {
                NodeList.Node<Object> nodeHead = (NodeList.Node) subjectObserver.index();
                if (nodeHead == null) {
                    nodeHead = boundedState.head();
                }
                boundedState.replayObserverFromIndex(nodeHead, (SubjectSubscriptionManager.SubjectObserver) subjectObserver);
            }
        };
        return new ReplaySubject<>(subjectSubscriptionManager, subjectSubscriptionManager, boundedState);
    }

    ReplaySubject(Observable.OnSubscribe<T> onSubscribe, SubjectSubscriptionManager<T> subjectSubscriptionManager, ReplayState<T, ?> replayState) {
        super(onSubscribe);
        this.ssm = subjectSubscriptionManager;
        this.state = replayState;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // rx.Observer
    public void onNext(T t) {
        if (this.ssm.active) {
            this.state.next(t);
            for (SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver : this.ssm.observers()) {
                if (caughtUp(subjectObserver)) {
                    subjectObserver.onNext(t);
                }
            }
        }
    }

    @Override // rx.Observer
    public void onError(Throwable th) {
        if (this.ssm.active) {
            this.state.error(th);
            ArrayList arrayList = null;
            for (SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver : this.ssm.terminate(NotificationLite.instance().error(th))) {
                try {
                    if (caughtUp(subjectObserver)) {
                        subjectObserver.onError(th);
                    }
                } catch (Throwable th2) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(th2);
                }
            }
            Exceptions.throwIfAny(arrayList);
        }
    }

    @Override // rx.Observer
    public void onCompleted() {
        if (this.ssm.active) {
            this.state.complete();
            for (SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver : this.ssm.terminate(NotificationLite.instance().completed())) {
                if (caughtUp(subjectObserver)) {
                    subjectObserver.onCompleted();
                }
            }
        }
    }

    int subscriberCount() {
        return this.ssm.state.observers.length;
    }

    @Override // rx.subjects.Subject
    public boolean hasObservers() {
        return this.ssm.observers().length > 0;
    }

    private boolean caughtUp(SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver) {
        if (subjectObserver.caughtUp) {
            return true;
        }
        if (!this.state.replayObserver(subjectObserver)) {
            return false;
        }
        subjectObserver.caughtUp = true;
        subjectObserver.index(null);
        return false;
    }

    static final class UnboundedReplayState<T> implements ReplayState<T, Integer> {
        static final AtomicIntegerFieldUpdater<UnboundedReplayState> INDEX_UPDATER = AtomicIntegerFieldUpdater.newUpdater(UnboundedReplayState.class, FirebaseAnalytics.Param.INDEX);
        volatile int index;
        private final ArrayList<Object> list;
        private final NotificationLite<T> nl = NotificationLite.instance();
        private volatile boolean terminated;

        public UnboundedReplayState(int i) {
            this.list = new ArrayList<>(i);
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public void next(T t) {
            if (this.terminated) {
                return;
            }
            this.list.add(this.nl.next(t));
            INDEX_UPDATER.getAndIncrement(this);
        }

        public void accept(Observer<? super T> observer, int i) {
            this.nl.accept(observer, this.list.get(i));
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public void complete() {
            if (this.terminated) {
                return;
            }
            this.terminated = true;
            this.list.add(this.nl.completed());
            INDEX_UPDATER.getAndIncrement(this);
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public void error(Throwable th) {
            if (this.terminated) {
                return;
            }
            this.terminated = true;
            this.list.add(this.nl.error(th));
            INDEX_UPDATER.getAndIncrement(this);
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public boolean terminated() {
            return this.terminated;
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public boolean replayObserver(SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver) {
            synchronized (subjectObserver) {
                subjectObserver.first = false;
                if (subjectObserver.emitting) {
                    return false;
                }
                Integer num = (Integer) subjectObserver.index();
                if (num != null) {
                    subjectObserver.index(Integer.valueOf(replayObserverFromIndex(num, (SubjectSubscriptionManager.SubjectObserver) subjectObserver).intValue()));
                    return true;
                }
                throw new IllegalStateException("failed to find lastEmittedLink for: " + subjectObserver);
            }
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public Integer replayObserverFromIndex(Integer num, SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver) {
            int iIntValue = num.intValue();
            while (iIntValue < this.index) {
                accept(subjectObserver, iIntValue);
                iIntValue++;
            }
            return Integer.valueOf(iIntValue);
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public Integer replayObserverFromIndexTest(Integer num, SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver, long j) {
            return replayObserverFromIndex(num, (SubjectSubscriptionManager.SubjectObserver) subjectObserver);
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public int size() {
            int i = this.index;
            if (i > 0) {
                int i2 = i - 1;
                Object obj = this.list.get(i2);
                if (this.nl.isCompleted(obj) || this.nl.isError(obj)) {
                    return i2;
                }
            }
            return i;
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public boolean isEmpty() {
            return size() == 0;
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public T[] toArray(T[] tArr) {
            int size = size();
            if (size > 0) {
                if (size > tArr.length) {
                    tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
                }
                for (int i = 0; i < size; i++) {
                    tArr[i] = this.list.get(i);
                }
                if (tArr.length > size) {
                    tArr[size] = null;
                }
            } else if (tArr.length > 0) {
                tArr[0] = null;
            }
            return tArr;
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public T latest() {
            int i = this.index;
            if (i > 0) {
                Object obj = this.list.get(i - 1);
                if (!this.nl.isCompleted(obj) && !this.nl.isError(obj)) {
                    return this.nl.getValue(obj);
                }
                if (i > 1) {
                    return this.nl.getValue(this.list.get(i - 2));
                }
            }
            return null;
        }
    }

    static final class BoundedState<T> implements ReplayState<T, NodeList.Node<Object>> {
        final Func1<Object, Object> enterTransform;
        final EvictionPolicy evictionPolicy;
        final Func1<Object, Object> leaveTransform;
        final NodeList<Object> list;
        final NotificationLite<T> nl = NotificationLite.instance();
        volatile NodeList.Node<Object> tail;
        volatile boolean terminated;

        public BoundedState(EvictionPolicy evictionPolicy, Func1<Object, Object> func1, Func1<Object, Object> func12) {
            NodeList<Object> nodeList = new NodeList<>();
            this.list = nodeList;
            this.tail = nodeList.tail;
            this.evictionPolicy = evictionPolicy;
            this.enterTransform = func1;
            this.leaveTransform = func12;
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public void next(T t) {
            if (this.terminated) {
                return;
            }
            this.list.addLast(this.enterTransform.call(this.nl.next(t)));
            this.evictionPolicy.evict(this.list);
            this.tail = this.list.tail;
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public void complete() {
            if (this.terminated) {
                return;
            }
            this.terminated = true;
            this.list.addLast(this.enterTransform.call(this.nl.completed()));
            this.evictionPolicy.evictFinal(this.list);
            this.tail = this.list.tail;
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public void error(Throwable th) {
            if (this.terminated) {
                return;
            }
            this.terminated = true;
            this.list.addLast(this.enterTransform.call(this.nl.error(th)));
            this.evictionPolicy.evictFinal(this.list);
            this.tail = this.list.tail;
        }

        public void accept(Observer<? super T> observer, NodeList.Node<Object> node) {
            this.nl.accept(observer, this.leaveTransform.call(node.value));
        }

        public void acceptTest(Observer<? super T> observer, NodeList.Node<Object> node, long j) {
            Object obj = node.value;
            if (this.evictionPolicy.test(obj, j)) {
                return;
            }
            this.nl.accept(observer, this.leaveTransform.call(obj));
        }

        public NodeList.Node<Object> head() {
            return this.list.head;
        }

        public NodeList.Node<Object> tail() {
            return this.tail;
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public boolean replayObserver(SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver) {
            synchronized (subjectObserver) {
                subjectObserver.first = false;
                if (subjectObserver.emitting) {
                    return false;
                }
                subjectObserver.index(replayObserverFromIndex((NodeList.Node<Object>) subjectObserver.index(), (SubjectSubscriptionManager.SubjectObserver) subjectObserver));
                return true;
            }
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public NodeList.Node<Object> replayObserverFromIndex(NodeList.Node<Object> node, SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver) {
            while (node != tail()) {
                accept(subjectObserver, node.next);
                node = node.next;
            }
            return node;
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public NodeList.Node<Object> replayObserverFromIndexTest(NodeList.Node<Object> node, SubjectSubscriptionManager.SubjectObserver<? super T> subjectObserver, long j) {
            while (node != tail()) {
                acceptTest(subjectObserver, node.next, j);
                node = node.next;
            }
            return node;
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public boolean terminated() {
            return this.terminated;
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public int size() {
            NodeList.Node<Object> node;
            Object objCall;
            NodeList.Node<Object> nodeHead = head();
            NodeList.Node<Object> node2 = nodeHead.next;
            int i = 0;
            while (true) {
                NodeList.Node<Object> node3 = node2;
                node = nodeHead;
                nodeHead = node3;
                if (nodeHead == null) {
                    break;
                }
                i++;
                node2 = nodeHead.next;
            }
            return (node.value == null || (objCall = this.leaveTransform.call(node.value)) == null) ? i : (this.nl.isError(objCall) || this.nl.isCompleted(objCall)) ? i - 1 : i;
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public boolean isEmpty() {
            NodeList.Node<Object> node = head().next;
            if (node == null) {
                return true;
            }
            Object objCall = this.leaveTransform.call(node.value);
            return this.nl.isError(objCall) || this.nl.isCompleted(objCall);
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public T[] toArray(T[] tArr) {
            ArrayList arrayList = new ArrayList();
            for (NodeList.Node node = head().next; node != null; node = node.next) {
                Object objCall = this.leaveTransform.call(node.value);
                if (node.next == null && (this.nl.isError(objCall) || this.nl.isCompleted(objCall))) {
                    break;
                }
                arrayList.add(objCall);
            }
            return (T[]) arrayList.toArray(tArr);
        }

        @Override // rx.subjects.ReplaySubject.ReplayState
        public T latest() {
            NodeList.Node<Object> node = head().next;
            if (node == null) {
                return null;
            }
            NodeList.Node<Object> node2 = null;
            while (node != tail()) {
                node2 = node;
                node = node.next;
            }
            Object objCall = this.leaveTransform.call(node.value);
            if (!this.nl.isError(objCall) && !this.nl.isCompleted(objCall)) {
                return this.nl.getValue(objCall);
            }
            if (node2 == null) {
                return null;
            }
            return this.nl.getValue(this.leaveTransform.call(node2.value));
        }
    }

    static final class SizeEvictionPolicy implements EvictionPolicy {
        final int maxSize;

        @Override // rx.subjects.ReplaySubject.EvictionPolicy
        public boolean test(Object obj, long j) {
            return false;
        }

        public SizeEvictionPolicy(int i) {
            this.maxSize = i;
        }

        @Override // rx.subjects.ReplaySubject.EvictionPolicy
        public void evict(NodeList<Object> nodeList) {
            while (nodeList.size() > this.maxSize) {
                nodeList.removeFirst();
            }
        }

        @Override // rx.subjects.ReplaySubject.EvictionPolicy
        public void evictFinal(NodeList<Object> nodeList) {
            while (nodeList.size() > this.maxSize + 1) {
                nodeList.removeFirst();
            }
        }
    }

    static final class TimeEvictionPolicy implements EvictionPolicy {
        final long maxAgeMillis;
        final Scheduler scheduler;

        public TimeEvictionPolicy(long j, Scheduler scheduler) {
            this.maxAgeMillis = j;
            this.scheduler = scheduler;
        }

        @Override // rx.subjects.ReplaySubject.EvictionPolicy
        public void evict(NodeList<Object> nodeList) {
            long jNow = this.scheduler.now();
            while (!nodeList.isEmpty() && test(nodeList.head.next.value, jNow)) {
                nodeList.removeFirst();
            }
        }

        @Override // rx.subjects.ReplaySubject.EvictionPolicy
        public void evictFinal(NodeList<Object> nodeList) {
            long jNow = this.scheduler.now();
            while (nodeList.size > 1 && test(nodeList.head.next.value, jNow)) {
                nodeList.removeFirst();
            }
        }

        @Override // rx.subjects.ReplaySubject.EvictionPolicy
        public boolean test(Object obj, long j) {
            return ((Timestamped) obj).getTimestampMillis() <= j - this.maxAgeMillis;
        }
    }

    static final class PairEvictionPolicy implements EvictionPolicy {
        final EvictionPolicy first;
        final EvictionPolicy second;

        public PairEvictionPolicy(EvictionPolicy evictionPolicy, EvictionPolicy evictionPolicy2) {
            this.first = evictionPolicy;
            this.second = evictionPolicy2;
        }

        @Override // rx.subjects.ReplaySubject.EvictionPolicy
        public void evict(NodeList<Object> nodeList) {
            this.first.evict(nodeList);
            this.second.evict(nodeList);
        }

        @Override // rx.subjects.ReplaySubject.EvictionPolicy
        public void evictFinal(NodeList<Object> nodeList) {
            this.first.evictFinal(nodeList);
            this.second.evictFinal(nodeList);
        }

        @Override // rx.subjects.ReplaySubject.EvictionPolicy
        public boolean test(Object obj, long j) {
            return this.first.test(obj, j) || this.second.test(obj, j);
        }
    }

    static final class AddTimestamped implements Func1<Object, Object> {
        final Scheduler scheduler;

        public AddTimestamped(Scheduler scheduler) {
            this.scheduler = scheduler;
        }

        @Override // rx.functions.Func1
        public Object call(Object obj) {
            return new Timestamped(this.scheduler.now(), obj);
        }
    }

    static final class RemoveTimestamped implements Func1<Object, Object> {
        RemoveTimestamped() {
        }

        @Override // rx.functions.Func1
        public Object call(Object obj) {
            return ((Timestamped) obj).getValue();
        }
    }

    static final class DefaultOnAdd<T> implements Action1<SubjectSubscriptionManager.SubjectObserver<T>> {
        final BoundedState<T> state;

        public DefaultOnAdd(BoundedState<T> boundedState) {
            this.state = boundedState;
        }

        @Override // rx.functions.Action1
        public void call(SubjectSubscriptionManager.SubjectObserver<T> subjectObserver) {
            BoundedState<T> boundedState = this.state;
            subjectObserver.index(boundedState.replayObserverFromIndex(boundedState.head(), (SubjectSubscriptionManager.SubjectObserver) subjectObserver));
        }
    }

    static final class TimedOnAdd<T> implements Action1<SubjectSubscriptionManager.SubjectObserver<T>> {
        final Scheduler scheduler;
        final BoundedState<T> state;

        public TimedOnAdd(BoundedState<T> boundedState, Scheduler scheduler) {
            this.state = boundedState;
            this.scheduler = scheduler;
        }

        @Override // rx.functions.Action1
        public void call(SubjectSubscriptionManager.SubjectObserver<T> subjectObserver) {
            NodeList.Node<Object> nodeReplayObserverFromIndex;
            if (!this.state.terminated) {
                BoundedState<T> boundedState = this.state;
                nodeReplayObserverFromIndex = boundedState.replayObserverFromIndexTest(boundedState.head(), (SubjectSubscriptionManager.SubjectObserver) subjectObserver, this.scheduler.now());
            } else {
                BoundedState<T> boundedState2 = this.state;
                nodeReplayObserverFromIndex = boundedState2.replayObserverFromIndex(boundedState2.head(), (SubjectSubscriptionManager.SubjectObserver) subjectObserver);
            }
            subjectObserver.index(nodeReplayObserverFromIndex);
        }
    }

    static final class NodeList<T> {
        final Node<T> head;
        int size;
        Node<T> tail;

        NodeList() {
            Node<T> node = new Node<>(null);
            this.head = node;
            this.tail = node;
        }

        static final class Node<T> {
            volatile Node<T> next;
            final T value;

            Node(T t) {
                this.value = t;
            }
        }

        public void addLast(T t) {
            Node<T> node = this.tail;
            Node<T> node2 = new Node<>(t);
            node.next = node2;
            this.tail = node2;
            this.size++;
        }

        public T removeFirst() {
            if (this.head.next == null) {
                throw new IllegalStateException("Empty!");
            }
            Node<T> node = this.head.next;
            this.head.next = node.next;
            if (this.head.next == null) {
                this.tail = this.head;
            }
            this.size--;
            return node.value;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        public int size() {
            return this.size;
        }

        public void clear() {
            this.tail = this.head;
            this.size = 0;
        }
    }

    static final class EmptyEvictionPolicy implements EvictionPolicy {
        @Override // rx.subjects.ReplaySubject.EvictionPolicy
        public void evict(NodeList<Object> nodeList) {
        }

        @Override // rx.subjects.ReplaySubject.EvictionPolicy
        public void evictFinal(NodeList<Object> nodeList) {
        }

        @Override // rx.subjects.ReplaySubject.EvictionPolicy
        public boolean test(Object obj, long j) {
            return true;
        }

        EmptyEvictionPolicy() {
        }
    }

    @Override // rx.subjects.Subject
    public boolean hasThrowable() {
        return this.ssm.nl.isError(this.ssm.get());
    }

    @Override // rx.subjects.Subject
    public boolean hasCompleted() {
        NotificationLite<T> notificationLite = this.ssm.nl;
        Object obj = this.ssm.get();
        return (obj == null || notificationLite.isError(obj)) ? false : true;
    }

    @Override // rx.subjects.Subject
    public Throwable getThrowable() {
        NotificationLite<T> notificationLite = this.ssm.nl;
        Object obj = this.ssm.get();
        if (notificationLite.isError(obj)) {
            return notificationLite.getError(obj);
        }
        return null;
    }

    public int size() {
        return this.state.size();
    }

    public boolean hasAnyValue() {
        return !this.state.isEmpty();
    }

    @Override // rx.subjects.Subject
    public boolean hasValue() {
        return hasAnyValue();
    }

    @Override // rx.subjects.Subject
    public T[] getValues(T[] tArr) {
        return this.state.toArray(tArr);
    }

    @Override // rx.subjects.Subject
    public T getValue() {
        return this.state.latest();
    }
}
