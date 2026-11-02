package org.acra.http;

import android.content.Context;
import android.net.Uri;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import org.acra.config.CoreConfiguration;
import org.acra.sender.HttpSender;
import org.acra.util.UriUtils;

/* loaded from: classes3.dex */
public class BinaryHttpRequest extends BaseHttpRequest<Uri> {
    private final Context context;

    public BinaryHttpRequest(CoreConfiguration coreConfiguration, Context context, String str, String str2, int i, int i2, Map<String, String> map) {
        super(coreConfiguration, context, HttpSender.Method.PUT, str, str2, i, i2, map);
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.acra.http.BaseHttpRequest
    public String getContentType(Context context, Uri uri) {
        return UriUtils.getMimeType(context, uri);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.acra.http.BaseHttpRequest
    public void write(OutputStream outputStream, Uri uri) throws IOException {
        UriUtils.copyFromUri(this.context, outputStream, uri);
    }
}
