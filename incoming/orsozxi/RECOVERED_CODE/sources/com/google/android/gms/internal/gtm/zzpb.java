package com.google.android.gms.internal.gtm;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes.dex */
final class zzpb implements zzpc {
    private HttpURLConnection zzatk;
    private InputStream zzatl = null;

    zzpb() {
    }

    @Override // com.google.android.gms.internal.gtm.zzpc
    public final InputStream zzcj(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setReadTimeout(BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT);
        httpURLConnection.setConnectTimeout(BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT);
        this.zzatk = httpURLConnection;
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 200) {
            InputStream inputStream = httpURLConnection.getInputStream();
            this.zzatl = inputStream;
            return inputStream;
        }
        StringBuilder sb = new StringBuilder(25);
        sb.append("Bad response: ");
        sb.append(responseCode);
        String string = sb.toString();
        if (responseCode == 404) {
            throw new FileNotFoundException(string);
        }
        if (responseCode == 503) {
            throw new zzpe(string);
        }
        throw new IOException(string);
    }

    @Override // com.google.android.gms.internal.gtm.zzpc
    public final void close() throws IOException {
        HttpURLConnection httpURLConnection = this.zzatk;
        try {
            if (this.zzatl != null) {
                this.zzatl.close();
            }
        } catch (IOException e) {
            String strValueOf = String.valueOf(e.getMessage());
            com.google.android.gms.tagmanager.zzdi.zza(strValueOf.length() != 0 ? "HttpUrlConnectionNetworkClient: Error when closing http input stream: ".concat(strValueOf) : new String("HttpUrlConnectionNetworkClient: Error when closing http input stream: "), e);
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }
}
