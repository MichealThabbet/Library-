package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import com.ibm.icu.text.DateFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.0 */
/* loaded from: classes.dex */
public final class zzex {
    final /* synthetic */ zzfb zza;
    private final String zzb;
    private final Bundle zzc;
    private Bundle zzd;

    public zzex(zzfb zzfbVar, String str, Bundle bundle) {
        this.zza = zzfbVar;
        Preconditions.checkNotEmpty("default_event_parameters");
        this.zzb = "default_event_parameters";
        this.zzc = new Bundle();
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009c A[Catch: NumberFormatException | JSONException -> 0x00a4, NumberFormatException | JSONException -> 0x00a4, TRY_LEAVE, TryCatch #0 {NumberFormatException | JSONException -> 0x00a4, blocks: (B:10:0x0027, B:32:0x0072, B:32:0x0072, B:33:0x0084, B:33:0x0084, B:34:0x0090, B:34:0x0090, B:35:0x009c, B:35:0x009c), top: B:47:0x0027, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.os.Bundle zza() {
        /*
            r11 = this;
            android.os.Bundle r0 = r11.zzd
            if (r0 == 0) goto L6
            goto Ld5
        L6:
            com.google.android.gms.measurement.internal.zzfb r0 = r11.zza
            android.content.SharedPreferences r0 = r0.zzd()
            java.lang.String r1 = r11.zzb
            r2 = 0
            java.lang.String r0 = r0.getString(r1, r2)
            if (r0 == 0) goto Lcd
            android.os.Bundle r1 = new android.os.Bundle     // Catch: org.json.JSONException -> Lbc
            r1.<init>()     // Catch: org.json.JSONException -> Lbc
            org.json.JSONArray r2 = new org.json.JSONArray     // Catch: org.json.JSONException -> Lbc
            r2.<init>(r0)     // Catch: org.json.JSONException -> Lbc
            r0 = 0
            r3 = 0
        L21:
            int r4 = r2.length()     // Catch: org.json.JSONException -> Lbc
            if (r3 >= r4) goto Lb9
            org.json.JSONObject r4 = r2.getJSONObject(r3)     // Catch: java.lang.Throwable -> La4
            java.lang.String r5 = "n"
            java.lang.String r5 = r4.getString(r5)     // Catch: java.lang.Throwable -> La4
            java.lang.String r6 = "t"
            java.lang.String r6 = r4.getString(r6)     // Catch: java.lang.Throwable -> La4
            int r7 = r6.hashCode()     // Catch: java.lang.Throwable -> La4
            r8 = 100
            r9 = 2
            r10 = 1
            if (r7 == r8) goto L5e
            r8 = 108(0x6c, float:1.51E-43)
            if (r7 == r8) goto L54
            r8 = 115(0x73, float:1.61E-43)
            if (r7 == r8) goto L4a
            goto L68
        L4a:
            java.lang.String r7 = "s"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L68
            r7 = 0
            goto L69
        L54:
            java.lang.String r7 = "l"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L68
            r7 = 2
            goto L69
        L5e:
            java.lang.String r7 = "d"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L68
            r7 = 1
            goto L69
        L68:
            r7 = -1
        L69:
            java.lang.String r8 = "v"
            if (r7 == 0) goto L9c
            if (r7 == r10) goto L90
            if (r7 == r9) goto L84
            com.google.android.gms.measurement.internal.zzfb r4 = r11.zza     // Catch: java.lang.Throwable -> La4 java.lang.Throwable -> La4
            com.google.android.gms.measurement.internal.zzfu r4 = r4.zzs     // Catch: java.lang.Throwable -> La4 java.lang.Throwable -> La4
            com.google.android.gms.measurement.internal.zzem r4 = r4.zzau()     // Catch: java.lang.Throwable -> La4 java.lang.Throwable -> La4
            com.google.android.gms.measurement.internal.zzek r4 = r4.zzb()     // Catch: java.lang.Throwable -> La4 java.lang.Throwable -> La4
            java.lang.String r5 = "Unrecognized persisted bundle type. Type"
            r4.zzb(r5, r6)     // Catch: java.lang.Throwable -> La4 java.lang.Throwable -> La4
            goto Lb5
        L84:
            java.lang.String r4 = r4.getString(r8)     // Catch: java.lang.Throwable -> La4 java.lang.Throwable -> La4
            long r6 = java.lang.Long.parseLong(r4)     // Catch: java.lang.Throwable -> La4 java.lang.Throwable -> La4
            r1.putLong(r5, r6)     // Catch: java.lang.Throwable -> La4 java.lang.Throwable -> La4
            goto Lb5
        L90:
            java.lang.String r4 = r4.getString(r8)     // Catch: java.lang.Throwable -> La4 java.lang.Throwable -> La4
            double r6 = java.lang.Double.parseDouble(r4)     // Catch: java.lang.Throwable -> La4 java.lang.Throwable -> La4
            r1.putDouble(r5, r6)     // Catch: java.lang.Throwable -> La4 java.lang.Throwable -> La4
            goto Lb5
        L9c:
            java.lang.String r4 = r4.getString(r8)     // Catch: java.lang.Throwable -> La4 java.lang.Throwable -> La4
            r1.putString(r5, r4)     // Catch: java.lang.Throwable -> La4 java.lang.Throwable -> La4
            goto Lb5
        La4:
            com.google.android.gms.measurement.internal.zzfb r4 = r11.zza     // Catch: org.json.JSONException -> Lbc
            com.google.android.gms.measurement.internal.zzfu r4 = r4.zzs     // Catch: org.json.JSONException -> Lbc
            com.google.android.gms.measurement.internal.zzem r4 = r4.zzau()     // Catch: org.json.JSONException -> Lbc
            com.google.android.gms.measurement.internal.zzek r4 = r4.zzb()     // Catch: org.json.JSONException -> Lbc
            java.lang.String r5 = "Error reading value from SharedPreferences. Value dropped"
            r4.zza(r5)     // Catch: org.json.JSONException -> Lbc
        Lb5:
            int r3 = r3 + 1
            goto L21
        Lb9:
            r11.zzd = r1     // Catch: org.json.JSONException -> Lbc
            goto Lcd
        Lbc:
            com.google.android.gms.measurement.internal.zzfb r0 = r11.zza
            com.google.android.gms.measurement.internal.zzfu r0 = r0.zzs
            com.google.android.gms.measurement.internal.zzem r0 = r0.zzau()
            com.google.android.gms.measurement.internal.zzek r0 = r0.zzb()
            java.lang.String r1 = "Error loading bundle from SharedPreferences. Values will be lost"
            r0.zza(r1)
        Lcd:
            android.os.Bundle r0 = r11.zzd
            if (r0 != 0) goto Ld5
            android.os.Bundle r0 = r11.zzc
            r11.zzd = r0
        Ld5:
            android.os.Bundle r0 = r11.zzd
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzex.zza():android.os.Bundle");
    }

    public final void zzb(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        SharedPreferences.Editor editorEdit = this.zza.zzd().edit();
        if (bundle.size() == 0) {
            editorEdit.remove(this.zzb);
        } else {
            String str = this.zzb;
            JSONArray jSONArray = new JSONArray();
            for (String str2 : bundle.keySet()) {
                Object obj = bundle.get(str2);
                if (obj != null) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("n", str2);
                        jSONObject.put(DateFormat.ABBR_GENERIC_TZ, String.valueOf(obj));
                        if (obj instanceof String) {
                            jSONObject.put("t", DateFormat.SECOND);
                        } else if (obj instanceof Long) {
                            jSONObject.put("t", "l");
                        } else if (obj instanceof Double) {
                            jSONObject.put("t", DateFormat.DAY);
                        } else {
                            this.zza.zzs.zzau().zzb().zzb("Cannot serialize bundle value to SharedPreferences. Type", obj.getClass());
                        }
                        jSONArray.put(jSONObject);
                    } catch (JSONException e) {
                        this.zza.zzs.zzau().zzb().zzb("Cannot serialize bundle value to SharedPreferences", e);
                    }
                }
            }
            editorEdit.putString(str, jSONArray.toString());
        }
        editorEdit.apply();
        this.zzd = bundle;
    }
}
