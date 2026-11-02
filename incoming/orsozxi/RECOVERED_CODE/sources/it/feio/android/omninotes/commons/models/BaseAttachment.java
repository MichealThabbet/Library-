package it.feio.android.omninotes.commons.models;

import java.util.Calendar;

/* loaded from: classes2.dex */
public class BaseAttachment {
    private Long id;
    private long length;
    private String mime_type;
    private String name;
    private long size;
    private String uriPath;

    public BaseAttachment() {
        this.id = Long.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    public BaseAttachment(String str, String str2) {
        this.id = Long.valueOf(Calendar.getInstance().getTimeInMillis());
        this.uriPath = str;
        setMime_type(str2);
    }

    public BaseAttachment(Long l, String str, String str2, long j, long j2, String str3) {
        this.id = l;
        this.uriPath = str;
        this.name = str2;
        this.size = j;
        this.length = j2;
        setMime_type(str3);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long l) {
        this.id = l;
    }

    public String getUriPath() {
        return this.uriPath;
    }

    public void setUriPath(String str) {
        this.uriPath = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long j) {
        this.size = j;
    }

    public long getLength() {
        return this.length;
    }

    public void setLength(long j) {
        this.length = j;
    }

    public String getMime_type() {
        return this.mime_type;
    }

    public void setMime_type(String str) {
        this.mime_type = str;
    }
}
