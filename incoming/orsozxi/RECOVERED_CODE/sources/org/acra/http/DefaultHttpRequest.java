package org.acra.http;

import android.content.Context;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import org.acra.config.CoreConfiguration;
import org.acra.sender.HttpSender;

/* loaded from: classes3.dex */
public class DefaultHttpRequest extends BaseHttpRequest<String> {
    private final String contentType;

    public DefaultHttpRequest(CoreConfiguration coreConfiguration, Context context, HttpSender.Method method, String str, String str2, String str3, int i, int i2, Map<String, String> map) {
        super(coreConfiguration, context, method, str2, str3, i, i2, map);
        this.contentType = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.acra.http.BaseHttpRequest
    public String getContentType(Context context, String str) {
        return this.contentType;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.acra.http.BaseHttpRequest
    public void write(OutputStream outputStream, String str) throws IOException {
        outputStream.write(str.getBytes("UTF-8"));
    }
}
