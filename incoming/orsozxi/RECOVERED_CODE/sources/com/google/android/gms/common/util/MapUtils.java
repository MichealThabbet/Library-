package com.google.android.gms.common.util;

import it.piapostolos.android.pinotes.utils.ConstantsBase;
import java.util.HashMap;

/* loaded from: classes.dex */
public class MapUtils {
    public static void writeStringMapToJson(StringBuilder sb, HashMap<String, String> map) {
        sb.append("{");
        boolean z = true;
        for (String str : map.keySet()) {
            if (z) {
                z = false;
            } else {
                sb.append(ConstantsBase.PROPERTIES_PARAMS_SEPARATOR);
            }
            String str2 = map.get(str);
            sb.append("\"");
            sb.append(str);
            sb.append("\":");
            if (str2 == null) {
                sb.append("null");
            } else {
                sb.append("\"");
                sb.append(str2);
                sb.append("\"");
            }
        }
        sb.append("}");
    }
}
