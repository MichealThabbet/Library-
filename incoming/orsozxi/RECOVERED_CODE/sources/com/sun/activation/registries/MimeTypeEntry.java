package com.sun.activation.registries;

/* loaded from: classes2.dex */
public class MimeTypeEntry {
    private String extension;
    private String type;

    public MimeTypeEntry(String str, String str2) {
        this.type = str;
        this.extension = str2;
    }

    public String getMIMEType() {
        return this.type;
    }

    public String getFileExtension() {
        return this.extension;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("MIMETypeEntry: ");
        stringBuffer.append(this.type);
        stringBuffer.append(", ");
        stringBuffer.append(this.extension);
        return stringBuffer.toString();
    }
}
