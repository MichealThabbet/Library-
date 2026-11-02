package org.apache.poi.hpsf;

import org.apache.poi.util.LittleEndian;

/* loaded from: classes3.dex */
public final class Thumbnail {
    public static int CFTAG_FMTID = -3;
    public static int CFTAG_MACINTOSH = -2;
    public static int CFTAG_NODATA = 0;
    public static int CFTAG_WINDOWS = -1;
    public static int CF_BITMAP = 2;
    public static int CF_DIB = 8;
    public static int CF_ENHMETAFILE = 14;
    public static int CF_METAFILEPICT = 3;
    public static int OFFSET_CF = 8;
    public static int OFFSET_CFTAG = 4;
    public static int OFFSET_WMFDATA = 20;
    private byte[] _thumbnailData;

    public Thumbnail() {
        this._thumbnailData = null;
    }

    public Thumbnail(byte[] bArr) {
        this._thumbnailData = null;
        this._thumbnailData = bArr;
    }

    public byte[] getThumbnail() {
        return this._thumbnailData;
    }

    public void setThumbnail(byte[] bArr) {
        this._thumbnailData = bArr;
    }

    public long getClipboardFormatTag() {
        return LittleEndian.getUInt(getThumbnail(), OFFSET_CFTAG);
    }

    public long getClipboardFormat() throws HPSFException {
        if (getClipboardFormatTag() != CFTAG_WINDOWS) {
            throw new HPSFException("Clipboard Format Tag of Thumbnail must be CFTAG_WINDOWS.");
        }
        return LittleEndian.getUInt(getThumbnail(), OFFSET_CF);
    }

    public byte[] getThumbnailAsWMF() throws HPSFException {
        if (getClipboardFormatTag() != CFTAG_WINDOWS) {
            throw new HPSFException("Clipboard Format Tag of Thumbnail must be CFTAG_WINDOWS.");
        }
        if (getClipboardFormat() != CF_METAFILEPICT) {
            throw new HPSFException("Clipboard Format of Thumbnail must be CF_METAFILEPICT.");
        }
        byte[] thumbnail = getThumbnail();
        int length = thumbnail.length;
        int i = OFFSET_WMFDATA;
        int i2 = length - i;
        byte[] bArr = new byte[i2];
        System.arraycopy(thumbnail, i, bArr, 0, i2);
        return bArr;
    }
}
