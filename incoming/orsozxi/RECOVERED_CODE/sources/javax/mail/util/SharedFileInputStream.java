package javax.mail.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import javax.mail.internet.SharedInputStream;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes3.dex */
public class SharedFileInputStream extends BufferedInputStream implements SharedInputStream {
    private static int defaultBufferSize = 2048;
    protected long bufpos;
    protected int bufsize;
    protected long datalen;

    /* renamed from: in, reason: collision with root package name */
    protected RandomAccessFile f26in;
    private boolean master;
    private SharedFile sf;
    protected long start;

    @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    static class SharedFile {
        private int cnt;

        /* renamed from: in, reason: collision with root package name */
        private RandomAccessFile f27in;

        SharedFile(String str) throws IOException {
            this.f27in = new RandomAccessFile(str, InternalZipConstants.READ_MODE);
        }

        SharedFile(File file) throws IOException {
            this.f27in = new RandomAccessFile(file, InternalZipConstants.READ_MODE);
        }

        public synchronized RandomAccessFile open() {
            this.cnt++;
            return this.f27in;
        }

        public synchronized void close() throws IOException {
            if (this.cnt > 0) {
                int i = this.cnt - 1;
                this.cnt = i;
                if (i <= 0) {
                    this.f27in.close();
                }
            }
        }

        public synchronized void forceClose() throws IOException {
            if (this.cnt > 0) {
                this.cnt = 0;
                this.f27in.close();
            } else {
                try {
                    this.f27in.close();
                } catch (IOException unused) {
                }
            }
        }

        protected void finalize() throws Throwable {
            try {
                this.f27in.close();
            } finally {
                super.finalize();
            }
        }
    }

    private void ensureOpen() throws IOException {
        if (this.f26in == null) {
            throw new IOException("Stream closed");
        }
    }

    public SharedFileInputStream(File file) throws IOException {
        this(file, defaultBufferSize);
    }

    public SharedFileInputStream(String str) throws IOException {
        this(str, defaultBufferSize);
    }

    public SharedFileInputStream(File file, int i) throws IOException {
        super(null);
        this.start = 0L;
        this.master = true;
        if (i <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        init(new SharedFile(file), i);
    }

    public SharedFileInputStream(String str, int i) throws IOException {
        super(null);
        this.start = 0L;
        this.master = true;
        if (i <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        init(new SharedFile(str), i);
    }

    private void init(SharedFile sharedFile, int i) throws IOException {
        this.sf = sharedFile;
        RandomAccessFile randomAccessFileOpen = sharedFile.open();
        this.f26in = randomAccessFileOpen;
        this.start = 0L;
        this.datalen = randomAccessFileOpen.length();
        this.bufsize = i;
        this.buf = new byte[i];
    }

    private SharedFileInputStream(SharedFile sharedFile, long j, long j2, int i) {
        super(null);
        this.start = 0L;
        this.master = true;
        this.master = false;
        this.sf = sharedFile;
        this.f26in = sharedFile.open();
        this.start = j;
        this.bufpos = j;
        this.datalen = j2;
        this.bufsize = i;
        this.buf = new byte[i];
    }

    private void fill() throws IOException {
        if (this.markpos < 0) {
            this.pos = 0;
            this.bufpos += this.count;
        } else if (this.pos >= this.buf.length) {
            if (this.markpos > 0) {
                int i = this.pos - this.markpos;
                System.arraycopy(this.buf, this.markpos, this.buf, 0, i);
                this.pos = i;
                this.bufpos += this.markpos;
                this.markpos = 0;
            } else if (this.buf.length >= this.marklimit) {
                this.markpos = -1;
                this.pos = 0;
                this.bufpos += this.count;
            } else {
                int i2 = this.pos * 2;
                if (i2 > this.marklimit) {
                    i2 = this.marklimit;
                }
                byte[] bArr = new byte[i2];
                System.arraycopy(this.buf, 0, bArr, 0, this.pos);
                this.buf = bArr;
            }
        }
        this.count = this.pos;
        int length = this.buf.length - this.pos;
        long j = (this.bufpos - this.start) + this.pos + length;
        long j2 = this.datalen;
        if (j > j2) {
            length = (int) (j2 - ((this.bufpos - this.start) + this.pos));
        }
        synchronized (this.f26in) {
            this.f26in.seek(this.bufpos + this.pos);
            int i3 = this.f26in.read(this.buf, this.pos, length);
            if (i3 > 0) {
                this.count = i3 + this.pos;
            }
        }
    }

    @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized int read() throws IOException {
        ensureOpen();
        if (this.pos >= this.count) {
            fill();
            if (this.pos >= this.count) {
                return -1;
            }
        }
        byte[] bArr = this.buf;
        int i = this.pos;
        this.pos = i + 1;
        return bArr[i] & 255;
    }

    private int read1(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.count - this.pos;
        if (i3 <= 0) {
            fill();
            i3 = this.count - this.pos;
            if (i3 <= 0) {
                return -1;
            }
        }
        if (i3 < i2) {
            i2 = i3;
        }
        System.arraycopy(this.buf, this.pos, bArr, i, i2);
        this.pos += i2;
        return i2;
    }

    @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized int read(byte[] bArr, int i, int i2) throws IOException {
        ensureOpen();
        int i3 = i + i2;
        if ((i | i2 | i3 | (bArr.length - i3)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == 0) {
            return 0;
        }
        int i4 = read1(bArr, i, i2);
        if (i4 <= 0) {
            return i4;
        }
        while (i4 < i2) {
            int i5 = read1(bArr, i + i4, i2 - i4);
            if (i5 <= 0) {
                break;
            }
            i4 += i5;
        }
        return i4;
    }

    @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized long skip(long j) throws IOException {
        ensureOpen();
        if (j <= 0) {
            return 0L;
        }
        long j2 = this.count - this.pos;
        if (j2 <= 0) {
            fill();
            j2 = this.count - this.pos;
            if (j2 <= 0) {
                return 0L;
            }
        }
        if (j2 < j) {
            j = j2;
        }
        this.pos = (int) (this.pos + j);
        return j;
    }

    @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized int available() throws IOException {
        ensureOpen();
        return (this.count - this.pos) + in_available();
    }

    private int in_available() throws IOException {
        return (int) ((this.start + this.datalen) - (this.bufpos + this.count));
    }

    @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized void mark(int i) {
        this.marklimit = i;
        this.markpos = this.pos;
    }

    @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        ensureOpen();
        if (this.markpos < 0) {
            throw new IOException("Resetting to invalid mark");
        }
        this.pos = this.markpos;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.f26in == null) {
            return;
        }
        try {
            if (this.master) {
                this.sf.forceClose();
            } else {
                this.sf.close();
            }
        } finally {
            this.sf = null;
            this.f26in = null;
            this.buf = null;
        }
    }

    @Override // javax.mail.internet.SharedInputStream
    public long getPosition() {
        if (this.f26in == null) {
            throw new RuntimeException("Stream closed");
        }
        return (this.bufpos + this.pos) - this.start;
    }

    @Override // javax.mail.internet.SharedInputStream
    public synchronized InputStream newStream(long j, long j2) {
        if (this.f26in == null) {
            throw new RuntimeException("Stream closed");
        }
        if (j < 0) {
            throw new IllegalArgumentException("start < 0");
        }
        if (j2 == -1) {
            j2 = this.datalen;
        }
        return new SharedFileInputStream(this.sf, this.start + j, j2 - j, this.bufsize);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }
}
