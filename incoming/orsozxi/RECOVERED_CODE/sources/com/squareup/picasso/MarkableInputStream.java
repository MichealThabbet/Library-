package com.squareup.picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
final class MarkableInputStream extends InputStream {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private long defaultMark;

    /* renamed from: in, reason: collision with root package name */
    private final InputStream f17in;
    private long limit;
    private long offset;
    private long reset;

    public MarkableInputStream(InputStream inputStream) {
        this(inputStream, 4096);
    }

    public MarkableInputStream(InputStream inputStream, int i) {
        this.defaultMark = -1L;
        this.f17in = inputStream.markSupported() ? inputStream : new BufferedInputStream(inputStream, i);
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        this.defaultMark = savePosition(i);
    }

    public long savePosition(int i) throws IOException {
        long j = this.offset + i;
        if (this.limit < j) {
            setLimit(j);
        }
        return this.offset;
    }

    private void setLimit(long j) throws IOException {
        try {
            if (this.reset < this.offset && this.offset <= this.limit) {
                this.f17in.reset();
                this.f17in.mark((int) (j - this.reset));
                skip(this.reset, this.offset);
            } else {
                this.reset = this.offset;
                this.f17in.mark((int) (j - this.offset));
            }
            this.limit = j;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to mark: " + e);
        }
    }

    @Override // java.io.InputStream
    public void reset() throws IOException {
        reset(this.defaultMark);
    }

    public void reset(long j) throws IOException {
        if (this.offset > this.limit || j < this.reset) {
            throw new IOException("Cannot reset");
        }
        this.f17in.reset();
        skip(this.reset, j);
        this.offset = j;
    }

    private void skip(long j, long j2) throws IOException {
        while (j < j2) {
            long jSkip = this.f17in.skip(j2 - j);
            if (jSkip == 0) {
                if (read() == -1) {
                    return;
                } else {
                    jSkip = 1;
                }
            }
            j += jSkip;
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i = this.f17in.read();
        if (i != -1) {
            this.offset++;
        }
        return i;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        int i = this.f17in.read(bArr);
        if (i != -1) {
            this.offset += i;
        }
        return i;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.f17in.read(bArr, i, i2);
        if (i3 != -1) {
            this.offset += i3;
        }
        return i3;
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        long jSkip = this.f17in.skip(j);
        this.offset += jSkip;
        return jSkip;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.f17in.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.f17in.close();
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.f17in.markSupported();
    }
}
