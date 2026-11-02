package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzrc;
import com.google.android.gms.internal.gtm.zzrc.zza;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public abstract class zzrc<MessageType extends zzrc<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzpl<MessageType, BuilderType> {
    private static Map<Object, zzrc<?, ?>> zzbam = new ConcurrentHashMap();
    protected zzts zzbak = zzts.zzrj();
    private int zzbal = -1;

    public static abstract class zzc<MessageType extends zzc<MessageType, BuilderType>, BuilderType> extends zzrc<MessageType, BuilderType> implements zzsm {
        protected zzqt<Object> zzbaq = zzqt.zzov();
    }

    public static class zzd<ContainingType extends zzsk, Type> extends zzqn<ContainingType, Type> {
    }

    public enum zze {
        public static final int zzbar = 1;
        public static final int zzbas = 2;
        public static final int zzbat = 3;
        public static final int zzbau = 4;
        public static final int zzbav = 5;
        public static final int zzbaw = 6;
        public static final int zzbax = 7;
        public static final int zzbaz = 1;
        public static final int zzbba = 2;
        public static final int zzbbc = 1;
        public static final int zzbbd = 2;
        private static final /* synthetic */ int[] zzbay = {1, 2, 3, 4, 5, 6, 7};
        private static final /* synthetic */ int[] zzbbb = {1, 2};
        private static final /* synthetic */ int[] zzbbe = {1, 2};

        public static int[] zzpn() {
            return (int[]) zzbay.clone();
        }
    }

    protected abstract Object zza(int i, Object obj, Object obj2);

    public static class zzb<T extends zzrc<T, ?>> extends zzpn<T> {
        private final T zzban;

        public zzb(T t) {
            this.zzban = t;
        }

        @Override // com.google.android.gms.internal.gtm.zzsu
        public final /* synthetic */ Object zza(zzqe zzqeVar, zzqp zzqpVar) throws zzrk {
            return zzrc.zza(this.zzban, zzqeVar, zzqpVar);
        }
    }

    public String toString() {
        return zzsn.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zzavp != 0) {
            return this.zzavp;
        }
        this.zzavp = zzsw.zzqs().zzaf(this).hashCode(this);
        return this.zzavp;
    }

    public static abstract class zza<MessageType extends zzrc<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzpm<MessageType, BuilderType> {
        private final MessageType zzban;
        private MessageType zzbao;
        private boolean zzbap = false;

        protected zza(MessageType messagetype) {
            this.zzban = messagetype;
            this.zzbao = (MessageType) messagetype.zza(zze.zzbau, null, null);
        }

        @Override // com.google.android.gms.internal.gtm.zzsm
        public final boolean isInitialized() {
            return zzrc.zza(this.zzbao, false);
        }

        @Override // com.google.android.gms.internal.gtm.zzsl
        /* renamed from: zzpj, reason: merged with bridge method [inline-methods] */
        public MessageType zzpl() {
            if (this.zzbap) {
                return this.zzbao;
            }
            this.zzbao.zzmi();
            this.zzbap = true;
            return this.zzbao;
        }

        @Override // com.google.android.gms.internal.gtm.zzsl
        /* renamed from: zzpk, reason: merged with bridge method [inline-methods] */
        public final MessageType zzpm() {
            MessageType messagetype = (MessageType) zzpl();
            if (messagetype.isInitialized()) {
                return messagetype;
            }
            throw new zztq(messagetype);
        }

        @Override // com.google.android.gms.internal.gtm.zzpm
        public final BuilderType zza(MessageType messagetype) {
            if (this.zzbap) {
                MessageType messagetype2 = (MessageType) this.zzbao.zza(zze.zzbau, null, null);
                zza(messagetype2, this.zzbao);
                this.zzbao = messagetype2;
                this.zzbap = false;
            }
            zza(this.zzbao, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzsw.zzqs().zzaf(messagetype).zzd(messagetype, messagetype2);
        }

        @Override // com.google.android.gms.internal.gtm.zzpm
        /* renamed from: zzmx */
        public final /* synthetic */ zzpm clone() {
            return (zza) clone();
        }

        @Override // com.google.android.gms.internal.gtm.zzsm
        public final /* synthetic */ zzsk zzpi() {
            return this.zzban;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.internal.gtm.zzpm
        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zzaVar = (zza) this.zzban.zza(zze.zzbav, null, null);
            zzaVar.zza((zza) zzpl());
            return zzaVar;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (((zzrc) zza(zze.zzbaw, (Object) null, (Object) null)).getClass().isInstance(obj)) {
            return zzsw.zzqs().zzaf(this).equals(this, (zzrc) obj);
        }
        return false;
    }

    protected final void zzmi() {
        zzsw.zzqs().zzaf(this).zzt(this);
    }

    @Override // com.google.android.gms.internal.gtm.zzsm
    public final boolean isInitialized() {
        return zza(this, Boolean.TRUE.booleanValue());
    }

    public final BuilderType zzpd() {
        BuilderType buildertype = (BuilderType) zza(zze.zzbav, (Object) null, (Object) null);
        buildertype.zza(this);
        return buildertype;
    }

    @Override // com.google.android.gms.internal.gtm.zzpl
    final int zzmw() {
        return this.zzbal;
    }

    @Override // com.google.android.gms.internal.gtm.zzpl
    final void zzag(int i) {
        this.zzbal = i;
    }

    @Override // com.google.android.gms.internal.gtm.zzsk
    public final void zzb(zzqj zzqjVar) throws IOException {
        zzsw.zzqs().zzi(getClass()).zza(this, zzql.zza(zzqjVar));
    }

    @Override // com.google.android.gms.internal.gtm.zzsk
    public final int zzpe() {
        if (this.zzbal == -1) {
            this.zzbal = zzsw.zzqs().zzaf(this).zzad(this);
        }
        return this.zzbal;
    }

    static <T extends zzrc<?, ?>> T zzg(Class<T> cls) throws ClassNotFoundException {
        zzrc<?, ?> zzrcVar = zzbam.get(cls);
        if (zzrcVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzrcVar = zzbam.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzrcVar == null) {
            zzrcVar = (T) ((zzrc) zztx.zzk(cls)).zza(zze.zzbaw, (Object) null, (Object) null);
            if (zzrcVar == null) {
                throw new IllegalStateException();
            }
            zzbam.put(cls, zzrcVar);
        }
        return (T) zzrcVar;
    }

    protected static <T extends zzrc<?, ?>> void zza(Class<T> cls, T t) {
        zzbam.put(cls, t);
    }

    protected static Object zza(zzsk zzskVar, String str, Object[] objArr) {
        return new zzsx(zzskVar, str, objArr);
    }

    static Object zza(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    protected static final <T extends zzrc<T, ?>> boolean zza(T t, boolean z) {
        byte bByteValue = ((Byte) t.zza(zze.zzbar, null, null)).byteValue();
        if (bByteValue == 1) {
            return true;
        }
        if (bByteValue == 0) {
            return false;
        }
        boolean zZzae = zzsw.zzqs().zzaf(t).zzae(t);
        if (z) {
            t.zza(zze.zzbas, zZzae ? t : null, null);
        }
        return zZzae;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.gtm.zzrd, com.google.android.gms.internal.gtm.zzri] */
    protected static zzri zzpf() {
        return zzrd.zzpo();
    }

    static <T extends zzrc<T, ?>> T zza(T t, zzqe zzqeVar, zzqp zzqpVar) throws zzrk {
        T t2 = (T) t.zza(zze.zzbau, null, null);
        try {
            zzsw.zzqs().zzaf(t2).zza(t2, zzqh.zza(zzqeVar), zzqpVar);
            t2.zzmi();
            return t2;
        } catch (IOException e) {
            if (e.getCause() instanceof zzrk) {
                throw ((zzrk) e.getCause());
            }
            throw new zzrk(e.getMessage()).zzg(t2);
        } catch (RuntimeException e2) {
            if (e2.getCause() instanceof zzrk) {
                throw ((zzrk) e2.getCause());
            }
            throw e2;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzsk
    public final /* synthetic */ zzsl zzpg() {
        zza zzaVar = (zza) zza(zze.zzbav, (Object) null, (Object) null);
        zzaVar.zza((zza) this);
        return zzaVar;
    }

    @Override // com.google.android.gms.internal.gtm.zzsk
    public final /* synthetic */ zzsl zzph() {
        return (zza) zza(zze.zzbav, (Object) null, (Object) null);
    }

    @Override // com.google.android.gms.internal.gtm.zzsm
    public final /* synthetic */ zzsk zzpi() {
        return (zzrc) zza(zze.zzbaw, (Object) null, (Object) null);
    }
}
