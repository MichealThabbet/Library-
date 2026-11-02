package com.google.android.gms.internal.gtm;

import it.piapostolos.android.pinotes.R2;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
final class zzut implements Cloneable {
    private Object value;
    private zzur<?, ?> zzbhi;
    private List<zzuy> zzbhj = new ArrayList();

    zzut() {
    }

    final void zza(zzuy zzuyVar) throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Object objZzag;
        Object obj;
        List<zzuy> list = this.zzbhj;
        if (list != null) {
            list.add(zzuyVar);
            return;
        }
        Object obj2 = this.value;
        if (obj2 instanceof zzuw) {
            byte[] bArr = zzuyVar.zzawe;
            zzun zzunVarZzj = zzun.zzj(bArr, 0, bArr.length);
            int iZzoa = zzunVarZzj.zzoa();
            if (iZzoa != bArr.length - zzuo.zzbc(iZzoa)) {
                throw zzuv.zzsa();
            }
            objZzag = ((zzuw) this.value).zza(zzunVarZzj);
        } else {
            if (obj2 instanceof zzuw[]) {
                zzuw[] zzuwVarArr = (zzuw[]) this.zzbhi.zzag(Collections.singletonList(zzuyVar));
                zzuw[] zzuwVarArr2 = (zzuw[]) this.value;
                obj = (zzuw[]) Arrays.copyOf(zzuwVarArr2, zzuwVarArr2.length + zzuwVarArr.length);
                System.arraycopy(zzuwVarArr, 0, obj, zzuwVarArr2.length, zzuwVarArr.length);
            } else if (obj2 instanceof zzsk) {
                objZzag = ((zzsk) this.value).zzpg().zza((zzsk) this.zzbhi.zzag(Collections.singletonList(zzuyVar))).zzpm();
            } else if (obj2 instanceof zzsk[]) {
                zzsk[] zzskVarArr = (zzsk[]) this.zzbhi.zzag(Collections.singletonList(zzuyVar));
                zzsk[] zzskVarArr2 = (zzsk[]) this.value;
                obj = (zzsk[]) Arrays.copyOf(zzskVarArr2, zzskVarArr2.length + zzskVarArr.length);
                System.arraycopy(zzskVarArr, 0, obj, zzskVarArr2.length, zzskVarArr.length);
            } else {
                objZzag = this.zzbhi.zzag(Collections.singletonList(zzuyVar));
            }
            objZzag = obj;
        }
        this.zzbhi = this.zzbhi;
        this.value = objZzag;
        this.zzbhj = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    final <T> T zzb(zzur<?, T> zzurVar) {
        if (this.value != null) {
            if (!this.zzbhi.equals(zzurVar)) {
                throw new IllegalStateException("Tried to getExtension with a different Extension.");
            }
        } else {
            this.zzbhi = zzurVar;
            this.value = zzurVar.zzag(this.zzbhj);
            this.zzbhj = null;
        }
        return (T) this.value;
    }

    final int zzy() throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Object obj = this.value;
        if (obj != null) {
            zzur<?, ?> zzurVar = this.zzbhi;
            if (zzurVar.zzbhd) {
                int length = Array.getLength(obj);
                int iZzaj = 0;
                for (int i = 0; i < length; i++) {
                    Object obj2 = Array.get(obj, i);
                    if (obj2 != null) {
                        iZzaj += zzurVar.zzaj(obj2);
                    }
                }
                return iZzaj;
            }
            return zzurVar.zzaj(obj);
        }
        int iZzbj = 0;
        for (zzuy zzuyVar : this.zzbhj) {
            iZzbj += zzuo.zzbj(zzuyVar.tag) + 0 + zzuyVar.zzawe.length;
        }
        return iZzbj;
    }

    final void zza(zzuo zzuoVar) throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Object obj = this.value;
        if (obj != null) {
            zzur<?, ?> zzurVar = this.zzbhi;
            if (zzurVar.zzbhd) {
                int length = Array.getLength(obj);
                for (int i = 0; i < length; i++) {
                    Object obj2 = Array.get(obj, i);
                    if (obj2 != null) {
                        zzurVar.zza(obj2, zzuoVar);
                    }
                }
                return;
            }
            zzurVar.zza(obj, zzuoVar);
            return;
        }
        for (zzuy zzuyVar : this.zzbhj) {
            zzuoVar.zzcb(zzuyVar.tag);
            zzuoVar.zzm(zzuyVar.zzawe);
        }
    }

    public final boolean equals(Object obj) {
        List<zzuy> list;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzut)) {
            return false;
        }
        zzut zzutVar = (zzut) obj;
        if (this.value != null && zzutVar.value != null) {
            zzur<?, ?> zzurVar = this.zzbhi;
            if (zzurVar != zzutVar.zzbhi) {
                return false;
            }
            if (!zzurVar.zzbhc.isArray()) {
                return this.value.equals(zzutVar.value);
            }
            Object obj2 = this.value;
            if (obj2 instanceof byte[]) {
                return Arrays.equals((byte[]) obj2, (byte[]) zzutVar.value);
            }
            if (obj2 instanceof int[]) {
                return Arrays.equals((int[]) obj2, (int[]) zzutVar.value);
            }
            if (obj2 instanceof long[]) {
                return Arrays.equals((long[]) obj2, (long[]) zzutVar.value);
            }
            if (obj2 instanceof float[]) {
                return Arrays.equals((float[]) obj2, (float[]) zzutVar.value);
            }
            if (obj2 instanceof double[]) {
                return Arrays.equals((double[]) obj2, (double[]) zzutVar.value);
            }
            if (obj2 instanceof boolean[]) {
                return Arrays.equals((boolean[]) obj2, (boolean[]) zzutVar.value);
            }
            return Arrays.deepEquals((Object[]) obj2, (Object[]) zzutVar.value);
        }
        List<zzuy> list2 = this.zzbhj;
        if (list2 != null && (list = zzutVar.zzbhj) != null) {
            return list2.equals(list);
        }
        try {
            return Arrays.equals(toByteArray(), zzutVar.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + R2.attr.hoveredFocusedTranslationZ;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private final byte[] toByteArray() throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        byte[] bArr = new byte[zzy()];
        zza(zzuo.zzl(bArr));
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: zzrz, reason: merged with bridge method [inline-methods] */
    public final zzut clone() {
        zzut zzutVar = new zzut();
        try {
            zzutVar.zzbhi = this.zzbhi;
            if (this.zzbhj == null) {
                zzutVar.zzbhj = null;
            } else {
                zzutVar.zzbhj.addAll(this.zzbhj);
            }
            if (this.value != null) {
                if (this.value instanceof zzuw) {
                    zzutVar.value = (zzuw) ((zzuw) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    zzutVar.value = ((byte[]) this.value).clone();
                } else {
                    int i = 0;
                    if (this.value instanceof byte[][]) {
                        byte[][] bArr = (byte[][]) this.value;
                        byte[][] bArr2 = new byte[bArr.length][];
                        zzutVar.value = bArr2;
                        while (i < bArr.length) {
                            bArr2[i] = (byte[]) bArr[i].clone();
                            i++;
                        }
                    } else if (this.value instanceof boolean[]) {
                        zzutVar.value = ((boolean[]) this.value).clone();
                    } else if (this.value instanceof int[]) {
                        zzutVar.value = ((int[]) this.value).clone();
                    } else if (this.value instanceof long[]) {
                        zzutVar.value = ((long[]) this.value).clone();
                    } else if (this.value instanceof float[]) {
                        zzutVar.value = ((float[]) this.value).clone();
                    } else if (this.value instanceof double[]) {
                        zzutVar.value = ((double[]) this.value).clone();
                    } else if (this.value instanceof zzuw[]) {
                        zzuw[] zzuwVarArr = (zzuw[]) this.value;
                        zzuw[] zzuwVarArr2 = new zzuw[zzuwVarArr.length];
                        zzutVar.value = zzuwVarArr2;
                        while (i < zzuwVarArr.length) {
                            zzuwVarArr2[i] = (zzuw) zzuwVarArr[i].clone();
                            i++;
                        }
                    }
                }
            }
            return zzutVar;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
