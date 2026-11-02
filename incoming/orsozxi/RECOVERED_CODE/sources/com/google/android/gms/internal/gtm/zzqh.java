package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
final class zzqh implements zzsy {
    private int tag;
    private final zzqe zzawp;
    private int zzawq;
    private int zzawr = 0;

    public static zzqh zza(zzqe zzqeVar) {
        return zzqeVar.zzawi != null ? zzqeVar.zzawi : new zzqh(zzqeVar);
    }

    private zzqh(zzqe zzqeVar) {
        zzqe zzqeVar2 = (zzqe) zzre.zza(zzqeVar, "input");
        this.zzawp = zzqeVar2;
        zzqeVar2.zzawi = this;
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final int zzog() throws IOException {
        int i = this.zzawr;
        if (i != 0) {
            this.tag = i;
            this.zzawr = 0;
        } else {
            this.tag = this.zzawp.zzni();
        }
        int i2 = this.tag;
        if (i2 == 0 || i2 == this.zzawq) {
            return Integer.MAX_VALUE;
        }
        return i2 >>> 3;
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final int getTag() {
        return this.tag;
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final boolean zzoh() throws IOException {
        int i;
        if (this.zzawp.zzny() || (i = this.tag) == this.zzawq) {
            return false;
        }
        return this.zzawp.zzao(i);
    }

    private final void zzat(int i) throws IOException {
        if ((this.tag & 7) != i) {
            throw zzrk.zzpt();
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final double readDouble() throws IOException {
        zzat(1);
        return this.zzawp.readDouble();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final float readFloat() throws IOException {
        zzat(5);
        return this.zzawp.readFloat();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final long zznj() throws IOException {
        zzat(0);
        return this.zzawp.zznj();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final long zznk() throws IOException {
        zzat(0);
        return this.zzawp.zznk();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final int zznl() throws IOException {
        zzat(0);
        return this.zzawp.zznl();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final long zznm() throws IOException {
        zzat(1);
        return this.zzawp.zznm();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final int zznn() throws IOException {
        zzat(5);
        return this.zzawp.zznn();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final boolean zzno() throws IOException {
        zzat(0);
        return this.zzawp.zzno();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final String readString() throws IOException {
        zzat(2);
        return this.zzawp.readString();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final String zznp() throws IOException {
        zzat(2);
        return this.zzawp.zznp();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final <T> T zza(zzsz<T> zzszVar, zzqp zzqpVar) throws IOException {
        zzat(2);
        return (T) zzc(zzszVar, zzqpVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final <T> T zzb(zzsz<T> zzszVar, zzqp zzqpVar) throws IOException {
        zzat(3);
        return (T) zzd(zzszVar, zzqpVar);
    }

    private final <T> T zzc(zzsz<T> zzszVar, zzqp zzqpVar) throws IOException {
        int iZznr = this.zzawp.zznr();
        if (this.zzawp.zzawf >= this.zzawp.zzawg) {
            throw zzrk.zzpu();
        }
        int iZzaq = this.zzawp.zzaq(iZznr);
        T tNewInstance = zzszVar.newInstance();
        this.zzawp.zzawf++;
        zzszVar.zza(tNewInstance, this, zzqpVar);
        zzszVar.zzt(tNewInstance);
        this.zzawp.zzan(0);
        zzqe zzqeVar = this.zzawp;
        zzqeVar.zzawf--;
        this.zzawp.zzar(iZzaq);
        return tNewInstance;
    }

    private final <T> T zzd(zzsz<T> zzszVar, zzqp zzqpVar) throws IOException {
        int i = this.zzawq;
        this.zzawq = ((this.tag >>> 3) << 3) | 4;
        try {
            T tNewInstance = zzszVar.newInstance();
            zzszVar.zza(tNewInstance, this, zzqpVar);
            zzszVar.zzt(tNewInstance);
            if (this.tag == this.zzawq) {
                return tNewInstance;
            }
            throw zzrk.zzpv();
        } finally {
            this.zzawq = i;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final zzps zznq() throws IOException {
        zzat(2);
        return this.zzawp.zznq();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final int zznr() throws IOException {
        zzat(0);
        return this.zzawp.zznr();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final int zzns() throws IOException {
        zzat(0);
        return this.zzawp.zzns();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final int zznt() throws IOException {
        zzat(5);
        return this.zzawp.zznt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final long zznu() throws IOException {
        zzat(1);
        return this.zzawp.zznu();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final int zznv() throws IOException {
        zzat(0);
        return this.zzawp.zznv();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final long zznw() throws IOException {
        zzat(0);
        return this.zzawp.zznw();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzg(List<Double> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzqm) {
            zzqm zzqmVar = (zzqm) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzqmVar.zzd(this.zzawp.readDouble());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            if (i == 2) {
                int iZznr = this.zzawp.zznr();
                zzau(iZznr);
                int iZznz = this.zzawp.zznz() + iZznr;
                do {
                    zzqmVar.zzd(this.zzawp.readDouble());
                } while (this.zzawp.zznz() < iZznz);
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 1) {
            do {
                list.add(Double.valueOf(this.zzawp.readDouble()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        if (i2 == 2) {
            int iZznr2 = this.zzawp.zznr();
            zzau(iZznr2);
            int iZznz2 = this.zzawp.zznz() + iZznr2;
            do {
                list.add(Double.valueOf(this.zzawp.readDouble()));
            } while (this.zzawp.zznz() < iZznz2);
            return;
        }
        throw zzrk.zzpt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzh(List<Float> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzqz) {
            zzqz zzqzVar = (zzqz) list;
            int i = this.tag & 7;
            if (i == 2) {
                int iZznr = this.zzawp.zznr();
                zzav(iZznr);
                int iZznz = this.zzawp.zznz() + iZznr;
                do {
                    zzqzVar.zzc(this.zzawp.readFloat());
                } while (this.zzawp.zznz() < iZznz);
                return;
            }
            if (i == 5) {
                do {
                    zzqzVar.zzc(this.zzawp.readFloat());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 2) {
            int iZznr2 = this.zzawp.zznr();
            zzav(iZznr2);
            int iZznz2 = this.zzawp.zznz() + iZznr2;
            do {
                list.add(Float.valueOf(this.zzawp.readFloat()));
            } while (this.zzawp.zznz() < iZznz2);
            return;
        }
        if (i2 == 5) {
            do {
                list.add(Float.valueOf(this.zzawp.readFloat()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        throw zzrk.zzpt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzi(List<Long> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzry) {
            zzry zzryVar = (zzry) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzryVar.zzaa(this.zzawp.zznj());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            if (i == 2) {
                int iZznz = this.zzawp.zznz() + this.zzawp.zznr();
                do {
                    zzryVar.zzaa(this.zzawp.zznj());
                } while (this.zzawp.zznz() < iZznz);
                zzaw(iZznz);
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Long.valueOf(this.zzawp.zznj()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        if (i2 == 2) {
            int iZznz2 = this.zzawp.zznz() + this.zzawp.zznr();
            do {
                list.add(Long.valueOf(this.zzawp.zznj()));
            } while (this.zzawp.zznz() < iZznz2);
            zzaw(iZznz2);
            return;
        }
        throw zzrk.zzpt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzj(List<Long> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzry) {
            zzry zzryVar = (zzry) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzryVar.zzaa(this.zzawp.zznk());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            if (i == 2) {
                int iZznz = this.zzawp.zznz() + this.zzawp.zznr();
                do {
                    zzryVar.zzaa(this.zzawp.zznk());
                } while (this.zzawp.zznz() < iZznz);
                zzaw(iZznz);
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Long.valueOf(this.zzawp.zznk()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        if (i2 == 2) {
            int iZznz2 = this.zzawp.zznz() + this.zzawp.zznr();
            do {
                list.add(Long.valueOf(this.zzawp.zznk()));
            } while (this.zzawp.zznz() < iZznz2);
            zzaw(iZznz2);
            return;
        }
        throw zzrk.zzpt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzk(List<Integer> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzrd) {
            zzrd zzrdVar = (zzrd) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzrdVar.zzbm(this.zzawp.zznl());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            if (i == 2) {
                int iZznz = this.zzawp.zznz() + this.zzawp.zznr();
                do {
                    zzrdVar.zzbm(this.zzawp.zznl());
                } while (this.zzawp.zznz() < iZznz);
                zzaw(iZznz);
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Integer.valueOf(this.zzawp.zznl()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        if (i2 == 2) {
            int iZznz2 = this.zzawp.zznz() + this.zzawp.zznr();
            do {
                list.add(Integer.valueOf(this.zzawp.zznl()));
            } while (this.zzawp.zznz() < iZznz2);
            zzaw(iZznz2);
            return;
        }
        throw zzrk.zzpt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzl(List<Long> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzry) {
            zzry zzryVar = (zzry) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzryVar.zzaa(this.zzawp.zznm());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            if (i == 2) {
                int iZznr = this.zzawp.zznr();
                zzau(iZznr);
                int iZznz = this.zzawp.zznz() + iZznr;
                do {
                    zzryVar.zzaa(this.zzawp.zznm());
                } while (this.zzawp.zznz() < iZznz);
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 1) {
            do {
                list.add(Long.valueOf(this.zzawp.zznm()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        if (i2 == 2) {
            int iZznr2 = this.zzawp.zznr();
            zzau(iZznr2);
            int iZznz2 = this.zzawp.zznz() + iZznr2;
            do {
                list.add(Long.valueOf(this.zzawp.zznm()));
            } while (this.zzawp.zznz() < iZznz2);
            return;
        }
        throw zzrk.zzpt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzm(List<Integer> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzrd) {
            zzrd zzrdVar = (zzrd) list;
            int i = this.tag & 7;
            if (i == 2) {
                int iZznr = this.zzawp.zznr();
                zzav(iZznr);
                int iZznz = this.zzawp.zznz() + iZznr;
                do {
                    zzrdVar.zzbm(this.zzawp.zznn());
                } while (this.zzawp.zznz() < iZznz);
                return;
            }
            if (i == 5) {
                do {
                    zzrdVar.zzbm(this.zzawp.zznn());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 2) {
            int iZznr2 = this.zzawp.zznr();
            zzav(iZznr2);
            int iZznz2 = this.zzawp.zznz() + iZznr2;
            do {
                list.add(Integer.valueOf(this.zzawp.zznn()));
            } while (this.zzawp.zznz() < iZznz2);
            return;
        }
        if (i2 == 5) {
            do {
                list.add(Integer.valueOf(this.zzawp.zznn()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        throw zzrk.zzpt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzn(List<Boolean> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzpq) {
            zzpq zzpqVar = (zzpq) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzpqVar.addBoolean(this.zzawp.zzno());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            if (i == 2) {
                int iZznz = this.zzawp.zznz() + this.zzawp.zznr();
                do {
                    zzpqVar.addBoolean(this.zzawp.zzno());
                } while (this.zzawp.zznz() < iZznz);
                zzaw(iZznz);
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Boolean.valueOf(this.zzawp.zzno()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        if (i2 == 2) {
            int iZznz2 = this.zzawp.zznz() + this.zzawp.zznr();
            do {
                list.add(Boolean.valueOf(this.zzawp.zzno()));
            } while (this.zzawp.zznz() < iZznz2);
            zzaw(iZznz2);
            return;
        }
        throw zzrk.zzpt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void readStringList(List<String> list) throws IOException {
        zza(list, false);
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzo(List<String> list) throws IOException {
        zza(list, true);
    }

    private final void zza(List<String> list, boolean z) throws IOException {
        int iZzni;
        int iZzni2;
        if ((this.tag & 7) != 2) {
            throw zzrk.zzpt();
        }
        if ((list instanceof zzrt) && !z) {
            zzrt zzrtVar = (zzrt) list;
            do {
                zzrtVar.zzc(zznq());
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni2 = this.zzawp.zzni();
                }
            } while (iZzni2 == this.tag);
            this.zzawr = iZzni2;
            return;
        }
        do {
            list.add(z ? zznp() : readString());
            if (this.zzawp.zzny()) {
                return;
            } else {
                iZzni = this.zzawp.zzni();
            }
        } while (iZzni == this.tag);
        this.zzawr = iZzni;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.gtm.zzsy
    public final <T> void zza(List<T> list, zzsz<T> zzszVar, zzqp zzqpVar) throws IOException {
        int iZzni;
        int i = this.tag;
        if ((i & 7) != 2) {
            throw zzrk.zzpt();
        }
        do {
            list.add(zzc(zzszVar, zzqpVar));
            if (this.zzawp.zzny() || this.zzawr != 0) {
                return;
            } else {
                iZzni = this.zzawp.zzni();
            }
        } while (iZzni == i);
        this.zzawr = iZzni;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.gtm.zzsy
    public final <T> void zzb(List<T> list, zzsz<T> zzszVar, zzqp zzqpVar) throws IOException {
        int iZzni;
        int i = this.tag;
        if ((i & 7) != 3) {
            throw zzrk.zzpt();
        }
        do {
            list.add(zzd(zzszVar, zzqpVar));
            if (this.zzawp.zzny() || this.zzawr != 0) {
                return;
            } else {
                iZzni = this.zzawp.zzni();
            }
        } while (iZzni == i);
        this.zzawr = iZzni;
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzp(List<zzps> list) throws IOException {
        int iZzni;
        if ((this.tag & 7) != 2) {
            throw zzrk.zzpt();
        }
        do {
            list.add(zznq());
            if (this.zzawp.zzny()) {
                return;
            } else {
                iZzni = this.zzawp.zzni();
            }
        } while (iZzni == this.tag);
        this.zzawr = iZzni;
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzq(List<Integer> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzrd) {
            zzrd zzrdVar = (zzrd) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzrdVar.zzbm(this.zzawp.zznr());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            if (i == 2) {
                int iZznz = this.zzawp.zznz() + this.zzawp.zznr();
                do {
                    zzrdVar.zzbm(this.zzawp.zznr());
                } while (this.zzawp.zznz() < iZznz);
                zzaw(iZznz);
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Integer.valueOf(this.zzawp.zznr()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        if (i2 == 2) {
            int iZznz2 = this.zzawp.zznz() + this.zzawp.zznr();
            do {
                list.add(Integer.valueOf(this.zzawp.zznr()));
            } while (this.zzawp.zznz() < iZznz2);
            zzaw(iZznz2);
            return;
        }
        throw zzrk.zzpt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzr(List<Integer> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzrd) {
            zzrd zzrdVar = (zzrd) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzrdVar.zzbm(this.zzawp.zzns());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            if (i == 2) {
                int iZznz = this.zzawp.zznz() + this.zzawp.zznr();
                do {
                    zzrdVar.zzbm(this.zzawp.zzns());
                } while (this.zzawp.zznz() < iZznz);
                zzaw(iZznz);
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Integer.valueOf(this.zzawp.zzns()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        if (i2 == 2) {
            int iZznz2 = this.zzawp.zznz() + this.zzawp.zznr();
            do {
                list.add(Integer.valueOf(this.zzawp.zzns()));
            } while (this.zzawp.zznz() < iZznz2);
            zzaw(iZznz2);
            return;
        }
        throw zzrk.zzpt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzs(List<Integer> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzrd) {
            zzrd zzrdVar = (zzrd) list;
            int i = this.tag & 7;
            if (i == 2) {
                int iZznr = this.zzawp.zznr();
                zzav(iZznr);
                int iZznz = this.zzawp.zznz() + iZznr;
                do {
                    zzrdVar.zzbm(this.zzawp.zznt());
                } while (this.zzawp.zznz() < iZznz);
                return;
            }
            if (i == 5) {
                do {
                    zzrdVar.zzbm(this.zzawp.zznt());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 2) {
            int iZznr2 = this.zzawp.zznr();
            zzav(iZznr2);
            int iZznz2 = this.zzawp.zznz() + iZznr2;
            do {
                list.add(Integer.valueOf(this.zzawp.zznt()));
            } while (this.zzawp.zznz() < iZznz2);
            return;
        }
        if (i2 == 5) {
            do {
                list.add(Integer.valueOf(this.zzawp.zznt()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        throw zzrk.zzpt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzt(List<Long> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzry) {
            zzry zzryVar = (zzry) list;
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    zzryVar.zzaa(this.zzawp.zznu());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            if (i == 2) {
                int iZznr = this.zzawp.zznr();
                zzau(iZznr);
                int iZznz = this.zzawp.zznz() + iZznr;
                do {
                    zzryVar.zzaa(this.zzawp.zznu());
                } while (this.zzawp.zznz() < iZznz);
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 1) {
            do {
                list.add(Long.valueOf(this.zzawp.zznu()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        if (i2 == 2) {
            int iZznr2 = this.zzawp.zznr();
            zzau(iZznr2);
            int iZznz2 = this.zzawp.zznz() + iZznr2;
            do {
                list.add(Long.valueOf(this.zzawp.zznu()));
            } while (this.zzawp.zznz() < iZznz2);
            return;
        }
        throw zzrk.zzpt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzu(List<Integer> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzrd) {
            zzrd zzrdVar = (zzrd) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzrdVar.zzbm(this.zzawp.zznv());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            if (i == 2) {
                int iZznz = this.zzawp.zznz() + this.zzawp.zznr();
                do {
                    zzrdVar.zzbm(this.zzawp.zznv());
                } while (this.zzawp.zznz() < iZznz);
                zzaw(iZznz);
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Integer.valueOf(this.zzawp.zznv()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        if (i2 == 2) {
            int iZznz2 = this.zzawp.zznz() + this.zzawp.zznr();
            do {
                list.add(Integer.valueOf(this.zzawp.zznv()));
            } while (this.zzawp.zznz() < iZznz2);
            zzaw(iZznz2);
            return;
        }
        throw zzrk.zzpt();
    }

    @Override // com.google.android.gms.internal.gtm.zzsy
    public final void zzv(List<Long> list) throws IOException {
        int iZzni;
        int iZzni2;
        if (list instanceof zzry) {
            zzry zzryVar = (zzry) list;
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    zzryVar.zzaa(this.zzawp.zznw());
                    if (this.zzawp.zzny()) {
                        return;
                    } else {
                        iZzni2 = this.zzawp.zzni();
                    }
                } while (iZzni2 == this.tag);
                this.zzawr = iZzni2;
                return;
            }
            if (i == 2) {
                int iZznz = this.zzawp.zznz() + this.zzawp.zznr();
                do {
                    zzryVar.zzaa(this.zzawp.zznw());
                } while (this.zzawp.zznz() < iZznz);
                zzaw(iZznz);
                return;
            }
            throw zzrk.zzpt();
        }
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                list.add(Long.valueOf(this.zzawp.zznw()));
                if (this.zzawp.zzny()) {
                    return;
                } else {
                    iZzni = this.zzawp.zzni();
                }
            } while (iZzni == this.tag);
            this.zzawr = iZzni;
            return;
        }
        if (i2 == 2) {
            int iZznz2 = this.zzawp.zznz() + this.zzawp.zznr();
            do {
                list.add(Long.valueOf(this.zzawp.zznw()));
            } while (this.zzawp.zznz() < iZznz2);
            zzaw(iZznz2);
            return;
        }
        throw zzrk.zzpt();
    }

    private static void zzau(int i) throws IOException {
        if ((i & 7) != 0) {
            throw zzrk.zzpv();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x005b, code lost:
    
        r8.put(r2, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0063, code lost:
    
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.gtm.zzsy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final <K, V> void zza(java.util.Map<K, V> r8, com.google.android.gms.internal.gtm.zzsd<K, V> r9, com.google.android.gms.internal.gtm.zzqp r10) throws java.io.IOException {
        /*
            r7 = this;
            r0 = 2
            r7.zzat(r0)
            com.google.android.gms.internal.gtm.zzqe r1 = r7.zzawp
            int r1 = r1.zznr()
            com.google.android.gms.internal.gtm.zzqe r2 = r7.zzawp
            int r1 = r2.zzaq(r1)
            K r2 = r9.zzbcq
            V r3 = r9.zzbcs
        L14:
            int r4 = r7.zzog()     // Catch: java.lang.Throwable -> L64
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 == r5) goto L5b
            com.google.android.gms.internal.gtm.zzqe r5 = r7.zzawp     // Catch: java.lang.Throwable -> L64
            boolean r5 = r5.zzny()     // Catch: java.lang.Throwable -> L64
            if (r5 != 0) goto L5b
            r5 = 1
            java.lang.String r6 = "Unable to parse map entry."
            if (r4 == r5) goto L46
            if (r4 == r0) goto L39
            boolean r4 = r7.zzoh()     // Catch: com.google.android.gms.internal.gtm.zzrl -> L4e java.lang.Throwable -> L64
            if (r4 == 0) goto L33
            goto L14
        L33:
            com.google.android.gms.internal.gtm.zzrk r4 = new com.google.android.gms.internal.gtm.zzrk     // Catch: com.google.android.gms.internal.gtm.zzrl -> L4e java.lang.Throwable -> L64
            r4.<init>(r6)     // Catch: com.google.android.gms.internal.gtm.zzrl -> L4e java.lang.Throwable -> L64
            throw r4     // Catch: com.google.android.gms.internal.gtm.zzrl -> L4e java.lang.Throwable -> L64
        L39:
            com.google.android.gms.internal.gtm.zzug r4 = r9.zzbcr     // Catch: com.google.android.gms.internal.gtm.zzrl -> L4e java.lang.Throwable -> L64
            V r5 = r9.zzbcs     // Catch: com.google.android.gms.internal.gtm.zzrl -> L4e java.lang.Throwable -> L64
            java.lang.Class r5 = r5.getClass()     // Catch: com.google.android.gms.internal.gtm.zzrl -> L4e java.lang.Throwable -> L64
            java.lang.Object r3 = r7.zza(r4, r5, r10)     // Catch: com.google.android.gms.internal.gtm.zzrl -> L4e java.lang.Throwable -> L64
            goto L14
        L46:
            com.google.android.gms.internal.gtm.zzug r4 = r9.zzbcp     // Catch: com.google.android.gms.internal.gtm.zzrl -> L4e java.lang.Throwable -> L64
            r5 = 0
            java.lang.Object r2 = r7.zza(r4, r5, r5)     // Catch: com.google.android.gms.internal.gtm.zzrl -> L4e java.lang.Throwable -> L64
            goto L14
        L4e:
            boolean r4 = r7.zzoh()     // Catch: java.lang.Throwable -> L64
            if (r4 == 0) goto L55
            goto L14
        L55:
            com.google.android.gms.internal.gtm.zzrk r8 = new com.google.android.gms.internal.gtm.zzrk     // Catch: java.lang.Throwable -> L64
            r8.<init>(r6)     // Catch: java.lang.Throwable -> L64
            throw r8     // Catch: java.lang.Throwable -> L64
        L5b:
            r8.put(r2, r3)     // Catch: java.lang.Throwable -> L64
            com.google.android.gms.internal.gtm.zzqe r8 = r7.zzawp
            r8.zzar(r1)
            return
        L64:
            r8 = move-exception
            com.google.android.gms.internal.gtm.zzqe r9 = r7.zzawp
            r9.zzar(r1)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzqh.zza(java.util.Map, com.google.android.gms.internal.gtm.zzsd, com.google.android.gms.internal.gtm.zzqp):void");
    }

    private final Object zza(zzug zzugVar, Class<?> cls, zzqp zzqpVar) throws IOException {
        switch (zzqi.zzaws[zzugVar.ordinal()]) {
            case 1:
                return Boolean.valueOf(zzno());
            case 2:
                return zznq();
            case 3:
                return Double.valueOf(readDouble());
            case 4:
                return Integer.valueOf(zzns());
            case 5:
                return Integer.valueOf(zznn());
            case 6:
                return Long.valueOf(zznm());
            case 7:
                return Float.valueOf(readFloat());
            case 8:
                return Integer.valueOf(zznl());
            case 9:
                return Long.valueOf(zznk());
            case 10:
                zzat(2);
                return zzc(zzsw.zzqs().zzi(cls), zzqpVar);
            case 11:
                return Integer.valueOf(zznt());
            case 12:
                return Long.valueOf(zznu());
            case 13:
                return Integer.valueOf(zznv());
            case 14:
                return Long.valueOf(zznw());
            case 15:
                return zznp();
            case 16:
                return Integer.valueOf(zznr());
            case 17:
                return Long.valueOf(zznj());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private static void zzav(int i) throws IOException {
        if ((i & 3) != 0) {
            throw zzrk.zzpv();
        }
    }

    private final void zzaw(int i) throws IOException {
        if (this.zzawp.zznz() != i) {
            throw zzrk.zzpp();
        }
    }
}
