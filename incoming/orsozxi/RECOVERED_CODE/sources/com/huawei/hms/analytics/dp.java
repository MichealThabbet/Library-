package com.huawei.hms.analytics;

import android.text.TextUtils;
import android.webkit.URLUtil;
import com.huawei.hms.analytics.core.log.HiLog;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

/* loaded from: classes2.dex */
public final class dp {
    private static final String[] lmn = {"e2f856b9f9a4fd4cb2795aeaf83268e4bff189aaec05d691ffde76e075b82648", "173cf86fe9894a0f70dadd09d4fd88c380836099d4939f8c3754361bdc16a32b", "b368b110e3b565fe97c91f786e11bc48754cc8e4e6f21d8a94a68ac6ad67aaaf", "db48223fd9e143f7e133c57f5d08a4e38549ce3ebd921fe3b4003c26e5e35bed", "4bdecdf772491e35c4e8b48f88aee22bae1311984f2e1da4dfad0b78ee7f5163", "3081a0adab3018d57165e6dd24074bdbac640f6dbe21a9e24d3474a87ebf38b8", "db53fcdc9ab71e9bdd4eab257fe1aba7989ad2b24fbe3a85dfef72ea1dd6bae2", "d80f18e8081b624cc64985f87f70118f1702985d2e10dbc985ee7be334fd3c7d", "5fed96c85bd58c58aadbd465c172a4c9a794d8eb2f86cbc7bcee6caf4c7a2c5f", "07ff9b7aeeff969173c45b285fe0fecdbaae244576ff7a2796a36f1c0c11adb4", "92974c6802419e4d18b5ec536cbfa167b8e8eff09ec4c8510a5b95750b1e0c82", "403f14ad2f0e5eb3c4f3a0bcd5c1592cc4492662ad53191c92905255d4990656", "4230baa077b401374d0fc012375047e79ea0790d58d095ef18d97d95470c738d", "f8d927750a0952ffb5bd87dfb83d781ae65f7bed043a7886d1d3cdcfc94bb77a"};

    private static String klm(String str) {
        if (TextUtils.isEmpty(str)) {
            HiLog.w("UriToolsKit", "url is null");
            return str;
        }
        try {
            if (URLUtil.isNetworkUrl(str) && !str.toLowerCase(Locale.US).startsWith("http:")) {
                return new URI(str).getHost();
            }
            HiLog.e("UriToolsKit", "url don't starts with https");
            return null;
        } catch (URISyntaxException unused) {
            HiLog.e("UriToolsKit", "getHostByURI error : ");
            return null;
        }
    }

    public static boolean lmn(String str) {
        for (String str2 : lmn) {
            if (lmn(str, str2)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0067 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean lmn(java.lang.String r9, java.lang.String r10) {
        /*
            java.lang.String r9 = klm(r9)
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            java.lang.String r1 = "."
            r2 = 0
            r3 = 1
            java.lang.String r4 = "UriToolsKit"
            if (r0 != 0) goto L5e
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            if (r0 == 0) goto L17
            goto L5e
        L17:
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 == 0) goto L25
            java.lang.String r0 = "url is null"
            com.huawei.hms.analytics.core.log.HiLog.i(r4, r0)
            r0 = r9
            goto L53
        L25:
            java.lang.String r0 = "\\."
            java.lang.String[] r0 = r9.split(r0)
            int r5 = r0.length
            r6 = 2
            if (r5 >= r6) goto L32
            java.lang.String r0 = ""
            goto L53
        L32:
            java.lang.StringBuffer r5 = new java.lang.StringBuffer
            r5.<init>()
            int r7 = r0.length
            int r7 = r7 - r6
            r7 = r0[r7]
            r5.append(r7)
            r7 = 1
        L3f:
            if (r7 >= r6) goto L4f
            r5.append(r1)
            int r8 = r0.length
            int r8 = r8 - r6
            int r8 = r8 + r3
            r8 = r0[r8]
            r5.append(r8)
            int r7 = r7 + 1
            goto L3f
        L4f:
            java.lang.String r0 = r5.toString()
        L53:
            boolean r5 = android.text.TextUtils.isEmpty(r0)
            if (r5 == 0) goto L5c
            java.lang.String r0 = "get urlLastNStr is null"
            goto L61
        L5c:
            r2 = r0
            goto L64
        L5e:
            java.lang.String r0 = "url or allowlistHash is null"
        L61:
            com.huawei.hms.analytics.core.log.HiLog.e(r4, r0)
        L64:
            r0 = 0
            if (r2 != 0) goto L68
            return r0
        L68:
            java.lang.String r5 = com.huawei.hms.analytics.de.lmn(r9)
            boolean r5 = r10.equals(r5)
            if (r5 == 0) goto L73
            return r3
        L73:
            java.lang.String r3 = com.huawei.hms.analytics.de.lmn(r2)
            boolean r10 = r10.equals(r3)
            if (r10 == 0) goto La1
            int r10 = r9.length()     // Catch: java.lang.Exception -> L98 java.lang.IndexOutOfBoundsException -> L9e
            int r2 = r2.length()     // Catch: java.lang.Exception -> L98 java.lang.IndexOutOfBoundsException -> L9e
            int r10 = r10 - r2
            java.lang.String r9 = r9.substring(r0, r10)     // Catch: java.lang.Exception -> L98 java.lang.IndexOutOfBoundsException -> L9e
            boolean r10 = r9.endsWith(r1)     // Catch: java.lang.Exception -> L98 java.lang.IndexOutOfBoundsException -> L9e
            if (r10 != 0) goto L91
            return r0
        L91:
            java.lang.String r10 = "^[A-Za-z0-9.-]+$"
            boolean r9 = r9.matches(r10)     // Catch: java.lang.Exception -> L98 java.lang.IndexOutOfBoundsException -> L9e
            return r9
        L98:
            java.lang.String r9 = "other Exception!"
        L9a:
            com.huawei.hms.analytics.core.log.HiLog.e(r4, r9)
            return r0
        L9e:
            java.lang.String r9 = "IndexOutOfBounds Exception"
            goto L9a
        La1:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.analytics.dp.lmn(java.lang.String, java.lang.String):boolean");
    }
}
