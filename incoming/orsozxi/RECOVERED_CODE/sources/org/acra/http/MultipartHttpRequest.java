package org.acra.http;

import android.content.Context;
import android.net.Uri;
import android.util.Pair;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import org.acra.ACRA;
import org.acra.config.CoreConfiguration;
import org.acra.sender.HttpSender;
import org.acra.util.UriUtils;

/* loaded from: classes3.dex */
public class MultipartHttpRequest extends BaseHttpRequest<Pair<String, List<Uri>>> {
    private static final String BOUNDARY = "%&ACRA_REPORT_DIVIDER&%";
    private static final String BOUNDARY_FIX = "--";
    private static final String CONTENT_DISPOSITION = "Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\n";
    private static final String CONTENT_TYPE = "Content-Type: %s\r\n";
    private static final String MESSAGE_END = "\r\n--%&ACRA_REPORT_DIVIDER&%--\r\n";
    private static final String NEW_LINE = "\r\n";
    private static final String SECTION_START = "\r\n--%&ACRA_REPORT_DIVIDER&%\r\n";
    private final String contentType;
    private final Context context;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.acra.http.BaseHttpRequest
    public String getContentType(Context context, Pair<String, List<Uri>> pair) {
        return "multipart/form-data; boundary=%&ACRA_REPORT_DIVIDER&%";
    }

    public MultipartHttpRequest(CoreConfiguration coreConfiguration, Context context, String str, String str2, String str3, int i, int i2, Map<String, String> map) {
        super(coreConfiguration, context, HttpSender.Method.POST, str2, str3, i, i2, map);
        this.context = context;
        this.contentType = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.acra.http.BaseHttpRequest
    public void write(OutputStream outputStream, Pair<String, List<Uri>> pair) throws IOException {
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        printWriter.append((CharSequence) SECTION_START).format(CONTENT_DISPOSITION, "ACRA_REPORT", "").format(CONTENT_TYPE, this.contentType).append((CharSequence) "\r\n").append((CharSequence) pair.first);
        for (Uri uri : (List) pair.second) {
            try {
                printWriter.append((CharSequence) SECTION_START).format(CONTENT_DISPOSITION, "ACRA_ATTACHMENT", UriUtils.getFileNameFromUri(this.context, uri)).format(CONTENT_TYPE, UriUtils.getMimeType(this.context, uri)).append((CharSequence) "\r\n").flush();
                UriUtils.copyFromUri(this.context, outputStream, uri);
            } catch (FileNotFoundException e) {
                ACRA.log.w("Not sending attachment", e);
            }
        }
        printWriter.append((CharSequence) MESSAGE_END).flush();
    }
}
