package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzrc;
import java.util.List;

/* loaded from: classes.dex */
public final class zzc {

    public static final class zza extends zzrc<zza, C0028zza> implements zzsm {
        private static final zza zznv;
        private static volatile zzsu<zza> zznw;
        private int zznr;
        private int zzns = 1;
        private int zznt;
        private int zznu;

        public enum zzb implements zzrf {
            NO_CACHE(1),
            PRIVATE(2),
            PUBLIC(3);

            private static final zzrg<zzb> zzoa = new com.google.android.gms.internal.gtm.zze();
            private final int value;

            @Override // com.google.android.gms.internal.gtm.zzrf
            public final int zzc() {
                return this.value;
            }

            public static zzb zza(int i) {
                if (i == 1) {
                    return NO_CACHE;
                }
                if (i == 2) {
                    return PRIVATE;
                }
                if (i != 3) {
                    return null;
                }
                return PUBLIC;
            }

            public static zzrh zzd() {
                return zzf.zzoc;
            }

            zzb(int i) {
                this.value = i;
            }
        }

        private zza() {
        }

        /* renamed from: com.google.android.gms.internal.gtm.zzc$zza$zza, reason: collision with other inner class name */
        public static final class C0028zza extends zzrc.zza<zza, C0028zza> implements zzsm {
            private C0028zza() {
                super(zza.zznv);
            }

            /* synthetic */ C0028zza(com.google.android.gms.internal.gtm.zzd zzdVar) {
                this();
            }
        }

        @Override // com.google.android.gms.internal.gtm.zzrc
        protected final Object zza(int i, Object obj, Object obj2) {
            com.google.android.gms.internal.gtm.zzd zzdVar = null;
            switch (com.google.android.gms.internal.gtm.zzd.zznq[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0028zza(zzdVar);
                case 3:
                    return zza(zznv, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\f\u0000\u0002\u0004\u0001\u0003\u0004\u0002", new Object[]{"zznr", "zzns", zzb.zzd(), "zznt", "zznu"});
                case 4:
                    return zznv;
                case 5:
                    zzsu<zza> zzbVar = zznw;
                    if (zzbVar == null) {
                        synchronized (zza.class) {
                            zzbVar = zznw;
                            if (zzbVar == null) {
                                zzbVar = new zzrc.zzb<>(zznv);
                                zznw = zzbVar;
                            }
                        }
                    }
                    return zzbVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzsu<zza> zza() {
            return (zzsu) zznv.zza(zzrc.zze.zzbax, (Object) null, (Object) null);
        }

        static {
            zza zzaVar = new zza();
            zznv = zzaVar;
            zzrc.zza((Class<zza>) zza.class, zzaVar);
        }
    }

    public static final class zzb extends zzrc<zzb, zza> implements zzsm {
        private static volatile zzsu<zzb> zznw;
        private static final zzb zzoj;
        private int zznr;
        private int zzoe;
        private int zzof;
        private boolean zzog;
        private boolean zzoh;
        private byte zzoi = 2;
        private zzri zzod = zzpf();

        private zzb() {
        }

        public static final class zza extends zzrc.zza<zzb, zza> implements zzsm {
            private zza() {
                super(zzb.zzoj);
            }

            /* synthetic */ zza(com.google.android.gms.internal.gtm.zzd zzdVar) {
                this();
            }
        }

        public final List<Integer> zze() {
            return this.zzod;
        }

        @Override // com.google.android.gms.internal.gtm.zzrc
        protected final Object zza(int i, Object obj, Object obj2) {
            com.google.android.gms.internal.gtm.zzd zzdVar = null;
            switch (com.google.android.gms.internal.gtm.zzd.zznq[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzdVar);
                case 3:
                    return zza(zzoj, "\u0001\u0005\u0000\u0001\u0001\u0006\u0005\u0000\u0001\u0001\u0001\u0007\u0003\u0002Ԅ\u0000\u0003\u0016\u0004\u0004\u0001\u0006\u0007\u0002", new Object[]{"zznr", "zzoh", "zzoe", "zzod", "zzof", "zzog"});
                case 4:
                    return zzoj;
                case 5:
                    zzsu<zzb> zzbVar = zznw;
                    if (zzbVar == null) {
                        synchronized (zzb.class) {
                            zzbVar = zznw;
                            if (zzbVar == null) {
                                zzbVar = new zzrc.zzb<>(zzoj);
                                zznw = zzbVar;
                            }
                        }
                    }
                    return zzbVar;
                case 6:
                    return Byte.valueOf(this.zzoi);
                case 7:
                    this.zzoi = (byte) (obj == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzsu<zzb> zza() {
            return (zzsu) zzoj.zza(zzrc.zze.zzbax, (Object) null, (Object) null);
        }

        static {
            zzb zzbVar = new zzb();
            zzoj = zzbVar;
            zzrc.zza((Class<zzb>) zzb.class, zzbVar);
        }
    }

    /* renamed from: com.google.android.gms.internal.gtm.zzc$zzc, reason: collision with other inner class name */
    public static final class C0029zzc extends zzrc<C0029zzc, zza> implements zzsm {
        private static volatile zzsu<C0029zzc> zznw;
        private static final C0029zzc zzop;
        private int zznr;
        private long zzol;
        private boolean zzon;
        private long zzoo;
        private String zzok = "";
        private long zzom = 2147483647L;

        private C0029zzc() {
        }

        /* renamed from: com.google.android.gms.internal.gtm.zzc$zzc$zza */
        public static final class zza extends zzrc.zza<C0029zzc, zza> implements zzsm {
            private zza() {
                super(C0029zzc.zzop);
            }

            /* synthetic */ zza(com.google.android.gms.internal.gtm.zzd zzdVar) {
                this();
            }
        }

        public final boolean hasKey() {
            return (this.zznr & 1) != 0;
        }

        public final String getKey() {
            return this.zzok;
        }

        public final long zzg() {
            return this.zzol;
        }

        public final long zzh() {
            return this.zzom;
        }

        public final boolean zzi() {
            return this.zzon;
        }

        public final long zzj() {
            return this.zzoo;
        }

        @Override // com.google.android.gms.internal.gtm.zzrc
        protected final Object zza(int i, Object obj, Object obj2) {
            com.google.android.gms.internal.gtm.zzd zzdVar = null;
            switch (com.google.android.gms.internal.gtm.zzd.zznq[i - 1]) {
                case 1:
                    return new C0029zzc();
                case 2:
                    return new zza(zzdVar);
                case 3:
                    return zza(zzop, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001\b\u0000\u0002\u0002\u0001\u0003\u0002\u0002\u0004\u0007\u0003\u0005\u0002\u0004", new Object[]{"zznr", "zzok", "zzol", "zzom", "zzon", "zzoo"});
                case 4:
                    return zzop;
                case 5:
                    zzsu<C0029zzc> zzbVar = zznw;
                    if (zzbVar == null) {
                        synchronized (C0029zzc.class) {
                            zzbVar = zznw;
                            if (zzbVar == null) {
                                zzbVar = new zzrc.zzb<>(zzop);
                                zznw = zzbVar;
                            }
                        }
                    }
                    return zzbVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzsu<C0029zzc> zza() {
            return (zzsu) zzop.zza(zzrc.zze.zzbax, (Object) null, (Object) null);
        }

        static {
            C0029zzc c0029zzc = new C0029zzc();
            zzop = c0029zzc;
            zzrc.zza((Class<C0029zzc>) C0029zzc.class, c0029zzc);
        }
    }

    public static final class zzd extends zzrc<zzd, zza> implements zzsm {
        private static volatile zzsu<zzd> zznw;
        private static final zzd zzos;
        private int zznr;
        private byte zzoi = 2;
        private int zzoq;
        private int zzor;

        private zzd() {
        }

        public static final class zza extends zzrc.zza<zzd, zza> implements zzsm {
            private zza() {
                super(zzd.zzos);
            }

            /* synthetic */ zza(com.google.android.gms.internal.gtm.zzd zzdVar) {
                this();
            }
        }

        public final int zzl() {
            return this.zzoq;
        }

        public final int getValue() {
            return this.zzor;
        }

        @Override // com.google.android.gms.internal.gtm.zzrc
        protected final Object zza(int i, Object obj, Object obj2) {
            com.google.android.gms.internal.gtm.zzd zzdVar = null;
            switch (com.google.android.gms.internal.gtm.zzd.zznq[i - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza(zzdVar);
                case 3:
                    return zza(zzos, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0002\u0001Ԅ\u0000\u0002Ԅ\u0001", new Object[]{"zznr", "zzoq", "zzor"});
                case 4:
                    return zzos;
                case 5:
                    zzsu<zzd> zzbVar = zznw;
                    if (zzbVar == null) {
                        synchronized (zzd.class) {
                            zzbVar = zznw;
                            if (zzbVar == null) {
                                zzbVar = new zzrc.zzb<>(zzos);
                                zznw = zzbVar;
                            }
                        }
                    }
                    return zzbVar;
                case 6:
                    return Byte.valueOf(this.zzoi);
                case 7:
                    this.zzoi = (byte) (obj == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzsu<zzd> zza() {
            return (zzsu) zzos.zza(zzrc.zze.zzbax, (Object) null, (Object) null);
        }

        static {
            zzd zzdVar = new zzd();
            zzos = zzdVar;
            zzrc.zza((Class<zzd>) zzd.class, zzdVar);
        }
    }

    public static final class zze extends zzrc<zze, zza> implements zzsm {
        private static volatile zzsu<zze> zznw;
        private static final zze zzpd;
        private zzri zzot = zzpf();
        private zzri zzou = zzpf();
        private zzri zzov = zzpf();
        private zzri zzow = zzpf();
        private zzri zzox = zzpf();
        private zzri zzoy = zzpf();
        private zzri zzoz = zzpf();
        private zzri zzpa = zzpf();
        private zzri zzpb = zzpf();
        private zzri zzpc = zzpf();

        private zze() {
        }

        public static final class zza extends zzrc.zza<zze, zza> implements zzsm {
            private zza() {
                super(zze.zzpd);
            }

            /* synthetic */ zza(com.google.android.gms.internal.gtm.zzd zzdVar) {
                this();
            }
        }

        public final List<Integer> zzn() {
            return this.zzot;
        }

        public final List<Integer> zzo() {
            return this.zzou;
        }

        public final List<Integer> zzp() {
            return this.zzov;
        }

        public final List<Integer> zzq() {
            return this.zzow;
        }

        public final List<Integer> zzr() {
            return this.zzox;
        }

        public final List<Integer> zzs() {
            return this.zzoy;
        }

        public final List<Integer> zzt() {
            return this.zzoz;
        }

        public final List<Integer> zzu() {
            return this.zzpa;
        }

        public final List<Integer> zzv() {
            return this.zzpb;
        }

        public final List<Integer> zzw() {
            return this.zzpc;
        }

        @Override // com.google.android.gms.internal.gtm.zzrc
        protected final Object zza(int i, Object obj, Object obj2) {
            com.google.android.gms.internal.gtm.zzd zzdVar = null;
            switch (com.google.android.gms.internal.gtm.zzd.zznq[i - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza(zzdVar);
                case 3:
                    return zza(zzpd, "\u0001\n\u0000\u0000\u0001\n\n\u0000\n\u0000\u0001\u0016\u0002\u0016\u0003\u0016\u0004\u0016\u0005\u0016\u0006\u0016\u0007\u0016\b\u0016\t\u0016\n\u0016", new Object[]{"zzot", "zzou", "zzov", "zzow", "zzox", "zzoy", "zzoz", "zzpa", "zzpb", "zzpc"});
                case 4:
                    return zzpd;
                case 5:
                    zzsu<zze> zzbVar = zznw;
                    if (zzbVar == null) {
                        synchronized (zze.class) {
                            zzbVar = zznw;
                            if (zzbVar == null) {
                                zzbVar = new zzrc.zzb<>(zzpd);
                                zznw = zzbVar;
                            }
                        }
                    }
                    return zzbVar;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzsu<zze> zza() {
            return (zzsu) zzpd.zza(zzrc.zze.zzbax, (Object) null, (Object) null);
        }

        static {
            zze zzeVar = new zze();
            zzpd = zzeVar;
            zzrc.zza((Class<zze>) zze.class, zzeVar);
        }
    }
}
