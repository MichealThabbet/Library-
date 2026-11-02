package com.google.android.gms.internal.measurement;

import com.google.firebase.analytics.FirebaseAnalytics;
import it.piapostolos.android.pinotes.db.DbHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: com.google.android.gms:play-services-measurement@@19.0.0 */
/* loaded from: classes.dex */
public final class zzat implements Iterable<zzap>, zzap {
    private final String zza;

    public zzat(String str) {
        if (str == null) {
            throw new IllegalArgumentException("StringValue cannot be null.");
        }
        this.zza = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzat) {
            return this.zza.equals(((zzat) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    @Override // java.lang.Iterable
    public final Iterator<zzap> iterator() {
        return new zzas(this);
    }

    public final String toString() {
        String str = this.zza;
        StringBuilder sb = new StringBuilder(str.length() + 2);
        sb.append('\"');
        sb.append(str);
        sb.append('\"');
        return sb.toString();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v101 */
    /* JADX WARN: Type inference failed for: r0v78 */
    /* JADX WARN: Type inference failed for: r0v79, types: [int] */
    @Override // com.google.android.gms.internal.measurement.zzap
    public final zzap zzbK(String str, zzg zzgVar, List<zzap> list) {
        String str2;
        String str3;
        String str4;
        String str5;
        char c;
        zzat zzatVar;
        int i;
        zzap zzahVar;
        String strZzc;
        zzat zzatVar2;
        int i2;
        boolean z;
        zzg zzgVar2;
        int iZzi;
        if ("charAt".equals(str) || "concat".equals(str) || "hasOwnProperty".equals(str) || "indexOf".equals(str) || "lastIndexOf".equals(str) || "match".equals(str) || "replace".equals(str) || FirebaseAnalytics.Event.SEARCH.equals(str) || "slice".equals(str) || "split".equals(str) || "substring".equals(str) || "toLowerCase".equals(str) || "toLocaleLowerCase".equals(str) || "toString".equals(str) || "toUpperCase".equals(str)) {
            str2 = "toLocaleUpperCase";
        } else {
            str2 = "toLocaleUpperCase";
            if (!str2.equals(str) && !"trim".equals(str)) {
                throw new IllegalArgumentException(String.format("%s is not a String function", str));
            }
        }
        switch (str.hashCode()) {
            case -1789698943:
                str3 = "charAt";
                str4 = "hasOwnProperty";
                if (str.equals(str4)) {
                    str5 = "toString";
                    c = 2;
                    break;
                }
                c = 65535;
                str5 = "toString";
                break;
            case -1776922004:
                str3 = "charAt";
                if (str.equals("toString")) {
                    c = 14;
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case -1464939364:
                str3 = "charAt";
                if (str.equals("toLocaleLowerCase")) {
                    c = '\f';
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case -1361633751:
                str3 = "charAt";
                if (str.equals(str3)) {
                    str4 = "hasOwnProperty";
                    c = 0;
                    str5 = "toString";
                    break;
                }
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case -1354795244:
                if (str.equals("concat")) {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    c = 1;
                    str5 = "toString";
                    break;
                }
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case -1137582698:
                if (str.equals("toLowerCase")) {
                    c = '\r';
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case -906336856:
                if (str.equals(FirebaseAnalytics.Event.SEARCH)) {
                    c = 7;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case -726908483:
                if (str.equals(str2)) {
                    c = 11;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case -467511597:
                if (str.equals("lastIndexOf")) {
                    c = 4;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case -399551817:
                if (str.equals("toUpperCase")) {
                    c = 15;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case 3568674:
                if (str.equals("trim")) {
                    c = 16;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case 103668165:
                if (str.equals("match")) {
                    c = 5;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case 109526418:
                if (str.equals("slice")) {
                    c = '\b';
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case 109648666:
                if (str.equals("split")) {
                    c = '\t';
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case 530542161:
                if (str.equals("substring")) {
                    c = '\n';
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case 1094496948:
                if (str.equals("replace")) {
                    c = 6;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            case 1943291465:
                if (str.equals("indexOf")) {
                    c = 3;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                    break;
                }
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
            default:
                str3 = "charAt";
                str4 = "hasOwnProperty";
                c = 65535;
                str5 = "toString";
                break;
        }
        String str6 = str3;
        switch (c) {
            case 0:
                zzh.zzc(str6, 1, list);
                int iZzi2 = list.size() > 0 ? (int) zzh.zzi(zzgVar.zza(list.get(0)).zzd().doubleValue()) : 0;
                String str7 = this.zza;
                return (iZzi2 < 0 || iZzi2 >= str7.length()) ? zzap.zzm : new zzat(String.valueOf(str7.charAt(iZzi2)));
            case 1:
                zzatVar = this;
                if (list.size() != 0) {
                    StringBuilder sb = new StringBuilder(zzatVar.zza);
                    for (int i3 = 0; i3 < list.size(); i3++) {
                        sb.append(zzgVar.zza(list.get(i3)).zzc());
                    }
                    return new zzat(sb.toString());
                }
                return zzatVar;
            case 2:
                zzh.zza(str4, 1, list);
                String str8 = this.zza;
                zzap zzapVarZza = zzgVar.zza(list.get(0));
                if (DbHelper.KEY_ATTACHMENT_LENGTH.equals(zzapVarZza.zzc())) {
                    return zzaf.zzk;
                }
                double dDoubleValue = zzapVarZza.zzd().doubleValue();
                return (dDoubleValue != Math.floor(dDoubleValue) || (i = (int) dDoubleValue) < 0 || i >= str8.length()) ? zzaf.zzl : zzaf.zzk;
            case 3:
                zzh.zzc("indexOf", 2, list);
                zzahVar = new zzah(Double.valueOf(this.zza.indexOf(list.size() > 0 ? zzgVar.zza(list.get(0)).zzc() : "undefined", (int) zzh.zzi(list.size() < 2 ? 0.0d : zzgVar.zza(list.get(1)).zzd().doubleValue()))));
                return zzahVar;
            case 4:
                zzh.zzc("lastIndexOf", 2, list);
                String str9 = this.zza;
                String strZzc2 = list.size() > 0 ? zzgVar.zza(list.get(0)).zzc() : "undefined";
                return new zzah(Double.valueOf(str9.lastIndexOf(strZzc2, (int) (Double.isNaN(list.size() < 2 ? Double.NaN : zzgVar.zza(list.get(1)).zzd().doubleValue()) ? Double.POSITIVE_INFINITY : zzh.zzi(r3)))));
            case 5:
                zzh.zzc("match", 1, list);
                Matcher matcher = Pattern.compile(list.size() > 0 ? zzgVar.zza(list.get(0)).zzc() : "").matcher(this.zza);
                return matcher.find() ? new zzae(Arrays.asList(new zzat(matcher.group()))) : zzap.zzg;
            case 6:
                zzatVar = this;
                zzh.zzc("replace", 2, list);
                zzap zzapVarZza2 = zzap.zzf;
                if (list.size() > 0) {
                    strZzc = zzgVar.zza(list.get(0)).zzc();
                    if (list.size() > 1) {
                        zzapVarZza2 = zzgVar.zza(list.get(1));
                    }
                }
                String str10 = strZzc;
                String str11 = zzatVar.zza;
                int iIndexOf = str11.indexOf(str10);
                if (iIndexOf >= 0) {
                    if (zzapVarZza2 instanceof zzai) {
                        zzapVarZza2 = ((zzai) zzapVarZza2).zza(zzgVar, Arrays.asList(new zzat(str10), new zzah(Double.valueOf(iIndexOf)), zzatVar));
                    }
                    String strSubstring = str11.substring(0, iIndexOf);
                    String strZzc3 = zzapVarZza2.zzc();
                    String strSubstring2 = str11.substring(iIndexOf + str10.length());
                    StringBuilder sb2 = new StringBuilder(String.valueOf(strSubstring).length() + String.valueOf(strZzc3).length() + String.valueOf(strSubstring2).length());
                    sb2.append(strSubstring);
                    sb2.append(strZzc3);
                    sb2.append(strSubstring2);
                    zzahVar = new zzat(sb2.toString());
                    return zzahVar;
                }
                return zzatVar;
            case 7:
                zzh.zzc(FirebaseAnalytics.Event.SEARCH, 1, list);
                return Pattern.compile(list.size() > 0 ? zzgVar.zza(list.get(0)).zzc() : "undefined").matcher(this.zza).find() ? new zzah(Double.valueOf(r0.start())) : new zzah(Double.valueOf(-1.0d));
            case '\b':
                zzh.zzc("slice", 2, list);
                String str12 = this.zza;
                double dZzi = zzh.zzi(list.size() > 0 ? zzgVar.zza(list.get(0)).zzd().doubleValue() : 0.0d);
                int iMax = (int) (dZzi < 0.0d ? Math.max(str12.length() + dZzi, 0.0d) : Math.min(dZzi, str12.length()));
                double dZzi2 = zzh.zzi(list.size() > 1 ? zzgVar.zza(list.get(1)).zzd().doubleValue() : str12.length());
                zzatVar2 = new zzat(str12.substring(iMax, Math.max(0, ((int) (dZzi2 < 0.0d ? Math.max(str12.length() + dZzi2, 0.0d) : Math.min(dZzi2, str12.length()))) - iMax) + iMax));
                return zzatVar2;
            case '\t':
                zzh.zzc("split", 2, list);
                String str13 = this.zza;
                if (str13.length() == 0) {
                    return new zzae(Arrays.asList(this));
                }
                ArrayList arrayList = new ArrayList();
                if (list.size() == 0) {
                    arrayList.add(this);
                } else {
                    String strZzc4 = zzgVar.zza(list.get(0)).zzc();
                    long jZzh = list.size() > 1 ? zzh.zzh(zzgVar.zza(list.get(1)).zzd().doubleValue()) : 2147483647L;
                    if (jZzh == 0) {
                        return new zzae();
                    }
                    String[] strArrSplit = str13.split(Pattern.quote(strZzc4), ((int) jZzh) + 1);
                    int length = strArrSplit.length;
                    if (!strZzc4.equals("") || length <= 0) {
                        i2 = length;
                        z = false;
                    } else {
                        boolean zEquals = strArrSplit[0].equals("");
                        i2 = length - 1;
                        if (!strArrSplit[i2].equals("")) {
                            i2 = length;
                        }
                        z = zEquals;
                    }
                    if (length > jZzh) {
                        i2--;
                    }
                    for (?? r0 = z; r0 < i2; r0++) {
                        arrayList.add(new zzat(strArrSplit[r0]));
                    }
                }
                return new zzae(arrayList);
            case '\n':
                zzh.zzc("substring", 2, list);
                String str14 = this.zza;
                if (list.size() > 0) {
                    zzgVar2 = zzgVar;
                    iZzi = (int) zzh.zzi(zzgVar2.zza(list.get(0)).zzd().doubleValue());
                } else {
                    zzgVar2 = zzgVar;
                    iZzi = 0;
                }
                int iZzi3 = list.size() > 1 ? (int) zzh.zzi(zzgVar2.zza(list.get(1)).zzd().doubleValue()) : str14.length();
                int iMin = Math.min(Math.max(iZzi, 0), str14.length());
                int iMin2 = Math.min(Math.max(iZzi3, 0), str14.length());
                zzatVar2 = new zzat(str14.substring(Math.min(iMin, iMin2), Math.max(iMin, iMin2)));
                return zzatVar2;
            case 11:
                zzh.zza(str2, 0, list);
                return new zzat(this.zza.toUpperCase());
            case '\f':
                zzh.zza("toLocaleLowerCase", 0, list);
                return new zzat(this.zza.toLowerCase());
            case '\r':
                zzh.zza("toLowerCase", 0, list);
                return new zzat(this.zza.toLowerCase(Locale.ENGLISH));
            case 14:
                zzatVar = this;
                zzh.zza(str5, 0, list);
                return zzatVar;
            case 15:
                zzh.zza("toUpperCase", 0, list);
                return new zzat(this.zza.toUpperCase(Locale.ENGLISH));
            case 16:
                zzh.zza("toUpperCase", 0, list);
                return new zzat(this.zza.trim());
            default:
                throw new IllegalArgumentException("Command not supported");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzap
    public final String zzc() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzap
    public final Double zzd() {
        if (this.zza.isEmpty()) {
            return Double.valueOf(0.0d);
        }
        try {
            return Double.valueOf(this.zza);
        } catch (NumberFormatException unused) {
            return Double.valueOf(Double.NaN);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzap
    public final Boolean zze() {
        return Boolean.valueOf(!this.zza.isEmpty());
    }

    @Override // com.google.android.gms.internal.measurement.zzap
    public final Iterator<zzap> zzf() {
        return new zzar(this);
    }

    @Override // com.google.android.gms.internal.measurement.zzap
    public final zzap zzt() {
        return new zzat(this.zza);
    }
}
