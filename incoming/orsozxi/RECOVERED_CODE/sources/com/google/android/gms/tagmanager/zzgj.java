package com.google.android.gms.tagmanager;

import com.facebook.internal.ServerProtocol;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class zzgj {
    private static final Object zzalo = null;
    private static Long zzalp = new Long(0);
    private static Double zzalq = new Double(0.0d);
    private static zzgi zzalr = zzgi.zzm(0);
    private static String zzals = new String("");
    private static Boolean zzalt = new Boolean(false);
    private static List<Object> zzalu = new ArrayList(0);
    private static Map<Object, Object> zzalv = new HashMap();
    private static com.google.android.gms.internal.gtm.zzl zzalw = zzi(zzals);

    public static Object zzjw() {
        return null;
    }

    public static Long zzjx() {
        return zzalp;
    }

    public static Double zzjy() {
        return zzalq;
    }

    public static Boolean zzjz() {
        return zzalt;
    }

    public static zzgi zzka() {
        return zzalr;
    }

    public static String zzkb() {
        return zzals;
    }

    public static com.google.android.gms.internal.gtm.zzl zzkc() {
        return zzalw;
    }

    private static String zzh(Object obj) {
        return obj == null ? zzals : obj.toString();
    }

    public static String zzc(com.google.android.gms.internal.gtm.zzl zzlVar) {
        return zzh(zzh(zzlVar));
    }

    public static zzgi zzd(com.google.android.gms.internal.gtm.zzl zzlVar) {
        Object objZzh = zzh(zzlVar);
        if (objZzh instanceof zzgi) {
            return (zzgi) objZzh;
        }
        if (zzk(objZzh)) {
            return zzgi.zzm(zzl(objZzh));
        }
        if (zzj(objZzh)) {
            return zzgi.zza(Double.valueOf(getDouble(objZzh)));
        }
        return zzbq(zzh(objZzh));
    }

    public static Long zze(com.google.android.gms.internal.gtm.zzl zzlVar) {
        Object objZzh = zzh(zzlVar);
        if (zzk(objZzh)) {
            return Long.valueOf(zzl(objZzh));
        }
        zzgi zzgiVarZzbq = zzbq(zzh(objZzh));
        return zzgiVarZzbq == zzalr ? zzalp : Long.valueOf(zzgiVarZzbq.longValue());
    }

    public static Double zzf(com.google.android.gms.internal.gtm.zzl zzlVar) {
        Object objZzh = zzh(zzlVar);
        if (zzj(objZzh)) {
            return Double.valueOf(getDouble(objZzh));
        }
        zzgi zzgiVarZzbq = zzbq(zzh(objZzh));
        return zzgiVarZzbq == zzalr ? zzalq : Double.valueOf(zzgiVarZzbq.doubleValue());
    }

    public static Boolean zzg(com.google.android.gms.internal.gtm.zzl zzlVar) {
        Object objZzh = zzh(zzlVar);
        if (objZzh instanceof Boolean) {
            return (Boolean) objZzh;
        }
        String strZzh = zzh(objZzh);
        if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equalsIgnoreCase(strZzh)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(strZzh)) {
            return Boolean.FALSE;
        }
        return zzalt;
    }

    public static com.google.android.gms.internal.gtm.zzl zzi(Object obj) {
        com.google.android.gms.internal.gtm.zzl zzlVar = new com.google.android.gms.internal.gtm.zzl();
        if (obj instanceof com.google.android.gms.internal.gtm.zzl) {
            return (com.google.android.gms.internal.gtm.zzl) obj;
        }
        boolean z = false;
        if (obj instanceof String) {
            zzlVar.type = 1;
            zzlVar.string = (String) obj;
        } else if (obj instanceof List) {
            zzlVar.type = 2;
            List list = (List) obj;
            ArrayList arrayList = new ArrayList(list.size());
            Iterator it2 = list.iterator();
            boolean z2 = false;
            while (it2.hasNext()) {
                com.google.android.gms.internal.gtm.zzl zzlVarZzi = zzi(it2.next());
                com.google.android.gms.internal.gtm.zzl zzlVar2 = zzalw;
                if (zzlVarZzi == zzlVar2) {
                    return zzlVar2;
                }
                z2 = z2 || zzlVarZzi.zzqw;
                arrayList.add(zzlVarZzi);
            }
            zzlVar.zzqn = (com.google.android.gms.internal.gtm.zzl[]) arrayList.toArray(new com.google.android.gms.internal.gtm.zzl[0]);
            z = z2;
        } else if (obj instanceof Map) {
            zzlVar.type = 3;
            Set<Map.Entry> setEntrySet = ((Map) obj).entrySet();
            ArrayList arrayList2 = new ArrayList(setEntrySet.size());
            ArrayList arrayList3 = new ArrayList(setEntrySet.size());
            boolean z3 = false;
            for (Map.Entry entry : setEntrySet) {
                com.google.android.gms.internal.gtm.zzl zzlVarZzi2 = zzi(entry.getKey());
                com.google.android.gms.internal.gtm.zzl zzlVarZzi3 = zzi(entry.getValue());
                com.google.android.gms.internal.gtm.zzl zzlVar3 = zzalw;
                if (zzlVarZzi2 == zzlVar3 || zzlVarZzi3 == zzlVar3) {
                    return zzalw;
                }
                z3 = z3 || zzlVarZzi2.zzqw || zzlVarZzi3.zzqw;
                arrayList2.add(zzlVarZzi2);
                arrayList3.add(zzlVarZzi3);
            }
            zzlVar.zzqo = (com.google.android.gms.internal.gtm.zzl[]) arrayList2.toArray(new com.google.android.gms.internal.gtm.zzl[0]);
            zzlVar.zzqp = (com.google.android.gms.internal.gtm.zzl[]) arrayList3.toArray(new com.google.android.gms.internal.gtm.zzl[0]);
            z = z3;
        } else if (zzj(obj)) {
            zzlVar.type = 1;
            zzlVar.string = obj.toString();
        } else if (zzk(obj)) {
            zzlVar.type = 6;
            zzlVar.zzqs = zzl(obj);
        } else if (obj instanceof Boolean) {
            zzlVar.type = 8;
            zzlVar.zzqt = ((Boolean) obj).booleanValue();
        } else {
            String strValueOf = String.valueOf(obj == null ? "null" : obj.getClass().toString());
            zzdi.zzav(strValueOf.length() != 0 ? "Converting to Value from unknown object type: ".concat(strValueOf) : new String("Converting to Value from unknown object type: "));
            return zzalw;
        }
        zzlVar.zzqw = z;
        return zzlVar;
    }

    public static com.google.android.gms.internal.gtm.zzl zzbp(String str) {
        com.google.android.gms.internal.gtm.zzl zzlVar = new com.google.android.gms.internal.gtm.zzl();
        zzlVar.type = 5;
        zzlVar.zzqr = str;
        return zzlVar;
    }

    private static boolean zzj(Object obj) {
        if ((obj instanceof Double) || (obj instanceof Float)) {
            return true;
        }
        return (obj instanceof zzgi) && ((zzgi) obj).zzju();
    }

    private static double getDouble(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        zzdi.zzav("getDouble received non-Number");
        return 0.0d;
    }

    private static boolean zzk(Object obj) {
        if ((obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long)) {
            return true;
        }
        return (obj instanceof zzgi) && ((zzgi) obj).zzjv();
    }

    private static long zzl(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        zzdi.zzav("getInt64 received non-Number");
        return 0L;
    }

    private static zzgi zzbq(String str) {
        try {
            return zzgi.zzbo(str);
        } catch (NumberFormatException unused) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 33);
            sb.append("Failed to convert '");
            sb.append(str);
            sb.append("' to a number.");
            zzdi.zzav(sb.toString());
            return zzalr;
        }
    }

    public static Object zzh(com.google.android.gms.internal.gtm.zzl zzlVar) {
        if (zzlVar == null) {
            return null;
        }
        int i = 0;
        switch (zzlVar.type) {
            case 1:
                break;
            case 2:
                ArrayList arrayList = new ArrayList(zzlVar.zzqn.length);
                com.google.android.gms.internal.gtm.zzl[] zzlVarArr = zzlVar.zzqn;
                int length = zzlVarArr.length;
                while (i < length) {
                    Object objZzh = zzh(zzlVarArr[i]);
                    if (objZzh == null) {
                        break;
                    } else {
                        arrayList.add(objZzh);
                        i++;
                    }
                }
                break;
            case 3:
                if (zzlVar.zzqo.length != zzlVar.zzqp.length) {
                    String strValueOf = String.valueOf(zzlVar.toString());
                    zzdi.zzav(strValueOf.length() != 0 ? "Converting an invalid value to object: ".concat(strValueOf) : new String("Converting an invalid value to object: "));
                    break;
                } else {
                    HashMap map = new HashMap(zzlVar.zzqp.length);
                    while (i < zzlVar.zzqo.length) {
                        Object objZzh2 = zzh(zzlVar.zzqo[i]);
                        Object objZzh3 = zzh(zzlVar.zzqp[i]);
                        if (objZzh2 == null || objZzh3 == null) {
                            break;
                        } else {
                            map.put(objZzh2, objZzh3);
                            i++;
                        }
                    }
                    break;
                }
                break;
            case 4:
                zzdi.zzav("Trying to convert a macro reference to object");
                break;
            case 5:
                zzdi.zzav("Trying to convert a function id to object");
                break;
            case 6:
                break;
            case 7:
                StringBuilder sb = new StringBuilder();
                com.google.android.gms.internal.gtm.zzl[] zzlVarArr2 = zzlVar.zzqu;
                int length2 = zzlVarArr2.length;
                while (i < length2) {
                    String strZzh = zzh(zzh(zzlVarArr2[i]));
                    if (strZzh == zzals) {
                        break;
                    } else {
                        sb.append(strZzh);
                        i++;
                    }
                }
                break;
            case 8:
                break;
            default:
                int i2 = zzlVar.type;
                StringBuilder sb2 = new StringBuilder(46);
                sb2.append("Failed to convert a value of type: ");
                sb2.append(i2);
                zzdi.zzav(sb2.toString());
                break;
        }
        return null;
    }
}
