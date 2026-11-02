package org.apache.commons.io.input;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* loaded from: classes3.dex */
public class Tailer implements Runnable {
    private static final int DEFAULT_BUFSIZE = 4096;
    private static final int DEFAULT_DELAY_MILLIS = 1000;
    private static final String RAF_MODE = "r";
    private final long delayMillis;
    private final boolean end;
    private final File file;
    private final byte[] inbuf;
    private final TailerListener listener;
    private final boolean reOpen;
    private volatile boolean run;

    public Tailer(File file, TailerListener tailerListener) {
        this(file, tailerListener, 1000L);
    }

    public Tailer(File file, TailerListener tailerListener, long j) {
        this(file, tailerListener, j, false);
    }

    public Tailer(File file, TailerListener tailerListener, long j, boolean z) {
        this(file, tailerListener, j, z, 4096);
    }

    public Tailer(File file, TailerListener tailerListener, long j, boolean z, boolean z2) {
        this(file, tailerListener, j, z, z2, 4096);
    }

    public Tailer(File file, TailerListener tailerListener, long j, boolean z, int i) {
        this(file, tailerListener, j, z, false, i);
    }

    public Tailer(File file, TailerListener tailerListener, long j, boolean z, boolean z2, int i) {
        this.run = true;
        this.file = file;
        this.delayMillis = j;
        this.end = z;
        this.inbuf = new byte[i];
        this.listener = tailerListener;
        tailerListener.init(this);
        this.reOpen = z2;
    }

    public static Tailer create(File file, TailerListener tailerListener, long j, boolean z, int i) {
        Tailer tailer = new Tailer(file, tailerListener, j, z, i);
        Thread thread = new Thread(tailer);
        thread.setDaemon(true);
        thread.start();
        return tailer;
    }

    public static Tailer create(File file, TailerListener tailerListener, long j, boolean z, boolean z2, int i) {
        Tailer tailer = new Tailer(file, tailerListener, j, z, z2, i);
        Thread thread = new Thread(tailer);
        thread.setDaemon(true);
        thread.start();
        return tailer;
    }

    public static Tailer create(File file, TailerListener tailerListener, long j, boolean z) {
        return create(file, tailerListener, j, z, 4096);
    }

    public static Tailer create(File file, TailerListener tailerListener, long j, boolean z, boolean z2) {
        return create(file, tailerListener, j, z, z2, 4096);
    }

    public static Tailer create(File file, TailerListener tailerListener, long j) {
        return create(file, tailerListener, j, false);
    }

    public static Tailer create(File file, TailerListener tailerListener) {
        return create(file, tailerListener, 1000L, false);
    }

    public File getFile() {
        return this.file;
    }

    public long getDelay() {
        return this.delayMillis;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:80|(1:34)(7:(1:37)|38|(1:40)|62|41|42|(1:87)(5:84|46|47|26|88))|35|38|(0)|62|41|42|(3:82|44|87)(1:86)) */
    /* JADX WARN: Removed duplicated region for block: B:40:0x008c A[Catch: all -> 0x00a7, Exception -> 0x00a9, TRY_LEAVE, TryCatch #3 {Exception -> 0x00a9, blocks: (B:3:0x0005, B:7:0x000d, B:11:0x001d, B:13:0x0023, B:15:0x0027, B:17:0x0030, B:9:0x0016, B:18:0x0038, B:20:0x003c, B:22:0x004c, B:23:0x0051, B:32:0x0066, B:34:0x006e, B:38:0x0088, B:40:0x008c, B:41:0x008f, B:42:0x0094, B:44:0x0098, B:46:0x009c, B:37:0x007c), top: B:66:0x0005 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run() throws java.lang.Throwable {
        /*
            r14 = this;
            r0 = 0
            r2 = 0
            r3 = r0
            r5 = r3
        L5:
            boolean r7 = r14.run     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            java.lang.String r8 = "r"
            if (r7 == 0) goto L38
            if (r2 != 0) goto L38
            java.io.RandomAccessFile r7 = new java.io.RandomAccessFile     // Catch: java.io.FileNotFoundException -> L16 java.lang.Throwable -> La7 java.lang.Exception -> La9
            java.io.File r9 = r14.file     // Catch: java.io.FileNotFoundException -> L16 java.lang.Throwable -> La7 java.lang.Exception -> La9
            r7.<init>(r9, r8)     // Catch: java.io.FileNotFoundException -> L16 java.lang.Throwable -> La7 java.lang.Exception -> La9
            r2 = r7
            goto L1b
        L16:
            org.apache.commons.io.input.TailerListener r7 = r14.listener     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            r7.fileNotFound()     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
        L1b:
            if (r2 != 0) goto L23
            long r7 = r14.delayMillis     // Catch: java.lang.InterruptedException -> L5 java.lang.Throwable -> La7 java.lang.Exception -> La9
            java.lang.Thread.sleep(r7)     // Catch: java.lang.InterruptedException -> L5 java.lang.Throwable -> La7 java.lang.Exception -> La9
            goto L5
        L23:
            boolean r3 = r14.end     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            if (r3 == 0) goto L2f
            java.io.File r3 = r14.file     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            long r3 = r3.length()     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            r5 = r3
            goto L30
        L2f:
            r5 = r0
        L30:
            long r3 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            r2.seek(r5)     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            goto L5
        L38:
            boolean r7 = r14.run     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            if (r7 == 0) goto Laf
            java.io.File r7 = r14.file     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            boolean r7 = org.apache.commons.io.FileUtils.isFileNewer(r7, r3)     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            java.io.File r9 = r14.file     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            long r9 = r9.length()     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            int r11 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r11 >= 0) goto L6c
            org.apache.commons.io.input.TailerListener r7 = r14.listener     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            r7.fileRotated()     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            java.io.RandomAccessFile r7 = new java.io.RandomAccessFile     // Catch: java.io.FileNotFoundException -> L66 java.lang.Throwable -> La7 java.lang.Exception -> La9
            java.io.File r9 = r14.file     // Catch: java.io.FileNotFoundException -> L66 java.lang.Throwable -> La7 java.lang.Exception -> La9
            r7.<init>(r9, r8)     // Catch: java.io.FileNotFoundException -> L66 java.lang.Throwable -> La7 java.lang.Exception -> La9
            org.apache.commons.io.IOUtils.closeQuietly(r2)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61 java.io.FileNotFoundException -> L64
            r5 = r0
        L5c:
            r2 = r7
            goto L38
        L5e:
            r0 = move-exception
            r2 = r7
            goto Lb3
        L61:
            r0 = move-exception
            r2 = r7
            goto Laa
        L64:
            r5 = r0
            r2 = r7
        L66:
            org.apache.commons.io.input.TailerListener r7 = r14.listener     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            r7.fileNotFound()     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            goto L38
        L6c:
            if (r11 <= 0) goto L7a
            long r3 = r14.readLines(r2)     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
        L76:
            r12 = r3
            r3 = r5
            r5 = r12
            goto L88
        L7a:
            if (r7 == 0) goto L88
            r2.seek(r0)     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            long r3 = r14.readLines(r2)     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            goto L76
        L88:
            boolean r7 = r14.reOpen     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            if (r7 == 0) goto L8f
            org.apache.commons.io.IOUtils.closeQuietly(r2)     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
        L8f:
            long r9 = r14.delayMillis     // Catch: java.lang.InterruptedException -> L94 java.lang.Throwable -> La7 java.lang.Exception -> La9
            java.lang.Thread.sleep(r9)     // Catch: java.lang.InterruptedException -> L94 java.lang.Throwable -> La7 java.lang.Exception -> La9
        L94:
            boolean r7 = r14.run     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            if (r7 == 0) goto L38
            boolean r7 = r14.reOpen     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            if (r7 == 0) goto L38
            java.io.RandomAccessFile r7 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            java.io.File r9 = r14.file     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            r7.<init>(r9, r8)     // Catch: java.lang.Throwable -> La7 java.lang.Exception -> La9
            r7.seek(r5)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            goto L5c
        La7:
            r0 = move-exception
            goto Lb3
        La9:
            r0 = move-exception
        Laa:
            org.apache.commons.io.input.TailerListener r1 = r14.listener     // Catch: java.lang.Throwable -> La7
            r1.handle(r0)     // Catch: java.lang.Throwable -> La7
        Laf:
            org.apache.commons.io.IOUtils.closeQuietly(r2)
            return
        Lb3:
            org.apache.commons.io.IOUtils.closeQuietly(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.io.input.Tailer.run():void");
    }

    public void stop() {
        this.run = false;
    }

    private long readLines(RandomAccessFile randomAccessFile) throws IOException {
        int i;
        StringBuilder sb = new StringBuilder();
        long filePointer = randomAccessFile.getFilePointer();
        long filePointer2 = filePointer;
        boolean z = false;
        while (this.run && (i = randomAccessFile.read(this.inbuf)) != -1) {
            for (int i2 = 0; i2 < i; i2++) {
                byte b = this.inbuf[i2];
                if (b == 10) {
                    this.listener.handle(sb.toString());
                    sb.setLength(0);
                    filePointer = i2 + filePointer2 + 1;
                    z = false;
                } else if (b != 13) {
                    if (z) {
                        this.listener.handle(sb.toString());
                        sb.setLength(0);
                        filePointer = i2 + filePointer2 + 1;
                        z = false;
                    }
                    sb.append((char) b);
                } else {
                    if (z) {
                        sb.append('\r');
                    }
                    z = true;
                }
            }
            filePointer2 = randomAccessFile.getFilePointer();
        }
        randomAccessFile.seek(filePointer);
        return filePointer;
    }
}
