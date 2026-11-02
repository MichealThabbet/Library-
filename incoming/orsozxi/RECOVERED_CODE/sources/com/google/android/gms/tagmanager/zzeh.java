package com.google.android.gms.tagmanager;

import android.net.Uri;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/* loaded from: classes.dex */
class zzeh {
    private static zzeh zzaip;
    private volatile zza zzaiq = zza.NONE;
    private volatile String zzair = null;
    private volatile String zzaec = null;
    private volatile String zzais = null;

    enum zza {
        NONE,
        CONTAINER,
        CONTAINER_DEBUG
    }

    zzeh() {
    }

    static zzeh zziy() {
        zzeh zzehVar;
        synchronized (zzeh.class) {
            if (zzaip == null) {
                zzaip = new zzeh();
            }
            zzehVar = zzaip;
        }
        return zzehVar;
    }

    final synchronized boolean zza(Uri uri) {
        try {
            String strDecode = URLDecoder.decode(uri.toString(), "UTF-8");
            if (strDecode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) {
                String strValueOf = String.valueOf(strDecode);
                zzdi.zzab(strValueOf.length() != 0 ? "Container preview url: ".concat(strValueOf) : new String("Container preview url: "));
                if (strDecode.matches(".*?&gtm_debug=x$")) {
                    this.zzaiq = zza.CONTAINER_DEBUG;
                } else {
                    this.zzaiq = zza.CONTAINER;
                }
                this.zzais = uri.getQuery().replace("&gtm_debug=x", "");
                if (this.zzaiq == zza.CONTAINER || this.zzaiq == zza.CONTAINER_DEBUG) {
                    String strValueOf2 = String.valueOf(this.zzais);
                    this.zzair = strValueOf2.length() != 0 ? "/r?".concat(strValueOf2) : new String("/r?");
                }
                this.zzaec = zzbh(this.zzais);
                return true;
            }
            if (strDecode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
                if (!zzbh(uri.getQuery()).equals(this.zzaec)) {
                    return false;
                }
                String strValueOf3 = String.valueOf(this.zzaec);
                zzdi.zzab(strValueOf3.length() != 0 ? "Exit preview mode for container: ".concat(strValueOf3) : new String("Exit preview mode for container: "));
                this.zzaiq = zza.NONE;
                this.zzair = null;
                return true;
            }
            String strValueOf4 = String.valueOf(strDecode);
            zzdi.zzac(strValueOf4.length() != 0 ? "Invalid preview uri: ".concat(strValueOf4) : new String("Invalid preview uri: "));
            return false;
        } catch (UnsupportedEncodingException unused) {
            return false;
        }
    }

    final zza zziz() {
        return this.zzaiq;
    }

    final String zzja() {
        return this.zzair;
    }

    final String getContainerId() {
        return this.zzaec;
    }

    private static String zzbh(String str) {
        return str.split(ContainerUtils.FIELD_DELIMITER)[0].split(ContainerUtils.KEY_VALUE_DELIMITER)[1];
    }
}
