package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes.dex */
public final class zzad extends zzan {
    private static boolean zzvo;
    private AdvertisingIdClient.Info zzvp;
    private final zzcv zzvq;
    private String zzvr;
    private boolean zzvs;
    private final Object zzvt;

    zzad(zzap zzapVar) {
        super(zzapVar);
        this.zzvs = false;
        this.zzvt = new Object();
        this.zzvq = new zzcv(zzapVar.zzcn());
    }

    @Override // com.google.android.gms.internal.gtm.zzan
    protected final void zzaw() {
    }

    public final boolean zzbw() {
        zzdb();
        AdvertisingIdClient.Info infoZzce = zzce();
        return (infoZzce == null || infoZzce.isLimitAdTrackingEnabled()) ? false : true;
    }

    public final String zzcd() {
        zzdb();
        AdvertisingIdClient.Info infoZzce = zzce();
        String id = infoZzce != null ? infoZzce.getId() : null;
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        return id;
    }

    private final synchronized AdvertisingIdClient.Info zzce() {
        if (this.zzvq.zzj(1000L)) {
            this.zzvq.start();
            AdvertisingIdClient.Info infoZzcf = zzcf();
            if (zza(this.zzvp, infoZzcf)) {
                this.zzvp = infoZzcf;
            } else {
                zzu("Failed to reset client id on adid change. Not using adid");
                this.zzvp = new AdvertisingIdClient.Info("", false);
            }
        }
        return this.zzvp;
    }

    private final boolean zza(AdvertisingIdClient.Info info, AdvertisingIdClient.Info info2) {
        String id = null;
        String id2 = info2 == null ? null : info2.getId();
        if (TextUtils.isEmpty(id2)) {
            return true;
        }
        String strZzeh = zzcw().zzeh();
        synchronized (this.zzvt) {
            if (!this.zzvs) {
                this.zzvr = zzcg();
                this.zzvs = true;
            } else if (TextUtils.isEmpty(this.zzvr)) {
                if (info != null) {
                    id = info.getId();
                }
                if (id == null) {
                    String strValueOf = String.valueOf(id2);
                    String strValueOf2 = String.valueOf(strZzeh);
                    return zzp(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf));
                }
                String strValueOf3 = String.valueOf(id);
                String strValueOf4 = String.valueOf(strZzeh);
                this.zzvr = zzo(strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3));
            }
            String strValueOf5 = String.valueOf(id2);
            String strValueOf6 = String.valueOf(strZzeh);
            String strZzo = zzo(strValueOf6.length() != 0 ? strValueOf5.concat(strValueOf6) : new String(strValueOf5));
            if (TextUtils.isEmpty(strZzo)) {
                return false;
            }
            if (strZzo.equals(this.zzvr)) {
                return true;
            }
            if (!TextUtils.isEmpty(this.zzvr)) {
                zzq("Resetting the client id because Advertising Id changed.");
                strZzeh = zzcw().zzei();
                zza("New client Id", strZzeh);
            }
            String strValueOf7 = String.valueOf(id2);
            String strValueOf8 = String.valueOf(strZzeh);
            return zzp(strValueOf8.length() != 0 ? strValueOf7.concat(strValueOf8) : new String(strValueOf7));
        }
    }

    private final AdvertisingIdClient.Info zzcf() {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(getContext());
        } catch (IllegalStateException unused) {
            zzt("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
            return null;
        } catch (Exception e) {
            if (!zzvo) {
                zzvo = true;
                zzd("Error getting advertiser id", e);
            }
            return null;
        }
    }

    private static String zzo(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigestZzai = zzcz.zzai(MessageDigestAlgorithms.MD5);
        if (messageDigestZzai == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigestZzai.digest(str.getBytes())));
    }

    private final boolean zzp(String str) throws NoSuchAlgorithmException, IOException {
        try {
            String strZzo = zzo(str);
            zzq("Storing hashed adid.");
            FileOutputStream fileOutputStreamOpenFileOutput = getContext().openFileOutput("gaClientIdData", 0);
            fileOutputStreamOpenFileOutput.write(strZzo.getBytes());
            fileOutputStreamOpenFileOutput.close();
            this.zzvr = strZzo;
            return true;
        } catch (IOException e) {
            zze("Error creating hash file", e);
            return false;
        }
    }

    private final String zzcg() throws IOException {
        String str = null;
        try {
            FileInputStream fileInputStreamOpenFileInput = getContext().openFileInput("gaClientIdData");
            byte[] bArr = new byte[128];
            int i = fileInputStreamOpenFileInput.read(bArr, 0, 128);
            if (fileInputStreamOpenFileInput.available() > 0) {
                zzt("Hash file seems corrupted, deleting it.");
                fileInputStreamOpenFileInput.close();
                getContext().deleteFile("gaClientIdData");
            } else if (i <= 0) {
                zzq("Hash file is empty.");
                fileInputStreamOpenFileInput.close();
            } else {
                String str2 = new String(bArr, 0, i);
                try {
                    fileInputStreamOpenFileInput.close();
                } catch (FileNotFoundException unused) {
                } catch (IOException e) {
                    e = e;
                    str = str2;
                    zzd("Error reading Hash file, deleting it", e);
                    getContext().deleteFile("gaClientIdData");
                    return str;
                }
                str = str2;
            }
        } catch (FileNotFoundException unused2) {
        } catch (IOException e2) {
            e = e2;
        }
        return str;
    }
}
