package com.google.android.gms.internal.gtm;

/* loaded from: classes.dex */
final class zztn {
    static String zzd(zzps zzpsVar) {
        zzto zztoVar = new zzto(zzpsVar);
        StringBuilder sb = new StringBuilder(zztoVar.size());
        for (int i = 0; i < zztoVar.size(); i++) {
            byte bZzak = zztoVar.zzak(i);
            if (bZzak == 34) {
                sb.append("\\\"");
            } else if (bZzak == 39) {
                sb.append("\\'");
            } else if (bZzak != 92) {
                switch (bZzak) {
                    case 7:
                        sb.append("\\a");
                        break;
                    case 8:
                        sb.append("\\b");
                        break;
                    case 9:
                        sb.append("\\t");
                        break;
                    case 10:
                        sb.append("\\n");
                        break;
                    case 11:
                        sb.append("\\v");
                        break;
                    case 12:
                        sb.append("\\f");
                        break;
                    case 13:
                        sb.append("\\r");
                        break;
                    default:
                        if (bZzak < 32 || bZzak > 126) {
                            sb.append('\\');
                            sb.append((char) (((bZzak >>> 6) & 3) + 48));
                            sb.append((char) (((bZzak >>> 3) & 7) + 48));
                            sb.append((char) ((bZzak & 7) + 48));
                            break;
                        } else {
                            sb.append((char) bZzak);
                            break;
                        }
                }
            } else {
                sb.append("\\\\");
            }
        }
        return sb.toString();
    }
}
