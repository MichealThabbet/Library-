package org.acra.sender;

import android.content.Context;
import android.net.Uri;
import android.util.Pair;
import com.huawei.hms.framework.common.ContainerUtils;
import com.ibm.icu.impl.locale.LanguageTag;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.config.ConfigUtils;
import org.acra.config.CoreConfiguration;
import org.acra.config.HttpSenderConfiguration;
import org.acra.data.CrashReportData;
import org.acra.data.StringFormat;
import org.acra.http.BinaryHttpRequest;
import org.acra.http.DefaultHttpRequest;
import org.acra.http.MultipartHttpRequest;
import org.acra.sender.ReportSender;
import org.acra.util.UriUtils;
import org.apache.commons.io.IOUtils;

/* loaded from: classes3.dex */
public class HttpSender implements ReportSender {
    private final CoreConfiguration config;
    private final HttpSenderConfiguration httpConfig;
    private final Uri mFormUri;
    private final Method mMethod;
    private String mPassword;
    private final StringFormat mType;
    private String mUsername;

    @Override // org.acra.sender.ReportSender
    public /* synthetic */ boolean requiresForeground() {
        return ReportSender.CC.$default$requiresForeground(this);
    }

    public HttpSender(CoreConfiguration coreConfiguration, Method method, StringFormat stringFormat) {
        this(coreConfiguration, method, stringFormat, null);
    }

    public HttpSender(CoreConfiguration coreConfiguration, Method method, StringFormat stringFormat, String str) {
        this.config = coreConfiguration;
        HttpSenderConfiguration httpSenderConfiguration = (HttpSenderConfiguration) ConfigUtils.getPluginConfiguration(coreConfiguration, HttpSenderConfiguration.class);
        this.httpConfig = httpSenderConfiguration;
        this.mMethod = method == null ? httpSenderConfiguration.httpMethod() : method;
        this.mFormUri = Uri.parse(str == null ? this.httpConfig.uri() : str);
        this.mType = stringFormat == null ? coreConfiguration.reportFormat() : stringFormat;
        this.mUsername = null;
        this.mPassword = null;
    }

    public void setBasicAuth(String str, String str2) {
        this.mUsername = str;
        this.mPassword = str2;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0047 A[Catch: Exception -> 0x00ab, TryCatch #0 {Exception -> 0x00ab, blocks: (B:3:0x0003, B:5:0x000d, B:6:0x0025, B:8:0x002a, B:14:0x0043, B:16:0x0047, B:22:0x0060, B:18:0x004b, B:21:0x0059, B:10:0x002e, B:13:0x003c), top: B:27:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004b A[Catch: Exception -> 0x00ab, TryCatch #0 {Exception -> 0x00ab, blocks: (B:3:0x0003, B:5:0x000d, B:6:0x0025, B:8:0x002a, B:14:0x0043, B:16:0x0047, B:22:0x0060, B:18:0x004b, B:21:0x0059, B:10:0x002e, B:13:0x003c), top: B:27:0x0003 }] */
    @Override // org.acra.sender.ReportSender
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void send(android.content.Context r16, org.acra.data.CrashReportData r17) throws org.acra.sender.ReportSenderException {
        /*
            r15 = this;
            r14 = r15
            r0 = r17
            android.net.Uri r1 = r14.mFormUri     // Catch: java.lang.Exception -> Lab
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Exception -> Lab
            boolean r2 = org.acra.ACRA.DEV_LOGGING     // Catch: java.lang.Exception -> Lab
            if (r2 == 0) goto L25
            org.acra.log.ACRALog r2 = org.acra.ACRA.log     // Catch: java.lang.Exception -> Lab
            java.lang.String r3 = org.acra.ACRA.LOG_TAG     // Catch: java.lang.Exception -> Lab
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Lab
            r4.<init>()     // Catch: java.lang.Exception -> Lab
            java.lang.String r5 = "Connect to "
            r4.append(r5)     // Catch: java.lang.Exception -> Lab
            r4.append(r1)     // Catch: java.lang.Exception -> Lab
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Exception -> Lab
            r2.d(r3, r4)     // Catch: java.lang.Exception -> Lab
        L25:
            java.lang.String r2 = r14.mUsername     // Catch: java.lang.Exception -> Lab
            r3 = 0
            if (r2 == 0) goto L2e
            java.lang.String r2 = r14.mUsername     // Catch: java.lang.Exception -> Lab
        L2c:
            r6 = r2
            goto L43
        L2e:
            org.acra.config.HttpSenderConfiguration r2 = r14.httpConfig     // Catch: java.lang.Exception -> Lab
            java.lang.String r2 = r2.basicAuthLogin()     // Catch: java.lang.Exception -> Lab
            boolean r2 = r15.isNull(r2)     // Catch: java.lang.Exception -> Lab
            if (r2 == 0) goto L3c
            r6 = r3
            goto L43
        L3c:
            org.acra.config.HttpSenderConfiguration r2 = r14.httpConfig     // Catch: java.lang.Exception -> Lab
            java.lang.String r2 = r2.basicAuthLogin()     // Catch: java.lang.Exception -> Lab
            goto L2c
        L43:
            java.lang.String r2 = r14.mPassword     // Catch: java.lang.Exception -> Lab
            if (r2 == 0) goto L4b
            java.lang.String r2 = r14.mPassword     // Catch: java.lang.Exception -> Lab
        L49:
            r7 = r2
            goto L60
        L4b:
            org.acra.config.HttpSenderConfiguration r2 = r14.httpConfig     // Catch: java.lang.Exception -> Lab
            java.lang.String r2 = r2.basicAuthPassword()     // Catch: java.lang.Exception -> Lab
            boolean r2 = r15.isNull(r2)     // Catch: java.lang.Exception -> Lab
            if (r2 == 0) goto L59
            r7 = r3
            goto L60
        L59:
            org.acra.config.HttpSenderConfiguration r2 = r14.httpConfig     // Catch: java.lang.Exception -> Lab
            java.lang.String r2 = r2.basicAuthPassword()     // Catch: java.lang.Exception -> Lab
            goto L49
        L60:
            org.acra.util.InstanceCreator r2 = new org.acra.util.InstanceCreator     // Catch: java.lang.Exception -> Lab
            r2.<init>()     // Catch: java.lang.Exception -> Lab
            org.acra.config.CoreConfiguration r3 = r14.config     // Catch: java.lang.Exception -> Lab
            java.lang.Class r3 = r3.attachmentUriProvider()     // Catch: java.lang.Exception -> Lab
            org.acra.sender.-$$Lambda$SRgNHF0fTRPu4DS24hMYEAdAspw r4 = new org.acra.util.InstanceCreator.Fallback() { // from class: org.acra.sender.-$$Lambda$SRgNHF0fTRPu4DS24hMYEAdAspw
                static {
                    /*
                        org.acra.sender.-$$Lambda$SRgNHF0fTRPu4DS24hMYEAdAspw r0 = new org.acra.sender.-$$Lambda$SRgNHF0fTRPu4DS24hMYEAdAspw
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:org.acra.sender.-$$Lambda$SRgNHF0fTRPu4DS24hMYEAdAspw) org.acra.sender.-$$Lambda$SRgNHF0fTRPu4DS24hMYEAdAspw.INSTANCE org.acra.sender.-$$Lambda$SRgNHF0fTRPu4DS24hMYEAdAspw
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.acra.sender.$$Lambda$SRgNHF0fTRPu4DS24hMYEAdAspw.<clinit>():void");
                }

                {
                    /*
                        r0 = this;
                        r0.<init>()
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.acra.sender.$$Lambda$SRgNHF0fTRPu4DS24hMYEAdAspw.<init>():void");
                }

                @Override // org.acra.util.InstanceCreator.Fallback
                public final java.lang.Object get() {
                    /*
                        r1 = this;
                        org.acra.attachment.DefaultAttachmentProvider r0 = new org.acra.attachment.DefaultAttachmentProvider
                        r0.<init>()
                        org.acra.attachment.AttachmentUriProvider r0 = (org.acra.attachment.AttachmentUriProvider) r0
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.acra.sender.$$Lambda$SRgNHF0fTRPu4DS24hMYEAdAspw.get():java.lang.Object");
                }
            }     // Catch: java.lang.Exception -> Lab
            java.lang.Object r2 = r2.create(r3, r4)     // Catch: java.lang.Exception -> Lab
            org.acra.attachment.AttachmentUriProvider r2 = (org.acra.attachment.AttachmentUriProvider) r2     // Catch: java.lang.Exception -> Lab
            org.acra.config.CoreConfiguration r3 = r14.config     // Catch: java.lang.Exception -> Lab
            r4 = r16
            java.util.List r13 = r2.getAttachments(r4, r3)     // Catch: java.lang.Exception -> Lab
            org.acra.data.StringFormat r2 = r14.mType     // Catch: java.lang.Exception -> Lab
            java.lang.String r11 = r15.convertToString(r0, r2)     // Catch: java.lang.Exception -> Lab
            org.acra.sender.HttpSender$Method r2 = r14.mMethod     // Catch: java.lang.Exception -> Lab
            java.net.URL r12 = r2.createURL(r1, r0)     // Catch: java.lang.Exception -> Lab
            org.acra.config.CoreConfiguration r2 = r14.config     // Catch: java.lang.Exception -> Lab
            org.acra.sender.HttpSender$Method r0 = r14.mMethod     // Catch: java.lang.Exception -> Lab
            org.acra.data.StringFormat r1 = r14.mType     // Catch: java.lang.Exception -> Lab
            java.lang.String r5 = r1.getMatchingHttpContentType()     // Catch: java.lang.Exception -> Lab
            org.acra.config.HttpSenderConfiguration r1 = r14.httpConfig     // Catch: java.lang.Exception -> Lab
            int r8 = r1.connectionTimeout()     // Catch: java.lang.Exception -> Lab
            org.acra.config.HttpSenderConfiguration r1 = r14.httpConfig     // Catch: java.lang.Exception -> Lab
            int r9 = r1.socketTimeout()     // Catch: java.lang.Exception -> Lab
            org.acra.config.HttpSenderConfiguration r1 = r14.httpConfig     // Catch: java.lang.Exception -> Lab
            org.acra.collections.ImmutableMap r10 = r1.httpHeaders()     // Catch: java.lang.Exception -> Lab
            r1 = r15
            r3 = r16
            r4 = r0
            r1.sendHttpRequests(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)     // Catch: java.lang.Exception -> Lab
            return
        Lab:
            r0 = move-exception
            org.acra.sender.ReportSenderException r1 = new org.acra.sender.ReportSenderException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Error while sending "
            r2.append(r3)
            org.acra.config.CoreConfiguration r3 = r14.config
            org.acra.data.StringFormat r3 = r3.reportFormat()
            r2.append(r3)
            java.lang.String r3 = " report via Http "
            r2.append(r3)
            org.acra.sender.HttpSender$Method r3 = r14.mMethod
            java.lang.String r3 = r3.name()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.acra.sender.HttpSender.send(android.content.Context, org.acra.data.CrashReportData):void");
    }

    /* renamed from: org.acra.sender.HttpSender$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$acra$sender$HttpSender$Method;

        static {
            int[] iArr = new int[Method.values().length];
            $SwitchMap$org$acra$sender$HttpSender$Method = iArr;
            try {
                iArr[Method.POST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$acra$sender$HttpSender$Method[Method.PUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    protected void sendHttpRequests(CoreConfiguration coreConfiguration, Context context, Method method, String str, String str2, String str3, int i, int i2, Map<String, String> map, String str4, URL url, List<Uri> list) throws IOException {
        int i3 = AnonymousClass1.$SwitchMap$org$acra$sender$HttpSender$Method[method.ordinal()];
        if (i3 == 1) {
            if (list.isEmpty()) {
                sendWithoutAttachments(coreConfiguration, context, method, str, str2, str3, i, i2, map, str4, url);
                return;
            } else {
                postMultipart(coreConfiguration, context, str, str2, str3, i, i2, map, str4, url, list);
                return;
            }
        }
        if (i3 != 2) {
            return;
        }
        sendWithoutAttachments(coreConfiguration, context, method, str, str2, str3, i, i2, map, str4, url);
        Iterator<Uri> it2 = list.iterator();
        while (it2.hasNext()) {
            putAttachment(coreConfiguration, context, str2, str3, i, i2, map, url, it2.next());
        }
    }

    protected void sendWithoutAttachments(CoreConfiguration coreConfiguration, Context context, Method method, String str, String str2, String str3, int i, int i2, Map<String, String> map, String str4, URL url) throws IOException {
        new DefaultHttpRequest(coreConfiguration, context, method, str, str2, str3, i, i2, map).send(url, str4);
    }

    protected void postMultipart(CoreConfiguration coreConfiguration, Context context, String str, String str2, String str3, int i, int i2, Map<String, String> map, String str4, URL url, List<Uri> list) throws IOException {
        new MultipartHttpRequest(coreConfiguration, context, str, str2, str3, i, i2, map).send(url, Pair.create(str4, list));
    }

    protected void putAttachment(CoreConfiguration coreConfiguration, Context context, String str, String str2, int i, int i2, Map<String, String> map, URL url, Uri uri) throws IOException {
        try {
            new BinaryHttpRequest(coreConfiguration, context, str, str2, i, i2, map).send(new URL(url.toString() + LanguageTag.SEP + UriUtils.getFileNameFromUri(context, uri)), uri);
        } catch (FileNotFoundException e) {
            ACRA.log.w("Not sending attachment", e);
        }
    }

    protected String convertToString(CrashReportData crashReportData, StringFormat stringFormat) throws Exception {
        return stringFormat.toFormattedString(crashReportData, this.config.reportContent(), ContainerUtils.FIELD_DELIMITER, "\n", true);
    }

    private boolean isNull(String str) {
        return str == null || ACRAConstants.NULL_VALUE.equals(str);
    }

    public enum Method {
        POST { // from class: org.acra.sender.HttpSender.Method.1
            @Override // org.acra.sender.HttpSender.Method
            URL createURL(String str, CrashReportData crashReportData) throws MalformedURLException {
                return new URL(str);
            }
        },
        PUT { // from class: org.acra.sender.HttpSender.Method.2
            @Override // org.acra.sender.HttpSender.Method
            URL createURL(String str, CrashReportData crashReportData) throws MalformedURLException {
                return new URL(str + IOUtils.DIR_SEPARATOR_UNIX + crashReportData.getString(ReportField.REPORT_ID));
            }
        };

        abstract URL createURL(String str, CrashReportData crashReportData) throws MalformedURLException;

        /* synthetic */ Method(AnonymousClass1 anonymousClass1) {
            this();
        }
    }
}
