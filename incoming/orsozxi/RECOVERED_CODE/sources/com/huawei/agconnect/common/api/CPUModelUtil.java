package com.huawei.agconnect.common.api;

/* loaded from: classes2.dex */
public class CPUModelUtil {
    private static final String FILE_KEY = "Hardware";
    private static final String FILE_PATH = "/proc/cpuinfo";
    private static final String SPLIT_KEY = ":";
    private static final String TAG = "CPUModelUtil";

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0038, code lost:
    
        r0 = r3.split(com.huawei.agconnect.common.api.CPUModelUtil.SPLIT_KEY)[1].trim();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0042, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0046, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0047, code lost:
    
        com.huawei.agconnect.common.api.Logger.e(com.huawei.agconnect.common.api.CPUModelUtil.TAG, "close cpuinfo io error :" + r3.getMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x005e, code lost:
    
        r4.close();
        r3 = r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getCpuModel() throws java.lang.Throwable {
        /*
            java.lang.String r0 = ":"
            java.lang.String r1 = "close cpuinfo io error :"
            java.lang.String r2 = "CPUModelUtil"
            r3 = 0
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L73 java.io.FileNotFoundException -> L99
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L73 java.io.FileNotFoundException -> L99
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L73 java.io.FileNotFoundException -> L99
            java.lang.String r7 = "/proc/cpuinfo"
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L73 java.io.FileNotFoundException -> L99
            java.lang.String r7 = "UTF-8"
            r5.<init>(r6, r7)     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L73 java.io.FileNotFoundException -> L99
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L73 java.io.FileNotFoundException -> L99
        L1a:
            java.lang.String r3 = r4.readLine()     // Catch: java.lang.Throwable -> L69 java.io.IOException -> L6c java.io.FileNotFoundException -> L6f
            if (r3 == 0) goto L5e
            java.lang.String r5 = "Hardware"
            boolean r5 = r3.contains(r5)     // Catch: java.lang.Throwable -> L69 java.io.IOException -> L6c java.io.FileNotFoundException -> L6f
            if (r5 == 0) goto L1a
            java.lang.String[] r5 = r3.split(r0)     // Catch: java.lang.Throwable -> L69 java.io.IOException -> L6c java.io.FileNotFoundException -> L6f
            int r5 = r5.length     // Catch: java.lang.Throwable -> L69 java.io.IOException -> L6c java.io.FileNotFoundException -> L6f
            r6 = 1
            if (r5 <= r6) goto L1a
            java.lang.String[] r5 = r3.split(r0)     // Catch: java.lang.Throwable -> L69 java.io.IOException -> L6c java.io.FileNotFoundException -> L6f
            r5 = r5[r6]     // Catch: java.lang.Throwable -> L69 java.io.IOException -> L6c java.io.FileNotFoundException -> L6f
            if (r5 == 0) goto L1a
            java.lang.String[] r0 = r3.split(r0)     // Catch: java.lang.Throwable -> L69 java.io.IOException -> L6c java.io.FileNotFoundException -> L6f
            r0 = r0[r6]     // Catch: java.lang.Throwable -> L69 java.io.IOException -> L6c java.io.FileNotFoundException -> L6f
            java.lang.String r0 = r0.trim()     // Catch: java.lang.Throwable -> L69 java.io.IOException -> L6c java.io.FileNotFoundException -> L6f
            r4.close()     // Catch: java.io.IOException -> L46
            goto L5d
        L46:
            r3 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r1)
            java.lang.String r1 = r3.getMessage()
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            com.huawei.agconnect.common.api.Logger.e(r2, r1)
        L5d:
            return r0
        L5e:
            r4.close()     // Catch: java.io.IOException -> L62
            goto Lbb
        L62:
            r0 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            goto Laa
        L69:
            r0 = move-exception
            r3 = r4
            goto Lbe
        L6c:
            r0 = move-exception
            r3 = r4
            goto L74
        L6f:
            r3 = r4
            goto L99
        L71:
            r0 = move-exception
            goto Lbe
        L73:
            r0 = move-exception
        L74:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L71
            r4.<init>()     // Catch: java.lang.Throwable -> L71
            java.lang.String r5 = "read cpuinfo file error :"
            r4.append(r5)     // Catch: java.lang.Throwable -> L71
            java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L71
            r4.append(r0)     // Catch: java.lang.Throwable -> L71
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L71
            com.huawei.agconnect.common.api.Logger.e(r2, r0)     // Catch: java.lang.Throwable -> L71
            if (r3 == 0) goto Lbb
            r3.close()     // Catch: java.io.IOException -> L92
            goto Lbb
        L92:
            r0 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            goto Laa
        L99:
            java.lang.String r0 = "can not find cpuinfo file"
            com.huawei.agconnect.common.api.Logger.e(r2, r0)     // Catch: java.lang.Throwable -> L71
            if (r3 == 0) goto Lbb
            r3.close()     // Catch: java.io.IOException -> La4
            goto Lbb
        La4:
            r0 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
        Laa:
            r3.append(r1)
            java.lang.String r0 = r0.getMessage()
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            com.huawei.agconnect.common.api.Logger.e(r2, r0)
        Lbb:
            java.lang.String r0 = ""
            return r0
        Lbe:
            if (r3 == 0) goto Ldb
            r3.close()     // Catch: java.io.IOException -> Lc4
            goto Ldb
        Lc4:
            r3 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r1)
            java.lang.String r1 = r3.getMessage()
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            com.huawei.agconnect.common.api.Logger.e(r2, r1)
        Ldb:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.agconnect.common.api.CPUModelUtil.getCpuModel():java.lang.String");
    }
}
